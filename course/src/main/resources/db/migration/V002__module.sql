CREATE TABLE IF NOT EXISTS public.tb_module
(
    id                    uuid         NOT NULL,
    creation_date_time    timestamp without time zone NOT NULL,
    last_update_date_time timestamp without time zone NOT NULL,
    title                 varchar(100)  NOT NULL,
    description           varchar(255)  NOT NULL,
    course_id             uuid          NOT NULL,
    CONSTRAINT module_pk PRIMARY KEY (id),
    CONSTRAINT module_fk_course_id FOREIGN KEY (course_id) REFERENCES public.tb_course(id)
);

ALTER TABLE IF EXISTS public.tb_module OWNER to postgres;