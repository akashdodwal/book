package com.example.demo.entity;

import java.util.List;

public class ResponseUserBook {
	
	List<User> user;
	
	List<Book> book;

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	public List<Book> getBook() {
		return book;
	}

	public void setBook(List<Book> book) {
		this.book = book;
	}

}
