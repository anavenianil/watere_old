package com.callippus.web.controller.file;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.callippus.web.beans.file.FileUploadBean;
import com.callippus.web.beans.file.FilesBean;
import com.callippus.web.beans.file.UploadAndDownloadBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.FileUpload;
import com.callippus.web.controller.exception.ErpAction;
import com.callippus.web.controller.exception.ResultStatus;
import com.callippus.web.controller.exception.SetErpException;

@Controller
@RequestMapping("/file.htm")
@SessionAttributes
public class UploadAndDownloadController {

	private static Log log = LogFactory.getLog(UploadAndDownloadController.class);
	@Autowired
	private FileUpload fileUpload;

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
					File reportFile = new File(session.getServletContext().getResource("/repository/").getPath());
					filePath = reportFile.getAbsolutePath() + "/";
					if (CPSUtils.compareStrings(upDownBean.getType(), CPSConstants.CGHS)) {// this condition is for folder path
						filePath += CPSConstants.CGHSATTACHMENTS + "/";			
						fileName = fileUpload.uploadCghsFile(value, key, value.getOriginalFilename().split("\\.")[1], filePath, CPSConstants.CHANGE,upDownBean.getRequestId());
					} else if (CPSUtils.compareStrings(upDownBean.getType(), CPSConstants.TRANSFER)) {
						filePath += CPSConstants.TRANSFERATTACHMENTS + "/";
						fileName = fileUpload.uploadFile(value, key, value.getOriginalFilename().split("\\.")[1], filePath, CPSConstants.CHANGE);
					}
					log.debug("Path::::::" + filePath);
					
					
                       
					session.setAttribute(key, fileName);
				}
				mav = new ModelAndView(CPSConstants.REQUESTRESULTPAGE, CPSConstants.COMMAND, upDownBean);
			}
			if (CPSUtils.compareStrings(CPSConstants.UPLOADTODATABASE, upDownBean.getParam())) {
				//Upload file to Database
				if(!CPSUtils.isNullOrEmpty(upDownBean.getUploadFile())){
				Map<String, MultipartFile> map =new HashMap<String, MultipartFile>();
				map.put(upDownBean.getDesc(), upDownBean.getUploadFile());
				FileUploadBean fub=new FileUploadBean();
				fub.setFiles(map);
				upDownBean.setFileUploadBean(fub);
				}
				if(!CPSUtils.isNullOrEmpty(upDownBean.getFileUploadBean())){
				upDownBean.setFileUploadBean(fileUpload.uploadFileToDatabase(upDownBean.getFileUploadBean()));
				}
				session.setAttribute("fileID", upDownBean.getFileUploadBean().getFileIds().get(upDownBean.getDesc()));
				mav = new ModelAndView(CPSConstants.REQUESTRESULTPAGE, CPSConstants.COMMAND, upDownBean);
			}
			if (CPSUtils.compareStrings(CPSConstants.DOWNLOADFROMDATABASE, upDownBean.getParam())) {
				//Download file from Database
				FilesBean file = fileUpload.downloadFromDatabase(upDownBean.getDownloadId());
				response.setContentType(file.getType());
				response.setContentLength(file.getFile().length);
				response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFilename() + "\"");
				FileCopyUtils.copy(file.getFile(), response.getOutputStream());
				mav = new ModelAndView(CPSConstants.DOWNLOADFILE, CPSConstants.COMMAND, upDownBean);
			}

		} catch (Exception e) {
			throw new ResultStatus(SetErpException.setException(e).getResultStatus().getErrorCode());
		}
		return mav;

	}
}
