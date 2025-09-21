package ru.academy.course.test;

import ru.academy.course.fountain.Fountain;
import ru.academy.course.testannotations.AcademyTest;
import ru.academy.course.testannotations.AfterSuite;
import ru.academy.course.testannotations.BeforeSuite;

import java.lang.reflect.Field;

public class FountainTest extends AbstractAcademyTest {

    private Fountain fountain;

    @BeforeSuite
    void init() {
        fountain = new Fountain(5, "F3000");
    }

    @AfterSuite
    void returnDefaultStatement() throws Exception {
        fountain.disable();
        fountain.disconnectFromWaterSupply();
        Field field = fountain.getClass().getDeclaredField("force");
        field.setAccessible(true);
        field.set(fountain, null);
    }

    @AcademyTest(priority = 3)
    void tryToEnableFountainWithNoWaterTest() throws Exception {
        fountain.enable();

        Field enabledField = fountain.getClass().getDeclaredField("enabled");
        enabledField.setAccessible(true);
        Boolean result = (Boolean) enabledField.get(fountain);
        if (result) {
            notPassed();
        } else {
            passed();
        }
    }

    @AcademyTest(priority = 1)
    void tryToEnableFountainWithWaterTest() throws Exception {
        fountain.connectToWaterSupply();
        fountain.enable();
        Field enabledField = fountain.getClass().getDeclaredField("enabled");
        enabledField.setAccessible(true);
        Boolean result = (Boolean) enabledField.get(fountain);
        if (result) {
            passed();
        } else {
            notPassed();
        }
    }
}
