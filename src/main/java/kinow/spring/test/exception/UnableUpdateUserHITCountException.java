package kinow.spring.test.exception;

import org.springframework.dao.DataAccessException;

public class UnableUpdateUserHITCountException extends DataAccessException {

	public UnableUpdateUserHITCountException(String msg) {
		super(msg);
	}

	public UnableUpdateUserHITCountException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
