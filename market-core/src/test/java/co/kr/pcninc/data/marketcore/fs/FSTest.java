package co.kr.pcninc.data.marketcore.fs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FSTest {

    @Test
    public void fileUploadTest() throws IOException {

        String filePath = "C:\\Users\\pcn\\Desktop\\210616_IoT.xlsx";
        String dir = "/ddd";


        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://14.49.44.164:9000");
        conf.setBoolean("fs.hdfs.impl.disable.cache", true);
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());

        FileSystem fs = FileSystem.get(conf);


        FSDataOutputStream fsDataOutputStream = fs.create(new Path(dir));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fsDataOutputStream, StandardCharsets.UTF_8));
        bufferedWriter.write("Java API to append data in HDFS file");
        bufferedWriter.newLine();
        bufferedWriter.close();
        fs.close();

    }
}

