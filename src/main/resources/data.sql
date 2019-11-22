DROP TABLE IF EXISTS user cascade;

CREATE TABLE user (
    user_id integer not null,
    "name" varchar(200),
    "lastname" varchar(200),
    "address" varchar(200),
    total_book_loaned integer,
    primary key(user_id)
);

DROP TABLE IF EXISTS book cascade;

CREATE TABLE book (
    book_id integer not null,
    book_name varchar(200),
    book_summary varchar(200),
    isbn varchar(200),
    total_copies integer not null,
    available_copies integer,
    primary key(book_id)
);

DROP TABLE IF EXISTS book_loan cascade;

CREATE TABLE book_loan (
    book_loan_id integer not null,
    user_id integer not null,
    book_id integer not null,
    loan_date date,
    returned_date date,
    is_loaned boolean,
    primary key(book_loan_id),
    foreign key(user_id) references user(user_id),
    foreign key(book_id) references book(book_id)
);