package com.callippus.web.controller.training;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.callippus.web.beans.training.TrainingBean;
import com.callippus.web.business.training.TrainingBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/training.htm")
@SessionAttributes
public class TrainingController {

	private static Log log = LogFactory.getLog(TrainingController.class);
	@Autowired
	private TrainingBusiness trainingBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.TRAINING) TrainingBean trainingBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mav = null;
		HttpSession session = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);

			if (CPSUtils.isNullOrEmpty(trainingBean.getParam())) {
				trainingBean.setParam(CPSConstants.VIEWTRAINING);
			} else if (CPSUtils.compareStrings(CPSConstants.PAGING, trainingBean.getParam())) {
				mav = new ModelAndView(CPSConstants.TRAININGLIST, CPSConstants.TRAINING, trainingBean);
			}

			if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.CHANGESFID))) {
				trainingBean.setSfid(session.getAttribute(CPSConstants.CHANGESFID).toString());
				if (CPSUtils.compareStrings(CPSConstants.VIEW, trainingBean.getParam())) {
					if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
						log.debug("TrainingController --> onSubmit --> param=view");
						session.setAttribute("trainingType", "admin");
						trainingBean = trainingBusiness.getTrainingDetails(trainingBean, request);
						session.setAttribute(CPSConstants.TRAININGLIST, trainingBean.getTrainingList());
						if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.YEARLIST))) {
							session.setAttribute(CPSConstants.YEARLIST, trainingBean.getYearList());
						}
						mav = new ModelAndView(CPSConstants.TRAININGDETAILS, CPSConstants.TRAINING, trainingBean);
						session.setAttribute(CPSConstants.RETURN, CPSConstants.TRAININGDETAILS);
					} else {
						session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
						mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
					}
				} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, trainingBean.getParam()) || CPSUtils.compareStrings(CPSConstants.UPDATE, trainingBean.getParam())) {
					log.debug("TrainingController --> onSubmit --> param=manage");
					trainingBean = trainingBusiness.saveTrainingDetails(trainingBean, request);
					session.setAttribute(CPSConstants.TRAININGLIST, trainingBean.getTrainingList());
					mav = new ModelAndView(CPSConstants.TRAININGLIST, CPSConstants.TRAINING, trainingBean);
					mav.addObject(CPSConstants.MESSAGE, trainingBean.getMessage());
				} else if (CPSUtils.compareStrings(CPSConstants.DELETE, trainingBean.getParam())) {
					log.debug("TrainingController --> onSubmit --> param=DELETE");
					String message = trainingBusiness.deleteTrainingDetails(trainingBean);
					trainingBean = trainingBusiness.getTrainingDetails(trainingBean, request);
					session.setAttribute(CPSConstants.TRAININGLIST, trainingBean.getTrainingList());
					mav = new ModelAndView(CPSConstants.TRAININGLIST, CPSConstants.TRAINING, trainingBean);
					mav.addObject(CPSConstants.MESSAGE, message);

				}
			} else {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					mav = new ModelAndView(CPSConstants.TRAININGDETAILS, CPSConstants.TRAINING, trainingBean);
					mav.addObject(CPSConstants.MESSAGE, CPSConstants.CHANGESFIDFIRST);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			}
			if (CPSUtils.compareStrings("editTrainingDetails", trainingBean.getParam())) {
				log.debug("TrainingController --> onSubmit --> param=editTrainingDetails");
				trainingBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				trainingBean = trainingBusiness.getTrainingDetails(trainingBean, request);
				session.setAttribute(CPSConstants.TRAININGLIST, trainingBean.getTrainingList());
				mav = new ModelAndView("CreateTraining", CPSConstants.TRAINING, trainingBean);
				session.setAttribute(CPSConstants.YEARLIST, trainingBean.getYearList());

			} else if (CPSUtils.compareStrings(CPSConstants.VIEWTRAINING, trainingBean.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					log.debug("TrainingController --> onSubmit --> param=VIEWTRAINING");
					trainingBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					trainingBean = trainingBusiness.getTrainingDetails(trainingBean, request);
					session.setAttribute("trainingType", "user");
					mav = new ModelAndView(CPSConstants.VIEWMASTER, CPSConstants.TRAINING, trainingBean);
					mav.addObject(CPSConstants.MESSAGE, CPSConstants.VIEWTRAINING);
					session.setAttribute(CPSConstants.RETURN, CPSConstants.VIEWMASTER);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}