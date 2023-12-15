package com.protsenko.test.parser;

import com.protsenko.test.Coordinate;

import java.io.IOException;

public class ElseParser extends StringWithBlockAbstractParser
{
    private String predicate;

    public ElseParser(Coordinate currentCoordinate, String code, StringWithBlockAbstractParser parent) {
        super(currentCoordinate, code, parent, null);
    }

    @Override
    protected boolean haveBlockCode() {
        return true;
    }

    @Override
    public void parse() throws IOException, CloneNotSupportedException
    {
        nextSequenceIsEqualToOrThrow("else", getCopyCoordinate(), "Ожидался elif");
        skipSpaceChars();
        String predicate = expectedPredicate();
        this.predicate = predicate;
        skipSpaceChars();
        parseBlock();
    }
}
