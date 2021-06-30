create sequence seq_m201712015 increment by 1 start with 1;

create table m201712015 (
                            id number(11) not null primary key,
                            email varchar2(30) not null unique,
                            pw varchar2(20) not null,
                            name varchar2(30) not null,
                            phone varchar2(13),
                            address varchar2(50)
);

INSERT INTO m201712015 VALUES(seq_m201712015.nextval,'root@induk.ac.kr', 'cometrue','관리자', '01012345678','korea');
INSERT INTO m201712015 VALUES(seq_m201712015.nextval,'jhkim@induk.ac.kr', 'cometrue','김준형', '01012345678', 'korea');

select * from m201712015;

select pw from m201712015 where email = 'jhkim@induk.ac.kr';

update m201712015 set name='comso', phone='7777', address='nowon, seoul' where email='comso1@induk.ac.kr' and pw='cometrue';

delete from m201712015 where id = 2;

drop sequence seq_m201712015;

drop table m201712015;