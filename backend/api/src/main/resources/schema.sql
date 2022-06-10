drop table if exists article CASCADE
drop table if exists category CASCADE
drop table if exists category_channel CASCADE
drop table if exists channel CASCADE
drop table if exists channel_member CASCADE
drop table if exists channel_notice CASCADE
drop table if exists comment CASCADE
drop table if exists file CASCADE
drop table if exists member CASCADE
drop table if exists notice CASCADE
drop table if exists post CASCADE
drop table if exists qna CASCADE
drop table if exists vote CASCADE
drop sequence if exists hibernate_sequence

create sequence hibernate_sequence start with 1 increment by 1

create table article (article_id bigint not null, created_date timestamp, last_modified_date timestamp, category varchar(255), content clob, post_category varchar(255), title varchar(255), views bigint, vote_count bigint, writer varchar(255), channel_id bigint, member_id bigint, primary key (article_id))
create table category (category_id bigint not null, name varchar(255), parent_id bigint, primary key (category_id))
create table category_channel (category_channel_id bigint not null, category_id bigint, channel_id bigint, primary key (category_channel_id))
create table channel (channel_id bigint not null, category varchar(255), date_created timestamp, description clob, limited_mem_num bigint, manager_name varchar(255), member_count bigint, name varchar(255), thumbnail_description varchar(255), store_file_name varchar(255), upload_file_name varchar(255), waiting_member_count bigint, manager_id bigint, primary key (channel_id))
create table channel_member (channel_member_id bigint not null, channel_id bigint, member_id bigint, waiting_member_id bigint, primary key (channel_member_id))
create table channel_notice (id bigint not null, content clob, date_created timestamp, title varchar(255), views bigint, writer varchar(255), channel_id bigint, primary key (id))
create table comment (comment_id bigint not null, content varchar(255), date_created timestamp, vote_count bigint, writer varchar(255), article_id bigint, channel_notice_id bigint, member_id bigint, notice_id bigint, parent bigint, post_id bigint, qna_id bigint, primary key (comment_id))
create table file (id bigint not null, store_file_name varchar(255), upload_file_name varchar(255), article_id bigint, channel_id bigint, channel_notice_id bigint, notice_id bigint, post_id bigint, qna_id bigint, primary key (id))
create table member (member_id bigint not null, authority varchar(255), birthday varchar(255), date_created timestamp, email varchar(255), gender varchar(255), name varchar(255), password varchar(255), phone varchar(255), store_file_name varchar(255), upload_file_name varchar(255), primary key (member_id))
create table notice (id bigint not null, content clob, date_created timestamp, title varchar(255), views bigint, writer varchar(255), member_id bigint, primary key (id))
create table post (post_id bigint not null, content clob, date_created timestamp, post_category varchar(255), title varchar(255), views bigint, vote_count bigint, writer varchar(255), channel_id bigint, member_id bigint, primary key (post_id))
create table qna (id bigint not null, content varchar(255), date_created timestamp, title varchar(255), views bigint, vote_count bigint, writer varchar(255), member_id bigint, primary key (id))
create table vote (id bigint not null, article_id bigint, comment_id bigint, member_id bigint, post_id bigint, qna_id bigint, primary key (id))

