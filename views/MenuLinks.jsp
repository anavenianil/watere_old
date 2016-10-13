<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%-- Begin : MenuLinks.jsp --%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.lang.System"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-fn" prefix="fn"%>
<link rel="stylesheet" type="text/css"
	href="styles/assets/css/webrupee.css" />
<script type="text/javascript">
	var siteURL1 = window.location;

	$jq(document).ready(function() {
		var ser = $jq('#sample').text();
		var client = new Date().getTime();
		var dif = client - ser;
		function updateTime() {
			var temp = new Date().getTime() - dif;
			var now = new Date(temp);
			$jq('#time').text(now.toLocaleString());

		}
		updateTime();
		setInterval(updateTime, 1000);
	});
	var site = new Array();
	site = siteURL1.toString().split("/");
	for (var i = 0; i < site.length - 2; i++) {
		if (i < 3)
			pathName += site[i] + "/";
	}
	jqueryslidemenu.init({
		mainmenuid : "slidemenu", //menu DIV id
		orientation : 'h', //Horizontal or vertical menu: Set to "h" or "v"
		classname : 'jqueryslidemenu', //class added to menu's outer DIV
		//customtheme: ["#1c5a80", "#18374a"],
		contentsource : "markup" //"markup" or ["container_id", "path_to_menu_file"]
	})
</script>

<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>

<div id="slidemenu" class="jqueryslidemenu">
	<ul>
		<li id="currentact"><a href="javascript:pisHome();">Dash
				Board</a></li>
				
	<!-- 	 <li><a href="javascript:daOnTour();">DA On Tour Master</a></li> 	 -->

	<!-- 	<li><a href="javascript:noticeHome();">ION-IN</a></li> -->

		<c:if test="${menuLinks.adminKey=='1'}">
			<li><a class="im_jqueryslidemenu" href="javascript:adminHome()"><img
					src="./images/ebbtcbindex13_2.png" border=0px /></a>
				<ul>
<%-- 					<c:if test="${menuLinks.doPartSearch=='1'}">
						<li><a href="#">DO Part II</a>
							<ul>
								<li><c:if test="${menuLinks.doPart=='1'}">
										<a href="#">Masters</a>
										<ul>
											<c:if test="${menuLinks.typeMaster=='1'}">
												<li><a href="javascript:typeHome();">DO Part Type
														Master</a></li>
											</c:if>
											<c:if test="${menuLinks.casualitiesMaster=='1'}">
												<li><a href="javascript:casualitiesHome();">Do Part
														Casualities Master</a></li>
											</c:if>
											<c:if test="${menuLinks.taskHolder=='1'}">
												<li><a href="javascript:taskHolderHome();">Do Part
														Task Holder</a></li>
											</c:if>
											<c:if test="${menuLinks.typeDesigMaster=='1'}">
												<li><a href="javascript:typeDesignationMasterHome();">Do
														Part Type Designation Master</a></li>
											</c:if>
											<c:if test="${menuLinks.assignDesigToTaskHolder=='1'}">
												<li><a href="javascript:doPartDetailsHome();">Assign
														Designation to TaskHolder</a></li>
											</c:if>
										</ul>
									</c:if></li>
								<c:if test="${menuLinks.doPartMaster=='1'}">
									<li><a href="javascript:doPartDetails()">Create/Freeze</a></li>
								</c:if>

								<li><a href="#">Publish </a>    
									<ul>
										<c:if test="${menuLinks.completeLeave=='1'}">
											<li><a href="javascript:dopartHome();">Leave</a></li>
										</c:if>
										<c:if test="${menuLinks.payFixation=='1'}">
											<li><a href="#">Pay Fixation</a>
												<ul>
													<c:if test="${menuLinks.payFixationDOPart=='1'}">
														<li><a href="javascript:payFixation();">Pay
																Fixation</a></li>
													</c:if>

													<c:if test="${menuLinks.payFixationDOPart=='1'}">
														<li><a href="javascript:payFixationDOPart();">Search</a></li>
													</c:if>
												</ul></li>
										</c:if>
										<c:if test="${menuLinks.annualIncrementDopart=='1'}">
											<li><a href="javascript:annualIncrement();">Annual
													Increment</a> <!--	<ul>
																	<c:if test="${menuLinks.annualIncrementDOReport=='1'}">
																		<li><a href="javascript:annualIncrementDoReport();">Report</a></li>
																	</c:if>
																</ul>   --></li>
											<li><a href="javascript:annualIncrementFinalDetails();">Annual
													Increment - Update Basic Pay</a></li>
										</c:if>

										<li><a href="javascript:retirementDopartHome();">Retirement</a></li>
									</ul></li>

								<c:if test="${menuLinks.releaseDoPart=='1'}">
									<li><a href="javascript:doPartSearchHome('r');">Release</a></li>
								</c:if>

								<c:if test="${menuLinks.AcceptDoPart=='1'}">
									<li><a href="javascript:doPartSearchHome('a');">Accept</a></li>
								</c:if>

								<c:if test="${menuLinks.DoPartiiDetails=='1'}">
									<li><a href="javascript:doPartSearchHome('all');">Accepted</a></li>
									<li><a href="javascript:doPartSearchHome();">Search</a></li>
									<li><a href="javascript:doPartModules();">Archives</a></li>
								</c:if>

							</ul></li>
					</c:if> --%>
<%-- 					<c:if test="${menuLinks.MasterData=='1'}">
						<li><a href="#">PIS</a>

							<ul>
								<li><a href="#">PIS Masters</a>
									<ul>
										<c:if test="${menuLinks.appointment=='1'}">
											<li><a href="javascript:masterData('appointment')">Appointment
													Type</a></li>
										</c:if>
										<c:if test="${menuLinks.category=='1'}">
											<li><a href="javascript:masterData('category')">Category</a></li>
										</c:if>
										<c:if test="${menuLinks.community=='1'}">
											<li><a href="javascript:masterData('community')">Community</a></li>
										</c:if>
										<c:if test="${menuLinks.DepartmentTypeMaster=='1'}">
											<li><a href="javascript:masterData('departmentType')">Department
													Type Master</a></li>
										</c:if>
										<c:if test="${menuLinks.designation=='1'}">
											<li><a href="javascript:desigMaster()">Designation</a></li>
										</c:if>
										<c:if test="${menuLinks.degree=='1'}">
											<li><a href="javascript:masterData('degree')">Degree</a></li>
										</c:if>
										<c:if test="${menuLinks.discipline=='1'}">
											<li><a href="javascript:disciplineMaster('discipline')">Discipline</a></li>
										</c:if>
										<c:if test="${menuLinks.district=='1'}">
											<li><a href="javascript:masterData('district')">District</a></li>
										</c:if>
										<c:if test="${menuLinks.employment=='1'}">
											<li><a href="javascript:masterData('employment')">Employment
													Type</a></li>
										</c:if>
										<c:if test="${menuLinks.relation=='1'}">
											<li><a href="javascript:masterData('relation')">Family
													Relations</a></li>
										</c:if>
										<c:if test="${menuLinks.group=='1'}">
											<li><a href="javascript:masterData('group')">Group</a></li>
										</c:if>
										<c:if test="${menuLinks.motherTongue=='1'}">
											<li><a href="javascript:masterData('motherTongue')">Mother
													Tongue Master</a></li>
										</c:if>
										<c:if test="${menuLinks.nominee=='1'}">
											<li><a href="javascript:masterData('nominee')">Nominee
													Type Master</a></li>
										</c:if>
										<c:if test="${menuLinks.qualification=='1'}">
											<li><a
												href="javascript:qualificationMaster('qualification')">Qualification</a></li>
										</c:if>
										<c:if test="${menuLinks.quarterType=='1'}">
											<li><a href="javascript:masterData('quarterType')">Quarter
													Type</a></li>
										</c:if>
										<c:if test="${menuLinks.religion=='1'}">
											<li><a href="javascript:religionMaster('religion')">Religion</a></li>
										</c:if>
										<c:if test="${menuLinks.retirementType=='1'}">
											<li><a href="javascript:masterData('retirementType')">Retirement
													Type</a></li>
										</c:if>
										<c:if test="${menuLinks.reservation=='1'}">
											<li><a href="javascript:masterData('reservation')">Reservation
													Master</a></li>
										</c:if>
										<c:if test="${menuLinks.state=='1'}">
											<li><a href="javascript:masterData('state')">State</a></li>
										</c:if>
										<c:if test="${menuLinks.subcategory=='1'}">
											<li><a href="javascript:masterData('subcategory')">Sub
													Category</a></li>
										</c:if>
										<c:if test="${menuLinks.subQuarters=='1'}">
											<li><a href="javascript:masterData('subQuarter')">Sub
													Quarter Type</a></li>
										</c:if>
										<c:if test="${menuLinks.year=='1'}">
											<li><a href="javascript:masterData('year')">Year
													Master</a></li>
										</c:if>
										<c:if test="${menuLinks.city=='1'}">
											<li><a href="javascript:masterData('city')">City
													Master</a></li>
										</c:if>
									</ul>
									<li><a href="#">Certificates</a>
										<ul>
											<li><a href="javascript:nocPassportHome();">NOC For
													PASSPORT</a></li>
											<c:if test="${menuLinks.residentialCertificate=='1'}">
												<li><a href="javascript:adminMiscHome('residential');">Residential
														Certificate</a></li>
											</c:if>
											<c:if test="${menuLinks.serviceCertificate=='1'}">
												<li><a href="javascript:adminMiscHome('service');">Service
														Certificate</a></li>
											</c:if>
										</ul></li> <!--Total Pis Reports configure in single jsp:prasad  -->
									<li><a href="#">Reports</a>
										<ul>
											<li><a href="javascript:pisReportsHome()">Regular
													Reports</a></li>
											<!--Total Pis Misc Reports configure in single jsp:prasad  -->
											<!--Total Pis Misc Reports configure in single jsp:prasad  -->
											<li><a href="javascript:pisMiscReportsHome()">Miscellaneous
													Reports</a></li>


											<!--Total Pis Misc Reports configure in single jsp:prasad  -->
											<li><a href="javascript:statusReportsHome()">Status
													Reports</a></li>
											<c:if test="${menuLinks.cpoolDetails=='1'}">
												<li><a href="javascript:CpoolDetails()">Cpool
														Details</a></li>
											</c:if>
											<c:if test="${menuLinks.reportDetails=='1'}">
												<li><a href="javascript:ReportDetails()">Report
														Details</a></li>
											</c:if>
											<c:if test="${menuLinks.areaofDeployment=='1'}">
												<li><a href="javascript:areaofDeployment()">Area of
														Deployment Report</a></li>
											</c:if>
											<c:if test="${menuLinks.customReport=='1'}">
												<li><a href="javascript:createCustomReport()">Custom
														Report</a></li>
											</c:if>
											<li><a href="javascript:orgHierarchyReports()">Organization
													Hierarchy Reports</a></li>


										</ul></li>
							</ul></li>
					</c:if> --%>

					<c:if test="${menuLinks.leaveAdmin=='1'}">
					<li><a href="javascript:desigMaster()">Designation</a></li>
						<li><a href="#">Leave</a>
							<ul>

								<!--this link added by bkr 07/06/2016  -->
								<li><a href="javascript:LeaveDaysEntry();">Leave
										Details Entry</a></li>
								<!--this link added by bkr 14/06/2016  -->
								<li><a href="javascript:dataErpEntryHome();">Leave
										Balance Entry</a></li>
											<li><a href="javascript:holidayHome();"><span>
												Holidays Master</span></a></li>
<!-- <li><a href="javascript:getEmpBGPaymentDetails();">Basic
											Pay And Grade Pay</a></li> -->

