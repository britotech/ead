CREATE TABLE IF NOT EXISTS public.tb_notification
(
    id                    uuid         NOT NULL,
    creation_date_time    timestamp without time zone NOT NULL,
    user_id               uuid         NOT NULL,
    title                 varchar(150) NOT NULL,
    message               text         NOT NULL,
    status                varchar(10)  NOT NULL,
    CONSTRAINT notification_pk PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.notification OWNER to postgres;