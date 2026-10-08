-- Database schema for the Rotina backend.
-- Apply this file to create the initial tables in PostgreSQL.

CREATE TABLE public."user" (
    usu_id serial4 NOT NULL,
    usu_name varchar NOT NULL,
    usu_email varchar NOT NULL,
    usu_phone varchar NOT NULL,
    usu_password varchar NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (usu_id)
);

CREATE TABLE public.task (
    task_id serial4 NOT NULL,
    user_id int4 NOT NULL,
    task_name varchar NOT NULL,
    task_description text,
    task_start_date date NOT NULL,
    task_active boolean NOT NULL DEFAULT true,
    CONSTRAINT task_pk PRIMARY KEY (task_id),
    CONSTRAINT task_user_fk FOREIGN KEY (user_id)
        REFERENCES public."user" (usu_id)
        ON DELETE CASCADE
);

CREATE TABLE public.task_completion (
    task_completion_id serial4 NOT NULL,
    task_id int4 NOT NULL,
    completion_date date NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT task_completion_pk PRIMARY KEY (task_completion_id),
    CONSTRAINT task_completion_task_fk FOREIGN KEY (task_id)
        REFERENCES public.task (task_id)
        ON DELETE CASCADE,
    CONSTRAINT task_completion_date_uk UNIQUE (task_id, completion_date)
);
