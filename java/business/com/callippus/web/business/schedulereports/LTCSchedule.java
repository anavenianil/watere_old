package com.callippus.web.business.schedulereports;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDefaultScriptlet;

public class LTCSchedule extends  JRDefaultScriptlet{

	BigDecimal s;
	BigDecimal pageValue;
	String x = "", x1 = "";
	@SuppressWarnings("unchecked")
	List arr = new ArrayList();
	@SuppressWarnings("unchecked")
	public void beforePageInit() {
		try {
			BeanStack b = new BeanStack();
			if (this.getVariableValue("LTCTotal") != null) {

				s = (BigDecimal) this.getVariableValue("LTCTotal");
				b.setTadaTotal(s);
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
			while (it.hasNext()) {
				BeanStack b = new BeanStack();
				b = (BeanStack) it.next();
				pageValue = b.getTadaTotal();
				x = x + "" + i + "\n\n";
				x1 = x1 + String.valueOf(pageValue) + "\n\n";
				i++;

			}//END WHILE

			this.setVariableValue("PageNo", x);
			this.setVariableValue("LTCPageTotal", x1);

		} catch (Exception e) {
			System.out.println(e);
		}

	}


}
