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

-- 本地消息表 a23994b7-39d6-4926-b788-a5cbba78f3ad
DROP TABLE IF EXISTS `t_account_msg`;
CREATE TABLE `t_account_msg` (
                             `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `account_no` varchar(256) NOT NULL COMMENT '账号',
                             `message_id` varchar(64) NOT NULL COMMENT '消息id',
                             `message_status` varchar(16) NOT NULL COMMENT '消息状态',
                             `create_user` varchar(256) NOT NULL COMMENT '创建人',
                             `create_time` datetime NOT NULL COMMENT '创建时间',
                             `update_user` varchar(255) NOT NULL COMMENT '修改人',
                             `update_time` datetime NOT NULL COMMENT '修改时间',
                             `status` tinyint(1) NOT NULL COMMENT '记录状态',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 给message_id创建唯一索引
CREATE UNIQUE INDEX idx_unique_msg_id ON t_account_msg (message_id);