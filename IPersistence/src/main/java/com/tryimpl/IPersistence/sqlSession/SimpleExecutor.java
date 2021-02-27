package com.tryimpl.IPersistence.sqlSession;

import com.tryimpl.IPersistence.pojo.Configuration;
import com.tryimpl.IPersistence.pojo.MappedStatement;
import com.tryimpl.IPersistence.utils.GenericTokenParser;
import com.tryimpl.IPersistence.utils.ParameterMapping;
import com.tryimpl.IPersistence.utils.ParameterMappingTokenHandler;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        Class parameterClass = getParameterlass(parameterType);
        for(int i=0; i<parameterMappings.size(); i++ ) {
            Object obj = null;
            if(parameterClass.isPrimitive()) {
                obj = parameters[i];
            } else {
                Field field = parameterClass.getDeclaredField(parameterMappings.get(i).getContent());
                field.setAccessible(true);
                obj = field.get(parameters[i]);
            }
            preparedStatement.setObject(i+1, obj);
        }

        //6. 执行sql语句
        preparedStatement.executeQuery();

        //7. 封装返回结果


        return null;
    }

    private Class getParameterlass(String parameterType) throws ClassNotFoundException {
        if(parameterType != null && !parameterType.trim().equals("")) {
            return Class.forName(parameterType);
        } else {
            return null;
        }
    }
}
