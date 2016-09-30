package com.callippus.web.mmg.cashbuildup.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;


public class InventoryMasterDTO extends CommonDTO{
		private String invId;
		private String inventoryNo;
		private String sfid;
		private String demandPurchaseLimit;
		private String inventoryHolderType;
		private String inventoryHolderTypeName;
		private String holderName;
		private String directorateName;
		private String divisionName;
		private Date createDate;
		private String demandNo;
		private String divisionId;		
		private String designation;
		private String phone;
		private String orgRoleId;
		private String deptName;
		private String projectName;
		
		
		public String getProjectName() {
			return projectName;
		}
		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}
		public String getDeptName() {
			return deptName;
		}
		public void setDeptName(String deptName) {
			this.deptName = deptName;
		}
		public String getOrgRoleId() {
			return orgRoleId;
		}
		public void setOrgRoleId(String orgRoleId) {
			this.orgRoleId = orgRoleId;
		}
		public String getInvId() {
			return invId;
		}
		public void setInvId(String invId) {
			this.invId = invId;
		}
		public String getInventoryHolderTypeName() {
			return inventoryHolderTypeName;
		}
		public void setInventoryHolderTypeName(String inventoryHolderTypeName) {
			this.inventoryHolderTypeName = inventoryHolderTypeName;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getDesignation() {
			return designation;
		}
		public void setDesignation(String designation) {
			this.designation = designation;
		}
		public String getDivisionId() {
			return divisionId;
		}
		public void setDivisionId(String divisionId) {
			this.divisionId = divisionId;
		}
		public String getDemandNo() {
			return demandNo;
		}
		public void setDemandNo(String demandNo) {
			this.demandNo = demandNo;
		}
		public Date getCreateDate() {
			return createDate;
		}
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
		public String getHolderName() {
			return holderName;
		}
		public void setHolderName(String holderName) {
			this.holderName = holderName;
		}
		public String getDirectorateName() {
			return directorateName;
		}
		public void setDirectorateName(String directorateName) {
			this.directorateName = directorateName;
		}
		public String getDivisionName() {
			return divisionName;
		}
		public void setDivisionName(String divisionName) {
			this.divisionName = divisionName;
		}
		public String getInventoryNo() {
			return inventoryNo;
		}
		public void setInventoryNo(String inventoryNo) {
			this.inventoryNo = inventoryNo;
		}
		public String getSfid() {
			return sfid;
		}
		public void setSfid(String sfid) {
			this.sfid = sfid;
		}
		public String getDemandPurchaseLimit() {
			return demandPurchaseLimit;
		}
		public void setDemandPurchaseLimit(String demandPurchaseLimit) {
			this.demandPurchaseLimit = demandPurchaseLimit;
		}
		public String getInventoryHolderType() {
			return inventoryHolderType;
		}
		public void setInventoryHolderType(String inventoryHolderType) {
			this.inventoryHolderType = inventoryHolderType;
		}
				
}
