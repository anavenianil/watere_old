<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:MMGMasterData.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>

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
<script type="text/javascript">
	$jq(document).ready(function(){
		lables(type);
		clearMMGMaster();
		$jq("#mmgMaster").validate({
    	 rules: { 
	         itemCodeId: "required",
	         typeValue: "required",
	         itemSubCode: "required",
	         categoryId: "required",
	         subCategoryId: "required",
	         itemCode: "required",
	         subCategoryCode: "required",
	         categoryCode: "required",
	         companyCode: "required"
	   },
	  messages: {
		itemCodeId: "Please select Item Code.",
		typeValue: "Please Enter Name.",
		itemSubCode: "Please select ItemSubCode.",
		categoryId: "Please select Category Name.",
		subCategoryId: "Please select Sub Category Name.", 
		itemCode: "Please select Item Code.",
		subCategoryCode: "Please select Sub Category Name.",
		categoryCode: "Please select  Category Code.",
		companyCode: "Please Enter Company Code."
		},
    });
   	});
</script>
</head>

<body>
	<form:form method="post" commandName="mmgMaster" name="mmgMaster" id="mmgMaster">
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
								<div class="headTitle" id="headTitle"></div>
								<div id="notification" class="notification" style="display: none;"><img src="./images/ajax-loader.gif" width="32" height="16" align="absmiddle">&nbsp;Loading...</img></div>
								<%-- Content Page starts --%>
								<div>
									
									<div class="line">
									
									<c:if test="${masterType eq 'itemSubCategory' or masterType eq 'itemCode'}">
										<div class="line">
											
												<div class="quarter bold" style="margin-left:8px;">Item Category<span class='failure'>*</span></div>
												<div class="quarter">
													<form:select path="categoryId" id="categoryId" cssStyle="width:145px;">
														<form:option value="">Select</form:option>
														<form:options items="${parentList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
											
										</div>
									</c:if>
									<c:if test="${masterType eq 'itemCode'}">
										<div class="line">
											
												<div class="quarter bold" style="margin-left:8px;">Item Sub Category<span class='failure'>*</span></div>
												<div class="quarter">
													<form:select path="subCategoryId" id="subCategoryId" cssStyle="width:145px;">
														<form:option value="">Select</form:option>
														<form:options items="${itemParentList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
											
										</div>
									</c:if>
									<c:if test="${masterType eq 'itemSubCode'}">
										<div class="line">
											
												<div class="quarter bold" style="margin-left:8px;">Item Code<span class='failure'>*</span></div>
												<div class="quarter">
													<form:select path="itemCodeId" id="itemCodeId" cssStyle="width:145px;">
														<form:option value="">Select</form:option>
														<form:options items="${parentList}" itemValue="id" itemLabel="name"/>
													</form:select>
												</div>
											
										</div>
									</c:if>
									<div class="line">
										
										<div class="quarter bold" id="labelType"></div>
										<div class="quarter"><form:input path="typeValue" id="typeValue" maxlength="50" /></div>
									</div>
									<c:if test="${masterType eq 'itemCode'}">
										<div class="line">
											<div class="quarter bold" style="margin-left:8px;">Item Code<span class='failure'>*</span></div>
											<div class="quarter"><form:input path="itemCode" id="itemCode" maxlength="4" onkeypress="return checkTwoDigitFloat(event,this.id);"/></div>
										</div>
									</c:if>
									<c:if test="${masterType eq 'itemSubCode'}">
										<div class="line">
											<div class="quarter bold" style="margin-left:8px;">Item Sub Code<span class='failure'>*</span></div>
											<div class="quarter"><form:input path="itemSubCode" id="itemSubCode" maxlength="3" onkeypress="return checkTwoDigitFloat(event,this.id);"/></div>
										</div>
									</c:if>
									<c:if test="${masterType eq 'taxType'}">
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">Percentage</div>
										<div class="quarter"><form:input path="percentage" id="percentage" maxlength="5" onkeypress="return checkTwoDigitFloat(event,this.id);" onblur="javascript:checkPercentage(this.id,this.value);"></form:input>
											
										</div>
									</div>
									</c:if>
									<c:if test="${masterType eq 'itemCompany'}">
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">Company Code<span class='failure'>*</span></div>
										<div class="quarter"><form:input path="companyCode" id="companyCode" maxlength="2" onkeypress="return checkTwoDigitFloat(event,this.id);"></form:input>
											
										</div>
									</div>
									</c:if>
									<c:if test="${masterType eq 'itemSubCategory'}">
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">Item Sub Category Code<span class='failure'>*</span></div>
										<div class="quarter"><form:input path="subCategoryCode" id="subCategoryCode" maxlength="4" onkeypress="return checkTwoDigitFloat(event,this.id);"></form:input>
											
										</div>
									</div>
									</c:if>
									<c:if test="${masterType eq 'itemCategory'}">
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">Item Category Code<span class='failure'>*</span></div>
										<div class="quarter"><form:input path="categoryCode" id="categoryCode" maxlength="2" onkeypress="return checkTwoDigitFloat(event,this.id);"></form:input>
											
										</div>
									</div>
									</c:if>
									
									<div class="line">
										<div class="quarter bold" style="margin-left:8px;">Description</div>
										<div class="quarter"><form:textarea path="description" id="description" cols="20" rows="3" onkeypress="textCounter(event,$jq('#description'),$jq('#counter'),500);"
										 onkeyup="textCounter(event,$jq('#description'),$jq('#counter'),500);"></form:textarea>
											<input type="text" class="counter" name="counter" value="500" id="counter"/>
										</div>
									</div>
									
									<div class="line">
										<div style="margin-left:27%;margin-right:5px;float:left;"><a id="createFundType" href="javascript:manageMMGMaster('${sessionScope.masterType}');"><div class="appbutton">Submit</div></a></div>
										<div><a id="createFundType" href="javascript:clearMMGMaster('${sessionScope.masterType}');"><div class="appbutton">Clear</div></a></div>  
																	
									</div>
									
									</div>
									<div class="line">
										<div id="result" class="line">
											<jsp:include page="MMGMasterDataList.jsp" />
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
		<form:hidden path="param" id="param"/>
		<form:hidden path="type" id="type"/>
		<form:hidden path="name" id="name"/>
		
		</form:form>
		<script type="text/javascript">
			var type='<c:out value='${sessionScope.masterType}'/>';
		</script>
	</body>
</html>
<!-- End:MMGMasterData.jsp -->