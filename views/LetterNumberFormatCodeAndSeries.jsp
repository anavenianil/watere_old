<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--Begin: LetterNumberFormatCodeAndSeries.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
         

<fieldset class="line">
<legend ><strong><font color='green'>Department Code & Series Details</font></strong></legend>
									
									<div class="line">
									<div  class="quarter bold leftmar">Department Code</div>
									<div  class="threefourth width:59%">	${letterNumberMaster.deptCode}</div>
									
									<div  class="quarter bold leftmar">Series</div>
									<div  class="threefourth width:59%">	${letterNumberMaster.series}</div>
									</div>	
									</fieldset>
												

<!--End: LetterNumberFormatCodeAndSeries.jsp -->