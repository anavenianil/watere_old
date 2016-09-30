package com.callippus.web.loan.business.management;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.orgstructure.IOrgStructureDAO;
import com.callippus.web.loan.beans.dto.LoanDesigMappingDTO;
import com.callippus.web.loan.beans.dto.LoanReliefFund;
import com.callippus.web.loan.beans.management.LoanManagementBean;
import com.callippus.web.loan.dao.management.ILoanManagementDAO;

@Service
public class LoanManagementBusiness {
	@Autowired
	private ILoanManagementDAO loanManagementDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private IOrgStructureDAO orgStructureDAO;

	public LoanManagementBean getLoanTypeMasterDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.getLoanTypeMasterDetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean submitLoanTypeMasterDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.submitLoanTypeMasterDetails(loanBean);		
			} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean deleteLoanTypeMasterDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.deleteLoanTypeMasterDetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	// loan festival Starts

	public LoanManagementBean getLoanFestivalList(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean.setLoanFestivalList(loanManagementDAO.getLoanFestivalList());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public String saveLoanFestivalDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean.setResult(loanManagementDAO.saveLoanFestivalDetails(loanBean));
		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
	}

	public String deleteLoanFestivalDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.deleteLoanFestivalDetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
	}

	// loan festival Ends

	public LoanManagementBean getLoanAmountListDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.getLoanAmountList(loanBean);
			loanBean = loanManagementDAO.getLoanDesignationMappings(loanBean);
			loanBean = loanManagementDAO.getLoanAmountGridDetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	// gpf closing balance
	public LoanManagementBean getGpfBalanceList(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.getGpfBalanceList(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public String submitGpfClosingBalanceDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean.setStatus(1);
			loanBean.setResult(commonDataDAO.checkEmployee(loanBean.getChangeSfID()));
			if (CPSUtils.compareStrings(loanBean.getResult(), CPSConstants.SUCCESS)) {
				loanBean.setResult(loanManagementDAO.submitGpfClosingBalanceDetails(loanBean));
			} else {
				loanBean.setResult(CPSConstants.EMPNOTEXISTS);
			}
		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
	}

	public String deleteGpfClosingBalanceDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean.setStatus(0);
			loanBean.setResult(loanManagementDAO.submitGpfClosingBalanceDetails(loanBean));
		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
	}

	public LoanManagementBean getGpfRulesHome(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.getGpfLoanTypeMasterDetails(loanBean);
			loanBean.setGpfRulesList(loanManagementDAO.getGpfRulesList());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public String submitGPFRulesDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean.setResult(loanManagementDAO.submitGPFRulesDetails(loanBean));
			if (CPSUtils.compareStrings(loanBean.getResult(), CPSConstants.SUCCESS)) {
				loanBean.setGpfRulesList(loanManagementDAO.getGpfRulesList());
			}
		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
	}

	// Loan Type details
	public LoanManagementBean getLoanTypeHomeDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.getLoanTypeMasterDetails(loanBean);
			loanBean = loanManagementDAO.getLeaveTypes(loanBean);

		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean getLoanTypeGrid(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.getLoanTypeDetails(loanBean);
			loanBean = loanManagementDAO.getLoanLeavesMapping(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean manageLoanTypeDetails(LoanManagementBean loanBean) throws Exception {
		try {
			// Duplicate Checking
			loanBean = loanManagementDAO.duplicateLoanTypeDetailsCheck(loanBean);
			if (CPSUtils.compareStrings(loanBean.getResult(), CPSConstants.SUCCESS)) {
				loanBean = loanManagementDAO.submitLoanTypeDetails(loanBean);

				if (CPSUtils.compareStrings(loanBean.getResult(), CPSConstants.SUCCESS)) {
					loanBean.setResult(loanManagementDAO.submitLoanLeavesMappings(loanBean));
				}
			}

		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean deleteLoanTypeDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.deleteLoanTypeDetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean manageLoanAmountDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.submitLoanAmountDetails(loanBean);
			if (CPSUtils.compareStrings(loanBean.getResult(), CPSConstants.SUCCESS)) {
				String[] selectedDesignations = loanBean.getDesignation().split(",");
				loanManagementDAO.resetloanDesigMapping(loanBean);
				for (String designationID : selectedDesignations) {
					LoanDesigMappingDTO loanDesigMappingDTO = new LoanDesigMappingDTO();
					loanDesigMappingDTO.setLoanAmountID(loanBean.getAmountID());
					loanDesigMappingDTO.setDesignationID(Integer.valueOf(designationID));
					loanDesigMappingDTO.setStatus(1);
					loanBean.setResult(loanManagementDAO.submitLoanDesigMappings(loanDesigMappingDTO));
				}
				// Insert Amount Grid
				if (!CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
					loanManagementDAO.resetAmountGrid(loanBean);
				}
				loanBean.setResult(loanManagementDAO.submitAmountGrid(loanBean));
			}
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean getLoanAmtMasterDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.getLoanTypeMasterDetails(loanBean);
			loanBean.setDesignationList(orgStructureDAO.getDesignationsList());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	// Loan Immediate Relief
	@SuppressWarnings("unchecked")
	public LoanManagementBean getFamilyRelationList(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean.setRelationList(commonDataDAO.getMasterData(CPSConstants.FAMILYRELATIONDTO));
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public String submitLoanReliefDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean.setResult(commonDataDAO.checkEmployee(loanBean.getRefSfID()));
			if (CPSUtils.compareStrings(loanBean.getResult(), CPSConstants.SUCCESS)) {
				loanBean.setResult(loanManagementDAO.checkDuplicateSfid(loanBean));
				if (CPSUtils.compareStrings(loanBean.getResult(), CPSConstants.SUCCESS)) {
					LoanReliefFund reliefFund = new LoanReliefFund();
					if (!CPSUtils.isNullOrEmpty(loanBean.getNodeID())) {
						reliefFund.setId(Integer.valueOf(loanBean.getNodeID()));
					}
					reliefFund.setSfID(loanBean.getRefSfID().toUpperCase());
					reliefFund.setDateOfDeath(loanBean.getDod());
					reliefFund.setNameOfApplicant(loanBean.getAppname());
					reliefFund.setRelationshipID(Integer.valueOf(loanBean.getRelationShipID()));
					reliefFund.setAdvance(Float.valueOf(loanBean.getAdvanceAmount()));
					reliefFund.setWitness1(loanBean.getWitness1());
					reliefFund.setWitness2(loanBean.getWitness2());
					reliefFund.setAddress(loanBean.getAddress());
					reliefFund.setApprovedBy(loanBean.getApprovedBy());
					reliefFund.setStatus(1);
					reliefFund.setCreatedBy(loanBean.getSfID());
					reliefFund.setCreationTime(CPSUtils.getCurrentDateWithTime());
					reliefFund.setLastModifiedTime(reliefFund.getCreationTime());
					reliefFund.setLastModifiedBy(loanBean.getSfID());
					loanBean.setResult(loanManagementDAO.saveLoanReliefDetails(reliefFund));
				} else {
					loanBean.setResult(CPSConstants.DUPLICATE);
				}
			} else {
				loanBean.setResult(CPSConstants.EMPNOTEXISTS);
			}
		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
	}

	public List<LoanReliefFund> getReliefList() throws Exception {
		List<LoanReliefFund> list = null;
		try {
			list = loanManagementDAO.getReliefList();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	public String deleteLoanReliefDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean.setResult(loanManagementDAO.deleteReliefDetails(loanBean));
		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
	}

	public String deleteLoanAmountDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean.setResult(loanManagementDAO.deleteLoanAmountDetails(loanBean));
		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();

	}

	public LoanManagementBean getFinancialYearList(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.getFinancialYearList(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean getAppliedLoansList(LoanManagementBean loanBean) throws Exception {

		try {
			loanBean = loanManagementDAO.getAppliedLoansList(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean submitSendingReportDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.submitSendingReportDetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean getReportNumberList(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.getReportNumberList(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean getSearchCDAList(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.getSearchCDAList(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean submitCDAReportDetails(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.submitCDAReportDetails(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean manageLoanHBAInterestGrid( LoanManagementBean loanBean)  throws Exception{
		try {
			loanBean = loanManagementDAO.manageLoanHBAInterestGrid(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean getLoanHBAInterestGridList(LoanManagementBean loanBean) throws Exception{
		try {
			loanBean.setLoanHBAInterestList(loanManagementDAO.getLoanHBAInterestList());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;

	}

	public String deleteLoanHBAInterestGrid(LoanManagementBean loanBean) throws Exception{
		try {
			loanBean = loanManagementDAO.deleteLoanHBAInterestGrid(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean.getResult();
	}

	public LoanManagementBean getConveyanceReportList(
			LoanManagementBean loanBean)throws Exception{
		try {
			loanBean.setConveyanceReportList(loanManagementDAO.getConveyanceReportList());
			loanBean.setSigningAuthorityList(loanManagementDAO.getSigningAuthorityList());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;	
}

	public LoanManagementBean submitAdminOfficer(LoanManagementBean loanBean) throws Exception{
		try {
		loanBean = loanManagementDAO.submitAdminOfficer(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean submitCDAReport(LoanManagementBean loanBean)throws Exception{
		try {
			loanBean = loanManagementDAO.submitCDAReport(loanBean);
			} catch (Exception e) {
				throw e;
			}
			return loanBean;
	}

	public LoanManagementBean submitReGenerateDetails(
			LoanManagementBean loanBean) throws Exception{
		try {
			loanBean = loanManagementDAO.submitReGenerateDetails(loanBean);
			} catch (Exception e) {
				throw e;
			}
			return loanBean;
	}

	public LoanManagementBean getHQReportList(LoanManagementBean loanBean) throws Exception{
		try {
			loanBean.setConveyanceReportList(loanManagementDAO.getHQReportList());
			loanBean.setSigningAuthorityList(loanManagementDAO.getSigningAuthorityList());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;	
	}

	public LoanManagementBean getCDAReportList(LoanManagementBean loanBean) throws Exception{
		try {
			loanBean=loanManagementDAO.getCDAReportList(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean getPaybillBACReportList(LoanManagementBean loanBean)throws Exception{
		try {
			loanBean=loanManagementDAO.getPaybillBACReportList(loanBean);
			loanBean.setSigningAuthorityList(loanManagementDAO.getSigningAuthorityList());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
	
	public LoanManagementBean getAppliedFestivalList(LoanManagementBean loanBean) throws Exception {

		try {
			loanBean = loanManagementDAO.getAppliedFestivalList(loanBean);
			loanBean.setSigningAuthorityList(loanManagementDAO.getSigningAuthorityList());
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean getHQReportNumberList(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.getHQReportNumberList(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}

	public LoanManagementBean getSanctionContingentList(LoanManagementBean loanBean) throws Exception {
		try {
			loanBean = loanManagementDAO.getSanctionContingentList(loanBean);
		} catch (Exception e) {
			throw e;
		}
		return loanBean;
	}
}