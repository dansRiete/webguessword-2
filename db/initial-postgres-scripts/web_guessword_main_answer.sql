create table main.answer
(
    id          integer   not null
        constraint answer_pkey
            primary key,
    answered    timestamp not null,
    correct     boolean   not null,
    question_id integer
        constraint fk8frr4bcabmmeyyu60qt7iiblo
            references question
);

