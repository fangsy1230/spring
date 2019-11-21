package spring.annotation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import spring.annotation.service.BookService;

@Controller
public class BookController {
	
	@Autowired
	private BookService bookService;

}
