package com.armhansa.app.cutepid.validation;

/**
 * Created by armha on 11/26/2017.
 */

public class CasePasswordNotFit implements FillRule {

    @Override
    public boolean validate(String text) {
        return text.length() < 6 || text.length() > 30;
    }
}
