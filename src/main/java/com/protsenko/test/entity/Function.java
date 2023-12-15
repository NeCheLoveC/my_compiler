package com.protsenko.test.entity;

import java.util.List;
import java.util.Map;

public class Function implements Operator
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

    public String convertToJavaCode()
    {
        StringBuilder result = new StringBuilder();
        result.append("private static ");
        result.append(getStringType(type));
        result.append(" " + name);
        result.append("(");
        if(!params.isEmpty())
        {
            List<Map.Entry<String, Class>> param = params.entrySet().stream().toList();
            result.append(getStringType(param.get(0).getValue()) + " ");
            result.append(param.get(0).getKey());
            for(int i = 1;i < param.size();i++)
            {
                result.append(",");
                result.append(getStringType(param.get(0).getValue()) + " ");
                result.append(param.get(0).getKey());
            }

        }
        result.append(")\n{");
        for(Operator o : operatorList)
        {
            result.append(o.convertToJavaCode());
        }
        result.append("}\n");
        return result.toString();
    }

    private String getStringType(Class type)
    {
        String result = "void";
        if(type == Boolean.class)
        {
            result =  "Boolean";
        }
        else if(type == Long.class)
        {
            result = "Long";
        }
        else if(type == Double.class)
        {
            result = "Double";
        }
        else if(type == String.class)
        {
            result = "String";
        }
        return result;
    }
}
