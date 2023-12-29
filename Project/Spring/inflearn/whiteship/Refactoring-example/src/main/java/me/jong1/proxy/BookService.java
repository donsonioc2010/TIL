package me.jong1.proxy;

public interface BookService {
	public void rent(ProxyBook proxyBook);

	public void returnBook(ProxyBook book);
}
