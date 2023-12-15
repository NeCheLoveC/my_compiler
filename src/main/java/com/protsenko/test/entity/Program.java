package com.protsenko.test.entity;

import java.util.Map;

public class Program
{
    private Map<String, Function> functionMap;

    public Program(Map<String, Function> functionMap)
    {
        this.functionMap = functionMap;
    }
    public String convertToJavaCode()
    {
        StringBuilder result = new StringBuilder();
        result.append("public class Program\n{");
        for(Map.Entry<String, Function> entry : functionMap.entrySet())
        {
            result.append(entry.getValue().convertToJavaCode());
        }
        result.append("}");
        return result.toString();
    }
}
