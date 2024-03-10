package jong1;

import jong1.boot.MySpringApplication;
import jong1.boot.MySpringBootApplication;

@MySpringBootApplication
public class MySpringBootMain {
    public static void main(String[] args) {
        System.out.println("MYSpringBootMain.main()");
        MySpringApplication.run(MySpringBootMain.class, args);
    }
}
