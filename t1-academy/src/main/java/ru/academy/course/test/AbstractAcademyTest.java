package ru.academy.course.test;

public abstract class AbstractAcademyTest {
    public AbstractAcademyTest() {
    }

    private Boolean testPassed;

    public void setTestPassed(Boolean testPassed) {
        this.testPassed = testPassed;
    }

    void passed() {
        testPassed = Boolean.TRUE;
    }

    void notPassed() {
        testPassed = Boolean.FALSE;
    }
}
