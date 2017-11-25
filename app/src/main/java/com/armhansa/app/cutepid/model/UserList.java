package com.armhansa.app.cutepid.model;

import java.util.List;
import java.util.Random;

public class UserList {

    private List<User> users;
    private Random random = new Random();

    public User getRandomUser() {
        int randInt = random.nextInt(users.size());
        User user_tmp = users.get(randInt);
        users.remove(randInt);

        return user_tmp;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
