package ru.gb.testAndLog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.stream.Stream;

public class ArrayUtilsTest {
    //NEW ARRAY WITH DIGITS AFTER LAST FOUR
    //======Exercise 1========
    @ParameterizedTest
    @MethodSource("pickNumbersAfterLast4ParametersProvider")
    void shouldCreateNewArrayAfterLast4OfOldArray(int[] arrayExpected,int[] array){
        Assertions.assertArrayEquals(arrayExpected, ArrayUtils.pickNumbersAfterLast4(array));
    }
    private static Stream<Arguments> pickNumbersAfterLast4ParametersProvider() {
        return Stream.of(
                Arguments.arguments(new int[]{7, 7, 7}, new int []{5, 6, 2, 4, 5, 1, 4, 7, 7, 7}),
                Arguments.arguments(new int[]{8, 5}, new int []{99, 3, 32, 4, 8, 5}),
                Arguments.arguments(new int[]{1, 7}, new int []{1, 2, 4, 4, 2, 3, 4, 1, 7})
        );
    }

    @Test
    void shouldThrowRuntimeExceptionIfArrayWithout4(){
        Assertions.assertThrows(RuntimeException.class, () -> ArrayUtils.pickNumbersAfterLast4(new int[]{1, 3, 1}));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowIllegalArgumentExceptionWhenEmptyArrayPassed(int[] emptyArray) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ArrayUtils.pickNumbersAfterLast4(emptyArray));
    }

    //CHECK THE ARRAY
    //======Exercise 2========
    @ParameterizedTest
    @MethodSource("isArrayConsistOf1and4ParametersProvider")
    void shouldTellWhetherOrNotArrayConsistsOf1and4(int[] array, int value1, int value2){
        Assertions.assertTrue(ArrayUtils.isArrayConsistOf1and4(array, value1, value2));
    }
    private static Stream<Arguments> isArrayConsistOf1and4ParametersProvider(){
        return Stream.of(
                Arguments.arguments(new int[]{1, 4, 1, 1, 1, 1}, 1, 4),
                Arguments.arguments(new int[]{4, 1, 1, 4, 1, 4}, 1, 4),
                Arguments.arguments(new int[]{4, 1, 4, 4, 4, 4}, 1, 4)
        );
    }
}
