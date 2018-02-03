CREATE TABLE `base_flight` (
  `flight_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增id',
  `update_flag` int(10) DEFAULT '0' COMMENT '更新标识字段',
  `flight_no` varchar(20) NOT NULL COMMENT '航班号',
  `flight_category` smallint(1) DEFAULT '6' COMMENT '航班属性(1：国内-国内;2：国内-国际;3：国内-地区;4：地区-国际;5：国际-国际;6：未知)',
  `flight_state` varchar(20) DEFAULT '计划' COMMENT '航班状态（计划，起飞，到达，延误，取消，备降，返航，提前取消）(Plan,Take off,Arrivals,Delay,Cancel,Alternate,Return,Advance cancel)',
  `is_stop` smallint(1) DEFAULT NULL COMMENT '是否 经停 （1：经停；2：不经停；默认2）',
  `is_share` smallint(1) DEFAULT NULL COMMENT '是否 共享 （1：共享；2：不共享；默认是2）',
  `flight_type` varchar(20) DEFAULT NULL COMMENT '机型',
  `plan_no` varchar(200) DEFAULT NULL COMMENT '机号',
  `share_flight_no` varchar(20) DEFAULT NULL COMMENT '共享航班号',
  `fill_flight_no` varchar(20) DEFAULT NULL COMMENT '补班 航班号 （取消 的航班才有此字段）',
  `is_near_or_far` smallint(1) DEFAULT NULL COMMENT '航班机位（1：远机位；2：近机位；3：未知）',
  `leg_flag` smallint(1) DEFAULT NULL COMMENT '航段标识（1：计划 航段；2：备降而产生的航段；3：返航而产生的航段）',
  `distance` varchar(10) DEFAULT NULL COMMENT '飞行距离',
  `ontime_rate` int(10) DEFAULT NULL COMMENT '准点率',
  `board_gate` varchar(20) DEFAULT NULL COMMENT '登机口',
  `board_time` datetime DEFAULT NULL COMMENT '登机时间',
  `board_state` smallint(1) DEFAULT '6' COMMENT '乘机状态（1：开始值机，2：值机结束，3：开始登机，4：催促登机，5：登机结束，6：未知）',
  `transfer_airport` varchar(20) DEFAULT NULL COMMENT '中转机场',
  `transfer_airport_code_en_name` varchar(10) DEFAULT NULL COMMENT '中转机场英文名',
  `transfer_airport_code` varchar(10) DEFAULT NULL COMMENT '中转机场三字码',
  `alternate_info` varchar(200) DEFAULT NULL COMMENT '备降信息节点',
  `gate_position` varchar(10) DEFAULT NULL COMMENT '停机位',
  `is_custom` smallint(1) DEFAULT '1' COMMENT '是否定制（1：未定制；2：定制）',
  `dep_airport_code` varchar(20) DEFAULT NULL COMMENT '出发机场三字码',
  `dep_airport` varchar(20) DEFAULT NULL COMMENT '出发机场',
  `dep_airport_name` varchar(20) DEFAULT NULL COMMENT '出发机场名',
  `dep_date` date NOT NULL COMMENT '出发日期',
  `dep_scheduled_date` datetime DEFAULT NULL COMMENT '计划起飞时间（yyyy-MM-dd HH-mm-ss格式）',
  `dep_estimated_date` datetime DEFAULT NULL COMMENT '预计起飞时间（yyyy-MM-dd HH-mm-ss格式）',
  `dep_actual_date` datetime DEFAULT NULL COMMENT '实际起飞时间（yyyy-MM-dd HH-mm-ss格式）',
  `dep_time_zone` varchar(10) DEFAULT NULL COMMENT '出发地时区',
  `dep_city` varchar(20) DEFAULT NULL COMMENT '出发城市名',
  `dep_city_english_name` varchar(20) DEFAULT NULL COMMENT '出发城市英文',
  `dep_terminal` varchar(10) DEFAULT NULL COMMENT '出发机场候机楼',
  `dep_gate` varchar(10) DEFAULT NULL COMMENT '出发口',
  `check_in_counter` varchar(20) DEFAULT NULL COMMENT '值机柜台',
  `arr_airport_code` varchar(20) DEFAULT NULL COMMENT '到达机场三字码',
  `arr_airport` varchar(20) DEFAULT NULL COMMENT '到达机场',
  `arr_airport_name` varchar(20) DEFAULT NULL COMMENT '到达机场名',
  `arr_date` date DEFAULT NULL COMMENT '到达日期',
  `arr_scheduled_date` datetime DEFAULT NULL COMMENT '计划到达时间（yyyy-MM-dd HH-mm-ss格式）',
  `arr_estimated_date` datetime DEFAULT NULL COMMENT '预计到达时间（yyyy-MM-dd HH-mm-ss格式）',
  `arr_actual_date` datetime DEFAULT NULL COMMENT '实际到达时间（yyyy-MM-dd HH-mm-ss格式）',
  `arr_time_zone` varchar(10) DEFAULT NULL COMMENT '到达地时区',
  `arr_city` varchar(20) DEFAULT NULL COMMENT '到达城市',
  `arr_city_english_name` varchar(20) DEFAULT NULL COMMENT '到达城市英文',
  `arr_terminal` varchar(10) DEFAULT NULL COMMENT '到达地机场候机楼（接机楼）',
  `arr_gate` varchar(10) DEFAULT NULL COMMENT '到达口',
  `baggage_carousel` varchar(20) DEFAULT NULL COMMENT '行李盘',
  `airline_chinese_name` varchar(20) DEFAULT NULL COMMENT '航空公司中文名称',
  `airline_english_name` varchar(10) DEFAULT NULL COMMENT '航空公司英语名',
  `airline_code` varchar(10) DEFAULT NULL COMMENT '航空公司编号',
  `is_deleted` smallint(1) DEFAULT '0' COMMENT '是否删除：默认为0，0：不删除，1：删除',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`flight_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='基础航班信息表';

CREATE TABLE `base_customer` (
  `customer_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增id',
  `customer_name` varchar(200) NOT NULL COMMENT '客户名字',
  `custom_remark` varchar(255) DEFAULT NULL COMMENT '客户说明',
  `custom_url` varchar(255) DEFAULT NULL COMMENT '定制URL',
  `custom_type` smallint(1) DEFAULT '2' COMMENT '客户类型 1：内部客户 2：外部客户',
  `custom_total` int(20) NOT NULL DEFAULT '5000' COMMENT '客户剩余次数',
  `custom_code` varchar(200) NOT NULL COMMENT '客户标识',
  `app_id` INT(10) NOT NULL COMMENT '客户ID号',
  `expiry_date` date DEFAULT '2099-12-20' COMMENT '过期日期',
  `is_deleted` smallint(1) DEFAULT '0' COMMENT '是否删除：默认为0，0：不删除，1：删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户信息表';

CREATE TABLE `operator_data_center_api` (
  `flight_center_api_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增id',
  `api_name` varchar(200) NOT NULL COMMENT 'flightCenter接口名字',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '客户ID',
  `invoke_state` varchar(200) NOT NULL COMMENT '执行状态',
  `invoke_result` varchar(200) NOT NULL COMMENT '执行结果',
  `is_deleted` smallint(1) DEFAULT '0' COMMENT '是否删除：默认为0，0：不删除，1：删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`flight_center_api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据中心接口日志';

CREATE TABLE `operator_source_api` (
  `source_api_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增id',
  `source_name` varchar(255) NOT NULL COMMENT '源接口名字',
  `api_name` varchar(200) NOT NULL COMMENT 'source接口名字',
  `invoke_state` varchar(200) DEFAULT NULL COMMENT '执行状态',
  `invoke_result` varchar(200) DEFAULT NULL COMMENT '执行结果',
  `is_deleted` smallint(1) DEFAULT '0' COMMENT '是否删除：默认为0，0：不删除，1：删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`source_api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据来源接口日志';

CREATE TABLE `f_flight_push` (
  `flight_push_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增id',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '客户ID',
  `is_succeeded` smallint(1) DEFAULT '1' COMMENT '是否推送成功 默认为1 1:成功， 2:失败',
  `invoke_result` varchar(200) DEFAULT NULL COMMENT '执行结果',
  `is_deleted` smallint(1) unsigned DEFAULT '0' COMMENT '是否删除：默认为0，0：不删除，1：删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`flight_push_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='航班信息推送日志表';

CREATE TABLE `f_custom_flight` (
  `custom_flight_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增id',
  `customer_id` bigint(20) NOT NULL COMMENT '客户ID',
  `custom_url` varchar(255) DEFAULT NULL COMMENT '定制的URl',
  `flight_id` bigint(20) NOT NULL COMMENT '航班ID',
  `update_flag` int(10) unsigned DEFAULT '0' COMMENT '更新标识',
  `is_deleted` smallint(1) DEFAULT '0' COMMENT '是否删除：默认为0，0：不删除，1：删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='航班定制信息表';

CREATE TABLE `base_white_list` (
  `white_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增id',
  `ip` varchar(100) NOT NULL COMMENT 'IP',
  `is_deleted` smallint(1) DEFAULT '0' COMMENT '是否删除：默认为0，0：不删除，1：删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`white_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='白名单';

CREATE TABLE `base_black_list` (
  `black_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增id',
  `ip` varchar(100) NOT NULL COMMENT 'IP',
  `is_deleted` smallint(1) DEFAULT '0' COMMENT '是否删除：默认为0，0：不删除，1：删除',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
    PRIMARY KEY (`black_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='黑名单';