package com.callippus.web.business.empPayment;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.EmpPayment.EmployeePaymentBean;
import com.callippus.web.beans.dto.EmpBasicPayHistoryDTO;
import com.callippus.web.beans.dto.EmpPaymentsDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.empPayment.IEmployeePaymentDAO;
import com.callippus.web.dao.paybill.IPayBillMasterDAO;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;
import com.callippus.web.promotions.beans.management.PromotionManagementBean;
import com.callippus.web.promotions.dto.EmpGradePayHistoryDTO;
import com.callippus.web.promotions.dto.PromoteesEntryDTO;

@Service
public class EmployeePaymentBusiness {
	@Autowired
	private IEmployeePaymentDAO empPaymentDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	public String insertEmpPaymentDetails(EmployeePaymentBean empPaymentBean) throws Exception {
		String message = null;
		try {
			/*message = commonDataDAO.checkEmployee(empPaymentBean.getSfid());
			if (CPSUtils.compareStrings(message, CPSConstants.SUCCESS)) {
				message = empPaymentDAO.insertEmpPaymentDetails(empPaymentBean);
			} else {
				message = CPSConstants.EMPNOTEXISTS;
			}*/
			message = empPaymentDAO.validateBasicSal(empPaymentBean.getSfid(),Float.parseFloat(empPaymentBean.getBasic()),empPaymentBean.getDesignationId());
			if(message.equals("YES")){
			if(empPaymentBean.getId()==0){
				String result = empPaymentDAO.checkDuplicateHistory(empPaymentBean);
				if(result.equalsIgnoreCase(CPSConstants.SUCCESS)){
					message = empPaymentDAO.insertEmpPaymentDetails(empPaymentBean);
				}else{
					message=CPSConstants.DUPLICATE;
					return message;
				}
				
			}else{
				message = empPaymentDAO.updateBasicPayHistory(empPaymentBean);
				message = CPSConstants.UPDATE;
			  }
			}else{
				message = "empPayBandFail";
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return message;
	}
	
	public List<EmpPaymentsDTO> getPaymentDetails() throws Exception{
		List<EmpPaymentsDTO> list=null;
		try{
			list=empPaymentDAO.getEmpPaymentDetails();
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	public List<EmployeeBean> validateSFID(String SFID) throws Exception{
		List<EmployeeBean> empList=null;
		try{
			empList=empPaymentDAO.validateSFID(SFID);
		}catch(Exception e){
			throw e;
		}
		return empList;
	}
	
	public EmployeePaymentBean getDetails(EmployeePaymentBean employeePaymentBean) throws Exception
	{
		try
		{
			empPaymentDAO.getDetails(employeePaymentBean);
		}catch(Exception e)
		{
			throw e;
		}
		return employeePaymentBean;
	}
	//start
	public List<EmpBasicPayHistoryDTO> getEmpBasicPayHistory(String sfid) throws Exception{
		List<EmpBasicPayHistoryDTO> list=null;
		try{
			list=empPaymentDAO.getEmpBasicPayHistory(sfid);
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	
	
	public List<EmpGradePayHistoryDTO> getOfflineEntryList(String sfid) throws Exception{
		List<EmpGradePayHistoryDTO> list=null;
		try{
			list=empPaymentDAO.getOfflineEntryList(sfid);
		}catch(Exception e){
			throw e;
		}
		return list;
	}
	
	public EmployeePaymentBean getDesignationList(EmployeePaymentBean empPaymentBean) throws Exception {
	try {
		empPaymentBean.setCatwiseDesig(empPaymentDAO.getCatwiseDesig(empPaymentBean.getSfid()));
		//added for scale
		List<PayScaleDesignationDTO> arrList = null;
		arrList = empPaymentDAO.getPayScaleDetailsList();
		for(int i=0; i<arrList.size(); i++){
			PayScaleDesignationDTO pdtoDesignationDTO = arrList.get(i);
			pdtoDesignationDTO.setId(pdtoDesignationDTO.getDesignationDetails().getId());
			pdtoDesignationDTO.setName(pdtoDesignationDTO.getPaybandDetails().getName() + "-" + pdtoDesignationDTO.getPaybandDetails().getRangeFrom() + "-"
							+ pdtoDesignationDTO.getPaybandDetails().getRangeTo());
			pdtoDesignationDTO.setGradePay(pdtoDesignationDTO.getGradePay());
		}
		empPaymentBean.setEmpPayScaleList(arrList);
	} catch (Exception e) {
		throw e;
	}	
	return empPaymentBean;
	}
	
	public EmployeePaymentBean deleteRecord(EmployeePaymentBean employeePaymentBean)throws Exception{
		try {
			employeePaymentBean.setResult(empPaymentDAO.deleteRecord(employeePaymentBean));
		} catch (Exception e) {
			throw e;
		}
		return employeePaymentBean;
	}
	
	
	
	
	public EmployeePaymentBean retrieveFamilyCatDesigList(EmployeePaymentBean employeePaymentBean) throws Exception {
		try {
			//promotionBean.setResult(commonDAO.checkEmployee(promotionBean.getRefSfid()));
			
			
			employeePaymentBean.setCatwiseDesig(empPaymentDAO.getCatwiseDesig(employeePaymentBean.getRefSfid()));
				
			employeePaymentBean.setResult("");
				//added for scale
				List<PayScaleDesignationDTO> arrList = null;
			
				arrList = empPaymentDAO.getPayScaleDetailsList();
				
				employeePaymentBean.setEmpPayScaleList(arrList);
				
			
		} catch (Exception e) {
			throw e;
		}
		return employeePaymentBean;
	}
	
	
	
	
	public String getBasicPayValue(EmployeePaymentBean employeePaymentBean) throws Exception {
		String basicValue = "";
		try{
			
			basicValue = empPaymentDAO.getBasicPay(employeePaymentBean);
				
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return basicValue;
	}

	
	
	public List<PromoteesEntryDTO> getOfflineEntryList(EmployeePaymentBean employeePaymentBean) throws Exception {
		try {
			employeePaymentBean.setOfflineEntryList(empPaymentDAO.getOfflineEntryList(employeePaymentBean));
		} catch (Exception e) {
			throw e;
		}
		return employeePaymentBean.getOfflineEntryList();
	}
	
	
	
	
	public EmployeePaymentBean submitOfflineEntry(EmployeePaymentBean employeePaymentBean) throws Exception {
		try {
			employeePaymentBean.setResult(empPaymentDAO.checkDuplicateOfflineEntry(employeePaymentBean));
			if (!CPSUtils.compareStrings(employeePaymentBean.getResult(), CPSConstants.DUPLICATE)) {
				employeePaymentBean.setResult(empPaymentDAO.saveOfflineEntry(employeePaymentBean));
			} else if (CPSUtils.compareStrings(employeePaymentBean.getResult(), CPSConstants.DUPLICATE)
					&& !CPSUtils.isNullOrEmpty(employeePaymentBean.getNodeID())) {
				employeePaymentBean.setResult(empPaymentDAO.saveOfflineEntry(employeePaymentBean));
			} else
				employeePaymentBean.setResult(CPSConstants.DUPLICATE);
		} catch (Exception e) {
			throw e;
		}
		return employeePaymentBean;

	}
	
	
	
	
	public EmployeePaymentBean deleteRecordPay(EmployeePaymentBean employeePaymentBean) throws Exception {
		try {
			employeePaymentBean.setResult(empPaymentDAO.deleteRecordPay(employeePaymentBean));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return employeePaymentBean;
	}
	public String getVariableIncrementValue(EmployeePaymentBean employeePaymentBean) throws Exception {
		String varIncValue = "";
		try{
			if(employeePaymentBean.getVarIncVal()!=null){
				 varIncValue = empPaymentDAO.getVarIncr(Integer.parseInt(employeePaymentBean.getNewGradePay()), employeePaymentBean.getDesignationFrom());
				 varIncValue = varIncValue.split("@")[1];
			}	
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return varIncValue;
	}
	
	
	
}
