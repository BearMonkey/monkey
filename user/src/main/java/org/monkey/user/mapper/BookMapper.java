package org.monkey.user.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.monkey.user.pojo.Book;
import org.monkey.user.pojo.User;

import java.util.List;

@Mapper
public interface BookMapper {
    @Insert("insert into book(book_name, created_by) values (#{bookName}, #{createdBy})")
    int insert(Book book);

    void batchInsert(List<Book> list);

    @Select("select count(*) as cnt from book where created_by=#{createdBy}")
    int findAll(String createdBy);

    @Delete("delete from book where id=#{id}")
    int delete(Integer id);
}
