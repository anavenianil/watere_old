package com.callippus.web.dao.transfer;

import java.util.List;

import com.callippus.web.beans.dto.DepartmentsDTO;
import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.transfer.EmpTransferBean;

public interface IEmpTransferDAO {
	public List<KeyValueDTO> getEmployees(EmpTransferBean transferBean) throws Exception;

	public String manageTransTxnDetails(EmpTransferBean transferBean)throws Exception;

	public String deleteTransTxnDetails(int doPartID) throws Exception;

	public List<DepartmentsDTO> getDepartments(String transferedSFID)throws Exception;
}
