<!-- Begin : PublishDoPartII.jsp -->
<%@page import="java.net.NetPermission"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONString"%>
<%@page import="net.sf.json.util.JSONStringer"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>
<div id="GazettedType2">




</div> 
<div id="resultMsg">
	<jsp:include page="Result.jsp"></jsp:include>
</div>
<div class="line">
	 <div class="line">
	 <div class="line">
		<%-- <div class="quarter leftmar">Gazetted Type</div>
			<div class="quarter">
		<select name="gazettedType" id="gazettedType" onmouseover="setSelectWidth('#gazettedType')">      <!--onchange="javascript:getGazettedTypeList()"   This function removed and placed at category id  -->
		<option value="0">Select</option>
		<c:forEach var="typeList" items="${typeList}">
		<option value="${typeList.id}">${typeList.name}</option>
		</c:forEach>
		</select>
		 </div> --%>
		 <div id="casualityDiv" style="display:none">
		<div class="quarter leftmar">Casuality</div>
		<div class="quarter">
		<select name="casualityId" id="casualityId" onmouseover="setSelectWidth('#casualityId');" onchange="javascript:getPromotionSearchList();">
		<option value="0">Select</option>
		<c:forEach var="CasualitiesMasterList" items="${CasualitiesMasterList}">
		<option value="${CasualitiesMasterList.id}#${CasualitiesMasterList.code}">${CasualitiesMasterList.name}</option>
		</c:forEach>
		</select>
		</div>
		</div>
		</div>
		<!-- onchange="javascript:getDateWiseDoPart('')" -->
          <div class="line" id="doPartNumberDiv" style="display:none">
			<div class="quarter leftmar">Do Part Number</div>
			<div class="quarter">
		<select name="doPartNumber" id="doPartNumber" onmouseover="setSelectWidth('#doPartNumber')" >
	    <option value="select">Select</option>
		<option value="0">No Action</option>
		<c:forEach var="doPartList" items="${doPartList}">
		<option value="${doPartList.id}">${doPartList.doPartNumber} - <fmt:formatDate pattern="dd-MMM-yyyy" value="${doPartList.doPartDate}"/></option>
		</c:forEach>
		</select>
			</div>
		<div class="quarter bold">Serial Number</div>
			<div class="quarter">
			<div class="quarter" ><input type="text" id="serialNumber" /></div>			
		</div>
					
		</div>
         <div class="line" id="publishButtonDiv">
			<div class="expbutton"  style="float:right"><a href="javascript:submitPayFixation();" ><span>Publish in DO Part II</span></a></div>
		</div>
		<div class="line" id="saveButtonDiv" style="display:none">
			<div class="expbutton"  style="float:right"><a href="javascript:submitPayFixation();" ><span>Save No Action</span></a></div>
		</div>
		<div class="line"><hr /></div>
		<c:if test="${not empty sessionScope.assessmentList  || not empty sessionScope.incrementsList}">
      	<div class="line" id="paylist">
		<div><jsp:include page="PayFixationList.jsp" /></div>
		</div>
		</c:if>
        
	</div>
</div>
<!-- End : PublishDoPartII.jsp -->