use level_up;

drop table if exists hibernate_sequence;
drop table if exists stopwords;

drop table if exists channel_article_vote;
drop table if exists channel_comment_vote;
drop table if exists channel_comment;
drop table if exists channel_article;
drop table if exists channel_member;
drop table if exists channel_skill;
drop table if exists channel;

drop table if exists article_count;
drop table if exists article_vote;
drop table if exists comment_vote;
drop table if exists comment;
drop table if exists article;
drop table if exists writer;

drop table if exists email_auth;
drop table if exists role;
drop table if exists member_skill;
drop table if exists `skill`;
drop table if exists member;

create table hibernate_sequence (
    next_val bigint
) engine=InnoDB;
INSERT INTO hibernate_sequence(next_val) VALUES (0);


#######################################################################################################################

create table if not exists `skill` (
    skill_id bigint not null auto_increment primary key,
    `name` varchar(255) not null unique,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00'
) engine=InnoDB default charset=utf8 collate=utf8_general_ci;

#######################################################################################################################

create table if not exists member (
    member_id bigint not null auto_increment primary key,
    email varchar(50) unique not null,
    password varchar(255) not null,
    member_name varchar(50) not null,
    nickname varchar(50) unique not null,
    gender varchar(6) not null,
    birthday date not null,
    phone varchar(13) not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00',
    created_by varchar(255) not null,
    updated_by varchar(255) not null
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

create table if not exists role (
    role_id bigint not null auto_increment primary key,
    member_id bigint not null,
    role_name varchar(20) not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00',
    index member_id (member_id)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

create table if not exists email_auth (
    email_auth_id  bigint not null auto_increment primary key,
    email_auth_type varchar(50) not null,
    email varchar(50) not null,
    security_code varchar(10) not null,
    is_authenticated bit(1) not null,
    received_date datetime not null,
    expire_date datetime not null,
    member_id bigint not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00',
    index member_id (member_id)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

create table if not exists member_skill (
    member_skill_id bigint not null auto_increment primary key,
    member_id bigint not null,
    skill_id bigint not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00'
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

#######################################################################################################################

create table if not exists writer (
    writer_id bigint not null auto_increment primary key,
    member_id bigint not null unique,
    nickname varchar(255) not null,
    email varchar(255) not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00',
    index nickname (nickname)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

create table if not exists article (
    article_id bigint not null auto_increment primary key,
    content longtext not null,
    title varchar(255) not null,
    views bigint not null,
    article_type varchar(20) not null,
    writer_id bigint not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00',
    index writer_id (writer_id),
    fulltext (title) with parser NGRAM
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

create table if not exists comment (
    comment_id bigint not null auto_increment primary key,
    content text not null,
    writer_id bigint not null,
    article_id bigint not null,
    parent_id bigint null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00',
    index writer_id (writer_id),
    index article_id (article_id),
    index parent_id (parent_id)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

create table if not exists article_vote (
    article_vote_id bigint not null auto_increment primary key,
    member_id bigint not null,
    article_id bigint not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00',
    index member_id (member_id),
    index article_id (article_id)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

create table if not exists comment_vote (
    comment_vote_id bigint not null auto_increment primary key,
    member_id bigint not null,
    comment_id bigint not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00',
    index member_id (member_id),
    index comment_id (comment_id)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

create table if not exists article_count (
    article_count_id bigint not null auto_increment primary key,
    article_type varchar(20) not null,
    count bigint not null default 0
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

insert into article_count (article_type, count)
values ('QNA', 0), ('NOTICE', 0), ('CHANNEL_POST', 0), ('CHANNEL_NOTICE', 0);

#######################################################################################################################

create table if not exists channel (
    channel_id bigint not null auto_increment primary key,
    `description` longtext not null,
    channel_name varchar(30) not null,
    member_max_number bigint not null,
    channel_category varchar(10) not null,
    expected_start_date date not null,
    expected_end_date date not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00'
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

create table if not exists channel_skill (
    channel_skill_id bigint not null auto_increment primary key,
    channel_id bigint not null,
    skill_id bigint not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00'
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

create table if not exists channel_member (
    channel_member_id bigint not null auto_increment primary key,
    email varchar(255) not null,
    nickname varchar(255) not null,
    is_manager bit(1) not null,
    is_waiting_member bit(1) not null,
    member_id bigint not null,
    channel_id bigint not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00',
    index member_id (member_id),
    index channel_id (channel_id)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

create table if not exists channel_article (
    channel_article_id bigint not null auto_increment primary key,
    category varchar(20) null,
    content longtext not null,
    title varchar(255) not null,
    views bigint not null,
    channel_id bigint not null,
    channel_member_id bigint not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00',
    index channel_id (channel_id),
    index channel_member_id (channel_member_id),
    fulltext (title) with parser NGRAM
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

create table if not exists channel_comment (
    channel_comment_id bigint not null auto_increment primary key,
    content text not null,
    channel_member_id bigint not null,
    channel_article_id bigint not null,
    parent_id bigint null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00',
    index member_id (channel_member_id),
    index article_id (channel_article_id),
    index parent_id (parent_id)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

create table if not exists channel_article_vote (
    channel_article_vote_id bigint not null auto_increment primary key,
    channel_member_id bigint not null,
    channel_article_id bigint not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00',
    index channel_member_id (channel_member_id),
    index channel_article_id (channel_article_id)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

create table if not exists channel_comment_vote (
    channel_comment_vote_id bigint not null auto_increment primary key,
    channel_member_id bigint not null,
    channel_comment_id bigint not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00',
    index channel_member_id (channel_member_id),
    index channel_comment_id (channel_comment_id)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

#######################################################################################################################

CREATE TABLE if not exists stopwords(value VARCHAR(30)) ENGINE = INNODB;

#######################################################################################################################