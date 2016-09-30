package com.callippus.web.beans.increment;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class AnnualIncrementDTO extends CommonDTO {
	private Float basicPay;
	private Float gradePay;
	private String name;
	private String userSfid;
	private String designationName;
	private Float increment3;
	private Float incRoundOff;
	private Float incrementBasicPay;
	private Float incrementGradePay;
	private int doPartNo;
	private int casualityId;
	private int gazettedType;
	private int incrementYearId;
	private Date financeAcceptedDate;
	private Date adminEffectDate;
	private int annIncrId;
	private int empBasicPayHistoryId;
	private String basicPayId;
	private int designationId;
	private String basicGradeTotal;
	
	
	
	public int getAnnIncrId() {
		return annIncrId;
	}

	public void setAnnIncrId(int annIncrId) {
		this.annIncrId = annIncrId;
	}

	public int getEmpBasicPayHistoryId() {
		return empBasicPayHistoryId;
	}

	public void setEmpBasicPayHistoryId(int empBasicPayHistoryId) {
		this.empBasicPayHistoryId = empBasicPayHistoryId;
	}

	public Float getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(Float basicPay) {
		this.basicPay = basicPay;
	}

	public Float getGradePay() {
		return gradePay;
	}

	public void setGradePay(Float gradePay) {
		this.gradePay = gradePay;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserSfid() {
		return userSfid;
	}

	public void setUserSfid(String userSfid) {
		this.userSfid = userSfid;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public Float getIncrement3() {
		return increment3;
	}

	public void setIncrement3(Float increment3) {
		this.increment3 = increment3;
	}

	public Float getIncRoundOff() {
		return incRoundOff;
	}

	public void setIncRoundOff(Float incRoundOff) {
		this.incRoundOff = incRoundOff;
	}

	public Float getIncrementBasicPay() {
		return incrementBasicPay;
	}

	public void setIncrementBasicPay(Float incrementBasicPay) {
		this.incrementBasicPay = incrementBasicPay;
	}

	public Float getIncrementGradePay() {
		return incrementGradePay;
	}

	public void setIncrementGradePay(Float incrementGradePay) {
		this.incrementGradePay = incrementGradePay;
	}

	public int getDoPartNo() {
		return doPartNo;
	}

	public void setDoPartNo(int doPartNo) {
		this.doPartNo = doPartNo;
	}

	public int getCasualityId() {
		return casualityId;
	}

	public void setCasualityId(int casualityId) {
		this.casualityId = casualityId;
	}

	public int getGazettedType() {
		return gazettedType;
	}

	public void setGazettedType(int gazettedType) {
		this.gazettedType = gazettedType;
	}

	public void setIncrementYearId(int incrementYearId) {
		this.incrementYearId = incrementYearId;
	}

	public int getIncrementYearId() {
		return incrementYearId;
	}

	public void setFinanceAcceptedDate(Date financeAcceptedDate) {
		this.financeAcceptedDate = financeAcceptedDate;
	}

	public Date getFinanceAcceptedDate() {
		return financeAcceptedDate;
	}

	public void setAdminEffectDate(Date adminEffectDate) {
		this.adminEffectDate = adminEffectDate;
	}

	public Date getAdminEffectDate() {
		return adminEffectDate;
	}

	public String getBasicPayId() {
		return basicPayId;
	}

	public void setBasicPayId(String basicPayId) {
		this.basicPayId = basicPayId;
	}

	public int getDesignationId() {
		return designationId;
	}

	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}

	public String getBasicGradeTotal() {
		return basicGradeTotal;
	}

	public void setBasicGradeTotal(String basicGradeTotal) {
		this.basicGradeTotal = basicGradeTotal;
	}


}
