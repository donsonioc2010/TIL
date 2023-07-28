# Sub Query

## Sub Query지원 함수

- \[NOT\] EXISTS (sub query) : 서브쿼리에 결과가 존재하는 경우 참참참
- {ALL, ANY, SOME} (sub query)
  - ALL : 모두 만족하는 경우 참참참
  - ANY, SOME : 같은 의미로, 조건을 하나라도 만족하는 경우 참
- \[NOT\] IN (sub query) : 서브쿼리의 결과 중 하나라도 같은것이 존재하는 경우 참이다.

## JPA 서브쿼리의 한계

- WHERE, HAVING절에서만 SubQuery의 사용이 가능하다.
- 하이버네이트에서는 SELECT절에서도 활용은 가능하다.
- FROM절의 서브쿼리는 JPQL에서는 불가능하다.
  - 조인으로 문제를 해결할 수 있으면 가능하지만 조인으로 해결을 못하는 경우 해결불가능
    - 쿼리를 분해해서 두번에 걸쳐서 작업을 진행
    - Application 레벨에서 데이터를 조립
    - Native SQL을 사용한다
