package com.callippus.web.business.schedulereports;
import java.math.BigDecimal;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CGHSSchedule extends JRDefaultScriptlet {
	BigDecimal s;
	BigDecimal pageValue, pageValue1, pageValue2;
	String x = "", x1 = "";
	@SuppressWarnings("unchecked")
	List arr = new ArrayList();
	@SuppressWarnings("unchecked")
	public void beforePageInit() {
		try {
			BeanStack b = new BeanStack();
			if (this.getVariableValue("CGHSTotal") != null) {

				s = (BigDecimal) this.getVariableValue("CGHSTotal");
				b.setCGHSTotal(s);
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
				pageValue = b.getCGHSTotal();
				x = x + "" + i + "\n\n";
				x1 = x1 + String.valueOf(pageValue) + "\n\n";
				i++;

			}//END WHILE

			this.setVariableValue("PageNo", x);
			this.setVariableValue("CGHSPageWiseTotal", x1);

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}