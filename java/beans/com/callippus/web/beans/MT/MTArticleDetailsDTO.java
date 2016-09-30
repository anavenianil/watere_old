package com.callippus.web.beans.MT;


public class MTArticleDetailsDTO {
	
		private int id;
		private int referenceID;
		private String type;
		private int length;
		private int breadth;
		private int height;
		private int weight;
		private int quantity;
		private String journeyTypeFlag;
		private int status;
		
		private String message;
		

		

		public int getStatus() {
			return status;
		}


		public void setStatus(int status) {
			this.status = status;
		}


		public String getType() {
			return type;
		}


		public void setType(String type) {
			this.type = type;
		}


		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public int getReferenceID() {
			return referenceID;
		}


		public void setReferenceID(int referenceID) {
			this.referenceID = referenceID;
		}


		public int getLength() {
			return length;
		}


		public void setLength(int length) {
			this.length = length;
		}


		public int getBreadth() {
			return breadth;
		}


		public void setBreadth(int breadth) {
			this.breadth = breadth;
		}


		public int getHeight() {
			return height;
		}


		public void setHeight(int height) {
			this.height = height;
		}


		public int getWeight() {
			return weight;
		}


		public void setWeight(int weight) {
			this.weight = weight;
		}


		public int getQuantity() {
			return quantity;
		}


		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}


		public String getJourneyTypeFlag() {
			return journeyTypeFlag;
		}


		public void setJourneyTypeFlag(String journeyTypeFlag) {
			this.journeyTypeFlag = journeyTypeFlag;
		}


		public String getMessage() {
			return message;
		}


		public void setMessage(String message) {
			this.message = message;
		}
	    
		
		
}