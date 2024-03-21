package org.monkey.product.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.monkey.product.pojo.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ProductMapper {
    
    @Insert("INSERT INTO t_product(`product_name`, `product_inventory`, `price`, `create_user`, `create_time`, `update_user`, `update_time`, `status`) \n" +
            "VALUES(#{productName}, #{productInventory}, #{price}, #{createUser}, NOW(), 'NA', NOW(), 1)")
    void addProduct(Product product);
    
    @Update("UPDATE t_product SET `status`=0 WHERE id=#{id}")
    void delProduct(Product product);
    
    // 使用xml文件实现
    //@Update("UPDATE t_product SET `status`=0 WHERE id=#{id}")
    void updateProduct(Product product);
    
    @Select("SELECT * FROM t_product")
    List<Product> queryAllProduct();
    
    @Select("SELECT * FROM t_product WHERE `id`=#{id}")
    Product queryById(Integer id);
    
    /***
     * 修改商品库存数量
     * @param id 需要修改的商品id
     * @param num 新的库存数量
     */
    @Update("UPDATE t_product SET `product_inventory`=#{num} WHERE `id`=#{id}")
    void updateInventory(Integer id, int num);
}
