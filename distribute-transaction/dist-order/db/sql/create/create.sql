CREATE database if NOT EXISTS `dist-order` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `dist-order`;

SET NAMES utf8mb4;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
                           `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `order_no` varchar(256) NOT NULL DEFAULT '' COMMENT '订单编号',
                           `totle_price` decimal(11,2) NOT NULL COMMENT '总价',
                           `create_user` varchar(256) NOT NULL COMMENT '创建人',
                           `create_time` datetime NOT NULL COMMENT '创建时间',
                           `update_user` varchar(255) NOT NULL COMMENT '修改人',
                           `update_time` datetime NOT NULL COMMENT '修改时间',
                           `status` tinyint(1) NOT NULL COMMENT '记录状态',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for `t_order_detail`
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail`;
CREATE TABLE `t_order_detail` (
                           `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `order_no` varchar(256) NOT NULL DEFAULT '' COMMENT '订单编号',
                           `product_id` int NOT NULL COMMENT '商品id',
                           `product_name` varchar(256) NOT NULL COMMENT '商品名称',
                           `number` int NOT NULL COMMENT '购买件数',
                           `price` decimal(11,2) NOT NULL COMMENT '单价',
                           `create_user` varchar(256) NOT NULL COMMENT '创建人',
                           `create_time` datetime NOT NULL COMMENT '创建时间',
                           `update_user` varchar(255) NOT NULL COMMENT '修改人',
                           `update_time` datetime NOT NULL COMMENT '修改时间',
                           `status` tinyint(1) NOT NULL COMMENT '记录状态',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;