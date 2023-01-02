package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientApp {

	public static void main(String[] args) {
		Socket socket = null;
		String name = null;
		Scanner scanner = new Scanner(System.in);

		try {

			// 1. create socket
			socket = new Socket();

			// 2. connect to server
			socket.connect(new InetSocketAddress(ChatServer.SERVER_IP, ChatServer.PORT));
			log("connected");

			// 3. get iostream
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

			// 4. join protocol
			String line = null;
			while (true) {
				System.out.print("닉네임>>");
				name = scanner.nextLine();
				if (!name.isEmpty()) {
					pw.println("JOIN:" + name);
					pw.flush();
					break;
				}
				System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
			}
			
			new ChatWindowSwing(name,pw,br).show();
			
			while(true) {
				if(socket.isClosed()) {
					break;
				}
			}
			
		} catch (IOException e) {
			log("error:" + e);
		} finally {
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
				if (scanner != null) {
					scanner.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// new ChatWindow(name).show();
	}

	private static void log(String message) {
		System.out.println("[Client] " + message);
	}
}
