<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:DemandRaise.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.autoSuggest.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link href="styles/calander.css" type="text/css" rel="stylesheet"/>
<link href="styles/autoSuggest.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script language="javascript" src="script/mmgscript.js"></script>
<script language="javascript" src="script/json2_mini.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<title>New Cash Purchase Demand</title>
<script type="text/javascript">
//initiate validator on load
$jq(document).ready(function(){
    $jq("#demandMaster").validate({
    	 rules: {
	         inventoryNo: "required",
	         accountHeadId: "required",
	         demadTypeId: "required"	 
	   },
	  messages: {
		inventoryNo: "Please select Inventory no.",
		accountHeadId: "Please select accountHead.",
		demadTypeId: "Please select Demand Type."
		}
    });
  });
</script>
</head>
<body>
	<form:form method="post" id="demandMaster">
		<form:hidden path="type"/>
		<form:hidden path="param"/>
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
								<div>
									<%-- Content Page starts --%>
									
										<div class="headTitle">New Cash Purchase Demand</div>
										<div id="result"></div>
										<div id="sek">
										<fieldset>
    									
										<div class="line">
												<div class="quarter">Inventory Number</div>
												<div class="quarter">
													<form:select path="inventoryNo" cssStyle="width:145px" id="inventoryNo" onchange="javascript:getInventoryHolderDetails();">
														<form:option value="" disabled="disabled">Select</form:option>
														<c:forEach var="invholder" items="${command.invList}">
															 <form:option value="${invholder.invId}">${invholder.inventoryNo}</form:option>
														</c:forEach>														
													</form:select>
												</div>
												<div class="quarter">Inventory Holder Name</div>
												<div class="quarter" id="holderName"></div>
												
										</div>
										<div class="line">
												<div class="quarter">Directorate</div>
												<div class="quarter" id="directorateName">&nbsp;</div>
												<div class="quarter">Division</div>
												<div class="quarter" id="divisionName">&nbsp;</div>
										</div>
										<div class="line">
												<div class="quarter">Demand Type</div>
												<div class="quarter">
													<form:select path="demadTypeId" cssStyle="width:145px" id="demadTypeId">
													<form:option value="">Select</form:option>
														<c:forEach var="demandType" items="${command.demandTypeList}">
															 <option value="${demandType.id}">${demandType.demandTypeName}</option>
														</c:forEach>
													</form:select>
												</div>
												<div class="quarter">Project</div>
												<div class="quarter">
													<form:select path="projectCode" cssStyle="width:145px" id="projectCode">
													<form:option value="" disabled="disabled">Select</form:option>
														<c:forEach var="project" items="${command.projectList}">
															 <option value="${project.id}">${project.projectName}</option>
														</c:forEach>
													</form:select>
												</div>																								
										</div>
										<div class="line">
												<div class="quarter">Account Head</div>
												<div class="quarter">
													<form:select path="accountHeadId" cssStyle="width:145px" id="accountHeadId">
														<form:option value="" disabled="disabled">Select</form:option>
														<c:forEach var="accountHead" items="${command.accountHeadList}">
															 <option value="${accountHead.id}">${accountHead.description}-${accountHead.fundTypeName}</option>
														</c:forEach>
													</form:select>
												</div>
												<div class="quarter">Group Demand Number</div>
												<div class="quarter" id="groupDemandNo">&nbsp;${command.demandNo}</div>
												
										</div>
										<div class="line">
												<div class="quarter">Group Demand Date</div>
												<div class="quarter" id="demandDate"><fmt:formatDate pattern="dd-MMM-yyyy" value="${command.demandDate}" /></div>
										</div>									
										</fieldset>
										<div class="line"><jsp:include page="ItemDetails.jsp" /></div>	
										</div>									
									  	<br/>
										<div class="line">
											<div id="demandList" class="line"><jsp:include page="DemandItemsList.jsp" /></div>
											<br/>
											<div class="line">
												<div class="expbutton" style="float:right"><a href="javascript:demandRaise();"><span>Raise Demand</span></a></div>
												<div class="expbutton" style="float:right"><a href="javascript:saveDraft();" class="quarterbutton"><span>Save Draft</span></a></div>
												<div class="expbutton" style="float:right"><a href="javascript:demandPreview();" class="quarterbutton"><span>Preview</span></a></div>
											</div>
										</div>
										<div class="line" id="drafts">
											<jsp:include page="DraftDemandDetails.jsp" />
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
		
		</form:form>
	</body>
</html>
<!-- End:TestPage.jsp -->