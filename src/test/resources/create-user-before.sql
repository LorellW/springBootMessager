
insert into users(id, username, password, active) values
(1, 'testOne', '$2a$08$zJivJKVp/eZZAfES1JnQGePIl0idiGCIMYvMQtA/iFf4gXKXvJT.u',true),
(2, 'testTwo', '$2a$08$zJivJKVp/eZZAfES1JnQGePIl0idiGCIMYvMQtA/iFf4gXKXvJT.u', true);

insert into user_role(id_user, roles) values
(1, 'USER'), (1, 'ADMIN'),
(2, 'USER');