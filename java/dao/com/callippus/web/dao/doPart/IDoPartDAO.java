package com.callippus.web.dao.doPart;

import java.util.List;

import com.callippus.web.beans.doPart.DoPartBean;
import com.callippus.web.beans.dto.DoPartDTO;

public interface IDoPartDAO {

	String submitDOPartDetails(DoPartBean doPartBean)throws Exception;

	String deleteDOPartDetails(int id) throws Exception;

	List<DoPartDTO> getDoPartDetails(DoPartBean doPartBean)throws Exception;

}
