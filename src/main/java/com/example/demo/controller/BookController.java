package com.example.demo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Book;
import com.example.demo.entity.ResponseUserBook;
import com.example.demo.entity.User;
import com.example.demo.reposetory.BookRepository;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@RestController
@RequestMapping(value = "/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("")
//	@HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
//      }, fallbackMethod = "handleUserFallBack")
	@CircuitBreaker(fallbackMethod = "handleUserFallBack", name = "bookser")
	public ResponseEntity<Object> getAllBooks() throws URISyntaxException {
		ResponseUserBook responseUserBook = new ResponseUserBook();

		List<Book> book = bookRepository.findAll();

		responseUserBook.setBook(book);

		List<User> user = restTemplate.getForObject("http://USERSERVICE/users", List.class);

		responseUserBook.setUser(user);

		return ResponseEntity.ok(responseUserBook);
	}

	public ResponseEntity<Object> handleUserFallBack(Exception e) throws URISyntaxException {
		ResponseUserBook responseUserBook = new ResponseUserBook();

		List<Book> book = bookRepository.findAll();

		responseUserBook.setBook(book);

		return ResponseEntity.ok(responseUserBook);
	}

}
