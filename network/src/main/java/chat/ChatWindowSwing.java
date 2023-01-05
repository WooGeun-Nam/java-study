package chat;

import java.awt.BorderLayout;
import java.awt.Color;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatWindowSwing {

	private JFrame frame;
	private JPanel pannel;
	private JPanel pannel2;
	private JButton buttonSend;
	private JTextField textField;
	private JTextArea textArea;
	private JList<String> userList;
	private PrintWriter pw;
	private BufferedReader br;

	public ChatWindowSwing(String name, PrintWriter pw, BufferedReader br) {
		frame = new JFrame(name);
		pannel = new JPanel();
		pannel2 = new JPanel();
		buttonSend = new JButton("전송");
		textField = new JTextField();
		textArea = new JTextArea(30, 70);
		userList = new JList<String>();
		this.pw = pw;
		this.br = br;
	}

	public void show() {
		// 사용자 받아오기
		listRefresh();

		// Button
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
		textField.setColumns(70);
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

		// TextArea
		textArea.setEditable(false);
		pannel2.add(BorderLayout.CENTER, textArea);
		// userArea.setEditable(false);
		pannel2.add(BorderLayout.EAST, userList);
		frame.add(BorderLayout.CENTER, pannel2);

		// Frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				finish();
			}
		});
		frame.setVisible(true);
		frame.pack();

		updateTextArea("접속을 환영합니다 도움말은 \"/?\" 입니다.");
		new ChatClientThread(br).start();
	}

	private void finish() {
		// quit protocol 구현
		pw.println("QUIT"); // Thread먼저 정리
		System.exit(0);
	}

	private void sendMessage() throws Exception {
		String message = textField.getText();

		// 명령어 인지 확인
		String bash = message.substring(0, 1);
		String cmd = null;
		String msg = null;
		if (message.equals("/")) {
			JOptionPane.showMessageDialog(frame, "잘못된 명령어 입니다.\n");
		} else if (bash.equals("/")) {
			cmd = message.substring(1, 2);
			msg = message.substring(2);
			if (cmd.equals("?")) {
				JOptionPane.showMessageDialog(frame, "/c : 채팅기록 지우기, [리스트에서 대상선택후] /w : 귓속말, /q : 나가기\n");
				// textArea.append("/c : 채팅기록 지우기, [리스트에서 대상선택후] /w : 귓속말, /q : 나가기\n");
			} else if (cmd.equals("c")) {
				textArea.selectAll();
				textArea.replaceSelection("");
			} else if (cmd.equals("w") && message.length() > 2) {
				if (userList.getSelectedValue() != null) {
					String encodedString = Base64.getEncoder().encodeToString(msg.getBytes("utf-8"));
					String response = "WHISPER#" + userList.getSelectedValue() + "#" + encodedString;
					pw.println(response);
				} else {
					JOptionPane.showMessageDialog(frame, "대상자를 선택해 주세요.\n");
				}
			} else if (cmd.equals("q")) {
				finish();
			} else {
				JOptionPane.showMessageDialog(frame, "잘못된 명령어 입니다.\n");
			}
		} else if (!message.equals("")) {
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
		pw.println("LIST");
		try {
			String request = br.readLine();
			if (request != null) {
				users = request.split(",");
			}
		} catch (IOException e) {
			System.out.println("사용자데이터를 불러오지 못함");
			pw.println("ERROR#[사용자호출]" + e);
		}
		userList.setListData(users);
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
