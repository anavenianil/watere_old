var sfidResult = "";
var pathName="";
var masterID = "";
var orderDetailsObj = new Object();
var newCategory = "";
var designationID = "";
var levelID = "";
var nodeID = "";
var nodeParentID = "";
var listObj = new Object();
var delistObj = new Object();
var newRoleName = "";
var newDivision = "";
var districtJSONList = new Object();
var roleParentJSONList = new Object();
var subInstanceJSONList = new Object();
var internalTreeID = "";
var familyID = "";
var nomineeID = "";
var addressID = "";
var nomineeJSONList = new Object();
var preOrgJSONList = new Object();
var percentageList = new Object();
var familyMemberInNominee = new Object();
var noOfContengensy = 0;
var designationListJSON = new Object();
var instanceListJSON = new Object();
var applicationRolesListJSON = new Object();
var applicationRoleID="";
var percentage = 0;
var gfamilyID = "";
var editNewDivision = "";
var funtype = "";
var applicationRolesMapJSON = new Object();
var dispUrl = "";
var subOrdinatesList = new Object();
var internalDivisionsList = new Object();
var internalRolesList = new Object();
var departmentID= "";
var addressDetails = new Object();
var leaveRequestDetails = new Object();
var validation = new Object();
var normalLeavesJSONList = new Object();
var convertLeavesDetails = "";
var type="";
var autoX='';
var parentJSONList = new Object();
var holidays = "";
var jsonAvailabeLeavesList = new Array();
var leavesExists = true;
var requestDetails = new Object();
var editableFromDate = "";
var editableToDate = "";
var referralList = new Object();
var wardTypeList = new Object();
var familyMember = new Object();
var reqTypeID = "";
var ltcApplicationBean = new Object();
var amendmentType = "";
var amendmentRequestId = "";
var noOfPerson=0;
var gazType ="";
var selectedLeave = "";
var familyImpactListJson = new Object();
var familyMembersListJson = new Object();
var availableLeavesDetailsJson = new Object();
var exceptionalDetailsListJson = new Object();
var leaveCreditsListJson = new Object();
var specialLeavesListJson = new Object();
var LeaveRelationMappingListJson = new Object();
var balance="0";
var holidayID="";
//Exceptional Keys
var priorAppr = "PRIOR_APPR";
var minLeaves = "MIN_PER_SPELL";
var maxLeaves = "MAX_PER_SPELL";
var otherCredit = "OTHER_CREDIT";
var fitCert = "FIT";
var medCert = "MED";
var otherCert = "OTHER_CERT";
var dispExceptionsIds = "";
var eol = "6";
var pl = "8";
var cml = "4";
var hpl = "2";
var lnd = "5";
var adpm = "16";
var cl = "3";
var maxLeavesLimit="";

var excepstatus = true;
var confNoOfMembers = "0";
var empChildrens = "0";
var jsonLeaveSpellsList =  new Object();
var requestListJSON = new Object();
var selectedDPDate = "";
var menuID="";
var instanceId='0';
var headID = "";
var jsonArrayObject = new Object();
var editSfid = "";
var isDefaultRole = "";
var jsonChildArrayObject = new Object();
var multipleDesigList = "";
var professionalTaxList = new Object();
var jsonSelectedArrayObject = new Object();
var LtcFamilyMemberList = new Object();
var departureDate='';
var returnDate='';
var checkDate = '';
var district = "";
var leaveStatus = "";
var cghsAdvance = "";

var jsonmasterlist="";
var empID="";
var payID="";
var familyID="";
var gradePayID="";
var quarterTypeID="";
var payDesID="";
var licenceFeeID="";

var internal = "45";
var external = "46";
var selfTransferRequestID = "28";
var empTransferRequestID = "29";
var loanRequestID = "16";
var newQuarterRequestID = "33";
var changeQuarterRequestID = "34";

var hqRequestID = "36";
var hqSancOfIncRequestID = "39";
var rolesListJSON = new Object();
var relationsListJSON = new Object();
var count = 0;
var relationshipListJSON = new Object();
var workflowStageListJSON = new Object();
var workflowStageDetailsJSON = new Object();
var incomeTaxStageListJSON = new Object();

//LOAN
var festivalID = "1";
var gpfAdvanceID = "2";
var gpfWithdrawalID = "3";
var motorcarID = "4";
var motorCycle = "5";
var pcID = "6";
var motorScooter = "7";
var moped = "8";
var jsonFestivalObject = new Object();
var globalMessage = "";
var jsonLoanAmountDetails = new Object();
var jsonLoanDesigMappings = new Object();
var jsonLoanAmountGrid = new Object();
var yearID = "";
var basicPay="";
var jsonHQReportDate = new Object();

//HBA
var plotAndConstructionID = "1";
var enlargingExistingHouseID = "2";
var readyBuiltHouseID = "3";
var ltcLeaveTypeList = new Object();
var ltcAdvanceDto = new Object();
var ltcAdvanceMoney=0;
var ltcSettlementReqID = "26";
var HBAID="9";




function tadaReportWaterRequestHome(){

	document.forms[0].param.value = "tadawaterReporthome";
	document.forms[0].action = "tadaWaterRequest.htm";
	document.forms[0].submit();
}



function ltcReportWaterRequestHome(){
	document.forms[0].param.value = "ltcwaterReporthome";
	document.forms[0].action = "ltcWaterRequest.htm";
	document.forms[0].submit();
}


//leave added newly by bkr

function ERPleaveLeaveBalance(){
	
	document.forms[0].param.value = "ErpEmpLeaveBalance";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}

function dataErpEntryHome() {
	//alert("k");
	document.forms[0].param.value = "dataErpEntryHome";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}


function ERPleaveApplicationForm(){
	document.forms[0].param.value = "ApplicationFormHome";
	document.forms[0].action = "leaveWaterRequest.htm";
	document.forms[0].submit();
}

/*function ERPleaveApplicationForm1(){
	document.forms[0].param.value = "ApplicationFormHome";
	document.forms[0].action = "empSearchHome.htm";
	document.forms[0].submit();
}*/


function LeaveDaysEntry(){
	document.forms[0].param.value = "LeaveWaterDaysEntry";
	document.forms[0].action = "leaveWaterRequest.htm";
	document.forms[0].submit();
}


//for erpLoanPurpose  added by mohib
function erpLoanPurpose(){
	document.forms[0].param.value = "erpLoanPurpose";
	document.forms[0].action = "erpLoanPurpose.htm";
	document.forms[0].submit();
}


//ltc Water screen functions


function ltcWaterSettlement(){
	document.forms[0].param.value = "ltcWaterSettAndReim";
	document.forms[0].action = "ltcWaterRequest.htm";
	document.forms[0].submit();
}



function ltcWaterRequestHome(){
	document.forms[0].param.value = "ltcwaterhome";
	document.forms[0].action = "ltcWaterRequest.htm";
	document.forms[0].submit();
}

function ltcWaterFinanceSettlement() {
	document.forms[0].param.value = "ltcWaterSettlements";
	document.forms[0].action = "ltcWaterRequest.htm";
	document.forms[0].submit();
}



///////////////////////////////////////////////////////////////////////////


function setSelectWidth(id){
    $jq(id).mouseover(function () {
        $jq(this).css("width","auto");
    });
    $jq(id).blur(function () {
        $jq(this).css("width","145px");
    }); 
}

function masterData(type) {
	document.forms[0].param.value = "home";
	document.forms[0].type.value = type;
	document.forms[0].action = "master.htm";
	document.forms[0].submit();
}
function desigMaster() {
	document.forms[0].param.value = "designation";
	document.forms[0].action = "master.htm";
	document.forms[0].submit();
}

function qualificationMaster(type) {
	document.forms[0].param.value = "qualification";
	document.forms[0].type.value = type;	
	document.forms[0].action = "master.htm";
	document.forms[0].submit();
}
function disciplineMaster(type) {
	document.forms[0].param.value = "discipline";
	document.forms[0].type.value = type;	
	document.forms[0].action = "master.htm";
	document.forms[0].submit();
}
function religionMaster(type) {
	document.forms[0].param.value = "religion";
	document.forms[0].type.value = type;	
	document.forms[0].action = "master.htm";
	document.forms[0].submit();
}
function pinMaster(type) {
	document.forms[0].param.value = "pin";
	document.forms[0].type.value = type;	
	document.forms[0].action = "master.htm";
	document.forms[0].submit();
}
function adharMaster(type) {
	document.forms[0].param.value = "adhar";
	document.forms[0].type.value = type;	
	document.forms[0].action = "master.htm";
	document.forms[0].submit();
}
function levelHome(type) {
	document.forms[0].param.value = "levelhome";
	document.forms[0].type.value = type;
	document.forms[0].action = "hierarchy.htm";
	document.forms[0].submit();
}

