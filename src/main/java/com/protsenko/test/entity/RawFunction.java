package com.protsenko.test.entity;

import com.protsenko.test.Coordinate;
import com.protsenko.test.parser.FunctionParser;

import java.io.IOException;
import java.util.Map;

public class RawFunction
{
    private String name = "";
    private Class<?> returnedType;
    private Map<String, Class> params;
    private Coordinate currentCoordinate;
    private String code;
    private RawProgram program;

    public RawFunction(String name, Class<?> returnedType, Map<String, Class> params, Coordinate currentCoordinate, String code)
    {
        this.name = name;
        this.returnedType = returnedType;
        this.params = params;
        this.currentCoordinate = currentCoordinate;
        this.code = code;
    }

    public Coordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    public Function compile() throws IOException, CloneNotSupportedException {
        return new FunctionParser(this, null, program).parse();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Class<?> getReturnedType() {
        return returnedType;
    }

    public Map<String, Class> getParams() {
        return params;
    }

    public RawProgram getProgram() {
        return program;
    }

    public void setProgram(RawProgram program) {
        this.program = program;
    }
}
