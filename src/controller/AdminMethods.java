package controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import model.QueryBuild.QueryBuilder;

public class AdminMethods {

	private QueryBuilder qb = new QueryBuilder();
	private Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	private ResultSet rs;

	public Vector<Vector<Object>> userTable() {
		try {
			String[] keys = {"username", "active"};
			rs = qb.selectFrom(keys, "users").all().ExecuteQuery();

			data.clear();

			while(rs.next()) {
				Vector<Object> row = new Vector<Object>();

				ResultSetMetaData metadata = rs.getMetaData();
				int numberOfColumns = metadata.getColumnCount();

				for(int i = 1; i <= numberOfColumns; i++) {
					row.addElement(rs.getObject(i));
				}
				data.addElement(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public boolean AddUser(String username, String password) {
		boolean succes = false;
		try {
			if(qb.selectFrom("users").where("username", "=", username).ExecuteQuery().next()) {
				succes = false;
			} else {
				String[] fields = {"username", "password"};
				String[] values = {username, password};
				qb.insertInto("users", fields).values(values).Execute();
				succes = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return succes;
	}

	public void DeleteUser(String username) {

		int active = 2;
		
		try {
			String[] values1 = {"active"};

			rs = qb.selectFrom(values1, "users").where("username", "=", username).ExecuteQuery();
			
			if(rs.next()) {
				active = rs.getInt("active");
				
				if(active == 1) {
					active = 0;
				} else if (active == 0){
					active = 1;
				}
			}
			if(active!=2) {
				String[] fields = {"active"};
				String[] values2 = {String.valueOf(active)};

				qb.update("users", fields, values2).where("username", "=", username).Execute();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
