package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStream {

    public static void main(String[] args) {

        int[] arrayNumbers = {6, 1, 3, 4, 3, 2, 1, 2, 4};
        System.out.println(minValue(arrayNumbers));

        List<Integer> listNumbers = List.of(5, 10, 13, 3, 22, 4, 17, 6, 8);
        System.out.println(oddOrEven2Streams(listNumbers));
        System.out.println(oddOrEven1Stream(listNumbers));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> 10 * a + b);
//                .reduce(0, (a, b) -> { System.out.printf("a=%d, b=%d%n", a, b); return 10 * a + b; });
    }

    public static List<Integer> oddOrEven1Stream(List<Integer> numbers) {
        Map<Boolean, List<Integer>> map = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0, Collectors.toList()));
//        System.out.println("odd list size=" + map.get(false).size());
        return map.get(map.get(false).size() % 2 != 0); // odd count of odds (=> odd sum) => get even list and v.v.
    }

    public static List<Integer> oddOrEven2Streams(List<Integer> numbers) {
        boolean oddCountOfOdd = numbers.stream()
                .filter(n -> n % 2 != 0)
                .count() % 2 != 0;
//        System.out.println("odd count of odds=" + oddCountOfOdd);
        return numbers
                .stream()
                .filter(n -> oddCountOfOdd == (n % 2 == 0)) // odd count of odds (=> odd sum) => keep evens and v.v.
                .collect(Collectors.toList());
    }
}
