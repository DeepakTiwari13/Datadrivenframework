package com.Naukri.util;

//set CLASSPATH=%CLASSPATH%;activation.jar;mail.jar

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import java.util.*;

public class SendMail {
	static Logger Naukri_Logs = Logger.getLogger("SendMail");

	public static void main(String[] args) {
		Naukri_Logs.info("Inside mail utils");
		BasicUtil.dofolderzip();
		String to = "kumar.deepaktiwari@gmail.com";// change accordingly
		// Get the session object
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"theautomationtestreport@gmail.com", "synerzip");// change
						// accordingly
					}
				});

		// compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("theautomationtestreport@gmail.com"));// change
			// accordingly
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));
			message.setSubject("Automation execution report");
			message.setText("Please find report in attachment");

			String filename = System.getProperty("user.dir") + "\\Reports.zip";// change
																				// accordingly
			DataSource source = new FileDataSource(filename);
			message.setDataHandler(new DataHandler(source));
			message.setFileName("Reports.zip");

			// send message
			Transport.send(message);

			Naukri_Logs.info("Mail sent successfully");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}