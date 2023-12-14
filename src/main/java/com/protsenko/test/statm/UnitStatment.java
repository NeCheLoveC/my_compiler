package com.protsenko.test.statm;

import com.protsenko.test.entity.RawProgram;
import com.protsenko.test.parser.FunctionParser;

public interface UnitStatment
{
    Class getType();
    boolean validate(FunctionParser functionParser);
}
