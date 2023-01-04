CREATE TABLE IF NOT EXISTS frame (
    id_frame uuid primary key not null,
    id_well uuid not null,
    voltage double precision not null,
    electric_current double precision not null,
    speed double precision not null,
    frequency double precision not null,
    pressure double precision not null,
    temperature double precision not null,
    liquid_flow_rate double precision not null,
    creation_date_time timestamp default current_timestamp
);