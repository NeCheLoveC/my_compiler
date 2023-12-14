package com.protsenko.test.parser;

import com.protsenko.compiler.Coordinate;
import com.protsenko.test.Validated;
import com.protsenko.test.entity.VariableDeclaration;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Optional;

public class DeclareVariableParse extends StringParser implements Validated
{
    public static final Character operator = '=';
    private VariableDeclaration variableDeclaration;
    public DeclareVariableParse(Coordinate currentCoordinate, String code, StringWithBlockAbstractParser parent) {
        super(currentCoordinate, code, parent);
    }

    @Override
    protected boolean haveBlockCode() {
        return false;
    }

    @Override
    public void parse() throws IOException, CloneNotSupportedException {
        Coordinate coordinate = getCopyCoordinate();
        String rawType = getNextOnlyLetterToken("Ожидалось объявление переменной", null);
        oneOrMoreSpaceChar("Ожидалось объявление переменной", coordinate);
        Class returnedType = getReturnedTypeClassByString(rawType, coordinate,"Ожидался тип переменной");
        String varName = getNextIdenteficator("Ожидалось объявление идентификатора\n", Optional.of(currentCoordinate));
        skipSpaceChars();
        String stm = "null";
        if (currentCoordinate.getCurrentChar() == operator) {
            //expectedCharIs(operator, "Ожидался символ присваивания - " + operator);
            nextChar();
            skipSpaceChars();
            stm = expectedStatment();
        }
        expectedCharIs(ParserAbstract.END_OPERATOR, "Ожидался символ - " + END_OPERATOR);
        this.variableDeclaration = new VariableDeclaration(varName, returnedType, stm);
        validated();
        parent.addVarsIntoScope(this.variableDeclaration);
    }

    @Override
    public void validated()
    {
        if(this.variableDeclaration.getValue() == "null")
            return;
    }
}
