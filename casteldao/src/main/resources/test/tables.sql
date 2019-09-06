create table author
(
    id     int auto_increment,
    name   varchar2(40)      not null,
    born   date              null,
    active tinyint default 1 null
);



create table book
(
    id        int auto_increment,
    title     varchar2(40) not null,
    published date         null,
    idAuthor  int          not null,
    constraint book_pk
        primary key (id),
    constraint book_author_id_fk
        foreign key (idAuthor) references author (id)
);

INSERT into author
values (null, 'Name 1', '1899-01-05', null),
       (null, 'Name 2', '1129-01-05', null),
       (null, 'Name 3', '1359-01-05', null),
       (null, 'Name 4', '1129-01-05', null),
       (null, 'Name 5', '1999-01-05', 0),
       (null, 'Name 6', '1159-01-05', 0),
       (null, 'Name 7', '2019-01-05', null),
       (null, 'Name 8', '2009-01-05', null);

INSERT into book
values (null, 'Title 1 1', '1829-01-05', 1),
       (null, 'Title 1 2', '1839-01-05', 1),
       (null, 'Title 1 3', '1849-01-05', 1),
       (null, 'Title 2 1', '1859-01-05', 2),
       (null, 'Title 2 2', '1869-01-05', 2),
       (null, 'Title 2 3', '1879-01-05', 2),
       (null, 'Title 3 1', '1139-01-05', 3),
       (null, 'Title 3 2', '1239-01-05', 3),
       (null, 'Title 3 3', '1249-01-05', 3),
       (null, 'Title 4 1', '1459-01-05', 4),
       (null, 'Title 4 2', '1679-01-05', 4),
       (null, 'Title 4 3', '1769-01-05', 4),
       (null, 'Title 4 4', '2899-01-05', 4);

