package com.protsenko.compiler;

import com.protsenko.compiler.operators.Operator;

import java.util.*;

public class Compiler
{
    //while
    //bool | long | string | double | void
    //if | elif | else
    //функции : GetNextLong | GetNextString | GetNextDouble | GetNextBool (0 == false | 1 == true)
    //true | false
    //func
    private static Set<String> keyWords = new HashSet<>()
    {
        {
            add("while");
            add("long");
            add("if");
            add("elif");
            add("else");
            add("bool");
        }
    };

    public String compile(String code)
    {
        throw new RuntimeException();
    }

}
