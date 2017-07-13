package com.webApp.maillService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.webApp.configuration.AppConfig;
public class MailServices {
	
private JavaMailSender mailSender;
	
	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendmail(String subj, String msg, String emailTo) throws MessagingException {
       @SuppressWarnings("resource")
	   AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	   ctx.register(AppConfig.class);
	   ctx.refresh();
	   JavaMailSenderImpl mailSender = ctx.getBean(JavaMailSenderImpl.class);
	   MimeMessage mimeMessage = mailSender.createMimeMessage();
      	   MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
      	   mailMsg.setFrom("reneidarris@gmail.com");
      	   mailMsg.setTo(emailTo);
      	   mailMsg.setSubject(subj);
      	   mailMsg.setText(msg);
	   mailSender.send(mimeMessage);
	   
	}
	
	public synchronized void sendEMail(String emailId, String subject, String msg){
        MimeMessage message =  this.mailSender.createMimeMessage();
        MimeMessageHelper mimeHelper;
        try {
               mimeHelper = new MimeMessageHelper(message,true);
               mimeHelper.setTo(emailId);    
               mimeHelper.setFrom("reneidarris@gmail.com");
               mimeHelper.setSubject(subject);
               mimeHelper.setText(msg,true);
               mailSender.send(message);
        } catch (MessagingException e) {
               System.out.println("Error Sending email "+ e.getMessage());
        }
        notifyAll();  
	}

	public synchronized void sendpasswordemail(String emailId){
		MimeMessage message =  this.mailSender.createMimeMessage();
		MimeMessageHelper mimeHelper;
		try {
			mimeHelper = new MimeMessageHelper(message,true);
			mimeHelper.setTo(emailId);
			
			
			mimeHelper.setFrom("reneidarris@gmail.com");
			mimeHelper.setSubject("Password Reset");
			mimeHelper.setText("<html><body>hi,<br/><a href='http://192.168.2.9:8080/wellness/resetpassword'> Click here</a> to reset password</body></html>",true);
			mailSender.send(message);
		} catch (MessagingException e) {
			System.out.println("Error Sending email "+ e.getMessage());
		}
		notify();  
	}
} 