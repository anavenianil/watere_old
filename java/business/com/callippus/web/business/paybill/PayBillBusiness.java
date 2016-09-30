package com.callippus.web.business.paybill;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.paybill.PayBillBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.paybill.IPayBillDAO;
import com.callippus.web.dao.paybill.IPayBillMasterDAO;
import com.callippus.web.paybill.dto.PayBillDTO;
import com.callippus.web.paybill.dto.PayBillStatusDTO;

@Service
public class PayBillBusiness {
	@Autowired
	private IPayBillDAO payBillDAO;
	@Autowired
	private IPayBillMasterDAO iPayBillMasterDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public PayBillBean getEmpPayDetails(PayBillBean payBillBean) throws Exception {

		try {
			String message = payBillDAO.getEmpExist(payBillBean.getSearchSfid().toUpperCase());
			if (CPSUtils.compareStrings(CPSConstants.YES, message)) {
				message = payBillDAO.getEmpPayStopFlag(payBillBean.getSearchSfid().toUpperCase());

				if (CPSUtils.compareStrings(CPSConstants.YES, message)) {
					payBillBean.setMessage(CPSConstants.EMPPAYSTOP);
					payBillBean.setPayStopRemarks(payBillDAO.getEmpPayStopRemarks(payBillBean.getSearchSfid().toUpperCase()));
				} else {

					EmployeeBean empBean = payBillDAO.getEmployeeDetails(payBillBean.getSearchSfid().toUpperCase());
					payBillBean.setNameInServiceBook(empBean.getName());
					payBillBean.setDesignationName(empBean.getDesignationName());
					payBillBean.setDepartmentName(empBean.getDivisionName());
					payBillBean.setType(empBean.getType());//newly added
					payBillBean.setCategoryName(empBean.getCategoryId());//newly added
					payBillBean.setWorkingLocation(empBean.getWorkingLocation());
					payBillBean.setMember(iPayBillMasterDAO.checkGPFmember(payBillBean.getSearchSfid().toUpperCase()));
                   PayBillDTO paydto = payBillDAO.getEmpPayDeatails(payBillBean.getSearchSfid().toUpperCase(),payBillBean.getRunMonth());
					if (!CPSUtils.isNull(paydto)) {
						BeanUtils.copyProperties(payBillBean, paydto);
						payBillBean.setTotalCredits1(paydto.getTotalCredits());
						payBillBean.setTotalDebits1(paydto.getTotalDebits());
						payBillBean.setTotalRecovery1(paydto.getTotalRecovery());
						payBillBean.setNetPay1(paydto.getNetPay());
						payBillBean.setFinalPay1(paydto.getFinalPay());
						payBillBean.setMessage(paydto.getMessage());
						
						payBillBean.setCreatedBy(paydto.getCreatedBy());
						payBillBean.setCreationTime(paydto.getCreationTime());
						payBillBean.setLastModifiedBy(paydto.getLastModifiedBy());
						payBillBean.setLastModifiedTime(paydto.getLastModifiedTime());
						//added on 31/12/2013
						payBillBean.setStatus(String.valueOf(paydto.getStatus()));
					} else {
						payBillBean.setMessage(CPSConstants.AUTORUNNOTCOMPLETED);
					}
				}
			} else {
				payBillBean.setMessage(CPSConstants.EMPNOTEXISTS);
			}
		} catch (Exception e) {
			throw e;
		}
		return payBillBean;

	}
	public PayBillBean updateEmpPayBillDetails(PayBillBean payBillBean) throws Exception {
		String message = "";
		try {
			PayBillDTO paydto = new PayBillDTO();
			BeanUtils.copyProperties(paydto, payBillBean);
			paydto.setSfid(payBillBean.getSearchSfid().toUpperCase());
			paydto.setLastModifiedBy(payBillBean.getSfId());
			paydto.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
			message = payBillDAO.updateEmpPayBillDetails(paydto);
			if (CPSUtils.compareStrings(CPSConstants.YES, message)) {
				payBillBean.setMessage(CPSConstants.SUCCESS);
			} else {

				payBillBean.setMessage(CPSConstants.CONSTRAINTS);
				payBillBean.setRemarks(message);
			}
		} catch (Exception e) {
			throw e;
		}
		return payBillBean;
	}
	
