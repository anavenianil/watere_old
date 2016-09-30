var divid;

function PopulateValues(detailsjson){
	var flag=false;
	var status=true;
	var documentTitle="";
	var documentHeadTitle="";
	var inventoryNo="";
	var newb = document.createElement('a');
	newb.innerHTML = "NEW";
	newb.setAttribute("class", "quarterbutton");
	
	if(detailsjson!="")	{
		var length=0;
		var nomineeFlag=true;
		var count="";
		for(var key in detailsjson)
		{
			length=length+1;		
		}
		var addressArray=new Array();
		addressArray["0"]="1";
		addressArray["1"]="0";
		addressArray["2"]="0";
		var maindiv=document.getElementById("viewMasterData");
		for(var k=0;k<length-1;k++)
		{
			status=false;
			var id="";
			var fieldsetelement=document.createElement("fieldset");
			var innertable=document.createElement("table");
			innertable.style.width="100%";
			var i=0;
			if(detailsjson[length-1]=="nominee" && detailsjson[k]["Incontengensy Of"]!=""){
				nomineeFlag=false;
				
			}else{
				nomineeFlag=true;
				count="";
				for(var z=0;z<length-1;z++){
					if(detailsjson[z]["Incontengensy Of"]==detailsjson[k]["Family Member"]){
						count+=","+z;
					}
				}
			}
			var tbody=document.createElement("tbody");
			var  linediv=document.createElement("tr");			
			for(var keys in detailsjson[k])
			{
				if(keys!="id")
				{				
				var  firstdiv=document.createElement("td");
				var  seconddiv=document.createElement("td");
				if(i%2==0)
				{
					linediv=document.createElement("tr");
				}	
				if(detailsjson[k][keys]!=null && detailsjson[k][keys]!='Select' && detailsjson[k][keys]!='select'){
					var bold=document.createElement("b");
					bold.innerHTML=keys;
					firstdiv.appendChild(bold);
					
					if(detailsjson[k][keys]!=null){
						
						seconddiv.innerHTML="&nbsp;<b >:&nbsp;</b>"+detailsjson[k][keys];
					}else
						seconddiv.innerHTML="&nbsp;<b >:&nbsp;</b>";	
					
					
					firstdiv.style.width="25%";
					//firstdiv.style.fontFamily="Times New Roman";
					firstdiv.style.lineHeight="24px";
					firstdiv.style.verticalAlign="top";
					seconddiv.style.width="25%";
					//seconddiv.style.fontFamily="Times New Roman";
					seconddiv.style.lineHeight="24px";
					seconddiv.style.verticalAlign="top";
					linediv.appendChild(firstdiv);
					linediv.appendChild(seconddiv);
					//valuediv.innerHTML="<b>"+keys+"</b> : "+detailsjson[k][keys];
				}else{
					//valuediv.innerHTML="<b>"+keys+"</b> : "+"";
					var bold=document.createElement("b");
					bold.innerHTML=keys;
					firstdiv.appendChild(bold);
					seconddiv.innerHTML="&nbsp;<b >:&nbsp;</b>";
					if(detailsjson[k][keys]!=null)
					seconddiv.innerHTML="&nbsp;<b >:&nbsp;</b>" +detailsjson[k][keys];
					else
					seconddiv.innerHTML="&nbsp;<b >:&nbsp;</b>";
					firstdiv.style.width="25%";
					//firstdiv.style.fontFamily="Times New Roman";
					firstdiv.style.lineHeight="24px";
					firstdiv.style.verticalAlign="top";
					seconddiv.style.width="25%";
					//seconddiv.style.fontFamily="Times New Roman";
					seconddiv.style.lineHeight="24px";
					seconddiv.style.verticalAlign="top";
					linediv.appendChild(firstdiv);
					linediv.appendChild(seconddiv);
					linediv.appendChild(firstdiv);
					linediv.appendChild(seconddiv);
				}						 
				i++;
				tbody.appendChild(linediv);
				innertable.appendChild(tbody);
				if(detailsjson[length-1]=="address")
				{
					if(i==2){
						var font=document.createElement("font");
						font.innerHTML=detailsjson[k][keys];
						font.setAttribute("color","green");
						var legend=document.createElement("legend");
						legend.appendChild(font);					
						fieldsetelement.appendChild(legend);
					}
				}
				fieldsetelement.appendChild(innertable);
				
				}else
				{
					id=detailsjson[k].id;
				}
			}
			
				if(nomineeFlag)
					var buttondiv=document.createElement("div");
				var elb = document.createElement('a');
				elb.innerHTML = "Edit";
			if(detailsjson[length-1]=="family"){
				elb.setAttribute("href", "javascript:editFamily('" +id+ "','"+k+"',"+(length-1)+")");
				newb.setAttribute("href", "javascript:editFamily('','',"+(length-1)+")");
				documentHeadTitle="Update Family Details";
				documentTitle="Update Family Details";
				flag=true;
			}else if(detailsjson[length-1]=="nominee"){	
				if(nomineeFlag)
					elb.setAttribute("href", "javascript:editNomineeDetails('" +id+ "','"+k+"','"+count+"',"+(length-1)+")");
				newb.setAttribute("href", "javascript:editNomineeDetails('','','',"+(length-1)+")");
				documentHeadTitle="Update Nominee Details";
				documentTitle="Update Nominee Details";
				flag=true;
			}else if(detailsjson[length-1]=="training"){	
				elb.setAttribute("href", "javascript:editTrainingDetails('" +JSON.stringify(detailsjson[k])+ "','"+k+"')");
				documentHeadTitle="View Training Details";
				documentTitle="View Trainig Details";
			}else if(detailsjson[length-1]=="address"){
					editflag="";
					if(detailsjson[k]["Address Type"]=="HOMETOWN1" ){
						editflag="2";
					}
					if(detailsjson[k]["Address Type"]=="HOMETOWN2"){
						editflag="3";
					}
					if(detailsjson[k]["Address Type"]=="CGHS ADDRESS"){
						editflag="4";
					}
					if(detailsjson[k]["Address Type"]=="PERMANENT")	{
						editflag="0";
					}
					if(detailsjson[k]["Address Type"]=="PRESENT"){	
						editflag="1";
					}
				elb.setAttribute("href", "javascript:editAddressDetails('" +id+ "','"+editflag+"',"+3+")");	
				documentHeadTitle="Update Address Details";
				documentTitle="Update Address Details";

			}else if(detailsjson[length-1]=="qualification"){
				documentHeadTitle="View Qualification Details";
				documentTitle="View Qualification Details";
			}else if(detailsjson[length-1]=="training"){
				documentHeadTitle="View Training Details";
				documentTitle="View Training Details";
			}else if(detailsjson[length-1]=="passport"){
				documentHeadTitle="View Passport Details";
				documentTitle="View Passport Details";
			}else if(detailsjson[length-1]=="empExp"){
				documentHeadTitle="View Employee Experience Details";
				documentTitle="View Employee Experience Details";
			}else if(detailsjson[length-1]=="PreOrgnDetails"){
				documentHeadTitle="View Pre-Organisation Details";
				documentTitle="View Pre-Organisation Details";
			}else if(detailsjson[length-1]=="award"){
				documentHeadTitle="View Award Details";
				documentTitle="View Award Details";
			}else if(detailsjson[length-1]=="quarter"){
				documentHeadTitle="View Quarter Details";
				documentTitle="View Quarter Details";
			}else if(detailsjson[length-1]=="retirement"){
				documentHeadTitle="View Retirement Details";
				documentTitle="View Retirement Details";
			}
			
			elb.setAttribute("maxlength", "45");
			elb.className="quarterbutton";		
			//buttondiv.className="apprightbutton";
			//buttondiv.setAttribute("style", "float:right");
			
//			buttondiv.setAttribute("id","el"+k);
			if(nomineeFlag){
				
				    buttondiv.setAttribute("id","el"+k);
					buttondiv.className="apprightbutton";
					buttondiv.appendChild(elb);
				
			}
			var flag="";
			if(detailsjson[length-1]=="address"){
				if(detailsjson[k]["Address Type"]=="HOMETOWN1" ){
					addressArray["2"]="1";
					flag="2";
				}
				if(detailsjson[k]["Address Type"]=="HOMETOWN2"){
					addressArray["3"]="1";
					flag="3";
				}
				if(detailsjson[k]["Address Type"]=="CGHS ADDRESS"){
					addressArray["4"]="1";
					flag="4";
				}
				if(detailsjson[k]["Address Type"]=="PERMANENT")	{
					addressArray["0"]="1";
					flag="0";
				}
				if(detailsjson[k]["Address Type"]=="PRESENT"){	
					addressArray["1"]="1";
					flag="1";
				}
				addressArray[i]=detailsjson[k]["Address Type"];								
				if(!(detailsjson[k]["Address Type"]!="" && detailsjson[k]["Address Type"]=="HOMETOWN2")){
						if(nomineeFlag)
							fieldsetelement.appendChild(buttondiv);
					}
			}else if(detailsjson[length-1]=="inventHolder"){
				fieldsetelement.appendChild(buttondiv);
			}else if(detailsjson[length-1]=="family"){
				fieldsetelement.appendChild(buttondiv);
			}
			//fieldsetelement.appendChild(buttondiv);
			//innertable.appendChild(fieldsetelement);	
				if(detailsjson[length-1]=="family")
					innertable.setAttribute("id", "createFamily"+k);		
				else if(detailsjson[length-1]=="nominee")
					innertable.setAttribute("id", "createNomineeDetails"+k);
				else if(detailsjson[length-1]=="training")			
					innertable.setAttribute("id", "createTrainig"+k);
				else if(detailsjson[length-1]=="address")
					innertable.setAttribute("id", "createAddressDetails"+flag);			
			
			maindiv.appendChild(fieldsetelement);

		}
		if(detailsjson[length-1]=="address"){
		for(var g=0;g<addressArray.length;g++){
			if(addressArray[g]=="0")
			{
				var fieldsetelement=document.createElement("fieldset");
				var elb = document.createElement('a');
				elb.innerHTML = "Add";
				var buttondiv=document.createElement("div");
				buttondiv.setAttribute("id","el"+k);
				elb.setAttribute("maxlength", "45");
				elb.className="quarterbutton";
				elb.setAttribute("href", "javascript:editAddressDetails('','"+g+"',3,'"+(g+1)+"')");	
				buttondiv.className="apprightbutton";
				buttondiv.appendChild(elb);
				buttondiv.setAttribute("id","el"+g);
				var elediv=document.createElement("div");
				if(g==0)
					elediv.innerHTML="PERMANENT";
				if(g==1)
					elediv.innerHTML="PRESENT";
				if(g==2)
					elediv.innerHTML="HOMETOWN1";
				if(g==3)
					elediv.innerHTML="HOMETOWN2";
				elediv.setAttribute("id", "createAddressDetails"+g);	
				fieldsetelement.appendChild(elediv);
				fieldsetelement.appendChild(buttondiv);
				maindiv.appendChild(fieldsetelement);
			}
		}
	}
}		
	if(status){
			var maindiv=document.getElementById("viewMasterData");
			maindiv.innerHTML="Nothing found to display.";
	}
	if(detailsjson!=null){
//		if(flag || detailsjson[0]=="family" || detailsjson[0]=="nominee"){
//			document.getElementById('newbutton').appendChild(newb);
//			if(detailsjson[0]=="family"){
//				newb.setAttribute("href", "javascript:editFamily('','',"+(length-1)+")");
//			}
//			if(detailsjson[0]=="nominee"){
//				newb.setAttribute("href", "javascript:editNomineeDetails('','',"+(length-1)+")");
//			}
//			document.getElementById('newbutton').style.display="block";
//		}
		if(detailsjson[0]=="address"){
				documentTitle="Update Address Details";
				documentHeadTitle="Update Address Details";
		}
		if(detailsjson[0]=="family"){
			documentHeadTitle="Update Family Details";
			documentTitle="Update Family Details";
		}else if(detailsjson[0]=="nominee"){	
			documentHeadTitle="Update Nominee Details";
			documentTitle="Update Nominee Details";
		}else if(detailsjson[0]=="training"){	
			documentHeadTitle="View Training Details";
			documentTitle="View Training Details";
		}else if(detailsjson[0]=="qualification"){
			documentHeadTitle="View Qualification Details";
			documentTitle="View Qualification Details";
		}else if(detailsjson[0]=="passport"){
			documentHeadTitle="View Passport Details";
			documentTitle="View Passport Details";
		}else if(detailsjson[0]=="empExp"){
			documentHeadTitle="View Employee Experience Details";
			documentTitle="View Employee Experience Details";
		}else if(detailsjson[0]=="PreOrgnDetails"){
			documentHeadTitle="View Pre-Organisation Details";
			documentTitle="View Pre-Organisation Details";
		}else if(detailsjson[0]=="award"){
			documentHeadTitle="View Award Details";
			documentTitle="View Award Details";
		}else if(detailsjson[0]=="quarter"){
			documentHeadTitle="View Quarter Details";
			documentTitle="View Quarter Details";
		}else if(detailsjson[0]=="retirement"){
			documentHeadTitle="View Retirement Details";
			documentTitle="View Retirement Details";
		}

	}
	
	document.getElementById('documentheadtitle').innerHTML=documentHeadTitle;
	document.title=documentTitle;
	
}

