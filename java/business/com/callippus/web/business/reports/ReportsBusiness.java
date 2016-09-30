package com.callippus.web.business.reports;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.dto.CategoryDTO;
import com.callippus.web.beans.dto.CommunityDTO;
import com.callippus.web.beans.dto.DesignationDTO;
import com.callippus.web.beans.dto.GroupDTO;
import com.callippus.web.beans.dto.ReligionDTO;
import com.callippus.web.beans.reports.ReportsBean;
import com.callippus.web.controller.common.CPSConstants;
import com.callippus.web.controller.common.CPSUtils;
import com.callippus.web.controller.common.PDFConverter;
import com.callippus.web.controller.exception.SetErpException;
import com.callippus.web.dao.common.IComonObjectDAO;
import com.callippus.web.dao.empsearch.IEmployeeSearchDAO;
import com.callippus.web.mmg.cashbuildup.dto.InventoryMasterDTO;
import com.callippus.web.tada.dto.TadaApprovalRequestDTO;

@Service
public class ReportsBusiness {
	private static Log log = LogFactory.getLog(ReportsBusiness.class);
	@Autowired
	private IComonObjectDAO commonDataDAO;
	@Autowired
	private IEmployeeSearchDAO empSearchDAO;

	public ReportsBean getReportHomeDate(ReportsBean reportsBean,
			HttpServletRequest request) throws Exception {
		HttpSession session = null;
		try {
			log.debug("::ReportsBusiness --> ");
			session = request.getSession(true);
			if (!CPSUtils.isNull(session
					.getAttribute("designationReportHomeList"))) {
				session.removeAttribute("designationReportHomeList");
			}
			if (!CPSUtils
					.isNull(session.getAttribute("categoryReportHomeList"))) {
				session.removeAttribute("categoryReportHomeList");
			}
			if (!CPSUtils.isNull(session
					.getAttribute("inventoryReportHomeList"))) {
				session.removeAttribute("inventoryReportHomeList");
			}
			if (!CPSUtils
					.isNull(session.getAttribute("divisionReportHomeList"))) {
				session.removeAttribute("divisionReportHomeList");
			}
			if (!CPSUtils.isNull(session.getAttribute("groupReportHomeList"))) {
				session.removeAttribute("groupReportHomeList");
			}
			if (!CPSUtils
					.isNull(session.getAttribute("religionReportHomeList"))) {
				session.removeAttribute("religionReportHomeList");
			}
			if (!CPSUtils.isNull(session
					.getAttribute("communityReportHomeList"))) {
				session.removeAttribute("communityReportHomeList");
			}

			if (request.getParameter("param") != null
					&& request.getParameter("param").equals("categoryreport")) {
				log.debug("::ReportsBusiness --> onSubmit --> param=categoryreport");
				reportsBean.setList(commonDataDAO.getCategoryList());

				// reportsBean.setList(commonDataDAO.getSubCategoryList());

				session.setAttribute("categoryReportHomeList",
						reportsBean.getList());
				// }
				// reportsBean.setList((List)session.getAttribute("categoryReportHomeList"));
				String id = "";
				for (int i = 0; i < reportsBean.getList().size(); i++) {
					id += ((CategoryDTO) reportsBean.getList().get(i)).getId()
							+ ",";

				}
				reportsBean.setValue(id);
			} else if (request.getParameter("param") != null
					&& request.getParameter("param").equals("inventoryreport")) {
				log.debug("::ReportsBusiness --> onSubmit --> param=inventoryreport");
				reportsBean.setList(commonDataDAO.getInventoryNo());
				session.setAttribute("inventoryReportHomeList",
						reportsBean.getList());
				// }
				// reportsBean.setList((List)session.getAttribute("inventoryReportHomeList"));
				String id = "";
				for (int i = 0; i < reportsBean.getList().size(); i++) {
					id += ((InventoryMasterDTO) reportsBean.getList().get(i))
							.getInvId() + ",";

				}
				reportsBean.setValue(id);
			} else if (request.getParameter("param") != null
					&& request.getParameter("param").equals(
							"designationwiseReport")) {
				log.debug("::ReportsBusiness --> onSubmit --> param=designationwiseReport");
				reportsBean.setList(commonDataDAO
						.getMasterData(CPSConstants.DESIGNATIONDTO));
				session.setAttribute("designationReportHomeList",
						reportsBean.getList());
				// }
				// reportsBean.setList((List)session.getAttribute("designationReportHomeList"));
				String id = "";
				for (int i = 0; i < reportsBean.getList().size(); i++) {
					id += ((DesignationDTO) reportsBean.getList().get(i))
							.getId() + ",";

				}
				reportsBean.setValue(id);
			} else if (request.getParameter("param") != null
					&& request.getParameter("param").equals("divisionReport")) {
				log.debug("::ReportsBusiness --> onSubmit --> param=designationwiseReport");
				reportsBean.setDivisionList(commonDataDAO.getDivisionList());
				session.setAttribute("divisionReportHomeList",
						reportsBean.getDivisionList());
				// }
				// reportsBean.setList((List)session.getAttribute("divisionReportHomeList"));
				String id = "";
				for (int i = 0; i < reportsBean.getDivisionList().size(); i++) {
					HashMap hmap = (HashMap) reportsBean.getDivisionList().get(
							i);
					id += hmap.get("ID") + ",";

				}
				reportsBean.setValue(id);
			} else if (request.getParameter("param") != null
					&& request.getParameter("param").equals(
							"EmpGroupwiseReport")) {
				log.debug("::ReportsBusiness --> onSubmit --> param=EmpGroupwiseReport");
				reportsBean.setList(commonDataDAO.getMasterData("GroupDTO"));
				session.setAttribute("groupReportHomeList",
						reportsBean.getList());
				// }
				// reportsBean.setList((List)session.getAttribute("groupReportHomeList"));
				String id = "";
				for (int i = 0; i < reportsBean.getList().size(); i++) {
					id += ((GroupDTO) reportsBean.getList().get(i)).getId()
							+ ",";

				}
				reportsBean.setValue(id);
			} else if (request.getParameter("param") != null
					&& request.getParameter("param").equals(
							"EmpReligionwiseReport")) {
				log.debug("::ReportsBusiness --> onSubmit --> param=EmpReligionwiseReport");
				reportsBean.setList(commonDataDAO
						.getMasterData(CPSConstants.RELIGIONDTO));
				session.setAttribute("religionReportHomeList",
						reportsBean.getList());
				// }
				// reportsBean.setList((List)session.getAttribute("religionReportHomeList"));
				String id = "";
				for (int i = 0; i < reportsBean.getList().size(); i++) {
					id += ((ReligionDTO) reportsBean.getList().get(i)).getId()
							+ ",";

				}
				reportsBean.setValue(id);
			} else if (request.getParameter("param") != null
					&& request.getParameter("param").equals(
							"EmpCommunityReport")) {
				log.debug("::ReportsBusiness --> onSubmit --> param=EmpCommunityReport");
				reportsBean.setList(commonDataDAO
						.getMasterData(CPSConstants.COMMUNITYDTO));
				session.setAttribute("communityReportHomeList",
						reportsBean.getList());
				// }
				// reportsBean.setList((List)session.getAttribute("communityReportHomeList"));
				String id = "";
				for (int i = 0; i < reportsBean.getList().size(); i++) {
					id += ((CommunityDTO) reportsBean.getList().get(i)).getId()
							+ ",";

				}
				reportsBean.setValue(id);
			} else if (request.getParameter("param") != null
					&& request.getParameter("param").equals("EmpHierarchyHome")) {
				log.debug("::ReportsBusiness --> onSubmit --> param=EmpCommunityReport");
				if (!CPSUtils.isNull(reportsBean.getType())
						&& CPSUtils.compareStrings(reportsBean.getType(),
								"Physical")) {
					session.setAttribute("PhyHierarchyReports", (commonDataDAO
							.getMasterData(CPSConstants.DepartmentsDTO)));
					reportsBean.setList((List) session
							.getAttribute("PhyHierarchyReports"));
				} else {
					session.setAttribute("HierarchyReports", (commonDataDAO
							.getMasterData(CPSConstants.ORGINSTANCEDTO)));
					reportsBean.setList((List) session
							.getAttribute("HierarchyReports"));
				}
			} else if (request.getParameter("param") != null
					&& request.getParameter("param").equals("empMapping")) {
				session.setAttribute("HierarchyReports", (commonDataDAO
						.getMasterData(CPSConstants.ORGINSTANCEDTO)));
				reportsBean.setList((List) session
						.getAttribute("HierarchyReports"));
			}
		} catch (Exception e) {
			throw e;
		}
		return reportsBean;
	}

