package org.monkey.user.pojo;

import lombok.Data;

@Data
public class Book {
    private Integer id;

    private String bookName;

    private String createdBy;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
