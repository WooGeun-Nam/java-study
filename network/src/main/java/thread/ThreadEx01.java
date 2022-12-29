package thread;

public class ThreadEx01 {

	public static void main(String[] args) {
		new DigitThread().start();
		for(char c = 'a'; c<='z'; c++) {
			System.out.print(c);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
