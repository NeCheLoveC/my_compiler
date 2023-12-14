package com.protsenko.test.parser;

import com.protsenko.compiler.Coordinate;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Deprecated
public class BlockParser extends StringParser
{
    public BlockParser(Coordinate currentCoordinate, String code, StringWithBlockAbstractParser parent) {
        super(currentCoordinate, code, parent);
    }

    @Override
    protected boolean haveBlockCode() {
        return true;
    }

    @Override
    public void parse() throws IOException, CloneNotSupportedException {
        throw new RuntimeException("Данный метод не поддреживается");
    }

    public List<StringParser> getOperators(StringWithBlockAbstractParser parent) throws IOException {
        List<StringParser> result = new LinkedList<>();
        if(currentCoordinate.getCurrentChar() != '{')
            throw new RuntimeException("Ожидалось - {\n" + currentCoordinate);
        while(!isEndOfFile())
        {
            String operator = getNextOperator();
            StringParser stringParser = convertToStringParser(operator, parent);
        }

    }
}
