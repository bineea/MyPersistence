package com.example.IPersistenceTest.test;

import com.example.IPersistenceTest.dao.TestDao;
import com.example.IPersistenceTest.pojo.Test;
import com.tryimpl.IPersistence.config.XMLConfigBuilder;
import com.tryimpl.IPersistence.io.Resources;
import com.tryimpl.IPersistence.sqlSession.SqlSession;
import com.tryimpl.IPersistence.sqlSession.SqlSessionFactory;
import com.tryimpl.IPersistence.sqlSession.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.time.LocalDateTime;
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

        Test test2Update = new Test();
        test2Update.setId(1L);
        test2Update.setValue(1);
        test2Update.setRemark("20210418-001");
        System.out.println("更新操作："+testDao.updateById(test2Update));

        System.out.println("删除操作："+testDao.deleteById(4L));

        Test test2Insert = new Test();
        test2Insert.setValue(10);
        test2Insert.setFakeValue(10);
        test2Insert.setRemark("20210418-002");
        test2Insert.setCreateTime(LocalDateTime.now());

        System.out.println("插入操作："+testDao.insert(test2Insert));

        List<Test> testList = testDao.findAll();
        for(Test testFor : testList) {
            System.out.println("查询操作："+testFor.toString());
        }
    }

    public static void main(String[] args) throws Exception {
        IPersistenceTest test = new IPersistenceTest();
        test.ipersistenceTest();
    }
}
