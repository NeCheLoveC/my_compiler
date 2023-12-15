package com.protsenko.test.parser;

import com.protsenko.test.Coordinate;
import com.protsenko.test.entity.Operator;
import com.protsenko.test.OperatorsManager;
import com.protsenko.test.entity.RawProgram;
import com.protsenko.test.entity.VariableDeclaration;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class StringWithBlockAbstractParser extends StringParser implements VarScopes
{

    protected List<StringParser> childrens = new LinkedList<>();
    protected Map<String, VariableDeclaration> scopeVars = new HashMap<>();
    protected OperatorsManager operatorsManager;

    public StringWithBlockAbstractParser(Coordinate currentCoordinate, String code, StringWithBlockAbstractParser parent, OperatorsManager operatorManager, RawProgram rawProgram) {
        super(currentCoordinate, code, parent, rawProgram);
        this.operatorsManager = operatorManager;
    }

    @Override
    public Map<String, VariableDeclaration> getDeclarationVars() {
        return scopeVars;
    }

    @Override
    public void addVarsIntoScope(VariableDeclaration newVar)
    {
        scopeVars.put(newVar.getName(), newVar);
    }

    protected void addStringParserChild(StringParser stringParser)
    {
        this.childrens.add(stringParser);
    }

    protected List<Operator> parseBlock() throws IOException, CloneNotSupportedException {
        expectedCharIs('{', "Ожидался символ - {");
        skipSpaceChars();
        List<Operator> operators = new LinkedList<>();
        while(!isEndOfBlock())
        {
            Coordinate startOperator = getCopyCoordinate();
            String rawOperator = getNextOperator();
            StringParser operator = convertToStringParser(rawOperator,this,startOperator);
            operators.add(operator.parse());
            addStringParserChild(operator);
            skipSpaceChars();
        }
        return operators;
    }
}