	@SuppressWarnings("deprecation")
	public ReportsBean getPdfReport(ReportsBean bean,
			HttpServletRequest request, Session dbsession,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		ServletOutputStream os = response.getOutputStream();
		InputStream is = null;
		try {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			File jasparPath = new File(session.getServletContext()
					.getResource("/jasper/").getPath());
			parameters.put("SubReportPath", jasparPath.getAbsolutePath()
					+ File.separatorChar);
			parameters.put("RequestID", bean.getRequestID());
			parameters.put("AddressTypeId", bean.getAddressType());
			parameters.put("sfid", bean.getRequesterSfid());
			if (request.getParameter("param").equals("ireportEmpCategorywise")
					|| request.getParameter("param").equalsIgnoreCase(
							"ireportEmpDesignationwise")
					|| request.getParameter("param").equals(
							"ireportEmpGroupwise")
					|| request.getParameter("param").equalsIgnoreCase(
							"ireportReligionwise")
					|| request.getParameter("param").equals(
							"ireportCommunitywise")
					|| request.getParameter("param").equalsIgnoreCase(
							"ireportInventorywise")
					|| request.getParameter("param").equals(
							"ireportEmpDesignationwise")
					|| request.getParameter("param").equalsIgnoreCase(
							"Designationwise")) {
				String[] cat = bean.getValue1().split(",");
				List<Object> value = new ArrayList<Object>();
				for (int i = 0; i < cat.length; i++) {
					value.add(cat[i]);
				}
				parameters.put(bean.getParameter1(), value);
			} else if (request.getParameter("param")
					.equals("ireportEmpDOJwsie")
					|| request.getParameter("param").equals(
							"ireportEmpLastModified")
					|| request.getParameter("param").equals(
							"overAllLoanRequests")) {
				parameters.put(bean.getParameter1(), bean.getValue1());
				parameters.put(bean.getParameter2(), bean.getValue2());
			} else if (request.getParameter("param").equals("empHierarchyPdf")
					|| request.getParameter("param").equals("empMappingPdf")) {
				parameters.put(bean.getParameter1(), bean.getValue1());
			} else if (request.getParameter("param").equals("quarterReport1")) {
				parameters.put("sfid", bean.getSfid());
				if (bean.getQuarterType().equalsIgnoreCase("alloted")) {
					String[] requestid = bean.getRequestID().split(",");
					List<Object> value = new ArrayList<Object>();
					for (int i = 0; i < requestid.length; i++) {
						value.add(requestid[i]);
					}
					parameters.put("RequestID", value);
				}
			} else if (request.getParameter("param").equals("passportReport")) {
				parameters.put("sfid", bean.getSfid());
			} else if (request.getParameter("param").equals(
					"ireportOrganizationwise")) {
				parameters.put(bean.getParameter1(), bean.getValue1());
			} else if (request.getParameter("param").equals("report")
					|| request.getParameter("param").equals(
							"loanAcquittanceReport")) {
				String[] requestid = bean.getRequestID().split(",");
				List<Object> value = new ArrayList<Object>();
				for (int i = 0; i < requestid.length; i++) {
					value.add(requestid[i]);
				}
				parameters.put("RequestID", value);
			} else if (request.getParameter("param").equals(
					"letterNumberReport")) {
				parameters.put("status", bean.getStatus());
				parameters.put("letterDate", bean.getLetterDate());
			}

			else if (request.getParameter("param").equals(
					"printITConfigDetails")) { // This is code for
												// IncomeTaxSavingDetails
				parameters.put("sfid", bean.getSfid());
				parameters.put("finyearId", bean.getYearID());

			}// end

			else if (request.getParameter("param").equals(
					"ireportReservationwise")) {
				parameters.put("fromDate", bean.getValue1());
			} else if ("doPartReport".equalsIgnoreCase(request
					.getParameter("type"))) {
				parameters.put("DoPartID", bean.getDoPartId());
			} else if (request.getParameter("param")
					.equals("payFixationReport")) {
				parameters.put("ID", bean.getRequestID());
			} else if (request.getParameter("param").equals("emuReport")) {
				parameters.put("letterNo", bean.getLetterNo());
				parameters.put("requestTypeID", bean.getRequestTypeID());
			} else if (request.getParameter("param").equals("promotionReports")) {
				parameters.put("assessmentCategoryID",
						bean.getAssessmentCategoryID());
				parameters.put("yearID", bean.getYearID());
				parameters.put("assessmentTypeID", bean.getAssessmentTypeID());
				parameters.put("sfID", bean.getSfid());
				List<Object> list = new ArrayList<Object>();
				if (bean.getType() != "residencyPeriodMaster"
						&& !(bean.getType().equals("residencyPeriodMaster"))) {
					if (!(CPSUtils.isNullOrEmpty(bean.getJsonValue()))) {
						JSONObject mainJson = new JSONObject(
								bean.getJsonValue());
						// /JSONObject tempJson =
						// (JSONObject)valJson1.get(String.valueOf(i));

						for (int i = 0; i < mainJson.length(); i++) {
							JSONObject tempJson = (JSONObject) mainJson
									.get("tempJson");
							list.add(tempJson.getInt("designation"));
						}

						parameters.put("designationFrom", list);
					}
				}
				if (bean.getType().equals("aslEligible")
						|| bean.getType().equals("sendToHQ")) {
					parameters.put("assessmentDate", bean.getAssessmentDate());
					if (!CPSUtils.isNullOrEmpty(bean.getAssessmentDate())) {
						bean.getAssessmentDate().toString();
					}
				}
			} else if (request.getParameter("param").equals("quarterReport")
					|| request.getParameter("param").equals("caQuarterReport")
					|| request.getParameter("param").equals(
							"ChangeQuarterReport")
					|| request.getParameter("param").equals("IONQuarterReport")) {
				String[] requestid = bean.getRequestID().split(",");
				List<Object> value = new ArrayList<Object>();
				for (int i = 0; i < requestid.length; i++) {
					value.add(requestid[i]);
				}
				parameters.put("RequestID", value);
			} else if (request.getParameter("param").equals("phVacantReport")) { // phVacantReport
				parameters.put(bean.getParameter1(), bean.getValue1());
				parameters.put(bean.getParameter2(), bean.getValue2());

			} else if (request.getParameter("param").equals(
					"loanConveyenceReport")) {
				parameters.put("ReportNumber", bean.getSendingReportNumber());
				parameters.put("ReportDate", bean.getSendingReportDate());

			} else if (!CPSUtils.isNullOrEmpty(request.getParameter("type"))) {
				if (request.getParameter("type").equals("ltcHistory")) {
					parameters.put("SFID",
							session.getAttribute(CPSConstants.SFID));
				} else if (request.getParameter("type")
						.equals("tadaTdApproval")
						|| request.getParameter("type").equals(
								"tadaTdAmendmentApproval")
						|| request.getParameter("type").equals(
								"tadaTdCancelApproval")) {
					parameters.put("requestID", bean.getRequestID());
				} else if (request.getParameter("type").equals("tadaTdAdvance")
						|| request.getParameter("type").equals(
								"tadaAdvanceFinLevel")
						|| request.getParameter("type").equals(
								"tadaAdvanceCDALevel")
						|| request.getParameter("type").equals(
								"tadaTdAdvanceFin")
						|| request.getParameter("type").equals(
								"tadaTdAdvanceCda")) {
					parameters.put("requestID", bean.getRequestID());
				} else if (request.getParameter("type").equals(
						"tadaTdSettlementRmaDay")
						|| request.getParameter("type").equals(
								"tadaTdSettlementRmaKm")
						|| request.getParameter("type").equals(
								"tadaTdSettlementOldDa")
						|| request.getParameter("type").equals(
								"tadaTdReimRmaDay")
						|| request.getParameter("type").equals(
								"tadaTdReimRmaKm")
						|| request.getParameter("type").equals(
								"tadaTdReimOldDa")) {
					parameters.put("draft", bean.getDraft().toString());
					parameters.put("settlementReqId", bean.getRequestID());

				} else if (request.getParameter("type").equals(
						"tadaTdMRODetails")) {
					parameters.put("requestID", bean.getRequestID());
					parameters.put("mroId", bean.getMroId());
				} else if (request.getParameter("type").equals(
						"tadaTdSettlement")
						|| request.getParameter("type").equals(
								"tadaTdReimbursement")) {
					parameters.put("settlementReqId", bean.getRequestID());
					if (CPSUtils.isNullOrEmpty(bean.getDraft())) {
						parameters.put("draft", bean.getDraft().toString());
					}
				} else if (request.getParameter("type").equals(
						"tdSettTourParticulars")
						|| request.getParameter("type").equals(
								"tdReimTourParticulars")) {
					parameters.put("SettlementReqId", bean.getRequestID());
					parameters.put("draft", bean.getDraft());
					parameters.put("category", bean.getCategory());
				} else if (request.getParameter("type").equals("tadaTdSett")
						|| request.getParameter("type")
								.equals("tadaTdNillSett")
						|| request.getParameter("type").equals("tadaTdReim")) {
					String[] requestid = bean.getRequestID().split(",");
					List<Object> value = new ArrayList<Object>();
					for (int i = 0; i < requestid.length; i++) {
						value.add(requestid[i]);
					}
					parameters.put("RequestID", value);
				} else if (request.getParameter("type")
						.equals("tdAppliedUsers")) {
					parameters.put("searchWith", bean.getSearchWith());
					if (!CPSUtils.isNullOrEmpty(bean.getRequestId())) {
						parameters.put("requestId", bean.getRequestId());
					}
					if (CPSUtils.compareStrings(bean.getRequestType(), "45"))
						parameters.put("requestType", "1");
					else if (CPSUtils.compareStrings(bean.getRequestType(),
							"46"))
						parameters.put("requestType", "2");
					else
						parameters.put("requestType", bean.getRequestType());
					parameters.put("sfid", bean.getSfID());
					parameters.put("directorateId", bean.getDirectorate());
					parameters.put("fromDate", bean.getDepartureDate());
					parameters.put("toDate", bean.getArrivalDate());
					JSONObject mainJson = new JSONObject(bean.getJsonValue());
					JSONObject valJson = (JSONObject) mainJson
							.get("statusData");
					JSONObject valJson1 = (JSONObject) mainJson.get("listData");
					List<Object> value = new ArrayList<Object>();
					List<TadaApprovalRequestDTO> listData = new ArrayList<TadaApprovalRequestDTO>();
					String requestIdList = "";
					String requestTypeList = "";
					String sfidList = "";
					String nameList = "";
					String applyDateList = "";
					String statusList = "";
					String sNo = "";
					for (int i = 0; i < valJson.length(); i++) {
						JSONObject tempJson = (JSONObject) valJson.get(String
								.valueOf(i));
						value.add(tempJson.getInt("statusVal"));
					}
					for (int i = 0; i < valJson1.length(); i++) {
						JSONObject tempJson = (JSONObject) valJson1.get(String
								.valueOf(i));
						/*
						 * TadaApprovalRequestDTO tadaApprovalRequestDTO=new
						 * TadaApprovalRequestDTO();
						 * tadaApprovalRequestDTO.setRequestId
						 * (tempJson.getString("requestId"));
						 * tadaApprovalRequestDTO
						 * .setAuthorizedMove(tempJson.getString
						 * ("requestType"));
						 * tadaApprovalRequestDTO.setSfid(tempJson
						 * .getString("sfid"));
						 * tadaApprovalRequestDTO.setName(tempJson
						 * .getString("name"));
						 * tadaApprovalRequestDTO.setApplyDate
						 * (CPSUtils.convertStringToDate
						 * (tempJson.get("applyDate").toString()));
						 * tadaApprovalRequestDTO
						 * .setType(tempJson.getString("status"));
						 * listData.add(tadaApprovalRequestDTO);
						 */
						requestIdList += tempJson.getString("requestId") + "#";
						requestTypeList += tempJson.getString("requestType")
								+ "#";
						sfidList += tempJson.getString("sfid") + "#";
						nameList += tempJson.getString("name") + "#";
						applyDateList += tempJson.getString("applyDate") + "#";
						statusList += tempJson.getString("status") + "#";
						sNo += String.valueOf(i + 1) + "#";
					}
					// parameters.put("statusList", value);
					parameters.put("requestIdList", requestIdList);
					parameters.put("requestTypeList", requestTypeList);
					parameters.put("sfidList", sfidList);
					parameters.put("nameList", nameList);
					parameters.put("applyDateList", applyDateList);
					parameters.put("statusList", statusList);
					parameters.put("sNo", sNo);
				}
			}

			if (request.getParameter("param").equals("courseReport")) {

				parameters.put("training_inistitute_id",
						request.getParameter("trainingInistituteId"));
				parameters.put("trainingYear",
						request.getParameter("courseYear"));// CPSUtils.critFormattedDate(request.getParameter("courseYear")).getYear()
															// + 1900);
				// parameters.put("trainingMonth",
				// CPSUtils.critFormattedDate(request.getParameter("courseYear")).getMonth()
				// + 1);
			}
			if (request.getParameter("param").equals("circulationReport")) {
				parameters.put("startDate", request.getParameter("startDate"));
				parameters.put("endDate", request.getParameter("endDate"));
			}
			if (request.getParameter("param")
					.equals("trainingNominationReport")) {
				parameters.put("nominationId",
						request.getParameter("nominationId"));

			}
			if (request.getParameter("param").equals(
					"trainingCancelNominationReport")) {
				parameters.put("nominationId",
						request.getParameter("nominationId"));
				parameters.put("requestId", request.getParameter("requestId"));

			}
			if (request.getParameter("param").equals(
					"trainingNominationFormReport")) {
				parameters.put("cirId", request.getParameter("cirId"));
				parameters.put("sfid", request.getParameter("nomineeSfid"));
			}
			if (request.getParameter("param").equals("trainingRegionReport")) {
			} else if (request.getParameter("param").equals("ionReport")) {
				parameters
						.put("durationId", request.getParameter("DurationId"));
			}
			if (request.getParameter("param").equals("yearBookReport")) {
				parameters.put("year", request.getParameter("courseYear"));
				parameters.put("trainingType",
						request.getParameter("trainingTypeId"));
				parameters.put("trainingTypeId",
						request.getParameter("trainingTypeId"));
			} else if (request.getParameter("param").equals("HRDGReport")) {
				parameters.put("yearId", request.getParameter("courseYear"));
				parameters.put("yearVal", request.getParameter("yearVal"));
			} else if (request.getParameter("param").equals("previewIONinPDF")
					|| request.getParameter("param").equals(
							"ionDispatchListinPDF")) {
				parameters.put("ionId", request.getParameter("ionId"));
			} else if (request.getParameter("param").equals(
					"previewIONinPreview")) {
				parameters.put("ionId", request.getParameter("ionId"));
				JSONObject mainJson = new JSONObject(bean.getJsonValue());
				JSONObject subJson = (JSONObject) mainJson.get(String
						.valueOf(0));

				parameters.put("subject", subJson.get("subjectData"));
				parameters.put("reference", subJson.get("referenceData"));
				parameters.put("enclosers", subJson.get("enclosersData"));
				parameters.put("department", subJson.get("department"));
				parameters.put("designation", subJson.get("designation"));
				parameters.put("role", subJson.get("role"));
				parameters.put("sfid", subJson.get("sfid"));
				parameters.put("roleHirarchy", subJson.get("roleHirarchy"));
				parameters.put("content", subJson.get("contentData"));
			} else if (CPSUtils.compareStrings(
					CPSConstants.TUTIONFEEREPORTTYPE, bean.getParam())) {
				parameters.put("requestId", bean.getRequestID());
			} else if (CPSUtils.compareStrings(
					CPSConstants.TUTIONFEECDAREPORTDOCUMENT, bean.getParam())) {
				// parameters.put("billNo", bean.getBillNo());
				// parameters.put("workFlowId", bean.getWorkFlowId());
				parameters.put("claimId", bean.getClaimId());
				String[] requestid = bean.getRequestID().split(",");
				List<Object> value = new ArrayList<Object>();
				for (int i = 0; i < requestid.length; i++) {
					value.add(requestid[i]);
				}
				parameters.put("requestId", value);
			} else if (CPSUtils
					.compareStrings(
							CPSConstants.TUTIONFEECDAREPORTINDIVIDUALSANCTIONEDDOCUMENT,
							bean.getParam())) {
				// parameters.put("billNo", bean.getBillNo());
				// parameters.put("workFlowId", bean.getWorkFlowId());
				parameters.put("claimId", bean.getClaimId());
				String[] requestid = bean.getRequestID().split(",");
				List<Object> value = new ArrayList<Object>();
				for (int i = 0; i < requestid.length; i++) {
					value.add(requestid[i]);
				}
				parameters.put("requestId", value);
			} else if (CPSUtils.compareStrings(
					"printTuitionFeeClaimDetailsReport", bean.getParam())) {
				parameters.put("sfid", bean.getSfid().toUpperCase());
				parameters.put("year", bean.getYear());
			} else if (CPSUtils.compareString(
					CPSConstants.TELEPHONEBILLCLAIMREQUESTREPORT,
					bean.getParam())) {
				parameters.put("requestId", bean.getRequestID());
			} else if (CPSUtils.compareString(
					CPSConstants.PRINTTELEPHONEBILLRELATEDDOCUMENTS,
					bean.getParam())) {
				parameters.put("billNo", bean.getBillNo());
			} else if (CPSUtils.compareString(
					CPSConstants.PRINTTELEPHONEBILLSANCTIONEDINDIVIDUALREPORT,
					bean.getParam())) {
				parameters.put("claimId", bean.getClaimId());
				parameters.put("billNo", bean.getBillNo());
			} else if (CPSUtils.compareStrings(
					"printTelephoneBillClaimDetailsReport", bean.getParam())) {
				parameters.put("sfid", bean.getSfid().toUpperCase());
				parameters.put("year", bean.getYear());
			} else if (CPSUtils.compareStrings("printAnnualIncrArrearsDetails",
					bean.getParam())) {
				parameters.put("adminAccDate", bean.getParameter1());
				parameters.put("financeAccDate", bean.getParameter2());
			} else if (CPSUtils.compareStrings("printDAArrearsDetails",
					bean.getParam())) {
				parameters.put("adminAccDate", bean.getParameter1());
				parameters.put("financeAccDate", bean.getParameter2());
				parameters.put("categoryId", bean.getCategory());
			} else if (CPSUtils.compareStrings(bean.getParam(),
					"retirementReport")) {
				parameters.put("sfid", bean.getUserSfid());
				parameters.put("draftORConfirm",
						request.getParameter("draftORConfirm"));
			} else if (request.getParameter("param").equals("gpfclosing")) {
				// parameters.put("nominationId",
				// request.getParameter("nominationId"));
				// parameters.put("sfid", bean.getSfid().toUpperCase());
				parameters.put("sfid", request.getParameter("sfid"));

			}
			// Ltc Report For Finance after Entering Bill No
			else if (request.getParameter("param").equals("initialCda")) {
				parameters.put("Billno", bean.getBillNo());

			}
			// cghs Reports for Finance after Entering Bill No
			else if (request.getParameter("param").equals("initialCdacghs")) {
				parameters.put("Billno", bean.getBillNo());

			}

			if (CPSUtils.compareStrings(bean.getParam(), "MTReports")) {
				if (CPSUtils.compareString(bean.getType(),
						"dayWiseAllocationReport")) {
					parameters.put("searchDate", bean.getFromDate());
					parameters.put("searchToDate", bean.getToDate());
					parameters.put("vehicleType", bean.getVehicleTypeId());
				} else if (CPSUtils.compareString(bean.getType(),
						"completionReport")) {
					parameters.put("fromDate", bean.getFromDate());
					parameters.put("toDate", bean.getToDate());
					parameters.put("vehicleType", bean.getVehicleTypeId());
				} else if (CPSUtils.compareString(bean.getType(),
						"dailyMileageEntryPdf")) {
					parameters.put("fromDate", bean.getFromDate());
					parameters.put("toDate", bean.getToDate());
					parameters.put("vehicleId", bean.getVehicleId());

				} else if (CPSUtils.compareString(bean.getType(),
						"mtRequestForm")) {
					parameters.put("requestId", bean.getRequestID());
				} else if (CPSUtils.compareString(bean.getType(),
						"yearlyMielageCard")) {
					parameters.put("vehicleId", bean.getVehicleId());
					parameters.put("year", bean.getYear());
				}

				/*
				 * else{ parameters.put("vehicleId", bean.getVehicleId());
				 * parameters.put("year", bean.getYear()); }
				 */

			}
			response.reset();
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Content-Disposition", "inline; filename=\""
					+ jasparPath.getName() + "\"");
			response.setContentType("application/pdf");

			is = session.getServletContext().getResourceAsStream(bean.getJasperFile());
			log.debug("calling Jasper file is:" + bean.getJasperFile());
			System.out.println("calling Jasper file is:" + bean.getJasperFile());
			JasperRunManager.runReportToPdfStream(is, os, parameters,
					dbsession.connection());
			os.flush();
			/*
			 * InputStream sourceFile =
			 * session.getServletContext().getResourceAsStream
			 * (bean.getJasperFile()); JasperReport jr = (JasperReport)
			 * JRLoader.loadObject(sourceFile); JasperPrint jp
			 * =JasperFillManager.fillReport(jr, parameters,
			 * dbsession.connection()); File reportFile = new
			 * File(session.getServletContext
			 * ().getResource("/PdfAndExcelReports").getPath()); if
			 * (!reportFile.exists()) reportFile.mkdir(); OutputStream fileout =
			 * new FileOutputStream(new File(reportFile.getAbsolutePath() + "/"
			 * + bean.getExportedFile())); String filepath1
			 * ="../PdfAndExcelReports/" + bean.getExportedFile();
			 * session.setAttribute("reportpath", filepath1); JRPdfExporter
			 * pdfExporter = new JRPdfExporter();
			 * pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
			 * pdfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
			 * fileout); pdfExporter.exportReport();
			 */

		} catch (Exception e) {
			throw e;
		} finally {
			try{
				os.close();
			}
			catch(Exception e){
				e.printStackTrace();
				log.debug("Exception>>>>>>>>>>>>>>>>>>>" + SetErpException.getStackTraceAsString(e));
				
			}
			try{
				is.close();
			}
			catch(Exception e){
				e.printStackTrace();
				log.debug("Exception>>>>>>>>>>>>>>>>>>>" + SetErpException.getStackTraceAsString(e));
				
			}
			

		}
		return bean;
	}

	public ReportsBean getExcelReport(ReportsBean bean,
			HttpServletRequest request, Session dbsession,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		OutputStream out = null;
		try {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			File jasparPath = new File(session.getServletContext()
					.getResource("/jasper/").getPath());
			parameters.put("SubReportPath", jasparPath.getAbsolutePath()
					+ File.separatorChar);
			if (request.getParameter("param").equals(
					"ireportEmpCategorywiseExcel")
					|| request.getParameter("param").equals(
							"ireportEmpDesignationwiseExcel")
					|| request.getParameter("param").equals(
							"ireportEmpGroupwiseExcel")
					|| request.getParameter("param").equals(
							"ireportReligionwiseExcel")
					|| request.getParameter("param").equals(
							"ireportCommunitywiseExcel")) {
				String[] cat = bean.getValue1().split(",");
				List<Object> value = new ArrayList<Object>();
				for (int i = 0; i < cat.length; i++) {
					value.add(cat[i]);
				}
				parameters.put(bean.getParameter1(), value);
			} else if (request.getParameter("param").equals(
					"ireportEmpDOJwsieExcel")
					|| request.getParameter("param").equals(
							"ireportEmpLastModifiedExcel")) {
				parameters.put(bean.getParameter1(), bean.getValue1());
				parameters.put(bean.getParameter2(), bean.getValue2());
			} else if (request.getParameter("param")
					.equals("empHierarchyExcel")
					|| request.getParameter("param").equals("empMappingExcel")) {
				parameters.put(bean.getParameter1(), bean.getValue1());
			} else if (request.getParameter("param").equals(
					"ireportReservationwiseExcel")) {
				parameters.put("fromDate", bean.getValue1());
			} else if (request.getParameter("type").equals("doPartReport")) {
				parameters.put("DoPartID", bean.getDoPartId());
			} else if (request.getParameter("param").equals("MTReports")) {
				parameters.put("vehicleId", bean.getVehicleId());
				parameters.put("year", bean.getYear());
			} else if (request.getParameter("param").equals(
					"printAllTelephoneBillRelatedDocuments")) {
				parameters.put("billNo", bean.getBillNo());
			} else if (request.getParameter("param").equals(
					"printTelephoneBillSanctionedIndividualReport")) {
				parameters.put("claimId", bean.getClaimId());
				parameters.put("billNo", bean.getBillNo());
			}

			InputStream sourceFile = session.getServletContext()
					.getResourceAsStream(bean.getJasperFile());
			JasperReport jr = (JasperReport) JRLoader.loadObject(sourceFile);
			JasperPrint jp = JasperFillManager.fillReport(jr, parameters,
					dbsession.connection());
			File reportFile = new File(session.getServletContext()
					.getResource("/PdfAndExcelReports").getPath());
			reportFile.mkdirs();
			String filepath1 = "../PdfAndExcelReports/"
					+ bean.getExportedFile();
			session.setAttribute("reportpath", filepath1);
			OutputStream fileout = new FileOutputStream(new File(
					reportFile.getAbsolutePath() + bean.getExportedFile()));
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
					fileout);
			// exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
			// Boolean.TRUE);
			exporterXLS
					.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
							Boolean.FALSE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);
			// exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
			// Boolean.FALSE);
			exporterXLS.setParameter(
					JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.TRUE);
			exporterXLS.exportReport();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Content-Disposition", "inline; filename=\""
					+ bean.getExportedFile() + "\"");
			File f = new File(session.getServletContext()
					.getResource("/PdfAndExcelReports/").getPath()
					+ bean.getExportedFile());
			out = response.getOutputStream();
			if (f.length() > 0) {
				BufferedInputStream in = new BufferedInputStream(
						session.getServletContext()
								.getResourceAsStream(
										"/PdfAndExcelReports/"
												+ bean.getExportedFile()));
				int length;
				byte[] buf = new byte[1024];
				while ((in != null) && ((length = in.read(buf)) != -1)) {
					out.write(buf, 0, length);
				}
			} else
				out.write(0);

		} catch (Exception e) {
			throw e;
		} finally {
			try{
			out.close();
			}
			catch(Exception e){
				e.printStackTrace();
				log.debug("Exception>>>>>>>>>>>>>>>>>>>" + SetErpException.getStackTraceAsString(e));
			}
		}

		return bean;
	}

	@SuppressWarnings("deprecation")
	public ReportsBean getDocumentReport(ReportsBean bean,
			HttpServletRequest request, Session dbsession,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		OutputStream fileout = null;
		OutputStream out = null;
		try {

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			File jasparPath = new File(session.getServletContext()
					.getResource("/jasper/").getPath());
			parameters.put("SubReportPath", jasparPath.getAbsolutePath()
					+ File.separatorChar);
			parameters.put("RequestID", bean.getRequestID());

			if (request.getParameter("param").equals("adminReport")) {
				parameters.put("sfid", bean.getSfid());
				parameters.put("adminNo", bean.getAdminNo());
				parameters.put("adminDt", bean.getAdminDt());
			}

			else if (request.getParameter("param").equals("miscReport")) {
				parameters.put("requestID", bean.getRequestID());
			} else if (request.getParameter("type") != null
					&& request.getParameter("type").equals("doPartReport")) {

				parameters.put("DoPartID", bean.getDoPartId());
			}

			if (request.getParameter("param").equals("promotionIONReports")) {
				if (request.getParameter("type").equals("QualifiedION")) {
					parameters.put("assessmentCategoryID",
							bean.getAssessmentCategoryID());
					parameters.put("yearID", bean.getYearID());
				} else if (request.getParameter("type").equals("eligibleION")) {
					parameters.put("yearID", bean.getYearID());
				} else if (request.getParameter("type").equals("payCCS")) {
					parameters.put("sfID", bean.getSfid());
				}
			} else if (request.getParameter("param").equals("ionReport")) {
				parameters
						.put("durationId", request.getParameter("DurationId"));
			}
			if (request.getParameter("param").equals(
					"trainingNominationToMDBReport")) {
				parameters
						.put("durationId", request.getParameter("durationId"));

			}
			if (request.getParameter("param").equals(
					"trainingNominationsApprovedByMDBReport")) {
				parameters
						.put("durationId", request.getParameter("durationId"));
			}
			if (request.getParameter("param").equals(
					"trainingNominationsToCDAReport")) {
				parameters
						.put("durationId", request.getParameter("durationId"));
			}
			if (request.getParameter("param").equals(
					"trainingNominationsToInistituteReport")) {
				parameters
						.put("durationId", request.getParameter("durationId"));
			}
			if (request.getParameter("param").equals("previewIONinDOC")
					|| request.getParameter("param").equals(
							"ionDispatchListinDOC")) {
				parameters.put("ionId", request.getParameter("ionId"));
			}
			if (CPSUtils.compareString(
					CPSConstants.PRINTTELEPHONEBILLSANCTIONEDINDIVIDUALREPORT,
					bean.getParam())) {
				parameters.put("claimId", bean.getClaimId());
				String[] requestid = bean.getRequestID().split(",");
				List<Object> value = new ArrayList<Object>();
				for (int i = 0; i < requestid.length; i++) {
					value.add(requestid[i]);
				}
				parameters.put("requestId", value);
			}
			if (CPSUtils.compareString(
					CPSConstants.PRINTTELEPHONEBILLRELATEDDOCUMENTS,
					bean.getParam())) {
				String[] requestid = bean.getRequestID().split(",");
				List<Object> value = new ArrayList<Object>();
				for (int i = 0; i < requestid.length; i++) {
					value.add(requestid[i]);
				}
				parameters.put("requestId", value);
			}
			if (CPSUtils.compareStrings(
					CPSConstants.TUTIONFEECDAREPORTDOCUMENT, bean.getParam())) {
				parameters.put("claimId", bean.getClaimId());
				String[] requestid = bean.getRequestID().split(",");
				List<Object> value = new ArrayList<Object>();
				for (int i = 0; i < requestid.length; i++) {
					value.add(requestid[i]);
				}
				parameters.put("requestId", value);
			}
			if (CPSUtils
					.compareStrings(
							CPSConstants.TUTIONFEECDAREPORTINDIVIDUALSANCTIONEDDOCUMENT,
							bean.getParam())) {
				parameters.put("claimId", bean.getClaimId());
				String[] requestid = bean.getRequestID().split(",");
				List<Object> value = new ArrayList<Object>();
				for (int i = 0; i < requestid.length; i++) {
					value.add(requestid[i]);
				}
				parameters.put("requestId", value);
			}
			InputStream sourceFile = session.getServletContext()
					.getResourceAsStream(bean.getJasperFile());
			JasperReport jr = (JasperReport) JRLoader.loadObject(sourceFile);
			JasperPrint jp = JasperFillManager.fillReport(jr, parameters,
					dbsession.connection());
			File reportFile = new File(session.getServletContext()
					.getResource("/PdfAndExcelReports").getPath());
			if (!reportFile.exists())
				reportFile.mkdir();
			fileout = new FileOutputStream(
					new File(reportFile.getAbsolutePath() + "/"
							+ bean.getExportedFile()));
			String filepath1 = "../PdfAndExcelReports/"
					+ bean.getExportedFile();
			session.setAttribute("reportpath", filepath1);
			JRRtfExporter pdfExporter = new JRRtfExporter();
			pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
			pdfExporter
					.setParameter(JRExporterParameter.OUTPUT_STREAM, fileout);
			pdfExporter.exportReport();
			response.reset();
			response.setContentType("application/vnd.ms-word");
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Content-Disposition", "inline; filename=\""
					+ bean.getExportedFile() + "\"");
			File f = new File(reportFile.getAbsolutePath() + "/"
					+ bean.getExportedFile());
			out = response.getOutputStream();
			if (f.length() > 0) {
				BufferedInputStream in = new BufferedInputStream(
						session.getServletContext()
								.getResourceAsStream(
										"/PdfAndExcelReports/"
												+ bean.getExportedFile()));
				int length;
				byte[] buf = new byte[1024];
				while ((in != null) && ((length = in.read(buf)) != -1)) {
					out.write(buf, 0, length);
				}
			} else
				out.write(0);

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				fileout.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.debug("Exception>>>>>>>>>>>>>>>>>>>" + SetErpException.getStackTraceAsString(e));

			}

			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.debug("Exception>>>>>>>>>>>>>>>>>>>" + SetErpException.getStackTraceAsString(e));

			}

		}

		return bean;
	}

	@SuppressWarnings("unchecked")
	public ReportsBean customReportHomeDate(ReportsBean reportsBean)
			throws Exception {
		try {
			reportsBean.setDesignationList(commonDataDAO
					.getMasterData(CPSConstants.DESIGNATIONDTO));
			reportsBean.setReligionList(commonDataDAO
					.getMasterData(CPSConstants.RELIGIONDTO));
			reportsBean.setCategoryList(commonDataDAO.getSubCategoryList());
			reportsBean.setHandicapList(commonDataDAO
					.getMasterData(CPSConstants.PHTYPEDTO));
			reportsBean.setGroupList(commonDataDAO
					.getMasterData(CPSConstants.GROUPDTO));
			reportsBean.setQualificationList(commonDataDAO
					.getMasterData(CPSConstants.QUALIFICATIONDTO));
		} catch (Exception e) {
			throw e;
		}
		return reportsBean;
	}

	public ReportsBean getCustomReport(ReportsBean bean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(true);
		try {
			String rootPath = (String) session.getServletContext()
					.getResource("/PdfAndExcelReports/temp").getPath();
			String xmlFilePath = rootPath + bean.getXmlFile();
			String classpath = (String) session.getServletContext()
					.getResource("/WEB-INF/classes").getPath();
			String pdfFilePath = rootPath + bean.getExportedFile();
			bean.setXSLPath(classpath + "/" + CPSConstants.CUSTOMXSLFILE);
			String sb = getData(bean);
			generateFile(request, sb, xmlFilePath);
			PDFConverter pdfcon = new PDFConverter(xmlFilePath,
					bean.getXSLPath(), pdfFilePath, rootPath);
			log.debug("Exit generatePDF File");
			String path = request.getContextPath()
					+ "/PdfAndExcelReports/temp/" + bean.getExportedFile();
			session.setAttribute("reportpath", path);
		} catch (Exception e) {
			throw e;
		}
		return bean;
	}

	public void generateFile(HttpServletRequest request, String content,
			String fileName) throws Exception {
		log.debug("start ReportsBusiness::generateFile()");
		PrintStream ps=null;
		try {
			FileOutputStream fos;
			fos = new FileOutputStream(fileName);
			ps = new PrintStream(fos);
			ps.print(content);
		} catch (Exception e) {
			throw e;
		}
		finally{
			try{
				ps.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				log.debug("Exception>>>>>>>>>>>>>>>>>>>" + SetErpException.getStackTraceAsString(e));
			}
		}
		log.debug("end ReportsBusiness::generateFile()");

	}

	public String getData(ReportsBean bean) throws Exception {
		log.debug("start ReportsBusiness::getData()");
		StringBuffer sb = null;
		int columnCount = 0;
		try {
			JSONObject json = new JSONObject(bean.getJsonValue());
			List<HashMap<String, String>> data = empSearchDAO
					.getCustomReportEmpDetails(json);
			sb = new StringBuffer();
			sb.append("<REPORT><REPORTNAME>" + bean.getReportName()
					+ "</REPORTNAME><REPORTBODY>");
			if (data.size() > 0) {
				HashMap<String, String> keymap = (HashMap<String, String>) data
						.get(0);
				Iterator<String> it = keymap.keySet().iterator();
				sb.append("<FIELDNAMES><FIELDNAME>Sl.No</FIELDNAME>");
				while (it.hasNext()) {
					sb.append("<FIELDNAME>" + it.next().toString()
							+ "</FIELDNAME>");
					columnCount++;
				}

				generateXSLFile(8.5 + columnCount * 5.5, bean.getXSLPath());
				sb.append("</FIELDNAMES><FIELDVALUES>");
				for (int i = 0; i < data.size(); i++) {
					HashMap<String, String> valuemap = (HashMap<String, String>) data
							.get(i);
					Iterator<String> key = valuemap.keySet().iterator();
					sb.append("<FIELD><FIELDVALUE>" + (i + 1) + "</FIELDVALUE>");
					while (key.hasNext()) {
						String value = key.next().toString();
						if (!CPSUtils.isNull(valuemap.get(value))) {
							String x = String.valueOf(valuemap.get(value));
							sb.append("<FIELDVALUE>"
									+ CPSUtils.getEncodedHTML(x)
									+ "</FIELDVALUE>");
						} else
							sb.append("<FIELDVALUE>-</FIELDVALUE>");
					}
					sb.append("</FIELD>");
				}
				sb.append("</FIELDVALUES>");
			} else {
				sb.append("<FIELDNAMES><FIELDVALUES><FIELDVALUE>No Data Found</FIELDVALUE></FIELDVALUES></FIELDNAMES>");
			}

			sb.append("</REPORTBODY></REPORT>");
		} catch (Exception e) {
			throw e;
		}
		return sb.toString();
	}

	/**
	 * This method generates XSL file with dynamic table width at specified path
	 * 
	 * @param columnWidth
	 * @param path
	 * @throws Exception
	 */
	public void generateXSLFile(double columnWidth, String path)
			throws Exception {
		Writer output = null;
		try {
			String XSLFileContent = "<?xml version='1.0' encoding='UTF-8'?><xsl:stylesheet version='1.0'	xmlns:xsl='http://www.w3.org/1999/XSL/Transform' xmlns:fo='http://www.w3.org/1999/XSL/Format'>	<xsl:template match='REPORT'>		<fo:root xmlns:fo='http://www.w3.org/1999/XSL/Format'>			<fo:layout-master-set>				<fo:simple-page-master page-height='29.7cm'		page-width='"
					+ columnWidth
					+ "cm' margin-top='0.2cm' margin-bottom='0.5cm'	margin-left='1.54cm' margin-right='1.54cm' master-name='first'>		<fo:region-body margin-top='1.0cm' margin-bottom='1.0cm' />	<fo:region-before extent='0.1cm' />	<fo:region-after extent='0.1cm' />	</fo:simple-page-master></fo:layout-master-set>	<fo:page-sequence master-reference='first'>	<fo:flow flow-name='xsl-region-body'><xsl:apply-templates /></fo:flow>			</fo:page-sequence>		</fo:root>	</xsl:template>	<!-- Report name Label format start -->	<xsl:template match='REPORTNAME'>		<fo:block font-size='18pt' font-family='Times Roman'			space-after.optimum='10pt' font-weight='bold' background-color='white'			color='black' text-align='center'>			<xsl:value-of select='.' />		</fo:block>	</xsl:template>	<!-- Report name Label format end -->	<xsl:template match='REPORTBODY'>		<fo:block font-size='10pt' font-family='Times Roman'			space-after.optimum='10pt' text-align='start'>			<fo:table>				<xsl:for-each select='/REPORT/REPORTBODY/FIELDNAMES/FIELDNAME'>					<fo:table-column column-width='55mm' />				</xsl:for-each>				<fo:table-header>					<fo:table-row>						<xsl:for-each select='/REPORT/REPORTBODY/FIELDNAMES/FIELDNAME'>							<fo:table-cell text-align='center' line-height='15pt'								padding='2pt' border='.1mm  solid black'>								<fo:block font-size='10pt' font-weight='bold'>									<xsl:value-of select='.' /><!-- Print the column heading names -->								</fo:block>							</fo:table-cell>						</xsl:for-each>					</fo:table-row>				</fo:table-header>				<fo:table-body >					<xsl:for-each select='/REPORT/REPORTBODY/FIELDVALUES/FIELD'>						<fo:table-row>							<xsl:for-each select='./FIELDVALUE'>								<fo:table-cell line-height='15pt' padding='2pt'									text-align='center' border='.1mm  solid black'>									<fo:block>										<xsl:value-of select='.' /><!-- Print the column values -->									</fo:block>								</fo:table-cell>							</xsl:for-each>						</fo:table-row>					</xsl:for-each>				</fo:table-body>			</fo:table>		</fo:block>	</xsl:template></xsl:stylesheet>";
			// String XSLFileContent =
			// "<?xml version='1.0' encoding='UTF-8'?><xsl:stylesheet version='1.0'	xmlns:xsl='http://www.w3.org/1999/XSL/Transform' xmlns:fo='http://www.w3.org/1999/XSL/Format'>	<xsl:template match='REPORT'>		<fo:root xmlns:fo='http://www.w3.org/1999/XSL/Format'>			<fo:layout-master-set>				<fo:simple-page-master page-height='29.7cm'					page-width='"+columnWidth+"cm' margin-top='0.2cm' margin-bottom='0.5cm'					margin-left='1.54cm' margin-right='1.54cm' master-name='first'>					<fo:region-body margin-top='1.0cm' margin-bottom='1.0cm' />					<fo:region-before extent='0.1cm' />					<fo:region-after extent='0.1cm' />				</fo:simple-page-master>			</fo:layout-master-set>			<fo:page-sequence master-reference='first'>				<fo:static-content flow-name='xsl-region-before'>					<fo:block text-align='end' font-size='9pt' font-family='Times Roman'						line-height='1pt'>						Custom Report						<fo:page-number />					</fo:block>				</fo:static-content>				<fo:static-content flow-name='xsl-region-after'>					<fo:block text-align='end' font-size='7pt' font-family='Times Roman'						line-height='10pt'>						<xsl:value-of select='/root/agreement/license_identifier' />					</fo:block>				</fo:static-content>				<fo:flow flow-name='xsl-region-body'>					<fo:table space-after.optimum='14pt'>            	<!--  <fo:table-column column-width='1cm'/>-->						<fo:table-column column-width='15cm' />						<fo:table-body font-size='10pt' font-family='Times Roman'>							<xsl:for-each select='/REPORT'>								<fo:table-row>									<fo:table-cell padding='2pt' border='.1mm  solid white'>										<fo:block text-align='center'></fo:block>									</fo:table-cell>								</fo:table-row>							</xsl:for-each>						</fo:table-body>					</fo:table>					<xsl:apply-templates />				</fo:flow>			</fo:page-sequence>		</fo:root>	</xsl:template>	<xsl:template match='page-break'>		<fo:block font-size='10pt' font-weight='bold'			space-before.minimum='1em' space-before.optimum='1.5em'			space-before.maximum='2em' break-after='page'></fo:block>	</xsl:template>	<xsl:template match='REPORTNAME'>		<fo:block font-size='18pt' font-family='Times Roman'			space-after.optimum='10pt' font-weight='bold' background-color='white' color='black'			text-align='center'>			<xsl:value-of select='.' />		</fo:block>	</xsl:template>	<xsl:template match='REPORTBODY'>		<fo:block font-size='10pt' font-family='Times Roman'			space-after.optimum='10pt' text-align='start'>			<fo:table>				<xsl:for-each select='/REPORT/REPORTBODY/FIELDNAMES/FIELDNAME'>					<fo:table-column column-width='55mm' />				</xsl:for-each>				<fo:table-header>					<fo:table-row>						<xsl:for-each select='/REPORT/REPORTBODY/FIELDNAMES/FIELDNAME'>							<fo:table-cell text-align='center' line-height='15pt'								padding='2pt' border='.1mm  solid black'>								<fo:block font-size='10pt' font-weight='bold'>									<xsl:value-of select='.' />								</fo:block>							</fo:table-cell>						</xsl:for-each>					</fo:table-row>				</fo:table-header>				<fo:table-body>					<xsl:for-each select='/REPORT/REPORTBODY/FIELDVALUES/FIELD'>						<fo:table-row>							<xsl:for-each select='./FIELDVALUE'>								<fo:table-cell line-height='15pt' padding='2pt'									text-align='center' border='.1mm  solid black'>									<fo:block>										<xsl:value-of select='.' />									</fo:block>								</fo:table-cell>							</xsl:for-each>						</fo:table-row>					</xsl:for-each>				</fo:table-body>			</fo:table>		</fo:block>	</xsl:template></xsl:stylesheet>";
			File file = new File(path);
			output = new BufferedWriter(new FileWriter(file));
			output.write(XSLFileContent);
			System.out.println("Your file has been written");
		} catch (Exception e) {
			throw e;
		} finally {
			try{
			output.close();
			}
			catch(Exception e){
				e.printStackTrace();
				log.debug("Exception>>>>>>>>>>>>>>>>>>>" + SetErpException.getStackTraceAsString(e));
			}
		}
	}

	public ReportsBean getPisMiscReportHome(ReportsBean reportsBean,
			HttpServletRequest request) throws Exception { // This is new Method
															// for
															// pisMiscReports
		HttpSession session = null;
		try {
			log.debug("::ReportsBusiness --> ");
			session = request.getSession(true);
			log.debug("::ReportsBusiness --> onSubmit --> param=categoryreport");
			// if
			// (CPSUtils.isNull(session.getAttribute("categoryReportHomeList")))
			// {

			reportsBean.setList(commonDataDAO.getCategoryList());

			// reportsBean.setList(commonDataDAO.getSubCategoryList());

			session.setAttribute("categoryReportHomeList",
					reportsBean.getList());
			// }
			// reportsBean.setList((List)session.getAttribute("categoryReportHomeList"));
			String id = "";
			for (int i = 0; i < reportsBean.getList().size(); i++) {
				id += ((CategoryDTO) reportsBean.getList().get(i)).getId()
						+ ",";
			}

		} catch (Exception e) {
			throw e;
		}
		return reportsBean;
	}

}
