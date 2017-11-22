/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50527
 Source Host           : localhost:3306
 Source Schema         : dev_example

 Target Server Type    : MySQL
 Target Server Version : 50527
 File Encoding         : 65001

 Date: 30/08/2017 18:52:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_used` datetime NOT NULL,
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------
INSERT INTO `persistent_logins` VALUES ('useremail', '4OKUq57LUPwTglKJOjVuBw==', 'QxL0PjyzOVeaI4BtHr2fkw==', '2017-05-15 09:58:20');
INSERT INTO `persistent_logins` VALUES ('useremail', '55KZrpBrEjH5a27t03lz+Q==', 'sRWwK+TIh5PClGFo6bJchw==', '2017-06-23 06:50:25');
INSERT INTO `persistent_logins` VALUES ('useremail', '5UhnCHGC6RLs/FfFejoKmQ==', 'lxuAkkxpXJ57c7/Z4jxboA==', '2017-05-03 10:10:59');
INSERT INTO `persistent_logins` VALUES ('useremail', '6QEOaAwfnxO37jxEkXmc9w==', 'iqZWSipcqwyY5VHDpEIipg==', '2017-05-15 10:00:44');
INSERT INTO `persistent_logins` VALUES ('useremail', 'DWL4yH6BZgY3XxRRC/Q6vA==', 'ZBbtofR230FWOxGkejylnw==', '2017-05-15 09:45:20');
INSERT INTO `persistent_logins` VALUES ('useremail', 'EQQLLoi/xBNPcQFuFWDW2A==', 'fIw2Gd1bfBjOCbaUfSM69g==', '2017-05-17 06:27:01');
INSERT INTO `persistent_logins` VALUES ('useremail', 'KzQBuW1iBdRpN1PZYITyrA==', 'qNvVTCQvW7PkXBvh3/DIbw==', '2017-05-04 02:24:51');
INSERT INTO `persistent_logins` VALUES ('useremail', 'L0+2kykKsDKvC8WF3oRHtQ==', 'vIqNtW0/sOgCh27xjOVJWQ==', '2017-05-15 13:14:09');
INSERT INTO `persistent_logins` VALUES ('useremail', 'ltU75WK8Iws3HY1hUeVT/A==', 'A18ftuFk6/Mw+0MwVXHymg==', '2017-05-04 04:13:05');
INSERT INTO `persistent_logins` VALUES ('useremail', 'sqR2MgHg+e9FqbPgyWov2w==', 'ur3ElRJZiKiRWEczbzmOuw==', '2017-05-15 09:42:02');
INSERT INTO `persistent_logins` VALUES ('useremail', 'xmB29ME+nrzTuKgUc0g5ew==', 'iVDG+MBpZ0co7KQq7huoRA==', '2017-06-23 06:34:51');

-- ----------------------------
-- Table structure for s_role
-- ----------------------------
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKpkoo0xfyi6rd0hs9ybqv92fjp`(`uid`) USING BTREE,
  CONSTRAINT `FKpkoo0xfyi6rd0hs9ybqv92fjp` FOREIGN KEY (`uid`) REFERENCES `s_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `s_role_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `s_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of s_role
-- ----------------------------
INSERT INTO `s_role` VALUES (2, 'users', 1);

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dob` date DEFAULT NULL,
  `email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of s_user
-- ----------------------------
INSERT INTO `s_user` VALUES (1, '2015-10-02', 'useremail', 'user', '4297f44b13955235245b2497399d7a93');

SET FOREIGN_KEY_CHECKS = 1;
