package com.callippus.web.business.holidays;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.holidays.HolidaysBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.holidays.IHolidaysDAO;

@Service
public class HolidaysBusiness {
	@Autowired
	private IHolidaysDAO holidaysDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	@SuppressWarnings("unchecked")
	public HolidaysBean getHolidaysHomeDetails(HolidaysBean holidayBean) throws Exception {
		try {
			holidayBean.setHolidaysList(holidaysDAO.getHolidaysList(holidayBean.getYear()));
			holidayBean.setYearsList(commonDataDAO.getMasterData(CPSConstants.YEARTYPEDTO));
		} catch (Exception e) {
			throw e;
		}
		return holidayBean;
	}

	public List<HolidaysBean> getHolidaysDetails(String year) throws Exception {
		List<HolidaysBean> holidaysList = null;
		try {
			holidaysList = holidaysDAO.getHolidaysList(year);
		} catch (Exception e) {
			throw e;
		}
		return holidaysList;
	}

	public HolidaysBean deleteHolidaysDetails(HolidaysBean holidayBean) throws Exception {
		try {
			holidayBean.setStatus(0);
			holidayBean.setMessage(holidaysDAO.saveHolidaysDetails(holidayBean));
			holidaysDAO.changeCalendarScript(holidayBean.getRealPath());
		} catch (Exception e) {
			throw e;
		}
		return holidayBean;
	}

	public HolidaysBean manageHolidaysDetails(HolidaysBean holidayBean) throws Exception {
		try {
			holidayBean.setStatus(1);
			holidayBean.setMessage(holidaysDAO.saveHolidaysDetails(holidayBean));
			holidaysDAO.changeCalendarScript(holidayBean.getRealPath());
		} catch (Exception e) {
			throw e;
		}
		return holidayBean;
	}
}
