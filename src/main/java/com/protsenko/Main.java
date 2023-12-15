package com.protsenko;

import com.protsenko.test.ProgramParser;
import com.protsenko.test.entity.Program;


import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Main
{
    private static String PATH = "";
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        //ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        File file = new File(PATH);
        ProgramParser programParser = new ProgramParser(PATH);
        Program program = programParser.parse().compile();

    }

}
