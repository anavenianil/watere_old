package com.callippus.web.beans.holidays;

import java.util.List;

import com.callippus.web.beans.dto.CommonDTO;
import com.callippus.web.beans.dto.YearTypeDTO;

public class HolidaysBean extends CommonDTO {

	private String param;
	private String type;
	private String holiday;
	private String holidayDate;
	private List<HolidaysBean> HolidaysList;
	private List<YearTypeDTO> yearsList;
	private String year;
	private String result;
	private String pk;
	private String sfID;
	private String message;
	private String realPath;
	private Boolean isEditable;
	
	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSfID() {
		return sfID;
	}

	public void setSfID(String sfID) {
		this.sfID = sfID;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<YearTypeDTO> getYearsList() {
		return yearsList;
	}

	public void setYearsList(List<YearTypeDTO> yearsList) {
		this.yearsList = yearsList;
	}

	public List<HolidaysBean> getHolidaysList() {
		return HolidaysList;
	}

	public void setHolidaysList(List<HolidaysBean> holidaysList) {
		HolidaysList = holidaysList;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}

	public String getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(String holidayDate) {
		this.holidayDate = holidayDate;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	public Boolean getIsEditable() {
		return isEditable;
	}

}
