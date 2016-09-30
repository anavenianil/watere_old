<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:DemnadPreview.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Cash Receipt Preview</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/mmgscript.js"></script>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<!--[if IE]><script language="javascript" type="text/javascript" src="script/excanvas.js"></script><![endif]-->
<script>
   
      $jq(document).ready(function() {
   
          createCashReceiptPreview();
   	  });
</script>
</head>
<body>
	<div id="mainDiv">
	</div>
	<script>
	IRItemsJson=<%= (net.sf.json.JSONArray)session.getAttribute("IRItemsJson") %>;
	cashReceiptPreviewJson=<%= session.getAttribute("cashReceiptPreviewJson") %>;
	</script>	
</body>

</html>
<!-- end:VoucherPreview.jsp -->