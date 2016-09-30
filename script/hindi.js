
function onselectCashAward(val)
{
	var temp = "";
	if(val == 'YES')
	{
		temp += "<div class='line'>";
		temp += "<div class='quarter leftmar' style='margin-left: 8px;'>Cash Award Applicable Amount<span class='failure'>*</span></div>";
		temp += "<div class='quarter'><input id=cashAwardAmount maxlength=100 onkeypress='return isNumberExp(event)'></input></div>";
		temp += "</div>";
		
		document.getElementById("cashApplicable").style.display = "block";
		document.getElementById("cashApplicable").innerHTML = temp;
	}
	if(val == 'NO')
	{
		document.getElementById("cashApplicable").style.display = "none";
	}
	
}
function onselectIncrement(val)
{
	var temp = "";
	if(val == 'YES')
	{
		temp += "<div class='line'>";
		temp += "<div class='quarter leftmar' style='margin-left: 8px;'>No.Of Increments<span class='failure'>*</span></div>";
		temp += "<div class='quarter'><input id=noOfIncrements maxlength=100></input></div>";
		temp += "</div>";
		
		document.getElementById("increment").style.display = "block";
		document.getElementById("increment").innerHTML = temp;
	}
	if(val == 'NO')
	{
		document.getElementById("increment").style.display = "none";
	}
}

