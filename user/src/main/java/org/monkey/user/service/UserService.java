package org.monkey.user.service;

import org.monkey.user.pojo.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    List<User> getAllUser();
}
