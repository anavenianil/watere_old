package com.callippus.web.retriments.beans;

import java.util.Date;
import java.util.List;

import com.callippus.web.tada.dto.BankNamesDTO;

public class RetrimentBean {
	
	private String sfID;
	private String param;
	private String result;
	private String type;
	private float retrimentAmt;
	private float transportAmt;
	private int noOfPerson;
	private float noofTons;
	private float luggageAmt;
	private float totAmt;
	private String empType;
	private Date retrimentDate;
	private String changeSfid;
	private String empName;
	private String message;
	private Integer id;
	private Integer updateID;
	private Integer status;
	private String sfId;
	private Date dvDate;
	private String dvno;
	private String cashorcheck;
	private String bankName;
	private String branchName;
	private String chequeNo;
	private List<BankNamesDTO> bankNamesList;
	private String param1;
	
	
	
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public List<BankNamesDTO> getBankNamesList() {
		return bankNamesList;
	}
	public void setBankNamesList(List<BankNamesDTO> bankNamesList) {
		this.bankNamesList = bankNamesList;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	public String getCashorcheck() {
		return cashorcheck;
	}
	public void setCashorcheck(String cashorcheck) {
		this.cashorcheck = cashorcheck;
	}
	public Date getDvDate() {
		return dvDate;
	}
	public void setDvDate(Date dvDate) {
		this.dvDate = dvDate;
	}
	public String getDvno() {
		return dvno;
	}
	public void setDvno(String dvno) {
		this.dvno = dvno;
	}
	public String getSfId() {
		return sfId;
	}
	public void setSfId(String sfId) {
		this.sfId = sfId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getUpdateID() {
		return updateID;
	}
	public void setUpdateID(Integer updateID) {
		this.updateID = updateID;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getChangeSfid() {
		return changeSfid;
	}
	public void setChangeSfid(String changeSfid) {
		this.changeSfid = changeSfid;
	}
	public String getEmpType() {
		return empType;
	}
	public void setEmpType(String empType) {
		this.empType = empType;
	}
	public Date getRetrimentDate() {
		return retrimentDate;
	}
	public void setRetrimentDate(Date retrimentDate) {
		this.retrimentDate = retrimentDate;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getRetrimentAmt() {
		return retrimentAmt;
	}
	public void setRetrimentAmt(float retrimentAmt) {
		this.retrimentAmt = retrimentAmt;
	}
	public float getTransportAmt() {
		return transportAmt;
	}
	public void setTransportAmt(float transportAmt) {
		this.transportAmt = transportAmt;
	}
	public int getNoOfPerson() {
		return noOfPerson;
	}
	public void setNoOfPerson(int noOfPerson) {
		this.noOfPerson = noOfPerson;
	}
	public float getNoofTons() {
		return noofTons;
	}
	public void setNoofTons(float noofTons) {
		this.noofTons = noofTons;
	}
	public float getLuggageAmt() {
		return luggageAmt;
	}
	public void setLuggageAmt(float luggageAmt) {
		this.luggageAmt = luggageAmt;
	}
	public float getTotAmt() {
		return totAmt;
	}
	public void setTotAmt(float totAmt) {
		this.totAmt = totAmt;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RetrimentBean [sfID=");
		builder.append(sfID);
		builder.append(", param=");
		builder.append(param);
		builder.append(", result=");
		builder.append(result);
		builder.append(", type=");
		builder.append(type);
		builder.append(", retrimentAmt=");
		builder.append(retrimentAmt);
		builder.append(", transportAmt=");
		builder.append(transportAmt);
		builder.append(", noOfPerson=");
		builder.append(noOfPerson);
		builder.append(", noofTons=");
		builder.append(noofTons);
		builder.append(", luggageAmt=");
		builder.append(luggageAmt);
		builder.append(", totAmt=");
		builder.append(totAmt);
		builder.append(", empType=");
		builder.append(empType);
		builder.append(", retrimentDate=");
		builder.append(retrimentDate);
		builder.append(", changeSfid=");
		builder.append(changeSfid);
		builder.append(", empName=");
		builder.append(empName);
		builder.append(", message=");
		builder.append(message);
		builder.append(", id=");
		builder.append(id);
		builder.append(", updateID=");
		builder.append(updateID);
		builder.append(", status=");
		builder.append(status);
		builder.append(", sfId=");
		builder.append(sfId);
		builder.append(", dvDate=");
		builder.append(dvDate);
		builder.append(", dvno=");
		builder.append(dvno);
		builder.append(", cashorcheck=");
		builder.append(cashorcheck);
		builder.append(", bankName=");
		builder.append(bankName);
		builder.append(", branchName=");
		builder.append(branchName);
		builder.append(", chequeNo=");
		builder.append(chequeNo);
		builder.append(", bankNamesList=");
		builder.append(bankNamesList);
		builder.append("]");
		return builder.toString();
	}
	
}
