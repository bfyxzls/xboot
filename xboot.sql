/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : xboot

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 01/06/2020 19:59:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_LOCKS` VALUES ('quartzScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int DEFAULT NULL,
  `INT_PROP_2` int DEFAULT NULL,
  `LONG_PROP_1` bigint DEFAULT NULL,
  `LONG_PROP_2` bigint DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint DEFAULT NULL,
  `PREV_FIRE_TIME` bigint DEFAULT NULL,
  `PRIORITY` int DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for evaluation_court
-- ----------------------------
DROP TABLE IF EXISTS `evaluation_court`;
CREATE TABLE `evaluation_court` (
  `id` bigint unsigned NOT NULL,
  `title` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '内容',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `department_id` bigint DEFAULT NULL COMMENT '组织机构ID',
  `tenement_id` bigint DEFAULT NULL COMMENT '物业公司ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小区';

-- ----------------------------
-- Table structure for evaluation_record
-- ----------------------------
DROP TABLE IF EXISTS `evaluation_record`;
CREATE TABLE `evaluation_record` (
  `id` bigint NOT NULL,
  `title` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '内容',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `create_department_id` bigint DEFAULT NULL,
  `court_id` bigint DEFAULT NULL,
  `tenement_id` bigint DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  `task_id` bigint DEFAULT NULL,
  `score` float(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for evaluation_report
-- ----------------------------
DROP TABLE IF EXISTS `evaluation_report`;
CREATE TABLE `evaluation_report` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `tenement_id` bigint DEFAULT NULL COMMENT '物业公司ID',
  `court_id` bigint DEFAULT NULL COMMENT '小区ID',
  `type_id` bigint DEFAULT NULL,
  `task_id` bigint DEFAULT NULL,
  `score` int DEFAULT NULL COMMENT '最终得分',
  `total_date` datetime DEFAULT NULL COMMENT '报告产生日期',
  `file_url` varchar(255) DEFAULT NULL COMMENT '报告URL',
  `total_count` int DEFAULT NULL COMMENT '提交次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for evaluation_run_task
-- ----------------------------
DROP TABLE IF EXISTS `evaluation_run_task`;
CREATE TABLE `evaluation_run_task` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `task_id` bigint DEFAULT NULL COMMENT '任务ID',
  `task_type_id` bigint DEFAULT NULL COMMENT '任务当前的节点（关系表ID）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='正在运行中的任务';

-- ----------------------------
-- Table structure for evaluation_task
-- ----------------------------
DROP TABLE IF EXISTS `evaluation_task`;
CREATE TABLE `evaluation_task` (
  `id` bigint unsigned NOT NULL,
  `title` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名称',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `status` int DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务表';

-- ----------------------------
-- Records of evaluation_task
-- ----------------------------
BEGIN;
INSERT INTO `evaluation_task` VALUES (1, '第一季度1号文件', NULL, NULL, NULL, NULL, NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for evaluation_task_type
-- ----------------------------
DROP TABLE IF EXISTS `evaluation_task_type`;
CREATE TABLE `evaluation_task_type` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `task_id` bigint NOT NULL COMMENT '任务ID',
  `type_id` bigint NOT NULL COMMENT '评价类型ID',
  `limit` int NOT NULL COMMENT '提交次数上限',
  `percent` int NOT NULL COMMENT '百分比',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of evaluation_task_type
-- ----------------------------
BEGIN;
INSERT INTO `evaluation_task_type` VALUES (1, NULL, NULL, NULL, NULL, NULL, 1, 1, 50, 40);
INSERT INTO `evaluation_task_type` VALUES (2, NULL, NULL, NULL, NULL, NULL, 1, 2, 1, 30);
INSERT INTO `evaluation_task_type` VALUES (3, NULL, NULL, NULL, NULL, NULL, 1, 3, 1, 30);
COMMIT;

-- ----------------------------
-- Table structure for evaluation_template
-- ----------------------------
DROP TABLE IF EXISTS `evaluation_template`;
CREATE TABLE `evaluation_template` (
  `id` bigint unsigned NOT NULL,
  `title` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名称',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL COMMENT '显示的内容',
  `score` int DEFAULT NULL COMMENT '分值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for evaluation_tenement
-- ----------------------------
DROP TABLE IF EXISTS `evaluation_tenement`;
CREATE TABLE `evaluation_tenement` (
  `id` bigint unsigned NOT NULL,
  `title` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '内容',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物业公司';

-- ----------------------------
-- Table structure for evaluation_type
-- ----------------------------
DROP TABLE IF EXISTS `evaluation_type`;
CREATE TABLE `evaluation_type` (
  `id` bigint unsigned NOT NULL,
  `title` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '内容',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评价类型';

-- ----------------------------
-- Records of evaluation_type
-- ----------------------------
BEGIN;
INSERT INTO `evaluation_type` VALUES (1, '业主评价表', 'test', '2020-05-30 13:26:00', 'test', '2020-05-30 13:26:06', 0);
INSERT INTO `evaluation_type` VALUES (2, '专家评价表', NULL, NULL, NULL, NULL, 0);
INSERT INTO `evaluation_type` VALUES (3, '管理评价表', NULL, NULL, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for t_client
-- ----------------------------
DROP TABLE IF EXISTS `t_client`;
CREATE TABLE `t_client` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `home_uri` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `redirect_uri` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_department
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `parent_id` bigint unsigned DEFAULT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `is_parent` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_department
-- ----------------------------
BEGIN;
INSERT INTO `t_department` VALUES (40322777781112832, '', '2018-08-10 20:40:40', 0, '', '2018-08-11 00:03:06', 0, 1.00, 0, '总部', b'1');
INSERT INTO `t_department` VALUES (40322811096469504, '', '2018-08-10 20:40:48', 0, '', '2018-08-11 00:27:05', 40322777781112832, 1.00, 0, '技术部', b'1');
INSERT INTO `t_department` VALUES (40322852833988608, '', '2018-08-10 20:40:58', 0, '', '2018-08-11 01:29:42', 40322811096469504, 1.00, 0, '研发中心', NULL);
INSERT INTO `t_department` VALUES (40327204755738624, '', '2018-08-10 20:58:15', 0, '', '2018-08-10 22:02:15', 40322811096469504, 2.00, 0, '大数据', NULL);
INSERT INTO `t_department` VALUES (40327253309001728, '', '2018-08-10 20:58:27', 0, '', '2018-08-11 17:26:38', 40322811096469504, 3.00, -1, '人工智障', NULL);
INSERT INTO `t_department` VALUES (40343262766043136, '', '2018-08-10 22:02:04', 0, '', '2018-08-11 00:02:53', 0, 2.00, 0, '成都分部', b'1');
INSERT INTO `t_department` VALUES (40344005342400512, '', '2018-08-10 22:05:01', 0, '', '2018-08-11 17:48:44', 40343262766043136, 2.00, 0, 'Vue', NULL);
INSERT INTO `t_department` VALUES (40389030113710080, '', '2018-08-11 01:03:56', 0, '', '2018-08-11 17:50:04', 40343262766043136, 1.00, 0, 'JAVA', b'0');
INSERT INTO `t_department` VALUES (40652270295060480, '', '2018-08-11 18:29:57', 0, '', '2018-08-12 18:45:01', 0, 3.00, 0, '人事部', b'1');
INSERT INTO `t_department` VALUES (40652338142121984, NULL, '2018-08-11 18:30:13', 0, NULL, '2018-08-11 18:30:13', 40652270295060480, 1.00, 0, '游客', b'0');
INSERT INTO `t_department` VALUES (40681289119961088, '', '2018-08-11 20:25:16', 0, '', '2018-08-11 22:47:48', 40652270295060480, 2.00, 0, 'VIP', b'0');
INSERT INTO `t_department` VALUES (279091577329029120, 'admin', '2020-05-30 09:42:39', 0, 'admin', '2020-05-30 09:43:13', 0, 0.00, 0, '北京', b'1');
INSERT INTO `t_department` VALUES (279091722783297536, 'admin', '2020-05-30 09:43:13', 0, 'admin', '2020-05-30 09:44:59', 279091577329029120, 0.00, 0, '西城', b'1');
INSERT INTO `t_department` VALUES (279092167815729152, 'admin', '2020-05-30 09:44:59', 0, 'admin', '2020-05-30 09:46:12', 279091722783297536, 0.00, 0, '月坛', b'1');
INSERT INTO `t_department` VALUES (279092472854876160, 'admin', '2020-05-30 09:46:12', 0, 'admin', '2020-05-30 09:46:38', 279092167815729152, 0.00, 0, '月坛街道办事处社区', b'1');
INSERT INTO `t_department` VALUES (279092582775001088, 'admin', '2020-05-30 09:46:38', 0, 'admin', '2020-05-30 09:46:38', 279092472854876160, 0.00, 0, '新华一里', b'0');
COMMIT;

-- ----------------------------
-- Table structure for t_department_header
-- ----------------------------
DROP TABLE IF EXISTS `t_department_header`;
CREATE TABLE `t_department_header` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `department_id` bigint unsigned DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `user_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_department_header
-- ----------------------------
BEGIN;
INSERT INTO `t_department_header` VALUES (1254427833757995008, 'admin', '2020-04-26 23:11:16', 0, 'admin', '2020-04-26 23:11:16', 40322777781112832, 0, 682265633886208);
COMMIT;

-- ----------------------------
-- Table structure for t_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dict
-- ----------------------------
BEGIN;
INSERT INTO `t_dict` VALUES (75135930788220928, 'admin', '2018-11-14 22:15:43', 0, 'admin', '2018-11-27 01:39:06', '', '性别', 'sex', 0.00);
INSERT INTO `t_dict` VALUES (75383353938808832, 'admin', '2018-11-15 14:38:53', 0, 'admin', '2018-11-27 01:39:15', '', '消息类型', 'message_type', 1.00);
INSERT INTO `t_dict` VALUES (75388696739713024, 'admin', '2018-11-15 15:00:07', 0, 'admin', '2018-11-27 01:39:22', '', '按钮权限类型', 'permission_type', 2.00);
INSERT INTO `t_dict` VALUES (75392985935646720, 'admin', '2018-11-15 15:17:10', 0, 'admin', '2018-11-27 01:39:29', '', '腾讯云对象存储区域', 'tencent_bucket_region', 3.00);
INSERT INTO `t_dict` VALUES (79717732567748608, 'admin', '2018-11-27 13:42:10', 0, 'admin', '2018-11-27 13:42:10', '', '流程节点类型', 'process_node_type', 4.00);
INSERT INTO `t_dict` VALUES (81843858882695168, 'admin', '2018-12-03 10:30:38', 0, 'admin', '2018-12-03 10:30:49', '', '优先级', 'priority', 5.00);
INSERT INTO `t_dict` VALUES (82236987314016256, 'admin', '2018-12-04 12:32:47', 0, 'admin', '2018-12-04 12:32:47', '', '业务表', 'business_table', 6.00);
INSERT INTO `t_dict` VALUES (82236987314016257, 'admin', '2018-12-04 12:32:47', 0, 'admin', '2018-12-04 12:32:47', '', '业务表单路由', 'business_form', 7.00);
INSERT INTO `t_dict` VALUES (99020862912466944, 'admin', '2019-01-19 20:05:54', 0, 'admin', '2019-01-19 20:06:10', '', '请假类型', 'leave_type', 8.00);
COMMIT;

