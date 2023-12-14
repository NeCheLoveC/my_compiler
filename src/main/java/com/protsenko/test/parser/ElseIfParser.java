package com.protsenko.test.parser;

import com.protsenko.compiler.Coordinate;
import com.protsenko.test.OperatorsManager;

import java.io.IOException;

public class ElseIfParser extends StringWithBlockAbstractParser
{
    private String predicate;
    public ElseIfParser(Coordinate currentCoordinate, String code, StringWithBlockAbstractParser parent) {
        super(currentCoordinate, code, parent, null);
    }

    @Override
    protected boolean haveBlockCode() {
        return true;
    }

    @Override
    public void parse() throws IOException, CloneNotSupportedException
    {
        nextSequenceIsEqualToOrThrow("elif", getCopyCoordinate(), "Ожидался elif");
        skipSpaceChars();
        String predicate = expectedPredicate();
        this.predicate = predicate;
        skipSpaceChars();
        parseBlock();
    }
}
