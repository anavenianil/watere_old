package com.callippus.web.dao.letterNumberFormat;

import java.util.List;

import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.OrgInstanceDTO;
import com.callippus.web.beans.file.FileUploadBean;
import com.callippus.web.beans.letterNumberFormat.IONDTO;
import com.callippus.web.beans.letterNumberFormat.IonCirculatedDetailsDTO;
import com.callippus.web.beans.letterNumberFormat.LetterNumberFormatBean;

@Service
public interface ILetterNumberFormatDAO {

	public List getDeptList() throws Exception;

	public List checkLetterNumberSeries(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public String updateLetterNumberSeries(LetterNumberFormatBean letterNumberFormatBean)throws Exception;
	
	public List getLetterNumberSeriesList()throws Exception;
	
	public List getLetterNumberSerisList()throws Exception;
	
	public List getLetterNumberFormatList(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public List checkLetterNumberFormat(LetterNumberFormatBean letterNumberFormatBean)throws Exception;
	
	public String updateLetterNumberFormat(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public List getLetterNumberFormatDetails(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public List getIONMstList(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public String updateLetterNumberFormatRunningNum(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public List checkLetterNumberFormatDetails(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public String updateLetterNumberFormatDetails(LetterNumberFormatBean letterNumberFormatBean)throws Exception;
	
	public String updateIONdetailsForLetter(IONDTO idto, String id)throws Exception;

	public String deleteIONDeptDetails(String ionId,String str,String str1,String refOrgRoleId)throws Exception;
	
	public List getIONMasterList(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public List getDepartmentList()throws Exception;
	
	public List designationList() throws Exception;

	public List getRole(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public List getDeptListBasedOnRole(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public List getSfidList(String str, String type,LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public List getNoticeBoardList(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public List getIONDetails(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public List getIONMstDetails(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public List getRoleHirarchyList()throws Exception;

	public List<String> getLevel1SfidList(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public String updateIonCirculationDetails(IonCirculatedDetailsDTO dto)throws Exception;

	public List<OrgInstanceDTO> getUserOrgRoleList(LetterNumberFormatBean letterNumberFormatBean,String str)throws Exception;

	public String checkLetterNumberSeres(LetterNumberFormatBean letterNumberFormatBean)throws Exception;
	
	public String checkLetterNumberFormt(LetterNumberFormatBean letterNumberFormatBean)throws Exception;
	
	public String deleteLetterNumberSeries(LetterNumberFormatBean letterNumberFormatBean)throws Exception;
	
	public String deleteLetterNumberFormat(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public List getDepartmentListBasedOnSfid(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public List getOrganizations(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public List getIONFileList(LetterNumberFormatBean letterNumberFormatBean)throws Exception;

	public void checkHRDGIon(LetterNumberFormatBean letterNumberFormatBean, FileUploadBean fub)throws Exception;

	public List getIONListToCirculate(LetterNumberFormatBean letterNumberFormatBean)throws Exception;
	
	public List getFileTypeList()throws Exception;

	public String manageCirculation(LetterNumberFormatBean letterNumberFormatBean)throws Exception;
	
	public String deleteLetterNumberFormatDetails(int id)throws Exception;
	
	public List<IONDTO> getIONDisplayDetails(LetterNumberFormatBean letterNumberFormatBean) throws Exception;

	
	

	

}
