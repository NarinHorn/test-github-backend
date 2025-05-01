create table users (
                       id serial primary key ,
                       email varchar(50) unique not null ,
                       password varchar(255) not null
);

create table roles (
                       id serial primary key ,
                       role_name varchar(50) unique not null
);

create table user_role (
                           user_id int not null references users(id) on delete cascade ,
                           role_id int not null references roles(id) on delete cascade ,
                           primary key (user_id, role_id)
);