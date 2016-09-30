package com.callippus.web.business.reportDetails;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.reportDetails.ReportDetailsBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.reportDetails.IReportDetailsDAO;

@SuppressWarnings("unchecked")
@Service
public class ReportDetailsBusiness {
	@Autowired
	private IReportDetailsDAO iReportDetailsDAO;

	public List<ReportDetailsBean> getDesignations() throws Exception {
		List designationsList = null;
		try {
			designationsList = iReportDetailsDAO.designationsList();

		} catch (Exception e) {
			throw e;
		}
		return designationsList;
	}

	public ReportDetailsBean saveCpoolDetails(ReportDetailsBean reportDetailsBean) throws Exception {
		JSONObject json = null;
		JSONArray jsonArray = null;
		ReportDetailsBean reportDetailsBean1 = null;
		try {
			json = new JSONObject(reportDetailsBean.getJsonValue());
			jsonArray = (JSONArray) json.get("reValues");
			for (int i = 0; i < jsonArray.length() - 1; i++) {
				String text = String.valueOf(jsonArray.get(i));
				String[] temp = text.split("-");
				reportDetailsBean.setDesignationId(temp[0]);
				reportDetailsBean1 = iReportDetailsDAO.checkCpoolDetails(reportDetailsBean);
				if (!CPSUtils.isNull(reportDetailsBean1)) {
					reportDetailsBean1.setcPoolIn(temp[1]);
					reportDetailsBean1.setcPoolOut(temp[2]);
					reportDetailsBean1.setTxnDate(reportDetailsBean.getTxnDate());
				} else {
					reportDetailsBean1 = new ReportDetailsBean();
					reportDetailsBean1.setDesignationId(temp[0]);
					reportDetailsBean1.setcPoolIn(temp[1]);
					reportDetailsBean1.setcPoolOut(temp[2]);
					reportDetailsBean1.setTxnDate(reportDetailsBean.getTxnDate());
				}
				iReportDetailsDAO.saveCpoolDetails(reportDetailsBean1);
				reportDetailsBean1 = null;
			}
			reportDetailsBean.setMessage(CPSConstants.SUCCESS);
		} catch (Exception e) {
			throw e;
		}
		return reportDetailsBean;
	}

}
