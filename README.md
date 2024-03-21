#### monkey 服务端口汇总

| 服务           | 端口    | 应用名称              |
|--------------|-------|-------------------|
| eureka服务端    | 10000 | eureka            |
| gateway      | 8080  | gateway           |
| user模块       | 10020 | user              |
| 2pc-老板       | 10050 | twopc-boss        |
| 2pc-消费者A     | 10051 | twopc-customer-a  |
| 2pc-消费者B     | 10052 | twopc-customer-b  |
| 外部扩展服务       | 10060 | external-systems  |
| 分布式事务-订单     | 10071 | dist-order        |
| 分布式事务-库存     | 10072 | dist-store        |
| 产品模块         | 10080 | product           |
| rabbitmq 生产者 | 10220 | rabbitmq-sender   |
| rabbitmq 消费者 | 10221 | rabbitmq-receiver |
| mq-tx:产品     | 10230 | mq-tx-product     |
| mq-tx:订单     | 10240 | mq-tx-order       |
| mq-tx:付款     | 10250 | mq-tx-pay         |





mq-tx-product：展示商品

mq-tx-product：检查库存

新订单队列

mq-tx-order：生成新订单

付款订单队列

mq-tx-pay：完成付款

待扣库存队列

mq-tx-product：完成库存扣减

完成订单队列

mq-tx-order：完成订单


