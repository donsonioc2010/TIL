package hello.container;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

import java.util.Set;

public class MyContainerInitV1 implements ServletContainerInitializer {
    /*
    META-INF/services/jakarta.servlet.ServletContainerInitializer에 선언한 클래스를 톰캣이 실행될 때 찾아서 실행한다.
    onStartup 메서드에 원하는 초기화 작업을 할 수 있다.
     */
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println("MyContainerInitV1.onStartup");
        System.out.println("MyContainerInitV1.onStartup.c >>> " + c);
        System.out.println("MyContainerInitV1.onStartup.ctx >>> " + ctx);

    }
}
