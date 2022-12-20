package prob5;

public class Prob5 {

	public static void main(String[] args) {
		
		for(int i=1; i<1000 ; i++) {
			String number = String.valueOf(i);
			int count = 0;
			for(int j=0; j<number.length(); j++) {
				if(number.charAt(j)=='3' || number.charAt(j)=='6' || number.charAt(j)=='9') {
					count += 1;
				}
			}
			if (count>0) {
				System.out.print(number+" ");
				for(int j=0; j<count; j++) {
					System.out.print("짝");
				}
				System.out.println(" ");
			}
			
		}
	}
}
