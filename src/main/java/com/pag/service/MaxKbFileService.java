package com.pag.service;

import com.pag.mapper.MaxKbDatasetMapper;
import com.pag.mapper.MaxKbFileMapper;
import com.pag.pojo.MaxKbDataset;
import com.pag.pojo.MaxKbFile;
import com.pag.unit.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MaxKbFileService {

    @Autowired
    private MaxKbFileMapper maxKbFileMapper;


    public int insert(MaxKbFile maxKbFile) {
        return maxKbFileMapper.insert(maxKbFile);
     }

    public int queryByFile(String md5) {
        return maxKbFileMapper.queryByFile(md5);
    }
}
