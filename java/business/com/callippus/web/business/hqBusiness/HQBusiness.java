package com.callippus.web.business.hqBusiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.higherQualification.HQBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.higherQualification.IHQDao;

@Service
public class HQBusiness {
	@Autowired
	IHQDao ihqDao;
	@Autowired
	IComonObjectDAO comonObjectDAO;
	public HQBean getHQDetails(HQBean hqBean) throws Exception{
		try{
			hqBean=ihqDao.getHQDetails(hqBean);
			if(!CPSUtils.isNullOrEmpty(hqBean.getHqDetailsDTO())){
				hqBean.setMessage(CPSConstants.SUCCESS);
			}
		}catch(Exception e){
			throw e;
		}
		return hqBean;
	}
	public HQBean getSanctionOfIncentiveDetails(HQBean hqBean)throws Exception {
		try{
			hqBean=ihqDao.getSanctionedHQDetails(hqBean);
			if(!CPSUtils.isNullOrEmpty(hqBean.getHqSanctionDTO())){
				hqBean.setResult(CPSConstants.SUCCESS);
			}
		}catch(Exception e){
			throw e;
		}
		return hqBean;
	}
}
