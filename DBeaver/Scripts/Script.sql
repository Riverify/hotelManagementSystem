-- RiverLuo

CREATE table roomInfo
(
    roomno   int(3) primary key,
    roomtype varchar(10),
    bednum   int(1),
    price    int(5),
    booked   boolean default false
);


create table customer
(
    id    int(4) primary key auto_increment,
    name  varchar(4) not null,
    idnum varchar(18),
    phone int(11),
    vip   boolean default true,
    money int(9),
    constraint uk_customer_idnum unique (idnum),
    constraint uk_customer_phone unique (phone)
);


create table roomOperation
(
    business  int(5) primary key auto_increment,
    idnum     varchar(18),
    roomno    int(3),
    enterdate date,
    outdate   date,
    status    varchar(2) check (status = '预定' or status = '入住' or status = '无人'),
    constraint fk_customer_roomInfo foreign key (roomno) references roomInfo (roomno),
    constraint fk_customer_roomOp foreign key (idnum) references customer (idnum)

);

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

insert into customer
values (1, 'admin', 111111111111111111,);






