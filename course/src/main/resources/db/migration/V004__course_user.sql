CREATE TABLE IF NOT EXISTS public.tb_course_user
(
    id         uuid         NOT NULL,
    course_id  uuid         NOT NULL,
    user_id    uuid         NOT NULL,
    CONSTRAINT tb_course_user_pk PRIMARY KEY (id),
    CONSTRAINT tb_course_user_fk_course_id FOREIGN KEY (course_id) REFERENCES public.tb_course(id)
);

ALTER TABLE IF EXISTS public.tb_course_user OWNER to postgres;