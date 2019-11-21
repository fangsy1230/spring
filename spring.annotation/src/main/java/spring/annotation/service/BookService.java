package spring.annotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import spring.annotation.dao.BookDao;

import javax.annotation.*;
import javax.inject.Inject;
@Service
public class BookService {
	
	//@Qualifier("bookDao")
	//@Autowired(required=false)
	//@Resource(name="bookDao2")
	@Inject
	private BookDao bookDao;
	
	public void print(){
		System.out.println(bookDao);
	}

}
