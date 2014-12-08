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

	/**
	 * Checks if the database exists
	 * @return doesDatabaseExist
	 */
	public boolean doesDatabaseExist() {
		try {
			getConnection(true);
			rs = getConn().getMetaData().getCatalogs();

			while (rs.next()) {
				String databaseName = rs.getString(1);

				if(databaseName.equals(dbName)){
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * Runs the SQL script for creating database
	 * @param filepath
	 */
	protected void readfromSqlFile(String filepath) {
		InputStreamReader reader = null;
		try {
			getConnection(true);
			ScriptRunner runner = new ScriptRunner(getConn(), false, false);
			reader = new InputStreamReader(new FileInputStream(filepath));
			runner.runScript(reader);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				conn.close();
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates a preparedstatement on a user specified query (Can be used when query is too advanced for the query builder to create)
	 * @param sql
	 * @return PreparedStatement
	 */
	public PreparedStatement doQuery(String sql) {
		try {
			getConnection(false);
			sqlStatement = getConn().prepareStatement(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sqlStatement;
	}

	/**
	 * Creates connection to database
	 * @param init
	 */
	public void getConnection(Boolean init) {
		try {
			if(init) {
				setConn(DriverManager.getConnection(sqlUrl, sqlUser, sqlPasswd));
			}else{
				setConn(DriverManager.getConnection(sqlUrl+"/"+dbName, sqlUser, sqlPasswd));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return stmt
	 */
	public Statement getStmt() {
		return stmt;
	}

	/**
	 * @return conn
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * @param conn
	 */
	private void setConn(Connection conn) {
		this.conn = conn;
	}
}