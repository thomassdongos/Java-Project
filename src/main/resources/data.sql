DROP TABLE IF EXISTS user cascade;

CREATE TABLE IF NOT EXISTS user (
    user_id integer not null auto_increment,
    firstname varchar(200),
    lastname varchar(200),
    useraddress varchar(200),
    total_book_loaned integer,
    primary key(user_id)
);

DROP TABLE IF EXISTS book cascade;

CREATE TABLE IF NOT EXISTS book (
    book_id integer not null auto_increment,
    book_name varchar(200),
    book_summary varchar(200),
    isbn varchar(200),
    total_copies integer not null,
    available_copies integer,
    primary key(book_id)
);

DROP TABLE IF EXISTS book_loan cascade;

CREATE TABLE IF NOT EXISTS book_loan (
    book_loan_id integer not null auto_increment,
    user_id integer not null,
    book_id integer not null,
    loan_date date,
    returned_date date,
    is_loaned boolean,
    primary key(book_loan_id),
    foreign key(user_id) references user(user_id),
    foreign key(book_id) references book(book_id)
);