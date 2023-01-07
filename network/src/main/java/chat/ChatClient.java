package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;

public class ChatClient {

	public static void main(String[] args) {
		Socket socket = null;
		String name = null;
		Scanner scanner = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		try {
			// 1. 키보드 연결
			scanner = new Scanner(System.in);

			// 2. 소켓 생성
			socket = new Socket();

			// 3. 연결
			socket.connect(new InetSocketAddress(ChatServer.SERVER_IP, ChatServer.PORT));
			System.out.println("[CONNECT:ACCEPT]");

			// 4. reader/writer
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

			// 4. join protocol
			while (true) {
				System.out.print("닉네임>>");
				name = scanner.nextLine();
				if (!name.isEmpty() || name.contains("#")) {
					pw.println("CHECK#" + name);
					String data = br.readLine();
					if("PASS".equals(data)) {
						pw.println("JOIN#" + name);
						break;
					} else {
						System.out.println("중복되는 대화명이 존재합니다.\n");
						continue;
					}
				}
				System.out.println("정확하지 않은 입력입니다. 또는 불가능한 문자(#)가 포함되었 습니다.\n");
			}
			
			System.out.println("[JOIN:OK]");
			// 6. ChatClientReceiveThread 시작
			new ChatClientThread(br, pw).start();
			System.out.println("환영합니다. 퇴장명령어는 quit 입니다.");
			// 키보드 입력처리
			while (true) {
				System.out.print(">>");
				String line = scanner.nextLine();
				if (line == "") {
					System.out.println("잘못 입력하셨습니다.");
					continue;
				}
				if ("quit".equals(line)) {
					String msg = "QUIT";
					pw.println(msg);
					break;
				} else {
					// 메시지 처리
					// 인코딩
					String encodedString = Base64.getEncoder().encodeToString(line.getBytes("utf-8"));
					// 프로토콜과 함께 담아서 전송
					String msg = "MESSAGE#" + encodedString;
					pw.println(msg);
				}
			}
		} catch (IOException e) {
			pw.println("ERROR#[Main]" + e);
		} finally {
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
				if (scanner != null) {
					scanner.close();
				}
			} catch (IOException e) {
				pw.println("ERROR#[Main]" + e);
			}
		}
	}
}
