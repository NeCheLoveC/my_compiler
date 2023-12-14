package com.protsenko.test.entity;

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
    }

    public Map<String, RawFunction> getRawFunctions() {
        return rawFunctions;
    }
}
