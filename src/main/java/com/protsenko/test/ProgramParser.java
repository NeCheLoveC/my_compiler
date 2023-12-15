package com.protsenko.test;

import com.protsenko.test.entity.RawFunction;
import com.protsenko.test.entity.RawProgram;
import com.protsenko.test.entity.VariableDeclaration;
import com.protsenko.test.parser.ParserAbstract;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

public class ProgramParser extends ParserAbstract
{
    private String path = "test";
    private FileInputStream file;
    private String fileName = "index";
    //private Map<String, Function> functionMap = new HashMap<>();

    public ProgramParser(String path) throws IOException {
        super(new Coordinate());
        this.file = new FileInputStream(path);
        this.currentCoordinate = new Coordinate();
    }

    public RawProgram parse() throws IOException, CloneNotSupportedException {
        nextChar();
        Map<String, RawFunction> functionBuilders = getAllPreFunctions();
        return new RawProgram(functionBuilders);
    }

    private Map<String, RawFunction> getAllPreFunctions() throws IOException, CloneNotSupportedException {
        nextChar();
        skipSpaceChars();
        Map<String, RawFunction> functionBuilderMap = new HashMap<>();

        while(!isEndOfFile())
        {
            Coordinate coordinate = getCopyCoordinate();

            nextSequenceIsEqualToOrThrow("func", coordinate, "Ожидалось объявление функции - func...");
            oneOrMoreSpaceChar("Ожидалось объявление функции - func...", coordinate);
            coordinate = getCopyCoordinate();
            String returnedType = getNextOnlyLetterToken("Ожидался один из возможных возвращаемых типов", Optional.of(coordinate));
            oneOrMoreSpaceChar("Ожидался один из возможных возвращаемых типов", coordinate);
            Coordinate coordinateBeforeFuncName = getCopyCoordinate();
            Class typeForReturn = getReturnedTypeClassByString(returnedType, coordinate, "Ожидался один из возможных возвращаемых типов");
            String nameFunction = getNextIdenteficator("Ожидалось имя функции",null);
            skipSpaceChars();
            expectedCharIs('(', "Ожидалось '('\n" + currentCoordinate);
            skipSpaceChars();

            Map<String, Class> paramsOfFunc = new HashMap<>();
            if(currentCoordinate.getCurrentChar() == ')')
            {
                nextChar();
                skipSpaceChars();
                String blockCode = expectedBlockCode();
            }
            else if(isEndOfFile())
            {
                throw new RuntimeException("Непредвиденный конец файла.Ожидалось - (\n" + currentCoordinate);
            }
            else
            {
                //получить список параметров функции
                VariableDeclaration firstVar = expectedVariableDeclaration();
                skipSpaceChars();
                paramsOfFunc.put(firstVar.getName(),firstVar.getType());
                while(currentCoordinate.getCurrentChar() != ')' && !isEndOfFile())
                {
                    expectedCharIs(',', "Параметры функции должны быть разделены ,");
                    skipSpaceChars();
                    Coordinate coordinateBeforeVarDecl = getCopyCoordinate();
                    VariableDeclaration variable = expectedVariableDeclaration();
                    //Если хеш-мап уже содержит параметра с данным именем - выкинуть ошибку
                    if(paramsOfFunc.putIfAbsent(variable.getName(), variable.getType()) != null)
                    {
                        throw new RuntimeException("Повторяющееся имя параметра функции.\n" + coordinateBeforeVarDecl.getPositionY() + ":" + coordinateBeforeVarDecl.getPositionX());
                    }
                    skipSpaceChars();
                }
                expectedCharIs(')', "Ожидался символ )\n" + currentCoordinate);
            }
            skipSpaceChars();
            Coordinate coordinateWhenStartFuncBlock = getCopyCoordinate();
            String block = expectedBlockCode();
            RawFunction newFunc = new RawFunction(nameFunction, typeForReturn,paramsOfFunc,coordinateWhenStartFuncBlock, block);
            //Если имя функции уже зарезервировано - кинуть ошибку
            if(functionBuilderMap.putIfAbsent(newFunc.getName(), newFunc) != null)
            {
                throw new RuntimeException("Имя функции уже зарезервировано\n" + coordinateBeforeFuncName);
            }
        }
        skipSpaceChars();
        return functionBuilderMap;
    }


    @Deprecated
    private String getNextToken() throws IOException {
        StringBuilder result = new StringBuilder();
        if(!Character.isSpaceChar(this.currentCoordinate.getCurrentChar()) && this.currentCoordinate.getCurrentChar() != -1)
            return null;
        result.append(currentCoordinate.getCurrentChar());
        nextChar();
        while(!Character.isSpaceChar(this.currentCoordinate.getCurrentChar()) && this.currentCoordinate.getCurrentChar() != -1)
        {
            result.append(currentCoordinate.getCurrentChar());
        }
        return result.toString();
    }

    @Deprecated
    public String getNextToken2() throws IOException {
        if(currentCoordinate.getCurrentChar() == -1)
            return null;
        StringBuilder token = new StringBuilder();
        if(Character.isSpaceChar(currentCoordinate.getCurrentChar()))
        {
            throw new RemoteException("Текущий символ -" + currentCoordinate.getCurrentChar() + ".Ожидался первый символ нового токена");
            //return "";
        }
        token.append(currentCoordinate.getCurrentChar());
        nextChar();
        while(!Character.isSpaceChar(currentCoordinate.getCurrentChar()) && !isEndOperatorOrStartBlock())
        {
            if(isEndOfFile())
                return token.toString();
            else if(isStartStringLiteral())
            {
                getStringLiteral();
            }
            else
            {
                token.append(currentCoordinate.getCurrentChar());
                nextChar();
            }
        }

        if(isStartBlockCode())
        {
            StringBuilder code = new StringBuilder("{\n");
            skipSpaceChars();
            while(!isEndOfBlock() || !isEndOfFile())
            {
                if(isEndOfFile())
                    throw new RuntimeException("Ожидался символ закрытия блока кода - }\n" + currentCoordinate.getPositionX() + 2 + ":" + currentCoordinate.getPositionY());
                code.append(getNextToken());
                skipSpaceChars();
            }
            code.append("}");
        }
        return token.toString();
    }


    @Override
    protected int next() throws IOException
    {
        return file.read();
    }
}
