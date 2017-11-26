package com.armhansa.app.cutepid.validation;

public class CasePhoneNotFit implements FillRule {

    @Override
    public boolean validate(String text) {
        return text.length() != 9 && text.length() != 10;
    }

}
