package com.protsenko.compiler.operators;

import com.protsenko.compiler.Coordinate;
import com.protsenko.compiler.Function;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FunctionParser extends ParserAbstract
{
    private String code;
    private Map<String, Function.FunctionBuilder> rawFunc;

    public FunctionParser(Coordinate currentCoordinate, String code, Map<String, Function.FunctionBuilder> rawFunc) {
        super(currentCoordinate);
        this.code = code;
        this.rawFunc = rawFunc;
    }

    public List<Operator> parse() throws IOException {
        List<Operator> operators = new LinkedList<>();
        nextChar();
        skipSpaceChars();
        while(!isEndOfFile())
        {
            skipSpaceChars();
        }
    }

    private String getNextOperator() throws IOException {
        if(isEndOfFile())
            throw new RuntimeException("Ожидалось начало оператора");
        StringBuilder result = new StringBuilder();
        while(!isEndOfFile() || (!isStringLiteral && (currentCoordinate.getCurrentChar() == '.' || currentCoordinate.getCurrentChar() == '{')))
        {
            result.append((char)currentCoordinate.getCurrentChar());
            nextChar();
        }

        if(currentCoordinate.getCurrentChar() == '.')
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


    @Override
    protected int next() throws IOException {
        int c;
        if(currentCoordinate.getCount() >= code.length())
        {
            this.currentCoordinate.setCurrentChar(-1);
            c = -1;
        }
        else
        {
            c = code.charAt(currentCoordinate.getCount());
        }
        return c;
    }
}