function editTrainingDetails(json,no) {
var divid="createTrainig"+no;	
	document.forms[0].param.value="editTrainingDetails";	
	new Ajax.Updater(divid, 'training.htm', {		onComplete : function() {
	editTraining('','','','','','','','','',json);		},
	parameters : Form.serialize(document.forms[0]),	
	asynchronous : false,		
	evalScripts : true
	});
}
function editAddressDetails(id,no,noIds,addressTypeId) {
	addressID = id;	
	divid="createAddressDetails"+no;	
		document.forms[0].param.value="editAddressDetails";	
		new Ajax.Updater(divid, 'address.htm?id='+id+'&addressTypeId='+no, {		onComplete : function() {
			if(document.getElementById('addressTypeId')!=null)	{
				document.getElementById('addressTypeId').value=''+(parseInt(no)+1);
				document.getElementById('addressTypeId').disabled=true;
			}
				for(var i=0;i<=noIds;i++){
					if(document.getElementById('el'+i)!=null)
						document.getElementById('el'+i).style.display="none";
				}
				if(document.getElementById('addressTypeId')=="2" || document.getElementById('addressTypeId')[document.getElementById('addressTypeId').selectedIndex].text=="PRESENT"){
					document.getElementById('dispensary').style.display = "block";
				}else
					document.getElementById('dispensary').style.display = "none";
					},
		parameters : Form.serialize(document.forms[0]),	
		asynchronous : false,		
		evalScripts : true
		});
}
function clearDelegation() {
	document.getElementById('assignedEmp').value = "select";
	document.getElementById('requestTypeID').value = "select";
	document.getElementById('instanceId').value="select";
	document.getElementById('requestID').value="";
	document.getElementById('remarks').value="";
}
function searchRequests() {
	if(document.getElementById("assignedEmp").value!="select" || document.getElementById("requestTypeID").value!="select" || document.getElementById("requestID").value!="")
	{
		document.forms[0].param.value="searchRequest";	
		new Ajax.Updater('searchList', 'workflow.htm', {		
			onComplete : function() {
		},
		parameters : Form.serialize(document.forms[0]),	
		asynchronous : false,		
		evalScripts : true
		});
		
	}else
	{
		alert("Please select SFID or Request Type Id or Enter Request ID");
	}
}
function saveRequestTypes() {
	var requestFlags = "";
	$jq("#requestTypeList tr:not(:first)").each(function() {	
		if($jq(this).find("td").eq(0).find(":input:checkbox").is(':checked')){
			requestFlags+=$jq(this).find("td").eq(0).find(":input:checkbox").attr('id')+"_1,";
		} else {
			requestFlags+=$jq(this).find("td").eq(0).find(":input:checkbox").attr('id')+"_0,";
		}
	});

	var requestDetails = {
		"type" : "",
		"param" : "saveRequest",
		"requestFlags":requestFlags				
	};
	$jq.ajax( {
		type : "POST",
		url : "master.htm",
		data : requestDetails,
		cache : false,
		success : function(html) {
			$jq('#result').html(html);
		}
	});		
}