<%-- 
								<c:if test="${menuLinks.othersApplyLeave=='1'}">
									<li><a href="javascript:offlineLeave();">Offline Leave
											Application Entry</a></li>
								</c:if>
								<c:if test="${menuLinks.createHolidays=='1'}">
									<li><a href="javascript:holidayHome();"><span>Manage
												Holidays Master</span></a></li>
								</c:if>
								<c:if test="${menuLinks.leavesMapping=='1'}">
									<li><a href="javascript:leaveRelationMappingHome();"><span>Configure
												Leave Relation Mappings</span></a></li>
								</c:if>
								<c:if test="${menuLinks.leaveManagement=='1'}">
									<li><a href="javascript:leaveManagementHome();"><span>Configure
												Leave Types</span></a></li>
								</c:if>
								<c:if test="${menuLinks.empLeaveAccount=='1'}">
									<li><a href="javascript:empLeaveAccount();"><span>Leave
												Card/Account</span></a></li>
								</c:if>
								<c:if test="${menuLinks.leaveTxnSearch=='1'}">
									<li><a href="javascript:leaveTxnSearch();"><span>Leave
												Txn Search</span></a></li>
								</c:if>
								<c:if test="${menuLinks.leaveExceptionalEmployee=='1'}">
									<li><a href="javascript:leaveExceptionalEmployee();"><span>Leave
												Exceptional Employee</span></a></li>
								</c:if>
								<c:if test="${menuLinks.leaveAdit=='1'}">
									<li><a href="javascript:leaveAdit();"><span>Leave
												Audit</span></a></li>
								</c:if>
								<c:if test="${menuLinks.leaveScript=='1'}">
									<li><a href="javascript:leaveScript();"><span>Leave
												Script</span></a></li>
								</c:if>
								<c:if test="${menuLinks.leaveBusinessRules=='1'}">
									<li><a href="javascript:leaveBusinessRules()"><span>Leave
												Business Rules</span></a></li>
								</c:if>
								<c:if test="${menuLinks.availableLeavesReport=='1'}">
									<li><a href="javascript:availableLeavesReport();"><span>Available
												Leaves Report</span></a></li>
								</c:if> --%>
							</ul></li>
							
					</c:if>
<%-- 					<c:if test="${menuLinks.adminCghs=='1'}">
						<li><a href="#">CGHS</a>
							<ul>
								<c:if test="${menuLinks.dislocation=='1'}">
									<li><a href="javascript:masterData('dislocation')">Dispensary
											Location</a></li>
								</c:if>
								<c:if test="${menuLinks.disnumber=='1'}">
									<li><a href="javascript:masterData('disnumber')">Dispensary
											Location Mapping</a></li>
								</c:if>
								<c:if test="${menuLinks.hospitalDetails=='1'}">
									<li><a href="javascript:referralHospitalHome()">Referral
											Hospital</a></li>
								</c:if>
								<c:if test="${menuLinks.wardType=='1'}">
									<li><a href="javascript:wardTypeHome()">Ward Type
											Master</a></li>
								</c:if>

								<c:if test="${menuLinks.adminOfflineCghs=='1'}">
									<li><a href="javascript:treatmentRequestHome('offline')">Offline
											CGHS Request Form</a></li>
								</c:if>
								<c:if test="${menuLinks.cghsFinance=='1'}">
									<li><a href="javascript:cghsFinanceHome()">Medical
											Settlement</a></li>
								</c:if>
								<c:if test="${menuLinks.awardCategory=='1'}">
									<li><a href="javascript:masterData('awardCategory')">Award
											Category</a></li>
								</c:if>
							</ul></li>
					</c:if> --%>
				<%-- 	<c:if test="${menuLinks.adminLTC == '1'}">
						<li><a href="#">LTC</a>
							<ul>

								<c:if test="${menuLinks.ltcTypeMaster == '1'}">
									<li><a href="javascript:ltcHome('ltcType')">LTC Type</a></li>
								</c:if>
								<c:if test="${menuLinks.ltcBlockMaster == '1'}">
									<li><a href="javascript:ltcHome('ltcBlock')">LTC Block</a></li>
								</c:if>
								<c:if test="${menuLinks.ltcBlockYearMaster == '1'}">
									<li><a href="javascript:ltcHome('ltcBlockYear')">LTC
											Block Year</a></li>
								</c:if>
								<c:if test="${menuLinks.ltcPenalInterestMaster == '1'}">
									<li><a href="javascript:ltcHome('ltcPenalInterestMaster')">LTC
											Penal Interest Master</a></li>
								</c:if>
								<c:if test="${menuLinks.ltcFinanceScreen == '1'}">
									<li><a href="javascript:FinanceHome();">Tour
											Settlements</a></li>
								</c:if>
								<c:if test="${menuLinks.ltcExperienceReport=='1'}">
									<li><a href="javascript:ltcExperienceReport();">LTC
											Employee Experience Status</a></li>
								</c:if>
							</ul></li>
					</c:if> --%>

<%-- 
					<c:if test="${menuLinks.adminLoan=='1'}">
						<li><a href="#">Loan</a>
							<ul>
								<c:if test="${menuLinks.loanMaster=='1'}">
									<li><a href="#">Loan Master</a>
										<ul>
											<li><a href="javascript:loanTypeMaster()">Loan Type
													Master</a></li>
											<li><a href="javascript:loanFestivalMaster()">Loan
													Festival Master</a></li>
											<c:if test="${menuLinks.loanTypeDetails=='1'}">
												<li><a href="javascript:loanTypeDetails()">Loan
														Type Details</a></li>
											</c:if>
											<c:if test="${menuLinks.loanAmountDetails=='1'}">
												<li><a href="javascript:loanAmountDetails()">Loan
														Amount Details</a></li>
											</c:if>
											<li><a href="javascript:hbaInterestRates()">HBA
													Interest Rate Master</a></li>
											<li><a href="javascript:gpfRulesMaster()">GPF Rules
													Master</a></li>
											<li><a href="javascript:gpfClosingBalance()">GPF
													Closing Balance Master</a></li>
										</ul></li>
								</c:if>
								<c:if test="${menuLinks.offline=='1'}">
									<li><a href="#">1)Offline Entry</a>
										<ul>
											<c:if test="${menuLinks.othersApplyLoan=='1'}">
												<li><a href="javascript:offlineLoan();">Offline
														Loan Application Entry</a></li>
											</c:if>
											<c:if test="${menuLinks.othersApplyLoanHBA=='1'}">
												<li><a href="javascript:offlineLoanHBA()">Offline
														HBA Application Entry</a></li>
											</c:if>
											<c:if test="${menuLinks.loanImmediateRelief=='1'}">
												<li><a href="javascript:loanRelief()">Loan
														Immediate Relief</a></li>
											</c:if>
										</ul></li>
								</c:if>
								<c:if test="${menuLinks.hqSendingDetails=='1'}">
									<li><a href="javascript:sendingReport()">2)
											HeadQuarter Sending Details</a></li>
								</c:if>
								<c:if test="${menuLinks.hqReports=='1'}">
									<li><a href="javascript:conveyanceAdvanceDemand()">3)
											HeadQuarter Sending Report</a></li>
								</c:if>
								<c:if test="${menuLinks.hqReceivedDetails=='1'}">
									<li><a href="javascript:hqReport()">4) HeadQuarter
											Received Details</a></li>
								</c:if>
								<c:if test="${menuLinks.sanctionAndContingent=='1'}">
									<li><a href="javascript:sanctionAndContingent()">5)
											Sanction Report & ContingentBill</a></li>
								</c:if>
								<c:if test="${menuLinks.payAcquittanceReports=='1'}">
									<li><a href="javascript:loanPaybillAcquittance()">Paybill
											& Acquittance Report</a></li>
								</c:if>

							</ul></li>
					</c:if> --%>

				<%-- 	<c:if test="${menuLinks.promotion=='1'}">
						<li><a href="#">Promotion</a>
							<ul>
								<c:if test="${menuLinks.promotionsMaster=='1'}">
									<li><a href="#">Promotions Masters</a>
										<ul>
											<li><a href="javascript:residencyPeriod();">Residency
													Period Master</a></li>
											<li><a href="javascript:boardTypeInfo();">Board
													Master</a></li>
											<li><a href="javascript:localBoard();">Local
													Assessment Board</a></li>
											<li><a href="javascript:venueDetails();">Venue
													Details</a></li>
											<li><a href="javascript:promotionDiscipline();">Discipline
													Master</a></li>
											<li><a href="javascript:promotionSubDiscipline();">Sub
													Discipline Master</a></li>
											<li><a href="javascript:addEmpAssessment();">Add
													Employee for Assessment</a></li>
										</ul></li>
								</c:if>
								<c:if test="${menuLinks.qualifiedCandidates=='1'}">
									<li><a href="javascript:headQuarterSending();">1&2)
											Eligible Candidates</a></li>
								</c:if>
								<c:if test="${menuLinks.qualifiedCandidatesDOI=='1'}">
									<li><a href="javascript:qualifiedCandidates();">3)
											Eligible Candidates With DOI</a></li>
								</c:if>
								<c:if test="${menuLinks.promotedCandidates=='1'}">
									<li><a href="javascript:promotedCandidates();">4)
											Promoted Candidates</a></li>
								</c:if>

								<c:if test="${menuLinks.optionalCertificate=='1'}">
									<li><a href="javascript:optionalCertificateHome();">5)
											Option Certificate & Joining Report</a></li>
								</c:if>
								<c:if test="${menuLinks.viewOptionalCertificate=='1'}">
									<li><a href="javascript:viewOptionalCertificateHome();">6)
											View Option Certificates & Update Designation</a></li>
								</c:if>
								<c:if test="${menuLinks.payUpdate=='1'}">
									<li><a href="javascript:PromotionPayUpdate();">7)
											Promotion Pay Update</a></li>
								</c:if>
							</ul></li>
					</c:if> --%>
