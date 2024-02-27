CREATE database if NOT EXISTS `dist-store` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `dist-store`;

SET NAMES utf8mb4;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_product`
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
                             `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `product_name` varchar(256) NOT NULL COMMENT '商品名称',
                             `product_inventory` int NOT NULL COMMENT '商品库存',
                             `price` decimal(11,2) NOT NULL COMMENT '单价',
                             `create_user` varchar(256) NOT NULL COMMENT '创建人',
                             `create_time` datetime NOT NULL COMMENT '创建时间',
                             `update_user` varchar(255) NOT NULL COMMENT '修改人',
                             `update_time` datetime NOT NULL COMMENT '修改时间',
                             `status` tinyint(1) NOT NULL COMMENT '记录状态',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;