create table user_info
(
    id         bigint generated always as identity
        constraint user_pkey
            primary key,
    ip_address varchar(30) not null
        constraint ip_address_unique
            unique
);

alter table user_info
    owner to postgres;

