delete from message;

insert into message(id, text, tag, id_user) values
(1, 'first', 'test-tag', 1),
(2, 'second', 'more', 1),
(3, 'third', 'test-tag', 1),
(4, 'fourth', 'another', 2);

alter sequence hibernate_sequence restart with 10;