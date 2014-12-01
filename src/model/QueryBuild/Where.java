package model.QueryBuild;

public class Where {

	private QueryBuilder qb;
	private String whereKey;
	private String whereOperator;
	private String whereValue;

	protected String getWhereKey() {
		return whereKey;
	}
	private void setWhereKey(String whereKey) {
		this.whereKey = whereKey;
	}
	protected String getWhereOperator() {
		return whereOperator;
	}
	private void setWhereOperator(String whereOperator) {
		this.whereOperator = whereOperator;
	}
	protected String getWhereValue() {
		return whereValue;
	}
	private void setWhereValue(String whereValue) {
		this.whereValue = whereValue;
	}
	protected QueryBuilder getQueryBuilder(){
		return qb;
	}

	public Where(QueryBuilder queryBuilder){
		qb = queryBuilder;
	}

	private Where(){

	}

	 public Execute all(){
		return new Execute(getQueryBuilder(), true);
	 }

	 public Execute where(String key, String operator, String value){

		 Where where = new Where();
		 where.setWhereKey(key);
		 where.setWhereOperator(operator);
		 where.setWhereValue(value);

		 return new Execute(getQueryBuilder(), where);
	 }
}