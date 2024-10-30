package com.pag.unit;

/**
 * 媒体类型工具类
 * 
 * @author qcxx
 */
public class MimeTypeUtils {
    public static final String IMAGE_PNG = "image/png";
    public static final String IMAGE_JPG = "image/jpg";
    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String IMAGE_BMP = "image/bmp";
    public static final String IMAGE_GIF = "image/gif";
    public static final String[] IMAGE_EXTENSION = new String[]{"bmp", "gif", "jpg", "jpeg", "png"};
    public static final String[] FLASH_EXTENSION = new String[]{"swf", "flv"};
    public static final String[] MEDIA_EXTENSION = new String[]{"swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg", "asf", "rm", "rmvb"};
    public static final String[] VIDEO_EXTENSION = new String[]{"mp4", "avi", "rmvb", "wav"};
    public static final String[] DEFAULT_ALLOWED_EXTENSION = new String[]{"bmp", "gif", "jpg", "jpeg", "png", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt", "rar", "zip", "gz", "bz2", "mp4", "avi", "rmvb", "wav", "mp3", "pdf"};

    public MimeTypeUtils() {
    }

    public static String getExtension(String prefix) {
        byte var2 = -1;
        switch(prefix.hashCode()) {
            case -1487394660:
                if (prefix.equals("image/jpeg")) {
                    var2 = 2;
                }
                break;
            case -879272239:
                if (prefix.equals("image/bmp")) {
                    var2 = 3;
                }
                break;
            case -879267568:
                if (prefix.equals("image/gif")) {
                    var2 = 4;
                }
                break;
            case -879264467:
                if (prefix.equals("image/jpg")) {
                    var2 = 1;
                }
                break;
            case -879258763:
                if (prefix.equals("image/png")) {
                    var2 = 0;
                }
        }

        switch(var2) {
            case 0:
                return "png";
            case 1:
                return "jpg";
            case 2:
                return "jpeg";
            case 3:
                return "bmp";
            case 4:
                return "gif";
            default:
                return "";
        }
    }
}
