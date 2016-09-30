package com.callippus.web.business.misc;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.misc.AdminMisc;
import com.callippus.web.beans.passport.MovablePropertyDTO;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.misc.IAdminMisc;

@Service
public class AdminMiscBusiness {

	@Autowired
	private IAdminMisc adminMisc;
	
	public String saveRecord(AdminMisc bean) throws Exception{
		try {
			return adminMisc.saveRecord(bean);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public String saveMovableRecord(AdminMisc bean) throws Exception{
		MovablePropertyDTO propertyDTO=null;
		try {
			propertyDTO=new MovablePropertyDTO();
			BeanUtils.copyProperties(propertyDTO, bean);
			propertyDTO.setStatus(1);
			propertyDTO.setCreationDate(CPSUtils.getCurrentDate());
			propertyDTO.setLastModifiedDate(CPSUtils.getCurrentDate());
			
			return adminMisc.saveMovableRecord(propertyDTO);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public AdminMisc deletePropertyRecord(AdminMisc bean) throws Exception{
		try {
			 bean.setResult(adminMisc.deletePropertyRecord(bean.getId()));
		} catch (Exception e) {
			throw e;
		}
		return bean;
	}
	
	public List<MovablePropertyDTO> getMovablePropertyDetails(AdminMisc bean) throws Exception{
		List<MovablePropertyDTO> list=null;
		try {
			list= adminMisc.getMovablePropertyDetails(bean.getSfID());
			/*for(MovablePropertyDTO propertyDTO:list){
				propertyDTO.setAcqOrDisDate(CPSUtils.formattedDate(propertyDTO.getAcqOrDisDate()));
				propertyDTO.setPropertyActualDate(CPSUtils.formattedDate(propertyDTO.getPropertyActualDate()));
			}*/
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
	
}
