package com.armhansa.app.cutepid.validation;

public class CaseEmpty implements FillRule {
    @Override
    public boolean validate(String text) {
        return "".equals(text);
    }
}
