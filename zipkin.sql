/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : zipkin

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2019-06-27 18:08:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `zipkin_annotations`
-- ----------------------------
DROP TABLE IF EXISTS `zipkin_annotations`;
CREATE TABLE `zipkin_annotations` (
  `trace_id` bigint(20) NOT NULL COMMENT 'coincides with zipkin_spans.trace_id',
  `span_id` bigint(20) NOT NULL COMMENT 'coincides with zipkin_spans.id',
  `a_key` varchar(255) NOT NULL COMMENT 'BinaryAnnotation.key or Annotation.value if type == -1',
  `a_value` blob COMMENT 'BinaryAnnotation.value(), which must be smaller than 64KB',
  `a_type` int(11) NOT NULL COMMENT 'BinaryAnnotation.type() or -1 if Annotation',
  `a_timestamp` bigint(20) DEFAULT NULL COMMENT 'Used to implement TTL; Annotation.timestamp or zipkin_spans.timestamp',
  `endpoint_ipv4` int(11) DEFAULT NULL COMMENT 'Null when Binary/Annotation.endpoint is null',
  `endpoint_ipv6` binary(16) DEFAULT NULL COMMENT 'Null when Binary/Annotation.endpoint is null, or no IPv6 address',
  `endpoint_port` smallint(6) DEFAULT NULL COMMENT 'Null when Binary/Annotation.endpoint is null',
  `endpoint_service_name` varchar(255) DEFAULT NULL COMMENT 'Null when Binary/Annotation.endpoint is null',
  KEY `trace_id` (`trace_id`,`span_id`) COMMENT 'for joining with zipkin_spans',
  KEY `trace_id_2` (`trace_id`) COMMENT 'for getTraces/ByIds',
  KEY `endpoint_service_name` (`endpoint_service_name`(191)) COMMENT 'for getTraces and getServiceNames',
  KEY `a_type` (`a_type`) COMMENT 'for getTraces',
  KEY `a_key` (`a_key`(191)) COMMENT 'for getTraces'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPRESSED;

-- ----------------------------
-- Records of zipkin_annotations
-- ----------------------------
INSERT INTO `zipkin_annotations` VALUES ('6549925606403170304', '6549925607342845952', 'sr', null, '-1', '1561623956523000', '-1062726432', null, '7001', 'springboot-dubbo-provider');
INSERT INTO `zipkin_annotations` VALUES ('6549925606403170304', '6549925607342845952', 'ss', null, '-1', '1561623956710116', '-1062726432', null, '7001', 'springboot-dubbo-provider');
INSERT INTO `zipkin_annotations` VALUES ('6549925606403170304', '6549925606889709568', 'cs', null, '-1', '1561623956415000', '-1062726432', null, '7003', 'springboot-dubbo-customer');
INSERT INTO `zipkin_annotations` VALUES ('6549925606403170304', '6549925606889709568', 'cr', null, '-1', '1561623956858641', '-1062726432', null, '7003', 'springboot-dubbo-customer');

-- ----------------------------
-- Table structure for `zipkin_dependencies`
-- ----------------------------
DROP TABLE IF EXISTS `zipkin_dependencies`;
CREATE TABLE `zipkin_dependencies` (
  `day` date NOT NULL,
  `parent` varchar(255) NOT NULL,
  `child` varchar(255) NOT NULL,
  `call_count` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPRESSED;

-- ----------------------------
-- Records of zipkin_dependencies
-- ----------------------------

-- ----------------------------
-- Table structure for `zipkin_spans`
-- ----------------------------
DROP TABLE IF EXISTS `zipkin_spans`;
CREATE TABLE `zipkin_spans` (
  `trace_id` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `debug` bit(1) DEFAULT NULL,
  `start_ts` bigint(20) DEFAULT NULL COMMENT 'Span.timestamp(): epoch micros used for endTs query and to implement TTL',
  `duration` bigint(20) DEFAULT NULL COMMENT 'Span.duration(): micros used for minDuration and maxDuration query',
  UNIQUE KEY `trace_id` (`trace_id`,`id`) COMMENT 'ignore insert on duplicate',
  UNIQUE KEY `trace_id_4` (`trace_id`,`id`) COMMENT 'ignore insert on duplicate',
  UNIQUE KEY `trace_id_7` (`trace_id`,`id`) COMMENT 'ignore insert on duplicate',
  UNIQUE KEY `trace_id_10` (`trace_id`,`id`) COMMENT 'ignore insert on duplicate',
  UNIQUE KEY `trace_id_13` (`trace_id`,`id`) COMMENT 'ignore insert on duplicate',
  KEY `trace_id_2` (`trace_id`,`id`) COMMENT 'for joining with zipkin_annotations',
  KEY `trace_id_3` (`trace_id`) COMMENT 'for getTracesByIds',
  KEY `name` (`name`(191)) COMMENT 'for getTraces and getSpanNames',
  KEY `start_ts` (`start_ts`) COMMENT 'for getTraces ordering and range',
  KEY `trace_id_5` (`trace_id`,`id`) COMMENT 'for joining with zipkin_annotations',
  KEY `trace_id_6` (`trace_id`) COMMENT 'for getTracesByIds',
  KEY `name_2` (`name`(191)) COMMENT 'for getTraces and getSpanNames',
  KEY `start_ts_2` (`start_ts`) COMMENT 'for getTraces ordering and range',
  KEY `trace_id_8` (`trace_id`,`id`) COMMENT 'for joining with zipkin_annotations',
  KEY `trace_id_9` (`trace_id`) COMMENT 'for getTracesByIds',
  KEY `name_3` (`name`(191)) COMMENT 'for getTraces and getSpanNames',
  KEY `start_ts_3` (`start_ts`) COMMENT 'for getTraces ordering and range',
  KEY `trace_id_11` (`trace_id`,`id`) COMMENT 'for joining with zipkin_annotations',
  KEY `trace_id_12` (`trace_id`) COMMENT 'for getTracesByIds',
  KEY `name_4` (`name`(191)) COMMENT 'for getTraces and getSpanNames',
  KEY `start_ts_4` (`start_ts`) COMMENT 'for getTraces ordering and range',
  KEY `trace_id_14` (`trace_id`,`id`) COMMENT 'for joining with zipkin_annotations',
  KEY `trace_id_15` (`trace_id`) COMMENT 'for getTracesByIds',
  KEY `name_5` (`name`(191)) COMMENT 'for getTraces and getSpanNames',
  KEY `start_ts_5` (`start_ts`) COMMENT 'for getTraces ordering and range'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPRESSED;

-- ----------------------------
-- Records of zipkin_spans
-- ----------------------------
INSERT INTO `zipkin_spans` VALUES ('6549925606403170304', '6549925606889709568', 'springboot-dubbo-customer', '6549925606403170304', null, '1561623956415000', '443641');
INSERT INTO `zipkin_spans` VALUES ('6549925606403170304', '6549925607342845952', 'springboot-dubbo-provider', '6549925606889709568', null, '1561623956523000', '187116');
