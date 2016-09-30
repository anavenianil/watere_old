package com.callippus.web.business.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.orgstructure.OrgStructureBean;
import com.callippus.web.beans.transfer.EmpTransferBean;
import com.callippus.web.beans.transfer.dto.EmpTransferTxnDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.orgstructure.IOrgStructureDAO;
import com.callippus.web.dao.transfer.IEmpTransferDAO;

@Service
public class EmpTransferBusiness {
	@Autowired
	private IEmpTransferDAO empTransferDAO;
	@Autowired
	private IComonObjectDAO commonObjectDAO;
	@Autowired
	private IOrgStructureDAO orgStructureDAO;

	@SuppressWarnings("unchecked")
	public EmpTransferBean getTransferHomeDetails(EmpTransferBean transferBean) throws Exception {
		try {
			// Get Departments List
			transferBean.setDepartmentsList(commonObjectDAO.getMasterData(CPSConstants.DepartmentsDTO));

			// If the type=employee, we should get the the departments
			if (CPSUtils.compareStrings(transferBean.getType(), CPSConstants.EMPLOYEE)) {
				OrgStructureBean orgStrBean = new OrgStructureBean();
				orgStrBean.setEmpRoles(transferBean.getEmpRoles());
				orgStrBean.setSfID(transferBean.getSfID());
				orgStrBean = orgStructureDAO.getDepartments(orgStrBean);
				transferBean.setEmpWorkingDept(orgStrBean.getDepartmentsList());
			}
		} catch (Exception e) {
			throw e;
		}
		return transferBean;
	}

	public EmpTransferBean getEmployees(EmpTransferBean transferBean) throws Exception {
		try {
			// Get Employees List
			transferBean.setEmployeesList(empTransferDAO.getEmployees(transferBean));

		} catch (Exception e) {
			throw e;
		}
		return transferBean;
	}

	@SuppressWarnings("unchecked")
	public EmpTransferBean getTransferTxnDetails(EmpTransferBean transferBean) throws Exception {
		try {
			// Get Transfer Txn List
			transferBean.setEmpTransferTxnList(commonObjectDAO.getMasterData(CPSConstants.EMPTRANSFERTXNDTO));
			transferBean.setOrgInstanceList(commonObjectDAO.getMasterData(CPSConstants.ORGINSTANCEDTO));
			transferBean.setDepartmentsList(commonObjectDAO.getMasterData(CPSConstants.DepartmentsDTO));
			transferBean.setDoPartList(commonObjectDAO.getMasterData("DoPartDTO"));
		} catch (Exception e) {
			throw e;
		}
		return transferBean;
	}

	public String manageTransTxnDetails(EmpTransferBean transferBean) throws Exception {
		String message = "";
		try {
			message = empTransferDAO.manageTransTxnDetails(transferBean);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public String deleteTransTxnDetails(int doPartID) throws Exception {
		String message = "";
		try {
			message = empTransferDAO.deleteTransTxnDetails(doPartID);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	public EmpTransferBean getDepartments(EmpTransferBean transferBean) throws Exception {
		try {
			transferBean.setResult(commonObjectDAO.checkEmployee(transferBean.getTransferedSFID()));
			if (CPSUtils.compareStrings(transferBean.getResult(), CPSConstants.SUCCESS)) {
				transferBean.setDepartmentsList(empTransferDAO.getDepartments(transferBean.getTransferedSFID()));
			}
		} catch (Exception e) {
			throw e;
		}
		return transferBean;
	}
}
