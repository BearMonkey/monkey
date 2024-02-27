package org.monkey.user.service;

import org.monkey.user.pojo.User;

import java.util.List;

public interface BookService {
    void addBook(String name) throws Exception;

    void del(Integer id);
}
