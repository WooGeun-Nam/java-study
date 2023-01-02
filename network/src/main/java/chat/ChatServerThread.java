package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;
import java.util.List;

public class ChatServerThread extends Thread {
	private User user;
	private Socket socket;
	List<User> listUser;

	public ChatServerThread(Socket socket, List<User> listUser) {
		this.socket = socket;
		this.listUser = listUser;
	}

	@Override
	public void run() {
		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while (true) {
				String request = br.readLine();
				if (request == null) {
					// 수정필요
					doQuit(pw);
					break;
				}
				// 프로토콜 처리 -> split
				String[] tokens = request.split(":");
				if ("CHECK".equals(tokens[0])) {
					nameCheck(tokens[1], pw);
				} else if ("JOIN".equals(tokens[0])) {
					doJoin(tokens[1], pw);
				} else if ("LIST".equals(tokens[0])) {
					responseUserList(pw);
				} else if ("MESSAGE".equals(tokens[0])) {
					doMessage(tokens[1], pw);
				} else if ("QUIT".equals(tokens[0])) {
					pw.println("");
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

	private void nameCheck(String receiveName, PrintWriter writer) {
		boolean nameCheck = false;
		synchronized (listUser) {
			for (User user : listUser) {
				if (user.getName().equals(receiveName)) {
					nameCheck = true;
				}
			}
		}
		if (!nameCheck) {
			writer.println("PASS");
		} else {
			writer.println("NONPASS");
		}
	}

	private void doJoin(String name, PrintWriter pw) {
		this.user = new User(pw, name);
		String data = user.getName() + "님이 참여하였습니다.";

		broadcast(data);

		/* writer pool에 저장 */
		addWriter(user);
	}

	private void responseUserList(PrintWriter pw) {
		String response = "";
		synchronized (listUser) {
			for (User user : listUser) {
				response += user.getName()+",";
			}
		}
		pw.println(response);
	}
	
	private void doMessage(String message, PrintWriter pw) {
		/* 잘 구현 해 보기 */
		// 디코딩
		byte[] decodedBytes = Base64.getDecoder().decode(message);
		String decodedString = new String(decodedBytes);

		// 사용자 이름과 함께 전송
		String sendMsg = user.getName() + ":" + decodedString;
		broadcast(sendMsg);
	}

	private void doQuit(PrintWriter writer) {
		removeWriter(user);

		String data = user.getName() + "님이 퇴장 하였습니다.";

		broadcast(data);
	}

	private void removeWriter(User user) {
		synchronized (listUser) {
			listUser.remove(user);
		}
	}

	private void addWriter(User user) {
		synchronized (listUser) {
			listUser.add(user);
		}
	}

	private void broadcast(String data) {
		synchronized (listUser) {
			for (User user : listUser) {
				PrintWriter printWriter = (PrintWriter) user.getWriter();
				printWriter.println(data);
				printWriter.flush();
			}
		}
	}

	public void log(String message) {
		System.out.println("[Server][" + getId() + "] " + message);
	}
}
