<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:FPARequest.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<!-- <link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/> -->

<script type="text/javascript" src="script/jquery.js"></script>
<!-- <script type="text/javascript" src="script/calendar.js"></script>
<script type="text/javascript" src="script/calendar-en.js"></script>
<script type="text/javascript" src="script/calendar-setup_3.js"></script> -->
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/Fpa.js"></script>
<link href="styles/calendar.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="script/calendar-hd.js"></script>
<script type="text/javascript" src="script/calendar_us.js"></script>

<title>FPA Request</title>
</head>

<body>
	<form:form method="post" commandName="fpaRequest" name="FpaRequestForm">
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
								<div class="headTitle">FPA Request</div>
								<div class="line">
								
								<%-- Content Page starts --%>
								
								<div id="result"></div>
									<div class="line">
										<div class="half leftmar">SFID</div>
										<div class="half">${fpaRequest.sfID}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Name</div>
										<div class="half">${fpaRequest.employeeDetails.nameInServiceBook}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Designation</div>
										<div class="half">${fpaRequest.employeeDetails.designationDetails.name}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Employment Type</div>
										<div class="half">${fpaRequest.employeeDetails.employmentDetails.name}</div>
									</div>
									<div class="line">
										<div class="half leftmar">Date of joining in DRDO</div>
										<div class="half"><fmt:formatDate pattern="dd-MMM-yyyy" value="${fpaRequest.employeeDetails.dojDrdoInFormat}" /></div>
									</div>
									<div class="line">
										<div class="half leftmar">Date of superannuation or retirement</div>
										<div class="half">${fpaRequest.employeeDetails.retiredDate}</div>
									</div>
									<div class="line">
												<div class="half leftmar">Vasectory / Tubectomy operation done at<span class="mandatory">*</span></div>
												<div class="half"><form:input path="operationLocation" id="operationLocation"/></div>
									</div>
									<div class="line">
												<div class="half leftmar">Date of Operation<span class="mandatory">*</span></div>
												<div class="half">
													<form:input path="operationDate" id="operationDate" cssClass="dateClass" readonly="true"/>
													<script type="text/javascript">
											         new tcal({'formname':'FpaRequestForm','controlname':'operationDate'});
											</script>
												</div>
									</div>
									<div class="line">
												<div class="half leftmar">Sterilization certificate issued by<span class="mandatory">*</span></div>
												<div class="half"><form:input path="sterilizationCert" id="sterilizationCert"/></div>
									</div>
									<div class="line">
										<div class="half leftmar">Is necessary sterilization certificate enclosed?<span class="mandatory">*</span> </div>
										<div class="half">
										<form:radiobutton path="sterilCertFlag" id="sterilCertFlagY" value="Y" label="Yes" />
										<form:radiobutton path="sterilCertFlag" id="sterilCertFlagN" value="N" label="No" />
										
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">I certify that I am having Two living children at the time of above operation.<span class="mandatory">*</span> </div>
										<div class="half">
										<form:radiobutton path="livingChildFlag" id="livingChildFlagY" value="Y" label="Yes" />
										<form:radiobutton path="livingChildFlag" id="livingChildFlagN" value="N" label="No" />
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">I also certify that <c:if test="${fpaRequest.employeeDetails.gender == 'M'}">my wife is</c:if> <c:if test="${fpaRequest.employeeDetails.gender == 'F'}">I am</c:if> not pregnant on this date. <span class="mandatory">*</span></div>
										<div class="half">
										<form:radiobutton path="wifePregFlag" id="wifePregFlagY" value="Y" label="Yes" />
										<form:radiobutton path="wifePregFlag" id="wifePregFlagN" value="N" label="No" />
										</div>
									</div>
									<div class="line">
										<div class="half leftmar">I also certify that my spouse is not employed/employed but the incentive of Special Increment for the purpose is availed and will not be availed.<span class="mandatory">*</span></div>
										<div class="half">
										<form:radiobutton path="spouseAvailedFlag" id="spouseAvailedFlagY" value="Y" label="Yes" />
										<form:radiobutton path="spouseAvailedFlag" id="spouseAvailedFlagN" value="N" label="No" />
										</div>
									</div>
										<div class="expbutton" style="margin-left:50%"><a href="javascript:fpaSubmit()"><span>Submit</span></a></div>
										<div class="expbutton"><a href="javascript:clearfpa();"><span>Clear</span></a></div>

								<%-- Content Page end --%>
								</div>
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
		<form:hidden path="requestID"/>
		<form:hidden path="requestId"/>
		</form:form>
	</body>
</html>
<!-- End:FPARequest.jsp -->