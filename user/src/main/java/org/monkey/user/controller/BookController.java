package org.monkey.user.controller;

import org.monkey.user.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/{name}")
    public String addBook(@PathVariable("name") String name) throws Exception {
        bookService.addBook(name);
        return "";
    }

    @PutMapping("/{id}")
    public String delBook(@PathVariable("id") Integer id) {
        bookService.del(id);
        return "del success";
    }
}
