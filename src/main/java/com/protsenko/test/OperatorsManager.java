package com.protsenko.test;

import com.protsenko.test.entity.Operator;
import com.protsenko.test.handlers.Handler;
import com.protsenko.test.parser.FunctionParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperatorsManager
{

    private List<Handler> handlers;

    @Autowired
    public void setHandlers(List<Handler> handlers) {
        this.handlers = handlers;
    }


    //Возвращает null - если оператор не распознан
    public Operator toOperator(String rawStringOperator, FunctionParser functionParser)
    {
        Operator operator  = null;
        for(Handler handler : handlers)
        {
            operator = handler.tryConvertToJavaOperator(rawStringOperator, functionParser);
            if(operator != null)
                break;
        }
        return operator;
    }

}