//view EmpTreeDetails
function PopulateEmpTree(empTreeJson){
	document.title="Employee Tree Details";
	document.getElementById('documentheadtitle').innerHTML="Employee Details";
	
	var status=true;
	if(empTreeJson!=""){
		var len=0;
		for(var key in empTreeJson){
			len+=1;
		}
		for(var z=0;z<len;z++){
			if(empTreeJson[z]!="")	{
				var length=0;
				var count="";
				for(var key in empTreeJson[z])
				{
					length=length+1;		
				}
				var fieldsetelement="";
				for(var k=0;k<length-1;k++)
				{
					var id="";
					status=false;
					if(fieldsetelement==""){
						fieldsetelement=document.createElement("fieldset");
						var elLegend=document.createElement("legend");
						var elStrong=document.createElement("strong");
						if(empTreeJson[z][length-1]=="address")
							elStrong.innerHTML="<font color='green'>Address Details</font>";
						else if(empTreeJson[z][length-1]=="family")
							elStrong.innerHTML="<font color='green'>Family Details</font>";
						else if(empTreeJson[z][length-1]=="nominee")
							elStrong.innerHTML="<font color='green'>Nominee Details</font>";
						else if(empTreeJson[z][length-1]=="training")
							elStrong.innerHTML="<font color='green'>Training Details</font>";
						else if(empTreeJson[z][length-1]=="qualification")
							elStrong.innerHTML="<font color='green'>Qualification Details</font>";
						else if(empTreeJson[z][length-1]=="passport")
							elStrong.innerHTML="<font color='green'>Passport Details</font>";
						else if(empTreeJson[z][length-1]=="PreOrgnDetails")
							elStrong.innerHTML="<font color='green'>Pre-Organisation Details</font>";
						else if(empTreeJson[z][length-1]=="award")
							elStrong.innerHTML="<font color='green'>Award Details</font>";
						else if(empTreeJson[z][length-1]=="empExp")
							elStrong.innerHTML="<font color='green'>Employee Experience Details</font>";
						else if(empTreeJson[z][length-1]=="quarter")
							elStrong.innerHTML="<font color='green'>Quarter Details</font>";
						elLegend.appendChild(elStrong);
						fieldsetelement.appendChild(elLegend);
					}
					var innerdiv=document.createElement("div");
					var  valuediv=document.createElement("table");
					valuediv.style.width="100%";
					var linediv="";
					var hrdiv="";
					var i=1;
					var tbody=document.createElement("tbody");
					var  row=document.createElement("tr");
					for(var keys in empTreeJson[z][k])
					{
						if(keys!="id" && keys!="SFID")
						{						
						var  firstdiv=document.createElement("td");
						var  seconddiv=document.createElement("td");
						if(empTreeJson[z][k][keys]!=null && empTreeJson[z][k][keys]!='Select' && empTreeJson[z][k][keys]!='select'){
							
							var bold=document.createElement("b");
							bold.innerHTML=keys;
							firstdiv.appendChild(bold);
							if(empTreeJson[z][k][keys]!=null)
							seconddiv.innerHTML="&nbsp;<b style='float:left;'>:&nbsp;</b>"+"<div  style='float:left; width:93%;'>"+empTreeJson[z][k][keys]+"</div>";
							else
							seconddiv.innerHTML="&nbsp;<b  style='float:left;'>:&nbsp;</b>";	
							firstdiv.style.width="25%";
							//firstdiv.style.fontFamily="Times New Roman";
							firstdiv.style.lineHeight="24px";
							firstdiv.style.verticalAlign="top";
							seconddiv.style.width="25%";
							//seconddiv.style.fontFamily="Times New Roman";
							seconddiv.style.lineHeight="24px";
							seconddiv.style.verticalAlign="top";
							row.appendChild(firstdiv);
							row.appendChild(seconddiv);
						}else{
							var bold=document.createElement("b");
							bold.innerHTML=keys;
							firstdiv.appendChild(bold);
							seconddiv.innerHTML="&nbsp;<b  style='float:left;'>:&nbsp;</b>";	
							firstdiv.style.width="25%";
							//firstdiv.style.fontFamily="Times New Roman";
							firstdiv.style.lineHeight="24px";
							firstdiv.style.verticalAlign="top";
							seconddiv.style.width="25%";
							//seconddiv.style.fontFamily="Times New Roman";
							seconddiv.style.lineHeight="24px";
							seconddiv.style.verticalAlign="top";
							row.appendChild(firstdiv);
							row.appendChild(seconddiv);
						}
						if(i%2==0)
						{
							row=document.createElement("tr");
						}			 
						i++;
						tbody.appendChild(row);
						valuediv.appendChild(tbody);
						fieldsetelement.appendChild(valuediv);
												
						}else{
							id=empTreeJson[z][k].id;
						}
					}
						if(k!=length-2){
							linediv=document.createElement("div");
							//linediv.setAttribute("style", "width:100%;float:left");
							linediv.style.width="100%";
							hrdiv=document.createElement("div");
							//hrdiv.setAttribute("style","border-bottom: 1px solid gray");
							hrdiv.style.borderBottom="1px solid gray";
							linediv.appendChild(hrdiv);
							fieldsetelement.appendChild(linediv);
						}
						
						innerdiv.appendChild(fieldsetelement);
						var maindiv=document.getElementById("viewMasterData");
						maindiv.appendChild(innerdiv);
				}
			}
			
		}
	}
	if(status){
		var maindiv=document.getElementById("viewMasterData");
		maindiv.innerHTML="Nothing found to display.";
	}
	//document.getElementById('newbutton').style.display="block";
}

//Quarter Request

function quarterRequest(type) {
	document.forms[0].param.value = "home";
	document.forms[0].type.value = type;	
	document.forms[0].action = "quarterReq.htm";
	document.forms[0].submit();
}

