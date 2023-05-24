#### form

form의 액션을 막고자 하는 경우 `@submit.prevent`

#### Router에서

> 다음과 같은 형식으로 사용 가능

```javascript
{
    path:'/movie',
    name: 'Movie Detail',
    component: () => import('@views/Movie')
}
```

#### v-if, else 사용시 주의점

v-else와 axios를 활용시 axios에서 통신이 오는 시간동안 v-else의 영역이 보일 수 있슴.
