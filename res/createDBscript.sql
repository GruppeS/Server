CREATE DATABASE IF NOT EXISTS cbscalendar;
use cbscalendar;
SET SESSION FOREIGN_KEY_CHECKS=0;

/* Create Tables */

CREATE TABLE IF NOT EXISTS Calendar
(
	calendarID int NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	active tinyint,
	createdBy varchar(255) NOT NULL,
	privatePublic tinyint NOT NULL COMMENT '1 = public
	2 = private',
	PRIMARY KEY (CalendarID)
);

CREATE TABLE IF NOT EXISTS qotd
(
	date datetime NOT NULL DEFAULT NOW() ON UPDATE NOW(),
	qotd varchar(500) NOT NULL,
	msg_type varchar(10) NOT NULL DEFAULT "qotd",
	primary KEY (date)
);

CREATE TABLE IF NOT EXISTS forecast
(
	forecastID int NOT NULL,
	date datetime NOT NULL DEFAULT NOW() ON UPDATE NOW(),
	day varchar (50) NOT NULL,
	apparentTemperature varchar(10) NOT NULL,
	summary varchar(50) NOT NULL,
	msg_type varchar(10) NOT NULL DEFAULT "forecast",
	primary KEY (forecastID)
);

CREATE TABLE IF NOT EXISTS events
(
	eventid int NOT NULL AUTO_INCREMENT,
	type int NOT NULL,
	location int,
	createdby int NOT NULL,
	start datetime NOT NULL,
	end datetime NOT NULL,
	name varchar(0) NOT NULL,
	text text NOT NULL,
	-- Decides wether the event is an import-event or user created
	-- 
	customevent boolean COMMENT 'Decides wether the event is an import-event or user created
',
	CalendarID int NOT NULL,
	PRIMARY KEY (eventid)
);


CREATE TABLE IF NOT EXISTS locationdata
(
	locationdataid int NOT NULL AUTO_INCREMENT,
	longitude int NOT NULL,
	latitude int UNIQUE,
	PRIMARY KEY (locationdataid)
);


CREATE TABLE IF NOT EXISTS notes
(
	noteId int NOT NULL AUTO_INCREMENT,
	eventId int NOT NULL,
	createdBy int NOT NULL,
	text text,
	dateTime datetime NOT NULL,
	active bit,
	PRIMARY KEY (noteid)
);


CREATE TABLE IF NOT EXISTS roles
(
	roleid int NOT NULL AUTO_INCREMENT,
	userid int NOT NULL,
	isAdmin boolean NOT NULL,
	PRIMARY KEY (roleid)
);


CREATE TABLE IF NOT EXISTS userevents
(
	userid int NOT NULL,
	CalendarID int NOT NULL
);


CREATE TABLE IF NOT EXISTS users
(
	userid int NOT NULL AUTO_INCREMENT,
	email varchar(40) NOT NULL,
	active boolean,
	created datetime NOT NULL DEFAULT NOW(),
	password varchar(200) NOT NULL,
	PRIMARY KEY (userid)
);

/* Create Foreign Keys */

ALTER TABLE events
	ADD FOREIGN KEY (CalendarID)
	REFERENCES Calendar (CalendarID)
	ON UPDATE RESTRICT
;


ALTER TABLE userevents
	ADD FOREIGN KEY (CalendarID)
	REFERENCES Calendar (CalendarID)
	ON UPDATE RESTRICT
;


ALTER TABLE notes
	ADD FOREIGN KEY (eventid)
	REFERENCES events (eventid)
	ON UPDATE RESTRICT
;


ALTER TABLE events
	ADD FOREIGN KEY (location)
	REFERENCES locationdata (locationdataid)
	ON UPDATE RESTRICT
;


ALTER TABLE events
	ADD FOREIGN KEY (createdby)
	REFERENCES users (userid)
	ON UPDATE RESTRICT
;


ALTER TABLE roles
	ADD FOREIGN KEY (userid)
	REFERENCES users (userid)
	ON UPDATE RESTRICT
;


ALTER TABLE userevents
	ADD FOREIGN KEY (userid)
	REFERENCES users (userid)
	ON UPDATE RESTRICT
;


ALTER TABLE notes
	ADD FOREIGN KEY (createdby)
	REFERENCES users (userid)
	ON UPDATE RESTRICT
;


/* Create Dummy Account */

INSERT INTO `cbscalendar`.`users`
(`email`,
`active`,
`password`)
VALUES
("bjsc13ac",
true,
"pass")
;

INSERT INTO `cbscalendar`.`roles`
(`userid`,
`isAdmin`)
VALUES
(1,
false
)
;

INSERT INTO `cbscalendar`.`users`
(`email`,
`active`,
`password`)
VALUES
("sual13ab",
true,
"pass")
;

INSERT INTO `cbscalendar`.`roles`
(`userid`,
`isAdmin`)
VALUES
(1,
false
)
;

INSERT INTO `cbscalendar`.`users`
(`email`,
`active`,
`password`)
VALUES
("hefr13ae",
true,
"pass")
;

INSERT INTO `cbscalendar`.`roles`
(`userid`,
`isAdmin`)
VALUES
(1,
false
)
;

INSERT INTO `cbscalendar`.`users`
(`email`,
`active`,
`password`)
VALUES
("admin",
true,
"pass")
;

INSERT INTO `cbscalendar`.`roles`
(`userid`,
`isAdmin`)
VALUES
(2,
true
)
;