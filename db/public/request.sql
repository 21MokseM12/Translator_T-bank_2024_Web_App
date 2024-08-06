create table request
(
    id                bigint generated always as identity
        primary key,
    user_id           bigint not null
        constraint user_id_foreign_key
            references user_info,
    request_sentence  text   not null,
    response_sentence text   not null
);

alter table request
    owner to postgres;

