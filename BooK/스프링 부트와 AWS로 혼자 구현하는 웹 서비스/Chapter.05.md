## **Chapter. 05 : 스프링 시큐리티와 OAuth2.0으로 로그인 기능 구현하기**

### **P.163**

**Spring Security란?**

> Spring Security는 막강한 인증(Authentication)과 인가(authorization) 혹은 권한부여 기능을 가진 프레임워크다.
>
> 스프링 기반의 Application에서는 보안을 위한 표준일 정도.
>
> Interceptor, Filter기반의 보안을 구현하는 것보다는 Spring Security를 통해서 구현하는걸 적극적으로 권장하고 있다.

---

### **다양한 서비스에서 로그인 기능을 왜 소셜로그인을 쓸까?**

> 많은 서비스가 최근 추세가 로그인 기능을 ID / PW를 대신하여 구글, 페이스북, 네이버 로그인과 같은 서비스를 사용하는 것일까?
>
> 그 이유는 아마 **배보다 배꼽이 더 커지기 떄문**일 것이며, 직접 구현해야 하는게 많기 떄문일 것이며 아래의 리스트를 모두 개발해야 하기 떄문이다. (Oauth를 사용해도 개발해야 하는것을 제외한 리스트 라고 한다.)
>
> > - 로그인 시 보안
> > - 회원가입 시 이메일 혹은 전화번호 인증
> > - 비밀번호 찾기
> > - 비밀번호 변경
> > - 회원정보 변경
>
> OAuth를 통한 로그인 구현시에는 위의 리스트들에 대해서는 해당 서비스에 맡겨버리면 되기 떄문에 다른 도메인의 개발에 집중할 수 가 있기 떄문이다.

<br>

### **P.164~165**

**스프링부트 1.5 VS 스프링부트 2.0**

> 스프링 부트 2버전에서 1.5 Oauth2의 연동을 사용할 수가 있고 설정방법에 크게 차이가 없는 자료를 많이 볼 수 있다. 해당 이유는 `spring-security-oauth2-autoconfigure`로 인해서 그대로 사용할 수 있다.
>
> 하지만 스프링 팀에서 기존 1.5에서 사용하던 spring-security-oauth 프로젝트의 경우 유지하면서 버그수정만 하고 신규기능은 추가를 하지 않고 oauth2라이브러리에만 지원하겠다 선언하였다.
>
> 이유는 확장 포인트가 적절하게 오픈되어 있지 않아 직접상속 또는 오버라이딩을 해야 하고 신규 라이브러리인 경우에는 확장포인트를 고려해서 설게된 상태 이기때문이라고 한다.
>
> 해당 저서는 Spring Security Oauth2 Client라이브러리를 사용해서 진행되었다.
>
> 추후 검색시 두가지를 화인하면 좋을 듯 하다.
>
> 1. spring-security-oauth2-autoconfigure 라이브러리를 사용하였는지
> 2. application.peoperties 또는 application.yml의 정보
>    > - 스프링부트 1.5에서는 url주소를 모두 명시해야 하지만 2.0 이후에서는 client인증 정보만 기입을 하면된다.
>    > - 2.0으로 넘어오면서 1.5버전에서 입력했던 url값들 등이 enum으로 대체되었으며, CommonOAuth2Provider라는 명싱이고, 구글, 깃허브, 페이스북, 옥타등 기본 설정값이 모두 제공된다.

<br>

### **P.172**

**구글 OAuth프로젝트를 제작하며 있던 승인된 리디렉션 URI?**

- 서비스에서 파라미터로 `인증 정보를 주었을 때 인증이 성공하면 구글에서 리다이렉트 할 URL`이다.
- 스프링 부트2 버전의 시큐리티에서는 기본적으로 `{도메인}/login/oauth/code/{소셜서비스코드}`로 `리다이렉트 URL을 지원`하고 있다.
- 현재는 개발 단계이므로 http://localhost:8080/login/oauth2/code/google로만 등록한다.
- AWS서버에 배포를 하게 되면 localhost이외의 주소를 추가해야 하므로 발급받은 이후 하면 된다.

<br>

### **P.174~175**

**[Google]OAuth.properties**

