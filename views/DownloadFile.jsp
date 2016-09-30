<!-- Begin:DownloadFile.jsp: --> 
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%
String file ="C:/MyImages/ltc.pdf";
%>
<html>
<head>
<meta http-equiv="Content-Language" content="en-us">
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<div style="float:left; width:100%; background-color:#FFFFFF;">
		<div style="float:left; width:10%">&nbsp;</div>
			<div style="float:left; width:80%;" align="center">
				
					<table width="789px" border="0" align="center" class="bgcolor">
						<tr>
							<td> <%	response.sendRedirect(file); %></td>
						</tr>		
					</table>
					
			</div>
		<div style="float:right;width:10%">&nbsp;</div>
	</div>
</body>
</html>
<!-- End:DownloadFile.jsp: --> 