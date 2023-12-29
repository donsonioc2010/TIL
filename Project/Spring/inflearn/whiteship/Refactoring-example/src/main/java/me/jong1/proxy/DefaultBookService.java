package me.jong1.proxy;

public class DefaultBookService implements BookService {

	@Override
	public void rent(ProxyBook proxyBook) {
		System.out.println(proxyBook.getTitle());
	}

	@Override
	public void returnBook(ProxyBook book) {
		System.out.println("return Book : " + book.getTitle());
	}
}
