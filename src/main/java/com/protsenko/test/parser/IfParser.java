package com.protsenko.test.parser;

import com.protsenko.test.Coordinate;
import com.protsenko.test.entity.Operator;
import com.protsenko.test.entity.IfOperator;
import com.protsenko.test.entity.RawProgram;

import java.io.IOException;
import java.util.List;

public class IfParser extends StringWithBlockAbstractParser
{
    private String predicate;
    public IfParser(Coordinate currentCoordinate, String code, StringWithBlockAbstractParser parent, RawProgram rawProgram) {
        super(currentCoordinate, code, parent,null,rawProgram);
    }

    @Override
    protected boolean haveBlockCode() {
        return true;
    }

    @Override
    public Operator parse() throws IOException, CloneNotSupportedException {
        nextSequenceIsEqualToOrThrow("if", getCopyCoordinate(),"Ожидался оператор 'if'");
        skipSpaceChars();
        Coordinate startPredicate = getCopyCoordinate();
        String predicate = expectedPredicate();
        // TODO: 14.12.2023 Валидация предиката
        validateStatement(predicate, startPredicate);
        this.predicate = predicate;
        skipSpaceChars();
        List<Operator> operators = parseBlock();
        return new IfOperator(predicate, operators);
    }
}
