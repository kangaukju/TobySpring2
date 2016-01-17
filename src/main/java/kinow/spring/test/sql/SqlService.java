package kinow.spring.test.sql;

import kinow.spring.test.exception.SqlRetrievalFauilreException;

public interface SqlService {
	String getSql(String key) throws SqlRetrievalFauilreException;
}
