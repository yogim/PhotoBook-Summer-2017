package com.photobook.model;

public class Book {
String ID;
	
	String Bookname;
	String bookauthor;
	String datecreated;
	public Book()
	{
		
	}
	public Book(String bookname, String bookauthor, String datecreated) {
		super();
		Bookname = bookname;
		this.bookauthor = bookauthor;
		this.datecreated = datecreated;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getBookname() {
		return Bookname;
	}
	public void setBookname(String bookname) {
		Bookname = bookname;
	}
	public String getBookauthor() {
		return bookauthor;
	}
	public void setBookauthor(String bookauthor) {
		this.bookauthor = bookauthor;
	}
	public String getDatecreated() {
		return datecreated;
	}
	public void setDatecreated(String datecreated) {
		this.datecreated = datecreated;
	}
	

}