```
spring.security.oauth2.client.registration.google.client-id=클라이언트ID
spring.security.oauth2.client.registration.google.client-secret=클라이언트PW
spring.security.oauth2.client.registration.google.scope=profile,email
```

> Google Cloud를 통해서 생성한 프로젝트의 발급받은 ID와 PW를 등록하면 된다.
>
> 많은 예제들이 `scope` 설정을 따로 하지 않는다. 이유는 기본값이 openid, profile, email이기 때문이며, scope를 따로 지정한 이유는 openid라는 scope가 존재하면 Open Id Provider로 인식하기 때문이다.
>
> OpenId Provider인 서비스로 만들어 버리게 되면 그렇지 않은 서비스와 나눠서 각각 OAuth2Service를 만들어야 하기 떄문이다.
>
> 예제에서 일부로 Open id의 scope를 하지 않은 이유는 하나의 OAuth2Service를 사용하기 위함이다.

---

**application-oauth.properties로 제작한 이유?**

> 스프링 부트에서는 properties의 이름을 만들 때 `application-xxx.properties`로 만들 경우 xxx라는 이름의 profile이 생성되어 관리가 가능하다.
>
> 즉 profile=xxx라는 식으로 호출하면 해당 properties의 설정들을 가져 올 수 있다
>
> 제작을 한 이후 application.properties에 `spring.profiles.include=oauth` 과 같은 항목을 추가하여 설정을 가져오면 된다.

<br>

### **P.178**

**`@Enumerated(EnumType.STRING)`**

- JPA로 데이터베이스에 저장 할 때 Enum값을 어떤 형태로 저장 할지를 결정한다.
- 기본값으로는 int로 된 숫자가 저장된다.
- 숫자로 저장되면 데이터베이스로 확인 할 떄 그 값이 무슨 코드를 가지고 있는지 의미를 알 수가 없다.
- 이해를 하기 위해 EnumType.STRING을 통하여 이해할 수 있도록 저장방식을 변경해서 선언한다.

<br>

### **P.181**

**`Spring Security Config`**

> 예제의 작성된 코드는 다음과 같으며 위에서부터 기록을 해보자면.

```
import com.jongwon.dev.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomOAuth2UserService customOAuth2UserService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
         .csrf().disable()
         .headers().frameOptions().disable()
         .and()
            .authorizeRequests()
            .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
            .antMatchers("/api/v1/**").hasRole(Role.USER.name())
            .anyRequest().authenticated()
         .and()
            .logout()
               .logoutSuccessUrl("/")
         .and()
            .oauth2Login()
               .userInfoEndpoint()
                  .userService(customOAuth2UserService);
  }
}
```

1. `@EnableWebSecurity`
   - Spring Security의 설정을 활성화 하는 Annotation
2. `.csrf().disable().headers().frameOptions().disable()`
   - h2-console 화면을 사용하기 위해서 해당 옵션의 disable설정 (왜...?)
3. `.authorizeRequest()`
   - URL별 권한 관리를 설정하는 옵션의 시작점
   - `authorizeRequests`가 선언되어야만 antMatchers옵션을 사용 할 수 있다.
4. `.antMatchers()`
   - 권한 관리 대상을 지정하는 옵션
   - URL, HTTP메소드 별로 관리가 가능하다.
   - `/` 등의 지정된 URL들은 `permitAll()` 옵션을 통해 전체 열람권한하도록 예제가 작성되었다.
   - `/api/v1/**`주소들의 경우에는 USER권한을 가진 사람만 가능하도록 제작되었다.
5. `.anyRequest()`
   - 설정된 값들 이외의 나머지 URL들을 나타낸다.
   - `authenticated()`메소드를 활용해서 나머지 모든 URL은 인증된 사용자들에게만 허용하도록 제작
   - 인증된 사용자는 로그인을 한 사용자들을 의미
6. `.logout().logoutSuccessUrl()`
   - 로그아웃 기능에 대한 설정의 진입점
   - 로그아웃 성공시에는 `/` 주소로 이동
7. `.oauth2Login()`
   - OAuth2 로그인 기능에 대한 설정의 진입점.
8. `.userInfoEndpoint()`
   - OAuth2 로그인 성공 이후 사용자 정보를 가져 올 떄의 설정들을 담당한다.
