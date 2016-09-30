package com.callippus.web.business.nominee;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.DistrictTypeDTO;
import com.callippus.web.beans.dto.StateTypeDTO;
import com.callippus.web.beans.nominee.NomineeBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.nominee.INomineeDAO;

@Service
public class NomineeBusiness {
	@Autowired
	private INomineeDAO nomineeDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	@SuppressWarnings("unchecked")
	public NomineeBean getNomineeHomeDetails(String sfid, HttpSession session) throws Exception {
		NomineeBean nomineeBean = null;
		try {
			nomineeBean = new NomineeBean();
			// nomineeBean.setNomineeTypeList(commonDataDAO.getMasterData("NomineeDTO"));
			session.setAttribute(CPSConstants.NOMINEETYPELIST, commonDataDAO.getMasterData(CPSConstants.NOMINEETYPEDTO));

			// nomineeBean.setFamilyMembersList(nomineeDAO.getFamilyMembersList(sfid));
			session.setAttribute(CPSConstants.FAMILYMEMBERSLIST, nomineeDAO.getFamilyMembersList(sfid));
			nomineeBean.setChangedSfid(sfid);
			nomineeBean = nomineeDAO.getNomineeList(nomineeBean);
			if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.STATELIST))) {
				nomineeBean.setStateList(getStateList());
				nomineeBean.setDistrictList(getDistrictList());

				session.setAttribute(CPSConstants.STATELIST, nomineeBean.getStateList());
				session.setAttribute(CPSConstants.DISTRICTLIST, nomineeBean.getDistrictList());
				session.setAttribute(CPSConstants.DISTRICTJSONLIST, (JSONArray) JSONSerializer.toJSON(nomineeBean.getDistrictList()));
			} else {
				nomineeBean.setStateList((List<StateTypeDTO>) session.getAttribute(CPSConstants.STATELIST));
				nomineeBean.setDistrictList((List<DistrictTypeDTO>) session.getAttribute(CPSConstants.DISTRICTLIST));
			}

		} catch (Exception e) {
			throw e;
		}
		return nomineeBean;
	}

	public NomineeBean submitNomineeDetails(NomineeBean nomineeBean) throws Exception {
		try {
			if (CPSUtils.isNullOrEmpty(nomineeBean.getNomineeID())) {
				nomineeBean.setMessage(nomineeDAO.submitNomineeDetails(nomineeBean));
			} else {
				nomineeBean.setMessage(nomineeDAO.updateNomineeDetails(nomineeBean));
			}
			nomineeBean = nomineeDAO.getNomineeList(nomineeBean);
		} catch (Exception e) {
			throw e;
		}
		return nomineeBean;
	}

	public List<StateTypeDTO> getStateList() throws Exception {
		List<StateTypeDTO> stateList = null;
		try {
			stateList = commonDataDAO.getStateList();
		} catch (Exception e) {
			throw e;
		}
		return stateList;
	}

	public List<DistrictTypeDTO> getDistrictList() throws Exception {
		List<DistrictTypeDTO> districtList = null;
		try {
			districtList = commonDataDAO.getDistrictList();
		} catch (Exception e) {
			throw e;
		}
		return districtList;
	}

	public String deleteNomineeDetails(NomineeBean nomineeBean) throws Exception {
		String message = null;
		try {
			message = nomineeDAO.deleteNomineeDetails(nomineeBean.getNomineeID());
			nomineeBean = nomineeDAO.getNomineeList(nomineeBean);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
}