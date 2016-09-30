package com.callippus.web.dao.higherQualification;

import com.callippus.web.beans.higherQualification.HQBean;

public interface IHQDao {
	HQBean getHQDetails(HQBean hqBean)throws Exception;

	HQBean getSanctionedHQDetails(HQBean hqBean)throws Exception;
}