function saveExams()
{
	var flag = true;
	var errorMessage = "";
	
	if($jq('#totalMarks').val() == '')
	{
		errorMessage += "\n Please Enter Total Marks Of Exam ";
		if(flag)
		{
			$jq('#totalMarks').focus();
		    flag = false;
		}
	}
	if($jq('#examName').val() == '')
	{
		errorMessage += "\n Please Enter Exam Name ";
		if(flag)
		{
			$jq('#examName').focus();
		    flag = false;
		}
	}
	if(flag)
	{
		var requestDetails = {
				"param" : "saveExams",
				"examName" : $jq('#examName').val(),
				"totalMarks" : $jq('#totalMarks').val(),
				"description" : $jq('#description').val(),
				"pk" :$jq('#pk').val()
		};
		$jq.post('hindi.htm',requestDetails, function(html){
			$jq("#result").html(html);
		});
		clearExams();
	}
	else {
		alert(errorMessage);
	}
}
function clearExams(){
	$jq('#examName').val('');	
	$jq('#totalMarks').val('');
	$jq('#description').val('');
	$jq('#pk').val('');
}
function editExams(eid)
{	
	$jq('#pk').val(eid);
    for(var i=0; i<hindiExamsJson.length; i++){
    	if(hindiExamsJson[i].examId == eid){
    		$jq('#examName').val(hindiExamsJson[i].examName);
    		$jq('#totalMarks').val(hindiExamsJson[i].totalMarks);
    		$jq('#description').val(hindiExamsJson[i].description);
    	}
    }
	/*$jq('#examName').val(examname);
	$jq('#totalMarks').val(totalMarks);
	$jq('#description').val(des);*/
}
function deleteExams(examid)
{
	var check = confirm("Do You Want To Delete? ");
	if(check)
	{
		var requestDetails = {
			"param" : "deleteExam",
			"examId" : examid
		};
		$jq.post('hindi.htm',requestDetails, function(html){
		$jq('#result').html(html);
		});
		clearExams();
	}
}
function onchangeSfid()
{
	$jq('#qualificationDiv').html('');	
	$jq('#empName').html('');
	var sfid = document.getElementById("sfid").value;
	var qualif = "";
	var temp = "";
	var empName = "";
	for(var i = 0; i<sfidNameJson.length; i++){
		if(sfidNameJson[i].key == sfid)
		{	
       $jq('#mothertongue').val(sfidNameJson[i].name);			
       $jq('#empName').append($jq('<div class="quarter bold">'+sfidNameJson[i].value+'</div>')).css("display","block");	
			
			
		}
	}
	
	
	for(var i = 0; i < empJsonList.length; i++)
	{
		if(empJsonList[i].id == sfid)
		{				
			//qualif +="<div class='line'>"+empJsonList[i].qual+"<input type=checkbox path='' id='check"+i+"' value='' onchange='javascript:checkQualif("+i+")'/></div></br>";
			qualif +="<div class='line'>"+empJsonList[i].qual+"</div></br>";
		}
	}
	$jq('#qualificationDiv').append($jq('<div class="line"><div class="quarter leftmar" style="margin-left: 8px;">'+
			'Qualification</div><div class="quarter bold">'+qualif+'</div> </div>')).css("display","block");
	
	
	if(sfid != '0')
	{
		var requestDetails = {
			"param" : "getCheckExamslist",	
			"sfid" : sfid
		};
		$jq.post('hindi.htm',requestDetails,function(html){
		$jq('#checkExams').html(html);
		});
	}
}
//for qual layout
/*function onchangeSfid()
{
	$jq('#qualificationDiv').html('');	
	$jq('#empName').html('');
	var sfid = document.getElementById("sfid").value;
	var qualif = "";
	var temp = "";
	var empName = "";
	for(var i = 0; i < empJsonList.length; i++)
	{
		if(empJsonList[i].id == sfid)
		{				
			qualif +=""+empJsonList[i].qual+"<input type=checkbox path='' id='check"+i+"' value='' onchange='javascript:checkQualif("+i+")'/>";			
			$jq('#mothertongue').val(empJsonList[i].language);			
			
			empName = empJsonList[i].name;
		}
	}
	$jq('#qualificationDiv').append($jq('<div class="line"><div class="quarter leftmar" style="margin-left: 8px;">'+
			'Qualification</div><div class="quarter bold" style="width: 50%;">'+qualif+'</div> </div>')).css("display","block");
	
	$jq('#empName').append($jq('<div class="quarter bold">'+empName+'</div>')).css("display","block");	
	
}*/
function checkQualif(id){
	if($jq('#check'+id).attr('checked')){
		//alert("hi");
	}
}
function qulificationEdit(sfid)
{
	$jq('#qualificationDiv').html('');	
	$jq('#empName').html('');
	var qualif = "";
	var temp = "";
	var empName = "";
	for(var i = 0; i < empJsonList.length; i++)
	{
		if(empJsonList[i].id == sfid)
		{				
			qualif +="<div class='line'>"+empJsonList[i].qual+"<input type=checkbox path='' id='check' value='' onchange='javascript:checkQualif()'/></div></br>";
			//qualif +="<input type=checkbox path='' id='check' value='' onchange='javascript:checkQualif()'/></br>";
			$jq('#mothertongue').val(empJsonList[i].language);
			
			empName = empJsonList[i].name;
		}
	}
	$jq('#qualificationDiv').append($jq('<div class="line"><div class="quarter leftmar" style="margin-left: 8px;">'+
			'Qualification</div><div class="quarter bold">'+qualif+'</div> </div>')).css("display","block");
	
	$jq('#empName').append($jq('<div class="quarter bold">'+empName+'</div>')).css("display","block");
			
}
function saveEmployeeDetails()
{
	   var flag = true;
	   var errorMessage = "";
	   var notEligibleId = "";
	   var eligibleId = "";
	   if($jq('#sfid').val() == '0')
	   {
		   errorMessage += "\n Please Select SFID";
		   if(flag)
		   {
			   $jq('#sfid').focus();
			   flag = false;
		   }
	   }	  
	   if($jq('#mothertongue').val() == '')
	   {
		   errorMessage += "\n Please Enter MotherTongue In Employee Master";
		   if(flag)
		   {
			   $jq('#mothertongue').focus();
			   flag = false;
		   }
	   }
	   $jq('#notEligibleExams').find('input:checkbox').each(
			   function(){
			           var checkId = $jq(this).attr('id');
			           if($jq('#'+checkId).is(':checked') == true){
			        	   notEligibleId += checkId+",";
			                   } else{
						        	  eligibleId += checkId+",";
						          }
			             } 
			         
			   )
	   
	   if(flag){
			var requestDetails = {
					"param" : "saveEmployeeDetails",
					"sfid" : $jq('#sfid').val(),
					"mothertongue" : $jq('#mothertongue').val(),						
					"remarks" : $jq('#remarks').val(),	
					"nonEligibleExamId" : notEligibleId,
					"eligibleExamId" : eligibleId,
					//"type" : $jq('#type').val(),
					"pk"  : $jq('#pk').val()
				};
				$jq.post('hindi.htm', requestDetails, function(html) {
					$jq("#result").html(html);				
				});
				clearEmployeeDetails();
		}else{
			alert(errorMessage);
		}
}
function clearEmployeeDetails(){
	$jq("input:checkbox").attr("checked", false);
	
	$jq('#mothertongue').val('');
	$jq('#sfid').val('0');
	$jq('#remarks').val('');	
	$jq('#qualificationDiv').html('');
	//$jq('#type').val('');
	$jq('#pk').val('');
	
}
function editEmployeeDetails(sfid,remarks,nonEligibleId)
{
	qulificationEdit(sfid);
	$jq('#sfid').val(sfid);	
	onchangeSfid();
	//$jq('#mothertongue').val(mothertongue);
	$jq('#remarks').val(remarks);
	//$jq('#type').val('1');
	$jq("input:checkbox").attr("checked", false);
	$jq('#pk').val(sfid);
	
	if(nonEligibleId == 1){
		for(var i=0; i<nonEligibleExamJsonList.length; i++){
			if(nonEligibleExamJsonList[i].sfid == sfid){
				$jq('#'+nonEligibleExamJsonList[i].examId).attr('checked', true);
				
			      }
	          }
          }
}
function deleteEmployeeDetails(sfid)
{
	var check=confirm("Do you want to delete ?");
	if(check){
		var requestDetails = {
				"param" : "deleteEmployeeDetails",
				"sfid"    : sfid
			};
			$jq.post('hindi.htm', requestDetails, function(html) {
				$jq("#result").html(html);
			});
			clearEmployeeDetails();
	}
}
function onchangeEmpType()
{		
	var flag = true;
	if (document.getElementById('empCategory').value == "select") {
		errorMessages = "Please Select Category ?\n";
		document.getElementById('empCategory').focus();
		flag = false;
	}
	if(flag){
		//var desg = $jq('#empCategory').val();
		
		$jq('#param').val('getDesignations');
		//$jq('#empCategory').val(desg);
		
		$jq.post('hindi.htm',$jq('#hindi').serialize(),function(html){
		$jq('#result1').html(html);
		});
	}
}
function onchangeCategory()
{
	$jq('#empType').val('0');
}
function saveExamConfigDetails()
{
	var flag = true;
	var errorMessages = "";
	var selectedValues = "";
	if($jq('#examId').val()=='0')
	{
		errorMessages += "\n Please Select Exam Name ";
		if(flag)
		{
			$jq('#examId').focus();
		    flag = false;
		}
	}
	if($jq('#mothertongue1').is(':checked')==false && $jq('#mothertongue2').is(':checked')==false )
	{
		errorMessages += "\n Please Select Mother Tongue ";
		if(flag)
		{			
		    flag = false;
		}
	}
	if($jq('#passMarks').val()=='' || $jq('#passMarks').val()=='0.0')
	{
		errorMessages += "\n Please Enter Pass % Of Marks ";
		if(flag)
		{
			$jq('#passMarks').focus();
		    flag = false;
		}
	}
	if(parseFloat($jq('#passMarks').val())<='0')
	{
		errorMessages += "\n Pass % Of Marks Must Be More Than 0 % ";
		if(flag)
		{
			$jq('#passMarks').focus();
		    flag = false;
		}
	}
	if(parseFloat($jq('#passMarks').val())>'100')
	{
		errorMessages += "\n Pass % Of Marks Must Be Less Than 100 % ";
		if(flag)
		{
			$jq('#passMarks').focus();
		    flag = false;
		}
	}
	if($jq('#empCategory').val()=='0')
	{
		errorMessages += "\n Please Select Employee Category";
		if(flag)
		{
			$jq('#empCategory').focus();
		    flag = false;
		}
	}
	if($jq('#empType').val()=='0')
	{
		errorMessages += "\n Please Select Employee Type ";
		if(flag)
		{
			$jq('#empType').focus();
		    flag = false;
		}
	}
	if($jq('#incrementApplicable1').is(':checked')==false && $jq('#incrementApplicable2').is(':checked')==false)
	{
		errorMessages += "\n Please Select Applicable For Increment ";
		if(flag)
		{			
		    flag = false;
		}
	}	
	if($jq('#noOfIncrements').val() =='' && $jq('input:radio[name=incrementApplicable]:checked').val()=='1')
	{
		errorMessages += "\n Please Enter No Of Increments ";
		if(flag)
		{			
		    flag = false;
		}
	}
	if($jq('#cashAwardApplicable1').is(':checked')==false && $jq('#cashAwardApplicable2').is(':checked')==false)
	{
		errorMessages += "\n Please Select Applicable For CashAward ";
		if(flag)
		{			
		    flag = false;
		}
	}
	/*if($jq('#cashAwardAmount').val() =='' && $jq('input:radio[name=cashAwardApplicable]:checked').val()=='1')
	{
		errorMessages += "\n Please Enter CashAward Amount ";
		if(flag)
		{			
		    flag = false;
		}
	}*/
	if(document.getElementById('SelectRight').options.length==0)
	{
		errorMessages += "\n Please Select Designation ";
		if(flag)
		{			
		    flag = false;
		}
	}
	for(var i = 0; i<document.getElementById('SelectRight').options.length; i++)
	{
		selectedValues += document.getElementById('SelectRight').options[i].value+ ",";
	}
	if(flag)
	{
			var requestDetails={
					   "param" : "saveExamConfigDetails",
						"examId" : $jq('#examId').val(),
						"mothertongue" : $jq('input:radio[name=mothertongue]:checked').val(),
						"passMarks" : $jq('#passMarks').val(),
						"empCategory" : $jq('#empCategory').val(),
						"empType" : $jq('#empType').val(),
						"incrementApplicable" : $jq('input:radio[name=incrementApplicable]:checked').val(),
						"noOfIncrements" : $jq('#noOfIncrements').val(),		
						"cashAwardApplicable" : $jq('input:radio[name=cashAwardApplicable]:checked').val(),
						"cashAwardAmount" : $jq('#cashAwardAmount').val(),
						"selectDesignation" : selectedValues,
						"pk" : $jq('#pk').val()
						/*"type" : $jq('#type').val(),
						"oldExamId" : $jq('#oldExamId').val(),
						"oldEmpCategory" : $jq('#oldEmpCategory').val(),
						"oldEmpType" : $jq('#oldEmpType').val()*/
			};
			$jq.post('hindi.htm',requestDetails, function(html){
			$jq('#result2').html(html);
			});
			clearExamConfigDetails();
	}
	else{
		alert(errorMessages);
	}
}
function clearExamConfigDetails(){
	$jq("input:radio").attr("checked", false);
	$jq('#examId').val('0');
	$jq('#passMarks').val('');
	$jq('#empCategory').val('0');
	$jq('#empType').val('0');
	$jq('#SelectLeft').val('');	
	$jq('#increment').html('');
	$jq('#cashApplicable').html('');
	
	$jq('#SelectLeft').find('option').remove().end();
	$jq('#SelectRight').find('option').remove().end();
	//$jq('#type').val('');
	$jq('#pk').val('');
}
function editExamConfigDetails(id,examId,mothertongue,passPercentage,categoryId,empType,incrementApplicable,noOfIncrements,cashAwardApplicable,cashAwardAmount)
{
	$jq('#pk').val(id);
	//$jq('#type').val('1');
	$jq('#examId').val(examId);
	if(mothertongue==1){
		$jq("#mothertongue1[value=" + mothertongue + "]").attr('checked', true);
		}
	else if(mothertongue==2){
			$jq("#mothertongue2[value=" + mothertongue + "]").attr('checked', true);
		}
	
	$jq('#passMarks').val(passPercentage);
	$jq('#empCategory').val(categoryId);
	$jq('#empType').val(empType);
	
	/*$jq('#oldExamId').val(oldExamId);
	$jq('#oldEmpCategory').val(oldCategoryId);
	$jq('#oldEmpType').val(oldEmpType);*/
	onchangeEmpType();
	
	if(incrementApplicable==1){		
		$jq("#incrementApplicable1[value=" + incrementApplicable + "]").attr('checked', true);
		onselectIncrement('YES');
		$jq('#noOfIncrements').val(noOfIncrements);
		
		}
	else if(incrementApplicable==2){		
			$jq("#incrementApplicable2[value=" + incrementApplicable + "]").attr('checked', true);
			onselectIncrement('NO');
		}
	if(cashAwardApplicable==1){		
		$jq("#cashAwardApplicable1[value=" + cashAwardApplicable + "]").attr('checked', true);
		//onselectCashAward('YES');
		//$jq('#cashAwardAmount').val(cashAwardAmount);
		
		}
	else if(cashAwardApplicable==2){		
			$jq("#cashAwardApplicable2[value=" + cashAwardApplicable + "]").attr('checked', true);
			onselectCashAward('NO');
		}
	
	
}
function deleteExamConfigDetails(id)
{
	var check = confirm("Do You Want To Delete? ");
	if(check){
		var requestDetails = {
				"param" : "deleteExamConfigDetails",
				"id" : id
			};
			$jq.post('hindi.htm',requestDetails, function(html){
			$jq('#result2').html(html);
			});
	}
}
function saveCashAwardDetails()
{
	var flag = true;
	var errorMessage = "";
	   if($jq('#examId').val() == '0')
	   {
		   errorMessage += "\n Please Select ExamName";
		   if(flag)
		   {
			   $jq('#examId').focus();
			   flag = false;
		   }
	   }	  
	   if($jq('#lowerPercentage').val() == '0.0' || $jq('#lowerPercentage').val() == '' )
	   {
		   errorMessage += "\n Please Enter Lower Percentage";
		   if(flag)
		   {
			   $jq('#lowerPercentage').focus();
			   flag = false;
		   }
	   }
	   if(parseFloat($jq('#lowerPercentage').val())  <=0)
	   {
		   errorMessage += "\n Lower Percentage Must More Than 0 %";
		   if(flag)
		   {
			   $jq('#lowerPercentage').val('');
			   $jq('#lowerPercentage').focus();
			   flag = false;
		   }
	   }
	   if(parseFloat($jq('#lowerPercentage').val()) >=100)
	   {
		   errorMessage += "\n Lower Percentage Must Be Less Than 100 %";
		   if(flag)
		   {
			   $jq('#lowerPercentage').val('');
			   $jq('#lowerPercentage').focus();
			   flag = false;
		   }
	   }
	   if($jq('#upperPercentage').val() == '0.0' || $jq('#upperPercentage').val() == '')
	   {
		   errorMessage += "\n Please Enter Upper Percentage";
		   if(flag)
		   {
			   $jq('#upperPercentage').focus();
			   flag = false;
		   }
	   }	
	   if(parseFloat($jq('#upperPercentage').val()) <=0)
	   {
		   errorMessage += "\n Upper Percentage Must Be More Than 0 %";
		   if(flag)
		   {
			   $jq('#upperPercentage').val('');
			   $jq('#upperPercentage').focus();
			   flag = false;
		   }
	   }
	   if(parseFloat($jq('#upperPercentage').val()) >100)
	   {
		   errorMessage += "\n Upper Percentage Must Be Less Than 100 %";
		   if(flag)
		   {
			   $jq('#upperPercentage').val('');
			   $jq('#upperPercentage').focus();
			   flag = false;
		   }
	   }
	  if($jq('#cashAwardAmount').val() == '')
	   {
		   errorMessage += "\n Please Enter CashAward Amount";
		   if(flag)
		   {
			   $jq('#cashAwardAmount').focus();
			   flag = false;
		   }
	   }
	  if(parseFloat($jq('#lowerPercentage').val()) > parseFloat($jq('#upperPercentage').val()))
	   {
		   errorMessage += "\n Lower Percentage Must Be Less Than Upper Percentage ";
		   if(flag)
		   {
			   $jq('#cashAwardAmount').focus();
			   flag = false;
		   }
	   }
	  if(parseFloat($jq('#lowerPercentage').val()) == parseFloat($jq('#upperPercentage').val()))
	   {
		   errorMessage += "\n Lower Percentage And Upper Percentage must Be Different ";
		   if(flag)
		   {
			   $jq('#cashAwardAmount').focus();
			   flag = false;
		   }
	   }
	   
	if(flag){
		requestDetails = {
				"param" : "saveCashAwardDetails",
				"examId" : $jq('#examId').val(),
				"lowerPercentage" : $jq('#lowerPercentage').val(),
				"upperPercentage" : $jq('#upperPercentage').val(),
				"cashAwardAmount" : $jq('#cashAwardAmount').val(),
				"pk" :$jq('#pk').val()
		};
		
		$jq.post('hindi.htm',requestDetails, function(html){
			$jq('#result').html(html);
			});
		clearCashAwardDetails();
	}
	else{
		alert(errorMessage);
	}
}
function clearCashAwardDetails(){
	$jq('#examId').val('0');		
	$jq('#lowerPercentage').val('');
	$jq('#upperPercentage').val('');
	$jq('#cashAwardAmount').val('');
	$jq('#pk').val('');
}
function editCashAwardDetails(id,examid,minPercentage,maxPercentage,amount)
{
	$jq('#pk').val(id);
	$jq('#examId').val(examid);
	$jq('#lowerPercentage').val(minPercentage);
	$jq('#upperPercentage').val(maxPercentage);
	$jq('#cashAwardAmount').val(amount);
	
}
function deleteCashAwardDetails(id)
{
	var check = confirm("Do You Want To Delete? ");
	if(check)
	{
		var requestDetails = {
				"param" : "deleteCashAwardDetails",
				"id" : id
			};
			$jq.post('hindi.htm',requestDetails, function(html){
			$jq('#result').html(html);
			});
	}
}
function getEligibleList()
{
	var flag = true;
	var errorMessage = "";
	   if($jq('#department').val() == '0')
	   {
		   errorMessage += "\n Please Select Department";
		   if(flag)
		   {
			   $jq('#department').focus();
			   flag = false;
		   }
	   }
	if(flag){
		$jq('#param').val('getNominationList');
		$jq.post('hindi.htm',$jq('#nomination').serialize(),function(html){
		$jq('#result').html(html);
		});
	}
	else{
		alert(errorMessage);
	}
}
/*function checkEligibility(sfid)
{
	var count =0;	
	$jq('#hindiNomination').find('tr:not(:first)').each(
			
			function(){
				//if($jq(this).find('td').eq(0).find('checkbox').val()==true){
					if($jq('#'+sfid).is(':checked')==true){
						
					count++;
			    }
	});
}*/
function saveNominationList()
{
	var count =0;	
	var innerjson = "";
	var flag = true;
	var errorMessage = "";
	 if($jq('#department').val() == '0')
	   {
		   errorMessage += "\n Please Select Department";
		   if(flag)
		   {
			   $jq('#department').focus();
			   flag = false;
		   }
	   }

	$jq('#hindiNomination').find('tr:not(:first)').each(
			
			function(){				
					var cid = $jq(this).find('td').eq(0).find('input:checkbox').attr('id');					
					if($jq('#'+cid).is(':checked')==true){						
						innerjson +=cid+",";
					}	
					
			}    
	)
	if(innerjson == ''){
		errorMessage += "\n Select Atleast one Record To Submit";
		flag = false;
	}
	if(flag){
		$jq('#param').val('saveNominationList');
		$jq('#sfid').val(innerjson);
		$jq.post('hindi.htm',$jq('#nomination').serialize(),function(html){
			$jq('#result').html('');
			$jq('#result').html(html);
		});
		clearNominationDetails();
	}
	else{
		alert(errorMessage);
		//changeNominationDept();
		getEligibleList()
	}
}
function changeNominationDept(){
	$jq('#result').html('');
}
function clearNominationDetails(){	
	$jq('#department').val('0');	
	$jq('#fromDate').val('');	
		
	//$jq('#cashApplicable').html('');
}
function saveResultDetails()
{
	var flag = true;
	var errorMessage = "";
	if($jq('#sfid').val()=='0')
	{
		errorMessage += "\n Please Select Sfid";
		if(flag){
			$jq('#sfid').focus();
			flag = false;
		}
	}
	if($jq('#examId').val()=='0')
	{
		errorMessage += "\n Please Select Exam Name";
		if(flag){
			$jq('#examId').focus();
			flag = false;
		}
	}
	if($jq('#totalMarks').val()=='')
	{
		errorMessage += "\n Please Enter Marks";
		if(flag){
			$jq('#totalMarks').focus();
			flag = false;
		}
	}
	
	if(flag){
		var eid =$jq('#examId').val();
		$jq('#param').val('saveResultDetails');
		$jq.post('hindi.htm',$jq('#hindiResult').serialize(),function(html){
		$jq('#result').html(html);	
		});
		clearResultDetails();
	}
	else{
		alert(errorMessage);
	}
}
function findMarksPercentage()
{	$jq('#formula').html('');
	var errorMessage = "";
	var configId = $jq('#examId').val();
	var totalMarks = 0;
	var flag = true;
	
	if($jq('#examId').val()!=0){
	for(var i=0;i<examConfigDetailsJSON.length;i++){
		if(examConfigDetailsJSON[i].id==configId){
			for(var j=0; j<hindiExamsJson.length; j++){
				if(hindiExamsJson[j].examId == examConfigDetailsJSON[i].examId){
					totalMarks = hindiExamsJson[j].totalMarks;
				}
			}
			
		}
	}
	
	
	var resultMarks = $jq('#totalMarks').val();
	if(parseInt(resultMarks) > totalMarks)
	{
		$jq('#totalMarks').val('');	
		$jq('#marksPercentage').val('');
		errorMessage += "\n Max Marks Of This Exam is "+totalMarks;		
		$jq('#totalMarks').focus();	
		flag = false;
		alert(errorMessage);
	}
	if(flag){
	
	if($jq('#examId').val() !='0'){	
		//temp = "[("+totalMarks+"/"+resultMarks+")*100";
	  var percentage = ( resultMarks /totalMarks ) * 100;
	  $jq('#marksPercentage').val(percentage);
	}
	}
	 $jq('#formula').append("[("+resultMarks+"/"+totalMarks+")*100]").css("display","block");
	}else{
		$jq('#totalMarks').val('');
		errorMessage += "\n Select Exam Name ";	
		alert(errorMessage);
	}
}
function clearResultDetails(){	
	$jq('#sfid').val('0');	
	$jq('#examId').val('0');	
	$jq('#totalMarks').val('');
	$jq('#marksPercentage').val('');
	$jq('#formula').html('');
	$jq('#pk').val('');
}
function editResultDetails(id,sfid,examId,totalMarks,percentage)
{
	$jq('#pk').val(id);
	$jq('#sfid').val(sfid);
	onchangeResultSfid();
	$jq('#examId').val(examId);
	$jq('#totalMarks').val(totalMarks);
	$jq('#marksPercentage').val(percentage);
}
function deleteResultDetails(id)
{
	var check = confirm("Do You Want To Delete? ");
	if(check){
		var requestDetails = {
				"param" : "deleteResultDetails",
				"id" : id
			};
			$jq.post('hindi.htm',requestDetails, function(html){
			$jq('#result').html(html);
			});
	}
}
function onchangeResultSfid()
{
	var flag = true;
	
	if (document.getElementById('sfid').value == "0") {
		errorMessages = "Please Select SFID ?\n";
		document.getElementById('sfid').focus();
		flag = false;
	}
	if(flag){
		var requestDetails = {
				"param" : "getEligibleExams",
				"sfid" : $jq('#sfid').val()
			};
			$jq.post('hindi.htm',requestDetails, function(html){
			$jq('#eligibleExams').html(html);
			});
	}
}

