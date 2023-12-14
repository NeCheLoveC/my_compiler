package com.protsenko.test.statm;

import com.protsenko.compiler.Coordinate;
import com.protsenko.compiler.operators.Operator;
import com.protsenko.test.entity.RawFunction;
import com.protsenko.test.entity.RawStatement;
import com.protsenko.test.parser.FunctionParser;

import java.util.Map;

public class FunctionCall implements UnitStatment, Operator
{
    private RawFunction function;
    private Coordinate coordinate;

    private Map<String, RawStatement> params;

    public FunctionCall(RawFunction function, Map<String, RawStatement> params) {
        this.function = function;
        this.params = params;
    }

    @Override
    public Class getType() {
        return function.getReturnedType();
    }

    @Override
    public String convertToJavaCode() {
        return null;
    }

    public RawFunction getFunction() {
        return function;
    }

    public void setFunction(RawFunction function) {
        this.function = function;
    }

    public Map<String, RawStatement> getParams() {
        return params;
    }

    public void setParams(Map<String, RawStatement> params) {
        this.params = params;
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
