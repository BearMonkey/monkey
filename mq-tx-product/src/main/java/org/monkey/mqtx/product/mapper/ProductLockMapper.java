package org.monkey.mqtx.product.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.monkey.mqtx.product.pojo.ProductLock;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProductLockMapper {

    @Select("select * from t_product_lock where product_id=#{productId} and status=1")
    ProductLock selectByProductId(Integer productId);


    @Insert("insert into t_product_lock(`user_id`, `product_id`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) " +
            "values(#{userId}, #{productId}, #{createUser}, NOW(), 'NA', NOW(), 1)")
    // INSERT INTO t_product(`product_name`, `product_inventory`, `price`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) \n" +
    // "VALUES(#{productName}, #{productInventory}, #{price}, #{createUser}, NOW(), 'NA', NOW(), 1)"
    void insert(Integer productId, Integer userId, String createUser);
}
