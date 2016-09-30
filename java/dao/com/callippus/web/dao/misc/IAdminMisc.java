package com.callippus.web.dao.misc;

import java.util.List;

import com.callippus.web.beans.misc.AdminMisc;
import com.callippus.web.beans.passport.MovablePropertyDTO;

public interface IAdminMisc {

	public String saveRecord(AdminMisc adminMisc) throws Exception;
	
	public String saveMovableRecord(MovablePropertyDTO propertyDTO) throws Exception;
	
	public List<MovablePropertyDTO> getMovablePropertyDetails(String sfID)throws Exception;
	
	public String deletePropertyRecord(int id)throws Exception;
	
}
