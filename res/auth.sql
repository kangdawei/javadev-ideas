DROP TABLE IF EXISTS `m_menu`;
DROP TABLE IF EXISTS `m_operation`;
DROP TABLE IF EXISTS `m_permission_group`;
DROP TABLE IF EXISTS `m_group_operation_rel`;
DROP TABLE IF EXISTS `m_user`;
DROP TABLE IF EXISTS `m_user_operation_rel`;
DROP TABLE IF EXISTS `m_user_group_rel`;



CREATE TABLE `m_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级菜单ID',
  `url` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '链接地址',
  `menu_name` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '菜单名称',
  `idx` bigint(20) DEFAULT NULL COMMENT '排序',
  `icon` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '菜单图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='菜单表';



CREATE TABLE `m_operation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  `url` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT 'RequestMapping value',
  `method` varchar(8) CHARACTER SET utf8mb4 NULL COMMENT 'RequestMapping method',
  `ope_name` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '操作名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='操作（权限）表';



CREATE TABLE `m_permission_group` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_name` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '权限组名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='权限组表';


CREATE TABLE `m_group_operation_rel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` bigint(20) NOT NULL COMMENT '权限组ID',
  `operation_id` bigint(20) NOT NULL COMMENT '操作ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='权限组操作映射表';

CREATE TABLE `m_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '密码',
  `nickname` varchar(16) CHARACTER SET utf8mb4 NOT NULL,
  `enabled` int(11) NOT NULL DEFAULT '0' COMMENT '是否启用 1：是 0：否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `m_user_operation_rel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `operation_id` bigint(20) NOT NULL COMMENT '操作ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='用户操作映射表（非必须）';

CREATE TABLE `m_user_group_rel` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` bigint(20) NOT NULL COMMENT '权限组ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='用户权限组映射表';





# insert INTO m_menu()