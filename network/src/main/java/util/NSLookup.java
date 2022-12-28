package util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NSLookup {
	public static void main(String[] args) {
		// 과제
		String line = "www.douzone.com";
		try {
			InetAddress[] inetAddresses = InetAddress.getAllByName(line);
			for(InetAddress Address : inetAddresses) {
				System.out.println(Address);
				// hostname : address
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
