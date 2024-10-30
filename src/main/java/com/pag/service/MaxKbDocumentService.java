package com.pag.service;

import com.pag.mapper.MaxKbDocumentMapper;
import com.pag.mapper.MaxKbFileMapper;
import com.pag.pojo.MaxKbDocument;
import com.pag.pojo.MaxKbFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MaxKbDocumentService {

    @Autowired
    private MaxKbDocumentMapper maxKbDocumentMapper;


    public int insert(MaxKbDocument maxKbDocument) {
        return maxKbDocumentMapper.insert(maxKbDocument);
     }

 }
