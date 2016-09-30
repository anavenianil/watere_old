package com.callippus.web.business.requestprocess;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.callippus.web.beans.requests.AlertMessageDTO;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.HibernateUtils;

@Service
public class AlertMessages {
	@Autowired
	private com.callippus.web.controller.common.HibernateUtils hibernateUtils;

	/**
	 * Insert with status=2 (Pending) if this alert requires any response other wise status=8(Completed)
	 * 
	 * @param alertMessageDTO
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public String submitAlert(AlertMessageDTO alertMessageDTO) throws Exception {
		Session session = null;
		String result = null;
		try {
			session = hibernateUtils.getSession(); //session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(alertMessageDTO);

			result = CPSConstants.SUCCESS;
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	//@Transactional(readOnly = false)
	public String checkAndUpdateAlert(AlertMessageDTO alertMessageDTO) throws Exception {
		Session session = null;
		String result = null;
		AlertMessageDTO alertMessageDTO1 = null;
		try {
			session = hibernateUtils.getSession(); //session = sessionFactory.getCurrentSession();
            System.out.println("FSHGHDSFTERTERTSDFS");
			alertMessageDTO1 = (AlertMessageDTO) session.createCriteria(AlertMessageDTO.class)
			        .add(Expression.eq("assignedTo", alertMessageDTO.getAssignedTo()))
			        .add(Expression.eq("alertID", alertMessageDTO.getAlertID()))
			        .add(Expression.eq("referenceID", alertMessageDTO.getReferenceID()))
					.add(Expression.eq("status",new Integer(CPSConstants.STATUSPENDING)))
					.uniqueResult();

			if (CPSUtils.isNull(alertMessageDTO1)) {
				// New Alert
				result = submitAlert(alertMessageDTO);
			} else if (alertMessageDTO1.getStatus() == Integer.valueOf(CPSConstants.STATUSPENDING)) {
				// Already sent, but not responded by the user. So we can update the old data
				session.evict(alertMessageDTO1);
				alertMessageDTO.setId(alertMessageDTO1.getId());
				result = submitAlert(alertMessageDTO);
			} else {
				// Already sent and the user also responded so we should send another alert message.
				result = submitAlert(alertMessageDTO);
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

}
