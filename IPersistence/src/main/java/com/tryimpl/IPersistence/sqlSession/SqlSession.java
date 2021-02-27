package com.tryimpl.IPersistence.sqlSession;

import com.tryimpl.IPersistence.pojo.Configuration;
import com.tryimpl.IPersistence.pojo.MappedStatement;

import java.util.List;

public interface SqlSession {

    /**
     * 查询全部数据
     * @param statementId
     * @param parameter
     * @param <E>
     * @return
     */
    <E> List<E> findAll(String statementId, Object... parameter) throws Exception;

    /**
     * 查询单条数据
     * @param statementId
     * @param parameter
     * @param <E>
     * @return
     */
    <E> E findOne(String statementId, Object... parameter) throws Exception;

}
