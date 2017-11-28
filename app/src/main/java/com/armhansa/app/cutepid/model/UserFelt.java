package com.armhansa.app.cutepid.model;

import java.util.HashMap;
import java.util.Map;

public class UserFelt {

    // Put User Felt Another (0 = disLiked, 1 = liked)
    private Map<String, Integer> felt;

    public UserFelt() {
        felt = new HashMap<>();
    }


    public void addLiked(String userId) {
        felt.put(userId, 1);
    }

    public void addDisLiked(String userId) {
        felt.put(userId, 0);
    }

    public boolean hasFelt(String userId) {
        return felt.get(userId) != null;
    }

    public boolean hasLiked(String userId) {
        return felt.get(userId) != null && felt.get(userId) == 1;
    }

    public boolean isLike(String userId) {
        return felt.get(userId) == 1;
    }

    public Map<String, Integer> getFelt() {
        return felt;
    }

    public void setFelt(Map<String, Integer> felt) {
        this.felt = felt;
    }

}
