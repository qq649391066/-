/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : booklist

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2018-06-22 09:29:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book_info
-- ----------------------------
DROP TABLE IF EXISTS `book_info`;
CREATE TABLE `book_info` (
  `book_num` varchar(25) NOT NULL,
  `book_name` varchar(25) NOT NULL,
  `book_writer` varchar(25) NOT NULL,
  `publish_house` varchar(25) NOT NULL,
  `publish_time` datetime NOT NULL,
  `book_price` int(25) NOT NULL,
  PRIMARY KEY  (`book_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book_info
-- ----------------------------
INSERT INTO `book_info` VALUES ('A123456', '白夜行', '东野圭吾', '文学出版社', '2000-01-14 00:00:00', '30');
INSERT INTO `book_info` VALUES ('C1008', 'JAVA数据结构', '李白', '中国出版社', '2018-09-05 00:00:00', '40');

-- ----------------------------
-- Table structure for userlist
-- ----------------------------
DROP TABLE IF EXISTS `userlist`;
CREATE TABLE `userlist` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `stuNum` varchar(30) NOT NULL,
  `pwd` varchar(20) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userlist
-- ----------------------------
INSERT INTO `userlist` VALUES ('123', '1', '1', '123');
