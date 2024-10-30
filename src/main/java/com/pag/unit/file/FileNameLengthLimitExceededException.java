package com.pag.unit.file;

/**
 * 文件名称超长限制异常类
 * 
 * @author qcxx
 */
public class FileNameLengthLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(String defaultFileNameLength)
    {
        super("upload.filename.exceed.length", new String[] { defaultFileNameLength });
    }
}
