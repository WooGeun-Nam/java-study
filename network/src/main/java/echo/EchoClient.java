package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_IP = "0.0.0.0";

	public static void main(String[] args) {

		Socket socket = null;
		Scanner scanner = null;
		try {
			socket = new Socket();

			socket.connect(new InetSocketAddress(SERVER_IP, EchoServer.PORT));
			log("connected");

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));

			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			scanner = new Scanner(System.in);
			while (true) {
				System.out.print(">");
				String line = scanner.nextLine();

				pw.println(line);
				if ("exit".equals(line)) {
					break;
				}

				pw.println(line);
				String data = br.readLine();
				if (data == null) {
					log("closed by server");
					break;
				}

				System.out.println("<" + data);
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
	}

	private static void log(String message) {
		System.out.println("[EchoClient] " + message);
	}
}