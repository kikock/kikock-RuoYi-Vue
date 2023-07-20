/*
 Navicat Premium Data Transfer

 Source Server         : 211.149.159.61
 Source Server Type    : MySQL
 Source Server Version : 50742
 Source Host           : 211.149.159.61:3306
 Source Schema         : ssosrasb

 Target Server Type    : MySQL
 Target Server Version : 50742
 File Encoding         : 65001

 Date: 19/07/2023 15:12:20
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_business_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_business_info`;
CREATE TABLE `sys_business_info`
(
    `ID`              varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
    `ENTITY_ID_INDEX` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务实体Id',
    `DOCUMENT_ID`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文档Id',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '业务实体关联文档表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_business_info
-- ----------------------------
INSERT INTO `sys_business_info`
VALUES ('c32fbb491e1446979e9b33a7272d9410', 'a2ed9046efd211eda7810242ac140002', '9225a2edefd211eda7810242ac140002');

-- ----------------------------
-- Table structure for sys_document_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_document_info`;
CREATE TABLE `sys_document_info`
(
    `ID`                  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
    `FULL_PATH`           varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件全路径',
    `FILE_NAME`           varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
    `FILE_TYPE`           char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型 0 户口本资料',
    `UPLOAD_USER_ID`      bigint(20) NULL DEFAULT NULL COMMENT '上传用户ID',
    `UPLOAD_USER_ACCOUNT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传用户账号',
    `UPLOAD_USER_NAME`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传用户名字',
    `UPLOAD_TIME`         datetime NULL DEFAULT NULL COMMENT '上传时间',
    `URL`                 varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文档下载地址',
    `OLD_FILE_NAME`       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原文件名',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文件信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_document_info
-- ----------------------------
INSERT INTO `sys_document_info`
VALUES ('9225a2edefd211eda7810242ac140002',
        '/home/ruoyi/uploadPath/2023/05/11/微信图片_20221125162436_20230511080519A001.jpg',
        '微信图片_20221125162436_20230511080519A001.jpg', '5', 7, 'hc', 'hc', '2023-05-11 08:05:19',
        '/profile/2023/05/11/微信图片_20221125162436_20230511080519A001.jpg', '微信图片_20221125162436.jpg');

SET
FOREIGN_KEY_CHECKS = 1;
