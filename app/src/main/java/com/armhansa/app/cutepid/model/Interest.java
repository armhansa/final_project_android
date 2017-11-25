package com.armhansa.app.cutepid.model;

public class Interest {

    private static Interest myInterest;

    private String gender;
    private int min_age;
    private int max_age;

//    Maybe must public for use firebase
    private Interest() { }
    private Interest(String gender, int min_age, int max_age) {
        this.gender = gender;
        this.min_age = min_age;
        this.max_age = max_age;
    }

    public static Interest getInterest() {
        if(myInterest == null) {
            myInterest = new Interest();
        }
        return myInterest;
    }

    public static void setInterest(Interest interest) {
        myInterest = interest;
    }

    public static Interest getInterest(String gender, int min_age, int max_age) {
        myInterest = new Interest(gender, min_age, max_age);
        return myInterest;
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
