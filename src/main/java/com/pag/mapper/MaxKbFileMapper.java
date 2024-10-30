package com.pag.mapper;

import com.pag.pojo.MaxKbFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaxKbFileMapper {


    public int insert(MaxKbFile maxKbFile);

    /**
     * 用文件生成的MD5查询
     * @param md5
     * @return
     */
    public int queryByFile(String md5);

 }

