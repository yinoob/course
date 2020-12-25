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