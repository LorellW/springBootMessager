insert into users(id, username, password, active)
    values(1,'admin','admin',true);
insert into user_role(id_user, roles)
    values(1,'USER'), (1,'ADMIN');