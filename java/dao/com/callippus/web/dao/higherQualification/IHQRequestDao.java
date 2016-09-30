package com.callippus.web.dao.higherQualification;

import com.callippus.web.beans.higherQualification.HQRequestBean;

public interface IHQRequestDao {

	HQRequestBean getHQRequestHome(String sfID)throws Exception;

}
