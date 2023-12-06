package com.protsenko.compiler.operators;

import org.springframework.stereotype.Component;

import java.util.Objects;

public class WhileUnit extends Unit
{
    private final static String KEY_WORD = "while";

    public WhileUnit()
    {
    }

    public void addOperator(Operator operator)
    {
        this.operators.add(operator);
    }

    @Override
    public boolean apply(String startCode)
    {
        return Objects.equals(KEY_WORD, startCode);//Objects.equals(this., startCode);
    }



}
