package com.protsenko.compiler;

import com.protsenko.compiler.operators.Handler;
import com.protsenko.compiler.operators.Operator;
import org.springframework.stereotype.Component;

@Component
public class FunctionHandler implements Handler
{
    @Override
    public boolean canHandle(String code) {
        return "func".equals(code);
    }


    public Function.FunctionBuilder build(Parser parser) {
        Function.FunctionBuilder builder = Function.getBuilder();


    }
}