function nodeHome(type) {
	document.forms[0].param.value = "nodehome";
	document.forms[0].type.value = type;
	document.forms[0].action = "hierarchy.htm";
	document.forms[0].submit();
}
function instanceEmployeeMap() {
	document.forms[0].param.value = "instanceMap";
	document.forms[0].action = "hierarchy.htm";
	document.forms[0].submit();
}
function internalEmployeeMap() {
	document.forms[0].param.value = "internalMap";
	document.forms[0].action = "hierarchy.htm";
	document.forms[0].submit();
}
function linksMapping() {
	document.forms[0].param.value = "menuhome";
	document.forms[0].action = "menu.htm";
	document.forms[0].submit();
}
//Requesttype mapping
function getRequesetRoleLinks() {
	if($jq('#applicationRole').val()!='select'){
		$jq.post('menu.htm?param=requestLinkList&id=' + document.getElementById('applicationRole').value, function(html) {
			$jq("#requestTypeList").html(html);
			
		});
	}
}
function requestRoleMapping() {
	document.forms[0].param.value = "requestMapping";
	document.forms[0].action = "menu.htm";
	document.forms[0].submit();
}
function clearRequest() {
	document.getElementById('applicationRole').value = "select";
	for ( var i = 0; i < document.getElementById('SelectLeft').options.length; i++) {
		document.getElementById('SelectLeft').options[i].text = "";
		document.getElementById('SelectLeft').options[i].value = "";
	}
	for ( var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
		document.getElementById('SelectRight').options[i].text = "";
		document.getElementById('SelectRight').options[i].value = "";
	}
}
function RequestSubmit() {
	var selectedValues = "";
	var errorMessages = "";
	var flag = true;
	var status = false;
	if (document.getElementById('applicationRole').value == "select") {
		errorMessages = "Please Select Application Role Name\n";
		document.getElementById('applicationRole').focus();
		flag = false;
	}
	for ( var i = 0; i < document.getElementById('SelectRight').options.length; i++) {
			selectedValues += document.getElementById('SelectRight').options[i].value+ ",";
			status = true;
	}
	if (!status) {
		errorMessages += "Please Select Request Types\n";
		flag = false;
	}
	if (flag) {
		document.forms[0].param.value = "requestSubmit";
		document.forms[0].selectedLinks.value = selectedValues;
		//document.forms[0].action.value = "menu.htm";
		//document.forms[0].submit();
	   $jq.post('menu.htm', $jq('#requestLinks').serialize(),function(html) {
			$jq("#result").html(html);
			//clearRequest();
			
		});
	} else {
		alert(errorMessages);
	}
	
}

//End Requesttype mapping
function createEmployee() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "employee.htm";
	document.forms[0].submit();

}
function getAddress() {
	document.forms[0].param.value = "view";
	document.forms[0].action = "address.htm";
	document.forms[0].submit();
}
function viewQualification() {
	document.forms[0].param.value = "view";
	document.forms[0].action = "qualification.htm";
	document.forms[0].submit();
}
function getTraining() {

	document.forms[0].param.value = "view";
	document.forms[0].action = "training.htm";
	document.forms[0].submit();

}
function getPassport() {
	document.forms[0].param.value = "view";
	document.forms[0].action = "passport.htm";
	document.forms[0].submit();

}
function familyDetails() {
	document.forms[0].param.value = "view";
	document.forms[0].action = "family.htm";
	document.forms[0].submit();
}
function getEmpExperienceDetails() {
	document.forms[0].param.value = "getExperience";
	document.forms[0].action = "empExperience.htm";
	document.forms[0].submit();
}
function NomineeDetails() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "nominee.htm";
	document.forms[0].submit();
}
function PreOrgnDetails() {
	document.forms[0].param.value = "getPreOrgn";
	document.forms[0].action = "preOrgnDetails.htm";
	document.forms[0].submit();
}
function getAwardDetails() {
	document.forms[0].param.value = "view";
	document.forms[0].action = "awardDetails.htm";
	document.forms[0].submit();
}
function getQuarterDetails() {
	document.forms[0].param.value = "adminHome";
	document.forms[0].action = "quarterDetails.htm";
	document.forms[0].submit();
}
function viewEMUQuarterDetails(){
	document.forms[0].param.value = "emuHome";
	document.forms[0].action = "quarterDetails.htm";
	document.forms[0].submit();
}

function quarterGradePay(){
	document.forms[0].param.value = "quarterGradePay";
	document.forms[0].action = "quarterReq.htm";
	document.forms[0].submit();
}
function getRetirementDetails() {
	document.forms[0].param.value = "view";
	document.forms[0].action = "retirementDetails.htm";
	document.forms[0].submit();
}
function editEmployee() {
	document.forms[0].param.value = "editEmployee";
	document.forms[0].action = "employee.htm";
	document.forms[0].submit();
}

function viewGeneral() {
	document.forms[0].param.value = "viewProfile";
	document.forms[0].action = "employee.htm";
	document.forms[0].submit();
}
function viewAddressDetails() {
	document.forms[0].param.value = "viewAddress";
	document.forms[0].action = "address.htm";
	document.forms[0].submit();
}
function viewQualificationDetails() {
	document.forms[0].param.value = "viewQualificationDetails";
	document.forms[0].action = 'qualification.htm';
	document.forms[0].submit();
}
function viewTraining() {

	document.forms[0].param.value = "viewTraining";
	document.forms[0].action = 'training.htm';
	document.forms[0].submit();
}
function viewPassportDetails() {
	document.forms[0].param.value = "viewPassport";

	document.forms[0].action = "passport.htm";
	document.forms[0].submit();
}
function viewFamily() {
	document.forms[0].param.value = "viewFamilyDetails";
	document.forms[0].action = 'family.htm';
	document.forms[0].submit();
}
function viewExperienceDetails() {
	document.forms[0].param.value = "getExperienceDetails";
	document.forms[0].action = "empExperience.htm";
	document.forms[0].submit();
}
function viewNomineeDetails() {
	document.forms[0].param.value = "viewNominee";
	document.forms[0].action = "nominee.htm";
	document.forms[0].submit();
}
function viewPreOrganisationDetails() {
	document.forms[0].param.value = "getOrganisationDetails";
	document.forms[0].action = "preOrgnDetails.htm";
	document.forms[0].submit();
}
function viewAwardDetails() {
	document.forms[0].param.value = "viewAwardDetails";
	document.forms[0].action = "awardDetails.htm";
	document.forms[0].submit();
}
function viewQuarterDetails() {
	document.forms[0].param.value = "view";
	document.forms[0].action = "quarterDetails.htm";
	document.forms[0].submit();
}
function quarterOfflineEntry() {
	document.forms[0].param.value = "quarterOfflineEntry";
	document.forms[0].action = "quarterReq.htm";
	document.forms[0].type.value ="quarterOfflineEntry";
	document.forms[0].submit();
}
function quarterOfflineWithOutWorkFlow() {
	document.forms[0].param.value = "quarterOfflineDetailsEntry";
	document.forms[0].action = "quarterReq.htm";
	document.forms[0].submit();
}

function emuQuarterDetails(typeView) {
	document.forms[0].param.value = "view";
	document.forms[0].type.value =typeView;
	document.forms[0].action = "quarterReq.htm";
	document.forms[0].submit();
}
function emuRequestFormDetails() {
	document.forms[0].param.value = "emuRequestForm";
	document.forms[0].action = "quarterReq.htm";
	document.forms[0].submit();
}

function viewRetirementDetails() {
	document.forms[0].param.value = "viewRetirementDetails";
	document.forms[0].action = "retirementDetails.htm";
	document.forms[0].submit();
}
function viewEmpSearchDetails() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "empSearch.htm";
	document.forms[0].submit();
}
function viewEmpTreeDetails(type) {
	document.forms[0].param.value = "empTree";
	document.forms[0].type.value =type;
	document.forms[0].action = "empSearch.htm";
	document.forms[0].submit();
}
function createCategoryReport() {
	document.forms[0].param.value = "categoryreport";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}
function createInventoryReport() {
	document.forms[0].param.value = "inventoryreport";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}
function higherQualification(){
	document.forms[0].param.value = "home";
	document.forms[0].action = "higherQualification.htm";
	document.forms[0].submit();
}
//function createBalanceReport() {
//	document.forms[0].param.value = "balancereport";
//	document.forms[0].action = "report.htm";
//	document.forms[0].submit();
//}
function createDivisionWiseReport() {
	document.forms[0].param.value = "divisionReport";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}
function createDesignationReport() {
	document.forms[0].param.value = "designationwiseReport";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}

function createEmpDOJReport() {
	// alert("hello");
	document.forms[0].param.value = "empDOJwiseReport";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}

function createGroupwiseReport() {
	document.forms[0].param.value = "EmpGroupwiseReport";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}

