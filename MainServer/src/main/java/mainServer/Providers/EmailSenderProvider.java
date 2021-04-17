package mainServer.Providers;

import mainServer.Providers.Interfaces.IEmailSenderProvider;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSenderProvider implements IEmailSenderProvider {

    private static String SWIM_ANALYTICS_EMAIL = "swimfixofficial@gmail.com";
    private static String SWIM_ANALYTICS_PASSWORD = "swimfix2020";

    @Override
    public boolean sendInvitationEmail(String from, String to) {
        Session session = getSession();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SWIM_ANALYTICS_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Invitation to Swim analytics");
            message.setContent(getContent(from), getContentType());
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getContent(String userName) {
        return "<body dir=\"ltr\"> " +
                "<h2>Hi, " + userName +
                " invited you to join Swim Analytics." +
                "</h2>\n" +
                "<p>Swim analytucs, is the best platform to improve your swimming abilities.</p>\n" +
                "<p>The platform allows any swimmer to record, view, train, receive swimming videos, feedback's and many more.</p>\n" +
                "<p>You are welcome to join our swimming platform via swim analytics web site or mobile application.</p>\n" +
                "<p>web site:&nbsp;<a href=\"https://swimanalytics.web.app/\">https://swimanalytics.web.app/</a></p>\n" +
                "<p>for download our mobile application go to " +
                "<a href=\"https://swimanalytics.web.app/downloads\">https://swimanalytics.web.app/downloads</a>" +
                "</p>\n" +
                "<p>&nbsp;</p>" +
                "</body>";
    }

    private String getContentType() {
        return "text/html";
    }


    private Properties getEmailProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.prot", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.user", SWIM_ANALYTICS_EMAIL);
        properties.put("mail.password", SWIM_ANALYTICS_PASSWORD);
        properties.put("mail.smtp.starttls.enable", "true");

        return properties;
    }

    private Session getSession() {
        Properties properties = getEmailProperties();
        return Session.getDefaultInstance(properties,  new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        SWIM_ANALYTICS_EMAIL, SWIM_ANALYTICS_PASSWORD);// Specify the Username and the PassWord
            }});
    }
}
