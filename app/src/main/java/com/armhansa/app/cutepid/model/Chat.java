package com.armhansa.app.cutepid.model;

import java.util.HashMap;
import java.util.Map;

public class Chat {

    private Map<String, String> chat;
    private int count;

    public Chat() {
        chat = new HashMap<>();
        count = -1;
    }

    public void addChat(String message) {
        chat.put(String.valueOf(count++), message);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Map<String, String> getChat() {
        return chat;
    }

    public void setChat(Map<String, String> chat) {
        this.chat = chat;
    }
}
