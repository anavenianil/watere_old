package com.callippus.web.beans.paybill;

import java.util.Date;
import java.util.List;


import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.paybill.dto.PayBillDTO;
import com.callippus.web.paybill.dto.PayBillStatusDTO;

public class PayBillBean {

	public String searchSfid;
	public String nameInServiceBook;
	public String departmentName;
	public String designationName;
	public int basicPay;
	public int gradePay;
	public int tpt;
	public int da;
	public int hra;
	public int variableIncr;
	public int twoAddlIncr;
	public int specialPay;
	public int fpa;
	public int washAllowance;
	public int xeroxAllowance;
	public int crMisc;
	public int hindiIncentive;
	public int riskAllowance;
	public int deputAllowance;
	public String crRemarks;
	public int gpf;
	public int gpfSa;
	public int gpfRecovery;
	public int pli;
	public int rent;
	public int water;
	public int elec;
	public int furn;
	public int cghs;
	public int cegis;
	public int incomeTax;
	public int cess;
	public int secondaryCess;
	public int profTax;
	public int tada;
	public int ltc;
	public int medical;
	public int eol;
	public int carLoan;
	public int scooterLoan;
	public int pcLoan;
	public int festivalAdv;
	public int hbaLoan;
	public int cycleLoan;
	public int drMisc1;
	public int drMisc2;
	public int totalDebits;
	public int netPay;
	public int totalCredits;
	public String drRemarks;
	public int welC;
	public int welRefund;
	public int mess;
	public int resSecu;
	public int benvolentFund;
	public int regimentalFund;
	public int ccs;
	public int ccsrecovery;
	public int hdfc;
	public int canfin;
	public int lic;
	public int courtAttachment;
	public int gic;
	public int recMisc1;
	public int recMisc2;
	public int recMisc3;
	public int totalRecovery;
	public int finalPay;
	public String recRemarks;
	public String remarks;
	public String message;
	public String param;
	public String type;
	public String mainRemarks;
	public int totalRecovery1;
	public int finalPay1;
	public int netPay1;
	public int totalCredits1;
	public int totalDebits1;
	public int carInstNumb;
	public int carTotInst;
	public int scooterInstNumb;
	public int scooterTotInst;
	public int cycleInstNumb;
	public int cycleTotInst;
	public int hbaInstNumb;
	public int hbaTotInst;
	public int pcInstNumb;
	public int pcTotInst;
	public int festivInstNumb;
	public int festivTotInst;
	public int canfinInstNumb;
	public int canfinTotInst;
	public int licInstNumb;
	public int licTotInst;
	public int gicInstNumb;
	public int gicTotInst;
	public int hdfcInstNumb;
	public int hdfcTotInst;
	public int courtInstNumb;
	public int courtTotInst;
	public int runStatus;
	public int manualStatus;
	public int userStatus;
	public String runMonth;
	
	//nagendra.v
	public String description;	
	public String sfId;
	public List<PayBillDTO> negPayEmpList;
    public String member;
    public List<PayBillStatusDTO> paySlipMonthsList;
    public String paySlipMonth;
    public String paySlipYear;
    public static String checkAutoRunStarted;
    public List<String> paySlipMonthList;
    public List<String> paySlipYearList;
    public String count;
    public String generatedCount;
    public int welInst;
    public int welTotinst;
    public String payRunMonth;
    public String employeeName;    
    public String workingLocation;
    private String payStopRemarks;
    private PayBillBean payDetails;
   //nagendra.v
    private String autoRunStatus;
  
    
    public String getAutoRunStatus() {
		return autoRunStatus;
	}

	public void setAutoRunStatus(String autoRunStatus) {
		this.autoRunStatus = autoRunStatus;
	}

	public static String getCheckAutoRunStarted() {
		return checkAutoRunStarted;
	}

	public static void setCheckAutoRunStarted(String checkAutoRunStarted) {
		PayBillBean.checkAutoRunStarted = checkAutoRunStarted;
	}

	public PayBillBean getPayDetails() {
		return payDetails;
	}

	public void setPayDetails(PayBillBean payDetails) {
		this.payDetails = payDetails;
	}

	public String getPayStopRemarks() {
		return payStopRemarks;
	}

	public void setPayStopRemarks(String payStopRemarks) {
		this.payStopRemarks = payStopRemarks;
	}

	public String getWorkingLocation() {
		return workingLocation;
	}

