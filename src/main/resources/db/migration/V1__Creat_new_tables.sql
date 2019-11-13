
create table user
(
    id                 int primary key auto_increment,
    username               varchar(20) unique ,
    encrypted_password varchar(100),
    avatar             varchar(200),
    create_at          datetime,
    update_at          timestamp
) default charset = utf8mb4;

create table blog
(
    id        bigint primary key auto_increment,
    user_id   int,
    title varchar(100),
    description varchar(200),
    content text,
    create_at datetime,
    update_at timestamp,
    foreign key (user_id) references user(id)
) default charset = utf8mb4;