function saveIncentiveDetails()
{
	var flag = true;
	var errorMessage = "";
	
	if ($jq('#totalAmount').val() == '') {
		errorMessage += "Please Enter Total Amount\n";
		document.getElementById('totalAmount').focus();
		flag = false;
	}
	if ($jq('#noOfInst').val() == '') {
		errorMessage += "Please Enter No Of Instalments \n";
		document.getElementById('noOfInst').focus();
		flag = false;
	}
	if ($jq('#presentInst').val() == '') {
		errorMessage += "Please Enter Present Installment \n";
		document.getElementById('presentInst').focus();
		flag = false;
	}
	
	if ($jq('#fromDate').val()== '') {
		errorMessage += "Please Select Effective date \n";
		document.getElementById('fromDate').focus();
		flag = false;
	}
	if (parseInt($jq('#presentInst').val()) > parseInt($jq('#noOfInst').val())) {
		errorMessage += "Present Installment Should Not More Than No Of Installments \n";
		document.getElementById('presentInst').focus();
		flag = false;
	}
	
	if(flag){		
		$jq('#param').val('saveIncentiveDetails');
		$jq.post('hindi.htm',$jq('#hindiIncentive').serialize(),function(html){
		$jq('#result').html(html);	
		});
		clearIncentiveDetails();
	}
	else{
		alert(errorMessage);
	}
}
function clearIncentiveDetails()
{
//$jq('#sfidSearch').val('');
$jq('#fromDate').val('');
$jq('#presentInst').val('');
$jq('#noOfInst').val('');
$jq('#pk').val('');
$jq('#cashAwardAmount').val('');
/*$jq('#empDivSfid').html('');
$jq('#empDivName').html('');
$jq('#empDivDivision').html('');
$jq('#empDivDesignation').html('');*/
}
function editIncentiveDetails(id,sfid,fromDate,noOfInst,presentInst,totalAmount,creationDate,createdBy,cashAward)
{	$jq('#empDetailsDiv').css('display','block');
	$jq('#empFinanceDetailsDiv').css('display','block');
	
	$jq('#sfid').val(sfid);	
	$jq('#fromDate').val(fromDate);
	//$jq('#toDate').val(toDate);
	$jq('#noOfInst').val(noOfInst);
	$jq('#totalAmount').val(totalAmount);
	$jq('#presentInst').val(presentInst);
	$jq('#creationDate').val(creationDate);
	$jq('#createdBy').val(createdBy);
	$jq('#cashAwardAmount').val(cashAward);
	$jq('#pk').val(id);
}
function deleteIncentiveDetails(id,sfid)
{
	var check = confirm("Do You Want To Delete? ")
	if(check){
		var requestDetails = {
				"param" : "deleteIncentiveDetails",
				"id" : id,
				"sfidSearch" : sfid
			};
			$jq.post('hindi.htm',requestDetails, function(html){
			$jq('#result').html(html);
			});
	}
}


