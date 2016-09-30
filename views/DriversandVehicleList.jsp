<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Begin : AwardList.jsp -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/displaytag" prefix="display"%>
<%@ taglib uri="/tags/ajaxanywhere" prefix="aa"%>
<%@ taglib uri="/tags/spring" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="width:100%;">


		<table width=100% class=list border=2px id=dynWorkFlow cellpadding=0 cellspacing=0 align=center>
		<tr>
		<th>Request Application Id</th>
		<th>Requested By</th>
		<th>Designation</th>
		<th>RequestedFor</th>
		<th>No Of people</th>
		<th>Travelling Date</th>
		<th>Time</th>
		<th>PickupPoint</th>
		<th>Dropping point</th>
		<th>Vehicle No</th>
		<th>Driver Name</th>
		
		<th>CLOSE</th>
		</tr>
		<tr id=row0>
		<td>R347</td>
		<td>Ram</td>
		<td>Tech</td>
		<td>SELF</td>
	
		
		<td>4</td>
		<td>28-Aug 2012</td>
		<td>5:00 pm</td><td>ASL</td>
		<td>STATION</td>
		<td>AP123</td><td>murali</td>
		<td><div><input type=button id=add class=smallbtn style="background-color: #3e7bbe;font-weight:bold;color:white;" value=[C] /></div></td>
		</tr>
		<tr id=row0>
		<td>R348</td>
		<td>Prasad</td>
		<td>Store</td>
	
		<td>Praveen</td>
		
		
		<td>4</td>
		<td>28-Aug 2012</td>
		<td>4:00 pm</td><td>Asl</td>
		<td>DRDL</td>
		<td>AP123</td><td>murali</td><td><div><input type=button id=add class=smallbtn style="background-color: #3e7bbe;font-weight:bold;color:white;" value=[C] /></div></td>
		</tr>
		<tr id=row0>
		<td>R3442</td>
		<td>krathik</td>
		<td>Officer</td>
		
		<td>SELF</td>
		
		
		<td>5</td>
		<td>27-Aug 2012</td>
		<td>11:00 am</td><td>STATION</td>
		<td>ASL</td>
		<td>AP 1235</td><td>Akhil</td></td>
		<td><div><input type=button id=add class=smallbtn style="background-color: #3e7bbe;font-weight:bold;color:white;" value=[C] /></div></td>
		</tr>
		
		<tr id=row0>
		<td>R3446</td>
		<td>Mahesh</td>
		<td>SC B</td>
		
		<td>raj</td>
		
		
		<td>8</td>
		<td>26-Aug 2012</td>
		<td>3:00 am</td><td>UPPAL</td>
		<td>ASL</td>
		<td>AP 287</td><td>Krishna</td>
		<td><div><input type=button id=add class=smallbtn style="background-color: #3e7bbe;font-weight:bold;color:white;" value=[C] /></div></td>
		</tr>
		<tr id=row0>
		<td>R3400</td>
		<td>Ram dev</td>
		<td>Officer</td>
		
		<td>Self</td>
		
		
		<td>4</td>
		<td>25-Aug 2012</td>
		<td>4:00 am</td><td>STATION</td>
		<td>ASL</td>
		<td>AP 1235</td><td>sampath</td>
		<td><div><input type=button id=add class=smallbtn style="background-color: #3e7bbe;font-weight:bold;color:white;" value=[C] /></div></td>
		</tr>
		<tr id=row0>
		<td>R34489</td>
		<td>Mohammad</td>
		<td>Officer</td>
		
		<td>SELF</td>
		
		<td>6</td>
		<td>3-Sep 2012</td>
		<td>5:00 am</td><td>ECIL</td>
		<td>ASL</td>
		<td>AP 1235</td><td>Raj</td>
		<td><div><input type=button id=add class=smallbtn style="background-color: #3e7bbe;font-weight:bold;color:white;" value=[C] /></div></td>
		</tr></tr></table>
		
<script>
	
	displayPaging("AwardtListTable","awardData");
</script>


<!-- End : AwardList.jsp -->
