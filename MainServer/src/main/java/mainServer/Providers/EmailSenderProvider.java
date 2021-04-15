package mainServer.Providers;

import mainServer.Providers.Interfaces.IEmailSenderProvider;

public class EmailSenderProvider implements IEmailSenderProvider {

    @Override
    public boolean sendInvetaionEmail(String from, String to) {
        return false;
    }
}
