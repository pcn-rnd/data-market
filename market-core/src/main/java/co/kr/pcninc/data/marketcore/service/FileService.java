package co.kr.pcninc.data.marketcore.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class FileService {

    @Value("${hadoop.hdfs.url}")
    private String fsUrl;

    public void uploadHadoop(List<MultipartFile> fileList) {
        try{
            FileSystem newFS = connectFs();

            for (MultipartFile multipartFile : fileList) {
                FSDataOutputStream fsDataOutputStream = newFS.create(new Path("/data/" + multipartFile.getOriginalFilename()));
                fsDataOutputStream.write(multipartFile.getBytes());
                fsDataOutputStream.close();
            }
            newFS.close();

        }catch (IOException e) {
            log.error("{}", "HDFS 연결에 실패헀습니다.");

            e.printStackTrace();
        }
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
}
