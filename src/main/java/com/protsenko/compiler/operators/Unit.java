package com.protsenko.compiler.operators;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class Unit implements Operator
{
    protected List<Operator> operators = new LinkedList<>();

    @Override
    public String convertToJavaCode()
    {
        StringBuilder result = new StringBuilder();

        for(Operator operator : operators)
        {
            result.append(operator.convertToJavaCode());
            result.append("\n");
        }

        return result.toString();
    }

    @Override
    public abstract boolean apply(String startCode);

}
