package com.callippus.web.business.schedulereports;
import java.math.BigDecimal;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LFChargesSchedule extends JRDefaultScriptlet {
	BigDecimal s, s1, s2,s3,s4;
	BigDecimal pageValue, pageValue1, pageValue2,pageValue3,pageValue4;
	String x = "", x1 = "", x2 = " ",x3="",x4="",x5="";
	@SuppressWarnings("unchecked")
	List arr = new ArrayList();

	@SuppressWarnings("unchecked")
	public void beforePageInit() {
		try {
			BeanStack b = new BeanStack();
			if (this.getVariableValue("RentTotal") != null) {

				s = (BigDecimal) this.getVariableValue("RentTotal");
				b.setRentTotal(s);
			}
			if (this.getVariableValue("WaterTotal") != null) {

				s1 = (BigDecimal) this.getVariableValue("WaterTotal");
				b.setWaterTotal(s1);
			}
			if (this.getVariableValue("ElecTotal") != null) {

				s2 = (BigDecimal) this.getVariableValue("ElecTotal");
				b.setElecTotal(s2);
			}
			if (this.getVariableValue("FurTotal") != null) {

				s3 = (BigDecimal) this.getVariableValue("FurTotal");
				b.setFurTotal(s3);
			}
			if (this.getVariableValue("TotalRecTotal") != null) {

				s4 = (BigDecimal) this.getVariableValue("TotalRecTotal");
				b.setTotalRecTotal(s4);
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
			x2 = "";
			x3="";
			x4="";
			x5="";
			while (it.hasNext()) {
				BeanStack b = new BeanStack();
				b = (BeanStack) it.next();
				pageValue = b.getRentTotal();
				pageValue1 = b.getWaterTotal();
				pageValue2 = b.getElecTotal();
				pageValue3 = b.getFurTotal();
				pageValue4 = b.getTotalRecTotal();

				x = x + "" + i + "\n\n";
				x1 = x1 + String.valueOf(pageValue) + "\n\n";
				x2 = x2 + String.valueOf(pageValue1) + "\n\n";
				x3 = x3 + String.valueOf(pageValue2) + "\n\n";
				x4 = x4 + String.valueOf(pageValue3) + "\n\n";
				x5 = x5 + String.valueOf(pageValue4) + "\n\n";
				i++;

			}//END WHILE

			this.setVariableValue("PageNo", x);
			this.setVariableValue("RentPagewiseTotal", x1);
			this.setVariableValue("WaterPagewiseTotal", x2);
			this.setVariableValue("ElecPagewiseTotal", x3);
			this.setVariableValue("FurPagewiseTotal", x4);
			this.setVariableValue("TotRecPagewiseTotal", x5);

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}