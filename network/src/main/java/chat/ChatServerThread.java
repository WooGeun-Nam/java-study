package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Base64;
import java.util.List;

public class ChatServerThread extends Thread {
	private String name;
	private Socket socket;
	List<Writer> listWriters;

	public ChatServerThread(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {
		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while (true) {
				String request = br.readLine();
				if (request.equals(null)) {
					// 수정필요
					doQuit(pw);
					break;
				}
				// 프로토콜 처리 -> split
				String[] tokens = request.split(":");
				if ("JOIN".equals(tokens[0])) {
					doJoin(tokens[1], pw);
					// ~~~ 메소드 조건들 추가
				} else if ("MESSAGE".equals(tokens[0])) {
					doMessage(tokens[1], pw);
				} else if ("QUIT".equals(tokens[0])) {
					doQuit(pw);
				} else {
					ChatServer.log("알수없는 메소드");
				}
			}

		} catch (IOException e) {
			log("error:" + e);
		} finally {
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void doJoin(String name, PrintWriter writer) {
		this.name = name;

		String data = name + "님이 참여하였습니다." + "\n>>";
		broadcast(data, writer);

		/* writer pool에 저장 */
		addWriter(writer);
		
		System.out.println(name+":JOIN:OK");
		writer.println("JOIN:OK\n>>");
		// ack
		// writer.println("join:ok");
		// writer.flush();
	}

	private void doMessage(String message, PrintWriter pw) {
		/* 잘 구현 해 보기 */
		// 디코딩
		byte[] decodedBytes = Base64.getDecoder().decode(message);
		String decodedString = new String(decodedBytes);

		// 사용자 이름과 함께 전송
		String sendMsg = name + ":" + decodedString + "\n>>";
		broadcast(sendMsg, pw);
	}

	private void doQuit(PrintWriter writer) {
		removeWriter(writer);
		
		String data = name + "님이 퇴장 하였습니다." + "\n>>";
		broadcast(data, writer);

		writer.println();
	}

	private void removeWriter(Writer writer) {
		/* 잘 구현 해보기 */
		synchronized (listWriters) {
			listWriters.remove(writer);
		}
	}

	private void addWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.add(writer);
		}
	}

	private void broadcast(String data, Writer pw) {

		synchronized (listWriters) {
			for (Writer writer : listWriters) {
				PrintWriter printWriter = (PrintWriter) writer;
				if (pw != printWriter) {
					printWriter.println(data);
					printWriter.flush();
				}
			}
		}
	}

	public void log(String message) {
		System.out.println("[Server][" + getId() + "] " + message);
	}
}