9. `.userService()`
   - 소셜로그인 성공 시 후속 조치를 진행 할 UserService인터페이스의 구현체를 등록한다.
   - 리소스 서버(소셜 서비스를 의미)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능의 명시가 가능하다.

<br>

### **P.184**

**`CustomOAuth2UserService`**

> `OAuth2UserService<OAuth2UserRequest, OAuth2User>`를 상속받아 제작된 Custom이며 소스의 기능에 대한 설명은 다음과 같다..

1. `userRequest.getClientRegistration().getRegistrationId()`
   - 현재 로그인이 진행중인 서비스를 구분하는 코드로 현재 단일 서비스를 하는 경우에는 불필요한 값이지만 이후 다른 소셜 서비스를 연동할 때 어떤 로그인인 인지 구분하기 위해 사용한다.
2. `userRequest.getClientRegistration().getProviderDetails()
.getUserInfoEndpoint().getUserNameAttributeName()`
   - OAuth2로그인 진행 시 키가 되는 필드값을 이야기 하며, PK와 같은 의미이다.
   - 구글의 경우 기본적으로 코드를 지원하지만, 네이버나 카카오 등은 기본 지원하지 않으며, 구글의 기본 코든는 `sub`ek
   - 추후 네이버, 구글 로그인을 동시 지원 할 때 사용할 예정이다.
3. `OAuthAttributes`
   - OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스로 추후 다른 소셜 로그인도 같이 사용을 진행할 예정이다.
4. `SessionUser`
   - 세션의 사용자 정보를 저장하기 위한 Dto클래스로, User클래스와 따로 분류하였다.

<br>

### **P.186~187**

**`OAuthAttributes` Dto의 예제를 제작 해설**

1. `of()`
   - OAuth2User에서 반환하는 사용자 정보는 Map이기 떄문에 값 하나하나를 변환해야만 하기에 제작.
2. `toEntity()`
   - User 엔티티를 생성한다.
   - OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때.
   - 가입할 때의 기본 권한을 GUEST로 주기 위해서 role빌더값에는 Role.GUEST를 사용한다.

<br>

### **P.188**

**`SessionUser`를 따로 제작해서 Session에 저장하는 이유는?**

> `User`클래스를 바로 Session에 저장을 하게 되면 `Failed to convert from type(Object) to type(byte[]) for value ~~` 오류가 발생하게 된다.
>
> 해당 오류는 `User` 클래스에 직렬화를 구현하지 않아서 발생이 되는 오류이다.
>
> 하지만 `User` 클래스에 직접 직렬화를 구현하지 않는 이유는 해당 클래스는 **`Entity`** 이기 때문이다. Entity클래스는 `언제 다른 엔티티 클래스와 관계가 형성될지 모르기 떄문`이다.
>
> `@OneToMany`,`@ManyToMany`등으로 자식 엔티티를 갖게 되면 직렬화 대상이 자식으로까지 넓어지기 떄문에 `성능이슈를 포함하여 여러 이슈가 발생`할 수 있기 때문이다.
>
> 위와 같은 사유로 `SessionUser`를 따로 제작하여 Session에 저장하듯 `직렬화 기능을 가진 세션 Dto`를 하나 추가로 만드는게 추후 나의 운영 및 유지보수에 많은 도움이 될 것이다.

<br>

### **P.189**

**`mustache`의 해설?**

1. `{{#userName}}`
   - `mustache`의 경우 if문을 제공하지 않으며, `true` / `false`여부만 판단한다. 그렇기 떄문에 `항상 최종 값`을 넘겨주어야 한다.
2. `a href="/logout"`
   - `Spring Security`에서 기본적으로 제공하는 `로그아웃 URL`이다.
   - 개발자가 별도로 저 URL에 해당하는 컨트롤러를 제작하지 않아도 된다.
   - SecurityConfig 클래스에서 URL을 변경 할 수 있지만, 기본 URL을 사용하도 충분하다.
3. `{{^userName}}`
   - `mustache`에서 해당 값이 존재하지 않는 경우 `^객체명`을 사용한다.
4. `a href="/oauth2/authorization/google"`
   - `Spring Security`에서 기본적으로 제공하는 `로그인 URL`이다.

<br>

### **P.196**

**Custom Annotation**

