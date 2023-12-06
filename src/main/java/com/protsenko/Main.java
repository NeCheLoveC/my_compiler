package com.protsenko;

import com.protsenko.compiler.Config;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;

public class Main
{
    private static String PATH = "";
    public static void main(String[] args)
    {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        File file = new File(PATH);
    }

}
