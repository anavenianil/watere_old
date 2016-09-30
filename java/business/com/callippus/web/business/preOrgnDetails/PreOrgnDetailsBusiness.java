package com.callippus.web.business.preOrgnDetails;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.preOrgnDetails.PreOrgnDetailsBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.preOrgnDetails.IPreOrgnDetailsDAO;

@Service
public class PreOrgnDetailsBusiness {
	@Autowired
	private IPreOrgnDetailsDAO preOrgnDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public String createPreOrgnDetails(PreOrgnDetailsBean preOrgnBean) throws Exception {
		String message = "";
		try {
			if (CPSUtils.isNullOrEmpty(preOrgnBean.getId())) {
				int id = commonDataDAO.getTableID(CPSConstants.ORGANISATION_DETAILS, CPSConstants.UPDATE);
				preOrgnBean.setId(String.valueOf(id));
				message = preOrgnDAO.createPreOrgnDetails(preOrgnBean);
			} else {
				message = preOrgnDAO.updatePreOrgnDetails(preOrgnBean);
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String updatePreOrgnDetails(PreOrgnDetailsBean preOrgnBean) throws Exception {
		String message = "";
		try {
			message = preOrgnDAO.updatePreOrgnDetails(preOrgnBean);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String deletePreOrgnDetails(PreOrgnDetailsBean preOrgnBean) throws Exception {
		String message = "";
		try {
			message = preOrgnDAO.deletePreOrgnDetails(preOrgnBean);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public List getPreOrgnDetails(PreOrgnDetailsBean preOrgnBean) throws Exception {
		List organisationlist = null;
		try {
			organisationlist = preOrgnDAO.getPreOrgnDetails(preOrgnBean);
		} catch (Exception e) {
			throw e;
		}
		return organisationlist;
	}

	public PreOrgnDetailsBean editPreOrgnDetails(PreOrgnDetailsBean preOrgnBean) throws Exception {
		List orgnList = null;
		try {
			orgnList = preOrgnDAO.editOrganisation(preOrgnBean);
			if (CPSUtils.checkList(orgnList)) {
				preOrgnBean = (PreOrgnDetailsBean) orgnList.get(0);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
				String[] date1 = preOrgnBean.getFromDate().split("\\.");
				Date date = df.parse(date1[0]);
				preOrgnBean.setFromDate(df1.format(date));
				date1 = preOrgnBean.getToDate().split("\\.");
				date = df.parse(date1[0]);
				preOrgnBean.setToDate(df1.format(date));
			}
		} catch (Exception e) {
			throw e;
		}
		return preOrgnBean;
	}
}