package hello.container;

import hello.spring.HelloConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitV2Spring implements AppInit {

    @Override
    public void onStartup(ServletContext servletContext) {
        System.out.println("AppInitV2Spring.onStartUp");

        // 스프링 컨테이너 생성
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(HelloConfig.class);

        // 스프링 MVC 디스패처 서블릿 생성과 스프링 컨테이너 연결
        DispatcherServlet dispatcheer = new DispatcherServlet(appContext);

        // 디스패처 서블릿을 서블릿 컨테이너에 등록
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcherV2", dispatcheer);

        // /spring/* 요청이 디스패처 서블릿을 통하도록 설정 즉 /hello-spring은 /spring/hello-spring으로 변경된다 (스프링컨테이너에서 생성되었기에)
        servlet.addMapping("/spring/*");
    }
}
