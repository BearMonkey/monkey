package org.monkey.product.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.monkey.product.exception.ProductException;
import org.monkey.product.mapper.ProductMapper;
import org.monkey.product.pojo.Product;
import org.monkey.product.service.ProductService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    @Autowired
    private RedissonClient redissonClient;
    
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
        redisTest();
        return productMapper.queryAllProduct();
    }
    
    @Override
    public Product queryById(Integer id) {
        return productMapper.queryById(id);
    }
    
    @Override
    @Transactional
    public void deductingInventory(Integer id, int num) throws ProductException {
        // todo: 这里要加分布式锁，单个节点先用java锁实现
        try {
            lock.lock();
            Product product = productMapper.queryById(id);
            int oldNum = product.getProductInventory();
            if (oldNum > num) {
                productMapper.updateInventory(id, oldNum - num);
            } else {
                throw new ProductException("库存不足");
            }
        } finally {
            lock.unlock();
        }
    }
    
    private void redisTest() {
        log.info("redis test");
        /*ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        stringValueOperations.set("test_key", "monkey");*/
        RLock lock = redissonClient.getLock("test_lock");
        boolean locked = false;
        try {
            locked = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (locked) {
            log.info("lock success");
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                log.info("sleep exeption");
            }
            lock.unlock();
            log.info("unlock");
        } else {
            log.info("lock failed");
        }
        /*lock.lock();
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.info("sleep exeption");
        } finally {
            lock.unlock();
        }*/
        log.info("redis test finished");
    }
}
