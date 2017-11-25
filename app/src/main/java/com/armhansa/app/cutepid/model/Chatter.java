package com.armhansa.app.cutepid.model;


import java.util.HashMap;
import java.util.Map;

public class Chatter {

    private Map<String, Chat> chatter;

    public Chatter() {
        chatter = new HashMap<>();
    }


    public void addChatter(String userId) {
        chatter.put(userId, new Chat());
    }

    public Map<String, Chat> getChater() {
        return chatter;
    }

    public void setChatter(Map<String, Chat> chatter) {
        this.chatter = chatter;
    }
}
