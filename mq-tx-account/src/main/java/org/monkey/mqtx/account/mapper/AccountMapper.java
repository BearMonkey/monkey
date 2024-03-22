package org.monkey.mqtx.account.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.monkey.mqtx.account.pojo.Account;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AccountMapper {

    @Insert("INSERT INTO t_account(`account_no`, `account_pwd`, `account_type`, `create_user`, `create_time`, `update_user`, `update_time`, `status` )\n" +
            "VALUES(#{accountNo}, #{accountPwd}, #{accountType}, #{createUser}, NOW(), 'NA', NOW(), 1)")
    void insert(Account account);

    void update(Account account);

    @Select("select * from t_account where id=#{id}")
    Account selctById(Integer id);

    @Select("select * from t_account where account_no=#{accountNo}")
    List<Account> selctByAccountNo(String accountNo);

    @Select("select * from t_account")
    List<Account> selctAll();


}
