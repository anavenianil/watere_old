function previewAnnualIncrArrears(adminEffDate,financeEffDate){
	var requestDetails = {
			"param" : "previewAnnIncrArrears",
			"adminAccDate":adminEffDate,
			"financeAccDate" :financeEffDate
		 };
		$jq.post('arrears.htm', requestDetails, function(html) {
			$jq("#preview").html(html);
		});	
}
function submitAnnualIncrArrearsDetails(){
	var empList={};
	var i=0;
	$jq('#drawn').find('table tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
			empList[i]=$jq(this).find('td').eq(0).find('input').val();
			i++;
		}
	});
	var requestDetails = {
			"param" : "submitAnnualIncrArrearsDetails",
			"empList" :JSON.stringify(empList),
			"adminAccDate":$jq('#adminAccDate').val(),
			"financeAccDate" :$jq('#financeAccDate').val()
		 };
		$jq.post('arrears.htm', requestDetails, function(html) {
			$jq("#preview").html(html);
 });	
}
function getDaArrearsPreviewDetails(){
	var requestDetails = {
			"param" : "previewDaArrears",
			"adminAccDate":$jq('#adminAccDate').val(),
			"financeAccDate" :$jq('#financeAccDate').val()
		 };
		$jq.post('arrears.htm', requestDetails, function(html) {
			$jq("#previewHome").html(html);
		});	
}
function previewDAArrearsDetails(){
	var requestDetails = {
			"param" : "DAArrearsPreviewDetails",
			"adminAccDate":$jq('#adminAccDate').val(),
			"financeAccDate" :$jq('#financeAccDate').val(),
			"categoryId"  :$jq('#categoryId').val()
		 };
		$jq.post('arrears.htm', requestDetails, function(html) {
			$jq("#previewList").html(html);
		});	
}
function submitDAArrearsDetails(){
	var empList={};
	var i=0;
	$jq('#daArrears').find('table tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
			empList[i]=$jq(this).find('td').eq(0).find('input').val();
			i++;
		}
	});
	var requestDetails = {
			"param" : "submitDAArrearsDetails",
			"empList" :JSON.stringify(empList),
			"adminAccDate":$jq('#adminAccDate').val(),
			"financeAccDate" :$jq('#financeAccDate').val()
		 };
		$jq.post('arrears.htm', requestDetails, function(html) {
			$jq("#previewList").html(html);
 });	
}
function getFpaArrearsDetails(){
	var requestDetails = {
			"param" : "getFpaArrearsDetails",
			"userSfid":$jq('#userSfid').val(),
		 };
		$jq.post('arrears.htm', requestDetails, function(html) {
			$jq("#previewList").html(html);
		});	
}
function submitFPAArrearsDetails(){
	var requestDetails = {
			"param" : "submitFPAArrearsDetails",
		 };
		$jq.post('arrears.htm', requestDetails, function(html) {
			$jq("#previewList").html(html);
		});	
}
function getPromotionArrearsDetails(){
	var requestDetails = {
			"param" : "getPromotionArrearsDetails",
			"userSfid":$jq('#userSfid').val(),
		 };
		$jq.post('arrears.htm', requestDetails, function(html) {
			$jq("#preview").html(html);
		});	
}
function getPromotionArrearsPreviewList(adminDate,financeDate,assessmentId){
	var requestDetails = {
			"param" : "getPromotionArrearsPreview",
			"userSfid":$jq('#userSfid').val(),
			"assessmentId":assessmentId,
			"adminAccDate":adminDate,
			"financeAccDate":financeDate
		 };
		$jq.post('arrears.htm', requestDetails, function(html) {
			$jq("#previewList").html(html);
		});	
}
function submitPromotionArrearsDetails(){
	var requestDetails = {
			"param" : "submitPromotionArrearsDetails",
			"userSfid":$jq('#userSfid').val(),
			"assessmentId":$jq('#assessmentId').val(),
			"adminAccDate":$jq('#adminAccDate').val(),
			"financeAccDate":$jq('#financeAccDate').val()
		 };
		$jq.post('arrears.htm', requestDetails, function(html) {
			$jq("#previewList").html(html);
		});	
}
function getFPAArrearsHomePage(){
	document.forms[0].param.value = "getFPAArrearsHomePage";
	document.forms[0].action = "arrears.htm";
	document.forms[0].submit();
}
function getPromotionArrearsReport(){
	document.forms[0].param.value = "getPromotionArrearsReport";
	document.forms[0].action = "arrears.htm";
	document.forms[0].submit();
}
function AllcheckBoxCheck(){
	alert("check3");
	if($jq('#selectall').is(':checked')==true){
	$jq('#promotion').find('td').eq(0).each(
	function(){
		$jq(this).find('input:checkbox').attr('checked',true);
	
	          }
	);
  }else{
	  $jq("input:checkbox").attr("checked", false);
  }
}