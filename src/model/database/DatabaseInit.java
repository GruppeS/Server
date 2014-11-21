package model.database;

import java.io.IOException;
import java.sql.SQLException;

import model.Model;

public class DatabaseInit extends Model {

	public void go() throws SQLException, IOException {

		if (doesDatabaseExist()) {
			System.out.println("Database environment does exist");
		} else {
			System.out.println("Database environment does NOT exist");
			readfromSqlFile("res/createDBscript.sql");
		}
	}
}
