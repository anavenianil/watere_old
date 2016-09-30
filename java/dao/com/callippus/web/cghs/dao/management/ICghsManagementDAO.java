package com.callippus.web.cghs.dao.management;

import java.util.List;

import com.callippus.web.cghs.beans.management.CghsManagementBean;

public interface ICghsManagementDAO {
	public String saveBeanVales(Object object) throws Exception;

	public List getList(Class beanName) throws Exception;

	public Object checkDuplicateEntry(CghsManagementBean cghsBean, Class beanName) throws Exception;

	public Object getBeanValue(Class beanName, String pk) throws Exception;

	public String seletedDeleteRefHospital(int id, String deletionDate) throws Exception;

}