function checkAllotment(){
	if ($jq('input:radio[name=allotment]:checked').val() == "Y"){
		$jq('#allotmentDiv').show();
	} else {
		$jq('#allotmentDiv').hide();
	}
}
function selectedQuarter(){
	if($jq("#eligibility").val()=='A' ||$jq("#eligibility").val()=='B' || $jq("#eligibility").val()=='C' || $jq("#eligibility").val()=='D'){
		$jq("#entitledAD").attr('checked',true);
	}else{
		$jq("#entitledE").attr('checked',true);
	}
	if($jq("#eligibility").val()=='select'){
		$jq("#entitledAD").attr('checked',false);
		$jq("#entitledE").attr('checked',false);
	}
}
function clearQuarterRequest() {
	$jq('#eligibility').val('');
	$jq('#priorityDate').val('');
	$jq('#entitled').val('');
	$jq('#saToRm').val('');
	$jq('#otherDept').val('');
	$jq('#debarred').val('');
	$jq('#suretyName').val('');
	$jq('#presentDesig').val('');
	$jq('#officeName').val('');
	$jq('#retirement').val('');
	$jq('#type').val('');
	$jq('input:radio[id=rent]').attr('checked', 'checked');
	$jq('input:radio[id=pmt]').attr('checked', 'checked');
	$jq('input:radio[id=suretyReq2]').attr('checked', 'checked');
	$jq('#quarterOption').val('');
	$jq('#remarks').val('');
//	$jq('#eligibility').text('');
//	$jq('#eligibility').append($jq("<option></option>").attr("value",'').text('Select'));
	$jq('#quarterResult').html('');
	/*$jq('#eligibility').text('');
	$jq('#eligibility').append($jq("<option></option>").attr("value",'').text('Select'));
	
	$jq('#sfID').val(' ');
	$jq('#sfidName').html(' ');
	$jq('#sfidDesignation').html(' ');
	$jq('#sfidDOA').html(' ');
	$jq('#quarterResult').html(' ');
	*/
	
}
function submitQuarterRequest(quaType) {
	var errorMessage = "";
	var status = true;
	if($jq('#eligibility').val() == "select") {
		errorMessage += "Please select Eligible Quarter type.\n";
		if(status)
			status = false;
	}
	if($jq('#priorityDate').val() == "") {
		errorMessage += "Please Select Priority Date.\n";
		if(status)
			status = false;
	}
	if(!$jq('input:radio[name=entitled]').is(':checked')) {
		errorMessage += "Please Select Entitled Type.\n";
		if(status)
			status = false;
	}
	if(!$jq('input:radio[name=allotment]').is(':checked')) {
		errorMessage += "Please Select Allotment Type.\n";
		if(status)
			status = false;
	}
	if(!$jq('input:radio[name=rentFree]').is(':checked')) {
		errorMessage += "Please Select Rent Free Type.\n";
		if(status)
			status = false;
	}
	if(!$jq('input:radio[name=suretyType]').is(':checked')) {
		errorMessage += "Please Select Surety Type.\n";
		if(status)
			status = false;
	}
	if($jq('#saToRm').val() == "" && $jq('input:radio[name=allotment]:checked').val() == 'Y') {
		errorMessage += "Please Enter SA to RM.\n";
		if(status){
			status = false;
			$jq('#saToRm').focus();
		}
	}
	if($jq('#otherDept').val() == ""&& $jq('input:radio[name=allotment]:checked').val() == 'Y') {
		errorMessage += "Please Enter Other Govt.Dept.\n";
		if(status){
			status = false;
			$jq('#otherDept').focus();
		}
	}
	if($jq('#suretyDiv').is(':visible')) {
		if($jq('#suretyName').val() == "") {
			errorMessage += "Please Enter Surety Name.\n";
			if(status){
				status = false;
				$jq('#suretyName').focus();
			}
		}
		if($jq('#presentDesig').val() == "") {
			errorMessage += "Please Enter Present Designation.\n";
			if(status){
				status = false;
				$jq('#presentDesig').focus();
			}
		}
		if($jq('#officeName').val() == "") {
			errorMessage += "Please Enter Office Name.\n";
			if(status){
				status = false;
				$jq('#officeName').focus();
			}
		}
		if($jq('#retirement').val() == "") {
			errorMessage += "Please Select Retirement Date.\n";
			if(status){
				status = false;
				$jq('#retirement').focus();
			}
		}
	}
	
	if(quaType != "new" || quaType != "change") {
		if($jq('#sfID').val() == "") {
			errorMessage += "Please Enter SFID.\n";
			if(status) {
				status = false;
				$jq('#sfID').focus();
			}
		}
	}
	else 
	{
		var sysdate = new Date();
		var retDate = $jq("#retirement").val();
		var date2 = new Date(retDate.split("-")[2], getMonthID(retDate.split("-")[1]) - 1, retDate.split("-")[0], 0, 0, 1, 0)
		if (sysdate > date2) {
			errorMessage += "Date of Retirement can not be Past Date.\n";
			if (status) {
				status = false;
				$jq("#retirement").focus();
			}
		}
	}

	if(status) {
		if(quaType == "new" ) {
			var requestDetails = {			
				"eligibility" : $jq('#eligibility').val().toUpperCase(),
				"priorityDate" : $jq('#priorityDate').val(),
				"entitled" : $jq('input:radio[name=entitled]:checked').val(),
				"allotment" : $jq('input:radio[name=allotment]:checked').val(),
				"saToRm" : $jq('#saToRm').val().toUpperCase(),
				"otherDept" : $jq('#otherDept').val().toUpperCase(),
				"debarred" : $jq('#debarred').val(),
				"rentFree" : $jq('input:radio[name=rentFree]:checked').val(),
				"suretyType" : $jq('input:radio[name=suretyType]:checked').val(),
				"suretyName" : $jq('#suretyName').val().toUpperCase(),
				"presentDesig" : $jq('#presentDesig').val().toUpperCase(),
				"officeName" : $jq('#officeName').val().toUpperCase(),
				"retirement" : $jq('#retirement').val(),
				"type" : $jq('#type').val(),
				"quarterOption" : $jq('#quarterOption').val(),
				"remarks" : $jq('#remarks').val(),
				"param" : "submitNewRequest"
			};
			$jq.post('quarterReq.htm', requestDetails, function(html) {
				$jq("#result").html(html);
				if ($jq('.success').is(':visible')) {
					var requestType = $jq('#headTitle').html();
					var check = confirm(" " + requestType + " has been Successfully Sent...\n\nPlease click OK to 'Preview " + requestType + " 'Take print' from here\n\n");
					if(check) {
						document.forms[0].requestId.value = $jq.trim(requestIDq);
						document.forms[0].param.value = "requestDetails";
						document.forms[0].action = "workflowmap.htm";
						document.forms[0].submit();	
					}
					clearQuarterRequest();
				}
			});
		}
		else if(quaType == "change") {
			var requestDetails = {			
				"eligibility" : $jq('#eligibility').val().toUpperCase(),
				"priorityDate" : $jq('#priorityDate').val(),
				"entitled" : $jq('input:radio[name=entitled]:checked').val(),
				"allotment" : $jq('input:radio[name=allotment]:checked').val(),
				"saToRm" : $jq('#saToRm').val().toUpperCase(),
				"otherDept" : $jq('#otherDept').val().toUpperCase(),
				"debarred" : $jq('#debarred').val(),
				"rentFree" : $jq('input:radio[name=rentFree]:checked').val(),
				"suretyType" : $jq('input:radio[name=suretyType]:checked').val(),
				"suretyName" : $jq('#suretyName').val().toUpperCase(),
				"presentDesig" : $jq('#presentDesig').val().toUpperCase(),
				"officeName" : $jq('#officeName').val().toUpperCase(),
				"retirement" : $jq('#retirement').val(),
				"type" : $jq('#type').val(),
				"param" : "submitChangeRequest"
			};
			$jq.post('quarterReq.htm', requestDetails, function(html) {
				$jq("#result").html(html);
				if ($jq('.success').is(':visible')) {

					var requestType = $jq('#headTitle').html();
					var check = confirm(" " + requestType + " has been Successfully Sent...\n\nPlease click OK to 'Preview " + requestType + " 'Take print' from here\n\n");
					if(check) {
						document.forms[0].requestId.value = $jq.trim(requestIDq);
						document.forms[0].param.value = "requestDetails";
						document.forms[0].action = "workflowmap.htm";
						document.forms[0].submit();	
					}
					clearQuarterRequest();
				
					
					
					clearQuarterRequest();
				}
			});
		}
		else
		{
			var requestDetails = {			
				"sfID" : $jq('#sfID').val().toUpperCase(),	
				"eligibility" : $jq('#eligibility').val().toUpperCase(),
				"priorityDate" : $jq('#priorityDate').val(),
				"entitled" : $jq('input:radio[name=entitled]:checked').val(),
				"allotment" : $jq('input:radio[name=allotment]:checked').val(),
				"saToRm" : $jq('#saToRm').val().toUpperCase(),
				"otherDept" : $jq('#otherDept').val().toUpperCase(),
				"debarred" : $jq('#debarred').val(),
				"rentFree" : $jq('input:radio[name=rentFree]:checked').val(),
				"suretyType" : $jq('input:radio[name=suretyType]:checked').val(),
				"suretyName" : $jq('#suretyName').val().toUpperCase(),
				"presentDesig" : $jq('#presentDesig').val().toUpperCase(),
				"officeName" : $jq('#officeName').val().toUpperCase(),
				"retirement" : $jq('#retirement').val(),
				"type" : $jq('#type').val(),
				"param" : "submitNewRequest"
			};
			$jq.post('quarterReq.htm', requestDetails, function(html) {
				$jq("#result").html(html);
				if ($jq('.success').is(':visible')) {
					clearQuarterRequest();
				}
			});
		}	
	}
	else
		alert(errorMessage);
}

