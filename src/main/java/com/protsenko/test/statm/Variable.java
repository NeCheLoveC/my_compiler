package com.protsenko.test.statm;

import com.protsenko.test.entity.RawFunction;
import com.protsenko.test.parser.FunctionParser;
import com.protsenko.test.statm.UnitStatment;

public class Variable implements UnitStatment
{
    String name;
    Class type;
    String value;

    public Variable(String name, Class type, String value)
    {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean validate(FunctionParser functionParser)
    {
        return false;
    }
}
