<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin : CreateAddress.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/aa.js"></script>

	<div id="reqResult"></div>
	<fieldset><legend><strong><font color='green'>Address Details</font></strong></legend>
		<div>
			<div class="line">
				<div class="quarter">SFID</div>
				<div class="quarter">${sfid}</div>
				<div class="quarter">Address Type<span class="mandatory">*</span></div>
				<div class="quarter">
				<spring:bind path="command">
					<form:select path="command.addressTypeId"  id="addressTypeId" cssClass="formSelect" onmouseover="setSelectWidth('#addressTypeId')" >
						<form:option value="Select">Select</form:option>
						<form:options items="${addressTypeList}" itemValue="id" itemLabel="name"></form:options>
					 </form:select>
			   </spring:bind>		
			   </div>
			</div>
			
			<div class="line">	
				<div class="quarter">C/o</div>
				<div class="quarter">
					<spring:bind path="command">
					<form:input path="command.careOfAddress" id="careOfAddress" maxlength="100" onkeypress="return checkChar(event);" />
					</spring:bind>
				</div>		
				<div class="quarter">Address Line1<span class="mandatory">*</span></div>
				<div class="quarter">
				<spring:bind path="command">
					<form:input path="command.address1" id="address1" maxlength="200"/>
				</spring:bind>
				</div> 
				
			</div>
			<div class="line">
				<div class="quarter">Address Line2</div>
				<div class="quarter">
				<spring:bind path="command">
					<form:input path="command.address2" id="address2" maxlength="200"/>
				</spring:bind>
				</div>
				<div class="quarter">Address Line3</div>
				<div class="quarter">
				<spring:bind path="command">
					<form:input path="command.address3" id="address3" maxlength="200"/>
				</spring:bind>
				</div>  	
			</div>
			<div class="line">
				<div class="quarter">City<span class="mandatory">*</span></div>
				<div class="quarter">
				<spring:bind path="command">
					<form:input path="command.city" id="city" maxlength="100" onkeypress="return checkChar(event);"/>
				</spring:bind>
				</div>
				<div class="quarter">State<span class="mandatory">*</span></div>
				<div class="quarter">
				<spring:bind path="command">
					<form:select path="command.state" id="state"  cssClass="formSelect" onmouseover="setSelectWidth('#state')" onchange="javascript:getDistrictList('state','district')">
						<form:option value="0">Select</form:option>
						<form:options items="${stateList}" itemValue="id" itemLabel="name"/>
					</form:select>
				</spring:bind>
				</div>				
			</div>
			<div class="line">
				<div class="quarter">District<span class="mandatory">*</span></div>
				<div class="quarter">
				<spring:bind path="command">
					<form:select path="command.district" id="district"  cssClass="formSelect" onmouseover="setSelectWidth('#district')" >
						<form:option value="0">Select</form:option>
				
					</form:select>
				</spring:bind>
				</div>
				<div class="quarter">Pincode</div>
				<div class="quarter">
				<spring:bind path="command">
					<form:input path="command.pincode" id="pincode" maxlength="6" onkeypress="return checkPinCode(event);"/>
				</spring:bind>
				</div>				
			</div>
			<div class="line">
				<div class="quarter">Phone</div>
				<div class="quarter">
				<spring:bind path="command">
					<form:input path="command.phone" id="phone" maxlength="15" onkeypress="return checkInt(event);"/>
				</spring:bind>
				</div>
				<div class="quarter">Mobile</div>
				<div class="quarter">
				<spring:bind path="command">
					<form:input path="command.mobile" id="mobile" maxlength="15" onkeypress="return checkInt(event);"/>
				</spring:bind>
				</div>				
			</div>
			<div class="line">
				<div class="quarter">Email</div>
				<div class="quarter">
				<spring:bind path="command">
					<form:input path="command.email" id="email" maxlength="100"/>
				</spring:bind>
				</div>
				<div class="quarter">Nearest Railway Station<span class="mandatory">*</span></div>
				<div class="quarter">
				<spring:bind path="command">
					<form:input path="command.nearestRyStation" id="nearestRyStation" maxlength="200" onkeypress="return checkChar(event);"/>
				</spring:bind>
				</div>				
			</div>
			<div class="line">
				<div class="quarter">Nearest Airport</div>
				<div class="quarter">
				<spring:bind path="command">
					<form:input path="command.nearestAirport" id="nearestAirport" maxlength="200" onkeypress="return checkChar(event);"/>
				</spring:bind>
				</div>
				<div style="float:left"  style="display: none" id="dispensary" class="half">
					<div class="half">New Dispensary No Allocation</div>
					<div class="half">
					<spring:bind path="command">
						<form:checkbox path="command.dispensaryNumber" value = "Y" id="dispensaryNumber"/> 
					</spring:bind>
					</div>
				</div>				
			</div>
			<div id="submitRequest" class="float">
			   	<div class="appbutton" style="float:right"><a class="quarterbutton appbutton" href="javascript:clearAddress('${address.type}')">Clear</a></div>
				<div style="float:right"><a class="quarterbutton appbutton" href="javascript:manageAddress('${message}');">Submit</a></div>
			</div>
			</div>
	</fieldset>
		
	<div class="height"><hr/></div>
	<script>
	districtJSONList = <%= (net.sf.json.JSONArray)session.getAttribute("districtJSONList") %>;
	setDistrictList(${command.district});
	validation = <%= (net.sf.json.JSONObject)session.getAttribute("addressBean") %>;
	</script>

<!-- End : CreateAddress.jsp -->