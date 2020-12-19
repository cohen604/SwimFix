package AcceptanceTests.Bridge;

import mainServer.SwimFixAPI;

public class SwimFixDriver {

    public static SwimFixAPI swimFixAPIFactory() {
        return new SwimFixAPI();
    }
}
