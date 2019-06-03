/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : faceverify

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 17/04/2019 08:32:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for um_t_permissions
-- ----------------------------

# DROP TABLE IF EXISTS `um_t_permissions`;
CREATE TABLE IF NOT EXISTS `um_t_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_time` bigint(20) NULL DEFAULT NULL,
  `created_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_time` bigint(20) NULL DEFAULT NULL,
  `updated_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of um_t_permissions
-- ----------------------------
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (12, 1555416830726, 'system', NULL, NULL, NULL, '添加企业', 'enterprise:add');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (11, 1555416830726, 'system', NULL, NULL, NULL, '修改企业', 'enterprise:edit');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (10, 1555416830726, 'system', NULL, NULL, NULL, '查询企业', 'enterprise:view');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (9, 1555416830726, 'system', NULL, NULL, NULL, '解/绑设备', 'device:pinless');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (8, 1555416830726, 'system', NULL, NULL, NULL, '查询设备', 'device:view');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (7, 1555416830726, 'system', NULL, NULL, NULL, '修改设备', 'device:edit');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (6, 1555416830726, 'system', NULL, NULL, NULL, '重置密码', 'user:resetPwd');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (5, 1555416830726, 'system', NULL, NULL, NULL, '修改密码', 'user:editPwd');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (4, 1555416830726, 'system', NULL, NULL, NULL, '查询用户', 'user:view');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (3, 1555416830726, 'system', NULL, NULL, NULL, '修改用户', 'user:edit');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (2, 1555416830726, 'system', NULL, NULL, NULL, '删除用户', 'user:delete');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (1, 1555416830726, 'system', NULL, NULL, NULL, '添加用户', 'user:add');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (13, 1555416830726, 'system', NULL, NULL, NULL, '删除企业', 'enterprise:delete');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (14, 1555416830726, 'system', NULL, NULL, NULL, '模糊查询企业', 'enterprise:fuzzy');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (15, 1555416830726, 'system', NULL, NULL, NULL, '查询人员', 'personnel:view');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (16, 1555416830726, 'system', NULL, NULL, NULL, '修改人员', 'personnel:edit');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (17, 1555416830726, 'system', NULL, NULL, NULL, '添加人员', 'personnel:add');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (18, 1555416830726, 'system', NULL, NULL, NULL, '删除人员', 'personnel:delete');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (19, 1555416830726, 'system', NULL, NULL, NULL, '模糊查询人员', 'personnel:fuzzy');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (20, 1555416830726, 'system', NULL, NULL, NULL, '添加设备', 'device:add');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (21, 1555416830726, 'system', NULL, NULL, NULL, '删除设备', 'device:delete');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (22, 1555416830726, 'system', NULL, NULL, NULL, '删除警务区', 'police:delete');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (23, 1555416830726, 'system', NULL, NULL, NULL, '添加警务区', 'police:add');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (24, 1555416830726, 'system', NULL, NULL, NULL, '修改警务区', 'police:update');
INSERT IGNORE INTO `um_t_permissions` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `name`, `permission`) VALUES (25, 1555416830726, 'system', NULL, NULL, NULL, '查询警务区', 'police:view');

-- ----------------------------
-- Table structure for um_t_sys_roles
-- ----------------------------
# DROP TABLE IF EXISTS `um_t_roles`;
CREATE TABLE IF NOT EXISTS `um_t_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_time` bigint(20) NULL DEFAULT NULL,
  `created_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_time` bigint(20) NULL DEFAULT NULL,
  `updated_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of um_t_sys_roles
-- ----------------------------
INSERT IGNORE INTO `um_t_roles` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `role`) VALUES (1, 1555416830726, 'system', '系统管理员', NULL, NULL, 'admin');
INSERT IGNORE INTO `um_t_roles` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `role`) VALUES (2, 1555416830726, 'system', '企业用户', NULL, NULL, 'enterpriseCustomers');
INSERT IGNORE INTO `um_t_roles` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `role`) VALUES (3, 1558435927000, 'system', '公安用户', NULL, NULL, 'policeCustomers');

-- ----------------------------
-- Table structure for um_t_users
-- ----------------------------
# DROP TABLE IF EXISTS `um_t_users`;
CREATE TABLE IF NOT EXISTS `um_t_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_time` bigint(20) NULL DEFAULT NULL,
  `created_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_time` bigint(20) NULL DEFAULT NULL,
  `updated_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_le2k4j94hfxpuc8a1523knjc5`(`account`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of um_t_users
-- ----------------------------
INSERT IGNORE INTO `um_t_users` (`id`, `created_time`, `created_user`, `description`, `updated_time`, `updated_user`, `account`, `email`, `gender`, `mobile`, `name`, `password`)VALUES (1, 1555499842340, 'system', NULL, NULL, NULL, 'admin', '14232432@qq.com', '男', '15019202295', '贾荣', '1C264A5F43254FB0AB9A85527F3B8A5D');

-- ----------------------------
-- Table structure for um_t_user_role
-- ----------------------------
# DROP TABLE IF EXISTS `um_t_user_role`;
CREATE TABLE IF NOT EXISTS `um_t_user_role` (
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`, `user_id`) USING BTREE,
  UNIQUE INDEX `UK_ireq85i1fxrbebj1849yrq70v`(`user_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of um_t_user_role
-- ----------------------------
INSERT IGNORE INTO `um_t_user_role` (`role_id`, `user_id`) VALUES (1, 1);

-- ----------------------------
-- Table structure for um_t_role_permission
-- ----------------------------
# DROP TABLE IF EXISTS `um_t_role_permission`;
CREATE TABLE  IF NOT EXISTS `um_t_role_permission`  (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`permission_id`, `role_id`) USING BTREE,
  INDEX `FK1yospai30vtxv4bd55oh6qo1b`(`role_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of um_t_role_permission
-- ----------------------------
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 1);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 2);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 3);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (2, 3);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 4);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (2, 4);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 5);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (2, 5);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 6);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 7);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 8);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 9);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 10);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (2, 10);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 11);
# INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (2, 11);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 12);
# INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (2, 12);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 13);
# INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (2, 13);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 14);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (2, 14);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 15);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (2, 15);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 16);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (2, 16);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 17);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (2, 17);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 18);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (2, 18);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 19);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (2, 19);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 20);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 21);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 22);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 23);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 24);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (1, 25);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (3, 3);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (3, 4);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (3, 5);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (3, 10);
# INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (3, 11);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (3, 14);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (3, 15);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (3, 16);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (3, 17);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (3, 18);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (3, 19);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (3, 24);
INSERT IGNORE INTO `um_t_role_permission` (`role_id`, `permission_id`) VALUES (3, 25);

SET FOREIGN_KEY_CHECKS = 1;







