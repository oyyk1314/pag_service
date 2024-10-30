package com.pag.unit.file;

/**
 * 文件名大小限制异常类
 * 
 * @author qcxx
 */
public class FileSizeLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(String defaultMaxSize)
    {
        super("upload.exceed.maxSize", new String[] { defaultMaxSize });
    }
}
