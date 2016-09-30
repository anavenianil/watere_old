package com.callippus.web.dao.nominee;

import java.util.ArrayList;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.beans.nominee.NomineeBean;

public interface INomineeDAO {
	public ArrayList<KeyValueDTO> getFamilyMembersList(String sfid) throws Exception;

	public NomineeBean getNomineeList(NomineeBean nomineeBean) throws Exception;

	public String submitNomineeDetails(NomineeBean nomineeBean) throws Exception;

	public String updateNomineeDetails(NomineeBean nomineeBean) throws Exception;

	public String deleteNomineeDetails(String id) throws Exception;
}
