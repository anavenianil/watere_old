package com.callippus.web.business.paybill;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.employee.EmployeeBean;
import com.callippus.web.beans.paybill.PayBillMasterBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.paybill.IPayBillMasterDAO;
import com.callippus.web.paybill.dto.PayBillCGEISMasterDTO;
import com.callippus.web.paybill.dto.PayBillCanfinDTO;
import com.callippus.web.paybill.dto.PayBillCcsDTO;
import com.callippus.web.paybill.dto.PayBillDTO;
import com.callippus.web.paybill.dto.PayBillDearnessAllowanceMasterDTO;
import com.callippus.web.paybill.dto.PayBillDuesDTO;
import com.callippus.web.paybill.dto.PayBillEmpCategoryDTO;
import com.callippus.web.paybill.dto.PayBillEmpPaymentDeatilsDTO;
import com.callippus.web.paybill.dto.PayBillLoanDTO;
import com.callippus.web.paybill.dto.PayHindiInceDTO;
import com.callippus.web.paybill.dto.PayQuarterManagementDTO;
import com.callippus.web.paybill.dto.PayScaleDesignationDTO;

@Service
public class PayBillMasterBusiness {
	@Autowired
	private IPayBillMasterDAO payBillMasterDAO;
	@Autowired
	private IComonObjectDAO commonObjectDAO;
	
