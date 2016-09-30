<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:HrdgTrainingNominationDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
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
<script language="javascript" src="script/calendar.js"></script>
<script language="javascript" src="script/calendar-en.js"></script>
<script language="javascript" src="script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/trainingscript.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<script type="text/javascript">
$jq(function() {
	var $tabs = $('#tabs').tabs();
	$jq(".ui-tabs-panel").each(function(i){
		var totalSize = $jq(".ui-tabs-panel").size() - 1;

		if (i != totalSize) {
			next = i + 2;
			$jq(this).append("<a href='#' class='next-tab mover' rel='" + next + "'>Next Page &#187;</a>");
			
		}
  
		if (i != 0) {
			prev = i;
			$jq(this).append("<a href='#' class='prev-tab mover' rel='" + prev + "'>&#171; Prev Page</a>");
			
		}
  		
	});

	$jq('.next-tab, .prev-tab').click(function() { 
		$tabs.tabs('select', $(this).attr("rel"));
		return false;
	});
      
});
</script>

<title>Nominations</title>
</head>
<body >
	<form:form method="post" commandName="trainingRequest">
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
								<div class="headTitle">Nominations and Approvals</div>
								<%-- Content Page starts --%>
								<div class="line">
									<div id="page-wrap">		
										<div id="tabs">
											<ul>        		
								        		<li><a href="#fragment-1">Nominations</a></li>
								        		<li><a href="#fragment-2">Approved By MDB</a></li>
								        		<li><a href="#fragment-3">Approved By AD</a></li>
								        		<li><a href="#fragment-4">Final List</a></li>
								        	</ul>
									     	<div id="fragment-1" class="ui-tabs-panel">
												<div class="line" id="advanceList">
												<div class="line">
												<div   class="quarter bold leftmar">Course Name<span class="failure">*</span></div>
												<div class="quarter">
													<form:select path="durationId" id="courseId1" cssClass="formSelect" onchange="javascript:gethrdgNominationList();" >
															<form:option value="">Select</form:option>
															<form:options items="${sessionScope.trainingRequestCourseList}" itemValue="id" itemLabel="course"/>
													</form:select>
												</div>
												<div   class="quarter bold leftmar">Board Name<span class="failure">*</span></div>
												<div class="quarter">
													<form:select path="boardId" id="boardId1" cssClass="formSelect" onchange="javascript:gethrdgNominationList();">
															<form:option value="">Select</form:option>
															<form:options items="${sessionScope.boardList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
												
												</div> 
												
									
												<div class="line">
												<div   class="quarter bold leftmar">Approved/Rejected<span class="failure">*</span></div>
												<div class="quarter">
													<form:select path="selectStatus" id="selectStatus1" cssClass="formSelect" >
															<form:option value="21">Approved</form:option>
															<form:option value="20">Rejected</form:option>
															
													</form:select>
												</div>
												
												
												</div> 
												<div class="expbutton" style="float:right"><a href="javascript:saveNominations('nomination');"><span>Save</span></a></div>
												<div class="expbutton" style="float:right"><a href="javascript:reportNominations('nomination');"><span>Note for Board</span></a></div>
												
													<div id="nomination"><jsp:include page="TrainingNominationRequestList.jsp" /></div>	
												</div>
													
											</div>
								     		<div id="fragment-2" class="ui-tabs-panel ui-tabs-hide">
								     		    <div class="line" id="advanceList">
												<div class="line">
												<div   class="quarter bold leftmar">Course Name<span class="failure">*</span></div>
												<div class="quarter">
													<form:select path="courseId" id="courseId2" cssClass="formSelect" onchange="javascript:getMdbSelectionList();" >
															<form:option value="">Select</form:option>
															<form:options items="${sessionScope.trainingRequestCourseList}" itemValue="id" itemLabel="course"/>
													</form:select>
												</div>
												<div   class="quarter bold leftmar">Board Name<span class="failure">*</span></div>
												<div class="quarter">
													<form:select path="boardId" id="boardId2" cssClass="formSelect" onchange="javascript:getMdbSelectionList();">
															<form:option value="">Select</form:option>
															<form:options items="${sessionScope.boardList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
												
												</div> 
												<div class="line">
													<div class="quarter bold leftmar">Serial Number<span class="failure">*</span></div>
													<div class="quarter">
															<form:select path="letterFormatId2" id="letterFormatId2" cssClass="formSelect" onchange="javascript:getCirculationIonLists('2');" onmouseover="setSelectWidth('#letterFormatId2');">
															<form:option value="">Select</form:option>
															<form:options items="${seriesMstList}" itemValue="id" itemLabel="serialNum"/>
												
													</form:select>
													</div>
												</div>
												<div class="line">
													<div class="quarter bold leftmar">Letter Number<span class="failure">*</span></div>
													<div class="quarter" >
														<form:select path="ionId2" id="ionId2" cssClass="formSelect" onmouseover="setSelectWidth('#ionId2');">
														<form:option value="">Select</form:option>
														<form:options items="${ionMstList2}" itemValue="id" itemLabel="letterNumber"/>
												
														</form:select>
													</div>
												</div>
												<div class="line">
													<div   class="quarter bold leftmar">Approved/Rejected<span class="failure">*</span></div>
													<div class="quarter">
													<form:select path="selectStatus" id="selectStatus2" cssClass="formSelect" >
															<form:option value="27">Approved</form:option>
															<form:option value="26">Rejected</form:option>
															
													</form:select>
													</div>
												</div> 
												<div class="line" id="result2">
													
												</div> 
												
												<div id="boardSelection">
													<jsp:include page="TrainingNominationBoardSelectionList.jsp" />
												</div>
													<div class="expbutton" style="float:right"><a href="javascript:saveNominations('boardSelection');"><span>Save</span></a></div>
													<div class="expbutton" style="float:right"><a href="javascript:reportNominations('boardSelection');"><span>Nominations Approved By Board --Report</span></a></div>								        	</div>
								        	</div>
								         	<div id="fragment-3" class="ui-tabs-panel ui-tabs-hide">
								     		    <div class="line" id="advanceList">
												<div class="line">
												<div   class="quarter bold leftmar">Course Name<span class="failure">*</span></div>
												<div class="quarter">
													<form:select path="courseId" id="courseId3" cssClass="formSelect" onchange="getLetterFormatListForCourse();getADSelectionList();" onmouseover="setSelectWidth('#courseId3')">
															<form:option value="">Select</form:option>
															<form:options items="${sessionScope.trainingRequestCourseList}" itemValue="id" itemLabel="course"/>
													</form:select>
												</div>
												
												
												</div> 
												
												<div class="line" id="result3">
													
									
												</div>
												
												
												<div class="line">
												<div   class="quarter bold leftmar">Approved/Rejected<span class="failure">*</span></div>
												<div class="quarter">
													<form:select path="selectStatus" id="selectStatus3" cssClass="formSelect" >
															<form:option value="33">Approved</form:option>
															<form:option value="32">Rejected</form:option>
															
													</form:select>
												</div>
												</div> 
												
												<div class="line" id="result31">
													
									
												</div>
												
												<div class="line" id="ADSelection">
													<jsp:include page="TrainingNominationADSelectionList.jsp" />
												</div>
													<div class="expbutton" style="float:right"><a href="javascript:saveNominations('ADSelection');"><span>Save</span></a></div>
													<div class="expbutton" style="float:right"><a href="javascript:reportNominations('ADSelection');"><span>Sanction of Registration Fee -- Report</span></a></div>
								        	</div>
								        	</div>
								         	<div id="fragment-4" class="ui-tabs-panel ui-tabs-hide">
												<div class="line" id="advanceList">
												<div class="line">
												<div   class="quarter bold leftmar">Course Name<span class="failure">*</span></div>
												<div class="quarter">
													<form:select path="courseId" id="courseId4" cssClass="formSelect" onchange="javascript:getFinalSelectionList();" >
															<form:option value="">Select</form:option>
															<form:options items="${sessionScope.trainingRequestCourseList1}" itemValue="id" itemLabel="course"/>
													</form:select>
												</div>
												
												
												</div> 
												
												<div class="line">
													<div class="quarter bold leftmar">Serial Number<span class="failure">*</span></div>
													<div class="quarter">
															<form:select path="letterFormatId4" id="letterFormatId4" cssClass="formSelect" onchange="javascript:getCirculationIonLists('4');" onmouseover="setSelectWidth('#letterFormatId4');">
															<form:option value="">Select</form:option>
															<form:options items="${seriesMstList}" itemValue="id" itemLabel="serialNum"/>
												
													</form:select>
													</div>
												</div>
												<div class="line">
													<div class="quarter bold leftmar">Letter Number<span class="failure">*</span></div>
													<div class="quarter" >
														<form:select path="ionId4" id="ionId4" cssClass="formSelect" onmouseover="setSelectWidth('#ionId4');">
														<form:option value="">Select</form:option>
														<form:options items="${ionMstList4}" itemValue="id" itemLabel="letterNumber"/>
												
														</form:select>
													</div>
									           </div>
												
												
												<div class="line">
												<div   class="quarter bold leftmar">Attended/not Attended<span class="failure">*</span></div>
												<div class="quarter">
													<form:select path="selectStatus" id="selectStatus4" cssClass="formSelect" >
															<form:option value="50">Attended</form:option>
															<form:option value="49">Not Attended</form:option>
															
													</form:select>
												</div>
												</div>
												<div class="line" id="result4">
													
									
												</div>
												 
												<div class="line" id="FinalAlert">
													<jsp:include page="TrainingNominationFinalSelectionList.jsp" />
												</div>
													<div class="expbutton" style="float:right"><a href="javascript:saveNominations('FinalAlert');"><span>Save</span></a></div>
													<div class="expbutton" style="float:right"><a href="javascript:reportNominations('FinalAlert');"><span>Confirmation of Participation-- Report</span></a></div>
													
								        	</div>
								       	</div>
									</div>
								</div>					
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
			<form:hidden path="message"/>
			<form:hidden path="historyID"/>
			<form:hidden path="requestId"/>
		<form:hidden path="statusMsg"/>
		<form:hidden path="back"/>
		<form:hidden path="letterFormatId" id="letterFormatId"/>
		<form:hidden path="ionId" id="ionId"/>
		<form:hidden path="ionFlag" id="ionFlag"/>
		
		
			
		</form:form>
	</body>
</html>
<!-- End:HrdgTrainingNominationDetails.jsp-->