create table users
(
    id       serial8,
    name     varchar not null,
    surname  varchar not null,
    role     int2    not null,
    username varchar not null,
    password varchar not null,
    logo     varchar,
    primary key (id)
);

create table projects
(
    id            serial8,
    name          varchar   not null,
    short_name    varchar,
    code          varchar,
    creation_date timestamp not null,
    cover         varchar,
    primary key (id)
);

-- create table users_projects
-- (
--     id         serial8,
--     user_id    int8 not null,
--     project_id int8 not null,
--     primary key (id),
--     foreign key (user_id) references users (id),
--     foreign key (project_id) references projects (id)
-- );

create table sections
(
    id         serial8,
    name       varchar not null,
    short_name varchar,
    code       varchar,
    project_id int8    not null,
    primary key (id),
    foreign key (project_id) references projects (id)
);

-- create table users_sections
-- (
--     id         serial8,
--     user_id    int8 not null,
--     section_id int8 not null,
--     primary key (id),
--     foreign key (user_id) references users (id),
--     foreign key (section_id) references sections (id)
-- );

create table albums
(
    id         serial8,
    user_id    int8,
    section_id int8      not null,
    author     int8      not null,
    album_name varchar   not null,
    file_name  varchar   not null,
    type       varchar   not null,
    size       int8      not null,
    created_at timestamp not null,
    updated_at timestamp,
    primary key (id),
    foreign key (user_id) references users (id),
    foreign key (section_id) references sections (id)
);
