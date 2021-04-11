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
    <E> List<E> selectAll(String statementId, Object... parameter) throws Exception;

    /**
     * 查询单条数据
     * @param statementId
     * @param parameter
     * @param <E>
     * @return
     */
    <E> E selectOne(String statementId, Object... parameter) throws Exception;

    /**
     *
     * @param statementId
     * @param parameter
     * @return
     */
    int update(String statementId, Object... parameter) throws Exception;

    /**
     *
     * @param statementId
     * @param parameter
     * @return
     */
    int delete(String statementId, Object parameter);

    /**
     *
     * @param statementId
     * @param parameter
     * @return
     */
    int insert(String statementId, Object parameter);

    /**
     * 创建代理对象
     * @param clazz
     * @param <E>
     * @return
     */
    <E> E getMapper(Class<?> clazz);
}
