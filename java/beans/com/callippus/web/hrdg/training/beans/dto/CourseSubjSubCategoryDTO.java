package com.callippus.web.hrdg.training.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class CourseSubjSubCategoryDTO extends CommonDTO{
	int categoryId;
	String category;
	int courseSubjCategoryId;
	String courseSubjCategory;
	
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
	public int getCourseSubjCategoryId() {
		return courseSubjCategoryId;
	}
	public void setCourseSubjCategoryId(int courseSubjCategoryId) {
		this.courseSubjCategoryId = courseSubjCategoryId;
	}
	public String getCourseSubjCategory() {
		return courseSubjCategory;
	}
	public void setCourseSubjCategory(String courseSubjCategory) {
		this.courseSubjCategory = courseSubjCategory;
	}
	
	
	

}
