package com.callippus.web.business.training;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.training.TrainingBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.training.ITrainingDAO;

@Service
public class TrainingBusiness {
	@Autowired
	private ITrainingDAO trainingDAO;
	@Autowired
	private IComonObjectDAO commonDataDAO;

	public TrainingBean getTrainingDetails(TrainingBean trainingBean, HttpServletRequest request) throws Exception {
		List trainingList = null;
		try {
			HttpSession session = request.getSession(true);
			// trainingBean.setId(String.valueOf(commonDataDAO.getTableID(
			// CPSConstants.TRAINING_MASTER, CPSConstants.NEW)));
			// message=trainingDAO.saveTrainingDetails(trainingBean);
			trainingList = trainingDAO.getTrainingDetails(trainingBean);
			trainingBean.setYearList(commonDataDAO.getMasterData(CPSConstants.YEARTYPEDTO));
			int size = trainingList.size();
			if (size > 0) {
				session.setAttribute(CPSConstants.MASTERJSON, trainingList.get(size - 1));
				trainingList.remove(size - 1);
			}

			trainingBean.setTrainingList(trainingList);
			// trainingBean.setMessage(message);
			// commonDataDAO.updateTableID(CPSConstants.TRAINING_MASTER,trainingBean.getId());

		} catch (Exception e) {
			throw e;
		}
		return trainingBean;
	}

	public TrainingBean saveTrainingDetails(TrainingBean trainingBean, HttpServletRequest request) throws Exception {
		List trainingList = null;
		String message = "";
		try {
			HttpSession session = request.getSession(true);
			if (CPSUtils.isNullOrEmpty(trainingBean.getId())) {// while new creation

				trainingBean.setId(String.valueOf(commonDataDAO.getTableID(CPSConstants.TRAINING_MASTER, CPSConstants.NEW)));
				trainingBean.setYearId(Integer.parseInt(trainingBean.getYear()));
				trainingBean.setStatus("1");
				trainingBean.setCreationDate(CPSUtils.getCurrentDate());
				trainingBean.setLastModifiedDate(CPSUtils.getCurrentDate());
				message = trainingDAO.saveTrainingDetails(trainingBean);
				trainingList = trainingDAO.getTrainingDetails(trainingBean);
				trainingBean.setTrainingList(trainingList);
				int size = trainingList.size();
				if (size > 0) {
					session.setAttribute(CPSConstants.MASTERJSON, trainingList.get(size - 1));
					trainingList.remove(size - 1);
				}

				trainingBean.setMessage(message);
				commonDataDAO.updateTableID(CPSConstants.TRAINING_MASTER, trainingBean.getId());
			} else { // while updation
				trainingBean.setCreationDate(commonDataDAO.getCreationDate(trainingBean.getId(), trainingBean.getSfid(), CPSConstants.TRAINING_MASTER));
				trainingBean.setLastModifiedDate(CPSUtils.getCurrentDate());
				trainingBean.setYearId(Integer.parseInt(trainingBean.getYear()));
				trainingBean.setStatus("1");
				if (CPSUtils.isNullOrEmpty(trainingBean.getArea())) {
					trainingBean.setArea("0");
				}
				message = trainingDAO.saveTrainingDetails(trainingBean);
				trainingList = trainingDAO.getTrainingDetails(trainingBean);
				int size = trainingList.size();
				if (size > 0) {
					session.setAttribute(CPSConstants.MASTERJSON, trainingList.get(size - 1));
					trainingList.remove(size - 1);
				}
				trainingBean.setTrainingList(trainingList);
				trainingBean.setMessage(message);
			}
		} catch (Exception e) {
			throw e;
		}
		return trainingBean;
	}

	public String deleteTrainingDetails(TrainingBean trainingBean) throws Exception {
		String message = "";
		try {
			message = trainingDAO.deleteTrainingDetails(trainingBean);
		} catch (Exception e) {
			message = CPSConstants.FAILED;
			throw e;
		}
		return message;
	}
}