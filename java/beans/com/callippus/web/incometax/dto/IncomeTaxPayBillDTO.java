package com.callippus.web.incometax.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class IncomeTaxPayBillDTO{
	private int id;
	private String sfid;
	private Date month;
	private int basicPay;
	private int gradePay;
	private int specialPay;
	private int da;
	private int hra;
	private int tpt;
	private int twoAddlIncr;
	private int variableIncr;
	private int ETA;
	private int pli;
	private int totalCredits;
	private int gpf;
	private int cegis;
	private int cghs;
	private int profTax;
	private int hbaLoan;
	private int hdGicCfin;
	private int lic;
	private int eol;
	private int credMisc;
	private int debMisc;
	private int incomeTaxRec;
	private String runType;
	private int runId;
	
	private int fpay;
	private int ccs;
	private int hdfc;
	private int gic;
	private int canfin;
	private int iTax;
	private Date creationDate;
	private Date modifiedDate;
	private String modifiedBy;
	private String remarks;
	private int cess;
	private int secondaryCess;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public Date getMonth() {
		return month;
	}
	public void setMonth(Date month) {
		this.month = month;
	}
	public int getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(int basicPay) {
		this.basicPay = basicPay;
	}
	public int getGradePay() {
		return gradePay;
	}
	public void setGradePay(int gradePay) {
		this.gradePay = gradePay;
	}
	public int getSpecialPay() {
		return specialPay;
	}
	public void setSpecialPay(int specialPay) {
		this.specialPay = specialPay;
	}
	public int getDa() {
		return da;
	}
	public void setDa(int da) {
		this.da = da;
	}
	public int getHra() {
		return hra;
	}
	public void setHra(int hra) {
		this.hra = hra;
	}
	public int getTpt() {
		return tpt;
	}
	public void setTpt(int tpt) {
		this.tpt = tpt;
	}
	public int getTwoAddlIncr() {
		return twoAddlIncr;
	}
	public void setTwoAddlIncr(int twoAddlIncr) {
		this.twoAddlIncr = twoAddlIncr;
	}
	public int getVariableIncr() {
		return variableIncr;
	}
	public void setVariableIncr(int variableIncr) {
		this.variableIncr = variableIncr;
	}
	public int getETA() {
		return ETA;
	}
	public void setETA(int eTA) {
		ETA = eTA;
	}
	public int getPli() {
		return pli;
	}
	public void setPli(int pli) {
		this.pli = pli;
	}
	public int getTotalCredits() {
		return totalCredits;
	}
	public void setTotalCredits(int totalCredits) {
		this.totalCredits = totalCredits;
	}
	public int getGpf() {
		return gpf;
	}
	public void setGpf(int gpf) {
		this.gpf = gpf;
	}
	public int getCegis() {
		return cegis;
	}
	public void setCegis(int cegis) {
		this.cegis = cegis;
	}
	public int getCghs() {
		return cghs;
	}
	public void setCghs(int cghs) {
		this.cghs = cghs;
	}
	public int getProfTax() {
		return profTax;
	}
	public void setProfTax(int profTax) {
		this.profTax = profTax;
	}
	public int getHbaLoan() {
		return hbaLoan;
	}
	public void setHbaLoan(int hbaLoan) {
		this.hbaLoan = hbaLoan;
	}
	public int getHdGicCfin() {
		return hdGicCfin;
	}
	public void setHdGicCfin(int hdGicCfin) {
		this.hdGicCfin = hdGicCfin;
	}
	public int getLic() {
		return lic;
	}
	public void setLic(int lic) {
		this.lic = lic;
	}
	public int getEol() {
		return eol;
	}
	public void setEol(int eol) {
		this.eol = eol;
	}
	public int getCredMisc() {
		return credMisc;
	}
	public void setCredMisc(int credMisc) {
		this.credMisc = credMisc;
	}
	public int getDebMisc() {
		return debMisc;
	}
	public void setDebMisc(int debMisc) {
		this.debMisc = debMisc;
	}
	public int getIncomeTaxRec() {
		return incomeTaxRec;
	}
	public void setIncomeTaxRec(int incomeTaxRec) {
		this.incomeTaxRec = incomeTaxRec;
	}
	public String getRunType() {
		return runType;
	}
	public void setRunType(String runType) {
		this.runType = runType;
	}
	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public int getFpay() {
		return fpay;
	}
	public void setFpay(int fpay) {
		this.fpay = fpay;
	}
	public int getCcs() {
		return ccs;
	}
	public void setCcs(int ccs) {
		this.ccs = ccs;
	}
	public int getHdfc() {
		return hdfc;
	}
	public void setHdfc(int hdfc) {
		this.hdfc = hdfc;
	}
	public int getGic() {
		return gic;
	}
	public void setGic(int gic) {
		this.gic = gic;
	}
	public int getCanfin() {
		return canfin;
	}
	public void setCanfin(int canfin) {
		this.canfin = canfin;
	}
	public int getiTax() {
		return iTax;
	}
	public void setiTax(int iTax) {
		this.iTax = iTax;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getCess() {
		return cess;
	}
	public void setCess(int cess) {
		this.cess = cess;
	}
	public int getSecondaryCess() {
		return secondaryCess;
	}
	public void setSecondaryCess(int secondaryCess) {
		this.secondaryCess = secondaryCess;
	}
	
}