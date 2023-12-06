package com.protsenko.test;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

@Component
public class B implements BeanNameAware
{
    @Override
    public void setBeanName(String name) {
        System.out.println("BEAN AWARE " + name);
    }

    @PostConstruct
    public void init()
    {
        System.out.println("Пост констракт класса B");
    }
}
