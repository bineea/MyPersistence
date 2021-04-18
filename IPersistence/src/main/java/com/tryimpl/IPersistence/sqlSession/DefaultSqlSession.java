package com.tryimpl.IPersistence.sqlSession;

import com.tryimpl.IPersistence.enums.SqlCommandType;
import com.tryimpl.IPersistence.pojo.Configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectAll(String statementId, Object... parameter) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        return simpleExecutor.query(configuration, configuration.getMappedStatementMap().get(statementId), parameter);
    }

    @Override
    public <E> E selectOne(String statementId, Object... parameter) throws Exception {
        List<E> resultList = this.selectAll(statementId, parameter);
        if(resultList == null || resultList.isEmpty()) {
            return null;
        } else if(resultList.size() > 1) {
            throw new RuntimeException("存在多条符合条件的数据");
        } else {
            return resultList.get(0);
        }
    }

    @Override
    public int update(String statementId, Object... parameter) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        return simpleExecutor.update(configuration, configuration.getMappedStatementMap().get(statementId), parameter);
    }

    @Override
    public int delete(String statementId, Object... parameter) throws Exception {
        return update(statementId, parameter);
    }

    @Override
    public int insert(String statementId, Object... parameter) throws Exception {
        return update(statementId, parameter);
    }

    @Override
    public <E> E getMapper(Class<?> clazz) {

        Object result = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String clazzName = method.getDeclaringClass().getName();
                String methodName = method.getName();
                // 因为无法通过dao获取mapper配置文件中的namespace和id，所以强制规定mapper配置文件的namespace就是dao的全限定名，而mapper配置文件方法的id就是dao的方法名
                String statementId = clazzName + "." + methodName;
                SqlCommandType sqlCommandType = configuration.getMappedStatementMap().get(statementId).getSqlCommandType();
                if(sqlCommandType == SqlCommandType.SELECT) {
                    Class<?> returnType = method.getReturnType();
                    // 判断method返回结果是否为集合，决定调用selectOne方法还是selectAll方法
                    if (returnType.isArray() || Collection.class.isAssignableFrom(returnType)) {
                        return selectAll(statementId, args);
                    } else {
                        return selectOne(statementId, args);
                    }
                } else if(sqlCommandType == SqlCommandType.UPDATE) {
                    return update(statementId, args);
                } else if(sqlCommandType == SqlCommandType.DELETE) {
                    return delete(statementId, args);
                } else if(sqlCommandType == sqlCommandType.INSERT) {
                    return insert(statementId, args);
                }

                return null;
            }
        });

        return (E) result;
    }
}
