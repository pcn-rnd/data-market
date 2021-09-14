package co.kr.pcninc.data.marketcore.api.service;

import co.kr.pcninc.data.marketcore.api.RestControllerBase;
import co.kr.pcninc.data.marketcore.common.message.HttpResponse;
import co.kr.pcninc.data.marketcore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = CategoryRestController.URL_PREFIX)
public class CategoryRestController extends RestControllerBase {
    static final String URL_PREFIX = API_URL_PREFIX + "/cat";

    @Autowired
    CategoryService categoryService;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getCategories() {
        return okResponse(categoryService.getCategories());
    }

}
