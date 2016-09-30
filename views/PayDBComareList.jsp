<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : payDBComareList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
	<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<head>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="./styles/calendar-win2k-cold-1.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<script type="text/javascript" src="script/jquery.displaytag-ajax-1.2.js"></script>
</head>
    <div class="line">&nbsp; </div> 
    <div class="line">&nbsp; </div> 
    <div class="line">&nbsp; </div> 
    <div class="line">&nbsp; </div> 
    <div class="line">credits Matched:<b>${sessionScope.creditsMatched}</b> </div>
	<div class="line">debits Matched: <b>${sessionScope.debitsMatched}</b></div>
	<div class="line">recoveries Matched: <b>${sessionScope.recoveriesMatched}</b></div>
	
	
    <div class="line">	
	     <div id="Pagination">
	
		<display:table name="${sessionScope.compList}" excludedParams="*"
			export="false" class="list" requestURI="" id="comp" pagesize="1000"
			sort="list">		
			<display:column title="SFID" style="width:20%;text-align:center">&nbsp;${comp.sfid}<br>&nbsp;</br>${comp.name}</display:column>
			<display:column title="CREDITS" style="width:20%;text-align:center">&nbsp;${comp.cpsCredits}<br>&nbsp;</br>${comp.misCredits}</display:column>
			<display:column title="CREDITS-DIFF" style="width:15%;text-align:center">&nbsp;${comp.creditDiff}</display:column>
			<display:column title="DEBITS" style="width:20%;text-align:center">&nbsp;${comp.cpsDebits}<br>&nbsp;</br>${comp.misDebits}</display:column>
			<display:column title="DEBITS-DIFF" style="width:15%;text-align:center">&nbsp;${comp.debitDiff}</display:column>
			<display:column title="REC" style="width:20%;text-align:center">&nbsp;${comp.cpsRec}<br>&nbsp;</br>${comp.misRec}</display:column>
			<display:column title="REC-DIFF" style="width:15%;text-align:center">&nbsp;${comp.recDiff}</display:column>
			<display:column title="TAKEHOME" style="width:20%;text-align:center">&nbsp;${comp.cpsTakeHome}<br>&nbsp;</br>${comp.misTakeHome}</display:column>
			<display:column title="TAKEHOME-DIFF" style="width:15%;text-align:center">&nbsp;${comp.takeHomeDiff}</display:column>
			<display:column title="BASIC" style="width:20%;text-align:center">&nbsp;${comp.cpsbasic}<br>&nbsp;</br>${comp.misbasic}</display:column>
			<display:column title="GRADE" style="width:20%;text-align:center">&nbsp;${comp.cpsgrade}<br>&nbsp;</br>${comp.misgrade}</display:column>
			<display:column title="DA" style="width:20%;text-align:center">&nbsp;${comp.cpsda}<br>&nbsp;</br>${comp.misda}</display:column>
			<display:column title="HRA" style="width:20%;text-align:center">&nbsp;${comp.cpshra}<br>&nbsp;</br>${comp.mishra}</display:column>
			<display:column title="TWOADD" style="width:20%;text-align:center">&nbsp;${comp.cpctwoaddincr}<br>&nbsp;</br>${comp.mistwoaddincr}</display:column>
			<display:column title="SPAY" style="width:20%;text-align:center">&nbsp;${comp.cpsspay}<br>&nbsp;</br>${comp.misspay}</display:column>
			<display:column title="TRA" style="width:20%;text-align:center">&nbsp;${comp.cpstra}<br>&nbsp;</br>${comp.mistra}</display:column>
			<display:column title="WASH" style="width:20%;text-align:center">&nbsp;${comp.cpswash}<br>&nbsp;</br>${comp.miswash}</display:column>
			<display:column title="FPA" style="width:20%;text-align:center">&nbsp;${comp.cpsfpa}<br>&nbsp;</br>${comp.misfpa}</display:column>
			<display:column title="VARINCR" style="width:20%;text-align:center">&nbsp;${comp.cpsvarincr}<br>&nbsp;</br>${comp.misvarincr}</display:column>
			<display:column title="CGHS" style="width:20%;text-align:center">&nbsp;${comp.cpscghs}<br>&nbsp;</br>${comp.miscghs}</display:column>
			<display:column title="CGEIS" style="width:20%;text-align:center">&nbsp;${comp.cpscgeis}<br>&nbsp;</br>${comp.miscgeis}</display:column>
			<display:column title="GPF" style="width:20%;text-align:center">&nbsp;${comp.cpsgpf}<br>&nbsp;</br>${comp.misgpf}</display:column>
			<display:column title="ITAX" style="width:20%;text-align:center">&nbsp;${comp.cpsitax}<br>&nbsp;</br>${comp.misitax}</display:column>
			<display:column title="PTAX" style="width:20%;text-align:center">&nbsp;${comp.cpsptax}<br>&nbsp;</br>${comp.misptax}</display:column>
			<display:column title="CYCLE" style="width:20%;text-align:center">&nbsp;${comp.cpscycle}<br>&nbsp;</br>${comp.miscycle}</display:column>
			<display:column title="CAR" style="width:20%;text-align:center">&nbsp;${comp.cpscar}<br>&nbsp;</br>${comp.miscar}</display:column>
			<display:column title="SCOOTER" style="width:20%;text-align:center">&nbsp;${comp.cpsscooter}<br>&nbsp;</br>${comp.misscooter}</display:column>
			<display:column title="PC" style="width:20%;text-align:center">&nbsp;${comp.cpspc}<br>&nbsp;</br>${comp.mispc}</display:column>
			<display:column title="HBA" style="width:20%;text-align:center">&nbsp;${comp.cpshba}<br>&nbsp;</br>${comp.mishba}</display:column>
			<display:column title="FESTADV" style="width:20%;text-align:center">&nbsp;${comp.cpsFest}<br>&nbsp;</br>${comp.misFest}</display:column>
			<display:column title="RENT" style="width:20%;text-align:center">&nbsp;${comp.cpsRent}<br>&nbsp;</br>${comp.misRent}</display:column>
			<display:column title="WATER" style="width:20%;text-align:center">&nbsp;${comp.cpsWater}<br>&nbsp;</br>${comp.misWater}</display:column>
			<display:column title="ELEC" style="width:20%;text-align:center">&nbsp;${comp.cpsElec}<br>&nbsp;</br>${comp.misElec}</display:column>
			<display:column title="TADA" style="width:20%;text-align:center">&nbsp;${comp.cpsTada}<br>&nbsp;</br>${comp.misTada}</display:column>
			<display:column title="BENFUND" style="width:20%;text-align:center">&nbsp;${comp.cpsBenFund}<br>&nbsp;</br>${comp.misBenFund}</display:column>
			<display:column title="RESSECU" style="width:20%;text-align:center">&nbsp;${comp.cpsResSecu}<br>&nbsp;</br>${comp.misResSecu}</display:column>
			<display:column title="MESS" style="width:20%;text-align:center">&nbsp;${comp.cpsMess}<br>&nbsp;</br>${comp.misMess}</display:column>
			<display:column title="WELFUND" style="width:20%;text-align:center">&nbsp;${comp.cpsWelFund}<br>&nbsp;</br>${comp.misWelFund}</display:column>
			<display:column title="WELREFUND" style="width:20%;text-align:center">&nbsp;${comp.misWelReFund}</display:column>
			<display:column title="REGFUND" style="width:20%;text-align:center">&nbsp;${comp.cpsRegFund}<br>&nbsp;</br>${comp.misRegFund}</display:column>
			<display:column title="COURTATT" style="width:20%;text-align:center">&nbsp;${comp.cpsCourt}<br>&nbsp;</br>${comp.misCourt}</display:column>
			<display:column title="HDFC" style="width:20%;text-align:center">&nbsp;${comp.cpsHdfc}<br>&nbsp;</br>${comp.misHdfc}</display:column>
			<display:column title="LIC" style="width:20%;text-align:center">&nbsp;${comp.cpsLic}<br>&nbsp;</br>${comp.misLic}</display:column>
			<display:column title="GIC" style="width:20%;text-align:center">&nbsp;${comp.cpsGic}<br>&nbsp;</br>${comp.rec5}</display:column>
			<display:column title="CANFIN" style="width:20%;text-align:center">&nbsp;${comp.cpsCanfin}<br>&nbsp;</br>${comp.rec1}</display:column>
			<display:column title="CCSC" style="width:20%;text-align:center">&nbsp;${comp.cpsCcsc}<br>&nbsp;</br>${comp.misCcsc}</display:column>
			<display:column title="CCSR" style="width:20%;text-align:center">&nbsp;${comp.cpsCcsr}<br>&nbsp;</br>${comp.misCcsr}</display:column>
			<display:column title="CR1" style="width:20%;text-align:center">&nbsp;${comp.cr1}</display:column>
			<display:column title="CR2" style="width:20%;text-align:center">&nbsp;${comp.cr2}</display:column>
			<display:column title="CR4" style="width:20%;text-align:center">&nbsp;${comp.cr4}</display:column>
			<display:column title="DB1" style="width:20%;text-align:center">&nbsp;${comp.db1}</display:column>
			<display:column title="DB2" style="width:20%;text-align:center">&nbsp;${comp.db2}</display:column>
			<display:column title="DB3" style="width:20%;text-align:center">&nbsp;${comp.db3}</display:column>
			<display:column title="DB4" style="width:20%;text-align:center">&nbsp;${comp.db4}</display:column>
			<display:column title="DB5" style="width:20%;text-align:center">&nbsp;${comp.db5}</display:column>
			<display:column title="REC2" style="width:20%;text-align:center">&nbsp;${comp.rec2}</display:column>
			<display:column title="REC3" style="width:20%;text-align:center">&nbsp;${comp.rec3}</display:column>
			<display:column title="REC4" style="width:20%;text-align:center">&nbsp;${comp.rec4}</display:column>
			<display:column title="CR-REMARKS" style="width:15%;text-align:center">&nbsp;${comp.crRemarks}</display:column>
			<display:column title="DB-REMARKS" style="width:15%;text-align:center">&nbsp;${comp.dbRemarks}</display:column>
			<display:column title="REC-REMARKS" style="width:15%;text-align:center">&nbsp;${comp.recRemarks}</display:column>
			</display:table>
	</div>	
	<script>
		$jq( function(){ $jq("#Pagination").displayTagAjax('paging');})
	</script>
</div>
<!-- End : payDBComareList.jsp -->