package prob04;
public class Prob04 {

	public static void main(String[] args) {
		char[] c1 = reverse( "Hello World" );
		printCharArray( c1 );
		
		char[] c2 = reverse( "Java Programming!" );
		printCharArray( c2 );
	}
	
	public static char[] reverse(String str) {
		/* 코드를 완성합니다 */
		char[] result = str.toCharArray();
		// result 뒤집어서 넣기 2분의 1로 쪼개서 뒤집기
		int len = result.length-1;
		for(int i=0; i<result.length/2; i++) {
			char save = result[0+i];
			result[0+i] = result[len-i];
			result[len-i] = save;
		}
		
		return result;
	}

	public static void printCharArray(char[] array){
		/* 코드를 완성합니다 */
		System.out.println( array );
	}
}