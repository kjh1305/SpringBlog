create sequence seq_b201712015 increment by 1 start with 1;

create table b201712015(
    id number(11) not null primary key,
    title varchar2(50) not null,
    content varchar2(200),
    filepath varchar(30),
    b201712015ger varchar2(30),
    reg_date_time date default SYSDATE
);

INSERT INTO b201712015(id, title, content, filepath, b201712015ger) VALUES (seq_b201712015.nextval,'제목-1','블로그 내용','ddochi.png','관리자');
