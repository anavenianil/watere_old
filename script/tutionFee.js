var maxAmount2=0;
var maxAmount3=0;
var countTuition=0;
function stepGrid(id) { // new hidingClaimDiv has added.
	var errorMessage="";
	var status=true;
	var count=0;
	if($jq('#hidingClaimDiv'+id).find('fieldSet').is(':visible')){
		$jq('#subfield'+id).find('tr:last').hide().val('');
		//$jq('#grandTotalDiv').hide();
	}
     if($jq('#typeId'+id).val()=='select'){
	    errorMessage+="Please Select Type.\n"
	    status=false;
	}
	 if($jq('#classId'+id).val()=='select'){
	    errorMessage+="Please Select The Class.\n"
		status=false;
	}
	 if($jq('#boardId'+id).val()=='select'){
	    errorMessage+="Please Select central/State.\n"
		status=false;
	}
	if(status){
		$jq('#hidingClaimDiv'+id).find('fieldSet').each(
				function(){
					if($jq(this).attr('id')=="field"+id){
					$jq(this).show();
					$jq(this).find('table').attr('id',"sub"+$jq(this).attr('id'));
					$jq('#subfield'+id).append('<tr class="odd"><td style="width: 100%; text-align: right;"'+
							'colspan=6><b><font color=blue>TOTAL</font></b><input id=total'+id+' size=40 readonly=true type="text" style="text-align:right"/>'+
							'</td></tr>');
					$jq('#grandTotalDiv').show();
					$jq('#hidingClaimDiv'+id).show();
					}
				});
	onChangeBoardType(id)
	//sumAllClaimAmounts();	
}else{
	alert(errorMessage);
	$jq('#typeId'+id).val('select');
	$jq('#boardId'+id).val('select');
	$jq('#classId'+id).val('select');
	$jq('#claimDeatails').find('fieldset:visible').each(function(){// for clearing the values in childdiv
		  $jq('#subfield'+id).find('tr:not(:first,:last)').each(function(){
		    $jq(this).find('td').eq(1).find('input').val('');
			$jq(this).find('td').eq(2).find('input').val('');
			$jq(this).find('td').eq(3).find('input').val('');
		 })
	})
	$jq('#limitId').val('select');
	$jq('#total').val('');
	$jq('#grandTotal').val('');
	$jq('#hidingClaimDiv'+id).hide();
	$jq('#family').find('tr:gt(0)').each(function(){// for hiding the grandtotal div when all values are in select
		if($jq('#typeId'+id).val()=='select' && $jq('#boardId'+id).val()=='select' && $jq('#classId'+id).val()=='select'){ // for removing grangtotal completely
			$jq('#grandTotalDiv').hide();
		}	
	});
	//$jq('#grandTotalDiv').show();
	}
}
/*function stepGrid(id) { 
	var errorMessage="";
	var status=true;
	var count=0;
	if($jq('#claimDeatails').find('fieldSet').is(':visible')){
		$jq('#grandTotalDiv').hide();
		$jq('#subfield'+id).find('tr:last').hide();
	}
     if($jq('#typeId'+id).val()=='select'){
	    errorMessage+="Please Select Type.\n"
	    status=false;
	}
	 if($jq('#classId'+id).val()=='select'){
	    errorMessage+="Please Select The Class.\n"
		status=false;
	}
	 if($jq('#boardId'+id).val()=='select'){
	    errorMessage+="Please Select central/State.\n"
		status=false;
	}
	if(status){
		$jq('#claimDeatails').find('fieldSet').each(
				function(){
					if($jq(this).attr('id')=="field"+id){
					$jq(this).show();
					$jq(this).find('table').attr('id',"sub"+$jq(this).attr('id'));
					$jq('#subfield'+id).append('<tr class="odd"><td style="width: 100%; text-align: right;"'+
							'colspan=4><b><font color=blue>TOTAL</font></b><input id=total'+id+' size=40 readonly=true type="text" style="text-align:right"/>'+
							'</td></tr>');
					$jq('#grandTotalDiv').show();
					}
				});
	if($jq('#academicYear'+id).val()!='Select'){
		$jq('#academicYear'+id).val('Select');
	    }
	sumAllClaimAmounts();	
	//if($jq('#academicYear'+id).val()=='select' && $jq('#limitId').is(':visible') ){
	if($jq('#academicYear'+id).val()=='select'){
		$jq('#limitId').attr('disabled',true);
	}	
}else{
	alert(errorMessage);
	$jq('#typeId'+id).val('select');
	//$jq('#claimDeatails').find('fieldSet').hide();
	//$jq('#grandTotalDiv'+id).hide();
}
}*/
function setCOrSAndQOrHGrid(id){
	var status=true;
	$jq('#childInfo').find('table').each(function(){
		$jq(this).find('tr:not(:first)').each(function(){
			if($jq(this).find('td').eq(5).find('select').attr('id').substring(6)==id){
				//if(status==true){
			 var cOrs=$jq(this).find('td').eq(4).find('select').val();
				var qOrhOra=$jq(this).find('td').eq(5).find('select').val();
		        if(cOrs=='C'&& qOrhOra=='Q'){
					$jq('#CQ'+id).show();
					//$jq('#CH'+id).hide();
					//$jq('#SQ'+id).hide();
					//$jq('#SH'+id).hide();
					//$jq('#CA'+id).hide();
					//$jq('#SA'+id).hide();
					//status=false;
				}else if(cOrs=='C'&& qOrhOra=='H'){
					$jq('#CH'+id).show();
					//$jq('#CQ'+id).hide();
					//$jq('#SQ'+id).hide();
					//$jq('#SH'+id).hide();
					//$jq('#CA'+id).hide();
					//$jq('#SA'+id).hide();
					//status=false;
				}else if(cOrs=='S'&& qOrhOra=='Q'){
					$jq('#SQ'+id).show();
					//$jq('#CQ'+id).hide();
					//$jq('#CH'+id).hide();
					//$jq('#SH'+id).hide();
					//$jq('#CA'+id).hide();
					//$jq('#SA'+id).hide();
					//status=false;
				}else if(cOrs=='S'&& qOrhOra=='H'){
					$jq('#SH'+id).show();
					//$jq('#CQ'+id).hide();
					//$jq('#CH'+id).hide();
					//$jq('#SQ'+id).hide();
					//$jq('#CA'+id).hide();
					//$jq('#SA'+id).hide();
					//status=false;
				}else if(cOrs=='C'&& qOrhOra=='A'){
					$jq('#CA'+id).show();
					//$jq('#CQ'+id).hide();
					//$jq('#CH'+id).hide();
					//$jq('#SQ'+id).hide();
					//$jq('#SA'+id).hide();
					//$jq('#SH'+id).hide();
					//$jq('#academicYear').val('select');
					//status=false;
				}else if(cOrs=='S'&& qOrhOra=='A'){
					$jq('#SA'+id).show();
					//$jq('#SH'+id).hide();
					//$jq('#CQ'+id).hide();
					//$jq('#CH'+id).hide();
					//$jq('#SQ'+id).hide();
					//$jq('#CA'+id).hide();
					//status=false;
				}
		    	//}
			}
		 });
});
}
function manageTutionFeeRequestDetails(){
	var errorMessage="";
	var status=true;
	var j=0;
	var mainJSON={};
	var flag=true;
	var flag6=true;
	if($jq('#family').find('td').text()=="Nothing found to display."){
		errorMessage+="Children does not exist.So not Eligible to claim for TUITION FEE";
		status =false;
	}
	if($jq('#childInfo').find('table').each(function(){
		 $jq(this).find('tr:not(:first)').each(function(){
			 var name=$jq(this).find('td').eq(0).text();
			 var flag5=true;
			if(flag5){
				 if($jq(this).find('td').eq(3).find('select').val()=='select' && $jq(this).find('td').eq(4).find('select').val()=='select' && $jq(this).find('td').eq(5).find('select').val()=='select'){
					 flag5=false;
				 }else{
					    if($jq(this).find('td').eq(3).find('select').val()=='select'){
						    errorMessage+='In Respective Of '  +name+ ": Class Is Not Selected   \n"
						    	status=false;
						}
						if($jq(this).find('td').eq(4).find('select').val()=='select'){
						    errorMessage+='In Respective Of '  +name+ ": Central/state Is Not Selected   \n"
							status=false;
						}
						if($jq(this).find('td').eq(5).find('select').val()=='select'){
						    errorMessage+='In Respective Of '  +name+ ": Type Is Not Selected   \n"
							status=false;
						} 
						flag6=false;
				 }
				 }
			 });
		 if(flag6){
			  errorMessage+="Please Select Class,Central/State,Type. "+name+" \n"
			  status=false;
		  }
		 }));
		$jq('#claimDeatails').find('fieldset:visible').each(function(){
			 var name=$jq(this).find('legend').text();
			 var familyId=$jq(this).attr('id').substring(5);
			  if($jq('#academicYear'+familyId).val()=='select'){
				    errorMessage+="Please Select The Academic Year. "+name+" \n"
					status=false;
				  }	
			 if( $jq('#claimDeatailsDiv'+familyId).is(':visible')){
				  if($jq('#claimDeatailsDiv'+familyId).find('#limitId').find('option:selected:visible').text()=='Select'){
					  errorMessage+="Please Select The Period of Claim. "+name+" \n"
						    status=false;
			  }
			  }
		 });
 $jq('#claimDeatails').find('fieldset:visible').each(function(){
	  var flag3=true;
	  var name=$jq(this).find('legend').text();
	  $jq(this).find('table').each(function(){
		  $jq(this).find('tr:visible:not(:first,:last)').each(function(){ //   $jq(this).find('tr:not(:first,:last)') is modified
			  var name1=$jq(this).find("td").eq(0).text();
			   var flags4=true;
			   if(flags4){
				   if($jq(this).find('td').eq(1).find('input').val()=='' && $jq(this).find('td').eq(2).find('input').val()=='' && $jq(this).find('td').eq(3).find('input').val()==''){
					   //status=true;
					   flags4=false;
				   }else{
					   if($jq(this).find('td').eq(1).find('input').val()==''){
						   errorMessage+='' +name+ ": ReceiptNo Is Empty For -- "  +name1+   "\n"
							  //errorMessage+="Please Enter the Reciept No Of "  +name1+  " " +name+ "\n"
								  status=false;
						  } 
						  if($jq(this).find('td').eq(2).find('input').val()==''){
							  errorMessage+='' +name+ ": Date Is Empty For -- "  +name1+   "\n"
								  status=false;
						  } 
						  if($jq(this).find('td').eq(3).find('input').val()==''){
							  errorMessage+='' +name+ ": Amount Is Empty For -- "  +name1+   "\n"
								  status=false;
						  }
						  flag3=false;
				   }
			   }  
		   });
	   });
	  if(flag3){
		  errorMessage+="Please Fill The ReceiptNo,Date,Amount Details" +name+ "\n"
		  status=false;
	  }
  });
 if(status==true){
	$jq('#claimDeatails').find('fieldset:visible').each(
			function(){
		var jsonFieldSetRow={};
		var mainTableJSON={};
		var i=0;
		var temp=$jq(this).attr('id');
		var id=$jq(this).attr('id').substring(5);
		jsonFieldSetRow['childId']=$jq(this).find("table").attr('id');
		//jsonFieldSetRow['limitId']=$jq(this).find("div:visible").find('option:selected:visible').val(); // modified on 05-05-2014
		jsonFieldSetRow['limitId']=$jq(this).find('select:visible:not(:first)').find('option:selected').val();
		//code for selecting the drop down period of claim
		jsonFieldSetRow['fromDate']=$jq(this).find('select:visible:not(:first)').find('option:selected').text().split("-")[2]+"-"+$jq(this).find('select:visible:not(:first)').find('option:selected').text().split("-")[3]+"-"+$jq(this).find('select:visible:not(:first)').find('option:selected').text().split("-")[4];
		jsonFieldSetRow['toDate']=$jq(this).find('select:visible:not(:first)').find('option:selected').text().split("-")[7]+"-"+$jq(this).find('select:visible:not(:first)').find('option:selected').text().split("-")[8]+"-"+$jq(this).find('select:visible:not(:first)').find('option:selected').text().split("-")[9];
		//code for submiting the Central/State
		$jq('#family').find('tr:not(:first)').each(function(){
			jsonFieldSetRow['boardType'] =$jq('#boardId'+id).find('option:selected').text();
		});
	/* if(jsonFieldSetRow['limitId']==undefined){
			$jq('#family tr:not(:first)').each(function(){
				var id1=$jq(this).find('td').eq(5).find('select').attr('id').substring(6);
				if(temp=='field'+id1){
					if($jq(this).find('td').eq(4).find('select').val()=='C' && $jq(this).find('td').eq(5).find('select').val()=='A'){
						jsonFieldSetRow['limitId']=tutionFeeBean.centralAnnualList[tutionFeeBean.centralAnnualList.length-1].key;
					}else if($jq(this).find('td').eq(4).find('select').val()=='S' && $jq(this).find('td').eq(5).find('select').val()=='A'){
						jsonFieldSetRow['limitId']=tutionFeeBean.stateAnnualList[tutionFeeBean.stateAnnualList.length-1].key;
					} 
				}
			});
		}*/
		$jq('#'+temp).find('table  tr:not(:first,:last)').each(function() {
			var jsonTableRow={};
			if($jq(this).is(':visible')){
			   jsonTableRow['appNumber']=$jq(this).find("td").eq(1).find('input').val();
			   jsonTableRow['dated']=$jq(this).find("td").eq(2).find('input').val();
			   if($jq(this).find("td").eq(3).find('input').val()=='')
			   jsonTableRow['amount']=0;
			   else
			   jsonTableRow['amount']=$jq(this).find("td").eq(3).find('input').val();
			   jsonTableRow['claimId']=$jq(this).find("td").eq(6).find('input').val();
			   //jsonTableRow['claimId']=$jq(this).find("td").eq(4).text();
			   mainTableJSON[i]=jsonTableRow;
				i++;
			}
		}
	)
	$jq('#'+temp).find('table tr:last').each(function() {
		jsonFieldSetRow['total']=$jq(this).find("td").eq(0).find('input').val();
	  });
	            jsonFieldSetRow['childList']=mainTableJSON;
		        
		        $jq('#childInfo').find('fieldset:visible').each(
		    			function(){
		    				$jq(this).find('table tr:not(:first)').each(
		    						function(){
		    					if(jsonFieldSetRow['childId']=='subfield'+$jq(this).find('td').eq(5).find('select').attr('id').substring(6)){
		    					jsonFieldSetRow['classId']=parseInt($jq(this).find("td").eq(3).find('select').val());
		    					}
		    				});
		    			});
		        mainJSON[j]=jsonFieldSetRow;
		        j++;
			}
	)
	}
if (status) {
		var requestDetails = {
			"mainClaimList" : JSON.stringify(mainJSON),
		    "grandTotal" :$jq('#grandTotal').val(),
		    "param"  :   "submitTFRequestDetails",
		    //"limitId"  :  $jq('#limitId').val(),
		};
		$jq.post('tutionFee.htm',requestDetails,function(html) {
			$jq("#returnrequest").html(html);
			if($jq('.success').is(':visible')){
			var check=confirm(" Tuition Fee Details has been Successfully Sent...\n\nPlease click ok to 'Preview Tuition Fee Details Form 'Take print' from here\n\n");
			if(check){
			document.forms[0].requestId.value = $jq.trim(requestIDt);
			
		    document.forms[0].param.value = "requestDetails";
			document.forms[0].action = "workflowmap.htm";
			document.forms[0].submit();	
			}}
			});
		clearTutionFeeDetails();
		//tutionFeeRequestFormHome();//for refreshing the screen
		}else {
		alert(errorMessage);
	}
}
function getChildClaimDetails(childRelationId,childName,limitId){
	$jq('#childName'+childRelationId).text(childName);
	$jq('#subChildList'+childRelationId).toggle();
	if($jq('#hideDiv'+childRelationId).show){// code has been changed show,hide toggle nt worked in the ie8
		$jq('#showDiv'+childRelationId).toggle();
	}
	if($jq('#showDiv'+childRelationId).show){
		$jq('#hideDiv'+childRelationId).toggle();
	}
	/*$jq('#hideDiv'+childRelationId).toggle();
	$jq('#showDiv'+childRelationId).toggle();*/
}
function sumAllClaimAmounts(){ 
	var mainTotal=0;
	$jq('#claimDeatails').find('div').find('fieldSet:visible').each(
			function(){
				var lastRowId=$jq(this).attr('id');
				lastRowId=lastRowId.substring(5);
				var indCliamTotal=0;
				$jq(this).find('table  tr:not(:first,:last)').each(function() {
					if($jq(this).find('td').eq(3).find('input').val()!='' && $jq(this).find('td').eq(3).find('input').val()!=undefined)
					    indCliamTotal+=parseInt($jq(this).find('td').eq(3).find('input').val());
					else{
						if($jq(this).find('td').eq(3).find('input').val()==undefined)
							indCliamTotal=indCliamTotal;
						else
							indCliamTotal+=0;
					}
					if(indCliamTotal==0)
					    $jq('#total'+lastRowId).val('');	
					else{
						//if($jq('#total'+lastRowId).is(':visible'))
						if($jq('#claimDeatails').find('fieldSet:visible').find('table').find('tr:last').is(':visible'))
							//$jq('#total'+lastRowId).val(indCliamTotal);
							$jq('#claimDeatails').find('fieldSet:visible').find('#total'+lastRowId).val(indCliamTotal);
						else
							$jq('#claimDeatails').find('fieldset:visible').find('table').find('tr:last').find('input').val(indCliamTotal);
					}
				});
				mainTotal+=indCliamTotal;
			});
	
	if(mainTotal==0 || isNaN(mainTotal)){
		$jq('#grandTotal').val('');
	}else{
		$jq('#grandTotal').val(mainTotal);
	}
}
function clearTutionFeeDetails(){
	$jq("input:checkbox").remove();
	$jq('input:text').val("");
	$jq('#grandTotal').val('');
	$jq('#claimDeatails').find('fieldset').each(function(){
		$jq(this).find('select').each(function(){
			$jq(this).val('');
		});
	});
	$jq('#family tr:not(:first)').each (function(){
		$jq(this).find('td').eq(3).find('select').val('select');
		$jq(this).find('td').eq(4).find('select').val('select');
		$jq(this).find('td').eq(5).find('select').val('select');
		$jq(this).find('td').eq(6).find('input').attr('checked',false);
	});
	$jq('#claimDeatails').find('fieldset').each(function(){
		$jq(this).hide();
	});
	$jq('#grandTotalDiv').hide();
	
}

