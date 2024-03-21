package org.monkey.mqtx.product.service;

import org.monkey.mqtx.product.pojo.Product;

import java.util.List;

public interface ProductService {
    /**
     * 查询所有商品
     *
     * @return List<Product>
     */
    List<Product> queryAllProduct();

    /**
     * 检查库存是否充足
     * @param id 指定检查的产品
     * @param quantity 购买量
     * 购买量 <= 库存，
     * 库存充足，发送消息给 待付款队列   order:pay
     * 库存不足，发送消息给 订单失败队列 order:fail
     */
    //void checkInventory(Integer id, OrderVo orderVo);
}
