package com.callippus.web.promotions.dto;


import com.callippus.web.beans.dto.CommonDTO;

public class PromotionsSubDisciplineDTO extends CommonDTO{
	private int id;
	private int disciplineID;
	private String description;
	private String shortForm;
	private PromotionsDisciplineDTO disciplineDetails;
	
	
	public String getShortForm() {
		return shortForm;
	}
	public void setShortForm(String shortForm) {
		this.shortForm = shortForm;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDisciplineID() {
		return disciplineID;
	}
	public void setDisciplineID(int disciplineID) {
		this.disciplineID = disciplineID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public PromotionsDisciplineDTO getDisciplineDetails() {
		return disciplineDetails;
	}
	public void setDisciplineDetails(PromotionsDisciplineDTO disciplineDetails) {
		this.disciplineDetails = disciplineDetails;
	}

}