alter table article add constraint FK4n3fg46ni0lnrnym1qjorq5e2 foreign key (channel_id) references channel
alter table article add constraint FK6l9vkfd5ixw8o8kph5rj1k7gu foreign key (member_id) references member
alter table category add constraint FK2y94svpmqttx80mshyny85wqr foreign key (parent_id) references category
alter table category_channel add constraint FKmbly2eycmtoy7qciu3pwpr7su foreign key (category_id) references category
alter table category_channel add constraint FKbjb3n6r1325fy51da73esxpy1 foreign key (channel_id) references channel
alter table channel add constraint FKdfldoonj4bqoss0a7mpajsick foreign key (manager_id) references member
alter table channel_member add constraint FKc8s8yiekqn7aienyyd4vw87u8 foreign key (channel_id) references channel
alter table channel_member add constraint FK5sikb9ckqanun8xm2llcuh1je foreign key (member_id) references member
alter table channel_member add constraint FKq5u7wnlgmlgnk53o5qmacitwe foreign key (waiting_member_id) references member
alter table channel_notice add constraint FKqf0w64lpqq6selionmhtnpuwp foreign key (channel_id) references channel
alter table comment add constraint FK5yx0uphgjc6ik6hb82kkw501y foreign key (article_id) references article
alter table comment add constraint FK994syo4a30lcpepe1qbftj36g foreign key (channel_notice_id) references channel_notice
alter table comment add constraint FKmrrrpi513ssu63i2783jyiv9m foreign key (member_id) references member
alter table comment add constraint FKq7rr5epaoagevts8cop65o31h foreign key (notice_id) references notice
alter table comment add constraint FKiv5gvib8r4qx2vunyjd1yu1i2 foreign key (parent) references comment
alter table comment add constraint FKs1slvnkuemjsq2kj4h3vhx7i1 foreign key (post_id) references post
alter table comment add constraint FKdmgl792nu5ndw17m8gyu8eiu5 foreign key (qna_id) references qna
alter table file add constraint FKp3x58xt25ld3mrbi37ce04vg2 foreign key (article_id) references article
alter table file add constraint FK5nvk3oaatacnnoprgotvscyc0 foreign key (channel_id) references channel
alter table file add constraint FK97jtl5qqwhyer2b71rs6mxg8b foreign key (channel_notice_id) references channel_notice
alter table file add constraint FK9sue39n4ky49ha47ujunh07b4 foreign key (notice_id) references notice
alter table file add constraint FKnm59rbv6qbowpdacbalxrud1e foreign key (post_id) references post
alter table file add constraint FKr931ktydggesw4y7vcu4iwnc3 foreign key (qna_id) references qna
alter table notice add constraint FKnriaekshh15qoqnlhvqkj931e foreign key (member_id) references member
alter table post add constraint FKpvtuuee0fnilflnjmilg8w6cw foreign key (channel_id) references channel
alter table post add constraint FK83s99f4kx8oiqm3ro0sasmpww foreign key (member_id) references member
alter table qna add constraint FKm1m3olxkohgmxid7vamkwo8i9 foreign key (member_id) references member
alter table vote add constraint FKr0cbtnr3ogukmmkj8041tf3v3 foreign key (article_id) references article
alter table vote add constraint FKpx8u9rivrirg53lsdcqvi90qo foreign key (comment_id) references comment
alter table vote add constraint FKgkbgl6xp2rpgwghb7mtyuv48h foreign key (member_id) references member
alter table vote add constraint FKl3c067ewaw5xktl5cjvniv3e9 foreign key (post_id) references post
alter table vote add constraint FK206ud6nidwcj4126ro8trhiu4 foreign key (qna_id) references qna

insert into member (authority, birthday, date_created, email, gender, name, password, phone, store_file_name, upload_file_name, member_id) VALUES ('ADMIN', '970927', '2022-06-10 11:09:26.259838', 'admin', 'MALE', '운영자', 'admin', '010-2354-9960', '/images/member/8a3d6850-c2d7-4d4e-b8b1-75fbe31b8a70.png', 'js.png', '1')
insert into member (authority, birthday, date_created, email, gender, name, password, phone, store_file_name, upload_file_name, member_id) VALUES ('NORMAL', '970927', '2022-06-10 11:09:26.259838', 'test1', 'MALE', '테스트1', 'test1', '010-2354-9960', '/images/member/8a3d6850-c2d7-4d4e-b8b1-75fbe31b8a70.png', 'js.png', '2')
insert into member (authority, birthday, date_created, email, gender, name, password, phone, store_file_name, upload_file_name, member_id) VALUES ('NORMAL', '970927', '2022-06-10 11:09:26.259838', 'test2', 'MALE', '테스트2', 'test2', '010-2354-9960', '/images/member/8a3d6850-c2d7-4d4e-b8b1-75fbe31b8a70.png', 'js.png', '3')
insert into member (authority, birthday, date_created, email, gender, name, password, phone, store_file_name, upload_file_name, member_id) VALUES ('NORMAL', '970927', '2022-06-10 11:09:26.259838', 'test3', 'MALE', '테스트3', 'test3', '010-2354-9960', '/images/member/8a3d6850-c2d7-4d4e-b8b1-75fbe31b8a70.png', 'js.png', '4')
