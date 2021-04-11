package com.example.IPersistenceTest.dao;

import com.example.IPersistenceTest.pojo.Test;

import java.util.List;

public interface TestDao {

    List<Test> findAll();

    Test findById(Test test);

    int updateById(Test test);
}
