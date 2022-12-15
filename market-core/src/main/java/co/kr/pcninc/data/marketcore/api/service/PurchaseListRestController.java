package co.kr.pcninc.data.marketcore.api.service;

import co.kr.pcninc.data.marketcore.api.RestControllerBase;
import co.kr.pcninc.data.marketcore.common.message.HttpResponse;
import co.kr.pcninc.data.marketcore.domain.PurchaseList;
import co.kr.pcninc.data.marketcore.service.PurchaseListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = PurchaseListRestController.URL_PREFIX)
public class PurchaseListRestController extends RestControllerBase {
    static final String URL_PREFIX = API_URL_PREFIX + "/pl";
    static final String USER_ID = "/{userId}";
    static final String LIST_ID = "/{listId}";
    static final String CREDIT = "/credit";
    static final String MY = "/my";

    static final String UNABLE = "/unable";

    @Autowired
    PurchaseListService purchaseListService;

    /**
     * 특정 사용자 구매내역 조회 - 공개
     * @param page
     * @param userId
     * @return
     */
    @RequestMapping(
            value = USER_ID,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getPurchaseListY(
            final Pageable page,
            @PathVariable String userId
            ) {
        return okResponse(purchaseListService.getListPageableY(userId, page));
    }

    /**
     * 내가 만든 데이터셋 목록 조회
     * @param page
     * @param userId
     * @return
     */
    @RequestMapping(
            value = USER_ID + MY,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getPurchaseListN(
            final Pageable page,
            @PathVariable String userId
    ) {
        return okResponse(purchaseListService.getListPageableN(userId, page));
    }

    /**
     * 구매 목록 추가
     * @param purchaseList
     * @return
     */
    @RequestMapping(
            value = CREDIT,
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<String>> createPurchase(
            @RequestBody PurchaseList purchaseList
    ) {
        log.info("요청 데이터 : {}", purchaseList);
        purchaseListService.createPurchase(purchaseList);

        //return okResponse(purchaseList);
        //공인인증용
        return okResponse(purchaseList.getSetId().getSetId() + "구매완료");
    }

    /**
     * 구매 목록 삭제
     *
     */
    @RequestMapping(
            value = "/del" + LIST_ID,
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<String>> deletePurchaseLog(
            @PathVariable int listId
    ) {
        purchaseListService.deletePurchase(listId);

        //return okResponse(purchaseList);
        //공인인증용
        return okResponse(listId + "번 삭제 완료");
    }


    @RequestMapping(
            value = UNABLE,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getUnablePurchase(

    ) {
        return okResponse(purchaseListService.unableDataset());
    }

    @RequestMapping(
            value = UNABLE + USER_ID,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getUnablePurchaseByUser(
            @PathVariable String userId

    ) {
        return okResponse(purchaseListService.unableDatasetByUser(userId));
    }


    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getAllList(){
        return okResponse(purchaseListService.getAll());
    }

}
