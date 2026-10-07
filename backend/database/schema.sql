-- Database schema for the Rotina backend.
-- Apply this file to create the initial tables in PostgreSQL.

CREATE TABLE public.usuario (
    usu_id serial4 NOT NULL,
    usu_name varchar NOT NULL,
    usu_email varchar NOT NULL,
    usu_phone varchar NOT NULL,
    usu_password varchar NOT NULL,
    CONSTRAINT usuario_pk PRIMARY KEY (usu_id)
);
