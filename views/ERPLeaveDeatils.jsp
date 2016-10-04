<!-- Begin :ERPLeaveDeatils.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/displaytag" prefix="display" %>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib uri="/tags/fmt" prefix="fmt"%>

<div>
	<div>
		<c:if test="${message=='failure'}"> <span class="failure"><spring:message code="failure"/></span></c:if>
		<c:if test="${message=='success'}"> <span class="success"><spring:message code="success"/></span></c:if>
		<c:if test="${message=='duplicate'}"> <span class="failure"><spring:message code="duplicate"/></span></c:if>
		<c:if test="${message=='deletefail'}"> <span class="failure"><spring:message code="recordexists"/></span></c:if> 
	</div>
	<div>
	
	
		<div id="Pagination">
			<display:table name="${sessionScope.erpAvailLeavesList}" excludedParams="*"
				export="false" class="list" requestURI="" id="dataList" pagesize="100"  style="width:100%;" 
				sort="list">
					<br>
					<display:column title="Leave Type"  style="width:20%;"  ><font color="voilet" font> <b >&emsp;&emsp;&emsp;&emsp; ${dataList.leaveType} </b></font>  </display:column>
					<display:column title="Total Leave Balance" style="width:20%;"  >&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<b><font color="#4d0000">
					<c:if test="${dataList.leaveCode eq 'AL'}">
								<c:choose>
										<c:when test="${empty leaveAdmin.erpUsedLeavesDTO.aL1}">
											<fmt:formatNumber value="${dataList.noOfDays}" maxFractionDigits="3"/>
										</c:when>
										<c:otherwise>
											<fmt:formatNumber value="${dataList.noOfDays + leaveAdmin.erpUsedLeavesDTO.aL1 +  leaveAdmin.erpUsedLeavesDTO.cL1}" maxFractionDigits="3"/>
										</c:otherwise>
								</c:choose>
					</c:if>
					<c:if test="${dataList.leaveCode eq 'SL'}">
									<c:choose>
										<c:when test="${empty leaveAdmin.erpUsedLeavesDTO.sL1}">
											<fmt:formatNumber value="${dataList.noOfDays}" maxFractionDigits="3"/>
										</c:when>
										<c:otherwise>
											<fmt:formatNumber value="${dataList.noOfDays + leaveAdmin.erpUsedLeavesDTO.sL1}" maxFractionDigits="3"/>
										</c:otherwise>
								</c:choose>
					</c:if>
					<c:if test="${dataList.leaveCode eq 'PL'}">
								<c:choose>
										<c:when test="${empty leaveAdmin.erpUsedLeavesDTO.pL1}">
											<fmt:formatNumber value="${dataList.noOfDays}" maxFractionDigits="3"/>
										</c:when>
										<c:otherwise>
											<fmt:formatNumber value="${dataList.noOfDays + leaveAdmin.erpUsedLeavesDTO.pL1}" maxFractionDigits="3"/>
										</c:otherwise>
								</c:choose>
					</c:if>
					<c:if test="${dataList.leaveCode eq 'ML'}">

								<c:choose>
										<c:when test="${empty leaveAdmin.erpUsedLeavesDTO.mL1}">
											<fmt:formatNumber value="${dataList.noOfDays}" maxFractionDigits="3"/>
										</c:when>
										<c:otherwise>
											<fmt:formatNumber value="${dataList.noOfDays + leaveAdmin.erpUsedLeavesDTO.mL1}" maxFractionDigits="3"/>
										</c:otherwise>
								</c:choose>
					</c:if>
					</font></b></display:column>
					<display:column title="Available Leave Balance"  style="width:20%;" >&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<b><font color="green">${dataList.noOfDays}</font></b></display:column>
					<display:column title="Used Leave Balance"  style="width:20%;" >&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<b><font color="red">
					<c:if test="${dataList.leaveCode eq 'AL'}">
								<c:choose>
										<c:when test="${empty leaveAdmin.erpUsedLeavesDTO.aL1 && empty leaveAdmin.erpUsedLeavesDTO.cL1 }">0</c:when>
										<c:otherwise>${leaveAdmin.erpUsedLeavesDTO.aL1 + leaveAdmin.erpUsedLeavesDTO.cL1 }</c:otherwise>
								</c:choose>
					</c:if>
					<c:if test="${dataList.leaveCode eq 'SL'}">
								<c:choose>
										<c:when test="${empty leaveAdmin.erpUsedLeavesDTO.sL1}">0</c:when>
										<c:otherwise>${leaveAdmin.erpUsedLeavesDTO.sL1 }</c:otherwise>
								</c:choose>
					</c:if>
					<c:if test="${dataList.leaveCode eq 'PL'}">
								<c:choose>
										<c:when test="${empty leaveAdmin.erpUsedLeavesDTO.pL1}">0</c:when>
										<c:otherwise>${leaveAdmin.erpUsedLeavesDTO.pL1 }</c:otherwise>
								</c:choose>
					</c:if>
					<c:if test="${dataList.leaveCode eq 'ML'}">
								<c:choose>
										<c:when test="${empty leaveAdmin.erpUsedLeavesDTO.mL1}">0</c:when>
										<c:otherwise>${leaveAdmin.erpUsedLeavesDTO.mL1 }</c:otherwise>
								</c:choose>
					</c:if>
					</font></b></display:column>
				<display:column title=""  style="width:20%;" >&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</display:column>
			</display:table>
		</div>

	    <script>
	    erpAvailLeavesList = <%= (net.sf.json.JSONArray)session.getAttribute("erpAvailLeavesList") %> 
		</script>
	</div>
</div>
<!-- End :ERPLeaveDeatils.jsp -->
