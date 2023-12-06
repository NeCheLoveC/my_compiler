package com.protsenko.compiler.operators;

import com.protsenko.compiler.Parser;

public interface Handler
{
    boolean canHandle(String code);
}
