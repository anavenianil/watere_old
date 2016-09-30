package com.callippus.web.hindi.dto;

public class HindiEmployeeDTO {
	
		//private int empId;
		private String status;
		private int id;
		
		private String sfid;
		 private String mothertongue;
		 private int mothertongueValue;
		private int qualification;
		private String remarks;
		private int nonEligibleExamId;
		private int eligibleExamId;
		
	    
		
		public int getEligibleExamId() {
			return eligibleExamId;
		}
		public void setEligibleExamId(int eligibleExamId) {
			this.eligibleExamId = eligibleExamId;
		}
		public int getMothertongueValue() {
			return mothertongueValue;
		}
		public void setMothertongueValue(int mothertongueValue) {
			this.mothertongueValue = mothertongueValue;
		}
		public int getNonEligibleExamId() {
			return nonEligibleExamId;
		}
		public void setNonEligibleExamId(int nonEligibleExamId) {
			this.nonEligibleExamId = nonEligibleExamId;
		}
		public String getSfid() {
			return sfid;
		}
		public void setSfid(String sfid) {
			this.sfid = sfid;
		}
		
		
		public String getMothertongue() {
			return mothertongue;
		}
		public void setMothertongue(String mothertongue) {
			this.mothertongue = mothertongue;
		}
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public int getQualification() {
			return qualification;
		}
		public void setQualification(int qualification) {
			this.qualification = qualification;
		}
		
		
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		



	


}
