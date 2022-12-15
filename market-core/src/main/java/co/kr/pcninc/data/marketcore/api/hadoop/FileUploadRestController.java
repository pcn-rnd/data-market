package co.kr.pcninc.data.marketcore.api.hadoop;

import co.kr.pcninc.data.marketcore.api.RestControllerBase;
import co.kr.pcninc.data.marketcore.api.search.DatasetRestController;
import co.kr.pcninc.data.marketcore.common.message.HttpResponse;
import co.kr.pcninc.data.marketcore.domain.Dataset;
import co.kr.pcninc.data.marketcore.domain.response.File;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = FileUploadRestController.URL_PREFIX)
public class FileUploadRestController extends RestControllerBase {
    static final String URL_PREFIX = API_URL_PREFIX + "/file";
    static final String F_LIST = "/f-list";

    @Autowired
    FileService fileService;

    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<HttpResponse<?>> requestUploadFile(@RequestParam("files") List<MultipartFile> fileList) {
        File response = fileService.uploadHadoop(fileList);

        return okResponse(response);
    }

    @RequestMapping(
            value = F_LIST,
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getFileList(@RequestBody String path) throws UnsupportedEncodingException {
        String nPath = URLDecoder.decode(path, "UTF-8");

        List<String> fList = fileService.getFList(nPath.substring(0, nPath.length()-1));

        return okResponse(fList);
    }



}
