package ru.academy.course.testannotations;

import ru.academy.course.exceptions.AcademyTestException;
import ru.academy.course.test.AbstractAcademyTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestRunner {

    public static void runTests(Class<? extends AbstractAcademyTest> c) throws Exception {
        try {
            Constructor<?> constructor = c.getConstructor();
            Object instance = constructor.newInstance();

            Method[] methods = c.getDeclaredMethods();

            validateTestClass(methods);

            Method beforeSuiteMethod = null;
            List<Method> testMethodList = new ArrayList<>();
            Method afterSuiteMethod = null;

            for (Method method : methods) {
                method.setAccessible(true);
                if (method.isAnnotationPresent(AfterSuite.class)) {
                    afterSuiteMethod = method;
                }
                if (method.isAnnotationPresent(BeforeSuite.class)) {
                    beforeSuiteMethod = method;
                }
                if (method.isAnnotationPresent(AcademyTest.class)) {
                    testMethodList.add(method);
                }
            }

            testMethodList.sort(
                    Comparator.comparingInt(a -> a.getAnnotation(AcademyTest.class).priority())
            );

            for (Method testMethod : testMethodList) {
                executeBeforeSuite(beforeSuiteMethod, instance);
                executeTest(testMethod, instance, c);
                executeAfterSuite(afterSuiteMethod, instance);
                System.out.println("-----------------------------------------");
            }

        } catch (AcademyTestException e) {
            System.out.println("Ошибка при исполнении тестов. " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла неизвестная ошибка при исполнении тестов над классом - " + c.getName());
            throw e;
        }
    }

    private static void validateTestClass(Method[] methods) throws AcademyTestException {
        long beforeSuiteMethodCount = 0;
        long afterSuiteMethodCount = 0;
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                beforeSuiteMethodCount++;
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                afterSuiteMethodCount++;
            }
        }

        String errorMessage = "";

        if (beforeSuiteMethodCount > 1) {
            errorMessage = "Найдено методов BeforeSuite больше одного!; ";
        }
        if (afterSuiteMethodCount > 1) {
            errorMessage = errorMessage.concat("Найдено методов AfterSuite больше одного!; ");
        }

        if (!errorMessage.isEmpty()) {
            throw new AcademyTestException(errorMessage);
        }
    }

    private static void executeAfterSuite(Method afterSuiteMethod, Object instance) throws Exception {
        if (afterSuiteMethod != null) {
            afterSuiteMethod.invoke(instance);
        }
    }

    private static void executeTest(Method testMethod, Object instance, Class<? extends AbstractAcademyTest> c) throws Exception {
        System.out.println("Выполняется тест " + testMethod.getName());
        testMethod.invoke(instance);
        Field testPassedField = c.getSuperclass().getDeclaredField("testPassed");
        testPassedField.setAccessible(true);
        if (testPassedField.get(instance) == null) {
            throw new AcademyTestException(
                    String.format("Для метода %s не определены assertions через методы passed(), notPassed()",
                            testMethod.getName()));
        }
        if ((boolean) testPassedField.get(instance)) {
            System.out.println(String.format("Тест %s пройден!",
                    testMethod.getName()));
        } else {
            System.out.println(String.format("Тест %s НЕ пройден!",
                    testMethod.getName()));
        }
    }

    private static void executeBeforeSuite(Method beforeSuiteMethod, Object instance) throws Exception {
        if (beforeSuiteMethod != null) {
            beforeSuiteMethod.invoke(instance);
        }
    }

}
