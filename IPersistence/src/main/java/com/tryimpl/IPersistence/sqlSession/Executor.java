package com.tryimpl.IPersistence.sqlSession;

import com.tryimpl.IPersistence.pojo.Configuration;
import com.tryimpl.IPersistence.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

public interface Executor {

    /**
     * jdbc查询操作
     * @param configuration
     * @param mappedStatement
     * @param parameters
     * @param <E>
     * @return
     * @throws Exception
     */
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... parameters) throws Exception;

    /**
     * jdbc更新操作
     * @param configuration
     * @param mappedStatement
     * @param parameters
     * @return
     */
    int update(Configuration configuration, MappedStatement mappedStatement, Object... parameters) throws Exception;

    /**
     * jdbc删除操作
     * @param configuration
     * @param mappedStatement
     * @param parameters
     * @return
     * @throws Exception
     */
    int delete(Configuration configuration, MappedStatement mappedStatement, Object... parameters) throws Exception;

    /**
     * jdbc插入操作
     * @param configuration
     * @param mappedStatement
     * @param parameters
     * @return
     * @throws Exception
     */
    int insert(Configuration configuration, MappedStatement mappedStatement, Object... parameters) throws Exception;
}
