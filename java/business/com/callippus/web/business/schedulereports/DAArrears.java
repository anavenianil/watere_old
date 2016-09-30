package com.callippus.web.business.schedulereports;
import java.math.BigDecimal;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DAArrears extends JRDefaultScriptlet {
	BigDecimal s,s1,s2,s3;
	int i1,i2;
	BigDecimal pageValue1,pageValue2,pageValue3,pageValue4,pageValue5;
	int pageValue6,pageValue7;
	String x = "", x1 = "",x2="",x3="",x4="",x5="",x6="",x7="",x8="",x9="",x10="",x11="",x12="",x13="",x14="",x15="",x16="";
	@SuppressWarnings("unchecked")
	List arr = new ArrayList();

	@SuppressWarnings("unchecked")
	public void beforePageInit() {
		try {
			BeanStack b = new BeanStack();
			if (this.getVariableValue("daTotal") != null) {
				s = (BigDecimal) this.getVariableValue("daTotal");
				b.setDaTotal(s);
			}
			if (this.getVariableValue("cpfTotal") != null) {
				s = (BigDecimal) this.getVariableValue("cpfTotal");
				b.setCpfTotal(s);
			}
			if (this.getVariableValue("netPMTTotal") != null) {
				i1 =  (Integer)this.getVariableValue("netPMTTotal");
				b.setNetPMTTotal(i1);
			}
			if (this.getVariableValue("tptTotal") != null) {
				s = (BigDecimal) this.getVariableValue("tptTotal");
				b.setTptTotal(s);				
			}
			if (this.getVariableValue("totalAmt") != null) {
				i2 =  (Integer)this.getVariableValue("totalAmt");
				b.setTotalAmt(i2);
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
			x4="";x5="";
			while (it.hasNext()) {
				BeanStack b = new BeanStack();
				b = (BeanStack) it.next();
			
				pageValue1 = b.getDaTotal();
				pageValue2 = b.getCpfTotal();
				pageValue6 = b.getNetPMTTotal();
				pageValue4 = b.getTptTotal();
				pageValue7 = b. getTotalAmt();
				x = x + "" + i + "\n\n";
				x1 = x1 + String.valueOf(pageValue1) + "\n\n";
		        x2 = x2 + String.valueOf(pageValue2) + "\n\n";
				x3 = x3 + String.valueOf(pageValue6) + "\n\n";
				x4 = x4 + String.valueOf(pageValue4) + "\n\n";
				x5 = x5 + String.valueOf(pageValue7) + "\n\n";
				i++;

			}//END WHILE

			this.setVariableValue("pageNumber", x);
			this.setVariableValue("pageDATotal", x1);//PayTotal
			this.setVariableValue("pageDAPMT", x2);//MessTotal
			this.setVariableValue("pageTPTDiff", x3);//RESTotal
			this.setVariableValue("pageFinal", x4);//CCSTotal
			this.setVariableValue("pageDACPF", x5);//HDFCTotal

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}