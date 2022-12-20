# README

> 책을 읽어가며 필요한 부분들, 기억해야 할 부분들에 대한 기록.  
> 잘못 알고있던, 혹은 혼동되서 알고있던 기록들도 전부 기록.

## **Chapter. 04 : 머스테치를 통한 화면 구성하기**

### **P.125~127**

**템플릿 엔진의 종류**

> `템플릿 엔진`이란? `지정된 템플릿 양식과 데이터`가 합쳐져 `HTML문서`를 `출력`하는 소프트웨어를 의미
>
> > **JSP, Freemarker등등**
> >
> > - 서버 템플릿 엔진
> > - 서버(WAS)에서 Java코드로 명령어를 (문자열을) 실행한 이후 HTML로 변환하여 브라우저로 전달한다.
>
> > **React, Vue 등등**
> >
> > - 클라이언트 템플릿 엔진
> > - SPA로도 칭한다.(Single Page Application)
> > - 서버 템플릿 엔진과는 다르게 브라우저 위에서 작동된다. 즉 브라우저에서 화면을 생성하기 떄문에 서버에서 이미 코드가 벗어난 경우다.
> > - Json또는 Xml형식의 데이터만 전달하고 클라이언트에서 해당 데이터를 조립한다.

<br>

### **P.128~9**

**머스테치란?**

> 다양한 언어를 지원하는 템플릿 엔진으로, Java에서 사용할 때는 서버 템플릿엔진으로, JS에서 사용할 떄는 클라이언트 템플릿 엔진으로 사용이 가능하다.

> 작가와 동일한 생각인 내용이 있어 기록한다. `템플릿 엔진`은 `화면 역할에만 충실`해야 한다. 너무 많은 기능을 제공하고 `API와 템플릿 엔진`, `자바스크립트`가 `서로 로직을 나눠 갖게 되면 유지보수를 하기가 굉장히 어려워진다.`
>
> > 이 전 회사에서 겪었던 점이다.. 회사에서 JSP와 Servlet을 활용하여 Application을 구축했는데 JSP에 너무나도 심각하게 많은 로직들이 있었다.  
> > 유지보수를 하려고 보면 Debug도 정상적으로 이뤄지지도 않아 추적이 힘들고 유지보수를 하면서 JSP영역에 있다 보니 해당 기능들을 생각을 하지 못하고 개발하거나 변경하는 경우가 너무나도 빈번했었다.  
> > 그러다보니 나의 경우에도 Back과 Front는 무조건 확실하게 분리하고 Front는 화면만 해야 한다는 신념을 가지게 되었다.

<br>

### **P.134**

**Page에 대한 Test**
HTML마크업 페이지에 대해서 테스트를 할 때 정상적으로 로딩이 되었는 지를 확인하기 위한 케이스를 만들 경우, 해당 페이지를 호출해서 일치하는 문자열이 존재하는지 검증하는 방식으로 테스트하면 수월하다.

<br>

### **P.137**

**CSS와 JS의 위치**

> `CSS`의 경우에는 `header`에 , `jquery`, `bootstrap.js`의 경우에는 `footer`에 배치를 하였으며 이유는 `페이지의 로딩속도를 높이기 위해서` 이다.
>
> 왜 이러냐면 보통 head가 다 불러지지 않으면 사용자 쪽에선 `백지 화면`만 노출이 된다.  
> 특히 `JS의 용량`이 `크면 클수록` body 부분의 `실행이 늦어지기 때문`에 js는 body의 하단에 두어 화면이 다 그려진 이후에 호출하는 것이 좋다.
>
> 반면에 `CSS`의 경우에는 `위`에 둬야 하는 이유는 CSS가 적용되지 않을 경우 `깨진 화면`을 사용자가 보게 되기 때문에 먼저 호출 한다.

※ bootstrap.js의 경우에는 4까지는 jQuery가 꼭 있어야만 한다. 하지만 5가되면서 jQuery를 걷어냈다.

<br>

### **P.138**

**{{>filePath}}**

> `{{>layout/footer, header}}`를 통하여 footer와 header를 참조하였다.  
> `{{>filePath}}`는 다른 파일을 가져와야 할 경우에 사용이 된다.

<br>

### **P.146**

**Example Code**

```
   {{#posts}}
      <tr>
        <td>{{id}}</td>
        <td>{{title}}</td>
        <td>{{author}}</td>
        <td>{{modifiedDate}}</td>
      </tr>
    {{/posts}}
```

> `{{#posts}}`
>
> > - posts라는 List를 순회하는 역할로 Java의 for문과 동일
>
> `{{id}}등의 {{변수명}}`
>
> > - `Controller`에서 가져온 `Entity(Dto)`결과의 객체의 필드값

<br>

### **P.147**

**JPA에서의 쿼리 사용**

```
@Query("SELECT p From Posts p ORDER BY p.id DESC")
  List<Posts> findAllDesc();
```

- `Spring Data JPA`에서도 `쿼리` 사용 가능하다는 예제..
- Repository Interface에서 사용한다.

---

**저자의 조언**

> 큰 규모의 프로젝트 에서는 데이터의 조회에 외래키의 조인 등 여러가지 `복잡한 조건`으로 인하여 `Entity클래스`만으로는 `처리하기 어려운 조건의 조회`를 사용하는 경우가 있다.
>
> 이런 경우 `조회용 프레임워크`를 추가로 사용을 하며 대표적인 종류는 3가지로 `Querydsl`, `jooq`, `MyBatis`등이 있다.
>
> 사용 방식은 조회는 위의 `조회용 프레임워크`를 통하여 하고 `등록`, `수정`, `삭제`의 경우에는 `SpringDataJpa`를 통해서 진행하는 방식으로 사용한다.
>
> 그 중에서 `Querydsl`을 추천하며 다음과 같다고 한다.
>
> > 1. 타입의 안정성을 보장한다.
> >    > 단순히 `문자열`로 `쿼리를 생성하는 것이 아니라`, `메소드를 기반으로 쿼리를 생성`하여 `오타나 존재하지 않는 컬럼명`을 명시할 경우 IDE에서 `자동으로 검출`된다.  
> >    > 해당 장점은 `Jooq에서도 지원`하는 장점이지만 `MyBatis에서는 지원하지 않는다.`
> > 2. 국내의 많은 회사에서 사용중이다.
> >    > 다양한 큰 서비스 기업에서 Querydsl을 적극 사용중이다
> > 3. Reference가 많다.
> >    > 사용하는 곳이 많고 기술공유를 많이 하는 기업들이다 보니 그만큼 국내 자료가 많고 커뮤니티 형성이 잘 되어있다.  
> >    > 이는 장애나 이슈가 발생시 그에 대한 답변을 쉽게 얻을 수 있다는 장점이 있다.

<br>

### **P.148**

**Example Code**

```
  @Transactional(readOnly = true)
  public List<PostsListResponseDto> findAllDesc() {
    return postsRepository.findAll().stream()
        .map(PostsListResponseDto::new)
        .collect(Collectors.toList());
  }
```

> `@Transactional(readOnly = true)`
>
> > `트랜잭션의 범위는 유지`하면서 `조회기능`만 남겨두는 Annotation으로 `조회속도가 개선,상향` 되기 때문에 `등록`, `수정`, `삭제` 기능이 전혀 `없는 메소드`에 사용하면 좋다.
>
> `.map(PostsListResponseDto::new)`
>
> > `.map(posts -> new PostsListResponseDto(posts))`와 동일 하다
>
> PostsListResponseDto로 변환한 이후 PostsListResponseDto List로 만들어서 반환하는 메소드.

