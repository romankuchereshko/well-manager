create table if not exists well (
    id_well bigint primary key not null,
    name varchar(30) not null
);

create table if not exists frame (
    id_frame serial primary key,
    id_well bigint not null,
    voltage double precision not null,
    electric_current double precision not null,
    speed double precision not null,
    frequency double precision not null,
    pressure double precision not null,
    temperature double precision not null,
    liquid_flow_rate double precision not null,
    creation_date_time timestamp default current_timestamp,
    updating_date_time timestamp default current_timestamp,
    constraint fk_well foreign key(id_well) references well(id_well)
);

insert into well (id_well, name) values (1, 'WEST WELL');
insert into well (id_well, name) values (2, 'EAST WELL');
insert into well (id_well, name) values (3, 'NORTH WELL');