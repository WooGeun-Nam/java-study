package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
	public static final int PORT = 8000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket();

			serverSocket.bind(new InetSocketAddress("0.0.0.0", PORT), 10);
			log("starts...[port:" + PORT + "]");

			Socket socket = serverSocket.accept();

			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remotePort = inetRemoteSocketAddress.getPort();
			log("connected by client[" + remoteHostAddress + ":" + remotePort + "]");

			try {
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
				// true를 하면 버퍼를 다 채우지 않아도 보낸다.
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				while (true) {
					String data = br.readLine();
					if (data == null) {
						log("closed by cliendt");
						break;
					}
					log("received:" + data);

					pw.println(data);
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
		} catch (SocketException e) {
			log("suddenly closed by client"+e);
		} catch (IOException e) {
			log("error:" + e);
		} finally {
			try {
				if (serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void log(String message) {
		System.out.println("[EchoServer] " + message);
	}

}
