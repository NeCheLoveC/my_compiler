package com.protsenko.compiler;

import com.protsenko.compiler.operators.Operator;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Function
{
    private Class<Object> returnedType;
    private Map<String, Class> params = new HashMap<>();
    private String name;
    private List<Operator> operators = new LinkedList<>();
    private Coordinate currentCoordinate;

    public String convertToJavaCode()
    {
        // TODO: 26.11.2023 Добавить к сигнатуру метода перед телом функции
        return operators.stream().map(o -> o.convertToJavaCode()).collect(Collectors.joining());
    }

    public static FunctionBuilder getBuilder()
    {
        return new FunctionBuilder();
    }

    public static class FunctionBuilder
    {
        String code = "";
        String name = "";
        private Class<?> returnedType;
        private Map<String, Class> params;
        private Coordinate startBlock;

        public FunctionBuilder setCode(String code) {
            this.code = code;
            return this;
        }

        public FunctionBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public FunctionBuilder setReturnedType(Class<?> returnedType) {
            this.returnedType = returnedType;
            return this;
        }

        public FunctionBuilder setParams(Map<String, Class> params) {
            this.params = params;
            return this;
        }

        public Function.FunctionBuilder setStartBlock(Coordinate startBlock)
        {
            this.startBlock = startBlock;
            return this;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public Class<?> getReturnedType() {
            return returnedType;
        }

        public Map<String, Class> getParams() {
            return params;
        }

        public Function build()
        {

            Character currentChar = null;
            int curPosition = -1;
            for(code.length() > curPosition)
            {
                currentChar = code.charAt(curPosition);

            }
        }
    }
}
