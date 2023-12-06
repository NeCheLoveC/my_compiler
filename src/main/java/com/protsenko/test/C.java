package com.protsenko.test;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class C implements BeanNameAware
{
    @Override
    public void setBeanName(String name) {
        System.out.println("BEAN AWARE " + name);
    }


}
