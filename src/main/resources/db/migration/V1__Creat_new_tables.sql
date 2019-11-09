drop table if exists USER;
create table USER
(
    ID                 int auto_increment,
    NAME               varchar(20),
    encrypted_password varchar(100),
    avatar             varchar(200),
    create_at          datetime,
    update_at          timestamp
)default charset=utf8mb4;

