package com.callippus.web.loan.dao.management;

import java.util.List;
import com.callippus.web.loan.beans.dto.GPFRulesDTO;
import com.callippus.web.loan.beans.dto.LoanCDADetailsDTO;
import com.callippus.web.loan.beans.dto.LoanDesigMappingDTO;
import com.callippus.web.loan.beans.dto.LoanFestivalMasterDTO;
import com.callippus.web.loan.beans.dto.LoanHBAInterestGridDTO;
import com.callippus.web.loan.beans.dto.LoanReliefFund;
import com.callippus.web.loan.beans.dto.LoanSigningAuthorityDTO;
import com.callippus.web.loan.beans.dto.LoanTypeMasterDTO;
import com.callippus.web.loan.beans.management.LoanManagementBean;

public interface ILoanManagementDAO {

	public LoanManagementBean getLoanTypeMasterDetails(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean submitLoanTypeMasterDetails(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean deleteLoanTypeMasterDetails(LoanManagementBean loanBean) throws Exception;

	public List<LoanFestivalMasterDTO> getLoanFestivalList() throws Exception;

	public String saveLoanFestivalDetails(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean getGpfBalanceList(LoanManagementBean loanBean) throws Exception;

	public String submitGpfClosingBalanceDetails(LoanManagementBean loanBean) throws Exception;

	public List<GPFRulesDTO> getGpfRulesList() throws Exception;

	public LoanManagementBean getGpfLoanTypeMasterDetails(LoanManagementBean loanBean) throws Exception;

	public String submitGPFRulesDetails(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean getLoanTypeDetails(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean getLeaveTypes(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean submitLoanTypeDetails(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean submitLoanAmountDetails(LoanManagementBean loanBean) throws Exception;

	public String submitLoanDesigMappings(LoanDesigMappingDTO loanDesigMappingDTO) throws Exception;

	public String submitLoanLeavesMappings(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean deleteLoanTypeDetails(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean getLoanLeavesMapping(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean duplicateLoanTypeDetailsCheck(LoanManagementBean loanBean) throws Exception;

	public String saveLoanReliefDetails(LoanReliefFund reliefFund) throws Exception;

	public String checkDuplicateSfid(LoanManagementBean loanBean) throws Exception;

	public List<LoanReliefFund> getReliefList() throws Exception;

	public String deleteReliefDetails(LoanManagementBean loanBean) throws Exception;

	public String submitAmountGrid(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean getLoanAmountList(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean getLoanDesignationMappings(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean getLoanAmountGridDetails(LoanManagementBean loanBean) throws Exception;

	public void resetAmountGrid(LoanManagementBean loanBean) throws Exception;

	public void resetloanDesigMapping(LoanManagementBean loanBean) throws Exception;

	public String deleteLoanAmountDetails(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean getFinancialYearList(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean getAppliedLoansList(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean submitSendingReportDetails(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean getReportNumberList(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean getSearchCDAList(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean submitCDAReportDetails(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean deleteLoanFestivalDetails(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean manageLoanHBAInterestGrid(LoanManagementBean loanBean) throws Exception;

	public List<LoanHBAInterestGridDTO> getLoanHBAInterestList() throws Exception;

	public LoanManagementBean deleteLoanHBAInterestGrid(LoanManagementBean loanBean) throws Exception;

	public List<LoanCDADetailsDTO> getConveyanceReportList() throws Exception;

	public List<LoanSigningAuthorityDTO> getSigningAuthorityList() throws Exception;

	public LoanManagementBean submitAdminOfficer(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean submitCDAReport(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean submitReGenerateDetails(LoanManagementBean loanBean) throws Exception;

	public List<LoanCDADetailsDTO> getHQReportList() throws Exception;

	public LoanManagementBean getCDAReportList(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean getPaybillBACReportList(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean getAppliedFestivalList(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean getHQReportNumberList(LoanManagementBean loanBean) throws Exception;

	public LoanManagementBean getSanctionContingentList(LoanManagementBean loanBean) throws Exception;

}
