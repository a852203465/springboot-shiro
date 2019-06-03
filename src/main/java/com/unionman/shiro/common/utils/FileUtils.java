package com.unionman.shiro.common.utils;


import com.alibaba.fastjson.util.IOUtils;
import com.unionman.shiro.common.constants.CommonConstant;
import com.unionman.shiro.common.constants.FileConstant;
import com.unionman.shiro.common.constants.NumberConstant;
import com.unionman.shiro.common.enums.ResponseEnum;
import com.unionman.shiro.common.exception.SpringbootShiroException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

/**
 * @description: 文件处理工具类
 * @author Rong.Jia
 * @date  2019/01/11 12:03
 */
@Slf4j
public class FileUtils {

    /**
     *
     * @description: URL 转 base64
     * @param requestUrl
     * @author Rong.Jia
     * @date 2019/01/11 11:20
     * @return String 图片base64
     * @throws IOException 文件写出异常
     */
    public static String urlToBase64(String requestUrl) throws IOException {

            URL url = new URL(requestUrl);
            URLConnection connection = url.openConnection();
            InputStream stream = connection.getInputStream();
            return inputStreamToBase64(stream);

    }

    /**
     * @description: base64 转InputStream
     * @param base64string base64文件
     * @date 2019/01/11 11:20
     * @author Rong.Jia
     * @return InputStream InputStream流对象
     * @throws IOException io流转换失败
     */
    public static InputStream baseToInputStream(String base64string) throws IOException {

        BASE64Decoder decoder = new BASE64Decoder();

        byte[] bytes1 = decoder.decodeBuffer(base64string);

        return new ByteArrayInputStream(bytes1);

    }

