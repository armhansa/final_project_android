package com.armhansa.app.cutepid.validation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PasswordInvalidateTest {

    @Test
    public void passwordNullTest() {
        PasswordValidation validation = new PasswordValidation();
        assertEquals(true, validation.invalid());
        assertEquals("Is Null", validation.alert());
    }

    @Test
    public void passwordEmptyTest() {
        PasswordValidation validation = new PasswordValidation();
        validation.setPassword("");
        assertEquals(true, validation.invalid());
        assertEquals("Is Empty", validation.alert());
    }

    @Test
    public void passwordTooShortTest() {
        PasswordValidation validation = new PasswordValidation();
        validation.setPassword("1234");
        assertEquals(true, validation.invalid());
        assertEquals("Must be 6 to 30 character", validation.alert());
    }

    @Test
    public void passwordTooLongTest() {
        PasswordValidation validation = new PasswordValidation();
        validation.setPassword("12345678901234567890012345678901");
        assertEquals(true, validation.invalid());
        assertEquals("Must be 6 to 30 character", validation.alert());
    }

    @Test
    public void passwordWrongPatternTest() {
        PasswordValidation validation = new PasswordValidation();
        validation.setPassword("(T^T)(*-*)(-.-)");
        assertEquals(true, validation.invalid());
        assertEquals("Is Wrong Pattern", validation.alert());
    }

    @Test
    public void passwordRightTest() {
        PasswordValidation validation = new PasswordValidation();
        validation.setPassword("abc12345");
        assertEquals(false, validation.invalid());
        assertEquals("Passed", validation.alert());
    }


}
