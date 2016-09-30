package com.callippus.web.business.redetails;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.redetails.ReDetailsBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.redetails.IReDetailsDAO;


@SuppressWarnings("unchecked")
@Service
public class ReDetailsBusiness {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(ReDetailsBusiness.class);
	@Autowired
	private IReDetailsDAO reDetailsDAO;
	
	public List<ReDetailsBean> getDesignations(ReDetailsBean reDetailsBean) throws Exception {
		List designationsList = null;
		try {
			designationsList = reDetailsDAO.designationsList(reDetailsBean);

		} catch (Exception e) {
			throw e;
		}
		return designationsList;
	}

	public List<CategoryDTO> getCategoryList() throws Exception {
		List categoryList = null;
		try {
			categoryList = reDetailsDAO.categoryList();
		} catch (Exception e) {
			throw e;
		}
		return categoryList;
	}
	
	public ReDetailsBean saveReDetails(ReDetailsBean reDetailsBean) throws Exception {
		ReDetailsBean reDetailsBean1 = null;
		try {
			
			/*json = new JSONObject(reDetailsBean.getJsonValue());
			jsonArray = (JSONArray) json.get("reValues");*/
			String desigId[] = reDetailsBean.getDesignationId().split("#");
			for (int i = 0; i < desigId.length; i++) {
				String[] temp = desigId[i].split("-");
				reDetailsBean.setDesignationId(temp[0]);
				reDetailsBean1 = reDetailsDAO.checkReDetails(reDetailsBean);
				if (!CPSUtils.isNull(reDetailsBean1)) {
					reDetailsBean1.setReValue(temp[1]);
					reDetailsBean1.setReportType("1");
					reDetailsBean1.setTxnDate(reDetailsBean.getTxnDate());
					reDetailsBean1.setCategoryID(reDetailsBean.getCategoryID());
				} else {
					reDetailsBean1 = new ReDetailsBean();
					reDetailsBean1.setDesignationId(temp[0]);
					reDetailsBean1.setReValue(temp[1]);
					reDetailsBean1.setReportType("1");
					reDetailsBean1.setTxnDate(reDetailsBean.getTxnDate());
					reDetailsBean1.setCategoryID(reDetailsBean.getCategoryID());
				}
				reDetailsDAO.saveReDetails(reDetailsBean1);

			}
			/*for (int i = 0; i < jsonArray.length() - 1; i++) {
				String text = String.valueOf(jsonArray.get(i));
				String[] temp = text.split("-");
				reDetailsBean.setDesignationId(temp[0]);
				reDetailsBean1 = reDetailsDAO.checkReDetails(reDetailsBean);
				if (!CPSUtils.isNull(reDetailsBean1)) {
					reDetailsBean1.setReValue(temp[1]);
					reDetailsBean1.setReportType("1");
					reDetailsBean1.setTxnDate(reDetailsBean.getTxnDate());
				} else {
					reDetailsBean1 = new ReDetailsBean();
					reDetailsBean1.setDesignationId(temp[0]);
					reDetailsBean1.setReValue(temp[1]);
					reDetailsBean1.setReportType("1");
					reDetailsBean1.setTxnDate(reDetailsBean.getTxnDate());
				}
				reDetailsDAO.saveReDetails(reDetailsBean1);
				reDetailsBean1 = null;
			}*/
			reDetailsBean.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return reDetailsBean;
	}
}	
