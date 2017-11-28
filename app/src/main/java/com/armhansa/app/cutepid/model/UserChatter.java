package com.armhansa.app.cutepid.model;


import java.util.HashMap;
import java.util.Map;

public class UserChatter {

    private Map<String, Chat> chatter;

    public UserChatter() {
        chatter = new HashMap<>();
    }

    public boolean hasChatter(String userId) {
        return chatter.get(userId) != null;
    }

    public void addChatter(String userId) {
        chatter.put(userId, new Chat());
        chatter.get(userId).addChat("Let's Start.");
    }

    public Map<String, Chat> getChatter() {
        return chatter;
    }

    public void setChatter(Map<String, Chat> chatter) {
        this.chatter = chatter;
    }

}
