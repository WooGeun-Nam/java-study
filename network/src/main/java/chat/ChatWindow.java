package chat;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
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

public class ChatWindow {

	private Frame frame;
	private Panel pannel;
	private Panel pannel2;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;
	private TextArea userArea;
	private PrintWriter pw;
	private BufferedReader br;

	public ChatWindow(String name, PrintWriter pw, BufferedReader br) {
		frame = new Frame(name);
		pannel = new Panel();
		pannel2 = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		textArea = new TextArea(30, 70);
		userArea = new TextArea(30, 10);
		this.pw = pw;
		this.br = br;
	}

	public void show() {
		// Button
		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.WHITE);
		// 옵저버 패턴
		buttonSend.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent actionEvent ) {
				sendMessage();
			}
		});

		// Textfield
		textField.setColumns(80);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char keyCode = e.getKeyChar();
				if(keyCode == KeyEvent.VK_ENTER) {
					sendMessage();
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
		frame.add(BorderLayout.CENTER, textArea);
		
		userArea.setEditable(false);
		frame.add(BorderLayout.EAST, userArea);

		// Frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				finish();
			}
		});
		frame.setVisible(true);
		frame.pack();
		
		new ChatClientThread(br).start();
	}
	
	private void finish() {
		// quit protocol 구현
		pw.println("QUIT"); // br 쓰레드 정리
		System.exit(0);
	}
	private void sendMessage() {
		String message = textField.getText();

		if (message == "") {
		} else if ("quit".equals(message)) {
			finish();
		} else {
			// 메시지 처리
			// 인코딩
			String encodedString = Base64.getEncoder().encodeToString(message.getBytes());
			// 프로토콜과 함께 담아서 전송
			String msg = "MESSAGE:" + encodedString;
			pw.println(msg);
		}
		
		textField.setText("");
		textField.requestFocus();
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
					if (data.equals("")) {
						break;
					} else {
						updateTextArea(data);
					}
				}
			} catch (IOException e) {
				System.out.println("error:" + e);
			}
		}
	}

}
