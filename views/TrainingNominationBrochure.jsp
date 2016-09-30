<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--Begin: TrainingNominationBrochure.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<script type="text/javascript" src="script/trainingscript.js"></script>

<fieldset >
<legend ><strong><font color='green'>Course Details</font></strong></legend>
									
									<div class="line">
									<div  class="quarter bold leftmar">Brochure</div>
									<div  class="quarter "><a class="expbutton" href="javascript:showDatabaseFile('${trainingRequest.brochure}');"><span>Download Brochure</span></a></div>
									<div  class="quarter "><a class="expbutton" href="javascript:reportION('${trainingRequest.durationId}');"><span>Download I.O.N</span></a></div>	
										
									</div>
									<div class="line">
									<div  class="quarter bold leftmar">Circulated To</div>
									<div  class="threefourth width:59%">	${trainingRequest.dept}</div>
									</div>	
									</fieldset>
												
<script>

$jq('#nomRequestStatus').val('${trainingRequest.nomRequestStatus}');
jsonLastAttendedCourse=<%=(net.sf.json.JSONArray)session.getAttribute("jsonLastAttendedCourse") %>;
setLastAttendedCourse(jsonLastAttendedCourse);


</script>
<!--End: TrainingNominationBrochure.jsp -->