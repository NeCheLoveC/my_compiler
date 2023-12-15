package com.protsenko.test.entity;

import com.protsenko.test.parser.WhileParser;

import java.util.List;

public class WhileOperator implements Operator
{
    private String predicate;
    private List<Operator> operatorList;

    public WhileOperator(String predicate, List<Operator> operatorList) {
        this.predicate = predicate;
        this.operatorList = operatorList;
    }

    @Override
    public String convertToJavaCode() {
        StringBuilder result = new StringBuilder(WhileParser.PATTERN + predicate + "{\n");
        for(Operator operator : operatorList)
        {
            result.append(operator.convertToJavaCode());
        }
        result.append("}\n");
        return result.toString();
    }
}
