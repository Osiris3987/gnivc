create table companies
(
    id varchar(36) primary key,
    inn varchar not null,
    address varchar not null,
    kpp varchar not null,
    ogrn varchar not null,
    name varchar not null,
    owner varchar not null,
    constraint companies_unique_inn unique (inn)
);

create table users
(
    id varchar(36) primary key,
    name varchar not null,
    surname varchar not null,
    username varchar(255) not null,
    email varchar(255) not null
);

create table companies_users
(
    company_id varchar(36) not null,
    user_id varchar(36) not null,
    constraint unique_companies_users_unique unique (company_id, user_id),
    constraint fk_companies_users_company foreign key (company_id) references companies (id),
    constraint fk_companies_users_user foreign key (user_id) references users (id)
);
