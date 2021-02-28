package com.example.IPersistenceTest.test;

import com.example.IPersistenceTest.dao.TestDao;
import com.example.IPersistenceTest.pojo.Test;
import com.tryimpl.IPersistence.config.XMLConfigBuilder;
import com.tryimpl.IPersistence.io.Resources;
import com.tryimpl.IPersistence.sqlSession.SqlSession;
import com.tryimpl.IPersistence.sqlSession.SqlSessionFactory;
import com.tryimpl.IPersistence.sqlSession.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IPersistenceTest {

    public void ipersistenceTest() throws Exception {
        // 实际应用中，会获取代理对象注册为springBean
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapperConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();

        TestDao testDao = sqlSession.getMapper(TestDao.class);

        Test test = new Test();
        test.setId(1);
        System.out.println(testDao.findById(test));

        List<Test> testList = testDao.findAll();
        for(Test testFor : testList) {
            System.out.println(testFor.getId());
        }
    }

    public static void main(String[] args) throws Exception {
        IPersistenceTest test = new IPersistenceTest();
        test.ipersistenceTest();
    }
}
