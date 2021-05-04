CREATE TABLE customer (
	cid varchar(20) primary key,
    cpw int not null,
    cname varchar(10) not null,
    cphone int not null,
    balance int default 0
);

CREATE TABLE store(
	snum int primary key,
	sname varchar(10),
	saddr varchar(100),
	sphone int
);

CREATE TABLE menu(
	mnum int primary key,
	menu varchar(20),
	snum int not null,
	mre int,
	mprice int
	foreign key (snum) references store(snum)
);

CREATE TABLE orderlist(
	cid varchar(10), 
    snum int,
    mnum int,
    mcount int,
    order_date datetime default now(),
    foreign key (mnum) references menu(mnum), 
    foreign key (cid) references customer(cid),
    foreign key (snum) references store(snum)
);

INSERT INTO store VALUES(1, "엽기떡볶이", "서울특별시 서대문구 창천동 연세로12길 28", 8592, "분식");
INSERT INTO store VALUES(2, "신전떡볶이", "서울특별시 강남구 대치4동 선릉로 420", 5256, "분식");
INSERT INTO store VALUES(3, "청년다방", "서울특별시 송파구 송파동 송파대로36길 7-38", 5757, "분식");
INSERT INTO store VALUES(4, "피자헛", "서울특별시 서초구 서초동 1689-5", 5588, "피자");
INSERT INTO store VALUES(5, "푸라닭", "서울특별시 송파구 잠실본동 올림픽로12길", 9206, "치킨");
INSERT INTO store VALUES(6, "굽네치킨", "서울특별시 강남구 대치동 990-3", 9492, "치킨");
INSERT INTO store VALUES(7, "홍콩반점", "서울특별시 강남구 신사동 578-5", 6994, "중식");
INSERT INTO store VALUES(8, "도미노피자", "서울특별시 용산구 이태원1동 보광로 112-3", 3082, "피자");
INSERT INTO store VALUES(9, "봉자마라탕", "서울특별시 광진구 자양동 동일로18길 22", 8889, "중식");

INSERT INTO menu VALUES (1, "엽기떡볶이", 1, 10, 14000);
INSERT INTO menu VALUES (2, "엽기로제떡볶이", 1, 10, 16000);
INSERT INTO menu VALUES (3, "치즈떡볶이", 2, 10, 4000);
INSERT INTO menu VALUES (4, "순대", 2, 10, 3500);

