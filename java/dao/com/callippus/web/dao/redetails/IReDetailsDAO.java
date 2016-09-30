package com.callippus.web.dao.redetails;

import java.util.List;

import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.redetails.ReDetailsBean;

public interface IReDetailsDAO {
	public List<ReDetailsBean> designationsList(ReDetailsBean reDetailsBean) throws Exception;

	public ReDetailsBean saveReDetails(ReDetailsBean reDetailsBean) throws Exception;

	public ReDetailsBean checkReDetails(ReDetailsBean reDetailsBean) throws Exception;

	public List<CategoryDTO> categoryList() throws Exception;
}
