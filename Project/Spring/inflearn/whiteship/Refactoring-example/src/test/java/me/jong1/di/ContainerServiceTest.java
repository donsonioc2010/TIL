package me.jong1.di;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContainerServiceTest {
	@Test
	public void getObject_BookRepository() {
		BookRepository bookRepository = ContainerService.getObject(BookRepository.class);
		Assertions.assertNotNull(bookRepository);
	}

	@Test
	public void getObject_BookService() {
		BookService bookService = ContainerService.getObject(BookService.class);
		Assertions.assertNotNull(bookService);
		Assertions.assertNotNull(bookService.bookRepository);
	}
}
