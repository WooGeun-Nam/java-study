package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class PhoneList01 {

	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			// io - stream 이용
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
			
			// 1. 기반 스트림(FileInputStream)
			FileInputStream fis = new FileInputStream(file);
			
			// 2. 보조 스트림1(byte|byte|byte -> char)
			InputStreamReader isr = new InputStreamReader(fis, "utf-8");
			
			// 3. 보조 스트림2(char1|char2|char3|char4|\n -> "char1char2fchar3char4"
			br = new BufferedReader(isr);
			
			// 4. 처리 : Tokenizer 사
			String line = null;
			while((line = br.readLine()) != null) {
				// 스플릿 도 가능
				StringTokenizer st = new StringTokenizer(line,"\t ");
				
				int index = 0;
				while(st.hasMoreElements()) {
					String token = st.nextToken();
					
					if(index  == 0) { // 이름
						System.out.print(token + ":");
					} else if(index == 1) { // 전화번호1
						System.out.print(token + "-");
					} else if(index == 2) {
						System.out.print(token + "-");
					} else {
						System.out.print(token);
					}
					index++;
				}
				System.out.print("\n");
			}
			
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error:"+e);
		} catch (IOException e){
			System.out.println("Error:"+e);
		} finally {
			try {
				if(br != null) {
					br.close();	
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
