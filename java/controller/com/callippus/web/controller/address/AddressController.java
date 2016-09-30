package com.callippus.web.controller.address;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.callippus.web.beans.address.AddressBean;
import com.callippus.web.beans.requests.AddressRequestBean;
import com.callippus.web.business.address.AddressBusiness;
import com.callippus.web.business.requestprocess.AddressRequestProcess;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/address.htm")
@SessionAttributes
public class AddressController {
	private static Log log = LogFactory.getLog(AddressController.class);
	@Autowired
	private AddressBusiness addressBusiness;
	@Autowired
	private AddressRequestProcess addressRequestProcess;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute(CPSConstants.ADDRESS) AddressBean addressBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String viewPage = null;
		String value = null;
		try {
			ErpAction.userChecks(request);
			session = request.getSession(true);
			if (CPSUtils.compareStrings(CPSConstants.PAGING, addressBean.getParam())) {
				viewPage = CPSConstants.ADDRESSLIST;
				mav = new ModelAndView(viewPage, CPSConstants.COMMAND, addressBean);
			} else if (CPSUtils.isNullOrEmpty(addressBean.getParam())) {
				addressBean.setParam(CPSConstants.VIEWADDRESS);
			}
			if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.JSONADDRESSLIST))) {
				session.removeAttribute(CPSConstants.JSONADDRESSLIST);
			}
			if (!CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.CHANGESFID))) {
		 		addressBean.setSfid(session.getAttribute(CPSConstants.CHANGESFID).toString());
				if (CPSUtils.compareStrings(CPSConstants.VIEW, addressBean.getParam())) {
					if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
						log.debug("::AddressController --> onSumit --> param=view");
						addressBean = addressBusiness.getAddressHomeDetails(addressBean);
						viewPage = CPSConstants.VIEWADDRESS;
						session.setAttribute(CPSConstants.RETURN, CPSConstants.VIEWADDRESS);
						session.setAttribute(CPSConstants.JSONADDRESSLIST,(JSONArray) JSONSerializer.toJSON(addressBean.getAddressList()));
						// if
						// (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.DISTRICTJSONLIST)))
						// {
						if (CPSUtils.checkList(addressBusiness.getDistrictList())) {
							session.setAttribute(CPSConstants.DISTRICTJSONLIST, (JSONArray) JSONSerializer.toJSON(addressBusiness.getDistrictList()));
						}
						// }
						// if
						// (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.STATELIST)))
						// {
						if (CPSUtils.checkList(addressBusiness.getStateList())) {
							session.setAttribute(CPSConstants.STATELIST, addressBusiness.getStateList());
						}
						// }
						mav = new ModelAndView(viewPage, CPSConstants.COMMAND, addressBean);
						
					} else {
						session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
						mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
					}
				} 
				else if (CPSUtils.compareStrings(CPSConstants.VIEW, addressBean.getParam())) {
					if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
						log.debug("::AddressController --> onSumit --> param=view");
						addressBean = addressBusiness.getAddressHomeDetails(addressBean);
						viewPage = CPSConstants.VIEWADDRESS;
						session.setAttribute(CPSConstants.RETURN, CPSConstants.VIEWADDRESS);
						session.setAttribute(CPSConstants.JSONADDRESSLIST,(JSONArray) JSONSerializer.toJSON(addressBean.getAddressList()));
						// if
						// (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.DISTRICTJSONLIST)))
						// {
						if (CPSUtils.checkList(addressBusiness.getDistrictList())) {
							session.setAttribute(CPSConstants.DISTRICTJSONLIST, (JSONArray) JSONSerializer.toJSON(addressBusiness.getDistrictList()));
						}
						// }
						// if
						// (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.STATELIST)))
						// {
						if (CPSUtils.checkList(addressBusiness.getStateList())) {
							session.setAttribute(CPSConstants.STATELIST, addressBusiness.getStateList());
						}
						// }
						mav = new ModelAndView(viewPage, CPSConstants.COMMAND, addressBean);
						
					} else {
						session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
						mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
					}
				} 
			
				
				else if (CPSUtils.compareStrings(CPSConstants.MANAGE, addressBean.getParam())) {
					log.debug("::AddressController --> onSubmit --> param=manage");
					addressBean.setAddressTypeId(request.getParameter("addressTypeId"));
					addressBean = addressBusiness.manageAddressDetails(addressBean);
					session.setAttribute(CPSConstants.JSONADDRESSLIST,(JSONArray) JSONSerializer.toJSON(addressBean.getAddressList()));

					viewPage = CPSConstants.ADDRESSLIST;
			        
					mav = new ModelAndView(viewPage, CPSConstants.COMMAND, addressBean);
				} else if (CPSUtils.compareStrings(CPSConstants.DELETE, addressBean.getParam())) {
					log.debug("::AddressController --> onSubmit --> param=delete");
					viewPage = CPSConstants.ADDRESSLIST;
					addressBean = addressBusiness.deleteAddressDetails(addressBean);
					mav = new ModelAndView(viewPage, CPSConstants.COMMAND, addressBean);
				}
			} else {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					viewPage = CPSConstants.VIEWADDRESS;
					addressBean.setMessage(CPSConstants.CHANGESFIDFIRST);
					mav = new ModelAndView(viewPage, CPSConstants.COMMAND, addressBean);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			}
			if (CPSUtils.compareStrings(CPSConstants.VIEWADDRESS, addressBean.getParam())) {
				if (!CPSUtils.isNull(request.getParameter("redirect")) && (CPSUtils.compareStrings(request.getParameter("redirect"), "true"))) {
					log.debug("::AddressController --> onSubmit --> param=viewAddress");
					addressBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
					addressBean = addressBusiness.getAddressHomeDetails(addressBean);
					viewPage = CPSConstants.VIEWMASTER;
					mav = new ModelAndView(viewPage, CPSConstants.COMMAND, addressBean);
					session.setAttribute(CPSConstants.RETURN, CPSConstants.VIEWMASTER);
				} else {
					session.setAttribute("path", request.getContextPath() + request.getServletPath() + "?param=" + request.getParameter("param"));
					mav = new ModelAndView(new RedirectView(request.getContextPath() + "/views/example.jsp"));
				}
			} else if (CPSUtils.compareStrings(CPSConstants.EDITADDRESSDETAILS, addressBean.getParam())) {
				viewPage = CPSConstants.CREATEADDRESS;
				addressBean.setSfid(session.getAttribute(CPSConstants.SFID).toString());
				addressBean.setId(request.getParameter("id").toString());
				addressBean.setType(CPSConstants.EDIT);
				addressBean = addressBusiness.getEditAddressDetails(addressBean);
				session.setAttribute("addressBean", (JSONObject) JSONSerializer.toJSON(addressBean));
				// if
				// (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.DISTRICTJSONLIST)))
				// {
				if (CPSUtils.checkList(addressBusiness.getDistrictList())) {
					session.setAttribute(CPSConstants.DISTRICTJSONLIST, (JSONArray) JSONSerializer.toJSON(addressBusiness.getDistrictList()));
				}
				// }
				// if
				// (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.STATELIST)))
				// {
				if (CPSUtils.checkList(addressBusiness.getStateList())) {
					session.setAttribute(CPSConstants.STATELIST, addressBusiness.getStateList());
				}
				// }
				addressBean.setMessage("addressRequest");
				mav = new ModelAndView(viewPage, CPSConstants.COMMAND, addressBean);
			} else if (CPSUtils.compareStrings(CPSConstants.SUBMITREQUEST, addressBean.getParam())) {
				log.debug("::AddressController --> onSubmit --> param=submitRequest");
				AddressRequestBean arb = new AddressRequestBean();
				BeanUtils.copyProperties(addressBean, arb);
				arb.setSfID((String) session.getAttribute(CPSConstants.SFID));
				arb.setIpAddress(request.getRemoteAddr());
				// call AddressRequestProcess initial workflow method
				addressBean.setMessage(addressRequestProcess.initWorkflow(arb));
				viewPage = CPSConstants.REQUESTRESULTPAGE;
				mav = new ModelAndView(viewPage, CPSConstants.COMMAND, addressBean);
				value = CPSConstants.REQUEST;
				if(!CPSUtils.isNullOrEmpty(addressBean.getMessage())){
				mav.addObject(CPSConstants.MESSAGE, addressBean.getMessage());
				}
				if(!CPSUtils.isNullOrEmpty(arb.getRemarks())){
					mav.addObject(CPSConstants.REMARKS, arb.getRemarks());
				}
			}
			if (CPSUtils.checkList(addressBean.getAddressList())) {
				int size = addressBean.getAddressList().size();
				if (size > 0) {
					session.setAttribute(CPSConstants.MASTERJSON, addressBean.getAddressList().get(size - 1));
					addressBean.getAddressList().remove(size - 1);
				}
				session.setAttribute(CPSConstants.JSONADDRESSLIST, (JSONArray) JSONSerializer.toJSON(addressBean.getAddressList()));
			}
			if (CPSUtils.checkList(addressBean.getAddressTypeList())) {
				// mav.addObject(CPSConstants.ADDRESSTYPELIST,
				// addressBean.getAddressTypeList());
				if (CPSUtils.isNullOrEmpty(session.getAttribute(CPSConstants.ADDRESSTYPELIST))) {
					session.setAttribute(CPSConstants.ADDRESSTYPELIST, addressBean.getAddressTypeList());
				}
			}
			mav.addObject(CPSConstants.MESSAGE, addressBean.getMessage());
			mav.addObject(CPSConstants.VALUE, value);
		}catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;
	}
}
