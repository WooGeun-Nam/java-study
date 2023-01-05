package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

			while (true) {
				String request = br.readLine();
				if (request == null) {
					// 수정필요
					if (user != null) {
						doQuit(pw);
					}
					break;
				}
				// 프로토콜 처리 -> split
				String[] tokens = request.split("#");
				if ("CHECK".equals(tokens[0])) {
					nameCheck(tokens[1], pw);
				} else if ("JOIN".equals(tokens[0])) {
					doJoin(tokens[1], pw);
				} else if ("LIST".equals(tokens[0])) {
					responseUserList(pw);
				} else if ("MESSAGE".equals(tokens[0])) {
					try {
						doMessage(tokens[1], pw);
					} catch (Exception e) {
						serverLog("ERROR#[UTF]:" + tokens[0]);
					}
				} else if ("QUIT".equals(tokens[0])) {
					pw.println("");
				} else if ("ERROR".equals(tokens[0])) {
					clientLog(tokens[1]);
				} else if ("WHISPER".equals(tokens[0])) {
					System.out.println(request);
					try {
						doWhisper(tokens[1], tokens[2]);
					} catch (Exception e) {
						serverLog("ERROR#[UTF]:" + tokens[0]);
					}
				} else {
					serverLog("ProtocolError:" + tokens[0]);
				}
			}

		} catch (IOException e) {
			serverLog("Error:" + e);
		} finally {
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (IOException e) {
				serverLog("Error:" + e);
			}
		}
	}

	private void doWhisper(String rcvName, String message) throws Exception {

		byte[] decodedBytes = Base64.getDecoder().decode(message);
		String decodedString = null;
		try {
			decodedString = new String(decodedBytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 사용자 이름과 함께 전송
		String sendMsg = user.getName() + ":" + decodedString;
		synchronized (listUser) {
			for (User user : listUser) {
				if (user.getName().equals(rcvName)) {
					PrintWriter printWriter = (PrintWriter) user.getWriter();
					printWriter.println(sendMsg);
				}
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
				response += user.getName() + ",";
			}
		}
		pw.println(response);
	}

	private void doMessage(String message, PrintWriter pw) throws Exception {
		/* 잘 구현 해 보기 */
		// 디코딩
		byte[] decodedBytes = Base64.getDecoder().decode(message);
		String decodedString = new String(decodedBytes, "utf-8");
		// 사용자 이름과 함께 전송
		String sendMsg = user.getName() + ":" + decodedString;

		// String sendMsg = Base64.getEncoder().encodeToString((user.getName() +
		// ":").getBytes()) + message;
		broadcast(sendMsg);
	}

	private void doQuit(PrintWriter writer) {
		String name = this.user.getName();
		removeWriter(user);

		String data = name + "님이 퇴장 하였습니다.";

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

	public void clientLog(String message) {
		System.out.println("[Client][" + user.getName() + "]" + message);
	}

	public void serverLog(String message) {
		System.out.println("[Server][" + user.getName() + "]" + message);
	}
}