<%-- 
					<c:if test="${menuLinks.remaining=='1'}">
						<li><a href="#">Quarter</a>
							<ul>
								<c:if test="${menuLinks.emuRequestFormDetails=='1'}">
									<li><a href="javascript:emuRequestFormDetails();">Emu
											Request Form</a></li>
								</c:if>
								<c:if test="${menuLinks.emuQuarterDetails=='1'}">
									<li><a href="javascript:emuQuarterDetails('alloted');">Quarter
											Alloted Details </a></li>
								</c:if>
								<c:if test="${menuLinks.emuOccupiedQuarterDetails=='1'}">
									<li><a href="javascript:emuQuarterDetails('occupied');">Quarter
											Occupied Details</a></li>
								</c:if>
								<c:if test="${menuLinks.emuVacatedQuarterDetails=='1'}">
									<li><a href="javascript:emuQuarterDetails('vacated');">Quarter
											Vacation Details</a></li>
								</c:if>
								<c:if test="${menuLinks.quarterGradePayMapping=='1'}">
									<li><a href="javascript:quarterGradePay();">Quarter
											GradePay Mapping</a></li>
								</c:if>

								<c:if test="${menuLinks.quarterOfflineEntry=='1'}">
									<li><a href="javascript:quarterOfflineEntry();">Quarter
											Offline Entry</a></li>
								</c:if>
								<c:if test="${menuLinks.emuQuarterVacationDetails=='1'}">
									<li><a
										href="javascript:emuQuarterDetails('vacationCmpl');">Quarter
											Vacation reports</a></li>
								</c:if>
							</ul></li>
					</c:if> --%>

				<%-- 	<c:if test="${menuLinks.hindi=='1'}">
						<li><a href='#'>Hindi</a>
							<ul>
								<c:if test="${menuLinks.hindiMaster=='1'}">

									<!--<a href='#'>Hindi Masters</a>
								       <ul>-->
									<li><a href="javascript:hindiExam();">Exam Master</a></li>
									<li><a href="javascript:hindiExamConfig();">Exam
											Configuration Master</a></li>
									<li><a href="javascript:hindiEmployee();">Employee
											Master</a></li>
									<li><a href="javascript:hindiCashAward();">Cash Award
											Master</a></li>
									<li><a href="javascript:hindiExamNomination();">Exam
											Nomination Master</a></li>
									<li><a href="javascript:hindiEmpResult();">Employee
											Result Master</a></li>

									<!--  <a href="javascript:hindiExamEligible();">Eligible Employees For Exam</a>

								       <a href="javascript:hindiExamIncentive();">Incentive Master</a>
									     </ul>-->

								</c:if>

							</ul></li>
					</c:if> --%>
				<%-- 	<c:if test="${menuLinks.Reports=='1'}">
						<li><a href="">Retirement Reports</a>
							<ul>
								<li><a href="javascript:Retirement('report')">Retirement
										Report</a>
							</ul></li>

					</c:if> --%>


					<%-- <%-- <c:if test="${menuLinks.adminPassportRequest=='1'}">
							  	<li>
							 			<a href="#">PassPort</a>
							    		<ul>
							    		<!--<c:if test="${menuLinks.passportApplication=='1'}">
												<li><a href="javascript:passportHome();">Passport And Proceeding for Abroad</a></li>
										</c:if>-->
										
										<c:if test="${menuLinks.adminNocForPassport=='1'}">
												<li><a href="javascript:nocPassportHome();">NOC For PASSPORT</a></li>
										</c:if>
										
										<!--<c:if test="${menuLinks.nocForPassport=='1'}">
												<li><a href="javascript:movablePropertyHome();">Movable Property/Immovable Property</a></li>
										</c:if>	-->
										</ul>
								</li>		
	  					</c:if>	  --%>

					<c:if test="${menuLinks.tadaMaster=='1'}">
						<li><a href="#">TA Admin</a>
							<ul>
								<!--this link commented by bkr 27/05/2016  -->
								<%--  <c:if test="${menuLinks.tadaMasters=='1'}">
			   			         <li>
						 	        <a href="#">TADA Masters</a>
					    	        <ul>
					    	          <li><a href="javascript:cityType();">City Type Master</a></li>
					    	          <li><a href="javascript:daEntitle();">DA(Old) Master</a></li>
					    	          <li><a href="javascript:daNewEntitle();">DA(New) Master</a></li>
					    	          <li><a href="javascript:travelType();">Travel Type</a></li>
					    	          <li><a href="javascript:travelTypeMap();">Travel Type Mapping</a></li>
					    	          <li><a href="javascript:entitleType();">TA Entitlement Type</a></li>
					    	          <li><a href="javascript:taEntitle();">TA Entitlements Master</a></li>
					    	          <li><a href="javascript:localRMA();">Local RMA Master</a></li>
					    	          <li><a href="javascript:daOnTour();">DA On Tour Master</a></li>        <!--This link for Tada da master  -->
					    	        </ul>
					  	          </li>
				  	             </c:if> --%>
								<c:if test="${menuLinks.tadaFinanceSettlement=='1'}">
									<!--this link hidden by bkr 27/05/2016  -->
									<!--  <li>
				  	                  <a href="javascript:tadaFinanceSettlement();">TADA Finance Settlements</a>
				  	                </li> -->
									<!-- <li>
				  	                  <a href="javascript:tadaCDAFinanceSettlement();">TADA CDAFinance Settlements</a>
				  	                </li> -->
								</c:if>
								<c:if test="${menuLinks.tadaFinanceSettlement=='1'}">
									<li><a href="javascript:tadaWaterFinanceSettlement();">TA
											Water Finance Settlements</a></li>
									<!-- <li>
				  	                  <a href="javascript:tadaCDAFinanceSettlement();">TADA CDAFinance Settlements</a>
				  	                </li> -->
								</c:if>
								<!--this link commented by bkr 27/05/2016  -->
								<%--   <c:if test="${menuLinks.tadaAppliedList=='1'}">
				  	                <li>
				  	                  <a href="javascript:tadaAppliedList();">TD Txn Search</a>
				  	                </li>
				  	             </c:if> --%>
							</ul></li>
					</c:if>


					<!--added by bkr 25/05/2016 c:if  -->

					<c:if test="${menuLinks.tadaMaster=='1'}">
						<li><a href="#">Annual Leave Admin</a>
							<ul>


								<c:if test="${menuLinks.tadaFinanceSettlement=='1'}">
									<li><a href="javascript:ltcWaterFinanceSettlement();">Annual
											Leave Finance Settlements</a></li>
								</c:if>

							</ul></li>
					</c:if>
						<%-- <c:if test="${menuLinks.familyPlanningAllowance=='1'}">
						<li><a href="javascript:allowancesRequest();">Allowances</a></li>
					</c:if> --%>
					<li><a href="#">Master Tables</a>
						<ul>
							<li><a href="javascript:masterData('state')"><!-- State -->Region</a></li>
							<li><a href="javascript:masterData('district')">District</a></li>
							<!-- <li><a href="#">BankNames</a></li> -->
						</ul></li>


					<li><a href="#">Retirement Benefits</a>
						<ul>
							<li><a href="javascript:retrimentBenfitsHome()">Application
									For Retirement Benefits</a></li>
							<li><a href="javascript:retrimentBenfitsAmtIssueHome()">Retirement
									Amount Issue</a></li>

						</ul></li>

					<li><a href="#">Reports</a>
						<ul>
							<li><a href="javascript:ltcReportWaterRequestHome()">All Employees Annual Leave</a></li>
							<li><a href="reports.htm?param=LeaveBalances">All Employees Leave Balance</a></li>
							<li><a href="javascript:tadaReportWaterRequestHome()">TA Report</a></li>
							<li><a href="reports.htm?param=RetriedEmployeeList">Retired Employee Benfits</a></li>
							<!-- <li><a href="reports.htm?param=RetriedEmployeeListTest">Retired Employee BenefitsTest</a></li> -->
							<!-- <li><a href="#">BankNames</a></li> -->
						</ul></li>
						
					




					<%-- 
					<c:if test="${menuLinks.adminClaims=='1'}">
						<li><a href="#">MISC Claims</a>
							<ul>
								<c:if test="${menuLinks.tuiteleCommonMasters=='1'}">
									<li><a href='#'> Common Masters</a>
										<ul>
											<li><a href="javascript:tuitionFeeClaimMaster();">Claim
													Type Master</a></li>
										</ul></li>
								</c:if>
								<c:if test="${menuLinks.tuitionFeeMasters=='1'}">
									<li><a href='#'> Tuition Fee Masters</a>
										<ul>
											<li><a href="javascript:tuitionFeeAcademicYearMaster();">
													Standard Master</a></li>
											<li><a href="javascript:tuitionFeeConfiguration();">
													Configuration</a></li>
											<li><a href="javascript:tuitionFeeLimitMaster();">
													Limit Master</a></li>
										</ul></li>
								</c:if>
								<c:if test="${menuLinks.telephoneBillMasters=='1'}">
									<li><a href='#'> TelephoneBill Masters</a>
										<ul>
											<li><a
												href="javascript:telePhoneBillDesignationEmployeeDetails();">
													Designation Eligibilty Details</a></li>
											<li><a
												href="javascript:telePhoneBillDesignationEligibityDetails();">
													Employee Eligibilty Details</a></li>
											<li><a
												href="javascript:telePhoneBillCashAssignmentDetails();">
													Cash Assignment Master</a></li>
										</ul></li>
								</c:if>
								<c:if test="${menuLinks.tuitionFeeFinanceDetails=='1'}">
									<li><a href="javascript:tutionFeeFinanceDetails();">Claim
											Finance Details</a></li>
								</c:if>

								<c:if test="${menuLinks.financeClaimsAmendementDetails=='1'}">
									<li><a href="javascript:financeClaimsAmendementDetails();">TuitionFee\TelephoneBill
											Employee Claims </a></li>
								</c:if>
							</ul></li>
					</c:if> --%>

					<%-- <c:if test="${menuLinks.residentialCertificate=='1'}">
								<li><a href="javascript:adminMiscHome('residential');">Residential Certificate</a></li>
						</c:if>
						<c:if test="${menuLinks.serviceCertificate=='1'}">
								<li><a href="javascript:adminMiscHome('service');">Service Certificate</a></li>
						</c:if>	 --%>

					<%--  <c:if test="${menuLinks.Reports=='1'}">
					  		<li>
					 			<a href="">Retirement Reports</a>
					    	- 	<ul> 
					    		      <li><a href="javascript:Retirement('report')">Retirement Report</a> --%>
					<!--<ul>
					    		     <li><a href="javascript:Retirement('report')">Retirement Report</a></li>
					    		     </ul>
					    		     </li> -->

					<%--  <c:if test="${menuLinks.cpoolDetails=='1'}">						 							
										<li><a href="javascript:CpoolDetails()">Cpool Details</a></li>													
									 </c:if>
									  <c:if test="${menuLinks.reportDetails=='1'}">						 							
										<li><a href="javascript:ReportDetails()">Report Details</a></li>													
									 </c:if>
									 <c:if test="${menuLinks.areaofDeployment=='1'}">						 							
										<li><a href="javascript:areaofDeployment()">Area of Deployment Report</a></li>													
									 </c:if>	 --%>
					<!-- <!--    Total Pis Reports configure in single jsp:prasad 				 							
										<li><a href="javascript:pisReportsHome()">PisReports</a></li>	 -->



					<!--Total Pis Misc Reports configure in single jsp:prasad  -->
					<!-- <li><a href="javascript:pisMiscReportsHome()">PisMiscellaneous Reports</a></li>	
																						
									  -->
					<!--Total Pis Misc Reports configure in single jsp:prasad  -->
					<!-- <li><a href="javascript:statusReportsHome()">Status Reports</a></li>													
									 -->
					<!--Total Pis Misc Reports configure in single jsp:prasad  -->
					<!-- <li><a href="javascript:orgHierarchyReports()">OrgHierarchy Reports</a></li> -->

					<%-- <c:if test="${menuLinks.monthlyReport=='1'}">	         <!-- This links are merged into single page-->
					    				<li>
								 			<a href="#">Monthly Reports</a>
								    		<ul>
								    			<li><a href="javascript:createReservationWiseReport('monthly')">Manpower vacancy Report </a></li>		    			
								    		</ul>
								  		</li>	
								  	</c:if> --%>
					<%-- <%-- <c:if test="${menuLinks.quarterlyReport=='1'}">
					    				<li>
								 			<a href="#">Quarterly Reports</a>
								    		<ul>
								    			 <li><a href="javascript:createReservationWiseReport('totalVacant')">Quarterly Strength Return </a></li>
								    		</ul>
								  		</li>	
								  	</c:if>  --%>
					<%-- <c:if test="${menuLinks.halfYearlyReport=='1'}">
					    				<li>
								 			<a href="#">Half Yearly Reports</a>
								    		<ul>
								    			<li><a href="javascript:createReservationWiseReport('halfYearEnding')">Minority Report </a></li>
												<li><a href="javascript:createLasrModifiedReport('HalfYearPhReport')">PH Report</a></li>
								    		</ul>
								  		</li>	
								  	</c:if>  --%>
					<%-- <c:if test="${menuLinks.annualReport=='1'}">
					    				<li>
								 			<a href="#">Annual Reports</a>
								    		<ul>
								    			<li><a href="javascript:createReservationWiseReport('empDetails')">Annual Nominal Roll Report </a></li>
												<li><a href="javascript:createReservationWiseReport('annualYearEnding')">Minority welfare Report </a></li> 				  
												<li><a href="javascript:createDisabilitiesAnnualReport()">Annual Disabilities Report </a></li>
												<c:if test="${menuLinks.annualLastModifiedReport=='1'}">
													<li><a href="javascript:createLasrModifiedReport('scStObc')">SC/ST/OBC Report </a></li>
												</c:if>
												<c:if test="${menuLinks.annualHRDGReport=='1'}">
												<li><a href="javascript:createReservationWiseReport('hrdgAnnual')">Annual HRDG Nominal Report </a></li>
												</c:if>
								    		</ul>
								  		</li>	
								  	</c:if>  --%>

					<%--  <c:if test="${menuLinks.miscReport=='1'}">
					    				<li>
								 			<a href="#">Misc Reports</a>
								    		<ul>
								    			<c:if test="${menuLinks.categoryReport=='1'}">
													<li><a href="javascript:createCategoryReport()">Category wise Report</a></li>
												</c:if>											
												<c:if test="${menuLinks.designationReport=='1'}">
													<li><a href="javascript:createDesignationReport()">Designation wise Report</a></li>
												</c:if>
												<c:if test="${menuLinks.groupReport=='1'}">
													<li><a href="javascript:createGroupwiseReport()">Group wise	Report</a></li>
												</c:if>
												<c:if test="${menuLinks.dojReport=='1'}">
													<li><a href="javascript:createEmpDOJReport()">Date of Joining wise Report</a></li>
												</c:if>
												<c:if test="${menuLinks.phReport=='1'}">
													<li><a href="javascript:createPHEmpReport()">PH wise Report </a></li>
												</c:if>
												<c:if test="${menuLinks.religionReport=='1'}">
													<li><a href="javascript:createReligionWiseReport()">Religion wise Report</a></li>
												</c:if>
												<c:if test="${menuLinks.reservationReport=='1'}">
													<li><a href="javascript:createReservationWiseReport('reservationReport')">Reservation wise Report</a></li>
												</c:if>
												<c:if test="${menuLinks.communityReport=='1'}">
													<li><a href="javascript:createCommunityWiseReport()">Community Wise Report</a></li>
												</c:if>		
												<c:if test="${menuLinks.HierarchyLogicalReport=='1'}">
													<li><a href="javascript:createHierarchyReport()">Employee Logical Hierarchy Report</a></li>
												</c:if>
												<c:if test="${menuLinks.HierarchyPhysicalReport=='1'}">
													<li><a href="javascript:createHierarchyReport('Physical')">Employee Physical Hierarchy Report</a></li>
												</c:if>
												<c:if test="${menuLinks.lastModifiedReport=='1'}">
												  	<li><a href="javascript:createLasrModifiedReport('DOJDRDO')">Date of Joining in DRDO  Report</a></li>
											  	</c:if>
											  	<c:if test="${menuLinks.empExperienceReport=='1'}">
												  	<li><a href="javascript:createEmpExpReport()">DRDO Employees Experience Report</a></li>
											  	</c:if>
											  	<c:if test="${menuLinks.retirementReport=='1'}">
											  	<li><a href="javascript:createRetirementReport()">Retirement Report</a></li>
											  	</c:if>
											  	<c:if test="${menuLinks.financeStatusActiveReport=='1'}">
											  	<li><a href="javascript:financeStatusActiveReport()">Finance Status Active Report</a></li>
											  	</c:if>
								    		</ul>
								  		</li>	
								  	</c:if>  --%>
					<%--  <c:if test="${menuLinks.customReport=='1'}">						 							
										<li><a href="javascript:createCustomReport()">Custom Report</a></li>													
								  </c:if> --%>
					<%-- <%--<c:if test="${menuLinks.organizationReport=='1'}">						 							
										<li><a href="javascript:organizationReport()">Organisation Details</a></li>														
									 </c:if>
									 <c:if test="${menuLinks.empMapping=='1'}">						 							
										<li><a href="javascript:EmpMapping()">Employee Role Mapping</a></li>													
									 </c:if>
									 <c:if test="${menuLinks.LogicalReport=='1'}">
										<li><a href="javascript:createHierarchyReport()">Employee Logical Hierarchy Report</a></li>
										<li><a href="javascript:createHierarchyReport('Physical')">Employee Physical Hierarchy Report</a></li>
									</c:if>	 --%>
					<%-- 	<c:if test="${menuLinks.allRequest=='1'}">       <!-- merge into  single file -->
										<li><a href="javascript:normalReports('all');">All Request</a></li>	
	    							</c:if>	
	    							<c:if test="${menuLinks.penReqCount=='1'}">	
            							<li><a href="javascript:normalReports('penCount');">Pending Request Count</a></li>
            						</c:if>	
            						<c:if test="${menuLinks.penLeaveReq=='1'}">
              							<li><a href="javascript:normalReports('leavePending');">Leave Pending Request</a></li>	
              						</c:if>	 --%>
					<%-- </ul>
					  		</li>
	  					
	  					</c:if> --%>
				<%-- 	<c:if test="${menuLinks.trainingMaster=='1'}">
						<li><a href="#">HRDG</a>
							<ul>
								<li><a href="javascript:trainingMaster('trainingType');">Training
										Type Master</a></li>
								<li><a
									href="javascript:trainingMaster('trainingInistitute');">Training
										Institute Master</a></li>
								<li><a href="javascript:trainingMaster('course');">Course
										Master</a></li>
								<li><a href="javascript:trainingMaster('HRDGBoardType');">Board
										Type Master</a></li>
								<li><a
									href="javascript:trainingMaster('HRDGBoardMemberType');">Board
										Member Type Master</a></li>
								<li><a href="javascript:trainingMaster('HRDGBoard');">Board
										Master</a></li>
								<li><a
									href="javascript:trainingMaster('TrainingCirculationDetails');">Training
										Circulation</a></li>
								<li><a
									href="javascript:trainingMaster('HrdgTrainingNominationDetails');">Nominations
										and Approvals</a></li>
								<li><a
									href="javascript:trainingMaster('TrainingNominationsCFASelection');">CFA
										Approval</a></li>
								<li><a
									href="javascript:trainingMaster('CourseAttendedDetails');">Attended
										Course Details</a></li>

								<li><a href="javascript:trainingMaster('awardMaster');">Award
										Master</a></li>
								<c:if test="${menuLinks.HRDGReport=='1'}">
									<li><a href="javascript:trainingMaster('HRDGReport');">Annual
											HRDG Reports</a></li>
								</c:if>

							</ul></li>

					</c:if> --%>
				<%-- 	<c:if test="${menuLinks.adminMT=='1'}">
						<li><a href="#">MT</a>
							<ul>
								<c:if test="${menuLinks.mtMasters=='1'}">
									<li><a href='#'>MT Masters</a>
										<ul>
											<li><a href="javascript:mtCategory();">Category
													Master</a></li>
											<li><a href="javascript:mtModel();">Model Master</a></li>
											<li><a href="javascript:mtTravelAgency();">Travel
													Agency Master</a></li>
											<li><a href="javascript:mtAddNewVehicle();">Vehicle
													Master</a></li>
											<li><a href="javascript:mtAddNewDriver();">Driver
													Master</a></li>
											<li><a href="javascript:mtVehicleVsDriver();">Allocate
													Driver To Vehicle</a></li>
											<li><a href="javascript:mtAddNewAddress();">Address
													Details Master </a></li>

										</ul></li>

									<c:if test="${menuLinks.driverAbsent=='1'}">
										<li><a href="javascript:mtAbsentDriver();">Driver
												Absent Form</a></li>
									</c:if>
									<c:if test="${menuLinks.vehicleAbsent=='1'}">
										<li><a href="javascript:mtAbsentVehicle();">Vehicle
												Absent Form</a></li>
									</c:if>
									<c:if test="${menuLinks.vehicleDriverMap=='1'}">
										<li><a href="javascript:mtVehicleVsDriverList();">Change
												Driver</a></li>
									</c:if>
									<c:if test="${menuLinks.vehicleAllocation=='1'}">
										<li><a href="javascript:mtGetApprovedRequests();">Vehicle-Allocations</a></li>
									</c:if>
									<c:if test="${menuLinks.releaseAllVehicles=='1'}">
										<li><a href="javascript:mtGetAllAllotedVehicles();">Vehicle-Deallocations</a></li>
									</c:if>
									<c:if test="${menuLinks.freeDedicatedVehicles=='1'}">
										<li><a href="javascript:mtGetAllDedicatedVehicles();">Free
												Dedicated Vehicles</a></li>
									</c:if>
									<c:if test="${menuLinks.mtMileage =='1'}">
										<li><a href="javascript:mtMileage();">Daily Mileage
												Entry</a></li>
									</c:if>
									<c:if test="${menuLinks.mtOfflineRequest =='1'}">
										<li><a href="javascript:mtOfflineRequest();">Offline
												Request Form</a></li>
									</c:if>
									<c:if test="${menuLinks.mtReports =='1'}">
										<li><a href="#">Reports</a>
											<ul>
												<li><a href="javascript:mtDayWiseAllocation();">Daily
														Allocations</a></li>
												<li><a href="javascript:mtCompletionDetails();">Completion
														Details</a></li>
												<li><a href="javascript:getYearlyMileageDetails();">Mileage
														Card</a></li>
												<li><a href="javascript:mtDailyMileageDetails();">Daily
														Mileage Entry</a></li>
												<li><a
													href="javascript:mtMileageReport('distanceKPLRecord');">Distance
														Covered And KPL Record</a></li>
											</ul></li>
									</c:if>
								</c:if>
								<c:if test="${menuLinks.vehicleAllocation=='1'}">
										<li><a href="javascript:AllocateDrivers();">Allocate Vehicle</a></li>
									</c:if>

							</ul></li>
					</c:if> --%>
				<%-- 	<c:if test="${menuLinks.adminION=='1'}">
						<li><a href="#">ION</a>
							<ul>
								<c:if test="${menuLinks.letterNumberFormat=='1'}">
									<li><a
										href="javascript:letterNumber('letterNumberFormatMaster');">
											Create Files</a></li>
								</c:if>

							</ul></li>
					</c:if> --%>

		<%-- 			<c:if test="${menuLinks.signingAuthority=='1'}">
						<li><a href="#">Signing Authority</a>
							<ul>
								<c:if test="${menuLinks.cfaAccAuthorityDetails =='1'}">
									<li><a href="javascript:cfaAccAuthorityDetails();">Signing
											Authority Master</a></li>
								</c:if>
							</ul></li>
					</c:if> --%>


				</ul></li>

		</c:if>
