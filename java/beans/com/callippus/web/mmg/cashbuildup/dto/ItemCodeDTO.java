package com.callippus.web.mmg.cashbuildup.dto;

import com.callippus.web.beans.dto.CommonDTO;


public class ItemCodeDTO extends CommonDTO{
		private String itemCode;
		private String categoryId;
		private String subCategoryId;
		private String categoryName;
		private String subCategoryName;
		
		
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
		public String getItemCode() {
			return itemCode;
		}
		public void setItemCode(String itemCode) {
			this.itemCode = itemCode;
		}
		public String getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(String categoryId) {
			this.categoryId = categoryId;
		}
		public String getSubCategoryId() {
			return subCategoryId;
		}
		public void setSubCategoryId(String subCategoryId) {
			this.subCategoryId = subCategoryId;
		}
		
		
}
