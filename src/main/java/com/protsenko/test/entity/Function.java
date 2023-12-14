package com.protsenko.test.entity;

import com.protsenko.compiler.operators.Operator;

import java.util.List;
import java.util.Map;

public class Function
{
    private String name;
    private Class type;
    private Map<String, Class> params;
    List<Operator> operatorList;

    public Function(String name, Class type, Map<String, Class> params, List<Operator> operatorList) {
        this.name = name;
        this.type = type;
        this.params = params;
        this.operatorList = operatorList;
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

    public Map<String, Class> getParams() {
        return params;
    }

    public void setParams(Map<String, Class> params) {
        this.params = params;
    }

    public List<Operator> getOperatorList() {
        return operatorList;
    }

    public void setOperatorList(List<Operator> operatorList) {
        this.operatorList = operatorList;
    }
}
