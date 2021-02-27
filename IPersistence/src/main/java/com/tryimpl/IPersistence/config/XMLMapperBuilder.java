package com.tryimpl.IPersistence.config;

import com.tryimpl.IPersistence.pojo.Configuration;
import com.tryimpl.IPersistence.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {

    private Configuration configuration;

    XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 使用dom4j解析配置文件，封装配置文件内容为MappedStatement对象
     * @param inputStream
     * @throws DocumentException
     */
    public void parseMapperXml (InputStream inputStream) throws DocumentException {
        //解析Mapper.xml
        Document document = new SAXReader().read(inputStream);
        //获取配置文件根对象，即<mapper>
        Element rootElement = document.getRootElement();
        String nameSpace = rootElement.attributeValue("namespace");
        List<Element> selectList = rootElement.elements("select");
        for(Element selectElement : selectList) {
            String id = selectElement.attributeValue("id");
            String parameterType = selectElement.attributeValue("parameterType");
            String resultType = selectElement.attributeValue("resultType");
            String sql = selectElement.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);
            configuration.getMappedStatementMap().put(nameSpace+"."+id, mappedStatement);
        }

    }
}

