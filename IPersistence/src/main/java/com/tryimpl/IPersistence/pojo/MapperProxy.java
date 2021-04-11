package com.tryimpl.IPersistence.pojo;

import com.tryimpl.IPersistence.sqlSession.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class MapperProxy implements InvocationHandler {
    private final SqlSession sqlSession;
    private final Map<Method, MapperMethodInvoker> methodCache;

    public MapperProxy(SqlSession sqlSession, Map<Method, MapperMethodInvoker> methodCache) {
        this.sqlSession = sqlSession;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperMethodInvoker mapperMethodInvoker = handleMethodInvoker(method);
        return mapperMethodInvoker.invoke(proxy, method, args, sqlSession);
    }

    private MapperMethodInvoker handleMethodInvoker(Method method) {
        MapperMethodInvoker mapperMethodInvoker = methodCache.get(method);
        if(mapperMethodInvoker != null) {
            return mapperMethodInvoker;
        }

        return null;
    }

    interface MapperMethodInvoker {
        Object invoke(Object proxy, Method method, Object[] args, SqlSession sqlSession) throws Throwable;
    }

    class SimpleMethodInvoker implements MapperMethodInvoker {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args, SqlSession sqlSession) throws Throwable {

            return null;
        }
    }
}
