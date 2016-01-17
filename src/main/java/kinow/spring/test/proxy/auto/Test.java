package kinow.spring.test.proxy.auto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

	public void a1() {
		a2();
	}
	public void a2(){
		a3();
	}
	public void a3(){
		StackTraceElement[] ss = Thread.currentThread().getStackTrace();
		for (StackTraceElement s : ss) {
			System.out.println(s);
		}
			
	}
	
	public void vargs(String ...args) {
		List<String> list = new ArrayList<String>(Arrays.asList(args));
		for (String s: args) {
			System.out.println(s);
		}
	}
	
	
	public static void main(String[] args) {
		Test t = new Test();
		/*
		List<String> list = new ArrayList<String>(Arrays.asList("1", "10", "100"));
		t.vargs(list.toArray(new String[list.size()]));
		*/
		t.a1();
		
	}

}
