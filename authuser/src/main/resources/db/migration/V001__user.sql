CREATE TABLE IF NOT EXISTS public.tb_user
(
    id                    uuid         NOT NULL,
    creation_date_time    timestamp without time zone NOT NULL,
    last_update_date_time timestamp without time zone NOT NULL,
    username              varchar(50)  NOT NULL,
    email                 varchar(80)  NOT NULL,
    cpf                   varchar(11)  NOT NULL,
    fullname              varchar(150) NOT NULL,
    password              varchar(255) NOT NULL,
    status                varchar(10)  NOT NULL,
    type                  varchar(10)  NOT NULL,
    phone_number          varchar(20),
    image_url             varchar(255),
    CONSTRAINT user_pk PRIMARY KEY (id),
    CONSTRAINT user_uk_username UNIQUE (username),
    CONSTRAINT user_uk_email    UNIQUE (email)
);

ALTER TABLE IF EXISTS public.tb_user OWNER to postgres;