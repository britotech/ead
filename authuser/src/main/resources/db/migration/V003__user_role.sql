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