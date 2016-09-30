package com.callippus.web.business.domainobject;

import org.springframework.stereotype.Service;

import com.callippus.web.beans.requests.LeaveRequestBean;
import com.callippus.web.business.tx.workflow.TxDomainObject;

@Service
public class LeaveDomainObject extends TxDomainObject {
	public String updateTxnDetails(LeaveRequestBean prb) throws Exception {

		// update leave table
		return null;
	}
}
