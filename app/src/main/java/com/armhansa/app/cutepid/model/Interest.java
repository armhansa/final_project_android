package com.armhansa.app.cutepid.model;

public class Interest {

    private static Interest myInterest;

    private String gender;
    private int min_age;
    private int max_age;

    private Interest() { }

    public static Interest getInterest() {
        if(myInterest == null) {
            myInterest = new Interest();
        }
        return myInterest;
    }

    public void setAttibute(String gender, int min_age, int max_age) {
        this.gender = gender;
        this.min_age = min_age;
        this.max_age = max_age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getMin_age() {
        return min_age;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
    }

    public int getMax_age() {
        return max_age;
    }

    public void setMax_age(int max_age) {
        this.max_age = max_age;
    }
}
