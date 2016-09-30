package com.callippus.web.paybill.dto;

import com.callippus.web.beans.dto.CommonDTO;

public class PayBillCcsDTO extends CommonDTO {

	private String sfid;
	private String msno;
	private float thrift;
	private float loan;
	private float loanInst;
	private float loanBal;
	private float rd;
	private float eml;
	private float emlInst;
	private float emlBal;
	private float misc;
	private float total;

	public String getSfid() {
		return sfid;
	}

	public void setSfid(String sfid) {
		this.sfid = sfid;
	}

	public String getMsno() {
		return msno;
	}

	public void setMsno(String msno) {
		this.msno = msno;
	}

	public float getThrift() {
		return thrift;
	}

	public void setThrift(float thrift) {
		this.thrift = thrift;
	}

	public float getLoan() {
		return loan;
	}

	public void setLoan(float loan) {
		this.loan = loan;
	}

	public float getLoanInst() {
		return loanInst;
	}

	public void setLoanInst(float loanInst) {
		this.loanInst = loanInst;
	}

	public float getLoanBal() {
		return loanBal;
	}

	public void setLoanBal(float loanBal) {
		this.loanBal = loanBal;
	}

	public float getRd() {
		return rd;
	}

	public void setRd(float rd) {
		this.rd = rd;
	}

	public float getEml() {
		return eml;
	}

	public void setEml(float eml) {
		this.eml = eml;
	}

	public float getEmlInst() {
		return emlInst;
	}

	public void setEmlInst(float emlInst) {
		this.emlInst = emlInst;
	}

	public float getEmlBal() {
		return emlBal;
	}

	public void setEmlBal(float emlBal) {
		this.emlBal = emlBal;
	}

	public float getMisc() {
		return misc;
	}

	public void setMisc(float misc) {
		this.misc = misc;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

}
