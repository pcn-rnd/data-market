package co.kr.pcninc.data.marketcore.api.search;

import co.kr.pcninc.data.marketcore.api.RestControllerBase;
import co.kr.pcninc.data.marketcore.common.message.HttpResponse;
import co.kr.pcninc.data.marketcore.domain.Dataset;
import co.kr.pcninc.data.marketcore.service.DatasetService;
import lombok.extern.slf4j.Slf4j;
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
    static final String SET_ID = "/{setId}";
    static final String POPULAR = "/populars";
    static final String NEWEST = "/newest";

    @Autowired
    DatasetService datasetService;

    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getDataSetList(
            final Pageable page,
            @RequestParam String q) {
        return okResponse(datasetService.getDataSetList(page, q));
    }


    @RequestMapping(
            value = SET_ID,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getDataSet(@PathVariable int setId) {
        return okResponse(datasetService.getDataSet(setId));
    }


    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> createDataSet(
            @RequestBody Dataset dataset
            ) {
        
        datasetService.createDataset(dataset);
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


    //인기 데이터셋
    @RequestMapping(
            value = POPULAR,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getPopularDataset() {
        return okResponse(datasetService.getPopulars());
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

}
