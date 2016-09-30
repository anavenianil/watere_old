package com.callippus.web.retriments.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RETRIMENT_REQUEST_DETAILS")
public class RetrimentDTO {

	private Integer id;
	private String sfID;
	private String empName;
	private String empType;
	private Integer noOfPerson;
	private Date retrimentDate;
	private Float retrimentAmt;
	private Float transportAmt;
	private Float luggageAmt;
	private Float noofTons;
	private Float totAmt;
	private Integer status;
	private String createdBy;
	private Date appliedDate;
	private String ipAddr;
	private String cashOrCheqe;
	private String bankName;
	private String branchName;
	private String chequeNo;
	private Date dvDate;
	private String dvno;
	private String issuedBy;

	@Id
	@SequenceGenerator(name = "RetrimentSeq", sequenceName = "RETRIMENTS_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RetrimentSeq")
	@Column(name = "ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "SFID", nullable = true)
	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	@Column(name = "EMP_NAME", nullable = true)
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name = "EMP_TYPE", nullable = true)
	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	@Column(name = "NO_OF_TONS", nullable = true)
	public Float getNoofTons() {
		return noofTons;
	}

	public void setNoofTons(Float noofTons) {
		this.noofTons = noofTons;
	}

	@Column(name = "LUGGAGE_AMT", nullable = true)
	public Float getLuggageAmt() {
		return luggageAmt;
	}

	public void setLuggageAmt(Float luggageAmt) {
		this.luggageAmt = luggageAmt;
	}

	@Column(name = "TRANSPORT_AMT", nullable = true)
	public Float getTransportAmt() {
		return transportAmt;
	}

	public void setTransportAmt(Float transportAmt) {
		this.transportAmt = transportAmt;
	}

	@Column(name = "RETRIMENT_AMT", nullable = true)
	public Float getRetrimentAmt() {
		return retrimentAmt;
	}

	public void setRetrimentAmt(Float retrimentAmt) {
		this.retrimentAmt = retrimentAmt;
	}

	@Column(name = "TOT_AMT", nullable = true)
	public Float getTotAmt() {
		return totAmt;
	}

	public void setTotAmt(Float totAmt) {
		this.totAmt = totAmt;
	}

	@Column(name = "STATUS", nullable = true)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "NO_OF_PERSONS", nullable = true)
	public Integer getNoOfPerson() {
		return noOfPerson;
	}

	public void setNoOfPerson(Integer noOfPerson) {
		this.noOfPerson = noOfPerson;
	}

	@Column(name = "RETRIMENT_DATE", nullable = true)
	public Date getRetrimentDate() {
		return retrimentDate;
	}

	public void setRetrimentDate(Date retrimentDate) {
		this.retrimentDate = retrimentDate;
	}

	@Column(name = "CREATED_BY", nullable = true)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "IP_ADDRESS", nullable = true)
	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	@Column(name = "APPLIED_DATE", nullable = true)
	public Date getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}

	@Column(name = "CASH_OR_CHEQUE", nullable = true)
	public String getCashOrCheqe() {
		return cashOrCheqe;
	}

	public void setCashOrCheqe(String cashOrCheqe) {
		this.cashOrCheqe = cashOrCheqe;
	}

	@Column(name = "BANK_NAME", nullable = true)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "BRANCH_NAME", nullable = true)
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Column(name = "CHEQUE_NO", nullable = true)
	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	@Column(name = "DVDATE", nullable = true)
	public Date getDvDate() {
		return dvDate;
	}

	public void setDvDate(Date dvDate) {
		this.dvDate = dvDate;
	}

	@Column(name = "DVNO", nullable = true)
	public String getDvno() {
		return dvno;
	}

	public void setDvno(String dvno) {
		this.dvno = dvno;
	}

	@Column(name = "ISSUED_BY", nullable = true)
	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RetrimentDTO [id=");
		builder.append(id);
		builder.append(", sfID=");
		builder.append(sfID);
		builder.append(", empName=");
		builder.append(empName);
		builder.append(", empType=");
		builder.append(empType);
		builder.append(", noOfPerson=");
		builder.append(noOfPerson);
		builder.append(", retrimentDate=");
		builder.append(retrimentDate);
		builder.append(", retrimentAmt=");
		builder.append(retrimentAmt);
		builder.append(", transportAmt=");
		builder.append(transportAmt);
		builder.append(", luggageAmt=");
		builder.append(luggageAmt);
		builder.append(", noofTons=");
		builder.append(noofTons);
		builder.append(", totAmt=");
		builder.append(totAmt);
		builder.append(", status=");
		builder.append(status);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", appliedDate=");
		builder.append(appliedDate);
		builder.append(", ipAddr=");
		builder.append(ipAddr);
		builder.append(", cashOrCheqe=");
		builder.append(cashOrCheqe);
		builder.append(", bankName=");
		builder.append(bankName);
		builder.append(", branchName=");
		builder.append(branchName);
		builder.append(", chequeNo=");
		builder.append(chequeNo);
		builder.append(", dvDate=");
		builder.append(dvDate);
		builder.append(", dvno=");
		builder.append(dvno);
		builder.append("]");
		return builder.toString();
	}

}
