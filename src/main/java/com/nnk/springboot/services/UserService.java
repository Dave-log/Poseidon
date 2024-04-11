package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;

public interface UserService {

    User getCurrentUser();
    User getUser(Integer id);
    User getUser(String username);
    Iterable<User> getUsers();
    void save(User user);
    void delete(User user);
}
