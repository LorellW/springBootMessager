create sequence hibernate_sequence start 1 increment 1;
create table message(
    id int4 not null,
    filename varchar(255),
    tag varchar(255),
    text varchar(2048) not null,
    id_user int4,
    primary key (id)
);
create table user_role (
    id_user int4 not null,
    roles varchar(255)
);
create table users(
    id int4 not null,
    activation_code varchar(255),
    active boolean not null,
    email varchar(255),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
);
alter table if exists message
    add constraint message_user_fk
    foreign key (id_user) references users;

alter table if exists user_role
    add constraint user_role_user_fk
    foreign key (id_user) references users;