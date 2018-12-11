package com.yatzy.refactoring.kata;

import static com.yatzy.refactoring.kata.YatzyUtils.occurenceOfValue;
import static com.yatzy.refactoring.kata.YatzyUtils.occurenceOfValueThenSum;
import static com.yatzy.refactoring.kata.YatzyUtils.sum_if_min_occurence_of_a_kind;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Yatzy {

    protected int[] dice;

    public Yatzy(int d1, int d2, int d3, int d4, int d5) {
        dice = new int[5];
        dice[0] = d1;
        dice[1] = d2;
        dice[2] = d3;
        dice[3] = d4;
        dice[4] = d5;
    }

    /**
     * Sum all values
     * @param d1
     * @param d2
     * @param d3
     * @param d4
     * @param d5
     * @return summed values
     */
    public static int chance(int d1, int d2, int d3, int d4, int d5) {
        return Arrays.asList(d1, d2, d3, d4, d5)
            .stream()
            .mapToInt(Integer::intValue)
            .sum();
    }

    /**
     *
     * @param dice
     * @return 50 if yatzy happens
     */
    public static int yatzy(int... dice) {
        return Arrays.stream(dice)
            .distinct()
            .count() == 1 ? 50 : 0;
    }

    /**
     * Sum all ones
     * @param d1
     * @param d2
     * @param d3
     * @param d4
     * @param d5
     * @return summed values
     */
    public static int ones(int d1, int d2, int d3, int d4, int d5) {
        int[] values = IntStream.of(d1, d2, d3, d4, d5).toArray();
        return occurenceOfValueThenSum(values, 1);
    }

    /**
     * Sum all twos
     * @param d1
     * @param d2
     * @param d3
     * @param d4
     * @param d5
     * @return summed values
     */
    public static int twos(int d1, int d2, int d3, int d4, int d5) {
        int[] values = IntStream.of(d1, d2, d3, d4, d5).toArray();
        return occurenceOfValueThenSum(values, 2);
    }

    /**
     * Sum all threes
     * @param d1
     * @param d2
     * @param d3
     * @param d4
     * @param d5
     * @return summed values
     */
    public static int threes(int d1, int d2, int d3, int d4, int d5) {
        int[] values = IntStream.of(d1, d2, d3, d4, d5).toArray();
        return occurenceOfValueThenSum(values, 3);
    }

    /**
     * Sum all fours
     * @return summed values
     */
    public int fours() {
        return occurenceOfValueThenSum(dice, 4);
    }

    /**
     * Sum all fives
     * @return summed values
     */
    public int fives() {
        return occurenceOfValueThenSum(dice, 5);
    }

    /**
     * Sum all sixes
     * @return summed values
     */
    public int sixes() {
        return occurenceOfValueThenSum(dice, 6);
    }

    /**
     *
     * @param d1
     * @param d2
     * @param d3
     * @param d4
     * @param d5
     * @return the sum of the highest pair, otherwise 0
     */
    public static int score_pair(int d1, int d2, int d3, int d4, int d5) {
        return IntStream.rangeClosed(1, 6)
            .mapToObj(value -> Tuple
                .of(value, occurenceOfValue(d1, d2, d3, d4, d5, value)))
            .filter(tuple -> tuple._2 >= 2)
            .max(Comparator.comparing(t1 -> t1._1))
            .map(highestTuple -> 2 * highestTuple._1)
            .orElse(0);
    }

    /**
     *
     * @param d1
     * @param d2
     * @param d3
     * @param d4
     * @param d5
     * @return if 2 pairs exits, the sum of them, otherwise 0
     */
    public static int two_pair(int d1, int d2, int d3, int d4, int d5) {
        List<Tuple2<Integer, Integer>> collect = IntStream.rangeClosed(1, 6)
            .mapToObj(value -> Tuple
                .of(value, occurenceOfValue(d1, d2, d3, d4, d5, value)))
            .filter(tuple -> tuple._2 >= 2)
            .collect(Collectors.toList());

        return (collect.size() == 2)
            ? collect
                .stream()
                .map(tuple -> tuple._1 * 2)
                .mapToInt(Integer::intValue)
                .sum()
            : 0;
    }

    /**
     *
     * @param d1
     * @param d2
     * @param d3
     * @param d4
     * @param d5
     * @return if 3 of the same number exists, return the sum of them, otherwise 0
     */
    public static int three_of_a_kind(int d1, int d2, int d3, int d4, int d5) {
        return sum_if_min_occurence_of_a_kind(d1, d2, d3, d4, d5, 3);
    }

    /**
     *
     * @param d1
     * @param d2
     * @param d3
     * @param d4
     * @param d5
     * @return if 4 of the same number exists, return the sum of them, otherwise 0
     */
    public static int four_of_a_kind(int d1, int d2, int d3, int d4, int d5) {
        return sum_if_min_occurence_of_a_kind(d1, d2, d3, d4, d5, 4);
    }

    /**
     *
     * @param d1
     * @param d2
     * @param d3
     * @param d4
     * @param d5
     * @return 15 if a non ordered suite composed of (1,2,3,4,5) exists, otherwise 0
     */
    public static int smallStraight(int d1, int d2, int d3, int d4, int d5) {
        List<Tuple2<Integer, Integer>> collect = IntStream.rangeClosed(1, 5)
            .mapToObj(value -> Tuple
                .of(value, occurenceOfValue(d1, d2, d3, d4, d5, value)))
            .filter(tuple -> tuple._2 == 1)
            .collect(Collectors.toList());

        return (collect.size() == 5) ? 15 : 0;
    }

    /**
     *
     * @param d1
     * @param d2
     * @param d3
     * @param d4
     * @param d5
     * @return 20 if a non ordered suite composed of (2,3,4,5,6) exists, otherwise 0
     */
    public static int largeStraight(int d1, int d2, int d3, int d4, int d5) {
        List<Tuple2<Integer, Integer>> collect = IntStream.rangeClosed(2, 6)
            .mapToObj(value -> Tuple
                .of(value, occurenceOfValue(d1, d2, d3, d4, d5, value)))
            .filter(tuple -> tuple._2 == 1)
            .collect(Collectors.toList());

        return (collect.size() == 5) ? 20 : 0;
    }

    /**
     *
     * @param d1
     * @param d2
     * @param d3
     * @param d4
     * @param d5
     * @return if 3 of the same number and 2 of the same number exist, return the sum of them, otherwise 0
     */
    public static int fullHouse(int d1, int d2, int d3, int d4, int d5) {
        List<Tuple2<Integer, Integer>> double_triple_tuples = IntStream.rangeClosed(1, 6)
            .mapToObj(value -> Tuple
                .of(value, occurenceOfValue(d1, d2, d3, d4, d5, value)))
            .filter(tuple -> tuple._2 == 3 || tuple._2 == 2)
            .collect(Collectors.toList());

        long count = double_triple_tuples
            .stream()
            .mapToInt(tutu -> tutu._2)
            .distinct()
            .count();

        return (count == 2)
            ? double_triple_tuples
                .stream()
                .map(tt -> tt._1 * tt._2)
                .mapToInt(Integer::intValue)
                .sum()
            : 0;
    }
}