<%-- 
		<c:if test="${menuLinks.cdaMenus=='1'}">

			<li><a href="#">CDA</a>
				<ul>
					<li><a href="#">TADA</a>
						<ul>
							<c:if test="${menuLinks.cdaMenusub=='1'}">
								<li><a href="javascript:tadaCDAFinanceSettlement();">
										Settlements</a></li>
							</c:if>
						</ul></li>

					<li><a href="#">LTC</a>
						<ul>
							<c:if test="${menuLinks.cdaMenusub=='1'}">
								<li><a href="javascript:ltcCDAFinanceSettlement();">
										Settlements</a></li>
							</c:if>
						</ul></li>
					<li><a href="#">CGHS</a>
						<ul>
							<c:if test="${menuLinks.cdaMenusub=='1'}">
								<li><a href="javascript:cghsCDAFinanceSettlement();">
										Settlements</a></li>
							</c:if>
						</ul></li>

					<li><a href="#">Miscillenous Claims</a>
						<ul>
							<c:if test="${menuLinks.tuitionFeeSentToCDA == '1'}">
								<li><a href="javascript:tuitionFeeSentToCDA();">Claim
										CDA Details</a></li>
							</c:if>
						</ul></li>
					<li><a href="javascript:addressReports()">Address Reports</a></li>

				</ul></li>
		</c:if> --%>




		<c:if test="${menuLinks.ManageEmployee=='1'}">
			<li><a href="#">Manage Employee</a>
				<ul>
					<c:if test="${menuLinks.createEmployee=='1'}">
						<li><a href="#">Create Employee</a>
							<ul>
								<!-- this link commented by bkr 17/05/2016 -->
								<%--  <c:if test="${menuLinks.createEmployee=='1'}">
						           <li><a href="javascript:createEmployee()">Add Employee</a></li>
					          </c:if> --%>
								<c:if test="${menuLinks.createOuterEmployee=='1'}">
									<li><a href="javascript:createOtherEmployee()">Add New
											Employee</a></li>
								</c:if>
							</ul></li>
					</c:if>

				<%-- 	<c:if test="${menuLinks.createPaymentDetails=='1'}">
						<li><a href="#">Add Pay Critical Data</a>
							<ul>
								<c:if test="${menuLinks.createPaymentDetails=='1'}">
									<li><a href="javascript:getEmpBGPaymentDetails();">Basic
											Pay And Grade Pay</a></li>
								</c:if>
								<!--	<c:if test="${menuLinks.createPaymentDetails=='1'}">
						  			<li><a href="javascript:getEmpPaymentDetails();">Basic Pay</a></li>
							</c:if>	


							<c:if test="${menuLinks.promotinoOfflineEntry=='1'}">
				              		<li><a href="javascript:promotionOfflineEntryHome();">Grade Pay</a></li>
				            </c:if>-->
								<c:if test="${menuLinks.payLaonDetails=='1'}">
									<li><a href="javascript:payLaonDetails();">Loan</a></li>
									<li><a href="javascript:payHindiIncDetails();">Hindi
											Incentive</a></li>
								</c:if>
								<c:if test="${menuLinks.quarterOffline=='1'}">
									<li><a href="javascript:quarterOfflineWithOutWorkFlow();">Quarter</a></li>
								</c:if>
							</ul></li>
					</c:if> --%>

					<%-- <c:if test="${menuLinks.dataEntryLeave=='1'}">
						<li><a href="#">Leave</a>
							<ul>
								<c:if test="${menuLinks.dataEntryLeave=='1'}">
									<li><a href="javascript:dateEntryHome();"><span>Leave
												Balance Entry</span></a></li>
								</c:if>
								<c:if test="${menuLinks.verifyLeaves=='1'}">
									<li><a href="javascript:verfyLeaveBalance();"><span>Verify
												Leave Balance Entry</span></a></li>
								</c:if>
							</ul></li>
					</c:if> --%>
				<%-- 	<c:if test="${menuLinks.ltcAdminEntryScreen=='1'}">
						<li><a href="javascript:adminEntryHome();">LTC</a></li>
					</c:if> --%>
					<c:if test="${menuLinks.editEmployee=='1'}">
						<li><a href="#">Edit PIS Data</a>
							<ul>
								<c:if test="${menuLinks.editEmployee=='1'}">
									<li><a href="javascript:editEmployee()">Employee</a></li>
								</c:if>
								<c:if test="${menuLinks.createAddress=='1'}">
									<li><a href="javascript:getAddress()">Address</a></li>
								</c:if>
								<%-- <c:if test="${menuLinks.createQualification=='1'}">
									<li><a href="javascript:viewQualification()">Qualification</a></li>
								</c:if> --%>
								<%-- <c:if test="${menuLinks.createTraining=='1'}">
									<li><a href="javascript:getTraining()">Training</a></li>
								</c:if> --%>
								<%-- <c:if test="${menuLinks.createPassport=='1'}">
									<li><a href="javascript:getPassport()">Passport</a></li>
								</c:if> --%>
								<c:if test="${menuLinks.createFamily=='1'}">
									<li><a href="javascript:familyDetails()">Family</a></li>
								</c:if>
								<c:if test="${menuLinks.createEmpExperience=='1'}">
									<li><a href="javascript:getEmpExperienceDetails()">Experience</a></li>
								</c:if>
								<%-- <c:if test="${menuLinks.createNominee=='1'}">
									<li><a href="javascript:NomineeDetails();">Nominee</a></li>
								</c:if> --%>
							<%-- 	<c:if test="${menuLinks.createPreOrgnDetails=='1'}">
									<li><a href="javascript:PreOrgnDetails();">Pre-Organisation</a></li>
								</c:if> --%>
							<%-- 	<c:if test="${menuLinks.createAwardDetails=='1'}">
									<li><a href="javascript:getAwardDetails();">Award</a></li>
								</c:if> --%>
								<%-- <c:if test="${menuLinks.createRetirementDetails=='1'}">
									<li><a href="javascript:getRetirementDetails();">Retirement</a></li>
								</c:if> --%>
								<%-- <c:if test="${menuLinks.pin=='1'}">
									<li><a href="javascript:pinMaster('pin')">Pin Number
											Master</a></li>
								</c:if> --%>
							</ul></li>
					</c:if>
				<!-- 	<li><a href="javascript:EmployeeStatus()">Employee Status</a></li> -->
					<c:if test="${menuLinks.assignRoles=='1'}">
						<li><a href="#">Assign Roles/Employee Transfer</a>
							<ul>
								<c:if test="${menuLinks.headMapping=='1'}">
									<li><a href="javascript:orgRoleMapping();">Organisation
											Role</a></li>
								</c:if>
								<c:if test="${menuLinks.employeeMapping=='1'}">
									<li><a href="javascript:normalEmpMapping();">Default
											Role</a></li>
								</c:if>
							</ul></li>
					</c:if>
				<%-- 	<c:if test="${menuLinks.internalDivision=='1'}">
						<li><a href="javascript:internalEmployeeMap();">Internal
								Divisions Creation</a></li>
					</c:if> --%>
				<%-- 	<c:if test="${menuLinks.dataValidationReport=='1'}">
						<li><a href="javascript:dataValidationReport();">Data
								Validation Report</a></li>
					</c:if> --%>
					<%-- <c:if test="${menuLinks.photoUpload=='1'}">
						<li><a href="javascript:uploadPhoto();">Upload Photo</a></li>
					</c:if> --%>
				</ul></li>
		</c:if>

		<c:if test="${menuLinks.ViewProfile=='1'}">
			<li><a href="#">My Profiles</a>
				<ul>
					<%-- <c:if test="${menuLinks.employeeAllDetails=='1'}">
						<li><a href="javascript:employeeAllDetails();">Bio Data</a></li>
					</c:if> --%>
					<c:if test="${menuLinks.viewEmployee=='1'}">
						<li><a href="javascript:viewGeneral()">View Employee
								Details</a></li>
					</c:if>

					<%-- <c:if test="${menuLinks.viewQualification=='1'}">
						<li><a href="javascript:viewQualificationDetails()">View
								Qualification Details</a></li>
					</c:if>
					<c:if test="${menuLinks.viewTraining=='1'}">
						<li><a href="javascript:viewTraining()">View Training
								Details</a></li>
					</c:if>
					<c:if test="${menuLinks.viewPassport=='1'}">
						<li><a href="javascript:viewPassportDetails()">View
								Passport Details</a></li>
					</c:if>

					<c:if test="${menuLinks.viewEmpExperience=='1'}">
						<li><a href="javascript:viewExperienceDetails();">View
								Experience</a></li>
					</c:if>

					<c:if test="${menuLinks.viewPreOrgnDetails=='1'}">
						<li><a href="javascript:viewPreOrganisationDetails();">View
								Pre-Organisation Details</a></li>
					</c:if>
					<c:if test="${menuLinks.viewAwardDetails=='1'}">
						<li><a href="javascript:viewAwardDetails();">View Award
								Details</a></li>
					</c:if>
					<c:if test="${menuLinks.viewHigherQualification=='1'}">
						<li><a href="javascript:higherQualification();">Higher
								Qualification</a></li>
					</c:if>
					<c:if test="${menuLinks.viewEmuQuarterReports=='1'}">
						<li><a href="javascript:viewEMUQuarterDetails();">EMU
								Quarter Details</a></li>
					</c:if>
					<c:if test="${menuLinks.viewRetirementDetails=='1'}">
						<li><a href="javascript:viewRetirementDetails();">View
								Retirement Details</a></li>
					</c:if> --%>


					<!--  <c:if test="${menuLinks.employeeSearch=='1'}">
						<li><a href="javascript:viewEmpSearchDetails();">Employee Search</a></li>
					</c:if>
					<c:if test="${menuLinks.viewTree=='1'}">
						<li><a href="javascript:viewEmpTreeDetails();">View Tree</a></li>
					</c:if>
					<c:if test="${menuLinks.downTree=='1'}">
						<li><a href="javascript:viewEmpTreeDetails('tree');">View Down Tree</a></li>
					</c:if>	
					-->
				</ul></li>
		</c:if>
		<c:if test="${menuLinks.myAdminKey=='1'}">
			<li><a class="im_jqueryslidemenu" href="#"><img
					src="./images/ebbtcbindex3_2.png" border=0px /></a>
				<ul>
					<c:if test="${menuLinks.leave=='1'}">
						<li><a href="#">Leave</a>
							<ul>
								<li><a href="javascript:ERPleaveApplicationForm();">
										Leave Application Form</a></li>
									<!-- 	<li><a href="javascript:ERPleaveApplicationForm1();">ERP
										Leave Application Form1</a></li> -->
								<li><a href="javascript:ERPleaveLeaveBalance();">
										Leave Balance</a></li>

								<%-- <c:if test="${menuLinks.availableLeaves=='1'}">
								<li><a href="javascript:leavesBalanceHome();"><span>Leave Balance</span></a></li>
							</c:if>	 --%>
								<%-- <c:if test="${menuLinks.applyLeave=='1'}">
									<li><a href="javascript:leaveApplicationForm();">Leave
											Application Form</a></li>
								</c:if>
								<c:if test="${menuLinks.cancelLeave=='1'}">
									<li><a href="javascript:leaveSearchHome();">Leave
											Cancellation/Conversion</a></li>
								</c:if>
								<c:if test="${menuLinks.leaveCard=='1'}">
									<li><a href="javascript:leaveCard();">Leave Card</a></li>
								</c:if>
								<c:if test="${menuLinks.leaveAccount=='1'}">
									<li><a href="javascript:leaveAccount();">Leave Account</a></li>
								</c:if>
								<c:if test="${menuLinks.leaveTxnView=='1'}">
									<li><a href="javascript:leaveTxnView();">Leave
											Card/Account</a></li>
								</c:if> --%>
							</ul></li>
					</c:if>

					<c:if test="${menuLinks.ltc=='1'}">
						<li><a href="#">Annual Leave</a>
							<ul>
								<!--this link added by bkr 26/05/2016  -->
								<li><a href="javascript:ltcWaterSettlement();">Annual
										Leave Settlement/Reimbursement</a></li>
								<c:if test="${menuLinks.ltcAdvanceRequest=='1'}">
									<li><a href="javascript:ltcWaterRequestHome();">Annual
											Leave Approval With "Advance"</a></li>
								</c:if>
								<!-- <li><a href="javascript:ltcApprovalRequest('ltcAdvance');">LTC Approval <b>With "Advance"</b></a></li> -->
								<!--this links are commented by bkr 27/04/2016  -->
								<%-- <c:if test="${menuLinks.ltcAdvanceRequest=='1'}">
							<li><a href="javascript:ltcApprovalRequest('ltcAdvance');">LTC Approval <b>With "Advance"</b></a></li>
						</c:if>
		    			<c:if test="${menuLinks.ltcApprovalRequest=='1'}">
		    				<li><a href="javascript:ltcApprovalRequest('ltcApproval');">LTC Approval <span style="color: red;"><b>Without Advance</b></span></a></li>
						</c:if>
						<c:if test="${menuLinks.ltcApprovedRequests=='1'}">
							<li><a href="javascript:ltcApprovalDetails();">LTC Claims(Includes Adv)/Amendment</a></li>
						</c:if>
						
						<c:if test="${menuLinks.ltcHistoryReport=='1'}">
						<li><a href="javascript:ltcHistoryReport();">My LTC History</a></li>
						</c:if> --%>
							</ul></li>
					</c:if>
			<%-- 		<c:if test="${menuLinks.loan=='1'}">
						<li><a href="#">Loan-Advances</a>
							<ul>
							
							<li><a href="javascript:erpLoanPurpose();">Loan
											Purpose</a></li>
													<li><a href="javascript:erpLoanRequest();">Loan
											Application Entry</a></li>
							
							<!--commented by bkr 16/09/2016  -->
								<c:if test="${menuLinks.othersApplyLoan=='1'}">
									<li><a href="javascript:erpLoanPurpose();">Loan
											Purpose</a></li>
								</c:if>
								<c:if test="${menuLinks.othersApplyLoan=='1'}">
									<li><a href="javascript:erpLoanRequest();">Loan
											Application Entry</a></li>
								</c:if>
								
								
								<c:if test="${menuLinks.loanRequest=='1'}">
									<li><a href="javascript:loanRequest()">Loan-Advance
											Request</a></li>
								</c:if>
								<c:if test="${menuLinks.houseBuilding=='1'}">
									<li><a href="javascript:houseBuildingLoan()">House
											Building Loan Request</a></li>
								</c:if>
								<c:if test="${menuLinks.test=='1'}">
									<li><a href="javascript:test()">Test</a></li>
								</c:if>
							</ul></li>
					</c:if> --%>
				<%-- 	<c:if test="${menuLinks.Cghs=='1'}">
						<li><a href="#">CGHS</a>
							<ul>
								<c:if test="${menuLinks.treatment=='1'}">
									<li><a href="javascript:treatmentRequestHome('')">CGHS
											Request Form</a></li>
								</c:if>
								<c:if test="${menuLinks.reimbursement=='1'}">
									<li><a href="javascript:reimbursementRequestHome()">CGHS
											Claims</a></li>
								</c:if>
								<c:if test="${menuLinks.nonReimbursement=='1'}">
									<li><a
										href="javascript:reimbursementRequest('','noncghsReimbursement')">Non
											CGHS Claims</a></li>
								</c:if>
								<c:if test="${menuLinks.emergencyClaims=='1'}">
									<li><a href="javascript:emergencyRequest()">Emergency
											Claims</a></li>
									<!-- </c:if> -->
							</ul></li>
					</c:if> --%>
				<%-- 	<c:if test="${menuLinks.hrdg=='1'}">
						<li><a href="#">HRDG</a>
							<ul>

								<c:if test="${menuLinks.trainingNominationMaster=='1'}">
									<li><a
										href="javascript:trainingMaster('TrainingNominationMaster');">Nomination
											Form</a></li>

								</c:if>
								<c:if test="${menuLinks.viewTrainingNom=='1'}">
									<li><a
										href="javascript:trainingMaster('ViewTrainingNominationDetails');">Nomination
											Details</a></li>
								</c:if>

								<c:if test="${menuLinks.HRDGYearBook=='1'}">
									<li><a href="javascript:trainingMaster('HRDGYearBook');">HRDG
											Year Book/Calendar</a></li>
								</c:if>

							</ul></li>
					</c:if> --%>
				<%-- 	<c:if test="${menuLinks.mt=='1'}">
						<li><a href="#">MT</a>
							<ul>
								<c:if test="${menuLinks.vehicleRequestForm=='1'}">
									<li><a href="javascript:mtRequestForVehicle();">Request
											for Vehicle</a></li>

								</c:if>

							</ul></li>
					</c:if> --%>
		
					
				
					<c:if test="${menuLinks.tada=='1'}">
						<li><a href="#">TA</a>
							<ul>

								<!-- this link commented by bkr 17/05/2016 -->
								<%-- 	<c:if test="${menuLinks.tadaTdApprovalRequest=='1'}">
				  	     <li>
				  	        <a href="javascript:tadaApprovalRequest();">TADA/TD Request Form</a>
				  	     </li>
				  	</c:if> --%>
								<%-- <c:if test="${menuLinks.tadaTdAdvance=='1'}">
									<li><a href="javascript:tadaTdAdvance();">TADA TD
											Advance</a></li>
								</c:if> --%>
								<!-- this link commented by bkr 17/05/2016 -->
								<%-- 	<c:if test="${menuLinks.tadaTdClaimAndSettlement=='1'}">
				  	     <li>
				  	        <a href="javascript:tadaTdClaimAndSettlement();">TADA TD Claims/Amendement</a>
				  	     </li>
				  	</c:if> --%>
								<!--added by bkr 10/05/2016  -->
								<li><a href="javascript:tadaWaterSettlement();">
										TA Settlement/Reimbursement</a></li>
								<!-- this link commented by bkr 17/05/2016 -->
								<%--  <c:if test="${menuLinks.tdUserDelegation=='1'}">
				  	     <li>
				  	        <a href="javascript:tdUserDelegation();">TD User Specific Delegation</a>
				  	     </li>
				  	</c:if> --%>
								<!--added by bkr 29/04/2016  -->
									<li><a href="javascript:tadaAdvanceCumRequest();">
											TA Advance Request</a></li>

							</ul></li>
					</c:if>
					
						<li><a href="javascript:changePassword()">Change Password</a></li>
					
				<%-- 	<c:if test="${menuLinks.quarter=='1'}">
						<li><a href="#">Quarter</a>
							<ul>
								<c:if test="${menuLinks.viewQuarterDetails=='1'}">
									<li><a href="javascript:viewQuarterDetails();">Quarter
											Request Form</a></li>
								</c:if>
							</ul></li>
					</c:if> --%>
				<%-- 	<c:if test="${menuLinks.claims=='1'}">
						<li><a href="#"> MISC Claims</a>
							<ul>
								<c:if test="${menuLinks.claimRequestForm=='1'}">
									<li><a href="javascript:tutionFeeRequestFormHome();">Tuition
											Fee Request Form</a> <a
										href="javascript:telePhoneBillRequestHome();">TelePhone
											Request Form </a> <a href="javascript:claimsAmendementDetails();">TuitionFee\TelephoneBill
											Claims </a></li>
								</c:if>
							</ul></li>
					</c:if> --%>
				<%-- 	<c:if test="${menuLinks.changeRequest=='1'}">
						<li><a href="#">Change Request</a>
							<ul>
								<c:if test="${menuLinks.pisRequest=='1'}">
									<li><a href="javascript:viewProfile();">Update Contact
											Number</a></li>
								</c:if>
								<c:if test="${menuLinks.viewAddress=='1'}">
									<li><a href="javascript:viewAddressDetails()">Update
											Address Details</a></li>
								</c:if>
								<c:if test="${menuLinks.viewFamily=='1'}">
									<li><a href="javascript:viewFamily()">Update Family
											Details</a></li>
								</c:if>
								<c:if test="${menuLinks.viewNominee=='1'}">
									<li><a href="javascript:viewNomineeDetails();">Update
											Nominee Details</a></li>
								</c:if>
							</ul></li>
					</c:if> --%>
				<%-- 	<c:if test="${menuLinks.familyPlanningAllowance=='1'}">
						<li><a href="javascript:fpaRequest();">Family Planning
								Allowance</a></li>
					</c:if> --%>
				
					
					<%-- <c:if test="${menuLinks.userSpecificConfiguration=='1'}">
						<li><a href="javascript:roleSpecific()">WorkFlow
								Delegation</a></li>
					</c:if> --%>
				<%-- 	<c:if test="${menuLinks.ION=='1'}">
						<li><a href="#">ION</a>
							<ul>
								<c:if test="${menuLinks.IONMaster=='1'}">
									<li><a
										href="javascript:letterNumber('letterNumberFormatMaster1');">Create
											New ION</a></li>
								</c:if>
								<c:if test="${menuLinks.editCirculateION=='1'}">
									<li><a href="javascript:letterNumber('IONDetails');">Search
											& Edit ION </a></li>
									<li><a href="javascript:letterNumber('CirculateION');">Circulate
											ION</a></li>
								</c:if>


							</ul></li>
					</c:if> --%>

					<%-- <c:if test="${menuLinks.changepassword =='1'}">
						<li><a href="javascript:changePassword()">Change Password</a></li>
					</c:if> --%>
					<%-- <c:if test="${menuLinks.changeEmployeeStatus =='1'}">
	            <li><a href="javascript:EmployeeStatus()">EmployeeStatus</a></li>
	            </c:if> (this will be closed myadmin menu link)--%>
					<%-- <c:if test="${menuLinks.createLetter=='1'}">
						<li><a href="javascript:letterNumberReference('letter')">Record
								Letter Number</a></li>
					</c:if> --%>

					<%-- <c:if test="${menuLinks.MyPromotion=='1'}">
						<li><a href="#">Promotion</a>
							<ul>
								<c:if test="${menuLinks.MyoptionalCertificate=='1'}">
									<li><a href="javascript:optionalCertificateHome();">Option
											Certificate & Joining Report</a></li>
								</c:if>
							</ul></li>
					</c:if> --%>
			<%-- 		<c:if test="${menuLinks.passportRequest=='1'}">
						<li><a href="#">PassPort</a>
							<ul>
								<c:if test="${menuLinks.passportApplication=='1'}">
									<li><a href="javascript:passportHome();">Passport And
											Proceeding for Abroad</a></li>
								</c:if>
								<c:if test="${menuLinks.nocForPassport=='1'}">
									<li><a href="javascript:movablePropertyHome();">Movable
											Property/Immovable Property</a></li>
								</c:if>
							</ul></li>
					</c:if> --%>
				<%-- 	<c:if test="${menuLinks.passportApplication=='1'}">
									<li><a href="javascript: healthinsurance();">Health Insurance
									</a></li>
								</c:if> --%>
								
					<!-- Health Insurance For watererp -->
					<%-- <c:if test="${menuLinks.passportApplication=='1'}">
					<li><a href="#">Health Insurance</a>
					<ul>
									<li><a href="javascript:viewHealthEnrollmentForm();">Health Enrollment
					 				</a></li>
									<li><a href="javascript:viewHealthEnrollmentDetails();">Health Enrollment Details
									</a></li>
									<li><a href="javascript:subscriptionDetailsForm();">Subscription Details</a></li>
								<li><a href="javascript:exitFormalitiesForm();">Exit Formalities</a></li> </ul> </li>
							</c:if> --%>

				</ul></li>
		</c:if>
		<c:if test="${menuLinks.employeeData=='1'}">
			<li><a href="#">Employee Data</a>
				<ul>
					<c:if test="${menuLinks.employeeSearch=='1'}">
						<li><a href="javascript:viewEmpSearchDetails();">Employee
								Search</a></li>
					</c:if>
					<%-- <c:if test="${menuLinks.viewTree=='1'}">
						<li><a href="javascript:viewEmpTreeDetails();">View Tree</a></li>
					</c:if>
					<c:if test="${menuLinks.downTree=='1'}">
						<li><a href="javascript:viewEmpTreeDetails('tree');">View
								Down Tree</a></li>
					</c:if> --%>
				</ul></li>
		</c:if>

		<c:if test="${menuLinks.mmg=='1'}">
			<li><a href="#">Cash Build Up</a>
				<ul>
					<c:if test="${menuLinks.mmgMaster=='1'}">
						<li><a href="#">MMGMaster Data</a>
							<ul>
								<li><a href="javascript:mmgMasterData('inventHolder')">Inventory
										Holder</a></li>
								<li><a href="javascript:mmgMasterData('accHead')">Account
										Head</a></li>
								<li><a href="javascript:mmgMasterData('taxType')">Tax
										Type</a></li>
								<li><a href="javascript:mmgMasterData('uom')">UOM</a></li>
								<li><a href="javascript:mmgMasterData('voucherType')">Voucher
										Type</a></li>
								<li><a href="javascript:mmgMasterData('itemCompany')">Item
										Company</a></li>
								<li><a href="javascript:mmgMasterData('itemCategory')">Item
										Category</a></li>
								<li><a href="javascript:mmgMasterData('itemSubCategory')">Item
										Sub Category</a></li>
								<li><a href="javascript:mmgMasterData('itemCode')">Item
										Code</a></li>
								<li><a href="javascript:mmgMasterData('itemSubCode')">Item
										Sub Code</a></li>
								<li><a href="javascript:mmgMasterData('material')">Material</a></li>
							</ul></li>
					</c:if>
					<c:if test="${menuLinks.demand=='1'}">
						<li><a href="#">Demand</a>
							<ul>
								<li><a href="javascript:raiseDemand();"><span>Raise
											Demand</span></a></li>
								<li><a href="javascript:securityChecking();"><span>Security
											Checking</span></a></li>
								<li><a href="javascript:cancelDemand();"><span>Cancel
											Demand</span></a></li>
							</ul></li>
					</c:if>
					<c:if test="${menuLinks.invoiceReceipt=='1'}">
						<li><a href="#">Invoice Receipt</a>
							<ul>
								<li><a href="javascript:invoiceReceipt();"><span>Invoice
											Receipt</span></a></li>
								<li><a href="javascript:cancelInvoice();"><span>Cancel
											Invoice</span></a></li>
							</ul></li>
					</c:if>
					<c:if test="${menuLinks.voucher=='1'}">
						<li><a href="javascript:mmgVouchers('voucher')">Voucher</a></li>
					</c:if>
					<c:if test="${menuLinks.reports=='1'}">
						<li><a href="#">Reports</a>
							<ul>
								<li><a
									href="javascript:createLasrModifiedReport('CashPurchaseDemand')">Cash
										Purchase Demand Report</a></li>
								<li><a
									href="javascript:createLasrModifiedReport('CashPurchaseVoucher')">Cash
										Purchase Voucher Report</a></li>
								<li><a
									href="javascript:createReservationWiseReport('ledgerReport')">Ledger
										Report</a></li>
								<li><a href="javascript:createInventoryReport()">Inventory
										Report </a></li>
								<li><a href="javascript:createDivisionWiseReport()">Division
										Wise Inventory Report </a></li>
							</ul></li>
					</c:if>
					<c:if test="${menuLinks.mmgChangeRequest=='1'}">
						<li><a href="#">Change Request</a>
							<ul>
								<li><a href="javascript:viewInventoryHolder()">Update
										Inventory Holder</a></li>
							</ul></li>
					</c:if>
					<c:if test="${menuLinks.mmgCofiguration=='1'}">
						<li><a href="javascript:mmgConfigurations()">MMG
								Configurations</a></li>
					</c:if>
				</ul></li>
		</c:if>
		<!--
	  	<c:if test="${menuLinks.PayMaster=='1'}">
	  	<li>
 			<a href="#">PAY Masters</a>
    		<ul>
    			<c:if test="${menuLinks.PayBillCghsMaster=='1'}">
    			<li><a href="javascript:payCGHSMaster()">CGHS Master Details</a></li>	
    			</c:if>
    			<c:if test="${menuLinks.PayBillCgeisMaster=='1'}">
                <li><a href="javascript:payCGEISMaster()">CGEIS Master Details</a></li>	
                </c:if>
                <c:if test="${menuLinks.PayBillDAMaster=='1'}">
                <li><a href="javascript:payDAMaster()">Dearness Allowance Master</a></li>	
                </c:if>
                <c:if test="${menuLinks.PayBillRSMaster=='1'}">
                <li><a href="javascript:payRSMaster()">Residential Security Master</a></li>	
                </c:if>		        		    			
    		</ul>
  		</li>
  		</c:if>
     	<c:if test="${menuLinks.payScale=='1'}">
	  		<li>
	 			<a href="#">PayScale</a>
	    		<ul>
	    			<c:if test="${menuLinks.professionalTax=='1'}"><li>
                        <a href="javascript:payScale('professionalTax')">Professional Tax Master</a></li>
			        </c:if>
			         <c:if test="${menuLinks.variableIncrement=='1'}"><li>
                        <a href="javascript:payScale('variableIncrement')">Variable Increment Master</a></li>
			        </c:if> 
			        <c:if test="${menuLinks.familyPlanning=='1'}"><li>
                        <a href="javascript:payScale('familyPlanning')">Family Planning Master</a></li>
			        </c:if>  
			        <c:if test="${menuLinks.travellingAllowance=='1'}"><li>
                        <a href="javascript:payScale('travellingAllowance')">Travelling Allowance Master</a></li>
			        </c:if>	
			        <c:if test="${menuLinks.quarterTypeMaster=='1'}"><li>
                        <a href="javascript:quarterType()">Quarter Type Master</a></li>
			        </c:if>		
			        <c:if test="${menuLinks.payScaleDesignation=='1'}"><li>
                        <a href="javascript:payScaleDesignation()">payScale Designation Master</a></li>
			        </c:if>	
			         <c:if test="${menuLinks.licenceFeeChargesMaster=='1'}"><li>
                        <a href="javascript:licenceFeeCharges()">Licence Fee Charges Master</a></li>
			        </c:if>	
			        <li><a href="javascript:payCGHSMaster()">CGHS Master Details</a></li>			        	
	    		</ul>
	  		</li>
	 	</c:if>
	 	<c:if test="${menuLinks.transfer=='1'}">
		 	<li>
				<a href="#">Transfer</a>
	   			<ul>
	   				<c:if test="${menuLinks.selfTransfer=='1'}">
	   					<li><a href="javascript:transfer('self');">Self Transfer</a></li>	
	   				</c:if>	
	   				<c:if test="${menuLinks.employeeTransfer=='1'}">
	               		<li><a href="javascript:transfer('employee');">Employee Transfer</a></li>	
	              	</c:if>	
	              	<c:if test="${menuLinks.transferTxnDetails=='1'}">
	               		<li><a href="javascript:transferTxnDetails();">Transfer Transaction Details</a></li>	
	              	</c:if>	
	            </ul>
	 		</li>
 		</c:if>	
 		<c:if test="${menuLinks.PayBill=='1'}">
 		<li>
			<a href="#">PayBill</a>
	   		<ul>
	   			<li>
	   			    <c:if test="${menuLinks.payMaster=='1'}">
				 	<a href="#">Pay Bill Masters</a>
			    	<ul> <c:if test="${menuLinks.payEmpCategory=='1'}">
						<li><a href="javascript:payEmpCategory();">Employee Category Master</a></li></c:if>
						<c:if test="${menuLinks.payBillAllowType=='1'}">
						<li><a href="javascript:payBillAllowType();">Pay Bill Configuration Type Master</a></li></c:if>
						<c:if test="${menuLinks.payBillAllowCofDetails=='1'}">
						<li><a href="javascript:payBillAllowConfigurationTypeDetails();">Pay Bill Configuration Details</a></li></c:if>
						<c:if test="${menuLinks.payAllowancConfiguration=='1'}">
						<li><a href="javascript:payAllowancConfiguration();">Allowance Configuration</a></li></c:if>	
					    <c:if test="${menuLinks.payBandCreation=='1'}">
						<li><a href="javascript:payBandCreation()">Pay Band Master</a></li></c:if>
						<c:if test="${menuLinks.payScaleDesignation=='1'}">
            		 	<li><a href="javascript:payScaleDesignation()">Pay Scale Designation Master</a></li></c:if>
            		 	<c:if test="${menuLinks.payCGHSMaster=='1'}">
          	            <li><a href="javascript:payCGHSMaster();">CGHS Master</a></li></c:if>
          	            <c:if test="${menuLinks.payCGEISMaster=='1'}">
           				<li><a href="javascript:payCGEISMaster();">CGEIS Master</a></li></c:if>
           				<c:if test="${menuLinks.payDAMaster=='1'}">	
           		        <li><a href="javascript:payDAMaster();">DA Master </a></li></c:if>
           		        <c:if test="${menuLinks.professionalTax=='1'}">
           		        <li><a href="javascript:payScale('professionalTax')">Professional Tax Master</a></li></c:if>
           		        <c:if test="${menuLinks.variableIncrement=='1'}">
          			    <li><a href="javascript:payScale('variableIncrement')">Variable Increment Master</a></li></c:if>
          			    <c:if test="${menuLinks.familyPlanning=='1'}">
          	            <li><a href="javascript:payScale('familyPlanning')">Family Planning Master</a></li>	</c:if>
          	            <c:if test="${menuLinks.travellingAllowance=='1'}">
          	     	    <li><a href="javascript:payScale('travellingAllowance')">Travelling Allowance Master</a></li></c:if>
          	     	    <c:if test="${menuLinks.quarterType=='1'}">	
                        <li><a href="javascript:quarterType();">Quarter Creation</a></li></c:if>
                        <c:if test="${menuLinks.licenceFeeCharges=='1'}">	
            	        <li><a href="javascript:licenceFeeCharges();">Licence Fee Charges Master</a></li></c:if>
            	        <c:if test="${menuLinks.payRSMaster=='1'}">
          	            <li><a href="javascript:payRSMaster();">Residenctial Security</a></li></c:if>
          	            <li><a href="javascript:payScaleTAIMaster();">PayScale TwoAddIncr Master</a></li>
          	            <c:if test="${menuLinks.empBank == '1'}">
          	            	<li><a href="javascript:empBankMaster();">Employee-Bank A/C Master</a></li>
          	            </c:if> 
              		 	
              		 </ul>
              		 </c:if>
			  	</li>
	   			<li>
	   			    <c:if test="${menuLinks.core=='1'}">
	   			  	<a href="#">Employee Core Info</a>
	   				<ul>
	   				    <c:if test="${menuLinks.payCcsUpload=='1'}">
   						<li><a href="javascript:payCcsUpload();">CCS Upload</a></li></c:if>
   						<c:if test="${menuLinks.payDeduction=='1'}">
               			<li><a href="javascript:payDeduction();">Deduction</a></li>	</c:if>
               			<c:if test="${menuLinks.payLaonDetails=='1'}">
               			<li><a href="javascript:payLaonDetails();">Loan Details</a></li>
               	
               			<li> <a href="javascript:hindiIncentiveDetails();">Hindi Incentive Details</a></li></c:if>
               			<c:if test="${menuLinks.payEmpConfiguration=='1'}">
               			<li><a href="javascript:payEmpConfiguration();">Update Employee Details</a></li>	</c:if>		
   					    
   					</ul>
   					</c:if>
	   			</li>
 				<li> 
 				    <c:if test="${menuLinks.onetime=='1'}">				
 					<a href="#">One Time Entry</a>
 					<ul>
 					    <c:if test="${menuLinks.payOneTimeEntry=='1'}">
 						<li><a href="javascript:payOneTimeEntry()">Employee Pay Details Entry</a></li></c:if>
 						<c:if test="${menuLinks.payDuesEntry=='1'}">
 						<li><a href="javascript:payDuesEntry();">Employee Pay Dues Entry</a></li></c:if>
 					</ul>
 					</c:if>
 				</li>
 				<c:if test="${menuLinks.empPayDetailsEntry=='1'}">
 				<li><a href="javascript:empPayDetailsEntry()">Pay Details </a></li></c:if>
 				<c:if test="${menuLinks.PayBillStatusEntry=='1'}">
 				<li><a href="javascript:PayBillStatusEntry()">Pay Bill Status </a></li></c:if>
 				<c:if test="${menuLinks.screports=='1'}">
 				<li><a href="javascript:screports();">Schedule Reports</a></li></c:if>
 				<c:if test="${menuLinks.negPayEmp=='1'}">
		 		<li><a href="javascript:negPayEmp();">Negative Pay Employees</a></li></c:if>
		 		<c:if test="${menuLinks.showPayslip=='1'}">
		 		<li><a href="javascript:showPayslip();">My Pay Slip</a></li></c:if>
		 		<c:if test="${menuLinks.payEOL=='1'}">
		 		<li><a href="javascript:payEOL();">EOL / HPL Acceptance</a></li></c:if>
		 		<c:if test="${menuLinks.paymigration=='1'}">
		 		<li><a href="javascript:dbMigration();">DB Migration</a></li></c:if>
		 		<li><a href="javascript:financePaySlipHome();">Finance Pay Slip</a></li>
		 		</ul>
	 	</li>
	 	</c:if>
	 	<c:if test="${menuLinks.incomeTax=='1'}">
 		<li>
			<a href="#">Income Tax</a>
	   		<ul>
	   			<li>
	   			    <c:if test="${menuLinks.IncomeTaxMasters=='1'}">
				 	<a href="#">Income Tax Masters</a>
				 	<ul>
 					    <c:if test="${menuLinks.payFYMaster=='1'}">
          	            <li><a href="javascript:payFYMaster();">Financial Year Master</a></li></c:if>	
              		 	<c:if test="${menuLinks.slabMaster=='1'}">
          	            <li><a href="javascript:incomeTaxMaster();">Income Tax Slab Master</a></li></c:if>	
          	            <c:if test="${menuLinks.section=='1'}">
          	            <li><a href="javascript:sectionMaster();">I.Tax Section Master</a></li></c:if>	
          	            <c:if test="${menuLinks.savingsMaster=='1'}">
          	            <li><a href="javascript:savingsMaster();">I.Tax Savings Master</a></li></c:if>	
 					     <c:if test="${menuLinks.allowanceConfMaster=='1'}">
          	            <li><a href="javascript:allowanceConfMaster();">Allowance Configuration</a></li></c:if>
          	            <c:if test="${menuLinks.prUpadteAllowance=='1'}">
          	            <li><a href="javascript:prUpadteAllowanceMaster();">Professional Update Allowance</a></li></c:if>	
 					</ul>
 				  </c:if>
 			   </li>
 			   <c:if test="${menuLinks.arrearsDetails=='1'}">
 				<li><a href="javascript:arrearsDetails()">DA1 & DA2 Arrears Details</a></li></c:if>
 			<c:if test="${menuLinks.configDetails=='1'}">
 				<li><a href="javascript:configDetails()">Income Tax Configuration Details</a></li></c:if>
 			<c:if test="${menuLinks.caluculateDetails=='1'}">
 				<li><a href="javascript:caluculateITDetails()">Income Tax Calculation Details</a></li></c:if>
 				<c:if test="${menuLinks.myITSlip=='1'}">
 				<li><a href="javascript:myITSlipFinance()">Income Tax Reports</a></li></c:if>
 				
 			</ul>
 		 </li>
 	</c:if>
      <c:if test="${menuLinks.incomeTaxReports=='1'}">
      <li>
	 			<a href="#">Arrears</a>
	  </li>
      </c:if>
	  	<c:if test="${menuLinks.arrears=='1'}">
	  	<li>
	 			<a href="#">Arrears</a>
	    		<ul>
	    		<c:if test="${menuLinks.daArrears=='1'}">
						<li><a href="javascript:DAArrearsHome();">DA Arrears Details</a></li>
				</c:if>
				<c:if test="${menuLinks.promotionArrears=='1'}">
						<li><a href="javascript:promotionArrearsHome();">Promotion Arrears Details</a></li>
				</c:if>
				<c:if test="${menuLinks.anualIncrArrears=='1'}">
						<li><a href="javascript:anualIncrArrearsHome();">Annual Increment Arrears Details</a></li>
				</c:if>
				<c:if test="${menuLinks.fpaArrears=='1'}">
						<li><a href="javascript:fpaArrearsHome();">FPA Arrears Details</a></li>
				</c:if>
					
				</ul>
		</li>		

	  	</c:if>	
     <c:if test="${menuLinks.incomeTaxReportsScreen=='1'}">
      <li>
			<a href="javascript:myITSlip();">My IT Statement/Income Form 16</a>
			</li>
     </c:if>	 
	  		
