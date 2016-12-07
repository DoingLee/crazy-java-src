
DROP DATABASE IF EXISTS `BOOK_SYSTEM`;

-- 创建DATABASE
CREATE DATABASE BOOK_SYSTEM;
-- 使用BOOK_SYSTEM
USE BOOK_SYSTEM;

-- 用户表
CREATE TABLE IF NOT EXISTS `T_USER` (
    `ID` int AUTO_INCREMENT NOT NULL,
    `USER_NAME` varchar(20),
    `USER_PASSWORD` varchar(20),
    PRIMARY KEY (`ID`)
);

INSERT INTO `T_USER` VALUES ('1', 'crazyit', 'crazyit');

-- 书种类
CREATE TABLE IF NOT EXISTS `T_BOOK_TYPE` (
    `ID` int AUTO_INCREMENT NOT NULL, -- 主键生成策略为自动增长
    `TYPE_NAME` varchar(50), -- 种类名称
    `TYPE_INTRO` varchar(200), -- 种类简介
    PRIMARY KEY (`ID`)
);

-- 出版社
CREATE TABLE IF NOT EXISTS `T_PUBLISHER` (
    `ID` int AUTO_INCREMENT NOT NULL, -- 主键生成策略为自动增长
    `PUB_NAME` varchar(50), -- 出版社名称
    `PUB_TEL` varchar(50), -- 联系电话
    `PUB_LINK_MAN` varchar(50), -- 联系人
    `PUB_INTRO` varchar(200), -- 简介
    PRIMARY KEY (`ID`) -- 声明主键
);

-- 书
CREATE TABLE IF NOT EXISTS `T_BOOK` (
    `ID` int AUTO_INCREMENT NOT NULL, -- ID字段，自增
    `BOOK_NAME` varchar(50), -- 书名称
    `BOOK_INTRO` varchar(200), -- 书简介
	`BOOK_PRICE` double, -- 书的单价
    `TYPE_ID_FK` int NOT NULL, -- 种类外键
    `PUB_ID_FK` int NOT NULL, -- 出版社外键
	`IMAGE_URL` varchar(200), -- 缩略图URL
	`AUTHOR` varchar(200), -- 作者
    `REPERTORY_SIZE` bigint(10), -- 库存数量
    FOREIGN KEY (`TYPE_ID_FK`) REFERENCES `T_BOOK_TYPE` (`ID`), -- 声明种类的外键
    FOREIGN KEY (`PUB_ID_FK`) REFERENCES `T_PUBLISHER` (`ID`), -- 声明出版社外键
    PRIMARY KEY (`ID`)
);

-- 交易记录表, 一个交易记录包括多个书的销售记录, 一次交易可能有多本书
CREATE TABLE IF NOT EXISTS `T_SALE_RECORD` (
    `ID` int AUTO_INCREMENT NOT NULL,
    `RECORD_DATE` datetime,-- 交易日期
    PRIMARY KEY (`ID`)
);

-- 书的销售记录, 一条记录对应一本书
CREATE TABLE IF NOT EXISTS `T_BOOK_SALE_RECORD` (
    `ID` int AUTO_INCREMENT NOT NULL,
    `BOOK_ID_FK` int, -- 销售的书
    `T_SALE_RECORD_ID_FK` int, -- 该书的销售记录所对应的交易记录
    `TRADE_SUM` int(10), -- 销售数量
    FOREIGN KEY (`BOOK_ID_FK`) REFERENCES `T_BOOK` (`ID`),
    FOREIGN KEY (`T_SALE_RECORD_ID_FK`) REFERENCES `T_SALE_RECORD` (`ID`),
    PRIMARY KEY (`ID`)
);

-- 入库记录表, 一次入库会入多本书
CREATE TABLE IF NOT EXISTS `T_IN_RECORD` (
    `ID` int AUTO_INCREMENT NOT NULL,
    `RECORD_DATE` datetime, -- 入库日期
    PRIMARY KEY (`ID`)
);

-- 书的入库记录
CREATE TABLE IF NOT EXISTS `T_BOOK_IN_RECORD` (
    `ID` int AUTO_INCREMENT NOT NULL, -- ID自增
    `BOOK_ID_FK` int, -- 入库的书
    `T_IN_RECORD_ID_FK` int, -- 对应的入库记录
    `IN_SUM` int(10), -- 入库数量
    FOREIGN KEY (`BOOK_ID_FK`) REFERENCES `T_BOOK` (`ID`), -- 声明书的外键
    FOREIGN KEY (`T_IN_RECORD_ID_FK`) REFERENCES `T_IN_RECORD` (`ID`), -- 声明入库记录外键
    PRIMARY KEY (`ID`)
);