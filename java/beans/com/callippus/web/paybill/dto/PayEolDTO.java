package com.callippus.web.paybill.dto;



import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class PayEolDTO extends CommonDTO{
	
	public String sfid;
	//changed to date as per db. by naagendra.v
	public Date runMonth;
	public int requestID;
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	
	public Date getRunMonth() {
		return runMonth;
	}
	public void setRunMonth(java.util.Date runmonth2) {
		this.runMonth = runmonth2;
	}
	public int getRequestID() {
		return requestID;
	}
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

}
