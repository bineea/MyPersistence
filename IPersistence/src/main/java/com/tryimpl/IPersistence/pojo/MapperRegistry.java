package com.tryimpl.IPersistence.pojo;

import java.util.HashMap;
import java.util.Map;

public class MapperRegistry {

    private final Configuration configuration;
    private Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    public MapperRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Map<Class<?>, MapperProxyFactory<?>> getKnownMappers() {
        return knownMappers;
    }

    public void setKnownMappers(Map<Class<?>, MapperProxyFactory<?>> knownMappers) {
        this.knownMappers = knownMappers;
    }

    public boolean hasMappers(Class<?> type) {
        return knownMappers.get(type) != null ? true : false;
    }

    public void addMappers(Class<?> type) {
        if(type.isInterface()) {
            if(hasMappers(type)) {
                throw new RuntimeException("already exist");
            }
        }
        knownMappers.put(type, new MapperProxyFactory<>(type));
    }
}