<c:if test="${menuLinks.gotopayBill=='1'}">
 		<li>
			<a href="javascript:goTopayBill();">PAYBILL</a>
</li></c:if>
-->
		<c:if test="${menuLinks.superAdminKey=='1'}">
			<li><a class="im_jqueryslidemenu" href="#"><img
					src="./images/ebbtcbindex12_2.png" border=0px /></a>
				<ul>
					<c:if test="${menuLinks.orgChart=='1'}">
						<li><a href="#">Organisation Chart</a>
							<ul>
								<c:if test="${menuLinks.deptHierarchyLevel=='1'}">
									<li><a href="javascript:orgLevelStr('departmentLevel')">Create
											Organisation Levels</a></li>
								</c:if>
								<c:if test="${menuLinks.deptHierarchyInstance=='1'}">
									<li><a href="javascript:departmentsStr()">Create
											Organisation Structure</a></li>
								</c:if>
								<c:if test="${menuLinks.hierarchyLevelMaster=='1'}">
									<li><a href="javascript:orgLevelStr('roleLevel')">Create
											Organisation Role Levels</a></li>
								</c:if>
								<c:if test="${menuLinks.hierarchyInstance=='1'}">
									<li><a href="javascript:rolesStr()">Create
											Organisation Roles</a></li>
								</c:if>
							</ul></li>
					</c:if>
					<c:if test="${menuLinks.approles=='1'}">
						<li><a href="javascript:masterData('approles')">Application
								Roles</a></li>
					</c:if>
					<c:if test="${menuLinks.applicationRoleMapping=='1'}">
						<li><a href="javascript:appRolesMapping()">Application
								Roles Mapping</a></li>
					</c:if>
					<c:if test="${menuLinks.MenuLinksMaster=='1'}">
						<li><a href="javascript:menuLinksMapping()">Menu Links
								Master</a></li>
					</c:if>
					<c:if test="${menuLinks.menuLinksMapping=='1'}">
						<li><a href="javascript:linksMapping()">Menu Links
								Mapping</a></li>
					</c:if>
