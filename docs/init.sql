create table `flow_design`
(
    `id`            integer primary key auto_increment,
    `name`          varchar(60)  not null comment '名称',
    `symbol`        varchar(32)  not null comment '标识',
    `version`       integer      not null comment '版本',
    `description`   varchar(120) not null comment '描述',
    `simple_json`   text         not null comment '简单的流程json',
    `detailed_json` text         not null comment '完整的流程json'
) engine = InnoDB
  default charset = utf8 comment '流程';

create table `flow_node`
(
    `id`                 bigint primary key comment '节点id',
    `flow_design_id`     integer      not null comment '流程设计id',
    `flow_design_symbol` varchar(32)  not null comment '流程设计标识',
    `type`               varchar(32)  not null comment '节点类型',
    `name`               varchar(60)  not null comment '节点名称',
    `symbol`             varchar(32)  not null comment '节点标识',
    `description`        varchar(120) not null comment '节点描述'
) engine = InnoDB
  default charset = utf8 comment '流程节点';

create table `flow_common`
(
    `symbol` varchar(32) primary key not null comment '标识',
    `value`  varchar(32)             not null comment '值'
) engine = InnoDB
  default charset = utf8 comment '流程公共信息';

insert into `flow_common`
values ('flow_id', '1');
insert into `flow_common`
values ('task_id', '1');

create table `flow_running`
(
    `id`         bigint primary key comment '流程id',
    `name`       varchar(60) not null comment '流程名称',
    `symbol`     varchar(60) not null comment '流程标识',
    `start_time` datetime    not null comment '开始时间',
    `version`    integer     not null comment '版本'
) engine = InnoDB
  default charset = utf8 comment '流程运行';

create table `flow_running_task`
(
    `id`          bigint primary key comment '任务id',
    `flow_id`     bigint      not null comment '流程id',
    `node_symbol` varchar(32) not null comment '节点标识',
    `name`        varchar(32) not null comment '节点名称',
    `flow_symbol` varchar(32) not null comment '流程标志',
    `executor`    varchar(32) default null comment '执行者',
    `start_time`  datetime    not null comment '开始时间',
    `type`        varchar(10) not null comment '任务类型 USUAL/PARALLEL/SERIAL',
    `version`     integer     not null comment '版本'
) engine = InnoDB
  default charset = utf8 comment '流程任务';

create table `flow_history`
(
    `id`         bigint primary key comment '流程id',
    `name`       varchar(60) not null comment '流程名称',
    `symbol`     varchar(60) not null comment '流程标识',
    `start_time` datetime    not null comment '开始时间',
    `end_time`   datetime default null comment '结束时间',
    `state`      varchar(32) not null comment '状态 RUNNING/FINISHED/DESTROYED',
    `version`    integer     not null comment '版本'
) engine = InnoDB
  default charset = utf8 comment '流程历史';

create table `flow_history_task`
(
    `id`          bigint primary key comment '任务id',
    `flow_id`     bigint      not null comment '流程id',
    `node_symbol` varchar(32) not null comment '节点标识',
    `name`        varchar(32) not null comment '节点名称',
    `flow_symbol` varchar(32) not null comment '流程标志',
    `executor`    varchar(32) not null comment '执行者',
    `start_time`  datetime    not null comment '开始时间',
    `end_time`    datetime default null comment '结束时间',
    `type`        varchar(10) not null comment '任务类型 USUAL/PARALLEL/SERIAL',
    `version`     integer     not null comment '版本'
) engine = InnoDB
  default charset = utf8 comment '流程任务-历史记录';

create table `flow_running_variables`
(
    `id`          bigint primary key auto_increment comment 'id',
    `task_id`     bigint   default null comment '任务id',
    `flow_id`     bigint      not null comment '流程id',
    `create_time` datetime default now() comment '创建时间',
    `type`        varchar(128) not null comment '类型',
    `name`        varchar(128) not null comment '键',
    `val`         text        not null comment '值'
) engine = InnoDB
  default charset = utf8 comment '运行时任务变量';

create table `flow_history_variables`
(
    `id`          bigint primary key auto_increment comment 'id',
    `task_id`     bigint   default null comment '任务id',
    `flow_id`     bigint      not null comment '流程id',
    `create_time` datetime default now() comment '创建时间',
    `type`        varchar(128) not null comment '类型',
    `name`        varchar(128) not null comment '键',
    `val`         text        not null comment '值'
) engine = InnoDB
  default charset = utf8 comment '历史任务变量';
