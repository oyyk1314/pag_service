package com.pag.mapper;

import com.pag.pojo.MaxKbDocument;
import com.pag.pojo.MaxKbParagraph;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaxKbParagraphMapper {

    public int insert(MaxKbParagraph maxKbParagraph);

    public int batchInsert(List<MaxKbParagraph> list);


}

