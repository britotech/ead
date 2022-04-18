CREATE TABLE IF NOT EXISTS public.tb_lesson
(
    id                    uuid         NOT NULL,
    creation_date_time    timestamp without time zone NOT NULL,
    last_update_date_time timestamp without time zone NOT NULL,
    title                 varchar(100)  NOT NULL,
    description           varchar(255)  NOT NULL,
    video_url             varchar(255)  NOT NULL,
    module_id             uuid          NOT NULL,
    CONSTRAINT lesson_pk PRIMARY KEY (id),
    CONSTRAINT lesson_fk_module_id FOREIGN KEY (module_id) REFERENCES public.tb_module(id)
    );

ALTER TABLE IF EXISTS public.tb_lesson OWNER to postgres;