package com.callippus.web.business.approles;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.approles.ApplicationRolesBean;
import com.callippus.web.beans.dto.GeneralListDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.approles.IApplicationRolesDAO;
import com.callippus.web.dao.common.IComonObjectDAO;

@Service
public class ApplicationRolesBusiness {
	@Autowired
	private IApplicationRolesDAO approlesDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public ApplicationRolesBean getHomeDetails(HttpSession session) throws Exception {
		ApplicationRolesBean appRolesBean = null;
		List empList = null;
		ArrayList<GeneralListDTO> employeeAppList = null;
		try {
			appRolesBean = new ApplicationRolesBean();
			empList = approlesDAO.getApplicationRolesMappingList(appRolesBean);
			employeeAppList = new ArrayList<GeneralListDTO>();

			for (int j = 0; j < empList.size(); j++) {
				Object[] obj = (Object[]) empList.get(j);
				GeneralListDTO genDTO = new GeneralListDTO();

				genDTO.setId(obj[0].toString());
				genDTO.setSfid(obj[1].toString());
				if (!CPSUtils.isNullOrEmpty(obj[2].toString()))
					genDTO.setEmpName(obj[2].toString());
				if (!CPSUtils.isNullOrEmpty(obj[3].toString()))
					genDTO.setDesignation(obj[3].toString());
				if (!CPSUtils.isNullOrEmpty(obj[4].toString()))
					genDTO.setApplicationRoleName(obj[4].toString());
				if (!CPSUtils.isNullOrEmpty(obj[5].toString()))
					genDTO.setApplicationRoleID(obj[5].toString());
				employeeAppList.add(genDTO);
			}

			appRolesBean.setAppRolesMapList(employeeAppList);

			session.setAttribute(CPSConstants.APPLICATIONROLESLIST, appRolesBean.getAppRolesMapList());
			session.setAttribute(CPSConstants.APPLICATIONROLESMAPJSON, (JSONArray) JSONSerializer.toJSON(appRolesBean.getAppRolesMapList()));

			if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.APPLICATIONEMPLOYEES))) {
				appRolesBean.setEmployeesList(commonDataDAO.getEmployeesList());
				session.setAttribute(CPSConstants.APPLICATIONEMPLOYEES, appRolesBean.getEmployeesList());
			}

			if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.DESIGNATIONLISTJSON))) {
				appRolesBean.setDesignationList(commonDataDAO.getMasterData(CPSConstants.DESIGNATIONDTO));
				session.setAttribute(CPSConstants.DESIGNATIONLISTJSON, (JSONArray) JSONSerializer.toJSON(appRolesBean.getDesignationList()));
			}
			/*
			 * if(CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants. INSTANCELISTJSON))) { appRolesBean.setInstanceList(getCommonDataDAO ().getMasterData(CPSConstants.ORGINSTANCEDTO));
			 * session.setAttribute(CPSConstants.INSTANCELISTJSON, (JSONArray) JSONSerializer.toJSON(appRolesBean.getInstanceList())); }
			 */

			appRolesBean.setApplicationRolesList(commonDataDAO.getMasterData(CPSConstants.APPLICATIONROLESDTO));
			session.setAttribute(CPSConstants.APPLICATIONROLESLISTJSON, (JSONArray) JSONSerializer.toJSON(appRolesBean.getApplicationRolesList()));
			session.setAttribute(CPSConstants.APPLICATIONROLES, appRolesBean.getApplicationRolesList());

		} catch (Exception e) {
			throw e;
		}
		return appRolesBean;
	}

	public ApplicationRolesBean getSearchedDetails(ApplicationRolesBean appRolesBean, HttpSession session) throws Exception {
		List empList = null;
		ArrayList<GeneralListDTO> employeeAppList = null;
		try {
			empList = approlesDAO.getApplicationRolesMappingList(appRolesBean);
			employeeAppList = new ArrayList<GeneralListDTO>();

			for (int j = 0; j < empList.size(); j++) {
				Object[] obj = (Object[]) empList.get(j);
				GeneralListDTO genDTO = new GeneralListDTO();

				genDTO.setId(obj[0].toString());
				genDTO.setSfid(obj[1].toString());
				genDTO.setEmpName(obj[2].toString());
				genDTO.setDesignation(obj[3].toString());
				genDTO.setApplicationRoleName(obj[4].toString());
				genDTO.setApplicationRoleID(obj[5].toString());
				employeeAppList.add(genDTO);
			}

			appRolesBean.setAppRolesMapList(employeeAppList);
			session.setAttribute(CPSConstants.APPLICATIONROLESLIST, appRolesBean.getAppRolesMapList());
		} catch (Exception e) {
			throw e;
		}
		return appRolesBean;
	}

	public ApplicationRolesBean submitApplicationRoles(ApplicationRolesBean appRolesBean, HttpSession session) throws Exception {
		List empList = null;
		ArrayList<GeneralListDTO> employeeAppList = null;
		try {
			appRolesBean.setMessage(approlesDAO.submitApplicationRoles(appRolesBean));
			if (CPSUtils.compareStrings(CPSConstants.SUCCESS, appRolesBean.getMessage())) {
				empList = approlesDAO.getApplicationRolesMappingList(appRolesBean);
				employeeAppList = new ArrayList<GeneralListDTO>();

				for (int j = 0; j < empList.size(); j++) {
					Object[] obj = (Object[]) empList.get(j);
					GeneralListDTO genDTO = new GeneralListDTO();

					genDTO.setId(obj[0].toString());
					genDTO.setSfid(obj[1].toString());
					genDTO.setEmpName(obj[2].toString());
					genDTO.setDesignation(obj[3].toString());
					genDTO.setApplicationRoleName(obj[4].toString());
					genDTO.setApplicationRoleID(obj[5].toString());
					employeeAppList.add(genDTO);
				}

				appRolesBean.setAppRolesMapList(employeeAppList);
				session.setAttribute(CPSConstants.APPLICATIONROLESLIST, appRolesBean.getAppRolesMapList());
				session.setAttribute(CPSConstants.APPLICATIONROLESMAPJSON, (JSONArray) JSONSerializer.toJSON(appRolesBean.getAppRolesMapList()));
			}
		} catch (Exception e) {
			throw e;
		}
		return appRolesBean;
	}

	public ApplicationRolesBean deleteApplicationDetails(ApplicationRolesBean appRolesBean) throws Exception {
		String message = null;
		List empList = null;
		ArrayList<GeneralListDTO> employeeAppList = null;
		try {
			message = approlesDAO.deleteApplicationRoles(appRolesBean);
			appRolesBean.setMessage(message);
			if (CPSUtils.compareStrings(CPSConstants.DELETED, appRolesBean.getMessage())) {
				empList = approlesDAO.getApplicationRolesMappingList(appRolesBean);
				employeeAppList = new ArrayList<GeneralListDTO>();

				for (int j = 0; j < empList.size(); j++) {
					Object[] obj = (Object[]) empList.get(j);
					GeneralListDTO genDTO = new GeneralListDTO();

					genDTO.setId(obj[0].toString());
					genDTO.setSfid(obj[1].toString());
					genDTO.setEmpName(obj[2].toString());
					genDTO.setDesignation(obj[3].toString());
					genDTO.setApplicationRoleName(obj[4].toString());
					genDTO.setApplicationRoleID(obj[5].toString());
					employeeAppList.add(genDTO);
				}
				appRolesBean.setAppRolesMapList(employeeAppList);
				// session.setAttribute(CPSConstants.APPLICATIONROLESLIST, appRolesBean.getAppRolesMapList());
			}
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return appRolesBean;
	}
}