package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

public class sendMail {

	public static String userName = null;
	public static String passWord = null;
	public static String emailTo = null;
	public static String emailToCC = null;
	public static String starttls = null;
	public static String host = null;
	public static String port = null;
	public static Properties prop = new Properties();
	public static String socketFactoryClass = null;
	public static String fallback = null;
	public static String path = null;
	public static String moduleName = null;
	public static int indexOfComma = 0;
	public static String userFullName = null;
	public static String EMAIL_REGEX = "[a-z0-9\\_\\-\\.]+@[a-z0-9\\_\\-\\.]+\\.[a-z]+";

	public static void sendEmailToClient() throws Exception {
		String mailPropertiesFile = System.getProperty("user.dir") + "/src/main/resources/ConfigFiles/mail.properties";
		prop.load(new FileInputStream(mailPropertiesFile));
		userName = prop.getProperty("userName");
		passWord = prop.getProperty("passWord");
		
		
		emailTo = GlobalUtil.commonSettings.getEmailIds();
		emailToCC = prop.getProperty("emailToCC");

		
		
		final String subject = prop.getProperty("subject");
		host = prop.getProperty("host");
		port = prop.getProperty("port");
		starttls = prop.getProperty("starttls");
		socketFactoryClass = prop.getProperty("socketFactoryClass");
		fallback = prop.getProperty("fallback");

		Properties props = System.getProperties();
		props.put("mail.smtp.user", userName);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");

		if (!"".equals(port)) {
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.socketFactory.port", port);
		}

		if (!"".equals(starttls))
			props.put("mail.smtp.starttls.enable", starttls);

		if (!"".equals(socketFactoryClass))
			props.put("mail.smtp.socketFactory.class", socketFactoryClass);

		if (!"".equals(fallback))
			props.put("mail.smtp.socketFactory.fallback", fallback);

		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(false);

		try {

			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(userName, prop.getProperty("userFullName")));
			msg.setSubject(subject);

			
			
			if (!"".equals(emailToCC)) {

				if (emailTo.contains(",")) {
					String[] multipleEmailTo = emailTo.split(",");
					for (int j = 0; j < multipleEmailTo.length; j++) {
						if(j==0)
						msg.addRecipient(Message.RecipientType.TO, new InternetAddress(multipleEmailTo[j]));
						else
							msg.addRecipient(Message.RecipientType.CC, new InternetAddress(multipleEmailTo[j]));	
					}

				} else {
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
				}
				}

			/*	if (emailToCC.contains(",")) {
					String[] multipleCC = emailToCC.split(",");

					for (int i = 0; i < multipleCC.length; i++) {
						msg.addRecipient(Message.RecipientType.CC, new InternetAddress(multipleCC[i]));
					}
				} else

					msg.addRecipient(Message.RecipientType.CC, new InternetAddress(emailToCC));
			}*/

			else if (emailToCC == null || emailToCC.equals("")) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			}

			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setText("Hi client, \n Please find Email Report for suite:- "
					+ Utility.getValue("SuiteName") + " \n \n \n Thanks & Regards \n Test Engineer");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			if (new File(System.getProperty("user.dir") + "/ExecutionReports/ExecutionReports").exists()) {
				Utility.delDirectory(new File(System.getProperty("user.dir") + "/ExecutionReports/ExecutionReports"));
			}

			if (GlobalUtil.commonSettings.getHtmlReport().contains("Y")) {
				copyDirectoryData("HtmlReport", "HtmlReport");
			}

			if (GlobalUtil.commonSettings.getXlsReport().contains("Y")) {
				copyDirectoryData("ExcelReport", "ExcelReport");
			}
			
			if (GlobalUtil.commonSettings.getTestLogs().contains("Y")) {
				copyDirectoryData("Logs", "Logs");
			}

			Utility.createZipFile();

			messageBodyPart = new MimeBodyPart();
			String path = System.getProperty("user.dir") + "/ExecutionReports/ExecutionReports.zip";
			DataSource source = new FileDataSource(path);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("ExecutionReports.zip");
			multipart.addBodyPart(messageBodyPart);

			msg.setContent(multipart);

			Transport transport = session.getTransport("smtp");
			transport.connect(host, userName, passWord);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			System.out.println("mail sent successfully");
			// LogUtil.infoLog(sendMail.class, "Mail Sent" + "-PASS ");
			// HtmlReportUtil.stepInfo("Mail Sent" + "-PASS");
			Utility.delDirectory(new File(System.getProperty("user.dir") + "/ExecutionReports/ExecutionReports"));

		} catch (AuthenticationFailedException e1) {
			JFrame wrongCredentials = new JFrame();
			JOptionPane.showMessageDialog(wrongCredentials, "Wrong Username or Password");

		} catch (Exception e) {
			e.printStackTrace();
			// LogUtil.infoLog(sendMail.class, "Mail Sent" + "-FAIL ");
			// HtmlReportUtil.stepInfo("Mail Sent" + "-FAIL");

		}
	}

	public static void copyDirectoryData(String sourceDir, String targetDir) throws IOException {
		File srcDir = new File(System.getProperty("user.dir") + "/ExecutionReports/" + sourceDir);
		File destDir = new File(System.getProperty("user.dir") + "/ExecutionReports/ExecutionReports/" + targetDir);
		FileUtils.copyDirectory(srcDir, destDir);
	}

}