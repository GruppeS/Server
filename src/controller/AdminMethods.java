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
			String[] keys = {"email", "active"};
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
}
