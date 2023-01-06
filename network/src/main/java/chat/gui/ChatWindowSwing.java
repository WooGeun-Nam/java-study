package chat.gui;

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
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;



public class ChatWindowSwing {

	private JFrame frame;
	private JPanel pannel;
	private JPanel pannel2;
	private JPanel pannel3;
	private JButton buttonSend;
	private JTextField textField;
	private JTextArea textArea;
	private JList<String> userList;
	private PrintWriter pw;
	private BufferedReader br;
    private JTextPane textPane;

	public ChatWindowSwing(String name, PrintWriter pw, BufferedReader br) {
		frame = new JFrame(name);
		pannel = new JPanel();
		pannel2 = new JPanel();
		pannel3 = new JPanel();
		buttonSend = new JButton("전송");
		textField = new JTextField();
		textArea = new JTextArea(30, 70);
		userList = new JList<String>();
		textPane = new JTextPane();
		
		textPane.setSize(15, 30);
		textPane.setBackground(pannel2.getBackground());
        StyledDocument doc = (StyledDocument) textPane.getDocument();
        SimpleAttributeSet boldBlue = new SimpleAttributeSet();
        StyleConstants.setFontFamily(boldBlue, "SansSerif");
        StyleConstants.setFontSize(boldBlue, 14);
        StyleConstants.setBold(boldBlue, true);
        StyleConstants.setForeground(boldBlue, Color.BLACK);
        try {
			doc.insertString(doc.getLength(), "도움말은 \" /? , / \" 입니다.", boldBlue);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		this.pw = pw;
		this.br = br;
	}

	public void show() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
                   e.printStackTrace();
        } // 크로스 플랫폼에서 출력 이상하게 나오는 문제 해결 코드, 삭제하지 마세요
		
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
		pannel2.add(BorderLayout.EAST, userList);
		frame.add(BorderLayout.CENTER, pannel2);
		
		// Noty
		pannel3.add(textPane);
		pannel3.setSize(15, 30);
		frame.add(BorderLayout.NORTH, pannel3);
		textPane.setEditable(false);
		
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
				+ "[리스트에서 대상선택후] /w : 귓속말 예) /w안녕 ");
	}
	
	private void sendMessage() throws Exception {
		String message = textField.getText();

		if(message.equals("")) {
			//공백이면 작동 x
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
