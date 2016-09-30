<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin:CreateQualification.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<script language="javascript" src="./script/RegExpValidate.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<fieldset><legend><strong><font color='green'>Qualification Details</font></strong></legend>
    <script>
		clearQualification();
	</script>
 <div>
		 <div class="line">
		 	<div class="quarter">SFID</div>
			<div class="quarter">${changeSfid}</div>
			<div class="quarter">Name</div>
			<div class="quarter">${changeSfidName}</div>
		</div>
		<div class="line">
			<div class="quarter">Degree<span class="mandatory">*</span></div>
			<div class="quarter">
				<form:select path="degree" id="degree"   cssClass="formSelect"  >
		 		 	<form:option value="Select">Select</form:option>
		 		 	 <%--<form:options items="${degreeList}" itemValue="id" itemLabel="name"></form:options>--%>
		 		 	   <c:forEach items="${degreeList}" var="degreeList">
		 		 	     <form:option value="${degreeList.id}">${degreeList.name} (${degreeList.description})</form:option>
		 		 	  </c:forEach>  
		 		 </form:select>
			</div>
			<div class="quarter">Qualification<span class="mandatory">*</span></div>
				<div class="quarter">
				    <form:select id="qualification" path="qualification" cssClass="formSelect" >
                       <form:option value="Select">Select</form:option>
                            <c:forEach items="${qualList}" var="qualficationList">
                                <form:option value="${qualficationList.id}">${qualficationList.name} (${qualficationList.shortForm})</form:option>        	
                            </c:forEach>   	
                     </form:select>	
			</div>
	 	</div>  
	 			
		  <div class="line">
		  	<div class="quarter">Discipline<span class="mandatory">*</span></div>
	 			<div class="quarter">
				    <%--<form:select path="discipline" id="discipline"  cssClass="formSelect"  onmouseover="setSelectWidth('#discipline')">--%>
                       <form:select path="discipline" id="discipline"  cssClass="formSelect" >
                       <form:option value="0">Select</form:option>
                           <c:forEach items="${desciplineList}" var="desciplineList">
                                <form:option value="${desciplineList.id}">${desciplineList.name}</form:option>          	
                            </c:forEach>   	    	
                     </form:select>	   
		   	</div> 
		  	<div class="quarter">Specialization</div>
			<div class="quarter"><form:input path="specilisation" id="specilisation" maxlength="150" cssClass="emptyTextBox" onkeypress="return checkChar(event);"></form:input></div>
				
		 </div>  
		 <div class="line">
		 	<div class="quarter">University<span class="mandatory">*</span></div>
			<div class="quarter"><form:input path="university" id="university" maxlength="150" cssClass="emptyTextBox" onkeypress="return isAlphabetExp(event);"></form:input></div>
		 	<div class="quarter">Year of Passing<span class="mandatory">*</span></div>
			<div class="quarter">
	 		   <form:select path="year" id="year"  cssClass="formSelect" >
	 		   		<form:option value="Select">Select</form:option>
	 		   		<form:options items="${yearList}" itemValue="id" itemLabel="name"></form:options>
	 		   </form:select>
			     					  
			</div>
		</div>   
		<div class="line">
			<div class="quarter">Marks Type</div>
			<div class="quarter">
				<form:select path="marks" id="marks"  cssClass="formSelect" >
					<form:option value="0">Select</form:option>
					<form:option value="17">CGPS</form:option>
					<form:option value="18">PERCENTAGE</form:option>
					<form:option value="19">MARKS</form:option>
				</form:select>
			</div>
			<div class="quarter">Value</div>
			<div class="quarter"><form:input path="totalPercentage" id="totalPercentage"  maxlength="30" cssClass="emptyTextBox" onkeypress="javascript:return isNumberExp(event);"></form:input></div>
			
		</div>  
		<div class="line">
			<div class="quarter">Division</div>
			<div class="quarter"><form:input path="divisionId" id="divisionId" maxlength="100" cssClass="emptyTextBox" onkeypress="return isAlphabetExp(event);"></form:input></div>
			<div class="quarter">Grade</div>
			<div class="quarter"><form:input path="grade" id="grade" maxlength="25" cssClass="emptyTextBox" onkeypress="javascript:checkSpecialChar(e);"></form:input></div>
			
		</div>
		<div class="line">
			<div class="quarter">Qualification Attained While DRDO<span class="mandatory">*</span></div>
			<div class="quarter">		 
			<input type="radio" name="qualAttainedDRDO" id="qualAttainedYES" value="Yes" onclick="javascript:enableRTIncentive();"/>Yes 
			<input type="radio" name="qualAttainedDRDO" id="qualAttainedNO"  value="No" checked="checked" onclick="javascript:enableRTIncentive();"/>No	  
		</div>
	
			<div id="rtscheme" class="displayvalues">
				<div class="quarter">R & T Scheme <span class="mandatory">*</span></div>
				<div class="quarter">
					<form:radiobutton path="sponsored" value="1" label="Yes" onclick="javascript:enableInsentives();"></form:radiobutton>
					<form:radiobutton path="sponsored" value="0" label="No" checked="checked" onclick="javascript:enableInsentives();"></form:radiobutton>
				</div>
			</div>
		</div>
		<div id="Incentive" class="displayvalues">
			<div class="line">
				<div class="quarter">Incentive</div>
				<div class="quarter"><form:input path="incentive" id="incentive" maxlength="10" onkeypress="return checkFloat(event,'incentive');" cssClass="emptyTextBox"></form:input></div>
			</div>
		</div>
		<%--<div class="line">
			<div class="quarter">Amount</div>
			<div class="quarter"><form:input path="amount" id="amount"></form:input></div>	
		</div>  --%>   
		<c:if test="${message ne 'viewQualificationDetails'}">
		 <div >
	          <div class="float">
				<div class="appbutton" style="float:right"><a href="javascript:clearQualification();" style="text-decoration: none">Clear</a></div>
				<div class="appbutton" style="float:right"><a href="javascript:manageQualification();" style="text-decoration: none">Submit</a></div>
			  </div>
	         
		</div>	
	   </c:if>	
</div>
</fieldset>	
	

<div class="height"><hr/></div>

<!-- End:CreateQualification.jsp -->