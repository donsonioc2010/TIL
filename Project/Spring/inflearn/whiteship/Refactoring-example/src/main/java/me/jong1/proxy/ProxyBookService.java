package me.jong1.proxy;

public class ProxyBookService implements BookService{
	BookService defaultBookService = new DefaultBookService();
	@Override
	public void rent(ProxyBook proxyBook) {
		System.out.println("aaaa");
		defaultBookService.rent(proxyBook);
		System.out.println("bbbb");
	}

	@Override
	public void returnBook(ProxyBook book) {
		defaultBookService.rent(book);
	}
}
