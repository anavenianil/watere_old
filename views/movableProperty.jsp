<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:movableProperty.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="./styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/layout.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
<script type="text/javascript" src="script/RegExpValidate.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script language="javascript" src="./script/calendar.js"></script>
<script language="javascript" src="./script/calendar-en.js"></script>
<script language="javascript" src="./script/calendar-setup_3.js"></script>
<script type="text/javascript" src="script/misc.js"></script>
<title>Movable Property Details</title>
</head>

<body>
	<form:form method="post" commandName="adminMisc" id="adminMisc">
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
								<div class="headTitle">Transaction Details of Movable Property</div>
								<%-- Content Page starts --%>
								<div class="line">
											<div class="line">
												<div class="leftmar quarter">Name of the Government servant</div>
												<div class="quarter">${adminMisc.sfID}</div>
												
												<div class="leftmar quarter">Scale of pay and Present pay</div>
												<div class="quarter"></div>											
											</div>
											<br/><br/>
											
											<div class="line">
										    	<div class="leftmar quarter"> Purpose of application-sanction for transaction/intimation of transaction<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="purpose" id="purpose" /> 
												</div>
												<div class="leftmar quarter">Whether property is being acquired or disposed of<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="acqOrDis" id="acqOrDis" />
												</div>
											</div>	
											
										    
										    <div class="line">
										    	<div class="leftmar quarter">Probable date of acquisition or disposal of property<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="acqOrDisDate" id="acqOrDisDate" cssClass="dateClass" readonly="true" /> 
													<img src="./images/calendar.gif" id="acqOrDisDate1" /> 
													<script type="text/javascript">
												  		Calendar.setup({inputField :"acqOrDisDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"acqOrDisDate1",singleClick : true,step : 1});
													</script>
												</div>
												
												<div class="leftmar quarter"> If the property is already acquired/disposed of Actual date of transaction<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="propertyActualDate" id="propertyActualDate" cssClass="dateClass" readonly="true" /> 
													<img src="./images/calendar.gif" id="propertyActualDate1" /> 
													<script type="text/javascript">
												  		Calendar.setup({inputField :"propertyActualDate",ifFormat : "%d-%b-%Y",showsTime : false,button :"propertyActualDate1",singleClick : true,step : 1});
													</script>
												</div>
												
											</div>	
											
											
										    
											<div class="line">
										    	<div class="leftmar quarter">Description of the property(eg.,Car/Scooter/Loans,etc.,)<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:textarea path="description" id="description" rows="3" cols="20"  />
												</div>
											</div>	
											<div class="line">
												<div class="leftmar quarter">Make, mode(and also Registration No. in case of Vehicles) where necessary<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="modeOfReg" id="modeOfReg" />
												</div>
												
												<div class="leftmar quarter">Mode of acquisition/disposal(purchase/sale,gift,mortage,lease or otherwise)<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="modeOfAcquisition" id="modeOfAcquisition" /> 
												</div>
										    </div>
										    	
											<div class="line">
												<div class="leftmar quarter">Sale/Purchase price of the property(Market value in the case of gifts)<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="saleOrPurchasePrice" id="saleOrPurchasePrice" />
												</div>
												
												<div class="leftmar quarter">In case of acquisition, source or sources from which financed/proposed to be financed<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="source" id="source" /> 
												</div>
										    </div>
										    	
											<div class="line">
												<div class="leftmar quarter">Personal savings<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="personalSavings" id="personalSavings" />
												</div>
												
												<div class="leftmar quarter">Other Source giving details<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="otherSource" id="otherSource" /> 
												</div>
										    </div>
										    
											
											<!-- Upload Button -->
											
											
											<div class="line">
										    	<div class="leftmar quarter">Name and address of the party with whom transaction is proposed to be made/has been made<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:textarea path="partyName" id="partyName" rows="3" cols="20"  />
												</div>
											</div>	
											
											
											<div class="line">
												<div class="leftmar quarter">Is the party related to the applicant ? If so,state the relationship<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="relationship" id="relationship" />
												</div>
												
												<div class="leftmar quarter">Did the applicant have any dealings with the party in his official capacity at any time, or is the applicant likely to have any dealings with him in the near future?<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="applicantDealings" id="applicantDealings" /> 
												</div>
										    </div>
										    
										    
											<div class="line">
												<div class="leftmar quarter">Nature of official dealings with party<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="officialDealings" id="officialDealings" />
												</div>
												
												<div class="leftmar quarter">How was the transaction arranged(whether through statutory body or a private agency through friends and relatives)<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="transaction" id="transaction" /> 
												</div>
										    </div>
										    
										    
											<div class="line">
												<div class="leftmar quarter">In the case of acquisition by gift,whether sanction is also required under rule[13 of the CCS(Conduct)Rules, 1964.]<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="acqByGift" id="acqByGift" />
												</div>
												
												<div class="leftmar quarter">Any other relevant fact which the applicant may like to mention<span class="mandatory">*</span></div>
												<div class="quarter">
													<form:input path="remarks" id="remarks" /> 
												</div>
										    </div>
										    
										   
											<div style="margin-left: 35%;">
												<div class="expbutton"><a href="javascript:saveMovableRecord();" ><span>save</span></a></div>
												<div class="expbutton"><a href="javascript:clearFields();" ><span>Clear</span></a></div>
												<div class="expbutton"><a href="javascript:sendRequestToWorkFlow();" ><span>Send Request</span></a></div>
											</div>
											
									      
									      <div class="line" id="result">
									       <jsp:include page="movablePropertyList.jsp"/>
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
		<form:hidden path="type"/>
		</form:form>
	</body>
</html>
<!-- End:movableProperty.jsp -->