package prob1;

public class Sort {
	
	public static void main(String[] arg) {
	
		int array[] = { 5, 9, 3, 8, 60, 20, 1 };
		int count =  array.length;
		
		System.out.println( "Before sort." );
		
		for (int i = 0; i < count; i++) {
			System.out.print( array[ i ] + " " );
		}
		
		//
		// 정렬 알고리즘이 적용된 코드를 여기에 작성합니다.
		while(true) {
			boolean sortCheck = true;
			for (int i=0; i<array.length-1; i++) {
				if(array[i]<array[i+1]) {
					int save = array[i];
					array[i] = array[i+1];
					array[i+1] = save;
					sortCheck = false;
				}
			}
			if(sortCheck) {
				break;
			}
		}

		//

		
		// 결과 출력
		System.out.println( "\nAfter Sort." );
		
		for (int i = 0; i < count; i++) {
			System.out.print(array[i] + " ");
		}		
	}
}