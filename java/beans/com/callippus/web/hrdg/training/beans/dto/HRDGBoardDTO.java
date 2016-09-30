package com.callippus.web.hrdg.training.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class HRDGBoardDTO extends CommonDTO{
	private Integer boardTypeId;
	private String boardType;
	private String yearId;
	private String year;
	public String getYearId() {
		return yearId;
	}

	public void setYearId(String yearId) {
		this.yearId = yearId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public Integer getBoardTypeId() {
		return boardTypeId;
	}

	public void setBoardTypeId(Integer boardTypeId) {
		this.boardTypeId = boardTypeId;
	}
	
	

	
	
	

}
