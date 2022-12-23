package algorithm;

import java.util.Scanner;

public class b10610 {
	public static int drainCheck(String number) {
		for(int i=0; i<number.length(); i++) {
			if(number.charAt(i)=='0') {
				return 1;
			}
		}
		return -1;
	}
	
	public static int sort(String number) {
		char[] numberArray = number.toCharArray();
		for(int i=0; i<number.length()-1; i++) {
			int index = 0;
			int max = number.charAt(i);
			for(int j=i+1; j<number.length(); j++) {
				if(max < number.charAt(j)) {
					
				}
			}
		}
		return 0;
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner( System.in );
		int result;
		
		String number = Integer.toString(scanner.nextInt());
		
		result = drainCheck(number);
		
		if (result > 0) {
			result = sort(number);
		}
		
		System.out.println(result);
		
	}

}
