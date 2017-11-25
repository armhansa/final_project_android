package com.armhansa.app.cutepid.model;

import java.util.HashMap;
import java.util.Map;

public class Chat {

    Map<String, String> chat;

    public Chat() {
        chat = new HashMap<>();
    }

    public void addChat(String userId, String message) {
        chat.put(userId, message);
    }

    public Map<String, String> getChat() {
        return chat;
    }

    public void setChat(Map<String, String> chat) {
        this.chat = chat;
    }
}
