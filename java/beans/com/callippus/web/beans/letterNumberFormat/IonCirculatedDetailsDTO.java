package com.callippus.web.beans.letterNumberFormat;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.Hibernate;

public class IonCirculatedDetailsDTO implements Serializable{
	private int id;
	
	private Integer ionId;
	private String sfid;
	private String refOrgRoleId;
	
	private String referenceSfid;
	private String forwardOrReplayOrFile;
	private String remarks;
	
	private int status;
	private String creationDate;
	private String lastModifiedDate;
	private String createdBy;
	private String lastModifiedBy;
	private String forwarded;
	private String replied;
	private String filed;
	private String forwardedRemarks;
	private String subject;
	private String forwardedSfid;
	
	public String getForwardedSfid() {
		return forwardedSfid;
	}
	public void setForwardedSfid(String forwardedSfid) {
		this.forwardedSfid = forwardedSfid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getIonId() {
		return ionId;
	}
	public void setIonId(Integer ionId) {
		this.ionId = ionId;
	}
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getReferenceSfid() {
		return referenceSfid;
	}
	public void setReferenceSfid(String referenceSfid) {
		this.referenceSfid = referenceSfid;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public String getForwardOrReplayOrFile() {
		return forwardOrReplayOrFile;
	}
	public void setForwardOrReplayOrFile(String forwardOrReplayOrFile) {
		this.forwardOrReplayOrFile = forwardOrReplayOrFile;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getForwarded() {
		return forwarded;
	}
	public void setForwarded(String forwarded) {
		this.forwarded = forwarded;
	}
	public String getReplied() {
		return replied;
	}
	public void setReplied(String replied) {
		this.replied = replied;
	}
	public String getFiled() {
		return filed;
	}
	public void setFiled(String filed) {
		this.filed = filed;
	}
	public String getForwardedRemarks() {
		return forwardedRemarks;
	}
	public void setForwardedRemarks(String forwardedRemarks) {
		this.forwardedRemarks = forwardedRemarks;
	}
	public String getRefOrgRoleId() {
		return refOrgRoleId;
	}
	public void setRefOrgRoleId(String refOrgRoleId) {
		this.refOrgRoleId = refOrgRoleId;
	}
	

	
	

}
