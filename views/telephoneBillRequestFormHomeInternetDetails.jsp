<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- Start : telephoneBillRequestFormHomeInternetDetails.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
							  
								    <div style="margin-left: 48%">
								    <table border=1>
								    <tr>
								    <td style="color: blue"> Eligible Amount </td> 
								    <td style="color: blue">Service Tax (%)</td>
								    <td style="color: blue">Total Eligible Amount</td>
								    </tr>
								    <tr>
								    <td style="text-align: center">${telephone.amount}</td>
								   	<td style="text-align: center">${telephone.serviceTax}</td>
								   	<td style="text-align: center">${telephone.totAmount}</td>
								    </tr>
								    </table>
								   </div>
								 				

<!-- End : telephoneBillRequestFormHomeInternetDetails.jsp-->
