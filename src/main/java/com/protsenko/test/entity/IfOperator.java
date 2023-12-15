package com.protsenko.test.entity;

import java.util.List;

public class IfOperator implements Operator
{
    private String predicate;
    private List<Operator> operatorList;

    public IfOperator(String predicate, List<Operator> operatorList) {
        this.predicate = predicate;
        this.operatorList = operatorList;
    }

    @Override
    public String convertToJavaCode() {
        StringBuilder result = new StringBuilder("if" + predicate + "{\n");
        for(Operator operator : operatorList)
        {
            result.append(operator.convertToJavaCode());
        }
        result.append("}\n");
        return result.toString();
    }
}
