package com.protsenko.test.handlers;


import com.protsenko.compiler.operators.Operator;
import com.protsenko.test.parser.FunctionParser;

public interface Handler
{
    Operator tryConvertToJavaOperator(String operator, FunctionParser functionParser);
}
