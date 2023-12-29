package me.jong1.proxy;

public class OnlyBookService  {

	public void rent(ProxyBook proxyBook) {
		System.out.println(proxyBook.getTitle());
	}

	public void returnBook(ProxyBook book) {
		System.out.println("return Book : " + book.getTitle());
	}
}
