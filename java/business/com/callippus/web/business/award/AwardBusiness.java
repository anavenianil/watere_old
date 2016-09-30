package com.callippus.web.business.award;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.award.AwardDetails;
import com.callippus.web.beans.dto.AwardCategoryDTO;
import com.callippus.web.beans.dto.YearTypeDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.award.IAwardDAO;
import com.callippus.web.dao.common.IComonObjectDAO;

@Service
public class AwardBusiness {
	@Autowired
	private IAwardDAO awardDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public AwardDetails getAwardHomeDetails(AwardDetails awardBean, HttpServletRequest request) throws Exception {
		List<AwardDetails> awardList = null;
		try {
			awardList = awardDAO.getAwardHomeDetails(awardBean);
			int size = awardList.size();
			HttpSession session = request.getSession(true);
			if (size > 0) {
				session.setAttribute(CPSConstants.MASTERJSON, awardList.get(size - 1));
				awardList.remove(size - 1);
			}
			awardBean.setAwardList(awardList);
		} catch (Exception e) {
			throw e;
		}
		return awardBean;
	}

	public AwardDetails manageAwardDetails(AwardDetails awardBean) throws Exception {
		String message = null;
		try {
			if (CPSUtils.isNullOrEmpty(awardBean.getId())) {
				int id = commonDataDAO.getTableID(CPSConstants.AWARD_DETAILS, CPSConstants.UPDATE);
				awardBean.setId(String.valueOf(id));
				awardBean.setCreationDate(CPSUtils.getCurrentDate());
				awardBean.setLastModifiedDate(CPSUtils.getCurrentDate());
				message = awardDAO.manageAwardDetails(awardBean);
			} else {
				message = awardDAO.updateAwardDetails(awardBean);
				if (message == CPSConstants.SUCCESS) {
					message = CPSConstants.UPDATE;
				}
			}
			awardBean.setMessage(message);
			List awardList = awardDAO.getAwardHomeDetails(awardBean);
			int size = awardList.size();
			if (size > 0) {
				awardList.remove(size - 1);
			}
			awardBean.setAwardList(awardList);
		} catch (Exception e) {
			throw e;
		}
		return awardBean;
	}

	public AwardDetails deleteAwardDetails(AwardDetails awardBean) throws Exception {
		String message = null;
		try {

			message = awardDAO.deleteAwardDetails(awardBean);
			if (message == CPSConstants.SUCCESS) {
				message = CPSConstants.DELETE;
			}
			awardBean.setMessage(message);
			List awardList = awardDAO.getAwardHomeDetails(awardBean);
			int size = awardList.size();
			if (size > 0) {
				awardList.remove(size - 1);
			}
			awardBean.setAwardList(awardList);
		} catch (Exception e) {
			throw e;
		}
		return awardBean;
	}

	public List<YearTypeDTO> getYearList() throws Exception {
		List<YearTypeDTO> list = null;
		try {
			list = commonDataDAO.getYearList();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public List<AwardCategoryDTO> getAwardCategoryList() throws Exception {
		List<AwardCategoryDTO> list = null;
		try {
			list = awardDAO.getAwardCategoryList();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
}