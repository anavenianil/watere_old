package com.callippus.web.loan.beans.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.FamilyRelationDTO;

public class LoanReliefFund extends CommonDTO {
	private String sfID;
	private Date dateOfDeath;
	private int relationshipID;
	private float advance;
	private String approvedBy;
	private String nameOfApplicant;
	private String witness1;
	private String witness2;
	private String address;
	private FamilyRelationDTO relationDetails;

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public float getAdvance() {
		return advance;
	}

	public void setAdvance(float advance) {
		this.advance = advance;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getNameOfApplicant() {
		return nameOfApplicant;
	}

	public void setNameOfApplicant(String nameOfApplicant) {
		this.nameOfApplicant = nameOfApplicant;
	}

	public int getRelationshipID() {
		return relationshipID;
	}

	public void setRelationshipID(int relationshipID) {
		this.relationshipID = relationshipID;
	}

	public void setWitness1(String witness1) {
		this.witness1 = witness1;
	}

	public String getWitness2() {
		return witness2;
	}

	public void setWitness2(String witness2) {
		this.witness2 = witness2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWitness1() {
		return witness1;
	}

	public Date getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public FamilyRelationDTO getRelationDetails() {
		return relationDetails;
	}

	public void setRelationDetails(FamilyRelationDTO relationDetails) {
		this.relationDetails = relationDetails;
	}

}
