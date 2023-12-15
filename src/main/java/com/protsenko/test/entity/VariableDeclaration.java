package com.protsenko.test.entity;

public class VariableDeclaration implements Operator
{
    private String name;
    private Class type;
    private String value;

    public VariableDeclaration(String name, Class type, String value) {
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
    public String convertToJavaCode()
    {
        StringBuilder result = new StringBuilder();
        if(this.type == Boolean.class)
        {
            result.append("Boolean ");
        }
        else if(this.type == Long.class)
        {
            result.append("Long ");
        }
        else if(this.type == String.class)
        {
            result.append("String ");
        }
        else if(this.type == Double.class)
        {
            result.append("Double ");
        }
        result.append(name);
        result.append("=");
        result.append(value);
        result.append(";\n");
        return result.toString();
    }
}