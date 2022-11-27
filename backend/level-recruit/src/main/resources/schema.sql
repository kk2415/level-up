drop table if exists `jobs`;

create table `jobs` (
    job_id bigint not null auto_increment primary key,
    title text not null,
    company varchar(50) not null,
    url text,
    job_status varchar(20) not null,
    closing_type varchar(20) not null,
    open_date datetime not null,
    closing_date datetime not null,
    created_at datetime not null default '2022-01-01 00:00:00',
    updated_at datetime not null default '2022-01-01 00:00:00'
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;