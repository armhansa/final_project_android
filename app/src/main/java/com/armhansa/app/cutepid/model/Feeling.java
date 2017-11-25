package com.armhansa.app.cutepid.model;

import java.util.HashMap;
import java.util.Map;

public class Feeling {

    // Singleton
    private static Feeling myFeeling;

    // Put User Felt Another (0 = disLiked, 1 = liked)
    Map<String, Integer> felt;

    private Feeling() {
        felt = new HashMap<>();
    }

    public static Feeling getMyFeeling() {
        if(myFeeling == null) myFeeling = new Feeling();
        return myFeeling;
    }

    public static void setMyFeeling(Feeling feeling) {
        myFeeling = feeling;
    }

    public void addDisLiked(String userId) {
        felt.put(userId, 0);
    }

    public void addLiked(String userId) {
        felt.put(userId, 1);
    }

    public boolean hasFelt(String userId) {
        return felt.get(userId) != null;
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
