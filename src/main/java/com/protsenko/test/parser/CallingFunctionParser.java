package com.protsenko.test.parser;

import com.protsenko.test.Coordinate;

import java.io.IOException;
import java.util.Optional;
import java.util.Stack;

public class CallingFunctionParser extends StringParser
{
    public CallingFunctionParser(Coordinate currentCoordinate, String code, StringWithBlockAbstractParser parent) {
        super(currentCoordinate, code, parent);
    }

    @Override
    protected boolean haveBlockCode() {
        return false;
    }

    @Override
    public void parse() throws IOException, CloneNotSupportedException
    {
        StringBuilder funcName = new StringBuilder();
        Coordinate start = getCopyCoordinate();
        if(currentCoordinate.getCurrentChar() == '_')
        {
            funcName.append("_");
            nextChar();
        }

        funcName.append(getNextIdenteficator("Нераспознан токен", Optional.of(start)));
        expectedCharIs('(', "Ожидаемый символ - (");
        skipSpaceChars();
        expectedStatment();



    }

    private String[] getFunctionParams() throws IOException {
        expectedCharIs('(', "Ожидался символ - (");
        Stack<Character> brackets = new Stack<>();
        brackets.add('(');
        skipSpaceChars();
        StringBuilder param = new StringBuilder();
        if(!brackets.isEmpty())
        {
            if(isEndOfFile() || isEndOfBlock() || isEndOperatorOrStartBlock())
                throw new RuntimeException("Ожидался символ - )" +
                        "" + currentCoordinate);
            if(currentCoordinate.getCurrentChar() == '(')
                brackets.add('(');
            else if(currentCoordinate.getCurrentChar() == ')' && !isStringLiteral)
                brackets.pop();
            else if(isComma() && !isStringLiteral && brackets.size() == 1)
            {
                param,
            }


                param.append(currentCoordinate.getCurrentChar());
        }
    }
}
