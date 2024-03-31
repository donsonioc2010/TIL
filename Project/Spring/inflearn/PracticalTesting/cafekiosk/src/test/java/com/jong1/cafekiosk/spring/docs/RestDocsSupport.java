package com.jong1.cafekiosk.spring.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(RestDocumentationExtension.class)
public abstract class RestDocsSupport {
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper = new ObjectMapper();

    // 다음과 같이 설정하는 경우에는 SpringBootTest를 통해서 테스트를 띄워야 한다.
    /*@BeforeEach
    void setUp(
        WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider provider
    ) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
            .build();
    }*/

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        // Controller 주입
        this.mockMvc = MockMvcBuilders.standaloneSetup(initController())
            .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
            .build();
    }

    protected abstract Object initController();
}
