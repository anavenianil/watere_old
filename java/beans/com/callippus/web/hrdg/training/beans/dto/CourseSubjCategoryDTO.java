package com.callippus.web.hrdg.training.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class CourseSubjCategoryDTO extends CommonDTO{
	int categoryId;
	String category;
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	

}
