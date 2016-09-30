package com.callippus.web.incometax.dto;

public class DBComparisonDTO {
	private String sfid;
	private String cpsbasic;
	private String misbasic;
	private String cpsgrade;
	private String misgrade;
	private String cpsda;
	private String misda;
	private String cpshra;
	private String mishra;
	private String cpctwoaddincr;
	private String mistwoaddincr;
	private String cpsspay;
	private String misspay;
	private String cpstra;
	private String mistra;
	private String cpswash;
	private String miswash;
	private String cpsfpa;
	private String misfpa;
	private String cpsvarincr;
	private String misvarincr;
	private String cpscghs;
	private String miscghs;
	private String cpscgeis;
	private String miscgeis;
	private String cpsgpf;
	private String misgpf;
	private String cpsitax;
	private String misitax;
	private String cpsptax;
	private String misptax;
	private String cpscycle;
	private String miscycle;
	private String cpscar;
	private String miscar;
	private String cpsscooter;
	private String misscooter;
	private String cpspc;
	private String mispc;
	private String cpshba;
	private String mishba;
	private String cpsFest;
	private String misFest;
	private String cpsRent;
	private String misRent;
	private String cpsWater;
	private String misWater;
	private String cpsElec;
	private String misElec;
	private String cpsTada;
	private String misTada;
	private String cpsBenFund;
	private String misBenFund;
	private String cpsResSecu;
	private String misResSecu;
	private String cpsMess;
	private String misMess;
	private String cpsWelFund;
	private String misWelFund;
	private String cpsRegFund;
	private String misRegFund;
	private String cpsCourt;
	private String misCourt;
	private String cpsHdfc;
	private String misHdfc;
	private String cpsLic;
	private String misLic;
	private String cpsCcsc;
	private String misCcsc;
	private String cpsCcsr;
	private String misCcsr;
	private String cr1;
	private String cr2;
	private String cr4;
	private String db1;
	private String db2;
	private String db3;
	private String db4;
	private String name;
	private String misWelReFund;
	private String db5;
	private String rec1;
	private String rec2;
	private String rec3;
	private String rec4;
	private String rec5;
	private String misDebits;
	private String misCredits;
	private String misRec;
	private String cpsDebits;
	private String cpsCredits;
	private String cpsRec;
	private String cpsTakeHome;
	private String misTakeHome;
	private String creditDiff;
	private String debitDiff;
	private String recDiff;
	private String takeHomeDiff;
	private String crRemarks;
	private String dbRemarks;
	private String recRemarks;
	private String cpsCanfin;
	private String cpsGic;
	private String nameInServiceBook;
	private String designation;
	private String category;
	
	//nagendra.v
	private String misDesignation;
	private String misRisk=null;
	private String cpsRisk=null;
	private String misDupt=null;
	private String cpsDupt=null;
	private String misHindi=null;
	private String cpsHindi=null;
	private String misMisc=null;
	private String cpsMisc=null;
	private String misXerox=null;
	private String cpsXerox=null;
	// SOME DEBITS
	private String misMisc1=null;
	private String cpsMisc1=null;
	private String misMisc2=null;
	private String cpsMisc2=null;

	private String misMedi=null;
	private String cpsMedi=null;

	private String misLtc;
	private String cpsLtc;

/*	private String misfurn=null;
	private String cpsfurn=null;*/
	
	private String cpsEol=null;
	private String misEol=null;
	
	private String misEducess=null;
	private String cpsEducess=null;
	
	private String misShec=null;
	private String cpsShec=null;
	private String cpsTitax=null;
	private String misTitax=null;
	
	private String misGpfsa=null;
	private String cpsGpfsa=null;
	
	private String misPli=null;
	private String cpsPli=null;
	//recovories
	private String rmismis1=null;
	private String rcpsmis1=null;
	private String rcpsmis2=null;
	private String rMismis2=null;
	private String rMismis3=null;
	private String rcpsmis3=null;
	private String rmisCrtTotIN=null;
	private String rcpsCrtTotIN=null;
	private String rMisCrtpIn=null;
	private String rCpsCrtpIn=null;
	private String rMisGicpIN=null;
	private String rCpsGicpIN=null;
	
	private String rMisGicTotIn=null;
	private String rCpsGicTotIn=null;
	private String rMisLicpinst=null;
	private String rCpsLicpinst=null;
	private String rMisLicTotInst=null;
	private String rCpsLicTotInst=null;

	private String rMisWelfund=null;
	private String rCpsWelfund=null;
	private String rMiswelLoan=null;
	private String rCpswelLoan=null;
	private String rmiswelTotInstall=null;
	private String rcpswelTotInstall=null;
	private String rMiswelInst=null;
	private String rCpsdwelInst=null;
	private String rmisHdfcpinstall=null;
	private String rcpsHdfcpinstall=null;
	private String rMisHdfcTotInstall=null;
	private String rCpsHdfcTotInstall=null;
	private String rMisCanfinpInstall=null;
	private String rCpsCanfinpInstall=null;
	private String rMisCanfinTotInstall=null;
	private String rCpsCanfinTotInstall=null;

	
	
