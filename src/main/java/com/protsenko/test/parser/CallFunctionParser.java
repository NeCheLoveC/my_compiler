package com.protsenko.test.parser;

import com.protsenko.test.Coordinate;
import com.protsenko.test.entity.Operator;
import com.protsenko.test.entity.RawProgram;
import com.protsenko.test.statm.FunctionCall;

import java.io.IOException;
import java.util.Optional;

public class CallFunctionParser extends StringParser
{
    public CallFunctionParser(Coordinate currentCoordinate, String code, StringWithBlockAbstractParser parent, RawProgram rawProgram)
    {
        super(currentCoordinate, code, parent, rawProgram);
    }

    @Override
    protected boolean haveBlockCode() {
        return false;
    }

    @Override
    public Operator parse() throws IOException, CloneNotSupportedException
    {
        StringBuilder funcName = new StringBuilder();
        Coordinate startCoordinate = getCopyCoordinate();
        if(curCharIs('_'))
            funcName.append((char)currentCoordinate.getCurrentChar());
        funcName.append(getNextIdenteficator("Неверный идентификатор для функции", Optional.of(startCoordinate)));
        expectedCharIs('(', "Ожидался символ - (");
        skipSpaceChars();
        String statement = "";
        if(curCharIs(')')) { }
        else
        {
            statement = expectedStatment();
            skipSpaceChars();
            expectedCharIs(')', "Ожидался - )");
        }
        skipSpaceChars();
        expectedCharIs(ParserAbstract.END_OPERATOR, "Ожидался - " + ParserAbstract.END_OPERATOR);
        return new FunctionCall(funcName.toString(), statement, startCoordinate, rawProgram);
    }
}
