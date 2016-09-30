package com.callippus.web.beans.dto;

public class AwardCategoryDTO extends CommonDTO{
 private String parentID;
 private String orgName;
 
public String getOrgName() {
    return orgName;
}

public void setOrgName(String orgName) {
    this.orgName = orgName;
}

public String getParentID() {
    return parentID;
}

public void setParentID(String parentID) {
    this.parentID = parentID;
}
 
}
