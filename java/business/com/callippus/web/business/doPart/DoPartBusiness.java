package com.callippus.web.business.doPart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.doPart.DoPartBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.doPart.IDoPartDAO;
@Service
public class DoPartBusiness {
	@Autowired
	IDoPartDAO iDoPartDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@SuppressWarnings("unchecked")
	public DoPartBean getDoPartDetails(DoPartBean doPartBean) throws Exception{
		try{
		doPartBean.setDoPartList(iDoPartDAO.getDoPartDetails(doPartBean));
		doPartBean.setYearsList(commonDataDAO.getMasterData("YearTypeDTO"));
		doPartBean.setTypeList(commonDataDAO.getMasterData(CPSConstants.TYPEDETAILSDTO));
		return doPartBean;
	}catch(Exception e){
		throw e;
	}
}
	public String manageDOPartDetails(DoPartBean doPartBean)throws Exception {
		String message="";
		try{
			message=iDoPartDAO.submitDOPartDetails(doPartBean);
		}catch(Exception e){
			throw e;
		}
		return message;
	}
	public String deleteDOPartDetails(int id) throws Exception{
		String message="";
		try{
			message=iDoPartDAO.deleteDOPartDetails(id);
		}catch(Exception e){
			throw e;
		}
		return message;
	}
}
