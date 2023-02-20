drop table if exists `hibernate_sequence`;
drop table if exists `job`;

create table hibernate_sequence
(
    next_val bigint null
) engine=InnoDB;
insert into hibernate_sequence values (1);

create table `job` (
    job_id bigint not null auto_increment primary key,
    title text not null,
    url text not null,
    company varchar(50) not null,
    notice_end_date varchar(50) not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00'
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
