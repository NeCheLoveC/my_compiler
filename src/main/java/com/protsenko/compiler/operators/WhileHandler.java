package com.protsenko.compiler.operators;

import org.springframework.stereotype.Component;

@Component
public class WhileHandler implements Handler, Converter
{
    @Override
    public boolean canHandle(String code) {
        return "while".equals(code);
    }

    @Override
    public Operator build(Parser parser) {

    }
}
