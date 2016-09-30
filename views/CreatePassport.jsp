<!-- begin : CreatePassport.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<fieldset><legend><strong><font color='green'> Passport Details</font></strong></legend>
		<div class="line">
			<div class="line">
				<div class="quarter">SFID</div>
				<div class="quarter">${changeSfid}</div>
				<div class="quarter">Name</div>
				<div class="quarter">${changeSfidName}</div>
				
			</div>
			<div class="line">
					<div class="quarter">Passport Type
						<c:if test="${message ne 'ViewPassportDetails'}">
							<span class="mandatory">*</span>
						</c:if>
					</div>
					<div class="quarter">
						<form:select path="passportType" id="passportType"  cssClass="formSelect" >
					 		<form:option value="Select">Select</form:option>
					 		<form:option value="PERSONAL">PERSONAL</form:option>
					 		<form:option value="OFFICIAL">OFFICIAL</form:option>
				   		</form:select>
				   	</div>


					<div class="quarter">Passport for
						<c:if test="${message ne 'ViewPassportDetails'}">
							<span class="mandatory">*</span>
						</c:if>
					</div>
					<div class="quarter">
						<form:select path="passPortFor"  id="passPortFor" onchange="javascript:enableFamilyList();"  cssClass="formSelect" >
				 			<form:option value="Select">Select</form:option>
				 			<form:option value="SELF">SELF</form:option>
				 			<form:option value="FAMILY">FAMILY</form:option>
			   			</form:select>
			   		</div>
				
			</div>
			<div class="line">
				<div class="quarter">Family Member
					
				</div>
				<div class="quarter">
					<form:select path="familyMember" id="familyMember" disabled="true"  cssClass="formSelect" onmouseover="setSelectWidth('#familyMember')">
				 		<form:option value="Select">Select</form:option>
				 		<form:options items="${FamilyList}" itemValue="id" itemLabel="name"></form:options>
			   		</form:select>
			   </div>


				<div class="quarter">Passport Number
					<c:if test="${message ne 'ViewPassportDetails'}">
						<span class="mandatory">*</span>
					</c:if>
				</div>
				<div class="quarter"><form:input path="passportNumber" id="PassportNumber" maxlength="25" onkeypress="return checkInt(event);"/></div>   
				
			</div>
			<div class="line">
				<div class="quarter">Issued Place<span class="mandatory">*</span>
					<c:if test="${message ne 'ViewPassportDetails'}">
						
					</c:if>
				</div>
				<div class="quarter"><form:input path="issuePlace" id="issuePlace" maxlength="100" onkeypress="return checkChar(event);"/></div>


				<div class="quarter">Valid Upto Date<span class="mandatory">*</span>
					<c:if test="${message ne 'ViewPassportDetails'}">
						
					</c:if>
				</div>
				<div class="quarter"><form:input path="validUpto" id="validUpto" cssClass="dateClass" readonly="true"/>
				
			    	 <img  src="./images/calendar.gif"   id="date_start_trigger1" />	
						<script type="text/javascript">
			           		Calendar.setup({inputField :"validUpto",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger1",singleClick : true,step : 1});
						</script> 
				</div>
				
			</div>
			<div class="line">
				<div class="quarter">Details</div>				
					<div class="quarter"><form:textarea path="details" id="details" rows="3" cols="20" onkeypress="textCounter(this,document.forms[0].counter1,500);" onkeyup="textCounter(this,document.forms[0].counter1,500);"/>
						<input type="text" class="counter" name="counter1" value="500" id="counter1" disabled="disabled"/>
				</div>
				<div class="quarter">Remarks</div>
				<div class="quarter"><form:textarea path="remarks" id="remarks" rows="3" cols="20" onkeypress="textCounter(this,document.forms[0].counter2,500);" onkeyup="textCounter(this,document.forms[0].counter2,500);"/>
					<input type="text" class="counter" name="counter2" value="500" id="counter2" disabled="disabled"/>
				</div>
			</div>
			<c:if test="${message ne 'ViewPassportDetails'}">
			<div class="float">
				<div style="float:right"><a href="javascript:clearPassport();"><div class="appbutton">Clear</div></a></div>
			<div style="float:right"><a href="javascript:managePassport();"><div class="appbutton">Submit</div></a></div>
				</div>
			</c:if>
		</div>
	</fieldset>
		
	<div class="height"><hr/></div>


<!-- End : CreatePassport.jsp -->