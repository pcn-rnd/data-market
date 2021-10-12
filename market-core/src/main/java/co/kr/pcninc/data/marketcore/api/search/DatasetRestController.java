package co.kr.pcninc.data.marketcore.api.search;

import co.kr.pcninc.data.marketcore.api.RestControllerBase;
import co.kr.pcninc.data.marketcore.common.message.HttpResponse;
import co.kr.pcninc.data.marketcore.domain.Dataset;
import co.kr.pcninc.data.marketcore.service.DatasetService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = DatasetRestController.URL_PREFIX)
public class DatasetRestController extends RestControllerBase {
    static final String URL_PREFIX = API_URL_PREFIX + "/ds";
    static final String CAT = "/cat";
    static final String CAT_ID = "/{catId}";
    static final String SET_ID = "/{setId}";
    static final String POPULAR = "/populars/{rows}";
    static final String NEWEST = "/newest";
    static final String MOST_PURCHASED = "/most";
    static final String CATS_COUNT = "/cat-cnt";
    static final String ALL_COUNT = "/count";
    static final String ALL_Y_COUNT = "/ycnt";


    /**
     * 통합 검색
     */
    @Autowired
    DatasetService datasetService;

    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getDataSetList(
            final Pageable page,
            @RequestParam String q) {
        log.info("검색어 : {}", q);

        return okResponse(datasetService.getDataSetList(page, q));
    }


    /**
     * 카테고리 검색
     */
    @RequestMapping(
            value = CAT + CAT_ID,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getDsListByCat(
            final Pageable page,
            @RequestParam String q,
            @PathVariable int catId) {
        log.info("검색어 : {}", q);

        return okResponse(datasetService.getDatasetListByCat(page, q, catId));
    }


    @RequestMapping(
            value = SET_ID,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getDataSet(@PathVariable int setId) {
        Dataset ds = datasetService.getDataSet(setId);

        ds.setViews(ds.getViews() + 1);
        datasetService.save(ds);

        return okResponse(ds);
    }


    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> createDataSet(
            @RequestBody Dataset dataset
            ) {
        
        log.info("요청 데이터 : {}", dataset);
        datasetService.save(dataset);

        return okResponse(dataset);
    }

    @RequestMapping(
            value = SET_ID,
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> updateDataSet(
            @RequestBody Dataset dataset,
            @PathVariable int setId
    ) {
        Dataset targetSet =  datasetService.getDataSet(setId);

        return okResponse(dataset);
    }

    @RequestMapping(
            value = SET_ID,
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<String>> updateDataSet(
            @PathVariable int setId
    ) {
        try{
            datasetService.deleteDataset(setId);
        }catch (Exception e) {
            log.error(String.valueOf(e));
        }

        return okResponse(setId + "번 데이터셋이 삭제되었습니다.");
    }


    //인기 데이터셋 (조회수 순)
    @RequestMapping(
            value = POPULAR,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getPopularDataset(@PathVariable int rows) {
        return okResponse(datasetService.getPopulars(rows));
    }

    //최신 데이터셋
    @RequestMapping(
            value = NEWEST,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getNewestDataset() {
        return okResponse(datasetService.getNewest());
    }


    //구매 별 데이터셋 순위
    @RequestMapping(
            value = MOST_PURCHASED,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getMostPurchased() {
        return okResponse(datasetService.getMostPurchased());
    }


    /**
     * 카테고리별 데이터셋 수
     * @return
     */
    @RequestMapping(
            value = CATS_COUNT,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getCountCats() {
        return okResponse(datasetService.getCountCats());
    }

    /**
     * 전체 데이터셋 수
     * @return
     */
    @RequestMapping(
            value = ALL_COUNT,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getAllCount() {
        return okResponse(datasetService.getAllCounts());
    }


    /**
     * 공개 데이터셋 개수
     * @return
     */
    @RequestMapping(
            value = ALL_Y_COUNT,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getYCount() {
        return okResponse(datasetService.getYCnt("Y"));
    }


}
