package com.callippus.web.business.schedulereports;
import java.math.BigDecimal;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RegularPayBillCredit extends JRDefaultScriptlet {
	BigDecimal s,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18;
	BigDecimal pageValue,pageValue1,pageValue2,pageValue3,pageValue4,pageValue5,pageValue6,pageValue7,pageValue8,pageValue9,pageValue10,pageValue11,pageValue12,pageValue13,pageValue14,pageValue15,pageValue16,pageValue17,pageValue18;
	String x = "", x1 = "",x2 = "",x3 = "",x4 = "",x5 = "",x6 = "",x7 = "",x8 = "",x9 = "",x10 = "",x11 = "",x12 = "",x13 = "",x14 = "",x15 = "",x16 = "",x17 = "",x18 = "";
	@SuppressWarnings("unchecked")
	List arr = new ArrayList();
	@SuppressWarnings("unchecked")
	public void beforePageInit() {
		try {
			BeanStack b = new BeanStack();
			if (this.getVariableValue("payTotal") != null) {
				s = (BigDecimal) this.getVariableValue("payTotal");
				b.setPayTotal(s);
			}
			if (this.getVariableValue("gpayTotal") != null) {
				s1 = (BigDecimal) this.getVariableValue("gpayTotal");
				b.setGpayTotal(s1);
			}
			if (this.getVariableValue("daTotal") != null) {
				s2 = (BigDecimal) this.getVariableValue("daTotal");
				b.setDaTotal(s2);
			}
			if (this.getVariableValue("splpayTotal") != null) {
				s3 = (BigDecimal) this.getVariableValue("splpayTotal");
				b.setSplpayTotal(s3);
			}
			if (this.getVariableValue("fpaTotal") != null) {
				s4 = (BigDecimal) this.getVariableValue("fpaTotal");
				b.setFpaTotal(s4);
			}
			if (this.getVariableValue("addincrTotal") != null) {
				s5 = (BigDecimal) this.getVariableValue("addincrTotal");
				b.setAddincrTotal(s5);
			}
			if (this.getVariableValue("traTotal") != null) {
				s6 = (BigDecimal) this.getVariableValue("traTotal");
				b.setTraTotal(s6);
			}
			if (this.getVariableValue("hraTotal") != null) {
				s7 = (BigDecimal) this.getVariableValue("hraTotal");
				b.setHraTotal(s7);
			}
			if (this.getVariableValue("wallowTotal") != null) {
				s8 = (BigDecimal) this.getVariableValue("wallowTotal");
				b.setWallowTotal(s8);
			}
			if (this.getVariableValue("xallowTotal") != null) {
				s9 = (BigDecimal) this.getVariableValue("xallowTotal");
				b.setXallowTotal(s9);
			}
			if (this.getVariableValue("vincrTotal") != null) {
				s10 = (BigDecimal) this.getVariableValue("vincrTotal");
				b.setVincrTotal(s10);
			}
			if (this.getVariableValue("miscTotal") != null) {
				s12 = (BigDecimal) this.getVariableValue("miscTotal");
				b.setMiscTotal(s12);
			}
			if (this.getVariableValue("totalpayTotal") != null) {
				s13 = (BigDecimal) this.getVariableValue("totalpayTotal");
				b.setTotalpayTotal(s13);
				arr.add(b);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void afterPageInit() {

	}

	public void beforeReportInit() {

	}

	public void afterReportInit() {
	}

	public void beforeColumnInit() {

	}

	@SuppressWarnings("unchecked")
	public void afterColumnInit() {
		try {
			int i = 1;
			Iterator it = arr.iterator();
			x = "";x1 = "";x2 = "";x3 = "";x4 = "";x5 = "";x6 = "";x7 = "";x8 = "";x9 = "";x10 = "";x11 = "";x12 = "";x13 = "";x14 = "";x15 = "";x16 = "";x17 = "";x18 = "";
			while (it.hasNext()) {
				BeanStack b = new BeanStack();
				b = (BeanStack) it.next();
				pageValue = b.getPayTotal();
				pageValue1 = b.getGpayTotal();
				pageValue2 = b.getDaTotal();
				pageValue3 = b.getSplpayTotal();
				pageValue4 = b.getFpaTotal();
				pageValue5 = b.getAddincrTotal();
				pageValue6 = b.getTraTotal();
				pageValue7 = b.getHraTotal();
				pageValue8 = b.getWallowTotal();
				pageValue9 = b.getXallowTotal();
				pageValue10 = b.getVincrTotal();
				pageValue11 = b.getMiscTotal();
				pageValue12 = b.getTotalpayTotal();
				x = x + "" + i + "\n\n";
				x1 = x1 + String.valueOf(pageValue) + "\n\n";
				x2 = x2 + String.valueOf(pageValue1) + "\n\n";
				x3 = x3 + String.valueOf(pageValue2) + "\n\n";
				x4 = x4 + String.valueOf(pageValue3) + "\n\n";
				x5 = x5 + String.valueOf(pageValue4) + "\n\n";
				x6 = x6 + String.valueOf(pageValue5) + "\n\n";
				x7 = x7 + String.valueOf(pageValue6) + "\n\n";
				x8 = x8 + String.valueOf(pageValue7) + "\n\n";
				x9 = x9 + String.valueOf(pageValue8) + "\n\n";
				x10 = x10 + String.valueOf(pageValue9) + "\n\n";
				x11 = x11 + String.valueOf(pageValue10) + "\n\n";
				x12 = x12 + String.valueOf(pageValue11) + "\n\n";
				x13 = x13 + String.valueOf(pageValue12) + "\n\n";
				i++;

			}//END WHILE

			this.setVariableValue("PageNo", x);
			this.setVariableValue("PayPagewiseTotal", x1);
			this.setVariableValue("GpayPagewiseTotal", x2);
			this.setVariableValue("DAPagewiseTotal", x3);
			this.setVariableValue("SplPayPagewiseTotal", x4);
			this.setVariableValue("FpaPagewiseTotal", x5);
			this.setVariableValue("AddincPagewiseTotal", x6);
			this.setVariableValue("TraPagewiseTotal", x7);
			this.setVariableValue("HraPagewiseTotal", x8);
			this.setVariableValue("WallowPagewiseTotal", x9);
			this.setVariableValue("XallowPagewiseTotal", x10);
			this.setVariableValue("VincrPagewiseTotal", x11);
			this.setVariableValue("MiscPagewiseTotal",x12);
			this.setVariableValue("TotalPayPagewise",x13);			
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}