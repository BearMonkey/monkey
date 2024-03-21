package org.monkey.product.service;

import org.monkey.product.exception.ProductException;
import org.monkey.product.pojo.Product;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);
    
    void delProduct(Product product);
    
    void updateProduct(Product product);
    
    List<Product> queryAllProduct();
    
    Product queryById(Integer id);
    
    /***
     * 修改商品库存
     * @param id 需要修改的商品id
     * @param num 减少或增加的商品数量
     */
    void deductingInventory(Integer id, int num) throws ProductException;
}
