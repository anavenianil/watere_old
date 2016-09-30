<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- begin:TreeView.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/spring-form" prefix="form"%>
<%@ taglib uri="/tags/spring" prefix="spring"%>
<%@ taglib uri="/tags/jstl-c" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Employee Tree Details</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/menustyles.css" rel="stylesheet" type="text/css" />
<link href="styles/base.css" rel="stylesheet" type="text/css" />
<link href="styles/spacetree.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jit.js"></script>
<script type="text/javascript" src="script/example.js"></script>
<!--[if IE]><script language="javascript" type="text/javascript" src="script/excanvas.js"></script><![endif]-->
<c:if test="${sessionScope.tree!=null && sessionScope.tree!='' && sessionScope.tree!='[]'}">
<script>
var json=${sessionScope.tree};
</script>
</c:if>
</head>

<body>
	<div>
						<div id="container">
													<div id="center-container">
													    <div id="infovis" ></div>    
													</div>
													<div id="right-container">
										
													<h4>Tree Orientation</h4>
													<table>
													    <tr>
													        <td>
													            <label for="r-left">Left </label>
													        </td>
													        <td>
													            <input type="radio" id="r-left" name="orientation" checked="checked" value="left" />
													        </td>
													    </tr>
													    <tr>
													         <td>
													            <label for="r-top">Top </label>
													         </td>
													         <td>
													            <input type="radio" id="r-top" name="orientation" value="top" />
													         </td>
													    </tr>
													    <tr>
													         <td>
													            <label for="r-bottom">Bottom </label>
													          </td>
													          <td>
													            <input type="radio" id="r-bottom" name="orientation" value="bottom" />
													          </td>
													    </tr>
													    <tr>
													          <td>
													            <label for="r-right">Right </label>
													          </td> 
													          <td> 
													           <input type="radio" id="r-right" name="orientation" value="right" />
													          </td>
													    </tr>
													</table>
													<h4>Selection Mode</h4>
													<table>
													    <tr>
													        <td>
													            <label for="s-normal">Normal </label>
													        </td>
													        <td>
													            <input type="radio" id="s-normal" name="selection" checked="checked" value="normal" />
													        </td>
													    </tr>
													</table>
											</div>
										<div id="log"></div>										
										</div>
										<div class="line" style="background-color:#FFFFFF;width:1000px">
											    <div class="line">											
													Note: i) .Please use the cursor to move the tree  in any direction.
												</div>
												<div class="line">
													        &nbsp;ii) .On click the nodes will expands the subtree wherever applicable.											
											   </div>
										 </div>										
				
		</div>
		<c:if test="${sessionScope.tree!=null && sessionScope.tree!='' && sessionScope.tree!='[]'}">	
		<script>init(json);</script>
		</c:if>			
	</body>
</html>