package com.protsenko.test.statm;

import com.protsenko.test.Coordinate;
import com.protsenko.test.entity.Operator;
import com.protsenko.test.entity.RawFunction;
import com.protsenko.test.entity.RawProgram;
import com.protsenko.test.parser.FunctionParser;

import java.util.Map;

public class FunctionCall implements UnitStatment, Operator
{
    private String name;
    private Map<String, String> params;
    private Coordinate coordinate;
    private RawProgram rawProgram;
    private String statement;

//    private Map<String, RawStatement> params;


    public FunctionCall(String name, Map<String, String> params, Coordinate coordinate, RawProgram rawProgram) {
        this.name = name;
        this.params = params;
        this.coordinate = coordinate;
    }

    public FunctionCall(String name, String statement, Coordinate coordinate, RawProgram rawProgram) {
        this.name = name;
        this.statement = statement;
        this.coordinate = coordinate;
        this.rawProgram = rawProgram;
    }

    @Override
    public Class getType() {
        return rawProgram.getRawFunctions().get(name).getReturnedType();
    }

    @Override
    public String convertToJavaCode() {
        StringBuilder result = new StringBuilder();
        result.append(name);
        result.append("(");
        result.append(statement);
        result.append(")");
        result.append(";\n");
        return result.toString();
    }


    @Override
    public boolean validate(FunctionParser functionParser)
    {
        RawFunction func = functionParser.getRawProgram().getRawFunctions().get(functionParser.getName());
        if(func == null)
            throw new RuntimeException("Не обнаружена функция\n" + coordinate);
        return true;
    }


}
