package com.example.IPersistenceTest.test;

import com.tryimpl.IPersistence.config.XMLConfigBuilder;

import java.io.InputStream;

public class IPersistenceTest {

    public void ipersistenceTest() throws Exception {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        InputStream resourceAsStream = XMLConfigBuilder.class.getClassLoader().getResourceAsStream("sqlMapperConfig.xml");
        if(resourceAsStream == null) {
            throw new Exception("配置文件不存在");
        }
        xmlConfigBuilder.parseConfigXml(resourceAsStream);
    }

    public static void main(String[] args) throws Exception {
        IPersistenceTest test = new IPersistenceTest();
        test.ipersistenceTest();
    }
}