	private String mispcInstall=null;
	private String cpscInstall=null;
	private String mispcTotamount=null;
	private String cpspcTotamount=null;
	
	private String misFestInstall=null;
	private String cpsFestInstall=null;
	private String misFesttotal=null;
	private String cpsFesttotal=null;
	
	private String misCycleinstall=null;
	private String cpsCycleinstall=null;
	private String misCycletot=null;
	private String cpsCycletot=null;
	
	

	private String misCarinstall=null;
	private String cpsCarinstall=null;
	private String misCartot=null;
	private String cpsCartot=null;
	
	private String misHbainstall=null;
	private String cpsHbainstall=null;
	private String misHbatot=null;
	private String cpsHbatot=null;
	private String misScotterinstall=null;
	private String cpsScotterinstall=null;
	private String misScottertot=null;
	private String cpsScottertot=null;
	

	private String misgpfinstall=null;
	private String cpsgpfinstall=null;
	private String misgpfpreinstall=null;
	private String cpsgpfpreinstall=null;
	private String misgpfTotinstall=null;
	private String cpsgpfTotinstall=null;

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getCpsTitax() {
		return cpsTitax;
	}
	public void setCpsTitax(String cpsTitax) {
		this.cpsTitax = cpsTitax;
	}
	public String getMisTitax() {
		return misTitax;
	}
	public void setMisTitax(String misTitax) {
		this.misTitax = misTitax;
	}
	public String getCpsEol() {
		return cpsEol;
	}
	public void setCpsEol(String cpsEol) {
		this.cpsEol = cpsEol;
	}
	public String getMisEol() {
		return misEol;
	}
	public void setMisEol(String misEol) {
		this.misEol = misEol;
	}
	public String getMisEducess() {
		return misEducess;
	}
	public void setMisEducess(String misEducess) {
		this.misEducess = misEducess;
	}
	public String getCpsEducess() {
		return cpsEducess;
	}
	public void setCpsEducess(String cpsEducess) {
		this.cpsEducess = cpsEducess;
	}
	public String getMisShec() {
		return misShec;
	}
	public void setMisShec(String misShec) {
		this.misShec = misShec;
	}
	public String getCpsShec() {
		return cpsShec;
	}
	public void setCpsShec(String cpsShec) {
		this.cpsShec = cpsShec;
	}
	public String getMisgpfinstall() {
		return misgpfinstall;
	}
	public void setMisgpfinstall(String misgpfinstall) {
		this.misgpfinstall = misgpfinstall;
	}
	public String getCpsgpfinstall() {
		return cpsgpfinstall;
	}
	public void setCpsgpfinstall(String cpsgpfinstall) {
		this.cpsgpfinstall = cpsgpfinstall;
	}
	public String getMisgpfpreinstall() {
		return misgpfpreinstall;
	}
	public void setMisgpfpreinstall(String misgpfpreinstall) {
		this.misgpfpreinstall = misgpfpreinstall;
	}
	public String getCpsgpfpreinstall() {
		return cpsgpfpreinstall;
	}
	public void setCpsgpfpreinstall(String cpsgpfpreinstall) {
		this.cpsgpfpreinstall = cpsgpfpreinstall;
	}
	public String getMisgpfTotinstall() {
		return misgpfTotinstall;
	}
	public void setMisgpfTotinstall(String misgpfTotinstall) {
		this.misgpfTotinstall = misgpfTotinstall;
	}
	public String getCpsgpfTotinstall() {
		return cpsgpfTotinstall;
	}
	public void setCpsgpfTotinstall(String cpsgpfTotinstall) {
		this.cpsgpfTotinstall = cpsgpfTotinstall;
	}
	public String getMisScotterinstall() {
		return misScotterinstall;
	}
	public void setMisScotterinstall(String misScotterinstall) {
		this.misScotterinstall = misScotterinstall;
	}
	public String getCpsScotterinstall() {
		return cpsScotterinstall;
	}
	public void setCpsScotterinstall(String cpsScotterinstall) {
		this.cpsScotterinstall = cpsScotterinstall;
	}
	public String getMisScottertot() {
		return misScottertot;
	}
	public void setMisScottertot(String misScottertot) {
		this.misScottertot = misScottertot;
	}
	public String getCpsScottertot() {
		return cpsScottertot;
	}
	public void setCpsScottertot(String cpsScottertot) {
		this.cpsScottertot = cpsScottertot;
	}
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	


	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public String getMisCycleinstall() {
		return misCycleinstall;
	}
	public void setMisCycleinstall(String misCycleinstall) {
		this.misCycleinstall = misCycleinstall;
	}
	public String getCpsCycleinstall() {
		return cpsCycleinstall;
	}
	public void setCpsCycleinstall(String cpsCycleinstall) {
		this.cpsCycleinstall = cpsCycleinstall;
	}
	public String getMisCycletot() {
		return misCycletot;
	}
	public void setMisCycletot(String misCycletot) {
		this.misCycletot = misCycletot;
	}
	public String getCpsCycletot() {
		return cpsCycletot;
	}
	public void setCpsCycletot(String cpsCycletot) {
		this.cpsCycletot = cpsCycletot;
	}
	public String getMisCarinstall() {
		return misCarinstall;
	}
	public void setMisCarinstall(String misCarinstall) {
		this.misCarinstall = misCarinstall;
	}
	public String getCpsCarinstall() {
		return cpsCarinstall;
	}
	public void setCpsCarinstall(String cpsCarinstall) {
		this.cpsCarinstall = cpsCarinstall;
	}
	public String getMisCartot() {
		return misCartot;
	}
	public void setMisCartot(String misCartot) {
		this.misCartot = misCartot;
	}
	public String getCpsCartot() {
		return cpsCartot;
	}
	public void setCpsCartot(String cpsCartot) {
		this.cpsCartot = cpsCartot;
	}
	public String getMisHbainstall() {
		return misHbainstall;
	}
	public void setMisHbainstall(String misHbainstall) {
		this.misHbainstall = misHbainstall;
	}
	public String getCpsHbainstall() {
		return cpsHbainstall;
	}
	public void setCpsHbainstall(String cpsHbainstall) {
		this.cpsHbainstall = cpsHbainstall;
	}
	public String getMisHbatot() {
		return misHbatot;
	}
	public void setMisHbatot(String misHbatot) {
		this.misHbatot = misHbatot;
	}
	public String getCpsHbatot() {
		return cpsHbatot;
	}
	public void setCpsHbatot(String cpsHbatot) {
		this.cpsHbatot = cpsHbatot;
	}
	public String getMispcInstall() {
		return mispcInstall;
	}
	public String getMisFestInstall() {
		return misFestInstall;
	}
	public void setMisFestInstall(String misFestInstall) {
		this.misFestInstall = misFestInstall;
	}
	public String getCpsFestInstall() {
		return cpsFestInstall;
	}
	public void setCpsFestInstall(String cpsFestInstall) {
		this.cpsFestInstall = cpsFestInstall;
	}
	public String getMisFesttotal() {
		return misFesttotal;
	}
	public void setMisFesttotal(String misFesttotal) {
		this.misFesttotal = misFesttotal;
	}
	public String getCpsFesttotal() {
		return cpsFesttotal;
	}
	public void setCpsFesttotal(String cpsFesttotal) {
		this.cpsFesttotal = cpsFesttotal;
	}
	public void setMispcInstall(String mispcInstall) {
		this.mispcInstall = mispcInstall;
	}
	public String getCpscInstall() {
		return cpscInstall;
	}
	public void setCpscInstall(String cpscInstall) {
		this.cpscInstall = cpscInstall;
	}
	public String getMispcTotamount() {
		return mispcTotamount;
	}
	public void setMispcTotamount(String mispcTotamount) {
		this.mispcTotamount = mispcTotamount;
	}
	public String getCpspcTotamount() {
		return cpspcTotamount;
	}
	public void setCpspcTotamount(String cpspcTotamount) {
		this.cpspcTotamount = cpspcTotamount;
	}
/*	public String getMispcInstalamount() {
		return mispcInstalamount;
	}
	public void setMispcInstalamount(String mispcInstalamount) {
		this.mispcInstalamount = mispcInstalamount;
	}
	public String getCpspcInstalamount() {
		return cpspcInstalamount;
	}
	public void setCpspcInstalamount(String cpspcInstalamount) {
		this.cpspcInstalamount = cpspcInstalamount;
	}*/
	public String getMisMisc() {
		return misMisc;
	}
	public void setMisMisc(String misMisc) {
		this.misMisc = misMisc;
	}
	public String getCpsMisc() {
		return cpsMisc;
	}
	public void setCpsMisc(String cpsMisc) {
		this.cpsMisc = cpsMisc;
	}
	public String getMisXerox() {
		return misXerox;
	}
	public void setMisXerox(String misXerox) {
		this.misXerox = misXerox;
	}
	public String getCpsXerox() {
		return cpsXerox;
	}
	public void setCpsXerox(String cpsXerox) {
		this.cpsXerox = cpsXerox;
	}
	public String getMisDupt() {
		return misDupt;
	}
	public void setMisDupt(String misDupt) {
		this.misDupt = misDupt;
	}
	public String getCpsDupt() {
		return cpsDupt;
	}
	public void setCpsDupt(String cpsDupt) {
		this.cpsDupt = cpsDupt;
	}

