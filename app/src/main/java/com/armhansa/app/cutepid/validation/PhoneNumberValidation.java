package com.armhansa.app.cutepid.validation;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumberValidation {

    String phoneNumber;

    private static String alerts[] = {
            "Is Null"
            , "Is Empty"
            , "Must Have 9 or 10 Number"
            , "Is Wrong Pattern"
            // You can create new case alert at this
            , "Passed"};

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean invalid() {
        return alert() != "Passed";
    }

    public String alert() {
        List<FillRule> invalidate = new ArrayList<>();
        invalidate.add(new CaseNull());
        invalidate.add(new CaseEmpty());
        invalidate.add(new CasePhoneNotFit());
        invalidate.add(new CasePhoneWrongPattern());
        // add new Case on this line

        int i = -1;
        while(++i < invalidate.size() && !invalidate.get(i).validate(phoneNumber));

        return alerts[i];
    }

}
