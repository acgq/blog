drop table if exists USER;
create table USER
(
    id                 int primary key auto_increment,
    name               varchar(20),
    encrypted_password varchar(100),
    avatar             varchar(200),
    create_at          datetime,
    update_at          timestamp
)default charset=utf8mb4;

