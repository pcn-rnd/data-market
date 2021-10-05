package co.kr.pcninc.data.marketcore.service;

import co.kr.pcninc.data.marketcore.api.AuthRestController;
import co.kr.pcninc.data.marketcore.domain.response.FileResponse;
import co.kr.pcninc.data.marketcore.util.ObjectConverter;
import co.kr.pcninc.data.marketcore.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.omg.CORBA.Current;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import co.kr.pcninc.data.marketcore.util.UUIDConverter;

import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

    @Value("${hadoop.hdfs.url}")
    private String fsUrl;

    private final String ROOT_PATH = "/data-market/data/";

    public FileResponse uploadHadoop(List<MultipartFile> fileList) {
        String prePath = null;


        try{
            FileSystem newFS = connectFs();

            prePath = pathGenerate();
            for (MultipartFile multipartFile : fileList) {
                FSDataOutputStream fsDataOutputStream = newFS.create(new Path(prePath + multipartFile.getOriginalFilename()));
                fsDataOutputStream.write(multipartFile.getBytes());
                fsDataOutputStream.close();
            }
            newFS.close();

        }catch (IOException e) {
            log.error("{}", "HDFS 연결에 실패헀습니다.");

            e.printStackTrace();
        }

        return new FileResponse(prePath);
    }

    public String pathGenerate() {
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("Current User : {}", authentication);
        if(authentication.equals("anonymousUser")) authentication = "";

        UUID uuid = UUID.randomUUID();
        String unqTimePath = UUIDConverter.toUnsignedString(uuid.getMostSignificantBits(), 6)
                + UUIDConverter.toUnsignedString(uuid.getLeastSignificantBits(), 6);

        String[] paths = {((UserDetails) authentication).getUsername(), unqTimePath, ""};

        return ROOT_PATH + StringUtils.join(paths, "/");
    }


    public FileSystem connectFs() {
        FileSystem fs = null;

        try {
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", fsUrl);
            conf.setBoolean("fs.hdfs.impl.disable.cache", true);
            conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());

            fs = FileSystem.get(conf);
        }catch (IOException e) {
            log.error("{}", "HDFS 연결에 실패헀습니다.");
        }

        return fs;
    }

    private String getCurrentUser() {
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ((UserDetails) authentication).getUsername();
    }
}
