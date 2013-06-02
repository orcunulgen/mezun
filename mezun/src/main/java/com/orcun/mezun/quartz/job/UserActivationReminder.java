package com.orcun.mezun.quartz.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.orcun.mezun.model.Role;
import com.orcun.mezun.model.User;
import com.orcun.mezun.service.SignUpService;
import com.orcun.mezun.service.admin.InactiveUserService;

public class UserActivationReminder implements StatefulJob {

	private InactiveUserService inactiveUserService;
	private SignUpService signUpService;

	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				"appContext/appContext-mezun.xml");

		inactiveUserService = (InactiveUserService) appContext
				.getBean("inactiveUserService");
		signUpService = (SignUpService) appContext.getBean("signUpService");

		Date now = new Date();

		List<User> inactiveUserList = getInactiveUserService()
				.inactiveUserList(now);

		if (inactiveUserList.size() != 0) {

			List<Role> userRoles = new ArrayList<Role>();

			Role defaultRole = new Role();
			
			defaultRole = signUpService.getRoleInfo("ROLE_ADMIN");

			userRoles.add(defaultRole);

			List<User> admins = inactiveUserService.getAllAdmin(userRoles);

			for (int i = 0; i < admins.size(); i++) {

				// -------------------Email sending-----------------

				final String username = "orcun.ulgen@gmail.com";
				final String password = "1986105?cos\"";

				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");

				Session session = Session.getInstance(props,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(username,
										password);
							}
						});

				try {

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("orcun.ulgen@gmail.com"));
					message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(admins.get(i).getEmail()));
					message.setSubject("Bekleyen Kullanıcı Aktivasyonları");

					String messageText = "Sayın Yönetici,\n\n"
							+ "Aktivasyon için beklemekte olan "
							+ inactiveUserList.size()
							+ " kullanıcı bulunmaktadır.\n\n"
							+ "Beklemekte Olan Kullanıcı Listesi :\n ";
					for (int j = 0; j < inactiveUserList.size(); j++) {
						messageText += inactiveUserList.get(j).getName() + " "
								+ inactiveUserList.get(j).getSurname() + "\n";
					}

					message.setText(messageText);

					Transport.send(message);

				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
			}

			// -------------------------------------------------
		}

	}

	public InactiveUserService getInactiveUserService() {
		return inactiveUserService;
	}

	public void setInactiveUserService(InactiveUserService inactiveUserService) {
		this.inactiveUserService = inactiveUserService;
	}

	public SignUpService getSignUpService() {
		return signUpService;
	}

	public void setSignUpService(SignUpService signUpService) {
		this.signUpService = signUpService;
	}

}
