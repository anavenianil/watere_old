package com.callippus.web.beans.incometax;

import java.util.Date;
import java.util.List;

import com.callippus.web.beans.configuration.ConfigurationBean;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.KeyValueDTO;



import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.dto.PayChallanDetailsDTO;

import com.callippus.web.incometax.dto.DBComparisonDTO;
import com.callippus.web.incometax.dto.IncomeTaxArrearsDTO;
import com.callippus.web.incometax.dto.IncomeTaxConfigDTO;
import com.callippus.web.incometax.dto.IncomeTaxDAStatusDTO;
import com.callippus.web.incometax.dto.IncomeTaxPayBillDTO;
import com.callippus.web.incometax.dto.IncomeTaxRentDTO;
import com.callippus.web.incometax.dto.IncomeTaxRunDTO;
import com.callippus.web.incometax.dto.IncomeTaxSavingsDTO;
import com.callippus.web.incometax.dto.IncomeTaxSectionDTO;
import com.callippus.web.incometax.dto.IncomeTaxSectionMappingDTO;
import com.callippus.web.incometax.dto.IncomeTaxStageDTO;
import com.callippus.web.incometax.dto.PayFinYearDTO;
import com.callippus.web.incometax.dto.PrUpdateAllwDTO;
import com.callippus.web.paybill.dto.PayBillDTO;
import com.callippus.web.paybill.dto.PayBillEmpCategoryDTO;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;
import com.callippus.web.beans.dto.CategoryDTO;
import net.sf.json.JSONArray;

public class IncomeTaxMasterBean {

	private String pk;
	private String remarks;
	private String param;
	private String message;
	
	private String result;
	private String type;
	private String sfID;
	private String nodeID;
	private String amount;
	private String FYearFrom;
	private String FYearTo;
	private String selectedFYear;
	private String taxableflag;
	private String stages;
	private List<PayFinYearDTO> finYearList;
	private List<IncomeTaxStageDTO> incomeTaxStageList;
	private Date fromDate;
	private Date toDate;















	private int oldDA;
	private int newDA;
	private List<PayBillEmpCategoryDTO> empCategroyMasterList;
	private String categoryId;
	private int daFlag;
	private String sName;
	private String sType;
	private List<IncomeTaxSavingsDTO> savingsList;
	private boolean submissionFlag;
	private String projection;
	private String actual;
	private String savingsTypeId;
	private String configList;
	private String searchSfid;
	private List<IncomeTaxConfigDTO> configDetailsList;
	private String designationName;
	private String division;
	private String nameInServiceBook;
	private String financialYear;
	private String name;
	private String daAmount;
	private String cpsRec;
	private String ITRec;
	private String runTypeFlag;
	private String selectSfid;
	private String runAllOrInd;
	private List<IncomeTaxArrearsDTO> arrearsList;
	private List<IncomeTaxDAStatusDTO> arrearsStatusList;
	private List<IncomeTaxSectionDTO> sectionList;
	private List<PrUpdateAllwDTO> puaList;
	private String secName;
	private String gender;
	private String disability;
	private String limit;
	private String sectionId;
	
		
	private String ittransportAllowance;
	private String itphyHandicapped;
	private String ageLimitForSrCitizen;
	private String configurationDetails;
	
	private Date rentFromMonth;
	private Date rentToMonth;
	
	private String rent;
	private String rentRemarks;
	private List<IncomeTaxRentDTO> rentList;
	
	private ConfigurationBean confBean;
	
	private String gradePay;
	private String designationId;
	private List<PayScaleDesignationDTO> designationList;
	
	private List<DBComparisonDTO> compList;
	
