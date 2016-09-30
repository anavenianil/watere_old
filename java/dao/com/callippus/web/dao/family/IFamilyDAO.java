package com.callippus.web.dao.family;

import java.util.ArrayList;
import java.util.List;

import com.callippus.web.beans.family.FamilyBean;

public interface IFamilyDAO {

	public ArrayList getFamilyDetails(String sfid) throws Exception;

	public String manageFamily(FamilyBean familyBean) throws Exception;

	public String deleteFamily(String id,String remarks) throws Exception;

	public List checkFamily(FamilyBean FamilyBean) throws Exception;

	public String updateFamily(FamilyBean familyBean) throws Exception;

	public boolean checkNomineeData(FamilyBean familyBean) throws Exception;

	public String updateLtcAndCghsDetails(String ltcFacility, String cghsFacility, String sfid, String relationId) throws Exception;

	public String getPreviousValue(String type, String sfid, String relationId) throws Exception;

	public void updateCghsAndLtcFacility(String type, int sonAgeLimit, int majorAgeLimit) throws Exception;
	
	public String ageFieldUpdation() throws Exception;

}
