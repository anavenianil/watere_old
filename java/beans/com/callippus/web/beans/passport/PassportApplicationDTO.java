package com.callippus.web.beans.passport;

import com.callippus.web.beans.dto.CommonDTO;


public class PassportApplicationDTO extends CommonDTO{


	private String sfID;
	private String passportAppType;
	private String letterDate;
	private String adminNoteDt;
	private String receivedDt;
	private String letterDesc;
	private String adminNoteNo;
	private String idCertificateNo;
	private String idCertificateDt;
	
	
	
	

    
	public String getIdCertificateNo() {
		return idCertificateNo;
	}
	public void setIdCertificateNo(String idCertificateNo) {
		this.idCertificateNo = idCertificateNo;
	}
	public String getIdCertificateDt() {
		return idCertificateDt;
	}
	public void setIdCertificateDt(String idCertificateDt) {
		this.idCertificateDt = idCertificateDt;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public String getLetterDate() {
		return letterDate;
	}
	public void setLetterDate(String letterDate) {
		this.letterDate = letterDate;
	}
	public String getAdminNoteDt() {
		return adminNoteDt;
	}
	public void setAdminNoteDt(String adminNoteDt) {
		this.adminNoteDt = adminNoteDt;
	}
	public String getReceivedDt() {
		return receivedDt;
	}
	public void setReceivedDt(String receivedDt) {
		this.receivedDt = receivedDt;
	}
	public String getLetterDesc() {
		return letterDesc;
	}
	public void setLetterDesc(String letterDesc) {
		this.letterDesc = letterDesc;
	}
	
	public String getPassportAppType() {
		return passportAppType;
	}
	public void setPassportAppType(String passportAppType) {
		this.passportAppType = passportAppType;
	}
	public String getAdminNoteNo() {
		return adminNoteNo;
	}
	public void setAdminNoteNo(String adminNoteNo) {
		this.adminNoteNo = adminNoteNo;
	}
	
	
	

	
}
