package com.protsenko.test.entity;

import com.protsenko.test.Coordinate;

public class RawStatement
{
    private RawFunction belongToFunction;
    private String code;
    private RawProgram rawProgram;
    private Coordinate currentCoordinate;

    public RawStatement(RawFunction belongToFunction, String code, RawProgram rawProgram, Coordinate currentCoordinate) {
        this.belongToFunction = belongToFunction;
        this.code = code;
        this.rawProgram = rawProgram;
        this.currentCoordinate = currentCoordinate;
    }

    public RawFunction getBelongToFunction() {
        return belongToFunction;
    }

    public void setBelongToFunction(RawFunction belongToFunction) {
        this.belongToFunction = belongToFunction;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RawProgram getRawProgram() {
        return rawProgram;
    }

    public void setRawProgram(RawProgram rawProgram) {
        this.rawProgram = rawProgram;
    }

    public Coordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    public void setCurrentCoordinate(Coordinate currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
    }
}
