package ru.academy.course.stream;

import ru.academy.course.entity.Position;
import ru.academy.course.entity.Worker;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTask {

    public static Integer getThirdGreatest(List<Integer> integerList) {
        return integerList.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Количество чисел меньше трех"));
    }

    public static Integer getThirdUniqueGreatest(List<Integer> integerList) {
        return integerList.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Количество уникальных чисел меньше трех"));
    }

    public static List<Worker> getNamesOfThreeMostAgedEngineers(List<Worker> workerList) {
        return workerList.stream()
                .filter(w -> Position.ENGINEER.equals(w.position()))
                .sorted(Comparator.comparing(Worker::age).reversed())
                .limit(3)
                .toList();
    }

    public static Double getAverageEngineerAge(List<Worker> workerList) {
        return workerList.stream()
                .filter(w -> Position.ENGINEER.equals(w.position()))
                .collect(
                        Collectors.averagingDouble(Worker::age)
                );
    }

    public static String getMostLongWord(List<String> stringList) {
        return stringList.stream().max(Comparator.comparing(String::length))
                .orElse("");
    }

    public static Map<String, Long> getWordCountedMap(String stroka) {
        return Stream.of(stroka.split(" "))
                .collect(
                        Collectors.groupingBy(String::valueOf, Collectors.counting())
                );
    }

    public static void stringPrinterByLength(List<String> stringList) {
        stringList
                .stream()
                .sorted(Comparator.comparing(String::length)
                        .thenComparing(String::valueOf))
                .forEach(System.out::println);
    }

    public static String getMostLongInnerWord(List<String> stringList) {
        return stringList
                .stream()
                .map(s -> s.split(" "))
                .flatMap(Stream::of)
                .max(Comparator.comparing(String::length))
                .orElse("");
    }
}
