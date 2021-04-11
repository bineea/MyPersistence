package com.tryimpl.IPersistence.sqlSession;

import com.tryimpl.IPersistence.pojo.Configuration;
import com.tryimpl.IPersistence.pojo.MappedStatement;
import com.tryimpl.IPersistence.utils.GenericTokenParser;
import com.tryimpl.IPersistence.utils.ParameterMapping;
import com.tryimpl.IPersistence.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... parameters) throws Exception {
        //1. 注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();

        //2. 获取sql语句，处理sql语句占位符，并将sql语句中的“#{XXX}”替换为“?”
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String jdbcSql = genericTokenParser.parse(mappedStatement.getSql());

        //3. 获取sql语句占位符，解析存储sql语句占位符
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        //4. 获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(jdbcSql);

        //5. 设置参数，赋值占位符
        String parameterType = mappedStatement.getParameterType();
        if(parameterType != null && !parameterType.trim().isEmpty()) {
            Class parameterClass = getClassByName(parameterType);
            for(int i=0; i<parameterMappings.size(); i++ ) {
                Object obj = null;
                if(parameterClass.isPrimitive() || parameterClass.isArray() || Collection.class.isAssignableFrom(parameterClass)) {
                    if(parameterMappings.size() != parameters.length) {
                        throw new RuntimeException("参数个数与sql语句参数个数不匹配");
                    }
                    obj = parameters[i];
                } else {
                    Field field = parameterClass.getDeclaredField(parameterMappings.get(i).getContent());
                    field.setAccessible(true);
                    obj = field.get(parameters[0]);
                }
                preparedStatement.setObject(i+1, obj);
            }
        } else {
            for(int i=0; i<parameterMappings.size(); i++ ) {
                if(parameterMappings.size() != parameters.length) {
                    throw new RuntimeException("参数个数与sql语句参数个数不匹配");
                }
                preparedStatement.setObject(i+1, parameters[i]);
            }
        }

        //6. 执行sql语句
        ResultSet resultSet = preparedStatement.executeQuery();

        //7. 封装返回结果
        Class resultTypeClass = getClassByName(mappedStatement.getResultType());
        List<E> resultList = new ArrayList<>();
        while(resultSet.next()) {
            //创建实例
            Object resultInstance = new Object();
            if(resultTypeClass != null) {
                resultInstance = resultTypeClass.getDeclaredConstructor().newInstance();
            }
            //元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for(int i=1; i<=metaData.getColumnCount(); i++) {
                //字段名称
                String columnName = metaData.getColumnName(i);
                //字段值
                Object columnValue = resultSet.getObject(columnName);
                String resultFieldName = this.handleDatabaseField(columnName);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(resultFieldName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(resultInstance, columnValue);
            }
            resultList.add((E) resultInstance);
        }

        return resultList;
    }

    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object... parameters) throws Exception {
        //1. 注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();

        //2. 获取sql语句，处理sql语句占位符，并将sql语句中的“#{XXX}”替换为“?”
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String jdbcSql = genericTokenParser.parse(mappedStatement.getSql());

        //3. 获取sql语句占位符，解析存储sql语句占位符
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        //4. 获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(jdbcSql);

        //5. 设置参数，赋值占位符
        String parameterType = mappedStatement.getParameterType();
        if(parameterType != null && !parameterType.trim().isEmpty()) {
            Class parameterClass = getClassByName(parameterType);
            for(int i=0; i<parameterMappings.size(); i++ ) {
                Object obj = null;
                if(parameterClass.isPrimitive() || parameterClass.isArray() || Collection.class.isAssignableFrom(parameterClass)) {
                    if(parameterMappings.size() != parameters.length) {
                        throw new RuntimeException("参数个数与sql语句参数个数不匹配");
                    }
                    obj = parameters[i];
                } else {
                    Field field = parameterClass.getDeclaredField(parameterMappings.get(i).getContent());
                    field.setAccessible(true);
                    obj = field.get(parameters[0]);
                }
                preparedStatement.setObject(i+1, obj);
            }
        } else {
            for(int i=0; i<parameterMappings.size(); i++ ) {
                if(parameterMappings.size() != parameters.length) {
                    throw new RuntimeException("参数个数与sql语句参数个数不匹配");
                }
                preparedStatement.setObject(i+1, parameters[i]);
            }
        }

        //6. 执行sql语句
        return preparedStatement.executeUpdate();
    }

    private Class getClassByName(String name) throws ClassNotFoundException {
        if(name != null && !name.trim().equals("")) {
            return Class.forName(name);
        } else {
            return null;
        }
    }

    private String handleDatabaseField(String columnName) {
        if(columnName.contains("_")) {
            String[] columnSplit = columnName.split("_");
            StringBuilder column = new StringBuilder();
            column.append(columnSplit[0]);
            for(int i=1; i<columnSplit.length; i++) {
                column.append(columnSplit[i].substring(0,1).toUpperCase());
                column.append(columnSplit[i].substring(1));
            }
            return column.toString();
        }
        return columnName;
    }
}
