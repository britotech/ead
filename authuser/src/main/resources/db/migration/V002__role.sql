CREATE TABLE IF NOT EXISTS public.tb_role
(
    id    uuid         NOT NULL,
    name  varchar(30)  NOT NULL,
    CONSTRAINT role_pk PRIMARY KEY (id),
    CONSTRAINT role_uk_name UNIQUE (name)
);

ALTER TABLE IF EXISTS public.tb_role OWNER to postgres;

insert into public.tb_role values ('24924ef3-15ca-44e7-b615-d199b8506f65', 'ROLE_ADMIN');
insert into public.tb_role values ('91d720a0-a7db-4736-87a7-e1cd4ddabc2f', 'ROLE_INSTRUCTOR');
insert into public.tb_role values ('d831507e-37db-48f3-aab7-1a53cca2053f', 'ROLE_STUDENT');
insert into public.tb_role values ('9c986ff6-32c8-4457-84da-ce81edcd3736', 'ROLE_USER');