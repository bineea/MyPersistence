package com.tryimpl.IPersistence.config;

import com.tryimpl.IPersistence.enums.SqlCommandType;
import com.tryimpl.IPersistence.pojo.Configuration;
import com.tryimpl.IPersistence.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Arrays;
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
    public void parseMapperXml (InputStream inputStream) throws DocumentException, ClassNotFoundException {
        //解析Mapper.xml
        Document document = new SAXReader().read(inputStream);
        //获取配置文件根对象，即<mapper>
        Element rootElement = document.getRootElement();
        String nameSpace = rootElement.attributeValue("namespace");
        List<Node> nodeList = rootElement.selectNodes("select|insert|update|delete");
        //List<Node> parameterList = rootElement.selectNodes("/mapper/parameterMap");
        //List<Node> resultList = rootElement.selectNodes("/mapper/resultMap");
        for (Node node : nodeList) {
            Element element = (Element) node;
            String elementName = element.getName();
            String id = element.attributeValue("id");
            String parameterType = element.attributeValue("parameterType");
            String resultType = element.attributeValue("resultType");
            //String resultMap = element.attributeValue("resultMap");
            //String parameterMap = element.attributeValue("parameterMap");
            String sql = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);
            mappedStatement.setSqlCommandType(Arrays.asList(SqlCommandType.values()).stream().filter(c -> c.name().equalsIgnoreCase(elementName)).findFirst().get());
            configuration.getMappedStatementMap().put(nameSpace+"."+id, mappedStatement);
        }
        configuration.getMapperRegistry().addMappers(Class.forName(nameSpace, true, this.getClass().getClassLoader()));
    }
}

