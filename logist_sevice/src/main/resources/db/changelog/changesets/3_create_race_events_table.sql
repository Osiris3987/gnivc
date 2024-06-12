create table race_events(
    event_type varchar not null,
    created_at varchar not null,
    race_id varchar(36) not null,
    constraint fk_race_events_race foreign key (race_id) references races (id),
    constraint unique_race_events_race unique (race_id, event_type)
)