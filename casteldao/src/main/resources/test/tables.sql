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