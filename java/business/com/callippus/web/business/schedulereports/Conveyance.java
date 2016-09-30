package com.callippus.web.business.schedulereports;
import java.math.BigDecimal;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Conveyance extends JRDefaultScriptlet {
	BigDecimal s,s1,s2,s3;
	BigDecimal pageValue,pageValue1,pageValue2,pageValue3;
	String x = "", x1 = "",x2="",x3="",x4="";
	@SuppressWarnings("unchecked")
	List arr = new ArrayList();

	@SuppressWarnings("unchecked")
	public void beforePageInit() {
		try {
			BeanStack b = new BeanStack();
			if (this.getVariableValue("ConPageTotal") != null) {
				s = (BigDecimal) this.getVariableValue("ConPageTotal");
				b.setConPageTotal(s);
			}
			if (this.getVariableValue("carinstamt") != null) {
				s1 = (BigDecimal) this.getVariableValue("carinstamt");
				b.setCarinstamt(s1);
			}
			if (this.getVariableValue("scooterinstamt") != null) {
				s2 = (BigDecimal) this.getVariableValue("scooterinstamt");
				b.setScooterinstamt(s2);
			}
			if (this.getVariableValue("bicycleinstamt") != null) {
				s3 = (BigDecimal) this.getVariableValue("bicycleinstamt");
				b.setBicycleinstamt(s3);
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
			x4="";
			while (it.hasNext()) {
				BeanStack b = new BeanStack();
				b = (BeanStack) it.next();
				pageValue = b.getConPageTotal();
				pageValue1 = b.getCarinstamt();
				pageValue2 = b.getScooterinstamt();
				pageValue3 = b.getBicycleinstamt();

				x = x + "" + i + "\n\n";
				x1 = x1 + String.valueOf(pageValue) + "\n\n";
		        x2 = x2 + String.valueOf(pageValue1) + "\n\n";
				x3 = x3 + String.valueOf(pageValue2) + "\n\n";
				x4 = x4 + String.valueOf(pageValue3) + "\n\n";
				i++;

			}//END WHILE

			this.setVariableValue("PageNo", x);
			this.setVariableValue("PagewiseTotal", x1);
			this.setVariableValue("caramtpagewise", x2);
			this.setVariableValue("scooteramtpagewise", x3);
			this.setVariableValue("bicycleamtpagewise", x4);

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}