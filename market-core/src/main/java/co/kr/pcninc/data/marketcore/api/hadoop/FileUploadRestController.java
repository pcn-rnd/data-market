package co.kr.pcninc.data.marketcore.api.hadoop;

import co.kr.pcninc.data.marketcore.api.RestControllerBase;
import co.kr.pcninc.data.marketcore.api.search.DatasetRestController;
import co.kr.pcninc.data.marketcore.common.message.HttpResponse;
import co.kr.pcninc.data.marketcore.domain.response.FileResponse;
import co.kr.pcninc.data.marketcore.service.FileService;
import co.kr.pcninc.data.marketcore.util.ObjectConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = FileUploadRestController.URL_PREFIX)
public class FileUploadRestController extends RestControllerBase {
    static final String URL_PREFIX = API_URL_PREFIX + "/file";

    @Autowired
    FileService fileService;

    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<HttpResponse<?>> requestUploadFile(@RequestParam("files") List<MultipartFile> fileList) {
        FileResponse response = fileService.uploadHadoop(fileList);

        return okResponse(response);
    }

    /*@RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> readFiles() {
        FileResponse response = fileService

        return okResponse(response);
    }*/



}
