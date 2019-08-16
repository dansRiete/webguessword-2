ALTER TABLE public.phrases_words
  ALTER COLUMN phrase_word_id TYPE TEXT USING phrase_word_id :: TEXT;