    /**
     * @description: 文件流转base64
     * @param in 文件流
     * @return String base64
     * @author Rong.Jia
     * @date 2019/01/11 11:20
     * @return String
     */
    public static String inputStreamToBase64 (InputStream in) {

        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[NumberConstant.HUNDRED];
            int rc ;
            while ((rc = in.read(buff, NumberConstant.ZERO, NumberConstant.HUNDRED)) > NumberConstant.ZERO) {
                swapStream.write(buff, NumberConstant.ZERO, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            log.error("class:FileUtils method:inputStreamToBase64() IOException {}", e.getMessage());
            throw new SpringbootShiroException(ResponseEnum.FILE_TYPE_CONVER_ERROR);
        } finally {
            IOUtils.close(in);
        }

        return new String(Base64.encodeBase64(data));

    }

    public static File base64ToFile(String base64) throws IOException{

        String filename = UUID.randomUUID().toString().substring(0, 8) + FileConstant.FILE_SUFFIX;

        // 判断文件夹是否存在
        CommonUtils.judeDirExists(new File(System.getProperty(FileConstant.USER_DIR) + FileConstant.FILE_DIR));

        String filePath = System.getProperty(FileConstant.USER_DIR) + FileConstant.FILE_DIR + FileConstant.SEPARATOR + filename;

        return Files.write(Paths.get(filePath),
                java.util.Base64.getDecoder().decode(base64),
                StandardOpenOption.CREATE).toFile();
    }

    /**
     * @description: base64 转File
     * @param filename 文件名
     * @param base64 base64
     * @param filesPath 指定保存路径
     * @return File
     * @author Rong.Jia
     * @date 2018/09/29 11:20
     * @throws Exception 异常抛出
     */
    public static File base64ToFile(String filename, String base64, String filesPath) throws Exception {

        String filePath = filesPath + FileConstant.SEPARATOR + filename;

        File file = null;

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;

        byte[] bytes =  java.util.Base64.getDecoder().decode(base64);
        file=new File(filePath);
        fos = new FileOutputStream(file);
        bos = new BufferedOutputStream(fos);
        bos.write(bytes);

        IOUtils.close(bos);
        IOUtils.close(fos);

        return file;

    }

    /**
     * @description: 将image(base64或者图片id) 转成File
     * @param image 图片
     * @return File 图片
     * @throws IOException 文件写出失败
     */
    public static File getImageFile(String image) throws IOException {

        File file = null;

        // true 包含，false 不包含
        boolean flag = image.startsWith(CommonConstant.HTTP_PREFIX);
        if (flag) {

            String base64 = FileUtils.urlToBase64(image);
            file = FileUtils.base64ToFile(base64);

        } else {

            file = FileUtils.base64ToFile(image);

        }

        return file;

    }

    /**
     * @description: file 转base64
     * @param file 文件
     * @return String base64
     * @throws IOException 文件转换失败
     */
    public static String fileToBase64(File file) throws IOException {

        return inputStreamToBase64(new FileInputStream(file));

    }

    /**
     * @description: url 转File
     * @param requestUrl  url
     * @date 2018/09/29 14:00
     * @author Rong.Jia
     * @return File
     * @throws IOException
     */
    public static File urlToFile(String requestUrl) throws IOException{

        URL url = new URL(requestUrl);
        URLConnection connection = url.openConnection();
        InputStream stream = connection.getInputStream();
        return inputstreamToFile(stream);

    }

    /**
     * @description: url 转File
     * @param requestUrl  url
     * @param dirPath 存储路径
     * @date 2019/04/03 10:45
     * @author Rong.Jia
     * @return File
     * @throws IOException
     */
    public static File urlToFile(String requestUrl, String dirPath) throws IOException {

        URL url = new URL(requestUrl);
        URLConnection connection = url.openConnection();
        InputStream stream = connection.getInputStream();
        return inputstreamToFile(stream, dirPath);

    }

    /**
     * @description: url 转File
     * @param requestUrl  url
     * @param dirPath 存储路径
     * @param fileName 文件名
     * @date 2019/04/03 10:45
     * @author Rong.Jia
     * @return File
     * @throws IOException
     */
    public static File urlToFile(String requestUrl, String dirPath, String fileName) throws IOException {

        URL url = new URL(requestUrl);
        URLConnection connection = url.openConnection();
        InputStream stream = connection.getInputStream();
        return inputstreamToFile(stream, dirPath, fileName);

    }

    /**
     * @description: ins 转File
     * @param ins  输入流
     * @date 2019/04/03 10:45
     * @author Rong.Jia
     * @return File
     */
    public static File inputstreamToFile(InputStream ins){

        String filename = UUID.randomUUID().toString().substring(0, 8) + FileConstant.FILE_SUFFIX;
        String dir = System.getProperty(FileConstant.USER_DIR) + FileConstant.FILE_DIR + FileConstant.SEPARATOR;

        // 判断文件夹是否存在
        CommonUtils.judeDirExists(new File(dir));

        File file = new File(dir + filename);
        writeFile(ins, file);

        return file;

    }

    /**
     * @description: ins 转File
     * @param dirPath 存储路径
     * @param ins  输入流
     * @date 2019/04/03 10:45
     * @author Rong.Jia
     * @return File
     */
    public static File inputstreamToFile(InputStream ins, String dirPath){

        String filename = UUID.randomUUID().toString().substring(0, 8) + FileConstant.FILE_SUFFIX;

        // 判断文件夹是否存在
        CommonUtils.judeDirExists(new File(dirPath));

        File file = new File(dirPath + filename);
        writeFile(ins, file);

        return file;

    }

    /**
     * @description: ins 转File
     * @param dirPath 存储路径
     * @param ins  输入流
     * @param fileName 文件名
     * @date 2018/12/05 13:45
     * @author Rong.Jia
     * @return File
     */
    public static File inputstreamToFile(InputStream ins, String dirPath, String fileName){

        // 判断文件夹是否存在
        CommonUtils.judeDirExists(new File(dirPath));

        File file = new File(dirPath + fileName);

        writeFile(ins, file);

        return file;

    }

    /**
     * @description: 将指定文件写入指定目录
     * @param ins 输入流
     * @param file 文件
     * @author Rong.Jia
     * @date 2019/12/05 12:55
     */
    private static void writeFile(InputStream ins, File file) {

        OutputStream os = null;

        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[NumberConstant.HUNDRED * NumberConstant.TWELVE];
            while ((bytesRead = ins.read(buffer, NumberConstant.ZERO, NumberConstant.HUNDRED * NumberConstant.TWELVE)) != -1) {
                os.write(buffer, NumberConstant.ZERO, bytesRead);
            }
        } catch (Exception e) {
            log.error("class:FileUtils method:writeFile() Exception {}", e.getMessage());
            throw new SpringbootShiroException(ResponseEnum.FILE_WRITE_ERROR);
        }finally {
            IOUtils.close(os);
            IOUtils.close(ins);
        }
    }

    /**
     * @description: multipartFile转File
     * @param multipartFile 待转文件
     * @author Rong.Jia
     * @date  2019/01/25 18:51
     * @return File 文件
     * @throws IOException 文件转换失败
     */
    public static File multipartFile2File(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        org.apache.commons.io.FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        return file;
    }

    /**
     * @description: 导出文件
     * @param aFileName 文件名
     * @param aFilePath 文件路径
     * @date 2019/01/28 15:17
     * @author Rong.Jia
     */
    public  static void doExport(String aFileName, String aFilePath, HttpServletRequest request, HttpServletResponse response){

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File file = null;
        file = new File(aFilePath);
        try{
            request.setCharacterEncoding("UTF-8");
            String agent = request.getHeader("User-Agent").toUpperCase();
            if (agent.indexOf("MSIE") > 0|| (agent.indexOf("RV") != -1&& agent.indexOf("FIREFOX") == -1)) {
                aFileName = URLEncoder.encode(aFileName, "UTF-8");
            } else {
                aFileName = new String(aFileName.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setContentType("application/x-msdownload;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + aFileName);
            response.setHeader("Content-Length", String.valueOf(file.length()));
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))){
                bos.write(buff, 0, bytesRead);
            }
            bos.flush();
            if(file.exists()){
                file.delete();
            }
        }catch (Exception e) {
            log.error("class:FileUtils method:doExport() IOException {}", e.getMessage());
            throw new SpringbootShiroException(ResponseEnum.FILE_WRITE_ERROR);

        } finally {
            IOUtils.close(bis);
            IOUtils.close(bos);
        }
    }

}
