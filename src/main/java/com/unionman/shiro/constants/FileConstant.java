package com.unionman.shiro.constants;

import java.io.File;

/**
 * @description: 文件常量类
 * @date 2019/02/19 15:24:22
 * @author Rong.Jia
 */
public class FileConstant {

    public static final Integer FILE_SIZE = 1024;

    public static final String USER_DIR = "user.dir";

    public static final String LOGS_DIR ="logs";

    public static final Integer FILE_SIZE_THRESHOLD = 20;

    public static final String SEPARATOR = File.separator;

    public static final String FILE_DIR = "data" + SEPARATOR + "tmp";

    /**
     * 文件后缀
     */
    public static final String FILE_SUFFIX = ".jpeg";

    public static final String FORMAT_NAME = "jpeg";

    /**
     * 操作系统的名称
     */
    public static final String SYSTEM_ENVIRONMENT = "os.name";

    /**
     * linux 系统
     */
    public static final String LINUX_SYSTEM = "Linux";

    /**
     * 图片顶级目录
     */
    public static final String OFFLINE_IMAGES = "offline_images";

    /**
     * win 顶级图片路径
     */
    public static final String WIN_DIR_BASE = "F:";
    public static final String WIN_DIR = WIN_DIR_BASE + FileConstant.SEPARATOR;

    /**
     * 人员图片顶级路径
     */
    public static final String PEOPLE_IMAGES = OFFLINE_IMAGES + SEPARATOR + "people_images";

    /**
     * 身份证头像路径
     */
    public static final String ID_IMAGES = PEOPLE_IMAGES + SEPARATOR + "id_images";

    /**
     * 拍摄人脸路径
     */
    public static final String FACE_IMAGES = PEOPLE_IMAGES + SEPARATOR + "face_images";

    public static final String HC_EHOME_LIB = "hc_ehome_lib";

}
