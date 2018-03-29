ALTER TABLE public.languages ADD id TEXT NOT NULL;
CREATE UNIQUE INDEX languages_id_uindex ON public.languages (id);
ALTER TABLE public.languages ADD user_id TEXT NOT NULL;
ALTER TABLE public.languages
  ADD CONSTRAINT languages_users_user_login_fk
FOREIGN KEY (user_id) REFERENCES users (user_login);
ALTER TABLE public.languages DROP CONSTRAINT languages_pkey CASCADE;
ALTER TABLE public.languages ADD CONSTRAINT languages_id_pk PRIMARY KEY (id);

ALTER TABLE public.phrases ADD CONSTRAINT phrases_languages_id_fk1 FOREIGN KEY (language_1) REFERENCES languages (id);
ALTER TABLE public.phrases ADD CONSTRAINT phrases_languages_id_fk2 FOREIGN KEY (language_2) REFERENCES languages (id);