1. **`@Target(ElementType.PARAMETER)`**
   - 해당 어노테이션이 생성 될 수 있는 위치를 설정하는 Annotation
   - 해당 예제는 PARAMETER로 지정하였기 때문에 메소드의 파라미터로 선언된 객체에만 사용 할 수 있다.
   - 외에도 다양한 위치에 조절 할 수 있도록 다양한 Type값이 존재한다.
2. **`@interface`**
   - 해당 파일을 Annotation Class로 지정하는 선언

<br>

### **P.197**

**`HandleMethodArgumentResolver`란?**

> 조건에 맞는 메소드가 있다면 `HandlerMethodArgumentrResolver`의 구현체가 지정한 값으로 해당 메소드의 파라미터로 넘길 수 있다.

1. **Override한 `supportsParameter(MethodParameter)`**

   - 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단하는 Method
   - 예제 소스에서는 파라미터에 `@LoginUser` 어노테이션이 붙어 있고, 파라미터 클래스 타입이 SessionUser.class 인 경우 true를 반환한다.
     - ```
         아래의 소스코드 참고
         boolean isLoginUserAnnotation =
            parameter.getParameterAnnotation(LoginUser.class) != null;
         boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
       ```

2. **Override한 `resolveArgument`**
   - 파라미터에 전달할 객체를 생성한다.
   - 여기서는 세션에서 객체를 가져온다.

<br>

### **P.200**

**Resolver를 등록함으로 생긴 이점**

```
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

  private final HttpSession httpSession;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
    boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

    return isLoginUserAnnotation && isUserClass;
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory
  ) throws Exception {
    return httpSession.getAttribute("user");
  }

}

```

해당 Resolver를 등록함으로써 Parameter에 직접 제작한 `@LoginUser`어노테이션과 `Type`을 `SessionUser`로 진행을 할 경우 굳이 Session의 getAttribute를 활용해서 가져오는 절차 없이 불러올 수 있다.

<br>

### **P.201**

**WAS의 세션 저장소로 DB를 활용하기**

> WAS의 경우 어플리케이션을 재실행 하는 경우 Session이 유지가 되지 않는다. 세션의 경우 `내장 톰캣의 메모리 영역`에 저장이 되기 떄문이다.  
> 즉 `내장 톰캣처럼 어플리케이션이 실행시 실행이 되는 구조에서는 항상 초기화`가 된다.
>
> 위의 말의 문제점은 `배포를 진행 할 때마다 톰캣이 재시작`하고 세션데이터가 증발하는 문제를 말 하는 것이다.
>
> 또 다른 문제점은 실제 `복수개의 서버`에서 `서비스를 진행`하고 있을 경우 `톰캣마다 세션동기화`를 해야한다. 그렇지 않으면 `한쪽만 세션데이터를 소지하고 있게 되는 것`이다.
>
> 그래서 실제 현업에서는 보통 3가지중에 한개를 선택한다.
>
> 1. 톰캣 세션을 사용한다
>    > - 일반적으로 사용하는 케이스로, 설정을 하지 않을 때 기본적으로 선택되는 방식
>    > - WAS에 저장이 되기에 아까 기록한 대로 `2대 이상의 WAS`가 구동되는 경우 세션공유를 위한 `추가 설정이 필요`하다
> 2. `MySQL`처럼 `데이터베이스`를 세션 저장소로 사용한다.
>    > - 여러 WAS간에 공용세션을 사용 할 수 있는 가장 쉬운 방법
>    > - 많은 설정은 필요 없지만 `DB IO가 매우 자주 발생`하기 떄문에 `성능상 이슈가 발생` 할 수 있다.
>    > - 보통 `로그인 요청이 많이 없는` 백오피스 또는 사내 시스템 등에서 자주 사용된다.
> 3. `Redis` ,`Memcached`와 같은 `메모리DB`를 세션 저장소로 사용한다
>    > - 가장 많이 활용되는 방식이다
>    > - 실제 서비스로 사용하기 위해서는 Embedded Redis와 같은 방식이 아니라, 외부 메모리 서버를 따로 구축해서 활용한다. (추가적인 서버 비용 발생)

<br>

### **P.202**

**DB로 세션 저장 변경방법**
JPA를 활용할 경우 변경은 쉽다.

