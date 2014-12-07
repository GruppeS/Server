CREATE DATABASE IF NOT EXISTS cbscalendar;
use cbscalendar;
SET SESSION FOREIGN_KEY_CHECKS=0;

CREATE TABLE IF NOT EXISTS calendars
(
	calendar varchar(50) NOT NULL,
	active boolean NOT NULL DEFAULT true,
	createdBy varchar(25) NOT NULL,
	isPublic boolean NOT NULL DEFAULT true,
	PRIMARY KEY (calendar)
);

CREATE TABLE IF NOT EXISTS usercalendars
(
	ID int NOT NULL AUTO_INCREMENT,
	username varchar(25) NOT NULL,
	calendar varchar(50) NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS events
(
	eventID int NOT NULL AUTO_INCREMENT,
	eventType varchar(25) NOT NULL,
	description varchar(50) NOT NULL,
	start datetime NOT NULL,
	end datetime NOT NULL,
	location varchar(25) NOT NULL,
	active boolean NOT NULL DEFAULT true,
	createdBy varchar(25) NOT NULL,
	calendar varchar(50) NOT NULL,
	PRIMARY KEY (eventID)
);

CREATE TABLE IF NOT EXISTS notes
(
	noteID int NOT NULL AUTO_INCREMENT,
	eventID int,
	cbsEventID varchar(90),
	createdBy varchar(40) NOT NULL,
	text text NOT NULL,
	active boolean NOT NULL DEFAULT true,
	PRIMARY KEY (noteID)
);

CREATE TABLE IF NOT EXISTS users
(
	username varchar(25) NOT NULL,
	active boolean NOT NULL DEFAULT true,
	password varchar(25) NOT NULL,
	isAdmin boolean NOT NULL DEFAULT false,
	PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS qotd
(
	qotdID int NOT NULL AUTO_INCREMENT,
	date datetime NOT NULL DEFAULT NOW() ON UPDATE NOW(),
	qotd varchar(500) NOT NULL,
	msg_type varchar(5) NOT NULL DEFAULT "qotd",
	primary KEY (qotdID)
);

CREATE TABLE IF NOT EXISTS forecast
(
	forecastID int NOT NULL AUTO_INCREMENT,
	date datetime NOT NULL DEFAULT NOW() ON UPDATE NOW(),
	day varchar (50) NOT NULL,
	temperature varchar(10) NOT NULL,
	summary varchar(50) NOT NULL,
	msg_type varchar(10) NOT NULL DEFAULT "forecast",
	primary KEY (forecastID)
);

ALTER TABLE calendars
	ADD FOREIGN KEY (createdBy)
	REFERENCES users (username)
	ON UPDATE RESTRICT
;

ALTER TABLE events
	ADD FOREIGN KEY (calendar)
	REFERENCES calendars (calendar)
	ON UPDATE RESTRICT
;

ALTER TABLE events
	ADD FOREIGN KEY (createdBy)
	REFERENCES users (username)
	ON UPDATE RESTRICT
;

ALTER TABLE notes
	ADD FOREIGN KEY (eventID)
	REFERENCES events (eventID)
	ON UPDATE RESTRICT
;

ALTER TABLE notes
	ADD FOREIGN KEY (createdBy)
	REFERENCES users (username)
	ON UPDATE RESTRICT
;

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

INSERT INTO `cbscalendar`.`usercalendars`
(`username`,
`calendar`)
VALUES
("bjsc13ac",
"test")
;

INSERT INTO `cbscalendar`.`calendars`
(`calendar`,
`createdBy`)
VALUES
("test",
"bjsc13ac")
;

INSERT INTO `cbscalendar`.`events`
(`eventType`,
`description`,
`start`,
`end`,
`location`,
`createdBy`,
`calendar`)
VALUES
("test",
"test",
now(),
now(),
"test",
"bjsc13ac",
"test")
;