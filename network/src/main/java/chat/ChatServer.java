package chat;

import java.io.IOException;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	public static final int PORT = 6000;
	public static final String SERVER_IP = "0.0.0.0";
	public static List<User> listUser = new ArrayList<User>();
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket();
			
			serverSocket.bind(new InetSocketAddress(SERVER_IP, PORT), 10);
			log("starts...[port:" + PORT + "]");
			
			while(true) {
				Socket socket = serverSocket.accept();
				new ChatServerThread(socket, listUser).start();
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
	
	public static void log(String message) {
		System.out.println("[Server]" + message);
	}

}
