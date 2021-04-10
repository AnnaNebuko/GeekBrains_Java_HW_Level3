package ru.gb.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //======Task 1======
        GenericArr<Integer> arr1 = new GenericArr(new Integer[]{2,4,3,5});
        GenericArr<Integer> arr2 = new GenericArr(new String[]{"cat", "dog", "cow", "bird"});
        System.out.println("Before changing: " + arr1);
        System.out.println("Before changing: " + arr2);

        System.out.println("After changing: " +
                Arrays.toString(arr1.changeElementsPosition(arr1, 1, 3)));
        System.out.println("After changing: " +
                Arrays.toString(arr2.changeElementsPosition(arr2, 3, 2)));

        //======Task2======
        String [] array = new String []{"a", "n", "n", "a"};
        List<String> arrayList = arrayToArrayList(array);
        System.out.println("Task 2: " + arrayList);

        //======Task3======
        Box <Apple> appleBox = new Box<>();
        appleBox.add(new Apple());

        Box<Orange> orangeBox = new Box<>();
        orangeBox.add(new Orange());
        orangeBox.add(new Orange());

        System.out.println("Is appleBox bigger then orangeBox? : " + appleBox.compare(orangeBox));
    }
    public static <T> List<T> arrayToArrayList(T[] array){
        return new ArrayList<T>(Arrays.asList(array));
    }

}
