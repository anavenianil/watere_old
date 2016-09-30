<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:DesignationMaster.jsp -->
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.validate.min.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/script.js"></script>
<title>Designation Master</title>
</head>

<body>
	<form:form method="post" commandName="master" id="master">
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
								<div class="headTitle">Create Designation</div>
								<%-- Content Page starts --%>
								<div>
									<div class="line">
										<div   class="quarter bold leftmar">Group<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="group" id="group" cssClass="formSelect">
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.groupList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
										<div   class="quarter bold leftmar">Type<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="typeValue" id="typeValue" cssClass="formSelect" >
												<form:option value="select">Select</form:option>
												<form:option value="GAZETTED">GAZETTED</form:option>
												<form:option value="NON GAZETTED">NON-GAZETTED</form:option>
												<form:option value="OTHER">OTHER</form:option>
											</form:select>											
										</div>
									</div>
									<div class="line">
									<div   class="quarter bold leftmar">Category<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="category" id="category" cssClass="formSelect"  onmouseover="setSelectWidth('#category')" onchange="javascript:getSubCategory()">
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.CategoryList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
									
										<div class="quarter bold leftmar">Sub Category<span class="failure">*</span></div>
										<div class="quarter">
											<form:select path="subCategory" id="subCategory" cssClass="formSelect"  onmouseover="setSelectWidth('#subCategory')">
												<form:option value="select">Select</form:option>
												<form:options items="${sessionScope.subCategoryList}" itemValue="id" itemLabel="name"/>
											</form:select>
										</div>
										
									</div>
																	
									<div class="line">
										<div class="quarter bold leftmar">Designation Code<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="shortForm" id="shortForm" maxlength="10" onkeypress="javascript:return isAlphaNumaricExp(event)"/>
										</div>
										<div class="quarter bold leftmar">Designation In Short<span class="failure">*</span></div>
										<div class="quarter">
											<%-- <form:input path="description" id="description" maxlength="18" onkeypress="javascript:increaseTextWidth('desig')" onkeypress="javascript:return isAlphaNumaricExp(event)"/> --%>
										<!--commented Above Added below by bkr 15/06/2016  -->
										
										<form:input path="description" id="description" maxlength="18" onkeypress="javascript:increaseTextWidth('desig')" /> 
										</div>
									</div>
									<div class="line">
										<div class="quarter bold leftmar">Designation In Full<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="designationId" id="designationId" maxlength="23" onkeypress="javascript:increaseTextWidth('designationId');return isAlphaNumaricExp(event)"/>
										</div>
										<div class="quarter bold leftmar">Alias<span class="failure">*</span></div>
										<div class="quarter">
										<%-- 	<form:input path="desigAlias" id="desigAlias" maxlength="50" onkeypress="javascript:increaseTextWidth('alias')" onkeypress="javascript:return isAlphaNumaricExp(event)"/> --%>
										 	<!--commented Above Added below by bkr 15/06/2016  -->
										 	<form:input path="desigAlias" id="desigAlias" maxlength="50" onkeypress="javascript:increaseTextWidth('alias')" /> 
										</div>
									</div>
									
									<div class="line">
									<div class="quarter bold leftmar">Service Type<span class="failure">*</span></div>
										<div class="quarter">		 
			                                 <input type="radio" name="serviceType" id="serviceTypeM" value="Y"/>Yes
			                                 <input type="radio" name="serviceType" id="serviceTypeN" value="N"/>No		 
		                                </div>
										<div class="quarter bold leftmar">Order No<span class="failure">*</span></div>
										<div class="quarter">
											<form:input path="orderNo" id="orderNo" maxlength="10" onkeypress="return checkInt(event);"/>
										</div>
									</div>
									<div class="line">
										<div style="margin-left:25%">
											<div class="appbutton"><a href="javascript:desigSubmit();">Submit</a></div>
										</div>
											<div class="appbutton"><a href="javascript:clearDesig();">Clear</a></div>
											<div class="appbutton"><a href="javascript:normalReports('designation');">Report</a></div>
									</div>
									
									<div class="line height"><hr/></div>
									<div class="line" id="displayTable">
										<jsp:include page="DesignationList.jsp"></jsp:include>
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
		</form:form>
	</body>
</html>
<!-- End:DesignationMaster.jsp -->