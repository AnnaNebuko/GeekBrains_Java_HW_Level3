package ru.gb.generics;

import java.util.Arrays;

public class GenericArr<T> {
    T[] array;

    GenericArr(T[] myArray) {
        array = myArray;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    public T[] getArray() {
        return array;
    }

    public T[] changeElementsPosition(GenericArr<T> myArray, int firstPosition, int secondPosition) {
        T[] newArray = myArray.getArray();
        try{
            T temp = newArray[firstPosition];
            newArray[firstPosition] = newArray[secondPosition];
            newArray[secondPosition] = temp;
            return newArray;
        } catch (ArrayIndexOutOfBoundsException e){
            throw new RuntimeException("Check your indexes, friend.", e);
        }
    }
}