function getEmployeeDetails(){
	var flag = true;
	var errorMessage = "";
	var j=0;
	
	var sfid = $jq('#sfidSearch').val();
	//$jq('#incentiveSfid').val(sfid);
	//$jq('#sfid').val(sfid);
	if ($jq('#sfidSearch').val() == '') {
		errorMessage += "Please Enter SFID\n";
		document.getElementById('sfidSearch').focus();
		flag = false;
	}
	if($jq('#sfidSearch').val() != '')
	{
		for ( var i = 0; i < sfidList.length; i++)
		{
			if (sfidList[i].key == sfid.toUpperCase()) {
				j++;
				break;
			}
		}
		if (j != 0) {
			flag = true;
		} else {
			errorMessage += "Entered SFID doesn't exist\n";
			flag = false;
			$jq('#sfidSearch').val('');
			$jq('#sfidSearch').focus();
		}
	}
	if(flag){	
		$jq('#totalAmount').val('');
		$jq('#cashAwardAmount').val('');
		$jq('#fromDate').val('');
		$jq('#noOfInst').val('');
		$jq('#presentInst').val('');
		
		$jq('#param').val('getIncentiveEmpDetails');
		//$jq('#sfid').val(sfid);
		$jq.post('hindi.htm',$jq('#hindiIncentive').serialize(),function(html){
		$jq('#reminingIncentiveDiv').html(html);	
		});
		$jq('#payrunmonth').show();
		//$jq('#empDetailsDiv').css('display','block');
		$jq('#reminingIncentiveDiv').css('display','block');
		//$jq('#empFinanceDetailsDiv').css('display','block');
	}
	else{
		alert(errorMessage);
		hindiIncentiveDetails();
	}
	/*$jq('#sfid').val(sfid);
	$jq('#empDetailsDiv').css('display','block');
	$jq('#empFinanceDetailsDiv').css('display','block');*/
}

function setReadOnly(e, id)
{
	$jq('#'+id).attr("readonly", "true");
}
//for pay effct
/*function saveHindiIncentiveDetails(){
	var resultIds="";
	var status=true;
$jq('#empListTab').find('tr:not(:first)').each(
		function(){
			if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
				resultIds += $jq(this).find('td').eq(0).find('input:checkbox').attr('id')+",";
			}
		});
if(resultIds==""){
	status=false;
}
if(status){
	var requestDetails = {
			"param" : "savePayHindiIncentiveDetails",
			"fromDate":$jq('#fromDate').val(),
			"toDate":$jq('#toDate').val(),
			"resultIds" : resultIds
		};
	
	$jq.post('hindi.htm',requestDetails,function(html){
	$jq('#empListTab').html(html);	
	//$jq('#result').html(html);
	});
}
}*/