	private String payMonth;
	private String basic;
	private String sPay;
	private String fPay;
	private String da;
	private String hra;
	private String ccs;
	private String tpt;
	private String cMisc;
	private String arrears;
	private String gpf;
	private String cgeis;
	private String pTax;
	private String iTax;
	private String hba;
	private String pli;
	private String otBill;
	private String lic;
	private String eolHpl;
	private String hdfc;
	private String gic;
	private String canfin;
	private String cghs;
	private String dMisc;
	private List<KeyValueDTO> payMonthList;
	private String srCitizen;
	private List<DesignationDTO> empDesignationList;
	private String matched;
	private List<IncomeTaxSectionMappingDTO> sectionMappingList;
	private int runId;
	private List<PayBillDTO> payBillList;
	private List<IncomeTaxPayBillDTO> payItPayBillList;
	private IncomeTaxRunDTO incomeTaxRunDTO;
	private int totalCghs;
	private int totalProfTax;
	private List <EmployeeBean> editEmpList;
	private String isEditEmp;
	//for edit emp details
	private int varInc;
	private int TwoAddInc;
	private int TotalCredits;
	private int HdGicCfin;
	private int ITaxRecovery;
	private String lastModifiedBy; 
	private Date lastModifiedDate;
	private String payRemarks;
	private int cess;
	private int secondaryCess;
	private String reportTypeFlag;
	

	private String challanDate;//These Three properties for ChallanAmountDetails(P)1712
	private String challanNo;
	private Integer challanAmount;
	private String bankBSRNo;
	
	private List<PayChallanDetailsDTO> payChallanDetailsList;

	private int sectionOrderNo;
	private String validFrom;
	private String validTo;
	private String age;
	private int rebate;
	private int surcharge;
	private List<CategoryDTO> categoryList;
	private List<KeyValueDTO> designations;
	private String compareFromMonth;
	private String compareToMonth;
	//nagendra.v
	private int percentage;
	private int savingId;
	private String challanRemarks;
	
	
	public String getChallanRemarks() {
		return challanRemarks;
	}

	public void setChallanRemarks(String challanRemarks) {
		this.challanRemarks = challanRemarks;
	}

	public int getSavingId() {
		return savingId;
	}

