package org.monkey.mqtx.product.service.impl;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.monkey.common.utils.JsonUtil;
import org.monkey.mqtx.common.vo.OrderVo;
import org.monkey.mqtx.common.vo.ProductVo;
import org.monkey.mqtx.product.mapper.ProductLockMapper;
import org.monkey.mqtx.product.mapper.ProductMapper;
import org.monkey.mqtx.product.pojo.Product;
import org.monkey.mqtx.product.pojo.ProductLock;
import org.monkey.mqtx.product.service.ProductService;
import org.monkey.mqtx.rabbitmq.service.config.RabbitMqConfig;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ProductServiceImppl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductLockMapper productLockMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public List<Product> queryAllProduct() {
        log.info("select all products");
        return productMapper.selectAllProduct();
    }

    @RabbitListener(queues = RabbitMqConfig.ORDER_MODIFY_INVENTORY_ROUTING_KEY)
    public void modifyInventory(String msg, Channel channel, Message message) {
        log.info("收到 order:modify_inventory 队列的消息，完成库存扣减, msg={}, message={}", msg, message);
        OrderVo orderVo = JsonUtil.strToObj(msg, OrderVo.class);
        if (null == orderVo) {
            log.error("从 order:modify_inventory 队列获取到的订单信息为空");
            return;
        }

        // 检查库存是否充足
    }

    /**
     * 商品加锁
     *
     * @param msg 订单信息
     * @param channel rabbitmq channel
     * @param message mq消息
     */
    @RabbitListener(queues = RabbitMqConfig.ORDER_NEW_QUEUE)
    public void lockProduct(String msg, Channel channel, Message message) {
        log.info("收到 order:new 队列的消息，完成商品加锁, msg={}, message={}", msg, message);
        OrderVo orderVo = JsonUtil.strToObj(msg, OrderVo.class);
        if (null == orderVo) {
            log.error("从 order:new 队列获取到的订单信息为空");
            return;
        }

        boolean locked = lockProduct(orderVo);
        if (locked) {
            log.info("锁库成功");
            rabbitTemplate.convertAndSend(
                    RabbitMqConfig.ORDER_DIRECT_EXCHANGE,
                    RabbitMqConfig.ORDER_LOCKED_ROUTING_KEY,
                    orderVo);
        } else {
            log.info("锁库失败");
            // todo: 发消息到 order:fail 队列
            rabbitTemplate.convertAndSend(
                    RabbitMqConfig.ORDER_DIRECT_EXCHANGE,
                    RabbitMqConfig.ORDER_FAIL_ROUTING_KEY,
                    orderVo);
        }
    }

    private boolean lockProduct(OrderVo orderVo) {
        Integer productId = orderVo.getProduct().getProductId();
        Integer userId = orderVo.getCreateUserId();
        // 获取分布式锁，先查询每一条商品的数量，判断 数量都充足时，锁定商品，锁定完成后释放分布式锁
        String dist_lock_key = "product_lock:" + productId;
        RLock lock = redissonClient.getLock(dist_lock_key);
        boolean locked = false;
        try {
            locked = lock.tryLock(2, TimeUnit.SECONDS); // 尝试获取锁，2s内获取到锁返回true
        } catch (InterruptedException e) {
            log.error("获取分布式锁异常：{}, ", dist_lock_key, e);
        }
        if (!locked) {
            log.info("获取分布式锁: {} 失败", dist_lock_key);
            return false;
        }

        ProductLock productLock = productLockMapper.selectByProductId(productId);
        if (productLock == null) {
            productLockMapper.insert(productId, userId, orderVo.getCreateUser());
            lock.unlock();
            return true;
        }
        if (!userId.equals(productLock.getUserId())) {
            log.info("商品已被其他用户锁定锁定, 商品id：{}, 锁定用户：{}, 非当前用户：{}",
                    productId, productLock.getUserId(), userId);
            lock.unlock();
            return false;
        }
        log.info("商品已被当前用户锁定, 商品id：{}, 锁定用户：{}, ", productId, userId);
        lock.unlock();
        return true;
    }


}
