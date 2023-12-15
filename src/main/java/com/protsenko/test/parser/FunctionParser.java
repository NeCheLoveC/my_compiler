package com.protsenko.test.parser;

import com.protsenko.test.Coordinate;
import com.protsenko.test.entity.*;

import com.protsenko.test.entity.Operator;

import com.protsenko.test.OperatorsManager;
import com.protsenko.test.entity.RawProgram;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FunctionParser extends StringWithBlockAbstractParser
{
    String name = "";
    private Class<?> returnedType;
    private Map<String, Class> params;

    public FunctionParser(RawFunction rawFunction,OperatorsManager operatorsManager, RawProgram rawProgram) {
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

    public Function parse() throws IOException, CloneNotSupportedException {
        List<StringParser> rawOperators = new LinkedList<>();
        List<Operator> operatorList = new LinkedList<>();
        nextChar();
        skipSpaceChars();
        while(!isEndOfFile())
        {
            Coordinate startOperator = getCopyCoordinate();
            String rawOperator = getNextOperator();
            StringParser operator = convertToStringParser(rawOperator, this,startOperator);
            if(operator == null)
                throw new RuntimeException("Не распознан токен\n" + startOperator);
            Operator compiledOperator = operator.parse();
            operatorList.add(compiledOperator);
            rawOperators.add(operator);
            skipSpaceChars();
        }
        return new Function(name,returnedType,params,operatorList);
    }

    @Override
    protected boolean haveBlockCode() {
        return true;
    }
}
