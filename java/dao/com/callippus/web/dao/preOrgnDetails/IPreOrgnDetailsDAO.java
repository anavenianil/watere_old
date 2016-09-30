package com.callippus.web.dao.preOrgnDetails;

import java.util.ArrayList;
import java.util.List;

import com.callippus.web.beans.preOrgnDetails.PreOrgnDetailsBean;

public interface IPreOrgnDetailsDAO {

	public ArrayList getPreOrgnDetails(PreOrgnDetailsBean preOrgn) throws Exception;

	public String createPreOrgnDetails(PreOrgnDetailsBean preOrgnBean) throws Exception;

	public String deletePreOrgnDetails(PreOrgnDetailsBean preOrgnBean) throws Exception;

	public String updatePreOrgnDetails(PreOrgnDetailsBean preOrgnBean) throws Exception;

	public List editOrganisation(PreOrgnDetailsBean preOrgnBean) throws Exception;

}
