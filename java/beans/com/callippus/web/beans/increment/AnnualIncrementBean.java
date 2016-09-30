package com.callippus.web.beans.increment;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.dto.CasualityDetailsDTO;
import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.DoPartDTO;
import com.callippus.web.beans.dto.TypeDetailsDTO;

public class AnnualIncrementBean extends CommonDTO {
	private String param;
	private String sfID;
	private String result;
	private String Remarks;
	private String type;
	private String gazettedType;
	private List<TypeDetailsDTO> typeList;
	private String doPartNo;
	private List<DoPartDTO> doPartList;
	private String casualityId;
	private List<CasualityDetailsDTO> casualitiesList;
	private String sfIds;
	private List<AnnualIncrementDTO> empDetails;
	private Date financeAcceptedDate;
	private Date adminEffectDate;
	private String adminEffectDateString;
	private Date payBillRunMonth;
	private String doPartNumberDetails;
	private List<DoPartDTO> basicPayIdList;
	private List<AnnualIncrementDTO> updateAnnualDetails;
	private List<AnnualIncrementDTO> empNullValuesDetails;
	private List<AnnualIncrementDTO> empNotNullValuesDetails;
	private int designationId;
	private int roleId;
	
	public String getAdminEffectDateString() {
		return adminEffectDateString;
	}

	public void setAdminEffectDateString(String adminEffectDateString) {
		this.adminEffectDateString = adminEffectDateString;
	}

	public List<DoPartDTO> getDoPartList() {
		return doPartList;
	}

	public void setDoPartList(List<DoPartDTO> doPartList) {
		this.doPartList = doPartList;
	}

	public List<CasualityDetailsDTO> getCasualitiesList() {
		return casualitiesList;
	}

	public void setCasualitiesList(List<CasualityDetailsDTO> casualitiesList) {
		this.casualitiesList = casualitiesList;
	}
	
 
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public void setEmpDetails(List<AnnualIncrementDTO> empDetails) {
		this.empDetails = empDetails;
	}

	public List<AnnualIncrementDTO> getEmpDetails() {
		return empDetails;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setTypeList(List<TypeDetailsDTO> typeList) {
		this.typeList = typeList;
	}

	public List<TypeDetailsDTO> getTypeList() {
		return typeList;
	}

	public void setGazettedType(String gazettedType) {
		this.gazettedType = gazettedType;
	}

	public String getGazettedType() {
		return gazettedType;
	}

	public void setDoPartNo(String doPartNo) {
		this.doPartNo = doPartNo;
	}

	public String getDoPartNo() {
		return doPartNo;
	}

	public void setCasualityId(String casualityId) {
		this.casualityId = casualityId;
	}

	public String getCasualityId() {
		return casualityId;
	}

	public void setSfIds(String sfIds) {
		this.sfIds = sfIds;
	}

	public String getSfIds() {
		return sfIds;
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

	public void setPayBillRunMonth(Date payBillRunMonth) {
		this.payBillRunMonth = payBillRunMonth;
	}

	public Date getPayBillRunMonth() {
		return payBillRunMonth;
	}

	public String getDoPartNumberDetails() {
		return doPartNumberDetails;
	}

	public void setDoPartNumberDetails(String doPartNumberDetails) {
		this.doPartNumberDetails = doPartNumberDetails;
	}

	public List<DoPartDTO> getBasicPayIdList() {
		return basicPayIdList;
	}

	public void setBasicPayIdList(List<DoPartDTO> basicPayIdList) {
		this.basicPayIdList = basicPayIdList;
	}

	public List<AnnualIncrementDTO> getUpdateAnnualDetails() {
		return updateAnnualDetails;
	}

	public void setUpdateAnnualDetails(List<AnnualIncrementDTO> updateAnnualDetails) {
		this.updateAnnualDetails = updateAnnualDetails;
	}

	public List<AnnualIncrementDTO> getEmpNullValuesDetails() {
		return empNullValuesDetails;
	}

	public void setEmpNullValuesDetails(
			List<AnnualIncrementDTO> empNullValuesDetails) {
		this.empNullValuesDetails = empNullValuesDetails;
	}

	public List<AnnualIncrementDTO> getEmpNotNullValuesDetails() {
		return empNotNullValuesDetails;
	}

	public void setEmpNotNullValuesDetails(
			List<AnnualIncrementDTO> empNotNullValuesDetails) {
		this.empNotNullValuesDetails = empNotNullValuesDetails;
	}

	public int getDesignationId() {
		return designationId;
	}

	public void setDesignationId(int designationId) {
		this.designationId = designationId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
}
