package org.monkey.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.monkey.user.pojo.User;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user(`user_name`, `password`) " +
            "values(#{userName}, #{password})")
    public void addUser(User user);

    @Select("select * from user")
    List<User> findAllUser();
}
