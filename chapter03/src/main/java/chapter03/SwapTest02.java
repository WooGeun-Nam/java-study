package chapter03;

public class SwapTest02 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a = 10;
		int b = 20;
		
		
		System.out.println("a:" +a+", b:"+b);
		
		swap(a,b);
		
		System.out.println("a:" +a+", b:"+b);
		
	}
	
	public static void swap(int m, int n) {
		int save = m;
		m = n;
		n = save;
	}
}
