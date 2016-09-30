function fpaSubmit(){
var status=true;
var errorMessages=""
	if($jq('#operationLocation').val()==''){
		errorMessages+="please enter vasectory / Tubectomyoperation done at\n"
			if(status)
			{
				status=false;
				$jq('#operationLocation').focus();
			}
	}
if($jq('#operationDate').val()==''){
	errorMessages+="please Enter Date of Operation \n"
		if(status)
		{
			status=false;
			$jq('#operationDate').focus();
		}
}
else{

	var sysdate = new Date();
	var opDate = $jq("#operationDate").val();
	var date2 = new Date(opDate.split("-")[2], getMonthID(opDate
			.split("-")[1]) - 1, opDate.split("-")[0], 0, 0, 1, 0)
	if (sysdate < date2) {
		errorMessages += "Date of operation can not be Future Date.\n";
		if (status) {
			status = false;
			$jq("#operationDate").focus();
		}
	}
}
if($jq('#sterilizationCert').val()==''){
	errorMessages+="please enter Sterilization certificate issued by \n"
		if(status)
		{
			status=false;
			$jq('#sterilizationCert').focus();
		}
}
if (!$jq('input:radio[name=sterilCertFlag]').is(':checked')) {
	errorMessages+="please select Is necessary strilization certificate enclosed? \n"
		if(status)
		{
			status=false;
			$jq('input:radio[name=sterilCertFlag]').focus();
		}
}
if (!$jq('input:radio[name=livingChildFlag]').is(':checked')) {
	errorMessages+="please select I certify that I am having Two living children at the time of above operation. \n"
		if(status)
		{
			status=false;
			$jq('input:radio[name=livingChildFlag]').focus();
		}
}
if($jq('#wifeName').val()==''){
	errorMessages+="please enter Wife Name \n"
		if(status)
		{
			status=false;
			$jq('#wifeName').focus();
		}
}
if (!$jq('input:radio[name=wifePregFlag]').is(':checked')) {
	errorMessages+="please select I also certify that my wife is/i am not pregnant on this date.  \n"
		if(status)
		{
			status=false;
			$jq('input:radio[name=wifePregFlag]').focus();
		}
}
if (!$jq('input:radio[name=spouseAvailedFlag]').is(':checked')) {
	errorMessages+="please select I also certify that my spouse is not employed/employed but the incentive of Special Increment for the purpose is availed and will not be availed.  \n"
		if(status)
		{
			status=false;
			$jq('input:radio[name=spouseAvailedFlag]').focus();
		}
}
if(status){
	requestDetails={
			"param":"submitFPARequest",
			"operationLocation" : $jq('#operationLocation').val(),
			"operationDate": $jq('#operationDate').val(),
			"sterilizationCert": $jq('#sterilizationCert').val(),
			"sterilCertFlag": $jq('input:radio[name=sterilCertFlag]:checked').val(),
			"livingChildFlag": $jq('input:radio[name=livingChildFlag]:checked').val(),
			"wifeName": $jq('#wifeName').val()=='' ? '': $jq('#wifeName').val() ,
			"wifePregFlag": $jq('input:radio[name=wifePregFlag]:checked').val(),
			"spouseAvailedFlag": $jq('input:radio[name=spouseAvailedFlag]:checked').val()
	}
	
	$jq.post("fpaRequest.htm",requestDetails,function(html){
		$jq('#result').html(html);
		if($jq('.success').is(':visible')){
			//var requestType = $jq('#headTitle').html();
			var check=confirm(" FPA Request has been Successfully Sent...\n\nPlease click ok to 'Preview FPA Request 'Take print' from here\n\n");
			if(check){
			document.forms[0].requestId.value = $jq.trim(requestIDFpa);
			
		   document.forms[0].param.value = "requestDetails";
			document.forms[0].action = "workflowmap.htm";
			document.forms[0].submit();	
			}}
		
	 clearfpa();
		});
}
else
{
	alert (errorMessages);
}
}

function clearfpa(){
	$jq('input:radio').attr("checked",false);
	$jq('input').val('');
}