function createPHEmpReport() {
	document.forms[0].param.value = "PHEmpReport";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}

function createReligionWiseReport() {
	document.forms[0].param.value = "EmpReligionwiseReport";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}
function createReservationWiseReport(type) {
	document.forms[0].param.value = "EmpReservationwiseReport";
	document.forms[0].type.value=type;
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}
function createCommunityWiseReport() {
	document.forms[0].param.value = "EmpCommunityReport";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}
function createLasrModifiedReport(type) {
	document.forms[0].type.value=type;
	document.forms[0].param.value = "EmpLastModifyReport";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}
function createEmpExpReport() {
	window.open('./report.htm?param=EmpExperienceReport','Report','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
	
}
function createDisabilitiesAnnualReport() {
	document.forms[0].param.value = "phVacant";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}
function createNominalRole(){
	document.forms[0].param.value = "nominalRoleReport";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}
function createHierarchyReport (type) {
	document.forms[0].type.value=type;
	document.forms[0].param.value = "EmpHierarchyHome";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}

function organizationReport(){
	document.forms[0].param.value = "organizationReport";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}

function EmpMapping(){
	document.forms[0].param.value = "empMapping";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}
function createRetirementReport() {
	window.open('./report.htm?param=retirementWiseReport','Report','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')	
}
//financeStatusActiveReport
function financeStatusActiveReport() {
	window.open('./report.htm?param=financeStatusActiveReport','Report','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')	
}
//Agegrid
//financeStatusActiveReport
function AgeReport() {
	window.open('./report.htm?param=AgeReport','Report','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')	
}
//QualificationGrid
function QualificationReport() {
	window.open('./report.htm?param=QualificationReport','Report','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')	
}
//function createWorkflow() {
//	document.forms[0].param.value = "workflowhome";
//	document.forms[0].action = "workflowmap.htm";
//	document.forms[0].submit();
//
//}
function createWorkflow() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "createworkflow.htm";
	document.forms[0].submit();

}
function workFlowMapping() {
	document.forms[0].param.value = "reqworkflow";
	document.forms[0].action = "workflowmap.htm";
	document.forms[0].submit();
}

//function userHome() {
//
//	document.forms[0].param.value = "userHome";
//	document.forms[0].action = "workflowmap.htm";
//	document.forms[0].submit();
//}

function viewProfile() {
	document.forms[0].param.value = "request";
	document.forms[0].action = "employee.htm";
	document.forms[0].submit();
}
function EmployeeMapping() {
	document.forms[0].param.value = "EmpMapping";
	document.forms[0].action = "hierarchy.htm";
	document.forms[0].submit();
}
function requestDelegation() {
	document.forms[0].param.value = "SuperAdminDelegation";
	document.forms[0].action = "workflow.htm";
	document.forms[0].submit();
}
function changeEmployeeSFID() {
	document.forms[0].param.value = "changeEmpSfid";
	document.forms[0].action = "master.htm";
	document.forms[0].submit();
}
function appRolesMapping() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "approles.htm";
	document.forms[0].submit();
}
function configurationDetails(){
	document.forms[0].param.value = "home";
	document.forms[0].action = "configuration.htm";
	document.forms[0].submit();
}
function pisHome() {
	document.forms[0].param.value = "pisHome";
	document.forms[0].action = "workflowmap.htm";
	document.forms[0].submit();
}
function logout() {
	document.forms[0].action = "logout.htm";
	document.forms[0].submit();
	/*
		$jq.post(
				'logout.htm',
				function(data){ 
					caslogout();					
				} 
			)
*/}
function employeeAllDetails() {	
	document.forms[0].param.value = "employeeAllDetails";
	document.forms[0].action = "empSearch.htm";
	document.forms[0].submit();	
}
//PROMOTION LINKS START
function residencyPeriodHome()
{
	document.forms[0].param.value="home";
	document.forms[0].action="promotion.htm";
	document.forms[0].submit();
}
function PromotionPayUpdate(){
	document.forms[0].param.value="promotionPayUpdateHome";
	document.forms[0].type.value="promotionPayUpdate";
	document.forms[0].action="promotion.htm";
	document.forms[0].submit();
}
//PROMOTION LINKS END

//LEAVE LINKS START
function holidayHome() {
	document.forms[0].param.value = "holidayHome";
	document.forms[0].action = "holidays.htm";
	document.forms[0].submit();
	
}
function leaveManagementHome() {
	document.forms[0].param.value = "leaveManagementHome";
	document.forms[0].action = "leave.htm";
	document.forms[0].submit();
}

function leaveRelationMappingHome(){
	document.forms[0].param.value = "leaveRelationMappingHome";
	document.forms[0].action = "leave.htm";
	document.forms[0].submit();
}


function leaveApplicationForm(){
	document.forms[0].type.value = null;
	document.forms[0].param.value = "home";
	document.forms[0].action = "leaveRequest.htm";
	document.forms[0].submit();
}

function offlineLeave(){
	document.forms[0].type.value = "offline";
	document.forms[0].param.value = "home";
	document.forms[0].action = "leaveRequest.htm";
	document.forms[0].submit();
}

function dateEntryHome() {
	document.forms[0].param.value = "dataEntryHome";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}

function verfyLeaveBalance(){
	document.forms[0].param.value = "verifyHome";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}

function dopartHome(){
	document.forms[0].param.value = "dopartHome";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}

function doPartSearchHome(type){
	if(type=="r"){
		document.forms[0].param.value = "releaseDOPart";
		document.forms[0].type.value = type;
	}else if(type=="a"){
		document.forms[0].param.value = "releaseDOPart";
		document.forms[0].type.value = type;
	}else if (type=="all"){
		document.forms[0].param.value = "releaseDOPart";
		document.forms[0].type.value = type;
	}
	else{
	document.forms[0].param.value = "doPartSearch";
	}
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}

function typeHome(){
	document.forms[0].param.value = "typeHome";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}

function taskHolderHome(){
	document.forms[0].param.value = "taskHolderHome";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}
function casualitiesHome(){
	document.forms[0].param.value = "casualitiesHome";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}

function typeDesignationMasterHome(){
	document.forms[0].param.value = "TypeDesignationHome";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}

function doPartDetailsHome(){
	document.forms[0].param.value = "doPartDetailsHome";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}
function doPartModules(){
	document.forms[0].param.value = "DoPartModulesHome";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}
function leaveSearchHome(){
	document.forms[0].param.value = "leaveSearch";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}
function leaveTxnSearch(){
	document.forms[0].param.value = "leaveTxnHome";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}

function leaveCard(){
	document.forms[0].param.value = "leavecard";
	document.forms[0].action = "leaveRequest.htm";
	document.forms[0].submit();
}

function leavesBalanceHome() {
	document.forms[0].param.value = "leaveBalance";
	document.forms[0].action = "leaveRequest.htm";
	document.forms[0].submit();
}

function leaveAccount(){
	window.open('./leaveRequest.htm?param=leaveAccount&folder=LeaveAccount','Leave','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}

function empLeaveAccount(){
	document.forms[0].param.value = "empLeaveAccount";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}

function leaveScript(){
	document.forms[0].param.value = "manualScript";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}
function leaveExceptionalEmployee(){
	document.forms[0].param.value = "leaveExceptionalEmp";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}
function leaveAdit(){
	document.forms[0].param.value = "leaveAditHome";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}
function leaveTxnView(){
	document.forms[0].param.value = "leaveTxnView";
	document.forms[0].action = "leaveRequest.htm";
	document.forms[0].submit();
}

function leaveBusinessRules() {
	document.forms[0].param.value = "businessRules";
	document.forms[0].action = "leaveAdmin.htm";
	document.forms[0].submit();
}
function availableLeavesReport(){
	window.open('./report.htm?param=availableLeavesReport&type=','Report','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')	
}
//LEAVE LINKS END

function raiseDemand() {
	document.forms[0].param.value ="home";
	document.forms[0].action="raiseDemand.htm";
	document.forms[0].submit();
}
// mmg master start
function mmgMasterData(type) {
	document.forms[0].param.value = "home";
	document.forms[0].type.value = type;
	document.forms[0].action = "mmgMaster.htm";
	document.forms[0].submit();
}
function mmgVouchers(type){
	document.forms[0].param.value = "home";
	document.forms[0].action = "mmgVoucher.htm";
	document.forms[0].submit();
}
// mmg master end
function securityChecking() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "securityDetails.htm";
	document.forms[0].submit();
}
function invoiceReceipt() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "invoiceReceipt.htm";
	document.forms[0].submit();
}

