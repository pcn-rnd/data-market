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

import java.util.Optional;

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


    static final String AUTH = "/auth";
    static final String ALL = "/all";
    static final String TITLES = "/titles";
    static final String CONTENTS = "/contents";
    static final String VIEWS = "/views";
    static final String TYPES = "/types";
    static final String PATHS = "/path";
    static final String PRICES = "/prices";
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
    public ResponseEntity<HttpResponse<?>> getDataSet(
            @PathVariable int setId,
            @RequestParam("m") Optional<Boolean> m) {
        Dataset ds = datasetService.getDataSet(setId);



        ds.setViews(ds.getViews() + 1);
        datasetService.save(ds);

        return okResponse(ds);
    }


    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<String>> createDataSet(
            @RequestBody Dataset dataset
            ) {
        
        log.info("요청 데이터 : {}", dataset);
        datasetService.save(dataset);

        // 공인인증용
        // return okResponse(dataset);
        return okResponse(dataset.getSetId() + "번 데이터셋 생성 완료");
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


    @RequestMapping(
            value = ALL,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getAll() {

        return okResponse(datasetService.getAll());
    }

    @RequestMapping(
            value = TITLES,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getTitles() {

        return okResponse(datasetService.getAllTitle());
    }

    @RequestMapping(
            value = CONTENTS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getAllContents() {

        return okResponse(datasetService.getAllContent());
    }

    @RequestMapping(
            value = VIEWS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getAllViews() {

        return okResponse(datasetService.getAllViews());
    }

    @RequestMapping(
            value = TYPES,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getAllTypes() {

        return okResponse(datasetService.getAllDataType());
    }

    @RequestMapping(
            value = PATHS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getAllPaths() {

        return okResponse(datasetService.getPathList());
    }

    @RequestMapping(
            value = PRICES,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getAllPrices() {

        return okResponse(datasetService.getAllPrice());
    }



}
