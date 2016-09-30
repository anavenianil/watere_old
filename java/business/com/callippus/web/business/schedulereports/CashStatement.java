package com.callippus.web.business.schedulereports;
import java.math.BigDecimal;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CashStatement extends JRDefaultScriptlet {
	BigDecimal s,s1,s2,s3;
	BigDecimal pageValue,pageValue1,pageValue2,pageValue3,pageValue4,pageValue5,pageValue6,pageValue7,pageValue8,pageValue9,pageValue10,pageValue11,pageValue12,pageValue13,pageValue14,pageValue15;
	String x = "", x1 = "",x2="",x3="",x4="",x5="",x6="",x7="",x8="",x9="",x10="",x11="",x12="",x13="",x14="",x15="",x16="";
	@SuppressWarnings("unchecked")
	List arr = new ArrayList();

	@SuppressWarnings("unchecked")
	public void beforePageInit() {
		try {
			BeanStack b = new BeanStack();
			if (this.getVariableValue("PayTotal") != null) {
				s = (BigDecimal) this.getVariableValue("PayTotal");
				b.setPayTotal(s);
			}
			if (this.getVariableValue("MessTotal") != null) {
				s = (BigDecimal) this.getVariableValue("MessTotal");
				b.setMessTotal(s);
			}
			if (this.getVariableValue("RESTotal") != null) {
				s = (BigDecimal) this.getVariableValue("RESTotal");
				b.setRESTotal(s);
			}
			if (this.getVariableValue("CCSTotal") != null) {
				s = (BigDecimal) this.getVariableValue("CCSTotal");
				b.setCCSTotal(s);				
			}
			if (this.getVariableValue("HDFCTotal") != null) {
				s = (BigDecimal) this.getVariableValue("HDFCTotal");
				b.setHDFCTotal(s);
			}
			if (this.getVariableValue("CANFINTotal") != null) {
				s = (BigDecimal) this.getVariableValue("CANFINTotal");
				b.setCANFINTotal(s);
			}
			if (this.getVariableValue("LICTotal") != null) {
				s = (BigDecimal) this.getVariableValue("LICTotal");
				b.setLICTotal(s);
			}
			if (this.getVariableValue("GICTotal") != null) {
				s = (BigDecimal) this.getVariableValue("GICTotal");
				b.setGICTotal(s);
			}
			if (this.getVariableValue("CAttachTotal") != null) {
				s = (BigDecimal) this.getVariableValue("CAttachTotal");
				b.setCAttachTotal(s);
			}
			if (this.getVariableValue("MiscTotal") != null) {
				s = (BigDecimal) this.getVariableValue("MiscTotal");
				b.setMiscTotal(s);
					
			}
			if (this.getVariableValue("TotalDedTotal") != null) {
				s = (BigDecimal) this.getVariableValue("TotalDedTotal");
				b.setTotalDedTotal(s);
			}
			if (this.getVariableValue("NetPayTotal") != null) {
				s = (BigDecimal) this.getVariableValue("NetPayTotal");
				b.setNetPayTotal(s);
			}
			if (this.getVariableValue("WsubTotal") != null) {
				s = (BigDecimal) this.getVariableValue("WsubTotal");
				b.setWsubTotal(s);
			}
			if (this.getVariableValue("WloanTotal") != null) {
				s = (BigDecimal) this.getVariableValue("WloanTotal");
				b.setWloanTotal(s);			
			}
			if (this.getVariableValue("BFTotal") != null) {
				s = (BigDecimal) this.getVariableValue("BFTotal");
				b.setBFTotal(s);
			}
			if (this.getVariableValue("RFTotal") != null) {
				s = (BigDecimal) this.getVariableValue("RFTotal");
				b.setRFTotal(s);
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
			x = "";
			x1 = "";
			x2="";
			x3="";
			x4="";x5="";x6="";x7="";x8="";x9="";x10="";x11="";x12="";x13="";x14="";x15="";x16="";
			while (it.hasNext()) {
				BeanStack b = new BeanStack();
				b = (BeanStack) it.next();
				pageValue = b.getPayTotal();
				pageValue1 = b.getMessTotal();
				pageValue2 = b.getRESTotal();
				pageValue3 = b.getCCSTotal();
				pageValue4 = b.getHDFCTotal();
				pageValue5 = b.getCANFINTotal();
				pageValue6 = b.getLICTotal();
				pageValue7 = b.getGICTotal();
				pageValue8 = b.getCAttachTotal();
				pageValue9 = b.getMiscTotal();
				pageValue10 = b.getTotalDedTotal();
				pageValue11 = b.getNetPayTotal();
				pageValue12 = b.getWsubTotal();
				pageValue13 = b.getWloanTotal();
				pageValue14 = b.getBFTotal();
				pageValue15 = b.getRFTotal();

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
				x14 = x14 + String.valueOf(pageValue13) + "\n\n";
				x15 = x15 + String.valueOf(pageValue14) + "\n\n";
				x16 = x16 + String.valueOf(pageValue15) + "\n\n";
				i++;

			}//END WHILE

			this.setVariableValue("PageNo", x);
			this.setVariableValue("PayPageWiseTotal", x1);//PayTotal
			this.setVariableValue("MessPageWiseTotal", x2);//MessTotal
			this.setVariableValue("RESPageWiseTotal", x3);//RESTotal
			this.setVariableValue("CCSPageWiseTotal", x4);//CCSTotal
			this.setVariableValue("HDFCPageWiseTotal", x5);//HDFCTotal
			this.setVariableValue("CANFINPageWiseTotal", x6);//CANFINTotal
			this.setVariableValue("LICPageWiseTotal", x7);//LICTotal
			this.setVariableValue("GICPageWiseTotal", x8);//GICTotal
			this.setVariableValue("CAttachPageWiseTotal", x9);//CAttachTotal
			this.setVariableValue("MISCPageWiseTotal", x10);//MiscTotal
			this.setVariableValue("TotalDedPageWiseTotal", x11);//TotalDedTotal
			this.setVariableValue("NetPayPAgeWiseTotal", x12);//NetPayTotal
			this.setVariableValue("WsubPagewisetotal", x13);//WsubTotal
			this.setVariableValue("WloanPagewiseTotal", x14);//WloanTotal
			this.setVariableValue("BFPageWiseTotal", x15);//WloanTotal
			this.setVariableValue("RFPageWiseTotal", x16);//WloanTotal

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}