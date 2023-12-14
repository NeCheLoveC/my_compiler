package com.protsenko.compiler;

import com.protsenko.test.handlers.Handler;
import com.protsenko.test.ProgramParser;
import org.springframework.stereotype.Component;

@Component
public class FunctionHandler implements Handler
{
    @Override
    public boolean canHandle(String code) {
        return "func".equals(code);
    }


    public Function.FunctionBuilder build(ProgramParser parser) {
        Function.FunctionBuilder builder = Function.getBuilder();


    }
}
