package com.armhansa.app.cutepid.validation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class NameValidateTest {

    String result;

    @Test
    public void nameIsNull() {
        NameValidation validation = new NameValidation();
        assertEquals(true, validation.invalid());
        assertEquals("Is Null", validation.alert());
    }

    @Test
    public void nameIsEmpty() {
        NameValidation validation = new NameValidation();
        validation.setName("");
        assertEquals(true, validation.invalid());
        assertEquals("Is Empty", validation.alert());
    }

    @Test
    public void nameTooShort() {
        NameValidation validation = new NameValidation();
        validation.setName("H");
        assertEquals(true, validation.invalid());
        assertEquals("Is Too Short", validation.alert());
    }

    @Test
    public void nameTooLong() {
        NameValidation validation = new NameValidation();
        validation.setName("HelloMyNameIsHansathon");
        assertEquals(true, validation.invalid());
        assertEquals("Is Too Long", validation.alert());
    }

    @Test
    public void nameWrongPattern() {
        NameValidation validation = new NameValidation();
        validation.setName("lnw999");
        assertEquals(true, validation.invalid());
        assertEquals("Is Wrong Pattern", validation.alert());
    }

    @Test
    public void nameIsCorrect() {
        NameValidation validation = new NameValidation();
        validation.setName("Henna");
        assertEquals(false, validation.invalid());
        assertEquals("Passed", validation.alert());
    }


}
