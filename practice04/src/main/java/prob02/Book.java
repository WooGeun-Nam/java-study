package prob02;

public class Book {
	private int bookNo;
	private String title;
	private String author;
	private int stateCode;
	
	public Book(int bookNo, String title, String author) {
		this.bookNo = bookNo;
		this.title = title;
		this.author = author;
		stateCode = 1;
	}
	
	public void rent() {
		stateCode = 0;
		System.out.println(title+"이(가) 대여 됐습니다.");
	}
	
	public void print() {
		boolean bookCheck;
		if (stateCode==1) {
			bookCheck=true;
		} else {
			bookCheck=false;
		}
		System.out.println("책 번호:"+bookNo+", 책 제목:"+title+", 작가:"+author+", 대여 유무:"+(bookCheck?"재고있음":"대여중"));
	}
}
