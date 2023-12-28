package com.jong1.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {
    // Closed Projection을 사용시에는 Entity를 전체조회하지 않는다.
//    String getUsername();

    // Open Projection을 사용시에는 Entity를 전체조회한다.
    @Value("#{target.username + ' ' + target.age}")
    String getUsername();

    int getAge();

}

