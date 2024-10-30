package com.pag.service;

import com.pag.mapper.MaxKbEmbeddingMapper;
import com.pag.mapper.MaxKbParagraphMapper;
import com.pag.pojo.MaxKbEmbedding;
import com.pag.pojo.MaxKbParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MaxKbEmbeddingService {

    @Autowired
    private MaxKbEmbeddingMapper maxKbEmbeddingMapper;


    public int insert(MaxKbEmbedding maxKbEmbedding) {
        return maxKbEmbeddingMapper.insert(maxKbEmbedding);
     }


 }
