package com.callippus.web.mmg.cashbuildup.dto;

import com.callippus.web.beans.dto.CommonDTO;


public class ItemSubCategoryDTO extends CommonDTO{
	private String categoryId;
	private String subCategoryCode;
	private String categoryName;
	
		
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getSubCategoryCode() {
		return subCategoryCode;
	}
	public void setSubCategoryCode(String subCategoryCode) {
		this.subCategoryCode = subCategoryCode;
	}
	
}
