package model.QueryBuild;

import java.sql.SQLException;

import model.Model;

import com.sun.rowset.CachedRowSetImpl;

public class Execute extends Model {

	private final String SELECT = "SELECT ";
	private final String FROM = " FROM ";
	private final String WHERE = " WHERE ";
	private final String INSERTINTO = "INSERT INTO ";
	private final String UPDATE = "UPDATE ";
	private final String VALUES = " VALUES ";

	private QueryBuilder queryBuilder;
	private Where where;
	private Values values;
	private boolean getAll = false;

	protected QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	protected Where getWhere() {
		return where;
	}

	protected Values getValues() {
		return values;
	}

	protected boolean isGetAll() {
		return getAll;
	}

	public Execute(QueryBuilder queryBuilder, boolean getAll) {
		this.queryBuilder = queryBuilder;
		this.getAll = getAll;
	}

	public Execute(QueryBuilder queryBuilder, Where where) {
		this.queryBuilder = queryBuilder;
		this.where = where;
	}

	public Execute(QueryBuilder queryBuilder, Values values) {
		this.queryBuilder = queryBuilder;
		this.values = values;
	}

	public CachedRowSetImpl executeQuery() throws SQLException {
		String sql = "";
		CachedRowSetImpl crs = new CachedRowSetImpl();

		if (isGetAll()) {
			sql = SELECT + getQueryBuilder().getSelectValue() + FROM + getQueryBuilder().getTableName() + ";";
			try {
				getConnection(false);
				sqlStatement = getConn().prepareStatement(sql);
				crs.populate(sqlStatement.executeQuery());

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					sqlStatement.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			sql = SELECT + getQueryBuilder().getSelectValue() +
					FROM + getQueryBuilder().getTableName() +
					WHERE + getWhere().getWhereKey() + " " + getWhere().getWhereOperator() + " ?;";
			try {
				getConnection(false);
				sqlStatement = getConn().prepareStatement(sql);
				sqlStatement.setString(1, getWhere().getWhereValue());
				crs.populate(sqlStatement.executeQuery());

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					sqlStatement.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return crs;
	}

	@SuppressWarnings("unused")
	public void execute() throws SQLException {
		String sql = null;

		if (getQueryBuilder().isUpdate()) {
			sql = UPDATE + getQueryBuilder().getTableName() + " SET " + getQueryBuilder().getFields() + "" + WHERE + getWhere().getWhereKey() + " " + getWhere().getWhereOperator() + " ?;";
			try {
				getConnection(false);
				sqlStatement = getConn().prepareStatement(sql);
				sqlStatement.setString(1, getWhere().getWhereValue());

				sqlStatement.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				sqlStatement.close();
				conn.close();
			}
		} else {
			sql = INSERTINTO + getQueryBuilder().getTableName() + " (" + getQueryBuilder().getFields() + ")" + VALUES + "(";
			StringBuilder sb = new StringBuilder();
			for (String n : getValues().getValues()) {
				if (sb.length() > 0) sb.append(',');
				sb.append(" ?");
			}
			sql += sb.toString();
			sql += " );";
			try {
				getConnection(false);
				sqlStatement = getConn().prepareStatement(sql);
				int x = 0;
				for (int i = 0; i < getValues().getValues().length; i++) {
					x = i;
					sqlStatement.setString(x+1, getValues().getValues()[i]);
				}

				sqlStatement.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				sqlStatement.close();
				conn.close();
			}
		}
	}
}
