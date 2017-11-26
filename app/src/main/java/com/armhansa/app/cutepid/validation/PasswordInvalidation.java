package com.armhansa.app.cutepid.validation;

import java.util.ArrayList;
import java.util.List;

public class PasswordInvalidation {

    String password;

    private static String alerts[] = {
            "Is Null"
            , "Is Empty"
            , "Must be 6 to 30 character"
            , "Is Wrong Pattern"
            // You can create new case alert at this
            , "Passed"};

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean invalid() {
        return alert() != "Passed";
    }

    public String alert() {
        List<FillRule> invalidate = new ArrayList<>();
        invalidate.add(new CaseNull());
        invalidate.add(new CaseEmpty());
        invalidate.add(new CasePasswordNotFit());
        invalidate.add(new CasePasswordWrongPattern());
        // add new Case on this line

        int i = -1;
        while(++i < invalidate.size() && !invalidate.get(i).validate(password));

        return alerts[i];
    }

}
