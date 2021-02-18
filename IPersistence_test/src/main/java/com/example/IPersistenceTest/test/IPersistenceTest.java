package com.example.IPersistenceTest.test;

import com.tryimpl.IPersistence.io.Resources;

import java.io.InputStream;

public class IPersistenceTest {

    public void ipersistenceTest() {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapperConfig.xml");
    }
}
