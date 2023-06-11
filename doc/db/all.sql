drop table if exists test;
create table test (
    id bigint not null comment 'id',
    name varchar(50) comment '名称',
    password varchar(50) comment '密码',
    primary key (id)
) engine = innodb default charset =utf8mb4 comment ='测试';


drop table if exists `demo`;
create table `demo` (
                      `id` bigint not null comment 'id',
                      `name` varchar(50) comment '名称',
                      primary key (id)
) engine = innodb default charset =utf8mb4 comment ='测试';

-- 用户表
drop table if exists `user`;
create table `user` (
                        `id` bigint not null comment 'ID',
                        `login_name` varchar(50) not null comment '登录名',
                        `name` varchar(50) comment '昵称',
                        `password` char(32) not null comment '密码',
                        primary key (id),
                        unique key `login_name_unique` (`login_name`)
) engine = innodb default charset =utf8mb4 comment ='用户';

insert into `user` (id, `login_name`, `name`, `password`) values (1, 'test', '测试', 'test');