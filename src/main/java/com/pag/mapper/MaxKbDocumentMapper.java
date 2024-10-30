package com.pag.mapper;

import com.pag.pojo.MaxKbDataset;
import com.pag.pojo.MaxKbDocument;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaxKbDocumentMapper {

    public int insert(MaxKbDocument maxKbDocument);


}

