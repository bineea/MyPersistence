package com.tryimpl.IPersistence.sqlSession;

import com.tryimpl.IPersistence.pojo.Configuration;

import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> findAll(String statementId, Object... parameter) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        return simpleExecutor.query(configuration, configuration.getMappedStatementMap().get(statementId), parameter);
    }

    @Override
    public <E> E findOne(String statementId, Object... parameter) throws Exception {
        List<E> resultList = this.findAll(statementId, parameter);
        if(resultList == null || resultList.isEmpty()) {
            return null;
        } else if(resultList.size() > 1) {
            throw new RuntimeException("存在多条符合条件的数据");
        } else {
            return resultList.get(0);
        }
    }
}
