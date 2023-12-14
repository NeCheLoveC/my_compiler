package com.protsenko.test.parser;

import com.protsenko.compiler.Coordinate;
import com.protsenko.test.entity.*;

import com.protsenko.compiler.operators.Operator;

import com.protsenko.test.OperatorsManager;
import com.protsenko.test.entity.RawProgram;


import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FunctionParser extends StringWithBlockAbstractParser
{
    String name = "";
    private Class<?> returnedType;
    private Map<String, Class> params;
    private RawProgram rawProgram;

    public FunctionParser(RawFunction rawFunction,OperatorsManager operatorsManager) {
        super(rawFunction.getCurrentCoordinate(), rawFunction.getCode(), null,null);
        this.rawProgram = rawFunction.getProgram();
        this.name = rawFunction.getName();
        this.returnedType = rawFunction.getReturnedType();
        this.params = rawFunction.getParams();
    }

    public Class<?> getReturnedType() {
        return returnedType;
    }

    public Map<String, Class> getParams() {
        return params;
    }

    public String getName() {
        return name;
    }

    public RawProgram getRawProgram() {
        return rawProgram;
    }

    public void setRawProgram(RawProgram rawProgram) {
        this.rawProgram = rawProgram;
    }

    public Function startParse() throws IOException, CloneNotSupportedException {
        List<StringParser> operators = new LinkedList<>();
        nextChar();
        skipSpaceChars();
        while(!isEndOfFile())
        {
            Coordinate startOperator = getCopyCoordinate();
            String rawOperator = getNextOperator();
            StringParser operator = convertToStringParser(rawOperator, this,startOperator);
            if(operator == null)
                throw new RuntimeException("Не распознан токен\n" + startOperator);
            operator.parse();
            operators.add(operator);
            skipSpaceChars();
        }
        return new Function(name,returnedType,params,operators);
    }

    @Override
    protected boolean haveBlockCode() {
        return true;
    }
}
