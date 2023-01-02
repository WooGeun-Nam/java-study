package chat;

import java.io.BufferedReader;
import java.io.IOException;

public class ChatClientThread extends Thread {
	private BufferedReader bufferedReader;

	public ChatClientThread(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	@Override
	public void run() {
		/* reader를 통해 읽은 데이터 콘솔에 출력하기 (message 처리) */
		try {
			while (true) {
				String data = bufferedReader.readLine();
				if (data.equals("")) {
					break;
				} else {
					System.out.println(data);
				}
			}
		} catch (IOException e) {
			// 소켓이 안닫힌상태에서 종료되서 여기서 예외발생 해결해야함
			System.out.println("error:" + e);
		}
	}
}
