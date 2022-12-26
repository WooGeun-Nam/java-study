package prob01;

public class Printer {
//	public void println(int a) {
//		System.out.println(a);
//	}
//	public void println(boolean a) {
//		System.out.println(a);
//	}
//	public void println(double a) {
//		System.out.println(a);
//	}
//	public void println(String a) {
//		System.out.println(a);
//	}
//	
	public <T> void println(T t) {
		System.out.println(t);
	}
	
	public int sum(Integer... nums) {
		int s = 0;
		for(Integer n:nums) {
			s += n;
		}
		return s;
	}
	
	public <T> void println(T... ts) {
		for(T t:ts) {
			System.out.println(t);
		}
	}
}
