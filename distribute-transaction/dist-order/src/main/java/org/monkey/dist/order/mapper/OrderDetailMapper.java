package org.monkey.dist.order.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.monkey.dist.order.pojo.OrderDetail;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    @Insert("INSERT INTO t_order_detail(`order_no`, `product_id`, `product_name`, `number`, `price`, `create_user`, `create_time`, `update_user`, `update_time`, `status`)\n" +
            "VALUES(#{orderNo}, #{productId}, #{productName}, #{number}, #{price}, #{createUser}, NOW(), 'NA', NOW(), 1)")
    void add(OrderDetail orderDetail);

    void batchInsert(List<OrderDetail> orderDetails);

    @Update("UPDATE t_order_detail SET `status`=0 WHERE id=#{id}")
    void del(OrderDetail orderDetail);

    // 使用xml文件实现
    //@Update("UPDATE t_order_detail SET `status`=0 WHERE id=#{id}")
    void update(OrderDetail orderDetail);

    @Select("SELECT * FROM t_order_detail")
    List<OrderDetail> queryAll();

    @Select("SELECT * FROM t_order_detail WHERE `id`=#{id}")
    OrderDetail queryById(Integer id);

    @Select("SELECT * FROM t_order_detail WHERE `order_no`=#{orderNo}")
    List<OrderDetail> queryByOrderNo(String orderNo);
}
