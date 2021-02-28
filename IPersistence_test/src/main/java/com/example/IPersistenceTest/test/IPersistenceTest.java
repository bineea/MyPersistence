package com.example.IPersistenceTest.test;

import com.example.IPersistenceTest.pojo.Test;
import com.tryimpl.IPersistence.config.XMLConfigBuilder;
import com.tryimpl.IPersistence.sqlSession.SqlSession;
import com.tryimpl.IPersistence.sqlSession.SqlSessionFactory;
import com.tryimpl.IPersistence.sqlSession.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class IPersistenceTest {

    public void ipersistenceTest() throws Exception {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        InputStream resourceAsStream = XMLConfigBuilder.class.getClassLoader().getResourceAsStream("sqlMapperConfig.xml");
        if(resourceAsStream == null) {
            throw new Exception("配置文件不存在");
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();
        Test test = new Test();
        test.setId(1);
        Test one = sqlSession.findOne("sampletest.findById", test);
        System.out.println(one);
    }

    public static void main(String[] args) throws Exception {
        IPersistenceTest test = new IPersistenceTest();
        test.ipersistenceTest();
    }
}
