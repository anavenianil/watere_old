package com.callippus.web.business.mailImpl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.callippus.web.beans.common.MailBean;
import com.callippus.web.controller.common.CPSUtils;

@Service("mailService")
/**
 * @author Rajendra
 *
 */
public class MailImplementaion {

private SimpleMailMessage simpleMessage;

private JavaMailSenderImpl mailSender;
private MimeMessage message;


public SimpleMailMessage getSimpleMessage() {
	return simpleMessage;
}
public void setSimpleMessage(SimpleMailMessage simpleMessage) {
	this.simpleMessage = simpleMessage;
}
public MailSender getMailsender() {
	return mailSender;
}
public void setMailsender(JavaMailSenderImpl mailsender) {
	this.mailSender = mailsender;
}
public void setMailSender(JavaMailSenderImpl mailsender){
	
	this.mailSender = mailsender;
}
	
	public void setMessage(SimpleMailMessage simpleMessage) {
		this.simpleMessage = simpleMessage;
		
	}
	
  public void sendMail(MailBean mailBean)  {
	 
	  String   mailSubject2 = "";
	  try{
	//for developer testing	  
	/*if(mailBean.getToMailAddress() !="ess@asl.net"){
	 mailBean.setToMailAddress("essamc@asl.net"); 
		
	}*/
	//for test server testing
		  /*mailBean.setToMailAddress("sf0906@asl.net"); */
	System.out.println("host:"+mailSender.getHost());
	System.out.println("host:"+mailSender.getPort());
	System.out.println("host:"+mailSender.getProtocol());
	message = mailSender.createMimeMessage();
	MimeMessageHelper helper = new MimeMessageHelper(message, true);
	helper.setTo(mailBean.getToMailAddress());	
	helper.setFrom("ess@asl.net","ESS");
	

	
	InternetAddress ir = new InternetAddress();
	
	
	System.out.println(ir.getAddress());
	ir.getAddress();
	
//	helper.setTo("");
	
	helper.setSubject( mailBean.getRequestedBy() +"  "+mailBean.getRequestType()+" Request is pending in your AXESS Dashboard");
	
	//helper.setSubject("");
	
	String  mailSubject1 = "<html><body>"
			                    + "<div style='float:left; width:100%;font-size:13px;'><b>From</b> : ESS </br>"
			                    + " <b>Sent </b>:"+CPSUtils.getCurrentDateWithTime()
			                    + "</br><b>To </b>: "+mailBean.getSendingToName()
			                    + "</br><b>Subject </b>:  Ess "+mailBean.getRequestType()+" Request from "+mailBean.getRequestedBy()
			                    + "</div> <br/></br>"
			                    + "<div style='float:left; width:100%;'>  Dear "+mailBean.getSendingToName()+",</div> <br/>"
	                            
			                    + "<table style='float:left; width:100%;'>"
			                    + " <tr style='float:left; width:100%;'><td style='float:left; width:130px;'> Request Id </td> <td style='float:left; '>: <b>"+mailBean.getRequestId()+"</b></td></tr>"
			                    + "<tr style='float:left; width:100%;'><td style='float:left; width:130px;'>Requester Name </td> <td style='float:left; '>: "+mailBean.getRequestedBy()+"</td></tr>" 
			                    + "<tr style='float:left; width:100%;'><td style='float:left; width:130px;'>Request Type </td> <td style='float:left; '>:  "+mailBean.getRequestType()+"</td></tr>";
	
	                        if(mailBean.getDescription() != null && !mailBean.getDescription().equals(null)){
	                        	mailSubject2    = "<tr style='float:left; width:100%;'><td style='float:left; width:130px;'>Description</td> <td style='float:left; '>: "+mailBean.getDescription()+" </td></tr></table> </br>";
	                             }else{
	                            	 mailSubject2  = " </table> </br>";	 
	                             }
			            String    mailSubject3  =   "</br></br> <div style='float:left; width:100%;'>   Please take necessary action in ESS Application,<a href='http://essc.asl.net/ess' target='_blank'>Click here to goto Ess</a>. "
			                                       + "</div> </br> "
			                                       + "<div style='text-align:center'>======================================xxxxx====================================</div>"
			                                       + " <div style='font-size:12px;'>NOTE:This message is automatically generated by "
			                                       + "ESS application,</br>For information &  queries please call ESS Helpdesk 8059(MIS) or mail us at ess@asl.net. </div> </body></html>";
	
	
	     helper.setText((mailSubject1+mailSubject2)+mailSubject3,true);
	    	 mailSender.send(message);
	  }catch(MailException e){
		  System.err.println(e.getMessage());
		  e.printStackTrace(); 
	  }catch (MessagingException ex) {
		  System.err.println(ex.getMessage());
		  ex.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}
  
  public void sendMailToRequester(MailBean mailBean)  {
	  String   mailSubject2 = "";
	  try{
		//for developer testing	  
			/*if(mailBean.getToMailAddress() !="ess@asl.net"){
			 mailBean.setToMailAddress("essamc@asl.net"); 
				
			}*/
			//for test server testing
				 /* mailBean.setToMailAddress("sf0906@asl.net"); */
	
	message = mailSender.createMimeMessage();
	MimeMessageHelper helper = new MimeMessageHelper(message, true);
	helper.setTo(mailBean.getToMailAddress());	
	helper.setFrom("ess@asl.net","ESS");
	String sub="";

	
	InternetAddress ir = new InternetAddress();
	
	
	//ir.
	
//	helper.setTo("");
	if(mailBean.getStatus()!=null&&mailBean.getStatus().equals("success")){
		sub= "Your  "+mailBean.getRequestType()+" Request with requestid:"+mailBean.getRequestId()+" is APPROVED ";
	}
	else if(mailBean.getStatus()!=null&&mailBean.getStatus().equals("declined")){
		sub= "Your  "+mailBean.getRequestType()+" Request with requestid:"+mailBean.getRequestId()+" is DECLINED ";
	}
	else{
		sub= "Your  "+mailBean.getRequestType()+" Request with requestid:"+mailBean.getRequestId()+" is pending at "+mailBean.getCurrentPosition();
	}
	
	helper.setSubject(sub);
	
	String  mailSubject1 = "<html><body>"
			                    + "<div style='float:left; width:100%;font-size:13px;'><b>From</b> : ESS </br>"
			                    + " <b>Sent </b>:"+CPSUtils.getCurrentDateWithTime()
			                    + "</br><b>To </b>: "+mailBean.getSendingToName()
			                    + "</br><b>Subject </b>:  Ess "+mailBean.getRequestType()+" Request  "
			                    + "</div> <br/></br>"
			                    + "<div style='float:left; width:100%;'>  Dear "+mailBean.getSendingToName()+",</div> <br/>"
	                             + "<div style='float:left; width:100%;'> "+sub+".</div> <br/>"
			                    + "<table style='float:left; width:100%;'>"
			                    + " <tr style='float:left; width:100%;'><td style='float:left; width:130px;'> Request Id </td> <td style='float:left; '>: <b>"+mailBean.getRequestId()+"</b></td></tr>"
			                   // + "<tr style='float:left; width:100%;'><td style='float:left; width:130px;'>Requester Name </td> <td style='float:left; '>: "+mailBean.getRequestedBy()+"</td></tr>" 
			                    + "<tr style='float:left; width:100%;'><td style='float:left; width:130px;'>Request Type </td> <td style='float:left; '>:  "+mailBean.getRequestType()+"</td></tr>";
	
	                        if(mailBean.getDescription() != null && !mailBean.getDescription().equals(null)){
	                        	mailSubject2    = "<tr style='float:left; width:100%;'><td style='float:left; width:130px;'>Description</td> <td style='float:left; '>: "+mailBean.getDescription()+" </td></tr></table> </br>";
	                             }else{
	                            	 mailSubject2  = " </table> </br>";	 
	                             }
			            String    mailSubject3  =   "</br></br> <div style='float:left; width:100%;'>   Please take necessary action in ESS Application,<a href='http://essc.asl.net/ess' target='_blank'>Click here to goto Ess</a>. "
			                                       + "</div> </br> "
			                                       + "<div style='text-align:center'>======================================xxxxx====================================</div>"
			                                       + " <div style='font-size:12px;'>NOTE:This message is automatically generated by "
			                                       + "ESS application,</br>For information &  queries please call ESS Helpdesk 8059(MIS) or mail us at ess@asl.net. </div> </body></html>";
	
	
	     helper.setText((mailSubject1+mailSubject2)+mailSubject3,true);
	    	 mailSender.send(message);
	  }catch(MailException e){
		  System.err.println(e.getMessage());
		  e.printStackTrace(); 
	  }catch (MessagingException ex) {
		  System.err.println(ex.getMessage());
		  ex.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
