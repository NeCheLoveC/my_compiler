package com.protsenko.compiler.operators;

import com.protsenko.compiler.Coordinate;
import com.protsenko.compiler.VariableDeclaration;


import java.io.IOException;
import java.util.Optional;
import java.util.Stack;

public abstract class ParserAbstract
{
    protected Coordinate currentCoordinate;
    protected boolean isStringLiteral = false;

    public ParserAbstract(Coordinate currentCoordinate)
    {
        this.currentCoordinate = currentCoordinate;
    }

    protected String expectedBlockCode() throws IOException {
        StringBuilder block = new StringBuilder();
        expectedCharIs('{', "Ожидалось - {");
        Stack<Character> brackets = new Stack<>();
        brackets.add((char)this.currentCoordinate.getCurrentChar());
        //Пропускаем все
        int c = nextChar();

        while(!brackets.isEmpty())
        {
            if(isEndOfFile())
                throw new RuntimeException("Ожидался - {\n" + currentCoordinate.getPositionY() + ":" + currentCoordinate.getPositionX());
            if(!isStringLiteral && (c == '{' || c == '}'))
            {
                if(c == '{')
                {
                    brackets.add('{');
                }
                if(c == '}')
                {
                    brackets.pop();
                }
            }
            block.append(c);
            c = nextChar();
        }
        return block.toString();
    }

    protected VariableDeclaration expectedVariableDeclaration() throws CloneNotSupportedException, IOException {
        Coordinate coordinate;
        coordinate = getCopyCoordinate();
        String paramType2 = getNextOnlyLetterToken("Ожидалось объявление параметра в формате: ТИП ИДЕНТИФИКАТОР", Optional.of(coordinate));
        oneOrMoreSpaceChar("Ожидалось объявление параметра в формате: ТИП ИДЕНТИФИКАТОР", coordinate);
        String paramName2 = getNextIdenteficator("Ожидалось объявление идентификатора", null);
        //skipSpaceChars();
        return new VariableDeclaration(paramName2, getReturnedTypeClassByString(paramType2,coordinate, "Ожидался ТИП ИДЕНТИФИКАТОРА"));
    }

    protected void expectedCharIs(char ch, String message) throws IOException {
        if(currentCoordinate.getCurrentChar() != ch)
            throw new RuntimeException(this.currentCoordinate.getPositionX() + ":" + this.currentCoordinate.getPositionY() + "\n" + message);
        nextChar();
    }

    protected Class getReturnedTypeClassByString(String type, Coordinate coordinate, String message)
    {
        if(type.equals("double"))
        {
            return Double.class;
        }
        else if(type.equals("long"))
        {
            return Long.class;
        }
        else if(type.equals("string"))
        {
            return String.class;
        }
        else if(type.equals("boolean"))
        {
            return Boolean.class;
        }
        else
            throw new RuntimeException(coordinate.getPositionX() + ":" + coordinate.getPositionY() + "\n" + message);
    }

    protected String getNextOnlyLetterToken(String message, Optional<Coordinate> coordinate) throws IOException {
        if(coordinate == null || coordinate.isEmpty())
            coordinate = Optional.of(currentCoordinate);
        if(
                !Character.isLetter(currentCoordinate.getCurrentChar())
            //currentCoordinate.getCurrentChar() == '"' || currentCoordinate.getCurrentChar() == '(' || currentCoordinate.getCurrentChar() == ')'
        )
            throw new RuntimeException(coordinate.get().getPositionX() + ":" + coordinate.get().getPositionY() + "\n" + message);
        StringBuilder result = new StringBuilder();
        result.append((char)this.currentCoordinate.getCurrentChar());
        int nextCh = nextChar();
        while(Character.isLetter(nextCh))
        {
            result.append((char) nextCh);
            nextCh = nextChar();
        }
        return result.toString();
    }

