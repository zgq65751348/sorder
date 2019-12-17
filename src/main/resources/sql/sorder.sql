/*
Navicat MySQL Data Transfer

Source Server         : 39.108.142.52
Source Server Version : 50520
Source Host           : 39.108.142.52:3306
Source Database       : sorder1

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2019-06-15 11:37:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tl_advertisement
-- ----------------------------
DROP TABLE IF EXISTS `tl_advertisement`;
CREATE TABLE `tl_advertisement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) DEFAULT NULL COMMENT '学校id',
  `type` int(2) DEFAULT NULL COMMENT '供应类型',
  `photo` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `sid` (`sid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COMMENT='轮播广告表';

-- ----------------------------
-- Table structure for tl_canteen
-- ----------------------------
DROP TABLE IF EXISTS `tl_canteen`;
CREATE TABLE `tl_canteen` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(32) DEFAULT '' COMMENT '食堂名称',
  `sid` int(11) DEFAULT NULL COMMENT '学校id',
  `sname` varchar(32) DEFAULT '' COMMENT '学校名称',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `uptime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`cid`),
  KEY `sid` (`sid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COMMENT='食堂表';

-- ----------------------------
-- Table structure for tl_canteen_food
-- ----------------------------
DROP TABLE IF EXISTS `tl_canteen_food`;
CREATE TABLE `tl_canteen_food` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `bar_code` varchar(64) DEFAULT NULL COMMENT '条形码',
  `food_name` varchar(32) DEFAULT '' COMMENT '食品名称',
  `food_rmk` varchar(128) DEFAULT '' COMMENT '食品描述',
  `type` int(11) DEFAULT NULL COMMENT '供应时段类型 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐',
  `supply_start` varchar(64) DEFAULT '' COMMENT '开始供应时间',
  `supply_end` varchar(64) DEFAULT '' COMMENT '结束供应时间',
  `photo` varchar(255) DEFAULT NULL COMMENT '食品图片',
  `status` int(2) DEFAULT '0' COMMENT '0下架 1上架（暂时废除 2019/2/20）',
  `daily_supply` int(11) DEFAULT '0' COMMENT '单日供应量',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `uptime` datetime DEFAULT NULL COMMENT '修改时间',
  `monthly_sales` int(8) DEFAULT '0' COMMENT '月销量',
  `total_sales` int(8) DEFAULT '0' COMMENT '总销量',
  `score` decimal(2,1) DEFAULT '0.0' COMMENT '评分',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '食品价格',
  `sid` int(11) DEFAULT NULL COMMENT '学校id',
  `cid` int(11) DEFAULT NULL COMMENT '食堂id',
  `uid` varchar(64) DEFAULT NULL COMMENT '操作人',
  `supply_count` int(8) DEFAULT '0' COMMENT '供应量',
  PRIMARY KEY (`fid`),
  KEY `cid` (`type`) USING BTREE,
  KEY `uid` (`uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COMMENT='食堂食品表';

-- ----------------------------
-- Table structure for tl_cart
-- ----------------------------
DROP TABLE IF EXISTS `tl_cart`;
CREATE TABLE `tl_cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` varchar(32) DEFAULT NULL COMMENT '关联人uid',
  `wfid` int(16) DEFAULT NULL COMMENT '窗口食品id',
  `num` int(4) DEFAULT '1' COMMENT '食品数量',
  `sub_date` varchar(16) DEFAULT NULL COMMENT '预定时间 格式：2019-02-22',
  `status` int(2) DEFAULT NULL COMMENT '0无效 1有效',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `uptime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `fid` (`wfid`)
) ENGINE=InnoDB AUTO_INCREMENT=391 DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- ----------------------------
-- Table structure for tl_comment
-- ----------------------------
DROP TABLE IF EXISTS `tl_comment`;
CREATE TABLE `tl_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(32) DEFAULT '' COMMENT '评论人uid',
  `oid` int(11) DEFAULT NULL COMMENT '订单条目id',
  `fid` int(16) DEFAULT NULL COMMENT '食品id',
  `food_name` varchar(32) DEFAULT '' COMMENT '食品名称',
  `food_type` int(2) DEFAULT NULL COMMENT '1: 食堂  2:供应商',
  `remark` varchar(255) DEFAULT '' COMMENT '评论内容',
  `reply` varchar(255) DEFAULT NULL COMMENT '商家回复',
  `status` int(2) DEFAULT '1' COMMENT '0不可见 1可见',
  `photo` varchar(255) DEFAULT '' COMMENT '图片地址',
  `score` decimal(2,1) DEFAULT '0.0' COMMENT '评分',
  `anomy` int(2) DEFAULT NULL COMMENT '是否匿名 0否1是',
  `cid` int(11) DEFAULT NULL COMMENT '食堂id',
  `sid` int(11) DEFAULT NULL COMMENT '学校id',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `order_id` (`oid`),
  KEY `fid` (`fid`),
  KEY `sid` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

-- ----------------------------
-- Table structure for tl_discount_price
-- ----------------------------
DROP TABLE IF EXISTS `tl_discount_price`;
CREATE TABLE `tl_discount_price` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `sid` int(16) DEFAULT NULL COMMENT '学校id',
  `role` int(2) DEFAULT NULL COMMENT '1:学生  2:班主任  3:普通教师',
  `type` int(2) DEFAULT NULL COMMENT '1:满减 2:金额折扣',
  `discount_price` decimal(10,2) DEFAULT '0.00' COMMENT '优惠价格',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT 'type=1 时  设置  满减金额',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`),
  KEY `sid` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8mb4 COMMENT='优惠区间表';

-- ----------------------------
-- Table structure for tl_feedback
-- ----------------------------
DROP TABLE IF EXISTS `tl_feedback`;
CREATE TABLE `tl_feedback` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT '' COMMENT '反馈内容',
  `uid` varchar(32) DEFAULT NULL COMMENT '关联人uid',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='反馈建议表';

-- ----------------------------
-- Table structure for tl_manager
-- ----------------------------
DROP TABLE IF EXISTS `tl_manager`;
CREATE TABLE `tl_manager` (
  `mid` varchar(64) NOT NULL,
  `mname` varchar(32) DEFAULT '' COMMENT '管理员姓名',
  `username` varchar(32) DEFAULT '' COMMENT '登录名',
  `password` varchar(32) DEFAULT '' COMMENT '密码',
  `phone` varchar(32) DEFAULT '' COMMENT '手机号',
  `portrait` varchar(255) DEFAULT '' COMMENT '头像',
  `type` int(2) DEFAULT '1' COMMENT '1学校 2平台',
  `status` int(2) DEFAULT '1' COMMENT '状态 （ 0停用  1正常 ）',
  `role` int(2) DEFAULT '1' COMMENT '角色 1管理员 2子管理员',
  `login_ip` varchar(255) DEFAULT '' COMMENT '最后一次登录ip',
  `login_time` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `uptime` datetime DEFAULT NULL COMMENT '修改时间',
  `sid` int(2) DEFAULT NULL COMMENT '所属学校id',
  `overdue_time` datetime DEFAULT NULL COMMENT '过期时间',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '上级负责人id',
  PRIMARY KEY (`mid`),
  KEY `sid` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

-- ----------------------------
-- Table structure for tl_msg
-- ----------------------------
DROP TABLE IF EXISTS `tl_msg`;
CREATE TABLE `tl_msg` (
  `mid` int(16) NOT NULL AUTO_INCREMENT,
  `push_platform` int(2) DEFAULT '0' COMMENT '推送平台( 0:ALL 1:IOS  2:Android )',
  `title` varchar(255) DEFAULT '' COMMENT '标题',
  `context` text COMMENT '内容',
  `push_obj` int(2) DEFAULT NULL COMMENT '消息推送对象 1学生端 2食堂 3 供应商',
  `sid` int(16) DEFAULT '0' COMMENT '学校id 0为大平台推送',
  `crtime` datetime DEFAULT NULL,
  PRIMARY KEY (`mid`),
  KEY `sid` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COMMENT='消息推送表';

-- ----------------------------
-- Table structure for tl_msg_log
-- ----------------------------
DROP TABLE IF EXISTS `tl_msg_log`;
CREATE TABLE `tl_msg_log` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `mid` int(16) DEFAULT NULL COMMENT '消息id',
  `uid` varchar(64) DEFAULT NULL COMMENT '被推送人uid',
  `status` int(2) DEFAULT '0' COMMENT '0未读 1已读',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `mid` (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COMMENT='消息推送日志表';

-- ----------------------------
-- Table structure for tl_order
-- ----------------------------
DROP TABLE IF EXISTS `tl_order`;
CREATE TABLE `tl_order` (
  `oid` varchar(32) NOT NULL DEFAULT '' COMMENT '订单id',
  `uid` varchar(32) DEFAULT NULL COMMENT '购买人uid',
  `status` int(2) DEFAULT NULL COMMENT '1:待付款 2:付款失败 3:付款成功=生成二维码 4:已完成 二维码已扫 5:取消/退款 6.订单已评论  ',
  `pay_type` int(2) DEFAULT NULL COMMENT '1:钱包 2:微信 3:支付宝 4:农行 5:刷支付码 6:人脸',
  `pay_price` decimal(10,2) DEFAULT '0.00' COMMENT '实际支付金额',
  `out_trade_no` varchar(32) DEFAULT NULL COMMENT '商户订单号',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `discounts_type` int(2) DEFAULT '0' COMMENT '优惠种类  (0:无 1: 满减 2:折扣 3:教师补贴)',
  `subsidy_price` decimal(10,2) DEFAULT '0.00' COMMENT '教师使用补贴金额',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `uptime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`oid`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- Table structure for tl_order_item
-- ----------------------------
DROP TABLE IF EXISTS `tl_order_item`;
CREATE TABLE `tl_order_item` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `oid` varchar(32) DEFAULT NULL COMMENT '订单id',
  `uid` varchar(32) DEFAULT NULL COMMENT 'uid',
  `wfid` int(11) DEFAULT NULL COMMENT '食品id',
  `type` int(2) DEFAULT NULL COMMENT '供应时段类型 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐',
  `food_name` varchar(16) DEFAULT NULL COMMENT '食品名称',
  `cname` varchar(16) DEFAULT NULL COMMENT '食堂名称',
  `wname` varchar(16) DEFAULT NULL COMMENT '窗口名称',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '食品单价',
  `num` int(8) DEFAULT '0' COMMENT '食品数量',
  `total_price` decimal(10,2) DEFAULT '0.00' COMMENT '总价',
  `photo` varchar(255) DEFAULT NULL COMMENT '食品图片',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `sub_time` varchar(16) DEFAULT NULL COMMENT '预定时间',
  `status` int(2) DEFAULT NULL COMMENT '0:订单已关闭 1:待付款 2:付款失败 3:付款成功=生成二维码 4:已完成 二维码已扫 5:退款 6.订单已评论  ',
  `crtime` datetime DEFAULT NULL,
  `uptime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`oid`),
  KEY `fid` (`food_name`)
) ENGINE=InnoDB AUTO_INCREMENT=315 DEFAULT CHARSET=utf8mb4 COMMENT='订单详情表';

-- ----------------------------
-- Table structure for tl_phone_code
-- ----------------------------
DROP TABLE IF EXISTS `tl_phone_code`;
CREATE TABLE `tl_phone_code` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `code` varchar(8) DEFAULT NULL COMMENT '验证码',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tl_school
-- ----------------------------
DROP TABLE IF EXISTS `tl_school`;
CREATE TABLE `tl_school` (
  `sid` int(8) NOT NULL AUTO_INCREMENT,
  `sname` varchar(64) DEFAULT '' COMMENT '学校名称',
  `snum` varchar(4) DEFAULT NULL COMMENT '学校编号 限四位字母n',
  `balance` decimal(10,2) DEFAULT '0.00' COMMENT '余额',
  `dredge_alipay` int(2) DEFAULT NULL COMMENT '开通支付宝 0否 1是',
  `dredge_wxpay` int(2) DEFAULT NULL COMMENT '开通微信 0否 1是',
  `dredge_face` int(2) DEFAULT NULL COMMENT '开通人脸',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `uptime` datetime DEFAULT NULL COMMENT '修改时间',
  `subsidy_status` int(2) DEFAULT '1' COMMENT '是否启用补贴 0否 1是',
  `subsidy_price` decimal(10,2) DEFAULT '0.00' COMMENT '（教师）补贴金额',
  `subsidy_time` datetime DEFAULT NULL COMMENT '补贴时间',
  `deposit_price` decimal(10,2) DEFAULT '0.00' COMMENT '已提现金钱',
  `total_deposit_price` decimal(10,2) DEFAULT '0.00' COMMENT '历史提现总金额',
  `use_help` text COMMENT '使用帮助',
  `about_us` text COMMENT '关于我们',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='学校表';

-- ----------------------------
-- Table structure for tl_school_class
-- ----------------------------
DROP TABLE IF EXISTS `tl_school_class`;
CREATE TABLE `tl_school_class` (
  `scid` int(8) NOT NULL AUTO_INCREMENT,
  `type` int(2) DEFAULT '1' COMMENT '类型 1高中 2大学',
  `grade` int(2) DEFAULT NULL COMMENT '年级',
  `class_num` int(2) DEFAULT NULL COMMENT '班级',
  `sid` int(8) DEFAULT NULL COMMENT '学校id',
  PRIMARY KEY (`scid`),
  KEY `sid` (`sid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COMMENT='年级班级表';

-- ----------------------------
-- Table structure for tl_school_supfood
-- ----------------------------
DROP TABLE IF EXISTS `tl_school_supfood`;
CREATE TABLE `tl_school_supfood` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fid` int(11) DEFAULT NULL COMMENT '食品id',
  `sid` int(11) DEFAULT NULL COMMENT '学校id',
  `type` int(11) DEFAULT NULL COMMENT '供应时段类型 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐',
  `supply_start` varchar(32) DEFAULT NULL COMMENT '开始供应时间',
  `supply_end` varchar(32) DEFAULT NULL COMMENT '结束供应时间',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '食品价格',
  `status` int(11) DEFAULT '0' COMMENT '供应商餐品状态 1-上架  ,0-下架     食堂，供应商可看，食堂不可看  未上架餐品',
  `monthly_sales` int(11) DEFAULT '0' COMMENT '月销量',
  `total_sales` int(11) DEFAULT '0' COMMENT '总销量',
  `buy_count` int(11) DEFAULT NULL COMMENT '食堂购买数量',
  `supplier_count` int(11) DEFAULT NULL COMMENT '食堂供应数量',
  `daily_supply` int(11) DEFAULT '0' COMMENT '当日供应数量',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `uptime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tl_school_supplier
-- ----------------------------
DROP TABLE IF EXISTS `tl_school_supplier`;
CREATE TABLE `tl_school_supplier` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `sid` int(8) NOT NULL COMMENT '学校id',
  `uid` varchar(64) NOT NULL COMMENT '供应商id',
  `mid` varchar(64) DEFAULT NULL COMMENT '审批人',
  `status` int(2) DEFAULT '0' COMMENT '学校是否启用该供应商状态  0停用  1启用 2拒绝',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `sid` (`sid`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COMMENT='学校供应商关系表';

-- ----------------------------
-- Table structure for tl_supplier_food
-- ----------------------------
DROP TABLE IF EXISTS `tl_supplier_food`;
CREATE TABLE `tl_supplier_food` (
  `fid` int(32) NOT NULL AUTO_INCREMENT,
  `food_name` varchar(32) DEFAULT '' COMMENT '食品名称',
  `food_rmk` varchar(32) DEFAULT '' COMMENT '食品描述',
  `status` int(2) DEFAULT '1' COMMENT '是否可用 0不可用 1可用',
  `photo` varchar(255) DEFAULT '' COMMENT '食品图片',
  `score` decimal(2,1) DEFAULT '0.0' COMMENT '评分',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `uptime` datetime DEFAULT NULL COMMENT '修改时间',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '食品价格',
  `uid` varchar(32) DEFAULT NULL COMMENT '供应商id',
  PRIMARY KEY (`fid`),
  KEY `supplier_uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COMMENT='供应商食品表';

-- ----------------------------
-- Table structure for tl_supplier_time
-- ----------------------------
DROP TABLE IF EXISTS `tl_supplier_time`;
CREATE TABLE `tl_supplier_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(2) DEFAULT NULL COMMENT '供应时段类型 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐',
  `sid` int(8) DEFAULT NULL COMMENT '学校id',
  `supply_start` varchar(16) DEFAULT '00:00' COMMENT '开始供应时间',
  `supply_end` varchar(16) DEFAULT '00:00' COMMENT '结束供应时间',
  `subsidy_price` decimal(10,2) DEFAULT '0.00' COMMENT '补贴金额',
  `subsidy_status` int(2) DEFAULT '0' COMMENT '补贴开启  0:否 1:是  ',
  PRIMARY KEY (`id`),
  KEY `sid` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=617 DEFAULT CHARSET=utf8mb4 COMMENT='食品分类表';

-- ----------------------------
-- Table structure for tl_sys_setting
-- ----------------------------
DROP TABLE IF EXISTS `tl_sys_setting`;
CREATE TABLE `tl_sys_setting` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `about_us` text COMMENT '关于我们',
  `use_help` text COMMENT '使用帮助 食堂',
  `withdraw_day` int(4) DEFAULT NULL COMMENT '设置 供应/食堂 提现 缓冲期 n天',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `uptime` datetime DEFAULT NULL COMMENT '修改时间',
  `use_help_supplier` text COMMENT '供应商使用帮助',
  `use_help_student` text COMMENT '学生端使用帮助',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='系统设定表';

-- ----------------------------
-- Table structure for tl_trade
-- ----------------------------
DROP TABLE IF EXISTS `tl_trade`;
CREATE TABLE `tl_trade` (
  `tid` varchar(32) NOT NULL COMMENT '流水id',
  `uid` varchar(32) DEFAULT NULL COMMENT '关联人uid',
  `oid` varchar(32) DEFAULT '' COMMENT '订单id',
  `cart_ids` varchar(255) DEFAULT NULL COMMENT '购物车id 多个以逗号分隔',
  `use_subsidy` int(2) DEFAULT '0' COMMENT '是否使用补贴 0否 1是',
  `out_trade_no` varchar(32) DEFAULT '' COMMENT '商户订单号',
  `trade_type` int(2) DEFAULT NULL COMMENT '流水类型 1钱包充值 2订单 3订单退款 4提现 5系统发放补贴 6单独发放补贴',
  `pay_type` int(2) DEFAULT NULL COMMENT '交易类型 0补贴 1钱包 2微信 3支付宝 4农行 5支付码 6人脸',
  `price` decimal(10,2) DEFAULT NULL COMMENT '交易金额',
  `account` varchar(64) DEFAULT NULL COMMENT '交易账号（微信/支付宝）',
  `status` int(2) DEFAULT NULL COMMENT '交易状态（0待审核  1已完成  2待支付  3提现失败）',
  `cause` varchar(255) DEFAULT NULL COMMENT '提现失败原因',
  `ie` int(2) DEFAULT NULL COMMENT '收支（1收入 2支出）',
  `balance` decimal(8,2) DEFAULT '0.00' COMMENT '当前账户余额',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `uptime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`tid`),
  KEY `uid` (`uid`),
  KEY `order_id` (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流水表';

-- ----------------------------
-- Table structure for tl_user
-- ----------------------------
DROP TABLE IF EXISTS `tl_user`;
CREATE TABLE `tl_user` (
  `uid` varchar(32) NOT NULL COMMENT '用户id',
  `username` varchar(16) DEFAULT '' COMMENT '用户名/姓名',
  `phone` varchar(16) DEFAULT '' COMMENT '手机号',
  `password` varchar(32) DEFAULT '' COMMENT '密码',
  `token` varchar(128) DEFAULT '' COMMENT 'token令牌',
  `pay_password` varchar(32) DEFAULT NULL COMMENT '支付密码',
  `age` int(4) DEFAULT '18' COMMENT '年龄',
  `sex` int(1) DEFAULT '1' COMMENT '性别(1男 2女)',
  `email` varchar(32) DEFAULT '' COMMENT '邮箱',
  `type` int(2) DEFAULT '1' COMMENT '用户类型（1学生 2教师 3班主任 4其他人员）',
  `status` int(2) DEFAULT '2' COMMENT '状态（0未通过 1通过 2待审核 3审核中）',
  `sid` int(8) DEFAULT NULL COMMENT '学校id',
  `cid` int(4) DEFAULT NULL COMMENT '年级id',
  `balance` decimal(10,2) DEFAULT '0.00' COMMENT '钱包金额',
  `stu_num` varchar(16) DEFAULT '' COMMENT '学号',
  `icon` varchar(512) DEFAULT '' COMMENT '头像',
  `taste` varchar(128) DEFAULT NULL COMMENT '口味',
  `is_face` int(2) DEFAULT '0' COMMENT '是否开通人脸',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间/注册时间',
  `uptime` datetime DEFAULT NULL COMMENT '修改时间',
  `subsidy_price` decimal(8,2) DEFAULT '0.00' COMMENT '补贴金额',
  `bfsb_status` tinyint(1) DEFAULT '0' COMMENT '早餐补贴状态（老师 ）（0未启用 1启用 ）',
  `lsb_status` tinyint(1) DEFAULT '0' COMMENT '午餐补贴状态（老师 ）（0未启用 1启用 ）',
  `dsb_status` tinyint(1) DEFAULT '0' COMMENT '晚餐补贴状态（老师 ）（0未启用 1启用 ）',
  PRIMARY KEY (`uid`),
  KEY `sid` (`sid`) USING BTREE,
  KEY `cid` (`cid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Table structure for tl_user_canteen
-- ----------------------------
DROP TABLE IF EXISTS `tl_user_canteen`;
CREATE TABLE `tl_user_canteen` (
  `uid` varchar(64) NOT NULL,
  `uname` varchar(32) DEFAULT '' COMMENT '姓名',
  `phone` varchar(32) DEFAULT '' COMMENT '手机号',
  `password` varchar(32) DEFAULT '' COMMENT '密码',
  `token` varchar(255) DEFAULT '' COMMENT 'token',
  `balance` double(8,2) DEFAULT '0.00' COMMENT '钱包余额',
  `sex` int(2) DEFAULT '1' COMMENT '性别 1男 2女',
  `portrait` varchar(255) DEFAULT '' COMMENT '用户头像',
  `photo` varchar(255) DEFAULT '' COMMENT '用户真人照片',
  `role` varchar(32) DEFAULT '' COMMENT '职位',
  `type` int(2) DEFAULT '1' COMMENT '用户类型 1员工 2负责人',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '负责人id',
  `sid` int(8) DEFAULT NULL COMMENT '学校id',
  `sname` varchar(64) DEFAULT '' COMMENT '学校名称',
  `cid` int(16) DEFAULT NULL COMMENT '食堂id',
  `cname` varchar(32) DEFAULT '' COMMENT '食堂名称',
  `wid` int(32) DEFAULT NULL COMMENT '窗口id',
  `wname` varchar(32) DEFAULT '' COMMENT '窗口名称',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `uptime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`uid`),
  KEY `sid` (`sid`),
  KEY `cid` (`cid`),
  KEY `wid` (`wid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='食堂员工表';

-- ----------------------------
-- Table structure for tl_user_supplier
-- ----------------------------
DROP TABLE IF EXISTS `tl_user_supplier`;
CREATE TABLE `tl_user_supplier` (
  `uid` varchar(64) NOT NULL,
  `phone` varchar(32) DEFAULT '' COMMENT '手机号',
  `password` varchar(32) DEFAULT '' COMMENT '密码',
  `token` varchar(128) DEFAULT NULL COMMENT 'token',
  `portrait` varchar(255) DEFAULT '' COMMENT '头像',
  `main_product` varchar(64) DEFAULT '' COMMENT '主营产品',
  `balance` double(8,2) DEFAULT '0.00' COMMENT '钱包余额',
  `status` int(2) DEFAULT '3' COMMENT '店铺审核状态 0未通过 1通过 2待审核 3注册后未提交入驻审核',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `uptime` datetime DEFAULT NULL COMMENT '修改时间',
  `leader_name` varchar(32) DEFAULT '' COMMENT '负责人姓名',
  `leader_phone` varchar(32) DEFAULT '' COMMENT '负责人联系方式',
  `id_card_front` varchar(128) DEFAULT '' COMMENT '身份证正面照',
  `id_card_contrary` varchar(128) DEFAULT '' COMMENT '身份证反面照',
  `business_license` varchar(128) DEFAULT '' COMMENT '营业执照',
  `relevant_license` varchar(128) DEFAULT '' COMMENT '相关许可证',
  `shop_name` varchar(32) DEFAULT '' COMMENT '店铺名称',
  `shop_photo` varchar(255) DEFAULT '' COMMENT '店铺照片',
  `axf` int(4) DEFAULT '0' COMMENT '人均消费',
  `m_sale_count` int(11) DEFAULT '0' COMMENT '月销量',
  `sale_count` int(8) DEFAULT '0' COMMENT '总销量',
  `score` varchar(4) DEFAULT '0.0' COMMENT '评分(最高5.0）',
  `approve_time` datetime DEFAULT NULL COMMENT '审核通过时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商表';

-- ----------------------------
-- Table structure for tl_window
-- ----------------------------
DROP TABLE IF EXISTS `tl_window`;
CREATE TABLE `tl_window` (
  `wid` int(32) NOT NULL AUTO_INCREMENT COMMENT '窗口id',
  `wname` varchar(32) DEFAULT '' COMMENT '窗口名称',
  `sid` int(8) DEFAULT NULL COMMENT '学校id',
  `cid` int(8) DEFAULT NULL COMMENT '食堂id',
  `type` int(2) DEFAULT NULL COMMENT '窗口类型 1普通窗口 2水果超市 3便利小超',
  `status` int(2) DEFAULT NULL COMMENT '是否显示窗口 0否 1是',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `uptime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`wid`),
  KEY `sid` (`sid`),
  KEY `cid` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COMMENT='食堂窗口表';

-- ----------------------------
-- Table structure for tl_window_food
-- ----------------------------
DROP TABLE IF EXISTS `tl_window_food`;
CREATE TABLE `tl_window_food` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `wid` int(32) DEFAULT NULL COMMENT '窗口id',
  `fid` int(32) DEFAULT NULL COMMENT '食物id/供应食品id',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '食堂供应价格',
  `type` int(2) DEFAULT NULL COMMENT '食品类型 1.食堂自营 2.供应商',
  `status` int(2) DEFAULT '0' COMMENT '是否上架 0否 1是',
  `daily_supply` int(11) DEFAULT '0' COMMENT '当日供应量',
  `residue_supply` int(11) DEFAULT '0' COMMENT '当日剩余供应量',
  `crtime` datetime DEFAULT NULL COMMENT '创建时间',
  `uptime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `wid` (`wid`),
  KEY `fid` (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8mb4 COMMENT='窗口-食品关系表';
