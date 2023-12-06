package com.protsenko.compiler;

public class Coordinate implements Cloneable
{
    int positionX = -1;
    private int positionY = 0;
    int count = 0;
    //-1 - конец файла
    //-2 - итерация по файлу еще не началась
    int currentChar = -2;

    public int getPositionX() {
        return positionX;
    }

    public void addPositionX() {
        this.positionX++;
    }

    public int getPositionY() {
        return positionY;
    }

    public void addPositionY() {
        this.positionY++;
        this.positionX = 0;
        this.count++;
    }

    public int getCurrentChar() {
        return currentChar;
    }

    public void setCurrentChar(int currentChar) {
        this.currentChar = currentChar;
    }

    public void addCount()
    {
        this.count++;
    }

    @Override
    public Coordinate clone() throws CloneNotSupportedException {
        return (Coordinate)super.clone();
    }

    @Override
    public String toString() {
        return positionY + ":" + positionY;
    }
}
