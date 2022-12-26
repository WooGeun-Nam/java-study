package prob5; // 배열 늘리는 메소드

public class MyStack02 {
	// vector -> 배열을 늘리기 좋음
	private Object[] buffer;
	private static int index;

	public MyStack02(int i) {
		buffer = new Object[i];
		index = 0;
	}

	public boolean isEmpty() {
		if (index <= 0) return true;
		return false;
	}

	public void push(Object string) {
		reSizing();
		buffer[index] = string;
		index++;
	}
	
	public void reSizing() {
		if(buffer.length<=index) {
			Object[] save = new Object[index];
			save = buffer;
			buffer = new Object[index*2];
			for(int i=0; i<save.length; i++) {
				buffer[i] = save[i];
			}
		}
	}

	public Object pop() {
		if (index<1) throw new MyStackException("stack is empty");
		return buffer[--index];
	}
	
}