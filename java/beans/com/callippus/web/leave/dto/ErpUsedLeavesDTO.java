package com.callippus.web.leave.dto;

public class ErpUsedLeavesDTO {
	
	private int aL;
	private int sL;
	private int mL;
	private int pL;
	private String pL1;
	private String mL1;
	private String aL1;
	private String sL1;
	private String cL1;
	private String comL1;
	
	public String getComL1() {
		return comL1;
	}
	public void setComL1(String comL1) {
		this.comL1 = comL1;
	}
	public String getcL1() {
		return cL1;
	}
	public void setcL1(String cL1) {
		this.cL1 = cL1;
	}
	public String getaL1() {
		return aL1;
	}
	public void setaL1(String aL1) {
		this.aL1 = aL1;
	}
	public String getsL1() {
		return sL1;
	}
	public void setsL1(String sL1) {
		this.sL1 = sL1;
	}
	public String getmL1() {
		return mL1;
	}
	public void setmL1(String mL1) {
		this.mL1 = mL1;
	}
	public String getpL1() {
		return pL1;
	}
	public void setpL1(String pL1) {
		this.pL1 = pL1;
	}
	public int getaL() {
		return aL;
	}
	public void setaL(int aL) {
		this.aL = aL;
	}
	public int getsL() {
		return sL;
	}
	public void setsL(int sL) {
		this.sL = sL;
	}
	public int getmL() {
		return mL;
	}
	public void setmL(int mL) {
		this.mL = mL;
	}
	public int getpL() {
		return pL;
	}
	public void setpL(int pL) {
		this.pL = pL;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErpUsedLeavesDTO [aL=");
		builder.append(aL);
		builder.append(", sL=");
		builder.append(sL);
		builder.append(", mL=");
		builder.append(mL);
		builder.append(", pL=");
		builder.append(pL);
		builder.append(", pL1=");
		builder.append(pL1);
		builder.append(", mL1=");
		builder.append(mL1);
		builder.append(", aL1=");
		builder.append(aL1);
		builder.append(", sL1=");
		builder.append(sL1);
		builder.append(", cL1=");
		builder.append(cL1);
		builder.append("]");
		return builder.toString();
	}
	
	

}
