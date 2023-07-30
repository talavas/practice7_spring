create table users
(
    id            serial    primary key,
    username      varchar(80)
        constraint users_pk
        unique,
    password      varchar(255),
    first_name    varchar(30),
    last_name     varchar(30),
    date_of_birth date,
    tax_id        varchar(10)
        unique,
    gender        varchar(10)
);

alter table users
    owner to postgres;

create index users_username_index
    on users (username);

create table roles
(
    id   serial
        primary key,
    name varchar(20)
);

alter table roles
    owner to postgres;

create table users_roles
(
    user_id integer
        constraint users_roles_users_id_fk
        references users,
    role_id integer
        constraint users_roles_roles_id_fk
        references roles
);

alter table users_roles
    owner to postgres;

create index users_roles_user_id_role_id_index
    on users_roles (user_id, role_id);

create table statuses
(
    id   serial
        primary key,
    name varchar(20)
);

alter table statuses
    owner to postgres;

create table permissions
(
    id   serial
        primary key,
    name varchar(20)
);

alter table permissions
    owner to postgres;

create table users_permissions
(
    user_id       integer
        constraint users_permissions_users_id_fk
        references users,
    permission_id integer
        constraint users_permissions_permissions_id_fk
        references permissions
);

alter table users_permissions
    owner to postgres;

create index users_permissions_user_id_permission_id_index
    on users_permissions (user_id, permission_id);

create table tasks
(
    id        serial
        primary key,
    name      varchar(255),
    user_id   bigint
        constraint tasks_users_id_fk
        references users,
    status_id bigint
        constraint tasks_statuses_id_fk
        references statuses,
    created   timestamp,
    updated   timestamp
);

alter table tasks
    owner to postgres;

