function validateEmail(addr)
{
var invalidChars = '\/\'\\ ";:?!()[]\{\}^|,$%&+-!~`<>=';

for (i=0; i < invalidChars.length; i++)
						{
	if (addr.indexOf(invalidChars.charAt(i), 0) > -1)
	{
		alert('Email Address contains invalid characters');
		return false;
	}
}

for (i=0; i < addr.length; i++)
{
	if (addr.charCodeAt(i) > 127)
	{
		alert("Email Address contains non-ASCII characters.");
		return false;
	}
}
var atPos1 = addr.indexOf('.', 0);

if (atPos1 == 0)
{
	alert('Email Address must Start with a period');
	return false;
}

var atPos = addr.indexOf('@', 0);

if (atPos == -1)
{
	alert('Email Address must contain an @');
	return false;
}


if (atPos == 0)
{
	alert('Email Address must not start with @');
	return false;
}

if (addr.indexOf('@', atPos + 1) > - 1)
{
	alert('Email Address must contain only one @');
	return false;
}

if (addr.indexOf('.', atPos) == -1)
{
	alert('Email Address must contain a Period in the Domain Name');
	return false;
}

if (addr.indexOf('.@', 0) != -1)
{
	alert('Period must not immediately precede @ in Email Address');
	return false;
}
                        if (addr.indexOf('@.', 0) != -1)
{
	alert('Period must not immediately succeed @ in Email Address');
	return false;
}

if (addr.indexOf('..', 0) != -1)
{
	alert('Two Periods must not be adjacent in Email Address');
	return false;
}

var suffix = addr.substring(addr.lastIndexOf('.') + 1);

if (suffix.length != 2 && suffix != 'com' && suffix != 'net' && suffix != 'org' && suffix != 'edu' && suffix != 'int' && suffix != 'mil' && suffix != 'gov' & suffix != 'arpa' && suffix != 'biz' && suffix != 'aero' && suffix != 'name' && suffix != 'coop' && suffix != 'info' && suffix != 'pro' && suffix != 'museum')
{
	alert('Invalid Primary Domain in Email Address');
		return false;
	}

	return true;
}
function validateUSPhone( strValue ) {
/*******************************************************************************
 * DESCRIPTION: Validates that a string contains valid US phone pattern. Ex.
 * (999) 999-9999 or (999)999-9999
 * 
 * PARAMETERS: strValue - String to be tested for validity
 * 
 * RETURNS: True if valid, otherwise false.
 ******************************************************************************/
  var objRegExp  = /^\([1-9]\d{2}\)\s?\d{3}\-\d{4}$/;
 
  // check for valid us phone with or without space between
  // area code
  return objRegExp.test(strValue); 
}

function  validateNumeric( strValue ) {
/*******************************************************************************
 * DESCRIPTION: Validates that a string contains only valid numbers.
 * 
 * PARAMETERS: strValue - String to be tested for validity
 * 
 * RETURNS: True if valid, otherwise false.
 ******************************************************************************/
  var objRegExp  =  /(^-?\d\d*\.\d*$)|(^-?\d\d*$)|(^-?\.\d\d*$)/; 
 
  // check for numeric characters
  return objRegExp.test(strValue);
}

function validateInteger( strValue ) {
/*******************************************************************************
 * DESCRIPTION: Validates that a string contains only valid integer number.
 * 
 * PARAMETERS: strValue - String to be tested for validity
 * 
 * RETURNS: True if valid, otherwise false.
 ******************************************************************************/
  var objRegExp  = /(^-?\d\d*$)/;
 
  // check for integer characters
  return objRegExp.test(strValue);
}

function validateNotEmpty( strValue ) {
/*******************************************************************************
 * DESCRIPTION: Validates that a string is not all blank (whitespace)
 * characters.
 * 
 * PARAMETERS: strValue - String to be tested for validity
 * 
 * RETURNS: True if valid, otherwise false.
 ******************************************************************************/
   var strTemp = strValue;
   strTemp = trimAll(strTemp);
   if(strTemp.length > 0){
     return true;
   }  
   return false;
}

function validateUSZip( strValue ) {
/*******************************************************************************
 * DESCRIPTION: Validates that a string a United States zip code in 5 digit
 * format or zip+4 format. 99999 or 99999-9999
 * 
 * PARAMETERS: strValue - String to be tested for validity
 * 
 * RETURNS: True if valid, otherwise false.
 * 
 ******************************************************************************/
var objRegExp  = /(^\d{5}$)|(^\d{5}-\d{4}$)/;
 
  // check for valid US Zipcode
  return objRegExp.test(strValue);
}

