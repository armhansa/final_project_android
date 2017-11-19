package com.armhansa.app.cutepid.validation;

public class CaseNull implements FillRule {
    @Override
    public boolean validate(String text) {
        return text == null;
    }
}