function printTutionFeeRequestFormDetails(requestId){
	window
	.open(
			"./report.htm?param=printTutionFeeRequestFormDetails&returnValue=report&requestID="+requestId,
			'preview',
			'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
}
function printTuitionFeeSanctionedIndividualReport(){
    var requestId='';
    var sfid='';
	var errorMessage='';
	var status=true;
	var i=0;
	$jq('#dataTable').find('table').find('tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
			i++;
		}
	});
	if(i==0){
		errorMessage="Please select atleast One Request!"
		status=false;
	}
	i=0;
	if(status){
		$jq('#dataTable').find('table tr:not(:first)').each(function(){
		 if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked')){
					requestId += $jq(this).find('td').eq(2).text()+",";
					}
				
				window
				.open(
						"./report.htm?param=printTuitionFeeSanctionedIndividualReport&returnValue=report&requestID="+requestId+'&claimId='+$jq('#claimType').val(),
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			})
		}else{
			alert(errorMessage);
		}	
}
function printAllTuitionFeeRelatedDocuments(){
	var requestId='';
	var errorMessage='';
	var status=true;
	var i=0;
	$jq('#dataTable').find('table').find('tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
			i++;
		}
	});
	if(i==0){
		errorMessage="Please select atleast One Request!"
		status=false;
	}
	i=0;
	if(status){
		$jq('#dataTable').find('table tr:not(:first)').each(function(){
		 if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked')){
					requestId += $jq(this).find('td').eq(2).text()+",";
				}
				window
				.open(
						"./report.htm?param=printAllTuitionFeeRelatedDocuments&returnValue=report&requestID="+requestId+'&claimId='+$jq('#claimType').val(),
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			})
		}else{
			alert(errorMessage);
		}		
}
//script for print in finance screen
function printAllTuitionFeeRelatedDocuments1(requestType){
	var requestId='';
	var errorMessage='';
	var status=true;
	var i=0;
	$jq('#dataTable').find('table').find('tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
			i++;
		}
	});
	if(i==0){
		errorMessage="Please select atleast One Request!"
		status=false;
	}
	i=0;
	if(status){
		$jq('#dataTable').find('table tr:not(:first)').each(function(){
		 if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked')){
			 	//requestId += $jq(this).find('td').eq(1).text().trim()+",";
				 requestId += $jq.trim($jq(this).find('td').eq(1).text()+",");
				}
				window
				.open(
						"./report.htm?param=printAllTuitionFeeRelatedDocuments&returnValue=report&requestID="+requestId+'&claimId='+$jq('#claimType').val()+'&requestType='+requestType,
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			})
		}else{
			alert(errorMessage);
		}		
}
//script for print in finance screen
function printTuitionFeeSanctionedIndividualReport1(requestType){
    var requestId='';
    var sfid='';
	var errorMessage='';
	var status=true;
	var i=0;
	$jq('#dataTable').find('table').find('tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
			i++;
		}
	});
	if(i==0){
		errorMessage="Please select atleast One Request!"
		status=false;
	}
	i=0;
	if(status){
		$jq('#dataTable').find('table tr:not(:first)').each(function(){
		 if($jq(this).find('td').eq(0).find('input:checkbox').is(':checked')){
					//requestId += $jq(this).find('td').eq(1).text().trim()+",";
			 		requestId += $jq.trim($jq(this).find('td').eq(1).text()+",");
					}
				
				window
				.open(
						"./report.htm?param=printTuitionFeeSanctionedIndividualReport&returnValue=report&requestID="+requestId+'&claimId='+$jq('#claimType').val()+'&requestType='+requestType,
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			})
		}else{
			alert(errorMessage);
		}	
}
function checkMaxAmount(e,maxAmount){
	var indCliamTotal=0;
	if($jq(e).val()>maxAmount){
		alert("Max Amount For Each Child is "+maxAmount+"/-");
		$jq(e).val(maxAmount);
	}
	$jq('#claimDetails1').find('tr:not(:first)').each(
			function(){
				if($jq(this).find('td').eq(5).find('input').val()==''){
					indCliamTotal+=0;
				}else{
					indCliamTotal+=parseInt($jq(this).find('td').eq(5).find('input').val());
				}
			});
	$jq('#total').val(indCliamTotal);
}
function saveClaimFinanceDetails(type){
	var mainJson={};
	var status=true;
	var errorMessage="";
	var i=0;
	if(type=='officers'){
		$jq('#fragment-1').find('table tr:not(:first)').each(function(){
			if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
				i++;
			}
		});
	}else if(type=='staff'){
		$jq('#fragment-2').find('table tr:not(:first)').each(function(){
			if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
				i++;
			}
		});
	}
	if(i==0){
		errorMessage+="Please select atleast One Request \n"
		status=false;
	}
	if(type=='officers'){
		if($jq('#saveOfficerTuitionTeleFinance').find('#sanctionNo').val()==''){
			errorMessage+="Please Enter Sanction No.\n"
			status=false;
		}
		if($jq('#saveOfficerTuitionTeleFinance').find('#billNo').val()==''){
			  errorMessage+="Please Enter Bill No.\n"
			  status=false;
		}
		if($jq('#saveOfficerTuitionTeleFinance').find('#accOfficer').val()=='select'){
			errorMessage+="Please Select the Accounts Officer.\n"
		    status=false;
		}
		if($jq('#saveOfficerTuitionTeleFinance').find('#cfaOfficer').val()=='select'){
			errorMessage+="Please Select the CFA Officer.\n"
		    status=false;
		}
	}else if(type=='staff'){
		if($jq('#saveStaffTuitionTeleFinance').find('#sanctionNo').val()==''){
			errorMessage+="Please Enter Sanction No.\n"
			status=false;
		}
		if($jq('#saveStaffTuitionTeleFinance').find('#billNo').val()==''){
			  errorMessage+="Please Enter Bill No.\n"
			  status=false;
		}
		if($jq('#saveStaffTuitionTeleFinance').find('#accOfficer').val()=='select'){
			errorMessage+="Please Select the Accounts Officer.\n"
		    status=false;
		}
		if($jq('#saveStaffTuitionTeleFinance').find('#cfaOfficer').val()=='select'){
			errorMessage+="Please Select the CFA Officer.\n"
		    status=false;
		}
	}
	
	i=0;
	if(status && type=='officers'){
	$jq('#fragment-1').find('table tr:not(:first)').each(function(){
		var innerJson={};
		if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
			if($jq(this).find('td').eq(0).find('input').val()==''){
				innerJson['financeId']=0;
			}else{
				innerJson['financeId']=$jq(this).find('td').eq(0).find('input').val();
			}
			innerJson['requestId']=$jq(this).find('td').eq(1).text();
			innerJson['sfid']=$jq(this).find('td').eq(2).text();
			innerJson['claimedAmount']=$jq(this).find('td').eq(4).find('input').val();
			innerJson['sanctionedAmount']=$jq(this).find('td').eq(5).find('input').val();
			/*if($jq(this).find('td').eq(6).find('input').val()==''){
				errorMessage+="Please Enter Sanction No. for the request Id "+innerJson['requestId']+"\n";
				status=false;
			}else{
				innerJson['sanctionNo']=$jq(this).find('td').eq(6).find('input').val();
			}
			if($jq(this).find('td').eq(7).find('input').val()==''){
				errorMessage+="Please Enter Bill No. for the request Id "+innerJson['requestId']+"\n";
				status=false;
			}else{
				innerJson['billNo']=$jq(this).find('td').eq(7).find('input').val();
		     }
			if($jq(this).find('td').eq(8).find('option:selected').val()=='select'){
				errorMessage+="Please Select Acc Officer  for the request Id "+innerJson['requestId']+"\n";
				status=false;
			}else{
				innerJson['accOfficer']=$jq(this).find('td').eq(8).find('option:selected').val();
			}*/
			mainJson[i]=innerJson;
			i++;
		}
	});
	}else if(status && type=='staff'){
		$jq('#fragment-2').find('table tr:not(:first)').each(function(){
			var innerJson={};
			if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
				innerJson['financeId']=$jq(this).find('td').eq(0).find('input').val();
				innerJson['requestId']=$jq(this).find('td').eq(1).text();
				innerJson['sfid']=$jq(this).find('td').eq(2).text();
				innerJson['claimedAmount']=$jq(this).find('td').eq(4).find('input').val();
				innerJson['sanctionedAmount']=$jq(this).find('td').eq(5).find('input').val();
				/*if($jq(this).find('td').eq(6).find('input').val()==''){
					errorMessage+="Please Enter Sanction No. for the request Id "+innerJson['requestId']+"\n";
					status=false;
				}else{
					innerJson['sanctionNo']=$jq(this).find('td').eq(6).find('input').val();
				}
				if($jq(this).find('td').eq(7).find('input').val()==''){
					errorMessage+="Please Enter Bill No. for the request Id "+innerJson['requestId']+"\n";
					status=false;
				}else{
					innerJson['billNo']=$jq(this).find('td').eq(7).find('input').val();
			     }
				innerJson['accOfficer']=$jq(this).find('td').eq(8).find('option:selected').val();*/
				mainJson[i]=innerJson;
				i++;
			}
		});
		}
	 if (status && type=='officers') {
			var requestDetails = {
				"mainCDAList" : JSON.stringify(mainJson),
			    "param"       : "saveCDAFinanceDetails",
			    "claimTypeId" : $jq('#claimType').val(),
			    "claimType" : $jq('#claimType').val(),
			    "sanctionNo"  : $jq('#saveOfficerTuitionTeleFinance').find('#sanctionNo').val(),
			    "billNo"      : $jq('#saveOfficerTuitionTeleFinance').find('#billNo').val(),
			    "accOfficer"  : $jq('#saveOfficerTuitionTeleFinance').find('#accOfficer').val(),
			    "cfaOfficer"  : $jq('#saveOfficerTuitionTeleFinance').find('#cfaOfficer').val(),
			};
			$jq.post('tutionFee.htm',requestDetails,function(html) {
				$jq("#claimDetails").html(html);
				//$jq('select').val('select');
				$jq('#saveOfficerTuitionTeleFinance').find('#sanctionNo').val('');
				$jq('#saveOfficerTuitionTeleFinance').find('#billNo').val('');
				$jq('#saveOfficerTuitionTeleFinance').find('#accOfficer').val('select');
				$jq('#saveOfficerTuitionTeleFinance').find('#cfaOfficer').val('select');
				});
		}else if(status && type=='staff'){
			var requestDetails = {
					"mainCDAList" : JSON.stringify(mainJson),
				    "param"       : "saveCDAFinanceDetails",
				    "claimTypeId" : $jq('#claimType').val(),
				    "claimType" : $jq('#claimType').val(),
				    "sanctionNo"  : $jq('#saveStaffTuitionTeleFinance').find('#sanctionNo').val(),
				    "billNo"      : $jq('#saveStaffTuitionTeleFinance').find('#billNo').val(),
				    "accOfficer"  : $jq('#saveStaffTuitionTeleFinance').find('#accOfficer').val(),
				    "cfaOfficer"  : $jq('#saveStaffTuitionTeleFinance').find('#cfaOfficer').val(),
				};
				$jq.post('tutionFee.htm',requestDetails,function(html) {
					$jq("#claimDetails").html(html);
					$jq('select').val('select');
					$jq('#saveStaffTuitionTeleFinance').find('#sanctionNo').val('');
					$jq('#saveStaffTuitionTeleFinance').find('#billNo').val('');
					$jq('#saveStaffTuitionTeleFinance').find('#accOfficer').val('select');
					$jq('#saveStaffTuitionTeleFinance').find('#cfaOfficer').val('select');
					});
		}else {
			alert(errorMessage);
		}
}
/*function tuitionFeeSendToCDA(e,id,requestId, roleId){
	var key;
	var value = document.getElementById(id).value; 
	
	
	if(window.event){
		key = window.event.keyCode;
	}
	else{
		key = e.which;
	}
	if(value=="" && key==13){
		alert("Please Enter RequestID ");
		return false;
	}
	else if(key!=13 && key!=8 && key!=0 && key!=46 &&  (key<48 || key>57)){
		alert("Please Enter Numbers Only");
		
		return false;
	}
	if($jq('#billNo').val()=='ASL/22/FIN/3041/2013-2014/CEA/'){
		errorName+="Please Enter BillNo.?"
		status=false;
	}
	if($jq('#billNo').val()=='ASL/FIN/022/RESI PHONE/'){
		errorName+="Please Enter BillNo.?"
		status=false;
	}
	if(value!="" && key==13){
		var errorName='';
		var status = true;
	if (status) {
		var requestDetails={
				"param" :"getTuitionFeeCDAList",
				"billNo" :$jq('#billNo').val(),
				"claimType" : $jq('#claimType').val(),
		};
		$jq.post('tutionFee.htm',requestDetails,function(html) {
			$jq("#result").html(html);
			});
		return true;
		}else{
			alert(errorName);
		}
	}
	
}*/
function tuitionFeeSendToCDA(billNo){
	var errorName='';
	var status=true;
	if($jq('#billNo').val()==billNo){
		errorName+="Please Enter BillNo..?"
		status=false;
	}
	if(status){
		var requestDetails={
				"param" :"getTuitionFeeCDAList",
				"billNo" :$jq('#billNo').val(),
				"claimType" : $jq('#claimType').val(),
		};
		$jq.post('tutionFee.htm',requestDetails,function(html) {
			$jq("#result").html(html);
			});
	}else{
		alert(errorName);
	}
}
//old code
/*function saveTutionFeeCdaAmount(billNo,claimType){
	var mainJson={};
	var status=true;
	var errorMessage="";
	var i=0;
	$jq('#dataTable').find('table tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
			i++;
		}
	});
	if(i==0){
		errorMessage="Please select atleast One Request!"
			status=false;
	}
	i=0;
	if(status){
	$jq('#dataTable').find('table tr:not(:first)').each(function(){
		var innerJson={};
		if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
			innerJson['referenceID']=$jq(this).find('td').eq(0).find('input').val();
			if($jq(this).find('td').eq(9).find('input').val()==''){
				errorMessage+="Please Enter Dv No. for the requestId \n";
				status=false;
			}else{
				innerJson['dvNo']=$jq(this).find('td').eq(9).find('input').val();
			}
			if($jq(this).find('td').eq(10).find('input').val()==''){
				errorMessage+="Please Enter Dv.Date for the requestId \n";
				status=false;
			}else{
				innerJson['dvDate']=$jq(this).find('td').eq(10).find('input').val();
		     }
			if($jq(this).find('td').eq(11).find('input').val()==''){
				errorMessage+="Please Enter CDA Amount for the requestId \n";
				status=false;
			}else{
				innerJson['cdaAmount']=$jq(this).find('td').eq(11).find('input').val();
		     }
			mainJson[i]=innerJson;
			i++;
		}
	});
	}
	 if (status) {
			var requestDetails = {
				"mainCDAList" : JSON.stringify(mainJson),
			    "param"  :   "saveCDADetails",
			    "billNo" :$jq('#billNo').val(),
				"claimType" : $jq('#claimType').val(),
			};
			$jq.post('tutionFee.htm',requestDetails,function(html) {
				$jq("#result").html(html);
			});
		} else {
			alert(errorMessage);
		}
}*/
//modified code
function saveTutionFeeCdaAmount(){
	var mainJson={};
	var status=true;
	var errorMessage="";
	var i=0;
	$jq('#dataTable').find('table tr:not(:first)').each(function(){
		if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
			i++;
		}
	});
	if($jq('#cdaDvNo').val()==''){
		errorMessage+="Please Enter Dv No \n"
		status=false;
	}
	
	if($jq('#fromDate').val()==''){
		errorMessage += "Please Select DV Date \n";
		status = false;
	}
	if(i==0){
		errorMessage+="Please select atleast One Request! \n"
			status=false;
	}
	i=0;
	if(status){
	$jq('#dataTable').find('table tr:not(:first)').each(function(){
		var innerJson={};
		if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
			innerJson['referenceID']=$jq(this).find('td').eq(0).find('input').val();
			if($jq('#claimType').val()==1){
				if($jq(this).find('td').eq(9).find('input').val()==''){
					errorMessage+="Please Enter CDA Amount for the requestId \n";
					status=false;
				}else{
					innerJson['cdaAmount']=$jq(this).find('td').eq(9).find('input').val();
			     }
			}else if($jq('#claimType').val()==2){
				if($jq(this).find('td').eq(10).find('input').val()==''){
					errorMessage+="Please Enter CDA Amount for the requestId \n";
					status=false;
				}else{
					innerJson['cdaAmount']=$jq('#tf').find('td').eq(10).find('input').val();
			     }
			}
			mainJson[i]=innerJson;
			i++;
		}
	});
	}
	 if (status) {
			var requestDetails = {
				"mainCDAList" : JSON.stringify(mainJson),
			    "param"  :   "saveCDADetails",
			    "billNo" :$jq('#billNo').val(),
				"claimType" : $jq('#claimType').val(),
				"dvDate"    : $jq('#fromDate').val(),
				"dvNo"      : $jq('#cdaDvNo').val(),
			};
			$jq.post('tutionFee.htm',requestDetails,function(html) {
				$jq("#result").html(html);
			});
		} else {
			alert(errorMessage);
		}
}
function sumAllAtSanctionStatus(maxAmount,limitId,idVal){
	var childId='';
	var flag=true;
	var maxAmount1=maxAmount;
	for(var i=0;i<tutionFeeLimitList.length;i++){
		if(limitId==tutionFeeLimitList[i].id){
			if(idVal==1){
				maxAmount2=tutionFeeLimitList[i].limit;
			}
			if(idVal==2){
				maxAmount3=tutionFeeLimitList[i].limit;
			}
			flag=false;
		}
	}
	$jq('#indDetails').find('fieldset').each(function(){
		var amountPerChild=0;
		childId=$jq(this).attr('id');
		$jq(this).find('table tr:not(:first)').each(function(){
			if($jq(this).find('td').eq(4).find('input').val()==''){
				amountPerChild+=0;
			}else{
			amountPerChild+=parseInt($jq(this).find('td').eq(4).find('input').val());
			}
		});
		 $jq('#claimDetails1  tr:not(:first)').each(function(){
			 if(childId=='subChildList'+$jq(this).find('td').eq(9).find('input').val()){
				 $jq(this).find('td').eq(7).find('input').val(amountPerChild);
			 }
		});
	});
	var j=1;
	$jq('#indDetails').find('fieldset').each(function(){
		var val=0;
		childId=$jq(this).attr('id');
		$jq(this).find('table tr:not(:first)').each(function(){
			if($jq(this).find('td').eq(4).find('input').val()!=''){
				val=val+parseInt($jq(this).find('td').eq(4).find('input').val());
			}
			if(j==1){
				if(val>((maxAmount1*maxAmount2)/100)){
				    $jq(this).find('td').eq(4).find('input').val('');
		        }
			}
			if(j==2){
				if(val>((maxAmount1*maxAmount3)/100)){
				    $jq(this).find('td').eq(4).find('input').val('');
		        }
			}
			
		});
		if(j==1){
			if(val>((maxAmount1*maxAmount2)/100)){
			    alert("Max Amount For Each Child is "+maxAmount2+"% of" +maxAmount1+"");
			    $jq("#"+childId.split("st")[1]).val(((maxAmount1*maxAmount2)/100));
			 }else{
	        	$jq("#"+childId.split("st")[1]).val();
	         }
		}
		if(j==2){
			if(val>((maxAmount1*maxAmount3)/100)){
			    alert("Max Amount For Each Child is "+maxAmount3+"% of" +maxAmount1+"");
			    $jq("#"+childId.split("st")[1]).val(((maxAmount1*maxAmount3)/100));
			 }else{
	        	$jq("#"+childId.split("st")[1]).val();
	         }
		}
		j++;
	});
	var claimTotal=0;
    $jq('#claimDetails1').find('tr:not(:first)').each(function(){
    		if($jq(this).find('td').eq(7).find('input').val()==''){
    		claimTotal=claimTotal+0;
    	}else{
    		claimTotal=claimTotal+parseInt($jq(this).find('td').eq(7).find('input').val());
    	}
    	$jq('#total').val(claimTotal);
        });
    	/*var val=0;
	$jq('#claimDetails1  tr:not(:first)').each(function(){
		var indCliamTotal=0;
		val=val+parseInt($jq(this).find('td').eq(5).find('input').val());
		if(val>((maxAmount1*maxAmount)/100)){
			alert("Max Amount For Each Child is "+maxAmount+"% of" +maxAmount1+"");
			val=val-parseInt($jq(this).find('td').eq(5).find('input').val());
			$jq(this).find('td').eq(5).find('input').val((maxAmount1*maxAmount)/100);
		}
		else{
			$jq(this).find('td').eq(5).find('input').val(val);
		}
		$jq('#claimDetails1').find('tr:not(:first)').each(
				function(){
					if($jq(this).find('td').eq(5).find('input').val()==''){
						indCliamTotal+=0;
					}else{
						indCliamTotal+=parseInt($jq(this).find('td').eq(5).find('input').val());
					}
				});
		$jq('#total').val(indCliamTotal);
});*/
	/*$jq('#claimDetails1  tr:not(:first)').each(function(){
		var value=0;
		var indCliamTotal=0;
		if(value>((maxAmount1*maxAmount)/100)){
			alert("Max Amount For Each Child is "+maxAmount+"% of" +maxAmount1+"");
			value=value-parseInt($jq(this).find('td').eq(5).find('input').val());
			$jq(this).find('td').eq(5).find('input').val((maxAmount1*maxAmount)/100);
		}
		$jq('#indDetails').find('tr:not(:first)').each(
				function(){
					if($jq(this).find('td').eq(5).find('input').val()==''){
						indCliamTotal+=0;
					}else{
						indCliamTotal+=parseInt($jq(this).find('td').eq(5).find('input').val());
					}
				});
		$jq('#total').val(value);

	});	*/
}
function submitTuitionFeeAcademicYearMaster(){
	var errorName='';
	var status=true;
	if($jq('#className').val()==''){
		errorName+="Please Enter Standard.\n"
		status=false;
		$jq('#className').focus();
	}
	if($jq('#claimOrderNo').val()==''){
		errorName+="Please Enter sequence Order."
		status=false;	
		$jq('#claimOrderNo').focus();
	}
	if(status){
		var requestDetails={
				"pk" : nodeID,
				"param" :"submitTFAClassName",
				"className" :$jq('#className').val(),
				"claimOrderNo" : $jq('#claimOrderNo').val()
				
		};
		$jq.post('tutionFee.htm',requestDetails,function(html) {
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				clearTuitionFeeacademicYearMaster();
			}
			});
	}else{
		alert(errorName);
	}
}
function editTutionAcademicMaster(id,className,orderNo){
	nodeID = id;
	$jq('#className').val(className); 
	$jq('#claimOrderNo').val(orderNo); 
	
}
function clearTuitionFeeacademicYearMaster() {
	$jq('#className').val(''); 
    $jq('#claimOrderNo').val(''); 
	nodeID = '';
}
function deleteTutionAcademicMaster(id) {
	//alert(id);
	var check=confirm("Do u want to delete ?");
	if(check){
		var requestDetails = {
			"param" : "deleteTutionFeeAcademicMaster",
			"pk" :id			
		};
		$jq.post('tutionFee.htm'+'?'+dispUrl, requestDetails, function(html) {
			$jq("#result").html(html);
		});
	}
}
function submitTuitionFeeCliamDetails(){
	var errorName='';
	var status=true;
	if($jq('#claimType').val()=='select'){
		errorName+="Please Select The Claim Type\n"
		status=false;
	}
	if($jq('#claimName').val()==''){
		errorName+="Please Enter Claim Name\n"
		status=false;
	}
	if($jq('#claimOrderNo').val()==''){
		errorName+="Please Enter OrderNo\n"
		status=false;
	}
	if(status){
		var requestDetails={
				"pk" : nodeID,
				"param" :"submitTFCliamName",
				"claimName" :$jq('#claimName').val(),
				"claimType" :$jq('#claimType').val(),
				"claimOrderNo" :$jq('#claimOrderNo').val(),
			    
		};
		$jq.post('tutionFee.htm',requestDetails,function(html) {
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				clearTuitionFeeCliamDetails();
				$jq('#editAddButton').css("display","block");
				$jq('#editSubmitButton').css("display","none");
			}
			});
	}else{
		alert(errorName);
	}
}
function editTutionClaimMaster(id,claimName,claimType,orderNo){
	nodeID = id;
	$jq('#claimName').val(claimName);
	$jq('#claimType').val(claimType);
	$jq('#claimOrderNo').val(orderNo);
	$jq('#editAddButton').css("display","none");
	$jq('#editSubmitButton').css("display","block");
}
function clearTuitionFeeCliamDetails(){
	$jq('#claimName').val('');
	nodeID = '';
	$jq('#claimType').val('select');
	$jq('#claimOrderNo').val('');
}
function deleteTutionClaimMaster(id) {
	//alert(id);
	var check=confirm("Do u want to delete ?");
	if(check){
		var requestDetails = {
			"param" : "deleteClaimMaster",
			"pk" :id			
		};
		$jq.post('tutionFee.htm'+'?'+dispUrl, requestDetails, function(html) {
			$jq("#result").html(html);
		});
	}
}
function submitTuitionFeeLimitMaster(){
	var errorMessage="";
	var annually="";
	var status = true;
	if ($jq('#academicType').val()=='select') {
		errorMessage += "Please Select Academic Type\n";
		status = false;
	}
	if($jq('#type1').is(':checked')==false && $jq('#type2').is(':checked')==false && $jq('#type3').is(':checked')==false && $jq('#type4').is(':checked')==false
			&& $jq('#type5').is(':checked')==false && $jq('#type6').is(':checked')==false && $jq('#type7').is(':checked')==false && $jq('#type8').is(':checked')==false && $jq('#type9').is(':checked')==false)
			{
				errorMessage += "Please Select The Type\n ";
				status = false;
			}
	if($jq('#type1').is(':checked')==true ){
		if($jq('#type2').is(':checked')==false && $jq('#type3').is(':checked')==false && $jq('#type4').is(':checked')==false && $jq('#type5').is(':checked')==false){
			errorMessage += "Please Select The Type\n ";
			status = false;
		}
	}
	if($jq('#type6').is(':checked')==true ){
		if($jq('#type7').is(':checked')==false && $jq('#type8').is(':checked')==false){
			errorMessage += "Please Select The Type\n ";
			status = false;
		}
	}
	if($jq('#fromDate').val()==''){
		errorMessage +=  "Please Select FromDate\n";
		status = false;
	}
	if($jq('#toDate').val()==''){
		errorMessage += "Please Select ToDate\n";
		status = false;
	}
	if($jq('#limit').val()==''){
		errorMessage += "Please Enter The Limit";
		status = false;
	}
	if($jq('input:radio[name=type]:checked').val()=='Annually'){
		annually = $jq('input:radio[name=type]:checked').val();
	}
	if(status){
		var requestDetails={
				"pk" : nodeID,
				"academicType" :$jq('#academicType').val(),
				"param" :"submitAcademicyear",
				"fromDate1" :$jq('#fromDate').val(),
				"toDate1" :$jq('#toDate').val(),		
				"limit" :$jq('#limit').val(),
				"type": annually,
				"typeHalfyearly" :$jq('input:radio[name=typeHalfyearly]:checked').val(),
				"typeQuartarly" :$jq('input:radio[name=typeQuartarly]:checked').val()
		};
		$jq.post('tutionFee.htm',requestDetails,function(html) {
			$jq("#result").html(html);
			if ($jq('.success').is(':visible')) {
				clearTuitionFeeLimitMaster();
			}
			});
	}else{
		alert(errorMessage);
	}
}
function clearTuitionFeeLimitMaster() {
	$jq("input:radio").attr("checked", false);
	$jq('#academicType').val('select');
	$jq('#fromDate').val('');
	$jq('#toDate').val('');
	$jq('#limit').val('');
	$jq('#type').val('');
	$jq('#quartarlyDetailsDiv').hide();
	$jq('#halfyearlyDetailsDiv').hide();
	nodeID = '';
}
function editTutionFeeLimitMaster(id,academicType,fromDate,toDate,limit,type){
	nodeID = id;
	$jq('#academicType').val(academicType);
	$jq('#fromDate').val(fromDate);
	$jq('#toDate').val(toDate);
	$jq('#limit').val(limit);
	 if(type=='Q1'){
		$jq("#type1[value=Quartarly]").attr('checked', true);
		$jq("#type2[value=Q1]").attr('checked', true);
		$jq("#type3[value=Q2]").attr('disabled','disabled');
		$jq("#type4[value=Q3]").attr('disabled','disabled');
		$jq("#type5[value=Q4]").attr('disabled','disabled');
		$jq("#type6[value=Halfyearly]").attr('disabled','disabled');
		$jq("#type9[value=Annually]").attr('disabled','disabled');
		$jq('#academicType').attr('disabled',true);
		$jq('#quartarlyDetailsDiv').show();
		$jq('#halfyearlyDetailsDiv').hide();
	  }
	else if(type=='Q2'){
		$jq("#type1[value=Quartarly]").attr('checked', true);
		$jq("#type2[value=Q1]").attr('disabled','disabled');
		$jq("#type3[value=Q2]").attr('checked', true);
		$jq("#type4[value=Q3]").attr('disabled','disabled');
		$jq("#type5[value=Q4]").attr('disabled','disabled');
		$jq("#type6[value=Halfyearly]").attr('disabled','disabled');
		$jq("#type9[value=Annually]").attr('disabled','disabled');
		$jq('#academicType').attr('disabled',true);
		$jq('#quartarlyDetailsDiv').show();
		$jq('#halfyearlyDetailsDiv').hide();
	  }
	else if(type=='Q3'){
		$jq("#type1[value=Quartarly]").attr('checked', true);
		$jq("#type2[value=Q1]").attr('disabled','disabled');
		$jq("#type3[value=Q2]").attr('disabled','disabled');
		$jq("#type4[value=Q3]").attr('checked', true);
		$jq("#type5[value=Q4]").attr('disabled','disabled');
		$jq("#type6[value=Halfyearly]").attr('disabled','disabled');
		$jq("#type9[value=Annually]").attr('disabled','disabled');
		$jq('#academicType').attr('disabled',true);
		$jq('#quartarlyDetailsDiv').show();
		$jq('#halfyearlyDetailsDiv').hide();
	   }
	else if(type=='Q4'){
		$jq("#type1[value=Quartarly]").attr('checked', true);
		$jq("#type2[value=Q1]").attr('disabled','disabled');
		$jq("#type3[value=Q2]").attr('disabled','disabled');
		$jq("#type4[value=Q3]").attr('disabled','disabled');
		$jq("#type5[value=Q4]").attr('checked', true);
		$jq("#type6[value=Halfyearly]").attr('disabled','disabled');
		$jq("#type9[value=Annually]").attr('disabled','disabled');
		$jq('#academicType').attr('disabled',true);
		$jq('#quartarlyDetailsDiv').show();
		$jq('#halfyearlyDetailsDiv').hide();
	   }
	else if(type=='H1'){
		$jq("#type6[value=Halfyearly]").attr('checked', true);   
		$jq("#type7[value=H1]").attr('checked', true);
		$jq("#type8[value=H2]").attr('disabled','disabled');
		$jq("#type1[value=Quartarly]").attr('disabled','disabled');
		$jq("#type9[value=Annually]").attr('disabled','disabled');
		$jq('#academicType').attr('disabled',true);
		$jq('#halfyearlyDetailsDiv').show();
		$jq('#quartarlyDetailsDiv').hide();
		}
	else if(type=='H2'){
		$jq("#type6[value=Halfyearly]").attr('checked', true); 
		$jq("#type7[value=H1]").attr('disabled','disabled');
		$jq("#type8[value=H2]").attr('checked', true);
		$jq("#type1[value=Quartarly]").attr('disabled','disabled');
		$jq("#type9[value=Annually]").attr('disabled','disabled');
		$jq('#academicType').attr('disabled',true);
		$jq('#halfyearlyDetailsDiv').show();
		$jq('#quartarlyDetailsDiv').hide();
	}
	else if(type=='Annually'){
		$jq("#type9[value=Annually]").attr('checked', true);
		$jq("#type1[value=Quartarly]").attr('disabled','disabled');
		$jq("#type6[value=Halfyearly]").attr('disabled','disabled');
		$jq('#academicType').attr('disabled',true);
		$jq('#halfyearlyDetailsDiv').hide();
		$jq('#quartarlyDetailsDiv').hide();
	}
}
function deleteTutionFeeLimitMaster(id){
	var check=confirm("Do u want to delete ?");
	if(check){
		var requestDetails = {
			"param" : "deleteTutionFeeLimitMaster",
			"pk" :id			
		};
		$jq.post('tutionFee.htm'+'?'+dispUrl, requestDetails, function(html) {
			$jq("#result").html(html);
		});
	}
	
}
function saveTuitionFeeconfiguration(){
	
	var flag=true;
	var errorMessage = "";
	var configurationDetails = "";
	 if($jq('#tutionFeeMaxLimitPerOneChild').val()==''){
		errorMessage += "Please Enter Tution Fee Max Limit Per One Child\n";
		if (flag) {
			$jq('#tutionFeeMaxLimitPerOneChild').focus();
			flag = false;
		}
		}else{
			configurationDetails +="TUITION_FEE_MAX_LIMIT_PER_ONE_CHILD,"+$jq('#tutionFeeMaxLimitPerOneChild').val()+"#";
			
		}
	  if($jq('#tutionFeeMaxChildToBeClaimed').val()==''){
		errorMessage += "Please Enter Max Child To Be Claimed\n";
		if (flag) {
			$jq('#tutionFeeMaxChildToBeClaimed').focus();
			flag = false;
		}
	 }else{
			configurationDetails +="TUITION_FEE_MAX_CHILD_TO_BE_CLAIMED,"+$jq('#tutionFeeMaxChildToBeClaimed').val()+"#";
		}
	
	if (flag) {
		var requestDetails = {
				"param"  : "saveTutionFeeConfiguration",
				"configurationDetails"   : configurationDetails
			};
			$jq.post('tutionFee.htm', requestDetails, function(html) {
				$jq("#result").html(html);
			});

	} else {
		alert(errorMessage);
	}
}
function getClaimFinanceDetails(){
	var flag=true;
	var errorMessage = "";
	if($jq('#claimType').val()=='select'){
	     errorMessage+="please Select Claim Type.\n"
		 flag=false;
	}
	 if(flag) {
		var requestDetails = {
				"param"  : "getClaimFinanceDetails",
				"claimType" :$jq('#claimType').val()
			};
			$jq.post('tutionFee.htm', requestDetails, function(html) {
				$jq("#claimDetails").html(html);
			});
	}else{
		alert(errorMessage);
	}
}
function getClaimCdaDetails(){
	var flag=true;
	var errorMessage = "";
	if($jq('#claimType').val()=='select'){
	     errorMessage+="please Select Claim Type.\n"
		 flag=false;
	}
    if(flag) {
			var requestDetails = {
					"param"  : "getClaimCDADeatils",
					"claimType" :$jq('#claimType').val()
				};
				$jq.post('tutionFee.htm', requestDetails, function(html) {
					$jq("#cdaDetails").html(html);
				});
	}else{
		alert(errorMessage);
	}
}
/*function checkTutionLimitDate(){
	if($jq('input:radio[name=type]:checked').val()=='Q1' || $jq('input:radio[name=type]:checked').val()=='Q2' || $jq('input:radio[name=type]:checked').val()=='Q3' || $jq('input:radio[name=type]:checked').val()=='Q4'){
		if(parseInt(compareDates(convertDate($jq('#fromDate').val()),convertDate($jq('#toDate').val())))>90){
			alert("Enter dates for quarterly should be between 3 months");
			$jq('#toDate').val('');
		}
	}
	if($jq('input:radio[name=type]:checked').val()=='H1' || $jq('input:radio[name=type]:checked').val()=='H2'){
		if(parseInt(compareDates(convertDate($jq('#fromDate').val()),convertDate($jq('#toDate').val())))>180){
			alert("Enter dates for Halfyearly should be between 6 months");
			$jq('#toDate').val('');
		}
	}
	
}*/
function quartarlyDetails(){
	if($jq('input:radio[name=type]:checked').val()=='Quartarly'){
		$jq('#quartarlyDetailsDiv').show();
		$jq('#halfyearlyDetailsDiv').hide();
		$jq('input:radio[id=type7]').attr('checked',false);
		$jq('input:radio[id=type8]').attr('checked',false);
	}else if($jq('input:radio[name=type]:checked').val()=='Halfyearly'){
		$jq('#quartarlyDetailsDiv').hide();
		$jq('#halfyearlyDetailsDiv').show();
		$jq('input:radio[id=type2]').attr('checked',false);
		$jq('input:radio[id=type3]').attr('checked',false);
		$jq('input:radio[id=type4]').attr('checked',false);
		$jq('input:radio[id=type5]').attr('checked',false);
	}else if($jq('input:radio[name=type]:checked').val()=='Annually'){
		$jq('#quartarlyDetailsDiv').hide();
		$jq('#halfyearlyDetailsDiv').hide();
		$jq('input:radio[id=type7]').attr('checked',false);
		$jq('input:radio[id=type8]').attr('checked',false);
		$jq('input:radio[id=type2]').attr('checked',false);
		$jq('input:radio[id=type3]').attr('checked',false);
		$jq('input:radio[id=type4]').attr('checked',false);
		$jq('input:radio[id=type5]').attr('checked',false);
	}
}
function alertInfoOfAcademicYear(){
	if($jq('#academicYear').val()=='select'){
		$jq('#limitId').val('select');
	    alert("Please Select The Academic Year");
	}
}
function selectOptionTutionFee(){
	$jq('#claimType').val('select');
}
function tutionFeeClaims(){
	$jq('#fragment-2').css('display','none');
	$jq('#fragment-1').css('display','block');
}
function telephoneBillClaims(){
	$jq('#fragment-1').css('display','none');
	$jq('#fragment-2').css('display','block');
}
// year checking details 
function checkTuitionFeeAcademicYearDetails(id){
	var flag=true;
	var errorMessage = "";
   if(flag) {
			var requestDetails = {
					"param"  : "checkTuitionFeeAppliedYearDetails",
					"academicYear" :$jq('#academicYear'+id).val(),
					"familyChildId"       : id,
					"academicType": $jq('#boardId'+id).find('option:selected').text(),
				    "type"   :   $jq('#typeId'+id).find('option:selected').text(),
				};
				$jq.post('tutionFee.htm', requestDetails, function(html) {
					$jq("#claimDeatailsDiv"+id).html(html);
					 setCOrSAndQOrHGrid(id);
	});
	}
}
function onChangeBoardType(id){
	$jq('#academicYear'+id).val('select');
	$jq('#claimDeatailsDiv'+id).find('#limitId').find('option:selected:visible').text('Select');
   $jq('#claimDeatails').find('fieldset:visible').each(function(){// for clearing the values in childdiv
		  $jq('#subfield'+id).find('tr:not(:first,:last)').each(function(){
		    $jq(this).find('td').eq(1).find('input').val('');
			$jq(this).find('td').eq(2).find('input').val('');
			$jq(this).find('td').eq(3).find('input').val('');
			});
	});
    if($jq('#claimDeatails').find('fieldset:visible').each(function(){
    	$jq('#subfield'+id).find('tr:not(:first,:last)').each(function(){
		    $jq(this).find('td').eq(1).find('input').val('');
			$jq(this).find('td').eq(2).find('input').val('');
			$jq(this).find('td').eq(3).find('input').val('');
			});
    	$jq('#grandTotal').val('');
    }));
}
function checkForPeriodOfClaim(countId,id){
	var status ="";
    var errorMessage ="";
     if($jq('#claimDeatailsDiv'+id).find('#limitId').find('option:selected:visible').text()=='Select'){
   	 status=true;
   	 errorMessage+="Please Select The Period Of Claim";
     }
     else if($jq('#academicYear'+id).val()=='select'){
    	 status=true;
    	 errorMessage+="Please Select The Academic Year.";
     }
	 if(status){
		 alert(errorMessage);
		 $jq('#receiptNo'+countId).text('');
	 }
}
function checkCreateNewRowForTuition(countId,childRelationId,claimId,claimName){
	var errorMessage ="";
	var status = true;
	 $jq('#claimDeatails').find('fieldset:visible').each(function(){
		  var flag3=true;
		  var name=$jq(this).find('legend').text();
		  $jq(this).find('table').each(function(){
			  $jq(this).find('tr:visible:not(:first,:last)').each(function(){ //   $jq(this).find('tr:not(:first,:last)') is modified
				  var name1=$jq(this).find("td").eq(0).text();
				  if(status){
						if($jq('#receiptNo'+countId).val()=='' && $jq('#dated'+countId).val()=='' && $jq('#amount'+countId).val()==''){
							errorMessage+="Please Fill The ReceiptNo,Date,Amount Details \n";
							status=false;
						}else{
							if($jq('#subfield'+childRelationId).find('#receiptNo'+countId).val()==''){
								errorMessage+='' +name+ ": ReceiptNo Is Empty For -- "  +name1+   "\n"
								status=false;
							}
							if($jq('#subfield'+childRelationId).find('#dated'+countId).val()==''){
								errorMessage+='' +name+ ": Date Is Empty For -- "  +name1+   "\n"
								status=false;
							}
							if($jq('#subfield'+childRelationId).find('#amount'+countId).val()==''){
								errorMessage+='' +name+ ": Amount Is Empty For -- "  +name1+   "\n"
								status=false;
							}
						}
					}
				});
		   });
		  });
	 if(status){
		 newRowForTuitionFee('subfield"+childRelationId+"',countId,childRelationId,claimId,claimName);
	 }else{
		 alert(errorMessage);
	 }	
}
function newRowForTuitionFee(idvalue,countId,childRelationId,claimId,claimName){
	//var claimName;
	var count = 0;
	//countTuition=$jq('#subfield'+childRelationId+' tr:gt(1)').length-1;
	count = countId;
	//countTuition++;
	$jq('#subfield'+childRelationId).find('tr:visible:not(:first,:last)').each(function(){
		countTuition++
	});
	/*$jq('#subfield'+childRelationId).each(function(){
		$jq(this).find('tr:visible:not(:first,:last)').each(function(){
			  claimName=$jq(this).find('td').eq(0).text();
		});
	});*/
	
	var row = "<tr id=row"+countTuition+""+childRelationId+">" ;
	row += "<td><b><font color=purple>"+claimName.replace('_',' ')+"</font></b></td>";
	row += "<td><input type=text size=30 id=receiptNo"+countTuition+" onkeypress='javascript:checkForPeriodOfClaim("+childRelationId+")'/></td>";
	row += "<td>";
	row += "<input type=text readonly=readonly style=height:16px;width:150px;font-size:12px;font-weight:bold; id=dated"+countTuition+""+childRelationId+" onfocus=javascript:Calendar.setup({inputField:'dated"+countTuition+""+childRelationId+"',ifFormat:'%d-%b-%Y',showsTime:false,button:'dated"+countTuition+""+childRelationId+"',step:1}); />";
	row += "</td>";
	
	row += "<td><input type=text size=20 id=amount"+countTuition+" style='text-align: right; float:right;' onkeyup='javascript:checkForPeriodOfClaim("+childRelationId+");sumAllClaimAmounts();' onkeypress='javascript:return checkInt(event);'/></td>";
	row += "<td><input type=button id=add"+countTuition+"  value=+ onclick=javascript:checkCreateNewRowForTuition("+countTuition+","+childRelationId+","+claimId+",'"+claimName.replace(' ','_')+"');></input></td>";	
	row += "<td><input type=button id=del"+countTuition+"  value=- onclick=javascript:deleteRowOfTuition(this,\'subfield"+childRelationId+"\'); /></td>";
	row += "<td style=display:none><input value="+claimId+" type=text size=30 /></td>";
	row += "</tr>";
	$jq("#row"+(count)+(childRelationId)).after(row);
}
function deleteRowOfTuition(node,tableID){
	var dynRow = document.getElementById(tableID);
	var index=node.parentNode.parentNode.rowIndex;
	var del=confirm("Do you want to delete this row?");
	var length1 = $jq('#claimDeatails').find('table tr:not(:first,:last)').length;
	if(del==true) {
		if(index>=0){
			if(length1>4)
				dynRow.deleteRow(index);
			else
				alert("you can't delete this row");
		}
	}
	if(length1=='4'){
		newRowForTuitionFee(tableID);
	}
	countTuition--;	
}
//tuitionfee n telephone claimdetails
function getUserTutionFeeClaimDetails(sfid){
	var errorMessage="";
	var status=true;
	var year='';
	if($jq('#tuitionYear').val()==''){
		errorMessage+="Please Enter Year For Printing TuitionFee Details.";
		status=false;
	}
	if(status){
		window.open(
				"./report.htm?param=printTuitionFeeClaimDetailsReport&returnValue=report&sfid="+sfid+'&year='+$jq('#tuitionYear').val(),
				'preview',
				'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}else{
		alert(errorMessage);
	}			
} 
function getTutionFeeClaimDetails(){
	var errorMessage="";
	var status=true;
	var sfid='';
	var year='';
	if($jq('#tuitionFeeSfid').val()==''){
		errorMessage+="Please Enter  Sfid For Printing TuitionFee Details\n";
		status=false;
	}
	if($jq('#tuitionYear').val()==''){
		errorMessage+="Please Enter Year For Printing TuitionFee Details\n";
		status=false;
	}
	if(status){
		window
				.open(
						"./report.htm?param=printTuitionFeeClaimDetailsReport&returnValue=report&sfid="+$jq('#tuitionFeeSfid').val()+'&year='+$jq('#tuitionYear').val(),
						'preview',
						'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
			
		}else{
			alert(errorMessage);
		}	
}
function checkBoxCheckForTuitionFee(type) {
	
	var indSancTotal=0;
	var limitSanTotal=0;
	var status=true
	$jq('#dataTable').find('table tr:not(:first)').each(function(){
    	if($jq(this).find('td').eq(0).find('input:checkbox').attr('checked')==true){
    		indSancTotal=indSancTotal+parseInt($jq(this).find('td').eq(5).find('input').val());
    	}
    	//$jq('#displayCalculateTotal').val(indSancTotal);
        if(indSancTotal>=60000){
        	 status=false;
        	 //limitSanTotal=indSancTotal-parseInt($jq(this).find('td').eq(5).find('input').val());
        	 //$jq('#displayCalculateTotal').val(limitSanTotal);
        	 $jq(this).find('td').eq(0).find('input:checkbox').attr('checked',false);
        }
    })
    if (status==false){
    	alert("Amount Can not saved more than  "+60000+"  For Single Bill No");
    }
    
}
/*function selectingPeriodOfClaim(){
	 var status ="";
	 var errorMessage ="";
	 if($jq('#limitId').find('option:selected:visible').text()=='State-Q4-01-Jan-31-Mar' || $jq('#limitId').find('option:selected:visible').text()=='Central-Q4-01-Oct-31-Dec'){
		 errorMessage+="Please Select Q1,Q2,Q3.\n"
	     status=false;
	 }
	 if($jq('#limitId').find('option:selected:visible').text()=='State-Q3-01-Oct-31-Dec' || $jq('#limitId').find('option:selected:visible').text()=='Central-Q3-01-Jul-30-Sep'){
		 errorMessage+="Please Select Q1,Q2.\n"
	     status=false;
	 }
	 if($jq('#limitId').find('option:selected:visible').text()=='State-Q2-01-Jul-30-Sep' || $jq('#limitId').find('option:selected:visible').text()=='Central-Q2-01-Apr-30-Jun'){
		 errorMessage+="Please Select Q1.\n"
	     status=false;
	 }else{
		 alert(errorMessage);
		 $jq('#claimDeatailsDiv'+id).find('#limitId').find('option:selected:visible').text('Select');
	 }
}
*/







/*var acdmcVal='';
var idVal='';
function appendingAcademicYear(idValue){
	$jq('#claimDeatails').find('fieldset').each(function(){
		if($jq(this).is(':visible')){
			if(idVal==''){
				idVal=idValue;
			}
			if(idVal==idValue){
				if(acdmcVal==''){
					acdmcVal=$jq(this).find('select').eq(0).val();
				}
			}else{
				acdmcVal='';
				acdmcVal=$jq(this).find('select').eq(0).val();
			}
			var academicYear=$jq(this).find('select').eq(0).val();
			if($jq(this).find('select').eq(0).val()!='select'){
				if(acdmcVal==academicYear){
					if(idVal==idValue){
						$jq(this).find('div:not(:first)').each(function(){
							 if($jq(this).attr('id')!='tfID'){
								 if($jq(this).is(':visible')){
									 var divID=$jq(this).attr('id');
									 $jq(this).find('select').find('option:not(:first)').each(function(){
										 if($jq(this).is(':visible')){
											 var chars=$jq(this).text().split("-");
											 var textVal='';
											 for(var i=0;i<chars.length;i++){
												 textVal+=chars[i]+"-";
												 if(i==chars.length-1){
													 if(i==3 || i==5){
														 textVal+=academicYear;
													 }
												 }else{
													 if(i==3 || i==5){
														 textVal+=academicYear+"-";
													 }
												 }
											 }
											 var optVal=$jq(this).val();
											 $jq(this).remove();
											 $jq('#'+divID+'').find('select').append($jq('<option value='+optVal+'>'+textVal+'</option>'));
										 }	 
									 });
								}
							  }
						 });
					}else{
						$jq(this).find('div:not(:first)').each(function(){
							 if($jq(this).attr('id')!='tfID'){
								 if($jq(this).is(':visible')){
									 var divID=$jq(this).attr('id');
									 $jq(this).find('select').find('option:not(:first)').each(function(){
										 if($jq(this).is(':visible')){
											 var chars=$jq(this).text().split("-");
											 if(chars.length==6){
												 var textVal='';
												 for(var i=0;i<chars.length;i++){
													 textVal+=chars[i]+"-";
													 if(i==chars.length-1){
														 if(i==3 || i==5){
															 textVal+=academicYear;
														 }
													 }else{
														 if(i==3 || i==5){
															 textVal+=academicYear+"-";
														 }
													 }
												 }
											 }else{
												 var textVal='';
												 for(var i=0;i<chars.length;i++){
													 if(i!=4 && i!=7){
														 textVal+=chars[i]+"-";
													 }
													 if(i==chars.length-1){
														 if(i==4 || i==7){
															 textVal+=academicYear;
														 }
													 }else{
														 if(i==4 || i==7){
															 textVal+=academicYear+"-";
														 }
													 }
												 }
											 }
											 var optVal=$jq(this).val();
											 $jq(this).remove();
											 $jq('#'+divID+'').find('select').append($jq('<option value='+optVal+'>'+textVal+'</option>'));
										 }	 
									 });
								}
							  }
						 });
					}
				}else{
					$jq(this).find('div:not(:first)').each(function(){
						 if($jq(this).attr('id')!='tfID'){
							 if($jq(this).is(':visible')){
								 var divID=$jq(this).attr('id');
								 $jq(this).find('select').find('option:not(:first)').each(function(){
									 if($jq(this).is(':visible')){
										 var chars=$jq(this).text().split("-");
										 var textVal='';
										 for(var i=0;i<chars.length;i++){
											 if(i!=4 && i!=7){
												 textVal+=chars[i]+"-";
											 }
											 if(i==chars.length-1){
												 if(i==4 || i==7){
													 textVal+=academicYear;
												 }
											 }else{
												 if(i==4 || i==7){
													 textVal+=academicYear+"-";
												 }
											 }
										 }
										 var optVal=$jq(this).val();
										 $jq(this).remove();
										 $jq('#'+divID+'').find('select').append($jq('<option value='+optVal+'>'+textVal+'</option>'));
									 }	 
								 });
							}
						  }
					 });
				}
			}
		}
	});	
}
*/