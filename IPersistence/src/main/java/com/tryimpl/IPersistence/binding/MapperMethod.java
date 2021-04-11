package com.tryimpl.IPersistence.binding;

import com.tryimpl.IPersistence.enums.SqlCommandType;

public class MapperMethod {
    private final SqlCommand command;
    private final MethodSignature method;

    public MapperMethod(SqlCommand command, MethodSignature method) {
        this.command = command;
        this.method = method;
    }

    private static class SqlCommand {
        private String name;
        private SqlCommandType sqlCommandType;


    }

    private static class MethodSignature {

    }
}
