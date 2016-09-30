<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:HigherQualification.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/pisscript.js"></script>

<title>Higher Qualification Details</title>
</head>

<body>
	<form:form method="post" commandName="HigherQaulification">
		<div>
			<div class="Innermaindiv">
				<div class="header"></div>
				<div><jsp:include page="MenuLinks.jsp" /></div>					
				<div class="mainDisplay">
					<div class="whitebox1">
						<div class="whitebox_t">
							<div class="whitebox_tl">
								<div class="whitebox_tr"></div>
							</div>
						</div>
						<div class="whitebox_m1">
							<div class="lightblueBg1">
								<div class="headTitle">Higher Qualification Details</div>
								<%-- Content Page starts --%>
								<c:if test="${HigherQaulification.message=='success'}">
											<fieldset>
											<div class="line">
												<div class="quarter bold">Higher Qaulification</div>
												<div class="quarter">${HigherQaulification.hqDetailsDTO.course}</div>
											</div>
											<div class="line">
												<div class="quarter bold">Duration Of Course</div>
												<div class="half"><fmt:formatDate pattern="dd-MMM-yyyy" value="${HigherQaulification.hqDetailsDTO.fromDate}"/>
												&nbsp;To&nbsp;&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${HigherQaulification.hqDetailsDTO.toDate}"/></div>
											</div>
										</fieldset>
								</c:if>
								<c:if test="${HigherQaulification.result=='success'}">
								<fieldset>
										<div class="line">
											<div class="bold headTitle">Questionnaire For Sanction Of Incentive</div>
										</div>
										<div class="line">
											<div class="threefourth bold">Whether the higher qualification has been acquired on or after 09-04-1999 and if so the date of acquiring</div>
											<div class="quarter">&nbsp;&nbsp;&nbsp;<c:if test="${HigherQaulification.hqSanctionDTO.dateOfAcquire=='Y'}">Yes</c:if><c:if test="${HigherQaulification.hqSanctionDTO.dateOfAcquire!='Y'}">No</c:if></div>
										</div>
										<div class="line">
											<div class="threefourth bold">Whether the HQ has been acquired after induction into service</div>
											<div class="quarter">&nbsp;&nbsp;&nbsp;<c:if test="${HigherQaulification.hqSanctionDTO.hqAcquired=='Y'}">Yes</c:if><c:if test="${HigherQaulification.hqSanctionDTO.hqAcquired!='Y'}">No</c:if></div>
										</div>
										<div class="line">
											<div class="threefourth bold">(i)Whether the appointment was made in relaxation of the requisite educational qualification</div>
											<div class="quarter">&nbsp;&nbsp;&nbsp;<c:if test="${HigherQaulification.hqSanctionDTO.relaxation=='Y'}">Yes</c:if><c:if test="${HigherQaulification.hqSanctionDTO.relaxation!='Y'}">No</c:if></div>
										</div>
										<div class="line">
											<div class="threefourth bold">(ii)If yes, whether the higher qualification now acquired and for which incentive is being claimed is same as referred to in (i) above</div>
											<div class="quarter">&nbsp;&nbsp;&nbsp;<c:if test="${HigherQaulification.hqSanctionDTO.incentiveClaimed=='Y'}">Yes</c:if><c:if test="${HigherQaulification.hqSanctionDTO.incentiveClaimed!='Y'}">No</c:if></div>
										</div>
										<div class="line">
											<div class="threefourth bold">Whether the Govt servant was sponsored by Govt or he/she availed of study leave</div>
											<div class="quarter">&nbsp;&nbsp;&nbsp;<c:if test="${HigherQaulification.hqSanctionDTO.sponceredByGovt=='Y'}">Yes</c:if><c:if test="${HigherQaulification.hqSanctionDTO.sponceredByGovt!='Y'}">No</c:if></div>
										</div>
										<div class="line">
											<div class="threefourth bold">Whether the higher qualification now acquired contributes to the efficiency of the govt servant</div>
											<div class="quarter">&nbsp;&nbsp;&nbsp;<c:if test="${HigherQaulification.hqSanctionDTO.hqIsEssential=='Y'}">Yes</c:if><c:if test="${HigherQaulification.hqSanctionDTO.hqIsEssential!='Y'}">No</c:if></div>
										</div>
										<div class="line">
											<div class="threefourth bold">Whether the HQ meriting grant of incentive is recognized by the AICTE/DE/University/Deemed University or by the Govt</div>
											<div class="quarter">&nbsp;&nbsp;&nbsp;<c:if test="${HigherQaulification.hqSanctionDTO.hqRecog=='Y'}">Yes</c:if><c:if test="${HigherQaulification.hqSanctionDTO.hqRecog!='Y'}">No</c:if></div>
										</div>
										<div class="line">
											<div class="threefourth bold">Whether there is a direct nexus b/w the functions of the post and the HQ acquired</div>
											<div class="quarter">&nbsp;&nbsp;&nbsp;<c:if test="${HigherQaulification.hqSanctionDTO.nexus=='Y'}">Yes</c:if><c:if test="${HigherQaulification.hqSanctionDTO.nexus!='Y'}">No</c:if></div>
										</div>
										<div class="line">
											<div class="threefourth bold">Additional Qualification Now Acquired would be helpful</div>
											<div class="quarter">&nbsp;&nbsp;&nbsp;<c:if test="${HigherQaulification.hqSanctionDTO.addHq=='Y'}">Yes</c:if><c:if test="${HigherQaulification.hqSanctionDTO.addHq!='Y'}">No</c:if></div>
										</div>
										<div class="line">
											<div class="threefourth bold">Whether attested copy of Diploma/Degree(Original/Provisional) pertaining to the Higher Qualification Enclosed</div>
											<div class="quarter">&nbsp;&nbsp;&nbsp;<c:if test="${HigherQaulification.hqSanctionDTO.hqEnclosed=='Y'}">Yes</c:if><c:if test="${HigherQaulification.hqSanctionDTO.hqEnclosed!='Y'}">No</c:if></div>
										</div>
										<div class="line">
											<div class="threefourth bold">Whether the HQ now acquired essential or desirable as per RRs for the post currently held</div>
											<div class="quarter">&nbsp;&nbsp;&nbsp;<c:if test="${HigherQaulification.hqSanctionDTO.hqIsEssential=='Y'}">Yes</c:if><c:if test="${HigherQaulification.hqSanctionDTO.hqIsEssential!='Y'}">No</c:if></div>
										</div>
										<div class="line">
											<div class="threefourth bold">Sl no of the Annexure to DOP&T's OM No.1/2/89-Esst(Pay) dated 09-04-1999 under which the HQ acquired is covered for incentive</div>
											<div class="quarter">&nbsp;&nbsp;&nbsp;<c:if test="${HigherQaulification.hqSanctionDTO.incentiveClaimed=='Y'}">Yes</c:if><c:if test="${HigherQaulification.hqSanctionDTO.incentiveClaimed!='Y'}">No</c:if></div>
										</div>
										</fieldset>
								</c:if>
									<c:if test="${HigherQaulification.message!='success'}">
										<div class="line"><spring:message code="emptyData"/></div>
										<div class="line">
											<div class="expbutton"><a href="javascript:hqRequest()"><span>Apply Higher Qualification</span></a></div>
										</div>
									</c:if>
									<c:if test="${HigherQaulification.result!='success' && not empty HigherQaulification.hqDetailsDTO}">
									<div class="line">
											<div class="expbutton"><a href="javascript:hqSanctionOfIncentive()"><span>Apply For Sanction Of Incentive</span></a></div>
										</div>
									</c:if>
								<%-- Content Page end --%>								
								<div>&nbsp;</div>
							</div>
						</div>
						<div class="whitebox_b_non">
							<div class="whitebox_bl">
								<div class="whitebox_br"></div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
			<div><jsp:include page="Footer.jsp" /></div>			
		</div>		
		<form:hidden path="param"/>
		<form:hidden path="type"/>
		</form:form>
	</body>
</html>
<!-- End:HigherQualification.jsp -->