<br>

### **P.150**

**Model**

> - `org.springframework.ui.Model`에 속해 있다
> - 서버 템플릿 엔진에서 사용 할 수 있는 객체를 저장 한다.
> - 여기서 addAttribute메소드를 통하여 posts를 저장한 이후 index.mustache에 전달이 가능하다

<br>

### **P.159**

```
  @Transactional
  public void delete(Long id) {
    Posts posts = postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

    postsRepository.delete(posts);
  }
```

> jpaRepository에서는 이미 delete 메소드를 지원하고 있어 해당 부분을 사용하면 편하다.
>
> `Entity`에서 `파라미터로의 삭제`도 가능하고, `deleteById메소드`를 활용해 `id로의 삭제`도 가능하다.
>
> 삭제 전에 실제 존재하는 데이터인지는 확인 정도는 하자...!

<br>

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

## **Chapter. 06 : AWS서버 환경을 만들어 보자 - AWS EC2**

### **P.225**

**24시간 작동하는 서버의 선택**

> 1.  집에 PC를 24시간 구동
> 2.  호스팅 서비스를 이용 [Cafe24가 대표적]
> 3.  클라우트 서비스를 이용 [AWS, NCP, AZURE, GCP등등]

---

**비용적으로 유리한 방법**

> 집에서 서버를 구동 또는 호스팅 서비스가 유리

---

**몰리는 트래픽의 처리가 필요한경우**

> 클라우드 서비스가 유리

<br>

## **Chapter. 07 : AWS에서 DB환경 만들기 - RDS**

### **P.290**

> 테이블 생성의 경우 인코딩 설정 변경전에 생성하면 안된다.  
> 만들어질 당시의 설정값을 그대로 유지하고 있어, 자동 변경이 되지 않고 강제로 변경해야 한다.
> 테이블은 모든 설정이 끝나느 이후 생성하도록 한다.

### **P.291**

**EC2에서 RDS를 통한 접근**

1. EC2 접속
2. MySQL 설치
3. mysql -u `username` -p -h `AWS Endpoint`
4. PW입력

<br>

## **Chapter. 08 : EC2서버에 프로젝트 배포하기**

**EC2에 프로젝트 Clone하기**

1. 깃 설치
   1. `sudo yum install git`
2. git 설치 상태 확인
   1. 책에서는 `git --version`을 통해 확인하였다.
3. clone해 넣어둘 디렉토리 생성
   1. `mkdir ~/app && ~/app/step1`
   2. `cd ~/app/step1`
4. 프로젝트 clone
   1. git clone git페이지주소
   2. clone한 프로젝트 실행해보기
      1. 제작한 TestCase를 실행해해봄
         1. `cd 프로젝트명`
         2. `./gradlew test`
            1. 실패의 경우 수정이후 `git pull`
            2. gradlew의 Permission denied인 경우 `chmod +x ./gradlew`

---

**배포란?**

> 작성한 코드를 실제 서버에 반영하는 것을 의미한다.

---

**책의 예제의 배포 과정은?**

1. Git Clone 과 Git Pull을 통해 새 버젼의 프로젝트를 받는다.
2. Gradle 또는 Maven을 통해 해당 프로젝트를 테스트 및 빌드한다.
3. EC2서버에서 해당 프로젝트 실행 및 재실행한다.

---

**배포 스크립트 만들기**

> 위 예제의 배포과정을 배포할 때마다 개발자가 하나 하나 명령어를 실행하는 것은 불편함이 많다, 그렇기에 이를 쉘 스크립트로 작성해 스크립트를 실행시켜 앞의 과정이 한번에 진행되도록 설정하는 것

```
#!/bin/bash

REPOSITORY=/home/ec2-user/app/step1
PROJECT_NAME=SpringBoot-With-Aws

cd $REPOSITORY/$PROJECT_NAME/
git pull
./gradlew build
cd $REPOSITORY
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/
CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)
if [ ~z "$CURRENT_PID" ]; then
        echo "> 현재 구동중인 어플리케이션이 없으므로 종료하지 않습니다."
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

JAR_NAME=$(ls -tr $REPOSITORY/ | grep .jar | tail -n 1)
nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &
```

1. `#!/bin/bash`
   - `bash`로 쉘을 실행시키는 의미다.
2. `REPOSITORY=/home/ec2-user/app/step1`와 `PROJECT_NAME=SpringBoot-With-Aws`
   - `$REPOSITORY`, `$PROJECT_NAME` 으로 호출을 하기 위한 변수 생성 및 경로값을 대입
3. `cd $REPOSITORY/$PROJECT_NAME/`후 `git pull`
   - 프로젝트 디렉토리에 이동한 이후 버전 최신화
4. `./gradlew build`
   - 프로젝트 빌드
5. `cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/`
   - build를 진행한 이후 나온 프로젝트 jar파일을 $REPOSITORY에 복사
6. `CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)`
   - 기존에 수행중이던 스프링 부트를 종료한다
   - `pgrep`은 process id 만 추출하는 명령어이다.
   - `-f` 옵션은 프로세스 이름으로 찾는 기능이다.
7. `if ~ else ~ fi`
   - 현재 구동중인 프로세스가 존재하는지 여부 판단을 위한 조건문
   - `process id` 값을 보고 프로세스가 존재하면 해당 프로세스를 종료한다
8. `JAR_NAME=$(ls -tr $REPOSITORY/ | grep .jar | tail -n 1)`
   - 새로 실행할 jar파일명을 찾아 JAR_NAMEdp sjgdma.
   - 여러 jar파일이 생기기 때문에 tail -n을 하여 가장 최신의 jar파일을 변수에 저장한다.
9. `nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &`
   - 발견한 jar파일명으로 해당 jar을 nohup으로 실행한다.
   - 그냥 java -jar을 통해 실행하는 경우 사용자가 터미널 접속 종료시에 Application도 같이 종료가 되기 떄문에 nohup을 활용
10. 이후 생성한 스크립트 파일 권한 변경
    - `chmod +x ./스크립트 파일`

---

**배포 스크립트 수정 - OAuth 적용**

> 위의 스크립트를 사용시에 `Spring Security`의 값을 가져올 수 있는 `application-oauth.properties`가 존재하지 않기 때문에 실패를 한다.  
> 하지만 `application-oauth.properties`의 경우 Git Ignore가 되어 있기 때문에 직접적으로 운영, 개발서버에 추가하고 설정해줘야한다.

1. `희망위치/application-oauth.properties`생성
2. 개발된 내용의 oauth복사
3. 배포스크립트 내용 수정

   ```
   //수정전
   nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &

   //수정후
   nohup java -jar \
   -Dspring.config.location=classpath:/application.properties, 생성한파일위치/application-oauth.properties \
   $REPOSITORY/$JAR_NAME 2>&1 &
   ```

**`-Dspring.config.location`**

- 스프링 설정 파일 위치를 지정한다.
- 지정된 properties마다 따로 지정이 가능하다
- classpath가 붙으면 jar안에 있는 resources디렉토리를 기준으로 경로가 생성된다.
- application-oauth.properties의 경우에는 외부에 파일이 있기 때문에 절대 경로를 사용한다.

---

**RDS서버에 테이블 생성**

