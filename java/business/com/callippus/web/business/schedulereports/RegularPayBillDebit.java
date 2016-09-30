package com.callippus.web.business.schedulereports;
import java.math.BigDecimal;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RegularPayBillDebit extends JRDefaultScriptlet {
	BigDecimal s,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20,s21,s22,s23,s24,s25;
	BigDecimal pageValue,pageValue1,pageValue2,pageValue3,pageValue4,pageValue5,pageValue6,pageValue7,pageValue8,pageValue9,pageValue10,pageValue11,pageValue12,pageValue13,pageValue14,pageValue15,pageValue16,pageValue17,pageValue18,pageValue19,pageValue20,pageValue21,pageValue22,pageValue23,pageValue24,pageValue25;
	String x = "", x1 = "",x2 = "",x3 = "",x4 = "",x5 = "",x6 = "",x7 = "",x8 = "",x9 = "",x10 = "",x11 = "",x12 = "",x13 = "",x14 = "",x15 = "",x16 = "",x17 = "",x18 = "",x19 = "",x20 = "",x21 = "",x22 = "",x23 = "",x24 = "",x25 = "",x26="";
	@SuppressWarnings("unchecked")
	List arr = new ArrayList();
	@SuppressWarnings("unchecked")
	public void beforePageInit() {
		try {
			BeanStack b = new BeanStack();
			if (this.getVariableValue("GPFTotal") != null) {
				s = (BigDecimal) this.getVariableValue("GPFTotal");
				b.setGPFTotal(s);
			}
			if (this.getVariableValue("GPFSATotal") != null) {
				s1 = (BigDecimal) this.getVariableValue("GPFSATotal");
				b.setGPFSATotal(s1);
			}
			if (this.getVariableValue("GPFRTotal") != null) {
				s2 = (BigDecimal) this.getVariableValue("GPFRTotal");
				b.setGPFRTotal(s2);
			}
			if (this.getVariableValue("PLITotal") != null) {
				s3 = (BigDecimal) this.getVariableValue("PLITotal");
				b.setPLITotal(s3);
			}
			if (this.getVariableValue("LFTotal") != null) {
				s4 = (BigDecimal) this.getVariableValue("LFTotal");
				b.setLFTotal(s4);
			}
			if (this.getVariableValue("CGEISTotal") != null) {
				s5 = (BigDecimal) this.getVariableValue("CGEISTotal");
				b.setCGEISTotal(s5);
			}
			if (this.getVariableValue("CGHSTotal") != null) {
				s6 = (BigDecimal) this.getVariableValue("CGHSTotal");
				b.setCGHSTotal(s6);
			}
			if (this.getVariableValue("carTotal") != null) {
				s7 = (BigDecimal) this.getVariableValue("carTotal");
				b.setCarTotal(s7);
			}
			if (this.getVariableValue("scooterTotal") != null) {
				s8 = (BigDecimal) this.getVariableValue("scooterTotal");
				b.setScooterTotal(s8);
			}
			if (this.getVariableValue("cycleTotal") != null) {
				s9 = (BigDecimal) this.getVariableValue("cycleTotal");
				b.setCycleTotal(s9);
			}
			if (this.getVariableValue("hbadvTotal") != null) {
				s10 = (BigDecimal) this.getVariableValue("hbadvTotal");
				b.setHbadvTotal(s10);
			}
			if (this.getVariableValue("pclnTotal") != null) {
				s11 = (BigDecimal) this.getVariableValue("pclnTotal");
				b.setPclnTotal(s11);
			}
			if (this.getVariableValue("festadvTot") != null) {
				s12 = (BigDecimal) this.getVariableValue("festadvTot");
				b.setFestadvTot(s12);
			}
			if (this.getVariableValue("itaxTotal") != null) {
				s13 = (BigDecimal) this.getVariableValue("itaxTotal");
				b.setItaxTotal(s13);
			}
			if (this.getVariableValue("educessTotal") != null) {
				s14 = (BigDecimal) this.getVariableValue("educessTotal");
				b.setEducessTotal(s14);
			}
			if (this.getVariableValue("shecTotal") != null) {
				s15 = (BigDecimal) this.getVariableValue("shecTotal");
				b.setShecTotal(s15);
			}
			if (this.getVariableValue("ptaxTotal") != null) {
				s16 = (BigDecimal) this.getVariableValue("ptaxTotal");
				b.setPtaxTotal(s16);
			}
			if (this.getVariableValue("tadaTotal") != null) {
				s17 = (BigDecimal) this.getVariableValue("tadaTotal");
				b.setTadaTotal(s17);
			}
			if (this.getVariableValue("ltcTotal") != null) {
				s18 = (BigDecimal) this.getVariableValue("ltcTotal");
				b.setLtcTotal(s18);
			}
			if (this.getVariableValue("medTotal") != null) {
				s19 = (BigDecimal) this.getVariableValue("medTotal");
				b.setMedTotal(s19);
			}
			if (this.getVariableValue("eolhplTotal") != null) {
				s20 = (BigDecimal) this.getVariableValue("eolhplTotal");
				b.setEolhplTotal(s20);
			}
			if (this.getVariableValue("misc1Total") != null) {
				s21 = (BigDecimal) this.getVariableValue("misc1Total");
				b.setMisc1Total(s21);
			}
			if (this.getVariableValue("misc2Total") != null) {
				s22 = (BigDecimal) this.getVariableValue("misc2Total");
				b.setMisc2Total(s22);
			}
			if (this.getVariableValue("totalDeduction") != null) {
				s23 = (BigDecimal) this.getVariableValue("totalDeduction");
				b.setTotalDeduction(s23);
			}
			if (this.getVariableValue("totalnetPay") != null) {
				s24 = (BigDecimal) this.getVariableValue("totalnetPay");
				b.setTotalnetPay(s24);
			}
			if (this.getVariableValue("ITAXCESSSHECTotal") != null) {
				s25 = (BigDecimal) this.getVariableValue("ITAXCESSSHECTotal");
				b.setITAXCESSSHECTotal(s25);
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
			x = "";x1 = "";x2 = "";x3 = "";x4 = "";x5 = "";x6 = "";x7 = "";x8 = "";x9 = "";x10 = "";x11 = "";x12 = "";x13 = "";x14 = "";x15 = "";x16 = "";x17 = "";x18 = "";x19 = "";x20 = "";x21 = "";x22 = "";x23 = "";x24 = "";x25 = "";x26 = "";
			while (it.hasNext()) {
				BeanStack b = new BeanStack();
				b = (BeanStack) it.next();
				pageValue = b.getGPFTotal();
				pageValue1 = b.getGPFSATotal();
				pageValue2 = b.getGPFRTotal();
				pageValue3 = b.getPLITotal();
				pageValue4 = b.getLFTotal();
				pageValue5 = b.getCGEISTotal();
				pageValue6 = b.getCGHSTotal();
				pageValue7 = b.getCarTotal();
				pageValue8 = b.getScooterTotal();
				pageValue9 = b.getCycleTotal();
				pageValue10 = b.getHbadvTotal();
				pageValue11 = b.getPclnTotal();
				pageValue12 = b.getFestadvTot();
				pageValue13 = b.getItaxTotal();
				pageValue14 = b.getEducessTotal();
				pageValue15 = b.getShecTotal();
				pageValue16 = b.getPtaxTotal();
				pageValue17 = b.getTadaTotal();
				pageValue18 = b.getLtcTotal();
				pageValue19 = b.getMedTotal();
				pageValue20 = b.getEolhplTotal();
				pageValue21 = b.getMisc1Total();
				pageValue22 = b.getMisc2Total();
				pageValue23 = b.getTotalDeduction();
				pageValue24 = b.getTotalnetPay();
				pageValue25 = b.getITAXCESSSHECTotal();
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
				x17 = x17 + String.valueOf(pageValue16) + "\n\n";
				x18 = x18 + String.valueOf(pageValue17) + "\n\n";
				x19 = x19 + String.valueOf(pageValue18) + "\n\n";
				x20 = x20 + String.valueOf(pageValue19) + "\n\n";
				x21 = x21 + String.valueOf(pageValue20) + "\n\n";
				x22 = x22 + String.valueOf(pageValue21) + "\n\n";
				x23 = x23 + String.valueOf(pageValue22) + "\n\n";
				x24 = x24 + String.valueOf(pageValue23) + "\n\n";
				x25 = x25 + String.valueOf(pageValue24) + "\n\n";
				x26 = x26 + String.valueOf(pageValue25) + "\n\n";
				i++;

			}//END WHILE

			this.setVariableValue("PageNo", x);
			this.setVariableValue("GPFPageWiseTotal", x1);//GPFTotal
			this.setVariableValue("GPFSAPageWiseTotal", x2);//GPFSATotal
			this.setVariableValue("GPFRPageWiseTotal", x3);//GPFRTotal
			this.setVariableValue("PLIPageWiseTotal", x4);//PLITotal
			this.setVariableValue("LFChargesPageWiseTotal", x5);//LFTotal
			this.setVariableValue("CGEISPageWiseTotal", x6);//CGEISTotal
			this.setVariableValue("CGHSPageWiseTotal", x7);//CGHSTotal
			this.setVariableValue("carPageWiseTotal", x8);//CarTotal
			this.setVariableValue("scooterPageWiseTotal", x9);//ScooterTotal
			this.setVariableValue("cyclePageWiseTotal", x10);//CycleTotal
			this.setVariableValue("HBADVPageWiseTotal", x11);//HbadvTotal
			this.setVariableValue("PCLNPageWiseTotal",x12);//PclnTotal
			this.setVariableValue("FestAdvPageWiseTotal",x13);//FestadvTot
			this.setVariableValue("IESPageWiseTotal",x26);//ItaxEduShectotal
			//this.setVariableValue(x14);ItaxTotal
			//this.setVariableValue(x15);EducessTotal
			//this.setVariableValue( x16);ShecTotal		
			this.setVariableValue("PtaxPageWiseTotal",x17);//PtaxTotal
			this.setVariableValue("TADAPageWiseTotal",x18);//TadaTotal
			this.setVariableValue("LTCPageWiseTotal",x19);//LtcTotal
			this.setVariableValue("MEDPageWiseTotal",x20);//MedTotal
			this.setVariableValue("EOLHPLPageWiseTotal",x21);//EolhplTotal
			this.setVariableValue("MISC1PageWiseTotal",x22);//Misc1Total
			this.setVariableValue("MISC2PageWiseTotal",x23);//Misc2Total
			this.setVariableValue("DeductionPageWiseTotal", x24);//TotalDeduction
			this.setVariableValue("netPayPageWiseTotal", x25);//TotalnetPay
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}