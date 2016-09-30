package com.callippus.web.beans.configuration;

import java.util.List;

public class ConfigurationBean {
    private String minAgeForJob;
    private String escalationtime;
    private String admin;
    private String message;
    private String param;
    private String name;
    private String type;
    private List instanceList;
    private String configurationDetails;
    private String maxLTCEncasementToEL;

    private String minELAvailAftLTCEncashment;
    private String maxLTCEncashmentToELService;
    private String commLeaveAvailELAvail;
    private String commLeaveAvailELAvailDesc;
    private String maternityLeaveAvailFor;
    private String maxLeaveConversion;
    private String leaveStartDate;
    private String cghsMajorAgeLimit;
    private String cghsSonAgeLimit;
    private String cghsSalaryLimit;
   
    private String cmlWoMcExceptionId;
    private String lndWoMcExceptionId;
    
    private String ltcJuniorWorkExp;
    private String ltcSeniorrWorkExp;
    private String ltcPriorDob;
    private String ltcSonAgeLimit;
    private String ltcMajorAgeLimit;
    private String ltcAdvancePercentage;
    
    private String dashboardRecords;
    private String myRequestDashboardRecords;
    
    @SuppressWarnings("unchecked")
	private List cmlWoMcExceptionIdList;
    @SuppressWarnings("unchecked")
	private List lndWoMcExceptionIdList;
    
    private String specialAllowance;
    private String washAllowance;
    private String xeroxAllowance;
    private String wfundOfficers;
    private String wfundStaff;
    private String messOfficers;
    private String messOutside;
    private String bfundOfficers;
    private String bfundStaff;
    private String regFundOfficers;
    private String regFundStaff;
    private String cghsAdvApprPerc;
    private Float rent;
    private Float water;
    private Float electricity;
    private Float furniture;
    private String ltcAdvancePriorDays;
    private String ltcDaPercengate;
    
    private String itTraAllw;
    private String itphysicallyHandicapped;
    private String srCitizemMaxAge;
    
    
    private String tutionFeeMaxLimitPerOneChild;
	private String tutionFeeMaxChildToBeClaimed;
	
	private Integer tadaTimeBoundPast;                   //This is declared for TadaDaPercentage
	private Integer tadaTimeBoundFuture;
	
	
    
	//signing authority
	private String authorityType;
	private String authorityEmpName;
	private String pk;
	private List<SigningAuthorityDTO> signingAuthorityList;
    
	//mailConfiguration
	private String sendMail;
	