function hqRequest(){
	document.forms[0].param.value = "home";
	document.forms[0].type.value="hqRequest";	
	document.forms[0].action = "hqReq.htm";
	document.forms[0].submit();
}
function clearHQRequest(){
	$jq("#course").val('');
	$jq("#fromDate").val('');
	$jq("#toDate").val('');
	$jq('input:radio').attr('checked',false);
}
function submitHQRequest(desigID){
	var errorMessage = "";
	var status = true;
	if($jq('#course').val()==""){
		errorMessage += "Please Select Higher Qualification.\n";
		if(status){
			status = false;
			$jq('#course').focus();
		}
	}else if($jq('#fromDate').val()==""){
		errorMessage += "Please Select From Date.\n";
		if(status){
			status = false;
			$jq('#fromDate').focus();
		}
	}else if($jq('#toDate').val()==""){
		errorMessage += "Please Select From Date.\n";
		if(status){
			status = false;
			$jq('#toDate').focus();
		}
	}else if(!$jq('input:radio[name=dischargeOfDuties]').is(':checked')){
		errorMessage += "Please Select Discharge Of Duties.\n";
		if(status){
			status = false;
			$jq('#dischargeOfDuties').focus();
		}
	}else if(!$jq('input:radio[name=labWork]').is(':checked')){
		errorMessage += "Please Select Study Course Will Be Useful For Lab Work.\n";
		if(status){
			status = false;
			$jq('#labWork').focus();
		}
	}
	if(status){
		var requestDetails = {			
			"course" :	$jq('#course').val(),
			"fromDate" :	$jq('#fromDate').val(),
			"religionFlag" : $jq('input:radio[name=religionFlag]:checked').val(),
			"dischargeOfDuties" :	$jq('input:radio[name=dischargeOfDuties]:checked').val(),
			"toDate" :	$jq('#toDate').val(),
			"param"  : "manageHQ",
			"type" : $jq('#type').val(),
			"desigID" : desigID,
			"labWork" :	$jq('input:radio[name=labWork]:checked').val()
		};
		$jq.post('hqReq.htm', requestDetails, function(html) {
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				clearHQRequest();
			}
		});
	}else {
		alert(errorMessage);
	}
}
function submitHQSanctionOfIncentive(){
	var errorMessage = "";
	var status = true;
	if(!$jq('input:radio[name=hqAcquired]').is(':checked')){
		errorMessage += "Please Select Whether the HQ has been acquired after induction into service.\n";
		if(status){
			status = false;
			$jq('#hqAcquired').focus();
		}
	}else if(!$jq('input:radio[name=incentiveClaimed]').is(':checked')){
		errorMessage += "Please Select Whether the HQ now acquired.\n";
		if(status){
			status = false;
			$jq('#incentiveClaimed').focus();
		}
	}else if(!$jq('input:radio[name=sponceredByGovt]').is(':checked')){
		errorMessage += "Please Select Whether the Govt servant was sponsored by Govt.\n";
		if(status){
			status = false;
			$jq('#sponceredByGovt').focus();
		}
	}else if(!$jq('input:radio[name=hqIsEssential]').is(':checked')){
		errorMessage += "Please Select Whether HQ now acquired essential or desirable.\n";
		if(status){
			status = false;
			$jq('#hqIsEssential').focus();
		}
	}else if(!$jq('input:radio[name=hqRecog]').is(':checked')){
		errorMessage += "Please Select Whether the HQ meriting grant of incentive is recognized by AICTE.\n";
		if(status){
			status = false;
			$jq('#hqRecog').focus();
		}
	}else if(!$jq('input:radio[name=nexus]').is(':checked')){
		errorMessage += "Please Select Whether there is a direct nexus.\n";
		if(status){
			status = false;
			$jq('#nexus').focus();
		}
	}else if(!$jq('input:radio[name=hqEnclosed]').is(':checked')){
		errorMessage += "Please Select Whether attested copy of Diploma/Degree.\n";
		if(status){
			status = false;
			$jq('#hqEnclosed').focus();
		}
	}else if(!$jq('input:radio[name=addHq]').is(':checked')){
		errorMessage += "Please Select addHq Type.\n";
		if(status){
			status = false;
			$jq('#addHq').focus();
		}
	}else if(!$jq('input:radio[name=hqAfterInduction]').is(':checked')){
		errorMessage += "Please Select Whether the higher qualification has been acquired after induction into service.\n";
		if(status){
			status = false;
			$jq('#hqAfterInduction').focus();
		}
	}else if(!$jq('input:radio[name=dateOfAcquire]').is(':checked')){
		errorMessage += "Please Select Whether the higher qualification has been acquired on or after 09-04-1999 and if so the date of qcquiring.\n";
		if(status){
			status = false;
			$jq('#dateOfAcquire').focus();
		}
	}
	if(status){
		var requestDetails = {				
			"dateOfAcquire" :$jq('input:radio[name=dateOfAcquire]:checked').val(),	
			"hqAcquired" :$jq('input:radio[name=hqAcquired]:checked').val(),
			"incentiveClaimed" :$jq('input:radio[name=incentiveClaimed]:checked').val(),
			"sponceredByGovt" :$jq('input:radio[name=sponceredByGovt]:checked').val(),
			"hqIsEssential" :$jq('input:radio[name=hqIsEssential]:checked').val(),
			"hqRecog" :$jq('input:radio[name=hqRecog]:checked').val(),
			"nexus" :$jq('input:radio[name=nexus]:checked').val(),
			"hqEnclosed" :$jq('input:radio[name=hqEnclosed]:checked').val(),
			"addHq" :$jq('input:radio[name=addHq]:checked').val(),
			"hqAfterInduction" :$jq('input:radio[name=hqAfterInduction]:checked').val(),
			"type" :$jq('#type').val(),
			"param" : "manageSOI"
		};
		
		$jq.post('hqReq.htm', requestDetails, function(html) {
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				clearHQSanctionOfIncentive();
			}
		});
	}else {
		alert(errorMessage);
	}

}
function clearHQSanctionOfIncentive(){
	$jq('input:radio[name=dateOfAcquire]').attr('checked',false);	
	$jq('input:radio[name=hqAcquired]').attr('checked',false);	
	$jq('input:radio[name=incentiveClaimed]').attr('checked',false);	
	$jq('input:radio[name=sponceredByGovt]').attr('checked',false);	
	$jq('input:radio[name=hqIsEssential]').attr('checked',false);	
	$jq('input:radio[name=hqRecog]').attr('checked',false);	
	$jq('input:radio[name=nexus]').attr('checked',false);	
	$jq('input:radio[name=hqEnclosed]').attr('checked',false);	
	$jq('input:radio[name=addHq]').attr('checked',false);	
	$jq('input:radio[name=hqAfterInduction]').attr('checked',false);	
}
function hqSanctionOfIncentive(){
	document.forms[0].param.value="sanctionOfIncentive";
	document.forms[0].type.value="hqSancOfIncRequest";	
	document.forms[0].action = "hqReq.htm";
	document.forms[0].submit();
}

//emu for quarter starts

function lablesView(type){
	if(type == 'alloted'){
		document.title='EMU Alloted Quarter Details';
		$jq('#headTitle').html('EMU Alloted Quarter Details');
		
	}else if(type == 'occupied'){
		document.title='EMU Occupied Quarter Details';
		$jq('#headTitle').html('EMU Occupied Quarter Details');
		
	}else if(type == 'vacated'){
		document.title='Quarter Vacation Details';
		$jq('#headTitle').html('Quarter Vacation Details');
	}else if(type == 'vacationCmpl'){
		document.title='Quarter Vacation Reports';
		$jq('#headTitle').html('Quarter Vacation Reports');
	}
	
	
}

function clearFields(){
	$jq('input:text').val("");
	$jq('select').val("");
}

function clearFields1(type) {
	$jq('#letterNo').val('select');
	$jq('#letterDt').val('');
	if($jq('input:checkbox[name=selectall]').is(':checked')) {
		$jq('#selectall').removeAttr('checked');
		$jq('.row').removeAttr('checked');
	}
	$jq('#emu').find('tr:not(:first)').each(function() {
		$jq(this).find('td').eq(0).find('input:checkbox').removeAttr('checked');
		if(type == 'alloted') {
			$jq(this).find('td').eq(3).find('select').val('select');
			$jq(this).find('td').eq(4).find('input').val('');
			$jq(this).find('td').eq(5).find('input').val('');
		}
		else if(type == 'occupied')
			$jq(this).find('td').eq(6).find('input').val('');
		else if(type == 'vacated')
			$jq(this).find('td').eq(7).find('input').val('');
	});
}

function saveEmuDetails() {
	var status=true;	
	var errorMessage="";	
    if ($jq("#sfID").val() == "") {
		errorMessage += "Please Select SFID.\n";
		if (status) {
			$jq('#sfID').focus();
			status = false;
		}
	}
    if ($jq("#quarterType").val() == "") {
		errorMessage += "Please Select QuarterType.\n";
		if (status) {
			$jq('#quarterType').focus();
			status = false;
		}
	}
	 if($jq("#allotedDate1").val() == "") {
		errorMessage += "Please Select Date.\n";
		if (status) {
			$jq('#allotedDate1').focus();
			status = false;
		}
	}
	 
	 if($jq("#occupiedDate1").val() == "") {
			errorMessage += "Please Select Date.\n";
			if (status) {
				$jq('#occupiedDate1').focus();
				status = false;
			}
	} 
    
	 if($jq("#quarterNo").val() == "") {
			errorMessage += "Please Enter QuarterNo.\n";
			if (status) {
				$jq('#quarterNo').focus();
				status = false;
			}
	} 
		 
	if (status) {
		var requestDetails = {
			"sfID" : $jq("#sfID").val(),
			"quarterType" : $jq("#quarterType").val(),
			"allotedDate1" : $jq("#allotedDate1").val(),
			"occupiedDate1" : $jq("#occupiedDate1").val(),
			"quarterNo" : $jq("#quarterNo").val(),	
			"param" : "saveEmuDetailsForQuarter"	
		};
		$jq.ajax( {
			type : "POST",
			url : "quarterReq.htm",
			data : requestDetails,
			cache : false,
			success : function(html) {
				$jq('#result').html(html);
				
			}
		});	
	}else{
		alert(errorMessage);
	}
}