function validateUSDate( strValue ) {
/*******************************************************************************
 * DESCRIPTION: Validates that a string contains only valid dates with 2 digit
 * month, 2 digit day, 4 digit year. Date separator can be ., -, or /. Uses
 * combination of regular expressions and string parsing to validate date. Ex.
 * mm/dd/yyyy or mm-dd-yyyy or mm.dd.yyyy
 * 
 * PARAMETERS: strValue - String to be tested for validity
 * 
 * RETURNS: True if valid, otherwise false.
 * 
 * REMARKS: Avoids some of the limitations of the Date.parse() method such as
 * the date separator character.
 ******************************************************************************/
  var objRegExp = /^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/
 
  // check to see if in correct format
  if(!objRegExp.test(strValue))
    return false; // doesn't match pattern, bad date
  else{
    var strSeparator = strValue.substring(2,3) // find date separator
    var arrayDate = strValue.split(strSeparator); // split date into month,
													// day, year
    // create a lookup for months not equal to Feb.
    var arrayLookup = { '01' : 31,'03' : 31, '04' : 30,'05' : 31,'06' : 30,'07' : 31,
                        '08' : 31,'09' : 30,'10' : 31,'11' : 30,'12' : 31}
    var intDay = parseInt(arrayDate[1],10); 

    // check if month value and day value agree
    if(arrayLookup[arrayDate[0]] != null) {
      if(intDay <= arrayLookup[arrayDate[0]] && intDay != 0)
        return true; // found in lookup table, good date
    }
    
    // check for February
    var intMonth = parseInt(arrayDate[0],10);
    if (intMonth == 2) { 
       var intYear = parseInt(arrayDate[2],10);
       if( ((intYear % 4 == 0 && intDay <= 29) || (intYear % 4 != 0 && intDay <=28)) && intDay !=0)
          return true; // Feb. had valid number of days
       }
  }
  return false; // any other values, bad date
}

function validateValue( strValue, strMatchPattern ) {
/*******************************************************************************
 * DESCRIPTION: Validates that a string a matches a valid regular expression
 * value.
 * 
 * PARAMETERS: strValue - String to be tested for validity strMatchPattern -
 * String containing a valid regular expression match pattern.
 * 
 * RETURNS: True if valid, otherwise false.
 ******************************************************************************/
var objRegExp = new RegExp( strMatchPattern);
 
 // check if string matches pattern
 return objRegExp.test(strValue);
}


function rightTrim( strValue ) {
/*******************************************************************************
 * DESCRIPTION: Trims trailing whitespace chars.
 * 
 * PARAMETERS: strValue - String to be trimmed.
 * 
 * RETURNS: Source string with right whitespaces removed.
 ******************************************************************************/
var objRegExp = /^([\w\W]*)(\b\s*)$/;
 
      if(objRegExp.test(strValue)) {
       // remove trailing a whitespace characters
       strValue = strValue.replace(objRegExp, '$1');
    }
  return strValue;
}

function leftTrim( strValue ) {
/*******************************************************************************
 * DESCRIPTION: Trims leading whitespace chars.
 * 
 * PARAMETERS: strValue - String to be trimmed
 * 
 * RETURNS: Source string with left whitespaces removed.
 ******************************************************************************/
var objRegExp = /^(\s*)(\b[\w\W]*)$/;
 
      if(objRegExp.test(strValue)) {
       // remove leading a whitespace characters
       strValue = strValue.replace(objRegExp, '$2');
    }
  return strValue;
}

function trimAll( strValue ) {
/*******************************************************************************
 * DESCRIPTION: Removes leading and trailing spaces.
 * 
 * PARAMETERS: Source string from which spaces will be removed;
 * 
 * RETURNS: Source string with whitespaces removed.
 ******************************************************************************/ 
 var objRegExp = /^(\s*)$/;

    // check for all spaces
    if(objRegExp.test(strValue)) {
       strValue = strValue.replace(objRegExp, '');
       if( strValue.length == 0)
          return strValue;
    }
    
   // check for leading & trailing spaces
   objRegExp = /^(\s*)([\W\w]*)(\b\s*$)/;
   if(objRegExp.test(strValue)) {
       // remove leading and trailing whitespace characters
       strValue = strValue.replace(objRegExp, '$2');
    }
  return strValue;
}
/* kiran add this function to check for credit card vaild or not */
function validateCredit(num)
{
	var objRegExp = /^((67\d{2})|(4\d{3})|(5[1-5]\d{2})|(6011))(-\s\d{4}){3}|(3[4,7])\d{2}-\s\d{6}-\s\d{5}/;
	// alert(objRegExp.test(num));
	return objRegExp.test(num);
}


