package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class ChatClientThread extends Thread {
	private Socket socket;
	private BufferedReader bufferedReader;

	public ChatClientThread(Socket socket, BufferedReader bufferedReader) {
		this.socket = socket;
		this.bufferedReader = bufferedReader;
	}

	@Override
	public void run() {
		/* reader를 통해 읽은 데이터 콘솔에 출력하기 (message 처리) */
		try {
			while (true) {
				if (socket.isClosed()) {
					break;
				}
				String data = bufferedReader.readLine();

				if (data == null) {
					System.out.println("closed by cliendt");
					break;
				} else {
					System.out.println(data);
				}
			}
		} catch (IOException e) {
			// 소켓이 안닫힌상태에서 종료되서 여기서 예외발생 해결해야
			// System.out.println("여기서뜬 에러임");
			System.out.println("error:" + e);
		}
	}
}
