package com.protsenko.compiler;

import com.protsenko.compiler.operators.ParserAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.*;

public class ProgramParser extends ParserAbstract
{
    private String path;
    private FileInputStream file;
    private String fileName = "index";
    private Map<String,Function> functionSet = new HashMap<>();

    public ProgramParser(String path) throws IOException {
        super(new Coordinate());
        this.file = new FileInputStream(path);
        this.currentCoordinate = new Coordinate();
    }

    public Map<String, Function.FunctionBuilder> parse() throws IOException, CloneNotSupportedException {
        nextChar();
        Map<String, Function.FunctionBuilder> functionBuilders = getAllPreFunctions();
    }

    private Map<String, Function.FunctionBuilder> getAllPreFunctions() throws IOException, CloneNotSupportedException {
        nextChar();
        skipSpaceChars();
        Map<String, Function.FunctionBuilder> functionBuilderMap = new HashMap<>();

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
            if(functionSet.containsKey(nameFunction))
            {
                throw new RuntimeException("Функция с данным именем была объявлена (" + nameFunction + ")" + "\n" + coordinateBeforeFuncName.getPositionY() + ":" + coordinateBeforeFuncName.getPositionX());
            }
            skipSpaceChars();
            expectedCharIs('(', "Ожидалось '('\n" + currentCoordinate);
            skipSpaceChars();

            Map<String, Class> variablesOfFunc = new HashMap<>();
            if(currentCoordinate.getCurrentChar() == ')')
            {
                nextChar();
                continue;
            }
            else if(currentCoordinate.getCurrentChar() == -1)
            {
                throw new RuntimeException("Ожидалось (\n" + currentCoordinate);
            }
            else
            {
                //получить список параметров функции
                expectedVariableDeclaration();
                while(currentCoordinate.getCurrentChar() != ')' && currentCoordinate.getCurrentChar() != -1)
                {
                    skipSpaceChars();
                    expectedCharIs(',', "Параметры функции должны быть разделены ,");
                    skipSpaceChars();
                    Coordinate coordinateBeforeVarDecl = getCopyCoordinate();
                    VariableDeclaration variable = expectedVariableDeclaration();
                    //Если хеш-мап уже содержит параметра с данным именем - выкинуть ошибку
                    if(variablesOfFunc.putIfAbsent(variable.getName(), variable.getType()) != null)
                    {
                        throw new RuntimeException("Повторяющееся имя.\n" + coordinateBeforeVarDecl.getPositionY() + ":" + coordinateBeforeVarDecl.getPositionX());
                    }
                }
                expectedCharIs(')', "Ожидался символ )\n" + currentCoordinate);
            }
            skipSpaceChars();
            Coordinate coordinateWhenStartFuncBlock = getCopyCoordinate();
            String block = expectedBlockCode();
            Function.FunctionBuilder functionBuilder = Function.getBuilder();
            functionBuilder
                    .setName(nameFunction)
                    .setParams(variablesOfFunc)
                    .setReturnedType(typeForReturn)
                    .setCode(block)
                    .setStartBlock(coordinateWhenStartFuncBlock);
            //Если имя функции уже зарезервировано - кинуть ошибку
            if(functionBuilderMap.putIfAbsent(functionBuilder.getName(), functionBuilder) != null)
            {
                throw new RuntimeException("Имя функции уже зарезервировано\n" + coordinateBeforeFuncName);
            }
            skipSpaceChars();
        }
        return functionBuilderMap;
        //\w[\w\d]*\s*
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
        while(!Character.isSpaceChar(currentCoordinate.getCurrentChar()) && !isEndOperatorOrStartBlock(currentCoordinate.getCurrentChar()))
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
