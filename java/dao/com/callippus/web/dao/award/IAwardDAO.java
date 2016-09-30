package com.callippus.web.dao.award;

import java.util.ArrayList;
import java.util.List;

import com.callippus.web.beans.award.AwardDetails;
import com.callippus.web.beans.dto.AwardCategoryDTO;

public interface IAwardDAO {
	public ArrayList<AwardDetails> getAwardHomeDetails(AwardDetails awardBean) throws Exception;

	public String manageAwardDetails(AwardDetails awardBean) throws Exception;

	public String deleteAwardDetails(AwardDetails awardBean) throws Exception;

	public List<AwardCategoryDTO> getAwardCategoryList() throws Exception;

	public String updateAwardDetails(AwardDetails awardBean) throws Exception;

}
