package model.database;

import java.io.IOException;
import java.sql.SQLException;

import model.Model;
import model.QueryBuild.QueryBuilder;

public class DatabaseInit extends Model {

	public void go() throws SQLException, IOException {

		String[] keys = {"firstKey", "secondKey"};
		String[] wheremparamters = {"alder", "hat"};
		QueryBuilder qb = new QueryBuilder();
		resultSet = qb.selectFrom("users").all().ExecuteQuery();
		resultSet = qb.selectFrom(keys, "events").where("id", "=", "123").ExecuteQuery();

		while (resultSet.next()){
			System.out.println(resultSet.getString("email"));
		}
		resultSet.close();

		if (doesDatabaseExist()) {
			System.out.print("Database environment does exist");
		} else {
			System.out.print("Database environment does NOT exist");
			readfromSqlFile("src/SQLFiles/createDBscript.sql");
		}

	}
}
