package com.pag.mapper;

import com.pag.pojo.MaxKbDataset;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaxKbDatasetMapper {


    public List<MaxKbDataset> findByName(String name);


    public List<MaxKbDataset> List();

    public List<MaxKbDataset> queryPage(Integer startRows);

    public int getRowCount();

    public int insert(MaxKbDataset maxKbDataset);

    public int delete(Long id);

    public int update(MaxKbDataset maxKbDataset);
}

