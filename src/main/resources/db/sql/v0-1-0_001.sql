create schema if not exists mainapp;

create table if not exists mainapp.answer
(
	client_login text,
	question_id integer,
	is_right boolean,
	answered timestamp,
	id serial not null
);

alter table mainapp.answer owner to postgres;

create table if not exists mainapp.client
(
	client_login text,
	email text,
	registration_date timestamp,
	last_access timestamp,
	is_active boolean,
	password integer
);

alter table mainapp.client owner to postgres;

create table if not exists mainapp.language
(
	language_code text,
	name text
);

alter table mainapp.language owner to postgres;

create table if not exists mainapp.question
(
	id serial not null,
	client_login text,
	is_active boolean,
	created timestamp,
	probability_factor double precision,
	probability_multiplier double precision,
	last_accessed timestamp,
	tag text
);

alter table mainapp.question owner to postgres;

create table if not exists mainapp.word
(
	id serial not null,
	question_id text,
	word text,
	added timestamp,
	is_active boolean,
	language_code text,
	transcription text
);

alter table mainapp.word owner to postgres;