<!--commented by bkr 15/10/2016  -->
					<!-- <li><a href="javascript:requestRoleMapping()">Application
							Roles Request Type Mapping</a></li> -->



				<%-- 	<c:if test="${menuLinks.configurationDetails=='1'}">
						<li><a href="javascript:configurationDetails()">Configuration
								Details</a></li>
					</c:if> --%>
					<!--commented by bkr 23/08/2016 ravi sir  -->
				<%-- 	<c:if test="${menuLinks.configurationDetails=='1'}">
						<li><a href="javascript:mailConfiguration()">Mail Sending
								Configuration</a></li>
					</c:if> --%>
					<c:if test="${menuLinks.Workflow=='1'}">
						<li><a href="#">WorkFlows</a>
							<ul>
								<!--    <c:if test="${menuLinks.dashBoard=='1'}">
						<li><a href="javascript:pisHome();">Dash Board</a></li>
					</c:if> -->
								<c:if test="${menuLinks.createWorkFlow=='1'}">
									<li><a href="javascript:createWorkflow()">Create Work
											Flow</a></li>
								</c:if>
									<!--COMMENTED BY BKR 15/10/2016  -->
							<%-- 	<c:if test="${menuLinks.generalWorkflowMap=='1'}">
									<li><a href="javascript:workFlowMapping()">Work Flow
											Mapping</a></li>
								</c:if>
								<c:if test="${menuLinks.internalWorkflowMap=='1'}">
									<li><a href="javascript:masterData('request')">Internal
											Workflow Mapping</a></li>
								</c:if> --%>
								<!-- <c:if test="${menuLinks.roleBasedWorkflow=='1'}">
						<li><a href="javascript:requestWorkflowMapping()">Role WorkFlow Mapping</a></li>
					</c:if> -->
									<!--COMMENTED BY BKR 15/10/2016  -->
								<%-- <c:if test="${menuLinks.userSpecificConfiguration=='1'}">
									<li><a href="javascript:roleSpecific()">User Specific
											Configuration</a></li>
								</c:if>
								<c:if test="${menuLinks.requestDelegation=='1'}">
									<li><a href="javascript:requestDelegation();">Request
											Delegation</a></li>
								</c:if> --%>
							</ul></li>
					</c:if>
					<c:if test="${menuLinks.ION=='1'}">
					<%-- 	<li><a href="#">ION</a>
							<ul>
								<c:if test="${menuLinks.letterNumberSeries=='1'}">
									<li><a
										href="javascript:letterNumber('letterNumberMaster');">Letter
											Number Series</a></li>
								</c:if>

							</ul></li> --%>
					</c:if>
				</ul></li>
		</c:if>
		<li id="currentact"><a href="hello.htm?param=EmpDetails">Download Employee List</a></li>
	
	<%-- 	<c:if test="${menuLinks.gotopayBill=='1'}">
			<li><a href="javascript:goTopayBill();"><span
					class="gotopay">Go To PAY</span></a></li>
		</c:if> --%>
		<%-- <c:if test="${menuLinks.adminKey=='1'}">
	<li><a href="javascript:svnVersion();">ABOUT SVN VERSION</a></li>
	</c:if> --%>
		<!--<li><a href="javascript:noticeHome2();"><span  >ASL</span></a></li>         This is testing  -->
		<!--   <li><a href="javascript:changePassword()">Change Password</a></li>
	 <li><a href="javascript:EmployeeStatus()">EmployeeStatus</a></li>
	-->
	</ul>