	public void setSavingId(int savingId) {
		this.savingId = savingId;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public List<PayChallanDetailsDTO> getPayChallanDetailsList() {
		return payChallanDetailsList;
	}

	public void setPayChallanDetailsList(
			List<PayChallanDetailsDTO> payChallanDetailsList) {
		this.payChallanDetailsList = payChallanDetailsList;
	}

	public Integer getChallanAmount() {
		return challanAmount;
	}

	public void setChallanAmount(Integer challanAmount) {
		this.challanAmount = challanAmount;
	}

	public String getChallanDate() {
		return challanDate;
	}

	public void setChallanDate(String challanDate) {
		this.challanDate = challanDate;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public String getBankBSRNo() {
		return bankBSRNo;
	}

	public void setBankBSRNo(String bankBSRNo) {
		this.bankBSRNo = bankBSRNo;
	}

	
	
	 
	
	

	//OT
	private String singleHour;
	private String doubleHour;
	private String singleRate;
	private String doubleRate;
	private String singleAmount;
	private String doubleAmount;
	private String totalOTAmount;
	private List<OverTimeVO> overTimeList;
	private OverTimeVO overTime;
	private String otDetailsList;
	private Integer year;
	private List<KeyValueDTO> categories;
	
	
	public List<KeyValueDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<KeyValueDTO> categories) {
		this.categories = categories;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getOtDetailsList() {
		return otDetailsList;
	}

	public void setOtDetailsList(String otDetailsList) {
		this.otDetailsList = otDetailsList;
	}

	public OverTimeVO getOverTime() {
		return overTime;
	}

	public void setOverTime(OverTimeVO overTime) {
		this.overTime = overTime;
	}

	public List<OverTimeVO> getOverTimeList() {
		return overTimeList;
	}

	public void setOverTimeList(List<OverTimeVO> overTimeList) {
		this.overTimeList = overTimeList;
	}

	public String getSingleHour() {
		return singleHour;
	}

	
	public String getSingleRate() {
		return singleRate;
	}

	public void setSingleRate(String singleRate) {
		this.singleRate = singleRate;
	}

	public String getDoubleRate() {
		return doubleRate;
	}

	public void setDoubleRate(String doubleRate) {
		this.doubleRate = doubleRate;
	}

	public void setSingleHour(String singleHour) {
		this.singleHour = singleHour;
	}

	public String getDoubleHour() {
		return doubleHour;
	}

	public void setDoubleHour(String doubleHour) {
		this.doubleHour = doubleHour;
	}

	public String getSingleAmount() {
		return singleAmount;
	}

	public void setSingleAmount(String singleAmount) {
		this.singleAmount = singleAmount;
	}

	public String getDoubleAmount() {
		return doubleAmount;
	}

	public void setDoubleAmount(String doubleAmount) {
		this.doubleAmount = doubleAmount;
	}

	public String getTotalOTAmount() {
		return totalOTAmount;
	}

	public void setTotalOTAmount(String totalOTAmount) {
		this.totalOTAmount = totalOTAmount;
	}

	public List<KeyValueDTO> getDesignations() {
		return designations;
	}

	public void setDesignations(List<KeyValueDTO> designations) {
		this.designations = designations;
	}

	public List<CategoryDTO> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryDTO> categoryList) {
		this.categoryList = categoryList;
	}

	public int getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(int surcharge) {
		this.surcharge = surcharge;
	}

	public int getRebate() {
		return rebate;
	}

	public void setRebate(int rebate) {
		this.rebate = rebate;
	}


	//for display total calculated values in page footer//kumari
	private int totSal;
	private int arrearsPaid;
	private int totIncome;
	private int totHraEolHpl;
	private int grandTot;
	private int totGpf;
	private int finalTot;
	private int hraExempted;
	private int traAllw;
	private int prUpallw;
	private int payAlwAfterExemption;
	private int totPTax;
	private int totDeductions;
	private int payAllowAfterDeductions;
	private int incomeFromOther;
	private int totalIncomeWithOther;
	private int totSavings;
	private int taxableIncome;
	private int roundedOff;
	private int totIncomeTax;
	private int educationCess;
	private int totIncomeTaxRecovered;
	private int taxPerMonth;
	private int savingstotal;
	
	public int getSavingstotal() {
		return savingstotal;
	}

	public void setSavingstotal(int savingstotal) {
		this.savingstotal = savingstotal;
	}

	public int getTotIncomeTaxRecovered() {
		return totIncomeTaxRecovered;
	}

	public void setTotIncomeTaxRecovered(int totIncomeTaxRecovered) {
		this.totIncomeTaxRecovered = totIncomeTaxRecovered;
	}

	public int getTotSavings() {
		return totSavings;
	}

	public void setTotSavings(int totSavings) {
		this.totSavings = totSavings;
	}

	public int getRoundedOff() {
		return roundedOff;
	}

	public void setRoundedOff(int roundedOff) {
		this.roundedOff = roundedOff;
	}

	public int getTotIncomeTax() {
		return totIncomeTax;
	}

	public void setTotIncomeTax(int totIncomeTax) {
		this.totIncomeTax = totIncomeTax;
	}

	public int getTaxPerMonth() {
		return taxPerMonth;
	}

	public void setTaxPerMonth(int taxPerMonth) {
		this.taxPerMonth = taxPerMonth;
	}

	

	public int getTaxableIncome() {
		return taxableIncome;
	}

	public void setTaxableIncome(int taxableIncome) {
		this.taxableIncome = taxableIncome;
	}

	public int getEducationCess() {
		return educationCess;
	}

	public void setEducationCess(int educationCess) {
		this.educationCess = educationCess;
	}

	
	public int getIncomeFromOther() {
		return incomeFromOther;
	}

	public void setIncomeFromOther(int incomeFromOther) {
		this.incomeFromOther = incomeFromOther;
	}

	public int getTotalIncomeWithOther() {
		return totalIncomeWithOther;
	}

	public void setTotalIncomeWithOther(int totalIncomeWithOther) {
		this.totalIncomeWithOther = totalIncomeWithOther;
	}

	public int getTotPTax() {
		return totPTax;
	}

	public void setTotPTax(int totPTax) {
		this.totPTax = totPTax;
	}

	public int getTotDeductions() {
		return totDeductions;
	}

	public void setTotDeductions(int totDeductions) {
		this.totDeductions = totDeductions;
	}

	public int getPayAllowAfterDeductions() {
		return payAllowAfterDeductions;
	}

	public void setPayAllowAfterDeductions(int payAllowAfterDeductions) {
		this.payAllowAfterDeductions = payAllowAfterDeductions;
	}

	public int getHraExempted() {
		return hraExempted;
	}

	public void setHraExempted(int hraExempted) {
		this.hraExempted = hraExempted;
	}

	public int getTraAllw() {
		return traAllw;
	}

	public void setTraAllw(int traAllw) {
		this.traAllw = traAllw;
	}

	public int getPrUpallw() {
		return prUpallw;
	}

	public void setPrUpallw(int prUpallw) {
		this.prUpallw = prUpallw;
	}

	public int getPayAlwAfterExemption() {
		return payAlwAfterExemption;
	}

	public void setPayAlwAfterExemption(int payAlwAfterExemption) {
		this.payAlwAfterExemption = payAlwAfterExemption;
	}

	public int getTotSal() {
		return totSal;
	}

	public void setTotSal(int totSal) {
		this.totSal = totSal;
	}

	public int getArrearsPaid() {
		return arrearsPaid;
	}

	public void setArrearsPaid(int arrearsPaid) {
		this.arrearsPaid = arrearsPaid;
	}

	public int getTotIncome() {
		return totIncome;
	}

	public void setTotIncome(int totIncome) {
		this.totIncome = totIncome;
	}

	public int getTotHraEolHpl() {
		return totHraEolHpl;
	}

	public void setTotHraEolHpl(int totHraEolHpl) {
		this.totHraEolHpl = totHraEolHpl;
	}

	public int getGrandTot() {
		return grandTot;
	}

	public void setGrandTot(int grandTot) {
		this.grandTot = grandTot;
	}

	public int getTotGpf() {
		return totGpf;
	}

	public void setTotGpf(int totGpf) {
		this.totGpf = totGpf;
	}

	public int getFinalTot() {
		return finalTot;
	}

	public void setFinalTot(int finalTot) {
		this.finalTot = finalTot;
	}

	public String getPayRemarks() {
		return payRemarks;
	}

	public void setPayRemarks(String payRemarks) {
		this.payRemarks = payRemarks;
	}


	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public int getSectionOrderNo() {
		return sectionOrderNo;
	}

	public void setSectionOrderNo(int sectionOrderNo) {
		this.sectionOrderNo = sectionOrderNo;
	}

	public String getItphyHandicapped() {
		return itphyHandicapped;
	}

	public void setItphyHandicapped(String itphyHandicapped) {
		this.itphyHandicapped = itphyHandicapped;
	}


	public List<IncomeTaxPayBillDTO> getPayItPayBillList() {
		return payItPayBillList;
	}

	public void setPayItPayBillList(List<IncomeTaxPayBillDTO> payItPayBillList) {
		this.payItPayBillList = payItPayBillList;
	}

	public int getTotalCghs() {
		return totalCghs;
	}

	public void setTotalCghs(int totalCghs) {
		this.totalCghs = totalCghs;
	}

	public int getTotalProfTax() {
		return totalProfTax;
	}

	public void setTotalProfTax(int totalProfTax) {
		this.totalProfTax = totalProfTax;
	}

	public IncomeTaxRunDTO getIncomeTaxRunDTO() {
		return incomeTaxRunDTO;
	}

	public void setIncomeTaxRunDTO(IncomeTaxRunDTO incomeTaxRunDTO) {
		this.incomeTaxRunDTO = incomeTaxRunDTO;
	}

	public List<PayBillDTO> getPayBillList() {
		return payBillList;
	}

	public void setPayBillList(List<PayBillDTO> payBillList) {
		this.payBillList = payBillList;
	}

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public List<IncomeTaxSectionMappingDTO> getSectionMappingList() {
		return sectionMappingList;
	}

	public void setSectionMappingList(List<IncomeTaxSectionMappingDTO> sectionMappingList) {
		this.sectionMappingList = sectionMappingList;
	}

	public String getMatched() {
		return matched;
	}

	public void setMatched(String matched) {
		this.matched = matched;
	}

	public List<DesignationDTO> getEmpDesignationList() {
		return empDesignationList;
	}

	public void setEmpDesignationList(List<DesignationDTO> empDesignationList) {
		this.empDesignationList = empDesignationList;
	}

	public String getSrCitizen() {
		return srCitizen;
	}

	public void setSrCitizen(String srCitizen) {
		this.srCitizen = srCitizen;
	}

	public List<KeyValueDTO> getPayMonthList() {
		return payMonthList;
	}

	public void setPayMonthList(List<KeyValueDTO> payMonthList) {
		this.payMonthList = payMonthList;
	}

	public String getBasic() {
		return basic;
	}

	public void setBasic(String basic) {
		this.basic = basic;
	}

	

	public String getsPay() {
		return sPay;
	}

	public void setsPay(String sPay) {
		this.sPay = sPay;
	}

	public String getfPay() {
		return fPay;
	}

	public void setfPay(String fPay) {
		this.fPay = fPay;
	}

	public String getDa() {
		return da;
	}

	public void setDa(String da) {
		this.da = da;
	}

	public String getHra() {
		return hra;
	}

	public void setHra(String hra) {
		this.hra = hra;
	}

	public String getCcs() {
		return ccs;
	}

	public void setCcs(String ccs) {
		this.ccs = ccs;
	}

	public String getTpt() {
		return tpt;
	}

	public void setTpt(String tpt) {
		this.tpt = tpt;
	}

	public String getcMisc() {
		return cMisc;
	}

	public void setcMisc(String cMisc) {
		this.cMisc = cMisc;
	}

	public String getArrears() {
		return arrears;
	}

	public void setArrears(String arrears) {
		this.arrears = arrears;
	}

	public String getGpf() {
		return gpf;
	}

	public void setGpf(String gpf) {
		this.gpf = gpf;
	}

	public String getCgeis() {
		return cgeis;
	}

	public void setCgeis(String cgeis) {
		this.cgeis = cgeis;
	}

	public String getpTax() {
		return pTax;
	}

	public void setpTax(String pTax) {
		this.pTax = pTax;
	}

	public String getiTax() {
		return iTax;
	}

	public void setiTax(String iTax) {
		this.iTax = iTax;
	}

	public String getHba() {
		return hba;
	}

	public void setHba(String hba) {
		this.hba = hba;
	}

	public String getPli() {
		return pli;
	}

	public void setPli(String pli) {
		this.pli = pli;
	}

	public String getOtBill() {
		return otBill;
	}

	public void setOtBill(String otBill) {
		this.otBill = otBill;
	}

	public String getLic() {
		return lic;
	}

	public void setLic(String lic) {
		this.lic = lic;
	}

	public String getEolHpl() {
		return eolHpl;
	}

	public void setEolHpl(String eolHpl) {
		this.eolHpl = eolHpl;
	}

	public String getHdfc() {
		return hdfc;
	}

	public void setHdfc(String hdfc) {
		this.hdfc = hdfc;
	}

	public String getGic() {
		return gic;
	}

	public void setGic(String gic) {
		this.gic = gic;
	}

	public String getCanfin() {
		return canfin;
	}

	public void setCanfin(String canfin) {
		this.canfin = canfin;
	}

	public String getCghs() {
		return cghs;
	}

	public void setCghs(String cghs) {
		this.cghs = cghs;
	}

	public String getdMisc() {
		return dMisc;
	}

	public void setdMisc(String dMisc) {
		this.dMisc = dMisc;
	}

	public String getPayMonth() {
		return payMonth;
	}

	public void setPayMonth(String payMonth) {
		this.payMonth = payMonth;
	}

	public List<DBComparisonDTO> getCompList() {
		return compList;
	}

	public void setCompList(List<DBComparisonDTO> compList) {
		this.compList = compList;
	}

	public List<PrUpdateAllwDTO> getPuaList() {
		return puaList;
	}

	public void setPuaList(List<PrUpdateAllwDTO> puaList) {
		this.puaList = puaList;
	}

	public List<PayScaleDesignationDTO> getDesignationList() {
		return designationList;
	}

	public void setDesignationList(List<PayScaleDesignationDTO> designationList) {
		this.designationList = designationList;
	}

	public String getDesignationId() {
		return designationId;
	}

	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}

