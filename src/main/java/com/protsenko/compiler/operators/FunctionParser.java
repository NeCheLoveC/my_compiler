package com.protsenko.compiler.operators;

import com.protsenko.compiler.*;
import com.protsenko.test.parser.StringParser;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class FunctionParser extends StringParser
{
    String name;
    private OperatorsManager operatorsManager;
    public FunctionParser(Coordinate currentCoordinate, String name, String code, Function.FunctionBuilder function, OperatorsManager operatorsManager) {
        super(currentCoordinate, code, rawProgram);
        this.name = name;
        this.operatorsManager = operatorsManager;
    }

    public List<Operator> parse() throws IOException {
        List<Operator> operators = new LinkedList<>();
        nextChar();
        skipSpaceChars();
        while(!isEndOfFile())
        {
            String rawOperator = getNextOperator();
            Operator operator = operatorsManager.toOperator(rawOperator, this);
            if(operator == null)
                throw new RuntimeException("Не распознан токен\n" + currentCoordinate);
            operators.add(operator);
            skipSpaceChars();
        }
        return operators;
    }

    private String getNextOperator() throws IOException {
        if(isEndOfFile())
            throw new RuntimeException("Ожидалось начало оператора");
        StringBuilder result = new StringBuilder();
        while(!isEndOfFile() && (!isStringLiteral && (currentCoordinate.getCurrentChar() == END_OPERATOR || currentCoordinate.getCurrentChar() == '{')))
        {
            result.append((char)currentCoordinate.getCurrentChar());
            nextChar();
        }

        if(currentCoordinate.getCurrentChar() == END_OPERATOR)
        {
            result.append((char)currentCoordinate.getCurrentChar());
            nextChar();
        }
        else if(isStartBlockCode())
        {
            String block = expectedBlockCode();
            result.append(block);
        }
        else
            throw new RemoteException("Expected - .\n" + currentCoordinate);
        return result.toString();
    }
}
