package co.kr.pcninc.data.marketcore.api.service;

import co.kr.pcninc.data.marketcore.api.RestControllerBase;
import co.kr.pcninc.data.marketcore.common.message.HttpResponse;
import co.kr.pcninc.data.marketcore.domain.Category;
import co.kr.pcninc.data.marketcore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = CategoryRestController.URL_PREFIX)
public class CategoryRestController extends RestControllerBase {
    static final String URL_PREFIX = API_URL_PREFIX + "/cat";
    static final String CAT_ID = "/{catId}";

    @Autowired
    CategoryService categoryService;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getCategories() {
        return okResponse(categoryService.getCategories());
    }

    // 공인인증
    @RequestMapping(
            value = CAT_ID,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getCategory(
            @PathVariable int catId
    ) {
        return okResponse(categoryService.getCategory(catId));
    }


    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<String>> modifyCat(
            @RequestBody Category cat
    ) {
        categoryService.createCategory(cat);
        return okResponse(cat.getCatName()+ " 카테고리 생성 완료");
    }

    @RequestMapping(
            value = CAT_ID,
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<String>> modifyCat(
            @PathVariable int catId
    ) {
        Optional<Category> cat = categoryService.getCategory(catId);

        return okResponse(cat.get().getCatName()+ " 카테고리 정보 변경");
    }

    @RequestMapping(
            value = CAT_ID,
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<String>> deleteCategory(
            @PathVariable int catId
    ) {
        categoryService.deleteCategory(catId);
        return okResponse(catId + "번 카테고리 삭제 완료");
    }

}
