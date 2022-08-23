DROP TABLE IF EXISTS public.tb_course_user;

CREATE TABLE IF NOT EXISTS public.tb_course_user
(
    course_id  uuid  NOT NULL,
    user_id    uuid  NOT NULL,
    CONSTRAINT course_user_pk           PRIMARY KEY (course_id, user_id),
    CONSTRAINT course_user_fk_course_id FOREIGN KEY (course_id) REFERENCES public.tb_course(id),
    CONSTRAINT course_user_fk_user_id   FOREIGN KEY (user_id)   REFERENCES public.tb_user(id)
);

ALTER TABLE IF EXISTS public.tb_course_user OWNER to postgres;