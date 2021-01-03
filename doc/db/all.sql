drop table if exists chapter;
create table chapter (
   id char(8) not null comment 'ID',
   course_id char(8) comment '课程ID',
   name varchar(50) comment '名称',
   primary key (id)
) engine=innodb default charset=utf8mb4 comment='大章';

drop table if exists test;
create table test (
    id char(8) not null default '' comment 'id' ,
    name varchar(50) comment '名称' ,
    primary key (id)
) engine=InnoDB default charset=utf8mb4 comment '测试';

insert into test (id, name) values (1, '测试');
insert into test (id, name) values (2, '测试2');


insert into chapter (id,course_id,name) values (00001, 000001, '测试大章001');
insert into chapter (id,course_id,name) values (00002, 000002, '测试大章002');
insert into chapter (id,course_id,name) values (00003, 000003, '测试大章003');
insert into chapter (id,course_id,name) values (00004, 000004, '测试大章004');
insert into chapter (id,course_id,name) values (00005, 000005, '测试大章005');
insert into chapter (id,course_id,name) values (00006, 000006, '测试大章006');
insert into chapter (id,course_id,name) values (00007, 000007, '测试大章007');
insert into chapter (id,course_id,name) values (00008, 000008, '测试大章008');
insert into chapter (id,course_id,name) values (00009, 000009, '测试大章009');
insert into chapter (id,course_id,name) values (00010, 000010, '测试大章010');
insert into chapter (id,course_id,name) values (00011, 000011, '测试大章011');
insert into chapter (id,course_id,name) values (00012, 000012, '测试大章012');


drop table if exists section;
create table section (
    id char(8) not null default '' comment 'ID',
    title varchar(50) not null comment '标题',
    course_id char(8) comment '课程|course.id',
    chapter_id char(8)  comment '大章|chapter.id',
    video varchar(200) comment '视频',
    time int comment '时长|单位秒',
    charge char(1) comment '收费|C 收费; F 免费',
    sort int comment '顺序',
    created_at datetime comment '创建时间',
    updated_at datetime comment '修改时间',
    primary key (id)
)engine=Innodb default charset=utf8mb4 comment='小节';

insert into section (id, title, course_id, chapter_id, video, time, charge, sort,created_at, updated_at)
values ('000001', '测试小节', '123', '123', '', 500, 'F', 1, now(), now());

drop table if exists course;
create table course (
    id char(8) not null default '' comment 'id',
    name varchar(50) not null comment '名称',
    summary varchar(2000) comment '概述',
    time int default 0 comment '时长|单位秒',
    price decimal(8,2)  default 0.00 comment '价格(元)',
    image varchar(100) comment '封面',
    level char(1) comment '级别|ONE("1","初级"),TWO("2","中级"),THREE("3","高级")',
    charge char(1) comment '收费|CHARGE("C","收费"),FREE("F","免费")',
    status char(1) comment '状态|PUBLISH("P","发布"),DRAFT("D","草稿")',
    enroll integer default 0 comment '报名数',
    sort int comment '顺序',
    created_at datetime comment '创建时间',
    updated_at datetime comment  '修改时间',
    primary key (id)
)engine=innodb default charset=utf8mb4 comment='课程表';

insert into course(id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at)
values ('00001', '测试课程', '这是一门测试课程', 7200, 19.9, '', 0, 'C', 'D', 100, 0, now(), now());

update course c set time =(select sum(time) from section where course_id= 'rb6tR7nY')
where c.id='rb6tR7nY';

#分类表
drop table if exists category;
create table category(
    id char(8) not null default '' comment 'id',
    parent char(8) not null default '' comment '父id',
    name varchar(50) not null comment '名称',
    sort int comment '顺序',
    primary key (id)
)engine=innodb default charset=utf8mb4 comment='分类';
insert into category (id, parent, name, sort) values (000100,000000, '前端技术', 100);
insert into category (id, parent, name, sort) values (000101,000100, 'html\css', 101);
insert into category (id, parent, name, sort) values (000102,000100, 'javascript', 102);
insert into category (id, parent, name, sort) values (000103,000100, 'vue.js', 103);


drop table if exists course_content;
create table course_content(
    id char(8) not null default '' comment '课程id',
    content mediumtext not null comment '课程内容',
    primary key (id)
)engine=innodb default charset=utf8mb4 comment='课程内容';

drop table if exists teacher;
create table teacher(
    id char(8) not null default '' comment 'id',
    name varchar(50) not null comment '姓名',
    nickname varchar(50) comment '昵称',
    image varchar(100) comment '头像',
    position varchar(50) comment '职位',
    motto varchar(50) comment '座右铭',
    intro varchar(500) comment '简介',
    primary key (id)
)engine =innodb default charset =utf8mb4 comment='讲师';

alter table course add column (teacher_id char(8) comment '讲师|teacher.id');

