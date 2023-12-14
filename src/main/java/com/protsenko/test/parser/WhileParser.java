package com.protsenko.test.parser;

import com.protsenko.compiler.Coordinate;
import java.io.IOException;

public class WhileParser extends StringWithBlockAbstractParser
{
    private static final String PATTERN = "while";
    private String predicate;

    public WhileParser(Coordinate currentCoordinate, String code, StringWithBlockAbstractParser parent) {
        super(currentCoordinate, code, parent,null);
        this.scopeVars = scopeVars;
    }

    @Override
    public void parse() throws IOException, CloneNotSupportedException
    {
        nextSequenceIsEqualToOrThrow("while", getCopyCoordinate(), "Ожидался оператор while");
        skipSpaceChars();
        String predicate = expectedPredicate();
        this.predicate = predicate;
        skipSpaceChars();
        parseBlock();
    }

    @Override
    protected boolean haveBlockCode() {
        return true;
    }


}
