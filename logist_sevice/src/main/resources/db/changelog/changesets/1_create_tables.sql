drop table if exists legal_entity_clients;
drop table if exists transaction;
drop table if exists legal_entity;
drop table if exists client;

create table tasks
(
    id varchar(36) primary key,
    start_point varchar not null,
    end_point varchar not null,
    driver_first_name varchar not null,
    driver_last_name varchar not null,
    description varchar not null,
    transport_state_number varchar not null
);

create table races
(
    id varchar(36) primary key,
    created_at timestamp not null,
    started_at timestamp,
    ended_at timestamp,
    task_id varchar(36) not null,
    constraint fk_races_tasks foreign key (task_id) references tasks (id)
);

