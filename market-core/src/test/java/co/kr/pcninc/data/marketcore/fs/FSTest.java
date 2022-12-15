package co.kr.pcninc.data.marketcore.fs;

import co.kr.pcninc.data.marketcore.api.AuthRestController;
import co.kr.pcninc.data.marketcore.domain.User;
import co.kr.pcninc.data.marketcore.util.StringUtils;
import co.kr.pcninc.data.marketcore.util.UUIDConverter;
import groovy.util.logging.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
public class FSTest {
/*
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


    @Test
    public void UuidUtilTest() {
        UUID uuid = UUID.randomUUID();
        String unqTimePath = UUIDConverter.toUnsignedString(uuid.getMostSignificantBits(), 6)
                + UUIDConverter.toUnsignedString(uuid.getLeastSignificantBits(), 6);

        String[] paths = {"", unqTimePath, ""};

        System.out.println(StringUtils.join(paths, "/"));

    }*/

    @Test
    public void start() throws NoSuchAlgorithmException {
        System.out.println(encryptSha256("1234", "admin"));
    }

    public static String encryptSha256(String value, String salt) throws NoSuchAlgorithmException {
        MessageDigest md;
        StringBuilder sb = new StringBuilder(org.apache.commons.lang3.StringUtils.EMPTY);
        String reversedSalt = new StringBuffer(salt).reverse().toString();
        String preSalt = salt.length() > 3 ? salt.substring(0, 3) : salt;
        String sufSalt = reversedSalt.length() > 3 ? reversedSalt.substring(0, 3) : reversedSalt;
        String saltAddedValue = preSalt + value + sufSalt;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(saltAddedValue.getBytes());
            byte[] byteData = md.digest();
            for (byte tmpStrByte : byteData) {
                String tmpEncTxt = Integer.toString((tmpStrByte & 0xff) + 0x100, 16).substring(1);
                sb.append(tmpEncTxt);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}

