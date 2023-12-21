package com.jong1.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 값타입은 기본적으로 Immutable하게 설계해야한다. 그렇기 때문에 생성자로만 생성이 가능하도록 함
 * 그렇기에 Getter만제공하고, Setter는 생성자로..
 * <p>
 * JPA에서 Proxy, Refelction등을 이용해서 객체를 생성하기 때문에 기본생성자가 필요하다.
 * 거기에서, AccessLevel의 경우에는 Protected까지는 JPA에서 지원을 하기 때문에 객체를 마음대로 생성하지 못하게 PROTECTED로 생성함
 */
@Embeddable
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String city;

    private String street;

    private String zipcode;

}
