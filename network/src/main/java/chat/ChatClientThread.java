package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Base64;

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
				
				if (data == null) {
					break;
				} else if (data.equals("")) {
					break;
				} else {
					String[] tokens = data.split("#");
					if (tokens[0].equals("DECODE")) {
						byte[] decodedBytes = Base64.getDecoder().decode(tokens[2]);
						String decodedString = new String(decodedBytes, "utf-8");

						data = tokens[1] + ":" + decodedString;
					}
					System.out.println(data);
				}
			}
		} catch (IOException e) {
			System.out.println("Error:" + e);
		} finally {
			System.exit(0);
		}
	}
}
