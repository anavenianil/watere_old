package com.callippus.web.ltc.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class LtcBlockYearsMasterDTO extends CommonDTO{
	private String ltcBlockId;
	private String fromDate;
	private String toDate;
	private String extendedDate;
	private String fromDateOne;
	private String toDateOne;
	private String extendedDateOne;
	private LtcBlockMasterDTO ltcBlockMaster;
	private String fourYearBlockId;
	
	
	
	
	public String getFourYearBlockId() {
		return fourYearBlockId;
	}
	public void setFourYearBlockId(String fourYearBlockId) {
		this.fourYearBlockId = fourYearBlockId;
	}
	public String getFromDateOne() {
		return fromDateOne;
	}
	public void setFromDateOne(String fromDateOne) {
		this.fromDateOne = fromDateOne;
	}
	public String getToDateOne() {
		return toDateOne;
	}
	public void setToDateOne(String toDateOne) {
		this.toDateOne = toDateOne;
	}
	public String getExtendedDateOne() {
		return extendedDateOne;
	}
	public void setExtendedDateOne(String extendedDateOne) {
		this.extendedDateOne = extendedDateOne;
	}
	public LtcBlockMasterDTO getLtcBlockMaster() {
		return ltcBlockMaster;
	}
	public void setLtcBlockMaster(LtcBlockMasterDTO ltcBlockMaster) {
		this.ltcBlockMaster = ltcBlockMaster;
	}
	public String getLtcBlockId() {
		return ltcBlockId;
	}
	public void setLtcBlockId(String ltcBlockId) {
		this.ltcBlockId = ltcBlockId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getExtendedDate() {
		return extendedDate;
	}
	public void setExtendedDate(String extendedDate) {
		this.extendedDate = extendedDate;
	}
		
}