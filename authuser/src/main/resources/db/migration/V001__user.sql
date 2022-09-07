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

INSERT INTO public.tb_user(id, creation_date_time, last_update_date_time, username, email, cpf, fullname, password, status, type)
VALUES ('cf1c2190-a2cc-43e3-ab13-f26d9a21c90f', '2022-09-07 10:00:00', '2022-09-07 10:00:00', 'admin', 'admin@brito.tech', '75033583000', 'Administrator', '$2a$10$goclcAykrE8Hz.TmSGsgeuLdDEhztR3Gdr39WtBSrn7I5URu/qlvi','ACTIVE', 'ADMIN');


INSERT INTO public.tb_user(id, creation_date_time, last_update_date_time, username, email, cpf, fullname, password, status, type)
VALUES ('b01832b6-592c-49fb-b99d-823a0a3a446e', '2022-09-07 10:00:00', '2022-09-07 10:00:00', 'anderson', 'anderson@brito.tech',
        '78413906024', 'Anderson Brito Garcia', '$2a$10$goclcAykrE8Hz.TmSGsgeuLdDEhztR3Gdr39WtBSrn7I5URu/qlvi','ACTIVE', 'INSTRUCTOR');
