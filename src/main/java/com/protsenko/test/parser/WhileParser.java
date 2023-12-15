package com.protsenko.test.parser;

import com.protsenko.test.Coordinate;
import com.protsenko.test.entity.Operator;

import java.io.IOException;
import java.util.List;

public class WhileParser extends StringWithBlockAbstractParser
{
    public static final String PATTERN = "cycle";
    private String predicate;

    public WhileParser(Coordinate currentCoordinate, String code, StringWithBlockAbstractParser parent) {
        super(currentCoordinate, code, parent,null);
        this.scopeVars = scopeVars;
    }

    @Override
    public Operator parse() throws IOException, CloneNotSupportedException
    {
        nextSequenceIsEqualToOrThrow(PATTERN, getCopyCoordinate(), "Ожидался оператор " + PATTERN);
        skipSpaceChars();
        String predicate = expectedPredicate();
        this.predicate = predicate;
        skipSpaceChars();
        List<Operator> operatorList = parseBlock();
        return new WhileUnit()
    }

    @Override
    protected boolean haveBlockCode() {
        return true;
    }


}
