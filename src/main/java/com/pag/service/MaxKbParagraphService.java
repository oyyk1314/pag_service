package com.pag.service;

import com.pag.mapper.MaxKbDocumentMapper;
import com.pag.mapper.MaxKbParagraphMapper;
import com.pag.pojo.MaxKbDocument;
import com.pag.pojo.MaxKbParagraph;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MaxKbParagraphService {

    @Autowired
    private MaxKbParagraphMapper maxKbParagraphMapper;


    public int insert(MaxKbParagraph maxKbParagraph) {
        return maxKbParagraphMapper.insert(maxKbParagraph);
     }

    public int batchInsert(List<MaxKbParagraph> list){

        return maxKbParagraphMapper.batchInsert(list);
    }

 }