	public String getGradePay() {
		return gradePay;
	}

	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}

	public ConfigurationBean getConfBean() {
		return confBean;
	}

	public void setConfBean(ConfigurationBean confBean) {
		this.confBean = confBean;
	}

	public List<IncomeTaxRentDTO> getRentList() {
		return rentList;
	}

	public void setRentList(List<IncomeTaxRentDTO> rentList) {
		this.rentList = rentList;
	}

	public String getRentRemarks() {
		return rentRemarks;
	}

	public void setRentRemarks(String rentRemarks) {
		this.rentRemarks = rentRemarks;
	}

	

	public Date getRentFromMonth() {
		return rentFromMonth;
	}

	public void setRentFromMonth(Date rentFromMonth) {
		this.rentFromMonth = rentFromMonth;
	}

	public Date getRentToMonth() {
		return rentToMonth;
	}

	public void setRentToMonth(Date rentToMonth) {
		this.rentToMonth = rentToMonth;
	}

	public String getRent() {
		return rent;
	}

	public void setRent(String rent) {
		this.rent = rent;
	}

	public String getConfigurationDetails() {
		return configurationDetails;
	}

	public void setConfigurationDetails(String configurationDetails) {
		this.configurationDetails = configurationDetails;
	}

	public String getIttransportAllowance() {
		return ittransportAllowance;
	}

	public void setIttransportAllowance(String ittransportAllowance) {
		this.ittransportAllowance = ittransportAllowance;
	}

	public String getAgeLimitForSrCitizen() {
		return ageLimitForSrCitizen;
	}

	public void setAgeLimitForSrCitizen(String ageLimitForSrCitizen) {
		this.ageLimitForSrCitizen = ageLimitForSrCitizen;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public List<IncomeTaxSectionDTO> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<IncomeTaxSectionDTO> sectionList) {
		this.sectionList = sectionList;
	}

	public String getSecName() {
		return secName;
	}

	public void setSecName(String secName) {
		this.secName = secName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDisability() {
		return disability;
	}

	public void setDisability(String disability) {
		this.disability = disability;
	}

	public List<IncomeTaxDAStatusDTO> getArrearsStatusList() {
		return arrearsStatusList;
	}

	public void setArrearsStatusList(List<IncomeTaxDAStatusDTO> arrearsStatusList) {
		this.arrearsStatusList = arrearsStatusList;
	}

	public String getRunAllOrInd() {
		return runAllOrInd;
	}

	public void setRunAllOrInd(String runAllOrInd) {
		this.runAllOrInd = runAllOrInd;
	}

	public String getSelectSfid() {
		return selectSfid;
	}

	public void setSelectSfid(String selectSfid) {
		this.selectSfid = selectSfid;
	}

	public String getRunTypeFlag() {
		return runTypeFlag;
	}

	public void setRunTypeFlag(String runTypeFlag) {
		this.runTypeFlag = runTypeFlag;
	}

	public List<IncomeTaxArrearsDTO> getArrearsList() {
		return arrearsList;
	}

	public void setArrearsList(List<IncomeTaxArrearsDTO> arrearsList) {
		this.arrearsList = arrearsList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDaAmount() {
		return daAmount;
	}

	public void setDaAmount(String daAmount) {
		this.daAmount = daAmount;
	}

	public String getCpsRec() {
		return cpsRec;
	}

	public void setCpsRec(String cpsRec) {
		this.cpsRec = cpsRec;
	}

	public String getITRec() {
		return ITRec;
	}

	public void setITRec(String iTRec) {
		ITRec = iTRec;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getNameInServiceBook() {
		return nameInServiceBook;
	}

	public void setNameInServiceBook(String nameInServiceBook) {
		this.nameInServiceBook = nameInServiceBook;
	}

	public List<IncomeTaxConfigDTO> getConfigDetailsList() {
		return configDetailsList;
	}

	public void setConfigDetailsList(List<IncomeTaxConfigDTO> configDetailsList) {
		this.configDetailsList = configDetailsList;
	}

	public String getSearchSfid() {
		return searchSfid;
	}

	public void setSearchSfid(String searchSfid) {
		this.searchSfid = searchSfid;
	}

	public String getConfigList() {
		return configList;
	}

	public void setConfigList(String configList) {
		this.configList = configList;
	}

	public String getSavingsTypeId() {
		return savingsTypeId;
	}

	public void setSavingsTypeId(String savingsTypeId) {
		this.savingsTypeId = savingsTypeId;
	}

	public String getProjection() {
		return projection;
	}

	public void setProjection(String projection) {
		this.projection = projection;
	}



	public boolean isSubmissionFlag() {
		return submissionFlag;
	}

	public void setSubmissionFlag(boolean submissionFlag) {
		this.submissionFlag = submissionFlag;
	}

	public List<IncomeTaxSavingsDTO> getSavingsList() {
		return savingsList;
	}

	public void setSavingsList(List<IncomeTaxSavingsDTO> savingsList) {
		this.savingsList = savingsList;
	}

	public String getsType() {
		return sType;
	}

	public void setsType(String sType) {
		this.sType = sType;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public int getDaFlag() {
		return daFlag;
	}

	public void setDaFlag(int daFlag) {
		this.daFlag = daFlag;
	}

	

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public List<PayBillEmpCategoryDTO> getEmpCategroyMasterList() {
		return empCategroyMasterList;
	}

	public void setEmpCategroyMasterList(List<PayBillEmpCategoryDTO> empCategroyMasterList) {
		this.empCategroyMasterList = empCategroyMasterList;
	}

	public int getOldDA() {
		return oldDA;
	}

	public void setOldDA(int oldDA) {
		this.oldDA = oldDA;
	}

	public int getNewDA() {
		return newDA;
	}

	public void setNewDA(int newDA) {
		this.newDA = newDA;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<IncomeTaxStageDTO> getIncomeTaxStageList() {
		return incomeTaxStageList;
	}

	public void setIncomeTaxStageList(List<IncomeTaxStageDTO> incomeTaxStageList) {
		this.incomeTaxStageList = incomeTaxStageList;
	}

	public String getStages() {
		return stages;
	}

	public void setStages(String stages) {
		this.stages = stages;
	}

	public String getTaxableflag() {
		return taxableflag;
	}

	public void setTaxableflag(String taxableflag) {
		this.taxableflag = taxableflag;
	}

	public String getSelectedFYear() {
		return selectedFYear;
	}

	public void setSelectedFYear(String selectedFYear) {
		this.selectedFYear = selectedFYear;
	}

	public List<PayFinYearDTO> getFinYearList() {
		return finYearList;
	}

	public void setFinYearList(List<PayFinYearDTO> finYearList) {
		this.finYearList = finYearList;
	}

	public String getFYearFrom() {
		return FYearFrom;
	}

	public void setFYearFrom(String fYearFrom) {
		FYearFrom = fYearFrom;
	}

	public String getFYearTo() {
		return FYearTo;
	}

	public void setFYearTo(String fYearTo) {
		FYearTo = fYearTo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public List<EmployeeBean> getEditEmpList() {
		return editEmpList;
	}

	public void setEditEmpList(List<EmployeeBean> editEmpList) {
		this.editEmpList = editEmpList;
	}

	public String getIsEditEmp() {
		return isEditEmp;
	}

	public void setIsEditEmp(String isEditEmp) {
		this.isEditEmp = isEditEmp;
	}

	public int getVarInc() {
		return varInc;
	}

	public void setVarInc(int varInc) {
		this.varInc = varInc;
	}

	public int getTwoAddInc() {
		return TwoAddInc;
	}

	public void setTwoAddInc(int twoAddInc) {
		TwoAddInc = twoAddInc;
	}

	public int getTotalCredits() {
		return TotalCredits;
	}

	public void setTotalCredits(int totalCredits) {
		TotalCredits = totalCredits;
	}

	public int getHdGicCfin() {
		return HdGicCfin;
	}

	public void setHdGicCfin(int hdGicCfin) {
		HdGicCfin = hdGicCfin;
	}

	public int getITaxRecovery() {
		return ITaxRecovery;
	}

	public void setITaxRecovery(int iTaxRecovery) {
		ITaxRecovery = iTaxRecovery;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
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

	public String getReportTypeFlag() {
		return reportTypeFlag;
	}

	public void setReportTypeFlag(String reportTypeFlag) {
		this.reportTypeFlag = reportTypeFlag;
	}

	public String getCompareFromMonth() {
		return compareFromMonth;
	}

	public void setCompareFromMonth(String compareFromMonth) {
		this.compareFromMonth = compareFromMonth;
	}

	public String getCompareToMonth() {
		return compareToMonth;
	}

	public void setCompareToMonth(String compareToMonth) {
		this.compareToMonth = compareToMonth;
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}


	
}
