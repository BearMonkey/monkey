package org.monkey.mqtx.account.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.monkey.mqtx.account.pojo.Account;
import org.monkey.mqtx.account.pojo.AccountMsg;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AccountMsgMapper {

    @Insert("INSERT INTO t_account_msg(`message_id`, `message_status`, `create_user`, `create_time`, `update_user`, `update_time`, `status` )\n" +
            "VALUES(#{messageId}, #{messageStatus}, #{createUser}, NOW(), 'NA', NOW(), 1)")
    void insert(AccountMsg accountMsg);

    void update(AccountMsg accountMsg);

    @Select("select * from t_account_msg where id=#{id}")
    AccountMsg selctById(Integer id);

    @Select("select * from t_account_msg where message_id=#{messageId}")
    AccountMsg selctByAccountMsg(String accountMsg);

    @Select("select * from t_account_msg")
    List<AccountMsg> selctAll();

    @Select("select * from t_account a, t_account_msg b where a.account_no = b.account_no " +
            "and b.message_status = ''")
    List<Account> selectNotConsumes();


}
