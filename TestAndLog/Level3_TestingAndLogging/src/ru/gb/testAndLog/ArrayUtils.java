package ru.gb.testAndLog;

public class ArrayUtils {
    private ArrayUtils(){}

    //NEW ARRAY WITH DIGITS AFTER LAST FOUR
    //======Exercise 1========
    public static int[] pickNumbersAfterLast4(int[] array){

        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }

        int x = -1;
        int [] newArray = null;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4) {
                x = array[i];
                newArray = new int[array.length - i - 1];
                for (int j = i + 1, k = 0; j < array.length; j++, k++) {
                    if (array[j] == 4) continue;
                    newArray[k] = array[j];
                }
            }
        }
        if (x < 0) throw new RuntimeException("No 4 found");

        return newArray;
    }

    //CHECK THE ARRAY
    //======Exercise 2========
    public static boolean isArrayConsistOf1and4(int[] array, int value1, int value2){
        boolean containsValue1 = false;
        boolean containsValue2 = false;

        for (int value : array) {
            if (value == value1)
                containsValue1 = true;
            else if (value == value2)
                containsValue2 = true;
            else
                return false;
        }

        return containsValue1 && containsValue2;
    }
}

