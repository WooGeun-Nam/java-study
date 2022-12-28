package test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class TCPServer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			// 1. Server Socket 생성
			serverSocket = new ServerSocket();
			
			// 2. 바인딩(binding)
			//    Socket에 InetSocketaddress(IP Address + Port)를 바인딩 한다.
			//    IPAddress: 0.0.0.0 : 특정 호스트 IP에 바인딩 하지 않는다.
			serverSocket.bind(new InetSocketAddress("0.0.0.0",5000));
			
			// 3. accept
			serverSocket.accept(); // blocking
			
			System.out.println("connected");
			
			
		} catch (IOException e) {
			System.out.println("[server] error:"+e);
		} finally {
			try {
				if(serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
