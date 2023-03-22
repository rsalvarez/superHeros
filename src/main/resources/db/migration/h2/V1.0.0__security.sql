create table users (
id NUMBER auto_increment,
username VARCHAR(255),
password VARCHAR(1000),
enabled INT
);


create table user_roles (
user_id BIGINT,
role_id NUMBER
);

create table role (
id NUMBER auto_increment,
authority VARCHAR(255)
);



INSERT INTO users (id, username, password,enabled)
VALUES
    (1, 'test', '{bcrypt}$2a$10$2J1wGL/GVkSegdSth.8A1e0I0x3YUGsdqSLQz5vDUsZOXPfWSLGCG',1);

INSERT INTO user_roles (role_id, user_id)
    VALUES
        (1, 1);

INSERT INTO role(id, authority) values (1,'ADMIN');
