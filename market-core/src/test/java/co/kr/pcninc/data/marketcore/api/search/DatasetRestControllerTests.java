package co.kr.pcninc.data.marketcore.api.search;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import co.kr.pcninc.data.marketcore.api.ControllerTestBase;
import co.kr.pcninc.data.marketcore.common.Constants;
import co.kr.pcninc.data.marketcore.domain.Category;
import co.kr.pcninc.data.marketcore.domain.Dataset;
import co.kr.pcninc.data.marketcore.domain.User;
import co.kr.pcninc.data.marketcore.util.ObjectConverter;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.math.BigDecimal;

public class DatasetRestControllerTests extends ControllerTestBase {

    static final String URL_PREFIX = DatasetRestController.URL_PREFIX;
    static final String SET_ID = DatasetRestController.SET_ID;

    int testSetId = 14; // Test용 고정 ID

    @Test
    @Order(1)
    void createDatasetTest() throws Exception{

        Dataset dataset = Dataset.builder()
                .setId(testSetId) //
                .catId(
                        Category.builder()
                                .catId(1)
                                .build())
                .title("의료 데이터셋")
                .content("의료 데이터셋 상세 정보")
                .seller(
                        User.builder()
                                .userId("gildong")
                                .build())
                .datatype("csv")
                .path("/data/")
                .price(new BigDecimal(1500))
                .build();

        mockMvc
                .perform(
                        RestDocumentationRequestBuilders.post(URL_PREFIX)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(Constants.DEFAULT_CHARSET_VALUE)
                                .content(ObjectConverter.toJson(dataset))
                )
                .andDo(print())
                .andDo(document.document(
                        requestFields(
                                fieldWithPath("setId").type(JsonFieldType.NUMBER).description("데이터셋 ID"),
                                fieldWithPath("catId").type(JsonFieldType.OBJECT).description("카테고리 ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("데이터셋 이름"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("데이터셋 본문 내용"),
                                fieldWithPath("seller").type(JsonFieldType.STRING).description("판매자 ID"),
                                fieldWithPath("publicYn").type(JsonFieldType.STRING).description("공개 여부"),
                                fieldWithPath("views").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("crtDt").type(JsonFieldType.STRING).description("구매일자"),
                                fieldWithPath("urlsInfo").type(JsonFieldType.STRING).description("url 정보"),
                                fieldWithPath("datatype").type(JsonFieldType.STRING).description("데이터 타입"),
                                fieldWithPath("wsId").type(JsonFieldType.NUMBER).description("워크스페이스 ID").optional(),
                                fieldWithPath("path").type(JsonFieldType.STRING).description("데이터 저장 경로"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("데이터셋 가격")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void updateDatasetTest() throws Exception{

        Dataset dataset = Dataset.builder()
                .catId(
                        Category.builder()
                                .catId(1)
                                .build())
                .title("의료 데이터셋")
                .content("의료 데이터셋 상세 정보")
                .seller(
                        User.builder()
                                .userId("gildong")
                                .build())
                .datatype("csv")
                .path("/data/")
                .price(new BigDecimal(1500))
                .build();

        mockMvc
                .perform(
                        RestDocumentationRequestBuilders.post(URL_PREFIX + SET_ID, testSetId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(Constants.DEFAULT_CHARSET_VALUE)
                                .content(ObjectConverter.toJson(dataset))
                )
                .andDo(print())
                .andDo(document.document(
                        pathParameters(
                                parameterWithName("set_id").description("데이터셋 ID")
                        ),
                        requestFields(
                                fieldWithPath("setId").type(JsonFieldType.NUMBER).description("데이터셋 ID"),
                                fieldWithPath("catId").type(JsonFieldType.OBJECT).description("카테고리 ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("데이터셋 이름"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("데이터셋 본문 내용"),
                                fieldWithPath("seller").type(JsonFieldType.STRING).description("판매자 ID"),
                                fieldWithPath("publicYn").type(JsonFieldType.STRING).description("공개 여부"),
                                fieldWithPath("views").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("crtDt").type(JsonFieldType.STRING).description("구매일자"),
                                fieldWithPath("urlsInfo").type(JsonFieldType.STRING).description("url 정보"),
                                fieldWithPath("datatype").type(JsonFieldType.STRING).description("데이터 타입"),
                                fieldWithPath("wsId").type(JsonFieldType.NUMBER).description("워크스페이스 ID").optional(),
                                fieldWithPath("path").type(JsonFieldType.STRING).description("데이터 저장 경로"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("데이터셋 가격")
                        )
                ))
                .andExpect(status().isOk());
    }



    @Test
    @Order(3)
    void getDatasetListTest() throws Exception{
        String q = "";
        mockMvc
                .perform(
                        RestDocumentationRequestBuilders.get(URL_PREFIX)
                            .param("q", q)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .characterEncoding(Constants.DEFAULT_CHARSET_VALUE)
                )
                .andDo(print())
                .andDo(document.document(
                        requestParameters(
                                parameterWithName("q").description("검색어")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("성공 여부"),
                                fieldWithPath("failure").type(JsonFieldType.STRING).description("실패").optional(),
                                fieldWithPath("body.content[0].setId").type(JsonFieldType.NUMBER).description("데이터셋 ID"),
                                fieldWithPath("body.content[0].catId.catId").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                fieldWithPath("body.content[0].catId.catName").type(JsonFieldType.STRING).description("카테고리 이름"),
                                fieldWithPath("body.content[0].title").type(JsonFieldType.STRING).description("데이터셋 이름"),
                                fieldWithPath("body.content[0].content").type(JsonFieldType.STRING).description("데이터셋 본문 내용"),
                                fieldWithPath("body.content[0].seller.userId").type(JsonFieldType.STRING).description("판매자 ID"),
                                fieldWithPath("body.content[0].seller.pwd").type(JsonFieldType.STRING).description("판매자 비밀번호").optional(),
                                fieldWithPath("body.content[0].seller.userName").type(JsonFieldType.STRING).description("판매자 이름"),
                                fieldWithPath("body.content[0].seller.email").type(JsonFieldType.STRING).description("판매자 이메일").optional(),
                                fieldWithPath("body.content[0].seller.userKey").type(JsonFieldType.STRING).description("판매자 사용자 키").optional(),
                                fieldWithPath("body.content[0].publicYn").type(JsonFieldType.STRING).description("공개 여부"),
                                fieldWithPath("body.content[0].views").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("body.content[0].crtDt").type(JsonFieldType.STRING).description("구매일자"),
                                fieldWithPath("body.content[0].urlsInfo").type(JsonFieldType.STRING).description("url 정보"),
                                fieldWithPath("body.content[0].datatype").type(JsonFieldType.STRING).description("데이터 타입"),
                                fieldWithPath("body.content[0].wsId").type(JsonFieldType.NUMBER).description("워크스페이스 ID").optional(),
                                fieldWithPath("body.content[0].path").type(JsonFieldType.STRING).description("데이터 저장 경로"),
                                fieldWithPath("body.content[0].price").type(JsonFieldType.NUMBER).description("데이터셋 가격"),
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
    @Order(4)
    void getDatasetTest() throws Exception{
        mockMvc
                .perform(
                        RestDocumentationRequestBuilders.get(URL_PREFIX + SET_ID, testSetId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(Constants.DEFAULT_CHARSET_VALUE)
                )
                .andDo(print())
                .andDo(document.document(
                        pathParameters(
                          parameterWithName("set_id").description("데이터셋 ID")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("성공 여부"),
                                fieldWithPath("failure").type(JsonFieldType.STRING).description("실패").optional(),
                                fieldWithPath("body.setId").type(JsonFieldType.NUMBER).description("데이터셋 ID"),
                                fieldWithPath("body.catId.catId").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                fieldWithPath("body.catId.catName").type(JsonFieldType.STRING).description("카테고리 이름"),
                                fieldWithPath("body.title").type(JsonFieldType.STRING).description("데이터셋 이름"),
                                fieldWithPath("body.content").type(JsonFieldType.STRING).description("데이터셋 본문 내용"),
                                fieldWithPath("body.seller.userId").type(JsonFieldType.STRING).description("판매자 ID"),
                                fieldWithPath("body.seller.pwd").type(JsonFieldType.STRING).description("판매자 비밀번호").optional(),
                                fieldWithPath("body.seller.userName").type(JsonFieldType.STRING).description("판매자 이름"),
                                fieldWithPath("body.seller.email").type(JsonFieldType.STRING).description("판매자 이메일").optional(),
                                fieldWithPath("body.seller.userKey").type(JsonFieldType.STRING).description("판매자 사용자 키").optional(),
                                fieldWithPath("body.publicYn").type(JsonFieldType.STRING).description("공개 여부"),
                                fieldWithPath("body.views").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("body.crtDt").type(JsonFieldType.STRING).description("구매일자"),
                                fieldWithPath("body.urlsInfo").type(JsonFieldType.STRING).description("url 정보"),
                                fieldWithPath("body.datatype").type(JsonFieldType.STRING).description("데이터 타입"),
                                fieldWithPath("body.wsId").type(JsonFieldType.NUMBER).description("워크스페이스 ID").optional(),
                                fieldWithPath("body.path").type(JsonFieldType.STRING).description("데이터 저장 경로"),
                                fieldWithPath("body.price").type(JsonFieldType.NUMBER).description("데이터셋 가격")
                        )
                ))
                .andExpect(status().isOk());
    }



    @Test
    @Order(5)
    void deleteDatasetTest() throws Exception{
        mockMvc
                .perform(
                        RestDocumentationRequestBuilders.post(URL_PREFIX + SET_ID, testSetId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding(Constants.DEFAULT_CHARSET_VALUE)
                )
                .andDo(print())
                .andDo(document.document(
                        pathParameters(
                                parameterWithName("set_id").description("데이터셋 ID")
                        )
                ))
                .andExpect(status().isOk());
    }
}
