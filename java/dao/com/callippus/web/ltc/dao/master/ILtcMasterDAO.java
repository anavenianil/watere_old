package com.callippus.web.ltc.dao.master;

import java.util.List;

import com.callippus.web.beans.dto.KeyValueDTO;
import com.callippus.web.ltc.beans.application.LtcApplicationBean;

public interface ILtcMasterDAO {
	public String checkLtcName(LtcApplicationBean ltcBean) throws Exception;
	
	public String createLtcMasterData(Object obj)throws Exception;
	
	public String updateLtcMasterData(LtcApplicationBean ltcBean) throws Exception;
	
	public String deleteLtcMasterData(LtcApplicationBean ltcBean) throws Exception;
	
	public List<KeyValueDTO> getBlockYearsList() throws Exception;
	
	public String getLtcBlockType(String type) throws Exception;
		
}
