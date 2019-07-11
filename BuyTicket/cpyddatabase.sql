/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : cpyddatabase

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2018-06-21 17:11:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for shuttlelist
-- ----------------------------
DROP TABLE IF EXISTS `shuttlelist`;
CREATE TABLE `shuttlelist` (
  `id` bigint(20) NOT NULL,
  `s_starting` varchar(50) NOT NULL,
  `s_ending` varchar(50) NOT NULL,
  `s_date` date NOT NULL,
  `s_time` time NOT NULL,
  `capacity` smallint(6) NOT NULL,
  `seating` smallint(6) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shuttlelist
-- ----------------------------
INSERT INTO `shuttlelist` VALUES ('1', '中大', '南方学院', '2018-05-21', '20:41:01', '50', '47');

-- ----------------------------
-- Table structure for ticketlist
-- ----------------------------
DROP TABLE IF EXISTS `ticketlist`;
CREATE TABLE `ticketlist` (
  `id` bigint(20) NOT NULL,
  `shuttle_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ticketlist
-- ----------------------------
INSERT INTO `ticketlist` VALUES ('1', '1', '123', '已预定');
INSERT INTO `ticketlist` VALUES ('2', '1', '123', '已预定');
INSERT INTO `ticketlist` VALUES ('3', '1', '123', '已预定');

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
