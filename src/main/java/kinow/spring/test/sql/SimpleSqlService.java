package kinow.spring.test.sql;

import java.util.Map;

import kinow.spring.test.exception.SqlRetrievalFauilreException;

public class SimpleSqlService implements SqlService {
	private Map<String, String> sqlMap;
	
	
	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}

	@Override
	public String getSql(String key) throws SqlRetrievalFauilreException {
		String sql = sqlMap.get(key);
		if (sql == null) {
			throw new SqlRetrievalFauilreException(key + "에 대한 SQL을 찾을 수 없습니다.");
		}
		return sql;
	}

}
