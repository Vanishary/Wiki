# 分类
drop  table if exists `category`;
create table `category` (
    `id` bigint not null comment 'id',
    `parent` bigint not null default 0 comment '父id',
    `name` varchar(50) not null comment '名称',
    `sort` int comment '顺序',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='分类';

insert into `category` (id, parent, name, sort) VALUES (100, 000, '前端开发', 100);
insert into `category` (id, parent, name, sort) VALUES (101, 100, 'Vue', 101);
insert into `category` (id, parent, name, sort) VALUES (102, 100, 'Html & CSS', 102);
insert into `category` (id, parent, name, sort) VALUES (200, 000, 'Java', 200);
insert into `category` (id, parent, name, sort) VALUES (201, 200, '基础应用', 201);
insert into `category` (id, parent, name, sort) VALUES (202, 200, '框架应用', 202);

insert into `category` (id, parent, name, sort) VALUES (300, 000, 'Python', 300);
insert into `category` (id, parent, name, sort) VALUES (301, 300, '基础应用', 301);
insert into `category` (id, parent, name, sort) VALUES (302, 300, '进阶方向应用', 302);
insert into `category` (id, parent, name, sort) VALUES (400, 000, '数据库', 400);
insert into `category` (id, parent, name, sort) VALUES (401, 400, 'Mysql', 401);
insert into `category` (id, parent, name, sort) VALUES (402, 400, 'Oracle', 402);
insert into `category` (id, parent, name, sort) VALUES (500, 000, '其它', 500);
insert into `category` (id, parent, name, sort) VALUES (501, 500, '服务器', 501);
insert into `category` (id, parent, name, sort) VALUES (502, 500, '开发工具', 502);
insert into `category` (id, parent, name, sort) VALUES (503, 500, '热门服务器语言', 503);