	//recalculate pay values with changed values in edit employee data//kumari
	public PayBillBean getRecalcultedPayDetails(PayBillBean payBillBean) throws Exception {
		PayBillBean recalculatedPayDetails=null;
		try {
			
				//for basic info
				EmployeeBean empBean = payBillDAO.getEmployeeDetails(payBillBean.getSearchSfid().toUpperCase());
				payBillBean.setNameInServiceBook(empBean.getName());
				payBillBean.setDesignationName(empBean.getDesignationName());
				payBillBean.setDepartmentName(empBean.getDivisionName());
				payBillBean.setType(empBean.getType());
				payBillBean.setCategoryName(empBean.getCategoryId());
				payBillBean.setWorkingLocation(empBean.getWorkingLocation());
				payBillBean.setMember(iPayBillMasterDAO.checkGPFmember(payBillBean.getSearchSfid().toUpperCase()));
				
				//to display original valus as lables
               PayBillDTO paydto = payBillDAO.getEmpPayDeatails(payBillBean.getSearchSfid().toUpperCase(),payBillBean.getRunMonth());
				if (!CPSUtils.isNull(paydto)) {
					PayBillBean dbPayDetails=new PayBillBean();
					BeanUtils.copyProperties(dbPayDetails, paydto);
					
					dbPayDetails.setTotalCredits1(paydto.getTotalCredits());
					dbPayDetails.setTotalDebits1(paydto.getTotalDebits());
					dbPayDetails.setTotalRecovery1(paydto.getTotalRecovery());
					dbPayDetails.setNetPay1(paydto.getNetPay());
					dbPayDetails.setFinalPay1(paydto.getFinalPay());
					dbPayDetails.setMessage(paydto.getMessage());
					
					/*dbPayDetails.setCreatedBy(paydto.getCreatedBy());
					dbPayDetails.setCreationTime(paydto.getCreationTime());
					dbPayDetails.setLastModifiedBy(paydto.getLastModifiedBy());
					dbPayDetails.setLastModifiedTime(paydto.getLastModifiedTime());*/
					dbPayDetails.setStatus(String.valueOf(paydto.getStatus()));
					
					payBillBean.setPayDetails(dbPayDetails);
					payBillBean.setCycleInstNumb(dbPayDetails.getCycleInstNumb());
					payBillBean.setCycleTotInst(dbPayDetails.getCycleTotInst());
					payBillBean.setScooterInstNumb(dbPayDetails.getScooterInstNumb());
					payBillBean.setScooterTotInst(dbPayDetails.getScooterTotInst());
					payBillBean.setCarInstNumb(dbPayDetails.getCarInstNumb());
					payBillBean.setCarTotInst(dbPayDetails.getCarTotInst());
					payBillBean.setPcInstNumb(dbPayDetails.getPcInstNumb());
					payBillBean.setPcTotInst(dbPayDetails.getPcTotInst());
					payBillBean.setHbaInstNumb(dbPayDetails.getHbaInstNumb());
					payBillBean.setHbaTotInst(dbPayDetails.getHbaTotInst());
					payBillBean.setFestivalAdv(dbPayDetails.getFestivalAdv());
					payBillBean.setFestivTotInst(dbPayDetails.getFestivTotInst());
					payBillBean.setStatus(String.valueOf(paydto.getStatus()));
					
				}
				
				//for recalculate pay values with changed values
				recalculatedPayDetails = payBillDAO.getRecalcultedPayDetails(payBillBean);
				payBillBean.setDa(recalculatedPayDetails.getDa());
				payBillBean.setHra(recalculatedPayDetails.getHra());
				payBillBean.setTpt(recalculatedPayDetails.getTpt());
				payBillBean.setGpf(recalculatedPayDetails.getGpf());
				
				int totalCredits=payBillBean.getBasicPay()+payBillBean.getGradePay()+payBillBean.getDa()+payBillBean.getHra()+payBillBean.getTwoAddlIncr()+payBillBean.getSpecialPay()+payBillBean.getTpt()+payBillBean.getWashAllowance()+payBillBean.getDeputAllowance()+payBillBean.getFpa()+payBillBean.getXeroxAllowance()+payBillBean.getRiskAllowance()+payBillBean.getHindiIncentive()+payBillBean.getVariableIncr()+payBillBean.getCrMisc();
				int totDebit=payBillBean.getDrMisc1()+payBillBean.getDrMisc2()+payBillBean.getTada()+payBillBean.getFestivalAdv()+payBillBean.getMedical()+payBillBean.getPli()+payBillBean.getEol()+payBillBean.getHbaLoan()+payBillBean.getLtc()+payBillBean.getGpfRecovery()+payBillBean.getFurn()+payBillBean.getPcLoan()+payBillBean.getProfTax()+payBillBean.getGpfSa()+payBillBean.getElec()+payBillBean.getCarLoan()+payBillBean.getSecondaryCess()+payBillBean.getGpf()+payBillBean.getWater()+payBillBean.getScooterLoan()+payBillBean.getCess()+payBillBean.getCegis()+payBillBean.getRent()+payBillBean.getCycleLoan()+payBillBean.getIncomeTax()+payBillBean.getCghs();
				int totRec=payBillBean.getCcs()+payBillBean.getMess()+payBillBean.getLic()+payBillBean.getResSecu()+payBillBean.getCcsrecovery()+payBillBean.getRegimentalFund()+payBillBean.getCanfin()+payBillBean.getWelC()+payBillBean.getBenvolentFund()+payBillBean.getGic()+payBillBean.getWelRefund()+payBillBean.getHdfc()+payBillBean.getCourtAttachment()+payBillBean.getRecMisc1()+payBillBean.getRecMisc2()+payBillBean.getRecMisc3();
				int netpay=totalCredits-totDebit;
				payBillBean.setTotalCredits1(totalCredits);
				payBillBean.setTotalCredits(totalCredits);
				payBillBean.setTotalDebits1(totDebit);
				payBillBean.setNetPay1(netpay);
				payBillBean.setFinalPay1(netpay-totRec);
				
				payBillBean.setCreatedBy(paydto.getCreatedBy());
				payBillBean.setCreationTime(paydto.getCreationTime());
				payBillBean.setLastModifiedBy(paydto.getLastModifiedBy());
				payBillBean.setLastModifiedTime(paydto.getLastModifiedTime());
			
		} catch (Exception e) {
			throw e;
		}
		return payBillBean;
	}

@SuppressWarnings("static-access")
public PayBillBean startAutoRun(PayBillBean payBillBean) throws Exception {

	//nagendra.v
		try {  
			  if(payBillBean.getCheckAutoRunStarted()==null||PayBillBean.getCheckAutoRunStarted().equals(""))
			  {
			//payBillBean.setCheckAutoRunStarted("auto run in progress.");
				      payBillBean.setCheckAutoRunStarted("auto run started");          
					PayBillStatusDTO payBillStatusDTO = payBillDAO.startPayAutoRun(payBillBean.getRunMonth(), payBillBean.getSfId());
					payBillBean.setRunStatus(payBillStatusDTO.getRunStatus());
					payBillBean.setManualStatus(payBillStatusDTO.getManualStatus());
					payBillBean.setUserStatus(payBillStatusDTO.getUserStatus());
					payBillBean.setMessage(payBillStatusDTO.getDescription());
					payBillBean.setCount(payBillStatusDTO.getCount());
					payBillBean.setGeneratedCount(payBillStatusDTO.getGeneratedCount());
					//nagendra.v
					payBillBean.setCheckAutoRunStarted("");
				
			    }
			    	
			  else{
				  //payBillBean.setCheckAutoRunStarted("auto run in progress.");
				   String decription="auto run stared by..."+payBillBean.getSfId()+"please try it after some time...."; 
				   payBillBean.setAutoRunStatus(decription);  
				  System.out.println(" you r in auto pay progress message is not null.......");
			  }
		} catch (Exception e) {
			payBillBean.setCheckAutoRunStarted("");
			throw e;
		}
		return payBillBean;
	}
	public PayBillBean startDryRun(PayBillBean payBillBean) throws Exception {

		try {
			PayBillStatusDTO pbStatusDTO = payBillDAO.startRun(payBillBean.getSfId(),payBillBean.getRunMonth(),payBillBean);
			if(CPSUtils.compareStrings(pbStatusDTO.getDescription(), CPSConstants.SUCCESS))
			payBillBean.setMessage(pbStatusDTO.getDescription());
			else{
				payBillBean.setMessage(CPSConstants.CONSTRAINTS);
				payBillBean.setRemarks(pbStatusDTO.getDescription());
			}
			payBillBean.setRunStatus(pbStatusDTO.getRunStatus());
			payBillBean.setManualStatus(pbStatusDTO.getManualStatus());
			payBillBean.setUserStatus(pbStatusDTO.getUserStatus());
			payBillBean.setCount(pbStatusDTO.getCount());
			payBillBean.setGeneratedCount(pbStatusDTO.getGeneratedCount());
			} catch (Exception e) {
			throw e;
		}
		return payBillBean;
	}
	public PayBillBean getPayBillStatus(PayBillBean payBillBean) throws Exception {

		try {
			PayBillStatusDTO pbStatusDTO = payBillDAO.getPayBillStatus();
			payBillBean.setRunStatus(pbStatusDTO.getRunStatus());
			payBillBean.setManualStatus(pbStatusDTO.getManualStatus());
			payBillBean.setUserStatus(pbStatusDTO.getUserStatus());
			payBillBean.setRunMonth(pbStatusDTO.getRunMonth());
			payBillBean.setDescription(pbStatusDTO.getDescription());
			payBillBean.setCount(pbStatusDTO.getCount());
			payBillBean.setGeneratedCount(pbStatusDTO.getGeneratedCount());
            payBillBean.setSfidList(pbStatusDTO.getSfidList());
            payBillBean=payBillDAO.countOfRegularAndSupplementaryForGenPayBill(payBillBean);
		} catch (Exception e) {
			throw e;
		}
		return payBillBean;
	}

