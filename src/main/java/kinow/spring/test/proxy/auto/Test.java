package kinow.spring.test.proxy.auto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

	public void vargs(String ...args) {
		List<String> list = new ArrayList<String>(Arrays.asList(args));
		for (String s: args) {
			System.out.println(s);
		}
	}
	
	
	public static void main(String[] args) {
		Test t = new Test();
		
		List<String> list = new ArrayList<String>(Arrays.asList("1", "10", "100"));
		t.vargs(list.toArray(new String[list.size()]));
		
	}

}
