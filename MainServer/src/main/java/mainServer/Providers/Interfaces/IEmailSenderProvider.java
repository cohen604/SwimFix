package mainServer.Providers.Interfaces;

public interface IEmailSenderProvider {

    boolean sendInvitationEmail(String from, String to);
}
