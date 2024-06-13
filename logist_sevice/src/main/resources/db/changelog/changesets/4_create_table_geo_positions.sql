create table geo_positions
(
    point varchar not null,
    race_id varchar(36) not null,
    constraint fk_race_events_geo_position foreign key (race_id) references races_l (id)
)