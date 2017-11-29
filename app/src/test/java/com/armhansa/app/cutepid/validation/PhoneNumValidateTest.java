package com.armhansa.app.cutepid.validation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PhoneNumValidateTest {

    @Test
    public void phoneNullTest() {
        PhoneNumberValidation validation = new PhoneNumberValidation();
        assertEquals("Is Null", validation.alert());
    }

    @Test
    public void phoneEmptyTest() {
        PhoneNumberValidation validation = new PhoneNumberValidation();
        validation.setPhoneNumber("");
        assertEquals("Is Empty", validation.alert());
    }

    @Test
    public void phoneNotFitTest() {
        PhoneNumberValidation validation = new PhoneNumberValidation();
        validation.setPhoneNumber("191");
        assertEquals("Must Have 9 or 10 Number", validation.alert());
    }

    @Test
    public void phoneWrongPatternTest() {
        PhoneNumberValidation validation = new PhoneNumberValidation();
        validation.setPhoneNumber("1234567890");
        assertEquals("Is Wrong Pattern", validation.alert());
    }

    @Test
    public void phoneRightWithMobileTest() {
        PhoneNumberValidation validation = new PhoneNumberValidation();
        validation.setPhoneNumber("0999999999");
        assertEquals("Passed", validation.alert());
    }

    @Test
    public void phoneRightWithHomeTest() {
        PhoneNumberValidation validation = new PhoneNumberValidation();
        validation.setPhoneNumber("029999999");
        assertEquals("Passed", validation.alert());
    }

    @Test
    public void phoneWrongTest() {
        PhoneNumberValidation validation = new PhoneNumberValidation();
        validation.setPhoneNumber("Wrong");
        assertEquals(true, validation.invalid());
    }

    @Test
    public void phoneRightTest() {
        PhoneNumberValidation validation = new PhoneNumberValidation();
        validation.setPhoneNumber("0999999999");
        assertEquals(false, validation.invalid());
    }


}
