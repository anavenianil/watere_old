package com.callippus.web.business.hqBusiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.higherQualification.HQRequestBean;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.higherQualification.IHQRequestDao;

@Service
public class HQRequestBusiness {
	@Autowired
	IHQRequestDao iHQRequestDao;
	@Autowired
	IComonObjectDAO iComonObjectDAO;
	public HQRequestBean getHQRequestHomeDetails(String sfID) throws Exception{
		HQRequestBean hqRequestBean=null;
		try{
			hqRequestBean=iHQRequestDao.getHQRequestHome(sfID);
			if(CPSUtils.isNullOrEmpty(hqRequestBean))
				hqRequestBean=new HQRequestBean();
			hqRequestBean.setQualificationList(iComonObjectDAO.getMasterData("QualificationDTO"));
		}catch(Exception e){
			throw e;
		}
		return hqRequestBean;
	}
}
