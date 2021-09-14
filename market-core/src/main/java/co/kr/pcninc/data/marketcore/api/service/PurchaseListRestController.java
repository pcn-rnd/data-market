package co.kr.pcninc.data.marketcore.api.service;

import co.kr.pcninc.data.marketcore.api.RestControllerBase;
import co.kr.pcninc.data.marketcore.common.message.HttpResponse;
import co.kr.pcninc.data.marketcore.domain.PurchaseList;
import co.kr.pcninc.data.marketcore.service.PurchaseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = PurchaseListRestController.URL_PREFIX)
public class PurchaseListRestController extends RestControllerBase {
    static final String URL_PREFIX = API_URL_PREFIX + "/pl";
    static final String USER_ID = "/{userId}";
    static final String CREDIT = "/credit";
    static final String MY = "/my";

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

    @RequestMapping(
            value = CREDIT,
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> createPurchase(
            @RequestBody PurchaseList purchaseList
    ) {
        purchaseListService.createPurchase(purchaseList);

        return okResponse(purchaseList);
    }

}
