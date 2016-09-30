package com.callippus.web.tada.dao.approval;


import org.springframework.stereotype.Service;

import com.callippus.web.tada.beans.request.TadaRequestBean;
import com.callippus.web.tada.dto.TadaApprovalRequestDTO;
import com.callippus.web.tada.dto.TadaRequestProcessBean;
import com.callippus.web.tada.dto.TadaTdAdvanceRequestDTO;


public interface ITadaTdApprovalRequestDAO {
	
	public TadaTdAdvanceRequestDTO getTadaTdApprovalDetails (String requestId) throws Exception;
	
	public TadaApprovalRequestDTO getRefRequestId (TadaRequestBean tadaRequestBean) throws Exception;
	
	public TadaApprovalRequestDTO getRefRequestId (TadaRequestProcessBean tadaRPB) throws Exception;

}
