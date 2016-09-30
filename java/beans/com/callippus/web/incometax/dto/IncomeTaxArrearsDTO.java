package com.callippus.web.incometax.dto;

import java.util.Date;

import com.callippus.web.beans.dto.CommonDTO;

public class IncomeTaxArrearsDTO extends CommonDTO{
private int categoryId;
private int daFrom;
private int daTo;
private double daDrawn;
private double daDue;
private int daDiff;
private int daTypeFlag;
private double tptDue;
private double tptDrawn;
private int tptDiff;
private int cpf;
private int itRec;
private String sfid;
private Date month;
private Date dateFrom;
private Date dateTo;
private String fyToFrom;
private int totCpfRec;
private int totAmt;
private int totItRec;


public int getTotItRec() {
	return totItRec;
}
public void setTotItRec(int totItRec) {
	this.totItRec = totItRec;
}
public int getTotCpfRec() {
	return totCpfRec;
}
public void setTotCpfRec(int totCpfRec) {
	this.totCpfRec = totCpfRec;
}
public int getTotAmt() {
	return totAmt;
}
public void setTotAmt(int totAmt) {
	this.totAmt = totAmt;
}
public int getCategoryId() {
	return categoryId;
}
public void setCategoryId(int categoryId) {
	this.categoryId = categoryId;
}
public int getDaFrom() {
	return daFrom;
}
public void setDaFrom(int daFrom) {
	this.daFrom = daFrom;
}
public int getDaTo() {
	return daTo;
}
public void setDaTo(int daTo) {
	this.daTo = daTo;
}
public double getDaDrawn() {
	return daDrawn;
}
public void setDaDrawn(double daDrawn) {
	this.daDrawn = daDrawn;
}
public double getDaDue() {
	return daDue;
}
public void setDaDue(double daDue) {
	this.daDue = daDue;
}
public int getDaDiff() {
	return daDiff;
}
public void setDaDiff(int daDiff) {
	this.daDiff = daDiff;
}
public int getDaTypeFlag() {
	return daTypeFlag;
}
public void setDaTypeFlag(int daTypeFlag) {
	this.daTypeFlag = daTypeFlag;
}
public double getTptDue() {
	return tptDue;
}
public void setTptDue(double tptDue) {
	this.tptDue = tptDue;
}
public double getTptDrawn() {
	return tptDrawn;
}
public void setTptDrawn(double tptDrawn) {
	this.tptDrawn = tptDrawn;
}
public int getTptDiff() {
	return tptDiff;
}
public void setTptDiff(int tptDiff) {
	this.tptDiff = tptDiff;
}
public int getCpf() {
	return cpf;
}
public void setCpf(int cpf) {
	this.cpf = cpf;
}
public int getItRec() {
	return itRec;
}
public void setItRec(int itRec) {
	this.itRec = itRec;
}
public String getSfid() {
	return sfid;
}
public void setSfid(String sfid) {
	this.sfid = sfid;
}
public Date getMonth() {
	return month;
}
public void setMonth(Date month) {
	this.month = month;
}
public Date getDateFrom() {
	return dateFrom;
}
public void setDateFrom(Date dateFrom) {
	this.dateFrom = dateFrom;
}
public Date getDateTo() {
	return dateTo;
}
public void setDateTo(Date dateTo) {
	this.dateTo = dateTo;
}
public String getFyToFrom() {
	return fyToFrom;
}
public void setFyToFrom(String fyToFrom) {
	this.fyToFrom = fyToFrom;
}








}