function saveEmuDetails1(quarterType) {
	var mainJSON = {};
	var status=true;
	var errorMessage='';
	var sfidReport="";
	var allotedReqIds1='';
	var allotedReqIds2='';
	var allotedReqIds='';
	if(quarterType == 'alloted'){
		if($jq('#letterNo').val()=='Select'){
			errorMessage+='Please Select Letter No.\n';
			status=false;
		}
		for(var i=0;i<=$jq('#emu').find("tr").length;i++) {
			if($jq('#adv'+i).is(':checked')) {
				if($jq('#sfID'+i).val()=='') {
					errorMessage+='SFID Cannot be Empty\n';
					$jq('#sfID'+i).focus();
					status=false;
				}if($jq('#quarterType'+i).val()=='') {
					errorMessage+='Please Select Quarter Type\n';
					$jq('#quarterType'+i).focus();
					status=false;
				}if($jq('#quarterNo'+i).val()=='') {
					errorMessage+='Please Enter Quarter No.\n';
					$jq('#quarterNo'+i).focus();
					status=false;
				}if($jq('#allotedDt'+i).val()=='') {
					errorMessage+='Please Select Alloted Date in Row '+(i+1)+'.\n';
					$jq('#allotedDt'+i).focus();
					status=false;
				}
			}
		}

		
		for(var i=0,j=0;i<=$jq('#emu').find("tr").length;i++) {
				if($jq('#adv'+i).is(':checked')) {
					allotedReqIds1 += $jq('#requestID'+i).val()+' ';
				}
				allotedReqIds2 = $jq.trim(allotedReqIds1);
				allotedReqIds = allotedReqIds2.replace(' ',',');
		}
		for(var i=0,j=0;i<=$jq('#emu').find("tr").length;i++) {
			if($jq('#adv'+i).is(':checked')) {
				var subJSON={};
				subJSON['quarterType']=$jq('#quarterType'+i).val();
				subJSON['quarterNo']=$jq('#quarterNo'+i).val();
				subJSON['allotedDt']=$jq('#allotedDt'+i).val();
				subJSON['occupiedDt']=$jq('#occupiedDt'+i).val();
				subJSON['sfID']=$jq('#sfID'+i).val();
				subJSON['requestID']=$jq('#requestID'+i).val();
				mainJSON[j]=subJSON;
				j++;
			}
	}
	}
	
	else if(quarterType == 'occupied'){
		if($jq('#letterNo').val()=='Select'){
			errorMessage+='Please Select Letter No.\n';
			status=false;
		}
		for(var i=0;i<=$jq('#emu').find("tr").length;i++) {
			if($jq('#adv'+i).is(':checked')) {
				if($jq('#sfID'+i).val()=='') {
					errorMessage+='SFID Cannot be Empty\n';
					$jq('#sfID'+i).focus();
					status=false;
				}if($jq('#requestID'+i).val()=='') {
					errorMessage+='requestID Cannot be Empty\n';
					$jq('#requestID'+i).focus();
					status=false;
				}if($jq('#occupiedDt'+i).val()=='') {
					errorMessage+='Please Select Occupied Date in Row '+(i+1)+'.\n';
					$jq('#occupiedDt'+i).focus();
					status=false;
				}
			}
		}

		
		for(var i=0,j=0;i<=$jq('#emu').find("tr").length;i++) {
				if($jq('#adv'+i).is(':checked')) {
					var subJSON={};
					subJSON['occupiedDt']=$jq('#occupiedDt'+i).val();
					subJSON['sfID']=$jq('#sfID'+i).val();
					sfidReport=$jq('#sfID'+i).val();
					subJSON['requestID']=$jq('#requestID'+i).val();
					mainJSON[j]=subJSON;
					j++;
				}
		}		
		
	}
	
	else if(quarterType == 'vacated'){
		if($jq('#letterNo').val()=='Select'){
			errorMessage+='Please Select Letter No.\n';
			status=false;
		}
		for(var i=0;i<=$jq('#emu').find("tr").length;i++) {
			if($jq('#adv'+i).is(':checked')) {
				if($jq('#sfID'+i).val()=='') {
					errorMessage+='SFID Cannot be Empty\n';
					$jq('#sfID'+i).focus();
					status=false;
				}if($jq('#requestID'+i).val()=='') {
					errorMessage+='requestID Cannot be Empty\n';
					$jq('#requestID'+i).focus();
					status=false;
				}if($jq('#vacatedDt'+i).val()=='') {
					errorMessage+='Please Select Vacated Date in Row '+(i+1)+'.\n';
					$jq('#vacatedDt'+i).focus();
					status=false;
				}if($jq('#occupiedDt'+i).val()=='') {
					errorMessage+='occupied Date Cannot be Empty.\n';
					$jq('#occupiedDt'+i).focus();
					status=false;
				}
			}
		}

		for(var i=0,j=0;i<=$jq('#emu').find("tr").length;i++) {
				if($jq('#adv'+i).is(':checked')) {
					var subJSON={};
					subJSON['vacatedDt']=$jq('#vacatedDt'+i).val();
					subJSON['occupiedDt']=$jq('#occupiedDt'+i).val();
					subJSON['sfID']=$jq('#sfID'+i).val();
					sfidReport=$jq('#sfID'+i).val();
					subJSON['requestID']=$jq('#requestID'+i).val();
					mainJSON[j]=subJSON;
					j++;
				}
		}		
		
	}
	
		if(JSON.stringify(mainJSON).length==2) {
			errorMessage+='please check at least one check box';
			status = false;
		}
	
	
	if(status) {
		$jq.post('quarterReq.htm?param=saveEmuDetailsForQuarter&type='+quarterType+'&allotedId='+$jq('#letterNo').val()+'&jsonValues='+JSON.stringify(mainJSON), 
		function(html) {	
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				clearFormFields1();
				/*if(quarterType == 'vacated' || quarterType == 'occupied'){
					occupyOrVacationReport(quarterType,sfidReport);
				}*/
				if(quarterType == 'alloted'){
					allotedReport(quarterType,allotedReqIds);
				}
			}
    	  });
	}else {
		alert(errorMessage);
	}
}


