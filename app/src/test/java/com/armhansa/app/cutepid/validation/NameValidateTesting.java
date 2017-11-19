package com.armhansa.app.cutepid.validation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class NameValidateTesting {

    String result;

    @Test
    public void nameIsNull() {
        NameValidation validation = new NameValidation();
        result = validation.alert();
        assertEquals("Is Null", result);
    }

    @Test
    public void nameIsEmpty() {
        NameValidation validation = new NameValidation();
        validation.setName("");
        result = validation.alert();
        assertEquals("Is Empty", result);
    }

    @Test
    public void nameTooShort() {
        NameValidation validation = new NameValidation();
        validation.setName("H");
        result = validation.alert();
        assertEquals("Is Too Short", result);
    }

    @Test
    public void nameTooLong() {
        NameValidation validation = new NameValidation();
        validation.setName("HelloMyNameIsHansathon");
        result = validation.alert();
        assertEquals("Is Too Long", result);
    }

    @Test
    public void nameWrongPattern() {
        NameValidation validation = new NameValidation();
        validation.setName("lnw999");
        result = validation.alert();
        assertEquals("Is Wrong Pattern", result);
    }

    @Test
    public void nameIsCorrect() {
        NameValidation validate = new NameValidation();
        validate.setName("Henna");
        result = validate.alert();
        assertEquals("Saved", result);
    }


}
