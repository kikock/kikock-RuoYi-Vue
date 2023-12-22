/*
 Navicat Premium Data Transfer

 Source Server         : 61服务器
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : 211.149.159.61:3306
 Source Schema         : ry_vue

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 22/12/2023 14:40:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成业务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table
-- ----------------------------
INSERT INTO `gen_table` VALUES (2, 'sys_social_user_bind', '社交绑定表', NULL, NULL, 'SysSocialUserBind', 'crud', 'com.ruoyi.system', 'system', 'socialUserBind', '三方用户', 'kikock', '0', '/', '{\"parentMenuId\":\"2000\"}', 'admin', '2023-11-28 14:12:24', '', '2023-12-04 16:42:59', NULL);
INSERT INTO `gen_table` VALUES (3, 'sys_social_app', '三方应用', NULL, NULL, 'SysSocialApp', 'crud', 'com.ruoyi.system', 'system', 'socialApp', '社交应用参数', 'kikock', '0', '/', '{\"parentMenuId\":\"2000\"}', 'admin', '2023-12-04 13:09:34', '', '2023-12-04 14:19:27', NULL);

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint(20) NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成业务表字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
INSERT INTO `gen_table_column` VALUES (17, 2, 'id', '主键(自增策略)', 'bigint(20) unsigned', 'Long', 'id', '1', '1', NULL, '0', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-11-28 14:12:24', '', '2023-12-04 16:42:59');
INSERT INTO `gen_table_column` VALUES (18, 2, 'user_id', '系统用户id', 'bigint(20)', 'Long', 'userId', '0', '0', '1', '0', '0', '1', '0', 'EQ', 'input', '', 2, 'admin', '2023-11-28 14:12:24', '', '2023-12-04 16:42:59');
INSERT INTO `gen_table_column` VALUES (20, 2, 'social_type', '绑定平台', 'tinyint(4)', 'Integer', 'socialType', '0', '0', '1', '0', '0', '1', '1', 'EQ', 'select', 'sys_social_type', 5, 'admin', '2023-11-28 14:12:24', '', '2023-12-04 16:42:59');
INSERT INTO `gen_table_column` VALUES (22, 2, 'del_flag', '删除标志（0代表存在 2代表删除）', 'char(1)', 'String', 'delFlag', '0', '0', NULL, '0', NULL, NULL, NULL, 'EQ', 'input', '', 8, 'admin', '2023-11-28 14:12:24', '', '2023-12-04 16:42:59');
INSERT INTO `gen_table_column` VALUES (23, 2, 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '0', NULL, NULL, NULL, 'EQ', 'input', '', 9, 'admin', '2023-11-28 14:12:24', '', '2023-12-04 16:42:59');
INSERT INTO `gen_table_column` VALUES (24, 2, 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', '0', '0', NULL, NULL, NULL, 'EQ', 'datetime', '', 10, 'admin', '2023-11-28 14:12:25', '', '2023-12-04 16:42:59');
INSERT INTO `gen_table_column` VALUES (25, 2, 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '0', '0', NULL, NULL, 'EQ', 'input', '', 11, 'admin', '2023-11-28 14:12:25', '', '2023-12-04 16:42:59');
INSERT INTO `gen_table_column` VALUES (26, 2, 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', '0', '0', '0', NULL, NULL, 'EQ', 'datetime', '', 12, 'admin', '2023-11-28 14:12:25', '', '2023-12-04 16:42:59');
INSERT INTO `gen_table_column` VALUES (27, 2, 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '0', '0', '0', NULL, 'EQ', 'textarea', '', 13, 'admin', '2023-11-28 14:12:25', '', '2023-12-04 16:42:59');
INSERT INTO `gen_table_column` VALUES (28, 3, 'id', '主键(自增策略)', 'bigint(20) unsigned', 'Long', 'id', '1', '1', NULL, '0', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2023-12-04 13:09:34', '', '2023-12-04 14:19:27');
INSERT INTO `gen_table_column` VALUES (29, 3, 'name', '应用名称', 'varchar(32)', 'String', 'name', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2023-12-04 13:09:34', '', '2023-12-04 14:19:27');
INSERT INTO `gen_table_column` VALUES (30, 3, 'type', '社交平台的类型', 'tinyint(4)', 'Integer', 'type', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'select', 'sys_social_type', 3, 'admin', '2023-12-04 13:09:34', '', '2023-12-04 14:19:27');
INSERT INTO `gen_table_column` VALUES (31, 3, 'app_key', '平台应用appKey', 'varchar(256)', 'String', 'appKey', '0', '0', NULL, '1', '1', '1', '0', 'EQ', 'input', '', 4, 'admin', '2023-12-04 13:09:34', '', '2023-12-04 14:19:27');
INSERT INTO `gen_table_column` VALUES (32, 3, 'app_secret', '平台应用 appSecret', 'varchar(256)', 'String', 'appSecret', '0', '0', NULL, '1', '1', '1', '0', 'EQ', 'input', '', 5, 'admin', '2023-12-04 13:09:34', '', '2023-12-04 14:19:27');
INSERT INTO `gen_table_column` VALUES (33, 3, 'status', '开启状态', 'char(1)', 'String', 'status', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'select', 'sys_normal_disable', 8, 'admin', '2023-12-04 13:09:34', '', '2023-12-04 14:19:27');
INSERT INTO `gen_table_column` VALUES (34, 3, 'del_flag', '删除标志（0代表存在 2代表删除）', 'char(1)', 'String', 'delFlag', '0', '0', NULL, '0', NULL, NULL, NULL, 'EQ', 'input', '', 9, 'admin', '2023-12-04 13:09:34', '', '2023-12-04 14:19:27');
INSERT INTO `gen_table_column` VALUES (35, 3, 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '0', NULL, NULL, NULL, 'EQ', 'input', '', 10, 'admin', '2023-12-04 13:09:34', '', '2023-12-04 14:19:27');
INSERT INTO `gen_table_column` VALUES (36, 3, 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', '0', '0', NULL, NULL, NULL, 'EQ', 'datetime', '', 11, 'admin', '2023-12-04 13:09:34', '', '2023-12-04 14:19:27');
INSERT INTO `gen_table_column` VALUES (37, 3, 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '0', '0', NULL, NULL, 'EQ', 'input', '', 12, 'admin', '2023-12-04 13:09:34', '', '2023-12-04 14:19:27');
INSERT INTO `gen_table_column` VALUES (38, 3, 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', '0', '0', '0', NULL, NULL, 'EQ', 'datetime', '', 13, 'admin', '2023-12-04 13:09:34', '', '2023-12-04 14:19:27');
INSERT INTO `gen_table_column` VALUES (39, 3, 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '0', '0', '0', NULL, 'EQ', 'textarea', '', 14, 'admin', '2023-12-04 13:09:34', '', '2023-12-04 14:19:27');
INSERT INTO `gen_table_column` VALUES (40, 3, 'code', '应用编码', 'varchar(50)', 'String', 'code', '0', '0', '1', '1', '1', '1', '0', 'EQ', 'input', '', 6, '', '2023-12-04 14:17:58', '', '2023-12-04 14:19:27');
INSERT INTO `gen_table_column` VALUES (41, 3, 'corp_id', '平台企业id', 'varchar(256)', 'String', 'corpId', '0', '0', NULL, '1', '1', '1', '0', 'EQ', 'input', '', 7, '', '2023-12-04 14:17:58', '', '2023-12-04 14:19:27');
INSERT INTO `gen_table_column` VALUES (42, 2, 'user_name', '用户登录账号', 'varchar(200)', 'String', 'userName', '0', '0', NULL, '0', '0', '1', '1', 'LIKE', 'input', '', 3, '', '2023-12-04 16:38:51', '', '2023-12-04 16:42:59');
INSERT INTO `gen_table_column` VALUES (43, 2, 'nick_name', '用户昵称', 'varchar(200)', 'String', 'nickName', '0', '0', NULL, '0', '0', '1', '1', 'LIKE', 'input', '', 4, '', '2023-12-04 16:38:51', '', '2023-12-04 16:42:59');
INSERT INTO `gen_table_column` VALUES (44, 2, 'social_uuid', '第三方平台用户唯一id', 'varchar(256)', 'String', 'socialUuid', '0', '0', '1', '0', '0', '1', '0', 'EQ', 'input', '', 6, '', '2023-12-04 16:38:51', '', '2023-12-04 16:42:59');
INSERT INTO `gen_table_column` VALUES (45, 2, 'email', '用户邮箱', 'varchar(200)', 'String', 'email', '0', '0', NULL, '0', '0', '1', '0', 'EQ', 'input', '', 7, '', '2023-12-04 16:38:51', '', '2023-12-04 16:42:59');

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob NULL COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Blob类型的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`, `calendar_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日历信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Cron类型的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint(13) NOT NULL COMMENT '触发的时间',
  `sched_time` bigint(13) NOT NULL COMMENT '定时器制定的时间',
  `priority` int(11) NOT NULL COMMENT '优先级',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`, `entry_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '已触发的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob NULL COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`, `lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存储的悲观锁信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`, `trigger_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '暂停的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint(13) NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint(13) NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`, `instance_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '调度器状态表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint(7) NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint(12) NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint(10) NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '简单触发器的信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int(11) NULL DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int(11) NULL DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint(20) NULL DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint(20) NULL DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '同步机制的行锁表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint(13) NULL DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint(13) NULL DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int(11) NULL DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的类型',
  `start_time` bigint(13) NOT NULL COMMENT '开始时间',
  `end_time` bigint(13) NULL DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint(2) NULL DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob NULL COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  INDEX `sched_name`(`sched_name`, `job_name`, `job_group`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '触发器详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2023-11-27 15:23:23', '', NULL, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2023-11-27 15:23:23', '', NULL, '初始化密码 123456');
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2023-11-27 15:23:23', '', NULL, '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` VALUES (4, '账号自助-验证码开关', 'sys.account.captchaEnabled', 'false', 'Y', 'admin', '2023-11-27 15:23:23', 'admin', '2023-11-27 16:22:27', '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'admin', '2023-11-27 15:23:23', '', NULL, '是否开启注册用户功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (6, '用户登录-黑名单列表', 'sys.login.blackIPList', '', 'Y', 'admin', '2023-11-27 15:23:23', '', NULL, '设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 200 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', '若依系统', 0, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-11-27 15:23:23', '', NULL);
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '深圳总公司', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-11-27 15:23:23', '', NULL);
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '研发部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-11-27 15:23:23', '', NULL);
INSERT INTO `sys_dept` VALUES (104, 101, '0,100,101', '市场部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-11-27 15:23:23', '', NULL);
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', '测试部门', 3, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2023-11-27 15:23:23', '', NULL);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '其他操作');
INSERT INTO `sys_dict_data` VALUES (19, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '新增操作');
INSERT INTO `sys_dict_data` VALUES (20, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '修改操作');
INSERT INTO `sys_dict_data` VALUES (21, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '删除操作');
INSERT INTO `sys_dict_data` VALUES (22, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '授权操作');
INSERT INTO `sys_dict_data` VALUES (23, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '导出操作');
INSERT INTO `sys_dict_data` VALUES (24, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '导入操作');
INSERT INTO `sys_dict_data` VALUES (25, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '强退操作');
INSERT INTO `sys_dict_data` VALUES (26, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '生成操作');
INSERT INTO `sys_dict_data` VALUES (27, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '清空操作');
INSERT INTO `sys_dict_data` VALUES (28, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (29, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (100, 0, '钉钉', 'DINGTALK', 'sys_social_type', NULL, 'primary', 'N', '0', 'admin', '2023-12-04 13:54:11', 'admin', '2023-12-06 11:00:02', NULL);
INSERT INTO `sys_dict_data` VALUES (101, 1, '微信公众平台', 'WECHAT_MP', 'sys_social_type', NULL, 'primary', 'N', '0', 'admin', '2023-12-04 13:54:25', 'admin', '2023-12-06 11:00:38', '公众号');
INSERT INTO `sys_dict_data` VALUES (102, 2, '微信小程序', 'WECHAT_OPEN', 'sys_social_type', NULL, 'primary', 'N', '0', 'admin', '2023-12-04 13:56:18', 'admin', '2023-12-06 11:00:50', NULL);
INSERT INTO `sys_dict_data` VALUES (103, 3, 'gitee', 'GITEE', 'sys_social_type', NULL, 'primary', 'N', '0', 'admin', '2023-12-04 13:56:37', 'admin', '2023-12-06 11:01:11', NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '登录状态列表');
INSERT INTO `sys_dict_type` VALUES (100, '第三方平台类型', 'sys_social_type', '0', 'admin', '2023-12-04 13:53:56', '', NULL, '第三方平台集成类型');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_job` VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_job` VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', 'admin', '2023-11-27 15:23:23', '', NULL, '');

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '异常信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE,
  INDEX `idx_sys_logininfor_s`(`status`) USING BTREE,
  INDEX `idx_sys_logininfor_lt`(`login_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 255 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES (100, 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'Mac OS X (iPhone)', '1', '用户不存在/密码错误', '2023-11-27 16:13:59');
INSERT INTO `sys_logininfor` VALUES (101, 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'Mac OS X (iPhone)', '1', '用户不存在/密码错误', '2023-11-27 16:14:08');
INSERT INTO `sys_logininfor` VALUES (102, 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'Mac OS X (iPhone)', '1', '验证码已失效', '2023-11-27 16:18:54');
INSERT INTO `sys_logininfor` VALUES (103, 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'Mac OS X (iPhone)', '1', '验证码已失效', '2023-11-27 16:18:54');
INSERT INTO `sys_logininfor` VALUES (104, 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'Mac OS X (iPhone)', '1', '用户不存在/密码错误', '2023-11-27 16:18:58');
INSERT INTO `sys_logininfor` VALUES (105, 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'Mac OS X (iPhone)', '1', NULL, '2023-11-27 16:19:06');
INSERT INTO `sys_logininfor` VALUES (106, 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'Mac OS X (iPhone)', '1', '用户不存在/密码错误', '2023-11-27 16:21:45');
INSERT INTO `sys_logininfor` VALUES (107, 'admin', '127.0.0.1', '内网IP', 'Mobile Safari', 'Mac OS X (iPhone)', '0', '登录成功', '2023-11-27 16:21:51');
INSERT INTO `sys_logininfor` VALUES (108, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '退出成功', '2023-11-27 16:27:42');
INSERT INTO `sys_logininfor` VALUES (109, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-11-28 09:30:50');
INSERT INTO `sys_logininfor` VALUES (110, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-11-28 13:20:30');
INSERT INTO `sys_logininfor` VALUES (111, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-11-28 14:12:10');
INSERT INTO `sys_logininfor` VALUES (112, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-12-04 13:09:03');
INSERT INTO `sys_logininfor` VALUES (113, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-12-04 13:38:31');
INSERT INTO `sys_logininfor` VALUES (114, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-12-04 13:42:12');
INSERT INTO `sys_logininfor` VALUES (115, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-12-04 15:25:02');
INSERT INTO `sys_logininfor` VALUES (116, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-12-04 16:08:01');
INSERT INTO `sys_logininfor` VALUES (117, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-12-04 17:21:08');
INSERT INTO `sys_logininfor` VALUES (118, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-12-05 09:02:00');
INSERT INTO `sys_logininfor` VALUES (119, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-12-05 16:51:13');
INSERT INTO `sys_logininfor` VALUES (120, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '退出成功', '2023-12-05 16:51:48');
INSERT INTO `sys_logininfor` VALUES (121, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-12-06 10:53:42');
INSERT INTO `sys_logininfor` VALUES (122, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '退出成功', '2023-12-06 10:53:47');
INSERT INTO `sys_logininfor` VALUES (123, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-12-06 10:53:49');
INSERT INTO `sys_logininfor` VALUES (124, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '退出成功', '2023-12-06 10:53:53');
INSERT INTO `sys_logininfor` VALUES (125, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-12-06 10:54:02');
INSERT INTO `sys_logininfor` VALUES (126, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '退出成功', '2023-12-06 10:54:21');
INSERT INTO `sys_logininfor` VALUES (127, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-12-06 10:54:23');
INSERT INTO `sys_logininfor` VALUES (128, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '登录成功', '2023-12-06 13:22:19');
INSERT INTO `sys_logininfor` VALUES (129, 'admin', '127.0.0.1', '内网IP', 'Chrome 11', 'Windows 10', '0', '退出成功', '2023-12-06 13:22:45');
INSERT INTO `sys_logininfor` VALUES (130, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-19 09:29:39');
INSERT INTO `sys_logininfor` VALUES (131, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-19 09:30:43');
INSERT INTO `sys_logininfor` VALUES (132, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-19 10:02:09');
INSERT INTO `sys_logininfor` VALUES (133, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-19 10:02:12');
INSERT INTO `sys_logininfor` VALUES (134, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-19 10:17:39');
INSERT INTO `sys_logininfor` VALUES (135, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-19 10:17:43');
INSERT INTO `sys_logininfor` VALUES (136, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-19 10:32:31');
INSERT INTO `sys_logininfor` VALUES (137, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-19 10:37:21');
INSERT INTO `sys_logininfor` VALUES (138, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-19 11:20:45');
INSERT INTO `sys_logininfor` VALUES (139, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-19 13:11:20');
INSERT INTO `sys_logininfor` VALUES (140, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-19 17:02:57');
INSERT INTO `sys_logininfor` VALUES (141, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-19 17:03:42');
INSERT INTO `sys_logininfor` VALUES (142, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-19 17:03:52');
INSERT INTO `sys_logininfor` VALUES (143, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-19 17:19:59');
INSERT INTO `sys_logininfor` VALUES (144, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-19 17:20:46');
INSERT INTO `sys_logininfor` VALUES (145, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 10:23:21');
INSERT INTO `sys_logininfor` VALUES (146, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 11:06:41');
INSERT INTO `sys_logininfor` VALUES (147, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 11:06:42');
INSERT INTO `sys_logininfor` VALUES (148, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 11:19:46');
INSERT INTO `sys_logininfor` VALUES (149, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 11:19:46');
INSERT INTO `sys_logininfor` VALUES (150, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 11:20:36');
INSERT INTO `sys_logininfor` VALUES (151, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 11:20:37');
INSERT INTO `sys_logininfor` VALUES (152, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 11:20:41');
INSERT INTO `sys_logininfor` VALUES (153, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 11:20:42');
INSERT INTO `sys_logininfor` VALUES (154, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 11:20:46');
INSERT INTO `sys_logininfor` VALUES (155, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 11:20:46');
INSERT INTO `sys_logininfor` VALUES (156, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 11:21:30');
INSERT INTO `sys_logininfor` VALUES (157, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 11:22:23');
INSERT INTO `sys_logininfor` VALUES (158, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 11:22:27');
INSERT INTO `sys_logininfor` VALUES (159, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 11:30:38');
INSERT INTO `sys_logininfor` VALUES (160, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 13:10:41');
INSERT INTO `sys_logininfor` VALUES (161, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 13:30:35');
INSERT INTO `sys_logininfor` VALUES (162, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 13:30:46');
INSERT INTO `sys_logininfor` VALUES (163, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 13:32:25');
INSERT INTO `sys_logininfor` VALUES (164, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 13:32:27');
INSERT INTO `sys_logininfor` VALUES (165, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 13:32:36');
INSERT INTO `sys_logininfor` VALUES (166, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 13:32:48');
INSERT INTO `sys_logininfor` VALUES (167, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 13:53:45');
INSERT INTO `sys_logininfor` VALUES (168, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 13:53:47');
INSERT INTO `sys_logininfor` VALUES (169, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '1', '密码输入错误1次', '2023-12-20 14:04:21');
INSERT INTO `sys_logininfor` VALUES (170, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '1', '用户不存在/密码错误', '2023-12-20 14:04:21');
INSERT INTO `sys_logininfor` VALUES (171, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '1', '密码输入错误2次', '2023-12-20 14:06:43');
INSERT INTO `sys_logininfor` VALUES (172, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '1', '用户不存在/密码错误', '2023-12-20 14:06:43');
INSERT INTO `sys_logininfor` VALUES (173, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 14:13:35');
INSERT INTO `sys_logininfor` VALUES (174, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 14:19:57');
INSERT INTO `sys_logininfor` VALUES (175, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 14:21:00');
INSERT INTO `sys_logininfor` VALUES (176, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 14:23:04');
INSERT INTO `sys_logininfor` VALUES (177, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 14:26:47');
INSERT INTO `sys_logininfor` VALUES (178, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:02:29');
INSERT INTO `sys_logininfor` VALUES (179, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 15:07:39');
INSERT INTO `sys_logininfor` VALUES (180, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 15:07:39');
INSERT INTO `sys_logininfor` VALUES (181, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:07:55');
INSERT INTO `sys_logininfor` VALUES (182, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:09:06');
INSERT INTO `sys_logininfor` VALUES (183, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:14:55');
INSERT INTO `sys_logininfor` VALUES (184, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:16:22');
INSERT INTO `sys_logininfor` VALUES (185, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 15:16:40');
INSERT INTO `sys_logininfor` VALUES (186, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:16:51');
INSERT INTO `sys_logininfor` VALUES (187, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:23:27');
INSERT INTO `sys_logininfor` VALUES (188, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:26:39');
INSERT INTO `sys_logininfor` VALUES (189, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:28:42');
INSERT INTO `sys_logininfor` VALUES (190, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:32:26');
INSERT INTO `sys_logininfor` VALUES (191, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:34:25');
INSERT INTO `sys_logininfor` VALUES (192, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:34:41');
INSERT INTO `sys_logininfor` VALUES (193, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:36:24');
INSERT INTO `sys_logininfor` VALUES (194, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:37:39');
INSERT INTO `sys_logininfor` VALUES (195, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:38:00');
INSERT INTO `sys_logininfor` VALUES (196, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:38:58');
INSERT INTO `sys_logininfor` VALUES (197, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:46:23');
INSERT INTO `sys_logininfor` VALUES (198, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:46:47');
INSERT INTO `sys_logininfor` VALUES (199, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:51:44');
INSERT INTO `sys_logininfor` VALUES (200, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:56:00');
INSERT INTO `sys_logininfor` VALUES (201, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:58:40');
INSERT INTO `sys_logininfor` VALUES (202, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 15:58:57');
INSERT INTO `sys_logininfor` VALUES (203, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 15:59:04');
INSERT INTO `sys_logininfor` VALUES (204, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:13:14');
INSERT INTO `sys_logininfor` VALUES (205, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:13:33');
INSERT INTO `sys_logininfor` VALUES (206, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:13:54');
INSERT INTO `sys_logininfor` VALUES (207, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:15:32');
INSERT INTO `sys_logininfor` VALUES (208, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:16:49');
INSERT INTO `sys_logininfor` VALUES (209, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 16:17:43');
INSERT INTO `sys_logininfor` VALUES (210, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:19:42');
INSERT INTO `sys_logininfor` VALUES (211, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 16:21:23');
INSERT INTO `sys_logininfor` VALUES (212, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:21:29');
INSERT INTO `sys_logininfor` VALUES (213, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:25:29');
INSERT INTO `sys_logininfor` VALUES (214, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 16:26:12');
INSERT INTO `sys_logininfor` VALUES (215, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:27:03');
INSERT INTO `sys_logininfor` VALUES (216, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 16:27:45');
INSERT INTO `sys_logininfor` VALUES (217, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:28:20');
INSERT INTO `sys_logininfor` VALUES (218, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 16:29:26');
INSERT INTO `sys_logininfor` VALUES (219, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:29:58');
INSERT INTO `sys_logininfor` VALUES (220, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 16:30:16');
INSERT INTO `sys_logininfor` VALUES (221, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:33:24');
INSERT INTO `sys_logininfor` VALUES (222, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:33:34');
INSERT INTO `sys_logininfor` VALUES (223, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:34:29');
INSERT INTO `sys_logininfor` VALUES (224, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:43:52');
INSERT INTO `sys_logininfor` VALUES (225, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-20 16:46:07');
INSERT INTO `sys_logininfor` VALUES (226, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:46:59');
INSERT INTO `sys_logininfor` VALUES (227, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:56:30');
INSERT INTO `sys_logininfor` VALUES (228, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:58:51');
INSERT INTO `sys_logininfor` VALUES (229, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:59:09');
INSERT INTO `sys_logininfor` VALUES (230, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:59:25');
INSERT INTO `sys_logininfor` VALUES (231, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-20 16:59:43');
INSERT INTO `sys_logininfor` VALUES (232, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-21 14:05:43');
INSERT INTO `sys_logininfor` VALUES (233, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-22 08:50:43');
INSERT INTO `sys_logininfor` VALUES (234, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-22 09:13:18');
INSERT INTO `sys_logininfor` VALUES (235, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-22 09:25:10');
INSERT INTO `sys_logininfor` VALUES (236, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-22 09:26:21');
INSERT INTO `sys_logininfor` VALUES (237, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-22 09:59:46');
INSERT INTO `sys_logininfor` VALUES (238, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-22 09:59:50');
INSERT INTO `sys_logininfor` VALUES (239, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-22 10:00:45');
INSERT INTO `sys_logininfor` VALUES (240, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-22 10:01:06');
INSERT INTO `sys_logininfor` VALUES (241, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-22 10:01:12');
INSERT INTO `sys_logininfor` VALUES (242, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-22 10:01:21');
INSERT INTO `sys_logininfor` VALUES (243, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-22 10:15:40');
INSERT INTO `sys_logininfor` VALUES (244, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-22 10:41:11');
INSERT INTO `sys_logininfor` VALUES (245, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-22 10:42:00');
INSERT INTO `sys_logininfor` VALUES (246, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-22 10:43:26');
INSERT INTO `sys_logininfor` VALUES (247, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-22 10:43:44');
INSERT INTO `sys_logininfor` VALUES (248, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-22 10:48:52');
INSERT INTO `sys_logininfor` VALUES (249, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-22 10:48:57');
INSERT INTO `sys_logininfor` VALUES (250, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-22 13:17:43');
INSERT INTO `sys_logininfor` VALUES (251, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '退出成功', '2023-12-22 13:17:46');
INSERT INTO `sys_logininfor` VALUES (252, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-22 13:17:50');
INSERT INTO `sys_logininfor` VALUES (253, 'admin', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-22 14:18:02');
INSERT INTO `sys_logininfor` VALUES (254, 'user', '127.0.0.1', '内网IP', 'Chrome 12', 'Windows 10', '0', '登录成功', '2023-12-22 14:34:42');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由参数',
  `is_frame` int(1) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2029 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, '', 1, 0, 'M', '0', '0', '', 'system', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 2, 'monitor', NULL, '', 1, 0, 'M', '0', '0', '', 'monitor', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '系统监控目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 3, 'tool', NULL, '', 1, 0, 'M', '0', '0', '', 'tool', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '系统工具目录');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', '', 1, 0, 'C', '0', '0', 'system:post:list', 'post', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '参数设置菜单');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '通知公告菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, 'log', '', '', 1, 0, 'M', '0', '0', '', 'log', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '日志管理菜单');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '在线用户菜单');
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', '', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '定时任务菜单');
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '数据监控菜单');
INSERT INTO `sys_menu` VALUES (112, '服务监控', 2, 4, 'server', 'monitor/server/index', '', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '服务监控菜单');
INSERT INTO `sys_menu` VALUES (113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '缓存监控菜单');
INSERT INTO `sys_menu` VALUES (114, '缓存列表', 2, 6, 'cacheList', 'monitor/cache/list', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis-list', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '缓存列表菜单');
INSERT INTO `sys_menu` VALUES (116, '代码生成', 3, 2, 'gen', 'tool/gen/index', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '代码生成菜单');
INSERT INTO `sys_menu` VALUES (117, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', '', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '系统接口菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1000, '用户查询', 100, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1001, '用户新增', 100, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1002, '用户修改', 100, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1003, '用户删除', 100, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1004, '用户导出', 100, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1005, '用户导入', 100, 6, '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1006, '重置密码', 100, 7, '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1007, '角色查询', 101, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1008, '角色新增', 101, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1009, '角色修改', 101, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1010, '角色删除', 101, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1011, '角色导出', 101, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1012, '菜单查询', 102, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1013, '菜单新增', 102, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1014, '菜单修改', 102, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1015, '菜单删除', 102, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1016, '部门查询', 103, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1017, '部门新增', 103, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1018, '部门修改', 103, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1019, '部门删除', 103, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1020, '岗位查询', 104, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1021, '岗位新增', 104, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1022, '岗位修改', 104, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1023, '岗位删除', 104, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1024, '岗位导出', 104, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1025, '字典查询', 105, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1026, '字典新增', 105, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1027, '字典修改', 105, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1028, '字典删除', 105, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1029, '字典导出', 105, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1030, '参数查询', 106, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1031, '参数新增', 106, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1032, '参数修改', 106, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1033, '参数删除', 106, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1034, '参数导出', 106, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1035, '公告查询', 107, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1036, '公告新增', 107, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1037, '公告修改', 107, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1038, '公告删除', 107, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1039, '操作查询', 500, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1040, '操作删除', 500, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1041, '日志导出', 500, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1042, '登录查询', 501, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1043, '登录删除', 501, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1044, '日志导出', 501, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1045, '账户解锁', 501, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1049, '任务查询', 110, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1050, '任务新增', 110, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1051, '任务修改', 110, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1052, '任务删除', 110, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1053, '状态修改', 110, 5, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1054, '任务导出', 110, 6, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1055, '生成查询', 116, 1, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1056, '生成修改', 116, 2, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1057, '生成删除', 116, 3, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1058, '导入代码', 116, 4, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1059, '预览代码', 116, 5, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1060, '生成代码', 116, 6, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2000, '三方登录', 0, 4, 'social', NULL, NULL, 1, 0, 'M', '0', '0', '', 'button', '0', 'admin', '2023-12-04 13:19:27', 'admin', '2023-12-04 13:19:45', '');
INSERT INTO `sys_menu` VALUES (2013, '三方应用', 2000, 1, 'socialApp', 'system/socialApp/index', NULL, 1, 0, 'C', '0', '0', 'system:socialApp:list', 'button', '0', 'admin', '2023-12-04 14:20:15', 'admin', '2023-12-04 14:21:21', '社交应用参数菜单');
INSERT INTO `sys_menu` VALUES (2014, '社交应用参数查询', 2013, 1, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:socialApp:query', '#', '0', 'admin', '2023-12-04 14:20:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2015, '社交应用参数新增', 2013, 2, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:socialApp:add', '#', '0', 'admin', '2023-12-04 14:20:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2016, '社交应用参数修改', 2013, 3, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:socialApp:edit', '#', '0', 'admin', '2023-12-04 14:20:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2017, '社交应用参数删除', 2013, 4, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:socialApp:remove', '#', '0', 'admin', '2023-12-04 14:20:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2018, '社交应用参数导出', 2013, 5, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:socialApp:export', '#', '0', 'admin', '2023-12-04 14:20:15', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2019, '三方用户', 2000, 1, 'socialUserBind', 'system/socialUserBind/index', NULL, 1, 0, 'C', '0', '0', 'system:socialUserBind:list', 'button', '0', 'admin', '2023-12-04 16:43:31', 'user', '2023-12-22 10:17:09', '三方用户菜单');
INSERT INTO `sys_menu` VALUES (2020, '三方用户查询', 2019, 1, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:socialUserBind:query', '#', '0', 'admin', '2023-12-04 16:43:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2021, '三方用户新增', 2019, 2, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:socialUserBind:add', '#', '0', 'admin', '2023-12-04 16:43:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2022, '三方用户修改', 2019, 3, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:socialUserBind:edit', '#', '0', 'admin', '2023-12-04 16:43:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2023, '三方用户删除', 2019, 4, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:socialUserBind:remove', '#', '0', 'admin', '2023-12-04 16:43:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2024, '三方用户导出', 2019, 5, '#', '', NULL, 1, 0, 'F', '0', '0', 'system:socialUserBind:export', '#', '0', 'admin', '2023-12-04 16:43:31', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2025, '工作流', 0, 6, '#', NULL, NULL, 1, 0, 'M', '0', '0', '', 'activiti', '0', 'admin', '2023-12-22 10:46:40', 'admin', '2023-12-22 10:51:08', '工作流菜单');
INSERT INTO `sys_menu` VALUES (2026, '流程管理', 2025, 1, '#', NULL, NULL, 1, 0, 'M', '0', '0', '', 'activiti', '0', 'admin', '2023-12-22 14:18:30', 'admin', '2023-12-22 14:18:55', '');
INSERT INTO `sys_menu` VALUES (2027, '任务管理', 2025, 2, '#', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'activiti', '0', 'admin', '2023-12-22 14:19:28', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2028, '流程示例(请假)', 2025, 3, '#', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'activiti', '0', 'admin', '2023-12-22 14:20:35', '', NULL, '');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob NULL COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '温馨提醒：2018-07-01 若依新版本发布啦', '2', 0xE696B0E78988E69CACE58685E5AEB9, '0', 'admin', '2023-11-27 15:23:23', '', NULL, '管理员');
INSERT INTO `sys_notice` VALUES (2, '维护通知：2018-07-01 若依系统凌晨维护', '1', 0xE7BBB4E68AA4E58685E5AEB9, '0', 'admin', '2023-11-27 15:23:23', '', NULL, '管理员');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int(1) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint(20) NULL DEFAULT 0 COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`) USING BTREE,
  INDEX `idx_sys_oper_log_bt`(`business_type`) USING BTREE,
  INDEX `idx_sys_oper_log_s`(`status`) USING BTREE,
  INDEX `idx_sys_oper_log_ot`(`oper_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 235 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES (100, '参数管理', 2, 'com.ruoyi.web.controller.system.SysConfigController.edit()', 'PUT', 1, 'admin', NULL, '/system/config', '127.0.0.1', '内网IP', '{\"configId\":4,\"configKey\":\"sys.account.captchaEnabled\",\"configName\":\"账号自助-验证码开关\",\"configType\":\"Y\",\"configValue\":\"false\",\"createBy\":\"admin\",\"createTime\":\"2023-11-27 15:23:23\",\"params\":{},\"remark\":\"是否开启验证码功能（true开启，false关闭）\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-11-27 16:22:27', 129);
INSERT INTO `sys_oper_log` VALUES (101, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/4', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"菜单已分配,不允许删除\",\"code\":601}', 0, NULL, '2023-11-27 16:23:06', 57);
INSERT INTO `sys_oper_log` VALUES (102, '角色管理', 4, 'com.ruoyi.web.controller.system.SysRoleController.cancelAuthUser()', 'PUT', 1, 'admin', NULL, '/system/role/authUser/cancel', '127.0.0.1', '内网IP', '{\"roleId\":2,\"userId\":2}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-11-27 16:23:17', 55);
INSERT INTO `sys_oper_log` VALUES (103, '角色管理', 2, 'com.ruoyi.web.controller.system.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '127.0.0.1', '内网IP', '{\"admin\":false,\"createTime\":\"2023-11-27 15:23:23\",\"dataScope\":\"2\",\"delFlag\":\"0\",\"deptCheckStrictly\":true,\"flag\":false,\"menuCheckStrictly\":true,\"menuIds\":[],\"params\":{},\"remark\":\"普通角色\",\"roleId\":2,\"roleKey\":\"common\",\"roleName\":\"普通角色\",\"roleSort\":2,\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-11-27 16:23:33', 202);
INSERT INTO `sys_oper_log` VALUES (104, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/4', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-11-27 16:23:37', 133);
INSERT INTO `sys_oper_log` VALUES (105, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/115', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-11-27 16:24:28', 76);
INSERT INTO `sys_oper_log` VALUES (106, '用户管理', 2, 'com.ruoyi.web.controller.system.SysUserController.edit()', 'PUT', 1, 'admin', NULL, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"avatar\":\"\",\"createBy\":\"admin\",\"createTime\":\"2023-11-27 15:23:23\",\"delFlag\":\"0\",\"dept\":{\"ancestors\":\"0,100,101\",\"children\":[],\"deptId\":105,\"deptName\":\"测试部门\",\"leader\":\"若依\",\"orderNum\":3,\"params\":{},\"parentId\":101,\"status\":\"0\"},\"deptId\":105,\"email\":\"ry@qq.com\",\"loginDate\":\"2023-11-27 15:23:23\",\"loginIp\":\"127.0.0.1\",\"nickName\":\"用户\",\"params\":{},\"phonenumber\":\"15666666666\",\"postIds\":[2],\"remark\":\"测试员\",\"roleIds\":[],\"roles\":[],\"sex\":\"1\",\"status\":\"0\",\"updateBy\":\"admin\",\"userId\":2,\"userName\":\"ry\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-11-28 13:20:52', 290);
INSERT INTO `sys_oper_log` VALUES (107, '部门管理', 3, 'com.ruoyi.web.controller.system.SysDeptController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dept/109', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-11-28 13:22:48', 90);
INSERT INTO `sys_oper_log` VALUES (108, '部门管理', 3, 'com.ruoyi.web.controller.system.SysDeptController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dept/108', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-11-28 13:22:49', 81);
INSERT INTO `sys_oper_log` VALUES (109, '部门管理', 3, 'com.ruoyi.web.controller.system.SysDeptController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dept/102', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-11-28 13:22:51', 92);
INSERT INTO `sys_oper_log` VALUES (110, '部门管理', 3, 'com.ruoyi.web.controller.system.SysDeptController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dept/107', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-11-28 13:22:53', 76);
INSERT INTO `sys_oper_log` VALUES (111, '部门管理', 3, 'com.ruoyi.web.controller.system.SysDeptController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dept/106', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-11-28 13:22:56', 74);
INSERT INTO `sys_oper_log` VALUES (112, '部门管理', 3, 'com.ruoyi.web.controller.system.SysDeptController.remove()', 'DELETE', 1, 'admin', NULL, '/system/dept/105', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"部门存在用户,不允许删除\",\"code\":601}', 0, NULL, '2023-11-28 13:22:59', 32);
INSERT INTO `sys_oper_log` VALUES (113, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', NULL, '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"sys_social_user_bind,sys_social_user\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-11-28 14:12:25', 1210);
INSERT INTO `sys_oper_log` VALUES (114, '代码生成', 6, 'com.ruoyi.generator.controller.GenController.importTableSave()', 'POST', 1, 'admin', NULL, '/tool/gen/importTable', '127.0.0.1', '内网IP', '{\"tables\":\"sys_social_app\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:09:34', 621);
INSERT INTO `sys_oper_log` VALUES (115, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"app\",\"className\":\"SocialApp\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"主键(自增策略)\",\"columnId\":28,\"columnName\":\"id\",\"columnType\":\"bigint(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"应用名称\",\"columnId\":29,\"columnName\":\"name\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Type\",\"columnComment\":\"社交平台的类型\",\"columnId\":30,\"columnName\":\"type\",\"columnType\":\"tinyint(4)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"select\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"type\",\"javaType\":\"Integer\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"AppKey\",\"columnComment\":\"各平台对应的 appKey\",\"columnId\":31,\"columnName\":\"app_key\",\"columnType\":\"varchar(256)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"appKey\",\"javaType\":\"String\",\"l', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:13:16', 631);
INSERT INTO `sys_oper_log` VALUES (116, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"app\",\"className\":\"SocialApp\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"主键(自增策略)\",\"columnId\":28,\"columnName\":\"id\",\"columnType\":\"bigint(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:13:15\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"应用名称\",\"columnId\":29,\"columnName\":\"name\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:13:15\",\"usableColumn\":false},{\"capJavaField\":\"Type\",\"columnComment\":\"社交平台的类型\",\"columnId\":30,\"columnName\":\"type\",\"columnType\":\"tinyint(4)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"sys_yes_no\",\"edit\":true,\"htmlType\":\"select\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"type\",\"javaType\":\"Integer\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:13:16\",\"usableColumn\":false},{\"capJavaField\":\"AppKey\",\"columnComment\":\"各平台对应的 appKey\",\"columnId\":31,\"columnName\":\"app_key\",\"columnType\":\"varchar(256)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:15:02', 310);
INSERT INTO `sys_oper_log` VALUES (117, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"app\",\"className\":\"SocialApp\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"主键(自增策略)\",\"columnId\":28,\"columnName\":\"id\",\"columnType\":\"bigint(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:15:02\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"应用名称\",\"columnId\":29,\"columnName\":\"name\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:15:02\",\"usableColumn\":false},{\"capJavaField\":\"Type\",\"columnComment\":\"社交平台的类型\",\"columnId\":30,\"columnName\":\"type\",\"columnType\":\"tinyint(4)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"sys_yes_no\",\"edit\":true,\"htmlType\":\"select\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"type\",\"javaType\":\"Integer\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:15:02\",\"usableColumn\":false},{\"capJavaField\":\"AppKey\",\"columnComment\":\"各平台对应的 appKey\",\"columnId\":31,\"columnName\":\"app_key\",\"columnType\":\"varchar(256)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:15:33', 366);
INSERT INTO `sys_oper_log` VALUES (118, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"SocialApp\",\"className\":\"SocialApp\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"主键(自增策略)\",\"columnId\":28,\"columnName\":\"id\",\"columnType\":\"bigint(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:15:33\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"应用名称\",\"columnId\":29,\"columnName\":\"name\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:15:33\",\"usableColumn\":false},{\"capJavaField\":\"Type\",\"columnComment\":\"社交平台的类型\",\"columnId\":30,\"columnName\":\"type\",\"columnType\":\"tinyint(4)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"sys_yes_no\",\"edit\":true,\"htmlType\":\"select\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"type\",\"javaType\":\"Integer\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:15:33\",\"usableColumn\":false},{\"capJavaField\":\"AppKey\",\"columnComment\":\"各平台对应的 appKey\",\"columnId\":31,\"columnName\":\"app_key\",\"columnType\":\"varchar(256)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEd', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:17:24', 409);
INSERT INTO `sys_oper_log` VALUES (119, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createBy\":\"admin\",\"icon\":\"button\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"三方登录\",\"menuType\":\"M\",\"orderNum\":4,\"params\":{},\"parentId\":0,\"path\":\"/\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:19:27', 53);
INSERT INTO `sys_oper_log` VALUES (120, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2023-12-04 13:19:27\",\"icon\":\"button\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2000,\"menuName\":\"三方登录\",\"menuType\":\"M\",\"orderNum\":4,\"params\":{},\"parentId\":0,\"path\":\"system\",\"perms\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:19:45', 46);
INSERT INTO `sys_oper_log` VALUES (121, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"SocialApp\",\"className\":\"SocialApp\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"主键(自增策略)\",\"columnId\":28,\"columnName\":\"id\",\"columnType\":\"bigint(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:17:23\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"应用名称\",\"columnId\":29,\"columnName\":\"name\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:17:23\",\"usableColumn\":false},{\"capJavaField\":\"Type\",\"columnComment\":\"社交平台的类型\",\"columnId\":30,\"columnName\":\"type\",\"columnType\":\"tinyint(4)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"sys_yes_no\",\"edit\":true,\"htmlType\":\"select\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"type\",\"javaType\":\"Integer\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:17:23\",\"usableColumn\":false},{\"capJavaField\":\"AppKey\",\"columnComment\":\"各平台对应的 appKey\",\"columnId\":31,\"columnName\":\"app_key\",\"columnType\":\"varchar(256)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEd', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:19:59', 386);
INSERT INTO `sys_oper_log` VALUES (122, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"sys_social_app\"}', NULL, 0, NULL, '2023-12-04 13:20:01', 124);
INSERT INTO `sys_oper_log` VALUES (123, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"SocialApp\",\"className\":\"SysSocialApp\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"主键(自增策略)\",\"columnId\":28,\"columnName\":\"id\",\"columnType\":\"bigint(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:19:58\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"应用名称\",\"columnId\":29,\"columnName\":\"name\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:19:58\",\"usableColumn\":false},{\"capJavaField\":\"Type\",\"columnComment\":\"社交平台的类型\",\"columnId\":30,\"columnName\":\"type\",\"columnType\":\"tinyint(4)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"sys_yes_no\",\"edit\":true,\"htmlType\":\"select\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"type\",\"javaType\":\"Integer\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:19:58\",\"usableColumn\":false},{\"capJavaField\":\"AppKey\",\"columnComment\":\"各平台对应的 appKey\",\"columnId\":31,\"columnName\":\"app_key\",\"columnType\":\"varchar(256)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"i', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:23:50', 359);
INSERT INTO `sys_oper_log` VALUES (124, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"sys_social_app\"}', NULL, 0, NULL, '2023-12-04 13:23:53', 97);
INSERT INTO `sys_oper_log` VALUES (125, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"SocialApp\",\"className\":\"SysSocialApp\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"主键(自增策略)\",\"columnId\":28,\"columnName\":\"id\",\"columnType\":\"bigint(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:23:49\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"应用名称\",\"columnId\":29,\"columnName\":\"name\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:23:49\",\"usableColumn\":false},{\"capJavaField\":\"Type\",\"columnComment\":\"社交平台的类型\",\"columnId\":30,\"columnName\":\"type\",\"columnType\":\"tinyint(4)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"sys_yes_no\",\"edit\":true,\"htmlType\":\"select\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"type\",\"javaType\":\"Integer\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:23:49\",\"usableColumn\":false},{\"capJavaField\":\"AppKey\",\"columnComment\":\"各平台对应的 appKey\",\"columnId\":31,\"columnName\":\"app_key\",\"columnType\":\"varchar(256)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"i', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:28:33', 1144);
INSERT INTO `sys_oper_log` VALUES (126, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"socialApp\",\"className\":\"SysSocialApp\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"主键(自增策略)\",\"columnId\":28,\"columnName\":\"id\",\"columnType\":\"bigint(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:28:32\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"应用名称\",\"columnId\":29,\"columnName\":\"name\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:28:32\",\"usableColumn\":false},{\"capJavaField\":\"Type\",\"columnComment\":\"社交平台的类型\",\"columnId\":30,\"columnName\":\"type\",\"columnType\":\"tinyint(4)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"sys_yes_no\",\"edit\":true,\"htmlType\":\"select\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"type\",\"javaType\":\"Integer\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 13:28:32\",\"usableColumn\":false},{\"capJavaField\":\"AppKey\",\"columnComment\":\"各平台对应的 appKey\",\"columnId\":31,\"columnName\":\"app_key\",\"columnType\":\"varchar(256)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"i', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:29:03', 326);
INSERT INTO `sys_oper_log` VALUES (127, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"sys_social_app\"}', NULL, 0, NULL, '2023-12-04 13:29:19', 71);
INSERT INTO `sys_oper_log` VALUES (128, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"sys_social_app\"}', NULL, 0, NULL, '2023-12-04 13:30:31', 77);
INSERT INTO `sys_oper_log` VALUES (129, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2001', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"存在子菜单,不允许删除\",\"code\":601}', 0, NULL, '2023-12-04 13:31:22', 16);
INSERT INTO `sys_oper_log` VALUES (130, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2002', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:31:24', 54);
INSERT INTO `sys_oper_log` VALUES (131, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2006', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:31:27', 46);
INSERT INTO `sys_oper_log` VALUES (132, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2005', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:31:30', 54);
INSERT INTO `sys_oper_log` VALUES (133, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2004', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:31:33', 60);
INSERT INTO `sys_oper_log` VALUES (134, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2003', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:31:34', 47);
INSERT INTO `sys_oper_log` VALUES (135, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2001', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:31:37', 54);
INSERT INTO `sys_oper_log` VALUES (136, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"sys_social_app\"}', NULL, 0, NULL, '2023-12-04 13:43:45', 16089);
INSERT INTO `sys_oper_log` VALUES (137, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"sys_social_app\"}', NULL, 0, NULL, '2023-12-04 13:46:39', 85);
INSERT INTO `sys_oper_log` VALUES (138, '字典类型', 1, 'com.ruoyi.web.controller.system.SysDictTypeController.add()', 'POST', 1, 'admin', NULL, '/system/dict/type', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"dictName\":\"第三方平台类型\",\"dictType\":\"sys_social_type\",\"params\":{},\"remark\":\"第三方平台集成类型\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:53:56', 74);
INSERT INTO `sys_oper_log` VALUES (139, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"0\",\"dictSort\":0,\"dictType\":\"sys_social_type\",\"dictValue\":\"微信\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:54:11', 166);
INSERT INTO `sys_oper_log` VALUES (140, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"1\",\"dictSort\":1,\"dictType\":\"sys_social_type\",\"dictValue\":\"钉钉\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:54:25', 68);
INSERT INTO `sys_oper_log` VALUES (141, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', NULL, '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:54:11\",\"default\":false,\"dictCode\":100,\"dictLabel\":\"0\",\"dictSort\":0,\"dictType\":\"sys_social_type\",\"dictValue\":\"钉钉\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:55:25', 64);
INSERT INTO `sys_oper_log` VALUES (142, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', NULL, '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:54:25\",\"default\":false,\"dictCode\":101,\"dictLabel\":\"1\",\"dictSort\":1,\"dictType\":\"sys_social_type\",\"dictValue\":\"微信公众平台\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"remark\":\"\",\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:55:40', 165);
INSERT INTO `sys_oper_log` VALUES (143, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', NULL, '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:54:25\",\"default\":false,\"dictCode\":101,\"dictLabel\":\"1\",\"dictSort\":1,\"dictType\":\"sys_social_type\",\"dictValue\":\"微信公众平台\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"remark\":\"公众号\",\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:56:04', 79);
INSERT INTO `sys_oper_log` VALUES (144, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"2\",\"dictSort\":2,\"dictType\":\"sys_social_type\",\"dictValue\":\"微信小程序\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:56:18', 54);
INSERT INTO `sys_oper_log` VALUES (145, '字典数据', 1, 'com.ruoyi.web.controller.system.SysDictDataController.add()', 'POST', 1, 'admin', NULL, '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"default\":false,\"dictLabel\":\"3\",\"dictSort\":3,\"dictType\":\"sys_social_type\",\"dictValue\":\"企业微信\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:56:38', 109);
INSERT INTO `sys_oper_log` VALUES (146, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"system/socialApp/index\",\"createTime\":\"2023-12-04 13:31:05\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2007,\"menuName\":\"三方应用\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2000,\"path\":\"socialApp\",\"perms\":\"system:socialApp:list\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 13:58:16', 88);
INSERT INTO `sys_oper_log` VALUES (147, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', NULL, '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:54:11\",\"default\":false,\"dictCode\":100,\"dictLabel\":\"钉钉\",\"dictSort\":0,\"dictType\":\"sys_social_type\",\"dictValue\":\"0\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:00:33', 66);
INSERT INTO `sys_oper_log` VALUES (148, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', NULL, '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:54:25\",\"default\":false,\"dictCode\":101,\"dictLabel\":\"微信公众平台\",\"dictSort\":1,\"dictType\":\"sys_social_type\",\"dictValue\":\"1\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"remark\":\"公众号\",\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:00:38', 56);
INSERT INTO `sys_oper_log` VALUES (149, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', NULL, '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:56:18\",\"default\":false,\"dictCode\":102,\"dictLabel\":\"微信小程序\",\"dictSort\":2,\"dictType\":\"sys_social_type\",\"dictValue\":\"2\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:00:46', 54);
INSERT INTO `sys_oper_log` VALUES (150, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', NULL, '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:56:37\",\"default\":false,\"dictCode\":103,\"dictLabel\":\"企业微信\",\"dictSort\":3,\"dictType\":\"sys_social_type\",\"dictValue\":\"3\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:00:53', 53);
INSERT INTO `sys_oper_log` VALUES (151, '社交应用参数', 1, 'com.ruoyi.web.controller.system.SysSocialAppController.add()', 'POST', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"createTime\":\"2023-12-04 14:06:44\",\"id\":1,\"name\":\"微信公众号\",\"params\":{},\"status\":\"0\",\"type\":1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:06:39', 39);
INSERT INTO `sys_oper_log` VALUES (152, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"ww6438008483ddf913\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"微信公众号\",\"params\":{},\"remark\":\"\",\"status\":\"0\",\"type\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 14:12:38\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:12:33', 28);
INSERT INTO `sys_oper_log` VALUES (153, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.synchDb()', 'GET', 1, 'admin', NULL, '/tool/gen/synchDb/sys_social_app', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:17:58', 458);
INSERT INTO `sys_oper_log` VALUES (154, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"socialApp\",\"className\":\"SysSocialApp\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"主键(自增策略)\",\"columnId\":28,\"columnName\":\"id\",\"columnType\":\"bigint(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 14:17:57\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"应用名称\",\"columnId\":29,\"columnName\":\"name\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 14:17:57\",\"usableColumn\":false},{\"capJavaField\":\"Type\",\"columnComment\":\"社交平台的类型\",\"columnId\":30,\"columnName\":\"type\",\"columnType\":\"tinyint(4)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"sys_social_type\",\"edit\":true,\"htmlType\":\"select\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"type\",\"javaType\":\"Integer\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 14:17:57\",\"usableColumn\":false},{\"capJavaField\":\"AppKey\",\"columnComment\":\"平台应用appKey\",\"columnId\":31,\"columnName\":\"app_key\",\"columnType\":\"varchar(256)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:19:16', 493);
INSERT INTO `sys_oper_log` VALUES (155, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"socialApp\",\"className\":\"SysSocialApp\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"主键(自增策略)\",\"columnId\":28,\"columnName\":\"id\",\"columnType\":\"bigint(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 14:19:16\",\"usableColumn\":false},{\"capJavaField\":\"Name\",\"columnComment\":\"应用名称\",\"columnId\":29,\"columnName\":\"name\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"name\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 14:19:16\",\"usableColumn\":false},{\"capJavaField\":\"Type\",\"columnComment\":\"社交平台的类型\",\"columnId\":30,\"columnName\":\"type\",\"columnType\":\"tinyint(4)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"sys_social_type\",\"edit\":true,\"htmlType\":\"select\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"type\",\"javaType\":\"Integer\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 14:19:16\",\"usableColumn\":false},{\"capJavaField\":\"AppKey\",\"columnComment\":\"平台应用appKey\",\"columnId\":31,\"columnName\":\"app_key\",\"columnType\":\"varchar(256)\",\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:09:34\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:19:28', 441);
INSERT INTO `sys_oper_log` VALUES (156, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"sys_social_app\"}', NULL, 0, NULL, '2023-12-04 14:19:29', 83);
INSERT INTO `sys_oper_log` VALUES (157, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2008', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:20:51', 54);
INSERT INTO `sys_oper_log` VALUES (158, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2009', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:20:52', 55);
INSERT INTO `sys_oper_log` VALUES (159, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2010', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:20:53', 53);
INSERT INTO `sys_oper_log` VALUES (160, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2011', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:20:55', 59);
INSERT INTO `sys_oper_log` VALUES (161, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2012', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:20:56', 54);
INSERT INTO `sys_oper_log` VALUES (162, '菜单管理', 3, 'com.ruoyi.web.controller.system.SysMenuController.remove()', 'DELETE', 1, 'admin', NULL, '/system/menu/2007', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:20:58', 49);
INSERT INTO `sys_oper_log` VALUES (163, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"system/socialApp/index\",\"createTime\":\"2023-12-04 14:20:15\",\"icon\":\"#\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2013,\"menuName\":\"三方应用\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2000,\"path\":\"socialApp\",\"perms\":\"system:socialApp:list\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:21:05', 37);
INSERT INTO `sys_oper_log` VALUES (164, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"system/socialApp/index\",\"createTime\":\"2023-12-04 14:20:15\",\"icon\":\"button\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2013,\"menuName\":\"三方应用\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2000,\"path\":\"socialApp\",\"perms\":\"system:socialApp:list\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:21:21', 172);
INSERT INTO `sys_oper_log` VALUES (165, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"企业微信\",\"params\":{},\"remark\":\"\",\"status\":\"0\",\"type\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 14:26:56\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:26:50', 76);
INSERT INTO `sys_oper_log` VALUES (166, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"1000003\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"企业微信\",\"params\":{},\"remark\":\"\",\"status\":\"0\",\"type\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 14:29:56\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:29:50', 32);
INSERT INTO `sys_oper_log` VALUES (167, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"1000003\",\"appSecret\":\"N2KNTeV0rVEwnKrQRDPgAFKNY5OAo6ZSHfZ-ed0H2ms\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"企业微信\",\"params\":{},\"remark\":\"\",\"status\":\"0\",\"type\":3,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 14:49:39\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 14:49:33', 36);
INSERT INTO `sys_oper_log` VALUES (168, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"user\",\"className\":\"SysSocialUser\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"主键(自增策略)\",\"columnId\":1,\"columnName\":\"id\",\"columnType\":\"bigint(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Type\",\"columnComment\":\"社交平台的类型\",\"columnId\":2,\"columnName\":\"type\",\"columnType\":\"tinyint(4)\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"select\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"type\",\"javaType\":\"Integer\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Openid\",\"columnComment\":\"社交 openid\",\"columnId\":3,\"columnName\":\"openid\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"openid\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"Token\",\"columnComment\":\"社交 token\",\"columnId\":4,\"columnName\":\"token\",\"columnType\":\"varchar(256)\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"token\",\"javaType\":\"String\",\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 16:38:15', 463);
INSERT INTO `sys_oper_log` VALUES (169, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"user\",\"className\":\"SysSocialUser\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"主键(自增策略)\",\"columnId\":1,\"columnName\":\"id\",\"columnType\":\"bigint(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 16:38:15\",\"usableColumn\":false},{\"capJavaField\":\"Type\",\"columnComment\":\"社交平台的类型\",\"columnId\":2,\"columnName\":\"type\",\"columnType\":\"tinyint(4)\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"select\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"type\",\"javaType\":\"Integer\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 16:38:15\",\"usableColumn\":false},{\"capJavaField\":\"Openid\",\"columnComment\":\"社交 openid\",\"columnId\":3,\"columnName\":\"openid\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"openid\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 16:38:15\",\"usableColumn\":false},{\"capJavaField\":\"Token\",\"columnComment\":\"社交 token\",\"columnId\":4,\"columnName\":\"token\",\"columnType\":\"varchar(256)\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 16:38:46', 479);
INSERT INTO `sys_oper_log` VALUES (170, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.synchDb()', 'GET', 1, 'admin', NULL, '/tool/gen/synchDb/sys_social_user_bind', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 16:38:51', 486);
INSERT INTO `sys_oper_log` VALUES (171, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"bind\",\"className\":\"SysSocialUserBind\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"主键(自增策略)\",\"columnId\":17,\"columnName\":\"id\",\"columnType\":\"bigint(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 16:38:51\",\"usableColumn\":false},{\"capJavaField\":\"UserId\",\"columnComment\":\"系统用户id\",\"columnId\":18,\"columnName\":\"user_id\",\"columnType\":\"bigint(20)\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"1\",\"javaField\":\"userId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 16:38:51\",\"usableColumn\":false},{\"capJavaField\":\"UserName\",\"columnComment\":\"用户登录账号\",\"columnId\":42,\"columnName\":\"user_name\",\"columnType\":\"varchar(200)\",\"createBy\":\"\",\"createTime\":\"2023-12-04 16:38:51\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"userName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"NickName\",\"columnComment\":\"用户昵称\",\"columnId\":43,\"columnName\":\"nick_name\",\"columnType\":\"varchar(200)\",\"createBy\":\"\",\"createTime\":\"2023-12-04 16:38:51\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 16:40:24', 324);
INSERT INTO `sys_oper_log` VALUES (172, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"SocialUser\",\"className\":\"SysSocialUser\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"主键(自增策略)\",\"columnId\":1,\"columnName\":\"id\",\"columnType\":\"bigint(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 16:38:46\",\"usableColumn\":false},{\"capJavaField\":\"Type\",\"columnComment\":\"社交平台的类型\",\"columnId\":2,\"columnName\":\"type\",\"columnType\":\"tinyint(4)\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"select\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"type\",\"javaType\":\"Integer\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 16:38:46\",\"usableColumn\":false},{\"capJavaField\":\"Openid\",\"columnComment\":\"社交 openid\",\"columnId\":3,\"columnName\":\"openid\",\"columnType\":\"varchar(32)\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"openid\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":1,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 16:38:46\",\"usableColumn\":false},{\"capJavaField\":\"Token\",\"columnComment\":\"社交 token\",\"columnId\":4,\"columnName\":\"token\",\"columnType\":\"varchar(256)\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 16:41:14', 580);
INSERT INTO `sys_oper_log` VALUES (173, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"sys_social_user\"}', NULL, 0, NULL, '2023-12-04 16:41:19', 531);
INSERT INTO `sys_oper_log` VALUES (174, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"SocialUserBind\",\"className\":\"SysSocialUserBind\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"主键(自增策略)\",\"columnId\":17,\"columnName\":\"id\",\"columnType\":\"bigint(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 16:40:24\",\"usableColumn\":false},{\"capJavaField\":\"UserId\",\"columnComment\":\"系统用户id\",\"columnId\":18,\"columnName\":\"user_id\",\"columnType\":\"bigint(20)\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"1\",\"javaField\":\"userId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 16:40:24\",\"usableColumn\":false},{\"capJavaField\":\"UserName\",\"columnComment\":\"用户登录账号\",\"columnId\":42,\"columnName\":\"user_name\",\"columnType\":\"varchar(200)\",\"createBy\":\"\",\"createTime\":\"2023-12-04 16:38:51\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"userName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 16:40:24\",\"usableColumn\":false},{\"capJavaField\":\"NickName\",\"columnComment\":\"用户昵称\",\"columnId\":43,\"columnName\":\"nick_name\",\"columnType\":\"varchar(200)\",\"createBy\":\"\",\"createTime\":\"2023-12-04 16:38:51\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 16:42:28', 347);
INSERT INTO `sys_oper_log` VALUES (175, '代码生成', 3, 'com.ruoyi.generator.controller.GenController.remove()', 'DELETE', 1, 'admin', NULL, '/tool/gen/1', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 16:42:33', 93);
INSERT INTO `sys_oper_log` VALUES (176, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"sys_social_user_bind\"}', NULL, 0, NULL, '2023-12-04 16:42:35', 118);
INSERT INTO `sys_oper_log` VALUES (177, '代码生成', 2, 'com.ruoyi.generator.controller.GenController.editSave()', 'PUT', 1, 'admin', NULL, '/tool/gen', '127.0.0.1', '内网IP', '{\"businessName\":\"socialUserBind\",\"className\":\"SysSocialUserBind\",\"columns\":[{\"capJavaField\":\"Id\",\"columnComment\":\"主键(自增策略)\",\"columnId\":17,\"columnName\":\"id\",\"columnType\":\"bigint(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":false,\"isIncrement\":\"1\",\"isInsert\":\"0\",\"isPk\":\"1\",\"javaField\":\"id\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 16:42:28\",\"usableColumn\":false},{\"capJavaField\":\"UserId\",\"columnComment\":\"系统用户id\",\"columnId\":18,\"columnName\":\"user_id\",\"columnType\":\"bigint(20)\",\"createBy\":\"admin\",\"createTime\":\"2023-11-28 14:12:24\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"0\",\"isRequired\":\"1\",\"javaField\":\"userId\",\"javaType\":\"Long\",\"list\":true,\"params\":{},\"pk\":false,\"query\":false,\"queryType\":\"EQ\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 16:42:28\",\"usableColumn\":false},{\"capJavaField\":\"UserName\",\"columnComment\":\"用户登录账号\",\"columnId\":42,\"columnName\":\"user_name\",\"columnType\":\"varchar(200)\",\"createBy\":\"\",\"createTime\":\"2023-12-04 16:38:51\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\":\"0\",\"isIncrement\":\"0\",\"isInsert\":\"0\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"javaField\":\"userName\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"LIKE\",\"required\":false,\"sort\":3,\"superColumn\":false,\"tableId\":2,\"updateBy\":\"\",\"updateTime\":\"2023-12-04 16:42:28\",\"usableColumn\":false},{\"capJavaField\":\"NickName\",\"columnComment\":\"用户昵称\",\"columnId\":43,\"columnName\":\"nick_name\",\"columnType\":\"varchar(200)\",\"createBy\":\"\",\"createTime\":\"2023-12-04 16:38:51\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":false,\"insert\":false,\"isEdit\"', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-04 16:42:59', 304);
INSERT INTO `sys_oper_log` VALUES (178, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'admin', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"sys_social_user_bind\"}', NULL, 0, NULL, '2023-12-04 16:43:01', 101);
INSERT INTO `sys_oper_log` VALUES (179, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', NULL, '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:54:11\",\"default\":false,\"dictCode\":100,\"dictLabel\":\"钉钉\",\"dictSort\":0,\"dictType\":\"sys_social_type\",\"dictValue\":\"DINGTALK\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-06 11:00:02', 69);
INSERT INTO `sys_oper_log` VALUES (180, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', NULL, '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:54:25\",\"default\":false,\"dictCode\":101,\"dictLabel\":\"微信公众平台\",\"dictSort\":1,\"dictType\":\"sys_social_type\",\"dictValue\":\"WECHAT_MP\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"remark\":\"公众号\",\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-06 11:00:38', 53);
INSERT INTO `sys_oper_log` VALUES (181, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', NULL, '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:56:18\",\"default\":false,\"dictCode\":102,\"dictLabel\":\"微信小程序\",\"dictSort\":2,\"dictType\":\"sys_social_type\",\"dictValue\":\"WECHAT_OPEN\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-06 11:00:51', 45);
INSERT INTO `sys_oper_log` VALUES (182, '字典数据', 2, 'com.ruoyi.web.controller.system.SysDictDataController.edit()', 'PUT', 1, 'admin', NULL, '/system/dict/data', '127.0.0.1', '内网IP', '{\"createBy\":\"admin\",\"createTime\":\"2023-12-04 13:56:37\",\"default\":false,\"dictCode\":103,\"dictLabel\":\"gitee\",\"dictSort\":3,\"dictType\":\"sys_social_type\",\"dictValue\":\"GITEE\",\"isDefault\":\"N\",\"listClass\":\"primary\",\"params\":{},\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-06 11:01:11', 54);
INSERT INTO `sys_oper_log` VALUES (183, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"1000003\",\"appSecret\":\"N2KNTeV0rVEwnKrQRDPgAFKNY5OAo6ZSHfZ-ed0H2ms\",\"code\":\"qywx001\",\"corpId\":\"ww6438008483ddf913\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"企业微信\",\"params\":{},\"remark\":\"\",\"status\":\"0\",\"updateBy\":\"\",\"updateTime\":\"2023-12-06 11:02:06\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-06 11:02:00', 41);
INSERT INTO `sys_oper_log` VALUES (184, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"1000003\",\"appSecret\":\"N2KNTeV0rVEwnKrQRDPgAFKNY5OAo6ZSHfZ-ed0H2ms\",\"code\":\"qywx001\",\"corpId\":\"ww6438008483ddf913\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"企业微信\",\"params\":{},\"remark\":\"\",\"status\":\"0\",\"updateBy\":\"\",\"updateTime\":\"2023-12-06 11:02:10\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-06 11:02:04', 321);
INSERT INTO `sys_oper_log` VALUES (185, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"1000003\",\"appSecret\":\"N2KNTeV0rVEwnKrQRDPgAFKNY5OAo6ZSHfZ-ed0H2ms\",\"code\":\"qywx001\",\"corpId\":\"ww6438008483ddf913\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"企业微信\",\"params\":{},\"remark\":\"\",\"status\":\"0\",\"updateBy\":\"\",\"updateTime\":\"2023-12-06 11:02:15\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-06 11:02:09', 31);
INSERT INTO `sys_oper_log` VALUES (186, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"1000003\",\"appSecret\":\"N2KNTeV0rVEwnKrQRDPgAFKNY5OAo6ZSHfZ-ed0H2ms\",\"code\":\"gitee\",\"corpId\":\"ww6438008483ddf913\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"企业微信\",\"params\":{},\"remark\":\"\",\"status\":\"0\",\"updateBy\":\"\",\"updateTime\":\"2023-12-06 11:02:51\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-06 11:02:45', 26);
INSERT INTO `sys_oper_log` VALUES (187, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"1000003\",\"appSecret\":\"N2KNTeV0rVEwnKrQRDPgAFKNY5OAo6ZSHfZ-ed0H2ms\",\"code\":\"gitee\",\"corpId\":\"ww6438008483ddf913\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"企业微信\",\"params\":{},\"remark\":\"\",\"status\":\"0\",\"updateBy\":\"\",\"updateTime\":\"2023-12-06 11:03:42\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-06 11:03:36', 21);
INSERT INTO `sys_oper_log` VALUES (188, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"1000003\",\"appSecret\":\"N2KNTeV0rVEwnKrQRDPgAFKNY5OAo6ZSHfZ-ed0H2ms\",\"code\":\"gitee\",\"corpId\":\"ww6438008483ddf913\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"gitee\",\"params\":{},\"remark\":\"\",\"status\":\"0\",\"updateBy\":\"\",\"updateTime\":\"2023-12-06 11:04:20\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-06 11:04:14', 28);
INSERT INTO `sys_oper_log` VALUES (189, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"1000003\",\"appSecret\":\"N2KNTeV0rVEwnKrQRDPgAFKNY5OAo6ZSHfZ-ed0H2ms\",\"code\":\"gitee\",\"corpId\":\"ww6438008483ddf913\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"gitee\",\"params\":{},\"remark\":\"\",\"status\":\"0\",\"updateBy\":\"\",\"updateTime\":\"2023-12-06 11:04:45\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-06 11:04:38', 35);
INSERT INTO `sys_oper_log` VALUES (190, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"1000003\",\"appSecret\":\"N2KNTeV0rVEwnKrQRDPgAFKNY5OAo6ZSHfZ-ed0H2ms\",\"code\":\"gitee\",\"corpId\":\"ww6438008483ddf913\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"gitee\",\"params\":{},\"remark\":\"\",\"status\":\"0\",\"type\":\"WECHAT_OPEN\",\"updateBy\":\"\",\"updateTime\":\"2023-12-06 11:05:35\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-06 11:05:29', 28);
INSERT INTO `sys_oper_log` VALUES (191, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"1000003\",\"appSecret\":\"N2KNTeV0rVEwnKrQRDPgAFKNY5OAo6ZSHfZ-ed0H2ms\",\"code\":\"gitee\",\"corpId\":\"ww6438008483ddf913\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"gitee\",\"params\":{},\"remark\":\"\",\"status\":\"0\",\"type\":\"GITEE\",\"updateBy\":\"\",\"updateTime\":\"2023-12-06 11:05:39\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-06 11:05:33', 53);
INSERT INTO `sys_oper_log` VALUES (192, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"6c33b5c4789a5e9c6c1909e86cd667e12b5ddb2666824eb224b49da32604b2cb\",\"appSecret\":\"9aa4f12d51089a65cc128bacee9dcd47069fa637ce312e55f8302ac915df3de6\",\"code\":\"gitee\",\"corpId\":\"\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"gitee\",\"params\":{},\"remark\":\"\",\"status\":\"0\",\"type\":\"GITEE\",\"updateBy\":\"\",\"updateTime\":\"2023-12-06 11:12:11\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-06 11:12:05', 218);
INSERT INTO `sys_oper_log` VALUES (193, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"6c33b5c4789a5e9c6c1909e86cd667e12b5ddb2666824eb224b49da32604b2cb\",\"appSecret\":\"9aa4f12d51089a65cc128bacee9dcd47069fa637ce312e55f8302ac915df3de6\",\"code\":\"gitee\",\"corpId\":\"\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"gitee\",\"params\":{},\"remark\":\"oauth-GITEE-client-id   ===&gt;   appKey\\noauth-GITEE-client-secret  ===&gt;  appSecret\\noauth-GITEE-redirect-uri  ===&gt; url\",\"status\":\"0\",\"type\":\"GITEE\",\"updateBy\":\"\",\"updateTime\":\"2023-12-06 11:18:03\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-06 11:17:57', 47);
INSERT INTO `sys_oper_log` VALUES (194, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"6c33b5c4789a5e9c6c1909e86cd667e12b5ddb2666824eb224b49da32604b2cb\",\"appSecret\":\"9aa4f12d51089a65cc128bacee9dcd47069fa637ce312e55f8302ac915df3de6\",\"code\":\"gitee\",\"corpId\":\"\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"gitee\",\"params\":{},\"remark\":\"oauth-GITEE-client-id   ===&gt;   appKey\\noauth-GITEE-client-secret  ===&gt;  appSecret\\noauth-GITEE-redirect-uri  ===&gt; url\",\"status\":\"0\",\"type\":\"GITEE\",\"updateBy\":\"\",\"updateTime\":\"2023-12-06 11:24:37\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-06 11:24:31', 41);
INSERT INTO `sys_oper_log` VALUES (195, '社交应用参数', 1, 'com.ruoyi.web.controller.system.SysSocialAppController.add()', 'POST', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"123\",\"appSecret\":\"123\",\"code\":\"gitee\",\"corpId\":\"123\",\"name\":\"阿松大\",\"params\":{},\"remark\":\"123\",\"status\":\"0\",\"type\":\"WECHAT_MP\"}', NULL, 1, '\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist\r\n### The error may exist in file [D:\\kikock_poject\\work\\jx\\ruoyi\\RuoYi-Vue-master\\ruoyi-system\\target\\classes\\mapper\\system\\SysSocialAppMapper.xml]\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT COUNT(*) > 0 FROM SysSocialApp WHERE code = ?\r\n### Cause: java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist', '2023-12-06 11:24:49', 142);
INSERT INTO `sys_oper_log` VALUES (196, '社交应用参数', 1, 'com.ruoyi.web.controller.system.SysSocialAppController.add()', 'POST', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"123\",\"appSecret\":\"123\",\"code\":\"gitee\",\"corpId\":\"123\",\"name\":\"阿松大\",\"params\":{},\"remark\":\"123\",\"status\":\"0\",\"type\":\"WECHAT_MP\"}', NULL, 1, '\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist\r\n### The error may exist in file [D:\\kikock_poject\\work\\jx\\ruoyi\\RuoYi-Vue-master\\ruoyi-system\\target\\classes\\mapper\\system\\SysSocialAppMapper.xml]\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT COUNT(*) > 0 FROM SysSocialApp WHERE code = ?\r\n### Cause: java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist', '2023-12-06 11:24:53', 11);
INSERT INTO `sys_oper_log` VALUES (197, '社交应用参数', 1, 'com.ruoyi.web.controller.system.SysSocialAppController.add()', 'POST', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"123\",\"appSecret\":\"123\",\"code\":\"gitee\",\"corpId\":\"123\",\"name\":\"阿松大\",\"params\":{},\"remark\":\"123\",\"status\":\"0\",\"type\":\"WECHAT_MP\"}', NULL, 1, '\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist\r\n### The error may exist in file [D:\\kikock_poject\\work\\jx\\ruoyi\\RuoYi-Vue-master\\ruoyi-system\\target\\classes\\mapper\\system\\SysSocialAppMapper.xml]\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT COUNT(*) > 0 FROM SysSocialApp WHERE code = ?\r\n### Cause: java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist', '2023-12-06 11:24:57', 20);
INSERT INTO `sys_oper_log` VALUES (198, '社交应用参数', 1, 'com.ruoyi.web.controller.system.SysSocialAppController.add()', 'POST', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"123\",\"appSecret\":\"123\",\"code\":\"gitee\",\"corpId\":\"123\",\"name\":\"阿松大\",\"params\":{},\"remark\":\"123\",\"status\":\"0\",\"type\":\"WECHAT_MP\"}', NULL, 1, '\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist\r\n### The error may exist in file [D:\\kikock_poject\\work\\jx\\ruoyi\\RuoYi-Vue-master\\ruoyi-system\\target\\classes\\mapper\\system\\SysSocialAppMapper.xml]\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT COUNT(*) > 0 FROM SysSocialApp WHERE code = ?\r\n### Cause: java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist', '2023-12-06 11:24:59', 12);
INSERT INTO `sys_oper_log` VALUES (199, '社交应用参数', 1, 'com.ruoyi.web.controller.system.SysSocialAppController.add()', 'POST', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"123\",\"appSecret\":\"123\",\"code\":\"gitee\",\"corpId\":\"123\",\"name\":\"阿松大\",\"params\":{},\"remark\":\"123\",\"status\":\"0\",\"type\":\"WECHAT_MP\"}', NULL, 1, '\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist\r\n### The error may exist in file [D:\\kikock_poject\\work\\jx\\ruoyi\\RuoYi-Vue-master\\ruoyi-system\\target\\classes\\mapper\\system\\SysSocialAppMapper.xml]\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT COUNT(*) > 0 FROM SysSocialApp WHERE code = ?\r\n### Cause: java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist', '2023-12-06 11:25:00', 14);
INSERT INTO `sys_oper_log` VALUES (200, '社交应用参数', 1, 'com.ruoyi.web.controller.system.SysSocialAppController.add()', 'POST', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"123\",\"appSecret\":\"123\",\"code\":\"gitee\",\"corpId\":\"123\",\"name\":\"阿松大\",\"params\":{},\"remark\":\"123\",\"status\":\"0\",\"type\":\"WECHAT_MP\"}', NULL, 1, '\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist\r\n### The error may exist in file [D:\\kikock_poject\\work\\jx\\ruoyi\\RuoYi-Vue-master\\ruoyi-system\\target\\classes\\mapper\\system\\SysSocialAppMapper.xml]\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT COUNT(*) > 0 FROM SysSocialApp WHERE code = ?\r\n### Cause: java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist', '2023-12-06 11:25:03', 14);
INSERT INTO `sys_oper_log` VALUES (201, '社交应用参数', 1, 'com.ruoyi.web.controller.system.SysSocialAppController.add()', 'POST', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"123\",\"appSecret\":\"123\",\"code\":\"gitee\",\"corpId\":\"123\",\"name\":\"阿松大\",\"params\":{},\"remark\":\"123\",\"status\":\"0\",\"type\":\"WECHAT_MP\"}', NULL, 1, '\r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist\r\n### The error may exist in file [D:\\kikock_poject\\work\\jx\\ruoyi\\RuoYi-Vue-master\\ruoyi-system\\target\\classes\\mapper\\system\\SysSocialAppMapper.xml]\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT COUNT(*) > 0 FROM SysSocialApp WHERE code = ?\r\n### Cause: java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Table \'ry_vue.syssocialapp\' doesn\'t exist', '2023-12-06 11:25:04', 27);
INSERT INTO `sys_oper_log` VALUES (202, '社交应用参数', 1, 'com.ruoyi.web.controller.system.SysSocialAppController.add()', 'POST', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"123\",\"appSecret\":\"123\",\"code\":\"gitee\",\"corpId\":\"123\",\"name\":\"阿松大\",\"params\":{},\"remark\":\"123\",\"status\":\"0\",\"type\":\"WECHAT_MP\"}', NULL, 1, '应用编码已经存在', '2023-12-06 11:25:34', 15);
INSERT INTO `sys_oper_log` VALUES (203, '社交应用参数', 1, 'com.ruoyi.web.controller.system.SysSocialAppController.add()', 'POST', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"123\",\"appSecret\":\"123\",\"code\":\"gitee\",\"corpId\":\"123\",\"name\":\"阿松大\",\"params\":{},\"remark\":\"123\",\"status\":\"0\",\"type\":\"WECHAT_MP\"}', NULL, 1, '应用编码已经存在', '2023-12-06 11:25:35', 13);
INSERT INTO `sys_oper_log` VALUES (204, '社交应用参数', 1, 'com.ruoyi.web.controller.system.SysSocialAppController.add()', 'POST', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"123\",\"appSecret\":\"123\",\"code\":\"gitee\",\"corpId\":\"123\",\"name\":\"阿松大\",\"params\":{},\"remark\":\"123\",\"status\":\"0\",\"type\":\"WECHAT_MP\"}', NULL, 1, '应用编码已经存在', '2023-12-06 11:25:38', 14);
INSERT INTO `sys_oper_log` VALUES (205, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"6c33b5c4789a5e9c6c1909e86cd667e12b5ddb2666824eb224b49da32604b2cb\",\"appSecret\":\"9aa4f12d51089a65cc128bacee9dcd47069fa637ce312e55f8302ac915df3de6\",\"code\":\"gitee\",\"corpId\":\"\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"gitee\",\"params\":{},\"remark\":\"oauth-GITEE-client-id   ===&gt;   appKey\\noauth-GITEE-client-secret  ===&gt;  appSecret\\noauth-GITEE-redirect-uri  ===&gt; url\",\"status\":\"0\",\"type\":\"GITEE\",\"updateBy\":\"\",\"updateTime\":\"2023-12-19 17:03:49\",\"url\":\"http://localhost/oauthBack?type=gitee\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-19 17:03:38', 83);
INSERT INTO `sys_oper_log` VALUES (206, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"889f3d4c2a940ece4a0c2109e68c91b44706fd971c98f0df5cf5b63e99f2f202\",\"appSecret\":\"4c782f5792211a6ee59734bbcd5e045fa65fb6044341d3dd5ee94163fa6072bd\",\"code\":\"gitee\",\"corpId\":\"\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"gitee\",\"params\":{},\"remark\":\"oauth-GITEE-client-id   ===&gt;   appKey\\noauth-GITEE-client-secret  ===&gt;  appSecret\\noauth-GITEE-redirect-uri  ===&gt; url\",\"status\":\"0\",\"type\":\"GITEE\",\"updateBy\":\"\",\"updateTime\":\"2023-12-19 17:20:46\",\"url\":\"http://localhost/oauthBack?type=gitee\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-19 17:20:36', 88);
INSERT INTO `sys_oper_log` VALUES (207, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"6c33b5c4789a5e9c6c1909e86cd667e12b5ddb2666824eb224b49da32604b2cb\",\"appSecret\":\"4c782f5792211a6ee59734bbcd5e045fa65fb6044341d3dd5ee94163fa6072bd\",\"code\":\"gitee\",\"corpId\":\"\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"gitee\",\"params\":{},\"remark\":\"oauth-GITEE-client-id   ===&gt;   appKey\\noauth-GITEE-client-secret  ===&gt;  appSecret\\noauth-GITEE-redirect-uri  ===&gt; url\",\"status\":\"0\",\"type\":\"GITEE\",\"updateBy\":\"\",\"updateTime\":\"2023-12-20 10:25:36\",\"url\":\"http://localhost/oauthBack?type=gitee\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-20 10:25:27', 54);
INSERT INTO `sys_oper_log` VALUES (208, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'admin', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"6c33b5c4789a5e9c6c1909e86cd667e12b5ddb2666824eb224b49da32604b2cb\",\"appSecret\":\"9aa4f12d51089a65cc128bacee9dcd47069fa637ce312e55f8302ac915df3de6\",\"code\":\"gitee\",\"corpId\":\"\",\"createBy\":\"\",\"createTime\":\"2023-12-04 14:06:45\",\"delFlag\":\"0\",\"id\":1,\"name\":\"gitee\",\"params\":{},\"remark\":\"oauth-GITEE-client-id   ===&gt;   appKey\\noauth-GITEE-client-secret  ===&gt;  appSecret\\noauth-GITEE-redirect-uri  ===&gt; url\",\"status\":\"0\",\"type\":\"GITEE\",\"updateBy\":\"\",\"updateTime\":\"2023-12-20 10:25:48\",\"url\":\"http://localhost/oauthBack?type=gitee\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-20 10:25:38', 29);
INSERT INTO `sys_oper_log` VALUES (209, '用户管理', 2, 'com.ruoyi.web.controller.system.SysUserController.resetPwd()', 'PUT', 1, 'admin', NULL, '/system/user/resetPwd', '127.0.0.1', '内网IP', '{\"admin\":false,\"params\":{},\"updateBy\":\"admin\",\"userId\":2}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-20 13:30:14', 186);
INSERT INTO `sys_oper_log` VALUES (210, '用户管理', 4, 'com.ruoyi.web.controller.system.SysUserController.insertAuthRole()', 'PUT', 1, 'admin', NULL, '/system/user/authRole', '127.0.0.1', '内网IP', '{\"roleIds\":\"2\",\"userId\":\"2\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-20 13:30:21', 244);
INSERT INTO `sys_oper_log` VALUES (211, '角色管理', 2, 'com.ruoyi.web.controller.system.SysRoleController.edit()', 'PUT', 1, 'admin', NULL, '/system/role', '127.0.0.1', '内网IP', '{\"admin\":false,\"createTime\":\"2023-11-27 15:23:23\",\"dataScope\":\"2\",\"delFlag\":\"0\",\"deptCheckStrictly\":true,\"flag\":false,\"menuCheckStrictly\":true,\"menuIds\":[1,100,1000,1001,1002,1003,1004,1005,1006,101,1007,1008,1009,1010,1011,102,1012,1013,1014,1015,103,1016,1017,1018,1019,104,1020,1021,1022,1023,1024,105,1025,1026,1027,1028,1029,106,1030,1031,1032,1033,1034,107,1035,1036,1037,1038,108,500,1039,1040,1041,501,1042,1043,1044,1045,2,109,1046,1047,1048,110,1049,1050,1051,1052,1053,1054,111,112,113,114,3,116,1055,1056,1057,1058,1059,1060,117,2000,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022,2023,2024],\"params\":{},\"remark\":\"普通角色\",\"roleId\":2,\"roleKey\":\"common\",\"roleName\":\"普通角色\",\"roleSort\":2,\"status\":\"0\",\"updateBy\":\"admin\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-20 13:30:32', 227);
INSERT INTO `sys_oper_log` VALUES (212, '在线用户', 7, 'com.ruoyi.web.controller.monitor.SysUserOnlineController.forceLogout()', 'DELETE', 1, 'user', NULL, '/monitor/online/8b73752e-7e60-45ec-83ff-4257de8bf758', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-20 16:00:11', 39);
INSERT INTO `sys_oper_log` VALUES (213, '在线用户', 7, 'com.ruoyi.web.controller.monitor.SysUserOnlineController.forceLogout()', 'DELETE', 1, 'user', NULL, '/monitor/online/c5e848b1-7d69-4be9-a48c-a30807a69cf4', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-20 16:13:00', 36);
INSERT INTO `sys_oper_log` VALUES (214, '在线用户', 7, 'com.ruoyi.web.controller.monitor.SysUserOnlineController.forceLogout()', 'DELETE', 1, 'user', NULL, '/monitor/online/70e017d3-093b-4913-a4c4-b9968f41540e', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-20 16:13:02', 15);
INSERT INTO `sys_oper_log` VALUES (215, '在线用户', 7, 'com.ruoyi.web.controller.monitor.SysUserOnlineController.forceLogout()', 'DELETE', 1, 'user', NULL, '/monitor/online/88741e5a-6f01-47bc-8892-74a020cf4372', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-20 16:13:03', 14);
INSERT INTO `sys_oper_log` VALUES (216, '在线用户', 7, 'com.ruoyi.web.controller.monitor.SysUserOnlineController.forceLogout()', 'DELETE', 1, 'user', NULL, '/monitor/online/3c9654dd-c32f-4585-99ae-e927035b9a64', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-20 16:13:05', 12);
INSERT INTO `sys_oper_log` VALUES (217, '在线用户', 7, 'com.ruoyi.web.controller.monitor.SysUserOnlineController.forceLogout()', 'DELETE', 1, 'user', NULL, '/monitor/online/69bd4064-7699-4c1c-ba2c-751bbfa320dd', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-20 16:13:06', 11);
INSERT INTO `sys_oper_log` VALUES (218, '在线用户', 7, 'com.ruoyi.web.controller.monitor.SysUserOnlineController.forceLogout()', 'DELETE', 1, 'user', NULL, '/monitor/online/4f984483-7555-4ffd-8b0e-2a6fb171ee35', '127.0.0.1', '内网IP', '{}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-20 16:58:43', 43);
INSERT INTO `sys_oper_log` VALUES (219, '社交应用参数', 1, 'com.ruoyi.web.controller.system.SysSocialAppController.add()', 'POST', 1, 'user', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"code\":\"DINGTALK\",\"createTime\":\"2023-12-22 08:53:07\",\"id\":2,\"name\":\"钉钉\",\"params\":{},\"remark\":\"钉钉扫码登录\",\"status\":\"0\",\"type\":\"DINGTALK\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-22 08:52:58', 116);
INSERT INTO `sys_oper_log` VALUES (220, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'user', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"code\":\"DINGTALK\",\"corpId\":\"dingac1852a0fcd38ab835c2f4657eb6378f\",\"createBy\":\"\",\"createTime\":\"2023-12-22 08:53:07\",\"delFlag\":\"0\",\"id\":2,\"name\":\"钉钉\",\"params\":{},\"remark\":\"钉钉扫码登录\",\"status\":\"0\",\"type\":\"DINGTALK\",\"updateBy\":\"\",\"updateTime\":\"2023-12-22 08:54:21\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-22 08:54:12', 41);
INSERT INTO `sys_oper_log` VALUES (221, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'user', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"dingk1yxmhcb64eycin2\",\"appSecret\":\"6dQsKYTjaarOKcpBgChNWSa9bsP8QpSGRC-us1PvUH0IjzC0O--dMa9PQdX2gNIS\",\"code\":\"DINGTALK\",\"corpId\":\"266882973\",\"createBy\":\"\",\"createTime\":\"2023-12-22 08:53:07\",\"delFlag\":\"0\",\"id\":2,\"name\":\"钉钉\",\"params\":{},\"remark\":\"钉钉扫码登录 应用id\",\"status\":\"0\",\"type\":\"DINGTALK\",\"updateBy\":\"\",\"updateTime\":\"2023-12-22 08:56:33\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-22 08:56:24', 36);
INSERT INTO `sys_oper_log` VALUES (222, '社交应用参数', 2, 'com.ruoyi.web.controller.system.SysSocialAppController.edit()', 'PUT', 1, 'user', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"appKey\":\"dingk1yxmhcb64eycin2\",\"appSecret\":\"6dQsKYTjaarOKcpBgChNWSa9bsP8QpSGRC-us1PvUH0IjzC0O--dMa9PQdX2gNIS\",\"code\":\"DINGTALK\",\"corpId\":\"266882973\",\"createBy\":\"\",\"createTime\":\"2023-12-22 08:53:07\",\"delFlag\":\"0\",\"id\":2,\"name\":\"钉钉\",\"params\":{},\"remark\":\"钉钉扫码登录 应用id\",\"status\":\"0\",\"type\":\"DINGTALK\",\"updateBy\":\"\",\"updateTime\":\"2023-12-22 08:57:03\",\"url\":\"http://localhost/oauthBack?type=dingtalk\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-22 08:56:54', 35);
INSERT INTO `sys_oper_log` VALUES (223, '社交应用参数', 1, 'com.ruoyi.web.controller.system.SysSocialAppController.add()', 'POST', 1, 'user', NULL, '/system/socialApp', '127.0.0.1', '内网IP', '{\"code\":\"dingtalk\",\"name\":\"钉钉\",\"params\":{},\"status\":\"0\",\"type\":\"DINGTALK\"}', NULL, 1, '应用编码已经存在', '2023-12-22 09:25:45', 47);
INSERT INTO `sys_oper_log` VALUES (224, '代码生成', 8, 'com.ruoyi.generator.controller.GenController.batchGenCode()', 'GET', 1, 'user', NULL, '/tool/gen/batchGenCode', '127.0.0.1', '内网IP', '{\"tables\":\"sys_social_user_bind\"}', NULL, 0, NULL, '2023-12-22 10:13:55', 80);
INSERT INTO `sys_oper_log` VALUES (225, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'user', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"component\":\"system/socialUserBind/index\",\"createTime\":\"2023-12-04 16:43:31\",\"icon\":\"button\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2019,\"menuName\":\"三方用户\",\"menuType\":\"C\",\"orderNum\":1,\"params\":{},\"parentId\":2000,\"path\":\"socialUserBind\",\"perms\":\"system:socialUserBind:list\",\"status\":\"0\",\"updateBy\":\"user\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-22 10:17:10', 44);
INSERT INTO `sys_oper_log` VALUES (226, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'user', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createBy\":\"user\",\"icon\":\"activiti\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"工作流\",\"menuType\":\"M\",\"orderNum\":6,\"params\":{},\"parentId\":0,\"path\":\"0\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-22 10:46:40', 80);
INSERT INTO `sys_oper_log` VALUES (227, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'user', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"icon\":\"activiti\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"工作流\",\"menuType\":\"M\",\"orderNum\":10,\"params\":{},\"parentId\":0,\"path\":\"#\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"新增菜单\'工作流\'失败，菜单名称已存在\",\"code\":500}', 0, NULL, '2023-12-22 10:47:21', 11);
INSERT INTO `sys_oper_log` VALUES (228, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2023-12-22 10:46:40\",\"icon\":\"activiti\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2025,\"menuName\":\"工作流\",\"menuType\":\"M\",\"orderNum\":6,\"params\":{},\"parentId\":0,\"path\":\"#\",\"perms\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-22 10:49:17', 40);
INSERT INTO `sys_oper_log` VALUES (229, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2023-12-22 10:46:40\",\"icon\":\"activiti2\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2025,\"menuName\":\"工作流\",\"menuType\":\"M\",\"orderNum\":6,\"params\":{},\"parentId\":0,\"path\":\"#\",\"perms\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-22 10:50:31', 35);
INSERT INTO `sys_oper_log` VALUES (230, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2023-12-22 10:46:40\",\"icon\":\"activiti\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2025,\"menuName\":\"工作流\",\"menuType\":\"M\",\"orderNum\":6,\"params\":{},\"parentId\":0,\"path\":\"#\",\"perms\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-22 10:51:08', 43);
INSERT INTO `sys_oper_log` VALUES (231, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createBy\":\"admin\",\"icon\":\"activiti\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"流程关联\",\"menuType\":\"M\",\"orderNum\":1,\"params\":{},\"parentId\":2025,\"path\":\"#\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-22 14:18:30', 39);
INSERT INTO `sys_oper_log` VALUES (232, '菜单管理', 2, 'com.ruoyi.web.controller.system.SysMenuController.edit()', 'PUT', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createTime\":\"2023-12-22 14:18:30\",\"icon\":\"activiti\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":2026,\"menuName\":\"流程管理\",\"menuType\":\"M\",\"orderNum\":1,\"params\":{},\"parentId\":2025,\"path\":\"#\",\"perms\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-22 14:18:55', 148);
INSERT INTO `sys_oper_log` VALUES (233, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createBy\":\"admin\",\"icon\":\"activiti\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"任务管理\",\"menuType\":\"M\",\"orderNum\":2,\"params\":{},\"parentId\":2025,\"path\":\"#\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-22 14:19:28', 47);
INSERT INTO `sys_oper_log` VALUES (234, '菜单管理', 1, 'com.ruoyi.web.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"children\":[],\"createBy\":\"admin\",\"icon\":\"activiti\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"流程示例(请假)\",\"menuType\":\"M\",\"orderNum\":3,\"params\":{},\"parentId\":2025,\"path\":\"#\",\"status\":\"0\",\"visible\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2023-12-22 14:20:35', 31);

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2023-11-27 15:23:23', '', NULL, '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2023-11-27 15:23:23', '', NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '2', 1, 1, '0', '0', 'admin', '2023-11-27 15:23:23', 'admin', '2023-12-20 13:30:32', '普通角色');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES (2, 100);
INSERT INTO `sys_role_dept` VALUES (2, 101);
INSERT INTO `sys_role_dept` VALUES (2, 105);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 103);
INSERT INTO `sys_role_menu` VALUES (2, 104);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 106);
INSERT INTO `sys_role_menu` VALUES (2, 107);
INSERT INTO `sys_role_menu` VALUES (2, 108);
INSERT INTO `sys_role_menu` VALUES (2, 109);
INSERT INTO `sys_role_menu` VALUES (2, 110);
INSERT INTO `sys_role_menu` VALUES (2, 111);
INSERT INTO `sys_role_menu` VALUES (2, 112);
INSERT INTO `sys_role_menu` VALUES (2, 113);
INSERT INTO `sys_role_menu` VALUES (2, 114);
INSERT INTO `sys_role_menu` VALUES (2, 116);
INSERT INTO `sys_role_menu` VALUES (2, 117);
INSERT INTO `sys_role_menu` VALUES (2, 500);
INSERT INTO `sys_role_menu` VALUES (2, 501);
INSERT INTO `sys_role_menu` VALUES (2, 1000);
INSERT INTO `sys_role_menu` VALUES (2, 1001);
INSERT INTO `sys_role_menu` VALUES (2, 1002);
INSERT INTO `sys_role_menu` VALUES (2, 1003);
INSERT INTO `sys_role_menu` VALUES (2, 1004);
INSERT INTO `sys_role_menu` VALUES (2, 1005);
INSERT INTO `sys_role_menu` VALUES (2, 1006);
INSERT INTO `sys_role_menu` VALUES (2, 1007);
INSERT INTO `sys_role_menu` VALUES (2, 1008);
INSERT INTO `sys_role_menu` VALUES (2, 1009);
INSERT INTO `sys_role_menu` VALUES (2, 1010);
INSERT INTO `sys_role_menu` VALUES (2, 1011);
INSERT INTO `sys_role_menu` VALUES (2, 1012);
INSERT INTO `sys_role_menu` VALUES (2, 1013);
INSERT INTO `sys_role_menu` VALUES (2, 1014);
INSERT INTO `sys_role_menu` VALUES (2, 1015);
INSERT INTO `sys_role_menu` VALUES (2, 1016);
INSERT INTO `sys_role_menu` VALUES (2, 1017);
INSERT INTO `sys_role_menu` VALUES (2, 1018);
INSERT INTO `sys_role_menu` VALUES (2, 1019);
INSERT INTO `sys_role_menu` VALUES (2, 1020);
INSERT INTO `sys_role_menu` VALUES (2, 1021);
INSERT INTO `sys_role_menu` VALUES (2, 1022);
INSERT INTO `sys_role_menu` VALUES (2, 1023);
INSERT INTO `sys_role_menu` VALUES (2, 1024);
INSERT INTO `sys_role_menu` VALUES (2, 1025);
INSERT INTO `sys_role_menu` VALUES (2, 1026);
INSERT INTO `sys_role_menu` VALUES (2, 1027);
INSERT INTO `sys_role_menu` VALUES (2, 1028);
INSERT INTO `sys_role_menu` VALUES (2, 1029);
INSERT INTO `sys_role_menu` VALUES (2, 1030);
INSERT INTO `sys_role_menu` VALUES (2, 1031);
INSERT INTO `sys_role_menu` VALUES (2, 1032);
INSERT INTO `sys_role_menu` VALUES (2, 1033);
INSERT INTO `sys_role_menu` VALUES (2, 1034);
INSERT INTO `sys_role_menu` VALUES (2, 1035);
INSERT INTO `sys_role_menu` VALUES (2, 1036);
INSERT INTO `sys_role_menu` VALUES (2, 1037);
INSERT INTO `sys_role_menu` VALUES (2, 1038);
INSERT INTO `sys_role_menu` VALUES (2, 1039);
INSERT INTO `sys_role_menu` VALUES (2, 1040);
INSERT INTO `sys_role_menu` VALUES (2, 1041);
INSERT INTO `sys_role_menu` VALUES (2, 1042);
INSERT INTO `sys_role_menu` VALUES (2, 1043);
INSERT INTO `sys_role_menu` VALUES (2, 1044);
INSERT INTO `sys_role_menu` VALUES (2, 1045);
INSERT INTO `sys_role_menu` VALUES (2, 1046);
INSERT INTO `sys_role_menu` VALUES (2, 1047);
INSERT INTO `sys_role_menu` VALUES (2, 1048);
INSERT INTO `sys_role_menu` VALUES (2, 1049);
INSERT INTO `sys_role_menu` VALUES (2, 1050);
INSERT INTO `sys_role_menu` VALUES (2, 1051);
INSERT INTO `sys_role_menu` VALUES (2, 1052);
INSERT INTO `sys_role_menu` VALUES (2, 1053);
INSERT INTO `sys_role_menu` VALUES (2, 1054);
INSERT INTO `sys_role_menu` VALUES (2, 1055);
INSERT INTO `sys_role_menu` VALUES (2, 1056);
INSERT INTO `sys_role_menu` VALUES (2, 1057);
INSERT INTO `sys_role_menu` VALUES (2, 1058);
INSERT INTO `sys_role_menu` VALUES (2, 1059);
INSERT INTO `sys_role_menu` VALUES (2, 1060);
INSERT INTO `sys_role_menu` VALUES (2, 2000);
INSERT INTO `sys_role_menu` VALUES (2, 2013);
INSERT INTO `sys_role_menu` VALUES (2, 2014);
INSERT INTO `sys_role_menu` VALUES (2, 2015);
INSERT INTO `sys_role_menu` VALUES (2, 2016);
INSERT INTO `sys_role_menu` VALUES (2, 2017);
INSERT INTO `sys_role_menu` VALUES (2, 2018);
INSERT INTO `sys_role_menu` VALUES (2, 2019);
INSERT INTO `sys_role_menu` VALUES (2, 2020);
INSERT INTO `sys_role_menu` VALUES (2, 2021);
INSERT INTO `sys_role_menu` VALUES (2, 2022);
INSERT INTO `sys_role_menu` VALUES (2, 2023);
INSERT INTO `sys_role_menu` VALUES (2, 2024);

-- ----------------------------
-- Table structure for sys_social_app
-- ----------------------------
DROP TABLE IF EXISTS `sys_social_app`;
CREATE TABLE `sys_social_app`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键(自增策略)',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用编码',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名称',
  `type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社交平台的类型',
  `app_key` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '平台应用appKey',
  `app_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '平台应用appSecret',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '登录回调地址',
  `corp_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '平台企业id',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '开启状态',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code_p`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '社交应用参数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_social_app
-- ----------------------------
INSERT INTO `sys_social_app` VALUES (1, 'gitee', 'gitee', 'GITEE', '6c33b5c4789a5e9c6c1909e86cd667e12b5ddb2666824eb224b49da32604b2cb', '9aa4f12d51089a65cc128bacee9dcd47069fa637ce312e55f8302ac915df3de6', 'http://localhost/oauthBack?type=gitee', '', '0', '0', '', '2023-12-04 14:06:45', '', '2023-12-20 10:25:48', 'oauth-GITEE-client-id   ===&gt;   appKey\noauth-GITEE-client-secret  ===&gt;  appSecret\noauth-GITEE-redirect-uri  ===&gt; url');
INSERT INTO `sys_social_app` VALUES (2, 'dingtalk', '钉钉', 'DINGTALK', 'dingk1yxmhcb64eycin2', '6dQsKYTjaarOKcpBgChNWSa9bsP8QpSGRC-us1PvUH0IjzC0O--dMa9PQdX2gNIS', 'http://localhost/oauthBack?type=dingtalk', '266882973', '0', '0', '', '2023-12-22 08:53:07', '', '2023-12-22 09:26:09', '钉钉扫码登录 应用id');

-- ----------------------------
-- Table structure for sys_social_app_copy1
-- ----------------------------
DROP TABLE IF EXISTS `sys_social_app_copy1`;
CREATE TABLE `sys_social_app_copy1`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键(自增策略)',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用编码',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名称',
  `type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社交平台的类型',
  `app_key` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '平台应用appKey',
  `app_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '平台应用appSecret',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '登录回调地址',
  `corp_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '平台企业id',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '开启状态',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code_p`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '社交应用参数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_social_app_copy1
-- ----------------------------

-- ----------------------------
-- Table structure for sys_social_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_social_user`;
CREATE TABLE `sys_social_user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键(自增策略)',
  `type` tinyint(4) NOT NULL COMMENT '社交平台的类型',
  `openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社交 openid',
  `token` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '社交 token',
  `raw_token_info` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始 Token 数据，一般是 JSON 格式',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
  `raw_user_info` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始用户数据，一般是 JSON 格式',
  `code` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '最后一次的认证 code',
  `state` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后一次的认证 state',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '社交用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_social_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_social_user_bind
-- ----------------------------
DROP TABLE IF EXISTS `sys_social_user_bind`;
CREATE TABLE `sys_social_user_bind`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键(自增策略)',
  `user_id` bigint(20) NOT NULL COMMENT '系统用户id',
  `user_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户登录账号',
  `nick_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `social_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社交平台(用户来源)',
  `social_uuid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '第三方平台用户唯一id',
  `email` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '社交绑定表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_social_user_bind
-- ----------------------------
INSERT INTO `sys_social_user_bind` VALUES (31, 2, '曹奎', '曹奎', 'dingtalk', 'dingtalkNMeWtzwP1nQggUBetj4asQiEiE', '', '0', '', '2023-12-22 10:15:49', '', '2023-12-22 10:15:40', '');
INSERT INTO `sys_social_user_bind` VALUES (35, 2, 'kikock', '“+遗民”', 'gitee', 'gitee1823903', '', '0', '', '2023-12-22 10:43:53', '', '2023-12-22 10:43:44', '');

-- ----------------------------
-- Table structure for sys_social_user_bind_copy1
-- ----------------------------
DROP TABLE IF EXISTS `sys_social_user_bind_copy1`;
CREATE TABLE `sys_social_user_bind_copy1`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键(自增策略)',
  `user_id` bigint(20) NOT NULL COMMENT '系统用户id',
  `user_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户登录账号',
  `nick_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `social_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社交平台(用户来源)',
  `social_uuid` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '第三方平台用户唯一id',
  `email` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '社交绑定表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_social_user_bind_copy1
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 103, 'admin', '系统管理员', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2023-12-22 14:18:11', 'admin', '2023-11-27 15:23:23', '', '2023-12-22 14:18:02', '管理员');
INSERT INTO `sys_user` VALUES (2, 105, 'user', '用户', '00', 'ry@qq.com', '15666666666', '1', '', '$2a$10$ndPEl8CTVVWSQxW5b2QbrOLs2DTMnAjxwtG1pKDc5h45P.sobVYwy', '0', '0', '127.0.0.1', '2023-12-22 14:34:52', 'admin', '2023-11-27 15:23:23', 'admin', '2023-12-22 14:34:42', '测试员');

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 1);
INSERT INTO `sys_user_post` VALUES (2, 2);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);

SET FOREIGN_KEY_CHECKS = 1;
