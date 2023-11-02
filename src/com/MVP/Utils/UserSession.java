package com.MVP.Utils;

import com.MVP.Entite.User;

public class UserSession {
    private static UserSession instance;
    private User currentUser;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public User getUser() {
        return currentUser;
    }

    public void setUser(User user) {
        currentUser = user;
    }
}