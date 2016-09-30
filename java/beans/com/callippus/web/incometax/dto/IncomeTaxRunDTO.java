package com.callippus.web.incometax.dto;


import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class IncomeTaxRunDTO extends CommonDTO{
   private int totSal;
   private String sfid;
   private int finYearId;
   private int totHra;
   private int totCghs;
   private int totCegis;
   private int totPTax;
   private int traAllw;
   private int arrearsPaid;
   private int lessHraEolHpl;
   private int govtSubs;
   private int hraExempted;
   private int prUpallw;
   private int taxableIncome;
   private long roundedOff;
   private int totIncomeTax;
   private int addEduCess;
   private int taxRecovered;
   private int taxToBeRecovered;
   private int taxPerMonth;
   private int da1;
   private int da2;
   private String runType;
   private int totExemptions;
   private int totOtherIncome;
   private int totSavings;
   private int totRentPaid;
   private Date effMonth;
   private int cpf;
   private int ITaxRec;
   private int runId;
   private int totPli;
   private int totalDeductions;
   private int payFixationArrears;
   private int fpaArrears;
   private int annIncrArrears;
   
   
   

public int getPayFixationArrears() {
	return payFixationArrears;
}
public void setPayFixationArrears(int payFixationArrears) {
	this.payFixationArrears = payFixationArrears;
}
public int getFpaArrears() {
	return fpaArrears;
}
public void setFpaArrears(int fpaArrears) {
	this.fpaArrears = fpaArrears;
}
public int getAnnIncrArrears() {
	return annIncrArrears;
}
public void setAnnIncrArrears(int annIncrArrears) {
	this.annIncrArrears = annIncrArrears;
}
public int getTotalDeductions() {
	return totalDeductions;
}
public void setTotalDeductions(int totalDeductions) {
	this.totalDeductions = totalDeductions;
}
public int getTotPli() {
	return totPli;
}
public void setTotPli(int totPli) {
	this.totPli = totPli;
}
public int getRunId() {
	return runId;
}
public void setRunId(int runId) {
	this.runId = runId;
}
public int getITaxRec() {
	return ITaxRec;
}
public void setITaxRec(int iTaxRec) {
	ITaxRec = iTaxRec;
}
public int getCpf() {
	return cpf;
}
public void setCpf(int cpf) {
	this.cpf = cpf;
}
public Date getEffMonth() {
	return effMonth;
}
public void setEffMonth(Date effMonth) {
	this.effMonth = effMonth;
}
public int getTotRentPaid() {
	return totRentPaid;
}
public void setTotRentPaid(int totRentPaid) {
	this.totRentPaid = totRentPaid;
}
public int getTotExemptions() {
	return totExemptions;
}
public void setTotExemptions(int totExemptions) {
	this.totExemptions = totExemptions;
}
public int getTotOtherIncome() {
	return totOtherIncome;
}
public void setTotOtherIncome(int totOtherIncome) {
	this.totOtherIncome = totOtherIncome;
}
public int getTotSavings() {
	return totSavings;
}
public void setTotSavings(int totSavings) {
	this.totSavings = totSavings;
}
public String getSfid() {
	return sfid;
}
public void setSfid(String sfid) {
	this.sfid = sfid;
}
public String getRunType() {
	return runType;
}
public void setRunType(String runType) {
	this.runType = runType;
}
public int getTotSal() {
	return totSal;
}
public void setTotSal(int totSal) {
	this.totSal = totSal;
}
public int getFinYearId() {
	return finYearId;
}
public void setFinYearId(int finYearId) {
	this.finYearId = finYearId;
}
public int getTotHra() {
	return totHra;
}
public void setTotHra(int totHra) {
	this.totHra = totHra;
}
public int getTotCghs() {
	return totCghs;
}
public void setTotCghs(int totCghs) {
	this.totCghs = totCghs;
}
public int getTotCegis() {
	return totCegis;
}
public void setTotCegis(int totCegis) {
	this.totCegis = totCegis;
}
public int getTotPTax() {
	return totPTax;
}
public void setTotPTax(int totPTax) {
	this.totPTax = totPTax;
}
public int getTraAllw() {
	return traAllw;
}
public void setTraAllw(int traAllw) {
	this.traAllw = traAllw;
}
public int getArrearsPaid() {
	return arrearsPaid;
}
public void setArrearsPaid(int arrearsPaid) {
	this.arrearsPaid = arrearsPaid;
}
public int getLessHraEolHpl() {
	return lessHraEolHpl;
}
public void setLessHraEolHpl(int lessHraEolHpl) {
	this.lessHraEolHpl = lessHraEolHpl;
}
public int getGovtSubs() {
	return govtSubs;
}
public void setGovtSubs(int govtSubs) {
	this.govtSubs = govtSubs;
}
public int getHraExempted() {
	return hraExempted;
}
public void setHraExempted(int hraExempted) {
	this.hraExempted = hraExempted;
}
public int getPrUpallw() {
	return prUpallw;
}
public void setPrUpallw(int prUpallw) {
	this.prUpallw = prUpallw;
}
public int getTaxableIncome() {
	return taxableIncome;
}
public void setTaxableIncome(int taxableIncome) {
	this.taxableIncome = taxableIncome;
}

public long getRoundedOff() {
	return roundedOff;
}
public void setRoundedOff(long roundedOff) {
	this.roundedOff = roundedOff;
}
public int getTotIncomeTax() {
	return totIncomeTax;
}
public void setTotIncomeTax(int totIncomeTax) {
	this.totIncomeTax = totIncomeTax;
}
public int getAddEduCess() {
	return addEduCess;
}
public void setAddEduCess(int addEduCess) {
	this.addEduCess = addEduCess;
}
public int getTaxRecovered() {
	return taxRecovered;
}
public void setTaxRecovered(int taxRecovered) {
	this.taxRecovered = taxRecovered;
}
public int getTaxToBeRecovered() {
	return taxToBeRecovered;
}
public void setTaxToBeRecovered(int taxToBeRecovered) {
	this.taxToBeRecovered = taxToBeRecovered;
}
public int getTaxPerMonth() {
	return taxPerMonth;
}
public void setTaxPerMonth(int taxPerMonth) {
	this.taxPerMonth = taxPerMonth;
}
public int getDa1() {
	return da1;
}
public void setDa1(int da1) {
	this.da1 = da1;
}
public int getDa2() {
	return da2;
}
public void setDa2(int da2) {
	this.da2 = da2;
}
   
  
}