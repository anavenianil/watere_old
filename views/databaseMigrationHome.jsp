<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:databaseMigrationHome.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/mmgscript.js"></script>
<link href="styles/displaytag.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/incomeTax.js"></script>
<title>DB Migration</title>
</head>

<body>
	<form:form method="post" commandName="incomeTaxMaster">
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
								<div class="headTitle">DataBase Migration</div>
								<div>
									<%-- Content Page starts --%>
								<div class="class" id="result"></div>
								<div class="line">
								<div class="quarter" style="margin-left:8px;">Month<span class='failure'>*</span></div>
											<div class="quarter"><%--<form:select path="sName" id="sName" cssClass="formSelect">
					                                                      <form:option  value="select" label="Select"></form:option>
					                                                      <form:option  value="01-AUG-2012" label="aug-2012"></form:option>
					                                                      <form:option  value="01-SEP-2012" label="sep-2012"></form:option>
					                                                      <form:option  value="01-OCT-2012" label="oct-2012"></form:option>
					                                                      <form:option  value="01-JAN-2011" label="Jan-2011"></form:option>
					                                                      <form:option  value="01-FEB-2011" label="Feb-2011"></form:option>
					                                                      <form:option  value="01-MAR-2011" label="Mar-2011"></form:option>
					                                                      <form:option  value="01-APR-2011" label="Apr-2011"></form:option>
					                                                      <form:option  value="01-MAY-2011" label="May-2011"></form:option>
					                                                      <form:option  value="01-JUN-2011" label="Jun-2011"></form:option>
					                                                      <form:option  value="01-JUL-2011" label="Jul-2011"></form:option>
					                                                      <form:option  value="01-AUG-2011" label="Aug-2011"></form:option>
					                                                      <form:option  value="01-SEP-2011" label="Sep-2011"></form:option>
					                                                      <form:option  value="01-OCT-2011" label="Oct-2011"></form:option>
					                                                      <form:option  value="01-NOV-2011" label="Nov-2011"></form:option>
					                                                      <form:option  value="01-DEC-2011" label="Dec-2011"></form:option>
					                                                      <form:option  value="01-JAN-2012" label="Jan-2012"></form:option>
					                                                      <form:option  value="01-FEB-2012" label="Feb-2012"></form:option>
					                                                      <form:option  value="01-MAR-2012" label="mar-2012"></form:option>
					                                                      <form:option  value="01-APR-2012" label="apr-2012"></form:option>
					                                                      <form:option  value="01-MAY-2012" label="may-2012"></form:option>
					                                                      <form:option  value="01-JUN-2012" label="jun-2012"></form:option>
					                                                      <form:option  value="01-JUL-2012" label="jul-2012"></form:option>
					                                                     </form:select>--%>
					                                                     <form:select path="sName" id="sName">
					                                                      <form:options items="${PayAutoRunList}" itemValue="key" itemLabel="value"/>					                                                    				                    
					                                                     </form:select>
	                                        </div>
								</div>
								<div class="line">
								<div class="quarter">&nbsp;</div>	
								<div class="quarter">		 
			                                 <input type="radio" name="matched" id="matchedY" value="Y"/>Matched
			                                 <input type="radio" name="matched" id="matchedN" value="N"/>Not Matched		 
		                                </div>
								</div>
								<div class="line" style="margin-left:25%;">
											<%-- <div class="expbutton"><a href="javascript:manageDBMigration();"><span>Submit</span></a></div>--%>
											<div class="expbutton"><a href="javascript:compareDBMigration();"><span>Compare</span></a></div>
								</div>
								<div class="line" id="comp"></div>
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
		</form:form>
	</body>
</html>
<!-- End:TestPage.jsp -->
