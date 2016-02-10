
drop table if exists user;

create table user
(
	id integer not null primary key autoincrement,
	name text not null,
	password text not null,
	firstname text not null,
	lastname text not null,
	email text not null,
    indexedrecords integer not null check (indexedrecords>=0),
    currentbatch integer not null
);

drop table if exists project;

create table project
(
	id integer not null primary key autoincrement,
	title text not null,
    recordsperimage int not null check (recordsperimage>=0),
    firstycoord integer not null check (firstycoord>=0),
    recordheight integer not null check (recordheight>=0),
    numfields integer not null
);

drop table if exists field;

create table field
(
	id integer not null primary key autoincrement,
	title text not null,
    xcoord integer not null check (xcoord>=0),
    width integer not null check (width>=0),
    helphtml text not null,
    columnnum integer not null,
    knowndata text,
    project integer not null
);

drop table if exists image;

create table image
(
	id integer not null primary key autoincrement,
    project integer not null,
	file text not null,
    
);

drop table if exists record;

create table record
(
    id integer not null primary key autoincrement,
    image integer not null,
    recordnum integer not null
);

drop table if exists value;

create table value
(
    id integer not null primary key autoincrement,
    record integer not null,
    columnnum integer not null,
    imageid integer not null,
    imageurl text not null,
    recordnum integer not null,
    value text, 
    project integer not null
);