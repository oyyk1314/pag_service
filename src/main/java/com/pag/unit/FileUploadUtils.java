package com.pag.unit;


import com.pag.unit.file.FileNameLengthLimitExceededException;
import com.pag.unit.file.FileSizeLimitExceededException;
import com.pag.unit.file.InvalidExtensionException;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传工具类
 *
 */
public class FileUploadUtils {
    public static final long DEFAULT_MAX_SIZE = 52428800L;
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;
    private static String defaultBaseDir = "d://upload";

    public FileUploadUtils() {
    }

    public static void setDefaultBaseDir(String defaultBaseDir) {
        defaultBaseDir = defaultBaseDir;
    }

    public static String getDefaultBaseDir() {
        return defaultBaseDir;
    }

    public static final Map upload(MultipartFile file) throws IOException {
        try {
            return upload(getDefaultBaseDir(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception var2) {
            throw new IOException(var2.getMessage(), var2);
        }
    }

    public static final Map upload(String baseDir, MultipartFile file) throws IOException {
        try {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception var3) {
            throw new IOException(var3.getMessage(), var3);
        }
    }

    public static final Map upload(String baseDir, MultipartFile file, String[] allowedExtension) throws FileUploadBase.FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException, InvalidExtensionException {
        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > 100) {
            throw new FileNameLengthLimitExceededException("100");
        } else {
            assertAllowed(file, allowedExtension);
            String fileName = extractFilename(file);
            File desc = getAbsoluteFile(baseDir, fileName);
            file.transferTo(desc);

            String targetName = file.getOriginalFilename();

            String pathFileName = getPathFileName(baseDir, fileName);
            String pathFullFileName = getPathFullFileName(baseDir, fileName);
            Map<String, String> map = new HashMap();
            map.put("fileName", fileName);
            map.put("fileSize", Long.toString(file.getSize()));
            map.put("pathFullFileName", pathFullFileName);
            map.put("targetName", targetName);

            return map;
        }
    }

    public static final String extractFilename(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        Snowflake snowflake = new Snowflake(1, 1);
        fileName = snowflake.nextId()+ "." + extension;
        return fileName;
    }

    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);
        if (!desc.exists() && !desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }

        return desc;
    }

    private static final String getPathFileName(String uploadDir, String fileName) throws IOException {
        int dirLastIndex = defaultBaseDir.length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        String pathFileName =  currentDir + "/" + fileName;
        return pathFileName;
    }

    private static final String getPathFullFileName(String uploadDir, String fileName) throws IOException {
        String pathFileName = uploadDir + "/" + fileName;
        return pathFileName;
    }

    public static final void assertAllowed(MultipartFile file, String[] allowedExtension) throws FileSizeLimitExceededException, InvalidExtensionException {
        long size = file.getSize();
        if (size > 52428800L) {
            throw new FileSizeLimitExceededException("50L");
        } else {
            String fileName = file.getOriginalFilename();
            String extension = getExtension(file);
            if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
                if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
                    throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension, fileName);
                } else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION) {
                    throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension, fileName);
                } else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
                    throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension, fileName);
                } else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION) {
                    throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension, fileName);
                } else {
                    throw new InvalidExtensionException(allowedExtension, extension, fileName);
                }
            }
        }
    }

    public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
        String[] var2 = allowedExtension;
        int var3 = allowedExtension.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String str = var2[var4];
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }

        return false;
    }

    public static final String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(file.getContentType());
        }

        return extension;
    }
}
