package com.pag.controller;

import com.pag.pojo.MaxKbDataset;
import com.pag.service.MaxKbDatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MaxKbDatasetController {

    @Autowired
    private MaxKbDatasetService maxKbDatasetService;


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Integer search(String val) {
        //System.out.println(id);
        int result = maxKbDatasetService.delete(Long.parseLong(val));
        return result;
    }

    @RequestMapping(value = "/deleteDataset", method = RequestMethod.POST)
    public Integer delete(String id) {
        System.out.println(id);
        int result = maxKbDatasetService.delete(Long.parseLong(id));
        return result;
    }

    @RequestMapping(value = "/updateDataset", method = RequestMethod.POST)
    @ResponseBody
    public String update(MaxKbDataset maxKbDataset) {
        int result = maxKbDatasetService.update(maxKbDataset);
        System.out.println("asdfas=="+result);
        if (result >= 1) {
            return "修改成功";
        } else {
            return "修改失败";
        }

    }

    @RequestMapping(value = "/insertDataset", method = RequestMethod.POST)
    public MaxKbDataset insert(MaxKbDataset maxKbDataset) {
        return maxKbDatasetService.insert(maxKbDataset);
    }

    @RequestMapping("/ListDataset")
    @ResponseBody
    public List<MaxKbDataset> ListUser() {
        return maxKbDatasetService.List();
    }


    /**
     * 分页
     * @return
     */
    @RequestMapping(value="/pageDataset")
    @ResponseBody
    public List<MaxKbDataset> page(Integer page){
        int pageNow = page == null ? 1 : page;
        int pageSize = 5;
        int startRows = pageSize*(pageNow-1);
        List<MaxKbDataset> list = maxKbDatasetService.queryPage(startRows);
        return list;
    }


    @RequestMapping("/queryByName")
    @ResponseBody
    public List<MaxKbDataset> ListUserByName(String name) {
        return maxKbDatasetService.findByName(name);
    }


    /**
     * rows
     * @return
     */
    @RequestMapping(value="/rowsDataset")
    @ResponseBody
    public int rows(){
        return maxKbDatasetService.getRowCount();
    }


}
