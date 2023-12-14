package com.protsenko.test.parser;

import com.protsenko.compiler.Coordinate;
import com.protsenko.test.entity.RawFunction;
import com.protsenko.test.entity.RawProgram;
import com.protsenko.test.entity.RawStatement;

public class StatementValidatorParser extends StringParser
{
    private RawProgram program;
    private RawFunction functionBelongTo;

    public StatementValidatorParser(Coordinate currentCoordinate, String code, RawProgram program, RawFunction functionBelongTo) {
        super(currentCoordinate, code);
        this.program = program;
        this.functionBelongTo = functionBelongTo;
    }
}
