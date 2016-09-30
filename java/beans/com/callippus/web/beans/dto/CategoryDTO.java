package com.callippus.web.beans.dto;

public class CategoryDTO extends CommonDTO {
	private String subCategoryID;
	private String categoryName;
	private String subCategoryName;
	private String categoryID;
	private String parentID;

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public String getSubCategoryID() {
		return subCategoryID;
	}

	public void setSubCategoryID(String subCategoryID) {
		this.subCategoryID = subCategoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

}