	public PayBillBean changeStatusToManual(PayBillBean payBillBean) throws Exception {
		try {
			PayBillStatusDTO pbStatusDTO = payBillDAO.changeStatusToManual(payBillBean.getRunMonth(), payBillBean.getSfId());
			payBillBean.setRunStatus(pbStatusDTO.getRunStatus());
			payBillBean.setManualStatus(pbStatusDTO.getManualStatus());
			payBillBean.setUserStatus(pbStatusDTO.getUserStatus());
			payBillBean.setRunMonth(pbStatusDTO.getRunMonth());
			payBillBean.setCount(pbStatusDTO.getCount());
			payBillBean.setGeneratedCount(pbStatusDTO.getGeneratedCount());
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, pbStatusDTO.getDescription())) {
				payBillBean.setMessage(CPSConstants.SUCCESS);
			} else {

				payBillBean.setMessage(CPSConstants.CONSTRAINTS);
				payBillBean.setRemarks(pbStatusDTO.getDescription());
			}
		} catch (Exception e) {
			throw e;
		}
		return payBillBean;
	}

	public PayBillBean changeStatusToVisible(PayBillBean payBillBean) throws Exception {
		try {
			PayBillStatusDTO pbStatusDTO = payBillDAO.changeStatusToVisible(payBillBean.getRunMonth(), payBillBean.getSfId());
			payBillBean.setRunStatus(pbStatusDTO.getRunStatus());
			payBillBean.setManualStatus(pbStatusDTO.getManualStatus());
			payBillBean.setUserStatus(pbStatusDTO.getUserStatus());
			payBillBean.setRunMonth(pbStatusDTO.getRunMonth());
			payBillBean.setCount(pbStatusDTO.getCount());
			payBillBean.setGeneratedCount(pbStatusDTO.getGeneratedCount());
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, pbStatusDTO.getDescription())) {
				payBillBean.setMessage(CPSConstants.SUCCESS);
			} else {

				payBillBean.setMessage(CPSConstants.CONSTRAINTS);
				payBillBean.setRemarks(pbStatusDTO.getDescription());
			}

		} catch (Exception e) {
			throw e;
		}
		return payBillBean;
	}

	public PayBillBean getNegPayEmployees(PayBillBean payBillBean) throws Exception {
		try {
			List<PayBillDTO> listusers = payBillDAO.getNegativePayEmployees();
			payBillBean.setNegPayEmpList(listusers);
		} catch (Exception e) {
			throw e;
		}
		return payBillBean;
	}

	public PayBillBean showPaySlip(PayBillBean payBillBean) throws Exception {
		try {
			PayBillDTO paybilldto = payBillDAO.showPaySlip(payBillBean.getSfId(), payBillBean.getPaySlipMonth()+"-"+payBillBean.getPaySlipYear());
			if (CPSUtils.isNull(paybilldto)) {
				payBillBean.setMessage(CPSConstants.CONSTRAINTS);
				payBillBean.setRemarks(CPSConstants.PAYSLIPSTATUS);
			} else {

				BeanUtils.copyProperties(payBillBean, paybilldto);
				EmployeeBean empBean = payBillDAO.getEmployeeDetails(payBillBean.getSfId());
				payBillBean.setNameInServiceBook(empBean.getName());
				payBillBean.setDesignationName(empBean.getDesignationName());
				payBillBean.setDepartmentName(empBean.getDivisionName());
				payBillBean.setMessage(CPSConstants.YES);
			}
			
		}catch(Exception e){
			throw e;
		}
		return payBillBean;
	}

	public List<PayBillStatusDTO> getPaySlipMonthList()throws Exception{
		List<PayBillStatusDTO> keyList=null;
		try{
			keyList=payBillDAO.getPaySlipMonthList();
		}catch(Exception e){
			throw e;
		}
		return keyList;
	}
	public List<String> getPaySlipYearList()throws Exception{
		List<String> keyList=null;
		try{
			keyList=payBillDAO.getPaySlipYearList();
		}catch(Exception e){
			throw e;
		}
		return keyList;
	}
	public List<String> getPaySlipMonthList(String year)throws Exception{
		List<String> keyList=null;
		try{
			keyList=payBillDAO.getPaySlipMonthList(year);
		}catch(Exception e){
			throw e;
		}
		return keyList;
	}
