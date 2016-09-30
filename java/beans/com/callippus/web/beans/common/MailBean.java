package com.callippus.web.beans.common;

/*
 * 
 * Properties need to send mail to superior 
 * at the time of Request
 * 
 * @author Rajendra
 */


public class MailBean {
	
	
 private String toMailAddress;
 private String fromMailAddress;
 private String  requestId;
 private String sendingToName;
 private String requestType;
 private String subject;
 private String textToSend;
 private String requestedBy;
 private String Description;
 private String currentPosition;
 private String status;
 
 

 
 
 
 
public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getCurrentPosition() {
	return currentPosition;
}

public void setCurrentPosition(String currentPosition) {
	this.currentPosition = currentPosition;
}

public String getDescription() {
	return Description;
}

public void setDescription(String description) {
	Description = description;
}

public String getToMailAddress() {
	return toMailAddress;
}

public void setToMailAddress(String toMailAddress) {
	this.toMailAddress = toMailAddress;
}

public String getFromMailAddress() {
	return fromMailAddress;
}

public void setFromMailAddress(String fromMailAddress) {
	this.fromMailAddress = fromMailAddress;
}

public String getRequestId() {
	return requestId;
}

public void setRequestId(String requestId) {
	this.requestId = requestId;
}

public String getSendingToName() {
	return sendingToName;
}

public void setSendingToName(String sendingToName) {
	this.sendingToName = sendingToName;
}

public String getRequestType() {
	return requestType;
}

public void setRequestType(String requestType) {
	this.requestType = requestType;
}

public String getSubject() {
	return subject;
}

public void setSubject(String subject) {
	this.subject = subject;
}

public String getTextToSend() {
	return textToSend;
}

public void setTextToSend(String textToSend) {
	this.textToSend = textToSend;
}

public String getRequestedBy() {
	return requestedBy;
}

public void setRequestedBy(String requestedBy) {
	this.requestedBy = requestedBy;
}

}
