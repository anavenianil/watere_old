package com.callippus.web.beans.preOrgnDetails;

import java.util.List;

public class PreOrgnDetailsBean {
    private String id;
    private String sfid;
    private String orgType;
    private String orgName;
    private String fromDate;
    private String toDate;
    private String divisionName;
    private String rankHeld;
    private String jobDescription;
    private String skills;
    private String remarks;
    private String param;
    private String type;
    private String pay;
    private String scaleOfPay;
    private List<PreOrgnDetailsBean> preOrgList;
    private String orgTypeId;
    
    
    
    
    
    public String getOrgTypeId() {
		return orgTypeId;
	}
	public void setOrgTypeId(String orgTypeId) {
		this.orgTypeId = orgTypeId;
	}
	public List<PreOrgnDetailsBean> getPreOrgList() {
		return preOrgList;
	}
	public void setPreOrgList(List<PreOrgnDetailsBean> preOrgList) {
		this.preOrgList = preOrgList;
	}
	public String getPay() {
        return pay;
    }
    public void setPay(String pay) {
        this.pay = pay;
    }
    public String getScaleOfPay() {
        return scaleOfPay;
    }
    public void setScaleOfPay(String scaleOfPay) {
        this.scaleOfPay = scaleOfPay;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getParam() {
        return param;
    }
    public void setParam(String param) {
        this.param = param;
    }
    public void setId(String id)
    {
        this.id=id;
    }
    public String getId()
    {
        return this.id;
    }
    public void setSfid(String sfid)
    {
        this.sfid=sfid;
    }
    public String getSfid()
    {
        return this.sfid;
    }
    public void setOrgType(String orgType)
    {
        this.orgType=orgType;
    }
    public String getOrgType()
    {
        return this.orgType;
    }
    public void setOrgName(String orgName)
    {
        this.orgName=orgName;
    }
    public String getOrgName()
    {
        return this.orgName;
    }
    public void setFromDate(String fromDate)
    {
        this.fromDate=fromDate;
    }
    public String getFromDate()
    {
        return this.fromDate;
    }
    public void setToDate(String toDate)
    {
        this.toDate=toDate;
    }
    public String getToDate()
    {
        return this.toDate;
    }
    public void setDivisionName(String divisionName)
    {
        this.divisionName=divisionName;
    }
    public String getDivisionName()
    {
        return this.divisionName;
    }
    public void setRankHeld(String rankHeld)
    {
        this.rankHeld=rankHeld;
    }
    public String getRankHeld()
    {
        return this.rankHeld;
    }
    public void setJobDescription(String jobDescription)
    {
        this.jobDescription=jobDescription;
    }
    public String getJobDescription()
    {
        return this.jobDescription;
    }
    public void setSkills(String skills)
    {
        this.skills=skills;
    }
    public String getSkills()
    {
        return this.skills;
    }
    public void setRemarks(String remarks)
    {
        this.remarks=remarks;
    }
    public String getRemarks()
    {
        return this.remarks;
    }
        

}
