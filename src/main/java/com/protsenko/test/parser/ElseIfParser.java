package com.protsenko.test.parser;

import com.protsenko.test.Coordinate;
import com.protsenko.test.entity.Operator;
import com.protsenko.test.entity.ElseIfOperator;
import com.protsenko.test.entity.RawProgram;

import java.io.IOException;
import java.util.List;

public class ElseIfParser extends StringWithBlockAbstractParser
{
    private String predicate;
    public ElseIfParser(Coordinate currentCoordinate, String code, StringWithBlockAbstractParser parent, RawProgram rawProgram) {
        super(currentCoordinate, code, parent, null, rawProgram);
    }

    @Override
    protected boolean haveBlockCode() {
        return true;
    }

    @Override
    public Operator parse() throws IOException, CloneNotSupportedException
    {
        nextSequenceIsEqualToOrThrow("elif", getCopyCoordinate(), "Ожидался elif");
        skipSpaceChars();
        Coordinate startPredicate = getCopyCoordinate();
        String predicate = expectedPredicate();
        // TODO: 14.12.2023 Валидация предиката
        validateStatement(predicate, startPredicate);
        this.predicate = predicate;
        skipSpaceChars();
        List<Operator> operators = parseBlock();
        return new ElseIfOperator(predicate, operators);
    }
}
