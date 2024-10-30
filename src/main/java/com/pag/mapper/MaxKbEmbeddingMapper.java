package com.pag.mapper;

import com.pag.pojo.MaxKbEmbedding;
import com.pag.pojo.MaxKbParagraph;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaxKbEmbeddingMapper {

    public int insert(MaxKbEmbedding maxKbEmbedding);

    /**
     * 查询
     * @param val
     * @return
     */
    public String queryVectorSentence(String val);

}

