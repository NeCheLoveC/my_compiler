package com.protsenko.compiler;

public class Coordinate implements Cloneable
{
    int positionX;
    private int positionY;
    int count;
    //-1 - конец файла
    //-2 - итерация по файлу еще не началась

    int currentChar;

    public Coordinate()
    {
        positionY = 1;
        positionX = -1;
        count = 0;
        currentChar = -2;
    }

    public int getPositionX() {
        return positionX;
    }

    public void addPositionX() {
        count++;
        this.positionX++;
    }

    public int getPositionY() {
        return positionY;
    }

    public void addPositionY() {
        this.positionY++;
        this.positionX = -1;
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

    public int getCount() {
        return count;
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
