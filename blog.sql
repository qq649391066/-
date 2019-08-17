/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2019-08-17 18:41:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article_info
-- ----------------------------
DROP TABLE IF EXISTS `article_info`;
CREATE TABLE `article_info` (
  `articleId` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `content_text` varchar(255) NOT NULL COMMENT '文章的简介',
  `cover` varchar(100) NOT NULL COMMENT '文章的图片',
  `view_count` int(11) NOT NULL COMMENT '浏览数量',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` int(11) NOT NULL COMMENT '文章状态，是否在回收站',
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`articleId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of article_info
-- ----------------------------
INSERT INTO `article_info` VALUES ('3', '习近平：确保主题教育取得扎扎实实的成效', '测试回显<img src=\"/upload/2019/08/14/c7587ec5-f92e-4a96-99d9-5c3e175168ea.jpg\">', '测试回显', '/upload/2019/08/14/b234cc91-beb8-49bf-9e7a-6389af0c2c37.jpg', '1', '2019-08-14 15:32:10', '1', '5');
INSERT INTO `article_info` VALUES ('4', '重庆球迷', '这里好像出了问题<img src=\"/upload/2019/08/14/48b2f752-6496-4444-ad4c-bbec91242289.jpg\">', '这里好像出了问题', '/upload/2019/08/14/05b087bf-409f-4f4f-a0ee-04794a30d05d.jpg', '1', '2019-08-14 19:05:11', '0', '5');
INSERT INTO `article_info` VALUES ('5', '新闻资讯网', 'hahahahahahahahahahahah', 'hahahahahahahahahahahah', '/upload/2019/08/14/4520de7f-b3d7-44f3-85f6-bf80cd4c7d3a.jpg', '1', '2019-08-14 21:21:48', '1', '5');
INSERT INTO `article_info` VALUES ('6', '新闻资讯网', 'hahahahahahahahahahahahdsadasd ', 'hahahahahahahahahahahahdsadasd ', '/upload/2019/08/14/5a31f2bd-845e-49ff-9035-6052737479d3.jpg', '1', '2019-08-14 22:08:00', '1', '4');
INSERT INTO `article_info` VALUES ('7', 'ssm整合文档', '8.14号的测试，真的累<img src=\"/upload/2019/08/14/ab6278bd-8ee0-440c-a075-aaa52974c2ab.jpg\"> <div><br></div><div><br></div><div>测试一下修改功能啊</div>', '8.14号的测试，真的累 测试一下修改功能啊', '/upload/2019/08/14/270def62-41c2-416e-a3f8-3c14a671cb71.jpg', '2', '2019-08-14 22:11:39', '1', '1');
INSERT INTO `article_info` VALUES ('8', '15号的文章测试', '这是测试用的哦！！~~~15号的测试', '这是测试用的哦！！~~~15号的测试', '/upload/2019/08/15/18c0c135-685f-4c75-9f31-fab7e3bd380d.jpg', '1', '2019-08-15 19:04:51', '1', '6');
INSERT INTO `article_info` VALUES ('9', '哪吒之魔童降世', '<span style=\"color: rgb(17, 17, 17); font-family: Helvetica, Arial, sans-serif; font-size: 13px;\">天地灵气孕育出一颗能量巨大的混元珠，元始天尊将混元珠提炼成灵珠和魔丸，灵珠投胎为人，助周伐纣时可堪大用；而魔丸则会诞出魔王，为祸人间。元始天尊启动了天劫咒语，3年后天雷将会降临，摧毁魔丸。太乙受命将灵珠托生于陈塘关李靖家的儿子哪吒身上。然而阴差阳错，灵珠和魔丸竟然被掉包。本应是灵珠英雄的哪吒却成了混世大魔王。调皮捣蛋顽劣不堪的哪吒却徒有一颗做英雄的心。然而面对众人对魔丸的误解和即将来临的天雷的降临，哪吒是否命中注定会立地成魔？他将何去何从？。</span>', '天地灵气孕育出一颗能量巨大的混元珠，元始天尊将混元珠提炼成灵珠和魔丸，灵珠投胎为人，助周伐纣时可堪大用；而魔丸则会诞出魔王，为祸人间。元始天尊启动了天劫咒语，3年后天雷将会降临，摧毁魔丸。太乙受命将灵', '/upload/2019/08/16/6dcb673b-705e-4d4d-9223-39854fa64805.png', '3', '2019-08-16 00:45:32', '1', '9');
INSERT INTO `article_info` VALUES ('10', '哈哈', 'sadsadsadas', '大苏打撒旦撒打算', '', '1', '2019-08-16 00:00:00', '0', '10');

-- ----------------------------
-- Table structure for type_info
-- ----------------------------
DROP TABLE IF EXISTS `type_info`;
CREATE TABLE `type_info` (
  `typeId` int(11) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(50) NOT NULL COMMENT '文章分类类型的名字',
  `typeSort` int(11) NOT NULL COMMENT '排序序号',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of type_info
-- ----------------------------
INSERT INTO `type_info` VALUES ('1', 'java', '1');
INSERT INTO `type_info` VALUES ('2', 'javaweb', '2');
INSERT INTO `type_info` VALUES ('5', '新闻头条', '6');
INSERT INTO `type_info` VALUES ('6', '动漫区', '3');
INSERT INTO `type_info` VALUES ('9', '电影区', '7');
INSERT INTO `type_info` VALUES ('10', '测试区', '8');
INSERT INTO `type_info` VALUES ('11', 'VIP区', '9');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL,
  `login_name` varchar(50) NOT NULL,
  `pass_word` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', '649391066', 'liuzhenbo');
