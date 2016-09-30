package com.callippus.web.business.schedulereports;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OTSchedule extends JRDefaultScriptlet
{
	Integer s;
	Integer pageValue;
	String x = "", x1 = "";
	List<BeanStack> arr = new ArrayList<BeanStack>();

	public void beforePageInit()
	{
		try
		{
			BeanStack b = new BeanStack();
			if (this.getVariableValue("OTTotal") != null)
			{
				s = (Integer) this.getVariableValue("OTTotal");
				b.setOTTotal(s);
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

	public void afterColumnInit()
	{
		try
		{
			int i = 1;
			Iterator<BeanStack> it = arr.iterator();
			x = "";
			x1 = "";
			while (it.hasNext())
			{
				BeanStack b = new BeanStack();
				b = (BeanStack) it.next();
				pageValue = b.getOTTotal();
				x = x + "" + i + "\n";
				x1 = x1 + String.valueOf(pageValue) + "\n";
				i++;
			} // END WHILE
			this.setVariableValue("PageNo", x);
			this.setVariableValue("OTPagewiseTotal", x1);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}