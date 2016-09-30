package com.callippus.web.business.schedulereports;
import java.math.BigDecimal;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class festSchedule extends JRDefaultScriptlet {
	BigDecimal s,s1,s2,s3;
	BigDecimal pageValue,pageValue1,pageValue2,pageValue3;
	String x = "", x1 = "",x2="",x3="",x4="";
	@SuppressWarnings("unchecked")
	List arr = new ArrayList();

	@SuppressWarnings("unchecked")
	public void beforePageInit() {
		try {
			BeanStack b = new BeanStack();
			System.out.println("****************beforePageInit*************************");
			if (this.getVariableValue("totAmt") != null) {
				s = (BigDecimal) this.getVariableValue("totAmt");
				b.setFestadvTot(s);
				arr.add(b);
			}




		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void afterPageInit() {
		System.out.println("****************afterPageInit*************************");
	}

	public void beforeReportInit() {
		System.out.println("****************beforeReportInit*************************");
	}

	public void afterReportInit() {
		System.out.println("****************afterReportInit*************************");
	}

	public void beforeColumnInit() {
		System.out.println("****************beforeColumnInit*************************");
	}

	@SuppressWarnings("unchecked")
	public void afterColumnInit() {
		try {
			System.out.println("****************afterColumnInit*************************");
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
				pageValue = b.getFestadvTot();
				x = x + "" + i + "\n\n";
				x1 = x1 + String.valueOf(pageValue) + "\n\n";
		       
				i++;

			}//END WHILE

			this.setVariableValue("pageNo", x);
			this.setVariableValue("pagewiseFestTotal", x1);
			

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}