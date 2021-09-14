package co.kr.pcninc.data.marketcore.api;

import co.kr.pcninc.data.marketcore.common.Constants;
import co.kr.pcninc.data.marketcore.common.message.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class RestControllerBase {
    public static final String API_URL_PREFIX = "/api/v1";

    //API요청에 대한 기본 성공 메세지
    protected static final ResponseEntity<HttpResponse<String>> SUCCESS_RESPONSE =
            ResponseEntity.ok().body(new HttpResponse<>(Constants.SUCCESS));

    //API요청에 대한 기본 실패 메세지
    protected static final ResponseEntity<HttpResponse<String>> FAIL_RESEPONSE =
            ResponseEntity.ok().body(new HttpResponse<>(Constants.FAILURE, Constants.FAILURE));

    /**
     * @fn okResponse
     * @remark
     * - AJAX 요청에 대한 성공 메시지 생성 (기본 헤더)
     * @param s
     * @return
     */
    protected static ResponseEntity<HttpResponse<String>> okResponse(String s) {
        return okResponse(s, null);
    }

    /**
     * @fn okResponse
     * @remark
     * - AJAX 요청에 대한 성공 메시지 생성 (헤더 추가)
     * @param s
     * @param headers
     * @return
     */
    protected static ResponseEntity<HttpResponse<String>> okResponse(String s, HttpHeaders headers) {

        if (headers == null) {
            return ResponseEntity.ok().body(new HttpResponse<>(s));
        } else {
            return ResponseEntity.ok().headers(headers).body(new HttpResponse<>(s));
        }

    }

    /**
     * @fn okResponse
     * @remark
     * - AJAX 요청에 대한 성공 메시지 생성 (오브젝트, 기본 헤더)
     * @param o
     * @return
     */
    protected static ResponseEntity<HttpResponse<?>> okResponse(Object o) {
        return okResponse(o, null);
    }

    /**
     * @fn okResponse
     * @remark
     * - AJAX 요청에 대한 성공 메시지 생성 (오브젝트, 헤더 추가)
     * @param o
     * @param headers
     * @return
     */
    protected static ResponseEntity<HttpResponse<?>> okResponse(Object o, HttpHeaders headers) {
        if (headers == null) {
            return ResponseEntity.ok().body(new HttpResponse<>(o));
        } else {
            return ResponseEntity.ok().headers(headers).body(new HttpResponse<>(o));
        }

    }

    /**
     * @fn failResponse
     * @remark
     * - AJAX 요청에 대한 성공 메시지 생성 (기본 헤더)
     * @param s
     * @return
     */
    protected static ResponseEntity<HttpResponse<String>> failResponse(String s) {
        return failResponse(s, null);
    }

    /**
     * @fn failResponse
     * @remark
     * - AJAX 요청에 대한 성공 메시지 생성 (헤더 추가)
     * @param s
     * @param headers
     * @return
     */
    protected static ResponseEntity<HttpResponse<String>> failResponse(String s, HttpHeaders headers) {

        if (headers == null) {
            return ResponseEntity.ok().body(new HttpResponse<>(s, Constants.FAILURE));
        } else {
            return ResponseEntity.ok().headers(headers).body(new HttpResponse<>(s, Constants.FAILURE));
        }
    }


    /**
     * @fn failResponse
     * @remark
     * - AJAX 요청에 대한 실패 메시지 생성 (오브젝트, 기본 헤더)
     * @param o
     * @return
     */
    protected static ResponseEntity<HttpResponse<?>> failResponse(Object o) {
        return failResponse(o, null);
    }


    /**
     * @fn failResponse
     * @remark
     * - AJAX 요청에 대한 실패 메시지 생성 (오브젝트, 헤더 추가)
     * @param o
     * @param headers
     * @return
     */
    protected static ResponseEntity<HttpResponse<?>> failResponse(Object o, HttpHeaders headers) {

        if (headers == null) {
            return ResponseEntity.ok().body(new HttpResponse<>(o, Constants.FAILURE));
        } else {
            return ResponseEntity.ok().headers(headers).body(new HttpResponse<>(o, Constants.FAILURE));
        }
    }



    /**
     * @fn createdResponse
     * @remark
     * - API 요청에 대한 Created 메시지 생성 (201)
     * @params
     * @return
     */
    protected static ResponseEntity<HttpResponse<?>> createdResponse(final Object o) {
        return response(o, HttpStatus.CREATED);
    }

    /**
     * @fn okResponse
     * @remark
     * - AJAX 요청에 대한 성공 메시지 생성 (기본 헤더)
     * @param s
     * @return
     */
    protected static ResponseEntity<HttpResponse<String>> createdResponse(String s) {
        return createdResponse(s, null);
    }

    /**
     * @fn okResponse
     * @remark
     * - AJAX 요청에 대한 성공 메시지 생성 (헤더 추가)
     * @param s
     * @param headers
     * @return
     */
    protected static ResponseEntity<HttpResponse<String>> createdResponse(String s, HttpHeaders headers) {

        if (headers == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new HttpResponse<>(s));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(new HttpResponse<>(s));
        }

    }

    /**
     * @fn response
     * @remark
     * - API 요청에 대한 성공 메시지 생성 (Status 지정)
     * @param o
     * @param status
     * @return
     */
    protected static ResponseEntity<HttpResponse<?>> response(final Object o, final HttpStatus status) {
        return ResponseEntity.status(status).body(new HttpResponse<>(o));
    }











    protected void debug(final String s) {
        if (log.isDebugEnabled()) {
            log.debug(s);
        }
    }

    protected void debug(final String s, Class<?> c) {
        if (log.isDebugEnabled()) {
            log.debug(s, c);
        }
    }

    protected void debug(final Object o) {
        if (log.isDebugEnabled()) {
            log.debug("{}", o);
        }
    }

    protected void debug(final String s, final Object... args) {
        if (log.isDebugEnabled()) {
            log.debug(s, args);
        }
    }

    protected void info(final String s) {
        if (log.isInfoEnabled()) {
            log.info(s);
        }
    }

    protected void info(final String s, final Object... args) {
        if (log.isInfoEnabled()) {
            log.info(s, args);
        }
    }

}
