package co.kr.pcninc.data.marketcore.api.service;

import co.kr.pcninc.data.marketcore.api.RestControllerBase;
import co.kr.pcninc.data.marketcore.common.message.HttpResponse;
import co.kr.pcninc.data.marketcore.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = LogRestController.URL_PREFIX)
public class LogRestController extends RestControllerBase {
    static final String URL_PREFIX = API_URL_PREFIX + "/log";
    static final String LOG_ID = "/{logId}";
    static final String SIMPLE = "/simple";
    static final String URL = "/urls";
    static final String ERROR = "/errors";
    static final String SYSTEMSWITHID = "/systems";

    static final String IDS = "/ids";
    static final String LEVELS = "/levels";
    static final String METHODS =  "/methods";
    static final String SNAMES = "/snames";
    static final String HOSTS = "/hosts";
    static final String PORTS = "/ports";
    static final String ACTIONS = "/actions";
    static final String CNAMES = "/cnames";
    static final String ACTORS = "/actors";
    static final String DATES = "/dates";
    static final String ROLES = "/roles";
    static final String PATHS = "/paths";

    @Autowired
    LogService logService;


    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getLogs() {
        return okResponse(logService.findAll());
    }

    @RequestMapping(
            value = LOG_ID,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getLog(@PathVariable int logId) {
        return okResponse(logService.findLog(logId));
    }

    @RequestMapping(
            value = SIMPLE,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getSimple() {
        return okResponse(logService.getSimple());
    }

    @RequestMapping(
            value = URL,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getUrls() {
        return okResponse(logService.getUrl());
    }

    @RequestMapping(
            value = ERROR,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getErrors() {
        return okResponse(logService.getErrors());

    }

    @RequestMapping(
            value = SYSTEMSWITHID,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getSystems() {
        return okResponse(logService.findSystems());
    }


    @RequestMapping(
            value = IDS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getIds() {
        return okResponse(logService.getIds());
    }

    @RequestMapping(
            value = METHODS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getMethods() {
        return okResponse(logService.getMethods());
    }

    @RequestMapping(
            value = LEVELS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getLevles() {
        return okResponse(logService.getLevels());
    }
    @RequestMapping(
            value = SNAMES,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getSnames() {
        return okResponse(logService.getSnames());
    }
    @RequestMapping(
            value = HOSTS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getHosts() {
        return okResponse(logService.getHosts());
    }
    @RequestMapping(
            value = PORTS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getPorts() {
        return okResponse(logService.getPorts());
    }
    @RequestMapping(
            value = ACTIONS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getActions() {
        return okResponse(logService.getActions());
    }


    @RequestMapping(
            value = CNAMES,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getCnames() {
        return okResponse(logService.getCnames());
    }
    @RequestMapping(
            value = ACTORS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getActors() {
        return okResponse(logService.getActors());
    }
    @RequestMapping(
            value = DATES,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getDates() {
        return okResponse(logService.getDates());
    }


    @RequestMapping(
            value = ROLES,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getRoles() {
        return okResponse(logService.getRoles());
    }

    @RequestMapping(
            value = PATHS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getPaths() {
        return okResponse(logService.getPaths());
    }


}
