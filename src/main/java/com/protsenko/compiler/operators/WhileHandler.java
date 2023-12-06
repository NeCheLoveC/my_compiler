package com.protsenko.compiler.operators;

import com.protsenko.compiler.Converter;
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
