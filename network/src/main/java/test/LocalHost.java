package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostName = inetAddress.getHostName();
			String hostIpAddress = inetAddress.getHostAddress();
			
			System.out.println(hostName);
			System.out.println(hostIpAddress);
			
			byte[] IpAdresses = inetAddress.getAddress();
			
			for(byte IpAdress : IpAdresses) {
				//System.out.print(IpAdress);
				System.out.print(IpAdress & 0x000000ff);
				System.out.print(".");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

}
