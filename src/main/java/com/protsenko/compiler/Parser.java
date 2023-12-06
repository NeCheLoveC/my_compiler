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

@Component
@Scope("prototype")
@PropertySource("classpath:application-properties")
@Validated
public class Parser
{
    @Value("${path}")
    private String path;
    private FileInputStream file;
    private String fileName = "index";
    private Coordinate currentCoordinate;
    private Map<String,Function> functionSet = new HashMap<>();
    private boolean isStringLiteral = false;
    //private Validator validator;
    @Autowired
    public Parser() throws IOException {
        //this.validator = validator;
        this.file = new FileInputStream(path.concat(fileName));
        this.currentCoordinate = new Coordinate();
    }


    public Program parse() throws IOException, CloneNotSupportedException {
        nextChar();
        Map<String, Function.FunctionBuilder> functionBuilders = getAllPreFunctions();
        //Валидируем наши функции

        //Создаем функциии



        while(!getNextToken().isEmpty())
        {

        }
    }

    private void validate(@Validated Map<String, Function.FunctionBuilder> functionBuilders)
    {

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

    private String expectedBlockCode() throws IOException
    {
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

    private VariableDeclaration expectedVariableDeclaration() throws CloneNotSupportedException, IOException {
        Coordinate coordinate;
        coordinate = getCopyCoordinate();
        String paramType2 = getNextOnlyLetterToken("Ожидалось объявление параметра в формате: ТИП ИДЕНТИФИКАТОР", Optional.of(coordinate));
        oneOrMoreSpaceChar("Ожидалось объявление параметра в формате: ТИП ИДЕНТИФИКАТОР", coordinate);
        String paramName2 = getNextIdenteficator("Ожидалось объявление идентификатора", null);
        //skipSpaceChars();
        return new VariableDeclaration(paramName2, getReturnedTypeClassByString(paramType2,coordinate, "Ожидался ТИП ИДЕНТИФИКАТОРА"));
    }

    private void expectedCharIs(char ch, String message) throws IOException {
        if(currentCoordinate.getCurrentChar() != ch)
            throw new RuntimeException(this.currentCoordinate.getPositionX() + ":" + this.currentCoordinate.getPositionY() + "\n" + message);
        nextChar();
    }


    private Class getReturnedTypeClassByString(String type, Coordinate coordinate, String message)
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

    private String getNextOnlyLetterToken(String message, Optional<Coordinate> coordinate) throws IOException {
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

    private String getNextIdenteficator(String message, Optional<Coordinate> coordinate) throws IOException {
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

    private void oneOrMoreSpaceChar(String messageError, Coordinate coordinate) throws IOException {
        if(!Character.isSpaceChar(this.currentCoordinate.getCurrentChar()) && this.currentCoordinate.getCurrentChar() == -1)
            throw new RuntimeException(coordinate.getPositionX() + ":" + coordinate.getPositionY() + "\n" + messageError);
        while(Character.isSpaceChar(nextChar())){}
    }

    private boolean curCharIs(char ch)
    {
        return ch == this.currentCoordinate.getCurrentChar();
    }

    private void nextSequenceIsEqualToOrThrow(String pattern, Coordinate coordinate, String message) throws IOException {
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

    private Coordinate getCopyCoordinate() throws CloneNotSupportedException {
        return (Coordinate) this.currentCoordinate.clone();
    }

    public void skipSpaceChars() throws IOException {
        //nextChar();
        while(Character.isSpaceChar(currentCoordinate.getCurrentChar()))
            nextChar();
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

    private boolean isEndOfBlock()
    {
        return currentCoordinate.getCurrentChar() == '}';
    }

    private boolean isStartBlockCode()
    {
        return this.currentCoordinate.getCurrentChar() == '{';
    }

    private boolean isStartStringLiteral()
    {
        return currentCoordinate.getCurrentChar() == '"';
    }

    private boolean isEndOperatorOrStartBlock(int charCode)
    {
        return charCode == '{' || charCode == ';';
    }

    private boolean isEndOfFile()
    {
        return currentCoordinate.getCurrentChar() == -1;
    }

    private String getStringLiteral() throws IOException {
        StringBuilder stringLiteral = new StringBuilder("\"");
        if(currentCoordinate.getCurrentChar() != '"')
            throw new RuntimeException("Ожидался знак - \"");
        while(nextChar() != '"')
        {
            if(isEndOfFile())
                throw new RuntimeException("Ожидалось окончание строки");
            stringLiteral.append(currentCoordinate.getCurrentChar());
        }
        nextChar();
        stringLiteral.append('"');
        return stringLiteral.toString();
    }

    private int nextChar() throws IOException
    {
        int c = file.read();
        currentCoordinate.setCurrentChar((char)c);
        if(c != -1)
        {
            currentCoordinate.addPositionX();
            char ch = (char) c;
            if(ch == '\n')
            {
                currentCoordinate.addPositionY();
            }
            if(ch == '"')
            {
                this.isStringLiteral = !isStringLiteral;
            }
        }
        return c;
    }
}
