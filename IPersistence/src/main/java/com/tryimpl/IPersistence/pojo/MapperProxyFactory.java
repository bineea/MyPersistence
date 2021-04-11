package com.tryimpl.IPersistence.pojo;

import com.tryimpl.IPersistence.sqlSession.SqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class MapperProxyFactory<T> {
    private final Class<T> mapperInterface;
    private final Map<Method, MapperProxy.MapperMethodInvoker> methodCache = new HashMap();


    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public T newInstance(SqlSession sqlSession) {
        MapperProxy mapperProxy = new MapperProxy(sqlSession, methodCache);
        T t = (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
        return t;
    }
}
