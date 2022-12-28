package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {
	public static void main(String[] args) {
		// 과제
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.println("> ");
			String line = scanner.nextLine();
			try {
				InetAddress[] inetAddresses = InetAddress.getAllByName(line);
				for(InetAddress Address : inetAddresses) {
					System.out.println(Address.getHostName()+" : "+Address.getHostAddress());
					// hostname : address
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
	}
}
