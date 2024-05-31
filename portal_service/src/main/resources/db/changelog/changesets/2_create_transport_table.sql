create table transports
(
    id varchar(36) primary key,
    vin varchar not null,
    year int4 not null,
    company_id varchar(36),
    constraint fk_transports_companies foreign key (company_id) references companies(id),
    constraint unique_vin unique (vin)
)