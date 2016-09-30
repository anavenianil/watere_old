package com.callippus.web.dao.reportDetails;

import java.util.List;

import com.callippus.web.beans.reportDetails.ReportDetailsBean;

public interface IReportDetailsDAO {

	public List<ReportDetailsBean> designationsList() throws Exception;

	public ReportDetailsBean saveCpoolDetails(ReportDetailsBean reportDetailsBean) throws Exception;

	public ReportDetailsBean checkCpoolDetails(ReportDetailsBean reDetailsBean) throws Exception;
}
