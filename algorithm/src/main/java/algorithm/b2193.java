package algorithm;

import java.util.Scanner;

public class b2193 {
	
	static long[] save = new long[91];
	public static long dp(int x) {
		if(x <= 2) {
			return 1;
		} else {
			if (save[x] > 0) {
				return save[x];
			}
			save[x] = dp(x-1)+dp(x-2);
			return save[x];
		}
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner( System.in );
		int number = scanner.nextInt();
		long result = dp(number);
		System.out.println(result);
		scanner.close();
	}
}
