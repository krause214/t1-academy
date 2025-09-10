package ru.academy.course.testannotations;

import ru.academy.course.exceptions.AcademyTestException;
import ru.academy.course.test.AbstractAcademyTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestRunner {

    public static void runTests(Class<? extends AbstractAcademyTest> c) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
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
        long beforeSuiteMethodCount = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(BeforeSuite.class))
                .count();
        if (beforeSuiteMethodCount > 1) {
            throw new AcademyTestException("Найдено методов BeforeSuite больше одного!");
        }

        long afterSuiteMethodCount = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(AfterSuite.class))
                .count();
        if (afterSuiteMethodCount > 1) {
            throw new AcademyTestException("Найдено методов AfterSuite больше одного!");
        }
    }

    private static void executeAfterSuite(Method afterSuiteMethod, Object instance) throws AcademyTestException, InvocationTargetException, IllegalAccessException {
        if (afterSuiteMethod != null) {
            afterSuiteMethod.invoke(instance);
        }
    }

    private static void executeTest(Method testMethod, Object instance, Class<? extends AbstractAcademyTest> c) throws AcademyTestException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
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

    private static void executeBeforeSuite(Method beforeSuiteMethod, Object instance) throws AcademyTestException, InvocationTargetException, IllegalAccessException {
        if (beforeSuiteMethod != null) {
            beforeSuiteMethod.invoke(instance);
        }
    }

}
