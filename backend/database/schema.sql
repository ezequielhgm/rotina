CREATE TABLE public."user" (
    user_id serial4 NOT NULL,
    user_name varchar(100) NOT NULL,
    user_email varchar(150) NOT NULL,
    user_phone varchar(20) NOT NULL,
    user_password varchar(255) NOT NULL,
    user_active boolean NOT NULL DEFAULT true,
    CONSTRAINT user_pk PRIMARY KEY (user_id),
    CONSTRAINT user_email_uk UNIQUE (user_email)
);

CREATE TABLE public.task_category (
    task_cat_id serial4 NOT NULL,
    task_cat_title varchar(100) NOT NULL,
    task_cat_color varchar(7),
    task_cat_active boolean NOT NULL DEFAULT true,
    CONSTRAINT task_category_pk PRIMARY KEY (task_cat_id)
);

CREATE TABLE public.task (
    task_id serial4 NOT NULL,
    fk_user int4 NOT NULL,
    fk_category int4,
    task_title varchar(100) NOT NULL,
    task_description text,
    task_start_date date NOT NULL,
    task_active boolean NOT NULL DEFAULT true,
    CONSTRAINT task_pk PRIMARY KEY (task_id),
    CONSTRAINT task_user_fk FOREIGgit aN KEY (fk_user)
        REFERENCES public."user" (user_id),
    CONSTRAINT task_category_fk FOREIGN KEY (fk_category)
        REFERENCES public.task_category (task_cat_id)
);

CREATE TABLE public.task_sequence (
    task_seq serial4 NOT NULL,
    fk_task int4 NOT NULL,
    task_seq_completion_date date NOT NULL,
    task_seq_active boolean NOT NULL DEFAULT true,
    CONSTRAINT task_sequence_pk PRIMARY KEY (task_seq),
    CONSTRAINT task_sequence_task_fk FOREIGN KEY (fk_task)
        REFERENCES public.task (task_id),
    CONSTRAINT task_sequence_date_uk UNIQUE (fk_task, task_seq_completion_date)
);
