<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--Begin: TrainingCirculationBrochure.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/jstl-c" prefix="c" %>
<%@ taglib uri="/tags/spring" prefix="spring" %>
	<div  class="quarter bold leftmar"><a class="expbutton" href="javascript:getTrainingDetails('${trainingMaster.brochure}');"><span>${trainingMaster.brochureName}</span></a></div>
										<div class="quarter">
											<a class="expbutton" href="javascript:cancelBrochure();"><span>Cancel</span></a></div>
										</div>
	<script>
	setBrochureId('${trainingMaster.brochure}');
	</script>
<!--End: TrainingCirculationBrochure.jsp -->