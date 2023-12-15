package com.protsenko.test.entity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RawProgram
{
    private static final String MAIN_FUNCTION = "main";
    private Map<String, RawFunction> rawFunctions;
    private RawFunction mainFunction;

    public RawProgram(Map<String, RawFunction> functions) {
        this.rawFunctions = functions;
        if(!functions.containsKey(MAIN_FUNCTION))
            throw new RuntimeException("Точка входа в поток программы - функция main");
        this.mainFunction = functions.get(MAIN_FUNCTION);
        for(Map.Entry<String, RawFunction> f : functions.entrySet())
        {
            f.getValue().setProgram(this);
        }
    }

    public Program compile() throws IOException, CloneNotSupportedException {
        // TODO: 14.12.2023
        Map<String, Function> functionMap = new HashMap<>();
        for(Map.Entry<String,RawFunction> entry : rawFunctions.entrySet())
        {
            Function newFunc = entry.getValue().compile();
            functionMap.put(newFunc.getName(), newFunc);
        }
        return new Program(functionMap);
    }

    public Map<String, RawFunction> getRawFunctions() {
        return rawFunctions;
    }

}
