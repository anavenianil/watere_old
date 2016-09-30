package com.callippus.web.beans.dto;

public class DispensaryLocationDTO extends CommonDTO {
    private String dispensaryNumber;
    private String parentID;

    public String getDispensaryNumber() {
        return dispensaryNumber;
    }

    public void setDispensaryNumber(String dispensaryNumber) {
        this.dispensaryNumber = dispensaryNumber;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

}
