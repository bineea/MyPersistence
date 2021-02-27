package com.tryimpl.IPersistence.sqlSession;

import com.tryimpl.IPersistence.config.XMLConfigBuilder;
import com.tryimpl.IPersistence.pojo.Configuration;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    /**
     * 使用dom4j解析xml配置文件，将解析出来的内容封装到Configuration中
     * 使用Configuration创建SqlSessionFactory
     * @param in
     * @return
     */
    public SqlSessionFactory build(InputStream in) throws Exception {
        //解析配置文件，封装Configuration
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfigXml(in);

        //创建SqlSessionFactory对象，SqlSessionFactory是工厂对象用于创建SqlSession对象
        DefaultSqlSessionFacory defaultSqlSessionFacory = new DefaultSqlSessionFacory(configuration);
        defaultSqlSessionFacory.openSqlSession();
        return defaultSqlSessionFacory;
    }
}
