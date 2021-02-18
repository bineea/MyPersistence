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
    public SqlSessionFactory build(InputStream in) {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfigXml(in);

        return null;
    }
}
