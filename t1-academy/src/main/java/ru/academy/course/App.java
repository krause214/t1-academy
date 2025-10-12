package ru.academy.course;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.academy.course.concurrency.ThreadPoolImplementation;
import ru.academy.course.config.ApplicationConfiguration;
import ru.academy.course.entity.Position;
import ru.academy.course.entity.Worker;
import ru.academy.course.spirngcontext.User;
import ru.academy.course.spirngcontext.UserService;
import ru.academy.course.stream.StreamTask;
import ru.academy.course.test.FountainTest;
import ru.academy.course.testannotations.TestRunner;

import java.util.List;
import java.util.Random;

@ComponentScan
public class App {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);

        UserService userService = (UserService) context.getBean("userService");

        User user = userService.createUser(new User("some_username"));
        userService.deleteUser(user.getId());

        user = userService.createUser(user);
        user.setUsername("other_username");
        userService.updateUser(user);

        List<User> userToCreate = List.of(
                new User("Pupa"),
                new User("Lupa"),
                new User("abvgd"),
                new User("PUPUPU"),
                new User("other_username")
        );

        List<User> createdUser = userService.createUsers(userToCreate);

        List<Long> idListToGet = List.of(
                createdUser.get(0).getId(),
                createdUser.get(1).getId()
        );

        List<User> getUserList = userService.getUsers(idListToGet);

        User userToDelete = createdUser.get(0);
        userService.deleteUser(userToDelete.getId());
        userService.deleteUser(userToDelete.getId());

        List<User> getAllUsers = userService.getAllUsers();
    }

    private static void executeStreamApiTask() {
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

    private static void executeTestTask() throws Exception {
        TestRunner.runTests(FountainTest.class);
    }

    private static void executeConcurrencyTask() throws InterruptedException {
        ThreadPoolImplementation threadPool = new ThreadPoolImplementation(4);

        addTasks(threadPool, 10);

        Thread.sleep(10000);

        addTasks(threadPool, 15);

        Thread.sleep(2000);

        threadPool.shutdown();
        threadPool.awaitTermination();

        System.out.println("MAIN PROCESS ENDS");
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
