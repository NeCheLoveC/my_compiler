package com.protsenko.compiler.operators;

public interface Handler
{
    boolean tryConvertToJavaOperator(Operator operator);
}