	public String getSendMail() {
		return sendMail;
	}

	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
	}

	public Integer getTadaTimeBoundPast() {
		return tadaTimeBoundPast;
	}

	public void setTadaTimeBoundPast(Integer tadaTimeBoundPast) {
		this.tadaTimeBoundPast = tadaTimeBoundPast;
	}

	public Integer getTadaTimeBoundFuture() {
		return tadaTimeBoundFuture;
	}

	public void setTadaTimeBoundFuture(Integer tadaTimeBoundFuture) {
		this.tadaTimeBoundFuture = tadaTimeBoundFuture;
	}

	public String getItphysicallyHandicapped() {
		return itphysicallyHandicapped;
	}

	public void setItphysicallyHandicapped(String itphysicallyHandicapped) {
		this.itphysicallyHandicapped = itphysicallyHandicapped;
	}

	public String getTutionFeeMaxLimitPerOneChild() {
		return tutionFeeMaxLimitPerOneChild;
	}

	public void setTutionFeeMaxLimitPerOneChild(String tutionFeeMaxLimitPerOneChild) {
		this.tutionFeeMaxLimitPerOneChild = tutionFeeMaxLimitPerOneChild;
	}

	public String getTutionFeeMaxChildToBeClaimed() {
		return tutionFeeMaxChildToBeClaimed;
	}

	public void setTutionFeeMaxChildToBeClaimed(String tutionFeeMaxChildToBeClaimed) {
		this.tutionFeeMaxChildToBeClaimed = tutionFeeMaxChildToBeClaimed;
	}

	public String getItTraAllw() {
		return itTraAllw;
	}

	public void setItTraAllw(String itTraAllw) {
		this.itTraAllw = itTraAllw;
	}

	public String getSrCitizemMaxAge() {
		return srCitizemMaxAge;
	}

	public void setSrCitizemMaxAge(String srCitizemMaxAge) {
		this.srCitizemMaxAge = srCitizemMaxAge;
	}

	public String getLtcDaPercengate() {
		return ltcDaPercengate;
	}

	public void setLtcDaPercengate(String ltcDaPercengate) {
		this.ltcDaPercengate = ltcDaPercengate;
	}

	public String getLtcAdvancePriorDays() {
		return ltcAdvancePriorDays;
	}

	public void setLtcAdvancePriorDays(String ltcAdvancePriorDays) {
		this.ltcAdvancePriorDays = ltcAdvancePriorDays;
	}

	public String getLtcAdvancePercentage() {
		return ltcAdvancePercentage;
	}

	public void setLtcAdvancePercentage(String ltcAdvancePercentage) {
		this.ltcAdvancePercentage = ltcAdvancePercentage;
	}

	public String getLtcMajorAgeLimit() {
		return ltcMajorAgeLimit;
	}

	public void setLtcMajorAgeLimit(String ltcMajorAgeLimit) {
		this.ltcMajorAgeLimit = ltcMajorAgeLimit;
	}

	public Float getRent() {
		return rent;
	}

	public void setRent(Float rent) {
		this.rent = rent;
	}

	public Float getWater() {
		return water;
	}

	public void setWater(Float water) {
		this.water = water;
	}

	public Float getElectricity() {
		return electricity;
	}

	public void setElectricity(Float electricity) {
		this.electricity = electricity;
	}

	public Float getFurniture() {
		return furniture;
	}

	public void setFurniture(Float furniture) {
		this.furniture = furniture;
	}

	public String getCghsAdvApprPerc() {
		return cghsAdvApprPerc;
	}

	public void setCghsAdvApprPerc(String cghsAdvApprPerc) {
		this.cghsAdvApprPerc = cghsAdvApprPerc;
	}

	public String getSpecialAllowance() {
		return specialAllowance;
	}

	public void setSpecialAllowance(String specialAllowance) {
		this.specialAllowance = specialAllowance;
	}

	public String getWashAllowance() {
		return washAllowance;
	}

	public void setWashAllowance(String washAllowance) {
		this.washAllowance = washAllowance;
	}

	public String getXeroxAllowance() {
		return xeroxAllowance;
	}

	public void setXeroxAllowance(String xeroxAllowance) {
		this.xeroxAllowance = xeroxAllowance;
	}

	public String getWfundOfficers() {
		return wfundOfficers;
	}

	public void setWfundOfficers(String wfundOfficers) {
		this.wfundOfficers = wfundOfficers;
	}

	public String getWfundStaff() {
		return wfundStaff;
	}

	public void setWfundStaff(String wfundStaff) {
		this.wfundStaff = wfundStaff;
	}

	public String getMessOfficers() {
		return messOfficers;
	}

	public void setMessOfficers(String messOfficers) {
		this.messOfficers = messOfficers;
	}

	public String getMessOutside() {
		return messOutside;
	}

	public void setMessOutside(String messOutside) {
		this.messOutside = messOutside;
	}

	public String getBfundOfficers() {
		return bfundOfficers;
	}

	public void setBfundOfficers(String bfundOfficers) {
		this.bfundOfficers = bfundOfficers;
	}

	public String getBfundStaff() {
		return bfundStaff;
	}

	public void setBfundStaff(String bfundStaff) {
		this.bfundStaff = bfundStaff;
	}

	public String getRegFundOfficers() {
		return regFundOfficers;
	}

	public void setRegFundOfficers(String regFundOfficers) {
		this.regFundOfficers = regFundOfficers;
	}

	public String getRegFundStaff() {
		return regFundStaff;
	}

	public void setRegFundStaff(String regFundStaff) {
		this.regFundStaff = regFundStaff;
	}

	
    public String getLtcSonAgeLimit() {
		return ltcSonAgeLimit;
	}

	public void setLtcSonAgeLimit(String ltcSonAgeLimit) {
		this.ltcSonAgeLimit = ltcSonAgeLimit;
	}

	public List getCmlWoMcExceptionIdList() {
		return cmlWoMcExceptionIdList;
	}

	public void setCmlWoMcExceptionIdList(List cmlWoMcExceptionIdList) {
		this.cmlWoMcExceptionIdList = cmlWoMcExceptionIdList;
	}

	public List getLndWoMcExceptionIdList() {
		return lndWoMcExceptionIdList;
	}

	public void setLndWoMcExceptionIdList(List lndWoMcExceptionIdList) {
		this.lndWoMcExceptionIdList = lndWoMcExceptionIdList;
	}

	public String getLtcSeniorrWorkExp() {
		return ltcSeniorrWorkExp;
	}

	public void setLtcSeniorrWorkExp(String ltcSeniorrWorkExp) {
		this.ltcSeniorrWorkExp = ltcSeniorrWorkExp;
	}

	public String getCmlWoMcExceptionId() {
		return cmlWoMcExceptionId;
	}

	public void setCmlWoMcExceptionId(String cmlWoMcExceptionId) {
		this.cmlWoMcExceptionId = cmlWoMcExceptionId;
	}

	public String getLtcJuniorWorkExp() {
		return ltcJuniorWorkExp;
	}

	public void setLtcJuniorWorkExp(String ltcJuniorWorkExp) {
		this.ltcJuniorWorkExp = ltcJuniorWorkExp;
	}

	public String getDashboardRecords() {
		return dashboardRecords;
	}

	public void setDashboardRecords(String dashboardRecords) {
		this.dashboardRecords = dashboardRecords;
	}

	public String getLndWoMcExceptionId() {
		return lndWoMcExceptionId;
	}

	public void setLndWoMcExceptionId(String lndWoMcExceptionId) {
		this.lndWoMcExceptionId = lndWoMcExceptionId;
	}

	public String getLtcPriorDob() {
		return ltcPriorDob;
	}

	public void setLtcPriorDob(String ltcPriorDob) {
		this.ltcPriorDob = ltcPriorDob;
	}

	public String getMyRequestDashboardRecords() {
		return myRequestDashboardRecords;
	}

	public void setMyRequestDashboardRecords(String myRequestDashboardRecords) {
		this.myRequestDashboardRecords = myRequestDashboardRecords;
	}

	public String getCghsMajorAgeLimit() {
		return cghsMajorAgeLimit;
	}

	public void setCghsMajorAgeLimit(String cghsMajorAgeLimit) {
		this.cghsMajorAgeLimit = cghsMajorAgeLimit;
	}

	public String getCghsSonAgeLimit() {
		return cghsSonAgeLimit;
	}

	public void setCghsSonAgeLimit(String cghsSonAgeLimit) {
		this.cghsSonAgeLimit = cghsSonAgeLimit;
	}

	public String getCghsSalaryLimit() {
		return cghsSalaryLimit;
	}

	public void setCghsSalaryLimit(String cghsSalaryLimit) {
		this.cghsSalaryLimit = cghsSalaryLimit;
	}

	public String getLeaveStartDate() {
		return leaveStartDate;
	}

	public void setLeaveStartDate(String leaveStartDate) {
		this.leaveStartDate = leaveStartDate;
	}

	public String getMaxLTCEncasementToEL() {
        return maxLTCEncasementToEL;
    }

    public void setMaxLTCEncasementToEL(String maxLTCEncasementToEL) {
        this.maxLTCEncasementToEL = maxLTCEncasementToEL;
    }

    public String getMinELAvailAftLTCEncashment() {
        return minELAvailAftLTCEncashment;
    }

    public void setMinELAvailAftLTCEncashment(String minELAvailAftLTCEncashment) {
        this.minELAvailAftLTCEncashment = minELAvailAftLTCEncashment;
    }

    public String getMaxLTCEncashmentToELService() {
        return maxLTCEncashmentToELService;
    }

    public void setMaxLTCEncashmentToELService(String maxLTCEncashmentToELService) {
        this.maxLTCEncashmentToELService = maxLTCEncashmentToELService;
    }

    public String getCommLeaveAvailELAvail() {
        return commLeaveAvailELAvail;
    }

    public void setCommLeaveAvailELAvail(String commLeaveAvailELAvail) {
        this.commLeaveAvailELAvail = commLeaveAvailELAvail;
    }

    public String getCommLeaveAvailELAvailDesc() {
        return commLeaveAvailELAvailDesc;
    }

    public void setCommLeaveAvailELAvailDesc(String commLeaveAvailELAvailDesc) {
        this.commLeaveAvailELAvailDesc = commLeaveAvailELAvailDesc;
    }

    public String getMaternityLeaveAvailFor() {
        return maternityLeaveAvailFor;
    }

    public void setMaternityLeaveAvailFor(String maternityLeaveAvailFor) {
        this.maternityLeaveAvailFor = maternityLeaveAvailFor;
    }

    public String getMaxLeaveConversion() {
        return maxLeaveConversion;
    }

    public void setMaxLeaveConversion(String maxLeaveConversion) {
        this.maxLeaveConversion = maxLeaveConversion;
    }

    public String getPeternityLeaveAvailFor() {
        return peternityLeaveAvailFor;
    }

    public void setPeternityLeaveAvailFor(String peternityLeaveAvailFor) {
        this.peternityLeaveAvailFor = peternityLeaveAvailFor;
    }

    private String peternityLeaveAvailFor;

    public String getMinAgeForJob() {
        return minAgeForJob;
    }

    public void setMinAgeForJob(String minAgeForJob) {
        this.minAgeForJob = minAgeForJob;
    }

    public String getEscalationtime() {
        return escalationtime;
    }

    public void setEscalationtime(String escalationtime) {
        this.escalationtime = escalationtime;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List getInstanceList() {
        return instanceList;
    }

    public void setInstanceList(List instanceList) {
        this.instanceList = instanceList;
    }

    public String getConfigurationDetails() {
        return configurationDetails;
    }

    public void setConfigurationDetails(String configurationDetails) {
        this.configurationDetails = configurationDetails;
    }

	public String getAuthorityType() {
		return authorityType;
	}

	public void setAuthorityType(String authorityType) {
		this.authorityType = authorityType;
	}

	public String getAuthorityEmpName() {
		return authorityEmpName;
	}

	public void setAuthorityEmpName(String authorityEmpName) {
		this.authorityEmpName = authorityEmpName;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public List<SigningAuthorityDTO> getSigningAuthorityList() {
		return signingAuthorityList;
	}

	public void setSigningAuthorityList(
			List<SigningAuthorityDTO> signingAuthorityList) {
		this.signingAuthorityList = signingAuthorityList;
	}

}
