package com.protsenko.compiler;

import com.protsenko.compiler.operators.Operator;

public interface Converter
{
    Operator build (Parser parser);
}
