<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TelephoneBillEmployeeEligibilityDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/tabs.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/telephone.js"></script>
<title>Telephone Bill Employee Eligibility Details</title>
</head>

<body>
	<form:form method="post" commandName="telephone" id="telephone">
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
								<div class="headTitle">Telephone Bill Employee Eligibility Details</div>
								<div>
									<%-- Content Page starts --%>
									<div>&nbsp;</div>
									<div id="result" class="line"></div>
									<div class="line">
										<div class="quarter leftmar">Designation Name<span class="mandatory">*</span></div>
										<div class="quarter"><form:select path="designationId" id="designationId"  cssClass="formSelect" onchange="javascript:selectDesignationWiseEmployeesList();">
												<form:option value="select">Select</form:option>
												<form:options items="${telephone.telephoneEmployeeSelectedList}" itemLabel="value" itemValue="key"/>
											</form:select>
												</div>
									</div>
									<div  class="line" id="result1">
									<div class="line">
											<div class="leftmar">
												<div style="float: left; width: 38%;">De Selected </div>
												<div class="half" >Selected </div>
											</div>
									</div>
								   <div class="line">
											<div class="leftmar">
												<div style="float: left; width : 28%">
													<form:select path="fromID" id="SelectLeft" size="10" multiple="true" cssStyle="width:250px">
													</form:select>
												</div>
												<div style="float: left; width : 10%; margin-top: 60px;">									
														<div style="margin-bottom: 5px;" align="center">
													          <input style="margin-bottom: 5px"id="MoveRight" type="button" value=" Add " />
      											              <input id="MoveLeft" type="button" value=" Remove " />    											        
      											        </div>		      																				
		      									</div>
												<div style="float: left; width : 35%">
													<form:select path="toID" id="SelectRight" size="10" multiple="true" cssStyle="width:250px">
													</form:select>
												</div>
											</div>
										
									</div>
						     </div>
									<div class="line">
										<div style="margin-left:25%">
										<a href="javascript:submitEmployeeSelectedList();" class="appbutton">Submit</a>
										<a href="javascript:clearEmployeeSelectedList();" class="appbutton">Clear</a>
										
										</div>
									</div>
							
								
								
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
		<form:hidden path="pk"/>
		</form:form>
	</body>
</html>
<!-- End:TelephoneBillEmployeeEligibilityDetails.jsp -->