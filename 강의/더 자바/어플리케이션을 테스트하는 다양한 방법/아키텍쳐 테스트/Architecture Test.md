# Architecture Test

## Architecture Test란?

> 해당 챕터의 아키텍쳐 테스트란 어플리케이션의 패키지 구조, 클래스간의 관계, 참조의 관계 등을 의미  
> 어플리케이션의 코드들이 이런 코드들의 특정한 코드 규율을 따르는지 확인하는 테스트

### 테스트 Tool

- ArchUnit
  - docs : https://www.archunit.org/

## ArchUnit Install

> 의존성 추가만 하면 된다.

- Gradle 추가
  - `implementation 'com.tngtech.archunit:archunit:1.0.1'`
- https://mvnrepository.com/artifact/com.tngtech.archunit/archunit/1.0.1

## ArchUnit 활용법

> 1번과 2번은 변경되어도 상관 없다.

1. 특정 패키지에 해당하는 클래스를 (바이트코드를 통해) 읽어들이고
2. 확인할 규칙을 정의하고
3. 읽어들인 클래스들이 그 규칙을 잘 따르는지 확인한다.

```java
@Test
public void Services_should_only_be_accessed_by_Controllers() {
    // 바이트 코드를 읽는 코드
    JavaClasses importedClasses = new ClassFileImporter().importPackages("com.mycompany.myapp");

    //규칙을 정의하는 코드
    ArchRule myRule = classes()
        .that().resideInAPackage("..service..")
        .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..");

    // 규칙확인
    myRule.check(importedClasses);
}
```

### JUnit5 Exteionsion

- @AnalyzeClasses: 클래스를 읽어들여서 확인할 패키지 설정
- @ArchTest: 확인할 규칙 정의
