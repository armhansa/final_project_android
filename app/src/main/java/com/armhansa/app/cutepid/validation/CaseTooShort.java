package com.armhansa.app.cutepid.validation;

public class CaseTooShort implements FillRule {
    @Override
    public boolean validate(String text) {
        return text.length() < 2;
    }
}
