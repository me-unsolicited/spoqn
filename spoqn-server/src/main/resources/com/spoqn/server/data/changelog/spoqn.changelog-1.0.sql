--liquibase formatted sql

--changeset bmannon:1
create table replace_me_with_something_real (
    id int primary key,
    name varchar(255)
);
--rollback drop table replace_me_with_something_real;
