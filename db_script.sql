drop sequence task_id_sequence;
drop table tasks;
drop sequence USER_ID_SEQUENCE;
drop table USERS;
create table USERS
(
    user_id      number(*)   not null primary key,
    name         varchar(32) not null,
    enc_password varchar(32)
);
create sequence user_id_sequence
    start with 1
    increment by 1;
create table tasks
(
    task_id     NUMBER(4) not null primary key,
    user_id     NUMBER(4) not null,
    task_name   varchar2(32),
    description varchar2(64),
    completed   NUMBER(1, 0),
    datetime    date,
    constraint task_user_fk foreign key (user_id) references USERS (USER_ID)
);
create sequence task_id_sequence
    start with 1
    increment by 1;
