package com.armhansa.app.cutepid.validation;

import java.util.regex.Pattern;

public class CaseEmailWrongPattern implements FillRule {
    @Override
    public boolean validate(String text) {
        return !Pattern.matches("[_a-zA-Z0-9]*@[a-zA-Z]*[.][a-zA-Z]*", text);
    }
}
