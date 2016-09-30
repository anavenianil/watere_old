<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:createAward.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="./script/RegExpValidate.js"></script>				
<fieldset><legend><strong><font color='green'>Award Details</font></strong></legend>
	<div class="line">
		<div class="line">
			<div class="quarter1_4">SFID</div>
			<div class="quarter2_4">${changeSfid}</div>
			<div class="quarter1_4">Name</div>
			<div class="quarter2_4">${changeSfidName}</div>
		</div>
		<div class="line">
				<div class="quarter1_4">Award Category<span class="mandatory">*</span></div>
				<div class="quarter2_4">
					<form:select path="awardCategory"  id="awardCategory"  cssClass="formSelect" onmouseover="setSelectWidth('#awardCategory')">
					 		<form:option value="Select">Select</form:option>
					 		<form:options items="${awardCategoryList}" itemValue="id" itemLabel="name"/>
					 	</form:select>
				</div>
				<div class="quarter1_4">Organisation</div>
				<div class="quarter2_4">
						<form:input path="organization" id="organization" maxlength="50"/>
				</div>
		</div>
		<div class="line">
				<div class="quarter1_4">Year<span class="mandatory">*</span></div>
				<div class="quarter2_4">
						<form:select path="year"  id="year"  cssClass="formSelect" >
					 		<form:option value="Select">Select</form:option>
					 		<form:options items="${yearList}" itemLabel="name" itemValue="id"></form:options>
					 		
					 	</form:select>
				</div>
				<div class="quarter1_4">Cash</div>
				<div class="quarter2_4">
						 <form:input path="cash" id="cash" maxlength="10" onkeypress="return checkInt(event);"/> 
						<!--<form:input path="cash" id="cash" maxlength="10" />-->
				</div>
		</div>
		<div class="line">
				<div class="quarter1_4">Medallion</div>
				<div class="quarter2_4">
						<form:input path="medallion" id="medallion" maxlength="50"/>
				</div>
				<div class="quarter1_4">Citation</div>
				<div class="quarter2_4">
						<form:input path="citation" id="citation" maxlength="50"/>
				</div>
		</div>
		<div class="line">
				<div class="quarter1_4">Certificate</div>
				<div class="quarter2_4">
						<form:input path="certificate" id="certificate" maxlength="50"/>
				</div>
				<div class="quarter1_4">Details of Work</div>
				<div class="quarter2_4">
						<form:textarea path="detailsOfWork" id="detailsOfWork" rows="3" cols="18" onkeypress="textCounter(this,document.forms[0].counter3,500);" onkeyup="textCounter(this,document.forms[0].counter3,500);"/>
						<input type="text" class="counter" name="counter" value="500" id="counter3" disabled="disabled"/>
				</div>
		</div>
		<div class="line">
				<div class="quarter1_4">Award Description</div>
				<div class="quarter2_4">
						<form:textarea path="awardDescription" id="awardDescription" rows="3" cols="18" onkeypress="textCounter(this,document.forms[0].counter1,500);" onkeyup="textCounter(this,document.forms[0].counter1,500);"/>
					<input type="text" class="counter" name="counter" value="500" id="counter1" disabled="disabled"/>
				</div>
				<div class="quarter1_4">Remarks</div>
				<div class="quarter2_4">
						<form:textarea path="remarks" id="remarks" rows="3" cols="18" onkeypress="textCounter(this,document.forms[0].counter2,500);" onkeyup="textCounter(this,document.forms[0].counter2,500);"/>
					<input type="text" class="counter" name="counter" value="500" id="counter2" disabled="disabled"/>
				</div>
		</div>
			<div class="float">
					<div style="float:right" class="appbutton"><a class="quarterbutton" href="javascript:clearAward();">Clear</a></div>
					<div style="float:right" class="appbutton"><a class="quarterbutton" href="javascript:manageAward();">Submit</a></div>
			</div>
		
	</div>
</fieldset>
<div class="height"><hr/></div>
<!-- End:createAward.jsp -->