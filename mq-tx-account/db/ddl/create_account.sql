DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
                             `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `account_no` varchar(256) NOT NULL COMMENT '账号',
                             `account_pwd` varchar(256) NOT NULL COMMENT '密码',
                             `account_type` varchar(16) NOT NULL COMMENT '账号类型',
                             `create_user` varchar(256) NOT NULL COMMENT '创建人',
                             `create_time` datetime NOT NULL COMMENT '创建时间',
                             `update_user` varchar(255) NOT NULL COMMENT '修改人',
                             `update_time` datetime NOT NULL COMMENT '修改时间',
                             `status` tinyint(1) NOT NULL COMMENT '记录状态',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;