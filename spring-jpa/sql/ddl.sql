drop table if exists member CASCADE;
create table member
(
    id bigint generated by default as identity,
    name varchar(255),
    primary key (id)
);
insert into member (name) values ('박수혁'), ('김영미'), ('박소원'), ('박시훈');