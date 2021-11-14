package com.jit.aquaculture.config;

import com.jit.aquaculture.domain.User;

public class UserInfoContext {
    private static ThreadLocal<User> userInfo = new ThreadLocal<User>();

    public UserInfoContext() {
    }

    public static User getUser() {
        return (User) userInfo.get();
    }

    public static void setUser(User user) {
        userInfo.set(user);
    }
}