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

 Date: 19/07/2023 15:11:44
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_wx_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_wx_menu`;
CREATE TABLE `sys_wx_menu`
(
    `ID`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单ID（click、scancode_push、scancode_waitmsg、pic_sysphoto、pic_photo_or_album、pic_weixin、location_select：保存key）',
    `DEL_FLAG`           char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
    `CREATE_TIME`        datetime NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_TIME`        datetime NULL DEFAULT NULL COMMENT '更新时间',
    `SORT`               int(11) NULL DEFAULT 1 COMMENT '排序值',
    `PARENT_ID`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父菜单ID',
    `TYPE`               char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单类型click、view、miniprogram、scancode_push、scancode_waitmsg、pic_sysphoto、pic_photo_or_album、pic_weixin、location_select、media_id、view_limited等',
    `NAME`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名',
    `URL`                varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'view、miniprogram保存链接',
    `MA_APP_ID`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小程序的appid',
    `MA_PAGE_PATH`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小程序的页面路径',
    `REP_TYPE`           char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回复消息类型（text：文本；image：图片；voice：语音；video：视频；music：音乐；news：图文）',
    `REP_CONTENT`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Text:保存文字',
    `REP_MEDIA_ID`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'imge、voice、news、video：mediaID',
    `REP_NAME`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '素材名、视频和音乐的标题',
    `REP_DESC`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频和音乐的描述',
    `REP_URL`            varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '链接',
    `REP_HQ_URL`         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '高质量链接',
    `REP_THUMB_MEDIA_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缩略图的媒体id',
    `REP_THUMB_URL`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缩略图url',
    `CONTENT`            mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '图文消息的内容',
    `IS_OAUTH`           char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否授权 0 是 1 否',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自定义菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_wx_menu
-- ----------------------------
INSERT INTO `sys_wx_menu`
VALUES ('186885e9bed711ecaf3700ffaabbccdd', '0', '2022-04-18 13:19:20', '2022-04-18 15:29:18', 1,
        '83d7704bbc9a11ecaf3700ffaabbccdd', 'view', '我的卡卷', 'http://br3uqh.natappfree.cc/captchaImage', NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1');
INSERT INTO `sys_wx_menu`
VALUES ('29b0904fbc8e11ecaf3700ffaabbccdd', '0', '2022-04-15 15:32:12', NULL, 1, '0', 'click', '会员中心', NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1');
INSERT INTO `sys_wx_menu`
VALUES ('7a48d40bbc9a11ecaf3700ffaabbccdd', '0', '2022-04-15 17:00:21', '2022-04-18 15:29:14', 1,
        '29b0904fbc8e11ecaf3700ffaabbccdd', 'view', '会员管理', 'http://www.baidu.com', NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_wx_menu`
