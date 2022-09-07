CREATE TABLE IF NOT EXISTS public.tb_user
(
    id        uuid         NOT NULL,
    username  varchar(50)  NOT NULL,
    email     varchar(80)  NOT NULL,
    cpf       varchar(11)  NOT NULL,
    fullname  varchar(150) NOT NULL,
    status    varchar(10)  NOT NULL,
    type      varchar(10)  NOT NULL,
    CONSTRAINT user_pk       PRIMARY KEY (id),
    CONSTRAINT user_uk_email UNIQUE (email)
    );

ALTER TABLE IF EXISTS public.tb_user OWNER to postgres;

INSERT INTO public.tb_user(id, username, email, cpf, fullname, status, type)
VALUES ('cf1c2190-a2cc-43e3-ab13-f26d9a21c90f', 'admin', 'admin@brito.tech', '75033583000', 'Administrator', 'ACTIVE', 'ADMIN');


INSERT INTO public.tb_user(id, username, email, cpf, fullname, status, type)
VALUES ('b01832b6-592c-49fb-b99d-823a0a3a446e', 'anderson', 'anderson@brito.tech','78413906024', 'Anderson Brito Garcia', 'ACTIVE', 'INSTRUCTOR');