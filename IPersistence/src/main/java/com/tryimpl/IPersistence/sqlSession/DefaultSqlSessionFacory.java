package com.tryimpl.IPersistence.sqlSession;

import com.tryimpl.IPersistence.pojo.Configuration;

public class DefaultSqlSessionFacory implements SqlSessionFactory{

    private Configuration configuration;

    public DefaultSqlSessionFacory(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public SqlSession openSqlSession() {
        return new DefaultSqlSession(configuration);
    }
}
