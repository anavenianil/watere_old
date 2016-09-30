package com.callippus.web.mmg.cashbuildup.dto;

import com.callippus.web.beans.dto.CommonDTO;


public class ItemSubCodeDTO extends CommonDTO{
		private String itemCodeId;
		private String itemSubCode;
		private String itemCodeName;
		
		
		public String getItemCodeName() {
			return itemCodeName;
		}

		public void setItemCodeName(String itemCodeName) {
			this.itemCodeName = itemCodeName;
		}

		public String getItemSubCode() {
			return itemSubCode;
		}

		public void setItemSubCode(String itemSubCode) {
			this.itemSubCode = itemSubCode;
		}

		public String getItemCodeId() {
			return itemCodeId;
		}

		public void setItemCodeId(String itemCodeId) {
			this.itemCodeId = itemCodeId;
		}
				
		
}
