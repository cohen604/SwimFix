package AcceptanceTests.Bridge;

public class AcceptanceTestsProxy implements AcceptanceTestsBridge {

    private AcceptanceTestsReal acceptanceTestsReal;

    public AcceptanceTestsProxy() {
        acceptanceTestsReal = new AcceptanceTestsReal();
    }

    @Override
    public boolean uploadVideoForStreamer(String user, byte[] video) {
        if(acceptanceTestsReal != null) {
            return acceptanceTestsReal.uploadVideoForStreamer(user, video);
        }
        return false;
    }

    @Override
    public boolean login(String uid, String email, String name) {
        if(acceptanceTestsReal != null){
            return acceptanceTestsReal.login(uid, email, name);
        }
        return true;
    }

    @Override
    public String streamFile(String user, String path) {
        if(acceptanceTestsReal != null) {
            return acceptanceTestsReal.streamFile(user, path);
        }
        return null;
    }

    @Override
    public boolean logout(String uid) {
        if(acceptanceTestsReal != null){
            return acceptanceTestsReal.logout(uid);
        }
        return true;
    }
}