function occupyOrVacationReport(quarterType,sfid) {
	window.open('./report.htm?param=quarterReport1&quarterType='+quarterType+'&sfid='+sfid,'Leave','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function allotedReport(quarterType,allotedReqIds){
	window.open('./report.htm?param=quarterReport1&quarterType='+quarterType+'&requestID='+allotedReqIds,'Leave','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}

function clearFormFields() {
	$jq('#letterNo').val('Select');
	$jq('#letterDt').val('');
	if($jq('input:checkbox[name=selectall]').is(':checked')) {
		$jq('#selectall').removeAttr('checked');
		$jq('.row').removeAttr('checked');
	}
	$jq('#emu').find('tr:not(:first)').each(function() {
		$jq(this).find('td').eq(0).find('input:checkbox').removeAttr('checked');
	});
}

function clearFormFields1(){
	$jq('#letterNo').val('Select');
	$jq('#letterDt').val('');
	$jq('#emu tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked')){
			$jq(this).remove();
		}
	});
}
function saveRequestForm(reqTypeID) {
	var mainJSON = {};
	var status=true;
	var errorMessage='';
	var letterNum='';
	var emuReqId='';
	var emuReqId1='';
	var emuReqId2='';

	if($jq('#letterNo').val()=='Select') {
		errorMessage+='Please Select Letter Number.\n';
		$jq('#letterNo').focus();
		status=false;
	}else{
		letterNum=$jq('#letterNo').val();
	}
	if($jq('#letterDt').val()=='') {
		errorMessage+='Please Select Letter Date.\n';
		$jq('#letterDt').focus();
		status=false;
	}
	
	
	for(var i=0;i<=$jq('#emu').find("tr").length;i++) {
		if($jq('#adv'+i).is(':checked')) {
			if($jq('#sfID'+i).val()=='') {
				errorMessage+='SFID Cannot be Empty.\n';
				$jq('#sfID'+i).focus();
				status=false;
			}
		}
	}

	for(var i=0,j=0;i<=$jq('#emu').find("tr").length;i++) {
			if($jq('#adv'+i).is(':checked')) {
				var subJSON={};
				subJSON['sfID']=$jq('#sfID'+i).val();
				subJSON['requestID']=$jq('#requestID'+i).val();
				emuReqId2 += $jq('#requestID'+i).val()+' ';
				mainJSON[j]=subJSON;
				j++;
			}
			emuReqId1 = $jq.trim(emuReqId2);
			emuReqId = emuReqId1.replace(' ',',');
	}
	if(emuSFIDListJSON!=null && emuSFIDListJSON!=undefined && emuSFIDListJSON!=''){
		if(JSON.stringify(mainJSON).length==2) {
			errorMessage+='please check at least one check box';
			status = false;
		}
	}else{
		errorMessage+='No Records Found To Submit';
		status = false;
	}
	
	
	
	
	if(status) {
		$jq.post('quarterReq.htm?param=saveRequestFormDetails&jsonValues='+JSON.stringify(mainJSON)+'&letterNo='+$jq('#letterNo').val()+'&letterDt='+$jq('#letterDt').val(), 
		function(html) {	
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				clearFormFields1();
				adminNoteReport(letterNum,reqTypeID);
				
    	  }
		});
	}
		else {
		alert(errorMessage);
	}

}

	function adminNoteReport(letterNum,reqTypeID,emuReqId){
		window.open('./report.htm?param=emuReport&letterNo='+letterNum+'&requestTypeID='+reqTypeID,'Leave','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
	}


//emu for quarter ends

function clearMapping() {
		document.getElementById('quarterType').value = "select";
		for ( var i = 0; i < document.getElementById('SelectLeft').options.length; i++) {
			document.getElementById('SelectLeft').options[i].text = "";
			document.getElementById('SelectLeft').options[i].value = "";
		}
		for ( var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
			document.getElementById('SelectRight').options[i].text = "";
			document.getElementById('SelectRight').options[i].value = "";
		}
		$jq('#result').html("");
		
}

function saveQuarterGradePays() {
	var selectedValues = "";
	var errorMessages = "";
	var flag = true;
	var status = false;
	if (document.getElementById('quarterType').value == "select") {
		errorMessages = "Please Select QuarterType\n";
		document.getElementById('quarterType').focus();
		flag = false;
	}
	for ( var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
			selectedValues += document.getElementById('SelectRight').options[i].value+ ",";
			status = true;
	}
	if (!status) {
		errorMessages += "Please Select Grade Pays\n";
		flag = false;
	}
	if (flag) {
		document.forms[0].param.value = "saveQuarterGradePays";
		document.forms[0].selectedGradePays.value = selectedValues;
		$jq.post('quarterReq.htm', $jq('#quarterRequest').serialize(),function(html) {
			$jq("#result").html(html);
			
		});
	} else {
		alert(errorMessages);
	}
}

function getSelQuarterTypes(){
	if($jq('#quarterType').val()!='select'){
		$jq.post('quarterReq.htm?param=getSelQuarterTypes&quarterType=' + document.getElementById('quarterType').value, function(html) {
			$jq("#result1").html(html);
			
		});
	}
}

function setGradePays() {
	var create1 = '';
	var create2 = '';
	 jQuery('#SelectRight option').remove();
	 jQuery('#SelectLeft option').remove();

    
    
    for(var i = 0; i < listObj.length;i++) {
    	var flag=true;
    	for(var j=0;j<delistObj.length;j++) 
    		{
    			if(delistObj[j].gradePay==listObj[i].gradePayID){
    				create2 += '<option value="'+listObj[i].gradePayID+'">'+listObj[i].gradePayID+'</option>';
    				flag=false;
    				break;
    			}
    		}
    	if(flag){
    		create1 += '<option value="'+listObj[i].gradePayID+'">'+listObj[i].gradePayID+'</option>';
    	}
    }
    jQuery('#SelectRight').append(create2);
    jQuery('#SelectLeft').append(create1);
   }

function getSFIDDetails()
{
	if($jq('#sfID').val().trim() != '')
	{
		$jq.post('quarterReq.htm?param=sfidDetails&sfID=' + $jq('#sfID').val().toUpperCase(), function(html) {
			$jq("#quarterResult").html(html);
		});
	}
}


function qLablesView(type){
	if(type == 'quarterOfflineEntry'){
		document.title='Quarter Offline Entry';
		$jq('#headTitle').html('Quarter Offline Entry');
		
	}
	else{
		document.title='Application For Allotment Of Government Accommodation';
		$jq('#headTitle').html('Application For Allotment Of Government Accommodation');
		
	}
	
}



function clearQuarterOffline(){
	$jq('input:text').val("");
	$jq('select').val("");
	$jq('#result').html('');
	$jq('#sfidName').html('');
	$jq('#sfidDesignation').html('');
	$jq('#result1').html('');
}
function clearQuarterOffline1(){
	$jq('#sfID').val("");
	$jq('#quarterNo').val("");
	
}

function saveQuarterOfflineDetails(){
	var status=true;	
	var errorMessage="";	
	$jq("#param").val()
    if ($jq("#quartersType").val() == "select") {
		errorMessage += "Please Select Quarter Type.\n";
		if (status) {
			$jq('#quarterType').focus();
			status = false;
		}
	}if ($jq("#quarterSubType").val() == "select") {
		errorMessage += "Please Select Quarter Sub Type.\n";
		if (status) {
			$jq('#quarterType').focus();
			status = false;
		}
	}
	
    if($jq("#quarterNo").val() == "") {
			errorMessage += "Please Enter QuarterNo.\n";
			if (status) {
				$jq('#quarterNo').focus();
				status = false;
			}
	}
	if($jq("#occupiedDt").val() == "") {
		errorMessage += "Please Select occupied Date.\n";
		if (status) {
			$jq('#occupiedDt').focus();
			status = false;
		}
	}
	if(!$jq('#vacatedDt').val()=='' && !$jq('#occupiedDt').val()=='') {
		if(!compareDate($jq('#vacatedDt').val(),$jq('#occupiedDt').val())) {
			errorMessage+="Vacation Date Should Not Greater Than Occupied Date \n Please Enter Right Dates";
			status=false;
		}
	}
	if (status) {
		var requestDetails = {
				"sfID" : $jq('#sfID').val(),
				"quarterTypeId" : $jq('#quartersType').val(),
				"quarterSubTypeId" : $jq('#quarterSubType').val(),
				"occupiedDate" : $jq('#occupiedDt').val(),
				"vacationDate" : $jq('#vacatedDt').val(),
				"quarterNo" : $jq('#quarterNo').val(),
				"param":"saveQuarterOfflineDetails",
				"parentID":  $jq('#parentID').val()
			};
		                $jq.post('quarterReq.htm', requestDetails, function(html) {
		                $jq("#listResult").html(html);
		                clearQuarterOfflineEntryDetails();
	                     });
		}else{
		alert(errorMessage);
	}


}
function clearQuarterOfflineEntryDetails(){
	 $jq('#quartersType').val('select'),
	 $jq('#quarterSubType').val('select'),
	 $jq('#occupiedDt').val(''),
	 $jq('#vacatedDt').val(''),
	 $jq('#param').val(''),
	 $jq('#quarterNo').val(''),
	 $jq('#parentID').val('')
	 $jq('#quarterSubType').find('option:not(:first)').remove().end();
}
function sfidQuarterDetails() {
	if($jq('#sfID').val()!=''){
		$jq.post('quarterReq.htm?param=sfidQuarterDetails&sfID=' + $jq('#sfID').val().toUpperCase(), function(html) {
			$jq("#result").html(html);
			
		});
	}
}

function onchangeSfidDetails(){
	if($jq('#sfID1').val() != ""){
		$jq.post('quarterReq.htm?param=onchangeSfid&sfID1='+$jq('#sfID1').val().toUpperCase(),
			function(html){
			$jq("#result").html(html);
		});
	}else
		alert("SFID cannot be empty");
}

function showQuarterEligibles()
{
	$jq('#eligibility').val("");
	$jq('#eligibility').text("");
	$jq('#eligibility').append($jq("<option></option>").attr("value",'').text('Select'));
	if(quarterEligibles != null)
	{
		for(i = 0; i < quarterEligibles.length; i++)
			$jq('#eligibility').append($jq("<option></option>").attr("value", quarterEligibles[i].id).text(quarterEligibles[i].name));
	}
}
function showQuarterStatus(resultVal){
	if(resultVal=='Applied'){
		$jq('#reqResult').show();
		$jq('#reqDetailsDiv').hide();
	}else{
		$jq('#reqResult').hide();
		$jq('#reqDetailsDiv').show();
	}
}
function showQuarterEligibles1(){
	$jq('#suretyType').val("");
	$jq('#suretyType').text("");
	$jq('#suretyType').append($jq("<option></option>").attr("value",'').text('Select'));
	
	for(i=0;i<quarterEligibles.length;i++){
		$jq('#suretyType').append($jq("<option></option>").attr("value",quarterEligibles[i].name).text(quarterEligibles[i].name));
		}
}

function editQuarterOffline(id,occupiedDate,vacationDate){
for(var i=0; i<quarterOfflineDetailsJson.length; i++){
	if(quarterOfflineDetailsJson[i].id==id){
		$jq('#sfID').val(quarterOfflineDetailsJson[i].sfid);
		$jq('#quarterNo').val(quarterOfflineDetailsJson[i].quarterNo);
		$jq('#occupiedDt').val(occupiedDate);
		$jq('#vacatedDt').val(vacationDate);
		$jq('#param').val('saveQuarterOfflineDetails');
		$jq('#parentID').val(id);
		for(var j=0; j<quarterSubTypeListJSON.length; j++){
			if(quarterOfflineDetailsJson[i].quarterTypeDetails.id==quarterSubTypeListJSON[j].id){
				$jq('#quartersType').val(quarterSubTypeListJSON[j].quarterTypeDetails.id);
				getQuarterSubTypeList();
				$jq('#quarterSubType').val(quarterSubTypeListJSON[j].id);
			}
		}
	}
 }
}

function getQuarterSubTypeList(){
  	$jq('#quarterSubType').find('option:not(:first)').remove().end();
  	if(quarterSubTypeListJSON!=null){
		for(var i=0;i<quarterSubTypeListJSON.length;i++){
				if(quarterSubTypeListJSON[i].quartersType==$jq('#quartersType').val()){
					$jq("#quarterSubType").append(
							'<option value=' + quarterSubTypeListJSON[i].id + '>'
							+ quarterSubTypeListJSON[i].quarterSubType
							+ '</option>');
				}
            }
	   }
}

function deleteQuarterOfflineDetails(id){
	var check=confirm("Do you really want to delete this record ?");
	if(check){
		var requestDetails = {
				"param":"deleteQuarterOfflineDetails",
				"parentID":  id
			};
        $jq.post('quarterReq.htm', requestDetails, function(html) {
        $jq("#listResult").html(html);
        clearQuarterOfflineEntryDetails();
         });
	}
}
//var quarterList1=new Array();
function setApplQuarter(val){
	var i=0;
	/*$jq('input:radio[name=appliedQuarter] :(:first)').attr('checked',true).each(function(){
		for(var k=1;k<=(parseInt($jq('#eligibility').find('option').length)-1);k++){
			var quarterList={};
			quarterList['name']=$jq('#eligibility').find('option').eq(k).val();
			quarterList1[k]=quarterList;
		}
	});*/
	/*if($jq('input:radio[name=appliedQuarter]:checked').val()=="1" && $jq('input:radio[name=appliedQuarter][disabled=true]:first').attr('checked',true)){
		
	}*/
	if($jq('input:radio[name=appliedQuarter]:checked').val()=="1"){
		for( var k=1;k<=(parseInt($jq('#eligibility').find('option').length)-1);){
			$jq('#eligibility').find('option').eq(k).remove();
			k=k;
		}
		for( var k=0;k<quarterList1.length;k++){
			$jq('#eligibility').append($jq('<option value='+quarterList1[k].name+'>'+quarterList1[k].name+'</option>'));
		}
		$jq('#eligibility').val(val);
		for(i=1;i<=(parseInt($jq('#eligibility').find('option').length)-1);i++){
			if(val!=$jq('#eligibility').find('option').eq(i).val()){
				$jq('#eligibility').find('option').eq(i).remove();
			}
			i=1;
		}
		selectedQuarter();
	}else{
		$jq('#eligibility').val('select');
		for( var k=1;k<=(parseInt($jq('#eligibility').find('option').length)-1);){
			$jq('#eligibility').find('option').eq(k).remove();
			k=k;
		}
		for( var k=0;k<quarterList1.length;k++){
			$jq('#eligibility').append($jq('<option value='+quarterList1[k].name+'>'+quarterList1[k].name+'</option>'));
		}
		for(i=1;i<=(parseInt($jq('#eligibility').find('option').length)-1);i++){
			if(val==$jq('#eligibility').find('option').eq(i).val()){
				$jq('#eligibility').find('option').eq(i).remove();
				i=1;
			}
		}
		selectedQuarter();
	}
}
function showSuretyDiv(){
	if($jq('input:radio[name=suretyReq]:checked').val()=="1"){
		$jq('#suretyDiv').show();
	}else{
		$jq('#suretyDiv').hide();
	}
}
function checkPriorityDate(){
	var sysdate = new Date();
	var systemDate=tadaFormatDate(sysdate,'dd-NNN-yyyy');
	var priorityDate = $jq("#priorityDate").val();
	if (parseInt(compareDates(convertDate(systemDate),convertDate(priorityDate)))<0) {
		alert("Priority Date can not be Past Date.\n");
		$jq("#priorityDate").val('');
		$jq("#priorityDate").focus();
	}
}
function checkOccupiedDate(id,type){
	var occupiedDate=$jq('#occupiedDt'+id).val();
	if(type=='occupied'){
		var allottedDate=$jq('#allotedDt'+id).val();
		if (parseInt(compareDates(convertDate(allottedDate),convertDate(occupiedDate)))<0) {
			alert("Occupied Date should be equal or grater than Alloted Date.\n");
			$jq('#occupiedDt'+id).val('');
			$jq('#occupiedDt'+id).focus();
		}
	}else if(type=='vacated'){
		var vacatedDate=$jq('#vacatedDt'+id).val();
		if (parseInt(compareDates(convertDate(occupiedDate),convertDate(vacatedDate)))<0) {
			alert("Vacated Date should be equal or grater than Occupied Date.\n");
			$jq('#vacatedDt'+id).val('');
			$jq('#vacatedDt'+id).focus();
		}
	}
	
}
function setEmuLetterDate(){
	for(var i=0;i<letterNoList.length;i++){
		if($jq('#letterNo').val()==letterNoList[i].letterNumber){
			$jq('#letterDt').val(letterNoList[i].ionDate);
		}else if($jq('#letterNo').val()=='Select'){
			$jq('#letterDt').val('');
		}
	}
}
function setEmuLettDate(){
	for(var i=0;i<letterNoList.length;i++){
		if($jq('#letterNo').val()==letterNoList[i].id){
			$jq('#letterDt').val(letterNoList[i].ionDate);
		}else if($jq('#letterNo').val()=='Select'){
			$jq('#letterDt').val('');
		}
	}
}
function setPdfReports(){
	if(type=='alloted'){
		var tableLength=$jq('#emu').find('tr').length;
		for(var i=1;i<tableLength;i++){
			for(var j=i+1;j<tableLength;j++){
				if(i!=j){
					if($jq('#emu').find('tr').eq(i).find('td').eq(1).find('input:hidden').val()==$jq('#emu').find('tr').eq(j).find('td').eq(1).find('input:hidden').val()){
						$jq('#emu').find('tr').eq(j).find('td').eq(6).remove();
						break;
					}
				}
			}
		}
	}else if(type=='vacated'){
		var tableLength=$jq('#emu').find('tr').length;
		for(var i=1;i<tableLength;i++){
			for(var j=i+1;j<tableLength;j++){
				if(i!=j){
					if($jq('#emu').find('tr').eq(i).find('td').eq(1).find('input:hidden').val()==$jq('#emu').find('tr').eq(j).find('td').eq(1).find('input:hidden').val()){
						$jq('#emu').find('tr').eq(j).find('td').eq(8).remove();
						break;
					}
				}
			}
		}
	}
//	for(var i=1;i<tableLength;i++){
//		var count=2;
//		if($jq('#emu').find('tr').eq(i).find('td').eq(6).next().text()=""){
//			$jq('#emu').find('tr').eq(i).find('td').eq(6).attr("rowspan",count);
//			count++;
//		}
//	}
//	var j=0;
//	for(var i=1;i<tableLength;i++){
//		var count=1;
//		for(j=i;;){
//			if($jq('#emu').find('tr').eq(j).is(':visible')){
//				if(!$jq('#emu').find('tr').eq(j).find('td').eq(6).is(':visible')){
//					j++;
//					count++;
//				}else{
//					$jq('#emu').find('tr').eq(i).find('td').eq(6).attr("rowspan",count);
//					break;
//				}
//			}
//		}
//	}
}

