-- drop trigger task_id_trigger;
-- drop trigger user_id_trigger;
-- drop sequence task_id_sequence;
-- drop table tasks;
-- drop sequence USER_ID_SEQUENCE;
-- drop table USERS;

create table USERS
(
    user_id      number(*)    not null primary key,
    mail         varchar(255) not null, /* must be unique  - non unique for tests*/
    name         varchar(32)  not null,
    enc_password varchar(32)  not null
);
create sequence user_id_sequence
    start with 1
    increment by 1;
create table tasks
(
    task_id     NUMBER(4)    not null primary key,
    user_id     NUMBER(4)    not null,
    task_name   varchar2(32) not null,
    description varchar2(64),
    completed   NUMBER(1, 0),
    datetime    date         not null,
    constraint task_user_fk foreign key (user_id) references USERS (USER_ID)
);
create sequence task_id_sequence
    start with 1
    increment by 1;

create or replace trigger user_id_trigger
    before insert
    on USERS
    REFERENCING new as new
    FOR EACH ROW
begin
    select user_id_sequence.nextval
    into :new.user_id
    from dual;
end;

create or replace trigger task_id_trigger
    before insert
    on TASKS
    REFERENCING new as new
    FOR EACH ROW
begin
    select task_id_sequence.nextval
    into :new.task_id
    from dual;
end;

insert into USERS(name, enc_password, mail)
values ('admin', 'KWT8VWCBV5kdWtnYdznAMQ==', 'dezen53412gml@gmail.com');


insert into TASKS(USER_ID, task_name, description, COMPLETED, datetime)
values (1, 'wefwed', 'asdwqef', 0, TO_Date('2021-03-11 23:40:00', 'YYYY-MM-DD HH24:MI:SS'));
insert into TASKS(USER_ID, task_name, description, COMPLETED, datetime)
values (1, 'SuperTask', 'fweddf', 0, TO_Date('2022-03-11 23:50:00', 'YYYY-MM-DD HH24:MI:SS'));

commit;