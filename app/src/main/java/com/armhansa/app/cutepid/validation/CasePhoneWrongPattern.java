package com.armhansa.app.cutepid.validation;

import java.util.regex.Pattern;

public class CasePhoneWrongPattern implements FillRule {

    @Override
    public boolean validate(String text) {
        return !Pattern.matches("0[2-9][0-9]*", text);
    }

}
