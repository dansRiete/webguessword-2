create table if not exists client
(
	client_login text not null
		constraint client_pk
			primary key,
	email text not null,
	registration_date timestamp not null,
	last_access timestamp not null,
	is_active boolean not null,
	password integer
);

alter table client owner to postgres;

create unique index if not exists client_user_login_uindex
	on client (client_login);

create table if not exists question
(
	id serial not null
		constraint question_pk
			primary key,
	client_login text not null,
	is_active boolean not null,
	created timestamp not null,
	probability_factor double precision not null,
	probability_multiplier double precision not null,
	last_accessed timestamp,
	tag text
);

alter table question owner to postgres;

create unique index if not exists question_id_uindex
	on question (id);

create table if not exists language
(
	language_code text not null
		constraint language_pk
			primary key,
	name text not null
);

alter table language owner to postgres;

create unique index if not exists language_language_code_uindex
	on language (language_code);

create table if not exists word
(
	id serial not null
		constraint question_word_pk
			primary key,
	question_id text not null,
	word text not null,
	added timestamp not null,
	is_active boolean not null,
	language_code text not null,
	transcription text
);

alter table word owner to postgres;

create unique index if not exists question_word_id_uindex
	on word (id);

create table if not exists answer
(
	client_login text not null,
	question_id integer not null,
	is_right boolean not null,
	answered timestamp not null,
	id serial not null
		constraint answer_pk
			primary key
);

alter table answer owner to postgres;

create unique index if not exists answer_client_login_uindex
	on answer (client_login);

create unique index if not exists answer_id_uindex
	on answer (id);