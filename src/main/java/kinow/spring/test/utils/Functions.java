package kinow.spring.test.utils;

public class Functions {
	public static void printCallers() {
		StackTraceElement [] ss = Thread.currentThread().getStackTrace();
		for (StackTraceElement s : ss) {
			System.out.println("[caller] " + s);
		}
	}
}
