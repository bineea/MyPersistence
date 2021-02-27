package com.tryimpl.IPersistence.sqlSession;

public interface SqlSessionFactory {

    /**
     * 创建数据库连接
     * @return
     */
    SqlSession openSqlSession();
}
