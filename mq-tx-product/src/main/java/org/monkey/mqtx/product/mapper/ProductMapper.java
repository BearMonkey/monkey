package org.monkey.mqtx.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.monkey.mqtx.product.pojo.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProductMapper {

    @Select("select * from t_product")
    List<Product> selectAllProduct();

    @Select("select * from t_product where id=#{id}")
    Product selectProductById(Integer id);
}
