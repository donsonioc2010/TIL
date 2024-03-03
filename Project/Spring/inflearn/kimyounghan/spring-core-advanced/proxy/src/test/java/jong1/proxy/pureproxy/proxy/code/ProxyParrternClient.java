package jong1.proxy.pureproxy.proxy.code;

public class ProxyParrternClient {
    private Subject subject;

    public ProxyParrternClient(Subject subject) {
        this.subject = subject;
    }

    public void execute() {
        subject.operation();
    }
}