	public void setWorkingLocation(String workingLocation) {
		this.workingLocation = workingLocation;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String createdBy;
	public Date creationTime;
	public String lastModifiedBy;
	public Date lastModifiedTime;
	//supplimentary bill
	private String fromDate;
    private List<KeyValueDTO> empSuppSelectedList;
	public String fromID;
	public String toID;
	public List<KeyValueDTO> sfidList;
	public String payRegCount;
	public String paySuppCount;
	public String status;
	private String categoryName;
    
	public String getPayRunMonth() {
		return payRunMonth;
	}

	public void setPayRunMonth(String payRunMonth) {
		this.payRunMonth = payRunMonth;
	}

	public int getWelInst() {
		return welInst;
	}

	public void setWelInst(int welInst) {
		this.welInst = welInst;
	}

	public int getWelTotinst() {
		return welTotinst;
	}

	public void setWelTotinst(int welTotinst) {
		this.welTotinst = welTotinst;
	}

	public int getCarInstNumb() {
		return carInstNumb;
	}

	public void setCarInstNumb(int carInstNumb) {
		this.carInstNumb = carInstNumb;
	}

	public int getCarTotInst() {
		return carTotInst;
	}

	public void setCarTotInst(int carTotInst) {
		this.carTotInst = carTotInst;
	}

	public int getScooterInstNumb() {
		return scooterInstNumb;
	}

	public void setScooterInstNumb(int scooterInstNumb) {
		this.scooterInstNumb = scooterInstNumb;
	}

	public int getScooterTotInst() {
		return scooterTotInst;
	}

	public void setScooterTotInst(int scooterTotInst) {
		this.scooterTotInst = scooterTotInst;
	}

	public int getCycleInstNumb() {
		return cycleInstNumb;
	}

	public void setCycleInstNumb(int cycleInstNumb) {
		this.cycleInstNumb = cycleInstNumb;
	}

	public int getCycleTotInst() {
		return cycleTotInst;
	}

	public void setCycleTotInst(int cycleTotInst) {
		this.cycleTotInst = cycleTotInst;
	}

	public int getHbaInstNumb() {
		return hbaInstNumb;
	}

	public void setHbaInstNumb(int hbaInstNumb) {
		this.hbaInstNumb = hbaInstNumb;
	}

	public int getHbaTotInst() {
		return hbaTotInst;
	}

	public void setHbaTotInst(int hbaTotInst) {
		this.hbaTotInst = hbaTotInst;
	}

	public int getPcInstNumb() {
		return pcInstNumb;
	}

	public void setPcInstNumb(int pcInstNumb) {
		this.pcInstNumb = pcInstNumb;
	}

	public int getPcTotInst() {
		return pcTotInst;
	}

	public void setPcTotInst(int pcTotInst) {
		this.pcTotInst = pcTotInst;
	}

	public int getFestivInstNumb() {
		return festivInstNumb;
	}

	public void setFestivInstNumb(int festivInstNumb) {
		this.festivInstNumb = festivInstNumb;
	}

	public int getFestivTotInst() {
		return festivTotInst;
	}

	public void setFestivTotInst(int festivTotInst) {
		this.festivTotInst = festivTotInst;
	}

	public int getCanfinInstNumb() {
		return canfinInstNumb;
	}

	public void setCanfinInstNumb(int canfinInstNumb) {
		this.canfinInstNumb = canfinInstNumb;
	}

	public int getCanfinTotInst() {
		return canfinTotInst;
	}

	public void setCanfinTotInst(int canfinTotInst) {
		this.canfinTotInst = canfinTotInst;
	}

	public int getLicInstNumb() {
		return licInstNumb;
	}

	public void setLicInstNumb(int licInstNumb) {
		this.licInstNumb = licInstNumb;
	}

	public int getLicTotInst() {
		return licTotInst;
	}

	public void setLicTotInst(int licTotInst) {
		this.licTotInst = licTotInst;
	}

	public int getGicInstNumb() {
		return gicInstNumb;
	}

	public void setGicInstNumb(int gicInstNumb) {
		this.gicInstNumb = gicInstNumb;
	}

	public int getGicTotInst() {
		return gicTotInst;
	}

	public void setGicTotInst(int gicTotInst) {
		this.gicTotInst = gicTotInst;
	}

	public int getHdfcInstNumb() {
		return hdfcInstNumb;
	}

	public void setHdfcInstNumb(int hdfcInstNumb) {
		this.hdfcInstNumb = hdfcInstNumb;
	}

	public int getHdfcTotInst() {
		return hdfcTotInst;
	}

	public void setHdfcTotInst(int hdfcTotInst) {
		this.hdfcTotInst = hdfcTotInst;
	}

	public int getCourtInstNumb() {
		return courtInstNumb;
	}

	public void setCourtInstNumb(int courtInstNumb) {
		this.courtInstNumb = courtInstNumb;
	}

	public int getCourtTotInst() {
		return courtTotInst;
	}

	public void setCourtTotInst(int courtTotInst) {
		this.courtTotInst = courtTotInst;
	}

	public String getGeneratedCount() {
		return generatedCount;
	}

	public void setGeneratedCount(String generatedCount) {
		this.generatedCount = generatedCount;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getPaySlipMonth() {
		return paySlipMonth;
	}

	public void setPaySlipMonth(String paySlipMonth) {
		this.paySlipMonth = paySlipMonth;
	}

	public String getPaySlipYear() {
		return paySlipYear;
	}

	public void setPaySlipYear(String paySlipYear) {
		this.paySlipYear = paySlipYear;
	}

	public List<String> getPaySlipMonthList() {
		return paySlipMonthList;
	}

	public void setPaySlipMonthList(List<String> paySlipMonthList) {
		this.paySlipMonthList = paySlipMonthList;
	}

	public List<String> getPaySlipYearList() {
		return paySlipYearList;
	}

	public void setPaySlipYearList(List<String> paySlipYearList) {
		this.paySlipYearList = paySlipYearList;
	}

	public List<PayBillStatusDTO> getPaySlipMonthsList() {
		return paySlipMonthsList;
	}

	public void setPaySlipMonthsList(List<PayBillStatusDTO> paySlipMonthsList) {
		this.paySlipMonthsList = paySlipMonthsList;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public List<PayBillDTO> getNegPayEmpList() {
		return negPayEmpList;
	}

	public void setNegPayEmpList(List<PayBillDTO> negPayEmpList) {
		this.negPayEmpList = negPayEmpList;
	}

	public String getSfId() {
		return sfId;
	}

	public void setSfId(String sfId) {
		this.sfId = sfId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(int runStatus) {
		this.runStatus = runStatus;
	}

	public int getManualStatus() {
		return manualStatus;
	}

	public void setManualStatus(int manualStatus) {
		this.manualStatus = manualStatus;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public String getRunMonth() {
		return runMonth;
	}

	public void setRunMonth(String runMonth) {
		this.runMonth = runMonth;
	}

	public int getTotalRecovery1() {
		return totalRecovery1;
	}

	public void setTotalRecovery1(int totalRecovery1) {
		this.totalRecovery1 = totalRecovery1;
	}

	public int getFinalPay1() {
		return finalPay1;
	}

	public void setFinalPay1(int finalPay1) {
		this.finalPay1 = finalPay1;
	}

	public int getNetPay1() {
		return netPay1;
	}

	public void setNetPay1(int netPay1) {
		this.netPay1 = netPay1;
	}

	public int getTotalCredits1() {
		return totalCredits1;
	}

	public void setTotalCredits1(int totalCredits1) {
		this.totalCredits1 = totalCredits1;
	}

	public int getTotalDebits1() {
		return totalDebits1;
	}

	public void setTotalDebits1(int totalDebits1) {
		this.totalDebits1 = totalDebits1;
	}

	public String getMainRemarks() {
		return mainRemarks;
	}

	public void setMainRemarks(String mainRemarks) {
		this.mainRemarks = mainRemarks;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSearchSfid() {
		return searchSfid;
	}

	public void setSearchSfid(String searchSfid) {
		this.searchSfid = searchSfid;
	}

	public String getNameInServiceBook() {
		return nameInServiceBook;
	}

	public void setNameInServiceBook(String nameInServiceBook) {
		this.nameInServiceBook = nameInServiceBook;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
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

	public int getTpt() {
		return tpt;
	}

	public void setTpt(int tpt) {
		this.tpt = tpt;
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

	public int getVariableIncr() {
		return variableIncr;
	}

	public void setVariableIncr(int variableIncr) {
		this.variableIncr = variableIncr;
	}

	public int getTwoAddlIncr() {
		return twoAddlIncr;
	}

	public void setTwoAddlIncr(int twoAddlIncr) {
		this.twoAddlIncr = twoAddlIncr;
	}

	public int getSpecialPay() {
		return specialPay;
	}

	public void setSpecialPay(int specialPay) {
		this.specialPay = specialPay;
	}

	public int getFpa() {
		return fpa;
	}

	public void setFpa(int fpa) {
		this.fpa = fpa;
	}

	public int getWashAllowance() {
		return washAllowance;
	}

	public void setWashAllowance(int washAllowance) {
		this.washAllowance = washAllowance;
	}

	public int getXeroxAllowance() {
		return xeroxAllowance;
	}

	public void setXeroxAllowance(int xeroxAllowance) {
		this.xeroxAllowance = xeroxAllowance;
	}

	public int getCrMisc() {
		return crMisc;
	}

	public void setCrMisc(int crMisc) {
		this.crMisc = crMisc;
	}

	public int getHindiIncentive() {
		return hindiIncentive;
	}

	public void setHindiIncentive(int hindiIncentive) {
		this.hindiIncentive = hindiIncentive;
	}

	public int getRiskAllowance() {
		return riskAllowance;
	}

	public void setRiskAllowance(int riskAllowance) {
		this.riskAllowance = riskAllowance;
	}

	public int getDeputAllowance() {
		return deputAllowance;
	}

	public void setDeputAllowance(int deputAllowance) {
		this.deputAllowance = deputAllowance;
	}

	public String getCrRemarks() {
		return crRemarks;
	}

	public void setCrRemarks(String crRemarks) {
		this.crRemarks = crRemarks;
	}

	public int getGpf() {
		return gpf;
	}

	public void setGpf(int gpf) {
		this.gpf = gpf;
	}

	public int getGpfSa() {
		return gpfSa;
	}

	public void setGpfSa(int gpfSa) {
		this.gpfSa = gpfSa;
	}

	public int getGpfRecovery() {
		return gpfRecovery;
	}

	public void setGpfRecovery(int gpfRecovery) {
		this.gpfRecovery = gpfRecovery;
	}

	public int getPli() {
		return pli;
	}

	public void setPli(int pli) {
		this.pli = pli;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public int getWater() {
		return water;
	}

	public void setWater(int water) {
		this.water = water;
	}

	public int getElec() {
		return elec;
	}

	public void setElec(int elec) {
		this.elec = elec;
	}

	public int getFurn() {
		return furn;
	}

	public void setFurn(int furn) {
		this.furn = furn;
	}

	public int getCghs() {
		return cghs;
	}

	public void setCghs(int cghs) {
		this.cghs = cghs;
	}

	public int getCegis() {
		return cegis;
	}

	public void setCegis(int cegis) {
		this.cegis = cegis;
	}

	public int getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(int incomeTax) {
		this.incomeTax = incomeTax;
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

	public int getProfTax() {
		return profTax;
	}

	public void setProfTax(int profTax) {
		this.profTax = profTax;
	}

	public int getTada() {
		return tada;
	}

	public void setTada(int tada) {
		this.tada = tada;
	}

	public int getLtc() {
		return ltc;
	}

	public void setLtc(int ltc) {
		this.ltc = ltc;
	}

	public int getMedical() {
		return medical;
	}

	public void setMedical(int medical) {
		this.medical = medical;
	}

	public int getEol() {
		return eol;
	}

	public void setEol(int eol) {
		this.eol = eol;
	}

	public int getCarLoan() {
		return carLoan;
	}

	public void setCarLoan(int carLoan) {
		this.carLoan = carLoan;
	}

	public int getScooterLoan() {
		return scooterLoan;
	}

	public void setScooterLoan(int scooterLoan) {
		this.scooterLoan = scooterLoan;
	}

	public int getPcLoan() {
		return pcLoan;
	}

	public void setPcLoan(int pcLoan) {
		this.pcLoan = pcLoan;
	}

	public int getFestivalAdv() {
		return festivalAdv;
	}

	public void setFestivalAdv(int festivalAdv) {
		this.festivalAdv = festivalAdv;
	}

	public int getHbaLoan() {
		return hbaLoan;
	}

	public void setHbaLoan(int hbaLoan) {
		this.hbaLoan = hbaLoan;
	}

	public int getCycleLoan() {
		return cycleLoan;
	}

	public void setCycleLoan(int cycleLoan) {
		this.cycleLoan = cycleLoan;
	}

	public int getDrMisc1() {
		return drMisc1;
	}

	public void setDrMisc1(int drMisc1) {
		this.drMisc1 = drMisc1;
	}

	public int getDrMisc2() {
		return drMisc2;
	}

	public void setDrMisc2(int drMisc2) {
		this.drMisc2 = drMisc2;
	}

	public int getTotalDebits() {
		return totalDebits;
	}

	public void setTotalDebits(int totalDebits) {
		this.totalDebits = totalDebits;
	}

	public int getNetPay() {
		return netPay;
	}

	public void setNetPay(int netPay) {
		this.netPay = netPay;
	}

	public int getTotalCredits() {
		return totalCredits;
	}

	public void setTotalCredits(int totalCredits) {
		this.totalCredits = totalCredits;
	}

	public String getDrRemarks() {
		return drRemarks;
	}

	public void setDrRemarks(String drRemarks) {
		this.drRemarks = drRemarks;
	}

	public int getWelC() {
		return welC;
	}

	public void setWelC(int welC) {
		this.welC = welC;
	}

	public int getWelRefund() {
		return welRefund;
	}

	public void setWelRefund(int welRefund) {
		this.welRefund = welRefund;
	}

	public int getMess() {
		return mess;
	}

	public void setMess(int mess) {
		this.mess = mess;
	}

	public int getResSecu() {
		return resSecu;
	}

	public void setResSecu(int resSecu) {
		this.resSecu = resSecu;
	}

	public int getBenvolentFund() {
		return benvolentFund;
	}

	public void setBenvolentFund(int benvolentFund) {
		this.benvolentFund = benvolentFund;
	}

	public int getRegimentalFund() {
		return regimentalFund;
	}

	public void setRegimentalFund(int regimentalFund) {
		this.regimentalFund = regimentalFund;
	}

	public int getCcs() {
		return ccs;
	}

	public void setCcs(int ccs) {
		this.ccs = ccs;
	}

	public int getCcsrecovery() {
		return ccsrecovery;
	}

	public void setCcsrecovery(int ccsrecovery) {
		this.ccsrecovery = ccsrecovery;
	}

	public int getHdfc() {
		return hdfc;
	}

	public void setHdfc(int hdfc) {
		this.hdfc = hdfc;
	}

	public int getCanfin() {
		return canfin;
	}

	public void setCanfin(int canfin) {
		this.canfin = canfin;
	}

	public int getLic() {
		return lic;
	}

	public void setLic(int lic) {
		this.lic = lic;
	}

	public int getCourtAttachment() {
		return courtAttachment;
	}

	public void setCourtAttachment(int courtAttachment) {
		this.courtAttachment = courtAttachment;
	}

	public int getGic() {
		return gic;
	}

	public void setGic(int gic) {
		this.gic = gic;
	}

	public int getRecMisc1() {
		return recMisc1;
	}

	public void setRecMisc1(int recMisc1) {
		this.recMisc1 = recMisc1;
	}

	public int getRecMisc2() {
		return recMisc2;
	}

	public void setRecMisc2(int recMisc2) {
		this.recMisc2 = recMisc2;
	}

	public int getRecMisc3() {
		return recMisc3;
	}

	public void setRecMisc3(int recMisc3) {
		this.recMisc3 = recMisc3;
	}

	public int getTotalRecovery() {
		return totalRecovery;
	}

	public void setTotalRecovery(int totalRecovery) {
		this.totalRecovery = totalRecovery;
	}

	public int getFinalPay() {
		return finalPay;
	}

	public void setFinalPay(int finalPay) {
		this.finalPay = finalPay;
	}

	public String getRecRemarks() {
		return recRemarks;
	}

	public void setRecRemarks(String recRemarks) {
		this.recRemarks = recRemarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public List<KeyValueDTO> getEmpSuppSelectedList() {
		return empSuppSelectedList;
	}

	public void setEmpSuppSelectedList(List<KeyValueDTO> empSuppSelectedList) {
		this.empSuppSelectedList = empSuppSelectedList;
	}

	public String getFromID() {
		return fromID;
	}

	public void setFromID(String fromID) {
		this.fromID = fromID;
	}

	public String getToID() {
		return toID;
	}

	public void setToID(String toID) {
		this.toID = toID;
	}

	public List<KeyValueDTO> getSfidList() {
		return sfidList;
	}

	public void setSfidList(List<KeyValueDTO> sfidList) {
		this.sfidList = sfidList;
	}

	public String getPayRegCount() {
		return payRegCount;
	}

	public void setPayRegCount(String payRegCount) {
		this.payRegCount = payRegCount;
	}

	public String getPaySuppCount() {
		return paySuppCount;
	}

	public void setPaySuppCount(String paySuppCount) {
		this.paySuppCount = paySuppCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
