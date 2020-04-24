DELETE FROM domain.word;
DELETE FROM domain.question;

INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (1, '2017-03-26 11:09:05.000000', null, 30, 1.45, null);
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (2, '2017-03-26 11:13:54.000000', null, 27, 1.45, null);
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (3, '2017-04-05 10:55:07.000000', '2017-04-25 22:18:10.000000', 15, 1.45, 'cars');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (4, '2017-04-05 10:55:20.000000', '2017-07-04 10:24:36.000000', 42, 1, 'cars');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (5, '2017-04-05 10:55:32.000000', null, 97, 1, 'cars');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (6, '2017-04-05 10:55:43.000000', '2017-04-25 22:16:49.000000', 27, 1.45, 'cars');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (7, '2017-04-05 10:55:54.000000', null, 8, 1, 'cars');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (8, '2017-04-05 10:56:05.000000', null, 30, 1, 'city');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (9, '2017-04-05 10:56:17.000000', '2017-05-09 13:07:15.000000', 10, 1.45, 'city');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (10, '2017-04-05 10:56:37.000000', null, 7, 1, 'city');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (11, '2017-04-05 11:00:13.000000', '2017-07-04 10:24:22.000000', 27, 1.45, 'city');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (12, '2017-04-05 11:00:22.000000', '2017-04-25 22:14:45.000000', 2, 1.45, 'city');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (13, '2017-04-05 11:00:34.000000', '2017-04-25 22:18:00.000000', 27, 1.45, 'city');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (14, '2017-04-05 11:00:44.000000', null, -1, 1.45, 'food');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (15, '2017-04-05 11:01:17.000000', null, 30, 1, 'food');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (16, '2017-04-05 11:01:27.000000', null, 24, 1, 'food');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (17, '2017-04-05 11:01:36.000000', '2017-04-26 10:46:36.000000', 22, 1.45, 'food');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (18, '2017-04-05 11:01:47.000000', null, 30, 1, 'food');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (19, '2017-04-05 11:01:57.000000', '2017-05-09 12:34:42.000000', 54, 2.1025, 'food');
INSERT INTO domain.question (id, created, last_accessed, probability_factor, probability_multiplier, tag) VALUES (20, '2017-04-05 11:02:10.000000', null, 0, 1, 'food');

INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (1, 'en', null, 'bumper', 1);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (2, 'ru', null, 'бампер', 1);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (3, 'en', null, 'boot', 2);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (4, 'ru', null, 'багажник', 2);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (5, 'en', 'аксэлэрэйтор', 'accelerator', 3);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (6, 'ru', null, 'акселератор', 3);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (7, 'en', null, 'cab', 4);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (8, 'ru', null, 'кабина', 4);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (9, 'en', null, 'dashboard', 5);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (10, 'ru', null, 'приборная панель', 5);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (11, 'en', 'энджин', 'engine', 6);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (12, 'ru', null, 'двигатель', 6);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (13, 'en', null, 'jack', 7);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (14, 'ru', null, 'домкрат', 7);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (15, 'en', null, 'arch', 8);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (16, 'ru', null, 'арка', 8);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (17, 'en', 'лайбрари', 'library', 9);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (18, 'ru', null, 'библиотека', 9);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (19, 'en', null, 'cheap', 10);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (20, 'ru', null, 'дешёвый', 10);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (21, 'en', 'синэма', 'cinema', 11);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (22, 'ru', null, 'кино', 11);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (23, 'en', null, 'coin', 12);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (24, 'ru', null, 'монета', 12);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (25, 'en', 'мъюзиум', 'museum', 13);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (26, 'ru', null, 'музей', 13);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (27, 'en', null, 'assortment', 14);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (28, 'ru', null, 'ассорти', 14);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (29, 'en', null, 'white mushroom', 15);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (30, 'ru', null, 'белый гриб', 15);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (31, 'en', 'сосэр', 'saucer', 16);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (32, 'ru', null, 'блюдце', 16);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (33, 'en', null, 'sandwich', 17);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (34, 'ru', null, 'бутерброд', 17);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (35, 'en', 'драед', 'dried', 18);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (36, 'ru', null, 'вяленый', 18);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (37, 'en', null, 'bitter', 19);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (38, 'ru', null, 'горький', 19);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (39, 'en', 'фрозэн', 'frozen', 20);
INSERT INTO domain.word (id, language, transcription, word, question_id) VALUES (40, 'ru', null, 'замороженный', 20);

SELECT setval('question_seq', 20, true);  -- next value will be 21
SELECT setval('word_seq', 40, true);  -- next value will be 21
