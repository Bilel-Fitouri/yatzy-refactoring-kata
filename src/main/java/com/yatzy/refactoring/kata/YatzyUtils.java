package com.yatzy.refactoring.kata;

import io.vavr.Tuple;
import java.util.Arrays;
import java.util.stream.IntStream;

public final class YatzyUtils {

    private YatzyUtils() {
    }

    /**
     *
     * @param dice
     * @param value
     * @return the sum of occurences of a given value
     */
    public static int occurenceOfValueThenSum(int[] dice, int value) {
        return Arrays.stream(dice)
            .filter(val -> val == value)
            .sum();
    }

    /**
     *
     * @param d1
     * @param d2
     * @param d3
     * @param d4
     * @param d5
     * @param value
     * @return the occurence of a given value
     */
    public static int occurenceOfValue(int d1, int d2, int d3, int d4, int d5, int value) {
        return (int) Arrays.asList(d1, d2, d3, d4, d5)
            .stream()
            .filter(val -> val == value)
            .mapToInt(Integer::intValue)
            .count();
    }

    /**
     *
     * @param d1
     * @param d2
     * @param d3
     * @param d4
     * @param d5
     * @param occurence
     * @return if a minimun number of given occurence is satisfied, return the sum of them, otherwise return 0
     */
    public static int sum_if_min_occurence_of_a_kind(int d1, int d2, int d3, int d4, int d5, int occurence) {
        return IntStream.rangeClosed(1, 6)
            .mapToObj(value -> Tuple
                .of(value, YatzyUtils.occurenceOfValue(d1, d2, d3, d4, d5, value)))
            .filter(tuple -> tuple._2 >= occurence)
            .findFirst()
            .map(tt -> occurence * tt._1)
            .orElse(0);
    }
}