	public String getCurrentRunMonth() throws Exception
	{
		String runMonth = "";
			try
			{
				runMonth = payBillMasterDAO.getCurrentRunMonth();
				runMonth += " Not started!";
			}catch(Exception e)
			{
				throw e;
			}	
		return runMonth;
	}
	@SuppressWarnings("unchecked")
	public PayBillMasterBean getPayBillCGHSDetails(PayBillMasterBean payBillMasterBean) throws Exception 
	{
		try 
		{
			payBillMasterBean.setGradePayList(payBillMasterDAO.getGradePayList(payBillMasterBean));
			payBillMasterBean.setCghsMasterDetails(commonObjectDAO.getMasterData(CPSConstants.PAYBILLCGHSMASTERDTO));
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}

	@SuppressWarnings("unchecked")
	public PayBillMasterBean managePayBillCGHSDetails(PayBillMasterBean payBillMasterBean) throws Exception 
	{
		try
		{
			payBillMasterBean = payBillMasterDAO.submitPayBillCGHSDetails(payBillMasterBean);
			payBillMasterBean.setCghsMasterDetails(commonObjectDAO.getMasterData(CPSConstants.PAYBILLCGHSMASTERDTO));
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}
  
	public PayBillMasterBean deletePayBillMasterRecord(PayBillMasterBean payBillMasterBean) throws Exception 
	{
		try 
		{
			payBillMasterBean.setResult(payBillMasterDAO.deletePayBillMasterRecord(payBillMasterBean));
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}

	@SuppressWarnings("unchecked")
	public PayBillMasterBean getPayBillCGEISDetails(PayBillMasterBean payBillMasterBean) throws Exception 
	{
		try 
		{
			payBillMasterBean.setCgeisMasterDetails(commonObjectDAO.getMasterData(CPSConstants.PAYBILLCGEISMASTERDTO));
			payBillMasterBean.setPayBillGroupMasterList(commonObjectDAO.getMasterData(CPSConstants.PAYBILLGROUPMASTERDTO));
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}

	@SuppressWarnings("unchecked")
	public PayBillMasterBean managePayBillCGEISDetails(PayBillMasterBean payBillMasterBean) throws Exception {
		try {
			// Duplicate checking
			payBillMasterBean.setResult(commonObjectDAO.checkDuplicate(CPSConstants.PAY_CGEIS_MASTER, CPSConstants.GROUP_ID, payBillMasterBean.getGroupId(), payBillMasterBean.getNodeID()));
			if (CPSUtils.compareStrings(payBillMasterBean.getResult(), CPSConstants.SUCCESS))
			{
				PayBillCGEISMasterDTO payBillCGEISDTO = new PayBillCGEISMasterDTO();
				if (CPSUtils.isNullOrEmpty(payBillMasterBean.getNodeID())) // New
				{
					payBillCGEISDTO.setCreatedBy(payBillMasterBean.getSfID());
					payBillCGEISDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
					payBillCGEISDTO.setModifiedDate(payBillCGEISDTO.getCreationDate());
					payBillCGEISDTO.setAfterMember(Float.parseFloat(payBillMasterBean.getAfterMember()));
					payBillCGEISDTO.setBeforeMember(Float.parseFloat(payBillMasterBean.getBeforeMember()));
					payBillCGEISDTO.setGroupId(Integer.parseInt(payBillMasterBean.getGroupId()));
					payBillCGEISDTO.setGroupInsuranceDate(payBillMasterBean.getGroupInsuranceDate());
				}
				else // Update
				{
					payBillCGEISDTO.setModifiedDate(CPSUtils.getCurrentDateWithTime());
					payBillCGEISDTO.setPk(Integer.valueOf(payBillMasterBean.getNodeID()));
					payBillCGEISDTO.setAfterMember(Float.parseFloat(payBillMasterBean.getAfterMember()));
					payBillCGEISDTO.setBeforeMember(Float.parseFloat(payBillMasterBean.getBeforeMember()));
					payBillCGEISDTO.setGroupInsuranceDate(payBillMasterBean.getGroupInsuranceDate());
					payBillCGEISDTO.setGroupId(Integer.parseInt(payBillMasterBean.getGroupId()));
				}
				payBillCGEISDTO.setModifiedBy(payBillMasterBean.getSfID());
				payBillCGEISDTO.setStatus(1);
				payBillMasterBean.setResult(payBillMasterDAO.submitPayBillCGEISDetails(payBillCGEISDTO)); // Submit
				payBillMasterBean.setCgeisMasterDetails(commonObjectDAO.getMasterData(CPSConstants.PAYBILLCGEISMASTERDTO));
			//nagendra.v
				}else{
				payBillMasterBean.setCgeisMasterDetails(commonObjectDAO.getMasterData(CPSConstants.PAYBILLCGEISMASTERDTO));
			}
			
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}

	@SuppressWarnings("unchecked")
	public PayBillMasterBean getPayBillDearNessAllowanceDetails(PayBillMasterBean payBillMasterBean) throws Exception {
		try {
			payBillMasterBean.setDearNessAllowanceMasterDetails(commonObjectDAO.getMasterData(CPSConstants.PAYBILLDEARNESSALLOWANCEMASTERDTO));
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}

	@SuppressWarnings("unchecked")
	public PayBillMasterBean managePayBillDearNessAllowanceDetails(PayBillMasterBean payBillMasterBean) throws Exception {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			// Duplicate checking
			payBillMasterBean.setResult(commonObjectDAO.checkDuplicate(CPSConstants.PAY_DEARNESS_ALLOWANCE_MASTER, CPSConstants.DA_DATE, sdf.format(payBillMasterBean.getDaDate()).toString(),
					payBillMasterBean.getNodeID()));
			if (CPSUtils.compareStrings(payBillMasterBean.getResult(), CPSConstants.SUCCESS)) {
				PayBillDearnessAllowanceMasterDTO payBillDearnessAllowanceMasterDTO = new PayBillDearnessAllowanceMasterDTO();

				if (CPSUtils.isNullOrEmpty(payBillMasterBean.getNodeID())) {
					// New
					payBillDearnessAllowanceMasterDTO.setCreatedBy(payBillMasterBean.getSfID());
					payBillDearnessAllowanceMasterDTO.setCreationDate(CPSUtils.getCurrentDateWithTime());
					payBillDearnessAllowanceMasterDTO.setModifiedDate(payBillDearnessAllowanceMasterDTO.getCreationDate());
					payBillDearnessAllowanceMasterDTO.setDaDate(payBillMasterBean.getDaDate());
					payBillDearnessAllowanceMasterDTO.setDaValue(Float.parseFloat(payBillMasterBean.getDaValue()));
				} else {
					// Update
					payBillDearnessAllowanceMasterDTO.setModifiedDate(CPSUtils.getCurrentDateWithTime());
					payBillDearnessAllowanceMasterDTO.setPk(Integer.valueOf(payBillMasterBean.getNodeID()));
					payBillDearnessAllowanceMasterDTO.setDaDate(payBillMasterBean.getDaDate());
					payBillDearnessAllowanceMasterDTO.setDaValue(Float.parseFloat(payBillMasterBean.getDaValue()));
					
				}
				payBillDearnessAllowanceMasterDTO.setModifiedBy(payBillMasterBean.getSfID());
				payBillDearnessAllowanceMasterDTO.setStatus(1);
				// Submit
				payBillMasterBean.setResult(payBillMasterDAO.submitPayBillDearNessAllowanceDetails(payBillDearnessAllowanceMasterDTO));

				payBillMasterBean.setDearNessAllowanceMasterDetails(commonObjectDAO.getMasterData(CPSConstants.PAYBILLDEARNESSALLOWANCEMASTERDTO));

			}else{
				//nagendra.v
				payBillMasterBean.setDearNessAllowanceMasterDetails(commonObjectDAO.getMasterData(CPSConstants.PAYBILLDEARNESSALLOWANCEMASTERDTO));
				
			}
			
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}

	@SuppressWarnings("unchecked")
	public PayBillMasterBean getPayBillResidentialSecurityDetails(PayBillMasterBean payBillMasterBean) throws Exception
	{
		try
		{
			payBillMasterBean.setResidentialSecurityMasterDeatails(commonObjectDAO.getMasterData(CPSConstants.PAYBILLRESIDENTIALSECURITYMASTERDTO));
			payBillMasterBean.setPayBillQuartersTypeMasterList(commonObjectDAO.getMasterData(CPSConstants.PAYBILLQUARTERTYPEMASTERDTO));
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}

	@SuppressWarnings("unchecked")
	public PayBillMasterBean managePayBillResidentialSecurityDetails(PayBillMasterBean payBillMasterBean) throws Exception
	{
		try
		{
			payBillMasterBean = payBillMasterDAO.submitPayBillResidentialSecurityDetails(payBillMasterBean);
			payBillMasterBean.setResidentialSecurityMasterDeatails(commonObjectDAO.getMasterData(CPSConstants.PAYBILLRESIDENTIALSECURITYMASTERDTO));
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}

	public String uploadCCSDetails(String filePath, String sfid) throws Exception {
		String message = "";
		try {
			Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath)); // or sample.xls
			// System.out.println("Number Of Sheets" + workbook.getNumberOfSheets());
			Sheet sheet = workbook.getSheetAt(0);
			// System.out.println("Number Of Rows:" + sheet.getLastRowNum());
			ArrayList<PayBillCcsDTO> arrList = new ArrayList<PayBillCcsDTO>();
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				String msno = validateCell(row.getCell(0));
				String idno = validateCell(row.getCell(1));
				String name = validateCell(row.getCell(2));
				float thrift_value = CPSUtils.convertToFloat(validateCell(row.getCell(3)));
				float loanIns_value = CPSUtils.convertToFloat(validateCell(row.getCell(4)));
				float loanInst_value = CPSUtils.convertToFloat(validateCell(row.getCell(5)));
				float loanBal_value = CPSUtils.convertToFloat(validateCell(row.getCell(6)));
				float rd_value = CPSUtils.convertToFloat(validateCell(row.getCell(7)));
				float emlIns_value = CPSUtils.convertToFloat(validateCell(row.getCell(8)));
				float emlInst_value = CPSUtils.convertToFloat(validateCell(row.getCell(9)));
				float emlBal_value = CPSUtils.convertToFloat(validateCell(row.getCell(10)));
				float misc_value = CPSUtils.convertToFloat(validateCell(row.getCell(11)));
				float total_value = CPSUtils.convertToFloat(validateCell(row.getCell(12)));

				PayBillCcsDTO ccsDTO = new PayBillCcsDTO();
				if (idno != null && !idno.equalsIgnoreCase("0")) {
					int id = payBillMasterDAO.getPresentIDDetails(idno);
					if (id != 0)
						ccsDTO.setId(id);
				}
				ccsDTO.setMsno(msno);
				ccsDTO.setSfid(idno);
				ccsDTO.setName(name);
				ccsDTO.setThrift(thrift_value);
				ccsDTO.setLoan(loanIns_value);
				ccsDTO.setLoanInst(loanInst_value);
				ccsDTO.setLoanBal(loanBal_value);
				ccsDTO.setRd(rd_value);
				ccsDTO.setEml(emlIns_value);
				ccsDTO.setEmlInst(emlInst_value);
				ccsDTO.setEmlBal(emlBal_value);
				ccsDTO.setMisc(misc_value);
				ccsDTO.setTotal(total_value);
				ccsDTO.setStatus(0);
				ccsDTO.setCreatedBy(sfid);
				ccsDTO.setCreationDate(CPSUtils.getCurrentDate());
				ccsDTO.setLastModifiedBy(sfid);
				ccsDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
				if (idno != null && !idno.equalsIgnoreCase("0"))
					arrList.add(ccsDTO);
			}

			if (arrList.size() > 0) {
				message = payBillMasterDAO.submitPayBillCcsDetails(arrList);

			}
			// System.out.println("Cell Value:" + row.getCell(0).getStringCellValue());

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String validateCell(Cell c) throws Exception {
		String retValue = "0";
		if (c != null) {
			switch (c.getCellType()) {
			case 0:

				try {
					retValue = "" + c.getNumericCellValue();
				} catch (Exception e) {
				}
				break;
			case 1:
				try {
					retValue = c.getStringCellValue();
				} catch (Exception e) {
				}
				break;
			case 2:
				try {
					retValue = "" + c.getCachedFormulaResultType();
				} catch (Exception e) {
				}
				break;
			case 3:
			case 4:
			case 5:
				break;

			}
		}
		return retValue;
	}

	public PayBillMasterBean getCoinCutDeductionMasterDetails(PayBillMasterBean payBillMasterBean) throws Exception {

		try {

			String message = payBillMasterDAO.getEmpExist(payBillMasterBean.getSfidSearchKey().toUpperCase());

			if (message != null && message.equalsIgnoreCase(CPSConstants.YES)) {
				EmployeeBean empBean=payBillMasterDAO.getEmployeeDetails(payBillMasterBean.getSfidSearchKey().toUpperCase());
				payBillMasterBean.setSfID(empBean.getSfid());
				payBillMasterBean.setDivisionName(empBean.getDivisionName());
				payBillMasterBean.setDesignationName(empBean.getDesignationName());
				payBillMasterBean.setNameInServiceBook(empBean.getName());
				payBillMasterBean.setMessage(CPSConstants.YES);
				payBillMasterBean.setEmpID(payBillMasterBean.getSfidSearchKey().toUpperCase());
				payBillMasterBean.setCoinCutMasterDetails(payBillMasterDAO.getDeductionMasterDeatails());
				payBillMasterBean.setEmpCCList(payBillMasterDAO.getEmpCCDetails(payBillMasterBean.getSfidSearchKey().toUpperCase()));
			} else {
				payBillMasterBean.setMessage(CPSConstants.EMPNOTEXISTS);
			}

		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}

	public PayBillMasterBean submitCanfinDetails(PayBillMasterBean payBillMasterBean) throws Exception {
		try {
			PayBillCanfinDTO canfinDTO = new PayBillCanfinDTO();

			if (payBillMasterDAO.getEmpExist(payBillMasterBean.getEmpID().toUpperCase()).equalsIgnoreCase(CPSConstants.NO)) {
				payBillMasterBean.setMessage(CPSConstants.EMPNOTEXISTS);
			} else {

				if (payBillMasterBean.getType() != null && payBillMasterBean.getType().equalsIgnoreCase("edit")) {

					if (payBillMasterDAO.verfiyCCEditDetailsExist(Integer.parseInt(payBillMasterBean.getPk()), Integer.parseInt(payBillMasterBean.getDeductionID())).equalsIgnoreCase(CPSConstants.YES)) {
						canfinDTO.setId(Integer.parseInt(payBillMasterBean.getPk()));
						canfinDTO.setAmount(Float.parseFloat(payBillMasterBean.getAmount()));
						canfinDTO.setEffDate(new SimpleDateFormat("dd-MMM-yyyy").parse(payBillMasterBean.getEffDate()));
						canfinDTO.setSfid(payBillMasterBean.getEmpID().toUpperCase());
						canfinDTO.setPresentInst(payBillMasterBean.getPresentInst());
						canfinDTO.setCreatedBy(payBillMasterBean.getSfID());
						canfinDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
						canfinDTO.setLastModifiedBy(payBillMasterBean.getSfID());
						canfinDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						canfinDTO.setNoOfInst(Integer.parseInt(payBillMasterBean.getInst()));
						canfinDTO.setDeductionID(Integer.parseInt(payBillMasterBean.getDeductionID()));
						canfinDTO.setStatus(1);
						String message = payBillMasterDAO.updateCanfinDetails(canfinDTO);
						payBillMasterBean.setMessage(message);
						payBillMasterBean.setEmpCCList(payBillMasterDAO.getEmpCCDetails(payBillMasterBean.getEmpID().toUpperCase()));
					} else {
						payBillMasterBean.setMessage(CPSConstants.CONSTRAINTS);
						payBillMasterBean.setRemarks("Deduction details already Exists");
						payBillMasterBean.setEmpCCList(payBillMasterDAO.getEmpCCDetails(payBillMasterBean.getEmpID().toUpperCase()));

					}
				} else {
					if (payBillMasterDAO.verfiyCCDetailsExist(payBillMasterBean.getEmpID().toUpperCase(), payBillMasterBean.getDeductionID()).equalsIgnoreCase(CPSConstants.NO)) {
						canfinDTO.setAmount(Float.parseFloat(payBillMasterBean.getAmount()));
						canfinDTO.setEffDate(new SimpleDateFormat("dd-MMM-yyyy").parse(payBillMasterBean.getEffDate()));
						canfinDTO.setSfid(payBillMasterBean.getEmpID().toUpperCase());
						canfinDTO.setPresentInst(payBillMasterBean.getPresentInst());
						canfinDTO.setCreatedBy(payBillMasterBean.getSfID());
						canfinDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
						canfinDTO.setLastModifiedBy(payBillMasterBean.getSfID());
						canfinDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
						canfinDTO.setNoOfInst(Integer.parseInt(payBillMasterBean.getInst()));
						canfinDTO.setDeductionID(Integer.parseInt(payBillMasterBean.getDeductionID()));
						canfinDTO.setStatus(1);
						String message = payBillMasterDAO.updateCanfinDetails(canfinDTO);
						payBillMasterBean.setMessage(message);
						payBillMasterBean.setEmpCCList(payBillMasterDAO.getEmpCCDetails(payBillMasterBean.getEmpID().toUpperCase()));
					} else {
						payBillMasterBean.setMessage(CPSConstants.CONSTRAINTS);
						payBillMasterBean.setRemarks("Deduction details already Exists");
						payBillMasterBean.setEmpCCList(payBillMasterDAO.getEmpCCDetails(payBillMasterBean.getEmpID().toUpperCase()));

					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}

	public PayBillMasterBean deleteEmpCCDetails(PayBillMasterBean payBillMasterBean) throws Exception {

		try {
			PayBillCanfinDTO canfinDTO = new PayBillCanfinDTO();
			canfinDTO.setId(Integer.parseInt(payBillMasterBean.getPk()));
			canfinDTO.setLastModifiedBy(payBillMasterBean.getSfID());
			canfinDTO.setSfid(payBillMasterBean.getEmpID());
			payBillMasterBean.setMessage(payBillMasterDAO.deleteEmpCCDetails(canfinDTO));
			payBillMasterBean.setEmpCCList(payBillMasterDAO.getEmpCCDetails(payBillMasterBean.getEmpID().toUpperCase()));
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}

	public String setEmpCategoryMasterDetails(PayBillMasterBean payBillMasterBean) throws Exception 
	{
		String message = "";
		try 
		{
			PayBillEmpCategoryDTO cDTO = new PayBillEmpCategoryDTO();
			payBillMasterBean.setMessage(payBillMasterDAO.checkEmpCategoryDetailsList(payBillMasterBean));
			if(!CPSUtils.compareStrings(payBillMasterBean.getMessage(), CPSConstants.DUPLICATE))
			{
				if(!CPSUtils.isNullOrEmpty(payBillMasterBean.getPk()))
					cDTO.setId(Integer.parseInt(payBillMasterBean.getPk()));
				cDTO.setName(payBillMasterBean.getCategoryName());
				cDTO.setPayOrderNo(payBillMasterBean.getPayOrderNo());
				cDTO.setPayBillType(payBillMasterBean.getPayBillType());
				//cDTO.setEffDate(payBillMasterBean.getEffDate());
				cDTO.setCreatedBy(payBillMasterBean.getSfID());
				cDTO.setCreationDate(CPSUtils.getCurrentDate());
				cDTO.setLastModifiedBy(payBillMasterBean.getSfID());
				cDTO.setLastModifiedDate(CPSUtils.getCurrentDate()); 
				cDTO.setStatus(1);
				
				message = payBillMasterDAO.insertEmpCategoryMasterDetails(cDTO);
				if(!CPSUtils.isNullOrEmpty(payBillMasterBean.getPk()))
					message = CPSConstants.UPDATE;
			}
			else
				message = CPSConstants.DUPLICATE;
			
			
			
		/*	if (payBillMasterBean.getType() != null && payBillMasterBean.getType().equalsIgnoreCase("edit"))
			cDTO.setName(payBillMasterBean.getCategoryName());
			cDTO.setPayOrderNo(payBillMasterBean.getPayOrderNo());
			cDTO.setPayBillType(payBillMasterBean.getPayBillType());
			cDTO.setEffDate(payBillMasterBean.getEffDate());
			cDTO.setCreatedBy(payBillMasterBean.getSfID());
			cDTO.setCreationDate(CPSUtils.getCurrentDate());
			cDTO.setLastModifiedBy(payBillMasterBean.getSfID());
			cDTO.setLastModifiedDate(CPSUtils.getCurrentDate()); 
			cDTO.setStatus(1);
			message=payBillMasterDAO.checkEmpCategoryDetailsList(payBillMasterBean);
			if(!CPSUtils.compareStrings(message, CPSConstants.DUPLICATE))
			message = payBillMasterDAO.insertEmpCategoryMasterDetails(cDTO,payBillMasterBean);	*/
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	/*private String checkEmpCategoryDetailsList(PayBillEmpCategoryDTO cDTO) throws Exception {
		String message = null;
		try {

			if (CPSUtils.checkList(payBillMasterDAO.checkEmpCategoryDetailsList(cDTO))) {
				message = CPSConstants.DUPLICATE;
			} else {
				message = CPSConstants.SUCCESS;
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;

		}
		return message;
	}*/
	public List<PayBillEmpCategoryDTO> getEmpCategoryDetailsList() throws Exception {
		List<PayBillEmpCategoryDTO> arrList = null;
		try {
			arrList = payBillMasterDAO.getEmpCategoryDetailsList();

		} catch (Exception e) {
			throw e;
		}
		return arrList;
	}

	public String deleteEmpCategoryMasterDetails(PayBillMasterBean payBillMasterBean) throws Exception {

		String message = "";
		try {
			PayBillEmpCategoryDTO cDTO = new PayBillEmpCategoryDTO();
			cDTO.setId(Integer.parseInt(payBillMasterBean.getPk()));
			cDTO.setLastModifiedBy(payBillMasterBean.getSfID());
			message = payBillMasterDAO.deleteEmpCategoryMasterDetails(cDTO);

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public PayBillMasterBean getEmpDetails(PayBillMasterBean payBillMasterBean) throws Exception {

		try {
			//String message = payBillMasterDAO.getEmpExist(payBillMasterBean.getSfidSearchKey().toUpperCase());
			String message = payBillMasterDAO.getPayEmpExist(payBillMasterBean.getSfidSearchKey().toUpperCase());
			
			if (message != null && message.equalsIgnoreCase(CPSConstants.YES)) {
				List<PayScaleDesignationDTO> arrList = null;
				arrList = payBillMasterDAO.getPayScaleDetailsList();
				ArrayList<DesignationDTO> designationList = new ArrayList<DesignationDTO>();
				ArrayList<PayScaleDesignationDTO> payScaleList = new ArrayList<PayScaleDesignationDTO>();
				//List<PayBillMasterBean> gradepayList = payBillMasterDAO.getGradePayList(payBillMasterBean);
				payBillMasterBean.setGradePayList(payBillMasterDAO.getGradePayList(payBillMasterBean));
				for (int i = 0; i < arrList.size(); i++) {
					PayScaleDesignationDTO pdtoDesignationDTO = arrList.get(i);
					DesignationDTO desgDTO = new DesignationDTO();
					desgDTO.setId(pdtoDesignationDTO.getDesignationDetails().getId());
					desgDTO.setDesignation(pdtoDesignationDTO.getDesignationDetails().getName());
					//System.out.println("desigantion name"+desgDTO.getName()+"Gruoup name"+desgDTO.getGroupName());
					//System.out.println("designation dto"+desgDTO);
					designationList.add(desgDTO);

					PayScaleDesignationDTO payscaleDTO = new PayScaleDesignationDTO();
					payscaleDTO.setId(pdtoDesignationDTO.getDesignationDetails().getId());
					payscaleDTO.setGradePay(pdtoDesignationDTO.getGradePay());
					payscaleDTO.setName(pdtoDesignationDTO.getPaybandDetails().getName() + "-" + pdtoDesignationDTO.getPaybandDetails().getRangeFrom() + "-"
							+ pdtoDesignationDTO.getPaybandDetails().getRangeTo());
					//System.out.println("payscale designation"+payscaleDTO.getDesignation()+"payband"+payscaleDTO.getPayband());
					//System.out.println("payscale dto value"+payscaleDTO);
					
					payScaleList.add(payscaleDTO);
				}

				payBillMasterBean.setPayscaleList(payScaleList);
				payBillMasterBean.setDesignationList(designationList);
				payBillMasterBean.setPayBillGroupMasterList(commonObjectDAO.getMasterData(CPSConstants.PAYBILLGROUPMASTERDTO));
				payBillMasterBean.setQuarterDetailsList(payBillMasterDAO.getQuarterDetails());

				payBillMasterBean.setHandicapList(commonObjectDAO.getMasterData(CPSConstants.PHTYPEDTO));
				PayBillEmpPaymentDeatilsDTO payBillEmpPaymentDeatilsDTO = new PayBillEmpPaymentDeatilsDTO();
				payBillEmpPaymentDeatilsDTO = payBillMasterDAO.getEmpDetails(payBillMasterBean.getSfidSearchKey().toUpperCase());
				 if(!CPSUtils.isNullOrEmpty(payBillEmpPaymentDeatilsDTO)){
				String sfid = payBillMasterBean.getSfID();
				BeanUtils.copyProperties(payBillMasterBean, payBillEmpPaymentDeatilsDTO);
				payBillMasterBean.setSfID(sfid);
               
				EmployeeBean empBean = payBillMasterDAO.getEmployeeDetails(payBillMasterBean.getSfidSearchKey().toUpperCase());
				payBillMasterBean.setGpfAccountNo(empBean.getGpfAcNo());
				payBillMasterBean.setFpaFlag(empBean.getFamPlanning());
				payBillMasterBean.setDojGovt(empBean.getDojGovt());
				payBillMasterBean.setDob(empBean.getDob());
				payBillMasterBean.setHandicap("" + empBean.getHandicap());
				payBillMasterBean.setGender(empBean.getGender());
				payBillMasterBean.setPayDesignationId(""+payBillEmpPaymentDeatilsDTO.getPayDesignationId());
				payBillMasterBean.setPayscaleId(""+payBillEmpPaymentDeatilsDTO.getPayDesignationId());
				payBillMasterBean.setGradePay(empBean.getGradePay());
				payBillMasterBean.setDivisionName(empBean.getDivisionName());
				payBillMasterBean.setNameInServiceBook(empBean.getName());
				payBillMasterBean.setDesignationName(empBean.getDesignationName());
				payBillMasterBean.setEmpID(payBillMasterBean.getSfidSearchKey().toUpperCase());
				payBillMasterBean.setRetirementDate(empBean.getRetirementDate());
				payBillMasterBean.setSeniorityDate(empBean.getSeniorityDate());
				payBillMasterBean.setReservation(empBean.getReservation());
				payBillMasterBean.setMember(payBillMasterDAO.checkGPFmember(payBillMasterBean.getSfidSearchKey().toUpperCase()));
				if(payBillMasterBean.getMember().equals("Yes")){
					String gpfRange=Math.round(((payBillMasterBean.getBasicPay()+payBillMasterBean.getgPay())*6)/100)+"-"+(payBillMasterBean.getBasicPay()+payBillMasterBean.getgPay());
			    	 payBillMasterBean.setGpfRange(gpfRange);
				}
				payBillMasterBean.setWorkLocation(empBean.getWorkingLocation());
				PayQuarterManagementDTO pqmDTO = payBillMasterDAO.getEmpQuarterDetails(payBillMasterBean.getSfidSearchKey().toUpperCase());
					if (!CPSUtils.isNull(pqmDTO)) {
						payBillMasterBean.setQuartersFlag(CPSConstants.YES);
						payBillMasterBean.setQuarterNo(pqmDTO.getQuarterNo());
						payBillMasterBean.setQuarterTypeId(pqmDTO.getQuartersType());
					}
				
                }else{
					payBillMasterBean.setMessage(CPSConstants.PAYDETAILSNOTEXISTS);
					payBillMasterBean.setRemarks("employee pay details not exists");
				}
			} else {
				payBillMasterBean.setMessage(CPSConstants.EMPNOTEXISTS);
			}
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;

	}

	@SuppressWarnings("unchecked")
	public PayBillMasterBean getPayScaleDetailsList(PayBillMasterBean payBillMasterBean) throws Exception {
		try {

			payBillMasterBean.setPayBillGroupMasterList(commonObjectDAO.getMasterData(CPSConstants.PAYBILLGROUPMASTERDTO));
			payBillMasterBean.setQuarterDetailsList(payBillMasterDAO.getQuarterDetails());
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}

	public PayBillMasterBean getEmpLoanDetails(PayBillMasterBean payBillMasterBean) throws Exception {

		try {
			String message = payBillMasterDAO.getEmpExist(payBillMasterBean.getSfidSearchKey().toUpperCase());

			if (message != null && message.equalsIgnoreCase(CPSConstants.YES)) {
				EmployeeBean empBean=payBillMasterDAO.getEmployeeDetails(payBillMasterBean.getSfidSearchKey().toUpperCase());
				payBillMasterBean.setSfID(empBean.getSfid());
				payBillMasterBean.setDivisionName(empBean.getDivisionName());
				payBillMasterBean.setDesignationName(empBean.getDesignationName());
				payBillMasterBean.setNameInServiceBook(empBean.getName());
				payBillMasterBean.setMessage(CPSConstants.YES);
				payBillMasterBean.setEmpID(payBillMasterBean.getSfidSearchKey().toUpperCase());
				payBillMasterBean.setLoanMasterList(payBillMasterDAO.getLoanMasterList());
				payBillMasterBean.setEmpLoanList(payBillMasterDAO.getEmpLoanDetails(payBillMasterBean.getSfidSearchKey().toUpperCase()));
			} else {
				payBillMasterBean.setMessage(CPSConstants.EMPNOTEXISTS);
			}
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;

	}

	public PayBillMasterBean updateEmpLoanDetails(PayBillMasterBean payBillMasterBean) throws Exception {
		try {

			PayBillLoanDTO pblDTO = new PayBillLoanDTO();

			if (payBillMasterBean.getType() != null && payBillMasterBean.getType().equalsIgnoreCase("edit")) {
				pblDTO.setId(Integer.parseInt(payBillMasterBean.getPk()));
				pblDTO.setLoanRefId(Integer.parseInt(payBillMasterBean.getLoanRefId()));
				pblDTO.setReferenceId(Integer.parseInt(payBillMasterBean.getReferenceId()));
				pblDTO.setRunId(Integer.parseInt(payBillMasterBean.getRunId()));
				pblDTO.setSfid(payBillMasterBean.getEmpID());
				pblDTO.setEmi(payBillMasterBean.getEmi());
				pblDTO.setOutStandAmt(Integer.parseInt(payBillMasterBean.getOutStandAmt()));
				pblDTO.setLoanDeduType(payBillMasterBean.getLoanDeduType());
				pblDTO.setLoanType(payBillMasterBean.getLoanType());
				pblDTO.setPresentInst(payBillMasterBean.getPresentInst());
				pblDTO.setTotalAmt(payBillMasterBean.getTotalAmt());
				pblDTO.setTotalInst(payBillMasterBean.getTotalInst());
				pblDTO.setRecoveryStartDate(payBillMasterBean.getRecoveryStartDate());
				pblDTO.setStatus(1);
				pblDTO.setCreatedBy(payBillMasterBean.getSfID());
				pblDTO.setLastModifiedBy(payBillMasterBean.getSfID());
				pblDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				pblDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				pblDTO.setInterestRate(payBillMasterBean.getInterestRate());
				String message = payBillMasterDAO.verfiyLoanEditDetailsExist(pblDTO.getId(), pblDTO.getLoanType());
				if (CPSUtils.compareStrings(CPSConstants.NO, message)) {
					payBillMasterBean.setMessage(CPSConstants.DUPLICATE);

				} else {
					payBillMasterBean.setMessage(payBillMasterDAO.updateEmpLoanDetails(pblDTO));
				}
			} else {

				pblDTO.setSfid(payBillMasterBean.getEmpID());
				pblDTO.setEmi(payBillMasterBean.getEmi());
				pblDTO.setOutStandAmt(Integer.parseInt(payBillMasterBean.getOutStandAmt()));
				pblDTO.setLoanDeduType(payBillMasterBean.getLoanDeduType());
				pblDTO.setLoanType(payBillMasterBean.getLoanType());
				pblDTO.setPresentInst(payBillMasterBean.getPresentInst());
				pblDTO.setTotalAmt(payBillMasterBean.getTotalAmt());
				pblDTO.setTotalInst(payBillMasterBean.getTotalInst());
				pblDTO.setStatus(1);
				pblDTO.setCreatedBy(payBillMasterBean.getSfID());
				pblDTO.setLastModifiedBy(payBillMasterBean.getSfID());
				pblDTO.setCreationTime(CPSUtils.getCurrentDateWithTime());
				pblDTO.setLastModifiedTime(CPSUtils.getCurrentDateWithTime());
				pblDTO.setRecoveryStartDate(payBillMasterBean.getRecoveryStartDate());
				pblDTO.setInterestRate(payBillMasterBean.getInterestRate());
				
				String message = payBillMasterDAO.getEmpLoanExist(pblDTO);
				if (CPSUtils.compareStrings(CPSConstants.YES, message)) {
					payBillMasterBean.setMessage(CPSConstants.DUPLICATE);

				} else {
					payBillMasterBean.setMessage(payBillMasterDAO.updateEmpLoanDetails(pblDTO));
				}

			}

			payBillMasterBean.setEmpLoanList(payBillMasterDAO.getEmpLoanDetails(payBillMasterBean.getEmpID()));

		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}

	public PayBillMasterBean insertEmpOneTimeEntry(PayBillMasterBean payBillMasterBean) throws Exception {

		try {
			String message = payBillMasterDAO.getEmpExist(payBillMasterBean.getSfidSearchKey().toUpperCase());

			if (message != null && message.equalsIgnoreCase(CPSConstants.YES)) {
				message = payBillMasterDAO.getEmpEntryDetails(payBillMasterBean.getSfidSearchKey().toUpperCase());
				if (message != null && !message.equalsIgnoreCase(CPSConstants.YES)) {
					message = payBillMasterDAO.updateEmpDetails(payBillMasterBean);
					if (message != null && !message.equalsIgnoreCase(CPSConstants.SUCCESS)) {
						payBillMasterBean.setMessage(CPSConstants.CONSTRAINTS);
						payBillMasterBean.setRemarks(message);
					} else {
						payBillMasterBean.setMessage(message);
					}
				} else {
					payBillMasterBean.setMessage(CPSConstants.CONSTRAINTS);
					payBillMasterBean.setRemarks("Emp details already Exists");
				}
			} else {
				payBillMasterBean.setMessage(CPSConstants.EMPNOTEXISTS);
			}
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;

	}

	public PayBillMasterBean getAllowanceGroupList(PayBillMasterBean payBillMasterBean) throws Exception {

		try {
			payBillMasterBean.setAllowanceGroupList(payBillMasterDAO.getAllowanceGroupList(payBillMasterBean.getAllowanceID()));
			payBillMasterBean.setAllowanceSelGroupList(payBillMasterDAO.getAllowanceSelGroupList(payBillMasterBean.getAllowanceID()));
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}

	public String manageAllowanceConfigurationList(PayBillMasterBean payBillMasterBean) throws Exception {

		String message = "";
		try {

			message = payBillMasterDAO.manageAllowanceConfigurationList(payBillMasterBean.getAllowanceID(), payBillMasterBean.getPk());

		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public PayBillMasterBean updateEmpPayDetails(PayBillMasterBean payBillMasterBean) throws Exception {
		String message = "";
		try {
			if(CPSUtils.compareStrings(CPSConstants.NO, payBillMasterBean.getGpfFlag())){
				payBillMasterBean.setGpfAccountNo("");
			 }
			message = payBillMasterDAO.updateEmpPayDetails(payBillMasterBean);
			if (message != null && !message.equalsIgnoreCase(CPSConstants.SUCCESS)) {
				payBillMasterBean.setMessage(CPSConstants.CONSTRAINTS);
				payBillMasterBean.setRemarks(message);
			} else {
				payBillMasterBean.setMessage(message);
			}
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}
	
	//This is start of getAssessmentCategoryList()
	public List<CategoryDTO> getAssessmentTypeList() throws Exception{
		List<CategoryDTO> list=null;
		try{
			list=payBillMasterDAO.getAssessmentTypeList();
		}catch(Exception e){
			throw e;
		}
		
		return list;
	}
	
	
	// End of getAssessmentCategoryList()
	
	//start payBillMasterBusiness.totalPayDetails("DRTC")
	
	public List<PayBillDTO>totalPayDetails(String payRunMonth,int categoryId) throws Exception{
		 List<PayBillDTO> list=null;
		 try{
			 list=payBillMasterDAO.getTotalPayDetails( payRunMonth,categoryId);
		 }catch(Exception e){
				throw e;
			}
		 
		return list;
	}
	//end of payBillMasterBusiness.totalPayDetails("DRTC")
	
	
	//start of saveTOTPayDetails
	public String saveTOTPayDetails(PayBillMasterBean payBillMasterBean)throws Exception{
		String message="";
		try{
			message=payBillMasterDAO.saveTOTPayDetails(payBillMasterBean);
		}catch (Exception e) {
			throw e;
		}
		return message;
	}
	
	//end of saveTOTPayDetails
	
	
	
	

	public PayBillMasterBean getDuesDetails(PayBillMasterBean payBillMasterBean) throws Exception {
		try {
			String message = payBillMasterDAO.getEmpExist(payBillMasterBean.getSfidSearchKey().toUpperCase());

			if (message != null && message.equalsIgnoreCase(CPSConstants.YES)) {
				message = payBillMasterDAO.getEmpEntryDetails(payBillMasterBean.getSfidSearchKey().toUpperCase());
				if (message != null && message.equalsIgnoreCase(CPSConstants.YES)) {
					EmployeeBean empBean=payBillMasterDAO.getEmployeeDetails(payBillMasterBean.getSfidSearchKey().toUpperCase());
					payBillMasterBean.setSfID(empBean.getSfid());
					payBillMasterBean.setDivisionName(empBean.getDivisionName());
					payBillMasterBean.setDesignationName(empBean.getDesignationName());
					payBillMasterBean.setNameInServiceBook(empBean.getName());
					payBillMasterBean.setEmpDuesList(payBillMasterDAO.getEmpDuesDetails(payBillMasterBean.getSfidSearchKey().toUpperCase()));
					payBillMasterBean.setEmpID(payBillMasterBean.getSfidSearchKey().toUpperCase());
				} else {
					payBillMasterBean.setMessage(CPSConstants.CONSTRAINTS);
					payBillMasterBean.setRemarks("Emp pay details not Exist");
				}
			} else {
				payBillMasterBean.setMessage(CPSConstants.EMPNOTEXISTS);
			}
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}

	public PayBillMasterBean updateEmpDues(PayBillMasterBean payBillMasterBean) throws Exception 
	{
    	try 
		{
			payBillMasterBean.setMessage(payBillMasterDAO.checkUpdateEmpDues(payBillMasterBean));
			if(!CPSUtils.compareStrings(CPSConstants.DUPLICATE, payBillMasterBean.getMessage()))
			{
				PayBillDuesDTO payduesDTO = new PayBillDuesDTO();
				payduesDTO.setSfid(payBillMasterBean.getEmpID().toUpperCase());
				payduesDTO.setDeductionName(payBillMasterBean.getDeductionID());
				payduesDTO.setDeductionType(payBillMasterBean.getLoanDeduType());
				payduesDTO.setAmount(Integer.parseInt(payBillMasterBean.getAmount()));
				payduesDTO.setRecAmount(Integer.parseInt(payBillMasterBean.getAmount()));
				payduesDTO.setStatus(1);
				payduesDTO.setCreatedBy(payBillMasterBean.getSfID());
				payduesDTO.setCreationDate(CPSUtils.getCurrentDate());
				payduesDTO.setModifiedBy(payBillMasterBean.getSfID());
				payduesDTO.setModifiedDate(CPSUtils.getCurrentDate());
				String message = payBillMasterDAO.updateEmpDues(payduesDTO);
				payBillMasterBean.setMessage(message);
				payBillMasterBean.setEmpDuesList(payBillMasterDAO.getEmpDuesDetails(payBillMasterBean.getEmpID().toUpperCase()));
			}
			else
			{
				payBillMasterBean.setMessage(CPSConstants.DUPLICATE);
				payBillMasterBean.setEmpDuesList(payBillMasterDAO.getEmpDuesDetails(payBillMasterBean.getEmpID().toUpperCase()));
			}
		} catch (Exception e) 
		{
			throw e;
		}
		return payBillMasterBean;
	}

	public PayBillMasterBean getEmpEolDetails(PayBillMasterBean payBillMasterBean) throws Exception {
		try {
			//passing end date by naagendra.v
			payBillMasterBean.setEmpEolList(payBillMasterDAO.getEOLLeaves(payBillMasterBean.getSfidSearchKey().toUpperCase(), payBillMasterBean.getStartDate(), payBillMasterBean.getEndDate(),payBillMasterBean.getDivisionName()));

		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}

	public PayBillMasterBean empEolDetails(PayBillMasterBean payBillMasterBean) throws Exception { 
		try {
			
			payBillMasterBean.setMessage(payBillMasterDAO.updateEmpEolDetails(payBillMasterBean.getSfidSearchKey().toUpperCase(),payBillMasterBean.getType(),payBillMasterBean.getSfID().toUpperCase(),payBillMasterBean.getRunmonth()));
		} catch (Exception e) {
			throw e;
		}
		return payBillMasterBean;
	}
	
	public String getPayMastersEffDate() throws Exception {
		String message="";
		try {
			message=payBillMasterDAO.getPayMastersEffDate();
			message=message.substring(3);
			
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	public List<PayHindiInceDTO> getPayHindiInceList() throws Exception{
		List<PayHindiInceDTO> list=null;
		try{
			list=payBillMasterDAO.getPayHindiInceList();
		}catch (Exception e) {
			throw e;
		}
		return list;
   }
	 public String submitPayHindiIncDetails(PayBillMasterBean payBillMasterBean) throws Exception{
		 String message="";
		 try{
			 message=payBillMasterDAO.submitPayHindiIncDetails(payBillMasterBean);
			}catch (Exception e) {
				throw e;
			}
			return message;
	 }
	 public String deletePayHindiDetails(int id ,String sfid) throws Exception{
		 String message="";
		 try{
			 message=payBillMasterDAO.deletePayHindiDetails(id,sfid);
			}catch (Exception e) {
				throw e;
			}
			return message;
	 }
	 public PayBillMasterBean getEmpCorePayDetails(PayBillMasterBean payBillMasterBean)throws Exception {
			try{
				payBillMasterBean=payBillMasterDAO.getEmpCorePayDetails(payBillMasterBean);
				payBillMasterBean.setMember(payBillMasterDAO.checkGPFmember(payBillMasterBean.getSfidSearchKey().toUpperCase()));
			}catch(Exception e){
				throw e;
			}
			return payBillMasterBean;
		}
	 public List<KeyValueDTO> getEmployeesNotExistedInPayBill()throws Exception{
			List<KeyValueDTO> empList=null;
			try{
				empList=payBillMasterDAO.getEmployeesNotExistedInPayBill();
			}catch (Exception e) {
				throw e;
			}
			return empList;
		}
	 
	 // Added By Naresh 
	 public String getSearchName(String sfid)throws Exception
	 {
		 String name = "";
		 try
		 {
			 name = payBillMasterDAO.employeeName(sfid);
		 }catch(Exception e)
		 {
			 throw e;
		 }
		 return name;
	 }
	 
	 public String getEmployee(String sfid) throws Exception
	 {
		 String str = "";
		 try
		 {
			 str = payBillMasterDAO.getEmpExist(sfid);
			 if(CPSUtils.compareStrings(CPSConstants.YES, str))
				 str = CPSConstants.YES; 
			 else
				 str = CPSConstants.EMPNOTEXISTS;
		 }catch(Exception e)
		 {
			 str = CPSConstants.FAILED;
			 throw e;
		 }
		 return str;
	 }
	 
	 public List<String> getSearchList(String sfid) throws Exception
	 {
		 List<String> list = null;
		 try
		 {
			 list = payBillMasterDAO.getEmpNameList(sfid);
		 }catch(Exception e)
		 {
			 throw e;
		 }
		 return list;
	 }
	 
	 
	 public String submitDetails(PayBillMasterBean payBillMasterBean) throws Exception
	 {
		 String message = "";
		 try
		 {
			 message = payBillMasterDAO.submitDetails(payBillMasterBean);
		 }catch(Exception e)
		 {
			 throw e;
		 }
		 return message;
	 }
	 
	 public PayBillMasterBean getDetails(PayBillMasterBean payBillMasterBean) throws Exception
	 {
		 PayBillEmpPaymentDeatilsDTO bankDetails = null;
		 try
		 {
			 bankDetails = payBillMasterDAO.getDetails(payBillMasterBean.getSfidSearchKey());
			 payBillMasterBean.setSfidSearchKey(payBillMasterBean.getSfidSearchKey());
			 payBillMasterBean.setBankAccNo(bankDetails.getBankAccNo());
			 payBillMasterBean.setBankBranch(bankDetails.getBankBranch());
			 payBillMasterBean.setBranchCode(bankDetails.getBranchCode());
		 }catch(Exception e)
		 {
			 throw e;
		 }
		 return payBillMasterBean;
	 }
	 
	 
	 
	 public String getLabid() throws Exception{
		 String value=null;
		 
		 try {
			 value=payBillMasterDAO.getLabid();
			 if(value.equals(null)){
					value= " ";
				}
				else if(value.equals("1")){
			value="SF";
				}
				else {
					value="DG";
				}
			 System.out.println(value);
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
		return value; 
	 }
	public PayBillMasterBean getRunmonth(PayBillMasterBean payBillMasterBean) throws Exception {
		// TODO Auto-generated method stub
		
	try{
		 payBillMasterBean.setDaDate(commonObjectDAO.getPaybillRunmonth());
	}catch(Exception e){
		e.printStackTrace();
	}
	return payBillMasterBean;
	
			
	

		
	}
	
/*	 
	 public PayBillMasterBean getRecord(String sfid) throws Exception
	 {
		 PayBillMasterBean payBillMasterBean = null;
		 try
		 {
			payBillMasterBean = payBillMasterDAO.getRecordDetails(sfid); 
		 }catch(Exception e)
		 {
			 throw e;
		 }
		 return payBillMasterBean;
	 }
*/	 
}
