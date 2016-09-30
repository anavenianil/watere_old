<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:SelectedRefHospitalList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
		<select name="referralHospitalId" id="referralHospitalId"  cssClass="formSelect required"  onmouseover="setSelectWidth('#referralHospitalId')" >
			<option value="">Select</option>
				<c:forEach items="${cghs.referralHospitalList}" var="hospital">
					<option value="${hospital.id}">${hospital.hospitalName}</option>
				</c:forEach>
				<option value="1111" onclick="javascript:enableOtherHospitals();">OTHER HOSPITAL</option>
		</select>
<!-- end:SelectedRefHospitalList.jsp -->
