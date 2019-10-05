package Spiders;

public class HttpUtil_spiderEntity {
	
	private int bookId;
	
	private String bookName;
	
	private String bookPrice;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(String bookPrice) {
		this.bookPrice = bookPrice;
	}
	
	
	public HttpUtil_spiderEntity() {
		// TODO Auto-generated constructor stub
	}

	public HttpUtil_spiderEntity(int bookId, String bookName, String bookPrice) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.bookPrice = bookPrice;
	}


}
