create sequence user_id_seq;

alter sequence user_id_seq owner to postgres;

alter sequence user_id_seq owned by user_info.id;

