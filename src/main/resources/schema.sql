CREATE DATABASE gym  WITH OWNER = postgres ENCODING = 'UTF8' CONNECTION LIMIT = -1;

CREATE TABLE public.clients
(
    id serial NOT NULL,
    name character varying(256) COLLATE pg_catalog."default" NOT NULL,
    gender character varying(10) COLLATE pg_catalog."default" NOT NULL,
    phone_no character varying(15) COLLATE pg_catalog."default",
    validation_expire_date date NOT NULL,
    pass character varying(15) COLLATE pg_catalog."default",
    registered_on date NOT NULL default CURRENT_DATE,
    CONSTRAINT clients_pkey PRIMARY KEY (id)
);

CREATE TABLE public.users
(
    id serial NOT NULL,
    name character varying(256) COLLATE pg_catalog."default" NOT NULL,
    username character varying(25) COLLATE pg_catalog."default" NOT NULL,
    password character varying(25) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

INSERT INTO public.users (name, username, password) VALUES ('Gym Admin', 'admin', 'admin');