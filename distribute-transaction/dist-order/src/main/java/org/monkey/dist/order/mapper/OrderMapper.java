package org.monkey.dist.order.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.monkey.dist.order.pojo.Order;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO t_order(`order_no`, `totle_price`, `create_user`, `create_time`, `update_user`, `update_time`, `status` )\n" +
            "VALUES(#{orderNo}, #{totlePrice}, #{createUser}, NOW(), 'NA', NOW(), 1)")
    void add(Order order);

    @Update("UPDATE t_order SET `status`=0 WHERE id=#{id}")
    void del(Order order);

    // 使用xml文件实现
    //@Update("UPDATE t_order SET `status`=0 WHERE id=#{id}")
    void update(Order order);

    @Select("SELECT * FROM t_order")
    List<Order> queryAll();

    @Select("SELECT * FROM t_order WHERE `id`=#{id}")
    Order queryById(Integer id);

    @Select("SELECT * FROM t_order WHERE `order_no`=#{orderNo}")
    Order queryByOrderNo(String orderNo);
}
