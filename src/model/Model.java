package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ibatis.common.jdbc.ScriptRunner;

import config.Configurations;

public abstract class Model {

	private static Configurations cf = new Configurations();

	private static String host = cf.getHost();
	private static String port = cf.getPort();
	private static String dbName = cf.getDbname();
	private static String sqlUrl = "jdbc:mysql://" + host + ":" + port;
	private static String sqlUser = cf.getUsername();
	private static String sqlPasswd = cf.getPassword();

	private Statement stmt;
	protected Connection conn = null;
	protected PreparedStatement sqlStatement;
	protected ResultSet rs;

	public boolean doesDatabaseExist() throws SQLException {

		getConnection(true);
		ResultSet resultSet = getConn().getMetaData().getCatalogs();

		while (resultSet.next()) {
			String databaseName = resultSet.getString(1);

			if(databaseName.equals(dbName)){
				return true;
			}
		}
		resultSet.close();
		return false;
	}

	protected void readfromSqlFile(String filepath) throws IOException, SQLException {
		getConnection(true);
		ScriptRunner runner = new ScriptRunner(getConn(), false, false);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filepath));
		runner.runScript(reader);
		reader.close();
		conn.close();
	}

	public PreparedStatement doQuery(String sql) {
		try {
			getConnection(false);
			sqlStatement = getConn().prepareStatement(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sqlStatement;
	}

	public void getConnection(Boolean init) throws SQLException {
		if(init) {
			setConn(DriverManager.getConnection(sqlUrl, sqlUser, sqlPasswd));
		}else{
			setConn(DriverManager.getConnection(sqlUrl+"/"+dbName, sqlUser, sqlPasswd));
		}
	}

	public Statement getStmt() {
		return stmt;
	}

	public Connection getConn() {
		return conn;
	}

	private void setConn(Connection conn) {
		this.conn = conn;
	}
}