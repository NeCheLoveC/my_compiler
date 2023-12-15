package com.protsenko.test.parser;

import com.protsenko.test.Coordinate;
import com.protsenko.test.entity.Operator;
import com.protsenko.test.entity.RawProgram;

import java.io.IOException;
import java.util.Stack;

public abstract class StringParser extends ParserAbstract
{
    protected String code;
    protected StringWithBlockAbstractParser parent;
    protected RawProgram rawProgram;

    public StringParser(Coordinate currentCoordinate, String code, RawProgram rawProgram) {
        super(currentCoordinate);
        this.rawProgram = rawProgram;
        this.code = code;
    }

    public StringParser(Coordinate currentCoordinate, String code, StringWithBlockAbstractParser parent,RawProgram rawProgram) {
        this(currentCoordinate, code, rawProgram);
        this.parent = parent;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    protected String expectedPredicate() throws IOException {
        expectedCharIs('(',"Ошибка\n" + currentCoordinate);
        Stack<Character> brack = new Stack<>();
        brack.add('(');
        StringBuilder predicate = new StringBuilder();
        predicate.append('(');
        while(!brack.isEmpty())
        {
            if(isEndOfFile() || !isEndOperatorOrStartBlock() || !isEndOfBlock())
            {
                throw new RuntimeException("Не ожидался символ - )\n" + currentCoordinate);
            }
            if(currentCoordinate.getCurrentChar() == '(')
                brack.add('(');
            else if(currentCoordinate.getCurrentChar() == ')')
            {
                brack.pop();
            }
            predicate.append(currentCoordinate.getCurrentChar());
            nextChar();
        }
        if(currentCoordinate.getCurrentChar() != ')')
            throw new RuntimeException("Ошибка в предикате (Нарушение парности скобок)\n" + currentCoordinate);
        return predicate.toString().trim();
    }


    public boolean validateStatement(String statement, Coordinate coordinateStart)
    {

    }

    public boolean validateStatement(String statement,Coordinate coordinateStart, Class expectedReturn)
    {
    }

    public String nextPartOfStatement(Coordinate coordinate)
    {

    }

    protected abstract boolean haveBlockCode();

    protected String getNextOperator() throws IOException {
        if(isEndOfFile())
            throw new RuntimeException("Ожидалось начало оператора");
        StringBuilder result = new StringBuilder();
        while(!isEndOfFile() && !isEndOperatorOrStartBlock() && !isEndOfBlock())
        {
            result.append((char)currentCoordinate.getCurrentChar());
            nextChar();
        }
        if(isEndOperator())
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
            throw new RuntimeException("Ожидалось - ;\n" + currentCoordinate);
        return result.toString();
    }

    protected StringParser convertToStringParser(String token, StringWithBlockAbstractParser parent, Coordinate startToken)
    {
        StringParser result;
        if(token.startsWith("while"))
            result = new WhileParser(startToken,token,parent);
        else if(token.startsWith("if"))
            result =  new IfParser(startToken,token,parent);
        else if(token.matches("^(\\w[\\w\\d]*\\s*" + DeclareVariableParse.operator + "\\s*)"))
        {

            //присвоение к уже существ. переменной
        }
        else if(token.matches("^(_?\\w[\\w\\d]*\\()"))
        {
            //Вызов функции без присвоения
        }
        else if(token.matches("^(boolean|bool|string|str|long|double)"))
        {
            //Объявление переменной
            result = new DeclareVariableParse(startToken,token,parent);
        }
        else if(token.startsWith("if"))
        {
            result = new IfParser(startToken,token,parent);
        }
        else if(token.startsWith("elif"))
        {
            result = new ElseIfParser(startToken,token,parent);
        }
        else if(token.startsWith("else"))
        {
            result = new ElseParser(startToken,token,parent);
        }
        else
        {
            throw new RuntimeException("Нераспознан токен\n" + startToken);
        }
        result.nextChar();
        return result;
    }

    public abstract Operator parse() throws IOException, CloneNotSupportedException;

}
