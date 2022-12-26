package com.douzone.paint.collection; // 배열 늘리는 메소드

public class MyStack<T> {
	// vector -> 배열을 늘리기 좋음
	private T[] buffer;
	private static int index;

	@SuppressWarnings("unchecked")
	public MyStack(int i) {
//		buffer = (T[])new Object[i];
		buffer = (T[])new Object[i];
		index = 0;
	}

	public boolean isEmpty() {
		if (index <= 0) return true;
		return false;
	}

	public void push(T string) {
		reSizing();
		buffer[index] = string;
		index++;
	}
	
	@SuppressWarnings("unchecked")
	public void reSizing() {
		if(buffer.length<=index) {
			T[] save = (T[])new Object[index];
			save = buffer;
			buffer = (T[])new Object[index*2];
			for(int i=0; i<save.length; i++) {
				buffer[i] = save[i];
			}
		}
	}

	public T pop() {
		if (index<1) throw new MyStackException("stack is empty");
		return buffer[--index];
	}
	
}