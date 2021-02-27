package com.tryimpl.IPersistence.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tryimpl.IPersistence.io.Resources;
import com.tryimpl.IPersistence.pojo.Configuration;
import jdk.internal.util.xml.impl.Input;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 使用dom4j解析配置文件，封装配置文件内容为Configuration对象
     * @param inputStream
     * @return
     */
    public Configuration parseConfigXml(InputStream inputStream) throws Exception {
        //解析sqlMapperConfig.xml
        Document document = new SAXReader().read(inputStream);
        //获取配置文件根对象，即<configuration>
        Element rootElement = document.getRootElement();

        List<Element> dataSourceList = rootElement.elements("dataSource");
        if(dataSourceList.size() > 1) {
            throw new Exception("暂时不支持多个数据源配置");
        }
        Properties dataSourceProperties = new Properties();
        for(Element dataSource : dataSourceList) {
            List<Element> propertyList = dataSource.elements("property");
            for(Element property : propertyList) {
                dataSourceProperties.setProperty(property.attributeValue("name"),property.attributeValue("value"));
            }
        }

        //创建c3p0连接池
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(dataSourceProperties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(dataSourceProperties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(dataSourceProperties.getProperty("username"));
        comboPooledDataSource.setPassword(dataSourceProperties.getProperty("password"));

        configuration.setDataSource(comboPooledDataSource);

        //解析mapper.xml
        List<Element> mapperList = rootElement.elements("mapper");
        List<InputStream> mapperInputStreamList = new ArrayList<>();
        for(Element mapper : mapperList) {
            String resourcePath = mapper.attributeValue("resource");
            Enumeration<URL> resources = Resources.class.getClassLoader().getResources(resourcePath);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                this.toLoadMapper(url, mapperInputStreamList);
            }
        }

        if(mapperInputStreamList != null && !mapperInputStreamList.isEmpty()) {
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            for(InputStream mapperInputStream : mapperInputStreamList) {
                xmlMapperBuilder.parseMapperXml(mapperInputStream);
            }
        }

        return configuration;
    }

    private void toLoadMapper(URL url, List<InputStream> mapperInputStreamList) throws IOException {
        if(url == null) {
            return;
        }
        File mapperFile = new File(url.getPath());
        if(mapperFile == null) {
            return;
        }
        if(mapperFile.isFile()) {
            mapperInputStreamList.add(url.openStream());
        }
        if(mapperFile.isDirectory()) {
            for(File file : mapperFile.listFiles()) {
                toLoadMapper(file.toURI().toURL(), mapperInputStreamList);
            }
        }
    }

}