public PayBillBean startRegeneratingPayBill(PayBillBean payBillBean) throws Exception {

	try {
		PayBillStatusDTO payBillStatusDTO = payBillDAO.startRegeneratingPayBill(payBillBean.getRunMonth(), payBillBean.getSfId());
		payBillBean.setRunStatus(payBillStatusDTO.getRunStatus());
		payBillBean.setManualStatus(payBillStatusDTO.getManualStatus());
		payBillBean.setUserStatus(payBillStatusDTO.getUserStatus());
		payBillBean.setMessage(payBillStatusDTO.getDescription());
		payBillBean.setCount(payBillStatusDTO.getCount());
		payBillBean.setGeneratedCount(payBillStatusDTO.getGeneratedCount());
	} catch (Exception e) {
		throw e;
	}
	return payBillBean;
}
	//supplimentary code
	public List<KeyValueDTO> getEmpSupplimentaryBillDetails(PayBillBean payBillBean) throws Exception{
		List<KeyValueDTO> empSuppList = null;
		try{
			empSuppList=payBillDAO.getEmpSupplimentaryBillDetails(payBillBean);
		}catch (Exception e) {
			throw e;
		}
		return empSuppList;
	}
	// to get month list of edit employee screen
	public List<String> getMonthListForEditEmployee(String year)throws Exception{
		List<String> keyList=null;
		try{
			keyList=payBillDAO.getMonthListForEditEmployee(year);
		}catch(Exception e){
			throw e;
		}
		return keyList;
	}
	// to get year list of edit employee screen
	public List<String> getYearListForEditEmployee()throws Exception{
		List<String> keyList=null;
		try{
			keyList=payBillDAO.getYearListForEditEmployee();
		}catch(Exception e){
			throw e;
		}
		return keyList;
	}
}
