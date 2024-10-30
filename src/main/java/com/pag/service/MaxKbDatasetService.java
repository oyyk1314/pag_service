package com.pag.service;

import com.pag.mapper.MaxKbDatasetMapper;
import com.pag.pojo.MaxKbDataset;
import com.pag.unit.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MaxKbDatasetService {

    @Autowired
    private MaxKbDatasetMapper maxKbDatasetMapper;


    public List<MaxKbDataset> findByName(String name) {
        return maxKbDatasetMapper.findByName(name);
    }


    public List<MaxKbDataset> queryPage(Integer startRows) {
        return maxKbDatasetMapper.queryPage(startRows);
    }

    public int getRowCount() {
        return maxKbDatasetMapper.getRowCount();
    }

    public MaxKbDataset insert(MaxKbDataset maxKbDataset) {
        Snowflake snowflake = new Snowflake(1, 1);
        maxKbDataset.setId(snowflake.nextId());
        maxKbDataset.setUserId("test");
        maxKbDatasetMapper.insert(maxKbDataset);
        return maxKbDataset;
    }

    public List<MaxKbDataset> List(){
        return maxKbDatasetMapper.List();
    }

    public int update(MaxKbDataset maxKbDataset){
        return maxKbDatasetMapper.update(maxKbDataset);
    }

    public int delete(Long id){
        return maxKbDatasetMapper.delete(id);
    }

}
