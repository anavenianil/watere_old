package com.callippus.web.business.schedulereports;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.callippus.web.controller.common.CPSUtils;

import net.sf.jasperreports.engine.JRDefaultScriptlet;

public class DAArrearsAllCategorySchedule extends JRDefaultScriptlet {
	BigDecimal s1,s2,s3,s4,s5;
	//Integer s6;
	BigDecimal pageValue1,pageValue2,pageValue3,pageValue4,pageValue5;
	//Integer pageValue10;
	String x= "",x1 = "",x2 = "",x3 = "",x4 = "",x5 = "";
	List<BeanStack> arr = new ArrayList<BeanStack>();
	public void beforePageInit() {
		try {
			System.out.println("beforePageInit");
			BeanStack b = new BeanStack();
			if (this.getVariableValue("total") != null) {
				System.out.println("total is not null");
				s1 = (BigDecimal) this.getVariableValue("total");
				System.out.println("value to be set to bean"+s1);
				b.setTotal(s1);
				System.out.println("value from bean"+b.getTotal());
			}
			
			if (this.getVariableValue("CPF_TOTAL") != null) {
				s2 = (BigDecimal) this.getVariableValue("CPF_TOTAL");
				b.setCPF(s2);
			}
			if (this.getVariableValue("NET_PMT_TOTAL") != null) {
				s3 = (BigDecimal) this.getVariableValue("NET_PMT_TOTAL");
				b.setNetPMT(s3);
			}
			if (this.getVariableValue("TPT_TOTAL") != null) {
				s4 = (BigDecimal) this.getVariableValue("TPT_TOTAL");
				b.setTPT(s4);
			}
			if (this.getVariableValue("PAYABLE_TOTAL") != null) {
				s5 = (BigDecimal) this.getVariableValue("PAYABLE_TOTAL");
				b.setPayable(s5);
				//arr.add(b);
				}
				
			arr.add(b);
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
			System.out.println("afterColumnInit");
			int i = 1;
			Iterator it = arr.iterator();
			 x = "";
			 x1 = "";
			 x2 = "";
			 x3 = "";
			 x4 = "";
			 x5 = "";
			 System.out.println("srr size"+arr.size());
			while (it.hasNext()) {
				System.out.println("ITR"+i);
				BeanStack b =(BeanStack) it.next();
				pageValue1 = b.getTotal();
				System.out.println("get value from bean");
				System.out.println("value"+b.getTotal());
				
				pageValue2 = b.getCPF();
				pageValue3 = b.getNetPMT();
				pageValue4 = b.getTPT();
				pageValue5 = b.getPayable();
				
				x = x + "PAGE " + i + " TOTAL \n\n";
				x1 = x1 + "" + (String.valueOf(pageValue1)=="null"?"":(String.valueOf(pageValue1) + "\n\n"));
				x2 = x2 + "" + (String.valueOf(pageValue2)=="null"?"":(String.valueOf(pageValue2) + "\n\n"));
				x3 = x3 + "" + (String.valueOf(pageValue3)=="null"?"":(String.valueOf(pageValue3) + "\n\n"));
				x4 = x4 + "" + (String.valueOf(pageValue4)=="null"?"":(String.valueOf(pageValue4) + "\n\n"));
				x5 = x5 + "" + (String.valueOf(pageValue5)=="null"?"":(String.valueOf(pageValue5) + "\n\n"));
				i++;
			}//END WHILE
			System.out.println("final value "+x1 );
			System.out.println("pageno:"+x );
			this.setVariableValue("PageNo", x);
			this.setVariableValue("PagewiseTotal", x1);
			this.setVariableValue("PagewiseCPF", x2);
			this.setVariableValue("PagewiseNetPMT", x3);
			this.setVariableValue("PagewiseTPT", x4);
			this.setVariableValue("PagewisePayable", x5);
					
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
