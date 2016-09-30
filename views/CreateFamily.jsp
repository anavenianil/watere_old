<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:createFamily.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

<div>
 <div id="reqResult"></div>
<fieldset><legend><strong><font color='green'>Family Details</font></strong></legend>
	<div class="line">
		<c:if test="${message eq 'familyRequest'}">
			<div class="quarter" >Employee ID</div>
			<div class="quarter">${family.sfid }</div>
		</c:if>
		<c:if test="${message ne 'familyRequest'}">
		<div class="quarter" >Employee ID</div>
		
		<div class="quarter">${changeSfid }</div>
		
			<div class="quarter">Name</div>
			<div class="quarter">${changeSfidName}</div>
		</c:if>
	</div>
	<div class="line">
		<div class="quarter">Family Member Name<span class="mandatory">*</span></div>
		<!--<div class="quarter"> <input type="text" name="name" maxlength="100" id="name" /> </div> -->				
		<div class="quarter"> <input type="text" name="name" maxlength="100" id="name" onkeypress="return isAlphabetExp(event);"/> </div>  			
		<div class="quarter">Relationship<span class="mandatory">*</span></div>
		<div class="quarter">
		 
			<select name="relationId" id="relation" class="formSelect" onchange="javascript:setGender();">
				<option value="select">Select</option>
				<c:forEach var="relation" items="${RelationList}">
					 <option value="${relation.id}">${relation.name}</option>
				</c:forEach> 
			</select>
		 
		</div>
	</div>
	<div class="line">
	<div class="quarter">Gender<span class="mandatory">*</span></div>
		<div class="quarter">		 
			<input type="radio" name="gender" id="genderm" value="M"/>Male
			<input type="radio" name="gender" id="genderf" value="F"/>Female		 
		</div>
	</div>
	<div class="line">		
		<div class="quarter">Date Of Birth/Age<span class="mandatory">*</span></div>
		<div class="quarter">
			<select id="dobselection" class="formSelect" onchange="javascript:setFamilyDetails()">
				<option value="select">Select</option>
				<option value="dob">Date Of Birth</option>
				<option value="age">Age</option>
			</select>
		</div>
		<div id="familydobirth" class="displayvalues">
		<div class="quarter">Date of Birth<span class="mandatory">*</span></div>
		<div class="quarter" > <input type="text" name="dob" id="familydob" readonly=readonly/> 
			<img  src="./images/calendar.gif"   id="date_start_trigger4" />	
						<script type="text/javascript">
							Calendar.setup({inputField :"familydob",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger4",singleClick : true,step : 1});
						</script>
		</div>
		</div>
		<div id="familyagediv" class="displayvalues">
		<div class="quarter">Age<span class="mandatory">*</span></div>
		<div class="quarter"> <input type="text" name="age" id="familyage" maxlength="2" onkeypress="return checkPinCode(event);"/> </div>
		</div>
	</div>
	<div class="line">
		<div class="quarter"  style="display: none;" >CGHS Dependent<span class="mandatory">*</span></div>
		<div class="quarter"   style="display: none;" ><input type="radio" id="cghsApplicable" name="cghsFacility" value="Y" onchange="javascript:onChangeCGHS('Y')"/>Yes
							<input type="radio" id="cghsNotApplicable" name="cghsFacility" value="N" checked="checked" onchange="javascript:onChangeCGHS('N')"/>No
		</div>
							
		<div class="quarter">Annual Leave Dependent<span class="mandatory">*</span></div>
		<div class="quarter"><input type="radio" id="ltcApplicable" name="ltcFacility" value="Y"  checked="checked"/>Yes
							<input type="radio" id="ltcNotApplicable" name="ltcFacility" value="N" />No
		</div>
	</div>
	
		<%-- <fieldset id="notMandatory"><legend>CGHS</legend>
			<div class="line">
			    <div class="quarter">CGHS Place of issue</div>
				     <div class="quarter">
				                <input type="text" name="placeOfIssue" id="placeOfIssue" maxlength="15" onkeypress="return checkChar(event);"/>
				     </div>	
				<div class="quarter">CGHS Beneficiary Number</div>
					<div class="quarter">
									<input type="text" id="beneficiary" name="beneficiary" maxlength="15"/>
					</div>
			</div>
			<div class="line">
			    <div class="quarter">Validity of CGHS From</div>
				     <div class="quarter"><input  name="validFrom" id="validFrom" class="dateClass" readonly="readonly"/>
				          <img  src="./images/calendar.gif"   id="date_start_trigger8" />	
								<script type="text/javascript">
									Calendar.setup({inputField :"validFrom",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger8",singleClick : true,step : 1});
								</script>
				
				</div>
				<div class="quarter">To Date</div>
				     <div class="quarter"><input  name="validTo" id="validTo" class="dateClass" readonly="readonly"/>
				          <img  src="./images/calendar.gif"   id="date_start_trigger9" />	
								<script type="text/javascript">
									Calendar.setup({inputField :"validTo",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger9",singleClick : true,step : 1});
								</script>
				
				    </div>		
			</div>
		</fieldset>--%>
	
	
	<div style="display:none;"> <!--added by bkr 30/3/2016 only div tag for hidden the fields  -->

		<fieldset id="mandatory" style="display:none;"><legend>CGHS</legend>
			<div class="line">
			    <div class="quarter">CGHS Place of issue<span class="mandatory">*</span></div>
				     <div class="quarter">
				                <input type="text" name="placeOfIssue" id="placeOfIssue" maxlength="15" onkeypress="return checkChar(event);"/>
				     </div>	
				<div class="quarter">CGHS Beneficiary Number<span class="mandatory">*</span></div>
					<div class="quarter">
									<input type="text" id="beneficiary" name="beneficiary" maxlength="15" />
					</div>
			</div>
			<div class="line">
			    <div class="quarter">Validity of CGHS From<span class="mandatory">*</span></div>
				     <div class="quarter"><input  name="validFrom" id="validFrom" class="dateClass" readonly="readonly"/>
				          <img  src="./images/calendar.gif"   id="date_start_trigger8" />	
								<script type="text/javascript">
									Calendar.setup({inputField :"validFrom",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger8",singleClick : true,step : 1});
								</script>
				
				</div>
				<div class="quarter">To Date<span class="mandatory">*</span></div>
				     <div class="quarter"><input  name="validTo" id="validTo" class="dateClass" readonly="readonly"/>
				          <img  src="./images/calendar.gif"   id="date_start_trigger9" />	
								<script type="text/javascript">
									Calendar.setup({inputField :"validTo",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger9",singleClick : true,step : 1});
								</script>
				
				    </div>		
			</div>
			
			<div class="line">
			    
				<div class="quarter">COPY OF CGHS CARD Details<span class="mandatory">*</span></div>
					<div class="quarter">
									<!--  <input type="file" id="cghscard" name="cghscard" maxlength="15" />-->
									<form:input path="cghscardfile" type="file" id="cghscardfile" />	
					</div>
			</div>
		</fieldset>
		
	</div>	
	
	<div class="line">
		<div id="dependentdate" class="displayvalues">
			<div class="quarter">Dependent From</div>
			<div class="quarter"> <input type="text" name="dependentFrom" id="dependentFrom" readonly="true"/> 
				<img  src="./images/calendar.gif"   id="date_start_trigger3" />	
							<script type="text/javascript">
								Calendar.setup({inputField :"dependentFrom",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger3",singleClick : true,step : 1});
							</script>
			</div>
		</div>
	</div>
	<div class="line">
		<div class="quarter">Marital Status<span class="mandatory">*</span></div>
		<div class="quarter">
		 
			<select name="maritalstatus" id="maritalstatus" class="formSelect">
				<option value="select">Select</option>
				<c:forEach var="maritalstatus" items="${maritalStatusList}">
					 <option value="${maritalstatus.id}">${maritalstatus.name}</option>
				</c:forEach> 
			</select>
		 
		</div>
		<div class="quarter">Address Type</div>
		<div class="quarter">				 
					<select name="addressTypeId" class="formSelect" id="addressTypeId" onchange="javascript:setFamilyDetails();">
						<option value="select">Select</option>
						<c:forEach var="address" items="${AddressList}">
							 <option value="${address.addressTypeId}">${address.addressType}</option>
						</c:forEach> 
						<!-- commented by bkr 30/03/2016 -->
						<!-- <option value="100">Other</option> -->
					 </select>			    		
		</div>		
	</div>
	<div id="addressdiv" style="display:none"> 
	<div class="line">
		<div class="quarter">Address Line1<span class="mandatory">*</span></div>
		<div class="quarter"> <input type="text" name="address1" id="address1" maxlength="200"/> </div>
		<div class="quarter">Address Line2</div>
		<div class="quarter"> <input type="text" name="address2" id="address2" maxlength="200"/> </div>		
	</div>
	<div class="line">
	<div class="quarter">Address Line3</div>
	<div class="quarter"> <input type="text" name="address3" id="address3" maxlength="200"/> </div>
	<div class="quarter">City<span class="mandatory">*</span></div>
		<div class="quarter">
		 <input type="text" name="city" id="city" maxlength="15" onkeypress="return checkChar(event);"/> </div>						
	</div>
	<div class="line">
	<div class="quarter">State</div>
	<div class="quarter">
		 
			<select name="state" id="state" onchange="javascript:getDistrictList('state','district');" class="formSelect">
				<option value="0">Select</option>
				<c:forEach var="state" items="${stateList}">
					 <option value="${state.id}">${state.name}</option>
				</c:forEach> 
			</select>
		 
	</div>
	<div class="quarter">District</div>
	<div class="quarter">		 
			<select name="district" id="district" class="formSelect">
				<option value="0">Select</option>
				<%--<c:forEach var="district" items="${districtJSONList}">
					 <option value="${district.id}">${district.name}</option>
				</c:forEach> --%>
			</select>
		 
		</div>
					
	</div>
	<div id="familyPin" class="line">
			<div class="quarter">Pin Code</div>
			<div class="quarter"><input type="text" name="pincode" id="pincode" maxlength="6" onkeypress="return checkPinCode(event);"/></div>	
	</div>
	</div>
	<div class="line">		
			<div class="quarter">Blood Group</div>
			<div class="quarter">				 
					<select name="bloodGroup" id="bloodGroup" class="formSelect">
						<option value="0">Select</option>
						<c:forEach var="blood" items="${bloodGroupList}">
							 <option value="${blood.id}">${blood.name}</option>
						</c:forEach>
					</select>			 
			</div>
			<div class="quarter">Employed<span class="mandatory">*</span></div>
			<div class="quarter">
				<input type="radio" name="employeed" id="employeedYes" value="Y" onclick="javascript:setFamilyDetails();"/>Yes
				<input type="radio" name="employeed" id="employeedNo" value="N" checked="checked" onclick="javascript:setFamilyDetails();"/>No
		</div>
	</div>
	<div class="line">		
		<div id="emporgtype" class="displayvalues">
			<div class="quarter">Employment Type<span class="mandatory">*</span></div>
			<div class="quarter">
				 
					<select name="employeedType" id="employeedType" class="formSelect">
						<option value="select">Select</option>
						<option value="21">Govt</option>
						<option value="20">Private</option>
					</select>
			</div>
			<div style="display:show " id="earningSalary">
			<div class="quarter">Earning Salary<span class="mandatory">*</span></div>
			<div class="quarter"><input type = "text" name="earningMoney" id="earningMoney" onkeypress="return checkFloat(event,'earningMoney')";/> </div></div>
		</div>	
	</div>
	<div class="line">
		<div class="quarter">Residing With</div>
			<div class="quarter">
				<input type="radio" name="residingWith" id="yes" value="Y" checked="checked"/>Yes
				<input type="radio" name="residingWith" id="no" value="N" />No
		</div>
		<div class="quarter">Contact Number</div>
		<div class="quarter"> <input type="text" name="contactNumber" id="contactNumber" maxlength="15" class="formSelect" onkeypress="return checkPinCode(event);"/> </div>
		
	</div>
	<div class="line">
	<div class="quarter" style="display: none">Declare Date</div>
		<div class="quarter"  style="display: none"><input  name="declareDate" id="declareDate" class="dateClass" readonly="readonly"/>
		<img  src="./images/calendar.gif"   id="date_start_trigger7" />	
						<script type="text/javascript">
							Calendar.setup({inputField :"declareDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger7",singleClick : true,step : 1});
						</script>
		
		</div>
		<div class="quarter" hidden>PH Type<span class="mandatory">*</span></div>
			<div class="quarter" hidden>
							<input type="radio" id="phtypeFlagYes" name="phtypeFlag" value="Y" onclick="javascript:setFamilyDetails();"/>Yes
							<input type="radio" id="phtypeFlagNo" name="phtypeFlag" value="N" checked="checked" onclick="javascript:setFamilyDetails();"/>No
			</div>
	</div>
	
	<div class="line displayvalues" id="disabilityType">
		<div class="quarter">Disability Type<span class="mandatory">*</span></div>
		<div class="quarter"><select name="disabilityId" id="disabilityId">
									<option value="Select">Select</option>
								<c:forEach var="disability" items="${sessionScope.disabilityList}">
									 <option value="${disability.id}">${disability.name}</option>
								</c:forEach>
							</select>
		</div>
	</div>
	<div class="line">
			<div id="adopt" style="display: none">
		<div class="quarter">Adopted<span class="mandatory">*</span></div>
		<div class="quarter"> 
							<input type="radio" id="adoptedYes" name="adopted" value="Y" onclick="javascript:setFamilyDetails();"/>Yes
							<input type="radio" id="adoptedNo" name="adopted" value="N" checked="checked" onclick="javascript:setFamilyDetails();"/>No
		 </div>
		 </div>
		<div id="adoptedfrom" class="displayvalues">
			<div class="quarter">Adopted Date</div>
			<div class="quarter"> <input  type="text" name="adoptedDate" id="adoptedDate" class="dateClass" readonly=readonly/> 
				<img  src="./images/calendar.gif"   id="date_start_trigger6" />	
							<script type="text/javascript">
								Calendar.setup({inputField :"adoptedDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"date_start_trigger6",singleClick : true,step : 1});
							</script>
			</div>
		</div>
	</div>
	
    <div id="button" class="float">
				<div class="appbutton" style="float:right"><a class="quarterbutton" href="javascript:clearFamily()">Clear</a></div>
				<div id="familySubmit" class="appbutton submitbutton"><a class="quarterbutton" href="javascript:manageFamily('${message}')">Submit</a></div>
    </div>
    
    <div><b style="color:red">NOTE :</b><span style="color:blue"> Please Enter SELF Details Also</span></div>

<script>

JsonFamilyList= <%= (net.sf.json.JSONArray)session.getAttribute("JsonEmpFamilyList") %>;
districtJSONList = <%= (net.sf.json.JSONArray)session.getAttribute("districtJSONList") %>
setFamilyDetails(JsonFamilyList,'${family.id}');
</script>
</fieldset>
<div class="height"><hr/></div>
</div>

<!-- End:createFamily.jsp -->