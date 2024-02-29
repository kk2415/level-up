use image;

DROP TABLE IF EXISTS joke;
CREATE TABLE joke(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    joke_text TEXT,
    joke_date DATE NOT NULL
) DEFAULT CHARSET=utf8  ENGINE=InnoDB;

INSERT INTO joke(joke_text, joke_date) VALUES ('hello', '2022-10-31');

DROP TABLE IF EXISTS hibernate_sequence;
DROP TABLE IF EXISTS file;

CREATE TABLE hibernate_sequence (
    next_val bigint
) ENGINE=InnoDB;
INSERT INTO hibernate_sequence(next_val) VALUES (0);

CREATE TABLE file (
    file_id bigint not null auto_increment primary key,
    upload_file_name varchar(30) not null,
    store_file_name varchar(100) not null,
    file_type varchar(100) not null,
    owner_id bigint not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;