package com.callippus.web.controller.holidays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.holidays.HolidaysBean;
import com.callippus.web.business.holidays.HolidaysBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/holidays.htm")
@SessionAttributes
public class HolidaysController {

	private static Log log = LogFactory.getLog(HolidaysController.class);
	@Autowired
	private HolidaysBusiness holidaysBusiness;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.HOLIDAYS) HolidaysBean holidayBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		String viewName = null;
		HttpSession session = null;
		try {
			ErpAction.userChecks(request);
			
			session = request.getSession(true);
			if (CPSUtils.compareStrings(CPSConstants.PAGING, holidayBean.getParam())) {
//				holidayBean.setResult(session.getAttribute(CPSConstants.RESULT).toString());
				if (Integer.valueOf(holidayBean.getYear()) >= Integer.valueOf(CPSUtils.currentYear())) {
					holidayBean.setIsEditable(true);
				}
				viewName = CPSConstants.HOLIDAYLIST;
			} else if (CPSUtils.isNullOrEmpty(holidayBean.getParam())) {
				holidayBean.setParam(CPSConstants.HOLIDAYHOME);
			}

			if (CPSUtils.isNullOrEmpty(holidayBean.getSfID())) {
				holidayBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			}

			if (CPSUtils.compareStrings(CPSConstants.HOLIDAYHOME, holidayBean.getParam())) {
				holidayBean.setYear(CPSUtils.currentYear());
				holidayBean = holidaysBusiness.getHolidaysHomeDetails(holidayBean);
				session.setAttribute("holidaysList", holidayBean.getHolidaysList());
				holidayBean.setIsEditable(true);
				viewName = CPSConstants.HOLIDAYSMASTER;
			} else if (CPSUtils.compareStrings(CPSConstants.GETLIST, holidayBean.getParam())) {
				log.debug("HolidaysController --> onSubmit --> param=getList");
				session.setAttribute("holidaysList", holidaysBusiness.getHolidaysDetails(holidayBean.getYear()));

				if (Integer.valueOf(holidayBean.getYear()) >= Integer.valueOf(CPSUtils.currentYear())) {
					holidayBean.setIsEditable(true);
				}
				viewName = CPSConstants.HOLIDAYLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.DELETEHOLIDAYS, holidayBean.getParam())) {
				log.debug("HolidaysController --> onSubmit --> param=deleteHoliday");
				holidayBean.setRealPath(session.getServletContext().getResource("/script").getPath());
				holidayBean = holidaysBusiness.deleteHolidaysDetails(holidayBean);
				session.setAttribute("holidaysList", holidaysBusiness.getHolidaysDetails(holidayBean.getYear()));
				if (Integer.valueOf(holidayBean.getYear()) >= Integer.valueOf(CPSUtils.currentYear())) {
					holidayBean.setIsEditable(true);
				}
				viewName = CPSConstants.HOLIDAYLIST;
			} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, holidayBean.getParam())) {
				log.debug("HolidaysController --> onSubmit --> param=manage");
				if (Integer.valueOf(holidayBean.getYear()) >= Integer.valueOf(CPSUtils.currentYear())) {
					holidayBean.setRealPath(session.getServletContext().getResource("/script").getPath());
					holidayBean = holidaysBusiness.manageHolidaysDetails(holidayBean);
				} else {
					holidayBean.setIsEditable(false);
				}
				session.setAttribute("holidaysList", holidaysBusiness.getHolidaysDetails(holidayBean.getYear()));
				if (Integer.valueOf(holidayBean.getYear()) >= Integer.valueOf(CPSUtils.currentYear())) {
					holidayBean.setIsEditable(true);
				}
				viewName = CPSConstants.HOLIDAYLIST;
			}

			mav = new ModelAndView(viewName, CPSConstants.HOLIDAYS, holidayBean);
			if (!CPSUtils.isNullOrEmpty(holidayBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, holidayBean.getMessage());
			}
			if (!CPSUtils.isNullOrEmpty(holidayBean.getResult())) {
				mav.addObject(CPSConstants.RESULT, holidayBean.getResult());
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}