> 현재 책의 예제의 테이블은 2종류가 있로 `JPA를 활용하는 엔티티 테이블` 그리고 DB를 세션으로 사용하고 있는 `세션테이블`이 존재한다. 이 2종류의 테이블을 만들 수 있는 쿼리는 다음과 같다.
>
> 1. `엔티티 테이블`은 `Application 실행시키며 생성되는 쿼리`를 사용한다.
> 2. `세션 테이블`은 프로젝트에 있는 `schema-mysql.sql`파일에서 추출한다

---

**EC2서버에서 RDS로 접속을 위한 `application-real-db.properties`설정**

```
spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mariadb://AWS Endpoint:Port/Database Name
spring.datasource.username=DB ID
spring.datasource.password=DB PW
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
```

1. `spring.jpa.hibernate.ddl-auto=none`
   - JPA로 테이블이 자동 생성되는 옵션을 None(생성하지 않도록) 한다.
   - RDS에는 실제 운영에서 사용될 테이블이기 떄문에 절대로 스프링 부트에서 새로 만들도록 하지 않아야 한다.
   - 이 옵션을 설정하지 않으면 테이블이 모두 새로 생성될 수 있기에 주의해야 한다.

---

**DB설정 추가에 따른 배포스크립트의 수정영역**

```
nohup java -jar \
        -Dspring.config.location=classpath:/application.properties, /home/ec2-user/app/application-oauth.properties, /home/ec2-user/app/application-real-db.properties, classpath:/application-real.properties \
        -Dspring.profiles.active=real \
        $REPOSITORY/$JAR_NAME 2>&1 &
```

1. `application-real`와 `application-real-db`설정 파일의 추가
2. `-Dspring.profiles.active=real`
   - `application-real.properties`항목을 활성화 시킨다.
   - `application-real.properties`의 spring.profiles.include=oauth,real-db옵션 때문에 real-db역시 같이 활성화 대상에 포함된다.

---

<br>

## **Chapter. 09 : 코드가 푸시되면 자동으로 배포하기 - Travis CI를 통한 배포 자동화**

> 24시간 365일 운영되는 서비스에서는 `CI / CD` 환경 구축은 선택이 아닌 필수이며, 책에서의 예제는 `Travis CI`를 활용해 구축할 예정이다.  
> 또한 `8장`에서 직접 경험을 해봤기 때문에 알 수 있듯이 `배포 스크립트`를 통한 배포는 개발자가 직접 실행을 해야하는 불편함이 있기 때문에 더더욱 필요한 사항이다.

<br>

**`CI` / `CD`**

> `CI`란 `Continuous Integration`이라 칭하며 `지속적 통합`이라 칭한다.  
> 코드 버전 관리(VCS)를 하는 시스템(`Git`또는 `SVN`)에 `PUSH`가 되면 자동으로 테스트와 빌드가 수행되는 것을 의미한다.

> `CD`란 `Continous Deployment`라 칭하며 `지속적인 배포`라고 칭한다.  
> `CI`의 과정(`테스트`와 `빌드`)가 전부 이뤄지면 `빌드 결과를 바탕으로 자동으로 운영서버에 무중단 배포까지 진행되는 과정`을 의미한다.

<br>

**`CI`와 `CD`의 역사? 개념이 나오게 된 배경**

> 현대의 웹 서비스의 경우 하나의 프로젝트를 다수의 개발자가 함께 개발을 진행하고 있다.  
> 그러다 보니 각자가 개발한 코드를 합쳐야 할 때 마다 큰 일 이었고, 병합일(코드를 Merge만 하는날)을 정해서 할 정도였다.
>
> 이런 작업은 생산성에 저하를 하는 요소이기 떄문에 CI환경이 구축된 것이다.
>
> 또한 CD의 경우에도 한두대의 서버에 개발자가 수동으로 배포는 할 수 있지만, 수십에서 수백대의 서버에 개발자가 직접 배포를 해야 하는 상황에서나 긴급하게 당장 배포하는 일을 수동으로만은 할 수 없게 되었고, 그렇기에 CD역시 구축을 하게 되었다.
>
> CI / CD 환경을 구축하게 되면 개발자가 갖게 되는 장점은 `개발자가 개발에만 집중 할 수 있다`는 장점이 생기게 된다.

<br>

**마틴 파울러의 CI에 대한 4가지 규칙**

> `CI도구를 도입했다고 해서 CI를 하고 있는 것은 아니다`를 명심하며, 마틴 파울러의 4가지 규칙을 기억하자.

1. `모든 소스코드가 살아(현재 실행되고) 있고` 누구든 `현재의 소스에 접근 할 수 있는 단일 지점을 유지`해야 한다.
2. `빌드 프로세스를 자동화` 해서 누구든 `소스로부터 시스템을 빌드하는 단일 명령어`를 `사용`할 수 있도록 한다.
3. `테스팅을 자동화` 해서 단일 명령어로 `언제든지 시스템에 대한 건전한 테스트 수트를 실행` 할 수 있게 한다.
4. 누구든 현재 실행 파일을 얻으면 지금까지 `가장 완전한 실행 파일을 얻었다는 확신`을 하게 한다.

> 여기서 3번 항목인 `테스팅 자동화`는 특히나 중요하다. 지속적인 통합을 하기 위해서는 이 프로젝트가 현재 `완전한 상태를 보장`해야 하기 위한 `테스트 코드`가 구현되어 있어야 하기 떄문이다.

<br>

**Travis CI?**

> Github에서 제공하는 무료 CI 서비스이며, Private Repository에는 다양한 유료플랜을, Public Repository에는 무료 플랜을 제공하고 있다.

<br>

**Travis CI 웹서비스 설정 방법**

1. https://travis-ci.org 접속 및 계정 생성
2. profile → settings
3. Repositories에서 Project 선택
4. 필자의 경우 `Build Pushed branches`와 `Build Pushed Pull Requests`에 대해서만 설정하였다.

**CI설정을 진행할 `.travis.yml` 파일 제작**

> 해당 파일은 `build.gradle`과 같은 파일에 있어야 한다.

```
//1번
language: java

//2번
jdk:
  - openjdk8

//3번
branches:
  only:
    - main

//4번
cache:
  directories:
    - '$HOME/.m2/repository'
    - '$HOME/.gradle'

//5번
script: "./gradlew clean build"

//6번
notifications:
  email:
    recipients:
      - whddnjs822@gmail.com
```

1. 자바 언어 설정
2. JDK 버전 설정
3. Travis CI를 어떤 브랜치가 푸쉬 될 때 수행할지 지정하며, 예제는 main브랜치가 푸쉬될때만 수행한다.
4. gradle을 통해 의존성을 받게 되면 이를 해당 디렉토리에 캐시하여 같은 의존성은 다음 배포때 부터 다시 받지 않도록 한 설정이다.
5. master브랜치에 푸시되었을 때 수행하는 명령어이며 프로젝트 내부에 둔 gradlew를 통해 clean & build를 수행한다.
6. Travis CI실행 완료시 자동으로 설정한 메일로 알람이 오도록 하는 설정

<br>

**AWS S3**

> S3란 AWS에서 제공하는 일종의 파일서버중 하나이다.
> 이미지 파일을 비롯하여 정적 파일을 관리하거나 예제에서 진행하는 것처럼 배포 파일들을 관리하는 등의 기능을 지원한다.
> 보통 이미지 업로드를 구현한다면 S3를 활용해 구현하는 경우가 많다.

<br>

