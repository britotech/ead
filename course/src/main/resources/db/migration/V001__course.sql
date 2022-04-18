CREATE TABLE IF NOT EXISTS public.tb_course
(
    id                    uuid         NOT NULL,
    creation_date_time    timestamp without time zone NOT NULL,
    last_update_date_time timestamp without time zone NOT NULL,
    name                  varchar(100)  NOT NULL,
    description           varchar(255)  NOT NULL,
    image_url             varchar(255),
    level                 varchar(12)   NOT NULL,
    status                varchar(10)   NOT NULL,
    user_instructor_id    uuid          NOT NULL,
    CONSTRAINT course_pk PRIMARY KEY (id)
    );

ALTER TABLE IF EXISTS public.tb_course OWNER to postgres;