package com.callippus.web.beans.retirement;

import java.util.List;

public class RetirementBean {
    private String id;
	private String param;
	private String type;
	private String sfid;
	private String idNo;
	private String retirementType;
	private String retirementDate;
	private String referenceNumber;
	private String retirementTypeName;
	private String creationDate;
	private String lastModifiedDate;
	private int status;
    private String message;
    
    private String retireSfid;
    private String yearId;
   

	private String startDate;
    private String endDate;
    private String userSfid;
    
    private String eol;
    private String cl;
    private String hpl;
    private String el;
    
    
	
    private String nominationConfirmationFlag;
    private String familyDetailsFlag;
    
    private String confirmationOfService;
    
    
    
   

	private String leaveType;
    private String totalLeaves;
    private Integer leaveTypeId;
    
    private String familyNomination;
    

	private String familyDetails;
    
    
    public Integer getLeaveTypeId() {
		return leaveTypeId;
	}
	public void setLeaveTypeId(Integer leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}
	public String getFamilyNomination() {
		return familyNomination;
	}
	public void setFamilyNomination(String familyNomination) {
		this.familyNomination = familyNomination;
	}
	public String getFamilyDetails() {
		return familyDetails;
	}
	public void setFamilyDetails(String familyDetails) {
		this.familyDetails = familyDetails;
	}
	
    
    
    
    public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getTotalLeaves() {
		return totalLeaves;
	}
	public void setTotalLeaves(String totalLeaves) {
		this.totalLeaves = totalLeaves;
	}
    public String getConfirmationOfService() {
		return confirmationOfService;
	}
	public void setConfirmationOfService(String confirmationOfService) {
		this.confirmationOfService = confirmationOfService;
	}
	public String getEol() {
		return eol;
	}
	public void setEol(String eol) {
		this.eol = eol;
	}
	public String getCl() {
		return cl;
	}
	public void setCl(String cl) {
		this.cl = cl;
	}
	public String getHpl() {
		return hpl;
	}
	public void setHpl(String hpl) {
		this.hpl = hpl;
	}
	public String getEl() {
		return el;
	}
	public void setEl(String el) {
		this.el = el;
	}
	public String getNominationConfirmationFlag() {
		return nominationConfirmationFlag;
	}
	public void setNominationConfirmationFlag(String nominationConfirmationFlag) {
		this.nominationConfirmationFlag = nominationConfirmationFlag;
	}
	public String getFamilyDetailsFlag() {
		return familyDetailsFlag;
	}
	public void setFamilyDetailsFlag(String familyDetailsFlag) {
		this.familyDetailsFlag = familyDetailsFlag;
	}

	
    
    public String getUserSfid() {
		return userSfid;
	}
	public void setUserSfid(String userSfid) {
		this.userSfid = userSfid;
	}
	public String getYearId() {
		return yearId;
	}
	public void setYearId(String yearId) {
		this.yearId = yearId;
	}
	public String getRetireSfid() {
		return retireSfid;
	}
	public void setRetireSfid(String retireSfid) {
		this.retireSfid = retireSfid;
	}
	public String getRetirementType() {
        return retirementType;
    }
    public void setRetirementType(String retirementType) {
        this.retirementType = retirementType;
    }
    public String getRetirementDate() {
        return retirementDate;
    }
    public void setRetirementDate(String retirementDate) {
        this.retirementDate = retirementDate;
    }
    public String getRetirementTypeName() {
        return retirementTypeName;
    }
    public void setRetirementTypeName(String retirementTypeName) {
        this.retirementTypeName = retirementTypeName;
    }
    public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}	
	public String getIdNo() {
        return idNo;
    }
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    
	private List<RetirementBean> retirementList;
	
	
    public List<RetirementBean> getRetirementList() {
        return retirementList;
    }
    public void setRetirementList(List<RetirementBean> retirementList) {
        this.retirementList = retirementList;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getParam() {
        return param;
    }
    public void setParam(String param) {
        this.param = param;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getSfid() {
        return sfid;
    }
    public void setSfid(String sfid) {
        this.sfid = sfid;
    }
    public String getReferenceNumber() {
        return referenceNumber;
    }
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
    public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
