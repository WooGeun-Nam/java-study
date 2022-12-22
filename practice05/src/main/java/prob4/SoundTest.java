package prob4; // 인터페이스 구현

public class SoundTest {

	public static void main(String[] args) {
		printSound( new Cat() );
		printSound( new Dog() );
		printSound( new Sparrow() );
		printSound( new Duck() );
	}
	
	private static void printSound( Soundable soundable ) {
		System.out.println( soundable.sound() );
	}
}