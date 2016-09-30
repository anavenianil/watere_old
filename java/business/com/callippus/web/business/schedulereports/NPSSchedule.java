package com.callippus.web.business.schedulereports;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

public class NPSSchedule extends JRDefaultScriptlet{
	@SuppressWarnings("unchecked")
	List arrList=new ArrayList();
@SuppressWarnings("unchecked")
@Override
public void beforePageInit(){
try{
	BeanStack bean=new BeanStack();
	BigDecimal s1,s2,s3,s4;
	if(this.getVariableValue("Tier1SubTotal")!= null){
		 s1=(BigDecimal)this.getVariableValue("Tier1SubTotal");
		 bean.setTier1SubTotal(s1);
	}
	if(this.getVariableValue("Tier1ArrTotal")!= null){
		 s2=(BigDecimal)this.getVariableValue("Tier1ArrTotal");
		 bean.setTier1ArrTotal(s2);
	}
	if(this.getVariableValue("ContTotal")!= null){
		 s3=(BigDecimal)this.getVariableValue("ContTotal");
		 bean.setContTotal(s3);
	}
	if(this.getVariableValue("PageTotal")!= null){
		 s4=(BigDecimal)this.getVariableValue("PageTotal");
		 bean.setPageTotal(s4);
		arrList.add(bean);
	}
	
}catch(Exception e){
	e.printStackTrace();
}
}
@SuppressWarnings("unchecked")
@Override
public void afterColumnInit() throws JRScriptletException {
try{
	int i=1;
	String x="",x1="",x2="",x3="", x4="";
	Iterator it=arrList.iterator();
	while(it.hasNext()){
		BeanStack b=new BeanStack();
		b=(BeanStack)it.next();
		x=x + "" +i+ "\n\n";
		x1=x1 + "" +b.getTier1SubTotal() + "\n\n";
		x2=x2 + "" +b.getTier1ArrTotal() +"\n\n";
		x3=x3 + "" +b.getContTotal() +"\n\n";
		x4=x4 + "" +b.getPageTotal() +"\n\n";
		i++;
	}
	this.setVariableValue("PageNo", x);
	this.setVariableValue("PageWiseTierSubTotal", x1);
	this.setVariableValue("PageWiseTierArrTotal", x2);
	this.setVariableValue("PageWiseContTotal", x3);
	this.setVariableValue("PageWiseTotal", x4);
	
}catch(Exception e){
	e.printStackTrace();
}
}
}
