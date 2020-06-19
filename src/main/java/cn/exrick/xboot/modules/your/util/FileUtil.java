package cn.exrick.xboot.modules.your.util;

import cn.exrick.xboot.common.exception.XbootException;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author Exrickx
 */
@Component
@Slf4j
public class FileUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${file.uploadFolder}")
    private String uploadPath;
    @Value("${file.imageUrl}")
    private String imageUrl;

    /**
     * 以UUID重命名
     *
     * @param fileName
     * @return
     */
    public static String renamePic(String fileName) {
        String extName = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString().replace("-", "") + extName;
    }

    public static InputStream returnBitMap(String path) {
        URL url = null;
        InputStream is = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();//利用HttpURLConnection对象,我们可以从网络中获取网页数据.
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();    //得到网络返回的输入流

        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    public static String replaceOldChar(String sourceStr, String oldChar, String newChar) {
        return StringUtils.replace(sourceStr, oldChar, newChar);
    }

    /**
     * 文件路径上传
     *
     * @param file
     * @param key
     * @return
     */
    public String localUpload(MultipartFile file, String key) {

        String day = DateUtil.format(DateUtil.date(), "yyyyMMdd");
        String path = uploadPath + "/" + day;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(path + "/" + key);
        if (f.exists()) {
            throw new XbootException("文件名已存在");
        }
        try {
            file.transferTo(f);
            return imageUrl + day + "/" + key;
        } catch (IOException e) {
            log.error(e.toString());
            throw new XbootException("上传文件出错");
        }
    }

    /**
     * 读取文件
     *
     * @param url
     * @param response
     */
    public void view(String url, HttpServletResponse response) {

        File file = new File(url);
        FileInputStream i = null;
        OutputStream o = null;

        try {
            i = new FileInputStream(file);
            o = response.getOutputStream();

            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = i.read(buf)) > 0) {
                o.write(buf, 0, bytesRead);
                o.flush();
            }

            i.close();
            o.close();
        } catch (IOException e) {
            log.error(e.toString());
            throw new XbootException("读取文件出错");
        }
    }

    /**
     * 重命名
     *
     * @param url
     * @param toKey
     * @return
     */
    public String renameFile(String url, String toKey) {

        String result = copyFile(url, toKey);
        deleteFile(url);
        return result;
    }

    /**
     * 复制文件
     *
     * @param url
     * @param toKey
     */
    public String copyFile(String url, String toKey) {

        File file = new File(url);
        FileInputStream i = null;
        FileOutputStream o = null;

        try {
            i = new FileInputStream(file);
            o = new FileOutputStream(new File(file.getParentFile() + "/" + toKey));

            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = i.read(buf)) > 0) {
                o.write(buf, 0, bytesRead);
            }

            i.close();
            o.close();
            return file.getParentFile() + "/" + toKey;
        } catch (IOException e) {
            log.error(e.toString());
            throw new XbootException("复制文件出错");
        }
    }

    /**
     * 删除文件
     *
     * @param url
     */
    public void deleteFile(String url) {

        File file = new File(url);
        if (!file.exists()) {
            System.out.println("文件不存在，删除失败。");
        }
        try {
            boolean deleteResult = file.delete();
            System.out.println(deleteResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public HttpServletResponse download(String path, String name, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        try {

            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(path)
                    .build();
            Response response = null;
            InputStream inputStream = null;
            try {
                response = httpClient.newCall(request).execute();
                assert response.body() != null;
                inputStream = response.body().byteStream();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 取得文件的后缀名。
            String suffix = path.substring(path.lastIndexOf("."));

            String filename = name.substring(0, name.lastIndexOf(".")) + suffix;

            // 以流的形式下载文件。
            assert inputStream != null;
            byte[] buffer = readInputStream(inputStream);
            inputStream.read(buffer);
            inputStream.close();
            // 清空response
            servletResponse.reset();
            String userAgent = servletRequest.getHeader("User-Agent");
            if (userAgent.contains("MSIE") || userAgent.contains("Trident") || userAgent.contains("Edge")) {
                filename = java.net.URLEncoder.encode(filename, "UTF-8");
            } else {
                // 非IE浏览器的处理：
                filename = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
            // 设置response的Header
            servletResponse.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", filename));
            servletResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
            servletResponse.setCharacterEncoding("UTF-8");
            OutputStream toClient = new BufferedOutputStream(servletResponse.getOutputStream());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            log.info("文件地址不存在");
            return null;
        }
        return servletResponse;
    }

    private byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        byte[] buff = new byte[1024 * 2];
        int len = 0;
        try {
            while ((len = is.read(buff)) != -1) {
                writer.write(buff, 0, len);
            }
            //is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toByteArray();
    }
}
