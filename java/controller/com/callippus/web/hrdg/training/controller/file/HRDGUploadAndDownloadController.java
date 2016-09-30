package com.callippus.web.hrdg.training.controller.file;

import java.io.File;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.hrdg.file.UploadAndDownloadBean;

@Controller
@RequestMapping("/fileUploadAndDownload.htm")
@SessionAttributes
public class HRDGUploadAndDownloadController {

	private static Log log = LogFactory.getLog(HRDGUploadAndDownloadController.class);
	@Autowired
	private HRDGFileUpload fileUpload;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView execute(@ModelAttribute UploadAndDownloadBean upDownBean, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = null;
		HttpSession session = null;
		String fileName = null;
		String filePath = "";
		try {
			ErpAction.sessionChecks(request);
			session = request.getSession(true);

			if (CPSUtils.compareStrings(CPSConstants.DOWNLOAD, upDownBean.getParam())) {
				request.setAttribute(CPSConstants.FILENAME, request.getParameter(CPSConstants.REQUESTID));
				mav = new ModelAndView(CPSConstants.DOWNLOADFILE, CPSConstants.COMMAND, upDownBean);
			}
			if (CPSUtils.compareStrings(CPSConstants.UPLOAD, upDownBean.getParam())) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Map<String, MultipartFile> files = multiRequest.getFileMap();
				if (files != null && !files.isEmpty()) {
					String key = files.entrySet().iterator().next().getKey();
					// above key value is the jsp property name
					MultipartFile value = files.entrySet().iterator().next().getValue();
					UploadAndDownloadBean bean = new UploadAndDownloadBean();
					bean.setContentType(value.getContentType());
					bean.setOriginalFilename(value.getOriginalFilename());
					bean.setName(value.getName());
					bean.setFileContent(value);
					
					File reportFile = new File(session.getServletContext().getResource("/repository/").getPath());
					filePath = reportFile.getAbsolutePath() + "/";
					if (CPSUtils.compareStrings(upDownBean.getType(), CPSConstants.TRANINING)) {// this condition is for folder path
						filePath += CPSConstants.HRDG + "/";
						filePath += CPSConstants.TRAINING + "/";						
					} 
					log.debug("Path::::::" + filePath);
					fileName = fileUpload.uploadFile(value, key, value.getOriginalFilename().split("\\.")[1], filePath, CPSConstants.CHANGE);
					session.setAttribute("filepath", filePath);
					bean.setName(filePath+fileName);
					session.setAttribute("trainingBrochure", bean);
				
				}
				mav = new ModelAndView(CPSConstants.REQUESTRESULTPAGE, CPSConstants.COMMAND, upDownBean);
			}

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;

	}
}

