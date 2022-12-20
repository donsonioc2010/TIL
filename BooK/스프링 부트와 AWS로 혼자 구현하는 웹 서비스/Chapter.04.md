# **Chapter. 04 : 머스테치를 통한 화면 구성하기**

## Index

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