1. `spring-session-jdbc`의존성 추가
2. `application.properties or application.yml`에 `spring.session.store-type`으로 `value`를 `jdbc`로 설정

<br>

### **P.211**

**기존 테스트에 Security 적용**
Security옵션이 활성화 되면 인증된 사용자만 API를 호출 할 수 있다. 이는 테스트코드 역시 마찬가지다.
만약 테스트코드도 정상적으로 진행을 하고 싶다면 테스트 코드마다 인증한 사용자가 호출한 것처럼 작동이 되도록 수정을 해야 한다.

application-oauth.properties에 설정값을 추가하였음에도 설정이 없다고 나오는 이유는 `src/main`환경과 `src/test`의 환경이 차이가 나기 때문이며, 둘은 환경구성을 따로따로 가지기 떄문이다.

여기서 의문인건 test에 application.properties가 없음에도 실행되는게 의문일 수도 있다. test의 application.properties가 없는경우에는 main의 설정을 그대로 가져오기 떄문에 문제가 없다.

하지만 가져오는 옵션의 범위는 application.properties까지 이기 때문에, 예제의 application-oauth.properties는 test에 없다고 가져오는 파일이 아니라고 나오는 것.

해당 문제를 해결하기 위해서는 application.properties를 test환경에 구축해야한다

<br>

### **P.215**

**`src/test`에 `application.properties` 구축이후**

> 그래도 테스트를 진행할 경우 302로 실패된 케이스가 존재한다. 이유는 스프링 시큐리티 설정 때문에 `인증되지 않은 사용자의 요청은 이동` 시키기 때문이다.  
> 이런 요청의 경우에는 `임의로 인증된 사용자를 추가`해 `API만 테스트 해 볼 수 있도록` 해야한다
>
> 해당 테스트에 대해서는 Spring Security에서 공식적인 방법을 지원하며, `Spring security Test`를 통해 테스팅이 가능하다
>
> `302`에러가 나는 경우이며, `@WithMockUser(roles="USER")`를 테스트케이스에 선언하여 Mock테스트 시에 USER권한을 부여한다. (MocvMVC 에서만 사용가능하다.)
>
> 이후 SpringBootTest에서 MockMvc테스트로 전환하자.

<br>

### **P.220**

**`CustomOAuth2UserService`를 스캔못한 오류는 왜날까?**

> Custom으로 제작한 OAuthService오류는 제목그대로 스캔하지 못하기떄문에 발생하고있다.  
> 해당 Controller의 경우에는 `WebMvcTest`를 사용해서 테스트를 하고있으며, WebMvcTest는 `@CustomOAuth2UserService`를 스캔하지 못하기 떄문에 문제가 발생하고 있는 것이다.
>
> `@WebMvcTest`는 `WebSecurityConfigureAdapter`, `WebMvcConfigurer`를 비롯하여 `@ControllerAdvice`, `@Controller`를 읽는다.  
> 즉 `@Service`, `@Repository`, `@Component`는 스캔의 대상이 아니다.
>
> 문제가 나오는 이유는 이 부분때문인며, Security Config는 읽었지만 CustomOAuth2UserService는 읽지를 못해서 에러가 발생한 것이라 해당 문제를 위해서는 SecurityConfig를 HelloController에서는 제거하는 것이다.
>
> 아래와 같은 설정을 해서 SecurityConfig를 제거하자.

```
@WebMvcTest(controllers = HelloController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
    })
```

> MockMvc테스트기 떄문에 테스트마다 `@WithMockUser(roles = "USER")`를 통해 User권한도 부여하자

> 이후에는 `JPA metamodel must not be empty!`에러가 난다.
> `@EnableJpaAuditing`으로 인해 발생하는 문제로 하나 이상의 Entity가 필요하다는 건데, `@WebMvcTest`라 Entity가 따로 없다.
> 하지만 예제에서 `@EnableJpaAuditing`과 `@SpringBootApplication`이 Main에 같이 존재하기 떄문에 `@WebMvcTest`에서도 같이 스캔하게 된 것이다.  
> 아래와 같이 따로 분류하도록 하자.

```
//@EnableJpaAuditing 주석처리
@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}

@Configuration
@EnableJpaAuditing
public class JpaConfig {}

```

<br>
