package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Base64;

import javax.swing.JOptionPane;

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
					System.out.println("서버와 연결이 종료되었습니다.");
					System.out.println("잠시후 시스템이 종료됩니다.");
					Thread.sleep(5000);
					System.exit(0);
				} else if (data.equals("")) {
					break;
				} else {
					String[] tokens = data.split("#");
					if (tokens[0].equals("DECODE")) {
						byte[] decodedBytes = Base64.getDecoder().decode(tokens[2]);
						String decodedString = new String(decodedBytes, "utf-8");

						data = tokens[1] + ":" + decodedString;
					} else if (tokens[0].equals("SYS")) {
						System.out.println("시스템메세지:" + tokens[1]);
						continue;
					} else if (tokens[0].equals("EXIT")) {
						break;
					} else if (tokens[0].equals("NOTI")) {
						System.out.println("공지사항:" + tokens[1]);
						continue;
					}
					System.out.println(data);
				}
			}
		} catch (InterruptedException e) {
			System.out.println("ERROR#[ThreadSleep]" + e);
		} catch (IOException e) {
			System.out.println("Error:" + e);
		} finally {
			System.exit(0);
		}
	}
}
