DROP TABLE IF EXISTS public.tb_course_user;

CREATE TABLE IF NOT EXISTS public.tb_user_role
(
    user_id  uuid   NOT NULL,
    role_id  uuid   NOT NULL,
    CONSTRAINT user_role_pk         PRIMARY KEY (user_id, role_id),
    CONSTRAINT user_role_fk_user_id FOREIGN KEY (user_id) REFERENCES public.tb_user(id),
    CONSTRAINT user_role_fk_role_id FOREIGN KEY (role_id) REFERENCES public.tb_role(id)
);

ALTER TABLE IF EXISTS public.tb_user_role OWNER to postgres;

INSERT INTO public.tb_user_role(user_id, role_id) VALUES ('cf1c2190-a2cc-43e3-ab13-f26d9a21c90f', '24924ef3-15ca-44e7-b615-d199b8506f65');
INSERT INTO public.tb_user_role(user_id, role_id) VALUES ('b01832b6-592c-49fb-b99d-823a0a3a446e', '91d720a0-a7db-4736-87a7-e1cd4ddabc2f');