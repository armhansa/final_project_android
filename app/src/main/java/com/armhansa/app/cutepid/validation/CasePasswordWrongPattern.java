package com.armhansa.app.cutepid.validation;

import java.util.regex.Pattern;

public class CasePasswordWrongPattern implements FillRule {

    @Override
    public boolean validate(String text) {
        return !Pattern.matches("[a-zA-Z0-9]*", text);
    }

}
