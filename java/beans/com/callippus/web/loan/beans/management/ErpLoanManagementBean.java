package com.callippus.web.loan.beans.management;

import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.loan.beans.dto.ErpLoanPurposeDTO;

public class ErpLoanManagementBean extends CommonDTO {
	private String loanType;
	private String param;
	private String sfID;
	private String result;
	private String loanName;
	private List<ErpLoanPurposeDTO> erpLoanTypeMasterList;
	private String nodeID; 

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
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

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public List<ErpLoanPurposeDTO> getErpLoanTypeMasterList() {
		return erpLoanTypeMasterList;
	}

	public void setErpLoanTypeMasterList(
			List<ErpLoanPurposeDTO> erpLoanTypeMasterList) {
		this.erpLoanTypeMasterList = erpLoanTypeMasterList;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	
	
}