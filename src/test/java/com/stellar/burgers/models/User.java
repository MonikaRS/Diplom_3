package com.stellar.burgers.models;

import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private final String email;
    private final String password;
    private final String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User getRandomUser() {
        String email = RandomStringUtils.randomAlphanumeric(8).toLowerCase() + "@test.com";
        String password = RandomStringUtils.randomAlphanumeric(10);
        String name = "TestUser_" + RandomStringUtils.randomAlphanumeric(5);
        System.out.println("🎲 Сгенерирован пользователь: " + email + " / " + password);
        return new User(email, password, name);
    }

    public static User getRandomUserWithPassword(String password) {
        User user = getRandomUser();
        return new User(user.getEmail(), password, user.getName());
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
}
