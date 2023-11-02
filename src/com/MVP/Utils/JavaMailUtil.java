package com.MVP.Utils;

import java.util.Properties;

import javax.mail.Message;

import javax.mail.MessagingException;

import javax.mail.Session;

import javax.mail.Transport;

import javax.mail.internet.InternetAddress;

import javax.mail.Authenticator;

import javax.mail.PasswordAuthentication;

import javax.mail.internet.MimeMessage;

public class JavaMailUtil {

    public static void sendMail(String to, String subject, String body) {
       
		final String from = "gamegalaxytn.noreply@gmail.com";

		final String password = "occxessljyetbghb";

		Properties props = new Properties();

		props.put("mail.smtp.host", "smtp.gmail.com");

		props.put("mail.smtp.port", "587");

		props.put("mail.smtp.auth", "true");

		props.put("mail.smtp.starttls.enable", "true");

		Authenticator auth = new Authenticator() {

			@Override

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(from, password);

			}

		};

		Session session = Session.getInstance(props, auth);

		MimeMessage msg = new MimeMessage(session);

		try {

			msg.addHeader("Content-type", "text/HTML");

			msg.addHeader("format", "flowed");
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
        
        try{
        	msg.setFrom(new InternetAddress( from ) );
        	msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        	msg.setSubject(subject);
        	msg.setContent(body,"text/HTML");
        	Transport.send(msg);
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }

	public static void SendReservationEmail(String to, String subject, String body)
	{
		body = "<h1>Game Galaxy</h1><p>Thank you for your reservation!</p><p>Here are the details of your reservation:</p><p>" + body + "</p><p>See you soon!</p>";
		sendMail(to, subject, body);
	}

	// Sends an email containing an activation_token to the user
	public static void SendAccountConfirmationMail(String to, String activationToken) {
		String subject = "Account confirmation";

		String body = "";
		body = "<h1>Game Galaxy</h1><p>Thank you for registering!</p><p>Here's your activation token:</p><p>"
				+ activationToken + "</p><p>See you soon!</p>";
		sendMail(to, subject, body);

	}

}
