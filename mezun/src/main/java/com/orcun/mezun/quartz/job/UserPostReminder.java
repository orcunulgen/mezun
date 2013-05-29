package com.orcun.mezun.quartz.job;

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

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.orcun.mezun.model.User;
import com.orcun.mezun.model.enums.ContentType;
import com.orcun.mezun.model.post.Post;
import com.orcun.mezun.service.user.AllPostService;

public class UserPostReminder implements StatefulJob {

	private AllPostService allPostService;

	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				"appContext/appContext-mezun.xml");

		allPostService = (AllPostService) appContext.getBean("allPostService");

		Date now = new Date();

		List<User> allUser = allPostService.allActiveUser();

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
						return new PasswordAuthentication(username, password);
					}
				});

		
		for (int i = 0; i < allUser.size(); i++) {

			User currentUser = allUser.get(i);
			List<Post> dailyPostList = getAllPostService().dailyPostList(now,
					currentUser);

			if (dailyPostList.size() != 0) {

				try {

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("orcun.ulgen@gmail.com"));
					message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(currentUser.getEmail()));
					message.setSubject("Günlük gönderi listesi");

					String messageText = "Sayın "
							+ "<label style=\"font-weight:bold;\">"+currentUser.getName()+"</label>"
							+ " "
							+ "<label style=\"font-weight:bold;\">"+currentUser.getSurname()+"</label>"
							+ " , <br/>"
							+ "Bugün içerisinde yapılan paylaşımların listesi aşağıdaki gibidir. <br/> ";

					int sharedPhotoCounter = 0;
					int sharedEventCounter = 0;
					int sharedAnnouncementCounter = 0;
					int sharedTextCounter = 0;

					String sharedPhotoMessageText = "<label style=\"font-weight:bold;\">Fotoğraf paylaşan kişiler : </label><br/>";
					String sharedEventMessageText = "<label style=\"font-weight:bold;\">Paylaşılan etkinlikler : </label><br/>";
					String sharedAnnouncementMessageText = "<label style=\"font-weight:bold;\">Paylaşılan duyurular : </label><br/>";
					String sharedTextMessageText = "<label style=\"font-weight:bold;\">Paylaşılan Diğer Gönderiler : </label><br/>";

					for (int j = 0; j < dailyPostList.size(); j++) {

						Post currentPost = dailyPostList.get(j);

						if (currentPost.getTextTypePost().getContentType()
								.equals(ContentType.PHOTO)) {

							sharedPhotoCounter++;
							sharedPhotoMessageText += currentPost
									.getTextTypePost().getUser().getName()
									+ " "
									+ currentPost.getTextTypePost().getUser()
											.getSurname() + "<br/>";

						} else if (currentPost.getTextTypePost()
								.getContentType().equals(ContentType.EVENT)) {

							sharedEventCounter++;
							sharedEventMessageText += "---------------<br/>";
							sharedEventMessageText += "Paylaşan Kişi : ";
							sharedEventMessageText += currentPost
									.getTextTypePost().getUser().getName()
									+ " "
									+ currentPost.getTextTypePost().getUser()
											.getSurname() + "<br/>";
							sharedEventMessageText += "Etkinlik İsmi : "
									+ currentPost.getEventPost().getEvent()
											.getTitle() + "<br/>";
							sharedEventMessageText += "Etkinlik Açıklaması : "
									+ currentPost.getEventPost().getEvent()
											.getDescription() + "<br/>";

							DateTime startDate = new DateTime(currentPost
									.getEventPost().getEvent().getStartDate()
									.getTime());
							DateTime endDate = new DateTime(currentPost
									.getEventPost().getEvent().getEndDate()
									.getTime());
							DateTimeFormatter format = DateTimeFormat
									.forPattern("dd-MM-yyyy");
							String startDateStr = format.print(startDate);
							String endDateStr = format.print(endDate);

							sharedEventMessageText += "Etkinlik Başlangıç/Bitiş Tarihi : "
									+ startDateStr
									+ " / "
									+ endDateStr
									+ "<br/>";
							sharedEventMessageText += "---------------<br/>";

						} else if (currentPost.getTextTypePost()
								.getContentType()
								.equals(ContentType.ANNOUNCEMENT)) {
							sharedAnnouncementCounter++;
							sharedAnnouncementMessageText += "---------------<br/>";
							sharedAnnouncementMessageText += "Paylaşan Kişi : ";
							sharedAnnouncementMessageText += currentPost
									.getTextTypePost().getUser().getName()
									+ " "
									+ currentPost.getTextTypePost().getUser()
											.getSurname() + "<br/>";
							sharedAnnouncementMessageText += "Duyuru İsmi : "
									+ currentPost.getAnnouncementPost()
											.getAnnouncement().getTitle()
									+ "<br/>";
							sharedAnnouncementMessageText += "Duyuru Açıklaması : "
									+ currentPost.getAnnouncementPost()
											.getAnnouncement().getDescription()
									+ "<br/>";

							DateTime registeredDate = new DateTime(currentPost
									.getAnnouncementPost().getAnnouncement()
									.getRegisteredDate().getTime());
							DateTimeFormatter format = DateTimeFormat
									.forPattern("dd-MM-yyyy");
							String registeredDateStr = format
									.print(registeredDate);

							sharedAnnouncementMessageText += "Duyuru Tarihi : "
									+ registeredDateStr + "<br/>";
							sharedAnnouncementMessageText += "---------------<br/>";

						} else {
							sharedTextCounter++;
							sharedTextMessageText += "---------------<br/>";
							sharedTextMessageText += "Paylaşan Kişi : ";
							sharedTextMessageText += currentPost
									.getTextTypePost().getUser().getName()
									+ " "
									+ currentPost.getTextTypePost().getUser()
											.getSurname() + "<br/>";
							sharedTextMessageText += "Gönderi Notu : "
									+ currentPost.getTextTypePost()
											.getDescription() + "<br/>";

							DateTime registeredDate = new DateTime(currentPost
									.getTextTypePost().getPublishedDate()
									.getTime());
							DateTimeFormatter format = DateTimeFormat
									.forPattern("dd-MM-yyyy");
							String registeredDateStr = format
									.print(registeredDate);

							sharedTextMessageText += "Tarih : "
									+ registeredDateStr + "<br/>";
							sharedTextMessageText += "---------------<br/>";

						}

					}

					messageText += "Gün içerisinde : ";
					if (sharedAnnouncementCounter != 0) {
						messageText += sharedAnnouncementCounter + " duyuru, ";
					}
					if (sharedEventCounter != 0) {
						messageText += sharedEventCounter + " etkinlik, ";
					}
					if (sharedPhotoCounter != 0) {
						messageText += sharedPhotoCounter + " fotoğraf, ";
					}
					if (sharedTextCounter != 0) {
						messageText += sharedTextCounter + " gönderi, ";
					}
					messageText += "paylaşılmıştır.<br/><br/>";

					if (sharedAnnouncementCounter != 0) {
						messageText += "<br/>"+sharedAnnouncementMessageText;
					}
					if (sharedEventCounter != 0) {
						messageText += "<br/>"+sharedEventMessageText;
					}
					if (sharedPhotoCounter != 0) {
						messageText +="<br/>"+ sharedPhotoMessageText;
					}
					if (sharedTextCounter != 0) {
						messageText +="<br/>"+ sharedTextMessageText;
					}

					messageText += "Daha fazla detay için lütfen sisteme giriş yapınız.<br/>";
					//messageText += MyURLUtil.getBaseURL(FacesContext
						//	.getCurrentInstance()) + "/login.xhtml";

					message.setContent(messageText, "text/html; charset=utf-8");

					Transport.send(message);

				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}

				// -------------------------------------------------

			}
		}

	}

	public AllPostService getAllPostService() {
		return allPostService;
	}

	public void setAllPostService(AllPostService allPostService) {
		this.allPostService = allPostService;
	}

}
