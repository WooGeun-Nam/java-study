package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PhoneList02 {

	public static void main(String[] args) {
		Scanner scanner = null;
		try {
			// scanner 활용
			File file = new File("phone.txt");
			if(!file.exists()) {
				System.out.println("file not found");
				return;
			}
			
			System.out.println("=====파일정보=====");
			System.out.println(file.getAbsolutePath());
			System.out.println(file.length()+"bytes");
			
			Date d = new Date(file.lastModified());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String date = sdf.format(d);
			System.out.println(date);
			
			System.out.println("=====전화번호부=====");
			scanner = new Scanner(file);
			// scanner 는 tokenizer가 내장되어 있다.
			while(scanner.hasNextLine()) {
				String name = scanner.next();
				String phone1 = scanner.next();
				String phone2 = scanner.next();
				String phone3 = scanner.next();
				
				System.out.println(name+":"+phone1+"-"+phone2+"-"+phone3);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(scanner != null) {
				scanner.close();
			}
		}
	}

}