VALUES ('83d7704bbc9a11ecaf3700ffaabbccdd', '0', '2022-04-15 17:00:37', NULL, 2, '0', 'click', '个人中心', NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1');
INSERT INTO `sys_wx_menu`
VALUES ('c7d83ae7da4111ec83e00242c0a86303', '0', '2022-05-23 10:43:23', NULL, 1, '29b0904fbc8e11ecaf3700ffaabbccdd',
        'click', '测试', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1');

-- ----------------------------
-- Table structure for sys_wx_msg
-- ----------------------------
DROP TABLE IF EXISTS `sys_wx_msg`;
CREATE TABLE `sys_wx_msg`
(
    `ID`                 varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '主键',
    `CREATE_ID`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `CREATE_TIME`        datetime NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_ID`          varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
    `UPDATE_TIME`        datetime NULL DEFAULT NULL COMMENT '更新时间',
    `REMARK`             varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    `DEL_FLAG`           char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
    `APP_NAME`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公众号名称',
    `APP_LOGO`           varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公众号logo',
    `WX_USER_ID`         varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信用户ID',
    `NICK_NAME`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信用户昵称',
    `HEADIMG_URL`        varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信用户头像',
    `TYPE`               char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息分类（1、用户发给公众号；2、公众号发给用户；）',
    `REP_TYPE`           char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息类型（text：文本；image：图片；voice：语音；video：视频；shortvideo：小视频；location：地理位置；music：音乐；news：图文；event：推送事件）',
    `REP_EVENT`          char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '事件类型（subscribe：关注；unsubscribe：取关；CLICK、VIEW：菜单事件）',
    `REP_CONTENT`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '回复类型文本保存文字、地理位置信息',
    `REP_MEDIA_ID`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id',
    `REP_NAME`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回复的素材名、视频和音乐的标题',
    `REP_DESC`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频和音乐的描述',
    `REP_URL`            varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '链接',
    `REP_HQ_URL`         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '高质量链接',
    `CONTENT`            mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '图文消息的内容',
    `REP_THUMB_MEDIA_ID` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缩略图的媒体id',
    `REP_THUMB_URL`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缩略图url',
    `REP_LOCATION_X`     double NULL DEFAULT NULL COMMENT '地理位置维度',
    `REP_LOCATION_Y`     double NULL DEFAULT NULL COMMENT '地理位置经度',
    `REP_SCALE`          double NULL DEFAULT NULL COMMENT '地图缩放大小',
    `READ_FLAG`          char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '已读标记（1：是；0：否）',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_wx_msg
-- ----------------------------

-- ----------------------------
-- Table structure for sys_wx_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_wx_user`;
CREATE TABLE `sys_wx_user`
(
    `ID`                    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci       NOT NULL DEFAULT '' COMMENT '主键',
    `CREATE_ID`             varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `CREATE_TIME`           datetime NULL DEFAULT NULL COMMENT '创建时间',
    `UPDATE_ID`             varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
    `UPDATE_TIME`           datetime NULL DEFAULT NULL COMMENT '更新时间',
    `REMARK`                varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户备注',
    `DEL_FLAG`              char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
    `APP_TYPE`              char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用类型(1:小程序，2:公众号)',
    `SUBSCRIBE`             char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否订阅（1：是；0：否；2：网页授权用户）',
    `SUBSCRIBE_SCENE`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他',
    `SUBSCRIBE_TIME`        datetime NULL DEFAULT NULL COMMENT '关注时间',
    `SUBSCRIBE_NUM`         int(11) NULL DEFAULT NULL COMMENT '关注次数',
    `CANCEL_SUBSCRIBE_TIME` datetime NULL DEFAULT NULL COMMENT '取消关注时间',
    `OPEN_ID`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户标识',
    `NICK_NAME`             varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
    `SEX`                   char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别（0：男，1：女，2：未知）',
    `CITY`                  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所在城市',
    `COUNTRY`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所在国家',
    `PROVINCE`              varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所在省份',
    `PHONE`                 varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
    `LANGUAGE`              varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户语言',
    `HEADIMG_URL`           varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
    `UNION_ID`              varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'union_id',
    `GROUP_ID`              varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户组',
    `TAGID_LIST`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签列表',
    `QR_SCENE_STR`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '二维码扫码场景',
    `LATITUDE`              double NULL DEFAULT NULL COMMENT '地理位置纬度',
    `LONGITUDE`             double NULL DEFAULT NULL COMMENT '地理位置经度',
    `PRECISION`             double NULL DEFAULT NULL COMMENT '地理位置精度',
    `SESSION_KEY`           varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会话密钥',
    `USER_ID`               bigint(20) NULL DEFAULT NULL COMMENT '用户id',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `UK_OPENID`(`OPEN_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_wx_user
-- ----------------------------
INSERT INTO `sys_wx_user`
VALUES ('defbf046c11e11ec83e00242c0a86303', NULL, '2022-04-21 11:00:29', NULL, '2022-04-24 15:42:03', NULL, '0', '2',
        '1', 'ADD_SCENE_QR_CODE', '2022-04-24 15:42:03', 22, NULL, 'oS-UD6bxE9-yIZex4B5B6TKt0uUA', '', '0', '', '', '',
        NULL, 'zh_CN', '', NULL, '0', NULL, '', NULL, NULL, NULL, NULL, 6);
INSERT INTO `sys_wx_user`
VALUES ('e5b24c19c13d11ec83e00242c0a86303', NULL, '2022-04-21 14:42:36', NULL, '2022-04-26 09:04:15', NULL, '0', '2',
        '1', 'ADD_SCENE_QR_CODE', '2022-04-26 09:04:15', 8, NULL, 'oS-UD6Tk_oRicmC1m1SAHRM-Ztm4', '', '0', '', '', '',
        NULL, 'zh_CN', '', NULL, '0', NULL, '', NULL, NULL, NULL, NULL, 5);

SET
FOREIGN_KEY_CHECKS = 1;