</div>
<div class="welcome">
	<div class="logoutIcon">
		<a href="javascript:logout()">Logout</a>
	</div>
	<div class="welcomeuser">${sessionScope.Welcome}</div>
	<div id="sample" style="display: none;"><%=System.currentTimeMillis()%></div>
</div>
<div class="line">
	<c:if
		test="${fn:length(sessionScope.holdRequests.totalHoldRequests) ne 0 || sessionScope.holdRequests.count ne 0}">
		<c:choose>
			<c:when test="${sessionScope.holdRequests.count eq 0}">
				<tr>
					<marquee direction="right" behavior="alternate">
					<div style="color: #fff; font-size: 20px;" align="center">
						<a href="javascript:moreRequests('TotalHold')"
							style="color: #fff; font-size: 20px;"> Total Requests On
							Hold: <u>${fn:length(sessionScope.holdRequests.totalHoldRequests)}</u>
						</a>
					</div>
					</marquee>
				</tr>
			</c:when>
			<c:when
				test="${fn:length(sessionScope.holdRequests.totalHoldRequests) eq sessionScope.holdRequests.count}">
				<tr>
					<marquee direction="right" behavior="alternate">
					<div style="color: #fff; font-size: 20px;" align="center">
						<a href="javascript:moreRequests('hold')"
							style="color: #fff; font-size: 20px;"> My Requests On Hold: <u>${sessionScope.holdRequests.count}(Click
								Here& Immediately Take Action)</u></a>
					</div>
					</marquee>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<marquee direction="right" behavior="alternate">
					<div style="color: #fff; font-size: 20px;" align="center">
						<a href="javascript:moreRequests('TotalHold')"
							style="color: #fff; font-size: 20px;"> Total Requests On
							Hold: <u>${fn:length(sessionScope.holdRequests.totalHoldRequests)}</u>
						</a> - <a href="javascript:moreRequests('hold')"
							style="color: #fff; font-size: 20px;"> My Requests On Hold:<u>${sessionScope.holdRequests.count}(Click
								Here & Immediately Take Action)</u></a>
					</div>
					</marquee>
				</tr>
			</c:otherwise>
		</c:choose>
	</c:if>
</div>
<div id="time" class="timeformat" type="text"></div>
<%-- End : MenuLinks.jsp --%>
