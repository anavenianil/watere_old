package com.callippus.web.promotions.dto;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.YearTypeDTO;

public class BoardMappingDTO extends CommonDTO {
	private YearTypeDTO yearDetails;
	private int yearID;
	private BoardMasterDTO boardDetails;
	private int boardID;
	private String boardMember;
	private String employees;
	private String boardName;
	private String members;
	private String year;
	private int categoryId;
	private String categoryName;

	
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	public String getEmployees() {
		return employees;
	}

	public void setEmployees(String employees) {
		this.employees = employees;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public YearTypeDTO getYearDetails() {
		return yearDetails;
	}

	public void setYearDetails(YearTypeDTO yearDetails) {
		this.yearDetails = yearDetails;
	}

	public int getYearID() {
		return yearID;
	}

	public void setYearID(int yearID) {
		this.yearID = yearID;
	}

	public BoardMasterDTO getBoardDetails() {
		return boardDetails;
	}

	public void setBoardDetails(BoardMasterDTO boardDetails) {
		this.boardDetails = boardDetails;
	}

	public int getBoardID() {
		return boardID;
	}

	public void setBoardID(int boardID) {
		this.boardID = boardID;
	}

	public String getBoardMember() {
		return boardMember;
	}

	public void setBoardMember(String boardMember) {
		this.boardMember = boardMember;
	}

}
