
 drop database bookmyshow;
 create database bookmyshow;


 create table bookings (id  bigserial not null, created_at timestamp, updated_at timestamp, ticket_id int8, user_id int8, primary key (id));
 create table cities (id  bigserial not null, created_at timestamp, updated_at timestamp, country varchar(255) not null, name varchar(255) not null, state varchar(255) not null, primary key (id));
 create table locations (id  bigserial not null, created_at timestamp, updated_at timestamp, latitude varchar(255), longitude varchar(255), primary key (id));
 create table movies (id  bigserial not null, created_at timestamp, updated_at timestamp, about_movie varchar(255), certificate_type varchar(255), genre varchar(255), language varchar(255), movie_name varchar(255), primary key (id));
 create table prebooking (id  bigserial not null, created_at timestamp, updated_at timestamp, seat_id int8, show_id int8, user_id int8, primary key (id));
 create table screens (id  bigserial not null, created_at timestamp, updated_at timestamp, screen_number int8 not null, theatre_id int8, primary key (id));
 create table seats (id  bigserial not null, created_at timestamp, updated_at timestamp, is_booked boolean not null, seat_name varchar(255) not null, seat_price float8 not null, booking_id int8, tier_id int8, primary key (id));
 create table show (id  bigserial not null, created_at timestamp, updated_at timestamp, show_time timestamp, city_id int8, movie_id int8, screen_id int8, primary key (id));
 create table theatres (id  bigserial not null, created_at timestamp, updated_at timestamp, base_price float8 not null, location_name varchar(255) not null, theatre_name varchar(255) not null, city_id int8, location_id int8, primary key (id));
 create table ticket (id  bigserial not null, created_at timestamp, updated_at timestamp, amount float8, booked_at timestamp not null, show_time timestamp, tier_name varchar(255), movie_id int8, screen_id int8, primary key (id));
 create table ticket_seats (ticket_id int8 not null, seats varchar(255));
 create table tier (id  bigserial not null, created_at timestamp, updated_at timestamp, multiplier float8, name varchar(255), screen_id int8, primary key (id));
 create table users (id  bigserial not null, created_at timestamp, updated_at timestamp, mobile varchar(255) not null, name varchar(255) not null, primary key (id));
 alter table cities drop constraint UK_l61tawv0e2a93es77jkyvi7qa;
 alter table cities add constraint UK_l61tawv0e2a93es77jkyvi7qa unique (name);
 alter table users drop constraint UK_63cf888pmqtt5tipcne79xsbm;
 alter table users add constraint UK_63cf888pmqtt5tipcne79xsbm unique (mobile);
 alter table bookings add constraint FKljwmijsciavq9us5ywsycyi68 foreign key (ticket_id) references ticket;
 alter table bookings add constraint FKeyog2oic85xg7hsu2je2lx3s6 foreign key (user_id) references users;
 alter table prebooking add constraint FK4tauiahhkknumacwehreb269y foreign key (seat_id) references seats;
 alter table prebooking add constraint FKefippty80ktkvfy8rtos6ot27 foreign key (show_id) references show;
 alter table prebooking add constraint FKa526swsuqx5drq1teneonceon foreign key (user_id) references users;
 alter table screens add constraint FKomykyk08ts7cl7rwxp5qlaear foreign key (theatre_id) references theatres;
 alter table seats add constraint FKku1rurcfuh5d7pve1vsyomxa6 foreign key (booking_id) references bookings;
 alter table seats add constraint FK3iabkkm7h40efn0jmnibeaw39 foreign key (tier_id) references tier;
 alter table show add constraint FKj5weuknsokloj2ouyprcjr45t foreign key (city_id) references cities;
 alter table show add constraint FKbpr58ci81go7v0r9ie522r5l9 foreign key (movie_id) references movies;
 alter table show add constraint FK7gcuxsx389yrymxeg4yu0qdmp foreign key (screen_id) references screens;
 alter table theatres add constraint FKmuvt4yyk0q3w7mquifdjfshhg foreign key (city_id) references cities;
 alter table theatres add constraint FKmqf6nyvxveij2dqipi02em14b foreign key (location_id) references locations;
 alter table ticket add constraint FKoodstanycslbv4if5fuy9unvf foreign key (movie_id) references movies;
 alter table ticket add constraint FK60qauhcl4fh4i48uhpdmdkydu foreign key (screen_id) references screens;
 alter table ticket_seats add constraint FK44gchftco6god800pnyfl3f06 foreign key (ticket_id) references ticket;
 alter table tier add constraint FK7ommllj0r0hbhthc7ji3wkiqg foreign key (screen_id) references screens;