function leaveCard(){
	document.forms[0].param.value = "leavecard";
	document.forms[0].action = "leaveRequest.htm";
	document.forms[0].submit();
}
function cancelDemand(){
	document.forms[0].param.value = "cancelDemand";
	document.forms[0].action = "raiseDemand.htm";
	document.forms[0].submit();
}
function cancelInvoice(){
	document.forms[0].param.value = "cancelInvoice";
	document.forms[0].action = "invoiceReceipt.htm";
	document.forms[0].submit();
}
function viewInventoryHolder(){
	document.forms[0].param.value = "inventoryHolderHome";
	document.forms[0].action = "mmgMaster.htm";
	document.forms[0].submit();
}
function mmgConfigurations(){
	document.forms[0].param.value = "mmgConfiguration";
	document.forms[0].action = "mmgMaster.htm";
	document.forms[0].submit();
}
function caslogout(){
	$jq.post(pathName+'cas/logout',
				function(data){ 
					document.forms[0].action = "logout.htm";
					document.forms[0].submit();
				} 
			)
}

function setWidth(id) {
	$jq(id).css("width","auto");
}

function createOtherEmployee() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "outerEmp.htm";
	document.forms[0].submit();
}

//Loan starts

function loanTypeMaster(){
	document.forms[0].param.value = "home";
	document.forms[0].action = "loan.htm";
	document.forms[0].submit();
}
function loanFestivalMaster() {
	document.forms[0].param.value = "festivalHome";
	document.forms[0].action = "loan.htm";
	document.forms[0].submit();
}
function gpfRulesMaster(){
	document.forms[0].param.value = "gpfRulesHome";
	document.forms[0].action = "loan.htm";
	document.forms[0].submit();
}
function gpfClosingBalance() {
	document.forms[0].param.value = "gpfBalanceHome";
	document.forms[0].action = "loan.htm";
	document.forms[0].submit();
}
function loanTypeDetails(){
	document.forms[0].param.value = "loanTypeDetails";
	document.forms[0].action = "loan.htm";
	document.forms[0].submit();
}
function loanAmountDetails(){
	document.forms[0].param.value = "LoanAmountHome";
	document.forms[0].action = "loan.htm";
	document.forms[0].submit();
}
function offlineLoan(){
	document.forms[0].type.value = "offline";
	document.forms[0].param.value = "home";
	document.forms[0].action = "loanRequest.htm";
	document.forms[0].submit();
}
function loanRequest(){
	document.forms[0].type.value = null;
	document.forms[0].param.value = "home";
	document.forms[0].action = "loanRequest.htm";
	document.forms[0].submit();
}

function loanRelief() {
	document.forms[0].param.value = "loanReliefHome";
	document.forms[0].action = "loan.htm";
	document.forms[0].submit();
}

function sendingReport() {
	document.forms[0].param.value = "sendingReportHome";
	document.forms[0].action = "loan.htm";
	document.forms[0].submit();
}

function hqReport() {
	document.forms[0].param.value = "hqReportHome";
	document.forms[0].action = "loan.htm";
	document.forms[0].submit();

}

function conveyanceAdvanceDemand(){
	document.forms[0].param.value = "conveyanceReportshome";
	document.forms[0].action = "loan.htm";
	document.forms[0].submit();
}
function loanPaybillAcquittance(){
	document.forms[0].param.value = "loanAcquittanceReport";
	document.forms[0].action = "loan.htm";
	document.forms[0].submit();
}
function sanctionAndContingent(){
	document.forms[0].param.value = "sanctionAndContingent";
	document.forms[0].action = "loan.htm";
	document.forms[0].submit();
}
//Loan End
//HBA start
function houseBuildingLoan() {
	document.forms[0].type.value = null;
	document.forms[0].param.value = "hbahome";
	document.forms[0].action = "loanHbaRequest.htm";
	document.forms[0].submit();

}
function offlineLoanHBA(){
	document.forms[0].type.value = "offline";
	document.forms[0].param.value = "hbahome";
	document.forms[0].action = "loanHbaRequest.htm";
	document.forms[0].submit();
}
function hbaInterestRates() {
	document.forms[0].param.value = "hbaInterestRate";
	document.forms[0].action = "loan.htm";
	document.forms[0].submit();

}
//HBA END



//CGHS SCREENS Start
function referralHospitalHome() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "cghs.htm";
	document.forms[0].submit();
}
//CGHS SCREENS End

//LTC SCREENS START
/*function ltcConfigurations(){
	document.forms[0].param.value = "ltcConfiguration";
	document.forms[0].action = "ltcMaster.htm";
	document.forms[0].submit();
}*/

function ltcApprovalDetails() {
	document.forms[0].param.value = "ltcapprovalhome";
	document.forms[0].action="ltcApprovalRequest.htm"
	document.forms[0].submit();	
}
function ltcHome(type) {
	document.forms[0].param.value = "home";
	document.forms[0].type.value = type;
	document.forms[0].action = "ltcMaster.htm";
	document.forms[0].submit();
}
function ltcApprovalRequest(type){
	document.forms[0].param.value = "home";
	document.forms[0].type.value = type;
	document.forms[0].action = "ltcApprovalRequest.htm";
	document.forms[0].submit();
}
/*function ltcAdvanceRequest(){
	document.forms[0].param.value = "home";
	document.forms[0].action = "ltcAdvanceRequest.htm";
	document.forms[0].submit();
}*/

function ltcEncashmentHistory(){
	document.forms[0].param.value = "ltcencashmentdays";
	document.forms[0].action = "ltcApprovalRequest.htm";
	document.forms[0].submit();
}


function FinanceHome(){
	document.forms[0].param.value = "financeHome";
	document.forms[0].action = "ltcApprovalRequest.htm";
	document.forms[0].submit();
}
function adminEntryHome(){
	document.forms[0].param.value = "adminEntryHome";
	document.forms[0].action = "ltcApprovalRequest.htm";
	document.forms[0].submit();	
}

function ltcCDAFinanceSettlement(){ 
	document.forms[0].param.value = "ltcCDASettlements";
	document.forms[0].action = "ltcApprovalRequest.htm";
	document.forms[0].submit();
	} 
