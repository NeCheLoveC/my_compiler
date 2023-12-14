package com.protsenko.compiler;

import java.util.Collection;
import java.util.LinkedList;


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
