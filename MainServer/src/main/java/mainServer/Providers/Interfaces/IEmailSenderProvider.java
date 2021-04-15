package mainServer.Providers.Interfaces;

public interface IEmailSenderProvider {

    boolean sendInvetaionEmail(String from, String to);
}
