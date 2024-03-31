package com.jong1.cafekiosk.spring.docs.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import com.jong1.cafekiosk.spring.api.controller.product.ProductController;
import com.jong1.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
import com.jong1.cafekiosk.spring.api.service.product.ProductService;
import com.jong1.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import com.jong1.cafekiosk.spring.api.service.product.response.ProductResponse;
import com.jong1.cafekiosk.spring.docs.RestDocsSupport;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class ProductControllerDocsTest extends RestDocsSupport {

    private final ProductService productService = mock(ProductService.class);

    @Override
    protected Object initController() {
        return new ProductController(productService);
    }

    @Test
    @DisplayName("신규 상품을 등록하는 API")
    void createProduct() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .type(ProductType.HANDMADE)
            .sellingStatus(ProductSellingType.SELLING)
            .name("아메리카노")
            .price(4000)
            .build();

        BDDMockito.given(productService.createProduct(any(ProductCreateServiceRequest.class)))
            .willReturn(
                ProductResponse.builder()
                    .id(1L)
                    .productNumber("P0001")
                    .type(ProductType.HANDMADE)
                    .sellingStatus(ProductSellingType.SELLING)
                    .name("아메리카노")
                    .price(4000)
                    .build()
            );

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/products/new")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(
                MockMvcRestDocumentation.document(
                    "product-create",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("type").type(JsonFieldType.STRING).description("상품 타입"),
                        PayloadDocumentation.fieldWithPath("sellingStatus").optional().type(JsonFieldType.STRING).description("상품 판매 상태"),
                        PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("상품명"),
                        PayloadDocumentation.fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                        PayloadDocumentation.fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                        PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                        PayloadDocumentation.fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                        PayloadDocumentation.fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("상품 ID"),
                        PayloadDocumentation.fieldWithPath("data.productNumber").type(JsonFieldType.STRING).description("상품 번호"),
                        PayloadDocumentation.fieldWithPath("data.type").type(JsonFieldType.STRING).description("상품 타입"),
                        PayloadDocumentation.fieldWithPath("data.sellingStatus").type(JsonFieldType.STRING).description("상품 판매 상태"),
                        PayloadDocumentation.fieldWithPath("data.name").type(JsonFieldType.STRING).description("상품명"),
                        PayloadDocumentation.fieldWithPath("data.price").type(JsonFieldType.NUMBER).description("상품 가격")
                    )
                )
            );
    }
}