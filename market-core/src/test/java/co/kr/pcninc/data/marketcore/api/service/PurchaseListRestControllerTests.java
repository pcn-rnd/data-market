package co.kr.pcninc.data.marketcore.api.service;

import co.kr.pcninc.data.marketcore.api.ControllerTestBase;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import co.kr.pcninc.data.marketcore.domain.PurchaseList;
import co.kr.pcninc.data.marketcore.domain.User;
import co.kr.pcninc.data.marketcore.util.ObjectConverter;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import co.kr.pcninc.data.marketcore.common.Constants;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Date;

//완
public class PurchaseListRestControllerTests extends ControllerTestBase {

    static final String URL_PREFIX = PurchaseListRestController.URL_PREFIX;
    static final String USER_ID = PurchaseListRestController.USER_ID;
    static final String CREDIT = PurchaseListRestController.CREDIT;
    static final String MY = PurchaseListRestController.MY;

    @Test
    @Order(1)
    void getPagePurchaseListY() throws Exception {
        String userId = "pcn";

        mockMvc
                .perform(
                        RestDocumentationRequestBuilders.get(URL_PREFIX + USER_ID, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(Constants.DEFAULT_CHARSET_VALUE)
                )
                .andDo(print())
                .andDo(document.document(
                        pathParameters(
                                parameterWithName("userId").description("조회할 사용자 ID")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("성공 여부"),
                                fieldWithPath("failure").type(JsonFieldType.STRING).description("실패").optional(),
                                fieldWithPath("body.content[0].list_id").type(JsonFieldType.NUMBER).description("구매 이력 ID"),
                                fieldWithPath("body.content[0].set_id").type(JsonFieldType.NUMBER).description("데이터셋 ID"),
                                fieldWithPath("body.content[0].customer.userId").type(JsonFieldType.STRING).description("구매자 ID"),
                                fieldWithPath("body.content[0].customer.pwd").type(JsonFieldType.STRING).description("구매자 패스워드").optional(),
                                fieldWithPath("body.content[0].customer.userName").type(JsonFieldType.STRING).description("구매자 이름"),
                                fieldWithPath("body.content[0].customer.email").type(JsonFieldType.STRING).description("구매자 이메일").optional(),
                                fieldWithPath("body.content[0].customer.userKey").type(JsonFieldType.STRING).description("구매자 키").optional(),
                                fieldWithPath("body.content[0].purDt").type(JsonFieldType.STRING).description("구매 시간"),
                                fieldWithPath("body.content[0].ableYn").type(JsonFieldType.STRING).description("API 유효여부"),
                                fieldWithPath("body.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 여부"),
                                fieldWithPath("body.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬 미여부").optional(),
                                fieldWithPath("body.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 정보 empty 여부").optional(),
                                fieldWithPath("body.pageable.offset").type(JsonFieldType.NUMBER).description("페이징 오프셋").optional(),
                                fieldWithPath("body.pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 수 단위").optional(),
                                fieldWithPath("body.pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호").optional(),
                                fieldWithPath("body.pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징 여부").optional(),
                                fieldWithPath("body.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("페이징 미여부").optional(),
                                fieldWithPath("body.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수").optional(),
                                fieldWithPath("body.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부").optional(),
                                fieldWithPath("body.totalElements").type(JsonFieldType.NUMBER).description("총 데이터 수").optional(),
                                fieldWithPath("body.number").type(JsonFieldType.NUMBER).description("현재 페이지").optional(),
                                fieldWithPath("body.size").type(JsonFieldType.NUMBER).description("페이지 크기").optional(),
                                fieldWithPath("body.sort.sorted").type(JsonFieldType.BOOLEAN).description("현재 페이지 정렬 여부").optional(),
                                fieldWithPath("body.sort.unsorted").type(JsonFieldType.BOOLEAN).description("현재 페이지 미정렬 여부").optional(),
                                fieldWithPath("body.sort.empty").type(JsonFieldType.BOOLEAN).description("현재 페이지 empty 여부").optional(),
                                fieldWithPath("body.first").type(JsonFieldType.BOOLEAN).description("첫 번째 페이지 여부").optional(),
                                fieldWithPath("body.numberOfElements").type(JsonFieldType.NUMBER).description("요소들의 개수").optional(),
                                fieldWithPath("body.empty").type(JsonFieldType.BOOLEAN).description("empty 여부").optional()

                        )
                ))
                .andExpect(status().isOk());
    }


    @Test
    @Order(2)
    void getPagePurchaseListN() throws Exception {
        String userId = "pcn";

        mockMvc
                .perform(
                        RestDocumentationRequestBuilders.get(URL_PREFIX + USER_ID + MY, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(Constants.DEFAULT_CHARSET_VALUE)
                )
                .andDo(print())
                .andDo(document.document(
                        pathParameters(
                                parameterWithName("userId").description("조회할 사용자 ID")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("성공 여부"),
                                fieldWithPath("failure").type(JsonFieldType.STRING).description("실패").optional(),
                                fieldWithPath("body.content[0].list_id").type(JsonFieldType.NUMBER).description("구매 이력 ID"),
                                fieldWithPath("body.content[0].set_id").type(JsonFieldType.NUMBER).description("데이터셋 ID"),
                                fieldWithPath("body.content[0].customer.userId").type(JsonFieldType.STRING).description("구매자 ID"),
                                fieldWithPath("body.content[0].customer.pwd").type(JsonFieldType.STRING).description("구매자 패스워드").optional(),
                                fieldWithPath("body.content[0].customer.userName").type(JsonFieldType.STRING).description("구매자 이름"),
                                fieldWithPath("body.content[0].customer.email").type(JsonFieldType.STRING).description("구매자 이메일").optional(),
                                fieldWithPath("body.content[0].customer.userKey").type(JsonFieldType.STRING).description("구매자 키").optional(),
                                fieldWithPath("body.content[0].purDt").type(JsonFieldType.STRING).description("구매 시간"),
                                fieldWithPath("body.content[0].ableYn").type(JsonFieldType.STRING).description("API 유효여부"),
                                fieldWithPath("body.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 여부"),
                                fieldWithPath("body.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬 미여부").optional(),
                                fieldWithPath("body.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 정보 empty 여부").optional(),
                                fieldWithPath("body.pageable.offset").type(JsonFieldType.NUMBER).description("페이징 오프셋").optional(),
                                fieldWithPath("body.pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 수 단위").optional(),
                                fieldWithPath("body.pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호").optional(),
                                fieldWithPath("body.pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징 여부").optional(),
                                fieldWithPath("body.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("페이징 미여부").optional(),
                                fieldWithPath("body.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수").optional(),
                                fieldWithPath("body.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부").optional(),
                                fieldWithPath("body.totalElements").type(JsonFieldType.NUMBER).description("총 데이터 수").optional(),
                                fieldWithPath("body.number").type(JsonFieldType.NUMBER).description("현재 페이지").optional(),
                                fieldWithPath("body.size").type(JsonFieldType.NUMBER).description("페이지 크기").optional(),
                                fieldWithPath("body.sort.sorted").type(JsonFieldType.BOOLEAN).description("현재 페이지 정렬 여부").optional(),
                                fieldWithPath("body.sort.unsorted").type(JsonFieldType.BOOLEAN).description("현재 페이지 미정렬 여부").optional(),
                                fieldWithPath("body.sort.empty").type(JsonFieldType.BOOLEAN).description("현재 페이지 empty 여부").optional(),
                                fieldWithPath("body.first").type(JsonFieldType.BOOLEAN).description("첫 번째 페이지 여부").optional(),
                                fieldWithPath("body.numberOfElements").type(JsonFieldType.NUMBER).description("요소들의 개수").optional(),
                                fieldWithPath("body.empty").type(JsonFieldType.BOOLEAN).description("empty 여부").optional()

                        )
                ))
                .andExpect(status().isOk());
    }


    @Test
    @Order(3)
    void createPagePurchase() throws Exception {
        PurchaseList purchaseList = PurchaseList.builder()
                    .set_id(5)
                    .customer(
                        User.builder()
                            .userId("gildong")
                            .build()
                    )
                    .purDt(new Date())
                    .ableYn("Y")
                    .build();


        mockMvc
                .perform(
                        RestDocumentationRequestBuilders.post(URL_PREFIX + CREDIT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(Constants.DEFAULT_CHARSET_VALUE)
                                .content(ObjectConverter.toJson(purchaseList))
                )
                .andDo(print())
                .andDo(document.document(
                    requestFields(
                            fieldWithPath("list_id").type(JsonFieldType.NUMBER).description("구매목록 ID"),
                            fieldWithPath("set_id").type(JsonFieldType.NUMBER).description("구매한 데이터셋 ID"),
                            fieldWithPath("customer.userId").type(JsonFieldType.STRING).description("구매자 ID"),
                            fieldWithPath("customer.pwd").type(JsonFieldType.STRING).description("구매자 비밀번호"),
                            fieldWithPath("customer.userName").type(JsonFieldType.STRING).description("구매자 이름"),
                            fieldWithPath("customer.email").type(JsonFieldType.STRING).description("구매자 이메일"),
                            fieldWithPath("customer.userKey").type(JsonFieldType.STRING).description("구매자 사용자 key"),
                            fieldWithPath("purDt").type(JsonFieldType.STRING).description("구매 일자"),
                            fieldWithPath("ableYn").type(JsonFieldType.STRING).description("데이터셋 사용가능 여부")
                    ),
                    responseFields(
                            fieldWithPath("list_id").type(JsonFieldType.NUMBER).description("구매완료 처리 된 구매목록 ID"),
                            fieldWithPath("set_id").type(JsonFieldType.NUMBER).description("구매한 데이터셋 ID"),
                            fieldWithPath("customer.userId").type(JsonFieldType.STRING).description("구매자 ID"),
                            fieldWithPath("customer.pwd").type(JsonFieldType.STRING).description("구매자 비밀번호"),
                            fieldWithPath("customer.userName").type(JsonFieldType.STRING).description("구매자 이름"),
                            fieldWithPath("customer.email").type(JsonFieldType.STRING).description("구매자 이메일"),
                            fieldWithPath("customer.userKey").type(JsonFieldType.STRING).description("구매자 사용자 key"),
                            fieldWithPath("purDt").type(JsonFieldType.STRING).description("구매 일자"),
                            fieldWithPath("ableYn").type(JsonFieldType.STRING).description("데이터셋 사용가능 여부")
                    )
                ))
                .andExpect(status().isOk());
    }


}
