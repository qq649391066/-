-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 2018-06-14 14:55:52
-- 服务器版本： 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_supermarket`
--

-- --------------------------------------------------------

--
-- 表的结构 `goods`
--

CREATE TABLE `goods` (
  `id` int(100) UNSIGNED NOT NULL COMMENT '商品编码',
  `name` varchar(255) NOT NULL COMMENT '商品名称',
  `price` int(6) NOT NULL COMMENT '商品价格',
  `amount` int(100) NOT NULL COMMENT '库存数量',
  `note` varchar(255) NOT NULL COMMENT '备注信息'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `goods`
--

INSERT INTO `goods` (`id`, `name`, `price`, `amount`, `note`) VALUES
(1001, '飘游洗发水', 36, 231, '持久洁净柔顺，\n绿茶清新留香蕴含洁净因子和柔顺因子，能发挥双效作用，\n洁净秀发的同时在头发表面形成顺滑膜阻隔外界尘染，令秀发长时间保持洁净清爽，\n就像刚刚洗过一样丝丝分明，清爽怡人！'),
(1002, '咪咪虾条', 5, 500, '咪咪虾条的成份有小麦心粉植物油白砂糖加碘精盐蟹粉葱头蒜头及咖喱粉。\n'),
(1003, '大白菜', 6, 20, '小白菜可以促进人体的新陈代谢，具有清肝的作用。'),
(1004, '榴莲', 15, 150, '榴莲果肉营养丰富，有“水果之王”美称');

-- --------------------------------------------------------

--
-- 表的结构 `tb_message`
--

CREATE TABLE `tb_message` (
  `id` int(255) UNSIGNED NOT NULL COMMENT '员工编号',
  `name` varchar(255) NOT NULL COMMENT '员工名字',
  `sex` varchar(255) NOT NULL COMMENT '性别',
  `id_number` varchar(100) NOT NULL COMMENT '身份证号码',
  `bank_account` varchar(100) NOT NULL COMMENT '工资卡号',
  `tel` varchar(100) NOT NULL COMMENT '电话号码',
  `address` varchar(255) NOT NULL COMMENT '家庭住址'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tb_message`
--

INSERT INTO `tb_message` (`id`, `name`, `sex`, `id_number`, `bank_account`, `tel`, `address`) VALUES
(135, '张三', '男', '440183', '456', '137', '街口'),
(136, '李四', '女', '440183196542', '6693248621', '13793945354', '增城'),
(137, '王五', '男', '4401839652147', '96354654', '13796548231', '从化'),
(138, '赵六', '女', '4401836987542', '5641239684', '1389634562', '太平');

-- --------------------------------------------------------

--
-- 表的结构 `tb_provide`
--

CREATE TABLE `tb_provide` (
  `name` varchar(255) NOT NULL COMMENT '供应商',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `tel` int(100) NOT NULL COMMENT '电话号码',
  `bank_account` int(100) NOT NULL COMMENT '银行卡号'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tb_provide`
--

INSERT INTO `tb_provide` (`name`, `address`, `tel`, `bank_account`) VALUES
('刘老板', '重庆', 88888888, 66666666),
('吴老板', 'abc', 111, 1235);

-- --------------------------------------------------------

--
-- 表的结构 `userlist`
--

CREATE TABLE `userlist` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `stuNum` varchar(255) NOT NULL,
  `pwd` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `userlist`
--

INSERT INTO `userlist` (`id`, `name`, `stuNum`, `pwd`) VALUES
(123, '1', '1', '123');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `goods`
--
ALTER TABLE `goods`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_message`
--
ALTER TABLE `tb_message`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_provide`
--
ALTER TABLE `tb_provide`
  ADD PRIMARY KEY (`name`);

--
-- Indexes for table `userlist`
--
ALTER TABLE `userlist`
  ADD PRIMARY KEY (`id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `goods`
--
ALTER TABLE `goods`
  MODIFY `id` int(100) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品编码', AUTO_INCREMENT=222223;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
