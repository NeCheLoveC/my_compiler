package com.protsenko.compiler.operators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperatorHandler
{
    List<Operator> operatorList;

    public List<Operator> getOperatorList() {
        return operatorList;
    }

    @Autowired
    public void setOperatorList(List<Operator> operatorList) {
        this.operatorList = operatorList;
    }

    public Coordinate handle(String token)
    {
        boolean waitHandler = true;
        for(Operator operator : operatorList)
        {
            if(operator.apply(token))
            {

                break;
            }
        }
    }

}
