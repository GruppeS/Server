package model.database;

import model.Model;

public class DatabaseInit extends Model {

	/**
	 * Calls method doesDatabaseExist to determine if database exists. If not then readfromSqlFile is called to create the database
	 */
	public void go() {

		if (doesDatabaseExist()) {
			System.out.println("Database environment does exist");
		} else {
			System.out.println("Database environment does NOT exist");
			readfromSqlFile("res/createDBscript.sql");
		}
	}
}
