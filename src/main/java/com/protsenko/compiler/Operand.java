package com.protsenko.compiler;

public class Operand
{
    private Class type;
    private Coordinate coordinate;

    private Operand(Class type, Coordinate coordinate) {
        this.type = type;
        this.coordinate = coordinate;
    }

    public static Operand operand(String operand, Coordinate coordinate)
    {
        if(operand.matches("[1-9]\\d*"))
        {
            return new Operand(Long.class,coordinate);
        }
        else if(operand.matches("false|true"))
        {
            //false, true
            return new Operand(Boolean.class,coordinate);
        }
        else if(operand.matches("(([1-9]\\d*)|(\\d))\\.\\d+"))
        {
            return new Operand(Double.class,coordinate);
        }
        else if(operand.matches("\"\\w*\""))
        {
            return new Operand(String.class,coordinate);
        }
        else
            throw new RuntimeException("Не распознан литерал\n" + coordinate);
    }
}