function ltcHistoryReport(){
	
	window.open('./report.htm?param=initial&type=ltcHistory&refSfID=','MyLTCHistory','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
function ltcExperienceReport(){
	
	window.open('./report.htm?param=initial&type=ltcExperience','LTCExperience','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}
//LTC SCREENS END
function wardTypeHome() {
	document.forms[0].param.value = "wardHome";
	document.forms[0].action = "cghs.htm";
	document.forms[0].submit();
}
function createCustomReport(){
	document.forms[0].param.value = "customreport";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}
function treatmentRequestHome(type) {
	document.forms[0].type.value = type;
	document.forms[0].param.value = "home";
	document.forms[0].action = "cghsRequest.htm";
	document.forms[0].submit();
}
//CGHS SCREENS End
function reimbursementRequestHome() {
	document.forms[0].param.value = "reimHome";
	document.forms[0].action = "cghsRequest.htm";
	document.forms[0].submit();
}
/*function advanceRequest(requestId) {
	document.forms[0].requestId.value = requestId;
	document.forms[0].param.value = "advance";
	document.forms[0].action = "cghsRequest.htm";
	document.forms[0].submit();
}*/
function reimbursementRequest(requestId,type,cdaAmount) {
	var status=true;
	var errorMessage='';
	document.forms[0].type.value = type;
	if(type!='noncghsReimbursement') {
		document.forms[0].requestId.value = requestId;
	}
if(type=='cghssettlement' && cdaAmount=='') {
		errorMessage+='CDA amount not yet issued';
		$jq('#dischargeDate').val('');
		status = false;
	}
	if(status){
	document.forms[0].param.value = "reimbursement";
	document.forms[0].action = "cghsRequest.htm";
	document.forms[0].submit();
	}else{
		alert(errorMessage);
	}
}
function settlementRequest() {
	document.forms[0].param.value = "reimbursement";
	document.forms[0].action = "cghsRequest.htm";
	document.forms[0].submit();
}
function emergencyRequest() {
	document.forms[0].param.value = "emergency";
	document.forms[0].action = "cghsRequest.htm";
	document.forms[0].submit();
}
function cghsFinanceHome() {
	document.forms[0].param.value = "financeHome";
	document.forms[0].action = "cghsRequest.htm";
	document.forms[0].submit();

}

//cda settlement screen for cghs
function cghsCDAFinanceSettlement() {
	document.forms[0].param.value = "cdaHome";
	document.forms[0].action = "cghsRequest.htm";
	document.forms[0].submit();

}
//CGHS SCREENS End

//Script for Report Details screen
function CpoolDetails() {

	document.forms[0].param.value = "home";
	document.forms[0].action = "reportDetails.htm";
	document.forms[0].submit();

}
function ReportDetails() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "reDetails.htm";
	document.forms[0].submit();
}
function areaofDeployment() {
	document.forms[0].param.value = "areaofDeployment";
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}

//start of emp Payment
function getEmpPaymentDetails() {
	document.forms[0].param.value = "empPaymentHome";
	document.forms[0].action = "empPayment.htm";
	document.forms[0].submit();
}


function getEmpBGPaymentDetails() {
	document.forms[0].param.value = "empBGPaymentHome";
	document.forms[0].action = "empPayment.htm";
	document.forms[0].submit();
}
//end of emp Payment

function menuLinksMapping(){
	document.forms[0].param.value = "menuLinkMap";
	document.forms[0].action = "menu.htm";
	document.forms[0].submit();
}
//organizational structure
function orgLevelStr(type){
	document.forms[0].param.value = "home";
	document.forms[0].type.value = type;
	document.forms[0].action = "orgStructure.htm";
	document.forms[0].submit();
}
function departmentsStr(){
	document.forms[0].param.value = "deptHome";
	document.forms[0].action = "orgStructure.htm";
	document.forms[0].submit();
}

function rolesStr(type) {
	document.forms[0].param.value = "roleHome";
	document.forms[0].type.value = type;
	document.forms[0].action = "orgStructure.htm";
	document.forms[0].submit();
}

function orgRoleMapping(){
	document.forms[0].param.value = "empRoleMap";
	document.forms[0].action = "orgStructure.htm";
	document.forms[0].submit();
}

function normalEmpMapping(){
	document.forms[0].param.value = "normalEmp";
	document.forms[0].action = "orgStructure.htm";
	document.forms[0].submit();

}
//pay Scale
function payScale(type) {
	document.forms[0].param.value = "payScaleHome";
	document.forms[0].type.value = type;
	document.forms[0].action = "payScale.htm";
	document.forms[0].submit();
}
function payAllowancConfiguration()
{
	document.forms[0].param.value="payAllowanceHome";
	document.forms[0].action="payBill.htm";
	document.forms[0].submit();
}
function payBillAllowConfigurationTypeDetails()
{
	document.forms[0].param.value="payAllwDetailsHome";
	document.forms[0].action="payScale.htm";
	document.forms[0].submit();
}
function payBillAllowType()
{
	document.forms[0].param.value="payAllwTypeHome";
	document.forms[0].action="payScale.htm";
	document.forms[0].submit();
}

function payBillAllowConfigurationDetails()
{
	document.forms[0].param.value = "home";
	document.forms[0].type.value="paybill";
	document.forms[0].action = "configuration.htm";
	document.forms[0].submit();
}
function payBandCreation()
{
	document.forms[0].param.value="payBandMasterHome";
	document.forms[0].action="payScale.htm";
	document.forms[0].submit();
}

function ITMaster() {
	document.forms[0].param.value = "ITHome";
	document.forms[0].action = "payBill.htm";
	document.forms[0].submit();
}
function payEmpCategory(){
	document.forms[0].param.value = "empCategoryHome";
	document.forms[0].action = "payBill.htm";
	document.forms[0].submit();
}
function empPayDetailsEntry(){
	document.forms[0].param.value = "payBillHome";
	document.forms[0].action = "pay.htm";
	document.forms[0].submit();
}
function negPayEmp(){
	document.forms[0].param.value = "getNegPayEmp";
	document.forms[0].action = "pay.htm";
	document.forms[0].submit();
}
function payDuesEntry(){
	document.forms[0].param.value = "payDueHome";
	document.forms[0].action = "payBill.htm";
	document.forms[0].submit();
}
function PayBillStatusEntry(){
	document.forms[0].param.value = "payBillStatusHome";
	document.forms[0].action = "pay.htm";
	document.forms[0].submit();
}
function showPayslip(){
	document.forms[0].param.value = "paySlipHome";
	document.forms[0].action = "pay.htm";
	document.forms[0].submit();
}
function payEmpConfiguration(){
	document.forms[0].param.value = "payEmpConfigurationDetails";
	document.forms[0].action = "payBill.htm";
	document.forms[0].submit();
}
function paySupplimentaryDetails(){
	document.forms[0].param.value = "payEmpSupplimentaryDetails";
	document.forms[0].action = "pay.htm";
	document.forms[0].submit();
}
function payDeduction(){
	document.forms[0].param.value = "coinCutSearch";
	document.forms[0].action = "payBill.htm";
	document.forms[0].submit();
}

function quarterType() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "quarterType.htm";
	document.forms[0].submit();
}
function payScaleDesignation() {
	document.forms[0].param.value = "payDesignationHome";
	document.forms[0].action = "payScale.htm";
	document.forms[0].submit();
}
function licenceFeeCharges()
{
	document.forms[0].param.value = "licenceFeeHome";
	document.forms[0].action = "quarterType.htm";
	document.forms[0].submit();
}
function roleSpecific(){
	document.forms[0].param.value = "roleSpecific";
	document.forms[0].action = "orgStructure.htm";
	document.forms[0].submit();
}
function payCGEISMaster() {
	document.forms[0].param.value = "cgeisMaster";
	document.forms[0].action = "payBill.htm";
	document.forms[0].submit();
}
function payCGHSMaster(){
	document.forms[0].param.value = "cghsMaster";
	document.forms[0].action = "payBill.htm";
	document.forms[0].submit();
}
function payOneTimeEntry()
{
	document.forms[0].param.value="payOneTimeEntryHome";
	document.forms[0].action="payBill.htm";
	document.forms[0].submit();
}
function payEOL(){
	document.forms[0].param.value = "payEolHome";
	document.forms[0].action = "payBill.htm";
	document.forms[0].submit();
}
function payLaonDetails()
{
	document.forms[0].param.value="payLoanDetailsHome";
	document.forms[0].action="payBill.htm";
	document.forms[0].submit();
}
function payCcsUpload(){
	document.forms[0].param.value = "homeCCS";
	document.forms[0].action = "payBill.htm";
	document.forms[0].submit();
}
function payDAMaster() {
	document.forms[0].param.value = "DAMaster";
	document.forms[0].action = "payBill.htm";
	document.forms[0].submit();
}
function payRSMaster() {
	document.forms[0].param.value = "RSMaster";
	document.forms[0].action = "payBill.htm";
	document.forms[0].submit();
}
function payScaleTAIMaster() {
	document.forms[0].param.value = "TAIMasterHome";
	document.forms[0].action = "payScale.htm";
	document.forms[0].submit();
}
function payAllwTypeHome() {
	document.forms[0].param.value = "payAllwTypeHome";
	document.forms[0].action = "payScale.htm";
	document.forms[0].submit();
}

//paysclae end 
function payFYMaster() {
	document.forms[0].param.value = "FYMaster";
	document.forms[0].action = "incomeTax.htm";
	document.forms[0].submit();
}

function incomeTaxMaster() {
	document.forms[0].param.value = "ITMaster";
	document.forms[0].action = "incomeTax.htm";
	document.forms[0].submit();
}
function incomeTaxConfiguration(){
	document.forms[0].param.value = "ITConfiguration";
	document.forms[0].action = "incomeTax.htm";
	document.forms[0].submit();
}
function arrearsDetails(){
	document.forms[0].param.value = "arrears";
	document.forms[0].action = "incomeTax.htm";
	document.forms[0].submit();
}
function configDetails(){
	document.forms[0].param.value = "configHome";
	document.forms[0].action = "incomeTax.htm";
	document.forms[0].submit();
}
function caluculateITDetails(){
	document.forms[0].param.value = "caluculateHome";
	document.forms[0].action = "incomeTax.htm";
	document.forms[0].submit();
}
function myITSlip(){
	document.forms[0].param.value = "myITSlipHome";
	document.forms[0].action = "incomeTax.htm";
	document.forms[0].submit();
}
function myITSlipFinance(){
	document.forms[0].param.value = "myITSlipFinance";
	document.forms[0].action = "incomeTax.htm";
	document.forms[0].submit();
}
function sectionMaster(){
	document.forms[0].param.value = "sectionHome";
	document.forms[0].action = "incomeTax.htm";
	document.forms[0].submit();
}
function sectionMapping(){
	document.forms[0].param.value = "sectionMappingMaster";
	document.forms[0].action = "incomeTax.htm";
	document.forms[0].submit();
}
function savingsMaster(){
	document.forms[0].param.value = "savings";
	document.forms[0].action = "incomeTax.htm";
	document.forms[0].submit();
}
function allowanceConfMaster(){
	document.forms[0].param.value = "allowanceConfiguration";
	document.forms[0].action = "incomeTax.htm";
	document.forms[0].submit();
}
function prUpadteAllowanceMaster(){
	document.forms[0].param.value = "prUpdate";
	document.forms[0].action = "incomeTax.htm";
	document.forms[0].submit();
}
function transfer(type){
	document.forms[0].type.value = type;
	document.forms[0].param.value = "home";
	document.forms[0].action = "transfer.htm";
	document.forms[0].submit();
}
function transferTxnDetails(){
	document.forms[0].param.value = "transferTxn";
	document.forms[0].action = "transfer.htm";
	document.forms[0].submit();
}
function viewFiles(){
	document.forms[0].param.value = "viewFile";
	document.forms[0].action = "workflowmap.htm";
	document.forms[0].submit();
}

//start schedule reports
function screports()
{
	document.forms[0].param.value = "schedulereportshome";
	document.forms[0].action = "scheduleReports.htm";
	document.forms[0].submit();
}
//end schedule reports=======

//promotion start

function residencyPeriod() {
	document.forms[0].param.value = "home";
	document.forms[0].action = "promotion.htm";
	document.forms[0].submit();
}
//new BoardTypeInfo]

function boardTypeInfo(){
	document.forms[0].param.value = "boardInfo";
	document.forms[0].action = "promotion.htm";
	document.forms[0].submit();
}

//end BoardTypeInfo
function boardType() {
	document.forms[0].param.value = "boardTypeMaster";
	document.forms[0].action = "promotion.htm";
	document.forms[0].submit();
}
function localBoard() {
	document.forms[0].param.value = "localBoardHome";
	document.forms[0].action = "promotion.htm";
	document.forms[0].submit();
}
function venueDetails() {
	document.forms[0].param.value = "venueHome";
	document.forms[0].type.value = "venueHome";
	
	document.forms[0].action = "promotion.htm";
	document.forms[0].submit();
}
function addEmpAssessment() {
	document.forms[0].param.value = "addAssessmentHome";
	document.forms[0].action = "promotion.htm";
	document.forms[0].submit();
}
function desigExperience(){
	document.forms[0].param.value = "desigExperienceHome";
	document.forms[0].action = "promotion.htm";
	document.forms[0].submit();

}

function promotedCandidates(){
	document.forms[0].param.value = "promotedCandidatesHome";
	document.forms[0].action = "promotion.htm";
	document.forms[0].submit();
}
function payFixation(){
	document.forms[0].param.value = "payFixationHome";
	document.forms[0].type.value = "payFixation";
	document.forms[0].action = "promotion.htm";
	document.forms[0].submit();
}

function optionalCertificateHome(){
	document.forms[0].param.value = "home";
	document.forms[0].action = "promotionReq.htm";
	document.forms[0].type.value = "option";
	document.forms[0].submit();
}
function payFixationDOPart(){
	document.forms[0].param.value = "payFixationDOPartHome";
	document.forms[0].action = "promotion.htm";
	document.forms[0].submit();	
}
function viewOptionalCertificateHome(){
	document.forms[0].param.value = "optionalCertificatesHome";
	document.forms[0].type.value = "viewOtionalCertificates";
	document.forms[0].action = "promotion.htm";
	document.forms[0].submit();
}
function test(){
	document.forms[0].param.value = "testHome";
	document.forms[0].action = "test.htm";
	document.forms[0].submit();	
}
function doPartDetails(){
	document.forms[0].param.value="home";
	document.forms[0].action="doPart.htm";
	document.forms[0].submit();
}
function headQuarterSending(){
	document.forms[0].param.value="headQuarterSend";
	document.forms[0].action="promotion.htm";
	document.forms[0].submit();
}
function qualifiedCandidates(){
	document.forms[0].param.value = "qualifiedCandidatesHome";
	document.forms[0].action = "promotion.htm";
	document.forms[0].submit();
}
function promotionReportHome(){
	document.forms[0].param.value= "promotionReports";
	document.forms[0].action="promotion.htm";
	document.forms[0].submit();
}
function promotionDiscipline(){
	document.forms[0].param.value= "DisciplineMaster";
	document.forms[0].action="promotion.htm";
	document.forms[0].submit();
}
function promotionSubDiscipline(){
	document.forms[0].param.value= "SubDisciplineMaster";
	document.forms[0].action="promotion.htm";
	document.forms[0].submit();
}
function addExternalMember(){
	document.forms[0].param.value= "externalBoardMember";
	document.forms[0].action="promotion.htm";
	document.forms[0].submit();
}
function promotionOfflineEntryHome(){
	document.forms[0].param.value= "PromotionOfflineEntry";
	document.forms[0].action="promotion.htm";
	document.forms[0].submit();
}
//promotion ends

//Passport cum Proceeding Abroad Starts
function passportHome(){
	document.forms[0].param.value= "passportHome";
	document.forms[0].action="passport.htm";
	document.forms[0].submit();
}
function nocPassportHome(){
	document.forms[0].param.value= "nocPassportHome";
	document.forms[0].action="passport.htm";
	document.forms[0].submit();
}

function movablePropertyHome(){
	document.forms[0].param.value= "movablePropertyHome";
	document.forms[0].action="adminMisc.htm";
	document.forms[0].submit();
}
//Passport cum Proceeding Abroad Ends=======


//Admin Misc Starts
function adminMiscHome(type) {
	document.forms[0].param.value= "adminMiscview";
	document.forms[0].type.value = type;
	document.forms[0].action="adminMisc.htm";
	document.forms[0].submit();
}

//Admin Misc Ends


function normalReports(type){
	window.open('./report.htm?param=normalReport&type='+type,'Report','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')
}

//Family planning allowance
function fpaRequest(){
	document.forms[0].param.value = "fpaHome";
	document.forms[0].action = "fpaRequest.htm";
	document.forms[0].submit();
}

//water erp allowances
function allowancesRequest(){
	document.forms[0].param.value = "AllowancesHome";
	document.forms[0].action = "allowancesRequest.htm";
	document.forms[0].submit();
}

//Annual Increment
function annualIncrement(){
	document.forms[0].param.value = "annualIncrementHome";
	document.forms[0].action = "annualIncrement.htm";
	document.forms[0].submit();
}


/*function annualIncrementFinance(){
	document.forms[0].param.value = "AnnualIncrementFinance";
	document.forms[0].action = "annualIncrement.htm";
	document.forms[0].submit();
}*/
function annualIncrementDoReport() {
	window.open('./report.htm?param=annualIncrementDOReport','Report','fullscreen=no,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no')	
}
function annualIncrementFinalDetails(){
	document.forms[0].param.value = "annualIncrementFinalDetails";
	document.forms[0].action = "annualIncrement.htm";
	document.forms[0].submit();
}
function dbMigration(){
	document.forms[0].param.value = "dbMigrationHome";
	document.forms[0].action = "incomeTax.htm";
	document.forms[0].submit();
}

//TADA
function cityType(){
	document.forms[0].param.value = "cityType";
	document.forms[0].action = "tada.htm";
	document.forms[0].submit();
}
function daEntitle() {
	document.forms[0].param.value = "daEntitleMaster";
	document.forms[0].action = "tada.htm";
	document.forms[0].submit();
}
function daNewEntitle(){
	document.forms[0].param.value = "daNewEntitleMaster";
	document.forms[0].action = "tada.htm";
	document.forms[0].submit();
}
function travelType(){
	document.forms[0].param.value = "travelType";
	document.forms[0].action = "tada.htm";
	document.forms[0].submit();
}
function taEntitle() {
	document.forms[0].param.value = "taEntitleMaster";
	document.forms[0].action = "tada.htm";
	document.forms[0].submit();
}
function entitleType() {
	document.forms[0].param.value = "entitleType";
	document.forms[0].action = "tada.htm";
	document.forms[0].submit();
}
function travelTypeMap(){
	document.forms[0].param.value = "travelTypeMap";
	document.forms[0].action = "tada.htm";
	document.forms[0].submit();
}
function localRMA(){
	document.forms[0].param.value = "localRMA";
	document.forms[0].action = "tada.htm";
	document.forms[0].submit();
}

function daOnTour(){                                              //start daOnTour 
	document.forms[0].param.value = "daOnTour";
	document.forms[0].action = "tada.htm";
	document.forms[0].submit();
}                                                               //end daOnTour


//added by bkr 23/09/2016

function retrimentBenfitsHome(){
	
	
	document.forms[0].param.value = "retrimentHome";
	document.forms[0].action = "retriment.htm?param1=hiddendata";
	document.forms[0].submit();
}

function retrimentBenfitsAmtIssueHome(){
	document.forms[0].param.value = "retrimentAmtIssueHome";
	document.forms[0].action = "retriment.htm";
	document.forms[0].submit();
}






function tadaApprovalRequest(){
	document.forms[0].param.value = "tadaApprovalRequest";
	document.forms[0].action = "tadaApprovalRequest.htm";
	document.forms[0].submit();
}
function tadaTdAdvance(){
	document.forms[0].param.value = "tadaTdAdvance";
	document.forms[0].action = "tadaApprovalRequest.htm";
	document.forms[0].submit();
}

/*added by bkr 29/04/2016*/

function tadaAdvanceCumRequest(){
	//alert("welcome111");
	document.forms[0].param.value = "tadaAdvanceCumRequest";
	document.forms[0].action = "tadaWaterRequest.htm";
	document.forms[0].submit();
}


function tadaTdClaimAndSettlement() {
	document.forms[0].param.value = "tadaTdClaim";
	document.forms[0].action = "tadaApprovalRequest.htm";
	document.forms[0].submit();
}
//added by bkr 10/05/2016

function tadaWaterSettlement() {
	document.forms[0].param.value = "tadaWaterSettAndReim";
	document.forms[0].action = "tadaWaterRequest.htm";
	document.forms[0].submit();
}

function tadaFinanceSettlement() {
	document.forms[0].param.value = "tadaSettlements";
	document.forms[0].action = "tadaApprovalRequest.htm";
	document.forms[0].submit();
}
//added by bkr 07/04/2016 

function tadaWaterFinanceSettlement() {
	document.forms[0].param.value = "tadaWaterSettlements";
	document.forms[0].action = "tadaWaterRequest.htm";
	document.forms[0].submit();
}


function tadaProjectDirector() {
	document.forms[0].param.value = "tadaProjectDirector";
	document.forms[0].action = "tada.htm";
	document.forms[0].submit();
}
function taEntitleExcemption(){
	document.forms[0].param.value = "taEntilteExemption";
	document.forms[0].action = "tada.htm";
	document.forms[0].submit();
}
function program(){
	document.forms[0].param.value = "program";
	document.forms[0].action = "tada.htm";
	document.forms[0].submit();
}
function tadaAppliedList(){
	document.forms[0].param.value = "tadaAppliedUsers";
	document.forms[0].action = "tadaApprovalRequest.htm";
	document.forms[0].submit();
}


function tadaCDAFinanceSettlement(){                                        //start tadaCDAFinanceSettlement for cda settlements (prasad)
	document.forms[0].param.value = "tadaCDASettlements";
	document.forms[0].action = "tadaApprovalRequest.htm";
	document.forms[0].submit();
}



function tdUserDelegation(){
	document.forms[0].param.value = "tdUserDelegation";
	document.forms[0].action = "tadaApprovalRequest.htm";
	document.forms[0].submit();
	
}


/*-----------------Arrears start----------------------------------*/

function DAArrearsHome(){
	document.forms[0].param.value = "daArrearsHome";
	document.forms[0].action = "arrears.htm";
	document.forms[0].submit();
}
function promotionArrearsHome(){
	document.forms[0].param.value = "promotionArrearsHome";
	document.forms[0].action = "arrears.htm";
	document.forms[0].submit();
}
function anualIncrArrearsHome(){
	document.forms[0].param.value = "anualIncrArrearsHome";
	document.forms[0].action = "arrears.htm";
	document.forms[0].submit();
}
function fpaArrearsHome(){
	document.forms[0].param.value = "fpaArrearsHome";
	document.forms[0].action = "arrears.htm";
	document.forms[0].submit();
}
/*-----------------Arrears end----------------------------------*/

function financePaySlipHome(){
	document.forms[0].param.value = "financePaySlipHome";
	document.forms[0].action = "scheduleReports.htm";
	document.forms[0].submit();
}


/*-----------------Hrdg Training Start----------------------------------*/
function trainingMaster(type){
//	document.forms[0].param.value = "trainingHome";
//	
//	if(type=='course' || type=='CourseDuration' || type=='TrainingCirculationMaster' || type=='HRDGBoard' || type=='HRDGBoardMember' || type=='TrainingNominationMaster' || type=='TrainingNominationOfflineMaster' || type=='HrdgTrainingNominationDetails' || type=='TrainingNominationsCFASelection' || type=='TrainingRegionMaster' || type=='')
	document.forms[0].param.value = type;
	document.forms[0].type.value = type;
	
	if(type =='TrainingNominationMaster' || type =='TrainingNominationOfflineMaster' || type=='HrdgTrainingNominationDetails' || type=='TrainingNominationsCFASelection' || type=='ViewTrainingNominationDetails' || type=='HRDGYearBook' || type=='HRDGReport')
		document.forms[0].action = "trainingRequest.htm";
	else 
	document.forms[0].action = "trainingMaster.htm";

	if(type=='awardMaster')
	{
		document.forms[0].action = "master.htm";
	}

	document.forms[0].submit();
}

function letterNumber(type){
	document.forms[0].param.value = type;
	document.forms[0].type.value = type;
	document.forms[0].action = "letterNumberFormat.htm";
		document.forms[0].submit();
	
}
function ionCreation(type) {
	document.forms[0].param.value = type;
	document.forms[0].type.value = type;
	document.forms[0].action = "letterNumberFormat.htm";
		document.forms[0].submit();
}
function noticeHome()
{
	document.forms[0].param.value = 'noticeHome';
	document.forms[0].type.value = 'noticeHome';
	document.forms[0].action = "ion.htm";
	document.forms[0].submit();
}
/*-----------------Hrdg Training  end----------------------------------*/

	





function payHindiIncDetails(){
	document.forms[0].param.value = "payHindiHome";
	document.forms[0].action = "payBill.htm";
	document.forms[0].submit();
}
/*----------------TIUTION FEE START----------------------------------*/


function tutionFeeRequestFormHome(){
	document.forms[0].param.value = "tutionFeeRequestFormHome";
	document.forms[0].action = "tutionFee.htm";
	document.forms[0].submit();
}
function tutionFeeFinanceDetails(){
	document.forms[0].param.value = "tutionFeeFinanceHome";
	document.forms[0].action = "tutionFee.htm";
	document.forms[0].submit();
}
function tuitionFeeSentToCDA(){
	document.forms[0].param.value = "tuitionFeeSentToCDAHome";
	document.forms[0].action = "tutionFee.htm";
	document.forms[0].submit();
}
function tuitionFeeClaimMaster(){
	document.forms[0].param.value = "tuitionFeeClaimMaster";
	document.forms[0].action = "tutionFee.htm";
	document.forms[0].submit();
}
function tuitionFeeAcademicYearMaster(){
	document.forms[0].param.value = "tutionFeeAcademicYearMaster";
	document.forms[0].action = "tutionFee.htm";
	document.forms[0].submit();
}
function tuitionFeeConfiguration(){
	document.forms[0].param.value = "tuitionFeeConfiguration";
	document.forms[0].action = "tutionFee.htm";
	document.forms[0].submit();
}
function tuitionFeeLimitMaster(){
	document.forms[0].param.value = "tuitionFeeLimitMaster";
	document.forms[0].action = "tutionFee.htm";
	document.forms[0].submit();
}
function telePhoneBillDesignationEligibityDetails(){
	document.forms[0].param.value = "telePhoneBillEmployeeEligibityDetails";
	document.forms[0].action = "telephone.htm";
	document.forms[0].submit();
}
function telePhoneBillDesignationEmployeeDetails(){
	document.forms[0].param.value = "telePhoneBillDesignationEmployeeDetails";
	document.forms[0].action = "telephone.htm";
	document.forms[0].submit();
}
function telePhoneBillCashAssignmentDetails(){
	document.forms[0].param.value = "telePhoneBillCashAssignmentDetails";
	document.forms[0].action = "telephone.htm";
	document.forms[0].submit();
}
/*----------------Tuition Fee end----------------------------------*/


function goTopayBill(){
	//alert("welcome");
	document.forms[0].param.value = "payWelcomeHome";
	//document.forms[0].action = "/pay/payBill.htm";
	document.forms[0].action = "payBill.htm";
	document.forms[0].submit();
}
function telePhoneBillEligibityHome(){
	document.forms[0].param.value = "telephoneBillEligibityParam";
	document.forms[0].action = "telephone.htm";
	document.forms[0].submit();
}
function telePhoneBillRequestHome(){
	document.forms[0].param.value = "telephoneBillRequestFormParam";
	document.forms[0].action = "telephone.htm";
	document.forms[0].submit();
}
function telePhoneBillFinanceHome(){
	document.forms[0].param.value = "telephoneBillFinanceParam";
	document.forms[0].action = "telephone.htm";
	document.forms[0].submit();
}
function telePhoneBillCDAHome(){
	document.forms[0].param.value = "telephoneBillCDAParam";
	document.forms[0].action = "telephone.htm";
	document.forms[0].submit();
}
function claimsAmendementDetails(){
	document.forms[0].param.value = "claimsAmendementDetailsParam";
	document.forms[0].action = "tutionFee.htm";
	document.forms[0].submit();
}
function financeClaimsAmendementDetails(){
	document.forms[0].param.value = "financeClaimsAmendementDetailsParam";
	document.forms[0].action = "tutionFee.htm";
	document.forms[0].submit();
}
/*----------------Tuition Fee end----------------------------------*/
function svnVersion(){
	document.forms[0].action = "svn.htm";
	document.forms[0].submit();
	}

/*---------------MechincalTransport Start-----------------------*/


function mtCategory(){
	document.forms[0].param.value = "category";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}

function mtModel(){
	document.forms[0].param.value = "model";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();		
}

function mtTravelAgency(){
	document.forms[0].param.value = "travelAgency";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}

function mtAddNewDriver(){
	document.forms[0].param.value = "newDriver";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}

function mtAddNewVehicle(){
	document.forms[0].param.value = "newVehicle";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}
function mtAddNewAddress(){
	document.forms[0].param.value = "newAddress";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}
function mtRequestForVehicle(){
	document.forms[0].param.value = "vehicleRequestForm";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}


function AllocateDrivers(){
	document.forms[0].param.value = "driver";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();	
}


function mtAbsentDriver(){
	document.forms[0].param.value = "DriverAbsent";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}

function mtAbsentVehicle(){
	document.forms[0].param.value = "VehicleAbsent";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}

function mtVehicleVsDriver(){
	document.forms[0].param.value = "VehicleVsDriver";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}

function mtVehicleVsDriverList(){
	document.forms[0].param.value = "VehicleVsDriverDetails";
	document.forms[0].type.value='';                                //The line has been added for change driver gui problem
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}

function mtReleaseVehicle(){
	document.forms[0].param.value = "ReleaseVehicle";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}

function mtGetAllAllotedVehicles(){
	document.forms[0].param.value = "ReleaseAllVehicles";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}
function mtMileage(){
	document.forms[0].param.value = "Mileage";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}
function mtOfflineRequest(){
	document.forms[0].param.value = "OfflineVehicleRequestForm";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}
function mtGetApprovedRequests(){
	document.forms[0].param.value = "VehicleAllocation";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}
function mtDayWiseAllocation(){
	document.forms[0].param.value = "mtDayWiseAllocation";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}
function mtGetAllDedicatedVehicles(){
	document.forms[0].param.value = "getDedicatedVehicles";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}
function mtCompletionDetails(){
	document.forms[0].param.value = "getCompletionDetails";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}
function mtDailyMileageDetails(){
	document.forms[0].param.value = "getDailyMileageDetails";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}
function getYearlyMileageDetails(){
	document.forms[0].param.value = "getYearlyMileageDetails";
	document.forms[0].action = "transport.htm";
	document.forms[0].submit();
}
/*---------------MechincalTransport End-----------------------*/

/*---------------Hindi Start-----------------------*/
function hindiExam()
{
	document.forms[0].param.value = "examMaster";
	document.forms[0].action = "hindi.htm";
	document.forms[0].submit();
}
function hindiCashAward()
{
	document.forms[0].param.value = "cashAwardMaster";
	document.forms[0].action = "hindi.htm";
	document.forms[0].submit();
}
function hindiEmployee(){
	document.forms[0].param.value = "employeeMaster";
	document.forms[0].action = "hindi.htm";
	document.forms[0].submit();
	
}
function hindiExamConfig(){
	document.forms[0].param.value = "examConfigMaster";
	document.forms[0].action = "hindi.htm";
	document.forms[0].submit();
	
}
function hindiExamNomination()
{
	document.forms[0].param.value = "examNominationMaster";
	document.forms[0].action = "hindi.htm";
	document.forms[0].submit();
}
function hindiExamEligible()
{
	document.forms[0].param.value = "eligibleEmpForExam";
	document.forms[0].action = "hindi.htm";
	document.forms[0].submit();	
}
function hindiExamIncentive()
{
	document.forms[0].param.value = "incentiveMaster";
	document.forms[0].action = "hindi.htm";
	document.forms[0].submit();	
}
function hindiEmpResult()
{
	document.forms[0].param.value = "empResultMaster";
	document.forms[0].action = "hindi.htm";
	document.forms[0].submit();
}
function hindiIncentiveDetails()
{
	document.forms[0].param.value = "incentiveDetailsMaster";
	document.forms[0].action = "hindi.htm";
	document.forms[0].submit();
}
/*---------------Hindi End-----------------------*/

function Retirement(type)
{
	if(type == 'report')
	document.forms[0].param.value = "retirementReportDetails";
	else
	document.forms[0].param.value = "RetirementFinalDetails";
	document.forms[0].action = "retirementDetails.htm";
	document.forms[0].submit();

}
//for hold requests
function moreRequests(id) {
	document.forms[0].param.value = "more";
	document.forms[0].type.value = id;
	document.forms[0].action = "workflowmap.htm";
	document.forms[0].submit();
}
function moreAlerts(id) {
	document.forms[0].param.value = "morealert";
	document.forms[0].type.value = id;
	document.forms[0].action = "workflowmap.htm";
	document.forms[0].submit();
}
//Director

function orgMaster(type)
{
	document.forms[0].param.value = "orgMaster";
	document.forms[0].type.value = 'orgMaster';
	document.forms[0].action = "orgStructure.htm";
	document.forms[0].submit();
	
}


function letterNumberReference(type){
	document.forms[0].param.value = "letterNumber";
	document.forms[0].type.value = type;
	document.forms[0].action = "master.htm";
		document.forms[0].submit();
		clearLetterNumber()
}

function changePassword(){
	document.forms[0].param.value = "changepwd";
	document.forms[0].action = "employee.htm";
	document.forms[0].submit();
}

function EmployeeStatus(){
	 document.forms[0].param.value = "empStatus";
	 document.forms[0].type.value = "empStatus"; 
	 document.forms[0].action = "employee.htm";
	 document.forms[0].submit(); 
}

// Developed by Naresh
function empBankMaster()
{
	document.forms[0].param.value = "empBankAccount";
	document.forms[0].action = "payBill.htm";
	document.forms[0].submit();
}
//data validation report
function dataValidationReport(){
	document.forms[0].param.value = "dataValidationReport";
	document.forms[0].action = "scheduleReports.htm";
	document.forms[0].submit();	
}
//loan Employee exp
function loanEmpExp()
{
	window
	.open(
			"./report.htm?param=loanEmpX&returnValue=report",
			'preview',
			'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	} 
	
	//document.forms[0].param.value = "loanEmpX";
	//document.forms[0].action = "report.htm";
	//document.forms[0].submit();	
//}
//loan History Report for Employee
function loanHistory()
{
	window
	.open(
			"./report.htm?param=loanHistory&returnValue=report",
			'preview',
			'fullscreen=yes,toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
	}
// signing authority 
function cfaAccAuthorityDetails(){
	document.forms[0].param.value = "cfaAccAuthorityDetails";
	document.forms[0].action = "signingAuthority.htm";
	document.forms[0].submit();
}
function mailConfiguration(){
	document.forms[0].param.value = "home";
	document.forms[0].action = "mailConfiguration.htm";
	document.forms[0].submit();
}
function pisReportsHome(){
	document.forms[0].param.value = "pisReportsHome";
//	document.forms[0].type.value=type;
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}
function pisMiscReportsHome(){
	document.forms[0].param.value = "pisMiscReportsHome";
//	document.forms[0].type.value=type;
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}
function statusReportsHome(){
	document.forms[0].param.value = "statusReports";
//	document.forms[0].type.value=type;
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}
function orgHierarchyReports(){
	document.forms[0].param.value = "orgHierarchyReport";
//	document.forms[0].type.value=type;
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}

function addressReports(){
	document.forms[0].param.value = "addressReports";
//	document.forms[0].type.value=type;
	document.forms[0].action = "report.htm";
	document.forms[0].submit();
}
function uploadPhoto() {
	document.forms[0].param.value = "photoUpload";
	document.forms[0].action = "image.htm";
	document.forms[0].submit();
}

function erpLoanRequest(){
	//document.forms[0].type.value = "offline";
	document.forms[0].param.value = "ErpLoanRequest";
	document.forms[0].action = "erpLoanRequest.htm";
	document.forms[0].submit();
}
// Health Insurance For watererp
function viewHealthEnrollmentForm(){
	alert("welcome");
	document.forms[0].param.value = "HealthEnrollmentHome";
	document.forms[0].action = "healthInsuranceEnrollment.htm";
	document.forms[0].submit();
}

function viewHealthEnrollmentDetails(){
	document.forms[0].param.value = "HealthEnrollmentDetailsHome";
	document.forms[0].action = "healthInsuranceEnrollment.htm";
	document.forms[0].submit(); 
}

function subscriptionDetailsForm(){
	document.forms[0].param.value = "SubscriptionDetailsHome";
	document.forms[0].action = "healthInsuranceEnrollment.htm";
	document.forms[0].submit();
}

function exitFormalitiesForm(){
	document.forms[0].param.value = "ExitFormalitiesHome";
	document.forms[0].action = "healthInsuranceEnrollment.htm";
	document.forms[0].submit();
}
