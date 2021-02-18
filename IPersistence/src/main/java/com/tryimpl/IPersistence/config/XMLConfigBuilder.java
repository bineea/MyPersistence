package com.tryimpl.IPersistence.config;

import com.tryimpl.IPersistence.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLConfigBuilder {

    /**
     * 使用dom4j解析配置文件，封装配置文件内容为Configuration对象
     * @param inputStream
     * @return
     */
    public Configuration parseConfigXml(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        //获取配置文件根对象，即<configuration>
        Element rootElement = document.getRootElement();
        //获取配置文件根对象中任意位置的所有<property>配置内容
        //List<Node> nodeList = rootElement.selectNodes("//property");

        return null;
    }
}