function removeCurrency( strValue ) {
/*******************************************************************************
 * DESCRIPTION: Removes currency formatting from source string.
 * 
 * PARAMETERS: strValue - Source string from which currency formatting will be
 * removed;
 * 
 * RETURNS: Source string with commas removed.
 ******************************************************************************/
  var objRegExp = /\(/;
  var strMinus = '';
 
  // check if negative
  if(objRegExp.test(strValue)){
    strMinus = '-';
  }
  
  objRegExp = /\)|\(|[,]/g;
  strValue = strValue.replace(objRegExp,'');
  if(strValue.indexOf('$') >= 0){
    strValue = strValue.substring(1, strValue.length);
  }
  return strMinus + strValue;
}

function addCurrency( strValue ) {
/*******************************************************************************
 * DESCRIPTION: Formats a number as currency.
 * 
 * PARAMETERS: strValue - Source string to be formatted
 * 
 * REMARKS: Assumes number passed is a valid numeric value in the rounded to 2
 * decimal places. If not, returns original value.
 ******************************************************************************/
  var objRegExp = /-?[0-9]+\.[0-9]{2}$/;
   
    if( objRegExp.test(strValue)) {
      objRegExp.compile('^-');
      strValue = addCommas(strValue);
      if (objRegExp.test(strValue)){
        strValue = '(' + strValue.replace(objRegExp,'') + ')';
      }
      return '$' + strValue;
    }
    else
      return strValue;
}

function removeCommas( strValue ) {
/*******************************************************************************
 * DESCRIPTION: Removes commas from source string.
 * 
 * PARAMETERS: strValue - Source string from which commas will be removed;
 * 
 * RETURNS: Source string with commas removed.
 ******************************************************************************/
  var objRegExp = /,/g; // search for commas globally
 
  // replace all matches with empty strings
  return strValue.replace(objRegExp,'');
}

function addCommas( strValue ) {
/*******************************************************************************
 * DESCRIPTION: Inserts commas into numeric string.
 * 
 * PARAMETERS: strValue - source string containing commas.
 * 
 * RETURNS: String modified with comma grouping if source was all numeric,
 * otherwise source is returned.
 * 
 * REMARKS: Used with integers or numbers with 2 or less decimal places.
 ******************************************************************************/
  var objRegExp  = new RegExp('(-?[0-9]+)([0-9]{3})'); 

    // check for match to search criteria
    while(objRegExp.test(strValue)) {
       // replace original string with first group match,
       // a comma, then second group match
       strValue = strValue.replace(objRegExp, '$1,$2');
    }
  return strValue;
}

function removeCharacters( strValue, strMatchPattern ) {
/*******************************************************************************
 * DESCRIPTION: Removes characters from a source string based upon matches of
 * the supplied pattern.
 * 
 * PARAMETERS: strValue - source string containing number.
 * 
 * RETURNS: String modified with characters matching search pattern removed
 * 
 * USAGE: strNoSpaces = removeCharacters( ' sfdf dfd', '\s*')
 ******************************************************************************/
 var objRegExp =  new RegExp( strMatchPattern, 'gi' );
 
 // replace passed pattern matches with blanks
  return strValue.replace(objRegExp,'');
}

 
function validateChar( strValue) {
/*******************************************************************************
 * DESCRIPTION: Validates that a string contains a valid email pattern.
 * 
 * PARAMETERS: strValue - String to be tested for validity
 * 
 * RETURNS: True if valid, otherwise false.
 * 
 * REMARKS: Accounts for email with country appended does not validate that
 * email contains valid URL type (.com, .gov, etc.) or valid country suffix.
 ******************************************************************************/
var objRegExp  = /(^[a-z]([a-z_\.]*)@([a-z_\.]*)([.][a-z]{3})$)|(^[a-z]([a-z_\.]*)@([a-z_\.]*)(\.[a-z]{3})(\.[a-z]{2})*$)/i;
// var objRegExp =
// /(^.+\@([a-z_\.]*)([.][a-z]{3})$)|(^.+\@([a-z_\.]*)(\.[a-z]{2})(\.[a-z]{2})*$)/i;
var mikExp = /[$\\@\\\#%\^\&\*\(\)\[\]\+\_\{\}\`\~\=\.\,\;\'\"\:\[0-9]|]/;

 
  // check for valid email

  return mikExp.test(strValue);
  
}
function validateSignedFloat(floatValue){
	var signedFloat = /^((\d+(\.\d*)?)|((\d*\.)?\d+))$/;
	return signedFloat.test(floatValue);
}

function ValidateImage(oForm){
	if (!/(\.(gif|jpg|jpeg|bmp|png))$/i.test(oForm.value)){
		return false;
	}
	return true;
}
function validatePhone(strValue)
{
	var objRegExp = / /g; // search for commas globally

	// replace all matches with empty strings
	var newString=strValue.replace(objRegExp,'');

	var objRegExp  = /(^[0123456789+-]*[0-9]$)/;
	return objRegExp.test(newString);
}


function isUrl(s) {
	var regexp = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/
	return regexp.test(s);
}
 function validateFloatTwoDecimal(str) {
    
    return /^[-+]?\d{3,5}(\.\d{1,3})?$/.test(str);
     }
     
function isFloat (s)
{   var i;
    var seenDecimalPoint = false;
    if (isEmpty(s)) 
       if (isFloat.arguments.length == 1) return defaultEmptyOK;
       else return (isFloat.arguments[1] == true);
    if (s == decimalPointDelimiter) return false;
    // Search through string's characters one by one
    // until we find a non-numeric character.
    // When we do, return false; if we don't, return true.
    for (i = 0; i < s.length; i++)
    {   
        // Check that current character is number.
        var c = s.charAt(i);

        if ((c == decimalPointDelimiter) && !seenDecimalPoint) seenDecimalPoint = true;
        else if (!isDigit(c)) return false;
    }
    // All characters are numbers.
    return true;
}
function checkFloat(e,id){
	var key;
	var value = document.getElementById(id).value; 
	var str1 = value.split(".");
	if(window.event){
		key = window.event.keyCode;
	}
	else{
		key = e.which;
	}
	if(key==46){	
	  var check = true;
		for(j=0;j<str1.length;j++){
			if(check){
				check = false;
			}else{
				alert("Invalid character");
				document.getElementById(id).focus();
				return false;
			}
		}
	}	
	if( key!=8 && key!=0 && key!=46 && (key<48 || key>57)){
		alert("Please enter numbers only");
		document.getElementById(id).focus();
		return false;
	}
	if(key==8 || key==0){
		document.getElementById(id).focus();
		return true;
	}else{
		document.getElementById(id).focus();
	    return true;
	}
}

function checkSignFloat(e,id,sign){
	var key;
	var value = document.getElementById(id).value; 
	var str1 = value.split(".");
	if(window.event){
		key = window.event.keyCode;
	}
	else{
		key = e.which;
	}
	if(key==46){	
	  var check = true;
		for(j=0;j<str1.length;j++){
			if(check){
				check = false;
			}else{
				alert("Invalid character");
				document.getElementById(id).focus();
				return false;
			}
		}
	}
	if(sign=='-'){
		if(key==45){	
			var str2 = value.split("-");
			for(j=0;j<str2.length;j++){
				if(j!=0){
					alert("Invalid character");
					document.getElementById(id).focus();
					return false;
				}
				if(str2[j]!='-' && str2[j]!=''){
					alert("Invalid character");
					document.getElementById(id).focus();
					return false;
				}
			}
			return true;
		}
	}	
	if( key!=8 && key!=0 && key!=46 && (key<48 || key>57)){
		alert("Please enter numbers only");
		document.getElementById(id).focus();
		return false;
	}
	if(key==8 || key==0){
		document.getElementById(id).focus();
		return true;
	}else{
		document.getElementById(id).focus();
	    return true;
	}
}
function checkInt(e)
{
	var key;
	if(window.event)
	{
		key = window.event.keyCode;
	}
	else
	{
		key = e.which;
	}
	if(( key != 8 && key != 0 && (key < 48 || key > 57)))
	{
	  	alert("Please Enter Numbers only!");
		return false;
	}
	if(key == 0)
		return true;
	else
		return true;	
}
function checkIntDecimal(e)
{
	var key;
	if(window.event)
	{
		key = window.event.keyCode;
	}
	else
	{
		key = e.which;
	}
	if( key != 8 && key != 0 && (key < 48 || key > 57)  && key !=46)
	{
	  	alert("Please Enter Numbers only!");
		return false;
	}
	if(key == 0)
		return true;
	else
		return true;	
}


function checkPinCode(e){
	   var key;
		if(window.event){
			key = window.event.keyCode;
		}
		else{
			key = e.which;
		}
	  	if( key!=8 && key!=0 && (key<48 || key>57)){
	  		alert("Please enter numbers only");
			return false;
		}
		if(key==0){
			return true;
		}else{
			return true;
		}
}
function checkChar(e)
{
	var key;
	if(window.event)
	{
		key = window.event.keyCode;
	}
	else
	{
		key = e.which;
	}
	
	if( key != 8 && key != 0 && (key < 48 || key > 57))
	{
			return true;
	}
	
	if(key == 0 || key == 8)
	{
		return true;
	}
	else
	{
		alert("Please enter characters only");
		return false;
	}
}

function checkDecimals(id)
{
	var value = document.getElementById(id).value; 
	var str1 = value.split(".");
	if(str1[1]!=null){
		if(str1[1].length>2)
		{
			document.getElementById(id).focus();
			return false;
		}
	}
	
}
function phoneValidation(){
	var val= document.getElementById('phone').value;
	if(!validatePhone(val)){
		alert("PLease enter valid phone number");
		document.getElementById('phone').focus();
		
	}
}

function checkTwoDigitFloat(e,id){
	var key;
	var value = document.getElementById(id).value; 
	var str1 = value.split(".");
	
	if(window.event){
		key = window.event.keyCode;
		
	}
	else{
		key = e.which;
	}
	if(str1[0]=="" && key==46){
		alert("Please Enter Numbers Only");
		return false;
	}
	if(str1[1]!=null && str1[1].length>1 && key!=8 && key!=0){
		
		return false;
	}
	if(key==46){	
	  var check = true;
		for(j=0;j<str1.length;j++){
			if(check){
				check = false;
			}else{
				alert("Invalid character");
				// document.getElementById(id).focus();
				return false;
			}
		}
	}
	if( key!=8 && key!=0 && key!=46 && (key<48 || key>57)){
		alert("Please Enter Numbers Only");
		document.getElementById(id).focus();
		return false;
	}
	if(key==8 || key==0){
		// document.getElementById(id).focus();
		return true;
	}else{
		// document.getElementById(id).focus();
	    return true;
	}
}

// Added By Naresh
function checkSpecialChar(e)	// This function doesn't allow special characters into the text field.
{
	var str = " !`~@#$%^&*()-_=+\/*.,'|;:[]{}?<>";
	var key;
	var flag = 0;
		if(window.event)
		{
			key = window.event.keyCode;
		}
		else
		{
			key = e.which;
			key = String.fromCharCode(key);
		}
		for(var i = 0; i < str.length; i++)
		{
			if(str[i] == key)
			{
				flag = 1;
				break;
			}
		}
		if(flag == 0 && key != '"' && String.charCodeAt(key) != 13)
		return true;
		else
		{
			alert("Please enter Alpha-Numeric characters only but not any special character");
			return false;
		}
} // End

//this function return the Number with decimal value ::added by Rakesh
function inprecise_round(value, decPlaces){
	return Math.round(value*Math.pow(10,decPlaces))/Math.pow(10,decPlaces);
}
//this function return the decimal value ::added by Rakesh
function precise_round(value, decPlaces){
	var val = value *Math.pow(10,decPlaces);
	var fraction = ((Math.round(val-parseInt(val))*10)/10);
	if(fraction == -0.5) fraction = -0.6;
	val = Math.round(parseInt(val) + fraction)/Math.pow(10,decPlaces);
	return val;
}
//this function return the two decimal value ::added by Rakesh
function  round2Fixed(value){// If we give 55.678 return value is 55.68
	value =value;
	value = value.toString().split('e');
	value = Math.round(+value[0] + 'e'  +(value[1] ? (+value[1] +2) :2));
	value = value.toString().split('a');
	return(+(value[0] + 'e' + (value[1] ? (+value[1] -2) : -2))).toFixed(2);
}
//added by vnr
//This function doesn't allow special characters into the text field.
//sending the ajax call when click on enter button in keyboard.
function checkSpecialChar1(e)	
{
	var str = " !`~@#$%^&*()-_=+\/*.,'|;:[]{}?<>";
	var key;
	var flag = 0;
		if(window.event)
		{
			key = window.event.keyCode;
		}
		else
		{
			key = e.which;
			key = String.fromCharCode(key);
		}
		for(var i = 0; i < str.length; i++)
		{
			if(str[i] == key)
			{
				flag = 1;
				break;
			}
		}
		if(flag == 0 && key != '"'&&  String.charCodeAt(key) != 13)
			return true;
			
		else if(String.charCodeAt(key) == 13){
			//alert("plz hit search button")*/
			getBPGPSFIDDetails();
			return false;
			}
		else
		{
			alert("Please enter Alpha-Numeric characters only but not any special character");
			return false;
		}
}

function getMonthID(month) {
	if (month == 'Jan' || month == 'JAN') {
		return '01';
	} else if (month == 'Feb' || month == 'FEB') {
		return '02';
	} else if (month == 'Mar' || month == 'MAR') {
		return '03';
	} else if (month == 'Apr' || month == 'APR') {
		return '04';
	} else if (month == 'May' || month == 'MAY') {
		return '05';
	} else if (month == 'Jun' || month == 'JUN') {
		return '06';
	} else if (month == 'Jul' || month == 'JUL') {
		return '07';
	} else if (month == 'Aug' || month == 'AUG') {
		return '08';
	} else if (month == 'Sep' || month == 'SEP') {
		return '09';
	} else if (month == 'Oct' || month == 'OCT') {
		return '10';
	} else if (month == 'Nov' || month == 'NOV') {
		return '11';
	} else if (month == 'Dec' || month == 'DEC') {
		return '12';
	}
}

function getMonthMON(month){
	// var month = parseInt(mon);
	if (month == 1 || month == '01') {
		return 'Jan';
	} else if (month == 2 || month == '02') {
		return 'Feb';
	} else if (month == 3 || month ==  '03') {
		return 'Mar';
	} else if (month == 4 || month == '04') {
		return 'Apr';
	} else if (month == 5 || month == '05') {
		return 'May';
	} else if (month == 6 || month == '06') {
		return 'Jun';
	} else if (month == 7 || month == '07') {
		return 'Jul';
	} else if (month == 8 || month == '08') {
		return 'Aug';
	} else if (month == 9 || month == '09') {
		return 'Sep';
	} else if (month == 10 || month == '10') {
		return 'Oct';
	} else if (month == 11 || month == '11') {
		return 'Nov';
	} else if (month == 12 || month == '12') {
		return 'Dec';
	}
}

function convertDate(date) {
	return date.split("-")[0] + "-" + getMonthID(date.split("-")[1]) + "-" + date.split("-")[2];
}

function createDDMMYYY(strDate)
{
   	arrDt = strDate.split("-");
    var stdt=null;
		stdt=new Date();
		stdt.setFullYear(arrDt[2]);
	if (parseInt(arrDt[1],10) == 9 || parseInt(arrDt[1],10) == 1 || parseInt(arrDt[1],10)==3 || parseInt(arrDt[1],10)==5 || parseInt(arrDt[1],10)==7 || parseInt(arrDt[1],10)==8 || parseInt(arrDt[1],10)==10 || parseInt(arrDt[1],10)==12)
		{
		    stdt.setMonth(arrDt[1]-1);
		    stdt.setDate(arrDt[0]);
		}
	else if (parseInt(arrDt[1],10) == 2 || parseInt(arrDt[1],10)==4 || parseInt(arrDt[1],10)==6 || parseInt(arrDt[1],10)==9 || parseInt(arrDt[1],10)==11)
		{
		    stdt.setDate(arrDt[0]);
		    stdt.setMonth(arrDt[1]-1);
		}
    return stdt;
}

function compareDate(date1, date2) {
	date1 = convertDate(date1);
	date2 = convertDate(date2);

	date1 = createDDMMYYY(date1);
	date2 = createDDMMYYY(date2);
	if (date1 > date2 || date1.equalsTo(date2)) {
		return true;
	} else {
		return false;
	}
}

function compareScriptDate(date1,date2){
	if(date1 >date2 ||date1.equalsTo(date2) ){
		return true;
	}else {
		return false;
	}
}

function compareDates(dt1, dt2){

	var dateRegEx = /^([0123]?\d)[\.\-\/\s]?([0123]?\d)[\.\-\/\s]?(\d{4})$/;
	var result1 = dt1.match(dateRegEx);
	var result2 = dt2.match(dateRegEx);
	if(result1 != null){
	     var month1 = result1[2];
	     var day1 = result1[1];
	     var year1 = result1[3];
	}
	if(result2 != null){
	     var month2 = result2[2];
	     var day2 = result2[1];
	     var year2 = result2[3];
	}
	if(result1 && result2){
		var d2 = new Date(year1, month1-1, day1);
		var d1 = new Date(year2, month2-1, day2);
		
		var diff = d1.getTime() - d2.getTime();
		return diff = (((diff / 1000) / 60) / 60) / 24;
	}
	return null;
}
function increaseTextWidth(id){
	if(document.getElementById(id).textLength > 15) {
		document.getElementById(id).size=document.getElementById(id).textLength+10;
	}else {
		document.getElementById(id).size=20
	}
}

function textClear(id){
	document.getElementById(id).value="";	
}

function addMonths(date,noOfMonths){
	var myDate = createDDMMYYY(convertDate(date));
	var d = myDate.getDate();
    var m = myDate.getMonth();
    var y = myDate.getFullYear();
   return new Date(y, m+noOfMonths, d);
}

function addDays(date,noOfDays){
	var myDate = createDDMMYYY(convertDate(date));
	var d = myDate.getDate();
    var m = myDate.getMonth();
    var y = myDate.getFullYear();
   return new Date(y, m, d+noOfDays);
}
function textCounter(field, countfield, maxlimit) {
	if (field.value.length > maxlimit) {
		field.value = field.value.substring(0, maxlimit);
		return false;
	} else {
		countfield.value = maxlimit - field.value.length;
	}
}

function roundNumber(num, dec) {
	return Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
}

function checkBoxCheckAll(type, rowType) {
	alert("check2");
	if ($jq('input:checkbox[name='+ type +']').is(':checked')) {		
		if ($jq('.' + rowType).length >= 0) {
			for (var i = 0; i < $jq('.' + rowType).length; i++) {
				if ($jq('.' + rowType).eq(i).is(':visible')) {
					$jq('.' + rowType).eq(i).attr('checked', 'checked');	
				} else {
					$jq('.'+rowType).eq(i).removeAttr("checked");	
				}
			}
		}
	//	$jq('.'+rowType).attr('checked', 'checked');	
	} else {
		if ($jq('.' + rowType).length >= 0) {
			for (var i = 0; i < $jq('.' + rowType).length; i++) {
				if ($jq('.' + rowType).eq(i).is(':visible')) {
					$jq('.' + rowType).eq(i).removeAttr("checked");	
				}
			}
		}
		$jq("."+rowType).removeAttr("checked");
	}
}

function checkBoxCheckAll(type, rowType) {
	if ($jq('input:checkbox[name='+ type +']').is(':checked')) {
		$jq('.' + rowType).attr('checked', 'checked');
	} else {
		 $jq("." + rowType).removeAttr("checked");
	}
}

function checkBoxCheck(type) {
    if($jq("." + type).length == $jq("."+type+":checked").length)
        $jq("#selectall").attr("checked", "checked");
    else
        $jq("#selectall").removeAttr("checked");			 
}

function clickRadio(e){
	
	if(!$jq(e).parent().parent().find(":checkbox").is(':disabled')){
	$jq(e).parent().parent().find(":checkbox").attr("checked", "checked");
}
}

function selectedDesignation() {
	$jq('#SelectRight').find('option').remove().end();
	$jq('#SelectLeft').find('option').remove().end();
	if (jsonArrayObject != null) {
		for ( var i = 0; i < jsonArrayObject.length; i++) {
			if ($jq("#gazType").val() == 0
					|| jsonArrayObject[i].key == $jq("#gazType").val() || ($jq("#gazType").val()=='GAZ' && jsonArrayObject[i].key=='GAZETTED') || ($jq("#gazType").val()=='NG' && jsonArrayObject[i].key=='NON GAZETTED')) {
				$jq("#SelectLeft").append(
						'<option value=' + jsonArrayObject[i].id + '>'
								+ jsonArrayObject[i].name + '</option>');
			}
		}
	}
}

function multiSelectBox(){
	 $jq(function() {
         $jq("#MoveRight,#MoveLeft").click(function(event) {
             var id = $jq(event.target).attr("id");
             var selectFrom = id == "MoveRight" ? "#SelectLeft" : "#SelectRight";
             var moveTo = id == "MoveRight" ? "#SelectRight" : "#SelectLeft";

             var selectedItems = $jq(selectFrom + " :selected").toArray();
             $jq(moveTo).append(selectedItems);
             selectedItems.remove;
         });
     });
}

function displayResult(result,remarks) {
	if (result == 'success' || result == 'update') {
		document.getElementById('result').innerHTML = "Saved Record Successfully.";
		document.getElementById("result").className = "success";		
	} else if (result == 'failure') {
		document.getElementById('result').innerHTML = "Error Saving the Record. "+remarks;
		document.getElementById("result").className = "failure";	
	} 
}
function validateTime() {
	var re = /^(\d{1,2}):(\d{2})([ap]m)?$/;
		if(form.starttime.value != '') { 
				if(regs = form.starttime.value.match(re)) { 
				if(regs[3]) { 
				// 12-hour value between 1 and 12
				if(regs[1] < 1 || regs[1] > 12) { 
				alert("Invalid value for hours: " + regs[1]); 
				form.starttime.focus(); 
				return false; 
				} 
			} else { 
			// 24-hour value between 0 and 23
			if(regs[1] > 23) { 
			alert("Invalid value for hours: " + regs[1]); 
			form.starttime.focus(); 
			return false; 
			} 
			} 
			// minute value between 0 and 59
			if(regs[2] > 59) { 
			alert("Invalid value for minutes: " + regs[2]); 
			form.starttime.focus(); return false; 
			} 
			
				}
		else { 
			alert("Invalid time format: " + form.starttime.value); 
			form.starttime.focus(); 
			return false; 
			}
		}
}

function getDoPartNumber(gazType) {
	var status =true;
	var errerMessage='';
	/**
	 * get the DoPartNumber of selected date
	 */
	if($jq('#gazettedType').is(':visible') && $jq('#gazettedType').val()=='Select') {
		errerMessage = 'Please select Gazetted Type\n';
		$jq('#doPartDate').val('');
		status=false;
		
	}if($jq.trim($jq('#departureDate').is(':visible') && $jq('#departureDate').html().split(':')[1]) !='') {
		if(!compareDate($jq.trim($jq('#departureDate').html().split(':')[1]),$jq('#doPartDate').val())) {
			errerMessage = 'DoPart date cannot be after Date of Departure \n';
			$jq('#doPartDate').val('');
			status=false;
		}
	}else if($jq('#gazettedType').is(':visible')){
		gazType = $jq('#gazettedType').val();
	}
	if(status) {
		$jq.post('workflowmap.htm?param=dopart&gazettedType='+gazType+'&doPartDate='+$jq('#doPartDate').val(),  function(html) {
			$jq("#doPartNumber").html(html);
		});
	}else {
		alert(errerMessage);
	}
	
}

function isAlphabetExp(e)
{
	var key;
	var objRegExp  = /^[a-zA-Z ]*$/
		if(window.event)
		{
			key = window.event.keyCode;
		}
		else
		{
			key = e.which;
		}
  		if( objRegExp.test(String.fromCharCode(key)) || key==8 || key==0 )
  		{
  			return true;
  		}
  		else
  		{
  			alert("Please Enter Alphabetic characters only");
  			return false;
  		}
}

function isNumberExp(e){
	var key;
	var objRegExp  = /^[0-9.]*$/
	if(window.event){
		key = window.event.keyCode;
	}
	else{
		key = e.which;
	}
  	if( objRegExp.test(String.fromCharCode(key)) || key==8 || key==0 ){
  		
		return true;
	}
  	else{
		alert("Please enter Numbers or decimals only");
		return false;
	}
}

function isAddressExp(e){
	var key;
	var objRegExp  = /^[^~!@$%^*+`=|\"';?><]*$/
	if(window.event){
		key = window.event.keyCode;
	}
	else{
		key = e.which;
	}
  	if( objRegExp.test(String.fromCharCode(key)) || key==8 || key==0 ){
  		
		return true;
	}
  	else{
		alert("Address should not contain ~!@$%^*+`=|\"';?>< ");
		return false;
	}
}

function isAlphaNumaricExp(e) {
	var key;
	var objRegExp  = /^[a-zA-Z0-9 ]*$/
	if (window.event)
		key = window.event.keyCode;
	else
		key = e.which;
  	if (objRegExp.test(String.fromCharCode(key)) || key == 8 || key == 0)
		return true;
  	else if (objRegExp.test(String.fromCharCode(key)) || key == 13)
  		return false;
  	else {
		alert("Please Enter Alpha-Numeric characters only");
		return false;
	}
}
function isAlphabetExp(e,id){
	var key;
	var objRegExp  = /^[a-zA-Z ]*$/
	if(window.event){
		key = window.event.keyCode;
	}
	else{
		key = e.which;
	}
  	if( objRegExp.test(String.fromCharCode(key)) || key==8 || key==0 || key==undefined){
  		
		return true;
	}else{
  		$jq('#'+id).val('');
  		alert("Please enter characters only");
  		return false;
	}
}
function isAlphabetSplCharExp(e,id){
	var key;
	var objRegExp  = /^[a-zA-Z ^~!@$%^*+`=|\"';?><.]*$/
	if(window.event){
		key = window.event.keyCode;
	}
	else{
		key = e.which;
	}
  	if( objRegExp.test(String.fromCharCode(key)) || key==8 || key==0 ){
  		
		return true;
	}
  	else{
  		alert("Please enter characters only");
  		return false;
	}
}
function hilightRow(preVal){
	$jq(preVal).parent().parent().parent().find('tr').attr("style","background-color:0000");
	$jq(preVal).parent().parent().attr("style","background-color:pink");
}

// For Time
function compareHoursTime(fHours,tHours){
	if(fHours==tHours)
		return 0;
	else if(fHours>tHours)
		return 1;
	else 
		return -1;
}
function compareMinutesTime(fMinutes,tMinutes){
	if(fMinutes==tMinutes)
		return 0;
	else if(fMinutes>tMinutes)
		return 1;
	else 
		return -1;
}
//The function doesn't allowed  Space, Numbers and Special characters
function checkSpace(e){
	var key;
	var objRegExp  = /^[a-zA-Z]*$/
		if(window.event)
		{
			key = window.event.keyCode;
		}
		else
		{
			key = e.which;
		}
  		if( objRegExp.test(String.fromCharCode(key)) || key==8 || key==0 )
  		{
  			return true;
  		}
  		else
  		{
  			alert("Please Enter Alphabetic characters only");
  			return false;
  		}
}

function clearCheckBox(){
	$jq('#tabs').find('thead').each(function(){
		   $jq(this).find('th').eq(0).find('input').removeAttr('checked');	
		  checkBoxCheckAll($jq(this).find('th').find('input:checkbox').attr('name'),'row')
		
		});
	
}
//The function  allowes  Numbers and Special characters like ,-/ only
function checkNumSymbolsExp(e){
	var key;
	var objRegExp  = /^[0-9,-/]*$/
	if(window.event){
		key = window.event.keyCode;
	}
	else{
		key = e.which;
	}
  	if( objRegExp.test(String.fromCharCode(key)) || key==8 || key==0 ){
  		
		return true;
	}
  	else{
		alert("Please enter Numbers and special charecters like -,/ only");
		return false;
	}
}

function getCurrentDate() {
	var date = '';
	date = formatDateIntoString(new Date(), 'dd-MMM-yyyy');
	return date;
}

// Converts Date format of date into required format of String date.
var MONTH_NAMES = new Array('January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December', 
		'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec');
var DAY_NAMES = new Array('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 
		'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat');

function formatDateIntoString(date, format) {
	format = format + "";
	var result = "";
	var i_format = 0;
	var c = "";
	var token = "";
	var y = date.getYear() + "";
	var M = date.getMonth() + 1;
	var d = date.getDate();
	var E = date.getDay();
	var H = date.getHours();
	var m = date.getMinutes();
	var s = date.getSeconds();
	var yyyy, yy, MMM, MM, dd, hh, h, mm, ss, ampm, HH, H, KK, K, kk, k;
	// Convert real date parts into formatted versions
	var value = new Object();
	if (y.length < 4) {
		y = "" + (y - 0 + 1900);
	}
	value["y"] = "" + y;
	value["yyyy"] = y;
	value["yy"] = y.substring(2, 4);
	value["M"] = M;
	value["MM"] = LZ(M);
	value["MMM"] = MONTH_NAMES[M + 11];
	value["Month"] = MONTH_NAMES[M - 1];
	value["d"] = d;
	value["dd"] = LZ(d);
	value["DY"] = DAY_NAMES[E + 7];
	value["Day"] = DAY_NAMES[E];
	value["H"] = H;
	value["HH"] = LZ(H);
	if (H == 0) {
		value["h"] = 12;
	} else if (H > 12) {
		value["h"] = H - 12;
	} else {
		value["h"] = H;
	}
	value["hh"] = LZ(value["h"]);
	if (H > 11) {
		value["K"] = H - 12;
	} else {
		value["K"] = H;
	}
	value["k"] = H + 1;
	value["KK"] = LZ(value["K"]);
	value["kk"] = LZ(value["k"]);
	if (H > 11) {
		value["a"] = "PM";
	} else {
		value["a"] = "AM";
	}
	value["m"] = m;
	value["mm"] = LZ(m);
	value["s"] = s;
	value["ss"] = LZ(s);
	while (i_format < format.length) {
		c = format.charAt(i_format);
		token = "";
		while ((format.charAt(i_format) == c) && (i_format < format.length)) {
			token += format.charAt(i_format++);
		}
		if (value[token] != null) {
			result = result + value[token];
		} else {
			result = result + token;
		}
	}
	return result;
}

function LZ(x) {
	return (x < 0 || x > 9 ? "" : "0") + x;
}