-- ----------------------------
-- Table structure for t_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `t_dict_data`;
CREATE TABLE `t_dict_data` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dict_id` bigint unsigned DEFAULT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sort_order` (`sort_order`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dict_data
-- ----------------------------
BEGIN;
INSERT INTO `t_dict_data` VALUES (75158227712479232, 'admin', '2018-11-14 23:44:19', 0, 'admin', '2019-04-01 23:53:13', '', 75135930788220928, 0.00, 0, '男', '男');
INSERT INTO `t_dict_data` VALUES (75159254272577536, 'admin', '2018-11-14 23:48:24', 0, 'admin', '2019-04-01 23:53:17', '', 75135930788220928, 1.00, 0, '女', '女');
INSERT INTO `t_dict_data` VALUES (75159898425397248, 'admin', '2018-11-14 23:50:57', 0, 'admin', '2019-04-01 23:53:22', '', 75135930788220928, 2.00, -1, '保密', '保密');
INSERT INTO `t_dict_data` VALUES (75385648017575936, 'admin', '2018-11-15 14:48:00', 0, 'admin', '2019-04-02 00:10:36', '', 75383353938808832, 0.00, 0, '系统公告', '系统公告');
INSERT INTO `t_dict_data` VALUES (75385706913992704, 'admin', '2018-11-15 14:48:14', 0, 'admin', '2019-04-02 00:10:32', '', 75383353938808832, 1.00, 0, '提醒', '提醒');
INSERT INTO `t_dict_data` VALUES (75385774274514944, 'admin', '2018-11-15 14:48:30', 0, 'admin', '2019-04-02 00:10:28', '', 75383353938808832, 2.00, 0, '私信', '私信');
INSERT INTO `t_dict_data` VALUES (75390787835138048, 'admin', '2018-11-15 15:08:26', 0, 'admin', '2018-11-15 15:08:26', '', 75388696739713024, 0.00, 0, '查看操作(view)', 'view');
INSERT INTO `t_dict_data` VALUES (75390886501945344, 'admin', '2018-11-15 15:08:49', 0, 'admin', '2018-11-15 15:08:57', '', 75388696739713024, 1.00, 0, '添加操作(add)', 'add');
INSERT INTO `t_dict_data` VALUES (75390993939042304, 'admin', '2018-11-15 15:09:15', 0, 'admin', '2018-11-15 15:09:15', '', 75388696739713024, 2.00, 0, '编辑操作(edit)', 'edit');
INSERT INTO `t_dict_data` VALUES (75391067532300288, 'admin', '2018-11-15 15:09:32', 0, 'admin', '2018-11-15 15:09:32', '', 75388696739713024, 3.00, 0, '删除操作(delete)', 'delete');
INSERT INTO `t_dict_data` VALUES (75391126902673408, 'admin', '2018-11-15 15:09:46', 0, 'admin', '2018-11-15 15:09:46', '', 75388696739713024, 4.00, 0, '清空操作(clear)', 'clear');
INSERT INTO `t_dict_data` VALUES (75391192883269632, 'admin', '2018-11-15 15:10:02', 0, 'admin', '2018-11-15 15:10:02', '', 75388696739713024, 5.00, 0, '启用操作(enable)', 'enable');
INSERT INTO `t_dict_data` VALUES (75391251024711680, 'admin', '2018-11-15 15:10:16', 0, 'admin', '2018-11-15 15:10:16', '', 75388696739713024, 6.00, 0, '禁用操作(disable)', 'disable');
INSERT INTO `t_dict_data` VALUES (75391297124306944, 'admin', '2018-11-15 15:10:27', 0, 'admin', '2018-11-15 15:10:27', '', 75388696739713024, 7.00, 0, '搜索操作(search)', 'search');
INSERT INTO `t_dict_data` VALUES (75391343379091456, 'admin', '2018-11-15 15:10:38', 0, 'admin', '2018-11-15 15:10:38', '', 75388696739713024, 8.00, 0, '上传文件(upload)', 'upload');
INSERT INTO `t_dict_data` VALUES (75391407526776832, 'admin', '2018-11-15 15:10:53', 0, 'admin', '2018-11-15 15:10:53', '', 75388696739713024, 9.00, 0, '导出操作(output)', 'output');
INSERT INTO `t_dict_data` VALUES (75391475042488320, 'admin', '2018-11-15 15:11:09', 0, 'admin', '2018-11-15 15:11:09', '', 75388696739713024, 10.00, 0, '导入操作(input)', 'input');
INSERT INTO `t_dict_data` VALUES (75391522182270976, 'admin', '2018-11-15 15:11:21', 0, 'admin', '2018-11-15 15:11:21', '', 75388696739713024, 11.00, 0, '分配权限(editPerm)', 'editPerm');
INSERT INTO `t_dict_data` VALUES (75391576364290048, 'admin', '2018-11-15 15:11:34', 0, 'admin', '2018-11-15 15:11:34', '', 75388696739713024, 12.00, 0, '设为默认(setDefault)', 'setDefault');
INSERT INTO `t_dict_data` VALUES (75391798033256448, 'admin', '2018-11-15 15:12:26', 0, 'admin', '2018-11-15 15:12:26', '', 75388696739713024, 13.00, 0, '其他操作(other)', 'other');
INSERT INTO `t_dict_data` VALUES (75393605291741184, 'admin', '2018-11-15 15:19:37', 0, 'admin', '2018-11-15 15:19:37', '', 75392985935646720, 0.00, 0, '北京一区（华北）', 'ap-beijing-1');
INSERT INTO `t_dict_data` VALUES (75393681254780928, 'admin', '2018-11-15 15:19:55', 0, 'admin', '2018-11-15 15:19:55', '', 75392985935646720, 1.00, 0, '北京', 'ap-beijing');
INSERT INTO `t_dict_data` VALUES (75393767619694592, 'admin', '2018-11-15 15:20:16', 0, 'admin', '2018-11-15 15:20:16', '', 75392985935646720, 2.00, 0, '上海（华东）', 'ap-shanghai');
INSERT INTO `t_dict_data` VALUES (75393851484803072, 'admin', '2018-11-15 15:20:36', 0, 'admin', '2018-11-15 15:20:36', '', 75392985935646720, 3.00, 0, '广州（华南）', 'ap-guangzhou');
INSERT INTO `t_dict_data` VALUES (75393961887272960, 'admin', '2018-11-15 15:21:02', 0, 'admin', '2018-11-15 15:21:02', '', 75392985935646720, 4.00, 0, '成都（西南）', 'ap-chengdu');
INSERT INTO `t_dict_data` VALUES (75394053969022976, 'admin', '2018-11-15 15:21:24', 0, 'admin', '2018-11-15 15:21:24', '', 75392985935646720, 5.00, 0, '重庆', 'ap-chongqing');
INSERT INTO `t_dict_data` VALUES (75394122474590208, 'admin', '2018-11-15 15:21:41', 0, 'admin', '2018-11-15 15:21:41', '', 75392985935646720, 6.00, 0, '新加坡', 'ap-singapore');
INSERT INTO `t_dict_data` VALUES (75394186202845184, 'admin', '2018-11-15 15:21:56', 0, 'admin', '2018-11-15 15:21:56', '', 75392985935646720, 7.00, 0, '香港', 'ap-hongkong');
INSERT INTO `t_dict_data` VALUES (75394254255427584, 'admin', '2018-11-15 15:22:12', 0, 'admin', '2018-11-15 15:22:12', '', 75392985935646720, 8.00, 0, '多伦多', 'na-toronto');
INSERT INTO `t_dict_data` VALUES (75394309125312512, 'admin', '2018-11-15 15:22:25', 0, 'admin', '2018-11-15 15:22:25', '', 75392985935646720, 9.00, 0, '法兰克福', 'eu-frankfurt');
INSERT INTO `t_dict_data` VALUES (75394367044456448, 'admin', '2018-11-15 15:22:39', 0, 'admin', '2018-11-15 15:22:39', '', 75392985935646720, 10.00, 0, '孟买', 'ap-mumbai');
INSERT INTO `t_dict_data` VALUES (75394448523005952, 'admin', '2018-11-15 15:22:58', 0, 'admin', '2018-11-15 15:22:58', '', 75392985935646720, 11.00, 0, '首尔', 'ap-seoul');
INSERT INTO `t_dict_data` VALUES (75394604765024256, 'admin', '2018-11-15 15:23:36', 0, 'admin', '2018-11-15 15:23:36', '', 75392985935646720, 12.00, 0, '硅谷', 'na-siliconvalley');
INSERT INTO `t_dict_data` VALUES (75394659299364864, 'admin', '2018-11-15 15:23:49', 0, 'admin', '2018-11-15 15:23:49', '', 75392985935646720, 13.00, 0, '弗吉尼亚', 'na-ashburn');
INSERT INTO `t_dict_data` VALUES (75394705700950016, 'admin', '2018-11-15 15:24:00', 0, 'admin', '2018-11-15 15:24:00', '', 75392985935646720, 14.00, 0, '曼谷', 'ap-bangkok');
INSERT INTO `t_dict_data` VALUES (75394759287377920, 'admin', '2018-11-15 15:24:12', 0, 'admin', '2018-11-15 15:24:12', '', 75392985935646720, 15.00, 0, '莫斯科', 'eu-moscow');
INSERT INTO `t_dict_data` VALUES (79717808262352896, 'admin', '2018-11-27 13:42:28', 0, 'admin', '2018-11-27 13:42:28', '', 79717732567748608, 0.00, 0, '开始节点', '0');
INSERT INTO `t_dict_data` VALUES (79717858308788224, 'admin', '2018-11-27 13:42:40', 0, 'admin', '2018-11-27 13:42:40', '', 79717732567748608, 1.00, 0, '审批节点', '1');
INSERT INTO `t_dict_data` VALUES (79717920061526016, 'admin', '2018-11-27 13:42:54', 0, 'admin', '2018-12-08 20:35:39', '', 79717732567748608, 2.00, 0, '结束节点', '2');
INSERT INTO `t_dict_data` VALUES (81843987719131136, 'admin', '2018-12-03 10:31:08', 0, 'admin', '2018-12-03 10:31:08', '', 81843858882695168, 0.00, 0, '普通', '0');
INSERT INTO `t_dict_data` VALUES (81844044015079424, 'admin', '2018-12-03 10:31:22', 0, 'admin', '2018-12-03 10:31:22', '', 81843858882695168, 1.00, 0, '重要', '1');
INSERT INTO `t_dict_data` VALUES (81844100705292288, 'admin', '2018-12-03 10:31:35', 0, 'admin', '2018-12-03 10:31:35', '', 81843858882695168, 2.00, 0, '紧急', '2');
INSERT INTO `t_dict_data` VALUES (82237106587439104, 'admin', '2018-12-04 12:33:15', 0, 'admin', '2018-12-04 12:33:15', '', 82236987314016256, 0.00, 0, '请假申请表', 't_leave');
INSERT INTO `t_dict_data` VALUES (82237106587439105, 'admin', '2018-12-04 12:33:15', 0, 'admin', '2018-12-04 12:33:15', '', 82236987314016257, 0.00, 0, '请假申请表', 'leave');
INSERT INTO `t_dict_data` VALUES (99020985985929216, 'admin', '2019-01-19 20:06:23', 0, 'admin', '2019-04-01 23:55:48', '', 99020862912466944, 0.00, 0, '年假', '年假');
INSERT INTO `t_dict_data` VALUES (99021020739932160, 'admin', '2019-01-19 20:06:32', 0, 'admin', '2019-04-01 23:55:52', '', 99020862912466944, 1.00, 0, '事假', '事假');
INSERT INTO `t_dict_data` VALUES (99021195566911488, 'admin', '2019-01-19 20:07:13', 0, 'admin', '2019-04-01 23:55:56', '', 99020862912466944, 2.00, 0, '病假', '病假');
INSERT INTO `t_dict_data` VALUES (99021242476007424, 'admin', '2019-01-19 20:07:24', 0, 'admin', '2019-04-01 23:56:01', '', 99020862912466944, 3.00, 0, '调休', '调休');
INSERT INTO `t_dict_data` VALUES (99021300730695680, 'admin', '2019-01-19 20:07:38', 0, 'admin', '2019-04-01 23:56:05', '', 99020862912466944, 4.00, 0, '产假', '产假');
INSERT INTO `t_dict_data` VALUES (99021341889400832, 'admin', '2019-01-19 20:07:48', 0, 'admin', '2019-04-01 23:56:10', '', 99020862912466944, 5.00, 0, '陪产假', '陪产假');
INSERT INTO `t_dict_data` VALUES (99021382393794560, 'admin', '2019-01-19 20:07:58', 0, 'admin', '2019-04-01 23:56:14', '', 99020862912466944, 6.00, 0, '婚假', '婚假');
INSERT INTO `t_dict_data` VALUES (99021437171404800, 'admin', '2019-01-19 20:08:11', 0, 'admin', '2019-04-01 23:56:18', '', 99020862912466944, 7.00, 0, '例假', '例假');
INSERT INTO `t_dict_data` VALUES (99021497724571648, 'admin', '2019-01-19 20:08:25', 0, 'admin', '2019-04-01 23:56:23', '', 99020862912466944, 8.00, 0, '丧假', '丧假');
INSERT INTO `t_dict_data` VALUES (99021556704874496, 'admin', '2019-01-19 20:08:39', 0, 'admin', '2019-04-01 23:56:27', '', 99020862912466944, 9.00, 0, '哺乳假', '哺乳假');
INSERT INTO `t_dict_data` VALUES (125170157323554816, 'admin', '2019-04-01 23:53:52', 0, 'admin', '2019-04-01 23:53:52', '', 75383353938808832, 3.00, 0, '工作流', '工作流');
COMMIT;

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `size` bigint unsigned DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `f_key` varchar(255) DEFAULT NULL,
  `location` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `cost_time` int unsigned DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `ip_info` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `request_param` longtext,
  `request_type` varchar(255) DEFAULT NULL,
  `request_url` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `log_type` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_log
-- ----------------------------
BEGIN;
INSERT INTO `t_log` VALUES (278407754605924352, NULL, '2020-05-28 12:25:23', 0, NULL, '2020-05-28 12:25:23', 546, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"7UK3\",\"saveLogin\":\"true\",\"captchaId\":\"c636c837748742858f41f19b97ca8498\",\"username\":\"admin\"}', 'POST', '/xboot/login', 'admin', 1);
INSERT INTO `t_log` VALUES (278779807947296768, NULL, '2020-05-29 13:03:47', 0, NULL, '2020-05-29 13:03:47', 234, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"4erB\",\"saveLogin\":\"true\",\"captchaId\":\"6c0bf98316704d908f987b8d73d8d7c4\",\"username\":\"admin\"}', 'POST', '/xboot/login', 'admin', 1);
INSERT INTO `t_log` VALUES (278780378846597120, NULL, '2020-05-29 13:06:03', 0, NULL, '2020-05-29 13:06:03', 221, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"tyvT\",\"saveLogin\":\"true\",\"captchaId\":\"3b034d6a4fe645b08bb8d3f8b4c14645\",\"username\":\"Exrick\"}', 'POST', '/xboot/login', 'Exrick', 1);
INSERT INTO `t_log` VALUES (278780484425617408, NULL, '2020-05-29 13:06:28', 0, NULL, '2020-05-29 13:06:28', 191, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"nfgY\",\"saveLogin\":\"true\",\"captchaId\":\"408584b457e5457ca0b0b2b6021d79aa\",\"username\":\"admin\"}', 'POST', '/xboot/login', 'admin', 1);
INSERT INTO `t_log` VALUES (278780794606981120, NULL, '2020-05-29 13:07:42', 0, NULL, '2020-05-29 13:07:42', 163, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"iwbs\",\"saveLogin\":\"true\",\"captchaId\":\"1fb40466db4748f59e225c46e43b436b\",\"username\":\"Exrick\"}', 'POST', '/xboot/login', 'Exrick', 1);
INSERT INTO `t_log` VALUES (278780942519111680, NULL, '2020-05-29 13:08:17', 0, NULL, '2020-05-29 13:08:17', 165, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"GmV2\",\"saveLogin\":\"true\",\"captchaId\":\"a26bf507e3ec4326969d205118eaa9b1\",\"username\":\"Exrick\"}', 'POST', '/xboot/login', 'Exrick', 1);
INSERT INTO `t_log` VALUES (278781971167973376, NULL, '2020-05-29 13:12:23', 0, NULL, '2020-05-29 13:12:23', 142, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"CNcn\",\"saveLogin\":\"true\",\"captchaId\":\"cc3e55956761439592bf6f599fb3d93f\",\"username\":\"admin\"}', 'POST', '/xboot/login', 'admin', 1);
INSERT INTO `t_log` VALUES (278782563965734912, NULL, '2020-05-29 13:14:44', 0, NULL, '2020-05-29 13:14:44', 195, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"njSm\",\"saveLogin\":\"true\",\"captchaId\":\"0a2350a61fb34cfe955806037448966a\",\"username\":\"Exrick\"}', 'POST', '/xboot/login', 'Exrick', 1);
INSERT INTO `t_log` VALUES (278782709130596352, NULL, '2020-05-29 13:15:19', 0, NULL, '2020-05-29 13:15:19', 255, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"qoSB\",\"saveLogin\":\"true\",\"captchaId\":\"d0a7fbba1f6c47bd832b000439e3e86d\",\"username\":\"Exrick\"}', 'POST', '/xboot/login', 'Exrick', 1);
INSERT INTO `t_log` VALUES (278782808820813824, NULL, '2020-05-29 13:15:42', 0, NULL, '2020-05-29 13:15:42', 186, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"1k9v\",\"saveLogin\":\"true\",\"captchaId\":\"f3f9d4d26c394d7c9552a67d6a763211\",\"username\":\"admin\"}', 'POST', '/xboot/login', 'admin', 1);
INSERT INTO `t_log` VALUES (278783291128025088, NULL, '2020-05-29 13:17:37', 0, NULL, '2020-05-29 13:17:37', 220, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"vgz6\",\"saveLogin\":\"true\",\"captchaId\":\"fd71ed5b0c644c3e8feb9ccaabd8dff0\",\"username\":\"Exrick\"}', 'POST', '/xboot/login', 'Exrick', 1);
INSERT INTO `t_log` VALUES (278783438482313216, NULL, '2020-05-29 13:18:12', 0, NULL, '2020-05-29 13:18:12', 161, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"SoDI\",\"saveLogin\":\"true\",\"captchaId\":\"1dd8b38ed4334364ab63464382e86488\",\"username\":\"Exrick\"}', 'POST', '/xboot/login', 'Exrick', 1);
INSERT INTO `t_log` VALUES (278784056697556992, NULL, '2020-05-29 13:20:40', 0, NULL, '2020-05-29 13:20:40', 173, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"4DtA\",\"saveLogin\":\"true\",\"captchaId\":\"314d56f8fc574de8aba7512b0c94228a\",\"username\":\"admin\"}', 'POST', '/xboot/login', 'admin', 1);
INSERT INTO `t_log` VALUES (278785568521850880, NULL, '2020-05-29 13:26:41', 0, NULL, '2020-05-29 13:26:41', 506, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"icza\",\"saveLogin\":\"true\",\"captchaId\":\"5210a8e13210471c892da749c7e183ce\",\"username\":\"admin\"}', 'POST', '/xboot/login', 'admin', 1);
INSERT INTO `t_log` VALUES (278793282660601856, NULL, '2020-05-29 13:57:20', 0, NULL, '2020-05-29 13:57:20', 660, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"IHGP\",\"saveLogin\":\"true\",\"captchaId\":\"edaf6bac81524dc3a8eb51148f627c01\",\"username\":\" lind\"}', 'POST', '/xboot/login', 'lind', 1);
INSERT INTO `t_log` VALUES (278793358904659968, NULL, '2020-05-29 13:57:38', 0, NULL, '2020-05-29 13:57:38', 248, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"iNZp\",\"saveLogin\":\"true\",\"captchaId\":\"c48c31189d32490581ce42e3d9af1e77\",\"username\":\"admin\"}', 'POST', '/xboot/login', 'admin', 1);
INSERT INTO `t_log` VALUES (278793711586906112, NULL, '2020-05-29 13:59:02', 0, NULL, '2020-05-29 13:59:02', 260, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"4NtH\",\"saveLogin\":\"true\",\"captchaId\":\"3639f2a3a47a4898889da06f609e2306\",\"username\":\"admin\"}', 'POST', '/xboot/login', 'admin', 1);
INSERT INTO `t_log` VALUES (278795269414326272, NULL, '2020-05-29 14:05:13', 0, NULL, '2020-05-29 14:05:13', 269, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"3A5z\",\"saveLogin\":\"true\",\"captchaId\":\"bd7f8efa27b440aeae82dce044872c61\",\"username\":\"lind\"}', 'POST', '/xboot/login', 'lind', 1);
INSERT INTO `t_log` VALUES (278797902791315456, NULL, '2020-05-29 14:15:41', 0, NULL, '2020-05-29 14:15:41', 551, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"qnum\",\"saveLogin\":\"true\",\"captchaId\":\"095b71da964744b9a65476e845b87f8e\",\"username\":\" lind\"}', 'POST', '/xboot/login', 'lind', 1);
INSERT INTO `t_log` VALUES (278798655236870144, NULL, '2020-05-29 14:18:40', 0, NULL, '2020-05-29 14:18:40', 195, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"g3zw\",\"saveLogin\":\"true\",\"captchaId\":\"8f244b13af8a431ca4c4192c3a39658c\",\"username\":\"admin\"}', 'POST', '/xboot/login', 'admin', 1);
INSERT INTO `t_log` VALUES (278801429173374976, NULL, '2020-05-29 14:29:42', 0, NULL, '2020-05-29 14:29:42', 810, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"o3BE\",\"saveLogin\":\"true\",\"captchaId\":\"5d893c4cfdae40c2a5c28074d6c9949a\",\"username\":\"lind\"}', 'POST', '/xboot/login', 'lind', 1);
INSERT INTO `t_log` VALUES (278958236856487936, NULL, '2020-05-30 00:52:48', 0, NULL, '2020-05-30 00:52:48', 213, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"UFBi\",\"saveLogin\":\"true\",\"captchaId\":\"7cef3c118cdb41d1a33a6ad7b38b7856\",\"username\":\"admin\"}', 'POST', '/xboot/login', 'admin', 1);
INSERT INTO `t_log` VALUES (279094549706444800, NULL, '2020-05-30 09:54:27', 0, NULL, '2020-05-30 09:54:27', 354, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"IvpG\",\"saveLogin\":\"true\",\"captchaId\":\"e297ffb490e54e31a4a99fffd397de67\",\"username\":\" xinhua\"}', 'POST', '/xboot/login', 'xinhua', 1);
INSERT INTO `t_log` VALUES (279111088157495296, NULL, '2020-05-30 11:00:10', 0, NULL, '2020-05-30 11:00:10', 158, '127.0.0.1', '未知', '登录系统', '{\"password\":\"你是看不见我的\",\"code\":\"fjUY\",\"saveLogin\":\"true\",\"captchaId\":\"5042523e8cdb4ee1be74d46eeedb06db\",\"username\":\"admin\"}', 'POST', '/xboot/login', 'admin', 1);
COMMIT;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` bigint unsigned DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `sort_order` decimal(10,2) DEFAULT NULL,
  `component` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `level` int unsigned DEFAULT NULL,
  `button_type` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `show_always` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
BEGIN;
INSERT INTO `t_permission` VALUES (5129710648430592, '', '2018-06-04 19:02:29', 0, '', '2018-09-29 23:11:56', '', 'sys', 125909152017944576, 0, 1.00, 'Main', '/sys', '系统管理', 'ios-settings', 1, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (5129710648430593, '', '2018-06-04 19:02:32', 0, '', '2018-08-13 15:15:33', '', 'user-manage', 5129710648430592, 0, 1.10, 'sys/user-manage/userManage', 'user-manage', '用户管理', 'md-person', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (5129710648430594, '', '2018-06-04 19:02:35', 0, '', '2018-10-13 13:51:36', '', 'role-manage', 5129710648430592, 0, 1.60, 'sys/role-manage/roleManage', 'role-manage', '角色权限管理', 'md-contacts', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (5129710648430595, '', '2018-06-04 19:02:37', 0, '', '2018-09-23 23:32:02', '', 'menu-manage', 5129710648430592, 0, 1.70, 'sys/menu-manage/menuManage', 'menu-manage', '菜单权限管理', 'md-menu', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (15701400130424832, '', '2018-06-03 22:04:06', 0, '', '2018-09-19 22:16:44', '', '', 5129710648430593, 1, 1.11, '', '/xboot/user/admin/add*', '添加用户', '', 3, 'add', 0, '', b'1');
INSERT INTO `t_permission` VALUES (15701915807518720, '', '2018-06-03 22:06:09', 0, '', '2018-06-06 14:46:51', '', '', 5129710648430593, 1, 1.13, '', '/xboot/user/admin/disable/**', '禁用用户', '', 3, 'disable', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (15708892205944832, '', '2018-06-03 22:33:52', 0, '', '2018-06-28 16:44:48', '', '', 5129710648430593, 1, 1.14, '', '/xboot/user/admin/enable/**', '启用用户', '', 3, 'enable', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (16392452747300864, '', '2018-06-05 19:50:06', 0, 'admin', '2019-06-26 20:38:41', '', 'access', 125909152017944576, 0, 7.00, 'Main', '/access', '权限按钮测试页', 'md-lock', 1, '', 0, '', b'0');
INSERT INTO `t_permission` VALUES (16392767785668608, '', '2018-06-05 19:51:21', 0, 'admin', '2018-10-23 12:34:38', '', 'access_index', 16392452747300864, 0, 5.10, 'access/access', 'index', '权限按钮测试页', 'md-lock', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (16438800255291392, '', '2018-06-05 22:54:18', 0, 'admin', '2018-10-23 12:34:51', '', '', 16392767785668608, 1, 5.11, '', 'test-add', '添加按钮测试', '', 3, 'add', 0, '', b'1');
INSERT INTO `t_permission` VALUES (16438962738434048, '', '2018-06-05 22:54:55', 0, 'admin', '2018-10-23 12:35:05', '', '', 16392767785668608, 1, 5.12, '', 'test-edit', '编辑按钮测试', '', 3, 'edit', 0, '', b'1');
INSERT INTO `t_permission` VALUES (16439068543946752, '', '2018-06-05 22:55:20', 0, 'admin', '2018-10-23 12:34:54', '', '', 16392767785668608, 1, 5.13, '', 'test-delete', '删除按钮测试', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (16678126574637056, '', '2018-06-06 14:45:16', 0, '', '2018-09-19 22:16:48', '', '', 5129710648430593, 1, 1.12, '', '/xboot/user/admin/edit*', '编辑用户', '', 3, 'edit', 0, '', b'1');
INSERT INTO `t_permission` VALUES (16678447719911424, '', '2018-06-06 14:46:32', 0, 'admin', '2020-04-28 14:27:03', '', '', 5129710648430593, 1, 1.15, '', '/xboot/user/delByIds**', '删除用户', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (16687383932047360, '', '2018-06-06 15:22:03', 0, '', '2018-09-19 22:07:34', '', '', 5129710648430594, 1, 1.21, '', '/xboot/role/save*', '添加角色', '', 3, 'add', 0, '', b'1');
INSERT INTO `t_permission` VALUES (16689632049631232, '', '2018-06-06 15:30:59', 0, '', '2018-09-19 22:07:37', '', '', 5129710648430594, 1, 1.22, '', '/xboot/role/edit*', '编辑角色', '', 3, 'edit', 0, '', b'1');
INSERT INTO `t_permission` VALUES (16689745006432256, '', '2018-06-06 15:31:26', 0, 'admin', '2020-04-28 14:29:02', '', '', 5129710648430594, 1, 1.23, '', '/xboot/role/delByIds**', '删除角色', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (16689883993083904, NULL, '2018-06-06 15:31:59', 0, NULL, '2018-06-06 15:31:59', NULL, NULL, 5129710648430594, 1, 1.24, NULL, '/xboot/role/editRolePerm**', '分配权限', NULL, 3, 'editPerm', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (16690313745666048, '', '2018-06-06 15:33:41', 0, '', '2018-09-19 22:07:46', '', '', 5129710648430594, 1, 1.25, '', '/xboot/role/setDefault*', '设为默认角色', '', 3, 'setDefault', 0, '', b'1');
INSERT INTO `t_permission` VALUES (16694861252005888, '', '2018-06-06 15:51:46', 0, '', '2018-09-19 22:07:52', '', '', 5129710648430595, 1, 1.31, '', '/xboot/permission/add*', '添加菜单', '', 3, 'add', 0, '', b'1');
INSERT INTO `t_permission` VALUES (16695107491205120, '', '2018-06-06 15:52:44', 0, '', '2018-09-19 22:07:57', '', '', 5129710648430595, 1, 1.32, '', '/xboot/permission/edit*', '编辑菜单', '', 3, 'edit', 0, '', b'1');
INSERT INTO `t_permission` VALUES (16695243126607872, '', '2018-06-06 15:53:17', 0, 'admin', '2020-04-28 14:29:17', '', '', 5129710648430595, 1, 1.33, '', '/xboot/permission/delByIds**', '删除菜单', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (25014528525733888, '', '2018-06-29 14:51:09', 0, '', '2018-10-08 11:13:27', '', '', 5129710648430593, 1, 1.16, '', '无', '上传图片', '', 3, 'upload', 0, '', b'1');
INSERT INTO `t_permission` VALUES (39915540965232640, NULL, '2018-08-09 17:42:28', 0, NULL, '2018-08-09 17:42:28', NULL, 'monitor', 125909152017944576, 0, 2.00, 'Main', '/monitor', '系统监控', 'ios-analytics', 1, NULL, 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (39916171171991552, '', '2018-08-09 17:44:57', 0, 'admin', '2019-01-20 00:37:29', '', 'druid', 39915540965232640, 0, 2.40, 'sys/monitor/monitor', 'druid', 'SQL监控', 'md-analytics', 2, '', 0, 'http://127.0.0.1:8888/druid/login.html', b'1');
INSERT INTO `t_permission` VALUES (39918482854252544, '', '2018-08-09 17:54:08', 0, 'admin', '2019-01-20 00:37:41', '', 'swagger', 39915540965232640, 0, 2.50, 'sys/monitor/monitor', 'swagger', '接口文档', 'md-document', 2, '', 0, 'http://127.0.0.1:8888/swagger-ui.html', b'1');
INSERT INTO `t_permission` VALUES (40238597734928384, NULL, '2018-08-10 15:06:10', 0, NULL, '2018-08-10 15:06:10', NULL, 'department-manage', 5129710648430592, 0, 1.20, 'sys/department-manage/departmentManage', 'department-manage', '部门管理', 'md-git-branch', 2, '', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (41363147411427328, '', '2018-08-13 17:34:43', 0, 'admin', '2020-03-25 20:31:16', '', 'log-manage', 39915540965232640, 0, 2.20, 'sys/log-manage/logManage', 'log-manage', '日志管理', 'md-list-box', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (41363537456533504, '', '2018-08-13 17:36:16', 0, 'admin', '2020-04-28 14:31:54', '', '', 41363147411427328, 1, 2.11, '', '/xboot/log/delByIds**', '删除日志', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (41364927394353152, '', '2018-08-13 17:41:48', 0, '', '2018-09-19 22:08:57', '', '', 41363147411427328, 1, 2.12, '', '/xboot/log/delAll*', '清空日志', '', 3, 'undefined', 0, '', b'1');
INSERT INTO `t_permission` VALUES (41370251991977984, NULL, '2018-08-13 18:02:57', 0, NULL, '2018-08-13 18:02:57', NULL, 'quartz-job', 39915540965232640, 0, 2.10, 'sys/quartz-manage/quartzManage', 'quartz-job', '定时任务', 'md-time', 2, '', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (41371711400054784, '', '2018-08-13 18:08:45', 0, '', '2019-07-07 21:24:26', '', 'actuator', 39915540965232640, 0, 2.30, 'sys/actuator/actuator', 'actuator', 'Actuator监控', 'logo-angular', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (41373430515240960, '', '2018-08-13 18:15:35', 0, 'admin', '2019-11-22 11:47:07', '', 'vue-template', 125909152017944576, 0, 3.10, 'Main', '/vue-template', '后台Vue模版', 'ios-albums', 1, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (41375330996326400, '', '2018-08-13 18:23:08', 0, 'admin', '2020-03-21 20:41:28', '', 'simple-table', 41373430515240960, 0, 3.10, 'xboot-vue-template/simple-table/simpleTable', 'simple-table', '简单表格+动态列', 'ios-grid-outline', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (41376192166629376, '', '2018-08-13 18:26:33', 0, 'admin', '2018-10-20 22:47:26', '', 'search-table', 41373430515240960, 0, 3.40, 'xboot-vue-template/search-table/searchTable', 'search-table', '搜索表格[付费]', 'md-grid', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (41377034236071936, '', '2018-08-13 18:29:54', 0, 'admin', '2018-10-20 22:47:33', '', 'complex-table', 41373430515240960, 0, 3.50, 'xboot-vue-template/complex-table/complexTable', 'complex-table', '复杂表格[付费]', 'ios-grid', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (41378916912336896, '', '2018-08-13 18:37:23', 0, 'admin', '2019-04-12 15:38:08', '', 'tree', 41373430515240960, 0, 3.22, 'xboot-vue-template/tree/tree', 'tree', '树形结构', 'ios-git-network', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (41469219249852416, NULL, '2018-08-14 00:36:13', 0, NULL, '2018-08-14 00:36:13', NULL, '', 41371711400054784, 1, 2.31, '', '无', '查看数据', '', 3, 'view', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (42082442672082944, '', '2018-08-15 17:12:57', 0, 'admin', '2019-04-12 15:48:50', '', 'new-window', 41373430515240960, 0, 3.21, 'xboot-vue-template/new-window/newWindow', 'new-window', '新窗口操作', 'ios-browsers', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (43117268627886080, '', '2018-08-18 13:44:58', 0, '', '2018-08-18 20:55:04', '', 'message-manage', 5129710648430592, 0, 1.30, 'sys/message-manage/messageManage', 'message-manage', '消息管理[付费]', 'md-mail', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (44986029924421632, '', '2018-08-23 17:30:46', 0, '', '2018-09-23 23:26:53', '', 'social-manage', 5129710648430592, 0, 1.50, 'sys/social-manage/socialManage', 'social-manage', '社交账号绑定[付费]', 'md-share', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (45069342940860416, '', '2018-08-23 23:01:49', 0, '', '2018-08-24 10:01:09', '', '', 44986029924421632, 1, 1.42, '', '无', '查看社交账号数据', '', 3, 'view', 0, '', b'1');
INSERT INTO `t_permission` VALUES (45235228800716800, '', '2018-08-24 10:01:00', 0, '', '2018-09-19 22:07:23', '', '', 44986029924421632, 1, 1.41, '', '/xboot/relate/delByIds*', '删除解绑', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (45235621697949696, '', '2018-08-24 10:02:33', 0, '', '2018-09-19 22:06:57', '', '', 40238597734928384, 1, 1.21, '', '/xboot/department/add*', '添加部门', '', 3, 'add', 0, '', b'1');
INSERT INTO `t_permission` VALUES (45235787867885568, '', '2018-08-24 10:03:13', 0, '', '2018-09-19 22:07:02', '', '', 40238597734928384, 1, 1.22, '', '/xboot/department/edit*', '编辑部门', '', 3, 'edit', 0, '', b'1');
INSERT INTO `t_permission` VALUES (45235939278065664, '', '2018-08-24 10:03:49', 0, 'admin', '2020-04-28 14:27:35', '', '', 40238597734928384, 1, 1.23, '', '/xboot/department/delByIds*', '删除部门', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (45236734832676864, '', '2018-08-24 10:06:59', 0, '', '2018-09-19 22:07:07', '', '', 43117268627886080, 1, 1.31, '', '/xboot/message/add*', '添加消息', '', 3, 'add', 0, '', b'1');
INSERT INTO `t_permission` VALUES (45237010692050944, '', '2018-08-24 10:08:04', 0, '', '2018-09-19 22:07:12', '', '', 43117268627886080, 1, 1.32, '', '/xboot/message/edit*', '编辑消息', '', 3, 'edit', 0, '', b'1');
INSERT INTO `t_permission` VALUES (45237170029465600, '', '2018-08-24 10:08:42', 0, 'admin', '2020-04-28 14:28:03', '', '', 43117268627886080, 1, 1.33, '', '/xboot/message/delByIds*', '删除消息', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (45264987354042368, '', '2018-08-24 11:59:14', 0, '', '2018-09-19 22:08:11', '', '', 41370251991977984, 1, 2.11, '', '/xboot/quartzJob/add*', '添加定时任务', '', 3, 'add', 0, '', b'1');
INSERT INTO `t_permission` VALUES (45265487029866496, '', '2018-08-24 12:01:14', 0, '', '2018-09-19 22:08:17', '', '', 41370251991977984, 1, 2.12, '', '/xboot/quartzJob/edit*', '编辑定时任务', '', 3, 'edit', 0, '', b'1');
INSERT INTO `t_permission` VALUES (45265762415284224, '', '2018-08-24 12:02:19', 0, '', '2018-09-19 22:08:24', '', '', 41370251991977984, 1, 2.13, '', '/xboot/quartzJob/pause*', '暂停定时任务', '', 3, 'disable', 0, '', b'1');
INSERT INTO `t_permission` VALUES (45265886315024384, '', '2018-08-24 12:02:49', 0, '', '2018-09-19 22:09:13', '', '', 41370251991977984, 1, 2.14, '', '/xboot/quartzJob/resume*', '恢复定时任务', '', 3, 'enable', 0, '', b'1');
INSERT INTO `t_permission` VALUES (45266070000373760, '', '2018-08-24 12:03:33', 0, 'admin', '2020-04-28 14:31:48', '', '', 41370251991977984, 1, 2.15, '', '/xboot/quartzJob/delByIds*', '删除定时任务', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (56309618086776832, '', '2018-09-23 23:26:40', 0, 'admin', '2018-11-15 15:17:43', '', 'oss-manage', 5129710648430592, 0, 1.40, 'sys/oss-manage/ossManage', 'oss-manage', '文件对象存储[付费]', 'ios-folder', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (56898976661639168, '', '2018-09-25 14:28:34', 0, '', '2018-09-25 15:12:46', '', '', 5129710648430593, 1, 1.17, '', '/xboot/user/importData*', '导入用户', '', 3, 'input', 0, '', b'1');
INSERT INTO `t_permission` VALUES (56911328312299520, '', '2018-09-25 15:17:39', 0, 'admin', '2018-10-20 22:47:36', '', 'excel', 41373430515240960, 0, 3.60, 'xboot-vue-template/excel/excel', 'excel', 'Excel导入导出[付费]', 'md-exit', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (57009544286441472, NULL, '2018-09-25 21:47:55', 0, NULL, '2018-09-25 21:47:55', NULL, '', 43117268627886080, 1, 1.40, '', '/xboot/messageSend/save*', '添加已发送消息', '', 3, 'add', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (57009744761589760, NULL, '2018-09-25 21:48:43', 0, NULL, '2018-09-25 21:48:43', NULL, '', 43117268627886080, 1, 1.50, '', '/xboot/messageSend/update*', '编辑已发送消息', '', 3, 'edit', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (57009981228060672, NULL, '2018-09-25 21:49:39', 0, NULL, '2018-09-25 21:49:39', NULL, '', 43117268627886080, 1, 1.60, '', '/xboot/messageSend/delByIds/*', '删除已发送消息', '', 3, 'delete', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (57212882168844288, '', '2018-09-26 11:15:55', 0, '', '2018-10-08 11:10:05', '', '', 56309618086776832, 1, 1.41, '', '无', '上传文件', '', 3, 'upload', 0, '', b'1');
INSERT INTO `t_permission` VALUES (58480609315524608, '', '2018-09-29 23:13:24', 0, 'admin', '2018-11-14 13:24:26', '', 'setting', 5129710648430592, 0, 1.90, 'sys/setting-manage/settingManage', 'setting', '系统配置[付费]', 'ios-settings-outline', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (61394706252173312, NULL, '2018-10-08 00:12:59', 0, NULL, '2018-10-08 00:12:59', NULL, '', 58480609315524608, 1, 1.81, '', '/xboot/setting/seeSecret/**', '查看私密配置', '', 3, 'view', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (61417744146370560, '', '2018-10-08 01:44:32', 0, '', '2018-10-08 01:50:03', '', '', 58480609315524608, 1, 1.82, '', '/xboot/setting/*/set*', '编辑配置', '', 3, 'edit', 0, '', b'1');
INSERT INTO `t_permission` VALUES (61560041605435392, NULL, '2018-10-08 11:09:58', 0, NULL, '2018-10-08 11:09:58', NULL, '', 56309618086776832, 1, 1.42, '', '/xboot/file/rename*', '重命名文件', '', 3, 'edit', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (61560275261722624, NULL, '2018-10-08 11:10:54', 0, NULL, '2018-10-08 11:10:54', NULL, '', 56309618086776832, 1, 1.43, '', '/xboot/file/copy*', '复制文件', '', 3, 'edit', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (61560480518377472, '', '2018-10-08 11:11:43', 0, 'admin', '2020-04-28 14:28:22', '', '', 56309618086776832, 1, 1.44, '', '/xboot/file/delete*', '删除文件', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (63482475359244288, '', '2018-10-13 18:29:02', 0, 'admin', '2018-10-20 22:47:45', '', 'custom-tree', 41373430515240960, 0, 3.80, 'xboot-vue-template/custom-tree/customTree', 'custom-tree', '自定义树[付费]', 'md-git-network', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (63741744973352960, '', '2018-10-14 11:39:17', 0, 'admin', '2019-02-04 17:03:07', '', 'render', 41373430515240960, 0, 3.30, 'xboot-vue-template/render/render', 'render', 'Render函数示例[付费]', 'md-aperture', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (64290663792906240, '', '2018-10-16 00:00:29', 0, 'admin', '2018-10-20 22:47:49', '', 'tree&table', 41373430515240960, 0, 3.90, 'xboot-vue-template/tree&table/tree&table', 'tree&table', '树+表格[付费]', 'md-list', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (66790433014943744, 'admin', '2018-10-22 21:33:42', 0, 'admin', '2018-10-22 21:37:12', '', 'card-list', 41373430515240960, 0, 3.91, 'xboot-vue-template/card-list/cardList', 'card-list', '卡片列表[付费]', 'md-card', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (67027338952445952, 'admin', '2018-10-23 13:15:03', 0, 'admin', '2018-10-23 14:57:38', '', 'product-template', 125909152017944576, 0, 4.00, 'Main', '/product-template', '前台产品级组件', 'md-ribbon', 1, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (67027909637836800, 'admin', '2018-10-23 13:17:19', 0, 'admin', '2019-11-22 16:19:01', '', 'banner', 67027338952445952, 0, 4.10, 'xboot-product-template/banner/Banner', 'banner', '轮播组件[付费]', 'md-book', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (67042515441684480, 'admin', '2018-10-23 14:15:22', 0, 'admin', '2019-11-22 16:19:24', '', 'product', 67027338952445952, 0, 4.20, 'xboot-product-template/product/Product', 'product', '产品组件[付费]', 'md-pricetags', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (67082402312228864, 'admin', '2018-10-23 16:53:53', 0, 'admin', '2019-11-22 16:19:32', '', 'category', 67027338952445952, 0, 4.30, 'xboot-product-template/category/Category', 'category', '分类栏组件[付费]', 'md-apps', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (75002207560273920, 'admin', '2018-11-14 13:24:21', 0, 'admin', '2018-11-20 20:06:22', '', 'dict', 5129710648430592, 0, 1.80, 'sys/dict-manage/dictManage', 'dict', '数据字典管理', 'md-bookmarks', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (76215889006956544, 'admin', '2018-11-17 21:47:05', 0, 'admin', '2018-11-17 21:47:53', '', '', 75002207560273920, 1, 1.81, '', '/xboot/dict/add*', '添加字典', '', 3, 'add', 0, '', b'1');
INSERT INTO `t_permission` VALUES (76216071333351424, 'admin', '2018-11-17 21:47:48', 0, 'admin', '2018-11-17 21:47:48', NULL, '', 75002207560273920, 1, 1.82, '', '/xboot/dict/edit*', '编辑字典', '', 3, 'edit', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (76216264070008832, 'admin', '2018-11-17 21:48:34', 0, 'admin', '2020-04-28 14:29:30', '', '', 75002207560273920, 1, 1.83, '', '/xboot/dict/delByIds**', '删除字典', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (76216459709124608, 'admin', '2018-11-17 21:49:21', 0, 'admin', '2018-11-17 21:49:21', NULL, '', 75002207560273920, 1, 1.84, '', '/xboot/dictData/add*', '添加字典数据', '', 3, 'add', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (76216594207870976, 'admin', '2018-11-17 21:49:53', 0, 'admin', '2018-11-17 21:49:53', NULL, '', 75002207560273920, 1, 1.85, '', '/xboot/dictData/edit*', '编辑字典数据', '', 3, 'edit', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (76216702639017984, 'admin', '2018-11-17 21:50:18', 0, 'admin', '2018-11-17 21:50:18', NULL, '', 75002207560273920, 1, 1.86, '', '/xboot/dictData/delByIds/**', '删除字典数据', '', 3, 'delete', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (76606430504816640, 'admin', '2018-11-18 23:38:57', 0, 'admin', '2018-11-18 23:39:12', '', 'activiti', 125909152017944576, 0, 1.10, 'Main', '/activiti', '工作流程', 'md-git-compare', 1, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (76607201262702592, 'admin', '2018-11-18 23:42:01', 0, 'admin', '2018-11-20 19:41:58', '', 'model-manage', 76606430504816640, 0, 1.00, 'activiti/model-manage/modelManage', 'model-manage', '模型管理[付费]', 'md-cube', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (76914082455752704, 'admin', '2018-11-19 20:01:28', 0, 'admin', '2018-11-20 19:41:50', '', 'process-manage', 76606430504816640, 0, 0.90, 'activiti/process-manage/processManage', 'process-manage', '流程管理[付费]', 'md-calendar', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (80539147005071360, 'admin', '2018-11-29 20:06:10', 0, 'admin', '2018-12-04 14:14:41', '', 'category-manage', 76606430504816640, 0, 2.00, 'activiti/category-manage/categoryManage', 'category-manage', '流程分类管理[付费]', 'md-bookmark', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (81319435670917120, 'admin', '2018-12-01 23:46:45', 0, 'admin', '2018-12-02 15:37:03', '', 'process-ins-manage', 76606430504816640, 0, 0.70, 'activiti/process-ins-manage/processInsManage', 'process-ins-manage', '运行中的流程[付费]', 'md-fastforward', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (81558529864896512, 'admin', '2018-12-02 15:36:50', 0, 'admin', '2018-12-08 14:50:38', '', 'apply-manage', 76606430504816640, 0, 0.00, 'activiti/apply-manage/applyManage', 'apply-manage', '我的申请[付费]', 'md-clipboard', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (81716172680073216, 'admin', '2018-12-03 02:03:15', 0, 'admin', '2018-12-03 02:03:15', NULL, 'todo-manage', 76606430504816640, 0, 0.10, 'activiti/todo-manage/todoManage', 'todo-manage', '我的待办[付费]', 'ios-pricetag', 2, '', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (82269650301227008, 'admin', '2018-12-04 14:42:34', 0, 'admin', '2018-12-04 14:42:34', NULL, 'done-manage', 76606430504816640, 0, 0.20, 'activiti/done-manage/doneManage', 'done-manage', '我的已办[付费]', 'ios-pricetag-outline', 2, '', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (84082369492946944, 'admin', '2018-12-09 14:45:40', 0, 'admin', '2018-12-09 14:46:59', '', '', 81558529864896512, 1, 0.00, '', '/xboot/leave/add*', '添加请假申请', '', 3, 'add', 0, '', b'1');
INSERT INTO `t_permission` VALUES (84082920431554560, 'admin', '2018-12-09 14:47:51', 0, 'admin', '2020-04-28 14:29:39', '', '', 81558529864896512, 1, 1.00, '', '/xboot/actBusiness/delByIds**', '删除草稿申请', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (84083562503999488, 'admin', '2018-12-09 14:50:25', 0, 'admin', '2018-12-09 14:50:25', NULL, '', 81716172680073216, 1, 0.00, '', '/xboot/actTask/pass**', '审批通过', '', 3, 'enable', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (84083641302388736, 'admin', '2018-12-09 14:50:43', 0, 'admin', '2018-12-09 14:50:43', NULL, '', 81716172680073216, 1, 1.00, '', '/xboot/actTask/back**', '审批驳回', '', 3, 'disable', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (84084404707659776, 'admin', '2018-12-09 14:53:45', 0, 'admin', '2018-12-09 14:53:45', NULL, '', 81716172680073216, 1, 3.00, '', '/xboot/actTask/delegate**', '委托代办', '', 3, 'other', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (84084724590448640, 'admin', '2018-12-09 14:55:02', 0, 'admin', '2020-04-28 14:31:19', '', '', 82269650301227008, 1, 0.00, '', '/xboot/actTask/deleteHistoric**', '删除已办任务', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (84084965817454592, 'admin', '2018-12-09 14:55:59', 0, 'admin', '2018-12-09 14:55:59', NULL, '', 81319435670917120, 1, 0.00, '', '/xboot/actProcess/delInsByIds/**', '删除运行流程', '', 3, 'delete', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (84085542324539392, 'admin', '2018-12-09 14:58:17', 0, 'admin', '2020-05-02 09:45:54', '', '', 76914082455752704, 1, 0.00, '', '/xboot/actProcess/updateInfo**', '编辑流程信息', '', 3, 'edit', 0, '', b'1');
INSERT INTO `t_permission` VALUES (84085810797744128, 'admin', '2018-12-09 14:59:21', 0, 'admin', '2018-12-09 14:59:21', NULL, '', 76914082455752704, 1, 1.00, '', '/xboot/actProcess/editNodeUser**', '流程节点设置', '', 3, 'edit', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (84085980943880192, 'admin', '2018-12-09 15:00:01', 0, 'admin', '2018-12-09 15:00:01', NULL, '', 76914082455752704, 1, 2.00, '', '/xboot/actProcess/convertToModel/**', '流程转模型', '', 3, 'other', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (84086163001839616, 'admin', '2018-12-09 15:00:45', 0, 'admin', '2020-04-28 14:31:27', '', '', 76914082455752704, 1, 3.00, '', '/xboot/actProcess/delByIds**', '删除流程定义', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (84086362248056832, 'admin', '2018-12-09 15:01:32', 0, 'admin', '2018-12-09 15:01:32', NULL, '', 76914082455752704, 1, 4.00, '', '/xboot/actModel/deployByFile**', '文件部署流程', '', 3, 'other', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (84086882907983872, 'admin', '2018-12-09 15:03:36', 0, 'admin', '2018-12-09 15:03:36', NULL, '', 76607201262702592, 1, 0.00, '', '/xboot/actModel/add**', '添加模型', '', 3, 'add', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (84087009940869120, 'admin', '2018-12-09 15:04:06', 0, 'admin', '2020-04-28 14:31:34', '', '', 76607201262702592, 1, 1.00, '', '/xboot/actModel/delByIds**', '删除模型', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (84087154040377344, 'admin', '2018-12-09 15:04:41', 0, 'admin', '2018-12-09 15:04:41', NULL, '', 76607201262702592, 1, 2.00, '', '/xboot/actModel/deploy/**', '部署模型', '', 3, 'other', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (84087344352727040, 'admin', '2018-12-09 15:05:26', 0, 'admin', '2018-12-09 15:05:33', '', '', 80539147005071360, 1, 0.00, '', '/xboot/actCategory/add**', '添加流程分类', '', 3, 'add', 0, '', b'1');
INSERT INTO `t_permission` VALUES (84087480852156416, 'admin', '2018-12-09 15:05:59', 0, 'admin', '2018-12-09 15:05:59', NULL, '', 80539147005071360, 1, 1.00, '', '/xboot/actCategory/edit**', '编辑流程分类', '', 3, 'edit', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (84087593041399808, 'admin', '2018-12-09 15:06:25', 0, 'admin', '2020-04-28 14:31:39', '', '', 80539147005071360, 1, 2.00, '', '/xboot/actCategory/delByIds**', '删除流程分类', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (102235632889237504, 'admin', '2019-01-28 17:00:15', 0, 'admin', '2019-11-22 11:47:14', '', 'vue-generator', 125909152017944576, 0, 3.00, 'Main', '/vue-generator', 'Vue代码生成', 'md-send', 1, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (102237605176807424, 'admin', '2019-01-28 17:08:06', 0, 'admin', '2019-02-01 20:41:31', '', 'table-generator', 102235632889237504, 0, 0.00, 'xboot-vue-generator/tableGenerator', 'table', '增删改表格生成[付费]', 'md-grid', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (102240052523831296, 'admin', '2019-01-28 17:17:49', 0, 'admin', '2019-02-01 20:41:38', '', 'tree-generator', 102235632889237504, 0, 1.00, 'xboot-vue-generator/treeGenerator', 'tree', '树形结构生成[付费]', 'md-git-branch', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (103658022701633536, 'admin', '2019-02-01 15:12:20', 0, 'admin', '2019-02-01 18:38:29', '', 'test', 102235632889237504, 0, 3.00, 'xboot-vue-generator/test', 'test', '代码生成测试页', 'ios-bug', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (113601631450304512, 'admin', '2019-03-01 01:44:41', 0, 'admin', '2020-04-28 14:31:02', '', '', 81716172680073216, 1, 2.00, '', '/xboot/actTask/backAll**', '审批驳回至发起人', '', 3, 'disable', 0, '', b'1');
INSERT INTO `t_permission` VALUES (113602149589454848, 'admin', '2019-03-01 01:46:44', 0, 'admin', '2019-06-26 20:38:55', '', 'components', 125909152017944576, 0, 5.00, 'Main', '/components', 'XBoot业务组件', 'md-cube', 1, '', 0, '', b'0');
INSERT INTO `t_permission` VALUES (113602342657462272, 'admin', '2019-03-01 01:47:30', 0, 'admin', '2019-03-01 02:03:26', '', 'components', 113602149589454848, 0, 0.00, 'xboot-components/xboot-components', 'components', 'XBoot业务组件', 'md-cube', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (113603512293658624, 'admin', '2019-03-01 01:52:09', 0, 'admin', '2019-06-26 20:38:47', '', 'library', 125909152017944576, 0, 6.00, 'Main', '/library', '第三方依赖工具/组件', 'ios-link', 1, '', 0, '', b'0');
INSERT INTO `t_permission` VALUES (113603617897844736, 'admin', '2019-03-01 01:52:34', 0, 'admin', '2019-03-01 02:03:15', '', 'library', 113603512293658624, 0, 0.00, 'xboot-library/xboot-library', 'library', '第三方依赖工具/组件', 'ios-link', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (117806106536841216, 'admin', '2019-03-12 16:11:46', 0, 'admin', '2019-03-12 16:11:46', NULL, 'process-finish-manage', 76606430504816640, 0, 0.80, 'activiti/process-finish-manage/processFinishManage', 'process-finish-manage', '结束的流程[付费]', 'md-power', 2, '', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (121426317022334976, 'admin', '2019-03-22 15:57:11', 0, 'admin', '2019-03-22 15:57:11', NULL, 'redis', 39915540965232640, 0, 2.21, 'sys/redis/redis', 'redis', 'Redis缓存管理', 'md-barcode', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (125909152017944576, 'admin', '2019-04-04 00:50:22', 0, 'admin', '2020-05-01 20:14:10', '', 'xboot', 0, -1, 0.00, '', '', 'XBoot管理系统', 'md-home', 0, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (127995258721013760, 'admin', '2019-04-09 18:59:49', 0, 'admin', '2020-03-25 21:38:53', '', 'doc', 0, -1, 2.00, '', '', 'XBoot开发文档', 'md-document', 0, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (127996320085446656, 'admin', '2019-04-09 19:04:02', 0, 'admin', '2020-03-25 21:38:58', '', 'app', 0, -1, 1.00, '', '', 'APP与小程序管理', 'md-phone-portrait', 0, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (127996726786134016, 'admin', '2019-04-09 19:05:39', 0, 'admin', '2019-04-09 19:05:39', NULL, 'xpay', 0, -1, 3.00, NULL, NULL, 'XPay支付系统', 'md-cash', 0, NULL, 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (127997001756315648, 'admin', '2019-04-09 19:06:44', 0, 'admin', '2019-10-23 20:23:33', '', 'xmall', 0, -1, 4.00, '', '', 'XMall商城', 'md-cart', 0, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (128364633437245440, 'admin', '2019-04-10 19:27:35', 0, 'admin', '2019-04-10 19:27:44', '', 'xboot-show', 0, -1, 2.10, '', '', 'XBoot宣传官网', 'md-paper-plane', 0, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (128366789653434368, 'admin', '2019-04-10 19:36:09', 0, 'admin', '2020-04-27 11:11:22', '', 'app', 127996320085446656, 0, 1.00, 'Main', '/app', 'XBoot Uniapp App', 'md-phone-portrait', 1, '', 0, '', b'0');
INSERT INTO `t_permission` VALUES (128368112335589376, 'admin', '2019-04-10 19:41:24', 0, 'admin', '2020-04-27 11:11:28', '', 'uniapp', 128366789653434368, 0, 0.00, 'uniapp/uniapp', 'uniapp', 'XBoot Uniapp App', 'md-phone-portrait', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (129033675403694080, 'admin', '2019-04-12 15:46:07', 0, 'admin', '2019-11-25 17:33:43', '', 'single-window', 41373430515240960, 0, 1.00, 'xboot-vue-template/single-window/singleWindow', 'single-window', '动态组件单页操作', 'md-easel', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (149452775095275520, 'admin', '2019-06-08 00:04:19', 0, 'admin', '2019-06-08 00:04:19', NULL, 'admin', 39915540965232640, 0, 2.29, 'sys/monitor/monitor', '/admin', 'Admin监控', 'md-speedometer', 2, '', 0, 'http://127.0.0.1:8888/xboot/admin', b'1');
INSERT INTO `t_permission` VALUES (156365156580855808, 'admin', '2019-06-27 01:51:39', 0, 'admin', '2019-06-27 01:51:39', NULL, '', 5129710648430593, 1, 1.18, '', '/xboot/user/resetPass', '重置密码', '', 3, 'other', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (160509731956330496, 'admin', '2019-07-08 12:20:43', 0, 'admin', '2019-07-08 12:22:13', '', 'weapp', 127996320085446656, 0, 0.00, 'Main', '/weapp', '微信小程序', 'ios-appstore', 1, '', 0, '', b'0');
INSERT INTO `t_permission` VALUES (160509918166650881, 'admin', '2019-07-08 12:21:28', 0, 'admin', '2020-04-27 11:10:54', '', 'weapp', 160509731956330496, 0, 0.00, 'weapp/weapp', 'weapp', '微信小程序', 'md-phone-portrait', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (175808753877127169, 'admin', '2019-08-19 17:33:34', 0, 'admin', '2019-08-19 17:34:41', '', 'sso', 125909152017944576, 0, 8.00, 'Main', '/sso', '单点登录测试站', 'md-log-in', 1, '', 0, '', b'0');
INSERT INTO `t_permission` VALUES (175808922710446081, 'admin', '2019-08-19 17:34:15', 0, 'admin', '2019-08-19 17:34:15', NULL, 'sso', 175808753877127169, 0, 0.00, 'sso/sso', 'sso', '单点登录测试站', 'md-log-in', 2, '', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (210154306362413056, 'admin', '2019-11-22 12:10:32', 0, 'admin', '2019-11-22 12:10:32', NULL, 'charts', 125909152017944576, 0, 3.10, 'Main', '/charts', '图表数据展示', 'md-analytics', 1, '', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (210155258859491329, 'admin', '2019-11-22 12:14:19', 0, 'admin', '2019-11-22 12:20:50', '', 'dashboard1', 210154306362413056, 0, 0.00, 'xboot-charts/dashboard1/dashboard1', 'dashboard1', 'Dashboard1[付费]', 'md-speedometer', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (210156371755143168, 'admin', '2019-11-22 12:18:45', 0, 'admin', '2019-11-22 12:18:45', NULL, 'dashboard2', 210154306362413056, 0, 1.00, 'xboot-charts/dashboard2/dashboard2', 'dashboard2', 'Dashboard2[付费]', 'ios-speedometer', 2, '', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (211251162815401984, 'admin', '2019-11-25 12:49:03', 0, 'admin', '2019-11-25 12:49:12', '', 'open', 125909152017944576, 0, 1.20, 'Main', '/open', '开放平台', 'ios-apps', 1, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (211251678651879424, 'admin', '2019-11-25 12:51:06', 0, 'admin', '2019-11-25 12:51:06', NULL, 'client', 211251162815401984, 0, 0.00, 'open/client/client', 'client', '接入网站管理', 'md-browsers', 2, '', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (211652331387752448, 'admin', '2019-11-26 15:23:09', 0, 'admin', '2019-11-26 15:23:09', NULL, 'doc', 211251162815401984, 0, 1.00, 'sys/monitor/monitor', 'doc', '开放平台文档', 'md-document', 2, '', 0, 'https://www.kancloud.cn/exrick/xboot/1399478', b'1');
INSERT INTO `t_permission` VALUES (1251152298521006080, 'admin', '2020-04-17 22:15:28', 0, 'admin', '2020-04-27 18:30:59', '', 'member', 127996320085446656, 0, 2.00, 'Main', '/member', '会员管理', 'md-contact', 1, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (1251153074307862528, 'admin', '2020-04-17 22:18:33', 0, 'admin', '2020-04-27 18:31:17', '', 'member', 1251152298521006080, 0, 0.00, 'app/member/member', 'member', '注册会员管理[付费]', 'md-contacts', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (1251760630449442816, 'admin', '2020-04-19 14:32:46', 0, 'admin', '2020-04-27 18:31:03', '', 'member-log', 127996320085446656, 0, 3.00, 'Main', '/member-log', '日志记录', 'md-list-box', 1, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (1251761480286736384, 'admin', '2020-04-19 14:36:08', 0, 'admin', '2020-04-27 18:31:22', '', 'member-log', 1251760630449442816, 0, 0.00, 'sys/log-manage/logManage', 'member-log', '日志记录[付费]', 'md-list-box', 2, '', 0, '', b'1');
INSERT INTO `t_permission` VALUES (1254739560500432897, 'admin', '2020-04-27 19:49:58', 0, 'admin', '2020-04-28 14:30:28', '', '', 81716172680073216, 1, 0.00, '', '/xboot/actTask/passAll**', '批量通过', '', 3, 'other', 0, '', b'1');
INSERT INTO `t_permission` VALUES (1254739666461134848, 'admin', '2020-04-27 19:50:23', 0, 'admin', '2020-04-27 19:50:23', NULL, '', 81716172680073216, 1, 0.00, '', '/xboot/actTask/back**', '批量驳回', '', 3, 'disable', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (1255022229452886016, 'admin', '2020-04-28 14:33:11', 0, 'admin', '2020-04-28 14:36:26', '', '', 1251153074307862528, 1, 0.00, '', '/xboot/app/member/admin/add**', '添加会员', '', 3, 'add', 0, '', b'1');
INSERT INTO `t_permission` VALUES (1255023250702995457, 'admin', '2020-04-28 14:37:14', 0, 'admin', '2020-04-28 14:37:14', NULL, '', 1251153074307862528, 1, 1.00, '', '/xboot/app/member/admin/edit**', '编辑会员', '', 3, 'edit', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (1255023865130782720, 'admin', '2020-04-28 14:39:41', 0, 'admin', '2020-05-06 14:57:54', '', '', 1251153074307862528, 1, 3.00, '', '/xboot/app/member/delByIds**', '删除会员', '', 3, 'delete', 0, '', b'1');
INSERT INTO `t_permission` VALUES (1255334110604431361, 'admin', '2020-04-29 11:12:29', 0, 'admin', '2020-04-29 11:12:29', NULL, '', 81558529864896512, 1, 0.00, '', '/xboot/actBusiness/apply*', '提交申请', '', 3, 'enable', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (1255334257396682752, 'admin', '2020-04-29 11:13:04', 0, 'admin', '2020-04-29 11:13:04', NULL, '', 81558529864896512, 1, 0.00, '', '/xboot/actBusiness/cancel*', '撤回申请', '', 3, 'disable', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (1255336077691064320, 'admin', '2020-04-29 11:20:18', 0, 'admin', '2020-04-29 11:20:47', '', '', 121426317022334976, 1, 0.00, '', '/xboot/redis/getAllByPage**', '获取Redis', '', 3, 'view', 0, '', b'1');
INSERT INTO `t_permission` VALUES (1255336361339260928, 'admin', '2020-04-29 11:21:26', 0, 'admin', '2020-04-29 11:21:26', NULL, '', 121426317022334976, 1, 0.00, '', '/xboot/redis/save', '添加Redis', '', 3, 'add', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (1255336455472025601, 'admin', '2020-04-29 11:21:48', 0, 'admin', '2020-04-29 11:21:48', NULL, '', 121426317022334976, 1, 0.00, '', '/xboot/redis/delByKeys**', '删除Redis', '', 3, 'delete', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (1255336553971060737, 'admin', '2020-04-29 11:22:12', 0, 'admin', '2020-04-29 11:22:12', NULL, '', 121426317022334976, 1, 0.00, '', '/xboot/redis/delAll', '清空Redis', '', 3, 'clear', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (1255337064858259457, 'admin', '2020-04-29 11:24:13', 0, 'admin', '2020-04-29 11:24:13', NULL, '', 211251678651879424, 1, 0.00, '', '/xboot/client/save**', '添加网站', '', 3, 'add', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (1255337148748533761, 'admin', '2020-04-29 11:24:33', 0, 'admin', '2020-04-29 11:24:33', NULL, '', 211251678651879424, 1, 0.00, '', '/xboot/client/delByIds**', '删除网站', '', 3, 'undefined', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (1255337241379737601, 'admin', '2020-04-29 11:24:56', 0, 'admin', '2020-04-29 11:24:56', NULL, '', 211251678651879424, 1, 0.00, '', '/xboot/client/update**', '编辑网站', '', 3, 'edit', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (1256398599336628224, 'admin', '2020-05-02 09:42:23', 0, 'admin', '2020-05-02 09:42:23', NULL, '', 117806106536841216, 1, 0.00, '', '/xboot/actProcess/delHistoricInsByIds**', '删除结束流程', '', 3, 'delete', 0, NULL, b'1');
INSERT INTO `t_permission` VALUES (1256399245200723969, 'admin', '2020-05-02 09:44:57', 0, 'admin', '2020-05-02 09:44:57', NULL, '', 76914082455752704, 1, 0.00, '', '/xboot/actProcess/editStartUser**', '编辑流程发起人', '', 3, 'edit', 0, NULL, b'1');
COMMIT;

-- ----------------------------
-- Table structure for t_quartz_job
-- ----------------------------
DROP TABLE IF EXISTS `t_quartz_job`;
CREATE TABLE `t_quartz_job` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `cron_expression` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `job_class_name` varchar(255) DEFAULT NULL,
  `parameter` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `default_role` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `data_type` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
BEGIN;
INSERT INTO `t_role` VALUES (496138616573952, '', '2018-04-22 23:03:49', 'admin', '2018-11-15 23:02:59', 'ROLE_ADMIN', 0, NULL, '超级管理员 拥有所有权限', 0);
INSERT INTO `t_role` VALUES (496138616573953, '', '2018-05-02 21:40:03', 'admin', '2020-05-31 06:19:28', 'ROLE_USER', 0, b'1', '普通注册用户 路过看看', 1);
INSERT INTO `t_role` VALUES (16457350655250432, '', '2018-06-06 00:08:00', 'admin', '2018-11-02 20:42:24', 'ROLE_TEST', 0, NULL, '测试权限按钮显示', 1);
COMMIT;

-- ----------------------------
-- Table structure for t_role_department
-- ----------------------------
DROP TABLE IF EXISTS `t_role_department`;
CREATE TABLE `t_role_department` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `department_id` bigint unsigned DEFAULT NULL,
  `role_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义数据权限表，查看对组织机构的控制';

-- ----------------------------
-- Records of t_role_department
-- ----------------------------
BEGIN;
INSERT INTO `t_role_department` VALUES (70763874256687105, 'admin', '2018-11-02 20:42:43', 0, 'admin', '2018-11-02 20:42:43', 40322777781112832, 16457350655250432);
INSERT INTO `t_role_department` VALUES (70763874265075712, 'admin', '2018-11-02 20:42:43', 0, 'admin', '2018-11-02 20:42:43', 40322811096469504, 16457350655250432);
INSERT INTO `t_role_department` VALUES (70763874277658624, 'admin', '2018-11-02 20:42:43', 0, 'admin', '2018-11-02 20:42:43', 40322852833988608, 16457350655250432);
INSERT INTO `t_role_department` VALUES (279403399315722242, 'admin', '2020-05-31 06:21:42', 0, 'admin', '2020-05-31 06:21:42', 279091722783297536, 496138616573953);
INSERT INTO `t_role_department` VALUES (279403399340888064, 'admin', '2020-05-31 06:21:43', 0, 'admin', '2020-05-31 06:21:43', 279092167815729152, 496138616573953);
INSERT INTO `t_role_department` VALUES (279403399504465920, 'admin', '2020-05-31 06:21:43', 0, 'admin', '2020-05-31 06:21:43', 279092472854876160, 496138616573953);
COMMIT;

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `permission_id` bigint unsigned DEFAULT NULL,
  `role_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `t_role_permission` VALUES (175877432572645376, NULL, '2019-08-19 22:06:29', 0, NULL, '2019-08-19 22:06:29', 125909152017944576, 16457350655250432);
INSERT INTO `t_role_permission` VALUES (175877432576839680, NULL, '2019-08-19 22:06:29', 0, NULL, '2019-08-19 22:06:29', 5129710648430592, 16457350655250432);
INSERT INTO `t_role_permission` VALUES (175877432581033985, NULL, '2019-08-19 22:06:29', 0, NULL, '2019-08-19 22:06:29', 5129710648430593, 16457350655250432);
INSERT INTO `t_role_permission` VALUES (175877432585228289, NULL, '2019-08-19 22:06:29', 0, NULL, '2019-08-19 22:06:29', 40238597734928384, 16457350655250432);
INSERT INTO `t_role_permission` VALUES (175877432589422593, NULL, '2019-08-19 22:06:29', 0, NULL, '2019-08-19 22:06:29', 57009744761589760, 16457350655250432);
INSERT INTO `t_role_permission` VALUES (175877432593616897, NULL, '2019-08-19 22:06:29', 0, NULL, '2019-08-19 22:06:29', 16392452747300864, 16457350655250432);
INSERT INTO `t_role_permission` VALUES (175877432602005504, NULL, '2019-08-19 22:06:29', 0, NULL, '2019-08-19 22:06:29', 16392767785668608, 16457350655250432);
INSERT INTO `t_role_permission` VALUES (175877432606199808, NULL, '2019-08-19 22:06:29', 0, NULL, '2019-08-19 22:06:29', 16439068543946752, 16457350655250432);
INSERT INTO `t_role_permission` VALUES (175877432610394113, NULL, '2019-08-19 22:06:29', 0, NULL, '2019-08-19 22:06:29', 175808753877127169, 16457350655250432);
INSERT INTO `t_role_permission` VALUES (175877432614588417, NULL, '2019-08-19 22:06:29', 0, NULL, '2019-08-19 22:06:29', 175808922710446081, 16457350655250432);
INSERT INTO `t_role_permission` VALUES (278793828486352896, 'admin', '2020-05-29 13:59:29', 0, 'admin', '2020-05-29 13:59:29', 125909152017944576, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828574433280, 'admin', '2020-05-29 13:59:29', 0, 'admin', '2020-05-29 13:59:29', 5129710648430592, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828587016192, 'admin', '2020-05-29 13:59:29', 0, 'admin', '2020-05-29 13:59:29', 5129710648430593, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828603793408, 'admin', '2020-05-29 13:59:29', 0, 'admin', '2020-05-29 13:59:29', 15701400130424832, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828620570624, 'admin', '2020-05-29 13:59:29', 0, 'admin', '2020-05-29 13:59:29', 16678126574637056, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828645736448, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 15701915807518720, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828675096576, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 15708892205944832, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828687679488, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 16678447719911424, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828704456704, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 25014528525733888, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828725428224, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 56898976661639168, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828746399744, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 156365156580855808, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828758982656, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 40238597734928384, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828784148480, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 45235621697949696, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828800925696, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 45235787867885568, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828813508608, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 45235939278065664, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828830285824, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 5129710648430594, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828851257344, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 16687383932047360, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828868034560, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 16689632049631232, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828884811776, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 16689745006432256, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828897394688, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 16689883993083904, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828914171904, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 16690313745666048, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828935143424, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 5129710648430595, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828947726336, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 16694861252005888, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828960309249, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 16695107491205120, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828977086464, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 16695243126607872, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793828993863680, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 75002207560273920, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793829010640896, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 76215889006956544, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793829027418112, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 76216071333351424, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793829040001024, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 76216264070008832, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793829060972544, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 76216459709124608, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793829077749760, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 76216594207870976, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793829098721280, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 76216702639017984, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793829115498496, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 127996320085446656, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793829140664320, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 160509731956330496, 496138616573953);
INSERT INTO `t_role_permission` VALUES (278793829161635840, 'admin', '2020-05-29 13:59:30', 0, 'admin', '2020-05-29 13:59:30', 160509918166650881, 496138616573953);
INSERT INTO `t_role_permission` VALUES (1256400030353461248, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 125909152017944576, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461249, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 5129710648430592, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461250, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 5129710648430593, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461251, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 15701400130424832, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461252, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 16678126574637056, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461253, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 15701915807518720, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461254, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 15708892205944832, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461255, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 16678447719911424, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461256, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 25014528525733888, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461257, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 56898976661639168, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461258, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 156365156580855808, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461259, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 40238597734928384, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461260, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 45235621697949696, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461261, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 45235787867885568, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461262, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 45235939278065664, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461263, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 43117268627886080, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461264, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 45236734832676864, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461265, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 45237010692050944, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461266, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 45237170029465600, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461267, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 57009544286441472, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461268, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 57009744761589760, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461269, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 57009981228060672, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461270, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 56309618086776832, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461271, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 57212882168844288, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461272, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 61560041605435392, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461273, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 61560275261722624, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461274, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 61560480518377472, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461275, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 44986029924421632, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461276, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 45235228800716800, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461277, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 45069342940860416, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461278, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 5129710648430594, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461279, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 16687383932047360, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461280, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 16689632049631232, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461281, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 16689745006432256, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461282, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 16689883993083904, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461283, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 16690313745666048, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461284, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 5129710648430595, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461285, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 16694861252005888, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461286, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 16695107491205120, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461287, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 16695243126607872, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461288, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 75002207560273920, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461289, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 76215889006956544, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461290, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 76216071333351424, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461291, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 76216264070008832, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461292, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 76216459709124608, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461293, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 76216594207870976, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461294, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 76216702639017984, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461295, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 58480609315524608, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461296, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 61394706252173312, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461297, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 61417744146370560, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461298, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 76606430504816640, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461299, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 81558529864896512, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461300, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84082369492946944, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461301, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 1255334257396682752, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461302, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 1255334110604431361, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461303, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84082920431554560, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461304, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 81716172680073216, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461305, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 1254739666461134848, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461306, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84083562503999488, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461307, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 1254739560500432897, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461308, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84083641302388736, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461309, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 113601631450304512, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461310, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84084404707659776, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461311, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 82269650301227008, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461312, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84084724590448640, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461313, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 81319435670917120, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461314, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84084965817454592, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461315, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 117806106536841216, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461316, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 1256398599336628224, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461317, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 76914082455752704, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461318, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84085542324539392, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461319, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 1256399245200723969, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461320, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84085810797744128, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461321, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84085980943880192, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461322, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84086163001839616, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461323, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84086362248056832, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461324, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 76607201262702592, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461325, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84086882907983872, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461326, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84087009940869120, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461327, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84087154040377344, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461328, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 80539147005071360, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461329, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84087344352727040, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461330, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84087480852156416, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461331, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 84087593041399808, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461332, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 211251162815401984, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461333, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 211251678651879424, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461334, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 1255337064858259457, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461335, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 1255337148748533761, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461336, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 1255337241379737601, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461337, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 211652331387752448, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461338, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 39915540965232640, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461339, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 41370251991977984, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461340, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 45264987354042368, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461341, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 45265487029866496, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461342, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 45265762415284224, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461343, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 45265886315024384, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461344, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 45266070000373760, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461345, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 41363147411427328, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461346, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 41363537456533504, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461347, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 41364927394353152, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461348, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 121426317022334976, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461349, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 1255336361339260928, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461350, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 1255336077691064320, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461351, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 1255336553971060737, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461352, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 1255336455472025601, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461353, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 149452775095275520, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461354, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 41371711400054784, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461355, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 41469219249852416, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461356, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 39916171171991552, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461357, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 39918482854252544, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461358, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 102235632889237504, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461359, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 102237605176807424, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461360, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 102240052523831296, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461361, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 103658022701633536, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461362, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 41373430515240960, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461363, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 129033675403694080, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461364, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 41375330996326400, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461365, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 42082442672082944, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461366, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 41378916912336896, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461367, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 63741744973352960, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461368, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 41376192166629376, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461369, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 41377034236071936, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461370, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 56911328312299520, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461371, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 63482475359244288, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461372, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 64290663792906240, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461373, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 66790433014943744, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461374, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 210154306362413056, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461375, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 210155258859491329, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461376, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 210156371755143168, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461377, 'admin', '2020-05-02 09:48:04', 0, 'admin', '2020-05-02 09:48:04', 67027338952445952, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461378, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 67027909637836800, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461379, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 67042515441684480, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461380, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 67082402312228864, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461381, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 113602149589454848, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461382, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 113602342657462272, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461383, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 113603512293658624, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461384, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 113603617897844736, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461385, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 16392452747300864, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461386, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 16392767785668608, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461387, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 16438800255291392, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461388, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 16438962738434048, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461389, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 16439068543946752, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461390, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 175808753877127169, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461391, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 175808922710446081, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461392, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 127996320085446656, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461393, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 160509731956330496, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461394, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 160509918166650881, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461395, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 128366789653434368, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461396, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 128368112335589376, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461397, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 1251152298521006080, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461398, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 1251153074307862528, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461399, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 1255022229452886016, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461400, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 1255023250702995457, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461401, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 1255023865130782720, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461402, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 1251760630449442816, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461403, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 1251761480286736384, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461404, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 127995258721013760, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461405, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 128364633437245440, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461406, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 127996726786134016, 496138616573952);
INSERT INTO `t_role_permission` VALUES (1256400030353461407, 'admin', '2020-05-02 09:48:05', 0, 'admin', '2020-05-02 09:48:05', 127997001756315648, 496138616573952);
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `department_id` bigint unsigned DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `pass_strength` varchar(2) DEFAULT NULL,
  `department_title` varchar(255) DEFAULT NULL,
  `birth` datetime(6) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE,
  UNIQUE KEY `mobile` (`mobile`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES (682265633886208, '', '2018-05-01 03:13:51', 'admin', '2020-04-30 04:56:32', '[\"510000\",\"510100\",\"510104\"]', 'https://i.loli.net/2019/04/28/5cc5a71a6e3b6.png', '我是大帅逼', 'admin@exrick.cn', '18782059031', '管理员', '$2a$10$PS04ecXfknNd3V8d.ymLTObQciapMU4xU8.GADBZZsuTZr7ymnagy', '男', 0, 1, 'admin', 0, 40322777781112832, '天府1街', '弱', '总部', '2020-04-15 11:00:00.000000', NULL);
INSERT INTO `t_user` VALUES (682265633886209, '', '2018-04-30 23:28:42', 'Exrick', '2020-05-29 13:11:45', '[\"110000\",\"110100\",\"110101\"]', 'https://i.loli.net/2019/04/28/5cc5a71a6e3b6.png', '', 'Exrick@exrick.cn', '18782059032', 'exrick', '$2a$10$PS04ecXfknNd3V8d.ymLTObQciapMU4xU8.GADBZZsuTZr7ymnagy', '女', 0, 0, 'Exrick', 0, 40343262766043136, '', '弱', '成都分部', '2020-04-13 00:00:00.000000', '');
INSERT INTO `t_user` VALUES (4363087427670016, '', '2018-05-03 15:09:42', 'admin', '2020-04-18 22:24:08', '[\"510000\",\"510100\",\"510114\"]', 'https://i.loli.net/2019/04/28/5cc5a71a6e3b6.png', '', 'test@exrick.cn', '18782059033', '游客1', '$2a$10$PS04ecXfknNd3V8d.ymLTObQciapMU4xU8.GADBZZsuTZr7ymnagy', '男', 0, 0, 'test', 0, 40343262766043136, '', '弱', '成都分部', '2020-04-28 00:00:00.000000', NULL);
INSERT INTO `t_user` VALUES (278793145926291456, 'admin', '2020-05-29 13:56:54', 'admin', '2020-05-29 14:20:01', '', 'https://i.loli.net/2019/04/28/5cc5a71a6e3b6.png', '', 'bfyxzls@sina.com', '13521972991', NULL, '$2a$10$NG9.RUkH35AbLCSFy8wcdueNmSEW4KcrBD.DBd.MfLbObv675uMay', '男', 0, 0, 'lind', 0, 40322777781112832, '', '弱', NULL, NULL, '');
INSERT INTO `t_user` VALUES (279093015459401728, 'admin', '2020-05-30 09:48:21', 'admin', '2020-05-30 09:50:13', '', 'https://i.loli.net/2019/04/28/5cc5a71a6e3b6.png', '', 'xinhua@sina.com', '13521972992', NULL, '$2a$10$04jZV89ggGQfNberyU23Rufg6uZyDtRqtv84AeprOSyI8M/W7etYe', '', 0, 0, 'xinhua', 0, 279092472854876160, '', '弱', NULL, NULL, '');
COMMIT;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint unsigned NOT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(1) DEFAULT NULL,
  `update_by` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `role_id` bigint unsigned DEFAULT NULL,
  `user_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
BEGIN;
INSERT INTO `t_user_role` VALUES (278801302899658752, 'admin', '2020-05-29 14:29:12', 0, 'admin', '2020-05-29 14:29:12', 496138616573953, 278793145926291456);
INSERT INTO `t_user_role` VALUES (279402316837163008, 'admin', '2020-05-31 06:17:24', 0, 'admin', '2020-05-31 06:17:24', 496138616573953, 279093015459401728);
INSERT INTO `t_user_role` VALUES (1251516869526032384, 'admin', '2020-04-18 22:24:08', 0, 'admin', '2020-04-18 22:24:08', 496138616573953, 4363087427670016);
INSERT INTO `t_user_role` VALUES (1254427593457930240, 'admin', '2020-04-26 23:10:19', 0, 'admin', '2020-04-26 23:10:19', 496138616573952, 682265633886208);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
