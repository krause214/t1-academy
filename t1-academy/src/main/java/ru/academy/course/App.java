package ru.academy.course;

import ru.academy.course.concurrency.ThreadPoolImplementation;

import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {

        ThreadPoolImplementation threadPool = new ThreadPoolImplementation(4);

        addTasks(threadPool, 10);

        Thread.sleep(10000);

        addTasks(threadPool, 15);

        Thread.sleep(2000);

        threadPool.shutdown();
        threadPool.awaitTermination();

        System.out.println("MAIN PROCESS ENDS");

        //TestRunner.runTests(FountainTest.class);

        /*List<Integer> integerList = List.of(1, 10, 12, 5, 64, 22, 123, 1, 123);
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
        System.out.println(StreamTask.getMostLongInnerWord(sentenceList));*/
    }

    private static void addTasks(ThreadPoolImplementation threadPool, int amount) {
        for (int i = 0; i < amount; i++) {
            int taskNumber = i;
            Random random = new Random();
            threadPool.execute(
                    () -> {
                        System.out.println("execution task number " + taskNumber);
                        try {
                            Thread.sleep(1000 + random.nextInt() % 500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        }
    }
}
