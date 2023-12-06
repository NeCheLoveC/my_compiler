package com.protsenko.compiler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Program
{
    private Set<Function> functionSet = new HashSet<>();
    private Function mainFunction;

    public String convertToJavaCode() throws RuntimeException
    {
        if(mainFunction == null)
            throw new RuntimeException("Не обнаружена функция с сигнатурой:\nfunc void main(){\n\t...\n}");

        return functionSet.stream().map(func -> func.convertToJavaCode()).collect(Collectors.joining());
    }

    private Program(InputStream file)
    {
        this.file = file;
    }

    public static ProgramBuilder getBuilder()
    {
        return new ProgramBuilder();
    }

    public void init()
    {

    }

    public static class ProgramBuilder
    {
        private String pathToFile = "";
        //private FileInputStream file;

        private ProgramBuilder()  {
            //this.file = new FileInputStream(pathToFile);
        }

        public ProgramBuilder setPathToFile(String pathToFile) {
            this.pathToFile = pathToFile;
            return this;
        }

        public Program build() throws FileNotFoundException {
            if (pathToFile == null || pathToFile.isBlank())
                throw new RuntimeException("Не задан путь к файлу.");
            return new Program(new FileInputStream(pathToFile));
        }
    }
}
