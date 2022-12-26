package prob5; // Stack 만드는 문제

public class MainApp03 {

	public static void main(String[] args) {
		try {
			MyStack03<String> stack = new MyStack03(3);
			stack.push("Hello");
			stack.push("World");
			stack.push("!!!");
			stack.push("1"); // 실행할때 오류가남
			stack.push(".");

			while (stack.isEmpty() == false) {
				String s = stack.pop();
				// 컴파일때 타입을 지정하기때문
				System.out.println( s );
			}

			System.out.println("======================================");

			stack = new MyStack03(3);
			stack.push("Hello");

			System.out.println(stack.pop());
			System.out.println(stack.pop());
			
		} catch ( MyStackException ex) {
			System.out.println( ex );
		}
	}

}
