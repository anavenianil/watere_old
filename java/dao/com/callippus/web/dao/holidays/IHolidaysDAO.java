package com.callippus.web.dao.holidays;

import java.util.List;

import com.callippus.web.beans.holidays.HolidaysBean;

public interface IHolidaysDAO {
	public List<HolidaysBean> getHolidaysList(String year) throws Exception;

	public String saveHolidaysDetails(HolidaysBean holidaysBean) throws Exception;

	public void changeCalendarScript(String realPath) throws Exception;
}
