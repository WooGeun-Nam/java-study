package chat;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

import javax.swing.JOptionPane;

public class ChatWindow {

	private Frame frame;
	private Panel pannel;
	private Panel pannel2;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;
	private List userList;
	private PrintWriter pw;
	private BufferedReader br;
	private TextField textPane;

	public ChatWindow(String name, PrintWriter pw, BufferedReader br) {
		frame = new Frame(name);
		pannel = new Panel();
		pannel2 = new Panel();
		;
		buttonSend = new Button("전송");
		textField = new TextField();
		textArea = new TextArea(30, 70);
		userList = new List(31);
		textPane = new TextField();
		textPane.setSize(60, 30);
		textPane.setText("도움말은 \" /? , / \" 입니다.");
		textPane.setEditable(false);
		userList.setSize(10, 70);

		textPane.setForeground(Color.BLACK);
		textPane.setFont(new Font("맑은고딕", Font.BOLD, 14));

		this.pw = pw;
		this.br = br;
	}

	public void show() {
		// 사용자 받아오기
		listRefresh();

		// Button
		buttonSend.setSize(10, 20);
		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.WHITE);
		// 옵저버 패턴
		buttonSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					sendMessage();
				} catch (Exception e) {
					pw.println("ERROR#[UTF]" + e);
				}
			}
		});

		// Textfield
		textField.setColumns(80);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char keyCode = e.getKeyChar();
				if (keyCode == KeyEvent.VK_ENTER) {
					try {
						sendMessage();
					} catch (Exception e1) {
						pw.println("ERROR#[UTF]" + e);
					}
				}
			}
		});

		// Pannel
		pannel.setBackground(Color.LIGHT_GRAY);
		pannel.add(textField);
		pannel.add(buttonSend);
		frame.add(BorderLayout.SOUTH, pannel);

		// Pannel2
		textArea.setEditable(false);
		pannel2.add(BorderLayout.CENTER, textArea);
		pannel2.add(BorderLayout.EAST, userList);
		frame.add(BorderLayout.CENTER, pannel2);

		// Noti
		frame.add(BorderLayout.NORTH, textPane);

		// Frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				finish();
			}
		});

		userList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char keyCode = e.getKeyChar();
				if (keyCode == KeyEvent.VK_ENTER) {
					try {
						sendMessage();
					} catch (Exception e1) {
						pw.println("ERROR#[UTF]" + e);
					}
				}
			}
		});

		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char keyCode = e.getKeyChar();
				if (keyCode == KeyEvent.VK_ENTER) {
					try {
						sendMessage();
					} catch (Exception e1) {
						pw.println("ERROR#[UTF]" + e);
					}
				}
			}
		});

		frame.setVisible(true);
		frame.pack();

		new ChatClientThread(br).start();
	}

	private void finish() {
		// quit protocol 구현
		pw.println("QUIT"); // Thread먼저 정리
		System.exit(0);
	}

	private void helpDialog() {
		JOptionPane.showMessageDialog(frame, "/c : 채팅기록 지우기, /q : 나가기\n" 
	                                   + "리스트에서 대상선택 + /w[메세지] : 귓속말 예) /w안녕\n"
									   + "<관리자 전용 명령어> \n리스트에서 대상선택 + /k, /n[메세지] : 채팅창 공지");
	}

	private void sendMessage() throws Exception {
		String message = textField.getText();

		if (message.equals("")) {
			// 공백이면 작동 x
			return;
		}

		// 명령어 인지 확인
		String bash = message.substring(0, 1);
		String cmd = null;
		String msg = null;
		if (message.equals("/")) {
			helpDialog();
		} else if (bash.equals("/")) {
			cmd = message.substring(1, 2);
			msg = message.substring(2);
			if (cmd.equals("?")) {
				helpDialog();
			} else if (cmd.equals("c")) {
				textArea.setText("");
			} else if (cmd.equals("w") && message.length() > 2) {
				if (userList.getSelectedItem() != null) {
					String encodedString = Base64.getEncoder().encodeToString(msg.getBytes("utf-8"));
					String response = "WHISPER#" + userList.getSelectedItem() + "#" + encodedString;
					pw.println(response);
				} else {
					JOptionPane.showMessageDialog(frame, "대상자를 선택해 주세요.");
				}
			} else if (cmd.equals("q")) {
				finish();
			} else if (cmd.equals("k")) {
				if (userList.getSelectedItem() != null) {
					pw.println("KICK#" + userList.getSelectedItem());
				} else {
					JOptionPane.showMessageDialog(frame, "대상자를 선택해 주세요.");
				}
			} else if (cmd.equals("n")) {
				if (msg != "") {
					pw.println("NOTI#" + msg);
				} else {
					JOptionPane.showMessageDialog(frame, "잘못된 명령어 입니다.");
				}
			} else {
				JOptionPane.showMessageDialog(frame, "잘못된 명령어 입니다.");
			}
		} else {
			// 인코딩
			String encodedString = Base64.getEncoder().encodeToString(message.getBytes("utf-8"));
			// 프로토콜과 함께 담아서 전송
			String response = "MESSAGE#" + encodedString;
			pw.println(response);
		}
		textField.setText("");
		textField.requestFocus();
	}

	private void listRefresh() {
		String[] users = null;
		userList.removeAll();

		pw.println("LIST");
		try {
			String request = br.readLine();
			if (request != null) {
				users = request.split(",");
				for (String name : users) {
					userList.add(name);
				}
			}
		} catch (IOException e) {
			System.out.println("사용자데이터를 불러오지 못함");
			pw.println("ERROR#[사용자호출]" + e);
		}
	}

	private void updateTextArea(String message) {
		textArea.append(message);
		textArea.append("\n");
	}

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
						updateTextArea("서버와 연결이 종료되었습니다.");
						updateTextArea("잠시후 시스템이 종료됩니다.");
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
							JOptionPane.showMessageDialog(frame, tokens[1]);
							continue;
						} else if (tokens[0].equals("EXIT")) {
							finish();
							break;
						} else if (tokens[0].equals("NOTI")) {
							textPane.setText(tokens[1]);
							continue;
						}
						updateTextArea(data);
					}
					listRefresh();
				}
			} catch (InterruptedException e) {
				pw.println("ERROR#[ThreadSleep]" + e);
			} catch (IOException e) {
				pw.println("ERROR#[Thread]" + e);
			}
		}
	}
}