	public String getMisHindi() {
		return misHindi;
	}
	public void setMisHindi(String misHindi) {
		this.misHindi = misHindi;
	}
	public String getCpsHindi() {
		return cpsHindi;
	}
	public void setCpsHindi(String cpsHindi) {
		this.cpsHindi = cpsHindi;
	}
	public String getMisRisk() {
		return misRisk;
	}
	public void setMisRisk(String misRisk) {
		this.misRisk = misRisk;
	}
	public String getCpsRisk() {
		return cpsRisk;
	}
	public void setCpsRisk(String cpsRisk) {
		this.cpsRisk = cpsRisk;
	}
	public String getNameInServiceBook() {
		return nameInServiceBook;
	}
	public void setNameInServiceBook(String nameInServiceBook) {
		this.nameInServiceBook = nameInServiceBook;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCpsCanfin() {
		return cpsCanfin;
	}
	public void setCpsCanfin(String cpsCanfin) {
		this.cpsCanfin = cpsCanfin;
	}
	public String getCpsGic() {
		return cpsGic;
	}
	public void setCpsGic(String cpsGic) {
		this.cpsGic = cpsGic;
	}
	public String getCrRemarks() {
		return crRemarks;
	}
	public void setCrRemarks(String crRemarks) {
		this.crRemarks = crRemarks;
	}
	public String getDbRemarks() {
		return dbRemarks;
	}
	public void setDbRemarks(String dbRemarks) {
		this.dbRemarks = dbRemarks;
	}
	public String getRecRemarks() {
		return recRemarks;
	}
	public void setRecRemarks(String recRemarks) {
		this.recRemarks = recRemarks;
	}
	public String getMisWelReFund() {
		return misWelReFund;
	}
	public void setMisWelReFund(String misWelReFund) {
		this.misWelReFund = misWelReFund;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getMisfpa() {
		return misfpa;
	}
	public void setMisfpa(String misfpa) {
		this.misfpa = misfpa;
	}
	public String getMisRegFund() {
		return misRegFund;
	}
	public void setMisRegFund(String misRegFund) {
		this.misRegFund = misRegFund;
	}
	public String getCpsbasic() {
		return cpsbasic;
	}
	public void setCpsbasic(String cpsbasic) {
		this.cpsbasic = cpsbasic;
	}
	public String getMisbasic() {
		return misbasic;
	}
	public void setMisbasic(String misbasic) {
		this.misbasic = misbasic;
	}
	public String getCpsgrade() {
		return cpsgrade;
	}
	public void setCpsgrade(String cpsgrade) {
		this.cpsgrade = cpsgrade;
	}
	public String getMisgrade() {
		return misgrade;
	}
	public void setMisgrade(String misgrade) {
		this.misgrade = misgrade;
	}
	public String getCpsda() {
		return cpsda;
	}
	public void setCpsda(String cpsda) {
		this.cpsda = cpsda;
	}
	public String getMisda() {
		return misda;
	}
	public void setMisda(String misda) {
		this.misda = misda;
	}
	public String getCpshra() {
		return cpshra;
	}
	public void setCpshra(String cpshra) {
		this.cpshra = cpshra;
	}
	public String getMishra() {
		return mishra;
	}
	public void setMishra(String mishra) {
		this.mishra = mishra;
	}
	public String getCpctwoaddincr() {
		return cpctwoaddincr;
	}
	public void setCpctwoaddincr(String cpctwoaddincr) {
		this.cpctwoaddincr = cpctwoaddincr;
	}
	public String getMistwoaddincr() {
		return mistwoaddincr;
	}
	public void setMistwoaddincr(String mistwoaddincr) {
		this.mistwoaddincr = mistwoaddincr;
	}
	public String getCpsspay() {
		return cpsspay;
	}
	public void setCpsspay(String cpsspay) {
		this.cpsspay = cpsspay;
	}
	public String getMisspay() {
		return misspay;
	}
	public void setMisspay(String misspay) {
		this.misspay = misspay;
	}
	public String getCpstra() {
		return cpstra;
	}
	public void setCpstra(String cpstra) {
		this.cpstra = cpstra;
	}
	
	public String getCpswash() {
		return cpswash;
	}
	public void setCpswash(String cpswash) {
		this.cpswash = cpswash;
	}
	public String getMiswash() {
		return miswash;
	}
	public void setMiswash(String miswash) {
		this.miswash = miswash;
	}
	public String getCpsfpa() {
		return cpsfpa;
	}
	public void setCpsfpa(String cpsfpa) {
		this.cpsfpa = cpsfpa;
	}
	public String getMistra() {
		return mistra;
	}
	public void setMistra(String mistra) {
		this.mistra = mistra;
	}
	public String getCpsvarincr() {
		return cpsvarincr;
	}
	public void setCpsvarincr(String cpsvarincr) {
		this.cpsvarincr = cpsvarincr;
	}
	public String getMisvarincr() {
		return misvarincr;
	}
	public void setMisvarincr(String misvarincr) {
		this.misvarincr = misvarincr;
	}
	public String getCpscghs() {
		return cpscghs;
	}
	public void setCpscghs(String cpscghs) {
		this.cpscghs = cpscghs;
	}
	public String getMiscghs() {
		return miscghs;
	}
	public void setMiscghs(String miscghs) {
		this.miscghs = miscghs;
	}
	public String getCpscgeis() {
		return cpscgeis;
	}
	public void setCpscgeis(String cpscgeis) {
		this.cpscgeis = cpscgeis;
	}
	public String getMiscgeis() {
		return miscgeis;
	}
	public void setMiscgeis(String miscgeis) {
		this.miscgeis = miscgeis;
	}
	public String getCpsgpf() {
		return cpsgpf;
	}
	public void setCpsgpf(String cpsgpf) {
		this.cpsgpf = cpsgpf;
	}
	public String getMisgpf() {
		return misgpf;
	}
	public void setMisgpf(String misgpf) {
		this.misgpf = misgpf;
	}
	public String getCpsitax() {
		return cpsitax;
	}
	public void setCpsitax(String cpsitax) {
		this.cpsitax = cpsitax;
	}
	public String getMisitax() {
		return misitax;
	}
	public void setMisitax(String misitax) {
		this.misitax = misitax;
	}
	public String getCpsptax() {
		return cpsptax;
	}
	public void setCpsptax(String cpsptax) {
		this.cpsptax = cpsptax;
	}
	public String getMisptax() {
		return misptax;
	}
	public void setMisptax(String misptax) {
		this.misptax = misptax;
	}
	public String getCpscycle() {
		return cpscycle;
	}
	public void setCpscycle(String cpscycle) {
		this.cpscycle = cpscycle;
	}
	public String getMiscycle() {
		return miscycle;
	}
	public void setMiscycle(String miscycle) {
		this.miscycle = miscycle;
	}
	public String getCpscar() {
		return cpscar;
	}
	public void setCpscar(String cpscar) {
		this.cpscar = cpscar;
	}
	public String getMiscar() {
		return miscar;
	}
	public void setMiscar(String miscar) {
		this.miscar = miscar;
	}
	public String getCpsscooter() {
		return cpsscooter;
	}
	public void setCpsscooter(String cpsscooter) {
		this.cpsscooter = cpsscooter;
	}
	public String getMisscooter() {
		return misscooter;
	}
	public void setMisscooter(String misscooter) {
		this.misscooter = misscooter;
	}
	public String getCpspc() {
		return cpspc;
	}
	public void setCpspc(String cpspc) {
		this.cpspc = cpspc;
	}
	public String getMispc() {
		return mispc;
	}
	public void setMispc(String mispc) {
		this.mispc = mispc;
	}
	public String getCpshba() {
		return cpshba;
	}
	public void setCpshba(String cpshba) {
		this.cpshba = cpshba;
	}
	public String getMishba() {
		return mishba;
	}
	public void setMishba(String mishba) {
		this.mishba = mishba;
	}
	public String getCpsFest() {
		return cpsFest;
	}
	public void setCpsFest(String cpsFest) {
		this.cpsFest = cpsFest;
	}
	public String getMisFest() {
		return misFest;
	}
	public void setMisFest(String misFest) {
		this.misFest = misFest;
	}
	public String getCpsRent() {
		return cpsRent;
	}
	public void setCpsRent(String cpsRent) {
		this.cpsRent = cpsRent;
	}
	public String getMisRent() {
		return misRent;
	}
	public void setMisRent(String misRent) {
		this.misRent = misRent;
	}
	public String getCpsWater() {
		return cpsWater;
	}
	public void setCpsWater(String cpsWater) {
		this.cpsWater = cpsWater;
	}
	public String getMisWater() {
		return misWater;
	}
	public void setMisWater(String misWater) {
		this.misWater = misWater;
	}
	public String getCpsElec() {
		return cpsElec;
	}
	public void setCpsElec(String cpsElec) {
		this.cpsElec = cpsElec;
	}
	public String getMisElec() {
		return misElec;
	}
	public void setMisElec(String misElec) {
		this.misElec = misElec;
	}
	public String getCpsTada() {
		return cpsTada;
	}
	public void setCpsTada(String cpsTada) {
		this.cpsTada = cpsTada;
	}
	public String getMisTada() {
		return misTada;
	}
	public void setMisTada(String misTada) {
		this.misTada = misTada;
	}
	public String getCpsBenFund() {
		return cpsBenFund;
	}
	public void setCpsBenFund(String cpsBenFund) {
		this.cpsBenFund = cpsBenFund;
	}
	public String getMisBenFund() {
		return misBenFund;
	}
	public void setMisBenFund(String misBenFund) {
		this.misBenFund = misBenFund;
	}
	public String getCpsResSecu() {
		return cpsResSecu;
	}
	public void setCpsResSecu(String cpsResSecu) {
		this.cpsResSecu = cpsResSecu;
	}
	public String getMisResSecu() {
		return misResSecu;
	}
	public void setMisResSecu(String misResSecu) {
		this.misResSecu = misResSecu;
	}
	public String getCpsMess() {
		return cpsMess;
	}
	public void setCpsMess(String cpsMess) {
		this.cpsMess = cpsMess;
	}
	public String getMisMess() {
		return misMess;
	}
	public void setMisMess(String misMess) {
		this.misMess = misMess;
	}
	public String getCpsWelFund() {
		return cpsWelFund;
	}
	public void setCpsWelFund(String cpsWelFund) {
		this.cpsWelFund = cpsWelFund;
	}
	public String getMisWelFund() {
		return misWelFund;
	}
	public void setMisWelFund(String misWelFund) {
		this.misWelFund = misWelFund;
	}
	public String getCpsRegFund() {
		return cpsRegFund;
	}
	public void setCpsRegFund(String cpsRegFund) {
		this.cpsRegFund = cpsRegFund;
	}
	public String getCpsCourt() {
		return cpsCourt;
	}
	public void setCpsCourt(String cpsCourt) {
		this.cpsCourt = cpsCourt;
	}
	public String getMisCourt() {
		return misCourt;
	}
	public void setMisCourt(String misCourt) {
		this.misCourt = misCourt;
	}
	public String getCpsHdfc() {
		return cpsHdfc;
	}
	public void setCpsHdfc(String cpsHdfc) {
		this.cpsHdfc = cpsHdfc;
	}
	public String getMisHdfc() {
		return misHdfc;
	}
	public void setMisHdfc(String misHdfc) {
		this.misHdfc = misHdfc;
	}
	public String getCpsLic() {
		return cpsLic;
	}
	public void setCpsLic(String cpsLic) {
		this.cpsLic = cpsLic;
	}
	public String getMisLic() {
		return misLic;
	}
	public void setMisLic(String misLic) {
		this.misLic = misLic;
	}
	public String getCpsCcsc() {
		return cpsCcsc;
	}
	public void setCpsCcsc(String cpsCcsc) {
		this.cpsCcsc = cpsCcsc;
	}
	public String getMisCcsc() {
		return misCcsc;
	}
	public void setMisCcsc(String misCcsc) {
		this.misCcsc = misCcsc;
	}
	public String getCpsCcsr() {
		return cpsCcsr;
	}
	public void setCpsCcsr(String cpsCcsr) {
		this.cpsCcsr = cpsCcsr;
	}
	public String getMisCcsr() {
		return misCcsr;
	}
	public void setMisCcsr(String misCcsr) {
		this.misCcsr = misCcsr;
	}
	public String getCr1() {
		return cr1;
	}
	public void setCr1(String cr1) {
		this.cr1 = cr1;
	}
	public String getCr2() {
		return cr2;
	}
	public void setCr2(String cr2) {
		this.cr2 = cr2;
	}
	public String getCr4() {
		return cr4;
	}
	public void setCr4(String cr4) {
		this.cr4 = cr4;
	}
	public String getDb1() {
		return db1;
	}
	public void setDb1(String db1) {
		this.db1 = db1;
	}
	public String getDb2() {
		return db2;
	}
	public void setDb2(String db2) {
		this.db2 = db2;
	}
	public String getDb3() {
		return db3;
	}
	public void setDb3(String db3) {
		this.db3 = db3;
	}
	public String getDb4() {
		return db4;
	}
	public void setDb4(String db4) {
		this.db4 = db4;
	}
	public String getDb5() {
		return db5;
	}
	public void setDb5(String db5) {
		this.db5 = db5;
	}
	public String getRec1() {
		return rec1;
	}
	public void setRec1(String rec1) {
		this.rec1 = rec1;
	}
	public String getRec2() {
		return rec2;
	}
	public void setRec2(String rec2) {
		this.rec2 = rec2;
	}
	public String getRec3() {
		return rec3;
	}
	public void setRec3(String rec3) {
		this.rec3 = rec3;
	}
	public String getRec4() {
		return rec4;
	}
	public void setRec4(String rec4) {
		this.rec4 = rec4;
	}
	public String getRec5() {
		return rec5;
	}
	public void setRec5(String rec5) {
		this.rec5 = rec5;
	}
	public String getMisDebits() {
		return misDebits;
	}
	public void setMisDebits(String misDebits) {
		this.misDebits = misDebits;
	}
	public String getMisCredits() {
		return misCredits;
	}
	public void setMisCredits(String misCredits) {
		this.misCredits = misCredits;
	}
	public String getMisRec() {
		return misRec;
	}
	public void setMisRec(String misRec) {
		this.misRec = misRec;
	}
	public String getCpsDebits() {
		return cpsDebits;
	}
	public void setCpsDebits(String cpsDebits) {
		this.cpsDebits = cpsDebits;
	}
	public String getCpsCredits() {
		return cpsCredits;
	}
	public void setCpsCredits(String cpsCredits) {
		this.cpsCredits = cpsCredits;
	}
	public String getCpsRec() {
		return cpsRec;
	}
	public void setCpsRec(String cpsRec) {
		this.cpsRec = cpsRec;
	}
	public String getCpsTakeHome() {
		return cpsTakeHome;
	}
	public void setCpsTakeHome(String cpsTakeHome) {
		this.cpsTakeHome = cpsTakeHome;
	}
	public String getMisTakeHome() {
		return misTakeHome;
	}
	public void setMisTakeHome(String misTakeHome) {
		this.misTakeHome = misTakeHome;
	}
	public String getCreditDiff() {
		return creditDiff;
	}
	public void setCreditDiff(String creditDiff) {
		this.creditDiff = creditDiff;
	}
	public String getDebitDiff() {
		return debitDiff;
	}
	public void setDebitDiff(String debitDiff) {
		this.debitDiff = debitDiff;
	}
	public String getRecDiff() {
		return recDiff;
	}
	public void setRecDiff(String recDiff) {
		this.recDiff = recDiff;
	}
	public String getTakeHomeDiff() {
		return takeHomeDiff;
	}
	public void setTakeHomeDiff(String takeHomeDiff) {
		this.takeHomeDiff = takeHomeDiff;
	}
	public String getMisDesignation() {
		return misDesignation;
	}
	public void setMisDesignation(String misDesignation) {
		this.misDesignation = misDesignation;
	}
	public String getMisMisc1() {
		return misMisc1;
	}
	public void setMisMisc1(String misMisc1) {
		this.misMisc1 = misMisc1;
	}
	public String getCpsMisc1() {
		return cpsMisc1;
	}
	public void setCpsMisc1(String cpsMisc1) {
		this.cpsMisc1 = cpsMisc1;
	}
	public String getMisMisc2() {
		return misMisc2;
	}
	public void setMisMisc2(String misMisc2) {
		this.misMisc2 = misMisc2;
	}
	public String getCpsMisc2() {
		return cpsMisc2;
	}
	public void setCpsMisc2(String cpsMisc2) {
		this.cpsMisc2 = cpsMisc2;
	}
	public String getMisMedi() {
		return misMedi;
	}
	public void setMisMedi(String misMedi) {
		this.misMedi = misMedi;
	}
	public String getCpsMedi() {
		return cpsMedi;
	}
	public void setCpsMedi(String cpsMedi) {
		this.cpsMedi = cpsMedi;
	}
	public String getMisLtc() {
		return misLtc;
	}
	public void setMisLtc(String misLtc) {
		this.misLtc = misLtc;
	}
	public String getCpsLtc() {
		return cpsLtc;
	}
	public void setCpsLtc(String cpsLtc) {
		this.cpsLtc = cpsLtc;
	}
	/*public String getMisfurn() {
		return misfurn;
	}
	public void setMisfurn(String misfurn) {
		this.misfurn = misfurn;
	}
	public String getCpsfurn() {
		return cpsfurn;
	}
	public void setCpsfurn(String cpsfurn) {
		this.cpsfurn = cpsfurn;
	}
	public String getMisEol_hpl() {
		return misEol_hpl;
	}
	public void setMisEol_hpl(String misEol_hpl) {
		this.misEol_hpl = misEol_hpl;
	}
	public String getCpsEol_hpl() {
		return cpsEol_hpl;
	}
	public void setCpsEol_hpl(String cpsEol_hpl) {
		this.cpsEol_hpl = cpsEol_hpl;
	}*/
/*	public String getMisShec() {
		return misShec;
	}
	public void setMisShec(String misShec) {
		this.misShec = misShec;
	}
	public String getCpsShec() {
		return cpsShec;
	}
	public void setCpsShec(String cpsShec) {
		this.cpsShec = cpsShec;
	}*/
	public String getMisGpfsa() {
		return misGpfsa;
	}
	public void setMisGpfsa(String misGpfsa) {
		this.misGpfsa = misGpfsa;
	}
	public String getCpsGpfsa() {
		return cpsGpfsa;
	}
	public void setCpsGpfsa(String cpsGpfsa) {
		this.cpsGpfsa = cpsGpfsa;
	}
	public String getMisPli() {
		return misPli;
	}
	public void setMisPli(String misPli) {
		this.misPli = misPli;
	}
	public String getCpsPli() {
		return cpsPli;
	}
	public void setCpsPli(String cpsPli) {
		this.cpsPli = cpsPli;
	}
	public String getRmismis1() {
		return rmismis1;
	}
	public void setRmismis1(String rmismis1) {
		this.rmismis1 = rmismis1;
	}
	public String getRcpsmis1() {
		return rcpsmis1;
	}
	public void setRcpsmis1(String rcpsmis1) {
		this.rcpsmis1 = rcpsmis1;
	}
	public String getRcpsmis2() {
		return rcpsmis2;
	}
	public void setRcpsmis2(String rcpsmis2) {
		this.rcpsmis2 = rcpsmis2;
	}
	public String getrMismis2() {
		return rMismis2;
	}
	public void setrMismis2(String rMismis2) {
		this.rMismis2 = rMismis2;
	}
	public String getrMismis3() {
		return rMismis3;
	}
	public void setrMismis3(String rMismis3) {
		this.rMismis3 = rMismis3;
	}
	public String getRcpsmis3() {
		return rcpsmis3;
	}
	public void setRcpsmis3(String rcpsmis3) {
		this.rcpsmis3 = rcpsmis3;
	}
	public String getRmisCrtTotIN() {
		return rmisCrtTotIN;
	}
	public void setRmisCrtTotIN(String rmisCrtTotIN) {
		this.rmisCrtTotIN = rmisCrtTotIN;
	}
	public String getRcpsCrtTotIN() {
		return rcpsCrtTotIN;
	}
	public void setRcpsCrtTotIN(String rcpsCrtTotIN) {
		this.rcpsCrtTotIN = rcpsCrtTotIN;
	}
	public String getrMisCrtpIn() {
		return rMisCrtpIn;
	}
	public void setrMisCrtpIn(String rMisCrtpIn) {
		this.rMisCrtpIn = rMisCrtpIn;
	}
	public String getrCpsCrtpIn() {
		return rCpsCrtpIn;
	}
	public void setrCpsCrtpIn(String rCpsCrtpIn) {
		this.rCpsCrtpIn = rCpsCrtpIn;
	}
	public String getrMisGicpIN() {
		return rMisGicpIN;
	}
	public void setrMisGicpIN(String rMisGicpIN) {
		this.rMisGicpIN = rMisGicpIN;
	}
	public String getrCpsGicpIN() {
		return rCpsGicpIN;
	}
	public void setrCpsGicpIN(String rCpsGicpIN) {
		this.rCpsGicpIN = rCpsGicpIN;
	}
	public String getrMisGicTotIn() {
		return rMisGicTotIn;
	}
	public void setrMisGicTotIn(String rMisGicTotIn) {
		this.rMisGicTotIn = rMisGicTotIn;
	}
	public String getrCpsGicTotIn() {
		return rCpsGicTotIn;
	}
	public void setrCpsGicTotIn(String rCpsGicTotIn) {
		this.rCpsGicTotIn = rCpsGicTotIn;
	}
	public String getrMisLicpinst() {
		return rMisLicpinst;
	}
	public void setrMisLicpinst(String rMisLicpinst) {
		this.rMisLicpinst = rMisLicpinst;
	}
	public String getrCpsLicpinst() {
		return rCpsLicpinst;
	}
	public void setrCpsLicpinst(String rCpsLicpinst) {
		this.rCpsLicpinst = rCpsLicpinst;
	}
	public String getrMisLicTotInst() {
		return rMisLicTotInst;
	}
	public void setrMisLicTotInst(String rMisLicTotInst) {
		this.rMisLicTotInst = rMisLicTotInst;
	}
	public String getrCpsLicTotInst() {
		return rCpsLicTotInst;
	}
	public void setrCpsLicTotInst(String rCpsLicTotInst) {
		this.rCpsLicTotInst = rCpsLicTotInst;
	}
	public String getrMisWelfund() {
		return rMisWelfund;
	}
	public void setrMisWelfund(String rMisWelfund) {
		this.rMisWelfund = rMisWelfund;
	}
	public String getrCpsWelfund() {
		return rCpsWelfund;
	}
	public void setrCpsWelfund(String rCpsWelfund) {
		this.rCpsWelfund = rCpsWelfund;
	}
	public String getrMiswelLoan() {
		return rMiswelLoan;
	}
	public void setrMiswelLoan(String rMiswelLoan) {
		this.rMiswelLoan = rMiswelLoan;
	}
	public String getrCpswelLoan() {
		return rCpswelLoan;
	}
	public void setrCpswelLoan(String rCpswelLoan) {
		this.rCpswelLoan = rCpswelLoan;
	}
	
	public String getrMiswelInst() {
		return rMiswelInst;
	}
	public void setrMiswelInst(String rMiswelInst) {
		this.rMiswelInst = rMiswelInst;
	}
	public String getrCpsdwelInst() {
		return rCpsdwelInst;
	}
	public void setrCpsdwelInst(String rCpsdwelInst) {
		this.rCpsdwelInst = rCpsdwelInst;
	}
	
	public String getRmiswelTotInstall() {
		return rmiswelTotInstall;
	}
	public void setRmiswelTotInstall(String rmiswelTotInstall) {
		this.rmiswelTotInstall = rmiswelTotInstall;
	}
	public String getRcpswelTotInstall() {
		return rcpswelTotInstall;
	}
	public void setRcpswelTotInstall(String rcpswelTotInstall) {
		this.rcpswelTotInstall = rcpswelTotInstall;
	}
	public String getRmisHdfcpinstall() {
		return rmisHdfcpinstall;
	}
	public void setRmisHdfcpinstall(String rmisHdfcpinstall) {
		this.rmisHdfcpinstall = rmisHdfcpinstall;
	}
	public String getRcpsHdfcpinstall() {
		return rcpsHdfcpinstall;
	}
	public void setRcpsHdfcpinstall(String rcpsHdfcpinstall) {
		this.rcpsHdfcpinstall = rcpsHdfcpinstall;
	}
	
	public String getrMisHdfcTotInstall() {
		return rMisHdfcTotInstall;
	}
	public void setrMisHdfcTotInstall(String rMisHdfcTotInstall) {
		this.rMisHdfcTotInstall = rMisHdfcTotInstall;
	}
	public String getrCpsHdfcTotInstall() {
		return rCpsHdfcTotInstall;
	}
	public void setrCpsHdfcTotInstall(String rCpsHdfcTotInstall) {
		this.rCpsHdfcTotInstall = rCpsHdfcTotInstall;
	}

	public String getrMisCanfinpInstall() {
		return rMisCanfinpInstall;
	}
	public void setrMisCanfinpInstall(String rMisCanfinpInstall) {
		this.rMisCanfinpInstall = rMisCanfinpInstall;
	}
	public String getrCpsCanfinpInstall() {
		return rCpsCanfinpInstall;
	}
	public void setrCpsCanfinpInstall(String rCpsCanfinpInstall) {
		this.rCpsCanfinpInstall = rCpsCanfinpInstall;
	}
	public String getrMisCanfinTotInstall() {
		return rMisCanfinTotInstall;
	}
	public void setrMisCanfinTotInstall(String rMisCanfinTotInstall) {
		this.rMisCanfinTotInstall = rMisCanfinTotInstall;
	}
	public String getrCpsCanfinTotInstall() {
		return rCpsCanfinTotInstall;
	}
	public void setrCpsCanfinTotInstall(String rCpsCanfinTotInstall) {
		this.rCpsCanfinTotInstall = rCpsCanfinTotInstall;
	}
	
	
	
	
	
	
	
	
}
