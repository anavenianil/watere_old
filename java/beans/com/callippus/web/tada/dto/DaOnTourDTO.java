package com.callippus.web.tada.dto;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

 @Entity
 @Table(name="TADA_DA_ON_TOUR_MASTER")

public class DaOnTourDTO {
	 
	 private Integer id;
	 private Float daRangeFrom; 
	 private Float daRangeTo;
	 private Float daOnTour;
	 private Integer status;
     private String creationDate;
	 private String lastModifiedDate;
	 private String createdBy;
	 private String lastModifiedBy;
	
	
	 
	 @Id
     @SequenceGenerator(name="DaOnTourSeq",sequenceName="TADA_DA_ON_TOUR_MASTER_SEQ")
	 @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="DaOnTourSeq")
	 @Column(name="ID")
	 public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
	
	@Column(name="DA_RANGE_FROM", nullable=true)
	public Float getDaRangeFrom() {
		return daRangeFrom;
	}
	public void setDaRangeFrom(Float daRangeFrom) {
		this.daRangeFrom = daRangeFrom;
	}
	
	@Column(name="DA_RANGE_TO", nullable=true)
	public Float getDaRangeTo() {
		return daRangeTo;
	}
	public void setDaRangeTo(Float daRangeTo) {
		this.daRangeTo = daRangeTo;
	}
	
	@Column(name="DA_ON_TOUR", nullable=true)
	public Float getDaOnTour() {
		return daOnTour;
	}
	public void setDaOnTour(Float daOnTour) {
		this.daOnTour = daOnTour;
	}
	
	@Column(name="STATUS", nullable=true)
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="CREATION_DATE", nullable=true)
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	@Column(name="LAST_MODIFIED_DATE", nullable=true)
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	@Column(name="CREATED_BY", nullable=true)
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name="LAST_MODIFIED_BY", nullable=true)
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
		
	
	

}
