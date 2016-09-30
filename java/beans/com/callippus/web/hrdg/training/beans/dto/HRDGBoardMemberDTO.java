package com.callippus.web.hrdg.training.beans.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class HRDGBoardMemberDTO extends CommonDTO{
	private String sfid;
	private String year;
	private Integer boardId;
	private Integer memberTypeId;
	private String board;
	private String memberType;
	
	public String getSfid() {
		return sfid;
	}
	public void setSfid(String sfid) {
		this.sfid = sfid;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Integer getBoardId() {
		return boardId;
	}
	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}
	public Integer getMemberTypeId() {
		return memberTypeId;
	}
	public void setMemberTypeId(Integer memberTypeId) {
		this.memberTypeId = memberTypeId;
	}
	public String getBoard() {
		return board;
	}
	public void setBoard(String board) {
		this.board = board;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	
	
	
	

}
