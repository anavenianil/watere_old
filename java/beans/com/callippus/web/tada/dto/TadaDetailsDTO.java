package com.callippus.web.tada.dto;

import java.util.List;
import java.util.Set;

import com.callippus.web.beans.dto.CommonDTO;

public class TadaDetailsDTO extends CommonDTO{
	private String param;
	private String sfID;
	private String result;
	private int payRangeFrom;
	private int payRangeTo;
	private int ord;
	private int hotel;
	private String type;
	private List<TadaDetailsDTO> gradePayList;
	private String gradePay;
	private String cityType;
	private List<CityTypeDTO> cityTypeList;
	private int cityTypeId;
	
	
	
	
	
	public int getCityTypeId() {
		return cityTypeId;
	}
	public void setCityTypeId(int cityTypeId) {
		this.cityTypeId = cityTypeId;
	}
	public List<CityTypeDTO> getCityTypeList() {
		return cityTypeList;
	}
	public void setCityTypeList(List<CityTypeDTO> cityTypeList) {
		this.cityTypeList = cityTypeList;
	}
	public String getCityType() {
		return cityType;
	}
	public void setCityType(String cityType) {
		this.cityType = cityType;
	}
	public List<TadaDetailsDTO> getGradePayList() {
		return gradePayList;
	}
	public void setGradePayList(List<TadaDetailsDTO> gradePayList) {
		this.gradePayList = gradePayList;
	}
	public String getGradePay() {
		return gradePay;
	}
	public void setGradePay(String gradePay) {
		this.gradePay = gradePay;
	}
	
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getSfID() {
		return sfID;
	}
	public void setSfID(String sfID) {
		this.sfID = sfID;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getPayRangeFrom() {
		return payRangeFrom;
	}
	public void setPayRangeFrom(int payRangeFrom) {
		this.payRangeFrom = payRangeFrom;
	}
	public int getPayRangeTo() {
		return payRangeTo;
	}
	public void setPayRangeTo(int payRangeTo) {
		this.payRangeTo = payRangeTo;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public int getHotel() {
		return hotel;
	}
	public void setHotel(int hotel) {
		this.hotel = hotel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
	
	

}
