package com.pag.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MaxKbDocument {

    private long id;
    private String name;
    private int charLength;
    private String status;
    private Boolean isActive;
    private String type;
    private Object meta;
    private long datasetId;
    private String hitHandlingMethod;
    private double directlyReturnSimilarity;
    private String files;

    private String creator;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private String updater;
    private Date updateTime;
    private int deleted;
    private int tenantId;

    public MaxKbDocument() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCharLength() {
        return charLength;
    }

    public void setCharLength(int charLength) {
        this.charLength = charLength;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getMeta() {
        return meta;
    }

    public void setMeta(Object meta) {
        this.meta = meta;
    }

    public long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(long datasetId) {
        this.datasetId = datasetId;
    }

    public String getHitHandlingMethod() {
        return hitHandlingMethod;
    }

    public void setHitHandlingMethod(String hitHandlingMethod) {
        this.hitHandlingMethod = hitHandlingMethod;
    }

    public double getDirectlyReturnSimilarity() {
        return directlyReturnSimilarity;
    }

    public void setDirectlyReturnSimilarity(double directlyReturnSimilarity) {
        this.directlyReturnSimilarity = directlyReturnSimilarity;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }
}
