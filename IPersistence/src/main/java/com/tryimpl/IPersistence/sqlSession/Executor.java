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
     *
     * @param configuration
     * @param mappedStatement
     * @param parameters
     * @return
     */
    int update(Configuration configuration, MappedStatement mappedStatement, Object... parameters) throws SQLException, Exception;
}
