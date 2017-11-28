package com.armhansa.app.cutepid.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class User {

    private static User ownAccount;

    private UserFilter myUserFilter;
    private UserFelt myUserFelt;
    private UserChatter myUserChatter;

    private String id;
    private String password;
    private String firstName;
    private Date birthDay;
    private String gender;
    private String status;
    private String profile;
    private int age;
    private boolean facebookUser;

    public static void setOwnAccount(User user) {
        ownAccount = user;
    }

    public static User getOwnerAccount() {
        return ownAccount;
    }

    public void setMyUserFilter(UserFilter myUserFilter) {
        this.myUserFilter = myUserFilter;
    }

    public UserFilter getMyUserFilter() {
        if(myUserFilter == null) myUserFilter = new UserFilter();
        return myUserFilter;
    }

    public UserFelt getMyUserFelt() {
        if(myUserFelt == null) myUserFelt = new UserFelt();
        return myUserFelt;
    }

    public void setMyUserFelt(UserFelt myUserFelt) {
        this.myUserFelt = myUserFelt;
    }

    public UserChatter getMyUserChatter() {
        if(myUserChatter == null) myUserChatter = new UserChatter();
        return myUserChatter;
    }

    public void setMyUserChatter(UserChatter myUserChatter) {
        this.myUserChatter = myUserChatter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
        age = getDiffYears(birthDay, new Date());
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        if(status == null) return "ยังโสดโสดอยู่ตรงนี้";
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    private int getDiffYears(Date first, Date last) {

        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH)
                        && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public boolean isFacebookUser() {
        return facebookUser;
    }

    public void setFacebookUser(boolean facebookUser) {
        this.facebookUser = facebookUser;
    }

    private static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

}
