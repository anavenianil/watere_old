package com.callippus.web.tada.dto;

import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;

public class CityTypeDTO extends CommonDTO{
	
	private String cityClass;
	private String cityName;
	private List<CityTypeDTO> uniqueCityTypeList;
	private int id1;
	
	
	public List<CityTypeDTO> getUniqueCityTypeList() {
		return uniqueCityTypeList;
	}
	public void setUniqueCityTypeList(List<CityTypeDTO> uniqueCityTypeList) {
		this.uniqueCityTypeList = uniqueCityTypeList;
	}
	public int getId1() {
		return id1;
	}
	public void setId1(int id1) {
		this.id1 = id1;
	}
	public String getCityClass() {
		return cityClass;
	}
	public void setCityClass(String cityClass) {
		this.cityClass = cityClass;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
