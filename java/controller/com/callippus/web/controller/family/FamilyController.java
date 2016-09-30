package com.callippus.web.controller.family;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.callippus.web.beans.family.FamilyBean;
import com.callippus.web.beans.file.FileUploadBean;
import com.callippus.web.beans.requests.FamilyRequestBean;
import com.callippus.web.business.family.FamilyBusiness;
import com.callippus.web.business.requestprocess.FamilyRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.FileUpload;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;


@Controller
@RequestMapping("/family.htm")
@SessionAttributes
public class FamilyController {

	private static Log log = LogFactory.getLog(FamilyController.class);
	@Autowired
	private FamilyBusiness familyBusiness;
	@Autowired
	private FamilyRequestProcess familyRequestProcess;
	@Autowired
	private FileUpload fileUpload;


	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.FAMILY) FamilyBean familyBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String message = "";
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);

			if (CPSUtils.compareStrings(CPSConstants.PAGING, familyBean.getParam())) {
				mav = new ModelAndView(CPSConstants.FAMILYLIST, CPSConstants.FAMILY, familyBean);
			} else if (CPSUtils.isNullOrEmpty(familyBean.getParam())) {
				familyBean.setParam(CPSConstants.VIEW);
			}

			if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.JSONEMPFAMILYLIST))) {
				session.removeAttribute(CPSConstants.JSONEMPFAMILYLIST);
			}
			if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.CHANGESFID))) {
				familyBean.setSfid(session.getAttribute(CPSConstants.CHANGESFID).toString());
				if (CPSUtils.compareStrings(CPSConstants.VIEW, familyBean.getParam())) {
					if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
						log.debug("familyController --> onSubmit --> param=view");
						mav = new ModelAndView(CPSConstants.FAMILYDETAILS, CPSConstants.FAMILY, familyBean);
						familyBean = familyBusiness.getFamilyDetails(familyBean);
						if(!CPSUtils.isNullOrEmpty(familyBean.getCghscardfile())){
							 session.setAttribute("cghscard", familyBean.getCghscardfile());
							familyBean.setCghscardfile(null);						}

						// mav.addObject(CPSConstants.STATELIST, familyBusiness.getStateList());
						if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.STATELIST))) {
							session.setAttribute(CPSConstants.STATELIST, familyBusiness.getStateList());
						}
						familyBean = familyBusiness.getMasterData(familyBean);
						// mav.addObject(CPSConstants.RELATIONLIST, familyBean.getRelationList());
						session.setAttribute(CPSConstants.BLOODGROUPLIST, familyBean.getBloodGroupList());
						session.setAttribute(CPSConstants.RELATIONLIST, familyBean.getRelationList());
						// mav.addObject(CPSConstants.MARITALLIST, familyBean.getMaritalStatusList());
						session.setAttribute(CPSConstants.MARITALSTATUSLIST, familyBean.getMaritalStatusList());
						session.setAttribute(CPSConstants.DISTRICTJSONLIST, (JSONArray) JSONSerializer.toJSON(familyBusiness.getDistrictList()));
						session.setAttribute(CPSConstants.DISABILITYLIST, familyBean.getDisabilityList());
						session.setAttribute(CPSConstants.RETURN, CPSConstants.FAMILYDETAILS);
					} else {
						session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
						mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
					}
				} else if (CPSUtils.compareStrings(CPSConstants.MANAGE, familyBean.getParam())) {
					Map<String, MultipartFile> map = new HashMap<String, MultipartFile>();
					log.debug("FAMILYController --> onSubmit --> param=manage");
					if (!CPSUtils.isNullOrEmpty(familyBean.getCghscardfile())) {
						 session.setAttribute("cghscard", familyBean.getCghscardfile());
						familyBean.setCghscardfile(null);
					}
					//FamilyRequestBean frb = new FamilyRequestBean();
					if (!CPSUtils.isNullOrEmpty(((MultipartFile)session.getAttribute("cghscard")).getOriginalFilename())) {
						map.put("cghscard", ((MultipartFile)session.getAttribute("cghscard")));
					}
					FileUploadBean fub = new FileUploadBean();
					if (map.size() != 0) {
						fub.setFiles(map);
						fub = fileUpload.uploadFileToDatabase(fub);
						familyBean.setCghscard((String.valueOf(fub.getFileIds().get("cghscard"))));
						//crpb.setPrescriptionFile(String.valueOf(fub.getFileIds().get("cghscard")));
					}
					familyBean.setRelationId(familyBean.getRid());
					familyBean = familyBusiness.manageFamily(familyBean);
					
					if (CPSUtils.checkList(familyBean.getEmpFamilyList())) {
						//session.removeAttribute("familyList");
						session.setAttribute(CPSConstants.JSONEMPFAMILYLIST, (JSONArray) JSONSerializer.toJSON(familyBean.getEmpFamilyList()));
					}
					mav = new ModelAndView(CPSConstants.FAMILYLIST, CPSConstants.FAMILY, familyBean);
					mav.addObject("reason", familyBean.getReason());
				} else if (CPSUtils.compareStrings(CPSConstants.DELETE, familyBean.getParam())) {
					log.debug("FAMILYController --> onSubmit --> param=delete");
					familyBean = familyBusiness.deleteFamily(familyBean);
					mav = new ModelAndView(CPSConstants.FAMILYLIST, CPSConstants.FAMILY, familyBean);
				}
			} else {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					mav = new ModelAndView(CPSConstants.FAMILYDETAILS, CPSConstants.FAMILY, familyBean);
					familyBean.setMessage(CPSConstants.CHANGESFIDFIRST);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			}
			if (CPSUtils.compareStrings(CPSConstants.VIEWFAMILYDETAILS, familyBean.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					log.debug("FAMILYController --> onSubmit --> param=ViewFAMILYDetails");
					session.setAttribute("roleType", "user");
					familyBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					familyBean = familyBusiness.getFamilyDetails(familyBean);
					familyBean.setMessage(CPSConstants.VIEWFAMILYDETAILS);
					mav = new ModelAndView(CPSConstants.VIEWMASTER, CPSConstants.FAMILY, familyBean);
					session.setAttribute(CPSConstants.RETURN, CPSConstants.VIEWMASTER);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITREQUEST, familyBean.getParam())) {
				log.debug("CreateEmployeeController --> onSubmit --> param=submitRequest");
				/**
				 * FamilyRequest mapping employee bean to FamilyRequestBean
				 */
				FamilyRequestBean frb = new FamilyRequestBean();
				BeanUtils.copyProperties(familyBean, frb);

				frb.setSfID((String) session.getAttribute(CPSConstants.SFID));
				frb.setIpAddress(request.getRemoteAddr());
				// call PISRequestProcess initial workflow method
				
				
				
				
				message = familyRequestProcess.initWorkflow(frb);
				String remarks[] = message.split("#");
				mav = new ModelAndView(CPSConstants.REQUESTRESULTPAGE, CPSConstants.FAMILY, familyBean);
				mav.addObject(CPSConstants.MESSAGE, remarks[0]);
				if (message.contains("#"))
					mav.addObject(CPSConstants.REMARKS, remarks[1]);
				return mav;
			} else if (CPSUtils.compareStrings(CPSConstants.GETMEMBER, familyBean.getParam())) {
				familyBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				mav = new ModelAndView(CPSConstants.CREATEFAMILY, CPSConstants.FAMILY, familyBean);
				familyBean = familyBusiness.getFamilyDetails(familyBean);
				familyBean.setMessage("familyRequest");
				mav.addObject(CPSConstants.STATELIST, familyBusiness.getStateList());
				familyBean = familyBusiness.getMasterData(familyBean);
				session.setAttribute(CPSConstants.DISABILITYLIST, familyBean.getDisabilityList());
				mav.addObject(CPSConstants.RELATIONLIST, familyBean.getRelationList());
				mav.addObject(CPSConstants.BLOODGROUPLIST, familyBean.getBloodGroupList());
				mav.addObject(CPSConstants.MARITALSTATUSLIST, familyBean.getMaritalStatusList());
				session.setAttribute(CPSConstants.DISTRICTJSONLIST, (JSONArray) JSONSerializer.toJSON(familyBusiness.getDistrictList()));
			}
			mav.addObject(CPSConstants.MESSAGE, familyBean.getMessage());
			if (CPSUtils.checkList(familyBean.getEmpFamilyList())) {
				int size = familyBean.getEmpFamilyList().size();
				if (size > 0) {
					session.setAttribute(CPSConstants.MASTERJSON, familyBean.getEmpFamilyList().get(size - 1));
					familyBean.getEmpFamilyList().remove(size - 1);
				}
			}
			if (!CPSUtils.isNull(familyBean.getEmpFamilyList()))
				session.setAttribute(CPSConstants.JSONEMPFAMILYLIST, (JSONArray) JSONSerializer.toJSON(familyBean.getEmpFamilyList()));

			// if(CPSUtils.checkList(familyBean.getAddressTypeList())){
			session.setAttribute(CPSConstants.ADDRESSLIST, familyBean.getAddressTypeList());
			// }

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}
