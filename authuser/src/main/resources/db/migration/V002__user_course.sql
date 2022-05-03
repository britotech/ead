CREATE TABLE IF NOT EXISTS public.tb_user_course
(
    id         uuid         NOT NULL,
    user_id    uuid         NOT NULL,
    course_id  uuid         NOT NULL,
    CONSTRAINT tb_user_course_pk PRIMARY KEY (id),
    CONSTRAINT tb_user_course_fk_user_id FOREIGN KEY (user_id) REFERENCES public.tb_user(id)
);

ALTER TABLE IF EXISTS public.tb_user_course OWNER to postgres;