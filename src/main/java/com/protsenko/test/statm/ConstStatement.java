package com.protsenko.test.statm;

import com.protsenko.test.parser.FunctionParser;
import com.protsenko.test.statm.UnitStatment;

public class ConstStatement implements UnitStatment
{
    private String value;
    private Class type;

    public ConstStatement(String value, Class type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    @Override
    public boolean validate(FunctionParser functionParser)
    {
        return true;
    }
}
