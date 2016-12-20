package com.linbingcheng.example.easemob.model;

import java.util.Date;
import java.util.List;

public class EasemobChatRoom {
    private String id;

    private String name;

    private String description;

    private Integer maxusers;

    private Integer affiliationsCount;

    private String owner;

    private Date createTime;

    private List<EasemobUser> affiliations;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getMaxusers() {
        return maxusers;
    }

    public void setMaxusers(Integer maxusers) {
        this.maxusers = maxusers;
    }

    public Integer getAffiliationsCount() {
        return affiliationsCount;
    }

    public void setAffiliationsCount(Integer affiliationsCount) {
        this.affiliationsCount = affiliationsCount;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public List<EasemobUser> getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(List<EasemobUser> affiliations) {
        this.affiliations = affiliations;
    }
}