package ru.academy.course;

import ru.academy.course.test.FountainTest;
import ru.academy.course.testannotations.TestRunner;

import java.lang.reflect.InvocationTargetException;

public class App {
    public static void main(String[] args) throws Exception {
        TestRunner.runTests(FountainTest.class);
    }
}
