package com.protsenko.compiler;

import com.protsenko.compiler.operators.ParserAbstract;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class FunctionController
{
    public Collection<Function> convert(Collection<Function.FunctionBuilder> functionCollection)
    {
        Collection<Function> result = new LinkedList<>();
        for(Function.FunctionBuilder rawFun : functionCollection)
        {
            Function func = rawFun.build();
            result.add(func);
        }

        return result;
    }

    private Function function(Function.FunctionBuilder rawFunction)
    {

    }

}
