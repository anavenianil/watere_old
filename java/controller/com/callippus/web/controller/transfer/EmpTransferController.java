package com.callippus.web.controller.transfer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

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

import com.callippus.web.beans.transfer.EmpTransferBean;
import com.callippus.web.business.requestprocess.TransferRequestProcess;
import com.callippus.web.business.transfer.EmpTransferBusiness;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/transfer.htm")
@SessionAttributes
public class EmpTransferController {
	private static Log log = LogFactory.getLog(EmpTransferController.class);
	@Autowired
	private EmpTransferBusiness empTransferBusiness;
	@Autowired
	private TransferRequestProcess transferRequestProcess;

	@SuppressWarnings("unchecked")
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.TRANSFER) EmpTransferBean transferBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewName = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			transferBean.setSfID(session.getAttribute(CPSConstants.SFID).toString());
			transferBean.setEmpRoles((List<String>) session.getAttribute(CPSConstants.ROLELIST));
			if (CPSUtils.isNullOrEmpty(transferBean.getParam())) {
				transferBean.setParam(CPSConstants.HOME);
			}

			if (CPSUtils.compareStrings(transferBean.getParam(), CPSConstants.HOME)) {
				log.debug("EmpTransferController --> onSubmit --> param=home");
				transferBean = empTransferBusiness.getTransferHomeDetails(transferBean);
				viewName = CPSConstants.EMPTRANSFERPAGE;
			} else if (CPSUtils.compareStrings(transferBean.getParam(), CPSConstants.GETEMPLOYEES)) {
				log.debug("EmpTransferController --> onSubmit --> param=getEmployees");
				transferBean = empTransferBusiness.getEmployees(transferBean);
				session.setAttribute(CPSConstants.JSONARRAYOBJECT, (JSONArray) JSONSerializer.toJSON(transferBean.getEmployeesList()));
				viewName = CPSConstants.RESULTOBJECTS;
			} else if (CPSUtils.compareStrings(transferBean.getParam(), CPSConstants.SUBMIT)) {
				log.debug("EmpTransferController --> onSubmit --> param=submit");
				transferBean.setAttachment((String) session.getAttribute(CPSConstants.ATTACHMENTFILE));
				transferBean.setIpAddress(request.getRemoteAddr());
				transferBean.setMessage(transferRequestProcess.initWorkflow(transferBean));
				session.setAttribute(CPSConstants.ATTACHMENTFILE, null);
				viewName = CPSConstants.REQUESTRESULTPAGE;
			} else if (CPSUtils.compareStrings(transferBean.getParam(), CPSConstants.TRANSFERTXN)) {
				log.debug("EmpTransferController --> onSubmit --> param=getTransferTxnDetails");
				transferBean = empTransferBusiness.getTransferTxnDetails(transferBean);
				session.setAttribute(CPSConstants.ORGINSTANCELIST, transferBean.getOrgInstanceList());
				session.setAttribute(CPSConstants.DEPARTMENTSLIST, transferBean.getDepartmentsList());
				session.setAttribute(CPSConstants.TRANSFERTXNLIST, transferBean.getEmpTransferTxnList());
				session.setAttribute("doPartJSON", (JSONArray) JSONSerializer.toJSON(transferBean.getDoPartList()));
				session.setAttribute("orgRoleJSON", (JSONArray) JSONSerializer.toJSON(transferBean.getOrgInstanceList()));
				session.setAttribute("departmentJSON", (JSONArray) JSONSerializer.toJSON(transferBean.getDepartmentsList()));
				viewName = CPSConstants.TRANSFERTXNDETAILS;
			} else if (CPSUtils.compareStrings(transferBean.getParam(), CPSConstants.MANAGETRANSTXN)) {
				log.debug("EmpTransferController --> onSubmit --> param=manageTransTxnDetails");
				transferBean.setMessage(empTransferBusiness.manageTransTxnDetails(transferBean));
				transferBean = empTransferBusiness.getTransferTxnDetails(transferBean);
				session.setAttribute(CPSConstants.ORGINSTANCELIST, transferBean.getOrgInstanceList());
				session.setAttribute(CPSConstants.DEPARTMENTSLIST, transferBean.getDepartmentsList());
				session.setAttribute(CPSConstants.TRANSFERTXNLIST, transferBean.getEmpTransferTxnList());
				viewName = CPSConstants.TRANSFERTXNLIST;
			} else if (CPSUtils.compareStrings(transferBean.getParam(), CPSConstants.DELETETRANSTXN)) {
				log.debug("EmpTransferController --> onSubmit --> param=deleteTransTxnDetails");
				transferBean.setMessage(empTransferBusiness.deleteTransTxnDetails(transferBean.getDoPartID()));
				transferBean = empTransferBusiness.getTransferTxnDetails(transferBean);
				session.setAttribute(CPSConstants.ORGINSTANCELIST, transferBean.getOrgInstanceList());
				session.setAttribute(CPSConstants.DEPARTMENTSLIST, transferBean.getDepartmentsList());
				session.setAttribute(CPSConstants.TRANSFERTXNLIST, transferBean.getEmpTransferTxnList());
				viewName = CPSConstants.TRANSFERTXNLIST;
			} else if (CPSUtils.compareStrings(transferBean.getParam(), CPSConstants.GETDEPARTMENTS)) {
				log.debug("EmpTransferController --> onSubmit --> param=getDepartments");
				transferBean = empTransferBusiness.getDepartments(transferBean);
				viewName = "Department";
				if (!CPSUtils.compareStrings(transferBean.getResult(), CPSConstants.SUCCESS)) {
					transferBean.setMessage(CPSConstants.EMPNOTEXISTS);
				} else {
					transferBean.setMessage(CPSConstants.EMPEXITS);
					session.setAttribute("department", transferBean.getDepartmentsList());
				}
			}
			mav = new ModelAndView(viewName, CPSConstants.TRANSFER, transferBean);
			if (!CPSUtils.isNullOrEmpty(transferBean.getResult())) {
				mav.addObject(CPSConstants.RESULT, transferBean.getResult());
			}
			if (!CPSUtils.isNullOrEmpty(transferBean.getType())) {
				mav.addObject(CPSConstants.TYPE, transferBean.getType());
			}
			if (!CPSUtils.isNullOrEmpty(transferBean.getMessage())) {
				mav.addObject(CPSConstants.MESSAGE, transferBean.getMessage());
			}
		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}