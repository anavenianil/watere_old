<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:leaveRelationMapping.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="styles/displaytag.css" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/tabs.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/jquery.ui.custom.min.js"></script>
<script type="text/javascript" src="script/menu.js"></script>
<script type="text/javascript" src="script/leave.js"></script>
<title>Configure Leave Relation Mappings</title>
</head>
<body>
	<form:form method="post" id="LeaveManagementBean" commandName="leave">
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
								<div class="headTitle">Configure Leave Relation Mappings</div>
								<%-- Content Page starts --%>
								<div class="line" id="result1"></div>
								<div class="line">

									<table class="list">
										<thead>
											<tr>
												<th>Mappings</th>
												<th>EL</th>
												<th>HPL</th>
												<th>CL</th>
												<th>CML</th>
												<th>LND</th>
												<th>EOL With MC</th>
												<th>EOL Without MC</th>
												<th>PL</th>
												<th>CCL</th>
												<th>Study Leave</th>
												<th>SPL</th>
												<th>Pregnancy</th>
												<th>Miscarriage</th>
												<th>Adoption for Female</th>
												<th>Adoption for Male</th>
											</tr>
										</thead>
										<tbody>
											<tr class="odd">
												<td>EL</td>
												<td style="text-align: center;"><input type="checkbox" id="EL1" value="1" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EL2" value="2" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EL3" value="3" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EL4" value="4" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EL5" value="5" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EL6" value="6" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EL15" value="15" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EL8" value="8" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EL9" value="9" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EL10" value="10" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EL11" value="11" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EL12" value="12" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EL13" value="13" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EL14" value="14" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EL16" value="16" /></td>
											</tr>
											<tr class="even">
												<td>HPL</td>
												<td style="text-align: center;"><input type="checkbox" id="HPL1" value="1" /></td>
												<td style="text-align: center;"><input type="checkbox" id="HPL2" value="2" /></td>
												<td style="text-align: center;"><input type="checkbox" id="HPL3" value="3" /></td>
												<td style="text-align: center;"><input type="checkbox" id="HPL4" value="4" /></td>
												<td style="text-align: center;"><input type="checkbox" id="HPL5" value="5" /></td>
												<td style="text-align: center;"><input type="checkbox" id="HPL6" value="6" /></td>
												<td style="text-align: center;"><input type="checkbox" id="HPL15" value="15" /></td>
												<td style="text-align: center;"><input type="checkbox" id="HPL8" value="8" /></td>
												<td style="text-align: center;"><input type="checkbox" id="HPL9" value="9" /></td>
												<td style="text-align: center;"><input type="checkbox" id="HPL10" value="10" /></td>
												<td style="text-align: center;"><input type="checkbox" id="HPL11" value="11" /></td>
												<td style="text-align: center;"><input type="checkbox" id="HPL12" value="12" /></td>
												<td style="text-align: center;"><input type="checkbox" id="HPL13" value="13" /></td>
												<td style="text-align: center;"><input type="checkbox" id="HPL14" value="14" /></td>
												<td style="text-align: center;"><input type="checkbox" id="HPL16" value="16" /></td>
											</tr>
											<tr class="odd">
												<td>CL</td>
												<td style="text-align: center;"><input type="checkbox" id="CL1" value="1" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CL2" value="2" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CL3" value="3" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CL4" value="4" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CL5" value="5" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CL6" value="6" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CL15" value="15" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CL8" value="8" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CL9" value="9" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CL10" value="10" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CL11" value="11" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CL12" value="12" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CL13" value="13" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CL14" value="14" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CL16" value="16" /></td>
											</tr>
											<tr class="even">
												<td>CML</td>
												<td style="text-align: center;"><input type="checkbox" id="CML1" value="1" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CML2" value="2" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CML3" value="3" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CML4" value="4" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CML5" value="5" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CML6" value="6" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CML15" value="15" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CML8" value="8" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CML9" value="9" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CML10" value="10" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CML11" value="11" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CML12" value="12" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CML13" value="13" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CML14" value="14" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CML16" value="16" /></td>
											</tr>
											<tr class="odd">
												<td>LND</td>
												<td style="text-align: center;"><input type="checkbox" id="LND1" value="1" /></td>
												<td style="text-align: center;"><input type="checkbox" id="LND2" value="2" /></td>
												<td style="text-align: center;"><input type="checkbox" id="LND3" value="3" /></td>
												<td style="text-align: center;"><input type="checkbox" id="LND4" value="4" /></td>
												<td style="text-align: center;"><input type="checkbox" id="LND5" value="5" /></td>
												<td style="text-align: center;"><input type="checkbox" id="LND6" value="6" /></td>
												<td style="text-align: center;"><input type="checkbox" id="LND15" value="15" /></td>
												<td style="text-align: center;"><input type="checkbox" id="LND8" value="8" /></td>
												<td style="text-align: center;"><input type="checkbox" id="LND9" value="9" /></td>
												<td style="text-align: center;"><input type="checkbox" id="LND10" value="10" /></td>
												<td style="text-align: center;"><input type="checkbox" id="LND11" value="11" /></td>
												<td style="text-align: center;"><input type="checkbox" id="LND12" value="12" /></td>
												<td style="text-align: center;"><input type="checkbox" id="LND13" value="13" /></td>
												<td style="text-align: center;"><input type="checkbox" id="LND14" value="14" /></td>
												<td style="text-align: center;"><input type="checkbox" id="LND16" value="16" /></td>
											</tr>
											<tr class="even">
												<td>EOL With MC</td>
												<td style="text-align: center;"><input type="checkbox" id="EOL1" value="1" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOL2" value="2" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOL3" value="3" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOL4" value="4" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOL5" value="5" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOL6" value="6" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOL15" value="15" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOL8" value="8" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOL9" value="9" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOL10" value="10" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOL11" value="11" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOL12" value="12" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOL13" value="13" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOL14" value="14" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOL16" value="16" /></td>
											</tr>
											<tr class="odd">
												<td>EOL Without MC</td>
												<td style="text-align: center;"><input type="checkbox" id="EOLWOMC1" value="1" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOLWOMC2" value="2" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOLWOMC3" value="3" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOLWOMC4" value="4" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOLWOMC5" value="5" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOLWOMC6" value="6" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOLWOMC15" value="15" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOLWOMC8" value="8" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOLWOMC9" value="9" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOLWOMC10" value="10" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOLWOMC11" value="11" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOLWOMC12" value="12" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOLWOMC13" value="13" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOLWOMC14" value="14" /></td>
												<td style="text-align: center;"><input type="checkbox" id="EOLWOMC16" value="16" /></td>
											</tr>
											<tr class="even">
												<td>PL</td>
												<td style="text-align: center;"><input type="checkbox" id="PL1" value="1" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PL2" value="2" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PL3" value="3" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PL4" value="4" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PL5" value="5" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PL6" value="6" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PL15" value="15" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PL8" value="8" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PL9" value="9" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PL10" value="10" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PL11" value="11" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PL12" value="12" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PL13" value="13" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PL14" value="14" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PL16" value="16" /></td>
											</tr>
											<tr class="odd">
												<td>CCL</td>
												<td style="text-align: center;"><input type="checkbox" id="CCL1" value="1" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CCL2" value="2" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CCL3" value="3" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CCL4" value="4" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CCL5" value="5" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CCL6" value="6" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CCL15" value="15" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CCL8" value="8" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CCL9" value="9" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CCL10" value="10" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CCL11" value="11" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CCL12" value="12" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CCL13" value="13" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CCL14" value="14" /></td>
												<td style="text-align: center;"><input type="checkbox" id="CCL16" value="16" /></td>
											</tr>
											<tr class="even">
												<td>Study Leave</td>
												<td style="text-align: center;"><input type="checkbox" id="SL1" value="1" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SL2" value="2" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SL3" value="3" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SL4" value="4" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SL5" value="5" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SL6" value="6" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SL15" value="15" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SL8" value="8" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SL9" value="9" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SL10" value="10" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SL11" value="11" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SL12" value="12" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SL13" value="13" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SL14" value="14" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SL16" value="16" /></td>
											</tr>
											<tr class="odd">
												<td>SPL</td>
												<td style="text-align: center;"><input type="checkbox" id="SPL1" value="1" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SPL2" value="2" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SPL3" value="3" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SPL4" value="4" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SPL5" value="5" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SPL6" value="6" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SPL15" value="15" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SPL8" value="8" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SPL9" value="9" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SPL10" value="10" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SPL11" value="11" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SPL12" value="12" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SPL13" value="13" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SPL14" value="14" /></td>
												<td style="text-align: center;"><input type="checkbox" id="SPL16" value="16" /></td>
											</tr>
											<tr class="even">
												<td>Pregnancy</td>
												<td style="text-align: center;"><input type="checkbox" id="PREL1" value="1" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PREL2" value="2" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PREL3" value="3" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PREL4" value="4" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PREL5" value="5" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PREL6" value="6" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PREL15" value="15" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PREL8" value="8" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PREL9" value="9" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PREL10" value="10" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PREL11" value="11" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PREL12" value="12" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PREL13" value="13" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PREL14" value="14" /></td>
												<td style="text-align: center;"><input type="checkbox" id="PREL16" value="16" /></td>
											</tr>
											<tr class="odd">
												<td>Miscarriage</td>
												<td style="text-align: center;"><input type="checkbox" id="MISL1" value="1" /></td>
												<td style="text-align: center;"><input type="checkbox" id="MISL2" value="2" /></td>
												<td style="text-align: center;"><input type="checkbox" id="MISL3" value="3" /></td>
												<td style="text-align: center;"><input type="checkbox" id="MISL4" value="4" /></td>
												<td style="text-align: center;"><input type="checkbox" id="MISL5" value="5" /></td>
												<td style="text-align: center;"><input type="checkbox" id="MISL6" value="6" /></td>
												<td style="text-align: center;"><input type="checkbox" id="MISL15" value="15" /></td>
												<td style="text-align: center;"><input type="checkbox" id="MISL8" value="8" /></td>
												<td style="text-align: center;"><input type="checkbox" id="MISL9" value="9" /></td>
												<td style="text-align: center;"><input type="checkbox" id="MISL10" value="10" /></td>
												<td style="text-align: center;"><input type="checkbox" id="MISL11" value="11" /></td>
												<td style="text-align: center;"><input type="checkbox" id="MISL12" value="12" /></td>
												<td style="text-align: center;"><input type="checkbox" id="MISL13" value="13" /></td>
												<td style="text-align: center;"><input type="checkbox" id="MISL14" value="14" /></td>
												<td style="text-align: center;"><input type="checkbox" id="MISL16" value="16" /></td>
											</tr>
											<tr class="even">
												<td>Adoption for Female</td>
												<td style="text-align: center;"><input type="checkbox" id="AL1" value="1" /></td>
												<td style="text-align: center;"><input type="checkbox" id="AL2" value="2" /></td>
												<td style="text-align: center;"><input type="checkbox" id="AL3" value="3" /></td>
												<td style="text-align: center;"><input type="checkbox" id="AL4" value="4" /></td>
												<td style="text-align: center;"><input type="checkbox" id="AL5" value="5" /></td>
												<td style="text-align: center;"><input type="checkbox" id="AL6" value="6" /></td>
												<td style="text-align: center;"><input type="checkbox" id="AL15" value="15" /></td>
												<td style="text-align: center;"><input type="checkbox" id="AL8" value="8" /></td>
												<td style="text-align: center;"><input type="checkbox" id="AL9" value="9" /></td>
												<td style="text-align: center;"><input type="checkbox" id="AL10" value="10" /></td>
												<td style="text-align: center;"><input type="checkbox" id="AL11" value="11" /></td>
												<td style="text-align: center;"><input type="checkbox" id="AL12" value="12" /></td>
												<td style="text-align: center;"><input type="checkbox" id="AL13" value="13" /></td>
												<td style="text-align: center;"><input type="checkbox" id="AL14" value="14" /></td>
												<td style="text-align: center;"><input type="checkbox" id="AL16" value="16" /></td>
											</tr>
											<tr class="odd">
												<td>Adoption for Male</td>
												<td style="text-align: center;"><input type="checkbox" id="ALM1" value="1" /></td>
												<td style="text-align: center;"><input type="checkbox" id="ALM2" value="2" /></td>
												<td style="text-align: center;"><input type="checkbox" id="ALM3" value="3" /></td>
												<td style="text-align: center;"><input type="checkbox" id="ALM4" value="4" /></td>
												<td style="text-align: center;"><input type="checkbox" id="ALM5" value="5" /></td>
												<td style="text-align: center;"><input type="checkbox" id="ALM6" value="6" /></td>
												<td style="text-align: center;"><input type="checkbox" id="ALM15" value="15" /></td>
												<td style="text-align: center;"><input type="checkbox" id="ALM8" value="8" /></td>
												<td style="text-align: center;"><input type="checkbox" id="ALM9" value="9" /></td>
												<td style="text-align: center;"><input type="checkbox" id="ALM10" value="10" /></td>
												<td style="text-align: center;"><input type="checkbox" id="ALM11" value="11" /></td>
												<td style="text-align: center;"><input type="checkbox" id="ALM12" value="12" /></td>
												<td style="text-align: center;"><input type="checkbox" id="ALM13" value="13" /></td>
												<td style="text-align: center;"><input type="checkbox" id="ALM14" value="14" /></td>
												<td style="text-align: center;"><input type="checkbox" id="ALM16" value="16" /></td>
											</tr>
										</tbody>
									</table>


								</div>
								<div class="line">
									<div style="margin-left: 50%">
										<div class="expbutton">
											<a onclick="javascript:submitLeaveMappingDetails()"> <span>Submit</span></a>
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
		<form:hidden path="param" />
		<form:hidden path="type" />
		<form:hidden path="exceptionsJson" />
	</form:form>
	<script>
		LeaveRelationMappingListJson = <%= (net.sf.json.JSONArray)session.getAttribute("relationmappingJSON") %>;
		$jq(document).ready(function(){loadLeaveMappings();});
	</script>
</body>
</html>
<!-- End:leaveRelationMapping.jsp -->