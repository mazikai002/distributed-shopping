/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50619
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2018-10-22 12:12:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) DEFAULT NULL,
  `goods_detail` longtext COMMENT '商品详情介绍',
  `goods_price` decimal(10,2) DEFAULT NULL COMMENT '单价',
  `goods_stock` int(11) DEFAULT NULL COMMENT '商品库存 -1表示无限制',
  `remoteAddr` varchar(50) DEFAULT NULL COMMENT '远程图片服务器IP+目录，如192.168.12.128/images',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', 'iphoneX', 'Apple iPhone X(A1865) 64GB 银色 移动联通电信4G手机', 'http://192.168.58.133/images/phone/iphonex.png', 'Apple iPhoneX 64GB 银色 移动联通电信4G手机', '8765.00', '100', null);