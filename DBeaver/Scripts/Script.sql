-- RiverLuo

-- 房间表
CREATE table roomInfo
(
    roomno   int(3) primary key,
    roomtype varchar(10),
    bednum   int(1),
    price    int(5),
    booked   boolean default false
);

-- 用户表
create table customer
(
    id       int(4) primary key auto_increment,
    name     varchar(4) not null,
    idnum    varchar(18),
    phone    varchar(11),
    vip      boolean default false,
    money    int(9) default 0,
    password varchar(20),
    constraint uk_customer_idnum unique (idnum),
    constraint uk_customer_phone unique (phone)
);

-- 账单表
create table roomOperation
(
    business  int(5) primary key auto_increment,
    idnum     varchar(18),
    roomno    int(3),
    enterdate date,
    outdate   date,
    -- status varchar(2) check(status = '预定' or status = '入住' or status = '无人'),
    constraint fk_customer_roomInfo foreign key (roomno) references roomInfo (roomno),
    constraint fk_customer_roomOp foreign key (idnum) references customer (idnum)

);

-- 细节概览视图
create
or replace view detailAll
as
select ro.business,
       c.name,
       c.phone,
       c.idnum,
       ro.roomno,
       ro.outdate - ro.enterdate as orderday,
       case
           when enterdate > CURRENT_DATE() then "预定"
           when enterdate <= CURRENT_DATE() and outdate >= CURRENT_DATE() then "在住"
           when outdate < CURRENT_DATE() then "无人"
           else "无人"
           end                   as status
        ,
       ri.roomtype,
       ri.bednum,
       ri.price
from customer c
         join roomOperation ro
              on (c.idnum = ro.idnum)
         join roomInfo ri
              on (ri.roomno = ro.roomno)
where c.vip = true
order by ro.business;

-- 测试数据
-- insert into roomOperation values(null, '342223200201266631', 101, '2022-06-04', '2022-06-05');


insert into roomInfo
values (101, '普通房', 2, 120, false);
insert into roomInfo
values (102, '普通房', 2, 120, false);
insert into roomInfo
values (103, '普通房', 2, 120, false);
insert into roomInfo
values (104, '普通房', 2, 120, false);
insert into roomInfo
values (105, '普通房', 2, 120, false);
insert into roomInfo
values (106, '普通房', 2, 120, false);
insert into roomInfo
values (107, '普通房', 2, 120, false);
insert into roomInfo
values (108, '普通房', 2, 120, false);
insert into roomInfo
values (109, '普通房', 2, 120, false);
insert into roomInfo
values (110, '总统房', 2, 1120, false);
insert into roomInfo
values (111, '廉价房', 2, 50, false);

-- 管理员
insert into customer
values (1, 'ad', 'admin', 'admin', true, 1000000);


-- ---------------------------------------------------------------------------

select *
from customer
where phone = "19355359399"
  and password = "114514"






