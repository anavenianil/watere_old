package com.callippus.web.hrdg.dao;

import com.callippus.web.beans.workflowmapping.WorkFlowMappingBean;

public interface ICommonDAO {
	public String save(Object obj) throws Exception;
	public String checkEmployee(String sfID) throws Exception;

}
