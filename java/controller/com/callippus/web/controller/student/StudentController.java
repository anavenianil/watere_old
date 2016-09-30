package com.callippus.web.controller.student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.student.StudentBean;
import com.callippus.web.business.student.StudentBusiness;
import com.callippus.web.business.tutionFee.TutionFeeBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;

@Controller
@RequestMapping("/student.htm")
@SessionAttributes

@Service
public class StudentController
{
	@Autowired(required = true)
	private StudentBusiness studentBusiness;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })

	public ModelAndView execute(@ModelAttribute(CPSConstants.STUDENT) StudentBean studentBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ModelAndView mav = null;
		String viewName = "";
		String message = "";

			try
			{
				if(CPSUtils.compareString(CPSConstants.STUDENTCREATION, studentBean.getParam()))
				{
					viewName = "StudentCreation";
					mav = new ModelAndView(viewName, CPSConstants.STUDENT, studentBean);
				}
				
				else if(CPSUtils.compareStrings(CPSConstants.SAVESTUDENT, studentBean.getParam()))
				{
					studentBean.setMessage(studentBusiness.submitStudentDetails(studentBean));
					viewName = CPSConstants.RESULT;
					mav = new ModelAndView(viewName, CPSConstants.STUDENT, studentBean);
					mav.addObject(CPSConstants.MESSAGE, studentBean.getMessage());						
				}
				
				else if(CPSUtils.compareStrings(CPSConstants.STUDENTUPDATION, studentBean.getParam()))
				{
					viewName = "StudentUpdation";
					mav = new ModelAndView(viewName, CPSConstants.STUDENT, studentBean);
				}
				else if(CPSUtils.compareStrings(CPSConstants.STUDENTDELETION, studentBean.getParam()))
				{
					viewName = "StudentDeletion";
					mav = new ModelAndView(viewName, CPSConstants.STUDENT, studentBean);
				}
				else if(CPSUtils.compareStrings(CPSConstants.STUDENTSELECTION, studentBean.getParam()))
				{
					viewName = "StudentSelection";
					mav = new ModelAndView(viewName, CPSConstants.STUDENT, studentBean);
				}
				else if(CPSUtils.compareStrings(CPSConstants.STUDENTLIST, studentBean.getParam()))
				{
					viewName = "StudentList";
					mav = new ModelAndView(viewName, CPSConstants.STUDENTLIST, studentBean);
					
				}
			}catch (Exception e) 
			{ 
				e.printStackTrace();
			}
			finally
			{
				
			}
		return mav;
	}
}