drop table if exists file;
create table file(
    id char(8) not null default '' comment 'id',
    path varchar(100) not null comment '相对路径',
    name varchar(100) comment '文件名',
    suffix varchar(10) comment '后缀',
    size int comment '大小|字节B',
    used char(1) comment '用途|枚举[FileUseEnum]: COURSE("C","讲师"), TEACHER("T","课程")',
    created_at datetime comment '创建时间',
    updated_at datetime comment '修改时间',
    primary key (id),
    unique key path_unique (path)
)engine=innodb default charset=utf8mb4 comment='文件';

alter table file add column (shard_index int comment '已上传分片');
alter table file add column (shard_size int comment '分片大小|B');
alter table file add column (shard_total int comment '分片总数');
alter table file add column (key_id varchar(32) comment '文件标识');
alter table file add unique key key_unique (key_id);

alter table file modify column size bigint;

drop table if exists user;
create table user(
    id char(8) not null default '' comment 'id',
    login_name varchar(50) not null comment '登录名',
    name varchar(50) comment '昵称',
    password char(32) not null comment '密码',
    primary key (id),
    unique key login_name_unique (login_name)
)engine=innodb default charset =utf8mb4 comment='用户表';

insert into user (id,login_name,name,password)values
(1000000,'test','测试','song666');
insert into user(id,login_name,name,password)values
(000000,'wys1234','wys1234','wys1234');

#资源表
drop table if exists resource;
create table resource(
    id char(6) not null default '' comment 'id',
    name varchar(100) not null comment '名称|菜单或按钮',
    page varchar(50) null comment '页面|路由',
    request varchar(200) null comment '请求|接口',
    parent char(6) comment '父id',
    primary key (id)
)engine=innodb default charset=utf8 comment='资源';

insert into resource values ('01','系统管理',null,null,null);
insert into resource values ('0101','用户管理','/system/user',null,'01');
insert into resource values ('010101','保存',null,'["/system/admin/user/list","/system/admin/user/save"]','0101');
insert into resource values ('010102','删除',null,'["/system/admin/user/delete"]','0101');

insert into resource values ('010103','重置密码',null,'["/system/admin/user/save-password"]','0101');
insert into resource values ('0102','资源管理','/system/resource',null,'01');
insert into resource values ('010201','保存/显示',null,'["/system/admin/resource"]','0102');
insert into resource values ('0103','角色管理','/system/role',null,'01');
insert into resource values ('010301','角色/权限管理',null,'["/system/admin/role"]','0103');


#角色表
drop table if exists role;
create table role(
    id char(8) not null default '' comment 'id',
    name varchar(50) not null comment '角色',
    desc_is varchar(10) not null comment '描述',
    primary key (id)
) engine=innodb default charset=utf8mb4 comment='角色';

insert into role values (000000,'系统管理员','管理用户，角色权限');
insert into role values (000001,'开发','维护资源');
insert into role values (000002,'业务管理员','负责业务管理');

#角色资源关联表
drop table if exists role_resources;
create table role_resource(
    id char(8) not null default '' comment 'id',
    role_id char(8) not null comment '角色|id',
    resource_id char(6) not null comment '资源|id',
    primary key (id)
)engine =innodb default charset=utf8mb4 comment='角色资源关联';

insert into role_resource values (000000,000000,'01');
insert into role_resource values (000001,000000,'0102');
insert into role_resource values (000002,000000,'010101');
insert into role_resource values (000003,000000,'010102');
insert into role_resource values (000004,000000,'010103');


#角色用户表
drop table if exists role_user;
create table role_user(
    id char(8) not null default '' comment 'id',
    role_id char(8) not null comment '角色|id',
    user_id char(8) not null comment '用户|id',
    primary key (id)
)engine =innodb default  charset =utf8mb4 comment ='角色用户关联表';

insert into role_user values (000000,000000,000000);
insert into role_user values (000001,000000,'VVbrthEW');

drop table if exists member;
create table member(
    id char(8) not null default '' comment 'id',
    mobile varchar(11) comment '手机号',
    password char(32) not null comment '密码',
    name varchar(50) comment '昵称',
    photo varchar(200) comment '头像url',
    register_time datetime comment '注册时间',
    primary key (id),
    unique key mobile_unique (mobile)
)engine=innodb default charset =utf8mb4 comment= '会员表';

insert into member values ('000001',12345678901,123456,'松哥',null,now());

#短信验证码
drop table if exists sms;
create table sms(
    id char(8) not null default '' comment 'id',
    mobile varchar(50) not null comment '手机号',
    code char(6) not null comment '验证码',
    used char(1) not null comment '用途|枚举[SmsUseEnum]:REGISTER("R","注册"),FORGET("F","忘记密码")',
    at datetime not null comment '生成时间',
    status char(1) not null comment '状态|枚举[SmsStatus]:USED("U","已使用”),NOT_USE("N","未使用")',
                primary key (id)
)engine=innodb default charset=utf8mb4  comment='短信验证码';


insert into sms values ('000001',12345678901,'123456','R',now(),'N');