**Travis CI와 AWS S3를 활용한 연동 구조**  
![이미지](https://raw.githubusercontent.com/smpark1020/tistory/master/CI%20%26%20CD/%5BTravis%20CI%5D%20%EC%BD%94%EB%93%9C%EA%B0%80%20%ED%91%B8%EC%8B%9C%EB%90%98%EB%A9%B4%20%EC%9E%90%EB%8F%99%EC%9C%BC%EB%A1%9C%20%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0%203%20-%20Travis%20CI%EC%99%80%20AWS%20S3%20%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0/1.jpg)

> Travis CI를 활용하게 될 경우 다음과 같은 구조가 이뤄지게 된다  
> 실제 배포에는 `AWS의 CodeDeploy` 서비스를 활용해야 한다. 하지만 S3연동이 먼저 필요한 이유는 `Jar파일의 전달`이 필요하기 때문이다.  
> `CodeDeploy`의 경우에는 `저장`기능이 없다. 그렇기 떄문에 Travis CI가 빌드한 결과물을 받아서 `CodeDeploy`가 가져갈 수 있도록 보관 할 수 있는 공간이 필요하며, 이때 `S3`를 활용한다.

> 추가적으로 `CodeDeploy`에서 빌드도 하고 배포도 할 수 있지만, `GitHub의 코드를 가져오는 기능`은 지원하지 않는다. 이렇게 되면 두개가 합쳐지기 때문에 빌드가 필요 없이 과거 버전에 대한 배포를 할떄 이전 빌드정보가 없기 때문에 대응하는게 힘들다.

> `빌드`와 `배포`가 분리되어 있으면 `과거에 빌드`되어 만들어진 파일(Jar, War)를 `재사용`하면 되지만 `CodeDeploy`가 모두 진행하게 되면

<br>

**AWS의 IAM을 사용하는 이유와 IAM에 대해서**

> 일반적으로 `AWS 서비스`에 `외부 서비스가 접근 할 수 없다.` 그렇기 때문에 `접근 가능한 권한을 가진 Key`를 `생성`해서 `사용`해야 한다.  
> AWS에서는 이러한 `인증과 관련된 기능을 제공하는 서비스`를 `IAM(Identity and Access Management)`가 존재한다.  
> IAM은 `AWS에서 제공하는 서비스`의 `접근 방식`과 `권한을 관리`한다.

> 예제에서 사용하는 `Travis CI`가 `AWS`의 `S3`와 `CodeDeploy`에 있도록 하기 위해서도 발급이 필요하다.

**AWS Key발급 방법(Travis CI Deploy적용방식)**

1. `AWS웹 콘솔`에서 `IAM`을 검색해 항목으로 이동한다.
2. 좌측 사이드바에서 `사용자` → `사용자 추가` 버튼을 차례로 클릭한다.
3. `사용자 이름 기입`과 `액세스키-프로그래밍 방식` 선택후 다음
4. `기존 정책 직접 연결` 선택 후 `AmazonS3FullAccess`, `AWSCodeDeployFullAccess`항목을 추가후 다음
   1. 실제 서비스 회사의 경우에는 `S3`와 `CodeDeploy`를 `분리해서 관리`하기도 하나, 예제의 경우에는 간단히 두개를 동시에 관리하도록 한다.
5. `태그`의 경우 키에 `Name`을 지정하고 값도 지정하나 예제의 경우에는 IAM 사용자이름과 일치시켰다.
6. 이후 정보확인을 하고 사용자 생성을 하면 `엑세스 키`와 `비밀 엑세스`키가 생성된다.

<br>

**Travis CI에서 Key설정**

1. `Travis CI`의 `Repository`별 Settings이동
2. `Environment Variables`항목에서 `NAME`을 `AWS_ACCESS_KEY`, `AWS_SECRET_KEY`로 발급받은 KEY값을 넣고 추가한다.
3. 해당 항목은 `.travis.yml`항목에서 `$AWS_ACCESS_KEY`, `$AWS_SECRET_KEY`라는 항목으로 활용이 가능하다

<br>

**S3 버킷 생성**

> S3는 일종의 파일서버로 순수하게 파일들을 저장하고 접근 권한을 관리, 검색등을 지원하는 역할을 한다.  
> S3는 보통 게시글을 쓸 때 나오는 `첨부파일 등록을 구현`할때 많이 이용된다.  
> Travis CI에서 생성된 Build파일을 저장하도록 예제는 구성을 하였다.

1. 버킷만들기 버튼을 통한 페이지 이동
2. 버킷 이름 생성
3. 버킷의 퍼블릭 액세스 차단 설정의 경우에는 `모든 퍼블릭 액세스 차단`으로 설정한다.
   1. 액세스의 차단이 중요한 이유는 실제 빌드후 배포해야 할 파일이 퍼블릭일 경우 누구나 내려받을 수 있기 떄문이다.
   2. 예제의 경우 IAM사용자를 통해 발급받는 키를 사용하기 때문에 접근 가능하다.

<br>

**.travis.yml추가**

> 다음과 같은 항목이 추가되었다.

```
before_deploy:
  - zip -r jong1-springboot-webservice *
  - mkdir -p deploy
  - mv jong1-springboot-webservice.zip deploy/jong1-springboot-webservice.zip

deploy:
  - provider: s3
    access_key_id: $AWS_ACCESS_KEY
    secret_access_key: $AWS_SECRET_KEY
    bucket: jong1-springboot-build
    region: ap-northeast-2
    skip_cleanup: true
    acl: private
    local_dir: deploy
    wait_until-deployd: true
```

1. `before_deploy`
   1. deploy명령어가 실행되기전에 수행한다.
   2. CodeDeploy는 Jar파일을 인식하지못하므로 Jar + 기타 설정 파일들을 모아서 압축(zip)한다.
2. `zip -r jong1-springboot-webservice *`
   1. 현재위치의 모든 파일을 zip파일로 압축한다.
3. mkdir -p deploy
   1. deploy라는 디렉토리를 Travis CI가 실행중인 위치에 생성한다.
4. mv jong1-springboot-webservice.zip deploy/jong1-springboot-webservice.zip
   1. zip파일을 deploy디렉토리로 이동
5. deploy
   1. S3로 파일 업로드 혹은 CodeDeploy로 배포 등 외부 서비스와 연동될 행위들을 선언한다.
6. local_dir: deploy
   1. 앞에서 생성한 deploy 디렉토리를 지정한다.
   2. 해당위치의 파일들만 S3로 전송한다.

<br>

**Travis CI와 AWS S3, CodeDeploy 연동하기**

> CodeDeploy를 연동 받을 수 있게 하기 위해 역할의 추가가 필요하다.

> IAM에서 사용자와 역할은 차이가 존재한다. 역할의 경우에는 AWS서비스에만 할당할 수 있는 권한을 의미하며`EC2`, `CodeDeploy`, `SQS`등등
> 사용자의 경우에는 `AWS 서비스 외`에 사용할 수 있는 권한으로 `로컬PC`, `IDC서버등이있다` 현재의 경우 EC2에서 활용할 것이기 떄문에 역할로 처리해야 한다.

1. IAM이동
2. 역할 → 역할만들기
3. AWS서비스, EC2 선택후 다음
4. 권한은 AmazonEC2RoleforAWSCodeDeploy 선택 후 다음
5. 역할이름 기입및 태그Key, Value입력
6. EC2에서 적용을 희망하는 인스턴스 우클릭 → 보안 → IAM역할 수정 → 제작한 IAM역할로 업데이트
7. EC2 재부팅
   1. 재부팅을 하지 않을 경우 역할이 정상적으로 적용되지 않기 떄문

<br>

**CodeDeploy 에이전트 설치하기**

1. EC2접속
2. aws s3 cp s3://aws-codedeploy-ap-northeast-2/latest/install . --region ap-northeast-2
   1. 다운로드에 성공하면 `download: s3://aws-codedeploy-ap-northeast-2/latest/install to ./install` 다음과 같은 echo를 리턴받는다.
3. `chmod +x ./install`을 하여 다운로드 받은 설치파일의 권한증가
4. `sudo ./install auto`스크립트로 다운로드 진행
   1. `/usr/bin/env: ruby: No such file or directory` 오류가 발생하며, ruby가 설치되어있지 않아 발생하는 오류이다.
   2. 나의 경우 `sudo yum install ruby`를 통하여 `ruby`의 설치를 진행하였다.
5. `sudo service codedeploy-agent status`를 통하여 서비스 실행 여부 확인
   1. `The AWS CodeDeploy agent is running as PID XXXX` 서비스 실행 문구이다.

<br>

**CodeDeploy를 위한 권한 생성**

> CodeDeploy에서 EC2에 접근하려면 마찬가지로 권한이 필요하다. AWS의 서비스를 통한 접근이기에 IAM에서 역할의 생성으로 필요하다

1. IAM 역할만들기
2. 사용사례에서 CodeDeploy 설정 → `AWSCodeDeployRole` 설정 확인
3. 역할이름, 태그설정

<br>

**AWS의 배포 생태계**

> 근데 일단 해당 저서가 몇년전에 만들어 진 것 이다보니 현재 TIL을 작성하는 2022년을 기준으로 맞는지는 모르겠네...

> 유명한 3종류가 존재하며 다음과 같다.

1. `Code Commit`
   - 깃허브 처럼 코드저장소의 역할을 한다.
   - 프라이빗 기능을 지원한다는 강점이 있지만 현재 Github에서 Private지원을 하고 있어 거의 사용하지 않는다.
2. `Code Build`
   - Travis CI와 마찬가지로 빌드용 서비스이다.
   - 멀티모듈을 배포해야 하는 경우에 사용 해 볼법 하지만 규모가 있는 서비스에서는 대부분 `젠킨스` / `심시티` 등을 이용하는등 외부 서비스를 사용하기 때문에 사용할 경우가 거의 없다.
3. `CodeDeploy`
   - AWS의 배포 서비스이다
   - `Code Commit`과 `Code Build`의 경우에는 다른 서비스들로 대체제가 존재하지만 `CodeDeploy`의 경우에는 대체제가 없다.
   - `오토 스케일링 그룹 배포`, `블루 그린 배포`, `롤링 배포`, `EC2 단독 배포`등 많은 기능을 지원한다.

<br>

**CodeDeploy 생성방법**

1. CodeDeploy 이동 및 Application생성
2. 어플리케이션 설정 및 EC2의 경우 컴퓨터 플랫폼에서 `EC2/온프레미스`를 사용한다.
3. 이후 배포그룹을 생성
   1. 배포그룹명기입 및 이전에 생성한 서비스 역할 선택
   2. 배포 유형의 경우 `내가 배포할 서비스가 2대 이상이라면 블루 / 그린`을 선택한다. 현재는 EC2한대만 이기 떄문에 `현재위치` 항목으로 진행한다.
   3. 환경구성은 EC2를 사용하기 떄문에 Amazon EC2 인스턴스를 사용한다.
   4. 배포 설정의 경우 CodeDeployDefault.AllAtOnce 항목 선택
      1. 항목이 `CodeDeployDefault.OneAtATime`, `CodeDeployDefault.HalfAtATime`, `CodeDeployDefault.AllAtOnce`이 존재한다.
      2. 각각 배포때 10대의 배포라면 한번에 한대씩 배포할지, 전체배포할지 등 조율하는 항목이다
      3. 커스텀으로 조절도 가능하다.
   5. 로드밸런싱은 현재 필요없기에 해제한다.

<br>

**Travis CI, S3, CodeDeploy 연동 순서**

> 해당 내역은 빌드한 파일을 연동까지만의 과정이며 `배포(실행)`하는 과정은 빠져있다..

1. S3에서 Zip파일을 넘겨받을 Directory 추가
   1. Travis CI의 Build가 끝나고 S3에 Zip파일이 전송된다.
   2. Zip파일을 생성한 Directory로 복사후 압출을 푼다.
   3. 설정은 .travis.yml에서 진행하며, AWS CodeDeploy의 경우에는 `appspec.yml`에서 진행한다
2. `appspec.yml`작성
3. `.travis.yml`에서 `CodeDeploy` 설정 추가
4. 설정 Push 이후 `CodeDeploy 웹콘솔` 에서 배포내역 확인

**`appspec.yml` 작성**

```
version: 0.0
os: linux
files:
- source: /
   destination: /home/ec2-user/app/step2/zip
   overwrite: yes
```

1. `version: 0.0`
   1. CodeDeploy버전을 의미한다.
   2. 프로젝트 버전이 아니므로 0.0 외에 다른 버전을 사용하는 경우 오류가 발생한다.
2. `source : /`
   1. CodeDeploy에서 전달을 해 준 파일 중 `destination`으로 이동시킬 대상을 지정한다.
   2. `루트경로(/)` 를 지정하면 전체 파일을 의미한다.
3. `destination: 경로`
   1. source에서 지정한 파일을 받을 위치를 의미한다.
   2. 이후 Jar을 실행하는 등은 destination에서 옮긴 파일들로 진행한다.
4. `overwrite`
   1. 기존에 파일들이 있으면 덮어쓸지를 결정한다.
   2. 현재 `yes`로 지정을 하였기 때문에 파일들을 덮어쓰게 된다.

<br>

**`.travis.yml`에서 `CodeDeploy` 설정 추가**

```
  - provider: codedeploy
    access_key_id: $AWS_ACCESS_KEY
    secret_access_key: $AWS_SECRET_KEY
    bucket: jong1-springboot-build
    key: jong1-springboot-webservice.zip
    bundle_type: zip
    application: jong1-springboot-webservice
    deployment_group: jong1-springboot-webservice-group
    region: ap-northeast-2
    wait-until-deployed: true
    on:
      all_branches: true
```

1. `bucket: jong1-springboot-build`
   - AWS의 S3 버킷에 설정한 명칭
2. `key: jong1-springboot-webservice.zip`
   - 빌드 파일을 압축해서 전달한다.
3. `bundle_type: zip`
   - 압축 확장자
4. `application: jong1-springboot-webservice`
   - CodeDeploy 웹 콘솔에서 등록한 Application 명
5. `deployment_group: jong1-springboot-webservice-group`
   - 웹 콘솔에서 등록한 CodeDeploy 배포 그룹
6. `on.all_branches: true`
   - `S3 Bucket` 설정때와 마찬가지로 작업을 진행햇을때 작업이 정상적으로 마치질 않았다.
   - 빌드는 성공했으나 파일이 없었고, AWS의 CodeDeploy 배포내역에도 존재하지 않는현상.
   - 브랜치설정을 추가한후 다시 빌드 했을 경우 정상적인 파일 배포가 가능해졌다.

**배포 자동화(실행)**

> 쉘 스크립트를 인텔리제이에서 제작할 때 `BashSupport`를 활용하면 보조를 받을 수 있다.

1. 쉘 배포 스크립트 프로젝트에서 생성
2. `.travis.yml` 수정
3. `appspec.yml` 수정

**쉘 배포 스크립트 변경사항**

```
# 기존
$REPOSITORY/$JAR_NAME 2>&1 &

# 변경
$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
```

1. nohup 실행을 하게 될 경우 `CodeDeploy`는 `무한 대기`를 하게 된다.
2. 해당 이슈를 해결 하기 위해 nohup.out파일을 표주준 입출력용으로 별도 사용해야 한다.
   1. 이렇게 사용하지 않을 경우에는 nohup.out파일이 생기지 않으며 `CodeDeploy`로그에 표준 입력이 출력된다.
   2. nohup이 끝나기 전 까지는 `CodeDeploy`도 끝나지 않으니 꼭 이렇게 해야 한다.

**`.travis.yml`의 `before-deploy` 수정**

```
before_deploy:
  - mkdir -p before-deploy
  - cp scripts/*.sh before-deploy/
  - cp appspec.yml before-deploy/
  - cp build/libs/*.jar before-deploy/
  - cd before-deploy && zip -r before-deploy *
  - cd ../ && mkdir -p deploy
  - mv before-deploy/before-deploy.zip deploy/jong1-springboot-webservice.zip
```

> 실제 필요한 파일은 `스크립트 실행(sh)파일`, `appspec.yml`, `jar` 총 세가지만 필요하나 현재 모든 파일을 zip으로 올리던 상황. 필요한 파일만 담도록 수정한것.

1. `mkdir -p before-deploy`
   1. 기존 `deploy`파일을 사용하는게 아니라 사전에 파일을 정리할 Directory 새로 생성
2. `cp scripts/*.sh before-deploy/`
   1. `쉘 스크립트 실행파일(sh)`을 `before-deploy`로 복사
3. `cp appspec.yml before-deploy/`
   1. `appspec.yml`을 `before-deploy`로 복사
4. `cp build/libs/*.jar before-deploy/`
   1. `jar`파일들을 `before-deploy`로 복사
5. `cd before-deploy && zip -r before-deploy *`
   1. `before-deploy`로 이동 및 `before-deploy`라는 명칭으로 디렉토리내 모든 파일 zip
6. `cd ../ && mkdir -p deploy`
   1. 상위 디렉토리 이동 및 `deploy`디렉토리 생성
7. `mv before-deploy/before-deploy.zip deploy/jong1-springboot-webservice.zip`
   1. `before-deploy`에 생성한 zip파일을 `deploy`디렉토리에 서비스 명칭으로 zip파일 명칭 변경해서 생성

<br>

**`appspec.yml` 수정**

```
permissions:
  - object: /
    pattern: "**"
    owner: ec2-user
    group: ec2-user

hooks:
  ApplicationStart:
    - location: deploy.sh
      timeout: 60
      runas: ec2-user
```

1. `permissions:`
   1. `CodeDeploy`에서 `EC2`서버로 넘겨준 파일들을 모두 `ec2-user`권한을 갖도록 설정.
2. `hooks`
   1. `CodeDeploy`배포 단계에서 실행할 명령어를 지정한다.
   2. `ApplicationStart`라는 단계에서 deploy.sh를 ec2-user 권한으로 실행한다.
   3. `timeout: 60`설정으로 인해서 스크립트 실행이 60초 이상 수행되면 실패가 된다.
      1. 무한정 기다릴순 없으므로 시간제한은 필수다.

<br>

**`CodeDeploy`를 통한 배포 로그 확인 방법**

1. `/opt/codedeploy-agent/deployment-root`에서 `9025f408-3a26-4083-862d-a0f124a5b95d` 이런 형식의 Directory의 역할
   1. 사용자의 `CodeDeploy ID` 이며, 고유한 ID로 각자 다른 ID가 발급된다.
   2. 해당 디렉토리에는 `배포를 진행한 단위별로 배포 파일들이 존재`하며, 본인의 `배포 파일을 정상적으로 수신하였는지 확인`이 가능하다
2. `/opt/codedeploy-agent/deployment-root/deployment-logs/codedeploy-agent-deployments.log`
   1. `CodeDeploy`의 로그 파일이 들어가게 된다.
   2. `CodeDeploy`로 이루어지는 배포 내용 중 입 / 출력 내용이 담기게 되며 작성한 `echo`내용도 모두 표기가 된다.

<br>

## **Chap.10 : 24시간 365일 중단 없는 서비스를 만들자**

> `Chap.09`의 내용의 경우 `배포의 자동화` 즉 `CI / CD` 에 대한 학습이었다.  
> 하지만 해당 챕터의 문제점은 배포하는 동안 어플리케이션이 종료가 된다는 문제가 남아 있다.  
> 긴 시간은 아니지만, `새로운 Jar가 실행되기 전`까지 `기존 Jar를 종료`시켜 놓기 떄문에 `짧은 시간 서비스가 중단`된다.
> 하지만 대규모 서비스의 경우 24시간 서비스가 되어야 하기에 `정지`하지 않는다. 해당 챕터에서는 무중단을 하며 서비스를 유지하는 방법에 대한 기록이다.

<br>

**`무중단 배포`에 대하여**

> 예전에는 배포가 팀에서 큰 이벤트 였기에, 코드 병합일을 정하고, 사용자가 적은시간에 배포를 준비했다.  
> 이후 문제가 발생하면 긴급 점검을 하고 , 서비스를 정지해야 하는경우가 많았다.  
> 이후 정지하지 않고 배포할 수 있는 방안을 모색해서 나온것이 `무중단 배포` 이다.
>
> 무중단 배포는 여러가지 방법이 있으나 몇가지가 있다.. (당시 책의 저서 기준)
>
> 1. AWS에서 블루-그린 (Blue-Green) 무중단 배포
> 2. 도커를 활용한 웹서비스 무중단 배포.
> 3. L4스위치를 이용한 무중단 배포
> 4. Nginx를 활용한 무중단 배포

> 책의 예제는 `Nginx`를 활용한 무중단 배포를 진행하며, 사용하는 핵심적인 이유는 `가장 저렴하고 쉽기 떄문`이다.  
> Nginx에서 활용할 기능은 `리버스 프록시`가 될 예정이며 기능의 역할은 `외부의 요청을 받아 백엔드 서버로 요청을 전달`하는 역할이며, 기존에 사용하던 EC2에서 그대로 적용할 수 있고, 추가적인 인스턴스도 불필요하며 클라우드 인프라가 구축되어 있지 않아도 활용할 수 있는 범용적인 방법 이기 때문에 개인서버 또는 사내 서버에서도 동일한 방식으로 구축 할 수 있다.

<br>

**`Nginx`?**

> `Nginx`란 웹 서버, 리버스 프록시, 캐싱, 로드 밸런싱, 미디어 스트리밍 등을 위한 오픈소스 소프트웨어이다.  
> 이전에 `Apache`가 대세였던 자리를 빼았은 웹서버 오픈소스이며, 고성능 웹서버 이기 떄문에 현재는 대부분 `Nginx`를 활용한다.

<br>
 
**`Nginx`를 활용한 무중단 배포 방법**
> 구조는 간단한데 하나의 EC2또는 리눅스 서버에 Nginx 1대 , Application을 2대 사용하는 것이다.

> 아래의 어플리케이션이 현재는 스프링부트를 백엔드로 삼기때문에 스프링부트로 생각하면 편하지만 다른 언어를 통한 백엔드 서버를 구성할떄도 비슷한 방식이 된다.

- Nginx에는 80번, 443번 포트(Http, Https)를 할당한다.
- 어플리케이션 1번은 Exam으로 8081번 포트로 실행한다
- 어플리케이션 2번은 Exam으로 8082번 포트를 실행한다.

1. 일반적인 Production 과정 (1.0 버전일때)
   1. 사용자는 서비스 주소를 통해 접속한다( 일반적인 도메인 그리고 80번 또는 443번 포트를 통한접속)
   2. Nginx는 사용자의 요청을 받아 현재 연결된 어플리케이션 1번 서버로 요청을 전달한다.(현재는 8081번으로 Exam을 생각)
   3. 어플리케이션 2번 서버(8082번 포트)는 연결된 상태가 아니기 떄문에 요청을 받지 못한다.
2. 1.1 버전으로 업그레이드가 필요할 때
   1. 1.1 버전의 배포가 필요하면 어플리케이션 2번 서버에 배포를 한다.
      1. 배포중에도 어플리케이션 1번을 바라보기 떄문에 서비스는 중단되지 않는다.
   2. 배포가 종료되면 스프링 부트 2가 구동중인지 확인한다.
   3. 스프링 부트 2가 정상 구동중이면 `nginx reload`명령어를 통해 어플리케이션 2번 서버를 바라바보게 한다.
      1. `nginx reload`의 경우에는 0.1초 이내에 완료가 된다
3. 1.2 버전으로 다시 업그레이드가 필요할때
   1. 어플리케이션 1번 서버로 배포를 진행한다.
   2. 배포가 안료되면 어플리케이션 1번서버가 구동중인지 확인 후 `nginx reload`를 실행한다.
   3. 이후 요청을 다시 어플리케이션 1번서버가 진행한다.

<br>

### `Nginx`의 설치와 `SpringBoot`연동하기

#### **`Nginx`설치**

**`Nginx` 설치**

```
# 1
sudo yum install nginx

# sudo amazon-linux-extras install nginx1
# EC2에서 nginx를 설치하려 했을때 nginx패키지를 찾지를 못하였으나 해당 명령어로 안내를 하고 있었다.

# 2
sudo service nginx start
# Redirecting to /bin/systemctl start nginx.service 다음과 같이 나오면 성공.
# EC2도메인을 통해 접속해보면 `Welcom to nginx`로 문구가 나온다.
```

**`Nginx`와 `SpringBoot`연동**

```
# 1
sudo vim /etc/nginx/nginx.conf

# 2 location추가
server {
        listen       80;
        listen       [::]:80;
        server_name  _;
        root         /usr/share/nginx/html;

        # Load configuration files for the default server block.
        include /etc/nginx/default.d/*.conf;

        location / {
                proxy_pass http://localhost:8080;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header Host $http_host;
        }

        error_page 404 /404.html;
        location = /404.html {
        }

        error_page 500 502 503 504 /50x.html;
        location = /50x.html {
        }
    }

# 3 Nginx 재시작
sudo service nginx restart

```

1.  nginx의 설정 파일을 연다
2.  server영역에서 기존에 location이 없으나 추가를 한다.

---

1.  `proxy_pass http://localhost:8080;`
    1.  Nginx로 요쳥이 들어오는 경우 8080번 포트로 전달한다.
2.  `proxy_set_header XXX`
    1.  실제 요청 데이터를 header의 각 항목에 할당한다.
    2.  예시로 `proxy_set_header X-Real-IP $remote_addr;`의 경우 X-Real-IP에 요청자의 IP를 저장해서 보낸다

**`SpringBoot`에서의 작업**

1. `Profile`을 확인할 수 있는 `Controller`의 제작
2. `Properties`의 분리

<br>

**`Profile`을 확인 할 수 있는 `Controller`의 제작**

```
@RequiredArgsConstructor
@RestController
public class ProfileController {

  private final Environment env;

  @GetMapping("/profile")
  public String profile() {
    List<String> profiles = Arrays.asList(env.getActiveProfiles());
    List<String> realProfiles = Arrays.asList("real", "real1", "real2");

    String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);

    return profiles.stream().filter(realProfiles::contains).findAny().orElse(defaultProfile);
  }
}
```

> 위와 같은 Controller를 제작하였으며, 해당 제작을 통해 현재 어떤 Profile을 소지중인지 확인이 가능하다.

<br>

**`Properties`의 분리**

```
# application-real1, 2 properties각각 추가 및 포트번호를 다르게 설정한다.
server.port=8081
server.port=8082
```

<br>

**`Nginx`의 설정 수정**

> 프록시 설정을 위해서는 `Nginx`의 설정이 추가적으로 필요하며 다음의 과정으로 이뤄진다. 추가적으로 `Nginx`의 설정파일은 `/etc/nginx/conf.d/` 에 모여있다.

```
# service-url.inc

# 1
cd /etc/nginx/conf.d
sudo vim ./service-url.inc

# 2
set $service_url http://127.0.0.1:8080;

이후 저장
```

```
# nginx.conf

# 1
sudo vim /etc/nginx/nginx.conf.d

# 2 기존코드 아래처럼 수정
server {
        listen       80;
        listen       [::]:80;
        server_name  _;
        root         /usr/share/nginx/html;

        # Load configuration files for the default server block.
        #include /etc/nginx/default.d/*.conf;
        include /etc/nginx/conf.d/service-url.inc;

        location / {
                # proxy_pass http://localhost:8080;
                proxy_pass $service_url;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header Host $http_host;
        }

        error_page 404 /404.html;
        location = /404.html {
        }

        error_page 500 502 503 504 /50x.html;
        location = /50x.html {
        }
    }

# 3 재시작
sudo service nginx restart
```

**`SpringBoot`에서 필요한 쉘 스크립트(sh) 파일**

1. `stop.sh` : 기존 Nginx에 연결되어 있지 않지만, 실행중이던 SpringBoot 종료
2. `start.sh` : 배포할 신규 버전 `Spring Boot`프로즉테를 `stop.sh`로 종료한 `profile`로 실행
3. `health.sh` : `start.sh`로 실행시킨 프로젝트가 정상적으로 실행 되었는지 확인
4. `switch.sh` : `Nginx`가 바라보는 `SpringBoot`를 최신 버전으로 변경
5. `profile.sh` : `1~4번`의 스크립트 파일에서 공용으로 사용할 `profile`과 `포트`체크 로직

**`profile.sh`**

```
#!/usr/bin/env bash

# 쉬고 있는 profile 찾는 기능, : real1이 사용중이라면 real2가 쉬고있으니 real2를 설정

function find_idle_profile()
{
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

  if [ ${RESPONSE_CODE} -ge 400 ] #400보다 큰 값(즉 4, 5오류 의미)

  then
      CURRENT_PROFILE=real2
  else
      CURRENT_PROFILE=$(curl -s http://localhost/profile)
  fi

  if [ ${CURRENT_PROFILE} == real1 ]
  then
    IDLE_PROFILE=real2
  else
    IDLE_PROFILE=real1
  fi

  echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
  IDLE_PROFILE=$(find_idle_profile)

  if [ ${IDLE_PROFILE} == real1 ]
  then
    echo "8081"
  else
    echo "8082"
  fi
}
```

1. `$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)`
   1. 현재 `Nginx`가 바라보고 있는 `SpringBoot`가 정상적으로 수행중인 지를 확인한다.
   2. 응답값을 `HttpStatus`로 받는다.
   3. 정상인 경우 200, 오류인 경우 예외코드로 발생하니, 400 이상은 모두 예외로 보고 real2를 현재 profile로 설정한다.
2. `IDLE_PROFILE`
   1. `Nginx와 연결되지 않은 profile 이다.`
   2. `Spring Boot`프로젝트를 이 profile로 연결하기 위한 반환이다.
3. `echo "${IDLE_PROFILE}"`
   1. `bash` 스크립트는 값을 반환하는 기능이 없다. 그래서 제일 마지막 줄에 echo로 결과를 출력하여 클라이언트가 그 값을 잡아서 사용 할 수 있게 해야한다.
   2. 위의 이유로 인하여 중간에 `echo`를 사용하면 안된다.

**`stop.sh`**

1. `ABSDIR=$(dirname $ABSPATH)`
   1. 현재 `stop.sh` 가 속해 있는 경로를 찾는다.
   2. `2번`항목의 `profile.sh`의 경로를 찾기 위해 사용된다.
2. `source ${ABSDIR}/profile.sh`
   1. 자바의 `import`같은 역할 구문이다.
   2. `stop.sh`에서 `profile.sh`의 `function`을 사용 할 수 있게 된다.

**`start.sh`**

```
#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source $ABSDIR/profile.sh

REPOSITORY=/home/ec2-user/app/step3
PROJECT_NAME=jong1-springboot-webservice

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/zip/*.jar $REPOSITORY/"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 새 Application 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name : $JAR_NAME"

echo "> $JAR_JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME 을 profile=$IDLE_PROFILE 로 실행합니다."
nohup java -jar \
  -Dspring.config.location=classpath:/application.properties,classpath:/application-$IDLE_PROFILE.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
  -Dspring.profiles.active=$IDLE_PROFILE \
  $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
```

1. 기본적인 스크립트는 기존의 `deploy.sh`와 유사하다.
2. 차이점은 `IDLE_PROFILE`을 통해 `properties`을 가져오고 `application-real.properties`를 사용하던 것을 `application-$IDLE_PROFILE.properties`로 수정한것과 -Dspring.profiles.active로 설정을 추가해 준것.

**`health.sh`**

```
#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

IDLE_PORT=$(find_idle_port)

echo "> Health Check Start!"
echo "> IDLE_PORT: $IDLE_PORT"
echo "> curl -s http://localhost:$IDLE_PORT/profile "
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
  UP_COUNT=$(echo ${RESPONSE} | grep 'real' | wc -l)

  if [ ${UP_COUNT} -ge 1 ]
  then # $up_count >= 1 ("real" 문자열이 있는지 검증)
      echo "> Health check 성공"
      switch_proxy
      break
  else
      echo "> Health check의 응답을 알 수 없거나 혹은 실행 상태가 아닙니다."
      echo "> Health check: ${RESPONSE}"
  fi

  if [ ${RETRY_COUNT} -eq 10 ]
  then
    echo "> Health check 실패. "
    echo "> 엔진엑스에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 10
done
```

1. `Nginx`와 연결되지 않은 포트로 스프링 부트가 잘 수행되었는지를 체크한다.
2. 정상적으로 실행이 완료된 경우 `switch_proxy`를 통해 프록시 설정을 변경한다.
3. `Nginx`프록시 설정 변경은 `switch.sh`에서 진행한다.

**`switch.sh`**

```
#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
  IDLE_PORT=$(find_idle_port)

  echo "> 전환할 PORt: $IDLE_PORT"
  echo "> Port 전환"
  echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

  echo "> Nginx Reload"
  sudo service nginx reload
}
```

1. `  echo "set \$service_url http://127.0.0.1:${IDLE_PORT};"`
   1. 하나의 문장을 만들어 `파이프 라인(|)`으로 넘겨주기 위해 echo를 활용한다.
   2. `Nginx`가 변경할 프록시 주소를 생성한다.
   3. `쌍다옴표("")`를 사용해야 하며, 사용하지 않을 경우 `$service_url`을 그대로 인식하지 못하게 변수를 찾게 된다.
2. `| sudo tee /etc/nginx/conf.d/service-url.inc`
   1. 앞에서 넘겨준 문장을 service-uri.inc에 덮어쓴다.
3. `sudo service nginx reload`
   1. `Nginx`설정을 다시 불러온다.
   2. `restart`와는 다르며, `restart`의 경우에는 잠시 끊기는 현상이 존재하지만 `reload`의 경우에는 끊김없아 디사 불러온다.
   3. `reload`의 경우에는 중요한 설정들은 반영되지 않으며, 반영이 필요할때는 `restart`가 필요하다
   4. `외부설정 파일`인 `service-url.inc`의 경우에는 다시 불러오는 거라 `reload`가 가능하다.

<br>

**버전의 자동 증가**

```
# build.gradle
version '10.1-SNAPSHOT-' + new Date().format("yyyyMMddHHmmss")
```

1. `new Date().format("yyyyMMddHHmmss")`의 추가
   1. new Date로 빌드시 그 시간이 버전에 추가되도록 빌드 된다

<br>

## **Chap.11 : 1인개발시 도움이 될 도구 그리고 조언**

**도구의 소개**

> 저자의 경우 1인 프로젝트를 진행시에 메인이 되는 기능 외에는 전부 외부 서비스에 의존해 개발한다고 한다. 외부서비스 종류들에 대한 기록드리다.
> 서비스 기능들에 대해서는 외부 서비스를 통해서 개발을 진행하는 것에 대해서도 추천한다.

> 댓글기능 활용할 예정이라면 Github를 기반으로 댓글을 남길수 있는 서비스인 [Utteranc](https://utteranc.es/) 를 활용하자

> 방문자 분석 툴의 경우에는 구글 애널리스트를 활용하자.

> `CDN`은 `Content Delivery Network`의 약자로 전 세계에 분산되어 있는 서버 네트워크 이다.  
> `서비스를 운영하던 서비스가 작동되는 서버`가 어디 있느냐에 따라 `한국에서 접속` 할 때, `해외에서 접속`할 때 `속도의 차이`가 발생 할 수밖에 없다.  
> 그리하여 흔히 `정적 컨텐츠`라고 불리는 `JS`,`CSS`, `이미지`등을 전세계에 퍼진 서버에 전달하여 사용자가 `서비스에 접속`시 가장 가까운 서버에서 가져가도록 지원하는 서비스가 `CDN`이며, 목적은 트래픽의 분산이다.
> `CDN`에서 정적 파일들을 전달하다 보니 서비스 서버에서는 API요청만 서버에서 받아주면 되니 비용, 속도가 많이 절약되며 대표적인 서비스는 `클라우드 플레어`이다.

> 뉴스레터 형식의 메일 발송 시스템을 `이메일 마케팅` 종류라고 한다. 해당 서비스로 유명한건 [Mailchimp](https://mailchimp.com/)이다.

**조언?**

1. 1인개발을 할 것이라면 핵심 기능을 제외한 나머지는 모두 무시하고, 욕심을 부리지 마라.
2. 여러가지를 고려하게 되면 결국 구상만 하다가 출시를 못한다.
3. 가장 자신있는 환경으로 개발해라
   1. Exam으로 React, Vue같은거보다 jQuery가 익숙하면 그걸 써라. 그이후 변경하라.
