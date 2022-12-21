package mypackage;

public class Goods2 {
	public String name; // 모든 접근 허용
	protected int price; // 같은 패키지 & 자식 클래스에서 접근 허용**
	int countStock; // 디폴트, 같은 패키지에서 접근 허용
	private int countSold; // 클래스 내부에서만 접근 허용
	
	public void m() {
		countSold = 50;
	}
}
