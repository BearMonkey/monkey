package org.monkey.dist.store.service.impl;

import org.monkey.dist.store.exception.StoreException;
import org.monkey.dist.store.mapper.ProductMapper;
import org.monkey.dist.store.pojo.Product;
import org.monkey.dist.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    private final Lock lock = new ReentrantLock();

    @Override
    public void addProduct(Product product) {
        productMapper.addProduct(product);
    }

    @Override
    public void delProduct(Product product) {
        productMapper.delProduct(product);
    }

    @Override
    public void updateProduct(Product product) {
        productMapper.updateProduct(product);
    }

    @Override
    public List<Product> queryAllProduct() {
        return productMapper.queryAllProduct();
    }

    @Override
    public Product queryById(Integer id) {
        return productMapper.queryById(id);
    }

    @Override
    @Transactional
    public void deductingInventory(Integer id, int num) throws StoreException {
        // todo: 这里要加分布式锁，单个节点先用java锁实现
        try {
            lock.lock();
            Product product = productMapper.queryById(id);
            int oldNum = product.getProductInventory();
            if (oldNum > num) {
                productMapper.updateInventory(id, oldNum - num);
            } else {
                throw new StoreException("库存不足");
            }
        } finally {
            lock.unlock();
        }
    }
}
