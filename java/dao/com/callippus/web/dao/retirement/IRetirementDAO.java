package com.callippus.web.dao.retirement;

import java.util.List;

import com.callippus.web.beans.dto.RetirementTypeDTO;
import com.callippus.web.beans.retirement.RetirementBean;

public interface IRetirementDAO {
	public List<RetirementBean> getRetirementHomeDetails(RetirementBean retirementBean) throws Exception;

	public String manageRetirementDetails(RetirementBean retirementBean) throws Exception;

	public String deleteRetirementDetails(RetirementBean retirementBean) throws Exception;

	public List<RetirementTypeDTO> getRetirementTypeList() throws Exception;

	public String updateRetirementDetails(RetirementBean retirementBean) throws Exception;

	public boolean checkRetirementDetails(RetirementBean retirementBean) throws Exception;

	public List getRetirementReportDetails(RetirementBean retirementBean) throws Exception;

	public List getYearList(RetirementBean retirementBean) throws Exception;

	public List getRetirementFianlDetails(RetirementBean retirementBean) throws Exception;

	public List getRetirementFinalDetail(RetirementBean retirementBean)throws Exception;
}
