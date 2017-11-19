package com.armhansa.app.cutepid.validation;

public class CaseTooLong implements FillRule {
    @Override
    public boolean validate(String text) {
        return text.length() > 20;
    }
}
