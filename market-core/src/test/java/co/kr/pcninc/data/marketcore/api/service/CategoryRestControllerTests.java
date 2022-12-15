package co.kr.pcninc.data.marketcore.api.service;

import co.kr.pcninc.data.marketcore.api.ControllerTestBase;
import co.kr.pcninc.data.marketcore.common.Constants;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryRestControllerTests extends ControllerTestBase {
/*    static final String URL_PREFIX = CategoryRestController.URL_PREFIX;

    @Test
    @Order(1)
    void getCategoryListTest() throws Exception {
        mockMvc
                .perform(
                        RestDocumentationRequestBuilders.get(URL_PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(Constants.DEFAULT_CHARSET_VALUE)
                )
                .andDo(print())
                .andDo(document.document(
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("성공 여부"),
                                fieldWithPath("failure").type(JsonFieldType.STRING).description("실패 여부").optional(),
                                fieldWithPath("body[0].catId").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                fieldWithPath("body[0].catName").type(JsonFieldType.STRING).description("카테고리 이름")
                        )
                ))
                .andExpect(status().isOk());
    }*/
}


