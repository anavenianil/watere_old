package com.callippus.web.business.retirement;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.RetirementTypeDTO;
import com.callippus.web.beans.retirement.RetirementBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.retirement.IRetirementDAO;

@Service
public class RetirementBusiness {
	@Autowired
	private IRetirementDAO retirementDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public RetirementBean getRetirementHomeDetails(RetirementBean retirementBean, HttpSession session) throws Exception {
		List<RetirementBean> retirementList = null;
		try {
			retirementList = retirementDAO.getRetirementHomeDetails(retirementBean);
			retirementBean.setRetirementList(retirementList);
			int size = retirementList.size();
			if (size > 0) {
				session.setAttribute(CPSConstants.MASTERJSON, retirementList.get(size - 1));
				retirementList.remove(size - 1);
			}
		} catch (Exception e) {
			throw e;
		}
		return retirementBean;
	}

	public RetirementBean manageRetirementDetails(RetirementBean retirementBean, HttpSession session) throws Exception {
		String message = null;
		try {
			if (CPSUtils.isNullOrEmpty(retirementBean.getId())) {
				if (retirementDAO.checkRetirementDetails(retirementBean)) {
					int id = commonDataDAO.getTableID(CPSConstants.RETIREMENT_DETAILS, CPSConstants.UPDATE);
					retirementBean.setId(String.valueOf(id));
					retirementBean.setCreationDate(CPSUtils.getCurrentDate());
					retirementBean.setLastModifiedDate(CPSUtils.getCurrentDate());
					message = retirementDAO.manageRetirementDetails(retirementBean);
					retirementBean.setMessage(message);
				}
			} else {
				retirementBean.setLastModifiedDate(CPSUtils.getCurrentDate());
				message = retirementDAO.updateRetirementDetails(retirementBean);
				if (message == CPSConstants.SUCCESS) {
					message = CPSConstants.UPDATE;
					retirementBean.setMessage(message);
				}
			}

			retirementBean.setRetirementList(retirementDAO.getRetirementHomeDetails(retirementBean));
			int size = retirementBean.getRetirementList().size();
			if (size > 0) {
				session.setAttribute(CPSConstants.MASTERJSON, retirementBean.getRetirementList().get(size - 1));
				retirementBean.getRetirementList().remove(size - 1);
			}
		} catch (Exception e) {
			throw e;
		}
		return retirementBean;
	}

	public RetirementBean deleteRetirementDetails(RetirementBean retirementBean, HttpSession session) throws Exception {
		String message = null;
		try {
			message = retirementDAO.deleteRetirementDetails(retirementBean);
			if (message == CPSConstants.SUCCESS) {
				message = CPSConstants.DELETE;
			}
			retirementBean.setMessage(message);
			retirementBean.setRetirementList(retirementDAO.getRetirementHomeDetails(retirementBean));
			int size = retirementBean.getRetirementList().size();
			if (size > 0) {
				session.setAttribute(CPSConstants.MASTERJSON, retirementBean.getRetirementList().get(size - 1));
				retirementBean.getRetirementList().remove(size - 1);
			}
		} catch (Exception e) {
			throw e;
		}
		return retirementBean;
	}

	public List<RetirementTypeDTO> getRetirementTypeList() throws Exception {
		List<RetirementTypeDTO> list = null;
		try {
			list = retirementDAO.getRetirementTypeList();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public RetirementBean getRetirementReportDetails(RetirementBean retirementBean) throws Exception{
		List list = null;
		try
		{
			list = retirementDAO.getRetirementReportDetails(retirementBean);
			retirementBean.setRetirementList(list);
		}
		catch (Exception e) {
			throw e;
		}
		return retirementBean;
	}

	public List getYearList(RetirementBean retirementBean) throws Exception{
		List list = null;
		try
		{
			list = retirementDAO.getYearList(retirementBean);
					
		}
		catch (Exception e) {
			throw e;
		}
		return list;
	}

	public RetirementBean getRetirementFinalDetails(RetirementBean retirementBean) throws Exception{
		List list= null;
		try
		{
			list = retirementDAO.getRetirementFianlDetails(retirementBean);
			retirementBean.setRetirementList(list);
		}
		catch(Exception e)
		{
			throw e;
		}
		return retirementBean;
	}

	public RetirementBean getRetirementFinalDetail(RetirementBean retirementBean) throws Exception{
		List list= null;
		try
		{
			list = retirementDAO.getRetirementFinalDetail(retirementBean);
			for(int i = 0;CPSUtils.checkList(list) && i<list.size();i++)
			{
				RetirementBean bean = (RetirementBean) list.get(i);
				switch(bean.getLeaveTypeId())
				{
				case 15:retirementBean.setEol(bean.getTotalLeaves());break;
				case 2:retirementBean.setHpl(bean.getTotalLeaves());break;
				case 1:retirementBean.setEl(bean.getTotalLeaves());break;
				case 3:retirementBean.setCl(bean.getTotalLeaves());break;
				
				}
				
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return retirementBean;
	}
	
	
}