/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : clothing

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 22/06/2022 11:31:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for commodity
-- ----------------------------
DROP TABLE IF EXISTS `commodity`;
CREATE TABLE `commodity`  (
  `id` int(0) NOT NULL,
  `comm_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `comm_color` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `comm_size` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `comm_number` int(0) NOT NULL,
  `is_deleted` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodity
-- ----------------------------
INSERT INTO `commodity` VALUES (29845387, '夹克衫', '蓝色', 'XL', 10, 0);
INSERT INTO `commodity` VALUES (29845389, '夹克衫', '紫色', 'XL', 40, 0);
INSERT INTO `commodity` VALUES (34587360, '短袖', '粉色', 'XXL', 20, 0);
INSERT INTO `commodity` VALUES (34587960, '短袖', '黄色', 'L', 60, 0);
INSERT INTO `commodity` VALUES (35673865, '牛仔裤', '白色', 'L', 32, 0);
INSERT INTO `commodity` VALUES (37640783, '秋衣', '白色', 'M', 30, 0);
INSERT INTO `commodity` VALUES (45356234, '短袖', '白色', 'XXL', 40, 0);
INSERT INTO `commodity` VALUES (45934523, '长袖', '黄色', 'L', 50, 0);
INSERT INTO `commodity` VALUES (46378543, '皮衣', '黑色', 'XL', 40, 0);
INSERT INTO `commodity` VALUES (65387454, '皮鞋', '黑色', '41', 40, 0);
INSERT INTO `commodity` VALUES (75849563, '短袜', '白色', '均码', 30, 0);
INSERT INTO `commodity` VALUES (89543975, '棉袄', '绿色', 'M', 50, 0);
INSERT INTO `commodity` VALUES (94327845, '卫衣', '白色', 'XXL', 40, 0);
INSERT INTO `commodity` VALUES (98434276, '棉袜', '白色', 'S', 110, 0);
INSERT INTO `commodity` VALUES (98547356, '运动鞋', '白色', '37', 30, 0);
INSERT INTO `commodity` VALUES (98547834, '短裤', '红色', 'S', 50, 0);

-- ----------------------------
-- Table structure for inbound
-- ----------------------------
DROP TABLE IF EXISTS `inbound`;
CREATE TABLE `inbound`  (
  `id` bigint(0) NOT NULL,
  `storage` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `date` date NOT NULL,
  `operator_id` int(0) NOT NULL,
  `source` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  INDEX `operator_id`(`operator_id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  CONSTRAINT `inbound_ibfk_1` FOREIGN KEY (`operator_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inbound
-- ----------------------------
INSERT INTO `inbound` VALUES (1532591123712057346, '一号仓库', '2022-01-02', 1, '珍珠港', '123', 1);
INSERT INTO `inbound` VALUES (1532591749468659714, '一号仓库', '2022-01-02', 1, '珍珠港', '', 1);
INSERT INTO `inbound` VALUES (1532706572110090242, '二号仓库', '2022-06-03', 1, '第一服装厂', '第一服装厂', 0);
INSERT INTO `inbound` VALUES (1532708355561705473, '三号仓库', '2022-06-03', 1, '第一服装厂', '', 0);
INSERT INTO `inbound` VALUES (1533004176064737281, '二号仓库', '2022-06-04', 1, '嘎巴脆公司', '品质好衣服', 0);
INSERT INTO `inbound` VALUES (1533369392409595906, '三号仓库', '2022-06-05', 1, '江南皮革厂', '江南皮革厂倒闭了，便宜进货', 0);
INSERT INTO `inbound` VALUES (1534443654515994625, '二号仓库', '2022-06-08', 1, '江南第二皮革厂', '', 0);

-- ----------------------------
-- Table structure for inbound_detail
-- ----------------------------
DROP TABLE IF EXISTS `inbound_detail`;
CREATE TABLE `inbound_detail`  (
  `id` bigint(0) NOT NULL,
  `comm_id` int(0) NOT NULL,
  `comm_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `comm_color` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `comm_size` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `comm_number` int(0) NOT NULL,
  `is_deleted` tinyint(1) NULL DEFAULT 0,
  INDEX `comm_id`(`comm_id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  CONSTRAINT `inbound_detail_ibfk_1` FOREIGN KEY (`id`) REFERENCES `inbound` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inbound_detail
-- ----------------------------
INSERT INTO `inbound_detail` VALUES (1532591123712057346, 29845387, '夹克衫', '蓝色', 'XL', 10, 1);
INSERT INTO `inbound_detail` VALUES (1532591749468659714, 29845389, '夹克衫', '紫色', 'XL', 10, 1);
INSERT INTO `inbound_detail` VALUES (1532706572110090242, 98547834, '短裤', '红色', 'S', 20, 0);
INSERT INTO `inbound_detail` VALUES (1532708355561705473, 98547834, '短裤', '红色', 'S', 10, 0);
INSERT INTO `inbound_detail` VALUES (1532708355561705473, 98547356, '运动鞋', '白色', '37', 10, 1);
INSERT INTO `inbound_detail` VALUES (1533004176064737281, 94327845, '卫衣', '白色', 'XXL', 10, 0);
INSERT INTO `inbound_detail` VALUES (1533004176064737281, 98547834, '短裤', '红色', 'S', 10, 0);
INSERT INTO `inbound_detail` VALUES (1533004176064737281, 29845387, '夹克衫', '蓝色', 'XL', 10, 0);
INSERT INTO `inbound_detail` VALUES (1533004176064737281, 75849563, '短袜', '白色', '均码', 10, 0);
INSERT INTO `inbound_detail` VALUES (1533369392409595906, 65387454, '皮鞋', '黑色', '41', 20, 0);
INSERT INTO `inbound_detail` VALUES (1533369392409595906, 46378543, '皮衣', '黑色', 'XL', 20, 0);
INSERT INTO `inbound_detail` VALUES (1533369392409595906, 94327845, '卫衣', '白色', 'XXL', 10, 0);
INSERT INTO `inbound_detail` VALUES (1532591123712057346, 46378543, '皮衣', '黑色', 'XL', 20, 1);
INSERT INTO `inbound_detail` VALUES (1532591749468659714, 75849563, '短袜', '白色', '均码', 20, 1);
INSERT INTO `inbound_detail` VALUES (1532591749468659714, 65387454, '皮鞋', '黑色', '41', 20, 1);
INSERT INTO `inbound_detail` VALUES (1532591749468659714, 65387454, '皮鞋', '黑色', '41', 30, 1);
INSERT INTO `inbound_detail` VALUES (1534443654515994625, 98547356, '运动鞋', '白色', '37', 40, 0);
INSERT INTO `inbound_detail` VALUES (1534443654515994625, 46378543, '皮衣', '黑色', 'XL', 40, 0);
INSERT INTO `inbound_detail` VALUES (1534443654515994625, 65387454, '皮鞋', '黑色', '41', 40, 0);
INSERT INTO `inbound_detail` VALUES (1534443654515994625, 29845389, '夹克衫', '紫色', 'XL', 40, 0);
INSERT INTO `inbound_detail` VALUES (1532706572110090242, 75849563, '短袜', '白色', '均码', 20, 0);

-- ----------------------------
-- Table structure for outbound
-- ----------------------------
DROP TABLE IF EXISTS `outbound`;
CREATE TABLE `outbound`  (
  `id` bigint(0) NOT NULL,
  `date` date NOT NULL,
  `operator_id` int(0) NOT NULL,
  `recipient` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `recipient_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  INDEX `operator_id`(`operator_id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  CONSTRAINT `outbound_ibfk_1` FOREIGN KEY (`operator_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of outbound
-- ----------------------------
INSERT INTO `outbound` VALUES (1534176373785460737, '2022-06-07', 1, '王五', '13788566543', '', 0);
INSERT INTO `outbound` VALUES (1534434365906636801, '2022-06-14', 1, '张三', '13788940541', '', 0);
INSERT INTO `outbound` VALUES (1534435706230034434, '2022-06-08', 1, '李四', '13578548765', '品质好物', 1);

-- ----------------------------
-- Table structure for outbound_detail
-- ----------------------------
DROP TABLE IF EXISTS `outbound_detail`;
CREATE TABLE `outbound_detail`  (
  `id` bigint(0) NOT NULL,
  `comm_id` int(0) NOT NULL,
  `comm_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `comm_color` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `comm_size` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `comm_number` int(0) NOT NULL,
  `is_deleted` tinyint(1) NULL DEFAULT 0,
  INDEX `comm_id`(`comm_id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  CONSTRAINT `outbound_detail_ibfk_1` FOREIGN KEY (`id`) REFERENCES `outbound` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of outbound_detail
-- ----------------------------
INSERT INTO `outbound_detail` VALUES (1534176373785460737, 98547834, '短裤', '红色', 'S', 20, 0);
INSERT INTO `outbound_detail` VALUES (1534176373785460737, 98434276, '棉袜', '白色', 'S', 10, 1);
INSERT INTO `outbound_detail` VALUES (1534434365906636801, 98434276, '棉袜', '白色', 'S', 20, 1);
INSERT INTO `outbound_detail` VALUES (1534434365906636801, 98434276, '棉袜', '白色', 'S', 10, 1);
INSERT INTO `outbound_detail` VALUES (1534435706230034434, 98434276, '棉袜', '白色', 'S', 10, 1);
INSERT INTO `outbound_detail` VALUES (1534434365906636801, 98434276, '棉袜', '白色', 'S', 20, 1);
INSERT INTO `outbound_detail` VALUES (1534434365906636801, 98547356, '运动鞋', '白色', '37', 10, 0);
INSERT INTO `outbound_detail` VALUES (1534434365906636801, 98434276, '棉袜', '白色', 'S', 10, 1);

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `comm_id` int(0) NOT NULL,
  `model_type` int(0) NOT NULL COMMENT '改变渠道，1为货品模块，2为库存模块',
  `operator` int(0) NOT NULL,
  `operation_type` int(0) NOT NULL COMMENT '操作类型，1为加，2为减，3删除，4新增',
  `number` int(0) NULL DEFAULT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `comm_id`(`comm_id`) USING BTREE,
  INDEX `operator`(`operator`) USING BTREE,
  CONSTRAINT `record_ibfk_1` FOREIGN KEY (`comm_id`) REFERENCES `commodity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `record_ibfk_2` FOREIGN KEY (`operator`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 96 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (1, 29845387, 1, 1, 4, 20, '2022-05-25');
INSERT INTO `record` VALUES (2, 37640783, 1, 1, 4, 30, '2022-05-30');
INSERT INTO `record` VALUES (3, 29845387, 1, 1, 1, 10, '2022-05-30');
INSERT INTO `record` VALUES (4, 29845387, 1, 1, 2, 10, '2022-05-30');
INSERT INTO `record` VALUES (6, 29845387, 1, 1, 1, 1, '2022-05-30');
INSERT INTO `record` VALUES (7, 29845387, 1, 1, 2, 1, '2022-05-30');
INSERT INTO `record` VALUES (8, 29845387, 1, 1, 1, 10, '2022-06-01');
INSERT INTO `record` VALUES (9, 29845387, 2, 1, 1, 10, '2022-06-03');
INSERT INTO `record` VALUES (11, 29845389, 2, 1, 4, 10, '2022-06-03');
INSERT INTO `record` VALUES (13, 98547834, 2, 1, 1, 10, '2022-06-03');
INSERT INTO `record` VALUES (15, 98547834, 2, 1, 1, 10, '2022-06-03');
INSERT INTO `record` VALUES (16, 98547356, 2, 1, 4, 10, '2022-06-03');
INSERT INTO `record` VALUES (17, 94327845, 2, 1, 1, 10, '2022-06-04');
INSERT INTO `record` VALUES (18, 98547834, 2, 1, 1, 10, '2022-06-04');
INSERT INTO `record` VALUES (19, 29845387, 2, 1, 1, 1, '2022-06-05');
INSERT INTO `record` VALUES (21, 29845387, 2, 1, 2, 1, '2022-06-05');
INSERT INTO `record` VALUES (22, 29845387, 2, 1, 1, 10, '2022-06-05');
INSERT INTO `record` VALUES (23, 75849563, 2, 1, 4, 10, '2022-06-05');
INSERT INTO `record` VALUES (24, 29845387, 2, 1, 2, 10, '2022-06-05');
INSERT INTO `record` VALUES (25, 65387454, 2, 1, 4, 20, '2022-06-05');
INSERT INTO `record` VALUES (26, 46378543, 2, 1, 4, 20, '2022-06-05');
INSERT INTO `record` VALUES (27, 94327845, 2, 1, 1, 10, '2022-06-05');
INSERT INTO `record` VALUES (28, 46378543, 2, 1, 1, 20, '2022-06-05');
INSERT INTO `record` VALUES (29, 46378543, 2, 1, 1, 10, '2022-06-05');
INSERT INTO `record` VALUES (30, 46378543, 2, 1, 2, 10, '2022-06-05');
INSERT INTO `record` VALUES (31, 46378543, 2, 1, 1, 10, '2022-06-05');
INSERT INTO `record` VALUES (32, 46378543, 2, 1, 2, 10, '2022-06-05');
INSERT INTO `record` VALUES (33, 98547356, 2, 1, 2, 10, '2022-06-05');
INSERT INTO `record` VALUES (34, 29845389, 2, 1, 2, 10, '2022-06-05');
INSERT INTO `record` VALUES (35, 75849563, 2, 1, 1, 20, '2022-06-05');
INSERT INTO `record` VALUES (36, 65387454, 2, 1, 1, 20, '2022-06-05');
INSERT INTO `record` VALUES (37, 65387454, 2, 1, 2, 20, '2022-06-05');
INSERT INTO `record` VALUES (38, 65387454, 2, 1, 1, 20, '2022-06-05');
INSERT INTO `record` VALUES (39, 65387454, 2, 1, 1, 10, '2022-06-05');
INSERT INTO `record` VALUES (40, 46378543, 2, 1, 2, 20, '2022-06-05');
INSERT INTO `record` VALUES (41, 75849563, 2, 1, 2, 20, '2022-06-05');
INSERT INTO `record` VALUES (42, 65387454, 2, 1, 2, 30, '2022-06-05');
INSERT INTO `record` VALUES (46, 98547834, 2, 1, 2, 10, '2022-06-07');
INSERT INTO `record` VALUES (47, 98434276, 2, 1, 2, 10, '2022-06-07');
INSERT INTO `record` VALUES (48, 34587960, 2, 1, 2, 10, '2022-06-08');
INSERT INTO `record` VALUES (49, 98434276, 2, 1, 2, 10, '2022-06-08');
INSERT INTO `record` VALUES (50, 98434276, 2, 1, 2, 10, '2022-06-08');
INSERT INTO `record` VALUES (51, 98434276, 2, 1, 2, 10, '2022-06-08');
INSERT INTO `record` VALUES (52, 98434276, 2, 1, 2, 10, '2022-06-08');
INSERT INTO `record` VALUES (53, 98434276, 2, 1, 1, 20, '2022-06-08');
INSERT INTO `record` VALUES (54, 98547356, 2, 1, 1, 40, '2022-06-08');
INSERT INTO `record` VALUES (55, 46378543, 2, 1, 1, 40, '2022-06-08');
INSERT INTO `record` VALUES (56, 65387454, 2, 1, 1, 40, '2022-06-08');
INSERT INTO `record` VALUES (57, 29845389, 2, 1, 1, 40, '2022-06-08');
INSERT INTO `record` VALUES (58, 98434276, 2, 1, 1, 10, '2022-06-08');
INSERT INTO `record` VALUES (60, 98434276, 2, 1, 2, 10, '2022-06-08');
INSERT INTO `record` VALUES (61, 98434276, 2, 1, 2, 10, '2022-06-08');
INSERT INTO `record` VALUES (62, 98434276, 2, 1, 1, 20, '2022-06-08');
INSERT INTO `record` VALUES (63, 98434276, 2, 1, 1, 10, '2022-06-08');
INSERT INTO `record` VALUES (64, 98547356, 2, 1, 2, 10, '2022-06-08');
INSERT INTO `record` VALUES (65, 98434276, 2, 1, 2, 10, '2022-06-08');
INSERT INTO `record` VALUES (66, 98434276, 2, 1, 1, 10, '2022-06-08');
INSERT INTO `record` VALUES (67, 98434276, 2, 1, 1, 10, '2022-06-11');
INSERT INTO `record` VALUES (68, 75849563, 2, 1, 1, 20, '2022-06-11');
INSERT INTO `record` VALUES (69, 98547834, 2, 1, 1, 10, '2022-06-14');
INSERT INTO `record` VALUES (70, 98547834, 2, 1, 2, 10, '2022-06-14');
INSERT INTO `record` VALUES (71, 98547834, 2, 1, 1, 10, '2022-06-14');
INSERT INTO `record` VALUES (72, 98547834, 2, 1, 2, 10, '2022-06-14');
INSERT INTO `record` VALUES (73, 98547834, 2, 1, 1, 10, '2022-06-14');
INSERT INTO `record` VALUES (74, 98547834, 2, 1, 1, 30, '2022-06-14');
INSERT INTO `record` VALUES (75, 98547834, 2, 1, 2, 40, '2022-06-14');
INSERT INTO `record` VALUES (76, 98547834, 2, 1, 1, 10, '2022-06-14');
INSERT INTO `record` VALUES (77, 98547834, 2, 1, 2, 10, '2022-06-14');
INSERT INTO `record` VALUES (78, 98547834, 2, 1, 1, 10, '2022-06-14');
INSERT INTO `record` VALUES (79, 98547834, 2, 1, 2, 10, '2022-06-14');
INSERT INTO `record` VALUES (80, 98547834, 2, 1, 1, 10, '2022-06-14');
INSERT INTO `record` VALUES (81, 98547834, 2, 1, 2, 10, '2022-06-14');
INSERT INTO `record` VALUES (82, 98547834, 2, 1, 1, 10, '2022-06-14');
INSERT INTO `record` VALUES (83, 98547834, 2, 1, 2, 10, '2022-06-14');
INSERT INTO `record` VALUES (84, 98547834, 2, 1, 1, 10, '2022-06-14');
INSERT INTO `record` VALUES (85, 98547834, 2, 1, 2, 10, '2022-06-14');
INSERT INTO `record` VALUES (86, 98547834, 2, 1, 1, 10, '2022-06-14');
INSERT INTO `record` VALUES (87, 98547834, 2, 1, 2, 10, '2022-06-14');
INSERT INTO `record` VALUES (88, 98547834, 2, 1, 1, 10, '2022-06-14');
INSERT INTO `record` VALUES (89, 98547834, 2, 1, 2, 10, '2022-06-14');
INSERT INTO `record` VALUES (90, 98547834, 2, 1, 1, 10, '2022-06-14');
INSERT INTO `record` VALUES (91, 98547834, 2, 1, 2, 10, '2022-06-14');
INSERT INTO `record` VALUES (92, 98547834, 2, 1, 1, 10, '2022-06-14');
INSERT INTO `record` VALUES (93, 98547834, 2, 1, 2, 10, '2022-06-14');
INSERT INTO `record` VALUES (94, 98547834, 2, 1, 1, 10, '2022-06-14');
INSERT INTO `record` VALUES (95, 98547834, 2, 1, 2, 10, '2022-06-21');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_login` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_password` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_role` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'USER',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '1为删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '1378894054@qq.com', '纣纣', 'e10adc3949ba59abbe56e057f20f883e', 'ADMIN', 0);
INSERT INTO `user` VALUES (2, '123@163.com', '张三', 'e10adc3949ba59abbe56e057f20f883e', 'USER', 0);
INSERT INTO `user` VALUES (4, '1234@qq.com', '李四', 'e10adc3949ba59abbe56e057f20f883e', 'ADMIN', 0);
INSERT INTO `user` VALUES (6, '1768056@qq.com', '游桑', 'e10adc3949ba59abbe56e057f20f883e', 'USER', 0);
INSERT INTO `user` VALUES (7, '156@qq.com', '米西', 'e10adc3949ba59abbe56e057f20f883e', 'USER', 0);
INSERT INTO `user` VALUES (8, 'zj1378894054@163.com', '村夫', 'e10adc3949ba59abbe56e057f20f883e', 'USER', 0);
INSERT INTO `user` VALUES (9, '2278807@163.com', '孙猴子', 'e10adc3949ba59abbe56e057f20f883e', 'USER', 0);
INSERT INTO `user` VALUES (10, '2278807141@163.com', '小日子', 'e10adc3949ba59abbe56e057f20f883e', 'USER', 0);
INSERT INTO `user` VALUES (11, '1378853@qq.com', '周就', 'e10adc3949ba59abbe56e057f20f883e', 'USER', 0);

SET FOREIGN_KEY_CHECKS = 1;
