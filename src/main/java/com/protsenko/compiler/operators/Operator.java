package com.protsenko.compiler.operators;

public interface Operator
{
    String convertToJavaCode();
    boolean apply(String code);
}
