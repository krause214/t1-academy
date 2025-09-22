package ru.academy.course;

import ru.academy.course.entity.Position;
import ru.academy.course.entity.Worker;
import ru.academy.course.stream.StreamTask;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        //TestRunner.runTests(FountainTest.class);

        List<Integer> integerList = List.of(1, 10, 12, 5, 64, 22, 123, 1, 123);
        List<Worker> workerList = List.of(
                new Worker("Alex", 70, Position.ENGINEER),
                new Worker("Fedor", 21, Position.MANAGER),
                new Worker("Nikolas", 54, Position.MANAGER),
                new Worker("Vyacheslav", 34, Position.ENGINEER),
                new Worker("Svyatoslav", 55, Position.MANAGER),
                new Worker("Dobrinya", 44, Position.ENGINEER),
                new Worker("Vasilevsk", 65, Position.ENGINEER)
        );
        List<String> stringList = List.of("найдите в списке слов самое длинное супердлинноеслово тест".split(" "));
        String stroka = "любая строка с повторениями любая любая любая с повторениями с повторениями а";
        List<String> sentenceList = List.of(
                "это предложение с разными словами",
                "еще одно предложение и тутсамоедлинноеслово",
                "тут просто будет пять слов"
        );

        System.out.println(StreamTask.getThirdGreatest(integerList));
        System.out.println(StreamTask.getThirdUniqueGreatest(integerList));
        System.out.println(StreamTask.getNamesOfThreeMostAgedEngineers(workerList));
        System.out.println(StreamTask.getAverageEngineerAge(workerList));
        System.out.println(StreamTask.getMostLongWord(stringList));
        System.out.println(StreamTask.getWordCountedMap(stroka));
        StreamTask.stringPrinterByLength(stringList);
        System.out.println(StreamTask.getMostLongInnerWord(sentenceList));
    }
}
