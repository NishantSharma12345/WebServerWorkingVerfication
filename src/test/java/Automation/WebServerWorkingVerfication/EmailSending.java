package Automation.WebServerWorkingVerfication;

import java.io.File;

import baseClass.BaseClass;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailSending extends BaseClass 
{
	public void emailSending() 
	{
		File reportFilePath = new File(System.getProperty("user.dir") + "//Reports//index.html");
		String report = reportFilePath.getPath();

		// Setting up mail server
		prop.put("mail.smtp.host", prop.getProperty("host"));

		// To use TLS
		prop.put("mail.smtp.auth", prop.getProperty("auth"));
		prop.put("mail.smtp.starttls.enable", prop.getProperty("starttls"));

		// To use SSL
		prop.put("mail.smtp.socketFactory.port", prop.getProperty("socketFactoryPort"));
		prop.put("mail.smtp.socketFactory.class", prop.getProperty("socketFactoryClass"));
		prop.put("mail.smtp.port", prop.getProperty("port"));

		// creating session object to get properties
		Session session = Session.getDefaultInstance(prop, new jakarta.mail.Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication() 
			{
				return new PasswordAuthentication(prop.getProperty("user"), prop.getProperty("password"));// Specify the Username and the PassWord
			}
		});

		try 
		{
			// MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From Field: adding senders email to from field.
			message.setFrom(new InternetAddress(prop.getProperty("sender")));

			// Set To Field: adding recipient's email to from field.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(prop.getProperty("recipient")));

			message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(prop.getProperty("cc")));

			// Set Subject: subject of the email
			message.setSubject("Bagisto Demos Web Working Report");

			// Create the Message part
			BodyPart messageBodypart = new MimeBodyPart();

			// Enter the message in the Mail Body
			messageBodypart.setText("*********** Find the Attachment for the Report ****************");

			// Create a Multipart Message
			Multipart multipart = new MimeMultipart();

			// Set Text message part
			multipart.addBodyPart(messageBodypart);

			// Part two is attachment
			// create the Message part
			messageBodypart = new MimeBodyPart();
			DataSource source = new FileDataSource(report);
			messageBodypart.setDataHandler(new DataHandler(source));
			messageBodypart.setFileName("Automation_Execution_Report.html");

			// set the text message part
			multipart.addBodyPart(messageBodypart);

			// Send the complete message part
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			System.out.println("Mail has sent successfully.");

		} 
		catch (MessagingException mex) 
		{
			mex.printStackTrace();
		}
	}

}
