CREATE DATABASE IF NOT EXISTS cbscalendar;
use cbscalendar;
SET SESSION FOREIGN_KEY_CHECKS=0;

/* Create Tables */

CREATE TABLE IF NOT EXISTS calendar
(
	calendarid int NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	active boolean NOT NULL DEFAULT true,
	createdBy int NOT NULL,
	public boolean NOT NULL DEFAULT true,
	PRIMARY KEY (CalendarID)
);

CREATE TABLE IF NOT EXISTS usercalendars
(
	userid int NOT NULL,
	CalendarID int NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
	eventid int NOT NULL AUTO_INCREMENT,
	activityid varchar(100) NOT NULL,
	eventType varchar(50) NOT NULL,
	title varchar(50) NOT NULL,
	describtion varchar(50) NOT NULL,
	start datetime NOT NULL,
	end datetime NOT NULL,
	location varchar(50) NOT NULL,
	createdBy int NOT NULL DEFAULT 1,
	CalendarID int NOT NULL,
	PRIMARY KEY (eventid)
);

CREATE TABLE IF NOT EXISTS notes
(
	noteid int NOT NULL AUTO_INCREMENT,
	eventid int NOT NULL,
	createdBy int NOT NULL,
	text text,
	active boolean NOT NULL DEFAULT true,
	PRIMARY KEY (noteid)
);

CREATE TABLE IF NOT EXISTS users
(
	userid int NOT NULL AUTO_INCREMENT,
	username varchar(40) NOT NULL,
	active boolean NOT NULL DEFAULT true,
	created datetime NOT NULL DEFAULT NOW(),
	password varchar(200) NOT NULL,
	isAdmin boolean NOT NULL DEFAULT false,
	PRIMARY KEY (userid)
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
	temperature varchar(10) NOT NULL,
	summary varchar(50) NOT NULL,
	msg_type varchar(10) NOT NULL DEFAULT "forecast",
	primary KEY (forecastID)
);

/* Create Foreign Keys */

ALTER TABLE events
	ADD FOREIGN KEY (calendarid)
	REFERENCES calendar (calendarid)
	ON UPDATE RESTRICT
;

ALTER TABLE events
	ADD FOREIGN KEY (createdBy)
	REFERENCES users (userid)
	ON UPDATE RESTRICT
;

ALTER TABLE usercalendars
	ADD FOREIGN KEY (calendarid)
	REFERENCES calendar (calendarid)
	ON UPDATE RESTRICT
;

ALTER TABLE usercalendars
	ADD FOREIGN KEY (userid)
	REFERENCES users (userid)
	ON UPDATE RESTRICT
;

ALTER TABLE notes
	ADD FOREIGN KEY (eventid)
	REFERENCES events (eventid)
	ON UPDATE RESTRICT
;

ALTER TABLE notes
	ADD FOREIGN KEY (createdBy)
	REFERENCES users (userid)
	ON UPDATE RESTRICT
;

/* Create Test Accounts */

INSERT INTO `cbscalendar`.`users`
(`username`,
`password`,
`isAdmin`)
VALUES
("admin",
"pass",
true)
;

INSERT INTO `cbscalendar`.`users`
(`username`,
`password`)
VALUES
("bjsc13ac",
"pass")
;

INSERT INTO `cbscalendar`.`users`
(`username`,
`password`)
VALUES
("sual13ab",
"pass")
;

INSERT INTO `cbscalendar`.`users`
(`username`,
`password`)
VALUES
("hefr13ae",
"pass")
;