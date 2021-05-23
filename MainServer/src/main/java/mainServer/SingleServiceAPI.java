package mainServer;

public class SingleServiceAPI {
    private static SwimFixAPI swimFixAPI;

    public static SwimFixAPI getInstance(){
        if (swimFixAPI == null)
            swimFixAPI = new SwimFixAPI("swimfix");
        return swimFixAPI;
    }
}
