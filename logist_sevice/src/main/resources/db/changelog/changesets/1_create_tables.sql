drop table if exists legal_entity_clients;
drop table if exists transaction;
drop table if exists legal_entity;
drop table if exists client;

create table legal_entity
(
    id varchar(36) primary key,
    name varchar not null,
    balance decimal not null,
    status varchar not null,
    owner varchar not null,
    version integer not null default 0
);

create table client
(
    id varchar(36) primary key,
    name varchar not null,
    username varchar(255)  not null,
    password varchar(255)  not null,
    status varchar not null
);

create table if not exists clients_roles
(
    client_id varchar(36)  not null,
    role    varchar(255) not null,
    primary key (client_id, role),
    constraint fk_clients_roles_clients foreign key (client_id) references client (id)
);

create table transaction
(
    id varchar(36) primary key,
    type varchar(10) not null,
    amount decimal not null,
    client_id varchar(36) not null,
    legal_entity_id varchar(36) not null,
    constraint transaction_client foreign key (client_id) references client(id),
    constraint transaction_legal_entity foreign key (legal_entity_id) references legal_entity(id)
);

create table legal_entity_clients
(
    legal_entity_id      varchar(36) not null,
    client_id varchar(36) not null,
    constraint legal_entity_clients_unique unique (legal_entity_id, client_id),
    constraint legal_entity_clients_legal_entity foreign key (legal_entity_id) references legal_entity (id),
    constraint legal_entity_clients_client foreign key (client_id) references client (id)
);