    protected String getNextIdenteficator(String message, Optional<Coordinate> coordinate) throws IOException {
        if(coordinate == null || coordinate.isEmpty())
            coordinate = Optional.of(currentCoordinate);
        if(Character.isLetter(currentCoordinate.getCurrentChar())
            //currentCoordinate.getCurrentChar() == '"' || currentCoordinate.getCurrentChar() == '(' || currentCoordinate.getCurrentChar() == ')'
        )
            throw new RuntimeException(coordinate.get().getPositionX() + ":" + coordinate.get().getPositionY() + "\n" + message);
        StringBuilder result = new StringBuilder();
        result.append((char)this.currentCoordinate.getCurrentChar());

        int nextCh = nextChar();
        while(Character.isLetter(nextCh) || Character.isDigit(nextCh))
        {
            result.append((char) nextCh);
            nextCh = nextChar();
        }
        return result.toString();
    }

    protected void oneOrMoreSpaceChar(String messageError, Coordinate coordinate) throws IOException {
        if(!Character.isSpaceChar(this.currentCoordinate.getCurrentChar()) && this.currentCoordinate.getCurrentChar() == -1)
            throw new RuntimeException(coordinate.getPositionX() + ":" + coordinate.getPositionY() + "\n" + messageError);
        while(Character.isSpaceChar(nextChar())){}
    }

    protected boolean curCharIs(char ch)
    {
        return ch == this.currentCoordinate.getCurrentChar();
    }

    protected void nextSequenceIsEqualToOrThrow(String pattern, Coordinate coordinate, String message) throws IOException {
        if(pattern == null)
            throw new NullPointerException("nextTokenIsEqualTo был передан null параметр");
        if(pattern.charAt(0) != this.currentCoordinate.getCurrentChar())
            throw new RuntimeException(coordinate.getPositionX() + ":" + coordinate.getPositionY() + "\n" + message);
        for (int i = 1; i < pattern.length(); i++)
        {
            if(pattern.charAt(i) != nextChar())
                throw new RuntimeException(coordinate.getPositionX() + ":" + coordinate.getPositionY() + "\n" + message);
        }
        nextChar();
    }

    protected Coordinate getCopyCoordinate() throws CloneNotSupportedException {
        return (Coordinate) this.currentCoordinate.clone();
    }

    protected void skipSpaceChars() throws IOException {
        //nextChar();
        while(Character.isSpaceChar(currentCoordinate.getCurrentChar()))
            nextChar();
    }

    protected boolean isEndOfBlock()
    {
        return currentCoordinate.getCurrentChar() == '}';
    }

    protected boolean isStartBlockCode()
    {
        return this.currentCoordinate.getCurrentChar() == '{';
    }

    protected boolean isStartStringLiteral()
    {
        return currentCoordinate.getCurrentChar() == '"';
    }

    protected boolean isEndOperatorOrStartBlock(int charCode)
    {
        return charCode == '{' || charCode == ';';
    }

    protected boolean isEndOfFile()
    {
        return currentCoordinate.getCurrentChar() == -1;
    }

    protected String getStringLiteral() throws IOException {
        StringBuilder stringLiteral = new StringBuilder("\"");
        if(currentCoordinate.getCurrentChar() != '"')
            throw new RuntimeException("Ожидался знак - \"");
        while(nextChar() != '"')
        {
            if(isEndOfFile())
                throw new RuntimeException("Ожидалось окончание строки '\"'");
            stringLiteral.append(currentCoordinate.getCurrentChar());
        }
        nextChar();
        stringLiteral.append('"');
        return stringLiteral.toString();
    }

    protected final int nextChar() throws IOException
    {
        int c = next();
        currentCoordinate.setCurrentChar(c);
        if(c == '\n')
        {
            currentCoordinate.addPositionY();
        }
        else
        {
            currentCoordinate.addPositionX();
        }
        if(c == '"')
        {
            this.isStringLiteral = !isStringLiteral;
        }
        /*
        if(!isEndOfFile())
        {
            if(c == '\n')
            {
                currentCoordinate.addPositionY();
            }
            else
            {
                currentCoordinate.addPositionX();
            }
            if(c == '"')
            {
                this.isStringLiteral = !isStringLiteral;
            }
        }
         */
        return c;
    }

    protected abstract int next() throws IOException;
}
