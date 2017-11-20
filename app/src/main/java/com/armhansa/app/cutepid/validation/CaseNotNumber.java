package com.armhansa.app.cutepid.validation;

public class CaseNotNumber implements FillRule {
    @Override
    public boolean validate(String text) {
        try {
            Integer.parseInt(text);
            return false;
        } catch (NumberFormatException ex) {
            return true;
        }
    }
}
