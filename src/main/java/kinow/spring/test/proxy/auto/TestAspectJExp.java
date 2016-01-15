package kinow.spring.test.proxy.auto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestAspectJExp {
	String exp;
	boolean matched;
	Class<?> classClazz;
	String method;
	List<Class<?>> args;
	
	public TestAspectJExp(String exp, boolean matched, Class<?> classClazz, String method, Class<?>...args) {
		super();
		this.exp = exp;
		this.matched = matched;
		this.classClazz = classClazz;
		this.method = method;
		this.args = new ArrayList<Class<?>>(Arrays.asList(args));
	}

	public String getExp() {
		return exp;
	}

	public boolean isMatched() {
		return matched;
	}

	public Class<?> getClassClazz() {
		return classClazz;
	}

	public String getMethod() {
		return method;
	}

	public List<Class<?>> getArgs() {
		return args;
	}
}
